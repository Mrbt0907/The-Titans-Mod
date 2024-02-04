package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titan.ai.EntityAITitanLookIdle;
import net.minecraft.entity.titan.ai.EntityAITitanWander;
import net.minecraft.entity.titan.ai.EntityAITitanWatchClosest;
import net.minecraft.entity.titan.animation.endercolossus.*;
import net.minecraft.entity.titanminion.EntityDragonMinion;
import net.minecraft.entity.titanminion.EntityEndermanMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.theTitans.world.WorldProviderVoid;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.common.ForgeHooks;
import thehippomaster.AnimationAPI.AnimationAPI;
import thehippomaster.AnimationAPI.IAnimatedEntity;
public class EntityEnderColossus
extends EntityTitan
implements IAnimatedEntity, ITitanHitbox
{
	private int roarcooldownTimer;
	public boolean isRevealed;
	public EntityEnderColossusCrystal healingEnderCrystal;
	public int numOfCrystals;
	public int maxNumOfCrystals = 32;
	public EntityTitanPart head;
	public EntityTitanPart body;
	public EntityTitanPart rightEye;
	public EntityTitanPart leftEye;
	public EntityTitanPart rightArm;
	public EntityTitanPart leftArm;
	public EntityTitanPart rightLeg;
	public EntityTitanPart leftLeg;
	public boolean isStunned;
	public int destroyedCrystals;
	public EntityEnderColossus(World worldIn)
	{
		super(worldIn);
		head = new EntityTitanPart(worldIn, this, "head", 12.0F, 12.0F);
		body = new EntityTitanPart(worldIn, this, "body", 10.0F, 18.0F);
		rightEye = new EntityTitanPart(worldIn, this, "righteye", 3F, 2F);
		leftEye = new EntityTitanPart(worldIn, this, "lefteye", 3F, 2F);
		rightArm = new EntityTitanPart(worldIn, this, "rightarm", 4F, 4F);
		leftArm = new EntityTitanPart(worldIn, this, "leftarm", 4F, 4F);
		rightLeg = new EntityTitanPart(worldIn, this, "rightleg", 4F, 42F);
		leftLeg = new EntityTitanPart(worldIn, this, "leftleg", 4F, 42F);
		maxNumOfCrystals = 20;
		partArray.add(head);
		partArray.add(body);
		partArray.add(rightEye);
		partArray.add(leftEye);
		partArray.add(rightArm);
		partArray.add(leftArm);
		partArray.add(rightLeg);
		partArray.add(leftLeg);
		setSize(12.0F, 72.0F);
		experienceValue = 1000000;
		tasks.addTask(6, new EntityAITitanWander(this, 200));
		tasks.addTask(7, new EntityAITitanWatchClosest(this, EntityTitanSpirit.class, 128.0F));
		tasks.addTask(8, new EntityAITitanWatchClosest(this, EntityTitan.class, 128.0F));
		tasks.addTask(9, new EntityAITitanWatchClosest(this, EntityPlayer.class, 64.0F));
		tasks.addTask(10, new EntityAITitanLookIdle(this));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		worldIn.spawnEntityInWorld(head);
		worldIn.spawnEntityInWorld(body);
		worldIn.spawnEntityInWorld(rightArm);
		worldIn.spawnEntityInWorld(leftArm);
		worldIn.spawnEntityInWorld(rightLeg);
		worldIn.spawnEntityInWorld(leftLeg);
		worldIn.spawnEntityInWorld(rightEye);
		worldIn.spawnEntityInWorld(leftEye);
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted > 5)
		{
			setSize(0.5F * getTitanSizeMultiplier(), 3F * getTitanSizeMultiplier());
			float f = renderYawOffset * 3.1415927F / 180.0F;
			float f1 = MathHelper.sin(f);
			float f2 = MathHelper.cos(f);
			head.setLocationAndAngles(posX, posY + (0.8325D * height), posZ, 0.0F, 0.0F);
			body.setLocationAndAngles(posX, posY + (0.575D * height), posZ, 0.0F, 0.0F);
			rightEye.setLocationAndAngles(posX + (MathHelper.cos(rotationYawHead * 3.1415927F / 180.0F) * (0.425D * width)) - (MathHelper.sin(rotationYawHead * 3.1415927F / 180.0F) * (0.75D * width)), posY + getEyeHeight(), posZ + (MathHelper.sin(rotationYawHead * 3.1415927F / 180.0F) * (0.425D * width)) + (MathHelper.cos(rotationYawHead * 3.1415927F / 180.0F) * (0.75D * width)), 0.0F, 0.0F);
			leftEye.setLocationAndAngles(posX - (MathHelper.cos(rotationYawHead * 3.1415927F / 180.0F) * (0.425D * width)) - (MathHelper.sin(rotationYawHead * 3.1415927F / 180.0F) * (0.75D * width)), posY + getEyeHeight(), posZ - (MathHelper.sin(rotationYawHead * 3.1415927F / 180.0F) * (0.425D * width)) + (MathHelper.cos(rotationYawHead * 3.1415927F / 180.0F) * (0.75D * width)), 0.0F, 0.0F);
			rightArm.setLocationAndAngles(posX + f2 * (0.625D * width), posY + (0.7875D * height), posZ + f1 * (0.625D * width), 0.0F, 0.0F);
			leftArm.setLocationAndAngles(posX - f2 * (0.625D * width), posY + (0.7875D * height), posZ - f1 * (0.625D * width), 0.0F, 0.0F);
			rightLeg.setLocationAndAngles(posX + f2 * (0.25D * width), posY, posZ + f1 * (0.25D * width), 0.0F, 0.0F);
			leftLeg.setLocationAndAngles(posX - f2 * (0.25D * width), posY, posZ - f1 * (0.25D * width), 0.0F, 0.0F);
			head.height = (isScreaming() ? 0.925F * getTitanSizeMultiplier() : 0.5F * getTitanSizeMultiplier());
			head.width = 0.5F * getTitanSizeMultiplier();
			body.height = 0.75F * getTitanSizeMultiplier();
			body.width = 0.475F * getTitanSizeMultiplier();
			rightEye.height = leftEye.height = 0.1F * getTitanSizeMultiplier();
			rightEye.width = leftEye.width = 0.125F * getTitanSizeMultiplier();
			leftLeg.height = rightLeg.height = 1.75F * getTitanSizeMultiplier();
			leftLeg.width = rightLeg.width = 0.125F * getTitanSizeMultiplier();
			rightArm.width = leftArm.width = rightArm.height = leftArm.height = 0.125F * getTitanSizeMultiplier();
			rightEye.rotationYaw = rotationYawHead;
			leftEye.rotationYaw = rotationYawHead;
			rightEye.rotationPitch = rotationPitch;
			leftEye.rotationPitch = rotationPitch;
		}

		super.onHitboxUpdate();
	}

	protected void applyEntityAI()
	{
		tasks.addTask(1, new AnimationEnderColossusDeath(this));
		tasks.addTask(1, new AnimationEnderColossusStun(this));
		tasks.addTask(1, new AnimationEnderColossusScream(this));
		tasks.addTask(1, new AnimationEnderColossusDragonLightningBall(this));
		tasks.addTask(1, new AnimationEnderColossusChainLightning(this));
		tasks.addTask(1, new AnimationEnderColossusLightningBall(this));
		tasks.addTask(1, new AnimationEnderColossusLightningAttack(this));
		tasks.addTask(1, new AnimationEnderColossusAntiTitanAttack(this));
		tasks.addTask(1, new AnimationEnderColossusMeteorRain(this));
		tasks.addTask(1, new AnimationEnderColossusAttack2(this));
		tasks.addTask(1, new AnimationEnderColossusAttack3(this));
		tasks.addTask(1, new AnimationEnderColossusAttack1(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.EnderColossusSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 40.0F;
	}

	public int getEyeLaserTime()
	{
		return dataWatcher.getWatchableObjectInt(28);
	}

	public void setEyeLaserTime(int p_82215_1_)
	{
		dataWatcher.updateObject(28, Integer.valueOf(p_82215_1_));
	}

	public boolean alreadyHasAName()
	{
		return isRevealed;
	}

	public int getTalkInterval()
	{
		return 100;
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		isRevealed = tagCompund.getBoolean("Musmu");
		setEyeLaserTime(tagCompund.getInteger("ShootTimer"));
		setCanCallBackUp(tagCompund.getBoolean("CallHelp"));
		if (tagCompund.hasKey("DestroyedCrystals", 99))
		{
			destroyedCrystals = tagCompund.getInteger("DestroyedCrystals");
		}

		isStunned = tagCompund.getBoolean("Stunned");
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("ShootTimer", getEyeLaserTime());
		tagCompound.setBoolean("CallHelp", getCanCallBackUp());
		tagCompound.setBoolean("Musmu", isRevealed);
		tagCompound.setInteger("DestroyedCrystals", destroyedCrystals);
		tagCompound.setBoolean("Stunned", isStunned);
	}

	public double getMeleeRange()
	{
		return width + (1.25D * getTitanSizeMultiplier());
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return p_70686_1_ != head.getClass() && p_70686_1_ != body.getClass() && p_70686_1_ != rightEye.getClass() && p_70686_1_ != leftEye.getClass() && p_70686_1_ != rightArm.getClass() && p_70686_1_ != leftArm.getClass() && p_70686_1_ != rightLeg.getClass() && p_70686_1_ != leftLeg.getClass() && (p_70686_1_ != EntityEnderman.class) && (p_70686_1_ != EntityEndermanMinion.class) && (p_70686_1_ != EntityEnderColossus.class) && (p_70686_1_ != EntityDragon.class) && (p_70686_1_ != EntityDragonMinion.class) && (p_70686_1_ != EntityEnderColossusCrystal.class);
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(500) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (isValidLightLevel());
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.EnderColossusMinionSpawnrate;
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.GREATER;
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(15, new Byte((byte)0));
		dataWatcher.addObject(16, new Byte((byte)0));
		dataWatcher.addObject(28, new Integer(0));
	}

	public boolean getCanCallBackUp()
	{
		return dataWatcher.getWatchableObjectByte(16) == 1;
	}

	public void setCanCallBackUp(boolean p_82201_1_)
	{
		dataWatcher.updateObject(16, Byte.valueOf((byte) (p_82201_1_ ? 1 : 0)));
	}

	public int getParticleCount()
	{
		return 60;
	}

	public String getParticles()
	{
		return "portal";
	}

	protected void fall(float p_70069_1_)
	{
		onGround = true;
		isAirBorne = false;
		p_70069_1_ = ForgeHooks.onLivingFall(this, p_70069_1_);
		if (p_70069_1_ <= 0.0F) return;
		PotionEffect potioneffect = getActivePotionEffect(Potion.jump);
		float f1 = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0.0F;
		int i = MathHelper.ceiling_float_int(p_70069_1_ - 24F - f1);
		if (i > 0)
		{
			shakeNearbyPlayerCameras(50D);
			playSound("thetitans:groundSmash", 20.0F, 0.9F);
			playSound("thetitans:titanland", 10000.0F, 1.0F);
			destroyBlocksInAABBTopless(boundingBox.expand(24D, 1D, 24D));
			List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(48D, 4D, 48D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (((entity instanceof EntityLivingBase)) && (!(entity instanceof EntityDragonPart)) && canAttackClass(entity.getClass()) && (!(entity instanceof EntityTitan)))
					{
						float smash = 100F - getDistanceToEntity(entity);
						if (smash <= 1F)
						smash = 1F;
						entity.attackEntityFrom(DamageSource.causeMobDamage(this), smash * 4F);
						double d0 = boundingBox.minX + boundingBox.maxX;
						double d1 = boundingBox.minZ + boundingBox.maxZ;
						double d2 = entity.posX - d0;
						double d3 = entity.posZ - d1;
						double d4 = d2 * d2 + d3 * d3;
						entity.addVelocity(d2 / d4 * 8.0D, 1.0D, d3 / d4 * 8.0D);
					}
				}
			}
		}
	}

	protected void kill()
	{
		playSound("mob.endermen.portal", 100.0F, 0.6F);
		if (((worldObj.provider instanceof WorldProviderEnd)) || ((worldObj.provider instanceof WorldProviderVoid)))
		{
			setPosition(0.0D, 128.0D, 0.0D);
		}

		else if ((!(worldObj.provider instanceof WorldProviderEnd)) && (!(worldObj.provider instanceof WorldProviderVoid)))
		{
			setPosition(posX + (rand.nextDouble() - 0.5D) * 48.0D, 128.0D, posZ + (rand.nextDouble() - 0.5D) * 48.0D);
		}
	}

	protected int decreaseAirSupply(int p_70682_1_)
	{
		return p_70682_1_;
	}

	protected Entity findPlayerToLookAt()
	{
		EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, -1.0D);
		if (entityplayer != null && !worldObj.isRemote && entityplayer.isEntityAlive() && isEntityAlive())
		{
			if ((isPlayerRegistered(entityplayer, rightEye) || isPlayerRegistered(entityplayer, leftEye)) && entityplayer.isEntityAlive())
			{
				getLookHelper().setLookPositionWithEntity(entityplayer, 180F, 30F);
				setAttackTarget(entityplayer);
				if (entityplayer.getHeldItem() != null && entityplayer.getHeldItem().getItem() == TitanItems.ultimaBlade)
				{
					attackEntityFrom(DamageSourceExtra.causeArmorPiercingMobDamage(entityplayer), 600F);
				}

				else
				{
					attackChoosenEntity(entityplayer, 20F, 100);
					entityplayer.motionY = 1F;
					entityplayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 400, 1));
					entityplayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400, 99));
					entityplayer.addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 1));
					setAttackTarget(entityplayer);
					worldObj.newExplosion(this, entityplayer.posX, entityplayer.posY, entityplayer.posZ, 8F, true, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					playSound("thetitans:titanEnderColossusStare", Float.MAX_VALUE, 1F);
				}
			}
		}

		return null;
	}

	protected boolean isPlayerRegistered(EntityPlayer p_70821_1_, EntityTitanPart part)
	{
		Vec3 vec3 = p_70821_1_.getLook(1.0F).normalize();
		Vec3 vec31 = Vec3.createVectorHelper(part.posX + (rand.nextDouble() * part.width - (part.width * 0.5)) - p_70821_1_.posX, part.boundingBox.minY + (rand.nextDouble() * part.height) - (p_70821_1_.posY + p_70821_1_.getEyeHeight()), part.posZ + (rand.nextDouble() * part.width - (part.width * 0.5)) - p_70821_1_.posZ);
		double d0 = vec31.lengthVector();
		vec31 = vec31.normalize();
		double d1 = vec3.dotProduct(vec31);
		return d1 > 1.0D - 0.025D / d0 ? p_70821_1_.canEntityBeSeen(this) : false;
	}

	public boolean canBeHurtByPlayer()
	{
		return (isStunned) && getAnimID() != 5 && (!isEntityInvulnerable());
	}

	public double getSpeed()
	{
		return 0.4D + getExtraPower() * 0.001D;
	}

	public boolean shouldMove()
	{
		return !isStunned &&super.shouldMove();
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return false;
	}

	public int getVerticalFaceSpeed()
	{
		return getEyeLaserTime() >= 0 ? 180 : 40;
	}

	public String getCommandSenderName()
	{
		switch (getTitanVariant())
		{
			case 0:
			return "\u00A76" + StatCollector.translateToLocal("entity.EndermanTitan.name") + "\u00A7f";
			case 1:
			return "\u00A7a" + StatCollector.translateToLocal("entity.EndermanTitanHoly.name") + "\u00A7f";
			case 2:
			return "\u00A7f" + StatCollector.translateToLocal("entity.EndermanTitanIcey.name") + "\u00A7f";
		}

		return null;
	}

	public void onLivingUpdate()
	{
		if (getAnimID() == 0)
		setEyeLaserTime(getEyeLaserTime() - 1);
		if (getAttackTarget() != null && getEyeLaserTime() <= -400 && getAnimID() == 0 && !isStunned && !getWaiting() && rand.nextInt(40) == 0)
		setEyeLaserTime(100);
		if (motionY > 1.5D)motionY *= 0.8D;
		if (!worldObj.isRemote && getAttackTarget() != null && getAttackTarget() instanceof EntityWitherzilla && getCanCallBackUp())
		{
			for (int i = 0; i < 24; i++)
			{
				EntityEnderColossus entitylargefireball = new EntityEnderColossus(worldObj);
				entitylargefireball.setWaiting(false);
				entitylargefireball.copyLocationAndAnglesFrom(this);
				entitylargefireball.setAttackTarget(getAttackTarget());
				worldObj.spawnEntityInWorld(entitylargefireball);
				playSound(getRoarSound(), getSoundVolume(), getSoundPitch());
			}

			setCanCallBackUp(false);
		}

		if (destroyedCrystals < 0)
		destroyedCrystals = 0;
		if (destroyedCrystals > 12)
		{
			destroyedCrystals = 0;
			isStunned = true;
		}

		if (!isRiding() && !getWaiting() && !isStunned && getAnimID() == 0)
		{
			titanFly(16F, 6F, 16D);
		}

		EntityPlayer player = worldObj.getClosestPlayerToEntity(this, -1.0D);
		if ((player != null) && getAttackTarget() != null && (player == getAttackTarget()))
		{
			if ((rand.nextInt(200) == 0) && (getHealth() <= getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue() / 100.0F))
			{
				player.attackEntityFrom(DamageSourceExtra.mindCrush, Float.MAX_VALUE);
			}

			if ((player.getAbsorptionAmount() <= 0.0F) && (ticksExisted % 5 == 0))
			{
				player.hurtResistantTime = 0;
				player.attackEntityFrom(DamageSourceExtra.mindCrush, 12F);
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 400, 1));
				if (player.getHealth() <= 15.0F)
				{
					player.motionY = 1.0D;
					player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400, 9));
				}

				if (player.getHealth() <= 5.0F)
				{
					player.addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 1));
				}
			}

			else if ((player.getAbsorptionAmount() >= 0.0F) && (ticksExisted % 20 == 0))
			{
				player.attackEntityFrom(DamageSourceExtra.mindCrush, 12F);
			}
		}

		if (getAnimID() == 10)
		{
			if ((getAnimTick() == 30) || (getAnimTick() == 70))
			{
				func_145780_a(0, 0, 0, Blocks.stone);
			}

			if ((getAnimTick() == 150) || (getAnimTick() == 230))
			{
				playSound("thetitans:titanFall", 20.0F, 1.0F);
				playSound("thetitans:groundSmash", 20.0F, 1.0F);
				shakeNearbyPlayerCameras(10D);
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
			}

			if (getAnimTick() == 240)
			{
				playSound("thetitans:distantLargeFall", 10000.0F, 1.0F);
			}
		}

		if (ticksExisted % 120 == 0 && isEntityAlive())
		{
			playSound("thetitans:titanEnderColossusLiving", getSoundVolume(), getSoundPitch());
		}

		if (numOfCrystals < 0)
		{
			numOfCrystals = 0;
		}

		for (int i = 0; i < getParticleCount() * 5; i++)
		{
			findPlayerToLookAt();
			worldObj.spawnParticle(getParticles(), posX + (rand.nextDouble() - 0.5D) * width * 3.0D, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width * 3.0D, 0.0D, 0.25D, 0.0D);
		}

		float fl = getBrightness(1.0F);
		if ((fl > 0.5F) && !worldObj.provider.hasNoSky && (worldObj.isDaytime()) && (!worldObj.isRemote) && (ticksExisted % 1 == 0))
		{
			rotationPitch = (rotationYawHead / 6.0F);
			rotationYawHead = -90.0F;
		}

		meleeTitan = true;
		if (isStunned || deathTicks > 0)
		{
			motionX *= 0.0D;
			motionZ *= 0.0D;
		}

		for (int i = 0; i < 3; ++i)
		{
			if (getAnimID() == 2 && (getAnimTick() >= 40 && getAnimTick() <= 80) && (getAttackTarget() != null) && !worldObj.isRemote)
			{
				for (int it = 0; it < 2; it++)
				{
					double d2 = (getAttackTarget().posX + (getRNG().nextGaussian() * 64D));
					double d3 = (getAttackTarget().posY + 150D + (getRNG().nextGaussian() * 32D));
					double d4 = (getAttackTarget().posZ + (getRNG().nextGaussian() * 64D));
					double d5 = getAttackTarget().posX - d2;
					double d6 = getAttackTarget().posY - d3;
					double d7 = getAttackTarget().posZ - d4;
					EntityTitanFireball entitylargefireball1 = new EntityTitanFireball(worldObj, this, d5, d6, d7, 4);
					entitylargefireball1.posX = d2;
					entitylargefireball1.posY = d3;
					entitylargefireball1.posZ = d4;
					worldObj.spawnEntityInWorld(entitylargefireball1);
					entitylargefireball1.setFireballID(4);
				}
			}
		}

		if ((getAnimID() == 3 && getAnimTick() == 20) || (getAnimID() == 4 && getAnimTick() == 10) || (getAnimID() == 11 && getAnimTick() == 10))
		playSound("thetitans:lightningCharge", 100F, 1F);
		if (getAnimID() == 3 && (getAnimTick() == 64))
		{
			playSound("thetitans:lightningThrow", 100F, 1F);
			double d8 = 24D;
			Vec3 vec3 = getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			float da = (float)getAttackValue(5.0F);
			int i1 = getKnockbackAmount();
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + (isChild() ? 9D : 18D), posZ + dz, 1F, 0F, 1F));
			if (getAttackTarget() != null)
			{
				worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1F, 0F, 1F));
				attackChoosenEntity(getAttackTarget(), da * 3.0F, i1);
				getAttackTarget().motionY += 1.0F + rand.nextFloat();
				worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2.0F, false, false);
				worldObj.newExplosion(this, posX + dx, posY + 26.0D, posZ + dz, 1.0F, false, false);
				getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, da);
				List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(getAttackTarget(), getAttackTarget().boundingBox.expand(4.0D, 4.0D, 4.0D));
				if ((list1 != null) && (!list1.isEmpty()))
				{
					for (int i11 = 0; i11 < list1.size(); i11++)
					{
						Entity entity1 = (Entity)list1.get(i11);
						if (((entity1 instanceof EntityLivingBase)) && canAttackClass(entity1.getClass()))
						{
							worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, 1F, 0F, 1F));
							attackChoosenEntity(entity1, da, 0);
							if (!(entity1 instanceof EntityTitan))
							entity1.motionY += 1.0F + rand.nextFloat();
							List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(entity1, entity1.boundingBox.expand(4.0D, 4.0D, 4.0D));
							if ((list11 != null) && (!list11.isEmpty()))
							{
								for (int i111 = 0; i111 < list11.size(); i111++)
								{
									Entity entity11 = (Entity)list11.get(i111);
									if (entity11 != entity1 && ((entity11 instanceof EntityLivingBase)) && canAttackClass(entity11.getClass()))
									{
										worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity11.posX, entity11.posY, entity11.posZ, 1F, 0F, 1F));
										attackChoosenEntity(entity11, da, 0);
										if (!(entity11 instanceof EntityTitan))
										entity11.motionY += 1.0F + rand.nextFloat();
										List<?> list111 = worldObj.getEntitiesWithinAABBExcludingEntity(entity11, entity11.boundingBox.expand(4.0D, 4.0D, 4.0D));
										if ((list111 != null) && (!list111.isEmpty()))
										{
											for (int i1111 = 0; i1111 < list111.size(); i1111++)
											{
												Entity entity111 = (Entity)list111.get(i1111);
												if (entity111 != entity11 && ((entity111 instanceof EntityLivingBase)) && canAttackClass(entity111.getClass()))
												{
													worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity111.posX, entity111.posY, entity111.posZ, 1F, 0F, 1F));
													attackChoosenEntity(entity111, da, 0);
													if (!(entity111 instanceof EntityTitan))
													entity111.motionY += 1.0F + rand.nextFloat();
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (getAnimID() == 4 && (getAnimTick() == 50) && (getAttackTarget() != null))
		{
			playSound("thetitans:lightningThrow", 100F, 1F);
			double d8 = 24D;
			Vec3 vec3 = getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			worldObj.newExplosion(this, posX + dx, posY + d8, posZ + dz, 1.0F, false, false);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + d8, posZ + dz, 1F, 0F, 1F));
			worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2.0F, false, false);
			getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, 100F);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1F, 0F, 1F));
			double d5 = getAttackTarget().posX - posX;
			double d6 = getAttackTarget().posY - d8 - posY;
			double d7 = getAttackTarget().posZ - posZ;
			EntityLightningBall entitylargefireball = new EntityLightningBall(worldObj, this, d5, d6, d7);
			entitylargefireball.posX = posX + vec3.xCoord * d8;
			entitylargefireball.posY = posY + d8;
			entitylargefireball.posZ = posZ + vec3.zCoord * d8;
			worldObj.spawnEntityInWorld(entitylargefireball);
		}

		if (getAnimID() == 11 && (getAnimTick() == 50) && (getAttackTarget() != null) && !worldObj.isRemote)
		{
			playSound("thetitans:lightningThrow", 100F, 1F);
			double d8 = 24D;
			Vec3 vec3 = getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2.0F, false, false);
			worldObj.newExplosion(this, posX + dx, posY + d8, posZ + dz, 1.0F, false, false);
			getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, 100F);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + d8, posZ + dz, 1F, 0F, 1F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + d8, posZ + dz, 1F, 0F, 1F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + d8, posZ + dz, 1F, 0F, 1F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + d8, posZ + dz, 1F, 0F, 1F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1F, 0F, 1F));
			EntityDragonMinion entitychicken = new EntityDragonMinion(worldObj);
			entitychicken.forceNewTarget = false;
			entitychicken.targetX = getAttackTarget().posX;entitychicken.targetY = getAttackTarget().posY;entitychicken.targetZ = getAttackTarget().posZ;
			entitychicken.setLocationAndAngles(posX + dx, posY + d8, posZ + dz, rotationYaw, 0.0F);
			entitychicken.addVelocity(vec3.xCoord * 10D, vec3.yCoord * 10D, vec3.zCoord * 10D);
			entitychicken.onSpawnWithEgg((IEntityLivingData)null);
			entitychicken.setAttackTarget(getAttackTarget());
			worldObj.spawnEntityInWorld(entitychicken);
			entitychicken.master = this;
			entitychicken.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitychicken.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitychicken.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitychicken.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitychicken.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitychicken.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitychicken.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitychicken.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			numSpecialMinions += 1;
		}

		if (getAnimID() == 13 && (getAnimTick() == 50) && (getAttackTarget() != null) && !worldObj.isRemote)
		{
			float da = (float)getAttackValue(15.0F);
			int i1 = getKnockbackAmount();
			attackChoosenEntity(getAttackTarget(), da * 3.0F, i1);
			getAttackTarget().motionY += 1.0F + rand.nextFloat();
			worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2.0F, false, false);
			getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, da);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1F, 0F, 1F));
			List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(getAttackTarget(), getAttackTarget().boundingBox.expand(12.0D, 12.0D, 12.0D));
			if ((list1 != null) && (!list1.isEmpty()))
			{
				for (int i11 = 0; i11 < list1.size(); i11++)
				{
					Entity entity1 = (Entity)list1.get(i11);
					if (((entity1 instanceof EntityLivingBase)) && canAttackClass(entity1.getClass()))
					{
						worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, 1F, 0F, 1F));
						worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, 1F, 0F, 1F));
						worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, 1F, 0F, 1F));
						worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, 1F, 0F, 1F));
						attackChoosenEntity(entity1, da, 0);
						if (!(entity1 instanceof EntityTitan))
						entity1.motionY += 1.0F + rand.nextFloat();
					}
				}
			}
		}

		if ((!AnimationAPI.isEffectiveClient()) && getEyeLaserTime() < 0 && (getAttackTarget() != null) && (!isStunned) && ((getAnimID() == 0)))
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 < getMeleeRange())
			{
				switch (rand.nextInt(4))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 6);
					setAnimID(6);
					break;
					case 1:AnimationAPI.sendAnimPacket(this, 7);
					setAnimID(7);
					break;
					case 2:AnimationAPI.sendAnimPacket(this, 9);
					setAnimID(9);
					break;
					case 3:if (((getAttackTarget() instanceof EntityTitan)) || (getAttackTarget().height >= 6.0F) || (getAttackTarget().posY > posY + 6D))
					{
						AnimationAPI.sendAnimPacket(this, 1);
						setAnimID(1);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 7);
						setAnimID(7);
					}

					break;
				}
			}

			else if ((getRNG().nextInt(80) == 0))
			{
				switch (rand.nextInt(6))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 4);
					setAnimID(4);
					break;
					case 1:if (rand.nextInt(25) == 0)
					{
						AnimationAPI.sendAnimPacket(this, 5);
						setAnimID(5);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 2);
						setAnimID(2);
					}

					break;
					case 2:AnimationAPI.sendAnimPacket(this, 3);
					setAnimID(3);
					break;
					case 3:AnimationAPI.sendAnimPacket(this, 11);
					setAnimID(11);
					break;
					case 4:AnimationAPI.sendAnimPacket(this, 13);
					setAnimID(13);
					break;
					case 5:AnimationAPI.sendAnimPacket(this, 2);
					setAnimID(2);
					break;
				}
			}
		}

		if (getAnimID() == 8)
		{
			if (getAnimTick() == 15)
			{
				worldObj.playSoundEffect(head.posX, head.posY, head.posZ, "random.explode", 10F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
				for (int i = 0; i < 50; ++i)
				{
					worldObj.spawnParticle("largeexplode", head.posX + (rand.nextDouble() - 0.5D) * (double)head.width, head.posY + rand.nextDouble() * (double)head.height, head.posZ + (rand.nextDouble() - 0.5D) * (double)head.width, 0D, 0D, 0D);
					worldObj.spawnParticle("explode", head.posX + (rand.nextDouble() - 0.5D) * (double)head.width, head.posY + rand.nextDouble() * (double)head.height, head.posZ + (rand.nextDouble() - 0.5D) * (double)head.width, 0D, 0D, 0D);
				}
			}

			if (getAnimTick() == 20)
			{
				playSound("thetitans:titanEnderColossusScream", getSoundVolume(), 1.25F);
			}

			if (getAnimTick() == 90)
			{
				playSound("thetitans:titanFall", 20.0F, 1.0F);
				playSound("thetitans:groundSmash", 20.0F, 1.0F);
				shakeNearbyPlayerCameras(10D);
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
			}

			if (getAnimTick() >= 360)
			{
				isStunned = false;
			}

			else
			{
				setAttackTarget(null);
				isStunned = true;
			}
		}

		if (isStunned)
		{
			setAttackTarget(null);
			AnimationAPI.sendAnimPacket(this, 8);
		}

		if ((isWet()) && !isInWater() && getAnimID() != 5)
		{
			AnimationAPI.sendAnimPacket(this, 5);
			setAnimID(5);
		}

		setCurrentItemOrArmor(0, new ItemStack(Blocks.dragon_egg));
		if ((ridingEntity != null) && ((ridingEntity instanceof EntityDragon)))
		{
			renderYawOffset = (rotationYaw = ridingEntity.rotationYaw - 180.0F);
		}

		if ((rand.nextInt(400) == 0) && (ridingEntity == null) && (getAttackTarget() != null) && (getHealth() <= getMaxHealth() / 100.0F))
		{
			EntityDragon entitydragon = new EntityDragon(worldObj);
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY);
			int k = MathHelper.floor_double(posZ);
			entitydragon.setLocationAndAngles(i + 0.5D, j, k + 0.5D, rotationYaw, 0.0F);
			entitydragon.onSpawnWithEgg((IEntityLivingData)null);
			worldObj.spawnEntityInWorld(entitydragon);
			entitydragon.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitydragon.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitydragon.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitydragon.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitydragon.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitydragon.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitydragon.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			entitydragon.playSound("mob.enderdragon.growl", 10.0F, 1.0F);
			mountEntity(entitydragon);
		}

		List<?> list1111 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(256.0D, 256.0D, 256.0D));
		if ((list1111 != null) && (!list1111.isEmpty()))
		{
			for (int i1 = 0; i1 < list1111.size(); i1++)
			{
				Entity entity1 = (Entity)list1111.get(i1);
				if ((entity1 != null) && (getAttackTarget() != null) && ((entity1 instanceof EntityDragon)))
				{
					((EntityDragon)entity1).targetX = getAttackTarget().posX;
					((EntityDragon)entity1).targetY = getAttackTarget().posY;
					((EntityDragon)entity1).targetZ = getAttackTarget().posZ;
				}

				else if ((entity1 != null) && (getAttackTarget() == null) && ((entity1 instanceof EntityDragon)) && (entity1.riddenByEntity == null))
				{
					((EntityDragon)entity1).targetX = posX;
					((EntityDragon)entity1).targetY = (posY + 60.0D);
					((EntityDragon)entity1).targetZ = posZ;
				}
			}
		}

		if (worldObj.isDaytime() && roarcooldownTimer < -1)
		roarcooldownTimer = -1;
		if (isEntityAlive())
		++roarcooldownTimer;
		if (roarcooldownTimer == 0)
		{
			setScreaming(true);
			if ((worldObj.isDaytime()) && !worldObj.provider.hasNoSky && (!worldObj.isRemote))
			worldObj.setWorldTime(worldObj.getWorldTime() + 14000L);
		}

		if (roarcooldownTimer >= 60 || !isEntityAlive())
		{
			roarcooldownTimer = -(400 - rand.nextInt(200));
			setScreaming(false);
		}

		super.onLivingUpdate();
	}

	protected void updateAITasks()
	{
		if (getEyeLaserTime() >= 0 && !worldObj.isRemote && ticksExisted % 4 == 0)
		{
			for (int i = 0; i < 2; i++)
			{
				Vec3 vec3 = getLookVec();
				double x = i == 1 ? leftEye.posX : rightEye.posX;
				double y = (i == 1 ? leftEye.posY : rightEye.posY) + 1D;
				double z = i == 1 ? leftEye.posZ : rightEye.posZ;
				double dx = x + vec3.xCoord * 300D;
				double dy = y + vec3.yCoord * 300D;
				double dz = z + vec3.zCoord * 300D;
				double d0 = dx - x;
				double d1 = dy - y;
				double d2 = dz - z;
				if (getAttackTarget() != null)
				{
					d0 = getAttackTarget().posX - x;
					d1 = getAttackTarget().posY - y;
					d2 = getAttackTarget().posZ - z;
				}

				EntitySkeletonTitanGiantArrow entityarrow = new EntitySkeletonTitanGiantArrow(worldObj, this, d0, d1, d2);
				entityarrow.posX = x;
				entityarrow.posY = y;
				entityarrow.posZ = z;
				worldObj.spawnEntityInWorld(entityarrow);
				entityarrow.setInvisible(true);
			}
		}

		if ((ticksExisted % 20 == 0) && (numOfCrystals < maxNumOfCrystals) && (!worldObj.isRemote))
		{
			EntityEnderColossusCrystal entitychicken = new EntityEnderColossusCrystal(worldObj);
			entitychicken.setLocationAndAngles(posX, posY + (height * 2), posZ, rotationYawHead, 0.0F);
			entitychicken.onSpawnWithEgg((IEntityLivingData)null);
			worldObj.spawnEntityInWorld(entitychicken);
			numOfCrystals += 1;
			entitychicken.owner = this;
			entitychicken.motionY = 2D;
			worldObj.createExplosion(entitychicken, entitychicken.posX, entitychicken.posY, entitychicken.posZ, 6.0F, false);
		}

		super.updateAITasks();
	}

	protected String getRoarSound()
	{
		return "thetitans:titanEnderColossusRoar";
	}

	protected String getLivingSound()
	{
		return null;
	}

	protected String getHurtSound()
	{
		return "thetitans:titanEnderColossusGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:titanEnderColossusDeath";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		playSound("thetitans:titanStep", 10.0F, 0.85F);
		shakeNearbyPlayerCameras(8D);
		float f3 = rotationYaw * (float)Math.PI / 180.0F;
		float f11 = MathHelper.sin(f3);
		float f4 = MathHelper.cos(f3);
		collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(1.0D, 1.0D, 1.0D).offset((double)(f11 * 10F), 0, (double)(f4 * 10F))));
		collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(1.0D, 1.0D, 1.0D).offset((double)(f11 * 10F), 0, (double)(f4 * 10F))));
	}

	protected Item getDropItem()
	{
		return Items.ender_eye;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < 90; x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
				entitylargefireball.setPosition(posX, posY + 4D, posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(26000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			for (int x = 0; x < 16; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.ender_eye));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 16; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.ender_pearl));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 12; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.end_stone));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 12; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.obsidian));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.coal_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 6; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.iron_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.gold_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.emerald_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.diamond_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanBlocks.harcadium_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanItems.voidItem));
				itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.bedrock));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.golden_apple, 1, 1));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.dragon_egg));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 5; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.end_portal_frame));
				itembomb.setItemCount(12);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanItems.voidSword));
				itembomb.setItemCount(1);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}
	}

	protected void dropRareDrop(int p_70600_1_)
	{
		switch (rand.nextInt(3))
		{
			case 0:entityDropItem(new ItemStack(Items.golden_apple, 64, 1), 0.0F);
			break;
			case 1:dropItem(Items.diamond_sword, 64);
			break;
			case 2:dropItem(Items.name_tag, 64);
		}
	}

	public StatBase getAchievement()
	{
		return TitansAchievments.endercolossus;
	}

	public float getEyeHeight()
	{
		return 0.9F * height;
	}

	public boolean isScreaming()
	{
		return dataWatcher.getWatchableObjectByte(15) > 0;
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_)
	{
		return 15728880;
	}

	public float getBrightness(float p_70013_1_)
	{
		return 1.0F;
	}

	public void setScreaming(boolean p_70819_1_)
	{
		if (!worldObj.isRemote)
		dataWatcher.updateObject(15, Byte.valueOf((byte)(p_70819_1_ ? 1 : 0)));
		if (p_70819_1_)
		{
			playSound(getRoarSound(), getSoundVolume(), 1.0F);
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		Object p_180482_2_1 = super.onSpawnWithEgg(p_180482_2_);
		playSound(getRoarSound(), getSoundVolume(), 1.0F);
		setScreaming(true);
		setCanCallBackUp(true);
		setRoarCooldownTimer(-20 - rand.nextInt(20));
		List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(256.0D, 256.0D, 256.0D));
		if ((list11 != null) && (!list11.isEmpty()))
		{
			for (int i1 = 0; i1 < list11.size(); i1++)
			{
				Entity entity = (Entity)list11.get(i1);
				if ((entity != null) && ((entity instanceof EntityWitherzilla)))
				{
					List<?> list111 = worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(256.0D, 256.0D, 256.0D));
					if ((list111 != null) && (!list111.isEmpty()))
					{
						for (int i11 = 0; i11 < list111.size(); i11++)
						{
							Entity entity1 = (Entity)list111.get(i11);
							if ((entity1 != null) && ((entity1 instanceof EntityPlayer)))
							{
								((EntityPlayer)entity1).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.witherzilla.endercolossus")));
							}
						}
					}
				}
			}
		}

		return (IEntityLivingData)p_180482_2_1;
	}

	protected boolean canDespawn()
	{
		return false;
	}

	public double getYOffset()
	{
		return super.getYOffset() - 1.0D;
	}

	protected void inactDeathAction()
	{
		if (!worldObj.isRemote)
		{
			playSound("mob.endermen.death", getSoundVolume(), getSoundPitch());
			if ((worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
			{
				dropFewItems(true, 0);
				dropEquipment(true, 0);
				dropRareDrop(1);
			}

			EntityTitanSpirit entitytitan = new EntityTitanSpirit(worldObj);
			entitytitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.spawnEntityInWorld(entitytitan);
			entitytitan.setVesselHunting(false);
			entitytitan.setSpiritType(10);
		}
	}

	@SuppressWarnings("unchecked")
	protected void onTitanDeathUpdate()
	{
		dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(getTitanHealth(), 0.0F, getMaxHealth())));
		if (getTitanHealth() <= 0F)
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

		if (deathTicks >= 500)
		{
			setInvulTime(getInvulTime() + 8);
			float f = (rand.nextFloat() - 0.5F) * 12.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 3.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 12.0F;
			worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}

		if (getInvulTime() >= getThreashHold())
		{
			setDead();
		}
	}

	public int getRoarCooldownTimer()
	{
		return roarcooldownTimer;
	}

	public void setRoarCooldownTimer(int roarcooldownTimer)
	{
		this.roarcooldownTimer = roarcooldownTimer;
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

		if (isEntityInvulnerable() || getAnimID() == 5)
		{
			return false;
		}

		else if (((source.getEntity() instanceof EntityEndermanMinion)) || ((source.getEntity() instanceof EntityEnderColossus)) || ((source.getEntity() instanceof EntityDragon)) || ((source.getEntity() instanceof EntityDragonMinion)))
		{
			return false;
		}

		else
		{
			return super.attackEntityFrom(source, amount);
		}
	}

	public boolean attackEntityFromPart(EntityTitanPart p_70965_1_, DamageSource source, float amount)
	{
		func_82195_e(source, amount);
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

	protected EntityLiving getMinion()
	{
		return new EntityEndermanMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}
}


