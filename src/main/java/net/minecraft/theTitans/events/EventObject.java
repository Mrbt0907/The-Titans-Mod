package net.minecraft.theTitans.events;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.network.NetworkHandler;
import net.minecraft.theTitans.network.packets.PacketRemoveBar;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
import net.mrbt0907.utils.Maths.Vec;
public class EventObject
{
	public static final List<EventObject> instances = new ArrayList<EventObject>();
	protected UUID uuid;
	private boolean[] timesAllowed = {true, true, true, true};
	
	public int time;
	public int ticksExisted;
	public World worldObj;
	protected String unlocalizedName;
	protected boolean enabled;
	protected boolean worldwide;
	protected double chance;
	public double range;
	public Vec position;
	private int[] entityLimit = {1, 1, 40, 20, 5, 5};
	private int[] entities = {0, 0, 0, 0, 0, 0};
	private int maxEntities;
	private int killed;
	private final List<Class<?>> bosses = new ArrayList<Class<?>>();
	private final List<Class<?>> miniBosses = new ArrayList<Class<?>>();
	private final List<Class<?>> commonEntities = new ArrayList<Class<?>>();
	private final List<Class<?>> uncommonEntities = new ArrayList<Class<?>>();
	private final List<Class<?>> rareEntities = new ArrayList<Class<?>>();
	private final List<Class<?>> specialEntities = new ArrayList<Class<?>>();
	EventObject(Entity entity, World worldObj, String unlocalizedName, double chance, double range)
	{
		if (entity == null)
		worldwide = true;
		else
		position = new Vec(entity.posX, entity.posZ);
		uuid = UUID.randomUUID();
		this.worldObj = worldObj;
		this.unlocalizedName = unlocalizedName;
		this.chance = chance;
		this.range = range;
	}

	public static EventObject create(Entity entity, World worldObj, String unlocalizedName, double chance, double range)
	{
		int dimensionID = worldObj.provider.dimensionId;
		instances.add(new EventObject(entity, worldObj, unlocalizedName, chance, range));
		for (EventObject event : instances)
		if (instances.get(instances.size() - 1).uuid.equals(event.uuid) && event.worldwide && instances.get(instances.size() - 1).worldwide && event.worldObj.provider.dimensionId == dimensionID)
		{
			instances.remove(instances.size() - 1);
			return null;
		}

		return instances.get(instances.size() - 1);
	}

	public String getName()
	{
		return TheTitans.translate("event", unlocalizedName);
	}

