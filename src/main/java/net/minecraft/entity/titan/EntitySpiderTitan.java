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
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titan.ai.EntityAITitanLookIdle;
import net.minecraft.entity.titan.ai.EntityAITitanWander;
import net.minecraft.entity.titan.ai.EntityAITitanWatchClosest;
import net.minecraft.entity.titan.animation.spidertitan.*;
import net.minecraft.entity.titanminion.EntityCaveSpiderMinion;
import net.minecraft.entity.titanminion.EntitySpiderMinion;
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
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.theTitans.world.WorldProviderNowhere;
import net.minecraft.theTitans.world.WorldProviderVoid;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import thehippomaster.AnimationAPI.AnimationAPI;
import thehippomaster.AnimationAPI.IAnimatedEntity;
public class EntitySpiderTitan	extends EntityTitan	implements IAnimatedEntity, ITitanHitbox
{
	public int damageToLegs;
	public EntityTitanPart head;
	public EntityTitanPart thorax;
	public EntityTitanPart abdomen;
	public EntityTitanPart rightlegs;
	public EntityTitanPart leftlegs;
	public EntitySpiderTitan(World worldIn)
	{
		super(worldIn);
		head = new EntityTitanPart(worldIn, this, "head", 8.0F, 8.0F);
		thorax = new EntityTitanPart(worldIn, this, "thorax", 6.0F, 6.0F);
		abdomen = new EntityTitanPart(worldIn, this, "abdomen", 12.0F, 8.0F);
		rightlegs = new EntityTitanPart(worldIn, this, "rightleg", 12.0F, 8.0F);
		leftlegs = new EntityTitanPart(worldIn, this, "leftleg", 12.0F, 8.0F);
		partArray.add(head);
		partArray.add(thorax);
		partArray.add(abdomen);
		partArray.add(rightlegs);
		partArray.add(leftlegs);
		setSize(1.75F * getTitanSizeMultiplier(), 0.875F * getTitanSizeMultiplier());
		experienceValue = (12000 + getExtraPower() * 500);
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		tasks.addTask(1, new AnimationSpiderTitanCreation(this));
		tasks.addTask(1, new AnimationSpiderTitanDeath(this));
		tasks.addTask(1, new AnimationSpiderTitanAttack3(this));
		tasks.addTask(1, new AnimationSpiderTitanStunned(this));
		tasks.addTask(1, new AnimationSpiderTitanShootWeb(this));
		tasks.addTask(1, new AnimationSpiderTitanShootLightning(this));
		tasks.addTask(1, new AnimationSpiderTitanAntiTitanAttack(this));
		tasks.addTask(1, new AnimationSpiderTitanAttack2(this));
		tasks.addTask(1, new AnimationSpiderTitanAttack1(this));
		tasks.addTask(1, new AnimationSpiderTitanSpit(this));
		tasks.addTask(1, new AnimationSpiderTitanAttack4(this));
		tasks.addTask(6, new EntityAITitanWander(this, 300));
		tasks.addTask(7, new EntityAITitanWatchClosest(this, EntityTitanSpirit.class, 128.0F));
		tasks.addTask(8, new EntityAITitanWatchClosest(this, EntityTitan.class, 128.0F));
		tasks.addTask(9, new EntityAITitanWatchClosest(this, EntityPlayer.class, 64.0F));
		tasks.addTask(10, new EntityAITitanLookIdle(this));
		worldIn.spawnEntityInWorld(head);
		worldIn.spawnEntityInWorld(thorax);
		worldIn.spawnEntityInWorld(abdomen);
		worldIn.spawnEntityInWorld(rightlegs);
		worldIn.spawnEntityInWorld(leftlegs);
	}

	protected void applyEntityAI()
	{
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SpiderTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 16.0F;
	}

