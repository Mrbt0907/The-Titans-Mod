package net.minecraft.entity.titan;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.ai.EntityAICreeperTitanSwell;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titan.ai.EntityAITitanLookIdle;
import net.minecraft.entity.titan.ai.EntityAITitanWander;
import net.minecraft.entity.titan.ai.EntityAITitanWatchClosest;
import net.minecraft.entity.titan.animation.creepertitan.*;
import net.minecraft.entity.titanminion.EntityCreeperMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeHooks;
import thehippomaster.AnimationAPI.AnimationAPI;
import thehippomaster.AnimationAPI.IAnimatedEntity;
public class EntityCreeperTitan
extends EntityTitan
implements IAnimatedEntity, ITitanHitbox
{
	public int damageToLegs;
	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 200;
	public EntityTitanPart head;
	public EntityTitanPart body;
	public EntityTitanPart leg1;
	public EntityTitanPart leg2;
	public EntityTitanPart leg3;
	public EntityTitanPart leg4;
	public EntityCreeperTitan(World worldIn)
	{
		super(worldIn);
		head = new EntityTitanPart(worldIn, this, "head", 8.0F, 8.0F);
		body = new EntityTitanPart(worldIn, this, "body", 7.0F, 12.0F);
		leg1 = new EntityTitanPart(worldIn, this, "leg1", 4.5F, 8.0F);
		leg2 = new EntityTitanPart(worldIn, this, "leg2", 4.5F, 8.0F);
		leg3 = new EntityTitanPart(worldIn, this, "leg3", 4.5F, 8.0F);
		leg4 = new EntityTitanPart(worldIn, this, "leg4", 4.5F, 8.0F);
		partArray.add(head);
		partArray.add(body);
		partArray.add(leg1);
		partArray.add(leg2);
		partArray.add(leg3);
		partArray.add(leg4);
		setSize(8.0F, 26.0F);
		experienceValue = 50000;
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		worldIn.spawnEntityInWorld(head);
		worldIn.spawnEntityInWorld(body);
		worldIn.spawnEntityInWorld(leg1);
		worldIn.spawnEntityInWorld(leg2);
		worldIn.spawnEntityInWorld(leg3);
		worldIn.spawnEntityInWorld(leg4);
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte)-1));
		dataWatcher.addObject(17, Byte.valueOf((byte)0));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		if (dataWatcher.getWatchableObjectByte(17) == 1)
		{
			tagCompound.setBoolean("Charged", true);
		}

		tagCompound.setInteger("DamageToLegs", damageToLegs);
		tagCompound.setShort("Fuse", (short)fuseTime);
		tagCompound.setBoolean("Stunned", isStunned);
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		dataWatcher.updateObject(17, Byte.valueOf((byte)(tagCompund.getBoolean("Charged") ? 1 : 0)));
		if (tagCompund.hasKey("DamageToLegs", 99))
		{
			damageToLegs = tagCompund.getInteger("DamageToLegs");
		}

		isStunned = tagCompund.getBoolean("Stunned");
		if (tagCompund.hasKey("Fuse", 99))
		{
			fuseTime = tagCompund.getShort("Fuse");
		}
	}

	protected void applyEntityAI()
	{
		tasks.addTask(0, new EntityAICreeperTitanSwell(this));
		tasks.addTask(1, new AnimationCreeperTitanCreation(this));
		tasks.addTask(1, new AnimationCreeperTitanDeath(this));
		tasks.addTask(1, new AnimationCreeperTitanStunned(this));
		tasks.addTask(1, new AnimationCreeperTitanThunderClap(this));
		tasks.addTask(1, new AnimationCreeperTitanSpit(this));
		tasks.addTask(1, new AnimationCreeperTitanAttack4(this));
		tasks.addTask(1, new AnimationCreeperTitanAntiTitanAttack(this));
		tasks.addTask(1, new AnimationCreeperTitanAttack2(this));
		tasks.addTask(1, new AnimationCreeperTitanAttack1(this));
		tasks.addTask(1, new AnimationCreeperTitanAttack3(this));
		tasks.addTask(6, new EntityAITitanWander(this, 300));
		tasks.addTask(7, new EntityAITitanWatchClosest(this, EntityTitanSpirit.class, 128.0F));
		tasks.addTask(8, new EntityAITitanWatchClosest(this, EntityTitan.class, 128.0F));
		tasks.addTask(9, new EntityAITitanWatchClosest(this, EntityPlayer.class, 64.0F));
		tasks.addTask(10, new EntityAITitanLookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.CreeperTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 20.0F;
	}

	public void onLivingUpdate()
	{
		meleeTitan = true;
		if (motionY > 3.0D)motionY *= 0.8D;
		if (!isRiding() && !getWaiting() && !isStunned && getAnimID() == 0)
		{
			titanFly(0.625F * getTitanSizeMultiplier(), 0.125F * getTitanSizeMultiplier(), 0.25D * getTitanSizeMultiplier());
		}

		if (getWaiting())
		{
			AnimationAPI.sendAnimPacket(this, 13);
			EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 24D);
			if (player != null)
			{
				setWaiting(false);
				AnimationAPI.sendAnimPacket(this, 13);
				player.triggerAchievement(TitansAchievments.locateTitan);
			}
		}

		else
		{
			if (getAnimID() == 13)
			{
				motionX = 0D;
				motionZ = 0D;
				if (motionY > 0D)
				motionY = 0D;
			}

			if (getAnimID() == 13 && getAnimTick() == 1)
			playSound("thetitans:titanBirth", 1000.0F, 1.0F);
			if (getAnimID() == 13 && getAnimTick() == 10)
			playSound("thetitans:titanCreeperAwaken", getSoundVolume(), 1F);
			if (getAnimID() == 13 && getAnimTick() == 130)
			playSound("thetitans:titanRumble", getSoundVolume(), 1F);
			if (getAnimID() == 13 && getAnimTick() == 160)
			playSound("thetitans:titanCreeperBeginMove", getSoundVolume(), 1F);
			if (getAnimID() == 13 && (getAnimTick() == 260 || getAnimTick() == 261 || getAnimTick() == 390 || getAnimTick() == 410))
			{
				func_145780_a(0, 0, 0, Blocks.stone);
				playSound("thetitans:titanPress", getSoundVolume(), 1F);
			}

			if (getAnimID() != 13 && (getPowered()) && (!isStunned))
			{
				WorldServer worldserver = net.minecraft.server.MinecraftServer.getServer().worldServers[0];
				WorldInfo worldinfo = worldserver.getWorldInfo();
				worldinfo.setRainTime(9999999);
				worldinfo.setThunderTime(1000000);
				worldinfo.setRaining(true);
				worldinfo.setThundering(true);
				if (rand.nextInt(20) == 0)
				{
					EntityGammaLightning entitylightning = new EntityGammaLightning(worldObj, posX, head.posY + 4D, posZ, 0F, 0.5F, 1.0F);
					worldObj.addWeatherEffect(entitylightning);
				}

				if (rand.nextInt(40) == 0)
				{
					for (int l = 0; l < 50; l++)
					{
						int i = MathHelper.floor_double(posX);
						int j = MathHelper.floor_double(posY);
						int k = MathHelper.floor_double(posZ);
						int i1 = i + MathHelper.getRandomIntegerInRange(rand, 10, 100) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
						int j1 = j + MathHelper.getRandomIntegerInRange(rand, 10, 100) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
						int k1 = k + MathHelper.getRandomIntegerInRange(rand, 10, 100) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
						EntityGammaLightning entitylightning = new EntityGammaLightning(worldObj, i1, j1, k1, 0F, 0.5F, 1.0F);
						if ((World.doesBlockHaveSolidTopSurface(worldObj, i1, j1 - 1, k1)) && (worldObj.checkNoEntityCollision(entitylightning.boundingBox)) && (worldObj.getCollidingBoundingBoxes(entitylightning, entitylightning.boundingBox).isEmpty()) && (!worldObj.isAnyLiquid(entitylightning.boundingBox)))
						{
							worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, i1, j1, k1, 0F, 0.5F, 1.0F));
						}
					}
				}
			}
		}

		if (isStunned)
		{
			setAttackTarget(null);
			AnimationAPI.sendAnimPacket(this, 8);
		}

		if (getAnimID() == 10)
		{
			if ((getAnimTick() == 50) || (getAnimTick() == 70) || (getAnimTick() == 100))
			{
				func_145780_a(0, 0, 0, Blocks.stone);
			}

			if (getAnimTick() == 120)
			{
				shakeNearbyPlayerCameras(10D);
				playSound("thetitans:titanFall", 20.0F, 1.0F);
				playSound("thetitans:groundSmash", 20.0F, 1.0F);
			}

			if (getAnimTick() == 160)
			{
				shakeNearbyPlayerCameras(10D);
				playSound("thetitans:titanFall", 20.0F, 1.0F);
			}
		}

		if (getAnimID() == 8)
		{
			if (getAnimTick() == 120)
			{
				shakeNearbyPlayerCameras(10D);
				playSound("thetitans:groundSmash", 8.0F, 0.9F);
				playSound("thetitans:titanFall", 10.0F, 1.0F);
			}

			if (getAnimTick() == 20)
			{
				playSound("thetitans:titanCreeperStun", getSoundVolume(), getSoundPitch());
			}

			if (getAnimTick() > 500)
			isStunned = false;
			else
			isStunned = true;
			setAttackTarget(null);
		}

		if (isStunned || deathTicks > 0)
		{
			motionX *= 0.0D;
			motionZ *= 0.0D;
		}

		if ((!AnimationAPI.isEffectiveClient()) && (getAttackTarget() != null) && (!isStunned) && (getAnimID() == 0))
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 < getMeleeRange())
			{
				switch (rand.nextInt(4))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 3);
					setAnimID(3);
					break;
					case 1:AnimationAPI.sendAnimPacket(this, 2);
					setAnimID(2);
					break;
					case 2:AnimationAPI.sendAnimPacket(this, 5);
					setAnimID(5);
					break;
					case 3:AnimationAPI.sendAnimPacket(this, 4);
					setAnimID(4);
					break;
					case 4:if (getAttackTarget().height >= height - 12F || (posY + getEyeHeight() < getAttackTarget().posY + getAttackTarget().getEyeHeight()))
					{
						AnimationAPI.sendAnimPacket(this, 1);
						setAnimID(1);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 3);
						setAnimID(3);
					}

					break;
				}
			}

			else if (getAnimID() == 0 && (getRNG().nextInt(100) == 0))
			{
				switch (rand.nextInt(3))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 7);
					setAnimID(7);
					break;
					default:AnimationAPI.sendAnimPacket(this, 6);
					setAnimID(6);
				}
			}
		}

		if (getAnimID() == 6)
		{
			if ((getAnimTick() <= 50) && (getAnimTick() >= 20) && (getAttackTarget() != null))
			{
				int it = getPowered() ? (5 + rand.nextInt(5)) : (2 + rand.nextInt(2));
				for (int i = 0; i < it; i++)
				{
					switch (rand.nextInt(3))
					{
						case 0:Vec3 vec3 = getLook(1F);
						double d5 = getAttackTarget().posX + ((getRNG().nextFloat() * 2.0F - 1.0F) * 4.0F) - head.posX + vec3.xCoord * (0.5D * getTitanSizeMultiplier());
						double d6 = getAttackTarget().posY - 16 + ((getRNG().nextFloat() * 2.0F - 1.0F) * 4.0F) - head.posY;
						double d7 = getAttackTarget().posZ + ((getRNG().nextFloat() * 2.0F - 1.0F) * 4.0F) - head.posZ + vec3.zCoord * (0.5D * getTitanSizeMultiplier());
						EntityTitanFireball entitylargefireball = new EntityTitanFireball(worldObj, this, d5, d6, d7, 1);
						entitylargefireball.posX = (head.posX + vec3.xCoord * (0.5D * getTitanSizeMultiplier()));
						entitylargefireball.posY = (head.posY);
						entitylargefireball.posZ = (head.posZ + vec3.zCoord * (0.5D * getTitanSizeMultiplier()));
						worldObj.spawnEntityInWorld(entitylargefireball);
						entitylargefireball.setFireballID(1);
						playSound("thetitans:titanGhastFireball", 100F, 1.25F);
						break;
						case 1:if (!worldObj.isRemote)
						{
							EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(worldObj, getAttackTarget().posX + 0.5D + (getRNG().nextFloat() * 2.0F - 1.0F) * 32.0F, getAttackTarget().posY + 32.0D + (getRNG().nextFloat() * 2.0F - 1.0F) * 32.0F, getAttackTarget().posZ + 0.5D + (getRNG().nextFloat() * 2.0F - 1.0F) * 32.0F, this);
							worldObj.spawnEntityInWorld(entitytntprimed);
							playSound("game.tnt.primed", 1.0F, 1.0F);
							entitytntprimed.fuse = (100 + rand.nextInt(60));
						}

						break;
					}
				}
			}
		}

		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(100.0D, 100.0D, 100.0D));
		if ((list != null) && (!list.isEmpty()) && (ticksExisted % 20 == 0))
		{
			for (int i1 = 0; i1 < list.size(); i1++)
			{
				Entity entity = (Entity)list.get(i1);
				if ((entity != null) && (getPowered()) && ((entity instanceof EntityCreeperMinion)))
				{
					if (!((EntityCreeperMinion)entity).getPowered())
					{
						worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, entity.posX, entity.posY + entity.height, entity.posZ));
					}

					else
					{
						((EntityCreeperMinion)entity).heal(5.0F);
					}
				}
			}
		}

		if ((getAttackTarget() != null) && ((getAttackTarget() instanceof EntityPlayer)) && (((EntityPlayer)getAttackTarget()).getCommandSenderName() == "Boom337317"))
		{
			setAttackTarget(null);
		}

		if (getPowered())
		{
			switch (rand.nextInt(5))
			{
				case 0:setCustomNameTag("\u00A72");
				break;
				case 1:setCustomNameTag("\u00A74Charged Creeper Titan");
				break;
				case 2:setCustomNameTag("\u00A76Charged Creeper Titan");
				break;
				case 3:setCustomNameTag("\u00A7aCharged Creeper Titan");
				break;
				case 4:setCustomNameTag("\u00A7eCharged Creeper Titan");
			}
		}

		else
		setCustomNameTag(StatCollector.translateToLocal("entity.CreeperTitan.name"));
		if ((getAttackTarget() != null) && (onGround) && (!isStunned) && getAnimID() == 0) 
		{
			 int in;
			if (getPowered())
			in = 50;
			else in = 600;
			if (rand.nextInt(in) == 0)
			{
				if (rand.nextInt(4) == 0)
				{
					jump();
					double d01 = getAttackTarget().posX - posX;
					double d11 = getAttackTarget().posZ - posZ;
					float f21 = MathHelper.sqrt_double(d01 * d01 + d11 * d11);
					double hor = f21 / 16.0F;
					double ver = 2.0D;
					motionX = (d01 / f21 * hor * hor + motionX * hor);
					motionZ = (d11 / f21 * hor * hor + motionZ * hor);
					motionY = ver;
				}

				else
				jumpAtEntity(getAttackTarget());
			}
		}

		if (getAttackTarget() != null && !(getAttackTarget() instanceof EntityTitan) && (((rand.nextInt(30) == 0 && getPowered()) || (rand.nextInt(150) == 0 && !getPowered())) && (!isStunned)))
		{
			doLightningAttackToEntity(this);
			doLightningAttackToEntity(getAttackTarget());
		}

		super.onLivingUpdate();
	}

	@SuppressWarnings("unchecked")
	protected void onTitanDeathUpdate()
	{
		if (timeSinceIgnited >= fuseTime)
		{
			deathTicks = 200;
			setDead();
		}

		else
		{
			setHealth((float) getTitanHealth());
			if (getTitanHealth() <= 0D)
			{
				++deathTicks;
				AnimationAPI.sendAnimPacket(this, 10);
				setAnimID(10);
				setTitanHealth(0.0F);
			}

			else
			{
				attackEntityFrom(DamageSource.outOfWorld, 25F);
				setTitanHealth(getTitanHealth());
				setHealth(getTitanHealth());
				deathTicks = 0;
				if (getAnimID() == 10)
				setAnimID(0);
			}

			motionX *= 0.0D;
			motionZ *= 0.0D;
			setAttackTarget(null);
			if ((deathTicks == 1) && (!worldObj.isRemote))
			{
				playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
				ArrayList<?> listp = Lists.newArrayList(worldObj.playerEntities);
				if ((listp != null) && (!listp.isEmpty()))
				{
					for (int i1 = 0; i1 < listp.size(); i1++)
					{
						Entity entity = (Entity)listp.get(i1);
						if ((entity != null) && ((entity instanceof EntityPlayer)))
						{
							((EntityPlayer)entity).triggerAchievement(getAchievement());
						}
					}
				}
			}

			if (deathTicks == 1)
			{
				setAnimTick(1);
			}

			if (deathTicks < 300)
			timeSinceIgnited = 0;
			if (deathTicks >= 300)
			{
				if (timeSinceIgnited == 1)
				playSound("thetitans:titanCreeperWarning", Float.MAX_VALUE, 1.0F);
				++timeSinceIgnited;
				setCreeperState(1);
				float f = (rand.nextFloat() - 0.5F) * 16.0F;
				float f1 = (rand.nextFloat() - 0.5F) * 12.0F;
				float f2 = (rand.nextFloat() - 0.5F) * 16.0F;
				worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
			}

			if (timeSinceIgnited >= fuseTime)
			{
				deathTicks = 200;
				setDead();
			}
		}
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted > 5)
		{
			setSize(0.5F * getTitanSizeMultiplier(), 1.625F * getTitanSizeMultiplier());
			head.height = head.width = 0.5F * getTitanSizeMultiplier();
			body.height = 0.75F * getTitanSizeMultiplier();
			body.width = 0.5F * getTitanSizeMultiplier();
			leg1.height = leg2.height = leg3.height = leg4.height = 0.5F * getTitanSizeMultiplier();
			leg1.width = leg2.width = leg3.width = leg4.width = 0.28125F * getTitanSizeMultiplier();
			float f = renderYawOffset * 3.1415927F / 180.0F;
			float f1 = MathHelper.sin(f);
			float f2 = MathHelper.cos(f);
			float dis = (0.34375F * getTitanSizeMultiplier());
			float offset = ((getAnimID() == 3 && getAnimTick() > 30 && getAnimTick() < 70) || (getAnimID() == 7 && getAnimTick() > 30 && getAnimTick() < 130)) ? (0.375F * getTitanSizeMultiplier()) : 0F;
			head.setLocationAndAngles(posX, posY + (getAnimID() == 8 ? (0.75F * getTitanSizeMultiplier()) : (1.125F * getTitanSizeMultiplier())), posZ, 0.0F, 0.0F);
			body.setLocationAndAngles(posX, posY + (getAnimID() == 8 ? 0.0D : (0.375F * getTitanSizeMultiplier())), posZ, 0.0F, 0.0F);
			leg1.setLocationAndAngles(posX - (f1 * dis) + (f2 * dis), posY + offset, posZ + (f2 * dis) + (f1 * dis), 0.0F, 0.0F);
			leg2.setLocationAndAngles(posX - (f1 * dis) - (f2 * dis), posY + offset, posZ + (f2 * dis) - (f1 * dis), 0.0F, 0.0F);
			leg3.setLocationAndAngles(posX + (f1 * dis) + (f2 * dis), posY, posZ - (f2 * dis) + (f1 * dis), 0.0F, 0.0F);
			leg4.setLocationAndAngles(posX + (f1 * dis) - (f2 * dis), posY, posZ - (f2 * dis) - (f1 * dis), 0.0F, 0.0F);
		}
		
		super.onHitboxUpdate();
	}

	protected void onAnimationUpdate() {}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != head.getClass()) && (p_70686_1_ != body.getClass()) && (p_70686_1_ != leg1.getClass()) && (p_70686_1_ != leg2.getClass()) && (p_70686_1_ != leg3.getClass()) && (p_70686_1_ != leg4.getClass()) && (p_70686_1_ != EntityCreeperMinion.class) && (p_70686_1_ != EntityCreeperTitan.class);
	}

	public boolean isArmored()
	{
		return getPowered();
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(250) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (isValidLightLevel());
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.CreeperTitanMinionSpawnrate;
	}

	public int getParticleCount()
	{
		if (getPowered())
		{
			return 28;
		}

		return super.getParticleCount();
	}

	public boolean canBeHurtByPlayer()
	{
		return (isStunned) && (!isEntityInvulnerable());
	}

	public float getEyeHeight()
	{
		return 0.9076923076923077F * height;
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.AVERAGE;
	}

	protected void fall(float p_70069_1_)
	{
		onGround = true;
		isAirBorne = false;
		p_70069_1_ = ForgeHooks.onLivingFall(this, p_70069_1_);
		if (p_70069_1_ <= 0.0F) return;
		PotionEffect potioneffect = getActivePotionEffect(Potion.jump);
		float f1 = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0.0F;
		int i = MathHelper.ceiling_float_int(p_70069_1_ - 32F - f1);
		playSound("thetitans:titanSlam", 1F + i, 1.0F);
		if (i > 0)
		{
			shakeNearbyPlayerCameras(i);
			playSound("thetitans:groundSmash", 20.0F, 1.0F);
			playSound("thetitans:titanland", 10000.0F, 1.0F);
			playSound("thetitans:titanStrike", 10.0F, 1.0F);
			destroyBlocksInAABBTopless(boundingBox.expand(24D, 1D, 24D));
			collideWithEntities(body, worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(24D, 4D, 24D)));
			List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(48D, 4D, 48D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (((entity instanceof EntityLivingBase)) && canAttackClass(entity.getClass()) && (!(entity instanceof EntityTitan)))
					{
						float smash = 50F - getDistanceToEntity(entity);
						if (smash <= 1F)
						smash = 1F;
						entity.attackEntityFrom(DamageSource.causeMobDamage(this), smash);
						double d0 = boundingBox.minX + boundingBox.maxX;
						double d1 = boundingBox.minZ + boundingBox.maxZ;
						double d2 = entity.posX - d0;
						double d3 = entity.posZ - d1;
						double d4 = d2 * d2 + d3 * d3;
						entity.addVelocity(d2 / d4 * 8.0D, 1.0D, d3 / d4 * 8.0D);
						if ((!worldObj.isRemote) && (isEntityAlive()) && (!isStunned))
						{
							collideWithEntities(body, worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(16.0D, 1.0D, 16.0D)));
						}
					}
				}
			}
		}

		if (getCreeperState() > 0)
		{
			timeSinceIgnited += 20;
		}
	}

	public double getSpeed()
	{
		return (getPowered() ? 0.375D : 0.25D) / 6;
	}

	public void attackChoosenEntity(Entity damagedEntity, float damage, int knockbackAmount)
	{
		super.attackChoosenEntity(damagedEntity, damage, knockbackAmount);
		if ((getPowered()) && ((damagedEntity instanceof EntityLivingBase)) && isEntityAlive())
		{
			worldObj.playSoundEffect(posX, posY, posZ, "ambient.weather.thunder", 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
			worldObj.playSoundEffect(posX, posY, posZ, "ambient.weather.thunder", 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
			worldObj.newExplosion(this, damagedEntity.posX, damagedEntity.posY + damagedEntity.getEyeHeight(), damagedEntity.posZ, 6.0F, false, false);
			worldObj.newExplosion(this, posX, head.posY, posZ, 2.0F, false, false);
			damagedEntity.attackEntityFrom(DamageSourceExtra.lightningBolt, 49.0F);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX, posY + getEyeHeight(), posZ, 0F, 0.5F, 1.0F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, damagedEntity.posX, damagedEntity.posY, damagedEntity.posZ, 0F, 0.5F, 1.0F));
		}
	}

	public boolean shouldMove()
	{
		return !isStunned &&super.shouldMove();
	}

	public void doLightningAttackToEntity(Entity p_70652_1_)
	{
		if (getPowered())
		{
			worldObj.playSoundEffect(posX, posY, posZ, "ambient.weather.thunder", 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
			worldObj.playSoundEffect(posX, posY, posZ, "ambient.weather.thunder", 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
			worldObj.newExplosion(this, p_70652_1_.posX, p_70652_1_.posY + p_70652_1_.getEyeHeight(), p_70652_1_.posZ, 8.0F, false, false);
			worldObj.newExplosion(this, posX, head.posY + 4D, posZ, 4.0F, false, false);
			p_70652_1_.attackEntityFrom(DamageSourceExtra.lightningBolt, 49.0F);
			float f = (float)getAttackValue(5.0F);
			int i = getKnockbackAmount();
			if (p_70652_1_ != this)
			attackChoosenEntity(p_70652_1_, f, i);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX, head.posY + 4D, posZ, 0F, 0.5F, 1.0F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, p_70652_1_.posX, p_70652_1_.posY, p_70652_1_.posZ, 0F, 0.5F, 1.0F));
		}

		else if (!getPowered())
		{
			worldObj.newExplosion(this, p_70652_1_.posX, p_70652_1_.posY + p_70652_1_.getEyeHeight(), p_70652_1_.posZ, 4.0F, false, false);
			worldObj.newExplosion(this, posX, head.posY + 4D, posZ, 2.0F, false, false);
			p_70652_1_.attackEntityFrom(DamageSourceExtra.lightningBolt, 49.0F);
			float f = (float)getAttackValue(2.0F);
			int i = getKnockbackAmount();
			if (p_70652_1_ != this)
			attackChoosenEntity(p_70652_1_, f, i);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX, head.posY + 4D, posZ, 0F, 0.5F, 0.25F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, p_70652_1_.posX, p_70652_1_.posY, p_70652_1_.posZ, 0F, 0.5F, 0.25F));
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return false;
	}

	public double getMeleeRange()
	{
		return width + (1.25D * getTitanSizeMultiplier());
	}

	protected String getLivingSound()
	{
		return isStunned || getWaiting() || getAnimID() == 13 ? null : "thetitans:titanCreeperLiving";
	}

	protected String getHurtSound()
	{
		return "thetitans:titanCreeperGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:titanCreeperDeath";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		playSound("thetitans:titanStep", 10.0F, 1.1F);
		playSound("thetitans:titanStep", 10.0F, 1.1F);
		shakeNearbyPlayerCameras(4D);
		shakeNearbyPlayerCameras(4D);
		if (!getWaiting() && getAnimID() != 13)
		{
			if (footID == 0)
			{
				destroyBlocksInAABB(leg1.boundingBox.offset(0, -1, 0));
				destroyBlocksInAABB(leg4.boundingBox.offset(0, -1, 0));
				++footID;
			}

			else
			{
				destroyBlocksInAABB(leg2.boundingBox.offset(0, -1, 0));
				destroyBlocksInAABB(leg3.boundingBox.offset(0, -1, 0));
				footID = 0;
			}
		}
	}

	public StatBase getAchievement()
	{
		return TitansAchievments.creepertitan;
	}

	public void setPowered(boolean powered)
	{
		dataWatcher.updateObject(17, Byte.valueOf(powered ? (byte)1 : (byte)0));
	}

	public boolean getPowered()
	{
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}

	@SideOnly(Side.CLIENT)
	public float getCreeperFlashIntensity(float p_70831_1_)
	{
		return (lastActiveTime + (timeSinceIgnited - lastActiveTime) * p_70831_1_) / (fuseTime - 2);
	}

	protected Item getDropItem()
	{
		return Items.gunpowder;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < (getPowered() ? 60 : 16); x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
				entitylargefireball.setPosition(posX, posY + 4D, posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(16000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.gunpowder));
				itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.tnt));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.coal));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.emerald));
				itembomb.setItemCount(8 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.diamond));
				itembomb.setItemCount(8 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.iron_ingot));
				itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanItems.harcadium));
				itembomb.setItemCount(4 + rand.nextInt(4 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			if (rand.nextInt(10) == 0)
			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.bedrock));
				itembomb.setItemCount(1);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}
	}

	public int getCreeperState()
	{
		return dataWatcher.getWatchableObjectByte(16);
	}

	public void setCreeperState(int p_70829_1_)
	{
		dataWatcher.updateObject(16, Byte.valueOf((byte)p_70829_1_));
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		Object p_180482_2_1 = super.onSpawnWithEgg(p_180482_2_);
		setWaiting(true);
		if (rand.nextInt(100) == 0)
		{
			setPowered(true);
		}

		return (IEntityLivingData)p_180482_2_1;
	}

	private void explode()
	{
		for (int i = 0; i < 1000; i++)
		{
			float f = (rand.nextFloat() - 0.5F) * 16F;
			float f1 = (rand.nextFloat() - 0.5F) * 48F;
			float f2 = (rand.nextFloat() - 0.5F) * 16F;
			worldObj.spawnParticle("hugeexplosion", posX + f, posY + f1, posZ + f2, (rand.nextDouble() - 0.5D), 0.0D, (rand.nextDouble() - 0.5D));
		}

		playSound("thetitans:titanCreeperExplosion", Float.MAX_VALUE, 1.0F);
		playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
		worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
		double d1 = getPowered() ? (16D * getTitanSizeMultiplier()) : (8D * getTitanSizeMultiplier());
		double d2 = getPowered() ? (64D * getTitanSizeMultiplier()) : (32D * getTitanSizeMultiplier());
		destroyBlocksInAABBGriefingBypass(boundingBox.expand(d1, d1, d1));
		playSound("thetitans:titanland", 10000.0F, 1.0F);
		for (int da = 0; da <= 10; da++)
		{
			List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d1, d1, d1));
			if ((list != null) && (!list.isEmpty()))
			for (int i = 0; i < list.size(); i++)
			{
				Entity entity = (Entity)list.get(i);
				if (entity != null && (entity instanceof EntityLivingBase))
				{
					if (entity instanceof EntityZombieTitan)
					if (((EntityZombieTitan)entity).getTitanVariant() == 0)
					((EntityZombieTitan)entity).setTitanVariant(3);
					attackChoosenEntity(entity, (float)getAttackValue(50), 2);
					entity.attackEntityFrom(DamageSourceExtra.causeCreeperTitanExplosionDamage(this), (float)getAttackValue(1));
					entity.attackEntityFrom(new DamageSource("blindness").setDamageBypassesArmor().setDamageIsAbsolute(), (float)getAttackValue(1));
					if (entity instanceof EntityCreeperTitan)
					{
						((EntityCreeperTitan)entity).heal(getPowered() ? 1000F : 500F);
						((EntityCreeperTitan)entity).setPowered(true);
					}
				}
			}
		}

		List<?> list111 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d2, d2, d2));
		if ((list111 != null) && (!list111.isEmpty()))
		{
			for (int i = 0; i < list111.size(); i++)
			{
				Entity entity = (Entity)list111.get(i);
				if (worldObj.isRemote && (entity instanceof EntityLivingBase))
				{
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(ClientProxy.creeperTitanRadiation.id, 30000, 1));
					if (getPowered())
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, 30000, 3));
				}
			}
		}
	}

	public void func_175493_co()
	{
	}

	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (source.getEntity() instanceof EntityPlayer && !worldObj.isRemote)
		{
			setAttackTarget((EntityLivingBase) source.getEntity());
			setRevengeTarget((EntityLivingBase) source.getEntity());
		}

		if (isEntityInvulnerable())
		{
			return false;
		}

		else if (source.getDamageType() == "lightningBolt" || source.getDamageType() == "explosion" || ((source.getEntity() instanceof EntityCreeperMinion)) || ((source.getEntity() instanceof EntityCreeperTitan)) || ((source.isExplosion()) && (!(source.getEntity() instanceof EntityWitherTurret))))
		{
			heal(amount);
			return false;
		}

		else
		{
			recentlyHit = 200;
			Entity entity = source.getEntity();
			if (((entity instanceof EntityLivingBase)) && (!isEntityInvulnerable()) && (amount > 25.0F))
			{
				List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(256.0D, 256.0D, 256.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if ((entity1 instanceof EntityCreeperTitan))
					{
						EntityCreeperTitan entitypigzombie = (EntityCreeperTitan)entity1;
						entitypigzombie.setAttackTarget((EntityLivingBase)entity);
						entitypigzombie.setRevengeTarget((EntityLivingBase)entity);
					}

					setAttackTarget((EntityLivingBase)entity);
					setRevengeTarget((EntityLivingBase)entity);
				}
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	public boolean attackEntityFromPart(EntityTitanPart p_70965_1_, DamageSource source, float amount)
	{
		func_82195_e(source, amount);
		if ((source == DamageSourceExtra.lightningBolt) || ((source.getEntity() instanceof EntityCreeperMinion)) || ((source.getEntity() instanceof EntityCreeperTitan)) || ((source.isExplosion()) && (!(source.getEntity() instanceof EntityWitherTurret))))
		{
			heal(amount);
			return false;
		}

		if (source.getEntity() != null && source.getEntity() instanceof EntityPlayer && source.canHarmInCreative() && (damageToLegs < 32) && (!isStunned) && ((p_70965_1_ == leg1) || (p_70965_1_ == leg2) || (p_70965_1_ == leg3) || (p_70965_1_ == leg4)))
		{
			damageToLegs += 1;
			setAttackTarget((EntityLivingBase) source.getEntity());
			setRevengeTarget((EntityLivingBase) source.getEntity());
			if (damageToLegs >= 32)
			{
				playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
				isStunned = true;
				damageToLegs = 0;
			}
		}

		return true;
	}

	protected boolean func_82195_e(DamageSource p_82195_1_, float p_82195_2_)
	{
		return attackEntityFrom(p_82195_1_, p_82195_2_);
	}

	public World func_82194_d()
	{
		return worldObj;
	}

	protected void inactDeathAction()
	{
		if (!worldObj.isRemote)
		{
			explode();
			playSound("mob.creeper.death", getSoundVolume(), getSoundPitch());
			if ((worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
			{
				dropFewItems(true, 0);
				if (getPowered())
				dropFewItems(true, 0);
				dropEquipment(true, 0);
				dropRareDrop(1);
			}

			EntityTitanSpirit entitytitan = new EntityTitanSpirit(worldObj);
			entitytitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.spawnEntityInWorld(entitytitan);
			entitytitan.setVesselHunting(false);
			entitytitan.setSpiritType(7);
		}
	}

	protected EntityLiving getMinion()
	{
		return new EntityCreeperMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}

	protected double getRegen()
	{
		return getPowered() ? super.getRegen() * 2 : super.getRegen();
	}
}


