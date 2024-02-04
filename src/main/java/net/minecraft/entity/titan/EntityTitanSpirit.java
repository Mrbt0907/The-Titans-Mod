package net.minecraft.entity.titan;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.titanminion.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.theTitans.ChunkLoadingEvent;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.world.WorldProviderVoid;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
public class EntityTitanSpirit extends EntityFlying
{
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private EntityLivingBase prevEntity;
	public EntityTitanSpirit(World worldIn)
	{
		super(worldIn);
		setSize(8.0F, 8.0F);
		noClip = true;
		isImmuneToFire = true;
		ignoreFrustumCheck = true;
		playSound("thetitans:titanBirth", 10000.0F, 2.0F);
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(12, Byte.valueOf((byte)0));
		dataWatcher.addObject(13, Float.valueOf(1.0F));
		dataWatcher.addObject(14, new Integer(0));
		dataWatcher.addObject(15, new Integer(0));
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		setTonnage(tagCompund.getFloat("Tonnage"));
		setSpiritType(tagCompund.getInteger("SpiritType"));
		setSpiritNameID(tagCompund.getInteger("SpiritNameID"));
		setVesselHunting(tagCompund.getBoolean("ShouldHuntForVessels"));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setFloat("Tonnage", getTonnage());
		tagCompound.setInteger("SpiritType", getSpiritType());
		tagCompound.setInteger("SpiritNameID", getSpiritNameID());
		tagCompound.setBoolean("ShouldHuntForVessels", isVesselHunting());
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(Double.MAX_VALUE);
	}

	public boolean isVesselHunting()
	{
		return dataWatcher.getWatchableObjectByte(12) == 1;
	}

	public void setVesselHunting(boolean hunting)
	{
		dataWatcher.updateObject(12, Byte.valueOf(hunting ? (byte)1 : (byte)0));
	}

	public int getSpiritType()
	{
		return dataWatcher.getWatchableObjectInt(14);
	}

	public void setSpiritType(int type)
	{
		dataWatcher.updateObject(14, Integer.valueOf(type));
	}

	public int getSpiritNameID()
	{
		return dataWatcher.getWatchableObjectInt(15);
	}

	public void setSpiritNameID(int nameid)
	{
		dataWatcher.updateObject(15, Integer.valueOf(nameid));
	}

	public float getTonnage()
	{
		return dataWatcher.getWatchableObjectFloat(13);
	}

	public void setTonnage(float ton)
	{
		dataWatcher.updateObject(13, ton);
	}

	public float getMaxTonnage()
	{
		switch (getSpiritType())
		{
			case 1:
			return 80000F;
			case 2:
			return 120000F;
			case 3:
			return 160000F;
			case 4:
			return 400000F;
			case 5:
			return 2000000F;
			case 6:
			return 400000F;
			case 7:
			return 400000F;
			case 8:
			return 400000F;
			case 9:
			return 400000F;
			case 10:
			return 1000000F;
			case 11:
			return 4000000F;
			case 12:
			return 30000000000F;
			case 13:
			return 20000000000F;
			default:
			return 1F;
		}
	}

	public String getCommandSenderName()
	{
		switch (getSpiritType())
		{
			case 1:
			return StatCollector.translateToLocal("entity.SilverfishTitan.name");
			case 2:
			return StatCollector.translateToLocal("entity.CaveSpiderTitan.name");
			case 3:
			return StatCollector.translateToLocal("entity.SpiderTitan.name");
			case 4:
			return StatCollector.translateToLocal("entity.SkeletonTitan.name");
			case 5:
			return StatCollector.translateToLocal("entity.WitherSkeletonTitan.name");
			case 6:
			return StatCollector.translateToLocal("entity.ZombieTitan.name");
			case 7:
			return StatCollector.translateToLocal("entity.CreeperTitan.name");
			case 8:
			return StatCollector.translateToLocal("entity.PigZombieTitan.name");
			case 9:
			return StatCollector.translateToLocal("entity.BlazeTitan.name");
			case 10:
			return StatCollector.translateToLocal("entity.EndermanTitan.realname");
			case 11:
			return StatCollector.translateToLocal("entity.GhastTitan.name");
			case 12:
			return "\u00A7kRegnator";
			default:
			return StatCollector.translateToLocal("entity.TitanSpirit.name");
		}
	}