	public boolean isArmored()
	{
		return getHealth() <= getMaxHealth() / 4.0F || TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) || TheTitans.isDifficulty(worldObj, TheCore.IMPOSSIBLE);
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return p_70686_1_ != head.getClass() && p_70686_1_ != thorax.getClass() && p_70686_1_ != abdomen.getClass() && p_70686_1_ != rightlegs.getClass() && p_70686_1_ != leftlegs.getClass() && p_70686_1_ != EntityWebShot.class && p_70686_1_ != EntitySpiderMinion.class && (p_70686_1_ == EntityCaveSpiderTitan.class || p_70686_1_ != EntitySpiderTitan.class);
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(25) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (isValidLightLevel());
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.SpiderTitanMinionSpawnrate;
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte)0));
		dataWatcher.addObject(17, Byte.valueOf((byte)0));
	}

	public int getBonusID()
	{
		return dataWatcher.getWatchableObjectByte(17);
	}

	public void setBonusID(int p_70829_1_)
	{
		dataWatcher.updateObject(17, Byte.valueOf((byte)p_70829_1_));
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		if (tagCompund.hasKey("DamageToLegs", 99))
		{
			damageToLegs = tagCompund.getInteger("DamageToLegs");
		}

		if (tagCompund.hasKey("SpawnedBonusID", 99))
		{
			setBonusID(tagCompund.getInteger("SpawnedBonusID"));
		}

		isStunned = tagCompund.getBoolean("Stunned");
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("DamageToLegs", damageToLegs);
		tagCompound.setInteger("SpawnedBonusID", getBonusID());
		tagCompound.setBoolean("Stunned", isStunned);
	}

	public void onUpdate()
	{
		super.onUpdate();
		if (!worldObj.isRemote)
		{
			setBesideClimbableBlock(isCollidedHorizontally);
		}
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	public boolean canBeHurtByPlayer()
	{
		return (isStunned) && (!isEntityInvulnerable());
	}

	public boolean canAttack()
	{
		return false;
	}

	public double getMeleeRange()
	{
		return width + (0.25D * getTitanSizeMultiplier());
	}

	protected String getLivingSound()
	{
		return isStunned || getWaiting() || getAnimID() == 13 ? null : "thetitans:titanSpiderLiving";
	}

	protected String getHurtSound()
	{
		return "thetitans:titanSpiderGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:titanSpiderDeath";
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return false;
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		if (isClient())
		{
			playSound("thetitans:titanStep", 10.0F, 1.5F);
			playSound("thetitans:titanStep", 10.0F, 1.5F);
			shakeNearbyPlayerCameras(4D);
			shakeNearbyPlayerCameras(4D);
		}
	}

	public boolean shouldMove()
	{
		return !isStunned &&super.shouldMove();
	}

	public double getSpeed()
	{
		switch (getTitanVariant())
		{
			case 1:
			return (getBonusID() == 1 ? 0.625D : 0.575D) / 4;
			case 2:
			return (getBonusID() == 1 ? 0.55D : 0.525D) / 4;
			case 3:
			return (getBonusID() == 1 ? 0.6D : 0.575D) / 4;
			case 4:
			return (getBonusID() == 1 ? 0.75D : 0.6D) / 4;
			default:
			return (getBonusID() == 1 ? 0.6D : 0.55D) / 4;
		}
	}

	public boolean isInvisible()
	{
		return getBonusID() == 4;
	}

	public void attackChoosenEntity(Entity damagedEntity, float damage, int knockbackAmount)
	{
		if (getBonusID() == 2)
		{
			damage *= 2.3F;
			knockbackAmount += 2;
			damagedEntity.playSound("thetitans:titanpunch", 10.0F, 1.0F);
		}

		super.attackChoosenEntity(damagedEntity, damage, knockbackAmount);
	}

	public void onLivingUpdate()
	{
		Calendar calendar = worldObj.getCurrentDate();
		if (!(this instanceof EntityCaveSpiderTitan) && !isDead && getTitanVariant() != 4 && getTitanVariant() != 3 && dimension == 0 && ((!worldObj.isDaytime() && worldObj.getCurrentMoonPhaseFactor() != 1.0F) || (calendar.get(2) + 1 == 12 && calendar.get(5) >= 16 && calendar.get(5) <= 26)))
		{
			setTitanVariant(3);
		}

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
				if (getAnimTick() == 1)
				playSound("thetitans:titanBirth", 100.0F, 1.25F);
				if (getAnimTick() == 40)
				playSound("thetitans:titanRumble", 10.0F, 1.0F);
				if (getAnimTick() == 30)
				playSound("thetitans:titanSpiderLiving", getSoundVolume(), 0.8F);
				if (getAnimTick() == 130)
				playSound("thetitans:titanFall", getSoundVolume(), 1F);
				if (getAnimTick() == 135 || getAnimTick() == 155)
				{
					func_145780_a(0, 0, 0, Blocks.stone);
					playSound("thetitans:titanPress", getSoundVolume(), 1F);
				}
			}
		}

		if (getAnimID() == 6 && getAnimTick() <= 100 && getAnimTick() >= 30 && getAnimTick() % 5 == 0 && (getAttackTarget() != null))
		{
			playSound("mob.wither.shoot", 5.0F, 1.0F);
			double d8 = 2D;
			float xfac = MathHelper.sin(renderYawOffset * (float)Math.PI / 180.0F);
			float zfac = MathHelper.cos(renderYawOffset * (float)Math.PI / 180.0F);
			double d0 = (getAttackTarget().posX) - (posX - (double)(xfac * d8));
			double d1 = (getAttackTarget().posY - 1D) - (posY + 1D);
			double d2 = (getAttackTarget().posZ) - (posZ + (double)(zfac * d8));
			MathHelper.sqrt_double(d0 * d0 + d2 * d2);
			EntityWebShot entitysnowball = new EntityWebShot(worldObj, this, d0, d1, d2);
			entitysnowball.posX = posX - (double)(xfac * d8);
			entitysnowball.posY = posY + 1D;
			entitysnowball.posZ = posZ + (double)(zfac * d8);
			worldObj.spawnEntityInWorld(entitysnowball);
		}

		if (getAnimID() == 7 && (getAnimTick() == 68) && (getAttackTarget() != null))
		{
			double d8 = -2.0D;
			Vec3 vec3 = getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			float f = (float)getAttackValue(5.0F);
			int i = getKnockbackAmount();
			attackChoosenEntity(getAttackTarget(), f, i);
			getAttackTarget().motionY += 2.0D;
			worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2.0F, false, false);
			worldObj.newExplosion(this, posX + dx, posY + 8.0D, posZ + dz, 1.0F, false, false);
			getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, f);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + 3.0D, posZ + dz, 0.6F, 0.1F, 0.2F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 0.6F, 0.1F, 0.2F));
			List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(getAttackTarget(), getAttackTarget().boundingBox.expand(6.0D, 3.0D, 6.0D));
			if ((list1 != null) && (!list1.isEmpty()))
			{
				for (int i1 = 0; i1 < list1.size(); i1++)
				{
					Entity entity1 = (Entity)list1.get(i1);
					if (canAttackClass(entity1.getClass()))
					{
						worldObj.newExplosion(this, entity1.posX, entity1.posY, entity1.posZ, 2.0F, false, false);
						attackChoosenEntity(entity1, f, i);
						entity1.attackEntityFrom(DamageSourceExtra.lightningBolt, f);
						worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, entity1.posX, entity1.posY, entity1.posZ));
					}
				}
			}
		}

		if (!worldObj.isRemote && getAnimID() == 2 && (getAttackTarget() != null))
		{
			List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(64.0D, 64.0D, 64.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if ((entity1 instanceof EntityLivingBase) && canAttackClass(entity1.getClass()))
					{
						entity1.hurtResistantTime = 0;
						((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.weakness.id, 100, 1));
					}
				}
			}
		}

		if (!worldObj.isRemote && getAnimID() == 2 && getAnimTick() <= 70 && getAnimTick() >= 60 && (getAttackTarget() != null))
		{
			playSound("mob.wither.shoot", 5.0F, 1.0F);
			attackChoosenEntity(getAttackTarget(), 25F, 0);
			for (int j = 0; j < 300; j++)
			{
				EntityPotion entitypotion = new EntityPotion(worldObj, this, (this instanceof EntityCaveSpiderTitan ? 16484 : (rand.nextInt(10) == 0 ? 16452 : 16490)));
				double d8 = 5D;
				float xfac = MathHelper.sin(renderYawOffset * (float)Math.PI / 180.0F);
				float zfac = MathHelper.cos(renderYawOffset * (float)Math.PI / 180.0F);
				double d0 = (getAttackTarget().posX) - (head.posX - (double)(xfac * d8));
				double d1 = (getAttackTarget().posY) - (head.posY + 4D);
				double d2 = (getAttackTarget().posZ) - (head.posZ + (double)(zfac * d8));
				float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
				entitypotion.setThrowableHeading(d0, d1 + (double)(f1 * 0.2F), d2, 1.6F, 1F + (float)((getAnimTick() * 2) - 60));
				entitypotion.posX = head.posX - (double)(xfac * d8);
				entitypotion.posY = head.posY + 4D;
				entitypotion.posZ = head.posZ + (double)(zfac * d8);
				worldObj.spawnEntityInWorld(entitypotion);
			}
		}

		if (getAnimID() == 7 && getAnimTick() == 68)
		worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX , posY + 3.0D, posZ, 0.6F, 0.1F, 0.2F));
		EntityPlayer player = worldObj.getClosestPlayerToEntity(head, 9D);
		if (player != null && head.posY < player.posY - 7D)
		{
			rotationYawHead += MathHelper.sin(ticksExisted) * 40F;
			rotationPitch -= MathHelper.cos(ticksExisted) * 40F;
		}

		meleeTitan = true;
		if (getAnimID() == 10)
		{
			if ((getAnimTick() == 80) || (getAnimTick() == 210) || (getAnimTick() == 250) || (getAnimTick() == 260))
			{
				shakeNearbyPlayerCameras(10000D);
				playSound("thetitans:titanFall", 20.0F, 1.0F);
				playSound("thetitans:distantLargeFall", 10000.0F, 1.0F);
				collideWithEntities(leftlegs, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftlegs.boundingBox.expand(24.0D, 1.0D, 24.0D)));
				collideWithEntities(rightlegs, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightlegs.boundingBox.expand(24.0D, 1.0D, 24.0D)));
			}

			if (getAnimTick() == 420)
			{
				isStunned = false;
				System.out.print("Done \n");
				setStamina(getMaxStamina());
			}

			else
			{
				setAttackTarget(null);
			}
		}

		if (getAnimID() == 8)
		{
			if (getAnimTick() == 58)
			{
				playSound("thetitans:largeFall", 8.0F, 0.9F);
				playSound("thetitans:titanFall", 10.0F, 1.0F);
			}

			if (getAnimTick() == 60)
			{
				playSound("thetitans:distantLargeFall", 10000.0F, 1.0F);
			}

			if (getAnimTick() == 420)
			{
				isStunned = false;
			}

			else
			{
				setAttackTarget(null);
			}
		}

		if (isStunned)
		{
			setAttackTarget(null);
			AnimationAPI.sendAnimPacket(this, 8);
		}

		if (ticksExisted > 5)
		{
			for (EntityTitanPart part : partArray)
			{
				if (getBonusID() > 0)
				{
					double red = 0.4862745098039216D;
					double green = 0.6862745098039216D;
					double blue = 0.7764705882352941D;
					if (getBonusID() == 2)
					{
						red = 0.5764705882352941D;
						green = 0.1411764705882353D;
						blue = 0.1372549019607843D;
					}

					if (getBonusID() == 3)
					{
						red = 0.803921568627451D;
						green = 0.3607843137254902D;
						blue = 0.6705882352941176D;
					}

					if (getBonusID() == 4)
					{
						red = 0.4980392156862745D;
						green = 0.5137254901960784D;
						blue = 0.5764705882352941D;
					}

					for (int i1 = 0; i1 < 50; i1++)
					worldObj.spawnParticle("mobSpell", part.posX + (rand.nextDouble() - 0.5D) * part.width, part.posY + rand.nextDouble() * part.height, part.posZ + (rand.nextDouble() - 0.5D) * part.width, red, green, blue);
				}
			}
		}

		if (isStunned || deathTicks > 0)
		{
			motionX *= 0.0D;
			motionZ *= 0.0D;
		}

		if ((!AnimationAPI.isEffectiveClient()) && (getAttackTarget() != null) && (!isStunned) && getAnimID() == 0)
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 < getMeleeRange())
			{
				if (getAnimID() == 0)
				{
					switch (rand.nextInt(4))
					{
						case 0:AnimationAPI.sendAnimPacket(this, 3);
						setAnimID(3);
						break;
						case 1:if (getAttackTarget().height >= height + 2F || (posY + getEyeHeight() < getAttackTarget().posY + getAttackTarget().getEyeHeight()))
						{
							AnimationAPI.sendAnimPacket(this, 1);
							setAnimID(1);
						}

						else
						{
							AnimationAPI.sendAnimPacket(this, 9);
							setAnimID(9);
						}

						break;
						case 3:AnimationAPI.sendAnimPacket(this, 4);
						setAnimID(4);
						break;
						default:if (getAttackTarget().height >= height + 2F || (posY + getEyeHeight() < getAttackTarget().posY + getAttackTarget().getEyeHeight()))
						{
							AnimationAPI.sendAnimPacket(this, 1);
							setAnimID(1);
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

			else if (getAnimID() == 0 && (getRNG().nextInt(100) == 0))
			{
				switch (rand.nextInt(3))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 2);
					setAnimID(2);
					break;
					case 1:AnimationAPI.sendAnimPacket(this, 7);
					setAnimID(7);
					break;
					case 2:AnimationAPI.sendAnimPacket(this, 6);
					setAnimID(6);
					break;
				}
			}
		}

		if ((rand.nextInt(100) == 0) && (getAttackTarget() != null) && (!isStunned) && (onGround) && getAnimID() == 0)
		{
			faceEntity(getAttackTarget(), 180.0F, 180.0F);
			double d01 = getAttackTarget().posX - posX;
			double d11 = getAttackTarget().posZ - posZ;
			float f21 = MathHelper.sqrt_double(d01 * d01 + d11 * d11);
			double hor = 2D;
			double ver = 2D;
			motionX = (d01 / f21 * hor * hor + motionX * hor);
			motionZ = (d11 / f21 * hor * hor + motionZ * hor);
			motionY = ver;
			collideWithEntities(rightlegs, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightlegs.boundingBox.expand(6D, 6D, 6D)));
			collideWithEntities(leftlegs, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftlegs.boundingBox.expand(6D, 6D, 6D)));
			if (getDistanceToEntity(getAttackTarget()) < 50D)
			{
				float f11 = (float)getAttackValue(2.0F);
				int i = getKnockbackAmount() * 2;
				attackChoosenEntity(getAttackTarget(), f11, i);
			}
		}

		if (getAttackTarget() != null)
		{
			if ((rand.nextInt(60) == 0) && (canEntityBeSeen(getAttackTarget())))
			{
				int i = MathHelper.floor_double(getAttackTarget().posX + rand.nextDouble() * 2.0D);
				int j = MathHelper.floor_double(getAttackTarget().posY + rand.nextDouble() * 2.0D);
				int k = MathHelper.floor_double(getAttackTarget().posZ + rand.nextDouble() * 2.0D);
				Block block1 = worldObj.getBlock(i, j, k);
				if ((block1.getMaterial() == Material.air) && (worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
				{
					worldObj.setBlock(i, j, k, Blocks.web);
				}

				else
				{
					getAttackTarget().addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 2));
				}
			}
		}

		if (getBonusID() > 4)
		setBonusID(4);
		if (getBonusID() < 0 || this instanceof EntityCaveSpiderTitan)
		setBonusID(0);
		super.onLivingUpdate();
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted > 5)
		{
			setSize(1.75F * getTitanSizeMultiplier(), 0.875F * getTitanSizeMultiplier());
			abdomen.width = rightlegs.width = leftlegs.width = 0.75F * getTitanSizeMultiplier();
			abdomen.height = rightlegs.height = leftlegs.height = head.height = head.width = 0.5F * getTitanSizeMultiplier();
			thorax.height = thorax.width = 0.375F * getTitanSizeMultiplier();
			float f = renderYawOffset * 3.1415927F / 180.0F;
			float f1 = MathHelper.sin(f);
			float f2 = MathHelper.cos(f);
			head.setLocationAndAngles(posX - MathHelper.sin(rotationYawHead * 3.1415927F / 180.0F) * (0.25F * width), posY + (0.3571428571428571F * height) - MathHelper.sin(rotationPitch * 3.1415927F / 180.0F) * (0.2857142857142857F * height), posZ + MathHelper.cos(rotationYawHead * 3.1415927F / 180.0F) * (0.25F * width), 0.0F, 0.0F);
			thorax.setLocationAndAngles(posX, posY + (0.4464285714285714F * height), posZ, 0.0F, 0.0F);
			abdomen.setLocationAndAngles(posX + f1 * (0.3214285714285714F * width), posY + (0.3571428571428571F * height), posZ - f2 * (0.3214285714285714F * width), 0.0F, 0.0F);
			rightlegs.setLocationAndAngles(posX + f2 * (0.3571428571428571F * width), posY, posZ + f1 * (0.3571428571428571F * width), 0.0F, 0.0F);
			leftlegs.setLocationAndAngles(posX - f2 * (0.3571428571428571F * width), posY, posZ - f1 * (0.3571428571428571F * width), 0.0F, 0.0F);
		}

		super.onHitboxUpdate();
	}

	public String getCommandSenderName()
	{
		switch (getTitanVariant())
		{
			case 1:
			return StatCollector.translateToLocal("entity.SpiderTitanAlpha.name");
			case 2:
			return StatCollector.translateToLocal("entity.SpiderTitanMycelium.name");
			case 3:
			return StatCollector.translateToLocal("entity.SpiderTitanLunar.name");
			case 4:
			return StatCollector.translateToLocal("entity.SpiderTitanVoid.name");
			default:
			return StatCollector.translateToLocal("entity.SpiderTitan.name");
		}
	}

	protected Item getDropItem()
	{
		return Items.string;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < 8; x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
				entitylargefireball.setPosition(posX, posY + 4D, posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(this instanceof EntityCaveSpiderTitan ? 3000 : 4000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.string));
				itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.coal));
				itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.emerald));
				itembomb.setItemCount(8 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
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
				itembomb.setItemCount(24 + rand.nextInt(24 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.web));
				itembomb.setItemCount(12 + rand.nextInt(12 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.mossy_cobblestone));
				itembomb.setItemCount(12 + rand.nextInt(12 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 3; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.leather));
				itembomb.setItemCount(12 + rand.nextInt(12 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.spider_eye));
				itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.fermented_spider_eye));
				itembomb.setItemCount(12 + rand.nextInt(12 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			if ((rand.nextInt(2) == 0 || rand.nextInt(1 + p_70628_2_) > 0))
			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanItems.harcadiumNugget));
				itembomb.setItemCount(1 + rand.nextInt(3 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}
	}

	public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
	}

	public void setInWeb() 
	{
		 
	}


	public boolean isPotionApplicable(PotionEffect p_70687_1_)
	{
		return p_70687_1_.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(p_70687_1_);
	}

	public boolean isBesideClimbableBlock()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
	}

	public void setBesideClimbableBlock(boolean p_70839_1_)
	{
		byte b0 = dataWatcher.getWatchableObjectByte(16);
		if (p_70839_1_)
		{
			b0 = (byte)(b0 | 0x1);
		}

		else
		{
			b0 = (byte)(b0 & 0xFFFFFFFE);
		}

		dataWatcher.updateObject(16, Byte.valueOf(b0));
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		Object p_180482_2_1 = super.onSpawnWithEgg(p_180482_2_);
		setWaiting(true);
		if (worldObj.rand.nextInt(10) == 0 || TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE))
		{
			setBonusID(rand.nextInt(5));
		}

		if (worldObj.provider instanceof WorldProviderNowhere || worldObj.provider instanceof WorldProviderVoid || worldObj.provider instanceof WorldProviderEnd)
		setTitanVariant(this instanceof EntityCaveSpiderTitan ? 5 : 4);
		return (IEntityLivingData)p_180482_2_1;
	}

	public float getEyeHeight()
	{
		return 0.7428571428571429F * height;
	}

	protected void updateAITasks()
	{
		if (getBonusID() == 3)
		{
			heal(2F);
			for (int u = 0; u < 1 + rand.nextInt(10); u++)
			heal(2F);
		}

		super.updateAITasks();
	}

	public StatBase getAchievement()
	{
		if ((this instanceof EntityCaveSpiderTitan))
		{
			return TitansAchievments.cavespidertitan;
		}

		if ((riddenByEntity != null) && ((riddenByEntity instanceof EntitySkeletonTitan)))
		{
			return TitansAchievments.spiderjockeytitan;
		}

		return TitansAchievments.spidertitan;
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
		setTitanHealth(0.0F);
		setHealth(0.0F);
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
			float f = (rand.nextFloat() - 0.5F) * 24.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 12.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 24.0F;
			worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}

		if (getInvulTime() >= getThreashHold())
		{
			setDead();
		}
	}

	protected void inactDeathAction()
	{
		if ((!worldObj.isRemote))
		{
			playSound("mob.spider.death", getSoundVolume(), getSoundPitch());
			if (worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
			{
				dropFewItems(true, 0);
				dropEquipment(true, 0);
				dropRareDrop(1);
			}

			if (this instanceof EntityCaveSpiderTitan)
			{
				EntityTitanSpirit entitytitan = new EntityTitanSpirit(worldObj);
				entitytitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				worldObj.spawnEntityInWorld(entitytitan);
				entitytitan.setVesselHunting(false);
				entitytitan.setSpiritType(2);
			}

			else
			{
				EntityTitanSpirit entitytitan = new EntityTitanSpirit(worldObj);
				entitytitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				worldObj.spawnEntityInWorld(entitytitan);
				entitytitan.setVesselHunting(false);
				entitytitan.setSpiritType(3);
			}
		}
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		else if (this instanceof EntityCaveSpiderTitan ? (source.getEntity() instanceof EntityCaveSpiderMinion || source.getEntity() instanceof EntityCaveSpiderTitan) : (source.getEntity() instanceof EntitySpiderMinion || source.getEntity() instanceof EntitySpiderTitan && !(source.getEntity() instanceof EntityCaveSpiderTitan)))
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
					if (entity1.getClass() == EntitySpiderTitan.class)
					{
						EntitySpiderTitan entitypigzombie = (EntitySpiderTitan)entity1;
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
		if (p_70965_1_ == head)
		amount *= 2F;
		if (source.getEntity() != null && source.getEntity() instanceof EntityPlayer && source.canHarmInCreative() && (damageToLegs < 8) && (!isStunned) && ((p_70965_1_ == leftlegs) || (p_70965_1_ == rightlegs)))
		{
			attackEntityFrom((new DamageSource("other")).setDamageBypassesArmor().setDamageIsAbsolute(), 100F);
			setAttackTarget((EntityLivingBase) source.getEntity());
			setRevengeTarget((EntityLivingBase) source.getEntity());
			setStamina(getStamina() - (getMaxStamina() / 100.0F));
			if (getStamina() <= 0.0D)
			{
				playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
			}
		}

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
		return new EntitySpiderMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}

	protected int recoveryTime()
	{
		return 660;
	}
}


