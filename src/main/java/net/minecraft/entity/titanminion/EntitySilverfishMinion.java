package net.minecraft.entity.titanminion;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titan.EntityEnderColossusCrystal;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.entity.titan.EntityWitherzilla;
import net.minecraft.entity.titan.EntityWitherzillaMinion;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.entity.titan.ITitan;
import net.minecraft.entity.titan.ai.EntityAIBreakDoorMinion;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class EntitySilverfishMinion extends EntitySilverfish implements IRangedAttackMob, ITemplar
{
	public EntityLiving master;
	public boolean isMasterSubdued;
	public int randomSoundDelay;
	private int attackPattern;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 10, 64F);
	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	public EntityLiving entityToHeal;
	public IEntitySelector allowPlayerPresence = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return ((p_179983_1_ instanceof EntityWitherzilla)) || ((p_179983_1_ instanceof EntityWitherzillaMinion)) || ((p_179983_1_ instanceof EntityAnimal)) || ((p_179983_1_ instanceof EntityGolem)) || ((p_179983_1_ instanceof EntityAgeable)) || ((p_179983_1_ instanceof EntityPlayer)) || ((p_179983_1_ instanceof EntityAmbientCreature)) || ((p_179983_1_ instanceof EntityWaterMob)) || ((p_179983_1_ instanceof EntityTameable)) || ((!(p_179983_1_ instanceof EntityDragon)) && (!(p_179983_1_ instanceof EntityBlaze)) && (!(p_179983_1_ instanceof EntityCaveSpider)) && (!(p_179983_1_ instanceof EntityCreeper)) && (!(p_179983_1_ instanceof EntityEnderman)) && (!(p_179983_1_ instanceof EntityPigZombie)) && (!(p_179983_1_ instanceof EntitySilverfish)) && (!(p_179983_1_ instanceof EntitySkeleton)) && (!(p_179983_1_ instanceof EntitySpider)) && (!(p_179983_1_ instanceof EntityWither)) && (!(p_179983_1_ instanceof EntityZombie)) && (!(p_179983_1_ instanceof EntityTitan)) && (!(p_179983_1_ instanceof EntityGhast)) && (!(p_179983_1_ instanceof EntityMagmaCube)) && (!(p_179983_1_ instanceof EntitySlime)) && (!(p_179983_1_ instanceof IMinion)) && (!(p_179983_1_ instanceof EntityTitanSpirit)) && (!(p_179983_1_ instanceof EntityEnderColossusCrystal)) && (!(p_179983_1_ instanceof EntityPlayer)));
		}
	};
	
	public EntitySilverfishMinion(World worldIn)
	{
		super(worldIn);
		getNavigator().setBreakDoors(true);
		getNavigator().setEnterDoors(true);
		getNavigator().setCanSwim(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityWitherSkull.class, 2.0F, 1.2D, 1.75D));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityTitanSpirit.class, 48.0F, 1.5D, 1.5D));
		this.tasks.addTask(0, new EntityAIBreakDoorMinion(this));
		this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.2D));
		this.tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, true));
		this.tasks.addTask(7, new EntitySilverfishMinion.EntityAIWander(this, 0.8D));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		if (this.isMasterSubdued)
		{
			this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, true, this.allowPlayerPresence));

		}

		 else 
		{

			if (TheTitans.TitansFFAMode)
			this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SilverfishTitanSorter));
			else
			this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
		}

		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
	}

	public void setDead()
	{
		super.setDead();
		if ((this.master != null) && ((this.master instanceof EntityTitan)))
		{
			((EntityTitan)this.master).retractMinionNumFromType(getMinionType());
		}
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	public boolean isPotionApplicable(PotionEffect p_70687_1_)
	{
		return p_70687_1_.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(p_70687_1_);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntitySilverfishMinion.class) && (p_70686_1_ != EntitySilverfishTitan.class);
	}

	protected void fall(float p_70069_1_)
	{
		if (this.getMinionTypeInt() != 4)
		super.fall(p_70069_1_);
		this.moveForward = 0F;
		this.moveStrafing = 0F;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(19, Integer.valueOf(0));
	}

	public void setCombatTask()
	{
		this.tasks.removeTask(this.aiArrowAttack);
		if (this.attackPattern == 0 && this.getMinionTypeInt() == 4)
		{
			this.tasks.addTask(0, this.aiArrowAttack);
		}
	}

	protected String getLivingSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanSilverfishLiving" : "mob.silverfish.say";
	}

	protected String getHurtSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanSilverfishGrunt" : "mob.silverfish.hit";
	}

	protected String getDeathSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanSilverfishDeath" : "mob.silverfish.kill";
	}

	protected float getSoundPitch()
	{
		return this.getMinionTypeInt() == 4 ? super.getSoundPitch() + 0.3F : super.getSoundPitch();
	}

	protected float applyPotionDamageCalculations(DamageSource p_70672_1_, float p_70672_2_)
	{
		if (this.getMinionTypeInt() == 4)
		{
			p_70672_2_ = super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);
			if (p_70672_1_.getEntity() == this)
			{
				p_70672_2_ = 0.0F;
			}

			if (p_70672_1_.isMagicDamage())
			{
				p_70672_2_ = (float)(p_70672_2_ * 0.15D);
			}

			return p_70672_2_;
		}

		else
		{
			return super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);
		}
	}

	public int getTotalArmorValue()
	{
		switch (this.getMinionTypeInt())
		{
			case 1:
			return 2;
			case 2:
			return 15;
			case 3:
			return 18;
			case 4:
			return 22;
			default:
			return 0;
		}
	}

	public int getMinionTypeInt()
	{
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	public EnumMinionType getMinionType()
	{
		switch (this.getMinionTypeInt())
		{
			case 1:
			return EnumMinionType.PRIEST;
			case 2:
			return EnumMinionType.ZEALOT;
			case 3:
			return EnumMinionType.BISHOP;
			case 4:
			return EnumMinionType.TEMPLAR;
			default:
			return EnumMinionType.LOYALIST;
		}
	}

	public void setMinionType(int miniontype)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(miniontype));
		switch (miniontype)
		{
			case 1:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
				this.tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
				this.setHealth(24F);
				this.experienceValue = 10;
				break;
			}

			case 2:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(72D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.375D);
				this.setHealth(72F);
				this.experienceValue = 50;
				break;
			}

			case 3:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(216D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
				this.setHealth(216F);
				this.isImmuneToFire = true;
				this.experienceValue = 250;
				break;
			}

			case 4:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(648D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(13.0D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
				this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				this.setHealth(648F);
				this.isImmuneToFire = true;
				this.experienceValue = 1250;
				break;
			}

			case 5:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1944D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(21.0D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.625D);
				this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				this.setHealth(1944F);
				this.isImmuneToFire = true;
				this.experienceValue = 6250;
				break;
			}

			case 6:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5832D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(34.0D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
				this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				this.setHealth(5832F);
				this.isImmuneToFire = true;
				this.experienceValue = 31250;
				break;
			}

			default:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
				this.setHealth(12F);
				this.experienceValue = 7;
			}
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		if (super.attackEntityAsMob(p_70652_1_))
		{
			if (p_70652_1_ instanceof EntityLivingBase && this.getMinionTypeInt() >= 3)
			{
				byte b0 = 10;
				if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					b0 = 20;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					b0 = 30;
				}

				if (b0 > 0)
				{
					if (worldObj.isRemote)
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, b0 * 20, 0));
				}
			}

			return true;
		}

		else
		{
			return false;
		}
	}

	public void onLivingUpdate()
	{
		if (this.getMinionTypeInt() == 1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
			this.experienceValue = 15;
		}

		else if (this.getMinionTypeInt() == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.experienceValue = 100;
		}

		else if (this.getMinionTypeInt() == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
			this.isImmuneToFire = true;
			this.experienceValue = 200;
		}

		else if (this.getMinionTypeInt() == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.isImmuneToFire = true;
			this.experienceValue = 1000;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
			this.experienceValue = 6;
		}

		if (this.isEntityAlive() || this.getMinionTypeInt() != 4)
		super.onLivingUpdate();
		if (this.getMinionTypeInt() == 3)
		{
			if (this.rand.nextInt(120) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntitySilverfishMinion entitychicken = new EntitySilverfishMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(0);
				this.worldObj.spawnEntityInWorld(entitychicken);
			}

			if (this.rand.nextInt(240) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntitySilverfishMinion entitychicken = new EntitySilverfishMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(1);
				this.worldObj.spawnEntityInWorld(entitychicken);
			}
		}

		if (this.onGround && this.isCollidedHorizontally)
		this.jump();
		if (this.getMinionTypeInt() == 4)
		{
			if (this.ticksExisted % 40 == 0)
			heal(1F);
			if (this.worldObj.rand.nextInt(150) == 1)
			heal(2.0F);
			if (this.worldObj.rand.nextInt(100) == 1 && getHealth() < this.getMaxHealth() * 0.75)
			heal(2.0F);
			if (this.worldObj.rand.nextInt(35) == 1 && getHealth() < this.getMaxHealth() * 0.5)
			heal(5.0F);
			if (this.worldObj.rand.nextInt(30) == 1 && getHealth() < this.getMaxHealth() * 0.25)
			heal(5.0F);
			if (this.worldObj.rand.nextInt(30) == 1 && getHealth() < this.getMaxHealth() * 0.05)
			heal(200.0F);
			if ((!this.onGround) && (this.motionY < 0.0D))
			{
				this.motionY *= 0.6D;
			}

			if (this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				if (this.rand.nextInt(60) == 0)
				{
					EntitySilverfishMinion entitychicken = new EntitySilverfishMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(0);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(120) == 0)
				{
					EntitySilverfishMinion entitychicken = new EntitySilverfishMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(1);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(240) == 0)
				{
					EntitySilverfishMinion entitychicken = new EntitySilverfishMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(2);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}
			}

			if ((this.worldObj.isRemote) && (!this.onGround))
			{
				for (int i = 0; i < 3; i++)
				{
					this.worldObj.spawnParticle("explode", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
				}
			}

			if ((this.rand.nextInt(60) == 0) && (getAttackTarget() != null))
			{
				setCombatTask();
				if (!this.onGround)
				{
					this.attackPattern = 0;
				}

				else
				{
					this.attackPattern = 1;
				}
			}

			--this.heightOffsetUpdateTime;
			if (this.heightOffsetUpdateTime <= 0)
			{
				jump();
				this.heightOffsetUpdateTime = 100;
				this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
				this.attackPattern = 0;
			}

			EntityLivingBase entitylivingbase = getAttackTarget();
			if ((this.attackPattern == 0) && (entitylivingbase != null) && !this.worldObj.isRemote)
			{
				if ((entitylivingbase.posY + entitylivingbase.getEyeHeight() > this.posY + getEyeHeight() + this.heightOffset))
				{
					this.motionY += (0.4D - this.motionY);
					this.isAirBorne = true;
				}

				this.getLookHelper().setLookPositionWithEntity(entitylivingbase, 180F, 40F);
				double d0 = entitylivingbase.posX - this.posX;
				double d1 = entitylivingbase.posZ - this.posZ;
				double d3 = d0 * d0 + d1 * d1;
				if (d3 > (entitylivingbase.width * entitylivingbase.width) + (this.width * this.width) + 16D)
				{
					double d5 = MathHelper.sqrt_double(d3);
					this.motionX += (d0 / d5 * 0.6D - this.motionX);
					this.motionZ += (d1 / d5 * 0.6D - this.motionZ);
				}
			}

			if (this.isEntityAlive() && (!this.worldObj.isRemote) && (this.rand.nextInt(1000) == 0) && (getAttackTarget() != null) && (getHealth() < getMaxHealth() / 2.0F) && (this.master == null))
			{
				for (int i = 0; i < 16; i++)
				{
					this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("flame", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
				}

				playSound("thetitans:titanland", 10000.0F, 1.0F);
				TransformEntity(this);
			}

			if (this.onGround)
			{
				this.isAirBorne = false;
			}

			List<?> list11 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(8D, 8D, 8D));
			if (!this.worldObj.isRemote && list11 != null && !list11.isEmpty() && this.ticksExisted % (this.getHealth() < this.getMaxHealth() / 2 ? 40 : 160) == 0)
			{
				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 8F, false);
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (entity != null && entity instanceof EntityLivingBase && this.canAttackClass(entity.getClass()))
					{
						entity.motionY += rand.nextDouble();
						if (worldObj.isRemote)
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, 10, 1));
						this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1017, (int)entity.posX, (int)entity.posY, (int)entity.posZ, 0);
					}
				}
			}
		}

		if (this.getMinionTypeInt() == 2)
		{
			if (getAttackTarget() != null)
			{
				double d0 = getDistanceSqToEntity(getAttackTarget());
				if (d0 < 4.0D)
				{
					swingItem();
					attackEntityAsMob(getAttackTarget());
				}

				if ((this.rand.nextInt(40) == 0) && (this.onGround) && (d0 < 256.0D) && (getAttackTarget().posY > this.posY + 3.0D))
				{
					addPotionEffect(new PotionEffect(Potion.jump.id, 60, 7));
					faceEntity(getAttackTarget(), 180.0F, 180.0F);
					double d01 = getAttackTarget().posX - this.posX;
					double d1 = getAttackTarget().posZ - this.posZ;
					float f2 = MathHelper.sqrt_double(d01 * d01 + d1 * d1);
					jump();
					this.motionX = (d01 / f2 * 0.75D * 0.75D + this.motionX * 0.75D);
					this.motionZ = (d1 / f2 * 0.75D * 0.75D + this.motionZ * 0.75D);
				}
			}
		}

		if (this.getMinionTypeInt() == 1)
		{
			if ((this.ticksExisted % 40 == 0) && (this.entityToHeal != null))
			{
				if (this.entityToHeal.getHealth() < this.entityToHeal.getMaxHealth())
				{
					swingItem();
					faceEntity(this.entityToHeal, 180.0F, getVerticalFaceSpeed());
					this.entityToHeal.heal(4.0F);
					playSound("mob.wither.shoot", 1.0F, 3.0F);
				}

				else
				{
					this.entityToHeal = null;
				}
			}
		}

		if ((getAttackTarget() != null) && (!getAttackTarget().isEntityAlive()))
		{
			setAttackTarget(null);
		}

		if (this.master != null)
		{
			if (getDistanceSqToEntity(this.master) > 2304.0D)
			{
				getMoveHelper().setMoveTo(this.master.posX, this.master.posY, this.master.posZ, 2.0D);
			}

			if ((this.master.getAttackTarget() != null))
			{
				if (this.master.getAttackTarget().height < 6F)
				this.setAttackTarget(this.master.getAttackTarget());
				else
				this.getLookHelper().setLookPositionWithEntity(this.master.getAttackTarget(), 10F, 40F);
			}

			if ((((EntitySilverfishTitan)this.master).isSubdued) && ((getAttackTarget() instanceof EntityPlayer)))
			{
				setAttackTarget(null);
				this.isMasterSubdued = true;
			}
		}

		else
		{
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(100.0D, 100.0D, 100.0D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i11 = 0; i11 < list.size(); i11++)
				{
					Entity entity = (Entity)list.get(i11);
					if ((entity != null) && ((entity instanceof EntitySilverfishTitan)))
					{
						this.master = ((EntitySilverfishTitan)entity);
					}
				}
			}
		}
	}

	public void TransformEntity(Entity entity)
	{
		entity.worldObj.newExplosion(entity, entity.posX, entity.posY, entity.posZ, 18.0F, true, entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		EntitySilverfishTitan entitytitan = new EntitySilverfishTitan(entity.worldObj);
		entitytitan.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0F);
		entity.setDead();
		entitytitan.func_82206_m();
		entity.worldObj.spawnEntityInWorld(entitytitan);
	}

	public String getCommandSenderName()
	{
		switch (this.getMinionTypeInt())
		{
			case 1:
			return StatCollector.translateToLocal("entity.SilverfishPriest.name");
			case 2:
			return StatCollector.translateToLocal("entity.SilverfishZealot.name");
			case 3:
			return StatCollector.translateToLocal("entity.SilverfishBishop.name");
			case 4:
			return StatCollector.translateToLocal("entity.SilverfishTemplar.name");
			default:
			return StatCollector.translateToLocal("entity.SilverfishLoyalist.name");
		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("MinionType", this.getMinionTypeInt());
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		this.setMinionType(tagCompund.getInteger("MinionType"));
	}

	protected Item getDropItem()
	{
		return Items.paper;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		Item item = getDropItem();
		int j = this.rand.nextInt(2);
		if (p_70628_2_ > 0)
		{
			j += this.rand.nextInt(p_70628_2_ + 1);
		}

		for (int k = 0; k < j; k++)
		{
			dropItem(item, 1);
		}

		if ((this.rand.nextInt(30) == 0) || (this.rand.nextInt(1 + p_70628_2_) > 0))
		{
			entityDropItem(new ItemStack(Blocks.stone), 0.0F);
		}

		if (this.getMinionTypeInt() >= 1)
		{
			j = this.rand.nextInt(2);
			if (p_70628_2_ > 0)
			{
				j += this.rand.nextInt(p_70628_2_ + 1);
			}

			for (int k = 0; k < j; k++)
			{
				dropItem(Items.experience_bottle, 1);
			}

			if (this.getMinionTypeInt() >= 2)
			{
				j = this.rand.nextInt(2);
				if (p_70628_2_ > 0)
				{
					j += this.rand.nextInt(p_70628_2_ + 1);
				}

				for (int k = 0; k < j; k++)
				{
					dropItem(Items.golden_apple, 1);
				}

				if (this.getMinionTypeInt() >= 3)
				{
					j = this.rand.nextInt(2);
					if (p_70628_2_ > 0)
					{
						j += this.rand.nextInt(p_70628_2_ + 1);
					}

					for (int k = 0; k < j; k++)
					{
						switch (this.rand.nextInt(5))
						{
							case 0:dropItem(Items.emerald, 1);
							break;
							case 1:dropItem(Items.diamond, 1);
							break;
							case 2:dropItem(Items.gold_ingot, 1);
							break;
							case 3:dropItem(Items.gold_ingot, 1);
							break;
							case 4:dropItem(Items.gold_ingot, 1);
						}
					}

					if (this.getMinionTypeInt() >= 4)
					{
						if (this.rand.nextInt(5) == 0)
						{
							entityDropItem(new ItemStack(TitanItems.pleasantBladeSeed), 0.0F);
						}

						if (this.rand.nextInt(100) == 0)
						{
							entityDropItem(new ItemStack(TitanItems.malgrumSeeds), 0.0F);
						}

						j = 2 + this.rand.nextInt(5);
						if (p_70628_2_ > 0)
						{
							j += this.rand.nextInt(p_70628_2_ + 1);
						}

						for (int k = 0; k < j; k++)
						{
							switch (this.rand.nextInt(3))
							{
								case 0:dropItem(Items.emerald, 1);
								break;
								case 1:dropItem(Items.diamond, 1);
								break;
								case 2:dropItem(Items.gold_ingot, 1);
							}
						}
					}
				}
			}
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable() || isFriendly(source.getEntity()) || (getMinionTypeInt() >= 4 && source == DamageSourceExtra.radiation))
		return false;
		if (source.getEntity() != null)
		{
			int difficulty = TheTitans.getDifficulty(worldObj);
			if (getMinionType() == EnumMinionType.ZEALOT && moveStrafing == 0F)
			{
				boolean shouldDodge;
				switch (difficulty)
				{
					case 0: shouldDodge = false; break;
					case 1: shouldDodge = false; break;
					case 2: shouldDodge = Maths.chance(10); break;
					case 3: shouldDodge = Maths.chance(33); break;
					case 4: shouldDodge = Maths.chance(50); break;
					case 5: shouldDodge = Maths.chance(75); break;
					default: shouldDodge = true;
				}

				if (shouldDodge)
				{
					renderYawOffset = rotationYaw = rotationYawHead;
					playSound("thetitans:titanSwing", 1F, 2F);
					switch (rand.nextInt(3))
					{
						case 0:
						{
							moveForward = -2F;
							moveFlying(0F, -2F, 0.99F);
							moveStrafing = 0.01F;
							break;
						}

						case 1:
						{
							moveStrafing = 1F;
							moveFlying(1F, 0F, 0.25F);
							break;
						}

						case 2:
						{
							moveStrafing = -1F;
							moveFlying(-1F, 0F, 0.25F);
						}
					}

					jump();
					return false;
				}
			}

			Entity entity = source.getEntity();
			if ((entity instanceof EntityLivingBase))
			{
				List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0D, 32.0D, 32.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if ((entity1 instanceof EntitySilverfishMinion))
					{
						EntitySilverfishMinion entitypigzombie = (EntitySilverfishMinion)entity1;
						entitypigzombie.setAttackTarget((EntityLivingBase)entity);
						entitypigzombie.setRevengeTarget((EntityLivingBase)entity);
						entitypigzombie.randomSoundDelay = this.rand.nextInt(40);
					}

					setAttackTarget((EntityLivingBase)entity);
					setRevengeTarget((EntityLivingBase)entity);
					randomSoundDelay = this.rand.nextInt(40);
				}
			}
		}

		return super.attackEntityFrom(source, amount);
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		this.swingItem();
		if (getDistanceSqToEntity(p_82196_1_) < (p_82196_1_.width * p_82196_1_.width) + 36D)
		attackEntityAsMob(p_82196_1_);
		else
		switch (this.rand.nextInt(4))
		{
			case 0:EntityArrow entityarrow = new EntityArrow(this.worldObj, this, p_82196_1_, 1.6F, 1.0F);
			entityarrow.setIsCritical(true);
			entityarrow.setDamage(p_82196_2_ * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting.getDifficultyId() * 0.11F);
			playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
			this.worldObj.spawnEntityInWorld(entityarrow);
			break;
			case 1:EntityPotion entitypotion = new EntityPotion(this.worldObj, this, 32732);
			if (p_82196_1_.isEntityUndead())
			{
				entitypotion.setPotionDamage(32725);
			}

			double d0 = p_82196_1_.posY + 0.5D;
			entitypotion.rotationPitch -= -20.0F;
			double d1 = p_82196_1_.posX + p_82196_1_.motionX - this.posX;
			double d2 = d0 - this.posY;
			double d3 = p_82196_1_.posZ + p_82196_1_.motionZ - this.posZ;
			float f1 = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
			entitypotion.setThrowableHeading(d1, d2 + f1 * 0.2F, d3, 1.0F, f1 / 20.0F);
			this.worldObj.spawnEntityInWorld(entitypotion);
			break;
			case 2:double d01 = getDistanceSqToEntity(p_82196_1_);
			double d11 = p_82196_1_.posX - this.posX;
			double d21 = p_82196_1_.boundingBox.minY + p_82196_1_.height / 2.0F - (this.posY + p_82196_1_.height / 2.0F);
			double d31 = p_82196_1_.posZ - this.posZ;
			float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d01)) * 0.1F;
			this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
			EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.worldObj, this, d11 + getRNG().nextGaussian() * f, d21, d31 + getRNG().nextGaussian() * f);
			entitysmallfireball.posY = (this.posY + this.height + 0.4D);
			this.worldObj.spawnEntityInWorld(entitysmallfireball);
			break;
			case 3:p_82196_1_.attackEntityFrom(DamageSource.magic, 10F);
			playSound("mob.wither.shoot", 1.0F, 3.0F);
			int i = MathHelper.floor_double(p_82196_1_.posX + this.rand.nextDouble());
			int j = MathHelper.floor_double(p_82196_1_.posY + this.rand.nextDouble());
			int k = MathHelper.floor_double(p_82196_1_.posZ + this.rand.nextDouble());
			Block block = this.worldObj.getBlock(i, j, k);
			if ((block.getMaterial() == Material.air))
			{
				this.worldObj.setBlock(i, j, k, Blocks.monster_egg);
			}
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		Object p_180482_2_1 = super.onSpawnWithEgg(p_180482_2_);
		if (!this.worldObj.isRemote)
		{
			this.setMinionType(0);
			EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, -1D);
			if (player != null)
			if (rand.nextInt(2) == 0 && player.getTotalArmorValue() >= 15)
			{
				setMinionType(1);
				if (rand.nextInt(4) == 0 && player.getTotalArmorValue() >= 20)
				{
					setMinionType(2);
					if (rand.nextInt(6) == 0 && player.getTotalArmorValue() >= 40)
					{
						setMinionType(3);
						if (rand.nextInt(10) == 0 && player.getTotalArmorValue() >= 50)
						{
							setMinionType(4);
						}
					}
				}
			}
		}

		return (IEntityLivingData)p_180482_2_1;
	}

	protected void updateAITasks()
	{
		this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
		if ((this.randomSoundDelay > 0) && (--this.randomSoundDelay == 0))
		{
			playSound(getHurtSound(), getSoundVolume(), getSoundPitch() + 0.25F);
		}

		int i;
		int j;
		int k;
		int i1;
		if (this.hurtResistantTime == 1)
		{
			i = MathHelper.floor_double(this.posX);
			j = MathHelper.floor_double(this.posY);
			k = MathHelper.floor_double(this.posZ);
			boolean flag = false;
			for (int l = 0; !flag && l <= 10 && l >= -10; l = l <= 0 ? 1 - l : 0 - l)
			{
				for (i1 = 0; !flag && i1 <= 10 && i1 >= -10; i1 = i1 <= 0 ? 1 - i1 : 0 - i1)
				{
					for (int j1 = 0; !flag && j1 <= 10 && j1 >= -10; j1 = j1 <= 0 ? 1 - j1 : 0 - j1)
					{
						if (this.worldObj.getBlock(i + i1, j + l, k + j1) == Blocks.monster_egg)
						{
							if (!this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
							{
								int k1 = this.worldObj.getBlockMetadata(i + i1, j + l, k + j1);
								ImmutablePair<?, ?> immutablepair = BlockSilverfish.func_150197_b(k1);
								this.worldObj.setBlock(i + i1, j + l, k + j1, (Block)immutablepair.getLeft(), ((Integer)immutablepair.getRight()).intValue(), 3);
							}

							else
							{
								this.worldObj.func_147480_a(i + i1, j + l, k + j1, false);
							}

							EntitySilverfishMinion entitysilverfish = new EntitySilverfishMinion(this.worldObj);
							entitysilverfish.setLocationAndAngles(i + i1 + 0.5D, j + l, k + j1 + 0.5D, 0.0F, 0.0F);
							entitysilverfish.onSpawnWithEgg(null);
							if (!this.worldObj.isRemote)
							this.worldObj.spawnEntityInWorld(entitysilverfish);
							this.worldObj.createExplosion(entitysilverfish, entitysilverfish.posX, entitysilverfish.posY, entitysilverfish.posZ, 2F, false);
							entitysilverfish.spawnExplosionParticle();
							this.worldObj.setBlockToAir(i + i1, j + l, k + j1);
							if (this.rand.nextBoolean())
							{
								flag = true;
								break;
							}
						}
					}
				}
			}
		}

		if (this.isCollidedHorizontally && this.master != null)
		this.motionY = 0.2D;
		super.updateAITasks();
	}

	public int deathTicks;
	public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_)
	{
		if (deathTicks > 0)
		{
			super.moveEntity(0.0D, 0.10000000149011612D, 0.0D);
		}

		else
		{
			super.moveEntity(p_70091_1_, p_70091_3_, p_70091_5_);
		}
	}

	protected void onDeathUpdate()
	{
		if (this.getMinionTypeInt() == 4)
		{
			--this.ticksExisted;
			++this.deathTicks;
			if (this.master != null)
			{
				double mx = this.posX - master.posX;
				double my = (this.posY + this.getEyeHeight()) - (master.posY + master.getEyeHeight());
				double mz = this.posZ - master.posZ;
				short short1 = (short) (getDistanceToEntity(master) * 2);
				for (int f = 0; f < short1; f++)
				{
					double d9 = f / (short1 - 1.0D);
					double d6 = this.posX + mx * -d9;
					double d7 = (this.posY + this.getEyeHeight()) + my * -d9;
					double d8 = this.posZ + mz * -d9;
					this.worldObj.spawnParticle("fireworksSpark", d6, d7, d8, master.motionX, master.motionY + 0.2D, master.motionZ);
				}
			}

			if (!this.worldObj.isRemote)
			{
				if (this.deathTicks > 150 && this.deathTicks % 5 == 0)
				{
					this.dropFewItems(true, 0);
				}

				if (this.deathTicks == 1)
				{
					this.worldObj.playBroadcastSound(1018, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				}
			}

			if (this.deathTicks >= 180 && this.deathTicks <= 200)
			{
				float f = (this.rand.nextFloat() - 0.5F) * this.width;
				float f1 = (this.rand.nextFloat() - 0.5F) * this.height;
				float f2 = (this.rand.nextFloat() - 0.5F) * this.width;
				this.worldObj.spawnParticle("hugeexplosion", this.posX + (double)f, this.posY + this.getEyeHeight() + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
			}

			this.moveEntity(0.0D, 0.10000000149011612D, 0.0D);
			float f = (this.rand.nextFloat() - 0.5F) * this.width;
			float f1 = (this.rand.nextFloat() - 0.5F) * this.height;
			float f2 = (this.rand.nextFloat() - 0.5F) * this.width;
			this.worldObj.spawnParticle("largeexplode", this.posX + (double)f, this.posY + this.getEyeHeight() + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("lava", this.posX + (double)f, this.posY + this.getEyeHeight() + (double)f1, this.posZ + (double)f2, rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian());
			if (this.deathTicks == 200 && !this.worldObj.isRemote)
			{
				if (this.master != null)
				{
					this.master.heal(this.master.getMaxHealth() / 100F);
					for (int i = 0; i < 100; ++i)
					{
						double d2 = this.rand.nextGaussian() * 0.02D;
						double d0 = this.rand.nextGaussian() * 0.02D;
						double d1 = this.rand.nextGaussian() * 0.02D;
						this.worldObj.spawnParticle("largeexplode", master.posX + (double)(this.rand.nextFloat() * master.width * 2.0F) - (double)master.width, master.posY + (double)(this.rand.nextFloat() * master.height), master.posZ + (double)(this.rand.nextFloat() * master.width * 2.0F) - (double)master.width, d2, d0, d1);
					}
				}

				int i = this.experienceValue;
				while (i > 0)
				{
					int j = EntityXPOrb.getXPSplit(i);
					i -= j;
					this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
				}

				this.setDead();
			}
		}

		else
		{
			super.onDeathUpdate();
		}
	}

	public class EntityAIFindEntityNearestInjuredAlly extends EntityAIBase
	{
		private EntitySilverfishMinion field_179434_b;
		private EntityLivingBase field_179433_e;
		public EntityAIFindEntityNearestInjuredAlly(EntitySilverfishMinion entityCaveSpiderPriest)
		{
			this.field_179434_b = entityCaveSpiderPriest;
		}

		public boolean shouldExecute()
		{
			if (!field_179434_b.isEntityAlive())
			{
				return false;
			}

			if (field_179434_b.getMinionType() != EnumMinionType.PRIEST)
			{
				return false;
			}

			if (this.field_179433_e != null)
			{
				return false;
			}

			double d0 = func_179431_f();
			List<?> list = this.field_179434_b.worldObj.getEntitiesWithinAABB(EntitySilverfishMinion.class, this.field_179434_b.boundingBox.expand(d0, d0, d0));
			if (list.isEmpty())
			{
				return false;
			}

			for (int i = 0; i < list.size(); i++)
			{
				EntitySilverfishMinion entity = (EntitySilverfishMinion)list.get(i);
				if (entity.getHealth() < entity.getMaxHealth() && entity.isEntityAlive())
				this.field_179433_e = entity;
			}

			return true;
		}

		public boolean continueExecuting()
		{
			EntityLivingBase entitylivingbase = this.field_179434_b.entityToHeal;
			if (entitylivingbase == null)
			{
				return false;
			}

			if (!entitylivingbase.isEntityAlive())
			{
				return false;
			}

			if (entitylivingbase.getHealth() >= entitylivingbase.getMaxHealth())
			{
				return false;
			}

			double d0 = func_179431_f();
			return this.field_179434_b.getDistanceSqToEntity(entitylivingbase) <= d0 * d0;
		}

		public void startExecuting()
		{
			this.field_179434_b.entityToHeal = ((EntityLiving)this.field_179433_e);
			super.startExecuting();
		}

		public void resetTask()
		{
			this.field_179434_b.entityToHeal = null;
			this.field_179433_e = null;
			super.resetTask();
		}

		public void updateTask()
		{
			if (this.field_179434_b.entityToHeal != null && this.field_179434_b.getDistanceToEntity(this.field_179434_b.entityToHeal) > 16D)
			{
				this.field_179434_b.getNavigator().tryMoveToEntityLiving(this.field_179434_b.entityToHeal, 1D);
				this.field_179434_b.getLookHelper().setLookPositionWithEntity(this.field_179434_b.entityToHeal, 10F, this.field_179434_b.getVerticalFaceSpeed());
			}
		}

		protected double func_179431_f()
		{
			return 24D;
		}
	}

	public class EntityAIWander extends EntityAIBase
	{
		private EntityCreature entity;
		private double xPosition;
		private double yPosition;
		private double zPosition;
		private double speed;
		public EntityAIWander(EntityCreature p_i1648_1_, double p_i1648_2_)
		{
			this.entity = p_i1648_1_;
			this.speed = p_i1648_2_;
			setMutexBits(1);
		}

		public boolean shouldExecute()
		{
			if (this.entity.getAge() >= 100)
			{
				return false;
			}

			else if (this.entity.getRNG().nextInt(10) != 0)
			{
				return false;
			}

			else
			{
				Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
				if (vec3 == null)
				{
					return false;
				}

				else
				{
					this.xPosition = vec3.xCoord;
					this.yPosition = vec3.yCoord;
					this.zPosition = vec3.zCoord;
					return true;
				}
			}
		}

		public boolean continueExecuting()
		{
			return !this.entity.getNavigator().noPath();
		}

		public void startExecuting()
		{
			this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
		}
	}

	@Override
	public EntityLiving getMaster() 
	{
		return master;
	}


	@Override
	public void setMaster(EntityLiving entity) 
	{
		master = entity;
	}


	@Override
	public boolean isFriendly(Entity entity)
	{
		if (entity == null)
		return false;
		return entity instanceof EntityZombieMinion || entity instanceof EntityZombieTitan || entity instanceof EntityGiantZombieBetter;
	}
}