	public EventObject setUnlocalizedName(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
		return this;
	}

	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}

	public void start(int bosses, int minibosses, int entities, int... limits)
	{
		EventData.sendMessage(worldObj, TheTitans.translate("event", unlocalizedName + "_start_" + Maths.random(1, 5)));
		enabled = true;
		maxEntities = (bosses * 30) + (minibosses * 5) + entities;
		killed = 0;
		if (limits.length > 0)
		for (int i = 0; i < limits.length; i++)
		entityLimit[i] = limits[i]; 
	}

	public void stop()
	{
		EventData.sendMessage(worldObj, TheTitans.translate("event", unlocalizedName + "_stop_" + Maths.random(1, 5)));
		enabled = false;
		timesAllowed = new boolean[] {true, true, true, true};
		entityLimit = new int[] {1, 1, 40, 20, 5, 5};
		entities = new int[] {0, 0, 0, 0, 0, 0};
		maxEntities = 0;
		killed = 0;
		NetworkHandler.sendClientPacket(new PacketRemoveBar(uuid.toString()));
		instances.remove(this);
	}

	public void onUpdate(Side side)
	{
		time = (int)((worldObj.getWorldTime() % 24000L) / 6000L);
		if (enabled)
		{
			if (killed >= maxEntities)
			stop();
			if (!timesAllowed[time])
			stop();
		}

		if (enabled)
		if (side == Side.CLIENT)
		onClientUpdate();
		else if (side == Side.SERVER)
		onServerUpdate();
		ticksExisted ++;
	}

	@SideOnly(Side.CLIENT)
	protected void onClientUpdate()
	{
	}

	protected void onServerUpdate()
	{
		if (ticksExisted % 10 == 0)
		{
			List<?> players = worldObj.playerEntities;
			Vec position;
			Vec offset;
			for (Object player : players)
			{
				position = new Vec(((EntityPlayer)player).posX, ((EntityPlayer)player).posZ);
				if (!worldwide && this.position != null && position.distance(this.position) > range)
				break;
				for (int i = 0; i < bosses.size(); i++)
				if (Maths.chance(0.0005F))
				{
					offset = new Vec(Math.floor(position.vecX) + 0.5D + Maths.random(-32, 32), Math.floor(position.vecY) + 0.5D + Maths.random(-32, 32));
					spawnEntity(0, Maths.random(bosses.size() - 1), offset.vecX, worldObj.getTopSolidOrLiquidBlock((int)offset.vecX, (int)offset.vecY), offset.vecY);
					break;
				}

				for (int i = 0; i < miniBosses.size(); i++)
				if (Maths.chance(0.01F))
				{
					offset = new Vec(Math.floor(position.vecX) + 0.5D + Maths.random(-32, 32), Math.floor(position.vecY) + 0.5D + Maths.random(-32, 32));
					spawnEntity(1, Maths.random(miniBosses.size() - 1), offset.vecX, worldObj.getTopSolidOrLiquidBlock((int)offset.vecX, (int)offset.vecY), offset.vecY);
					break;
				}

				for (int i = 0; i < commonEntities.size(); i++)
				if (Maths.chance(0.3F))
				{
					offset = new Vec(Math.floor(position.vecX) + 0.5D + Maths.random(-32, 32), Math.floor(position.vecY) + 0.5D + Maths.random(-32, 32));
					spawnEntity(2, Maths.random(commonEntities.size() - 1), offset.vecX, worldObj.getTopSolidOrLiquidBlock((int)offset.vecX, (int)offset.vecY), offset.vecY);
					break;
				}

				for (int i = 0; i < uncommonEntities.size(); i++)
				if (Maths.chance(0.075F))
				{
					offset = new Vec(Math.floor(position.vecX) + 0.5D + Maths.random(-32, 32), Math.floor(position.vecY) + 0.5D + Maths.random(-32, 32));
					spawnEntity(3, Maths.random(uncommonEntities.size() - 1), offset.vecX, worldObj.getTopSolidOrLiquidBlock((int)offset.vecX, (int)offset.vecY), offset.vecY);
					break;
				}

				for (int i = 0; i < rareEntities.size(); i++)
				if (Maths.chance(0.001F))
				{
					offset = new Vec(Math.floor(position.vecX) + 0.5D + Maths.random(-32, 32), Math.floor(position.vecY) + 0.5D + Maths.random(-32, 32));
					spawnEntity(4, Maths.random(rareEntities.size() - 1), offset.vecX, worldObj.getTopSolidOrLiquidBlock((int)offset.vecX, (int)offset.vecY), offset.vecY);
					break;
				}

				for (int i = 0; i < specialEntities.size(); i++)
				if (Maths.chance(0.025F))
				{
					offset = new Vec(Math.floor(position.vecX) + 0.5D + Maths.random(-32, 32), Math.floor(position.vecY) + 0.5D + Maths.random(-32, 32));
					spawnEntity(5, Maths.random(specialEntities.size() - 1), offset.vecX, worldObj.getTopSolidOrLiquidBlock((int)offset.vecX, (int)offset.vecY), offset.vecY);
					break;
				}
			}
		}
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	protected int getEntityValue(int type)
	{
		switch(type)
		{
			case 0: return 30;
			case 1: return 5;
			case 5: return 2;
			default: return 1;
		}
	}

	public int getAmountMax()
	{
		return maxEntities;
	}

	public int getAmountLeft()
	{
		return maxEntities - killed;
	}

	public int getAmountDead()
	{
		return killed;
	}

	public int getAmountAlive()
	{
		return (entities[0] * 30) + (entities[1] * 5) + entities[2] + entities[3] + entities[4] + (entities[5] * 2);
	}

	public boolean canSee(Entity entity)
	{
		return enabled && entity.dimension == worldObj.provider.dimensionId && (worldwide || position.distance(entity.posX, entity.posZ) < range);
	}

	public EventObject setTimeRange(boolean... flags)
	{
		for(int i = 0; i < flags.length; i++)
		timesAllowed[i] = flags[i];
		return this;
	}

	protected boolean spawnEntity(int type, int index, double x, double y, double z)
	{
		List<Class<?>> list = null;
		switch(type)
		{
			case 0: list = bosses; break;
			case 1: list = miniBosses; break;
			case 2: list = commonEntities; break;
			case 3: list = uncommonEntities; break;
			case 4: list = rareEntities; break;
			case 5: list = specialEntities;
		}

		if (worldObj == null || list == null || list.isEmpty() || getAmountAlive() + getEntityValue(type) >= maxEntities || entities[type] >= entityLimit[type])
		return false;
		Entity entity;
		try
		{
			entity = (Entity) list.get(MathHelper.clamp_int(index, 0, Math.max(list.size() - 1, 0))).getConstructor(World.class).newInstance(worldObj);
		}

		catch (Exception e)
		{
			TheTitans.error("Entity from class " + list.get(MathHelper.clamp_int(index, 0, Math.max(list.size() - 1, 0))).getSimpleName() + " does not have the constructor Entity(World)", e);
			return false;
		}

		if (entity instanceof EntityLiving)
		((EntityLiving)entity).onSpawnWithEgg(null);
		entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
		entity.getEntityData().setString("TitanEvent", uuid.toString());
		((EntityLiving)entity).setAttackTarget(worldObj.getClosestPlayer(position.vecX, worldObj.getTopSolidOrLiquidBlock((int)position.vecX, (int)position.vecY), position.vecY, range));
		worldObj.spawnEntityInWorld(entity);
		entities[type] ++;
		return true;
	}

	public boolean onDeath(Entity entity)
	{
		if (!enabled || !entity.getEntityData().getString("TitanEvent").equals(uuid.toString()))
		return false;
		entity.getEntityData().setString("TitanEvent", "");
		for (int i = 0; i < 6; i++)
		switch(i)
		{
			case 0:
			{
				for (Class<?> clazz : bosses)
				{
					if (entities[i] <= 0)
					break;
					if (entity.getClass().equals(clazz))
					{
						entities[i] --;
						killed += 30;
						return true;
					}
				}

				break;
			}

			case 1:
			{
				for (Class<?> clazz : miniBosses)
				{
					if (entities[i] <= 0)
					break;
					if (entity.getClass().equals(clazz))
					{
						entities[i] --;
						killed += 5;
						return true;
					}
				}

				break;
			}

			case 2:
			{
				for (Class<?> clazz : commonEntities)
				{
					if (entities[i] <= 0)
					break;
					if (entity.getClass().equals(clazz))
					{
						entities[i] --;
						killed ++;
						return true;
					}
				}

				break;
			}

			case 3:
			{
				for (Class<?> clazz : uncommonEntities)
				{
					if (entities[i] <= 0)
					break;
					if (entity.getClass().equals(clazz))
					{
						entities[i] --;
						killed ++;
						return true;
					}
				}

				break;
			}

			case 4:
			{
				for (Class<?> clazz : rareEntities)
				{
					if (entities[i] <= 0)
					break;
					if (entity.getClass().equals(clazz))
					{
						entities[i] --;
						killed ++;
						return true;
					}
				}

				break;
			}

			case 5:
			{
				for (Class<?> clazz : specialEntities)
				{
					if (entities[i] <= 0)
					break;
					if (entity.getClass().equals(clazz))
					{
						entities[i] --;
						killed += 2;
						return true;
					}
				}
			}
		}

		return false;
	}

	public EventObject addBosses(Class<?>... entities)
	{
		for (Class<?> entity : entities)
		if (entity != null && Entity.class.isAssignableFrom(entity))
		bosses.add(entity);
		return this;
	}

	public EventObject addMiniBosses(Class<?>... entities)
	{
		for (Class<?> entity : entities)
		if (entity != null && Entity.class.isAssignableFrom(entity))
		miniBosses.add(entity);
		return this;
	}

	public EventObject addCommon(Class<?>... entities)
	{
		for (Class<?> entity : entities)
		if (entity != null && Entity.class.isAssignableFrom(entity))
		commonEntities.add(entity);
		return this;
	}

	public EventObject addUncommon(Class<?>... entities)
	{
		for (Class<?> entity : entities)
		if (entity != null && Entity.class.isAssignableFrom(entity))
		uncommonEntities.add(entity);
		return this;
	}

	public EventObject addRare(Class<?>... entities)
	{
		for (Class<?> entity : entities)
		if (entity != null && Entity.class.isAssignableFrom(entity))
		rareEntities.add(entity);
		return this;
	}

	public EventObject addSpecial(Class<?>... entities)
	{
		for (Class<?> entity : entities)
		if (entity != null && Entity.class.isAssignableFrom(entity))
		specialEntities.add(entity);
		return this;
	}

	public UUID getBarUUID()
	{
		return uuid;
	}
}


