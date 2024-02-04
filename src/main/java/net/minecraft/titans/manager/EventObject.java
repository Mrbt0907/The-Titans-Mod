package net.minecraft.titans.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.api.ITitanBossBar;
import net.minecraft.titans.network.EnumPackets;
import net.minecraft.titans.network.NetworkHandler;
import net.minecraft.titans.utils.ChunkUtils;
import net.minecraft.titans.utils.Maths;
import net.minecraft.titans.utils.Maths.Vec3;
import net.minecraft.titans.utils.TranslateUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EventObject implements ITitanBossBar
{
	private UUID uuid;
	private int type;
	public Vec3 pos;
	protected World world;
	protected int dimension;
	protected String unlocalizedName;
	protected final List<EntityLiving> entities = new ArrayList<EntityLiving>();
	protected final List<EventMobEntry> spawnList = new ArrayList<EventMobEntry>();
	protected final List<EntityPlayer> players = new ArrayList<EntityPlayer>();
	protected int spawned, totalSpawned, killed, neededKills;
	protected double distance;
	public boolean isDead;
	
	
	public EventObject(World world)
	{
		this.world = world;
		this.pos = new Vec3();
	}
	
	public EventObject(World world, Vec3 pos, EnumEvents event)
	{
		this(world, event.getUnlocalizedName(), event.getType(), pos, event.getEventRange(), event.getKillsNeeded());
		event.addMobs(this);
	}
	
	public EventObject(World world, String unlocalizedName, int type, Vec3 pos, double distance, int neededKills)
	{
		uuid = UUID.randomUUID();
		this.world = world;
		dimension = this.world.provider.getDimension();
		this.unlocalizedName = unlocalizedName;
		this.type = type;
		this.neededKills = neededKills;
		this.pos = pos;
		this.distance = distance;
	}
	
	public EventObject readNBT(NBTTagCompound nbt)
	{
		NBTTagCompound entryNBT;
		NBTTagCompound nbtEntries = null;

		if (nbt.hasKey("entries"))
			nbtEntries = (NBTTagCompound) nbt.getTag("entries");
		
		if (nbt.hasKey("name"))
			unlocalizedName = nbt.getString("name");
		if (nbt.hasKey("type"))
			type = nbt.getInteger("type");
		uuid = nbt.getUniqueId("uuid");
		if (nbt.hasKey("neededKills"))
			neededKills = nbt.getInteger("neededKills");
		if (nbt.hasKey("killed"))
			killed = nbt.getInteger("killed");
		if (nbt.hasKey("totalSpawned"))
			totalSpawned = nbt.getInteger("totalSpawned");
		if (nbt.hasKey("spawned"))
			spawned = nbt.getInteger("spawned");
		if (nbt.hasKey("distance"))
			distance = nbt.getDouble("distance");
		if (nbt.hasKey("posX"))
			pos = new Vec3(nbt.getDouble("posX"), nbt.getDouble("posY"), nbt.getDouble("posZ"));
		
		if (nbtEntries != null)
			for (String key : nbtEntries.getKeySet())
			{
				try
				{
					entryNBT = (NBTTagCompound) nbtEntries.getTag(key);
					addMob(Class.forName(entryNBT.getString("class")), entryNBT.getFloat("chance"));
				}
				catch (ClassNotFoundException e)
				{
					TheTitans.error("Mob Class was not found");
				}
			}
		
		return this;
	}
	
	public NBTTagCompound writeNBT(NBTTagCompound nbt)
	{
		nbt.setString("name", unlocalizedName);
		nbt.setInteger("type", type);
		nbt.setUniqueId("uuid", uuid);
		nbt.setInteger("dimension", dimension);
		nbt.setInteger("neededKills", neededKills);
		nbt.setInteger("killed", killed);
		nbt.setInteger("totalSpawned", totalSpawned);
		nbt.setInteger("spawned", spawned);
		nbt.setDouble("distance", distance);

		nbt.setDouble("posX", pos.posX);
		nbt.setDouble("posY", pos.posY);
		nbt.setDouble("posZ", pos.posZ);
		
		NBTTagCompound entryNBT;
		NBTTagCompound nbtEntries = new NBTTagCompound();
		for (EventMobEntry entry : spawnList)
		{
			entryNBT = new NBTTagCompound();
			entryNBT.setString("class", entry.entity.getName());
			entryNBT.setFloat("chance", entry.chance);
			nbtEntries.setTag("entry_" + entry.entity.getName(), entryNBT);
		}
		nbt.setTag("entries", nbtEntries);
		
		return nbt;
	}
	
	public void tick()
	{
		if (world.getTotalWorldTime() % 5L == 0L)
		{
			if (spawned < 30)
				for (EventMobEntry entry : spawnList)
					if (entry.spawn(false))
					{
						spawned++;
						totalSpawned++;
					}
			
			for (EntityPlayer player : world.playerEntities)
			{
				boolean contains = players.contains(player);
				
				if (Maths.distance(player.posX, player.posY, player.posZ, pos) <= this.distance)
				{
					if (!contains)	
						players.add(player);
				}
				else if (contains)
					players.remove(player);
			}
			
			entities.forEach(entity -> {
				if (entity.getAttackTarget() == null || !entity.getAttackTarget().isEntityAlive())
				{
					EntityPlayer ent = null;
					double distance = Double.MAX_VALUE, dist;
					
					for (EntityPlayer player : players)
					{
						dist = pos.distance(player.posX, pos.posY, player.posZ);
						if (player.isEntityAlive() && dist < distance)
						{
							ent = player;
							distance = dist;
						}
					}
					
					entity.setAttackTarget(ent);
				}
			});
		}
	}
	
	public void tickClient()
	{
		
	}
	
	public boolean onEntityJoined(Entity entity)
	{
		if (entity.world.isRemote) return false;
		UUID uuid = entity.getEntityData().getUniqueId("TitanEvent");
		if (uuid == null || !uuid.equals(this.uuid) || !(entity instanceof EntityLiving)) return false;
		
		entities.add((EntityLiving) entity);
		return true;
	}
	
	public boolean onEntityKilled(Entity entity)
	{
		if (entity.world.isRemote) return false;
		UUID uuid = entity.getEntityData().getUniqueId("TitanEvent");
		if (uuid == null || !uuid.equals(this.uuid)) return false;
		
		spawned--;
		killed++;
		entities.remove(entity);
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setUniqueId("uuid", uuid);
		nbt.setInteger("neededKills", neededKills);
		nbt.setInteger("killed", killed);
		nbt.setInteger("totalSpawned", totalSpawned);
		nbt.setInteger("spawned", spawned);
		
		NetworkHandler.sendClientPacket(EnumPackets.EVENT_UPDATE, nbt, dimension);
		
		TheTitans.debug("Killed " + entity.getName() + " for " + getName() + " event  (remaining:" + (neededKills - killed) + ", uuid:" + uuid + ")");
		if (killed >= neededKills)
			isDead = true;
		
		return true;
	}
	
	public EventObject addMob(Class<?> entity, float chance)
	{
		if (entity != null && Entity.class.isAssignableFrom(entity))
			spawnList.add(new EventMobEntry(entity, MathHelper.clamp(chance, 0.0F, 1.0F)));
				
		return this;
	}
	
	public EventObject addMobs(Class<?>... entities)
	{
		for(Class<?> entity : entities)
			if (entity != null && Entity.class.isAssignableFrom(entity))
				spawnList.add(new EventMobEntry(entity, 0.25F));
				
		return this;
	}
	
	public void reset()
	{
		entities.clear();
		players.clear();
	}
	
	public int getAmountKilled()
	{
		return killed;
	}
	
	public int getKillsNeeded()
	{
		return neededKills;
	}
	
	public int getKillsLeft()
	{
		return neededKills - killed;
	}
	
	public int getAmountSpawned()
	{
		return totalSpawned;
	}
	
	public boolean canSee(EntityPlayer player)
	{
		return players.contains(player);
	}
	
	public String getName()
	{
		return TranslateUtil.translate("event." + unlocalizedName + ".name");
	}
	
	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}
	
	public int getType()
	{
		return type;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public int getDimension()
	{
		return dimension;
	}
	
	public static class EventMobEntryPre
	{
		public Class<?> entity;
		public float chance;
		
		public EventMobEntryPre(Class<?> entity, float chance)
		{
			this.entity = entity;
			this.chance = chance;
		}
	}
		
	private class EventMobEntry
	{
		private Class<?> entity;
		private float chance;
		
		public EventMobEntry(Class<?> entity, float chance)
		{
			this.entity = entity;
			this.chance = chance;
		}
		
		public boolean spawn(boolean forced)
		{
			if (!forced && !Maths.chance(chance) || players.size() == 0) return false;
			
			Entity entity;
			EntityPlayer player = players.get(Maths.random(players.size() - 1));
			BlockPos blockPos = new BlockPos(player.posX + Maths.random(-32.0D, 32.0D), player.posY + Maths.random(-10.0D, 10.0D), player.posZ + Maths.random(-32.0D, 32.0D)).up();
			int y;
			boolean isValid = true;
			
			try
			{
				entity = (Entity) this.entity.getConstructor(World.class).newInstance(world);
			}
			catch (Exception e)
			{
				TheTitans.error("Entity from class " + this.entity.getClass().getSimpleName() + " does not have the constructor Entity(World)");
				return false;
			}

			while(blockPos.getY() > player.chasingPosY - 10.0D)
			{
				if (ChunkUtils.isValidPos(world, blockPos.down().getY()) && ChunkUtils.getBlockState(world, blockPos.down()).isFullBlock())
				{
					y = blockPos.getY();
					for(; y < blockPos.getY() + entity.height; y++)
					{
						if (!ChunkUtils.isValidPos(world, y) || ChunkUtils.getBlockState(world, blockPos.getX(), y, blockPos.getZ()) != Blocks.AIR.getDefaultState())
						{
							isValid = false;
							break;
						}
					}
					if (isValid)
						break;
				}
				
				
				
				blockPos = blockPos.down();
				if (blockPos.getY() <= player.chasingPosY - 10.0D || !ChunkUtils.isValidPos(world, blockPos.getY()))
					return false;
			}
			
			entity.setLocationAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.0F, 0.0F);
			entity.getEntityData().setUniqueId("TitanEvent", uuid);
			((EntityLiving)entity).setAttackTarget(player);
			world.spawnEntity(entity);
			return true;
		}
	}

	@Override
	public boolean canRenderBar()
	{
		return true;
	}

	@Override
	public boolean hasStamina()
	{
		return false;
	}

	@Override
	public boolean canShowDamage()
	{
		return false;
	}

	@Override
	public double getBarHealth()
	{
		return neededKills - killed;
	}

	@Override
	public double getBarMaxHealth()
	{
		return neededKills;
	}

	@Override
	public double getBarStamina()
	{
		return 0;
	}

	@Override
	public double getBarMaxStamina()
	{
		return 0;
	}

	@Override
	public ResourceLocation getBarTexture()
	{
		return new ResourceLocation(TheTitans.MODID, "textures/gui/eventbars/base.png");
	}

	public UUID getUniqueID()
	{
		return uuid;
	}

	@Override
	public UUID getUniqueBarID()
	{
		return getUniqueID();
	}

	@Override
	public String getBarName()
	{
		return getName();
	}
}
