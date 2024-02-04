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
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titan.ai.EntityAITitanWander;
import net.minecraft.entity.titan.ai.EntityAITitanWatchClosest;
import net.minecraft.entity.titan.animation.zombietitan.*;
import net.minecraft.entity.titanminion.EntityGiantZombieBetter;
import net.minecraft.entity.titanminion.EntityZombieMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.theTitans.world.WorldProviderNowhere;
import net.minecraft.theTitans.world.WorldProviderVoid;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.common.ForgeModContainer;
import thehippomaster.AnimationAPI.AnimationAPI;
import thehippomaster.AnimationAPI.IAnimatedEntity;
public class EntityZombieTitan
extends EntityTitan
implements IAnimatedEntity, ITitanHitbox
{
	public EntityTitanPart head;
	public EntityTitanPart body;
	public EntityTitanPart rightArm;
	public EntityTitanPart leftArm;
	public EntityTitanPart rightLeg;
	public EntityTitanPart leftLeg;
	public EntityZombieTitan(World worldIn)
	{
		super(worldIn);
		head = new EntityTitanPart(worldIn, this, "head", 0.5F * getTitanSizeMultiplier(), 0.5F * getTitanSizeMultiplier());
		body = new EntityTitanPart(worldIn, this, "body", 0.5F * getTitanSizeMultiplier(), 0.75F * getTitanSizeMultiplier());
		rightArm = new EntityTitanPart(worldIn, this, "rightarm", 0.25F * getTitanSizeMultiplier(), 0.25F * getTitanSizeMultiplier());
		leftArm = new EntityTitanPart(worldIn, this, "leftarm", 0.25F * getTitanSizeMultiplier(), 0.25F * getTitanSizeMultiplier());
		rightLeg = new EntityTitanPart(worldIn, this, "rightleg", 0.25F * getTitanSizeMultiplier(), 0.75F * getTitanSizeMultiplier());
		leftLeg = new EntityTitanPart(worldIn, this, "leftleg", 0.25F * getTitanSizeMultiplier(), 0.75F * getTitanSizeMultiplier());
		partArray.add(head);
		partArray.add(body);
		partArray.add(rightArm);
		partArray.add(leftArm);
		partArray.add(rightLeg);
		partArray.add(leftLeg);
		setSize(0.5F * getTitanSizeMultiplier(), 2F * getTitanSizeMultiplier());
		experienceValue = 10000;
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		worldIn.spawnEntityInWorld(head);
		worldIn.spawnEntityInWorld(body);
		worldIn.spawnEntityInWorld(rightArm);
		worldIn.spawnEntityInWorld(leftArm);
		worldIn.spawnEntityInWorld(rightLeg);
		worldIn.spawnEntityInWorld(leftLeg);
	}

	protected void applyEntityAI()
	{
		footID = 1;
		tasks.addTask(1, new AnimationZombieTitanCreation(this));
		tasks.addTask(1, new AnimationZombieTitanDeath(this));
		tasks.addTask(1, new AnimationZombieTitanAttack4(this));
		tasks.addTask(1, new AnimationZombieTitanStun(this));
		tasks.addTask(1, new AnimationZombieTitanAttack3(this));
		tasks.addTask(1, new AnimationZombieTitanAttack2(this));
		tasks.addTask(1, new AnimationZombieTitanRangedAttack(this));
		tasks.addTask(1, new AnimationZombieTitanLightningAttack(this));
		tasks.addTask(1, new AnimationZombieTitanRoar(this));
		tasks.addTask(1, new AnimationZombieTitanAntiTitanAttack(this));
		tasks.addTask(1, new AnimationZombieTitanAttack1(this));
		tasks.addTask(1, new AnimationZombieTitanReformSword(this));
		tasks.addTask(1, new AnimationZombieTitanAttack5(this));
		tasks.addTask(6, new EntityAITitanWander(this, 300));
		tasks.addTask(7, new EntityAITitanWatchClosest(this, EntityTitanSpirit.class, 128.0F));
		tasks.addTask(8, new EntityAITitanWatchClosest(this, EntityTitan.class, 128.0F));
		tasks.addTask(9, new EntityAITitanWatchClosest(this, EntityPlayer.class, 64.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.ZombieTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 20.0F;
	}

	public boolean isArmored()
	{
		return getHealth() <= getMaxHealth() / 4 || TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) || TheTitans.isDifficulty(worldObj, TheCore.IMPOSSIBLE);
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return p_70686_1_ != head.getClass() && p_70686_1_ != body.getClass() && p_70686_1_ != rightArm.getClass() && p_70686_1_ != leftArm.getClass() && p_70686_1_ != rightLeg.getClass() && p_70686_1_ != leftLeg.getClass() && (p_70686_1_ != EntityZombieMinion.class) && (p_70686_1_ != EntityGiantZombieBetter.class) && (p_70686_1_ != EntityZombieTitan.class);
	}

	public float getRenderSizeModifier()
	{
		return super.getRenderSizeModifier();
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(50) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (isValidLightLevel());
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.ZombieTitanMinionSpawnrate;
	}

	public int getMinionCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getMinionCap() * 4) : (super.getMinionCap() * 2);
	}

	public int getPriestCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getPriestCap() * 4) : (super.getPriestCap() * 2);
	}

	public int getZealotCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getZealotCap() * 4) : (super.getZealotCap() * 2);
	}

	public int getBishopCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getBishopCap() * 4) : (super.getBishopCap() * 2);
	}

	public int getTemplarCap()
	{
		return (getAnimID() == 11 && getAnimTick() > 80) ? (super.getTemplarCap() * 4) : (super.getTemplarCap() * 2);
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.AVERAGE;
	}

	protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		getDataWatcher().addObject(13, Byte.valueOf((byte)0));
		getDataWatcher().addObject(14, Byte.valueOf((byte)0));
		getDataWatcher().addObject(15, Byte.valueOf((byte)0));
		getDataWatcher().addObject(16, Byte.valueOf((byte)0));
	}

	public int getFootStepModifer()
	{
		return isChild() ? 1 : super.getFootStepModifer();
	}

	public boolean isEntityUndead()
	{
		return true;
	}

	public boolean isChild()
	{
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	protected int getExperiencePoints(EntityPlayer player)
	{
		if (isChild())
		{
			experienceValue = ((int)(experienceValue * 2.5F));
		}

		return super.getExperiencePoints(player);
	}

	public void setChild(boolean childZombie)
	{
		getDataWatcher().updateObject(12, Byte.valueOf((byte)(childZombie ? 1 : 0)));
		if (!childZombie)
		{
			AnimationAPI.sendAnimPacket(this, 2);
			AnimationAPI.sendAnimPacket(this, 0);
			setAnimTick(0);
		}
	}

	public boolean isVillager()
	{
		return getDataWatcher().getWatchableObjectByte(13) == 1;
	}

	public void setVillager(boolean villager)
	{
		getDataWatcher().updateObject(13, Byte.valueOf((byte)(villager ? 1 : 0)));
	}

	public boolean isArmed()
	{
		return getDataWatcher().getWatchableObjectByte(15) == 1;
	}

	public void setArmed(boolean armed)
	{
		getDataWatcher().updateObject(15, Byte.valueOf((byte)(armed ? 1 : 0)));
	}

	public boolean isSwordSoft()
	{
		return getDataWatcher().getWatchableObjectByte(16) == 1;
	}

	public void setSwordSoft(boolean armed)
	{
		getDataWatcher().updateObject(16, Byte.valueOf((byte)(armed ? 1 : 0)));
	}

	public double getSpeed()
	{
		return (isChild() ? 0.46D : 0.23D) / 4;
	}

	public boolean canBeHurtByPlayer()
	{
		return (!isArmed()) && (!isEntityInvulnerable());
	}

	public boolean shouldMove()
	{
		return !isStunned && super.shouldMove();
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return false;
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
		entityarrow.setThrowableHeading(d0, d1 + f1, d2, 0.95F, (float)((45 * (p_82196_2_ + 0.1F)) - worldObj.difficultySetting.getDifficultyId() * 5));
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

	public void onLivingUpdate()
	{
		if (motionY > 3.0D)motionY *= 0.8D;
		if (ticksExisted % 80 == 0)
		if (!worldObj.loadedEntityList.isEmpty())
		{
			for (int l1 = 0; l1 < worldObj.loadedEntityList.size(); ++l1)
			{
				Entity entity = (Entity)worldObj.loadedEntityList.get(l1);
				if (entity != null && entity instanceof EntityTitan && !entity.isEntityAlive() && getDistanceToEntity(entity) < 150D && getAnimTick() <= 0 && getAnimID() != 11)
				{
					AnimationAPI.sendAnimPacket(this, 11);
					setAnimID(11);
				}
			}
		}

		switch (getTitanVariant())
		{
			case 2:
			{
				enactEffectAura(3, boundingBox.expand(8, 8, 8));
				break;
			}

			case 3:
			{
				enactEffectAura(0, boundingBox.expand(24, 12, 24));
				break;
			}

			case 4:
			{
				enactEffectAura(4, boundingBox.expand(48, 48, 48));
				break;
			}
		}

		if (!isRiding() && !getWaiting() && !isStunned && getAnimID() == 0)
		{
			titanFly(0.625F * getTitanSizeMultiplier(), 0.125F * getTitanSizeMultiplier(), 0.25D * getTitanSizeMultiplier());
		}

		if (getWaiting())
		{
			AnimationAPI.sendAnimPacket(this, 13);
			EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 16D);
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
				if ((getAnimTick() == 80 || getAnimTick() == 150 || getAnimTick() == 370 || getAnimTick() == 410 || getAnimTick() == 450 || getAnimTick() == 470 || getAnimTick() == 490))
				{
					func_145780_a(0, 0, 0, Blocks.stone);
					playSound("thetitans:titanPress", getSoundVolume(), 1F);
				}
			}
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

		if (getAnimID() == 8)
		{
			if (getAnimTick() > 138)
			isStunned = false;
			else
			isStunned = true;
		}

		if ((getAnimID() == 8) && (getAnimTick() == 4) && (!worldObj.isRemote))
		{
			dropSword();
		}

		if ((getAnimID() == 8) && (getAnimTick() >= 80) && (getAnimTick() <= 100))
		{
			playSound(getLivingSound(), getSoundVolume(), 1.1F);
		}

		if ((getAnimID() == 7) && (getAnimTick() == 122))
		{
			double d8 = 2D * getTitanSizeMultiplier();
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
				playSound("thetitans:titanPress", 100F, 1F);
			}

			for (int l1 = -4; l1 <= 4; l1++)
			{
				for (int i2 = -4; i2 <= 4; i2++)
				{
					for (int j = -2; j <= 2; j++)
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

						if (block == Blocks.netherrack && block == Blocks.gravel && block == Blocks.ice && block == Blocks.glass && block == Blocks.glass_pane)
						{
							worldObj.setBlock(j2, k, l, Blocks.air);
						}

						if (block.getExplosionResistance(this) > 500.0F && block.isOpaqueCube() && !isSwordSoft())
						{
							setAnimTick(0);
							AnimationAPI.sendAnimPacket(this, 8);
							setAnimID(8);
							playSound("random.anvil_land", 20.0F, 0.5F);
							isStunned = true;
						}

						else if (block.getExplosionResistance(this) <= 1.5F && block.isOpaqueCube() && isSwordSoft() && block != Blocks.air && block != Blocks.netherrack && block != Blocks.dirt && block != Blocks.grass && block != Blocks.glass && block != Blocks.glass_pane)
						{
							setAnimTick(0);
							AnimationAPI.sendAnimPacket(this, 8);
							setAnimID(8);
							playSound("random.anvil_land", 20.0F, 0.5F);
							isStunned = true;
						}
					}
				}
			}
		}

		if ((getAnimID() == 2) && (getAnimTick() == 160))
		{
			double d8 = 0.75D * getTitanSizeMultiplier();
			Vec3 vec3 = getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			int y = MathHelper.floor_double(posY);
			int x = MathHelper.floor_double(posX + dx);
			int z = MathHelper.floor_double(posZ + dz);
			for (int l1 = -2; l1 <= 2; l1++)
			{
				for (int i2 = -2; i2 <= 2; i2++)
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

						if (block.getExplosionResistance(this) > 500.0F)
						{
							setSwordSoft(false);
						}

						else if (block.getExplosionResistance(this) <= 500.0F)
						{
							setSwordSoft(true);
						}
					}
				}
			}
		}

		meleeTitan = true;
		if (getAnimID() == 12)
		{
			if (getAttackTarget() != null && getAnimTick() == 55)
			{
				attackEntityWithRangedAttack(getAttackTarget(), 0F);
				for (int i = 0; i < 4 + (2 * worldObj.difficultySetting.getDifficultyId()); ++i)
				attackEntityWithRangedAttack(getAttackTarget(), 1F);
			}
		}

		if (isStunned || deathTicks > 0)
		{
			motionX *= 0.0D;
			motionZ *= 0.0D;
		}

		if ((!AnimationAPI.isEffectiveClient()) && (getAttackTarget() != null) && (!isStunned) && (getAnimID() == 0) && ticksExisted > 5)
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 < getMeleeRange())
			{
				switch (rand.nextInt(7))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 6);
					setAnimID(6);
					break;
					case 1:if (isArmed())
					{
						AnimationAPI.sendAnimPacket(this, 7);
						setAnimID(7);
					}

					else
					{
						if (getRNG().nextInt(4) == 0)
						{
							AnimationAPI.sendAnimPacket(this, 2);
							setAnimID(2);
						}

						else
						{
							AnimationAPI.sendAnimPacket(this, 4);
							setAnimID(4);
						}
					}

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
				switch (rand.nextInt(4))
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
					case 3:if (isArmed())
					{
						AnimationAPI.sendAnimPacket(this, 5);
						setAnimID(5);
					}

					else
					{
						if (getRNG().nextInt(5) == 0)
						{
							AnimationAPI.sendAnimPacket(this, 2);
							setAnimID(2);
						}

						else
						{
							AnimationAPI.sendAnimPacket(this, 5);
							setAnimID(5);
						}
					}

					break;
				}
			}
		}

		if (getAnimID() == 2 && motionY > 0D)motionY = 0.0D;
		if (!isArmed() && getAttackTarget() != null && getAttackTarget() instanceof EntityTitan && getAnimID() == 0)
		{
			AnimationAPI.sendAnimPacket(this, 2);
		}

		super.onLivingUpdate();
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted > 5)
		{
			float f = renderYawOffset * 3.1415927F / 180.0F;
			float f1 = MathHelper.sin(f);
			float f2 = MathHelper.cos(f);
			getTitanSizeMultiplier();
			MathHelper.sin(limbSwing + f1);
			MathHelper.cos(limbSwing + f2);
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
		}

		super.onHitboxUpdate();
	}

	public String getCommandSenderName()
	{
		String addition = "";
		if (isVillager() && isChild())
		addition = ".babyvillager";
		if (!isVillager() && isChild())
		addition = ".baby";
		if (isVillager() && !isChild())
		addition = ".villager";
		if (!isVillager() && !isChild())
		addition = "";
		switch (getTitanVariant())
		{
			case 1:
			return StatCollector.translateToLocal("entity.ZombieTitanJungle.name" + addition);
			case 2:
			return StatCollector.translateToLocal("entity.ZombieTitanBlood.name" + addition);
			case 3:
			return StatCollector.translateToLocal("entity.ZombieTitanRadioactive.name" + addition);
			case 4:
			return StatCollector.translateToLocal("entity.ZombieTitanVoid.name" + addition);
			default:
			return StatCollector.translateToLocal("entity.ZombieTitan.name" + addition);
		}
	}

	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	protected String getLivingSound()
	{
		if (!getWaiting() && getAnimID() != 13)
		playSound("mob.zombie.say", getSoundVolume(), getSoundPitch() - 0.6F);
		return getWaiting() || getAnimID() == 13 ? null : "thetitans:titanZombieLiving";
	}

	protected String getHurtSound()
	{
		playSound("mob.zombie.hurt", getSoundVolume(), getSoundPitch() - 0.6F);
		return "thetitans:titanZombieGrunt";
	}

	protected String getDeathSound()
	{
		playSound("mob.zombie.death", getSoundVolume(), getSoundPitch() - 0.6F);
		return "thetitans:titanZombieDeath";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		playSound(getTitanSizeMultiplier() <= 3F ? "mob.zombie.step" : "thetitans:titanStep", getTitanSizeMultiplier(), isChild() ? 1.5F : 1.0F);
		shakeNearbyPlayerCameras(isChild() ? 2 : 4);
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

	protected void dropSword()
	{
		if (isArmed() && !worldObj.isRemote)
		{
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
				itembomb.setEntityItemStack(new ItemStack(Items.iron_ingot));
				itembomb.setItemCount(16);
				worldObj.spawnEntityInWorld(itembomb);
			}

			setArmed(false);
		}
	}

	protected Item getDropItem()
	{
		return Items.rotten_flesh;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < 16; x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
				entitylargefireball.setPosition(posX, posY + 4D, posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(12000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			if (isArmed())
			{
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
					itembomb.setEntityItemStack(new ItemStack(Items.iron_ingot));
					itembomb.setItemCount(16);
					worldObj.spawnEntityInWorld(itembomb);
				}
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.rotten_flesh));
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

	protected void dropRareDrop(int p_70600_1_)
	{
		switch (rand.nextInt(3))
		{
			case 0:dropItem(Items.iron_ingot, 64);
			break;
			case 1:dropItem(Items.carrot, 64);
			break;
			case 2:dropItem(Items.potato, 64);
		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("IsBaby", isChild());
		tagCompound.setBoolean("IsVillager", isVillager());
		tagCompound.setBoolean("IsArmed", isArmed());
		tagCompound.setBoolean("Stunned", isStunned);
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		setChild(tagCompund.getBoolean("IsBaby"));
		setVillager(tagCompund.getBoolean("IsVillager"));
		setArmed(tagCompund.getBoolean("IsArmed"));
		isStunned = tagCompund.getBoolean("Stunned");
	}

	public void onKillEntity(EntityLivingBase entityLivingIn)
	{
		super.onKillEntity(entityLivingIn);
		if ((entityLivingIn instanceof EntityVillager))
		{
			EntityZombieMinion entityzombie = new EntityZombieMinion(worldObj);
			entityzombie.copyLocationAndAnglesFrom(entityLivingIn);
			worldObj.removeEntity(entityLivingIn);
			entityzombie.onSpawnWithEgg((IEntityLivingData)null);
			entityzombie.setVillager(true);
			if (entityLivingIn.isChild())
			{
				entityzombie.setChild(true);
			}

			worldObj.spawnEntityInWorld(entityzombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);
		}
	}

	public float getEyeHeight()
	{
		return 0.8625F * height;
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		Object p_180482_2_1 = super.onSpawnWithEgg(p_180482_2_);
		setCanPickUpLoot(true);
		setArmed(false);
		if (worldObj.provider instanceof WorldProviderNowhere || worldObj.provider instanceof WorldProviderVoid || worldObj.provider instanceof WorldProviderEnd)
		setTitanVariant(4);
		setWaiting(true);
		if (p_180482_2_1 == null)
		{
			p_180482_2_1 = new GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, worldObj.rand.nextFloat() < 0.05F, null);
		}

		if ((p_180482_2_1 instanceof GroupData))
		{
			GroupData groupdata = (GroupData)p_180482_2_1;
			if (groupdata.field_142046_b)
			{
				setVillager(true);
			}

			if (groupdata.field_142048_a)
			{
				setChild(true);
			}
		}

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

	public double getYOffset()
	{
		return super.getYOffset() - 8.0D;
	}

	public StatBase getAchievement()
	{
		return TitansAchievments.zombietitan;
	}

	protected void inactDeathAction()
	{
		if (!worldObj.isRemote)
		{
			playSound("mob.zombie.death", getSoundVolume(), getSoundPitch());
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
			entitytitan.setSpiritType(6);
		}
	}

	@SuppressWarnings("unchecked")
	protected void onTitanDeathUpdate()
	{
		dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(getTitanHealth(), 0.0F, getTitanMaxHealth())));
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

		if (deathTicks >= 80 && isArmed()) dropSword();
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

	class GroupData implements IEntityLivingData
	{
		public boolean field_142048_a;
		public boolean field_142046_b;
		private GroupData(boolean p_i2348_2_, boolean p_i2348_3_)
		{
			field_142048_a = false;
			field_142046_b = false;
			field_142048_a = p_i2348_2_;
			field_142046_b = p_i2348_3_;
		}

		GroupData(boolean p_i2349_2_, boolean p_i2349_3_, Object p_i2349_4_)
		{
			this(p_i2349_2_, p_i2349_3_);
		}
	}

	public boolean attackZombieFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		else if (isArmored() && source instanceof EntityDamageSourceIndirect)
		{
			return false;
		}

		else if (((source.getEntity() instanceof EntityZombieMinion)) || ((source.getEntity() instanceof EntityZombieTitan)) || ((source.getEntity() instanceof EntityGiantZombieBetter)))
		{
			return false;
		}

		else
		{
			Entity entity = source.getEntity();
			if (((entity instanceof EntityLivingBase)) && (!isEntityInvulnerable()) && (amount > 25.0F))
			{
				List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(100.0D, 100.0D, 100.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if ((entity1 instanceof EntityZombieTitan))
					{
						EntityZombieTitan entitypigzombie = (EntityZombieTitan)entity1;
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

	protected EntityLiving getMinion()
	{
		return new EntityZombieMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}

	protected double getRegen()
	{
		return worldObj.isDaytime() ? super.getRegen() / 3 : super.getRegen();
	}
}


