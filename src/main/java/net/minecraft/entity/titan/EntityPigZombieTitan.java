package net.minecraft.entity.titan;
import com.google.common.collect.Lists;
import net.minecraft.theTitans.core.TheCore;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titan.ai.EntityAITitanLookIdle;
import net.minecraft.entity.titan.ai.EntityAITitanWander;
import net.minecraft.entity.titan.ai.EntityAITitanWatchClosest;
import net.minecraft.entity.titan.animation.pigzombietitan.*;
import net.minecraft.entity.titanminion.EntityGhastGuard;
import net.minecraft.entity.titanminion.EntityGhastGuardFireball;
import net.minecraft.entity.titanminion.EntityPigZombieMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
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
import thehippomaster.AnimationAPI.AnimationAPI;
import thehippomaster.AnimationAPI.IAnimatedEntity;
public class EntityPigZombieTitan
extends EntityTitan
implements IAnimatedEntity, ITitanHitbox
{
	public boolean isStunned;
	public EntityTitanPart head;
	public EntityTitanPart body;
	public EntityTitanPart rightArm;
	public EntityTitanPart leftArm;
	public EntityTitanPart rightLeg;
	public EntityTitanPart leftLeg;
	public EntityPigZombieTitan(World worldIn)
	{
		super(worldIn);
		head = new EntityTitanPart(worldIn, this, "head", 8.0F, 8.0F);
		body = new EntityTitanPart(worldIn, this, "body", 8.0F, 12.0F);
		rightArm = new EntityTitanPart(worldIn, this, "rightarm", 4.0F, 4.0F);
		leftArm = new EntityTitanPart(worldIn, this, "leftarm", 4.0F, 4.0F);
		rightLeg = new EntityTitanPart(worldIn, this, "rightleg", 4.0F, 12.0F);
		leftLeg = new EntityTitanPart(worldIn, this, "leftleg", 4.0F, 12.0F);
		partArray.add(head);
		partArray.add(body);
		partArray.add(rightArm);
		partArray.add(leftArm);
		partArray.add(rightLeg);
		partArray.add(leftLeg);
		shouldParticlesBeUpward = true;
		experienceValue = 100000;
		setSize(8.0F, 32.0F);
		meleeTitan = true;
		setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		worldObj.spawnEntityInWorld(head);
		worldObj.spawnEntityInWorld(body);
		worldObj.spawnEntityInWorld(rightArm);
		worldObj.spawnEntityInWorld(leftArm);
		worldObj.spawnEntityInWorld(rightLeg);
		worldObj.spawnEntityInWorld(leftLeg);
	}

	protected void applyEntityAI()
	{
		tasks.addTask(1, new AnimationPigZombieTitanCreation(this));
		tasks.addTask(1, new AnimationPigZombieTitanRangedAttack(this));
		tasks.addTask(1, new AnimationPigZombieTitanRoar(this));
		tasks.addTask(1, new AnimationPigZombieTitanDeath(this));
		tasks.addTask(1, new AnimationPigZombieTitanAttack4(this));
		tasks.addTask(1, new AnimationPigZombieTitanStun(this));
		tasks.addTask(1, new AnimationPigZombieTitanAttack3(this));
		tasks.addTask(1, new AnimationPigZombieTitanAttack2(this));
		tasks.addTask(1, new AnimationPigZombieTitanLightningAttack(this));
		tasks.addTask(1, new AnimationPigZombieTitanAntiTitanAttack(this));
		tasks.addTask(1, new AnimationPigZombieTitanAttack1(this));
		tasks.addTask(1, new AnimationPigZombieTitanAttack6(this));
		tasks.addTask(1, new AnimationPigZombieTitanAttack5(this));
		tasks.addTask(6, new EntityAITitanWander(this, 300));
		tasks.addTask(7, new EntityAITitanWatchClosest(this, EntityTitanSpirit.class, 128.0F));
		tasks.addTask(8, new EntityAITitanWatchClosest(this, EntityTitan.class, 128.0F));
		tasks.addTask(9, new EntityAITitanWatchClosest(this, EntityPlayer.class, 64.0F));
		tasks.addTask(10, new EntityAITitanLookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.PigZombieTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(12, Byte.valueOf((byte)0));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 25.0F;
	}

	public double getSpeed()
	{
		return isChild() ? 0.9D + getExtraPower() * 0.001D : 0.6D + getExtraPower() * 0.001D;
	}

	public boolean isArmored()
	{
		return getHealth() <= getMaxHealth() / 4.0F || TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) || TheTitans.isDifficulty(worldObj, TheCore.IMPOSSIBLE);
	}

	public boolean isChild()
	{
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	public float getRenderSizeModifier()
	{
		return super.getRenderSizeModifier();
	}

	public int getMinionCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getMinionCap() * 6) : (super.getMinionCap() * 3);
	}

	public int getPriestCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getPriestCap() * 6) : (super.getPriestCap() * 3);
	}

	public int getZealotCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getZealotCap() * 6) : (super.getZealotCap() * 3);
	}

	public int getBishopCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getBishopCap() * 6) : (super.getBishopCap() * 3);
	}

	public int getTemplarCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getTemplarCap() * 6) : (super.getTemplarCap() * 3);
	}

	protected int getExperiencePoints(EntityPlayer player)
	{
		if (isChild())
		{
			experienceValue = ((int)(experienceValue * 2.5F));
		}

		return super.getExperiencePoints(player);
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted > 5)
		{
			float f = renderYawOffset * 3.1415927F / 180.0F;
			float f1 = MathHelper.sin(f);
			float f2 = MathHelper.cos(f);
			
			if (isChild())
			{
				setSize(0.325F * getTitanSizeMultiplier(), 1F * getTitanSizeMultiplier());
				head.height = head.width = 0.375F * getTitanSizeMultiplier();
				body.height = 0.375F * getTitanSizeMultiplier();
				body.width = 0.25F * getTitanSizeMultiplier();
				leftLeg.height = rightLeg.height = 0.375F * getTitanSizeMultiplier();
				leftLeg.width = rightLeg.width = 0.125F * getTitanSizeMultiplier();
				rightArm.width = leftArm.width = rightArm.height = leftArm.height = 0.125F * getTitanSizeMultiplier();
			}
			else
			{
				setSize(0.5F * getTitanSizeMultiplier(), 2F * getTitanSizeMultiplier());
				head.height = head.width = 0.5F * getTitanSizeMultiplier();
				body.height = 0.75F * getTitanSizeMultiplier();
				body.width = 0.5F * getTitanSizeMultiplier();
				leftLeg.height = rightLeg.height = 0.75F * getTitanSizeMultiplier();
				leftLeg.width = rightLeg.width = 0.25F * getTitanSizeMultiplier();
				rightArm.width = leftArm.width = rightArm.height = leftArm.height = 0.25F * getTitanSizeMultiplier();
			}

			head.setLocationAndAngles(posX, posY + (0.75D * height), posZ, 0.0F, 0.0F);
			body.setLocationAndAngles(posX, posY + (0.375D * height), posZ, 0.0F, 0.0F);
			rightArm.setLocationAndAngles(posX + f2 * (0.75D * width), posY + (0.625D * height), posZ + f1 * (0.75D * width), 0.0F, 0.0F);
			leftArm.setLocationAndAngles(posX - f2 * (0.75D * width), posY + (0.625D * height), posZ - f1 * (0.75D * width), 0.0F, 0.0F);
			rightLeg.setLocationAndAngles(posX + f2 * (0.25D * width), posY, posZ + f1 * (0.25D * width), 0.0F, 0.0F);
			leftLeg.setLocationAndAngles(posX - f2 * (0.25D * width), posY, posZ - f1 * (0.25D * width), 0.0F, 0.0F);
			if ((isEntityAlive()) && (!isStunned))
			{
				collideWithEntities(head, worldObj.getEntitiesWithinAABBExcludingEntity(this, head.boundingBox.expand(1.0D, 0.0D, 1.0D)));
				collideWithEntities(body, worldObj.getEntitiesWithinAABBExcludingEntity(this, body.boundingBox.expand(1.0D, 0.0D, 1.0D)));
				collideWithEntities(rightArm, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightArm.boundingBox.expand(1.0D, 0.0D, 1.0D)));
				collideWithEntities(leftArm, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftArm.boundingBox.expand(1.0D, 0.0D, 1.0D)));
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(1.0D, 0.0D, 1.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(1.0D, 0.0D, 1.0D)));
			}

			destroyBlocksInAABB(head.boundingBox);
			destroyBlocksInAABB(body.boundingBox);
			destroyBlocksInAABB(rightArm.boundingBox);
			destroyBlocksInAABB(leftArm.boundingBox);
			destroyBlocksInAABB(leftLeg.boundingBox.expand(1.0D, 0.0D, 1.0D));
			destroyBlocksInAABB(rightLeg.boundingBox.expand(1.0D, 0.0D, 1.0D));
		}
		
		super.onHitboxUpdate();
	}

	public void setChild(boolean childZombie)
	{
		getDataWatcher().updateObject(12, Byte.valueOf((byte)(childZombie ? 1 : 0)));
	}

	public int getFootStepModifer()
	{
		return 5;
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		if (isChild())
		{
			tagCompound.setBoolean("IsBaby", true);
		}

		tagCompound.setBoolean("Stunned", isStunned);
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		if (tagCompund.getBoolean("IsBaby"))
		{
			setChild(true);
		}

		isStunned = tagCompund.getBoolean("Stunned");
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.AVERAGE;
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return p_70686_1_ != head.getClass() && p_70686_1_ != body.getClass() && p_70686_1_ != rightArm.getClass() && p_70686_1_ != leftArm.getClass() && p_70686_1_ != rightLeg.getClass() && p_70686_1_ != leftLeg.getClass() && (p_70686_1_ != EntityPigZombieMinion.class) && (p_70686_1_ != EntityGhastGuard.class) && (p_70686_1_ != EntityPigZombieTitan.class);
	}

	public void onLivingUpdate()
	{
		if (motionY > 3.0D)motionY *= 0.8D;
		if (!isRiding() && !getWaiting() && !isStunned && getAnimID() == 0)
		{
			titanFly(10F, 2F, 4D);
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
			playSound("thetitans:titanPigZombieLiving", getSoundVolume(), 0.8F);
			if (getAnimID() == 13 && getAnimTick() == 40)
			playSound("thetitans:titanRumble", getSoundVolume(), 1F);
			if (getAnimID() == 13 && getAnimTick() == 240)
			playSound("thetitans:titanZombieRoar", getSoundVolume(), 1F);
			if (getAnimID() == 13 && getAnimTick() == 130)
			playSound("thetitans:titanSkeletonGetUp", getSoundVolume(), 1F);
			if (getAnimID() == 13 && (getAnimTick() == 110 || getAnimTick() == 150 || getAnimTick() == 170))
			{
				func_145780_a(0, 0, 0, Blocks.stone);
				playSound("thetitans:titanPress", getSoundVolume(), 1F);
			}
		}

		if ((worldObj.isRemote) && (deathTicks < getThreashHold()) && (isWet()))
		{
			for (int i = 0; i < getParticleCount() * 50; i++)
			{
				worldObj.spawnParticle(getParticles(), posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.25D, 0.0D);
			}
		}

		if ((rand.nextInt(40) == 0) && (isWet()))
		{
			playSound("random.fizz", 20.0F, 1.5F);
		}

		if (getAnimID() == 8)
		{
			if (getAnimTick() == 140 || getAnimTick() == 300 || getAnimTick() == 330)
			{
				playSound("thetitans:slashFlesh", 20.0F, 1.0F);
				attackEntityFromPart(head, (new DamageSource("other")).setDamageBypassesArmor().setDamageIsAbsolute(), 1000F);
			}

			if (getAnimTick() == 400)
			playSound("thetitans:titanZombieRoar", 1000F, 0.95F);
			if (getAnimTick() == 530)
			isStunned = false;
			else
			setAttackTarget(null);
		}

		if (isStunned)
		{
			setAttackTarget(null);
			AnimationAPI.sendAnimPacket(this, 8);
			setAnimID(8);
		}

		if (getAnimID() == 10)
		{
			if ((getAnimTick() == 30) || (getAnimTick() == 70))
			{
				func_145780_a(0, 0, 0, Blocks.stone);
			}

			if (getAnimTick() == 190)
			{
				playSound("thetitans:titanFall", 20.0F, 1.0F);
				playSound("thetitans:groundSmash", 20.0F, 1.0F);
				shakeNearbyPlayerCameras(10D);
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
			}

			if (getAnimTick() == 200)
			{
				playSound("thetitans:distantLargeFall", 10000.0F, 1.0F);
			}
		}

		if ((getAnimID() == 7) && (getAnimTick() == 122))
		{
			double d8 = 32.0D;
			Vec3 vec3 = getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			int y = MathHelper.floor_double(posY);
			int x = MathHelper.floor_double(posX + dx);
			int z = MathHelper.floor_double(posZ + dz);
			if (worldObj.getBlock(x, y - 1, z).getMaterial() != Material.air)
			{
				playSound("thetitans:titanStrike", 20.0F, 1.0F);
				playSound("thetitans:titanSlam", 20.0F, 1.0F);
			}

			for (int l1 = -4; l1 <= 4; l1++)
			{
				for (int i2 = -4; i2 <= 4; i2++)
				{
					for (int j = -1; j <= 1; j++)
					{
						int j2 = x + l1;
						int k = y + j;
						int l = z + i2;
						Block block = worldObj.getBlock(j2, k, l);
						if (!block.isAir(worldObj, j2, k, l))
						{
							worldObj.playAuxSFX(2001, j2, k, l, Block.getIdFromBlock(block));
							if (block == Blocks.grass)
							{
								worldObj.setBlock(j2, k, l, Blocks.dirt);
							}
						}

						if (block.getExplosionResistance(this) <= 1.5F && block.isOpaqueCube() && block != Blocks.air && block != Blocks.netherrack && block != Blocks.dirt && block != Blocks.grass && block != Blocks.glass && block != Blocks.glass_pane)
						{
							AnimationAPI.sendAnimPacket(this, 8);
							setAnimID(8);
							setAnimTick(0);
							playSound("random.anvil_land", 20.0F, 0.5F);
							if (!worldObj.isRemote)
							isStunned = true;
						}
					}
				}
			}
		}

		float f = renderYawOffset * 3.1415927F / 180.0F;

		meleeTitan = true;
		if (getAnimID() == 5)
		{
			if (getAnimTick() == 34)
			playSound("thetitans:lightningCharge", 100F, 1F);
			if ((getAnimTick() <= 50) && (getAnimTick() >= 20))
			{
				float ex = isChild() ? 4.5F : 9.5F;
				float fl = renderYawOffset * 3.1415927F / 180.0F;
				float fl1 = MathHelper.sin(fl);
				float fl2 = MathHelper.cos(fl);
				worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX - fl2 * ex, posY + (isChild() ? 12D : 24D), posZ - fl1 * ex, 1.0F, 0.0F, 0F));
				worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + fl2 * ex, posY + (isChild() ? 12D : 24D), posZ + fl1 * ex, 1.0F, 0.0F, 0F));
				if (getAttackTarget() == null && !worldObj.isRemote)
				heal(50F);
			}

			if ((getAnimTick() == 64))
			{
				playSound("thetitans:lightningThrow", 100F, 1F);
				double d8 = isChild() ? 6F : 12D;
				Vec3 vec3 = getLook(1.0F);
				double dx = vec3.xCoord * d8;
				double dz = vec3.zCoord * d8;
				float da = (float)getAttackValue(5.0F);
				int i1 = getKnockbackAmount();
				worldObj.newExplosion(this, posX + dx, posY + 26.0D, posZ + dz, 1.0F, false, false);
				worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + (isChild() ? 9D : 18D), posZ + dz, 1.0F, 0.0F, 0F));
				if (getAttackTarget() != null)
				{
					attackChoosenEntity(getAttackTarget(), da, i1);
					attackChoosenEntity(getAttackTarget(), da, i1);
					attackChoosenEntity(getAttackTarget(), da, i1);
					getAttackTarget().motionY += 1.0F + rand.nextFloat();
					worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2.0F, false, false);
					getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, f);
					worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1.0F, 0.0F, 0F));
					List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(getAttackTarget(), getAttackTarget().boundingBox.expand(12.0D, 12.0D, 12.0D));
					if ((list1 != null) && (!list1.isEmpty()))
					{
						for (int i11 = 0; i11 < list1.size(); i11++)
						{
							Entity entity1 = (Entity)list1.get(i11);
							if (((entity1 instanceof EntityLivingBase)) && canAttackClass(entity1.getClass()))
							{
								worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, 1.0F, 0.0F, 0F));
								attackChoosenEntity(entity1, da, i1);
								attackChoosenEntity(entity1, da, i1);
								if (!(entity1 instanceof EntityTitan))
								entity1.motionY += 1.0F + rand.nextFloat();
							}
						}
					}

					for (int it = 0; it < 300; it++)
					{
						Vec3 vec31 = getLook(1F);
						double d5 = (getAttackTarget().posX + (rand.nextGaussian() * 8D)) - (head.posX + vec31.xCoord * (16.0D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F));
						double d6 = (getAttackTarget().posY + (rand.nextGaussian() * 8D)) - (head.posY + 32D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F);
						double d7 = (getAttackTarget().posZ + (rand.nextGaussian() * 8D)) - (head.posZ + vec31.zCoord * (16.0D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F));
						EntityGhastGuardFireball entitylargefireball = new EntityGhastGuardFireball(worldObj, this, d5, d6, d7);
						entitylargefireball.posX = (head.posX + vec31.xCoord * (16.0D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F));
						entitylargefireball.posY = (head.posY + 32D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F);
						entitylargefireball.posZ = (head.posZ + vec31.zCoord * (16.0D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F));
						worldObj.spawnEntityInWorld(entitylargefireball);
						EntityTitanFireball entitylargefireball1 = new EntityTitanFireball(worldObj, this, d5, d6, d7, 3);
						entitylargefireball1.posX = (head.posX + (vec31.xCoord * (16.0D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F)));
						entitylargefireball1.posY = (head.posY + 32D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F);
						entitylargefireball1.posZ = (head.posZ + (vec31.zCoord * (16.0D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F)));
						worldObj.spawnEntityInWorld(entitylargefireball1);
						entitylargefireball1.setFireballID(3);
						EntitySmallFireball entitysmallfireball = new EntitySmallFireball(worldObj, this, d5, d6, d7);
						entitysmallfireball.posX = (head.posX + vec31.xCoord * (16.0D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F));
						entitysmallfireball.posY = (head.posY + 32D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F);
						entitysmallfireball.posZ = (head.posZ + vec31.zCoord * (16.0D + (getRNG().nextFloat() * 2.0F - 1.0F) * 16.0F));
						worldObj.spawnEntityInWorld(entitysmallfireball);
					}
				}
			}
		}

		if (isStunned || deathTicks > 0)
		{
			motionX *= 0.0D;
			motionZ *= 0.0D;
		}

		if ((!AnimationAPI.isEffectiveClient()) && (getAttackTarget() != null) && (!isStunned) && ((getAnimID() == 0)))
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 < getMeleeRange())
			{
				switch (rand.nextInt(7))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 6);
					setAnimID(6);
					break;
					case 1:AnimationAPI.sendAnimPacket(this, 4);
					setAnimID(4);
					break;
					case 2:AnimationAPI.sendAnimPacket(this, 9);
					setAnimID(9);
					break;
					case 3:if (getAttackTarget().height >= height - 12F || (posY + getEyeHeight() < getAttackTarget().posY + getAttackTarget().getEyeHeight()))
					{
						AnimationAPI.sendAnimPacket(this, 1);
						setAnimID(1);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 4);
						setAnimID(4);
					}

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

			else if ((getAnimID() == 0) && (getRNG().nextInt(100) == 0))
			{
				switch (rand.nextInt(3))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 5);
					setAnimID(5);
					break;
					case 1:AnimationAPI.sendAnimPacket(this, 12);
					setAnimID(12);
					break;
					case 2:if (getRNG().nextInt(4) == 0)
					{
						AnimationAPI.sendAnimPacket(this, 11);
						setAnimID(11);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 5);
						setAnimID(5);
					}

					break;
				}
			}
		}

		if (getAnimID() == 12)
		{
			if (getAttackTarget() != null && getAnimTick() == 55)
			for (int i = 0; i < 4 + (2 * worldObj.difficultySetting.getDifficultyId()); ++i)
			attackEntityWithRangedAttack(getAttackTarget(), 0F);
		}

		if ((rand.nextInt(20) == 0) && (getAttackTarget() != null))
		{
			playSound("mob.zombiepig.zpigangry", 10000.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * ((0.6F + rand.nextFloat())));
		}

		if (isChild())
		{
			setCustomNameTag(StatCollector.translateToLocal("entity.PigZombieTitan.name.baby"));
		}

		else
		{
			setCustomNameTag(StatCollector.translateToLocal("entity.PigZombieTitan.name"));
		}

		super.onLivingUpdate();
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		renderYawOffset = rotationYaw = rotationYawHead;
		faceEntity(p_82196_1_, 180.0F, 30.0F);
		double d8 = 12D;
		Vec3 vec3 = getLook(1.0F);
		double dx = vec3.xCoord * d8;
		double dz = vec3.zCoord * d8;
		EntityProtoBall entityarrow = new EntityProtoBall(worldObj, this);
		double d0 = (p_82196_1_.posX + p_82196_1_.motionX) - (head.posX + dx);
		double d1 = (p_82196_1_.posY + (double)p_82196_1_.getEyeHeight() - 8D) - head.posY;
		double d2 = (p_82196_1_.posZ + p_82196_1_.motionZ) - (head.posZ + dz);
		float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
		entityarrow.setThrowableHeading(d0, d1 + f1, d2, 0.95F, (float)(45 - worldObj.difficultySetting.getDifficultyId() * 5));
		entityarrow.posX = head.posX + dx;
		entityarrow.posY = head.posY;
		entityarrow.posZ = head.posZ + dz;
		if (!worldObj.isRemote)
		worldObj.spawnEntityInWorld(entityarrow);
		entityarrow.motionX *= 1.5F;
		entityarrow.motionZ *= 1.5F;
		worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)posX, (int)posY, (int)posZ, 0);
		if (getDistanceToEntity(p_82196_1_) < 20.0D)
		{
			attackChoosenEntity(p_82196_1_, 10F, 5);
		}
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(100) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL);
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.PigZombieTitanMinionSpawnrate;
	}

	public boolean shouldMove()
	{
		return !isStunned && super.shouldMove();
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return false;
	}

	public boolean canBeHurtByPlayer()
	{
		return (isStunned) && (!isEntityInvulnerable());
	}

	public String getParticles()
	{
		if (worldObj.rand.nextFloat() < 0.25D)
		{
			return "explode";
		}

		return "smoke";
	}

	public boolean isEntityUndead()
	{
		return true;
	}

	protected void fall(float p_70069_1_)
	{
		super.fall(p_70069_1_);
		if (!worldObj.isRemote)
		addRandomArmor();
	}

	public float getEyeHeight()
	{
		return 0.8625F * height;
	}

	protected String getLivingSound()
	{
		return isStunned || getWaiting() || getAnimID() == 13 ? null : "thetitans:titanPigZombieLiving";
	}

	protected String getHurtSound()
	{
		return "thetitans:titanPigZombieGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:titanPigZombieDeath";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		playSound("thetitans:titanStep", 10.0F, 1.0F);
		shakeNearbyPlayerCameras(4D);
		if (!getWaiting() && getAnimID() != 13)
		{
			float f3 = rotationYaw * (float)Math.PI / 180.0F;
			float f11 = MathHelper.sin(f3);
			float f4 = MathHelper.cos(f3);
			if (footID == 0)
			{
				destroyBlocksInAABB(leftLeg.boundingBox.offset(0, -1, 0));
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(1.0D, 1.0D, 1.0D).offset((double)(f11 * 4F), 0, (double)(f4 * 4F))));
				++footID;
			}

			else
			{
				destroyBlocksInAABB(rightLeg.boundingBox.offset(0, -1, 0));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(1.0D, 1.0D, 1.0D).offset((double)(f11 * 4F), 0, (double)(f4 * 4F))));
				footID = 0;
			}
		}
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < 24; x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
				entitylargefireball.setPosition(posX, posY + 4D, posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(20000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.stick));
				itembomb.setItemCount(16);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.gold_ingot));
				itembomb.setItemCount(16);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.rotten_flesh));
				itembomb.setItemCount(24 + rand.nextInt(24 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.gold_nugget));
				itembomb.setItemCount(24 + rand.nextInt(24 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.bone));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.coal));
				itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
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
				itembomb.setEntityItemStack(new ItemStack(Items.gold_ingot));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
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

			if (rand.nextInt(5) == 0)
			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.bedrock));
				itembomb.setItemCount(1);
				worldObj.spawnEntityInWorld(itembomb);
			}

			if (rand.nextInt(5) == 0)
			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.bedrock));
				itembomb.setItemCount(1);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}
	}

	public StatBase getAchievement()
	{
		return TitansAchievments.pigzombietitan;
	}

	public double getYOffset()
	{
		return super.getYOffset() - 8.0D;
	}

	protected void dropRareDrop(int p_70600_1_)
	{
		entityDropItem(new ItemStack(Blocks.gold_block, 64, 1), 0.0F);
	}

	protected void addRandomArmor()
	{
		super.addRandomArmor();
		setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
		for (int i = 0; i < 2; i++)
		{
			EntityGhastGuard entitychicken = new EntityGhastGuard(worldObj);
			entitychicken.master = this;
			entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			entitychicken.onSpawnWithEgg((IEntityLivingData)null);
			worldObj.spawnEntityInWorld(entitychicken);
			entitychicken.addVelocity(0.0D, 1.5D, 0.0D);
			entitychicken.playSound("thetitans:titansummonminion", 10.0F, 0.5F);
		}
	}

	public boolean attackZombieFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		else if (((source.getEntity() instanceof EntityPigZombieMinion)) || ((source.getEntity() instanceof EntityPigZombieTitan)) || ((source.getEntity() instanceof EntityGhastGuard)))
		{
			return false;
		}

		else
		{
			Entity entity = source.getEntity();
			if (((entity instanceof EntityLivingBase)) && (!isEntityInvulnerable()) && (amount > 25.0F))
			{
				List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(256.0D, 256.0D, 256.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if ((entity1 instanceof EntityPigZombieTitan))
					{
						EntityPigZombieTitan entitypigzombie = (EntityPigZombieTitan)entity1;
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

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return attackZombieFrom(source, amount);
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean attackEntityFromPart(EntityTitanPart p_70965_1_, DamageSource source, float amount)
	{
		func_82195_e(source, amount);
		return true;
	}

	protected boolean func_82195_e(DamageSource p_82195_1_, float p_82195_2_)
	{
		return attackZombieFrom(p_82195_1_, p_82195_2_);
	}

	public World func_82194_d()
	{
		return worldObj;
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		Object p_180482_2_1 = super.onSpawnWithEgg(p_180482_2_);
		setCanPickUpLoot(true);
		setWaiting(true);
		if (worldObj.rand.nextFloat() < 0.05F)
		{
			setChild(true);
		}

		addRandomArmor();
		enchantEquipment();
		if (getEquipmentInSlot(4) == null)
		{
			Calendar calendar = worldObj.getCurrentDate();
			if ((calendar.get(2) + 1 == 10) && (calendar.get(5) == 31) && (rand.nextFloat() < 0.25F))
			{
				setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				equipmentDropChances[4] = 0.0F;
			}
		}

		return (IEntityLivingData)p_180482_2_1;
	}

	protected void inactDeathAction()
	{
		if (!worldObj.isRemote)
		{
			playSound("mob.zombiepig.zpigdeath", getSoundVolume(), getSoundPitch());
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
			entitytitan.setSpiritType(8);
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
			AnimationAPI.sendAnimPacket(this, 0);
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

	protected EntityLiving getMinion()
	{
		return new EntityPigZombieMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}
}