	@SuppressWarnings("unchecked")
	public void setDead()
	{
		if (!worldObj.isRemote)
		{
			switch (getSpiritType())
			{
				case 1:
				worldObj.newExplosion(this, posX, posY, posZ, 18.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntitySilverfishTitan omegafish = new EntitySilverfishTitan(worldObj);
				omegafish.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				omegafish.func_82206_m();
				worldObj.spawnEntityInWorld(omegafish);
				break;
				case 2:
				worldObj.newExplosion(this, posX, posY, posZ, 12.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntityCaveSpiderTitan cavespidertitan = new EntityCaveSpiderTitan(worldObj);
				cavespidertitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				cavespidertitan.func_82206_m();
				worldObj.spawnEntityInWorld(cavespidertitan);
				break;
				case 3:
				worldObj.newExplosion(this, posX, posY, posZ, 12.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntitySpiderTitan spidertitan = new EntitySpiderTitan(worldObj);
				spidertitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				spidertitan.func_82206_m();
				worldObj.spawnEntityInWorld(spidertitan);
				break;
				case 4:
				worldObj.newExplosion(this, posX, posY, posZ, 18.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntitySkeletonTitan skeletontitan = new EntitySkeletonTitan(worldObj);
				skeletontitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				skeletontitan.setSkeletonType(0);
				skeletontitan.func_82206_m();
				worldObj.spawnEntityInWorld(skeletontitan);
				break;
				case 5:
				worldObj.newExplosion(this, posX, posY, posZ, 18.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntitySkeletonTitan witherskeletontitan = new EntitySkeletonTitan(worldObj);
				witherskeletontitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				witherskeletontitan.setSkeletonType(1);
				witherskeletontitan.func_82206_m();
				worldObj.spawnEntityInWorld(witherskeletontitan);
				break;
				case 6:
				worldObj.newExplosion(this, posX, posY, posZ, 18.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntityZombieTitan zombietitan = new EntityZombieTitan(worldObj);
				zombietitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				zombietitan.onSpawnWithEgg((IEntityLivingData)null);
				zombietitan.func_82206_m();
				worldObj.spawnEntityInWorld(zombietitan);
				if (prevEntity != null)
				{
					zombietitan.setChild(((EntityZombieMinion)prevEntity).isChild());
					zombietitan.setVillager(((EntityZombieMinion)prevEntity).isVillager());
				}

				break;
				case 7:
				worldObj.newExplosion(this, posX, posY, posZ, 18.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntityCreeperTitan creepertitan = new EntityCreeperTitan(worldObj);
				creepertitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				creepertitan.func_82206_m();
				worldObj.spawnEntityInWorld(creepertitan);
				if (prevEntity != null)
				creepertitan.setPowered(((EntityCreeperMinion)prevEntity).getPowered());
				break;
				case 8:
				worldObj.newExplosion(this, posX, posY, posZ, 18.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntityPigZombieTitan pigzombietitan = new EntityPigZombieTitan(worldObj);
				pigzombietitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				pigzombietitan.func_82206_m();
				worldObj.spawnEntityInWorld(pigzombietitan);
				if (prevEntity != null)
				pigzombietitan.setChild(((EntityPigZombieMinion)prevEntity).isChild());
				break;
				case 9:
				worldObj.newExplosion(this, posX, posY, posZ, 16.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntityBlazeTitan blazetitan = new EntityBlazeTitan(worldObj);
				blazetitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				blazetitan.func_82206_m();
				worldObj.spawnEntityInWorld(blazetitan);
				break;
				case 10:
				worldObj.newExplosion(this, posX, posY, posZ, 45.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntityEnderColossus endercolossus = new EntityEnderColossus(worldObj);
				endercolossus.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				endercolossus.func_82206_m();
				worldObj.spawnEntityInWorld(endercolossus);
				break;
				case 11:
				worldObj.newExplosion(this, posX, posY, posZ, 8.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				EntityGhastTitan ghasttitan = new EntityGhastTitan(worldObj);
				ghasttitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				ghasttitan.func_82206_m();
				worldObj.spawnEntityInWorld(ghasttitan);
				break;
				case 12:
				if ((worldObj.provider instanceof WorldProviderVoid))
				{
					if (prevEntity != null)
					((EntityPlayer)prevEntity).addChatMessage(new ChatComponentText("In the end, you are your own undoing."));
					worldObj.theProfiler.startSection("changeDimension");
					MinecraftServer minecraftserver = MinecraftServer.getServer();
					int j = dimension;
					WorldServer worldserver = minecraftserver.worldServerForDimension(j);
					WorldServer worldserver1 = minecraftserver.worldServerForDimension(0);
					dimension = 0;
					worldObj.theProfiler.startSection("reposition");
					minecraftserver.getConfigurationManager().transferEntityToWorld(this, j, worldserver, worldserver1);
					worldObj.theProfiler.endStartSection("reloading");
					Entity entity = EntityList.createEntityByName(EntityList.getEntityString(this), worldserver1);
					if (entity != null)
					{
						entity.copyDataFrom(this, true);
						worldserver1.spawnEntityInWorld(entity);
					}

					worldObj.theProfiler.endSection();
					worldserver.resetUpdateEntityTick();
					worldserver1.resetUpdateEntityTick();
					worldObj.theProfiler.endSection();
				}

				else
				{
					if (prevEntity != null)
					{
						((EntityPlayer)prevEntity).attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
						((EntityPlayer)prevEntity).setHealth(0F);
						((EntityPlayer)prevEntity).getDataWatcher().updateObject(6, Float.valueOf(MathHelper.clamp_float(0.0F, 0.0F, ((EntityPlayer)prevEntity).getMaxHealth())));
					}

					worldObj.newExplosion(this, posX, posY, posZ, 7.0F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					EntityWitherzilla witherzilla = new EntityWitherzilla(worldObj);
					witherzilla.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
					witherzilla.func_82206_m();
					worldObj.spawnEntityInWorld(witherzilla);
					if (prevEntity != null)
					((EntityPlayer)prevEntity).addChatMessage(new ChatComponentText("This universe is flawed, Notch. They've corrupted it. It has to go."));
				}

				break;
			}

			ArrayList<?> listp = Lists.newArrayList(worldObj.playerEntities);
			if (getSpiritType() != 12 && (listp != null) && (!listp.isEmpty()) && !worldObj.isRemote)
			{
				for (int i1 = 0; i1 < listp.size(); i1++)
				{
					Entity entity = (Entity)listp.get(i1);
					if ((entity != null) && ((entity instanceof EntityPlayer)))
					{
						playLivingSound();
						((EntityPlayer)entity).addChatMessage(new ChatComponentText(getCommandSenderName() + ": Now to return to where we left off, " + ((EntityPlayer)entity).getCommandSenderName() + "."));
					}
				}
			}
		}

		super.setDead();
	}

	public void onUpdate()
	{
		super.onUpdate();
		if (!worldObj.isRemote)
		ChunkLoadingEvent.updateLoaded(this);
		switch (getSpiritType())
		{
			case 1:
			setSize(8.0F, 8.0F);
			break;
			case 2:
			setSize(8.0F, 8.0F);
			break;
			case 3:
			setSize(8.0F, 8.0F);
			break;
			case 4:
			setSize(12.0F, 12.0F);
			break;
			case 6:
			setSize(12.0F, 12.0F);
			break;
			case 7:
			setSize(12.0F, 12.0F);
			break;
			case 8:
			setSize(12.0F, 12.0F);
			break;
			case 9:
			setSize(12.0F, 12.0F);
			break;
			case 5:setSize(16.0F, 16.0F);
			break;
			case 10:setSize(16.0F, 16.0F);
			break;
			case 11:
			setSize(16.0F, 16.0F);
			break;
			case 12:
			setSize(20.0F, 20.0F);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	public void onLivingUpdate()
	{
		if (getTonnage() >= getMaxTonnage())
		setDead();
		if (getTonnage() < 0F)
		setTonnage(0F);
		else
		setTonnage(getTonnage() - 1F);
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(32D, 32D, 32D));
		if ((list != null) && (!list.isEmpty()))
		{
			for (int i = 0; i < list.size(); i++)
			{
				Entity entity = (Entity)list.get(i);
				if (entity != null && entity.isEntityAlive() && entity instanceof EntityLivingBase && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit) && !(entity instanceof EntityTitanPart))
				{
					if (ticksExisted % 40 == 0)
					entity.attackEntityFrom(DamageSourceExtra.causeSoulStealingDamage(this), 2F);
					double speed = entity.isSneaking() ? 0.2D : 0.4D;
					double mx = posX - entity.posX;
					double my = (posY + 4D) - (entity.posY + 1D);
					double mz = posZ - entity.posZ;
					float f2 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
					entity.motionX = (mx / f2 * speed * speed + entity.motionX);
					entity.motionY = (my / f2 * speed * speed + entity.motionY);
					entity.motionZ = (mz / f2 * speed * speed + entity.motionZ);
					if (entity instanceof EntityLiving)
					((EntityLiving)entity).setAttackTarget(this);
					short short1 = (short)(int)getDistanceToEntity(entity);
					for (int f = 0; f < short1; f++)
					{
						double d9 = f / (short1 - 1.0D);
						double d6 = posX + mx * -d9;
						double d7 = (posY + 4D) + my * -d9;
						double d8 = posZ + mz * -d9;
						worldObj.spawnParticle("fireworksSpark", d6, d7, d8, entity.motionX, entity.motionY, entity.motionZ);
					}
				}

				if (entity != null && entity instanceof EntityItem)
				{
					entity.hurtResistantTime = 0;
					double mx = posX - entity.posX;
					double my = (posY + (height * 0.5D)) - entity.posY;
					double mz = posZ - entity.posZ;
					float f2 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
					entity.motionX = (mx / f2 * 0.3D * 0.3D + entity.motionX);
					entity.motionY = (my / f2 * 0.3D * 0.3D + entity.motionY);
					entity.motionZ = (mz / f2 * 0.3D * 0.3D + entity.motionZ);
				}

				if (entity != null && entity instanceof EntityTNTPrimed)
				{
					entity.hurtResistantTime = 0;
					double mx = posX - entity.posX;
					double my = (posY + (height * 0.5D)) - entity.posY;
					double mz = posZ - entity.posZ;
					float f2 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
					entity.motionX = (mx / f2 * 0.3D * 0.3D + entity.motionX);
					entity.motionY = (my / f2 * 0.3D * 0.3D + entity.motionY);
					entity.motionZ = (mz / f2 * 0.3D * 0.3D + entity.motionZ);
				}

				if (entity != null && entity instanceof EntityFallingBlock)
				{
					entity.hurtResistantTime = 0;
					float f6 = (entity.ticksExisted + entity.getEntityId()) * (float)Math.PI * -0.5F;
					double mx = (posX + (MathHelper.cos(f6) * 16F)) - entity.posX;
					double my = (posY + (height * 0.5D)) - entity.posY;
					double mz = (posZ + (MathHelper.sin(f6) * 16F)) - entity.posZ;
					float f2 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
					entity.motionX = (mx / f2 * 0.4D * 0.4D + entity.motionX);
					entity.motionY = (my / f2 * 0.4D * 0.4D + entity.motionY);
					entity.motionZ = (mz / f2 * 0.4D * 0.4D + entity.motionZ);
					ArrayList<?> arraylist = new ArrayList<Object>(worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox));
					boolean flag = ((EntityFallingBlock)entity).func_145805_f() == Blocks.anvil;
					DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;
					Iterator<?> iterator = arraylist.iterator();
					while (iterator.hasNext())
					{
						Entity entity1 = (Entity)iterator.next();
						entity1.attackEntityFrom(damagesource, 20F);
					}
				}

				if (entity != null && entity instanceof EntityFireball)
				{
					entity.hurtResistantTime = 0;
					double mx = posX - entity.posX;
					double my = (posY + (height * 0.5D)) - entity.posY;
					double mz = posZ - entity.posZ;
					float f2 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
					entity.motionX = (mx / f2 * 0.3D * 0.3D + entity.motionX);
					entity.motionY = (my / f2 * 0.3D * 0.3D + entity.motionY);
					entity.motionZ = (mz / f2 * 0.3D * 0.3D + entity.motionZ);
				}
			}
		}

		if (getSpiritNameID() <= 0)
		setSpiritNameID(1);
		super.onLivingUpdate();
		setHealth(Float.MAX_VALUE);
		deathTime = 0;
		for (int i = 0; i < width + height + 20; i++)
		{
			float f = (rand.nextFloat() - 0.5F) * width;
			float f1 = (rand.nextFloat() - 0.5F) * height;
			float f2 = (rand.nextFloat() - 0.5F) * width;
			worldObj.spawnParticle("largeexplode", posX + f, posY + 4.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("explode", posX + f, posY + 2.0D + f1, posZ + f2, motionX, motionY, motionZ);
			worldObj.spawnParticle("fireworksSpark", posX + f, posY + 4.0D + f1, posZ + f2, motionX, motionY, motionZ);
		}

		List<?> theBoundingBox = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
		if ((theBoundingBox != null) && (!theBoundingBox.isEmpty()))
		{
			for (int i1 = 0; i1 < theBoundingBox.size(); i1++)
			{
				Entity entity = (Entity)theBoundingBox.get(i1);
				if (entity != null)
				{
					if ((getSpiritType() == 1) && ((entity instanceof EntitySilverfishMinion)) && ((EntitySilverfishMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntitySilverfishMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 2) && ((entity instanceof EntityCaveSpiderMinion)) && ((EntityCaveSpiderMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntityCaveSpiderMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 3) && ((entity instanceof EntitySpiderMinion)) && ((EntitySpiderMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntitySpiderMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 4) && ((entity instanceof EntitySkeletonMinion)) && (((EntitySkeletonMinion)entity).getSkeletonType() != 1) && ((EntitySkeletonMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntitySkeletonMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 5) && ((entity instanceof EntitySkeletonMinion)) && (((EntitySkeletonMinion)entity).getSkeletonType() == 1) && ((EntitySkeletonMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntitySkeletonMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 6) && ((entity instanceof EntityZombieMinion)) && ((EntityZombieMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntityZombieMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 7) && ((entity instanceof EntityCreeperMinion)) && ((EntityCreeperMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntityCreeperMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 8) && ((entity instanceof EntityPigZombieMinion)) && ((EntityPigZombieMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntityPigZombieMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 9) && ((entity instanceof EntityBlazeMinion)) && ((EntityBlazeMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntityBlazeMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 10) && ((entity instanceof EntityEndermanMinion)) && ((EntityEndermanMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntityEndermanMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 11) && ((entity instanceof EntityGhastMinion)) && ((EntityGhastMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
					{
						prevEntity = (EntityGhastMinion) entity;
						setDead();
					}

					if ((getSpiritType() == 12) && ((entity instanceof EntityPlayer)))
					{
						prevEntity = (EntityPlayer) entity;
						setDead();
					}

					if ((entity instanceof EntityLivingBase))
					{
						((EntityLivingBase)entity).attackEntityFrom(DamageSourceExtra.causeSoulStealingDamage(this), 100F);
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.id, 300, 3));
					}

					if (((entity instanceof EntityItem)) && (((EntityItem)entity).delayBeforeCanPickup < 0) && (((EntityItem)entity).getEntityItem().getItem() != Items.nether_star))
					{
						((EntityItem)entity).attackEntityFrom(DamageSourceExtra.causeSoulStealingDamage(this), 100F);
					}

					if ((entity instanceof EntityEnderCrystal))
					{
						((EntityEnderCrystal)entity).attackEntityFrom(DamageSourceExtra.causeSoulStealingDamage(this), 100F);
					}
				}
			}
		}
	}

	protected void updateEntityActionState()
	{
		super.updateEntityActionState();
		if (waypointY <= 0D)
		waypointY = 0D;
		if (waypointY > 255D)
		waypointY = 255D;
		if (isVesselHunting())
		{
			EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 256D);
			if (getAttackTarget() != null)
			{
				waypointX = getAttackTarget().posX;
				waypointY = getAttackTarget().posY;
				waypointZ = getAttackTarget().posZ;
				if (!getAttackTarget().isEntityAlive())
				setAttackTarget(null);
			}

			else if (ticksExisted % 40 == 0 && player != null && posX != waypointX && posY != waypointZ)
			{
				waypointX = (player.posX + (rand.nextFloat() * 2.0F - 1.0F) * 64.0F);
				waypointY = (player.posY + 48D + (rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
				waypointZ = (player.posZ + (rand.nextFloat() * 2.0F - 1.0F) * 64.0F);
			}

			else if (player == null && ticksExisted % 40 == 0 && rand.nextInt(5) == 0)
			{
				waypointX = (posX + (rand.nextFloat() * 2.0F - 1.0F) * 64.0F);
				waypointY = (posY + (rand.nextFloat() * 2.0F - 1.0F) * 64.0F);
				waypointZ = (posZ + (rand.nextFloat() * 2.0F - 1.0F) * 64.0F);
			}
		}

		double d0 = waypointX - posX;
		double d1 = waypointY - posY;
		double d2 = waypointZ - posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		d3 = MathHelper.sqrt_double(d3);
		if (isVesselHunting() && getDistanceSq(waypointX, waypointY, waypointZ) > 40000D)
		{
			waypointX = posX;
			waypointY = posY;
			waypointZ = posZ;
		}

		if (getDistanceSq(waypointX, waypointY, waypointZ) > 4D)
		{
			motionX += d0 / d3 * 0.15D;
			motionY += d1 / d3 * 0.15D;
			motionZ += d2 / d3 * 0.15D;
		}

		if (getSpiritType() == 12)
		{
			if (worldObj.provider instanceof WorldProviderVoid)
			setVesselHunting(true);
			if (!isVesselHunting())
			{
				waypointX = posX;
				waypointY = 200D;
				waypointZ = posZ;
			}
		}

		else if (!isVesselHunting())
		{
			waypointX = (posX + (rand.nextFloat() * 2.0F - 1.0F) * 128.0F);
			waypointY = 255.0D;
			waypointZ = (posZ + (rand.nextFloat() * 2.0F - 1.0F) * 128.0F);
			if (posY >= 254.0D)
			{
				setVesselHunting(true);
				if (!worldObj.isRemote)
				setPosition(posX + (rand.nextFloat() * 2.0F - 1.0F) * 1024.0F, 250.0D, posZ + (rand.nextFloat() * 2.0F - 1.0F) * 1024.0F);
			}
		}

		else 
		{

			List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(256.0D, 256.0D, 256.0D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if (entity != null)
					{
						if ((getSpiritType() == 1) && ((entity instanceof EntitySilverfishMinion)) && ((EntitySilverfishMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 2) && ((entity instanceof EntityCaveSpiderMinion)) && ((EntityCaveSpiderMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 3) && ((entity instanceof EntitySpiderMinion)) && ((EntitySpiderMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 4) && ((entity instanceof EntitySkeletonMinion)) && (((EntitySkeletonMinion)entity).getSkeletonType() != 1) && ((EntitySkeletonMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 5) && ((entity instanceof EntitySkeletonMinion)) && (((EntitySkeletonMinion)entity).getSkeletonType() == 1) && ((EntitySkeletonMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 6) && ((entity instanceof EntityZombieMinion)) && ((EntityZombieMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 7) && ((entity instanceof EntityCreeperMinion)) && ((EntityCreeperMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 8) && ((entity instanceof EntityPigZombieMinion)) && ((EntityPigZombieMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 9) && ((entity instanceof EntityBlazeMinion)) && ((EntityBlazeMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 10) && ((entity instanceof EntityEndermanMinion)) && ((EntityEndermanMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 11) && ((entity instanceof EntityGhastMinion)) && ((EntityGhastMinion)entity).getMinionType() == EnumMinionType.TEMPLAR)
						{
							setAttackTarget((EntityLiving) entity);
							((EntityLiving)entity).setAttackTarget(this);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}

						if ((getSpiritType() == 12) && ((entity instanceof EntityPlayer)))
						{
							setAttackTarget((EntityLiving) entity);
							waypointX = entity.posX;
							waypointY = entity.posY;
							waypointZ = entity.posZ;
						}
					}
				}
			}
		}
	}

	protected void despawnEntity() 
	{
		 
	}


	protected void collideWithNearbyEntities()
	{
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1D, 1D, 1D));
		if ((list != null) && (!list.isEmpty()))
		{
			for (int i = 0; i < list.size(); i++)
			{
				Entity entity = (Entity)list.get(i);
				if (entity != null && entity instanceof EntityLivingBase && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit) && !(entity instanceof EntityTitanPart))
				{
					entity.hurtResistantTime = 0;
					double mx = posX - entity.posX;
					double my = (posY + 4D) - entity.posY;
					double mz = posZ - entity.posZ;
					float f2 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
					entity.motionX = (mx / f2 * 0.9D * 0.9D + entity.motionX);
					entity.motionY = (my / f2 * 0.9D * 0.9D + entity.motionY);
					entity.motionZ = (mz / f2 * 0.9D * 0.9D + entity.motionZ);
					((EntityLivingBase)entity).setHealth(0F);
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.id, 100, 3));
					((EntityLivingBase)entity).copyLocationAndAnglesFrom(this);
					if (((EntityLivingBase)entity).deathTime > 0)
					{
						worldObj.spawnParticle("hugeexplosion", posX, posY, posZ, 1.0D, 0.0D, 0.0D);
						playSound("random.explode", 4.0F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
						playSound("thetitans:titanCreeperExplosion", 10F, getSoundPitch() + 0.5F);
						playSound("mob.endermen.portal", 10F, getSoundPitch() + 0.75F);
						((EntityLivingBase)entity).setDead();
						setTonnage(getTonnage() + ((EntityLivingBase)entity).getMaxHealth());
					}
				}

				if (isVesselHunting() && entity != null && entity instanceof EntityItem)
				{
					entity.hurtResistantTime = 0;
					double mx = posX - entity.posX;
					double my = (posY + 4D) - entity.posY;
					double mz = posZ - entity.posZ;
					float f2 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
					entity.motionX = (mx / f2 * 0.9D * 0.9D + entity.motionX);
					entity.motionY = (my / f2 * 0.9D * 0.9D + entity.motionY);
					entity.motionZ = (mz / f2 * 0.9D * 0.9D + entity.motionZ);
					entity.attackEntityFrom(DamageSourceExtra.causeSoulStealingDamage(this), 100F);
				}
			}
		}
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public boolean canBePushed()
	{
		return false;
	}

	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}

	protected String getLivingSound()
	{
		switch (getSpiritType())
		{
			case 1:
			return "thetitans:titanSilverfishLiving";
			case 2:
			return "thetitans:titanSpiderLiving";
			case 3:
			return "thetitans:titanSpiderLiving";
			case 4:
			return "thetitans:titanSkeletonLiving";
			case 5:
			return "thetitans:titanWitherSkeletonLiving";
			case 6:
			return "thetitans:titanZombieLiving";
			case 7:
			return "thetitans:titanCreeperLiving";
			case 8:
			return "thetitans:titanPigZombieLiving";
			case 9:
			return "thetitans:titanBlazeBreathe";
			case 10:
			return "thetitans:titanEnderColossusRoar";
			case 11:
			return "thetitans:titanGhastLiving";
			case 12:
			return "thetitans:witherzillaLiving";
			default:
			return "mob.wither/idle";
		}
	}

	protected String getHurtSound()
	{
		return "";
	}

	protected String getDeathSound()
	{
		return "";
	}

	protected float getSoundVolume()
	{
		return 6.0F;
	}

	protected void damageEntity(DamageSource p_70665_1_, float p_70665_2_) 
	{
		 
	}


	public boolean isEntityInvulnerable()
	{
		return true;
	}

	protected void onDeathUpdate() 
	{
		 
	}


	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (source.getEntity() != null && source.getEntity() instanceof EntityLiving)
		setAttackTarget((EntityLiving) source.getEntity());
		return false;
	}
}


