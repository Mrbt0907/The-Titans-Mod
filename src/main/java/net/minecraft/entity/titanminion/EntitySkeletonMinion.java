package net.minecraft.entity.titanminion;
import java.util.Calendar;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titan.EntityHarcadiumArrow;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
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
import net.minecraft.world.WorldProviderHell;
import net.mrbt0907.utils.Maths;
public class EntitySkeletonMinion
extends EntitySkeleton
implements IRangedAttackMob, ITemplar
{
	public EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.2D, 10, 40, 24F);
	public EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, 1.2D, true);
	public EntityLiving master;
	public boolean isReadyToAttack;
	public int randomSoundDelay;
	private int attackPattern;
	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	public EntityLiving entityToHeal;
	public EntitySkeletonMinion(World worldIn)
	{
		super(worldIn);
		for (int i = 0; i < this.equipmentDropChances.length; ++i)
		{
			this.equipmentDropChances[i] = 0.2F;
		}

		setSize(0.5F, 1.95F);
		getNavigator().setBreakDoors(true);
		getNavigator().setEnterDoors(true);
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityWitherSkull.class, 2.0F, 1.2D, 1.75D));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityTitanSpirit.class, 48.0F, 1.5D, 1.5D));
		this.tasks.addTask(0, new EntityAIBreakDoorMinion(this));
		this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.2D));
		this.tasks.addTask(1, new EntityAIRestrictSun(this));
		this.tasks.addTask(1, new EntityAIFleeSun(this, 1.2D));
		this.tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SkeletonTitanSorter));
		else
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
		if ((worldIn != null) && (!worldIn.isRemote))
		{
			setAttackTask();
		}
	}

	protected int getExperiencePoints(EntityPlayer p_70693_1_)
	{
		if (this.getSkeletonType() == 1)
		{
			this.experienceValue = (int)((float)this.experienceValue * 2.5F);
		}

		return super.getExperiencePoints(p_70693_1_);
	}

	public void setDead()
	{
		super.setDead();
		if ((this.master != null) && ((this.master instanceof EntityTitan)))
		{
			((EntityTitan)this.master).retractMinionNumFromType(getMinionType());
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

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntitySkeletonMinion.class) && (p_70686_1_ != EntitySkeletonTitan.class) && (p_70686_1_ != EntityWitherMinion.class);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
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

	protected String getLivingSound()
	{
		return this.getMinionTypeInt() == 4 ? (getSkeletonType() == 1 ? "thetitans:titanWitherSkeletonLiving" : "thetitans:titanSkeletonLiving") : "mob.skeleton.say";
	}

	protected String getHurtSound()
	{
		return this.getMinionTypeInt() == 4 ? (getSkeletonType() == 1 ? "thetitans:titanWitherSkeletonGrunt" : "thetitans:titanSkeletonGrunt") : "mob.skeleton.hurt";
	}

	protected String getDeathSound()
	{
		return this.getMinionTypeInt() == 4 ? (getSkeletonType() == 1 ? "thetitans:titanWitherSkeletonDeath" : "thetitans:titanSkeletonDeath") : "mob.skeleton.death";
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
		if (this.getSkeletonType() == 1)
		{
			switch (this.getMinionTypeInt())
			{
				case 1:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
					this.setHealth(this.getMaxHealth());
					this.experienceValue = 10;
					break;
				}

				case 2:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(360D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(16.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.375D);
					this.setHealth(this.getMaxHealth());
					this.experienceValue = 50;
					break;
				}

				case 3:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1080D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(26.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
					this.setHealth(this.getMaxHealth());
					this.isImmuneToFire = true;
					this.experienceValue = 250;
					break;
				}

				case 4:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(3240D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(42.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.setHealth(this.getMaxHealth());
					this.isImmuneToFire = true;
					this.experienceValue = 1250;
					break;
				}

				case 5:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(9720D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(68.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.625D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.setHealth(this.getMaxHealth());
					this.isImmuneToFire = true;
					this.experienceValue = 6250;
					break;
				}

				case 6:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(29160D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(110.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.setHealth(this.getMaxHealth());
					this.isImmuneToFire = true;
					this.experienceValue = 31250;
					break;
				}

				default:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
					this.setHealth(this.getMaxHealth());
					this.experienceValue = 7;
				}
			}
		}

		else
		{
			switch (this.getMinionTypeInt())
			{
				case 1:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
					this.setHealth(this.getMaxHealth());
					this.experienceValue = 10;
					break;
				}

				case 2:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(180D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.375D);
					this.setHealth(this.getMaxHealth());
					this.experienceValue = 50;
					break;
				}

				case 3:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(540D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(13.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
					this.setHealth(this.getMaxHealth());
					this.isImmuneToFire = true;
					this.experienceValue = 250;
					break;
				}

				case 4:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1620D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(21.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.setHealth(this.getMaxHealth());
					this.isImmuneToFire = true;
					this.experienceValue = 1250;
					break;
				}

				case 5:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4860D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(34.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.625D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.setHealth(this.getMaxHealth());
					this.isImmuneToFire = true;
					this.experienceValue = 6250;
					break;
				}

				case 6:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14580D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(55.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.setHealth(this.getMaxHealth());
					this.isImmuneToFire = true;
					this.experienceValue = 31250;
					break;
				}

				default:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
					this.setHealth(this.getMaxHealth());
					this.experienceValue = 7;
				}
			}
		}
	}

	public void TransformEntity(Entity entity)
	{
		entity.worldObj.newExplosion(entity, entity.posX, entity.posY, entity.posZ, 18.0F, true, entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		EntitySkeletonTitan entitytitan = new EntitySkeletonTitan(entity.worldObj);
		entitytitan.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0F);
		entity.setDead();
		entitytitan.func_82206_m();
		entity.worldObj.spawnEntityInWorld(entitytitan);
		if (getSkeletonType() == 1)
		{
			entitytitan.becomeWitherSkeleton(true);
		}

		else
		{
			entitytitan.setSkeletonType(0);
			entitytitan.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
		}
	}

	public void onLivingUpdate()
	{
		if (this.getHealth() > this.getMaxHealth())
		this.setHealth(this.getMaxHealth());
		if (this.getSkeletonType() == 1)
		{
			switch (this.getMinionTypeInt())
			{
				case 1:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
					this.experienceValue = 10;
					break;
				}

				case 2:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(360D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(16.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.375D);
					this.experienceValue = 50;
					break;
				}

				case 3:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1080D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(26.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
					this.isImmuneToFire = true;
					this.experienceValue = 250;
					break;
				}

				case 4:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(3240D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(42.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.isImmuneToFire = true;
					this.experienceValue = 1250;
					break;
				}

				case 5:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(9720D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(68.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.625D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.isImmuneToFire = true;
					this.experienceValue = 6250;
					break;
				}

				case 6:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(29160D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(110.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.isImmuneToFire = true;
					this.experienceValue = 31250;
					break;
				}

				default:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
					this.experienceValue = 7;
				}
			}
		}

		else
		{
			switch (this.getMinionTypeInt())
			{
				case 1:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
					this.experienceValue = 10;
					break;
				}

				case 2:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(180D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.375D);
					this.experienceValue = 50;
					break;
				}

				case 3:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(540D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(13.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
					this.isImmuneToFire = true;
					this.experienceValue = 250;
					break;
				}

				case 4:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1620D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(21.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.isImmuneToFire = true;
					this.experienceValue = 1250;
					break;
				}

				case 5:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4860D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(34.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.625D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.isImmuneToFire = true;
					this.experienceValue = 6250;
					break;
				}

				case 6:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14580D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(55.0D);
					this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
					this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
					this.isImmuneToFire = true;
					this.experienceValue = 31250;
					break;
				}

				default:
				{
					this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
					this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
					this.experienceValue = 7;
				}
			}
		}

		this.tasks.removeTask(new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
		this.tasks.removeTask(new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false));
		if ((getAttackTarget() != null) && (getDistanceSqToEntity(getAttackTarget()) > 256.0D))
		{
			getMoveHelper().setMoveTo(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1.0D);
		}

		if (getAttackTarget() != null)
		{
			setRevengeTarget(getAttackTarget());
		}

		if ((this.worldObj.isRemote) && (getSkeletonType() == 1))
		{
			setSize(0.6F, 2.39F);
		}

		if (this.isEntityAlive() || this.getMinionTypeInt() != 4)
		super.onLivingUpdate();
		if (this.getMinionTypeInt() == 3)
		{
			if (this.rand.nextInt(120) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntitySkeletonMinion entitychicken = new EntitySkeletonMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(0);
				entitychicken.setSkeletonType(this.getSkeletonType());
				this.worldObj.spawnEntityInWorld(entitychicken);
			}

			if (this.rand.nextInt(240) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntitySkeletonMinion entitychicken = new EntitySkeletonMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(1);
				entitychicken.setSkeletonType(this.getSkeletonType());
				this.worldObj.spawnEntityInWorld(entitychicken);
			}
		}

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
					EntitySkeletonMinion entitychicken = new EntitySkeletonMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(0);
					entitychicken.setSkeletonType(this.getSkeletonType());
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(120) == 0)
				{
					EntitySkeletonMinion entitychicken = new EntitySkeletonMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(1);
					entitychicken.setSkeletonType(this.getSkeletonType());
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(240) == 0)
				{
					EntitySkeletonMinion entitychicken = new EntitySkeletonMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(2);
					entitychicken.setSkeletonType(this.getSkeletonType());
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
		}

		else
		{
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(100.0D, 100.0D, 100.0D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if ((entity != null) && ((entity instanceof EntitySkeletonTitan)))
					{
						if ((getSkeletonType() == ((EntitySkeletonTitan)entity).getSkeletonType()))
						{
							this.master = ((EntitySkeletonTitan)entity);
						}
					}
				}
			}
		}
	}

	protected void updateAITasks()
	{
		if ((this.randomSoundDelay > 0) && (--this.randomSoundDelay == 0))
		{
			playSound(getHurtSound(), getSoundVolume(), getSoundPitch() + 0.25F);
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

	public void updateRidden()
	{
		super.updateRidden();
		if ((this.ridingEntity instanceof EntitySpiderMinion))
		{
			EntitySpiderMinion entitycreature = (EntitySpiderMinion)this.ridingEntity;
			entitycreature.renderYawOffset = this.renderYawOffset;
			entitycreature.rotationYaw = this.rotationYaw;
			entitycreature.rotationYawHead = this.rotationYawHead;
			if (getAttackTarget() != null)
			{
				entitycreature.setAttackTarget(getAttackTarget());
			}

			if (getAttackTarget() == entitycreature)
			{
				this.ridingEntity = null;
			}
		}
	}

	public String getCommandSenderName()
	{
		if (this.getSkeletonType() == 1)
		{
			switch (this.getMinionTypeInt())
			{
				case 1:
				return StatCollector.translateToLocal("entity.WitherSkeletonPriest.name");
				case 2:
				return StatCollector.translateToLocal("entity.WitherSkeletonZealot.name");
				case 3:
				return StatCollector.translateToLocal("entity.WitherSkeletonBishop.name");
				case 4:
				return StatCollector.translateToLocal("entity.WitherSkeletonTemplar.name");
				default:
				return StatCollector.translateToLocal("entity.WitherSkeletonLoyalist.name");
			}
		}

		else
		{
			switch (this.getMinionTypeInt())
			{
				case 1:
				return StatCollector.translateToLocal("entity.SkeletonPriest.name");
				case 2:
				return StatCollector.translateToLocal("entity.SkeletonZealot.name");
				case 3:
				return StatCollector.translateToLocal("entity.SkeletonBishop.name");
				case 4:
				return StatCollector.translateToLocal("entity.SkeletonTemplar.name");
				default:
				return StatCollector.translateToLocal("entity.SkeletonLoyalist.name");
			}
		}
	}

	protected Item getDefaultEquipment()
	{
		switch (this.getMinionTypeInt())
		{
			case 1:
			return Items.iron_axe;
			case 2:
			return Items.iron_sword;
			case 3:
			return Items.diamond_axe;
			case 4:
			return Items.diamond_sword;
			default:
			return Items.stone_sword;
		}
	}

	protected Item getDropItem()
	{
		return Items.arrow;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		int j = this.rand.nextInt(3 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.bone, 1);
		}

		j = this.rand.nextInt(5 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			entityDropItem(new ItemStack(Items.dye, 1, 15), 0.0F);
		}

		if (this.getSkeletonType() == 1)
		{
			j = this.rand.nextInt(3 + p_70628_2_) - 1;
			for (int k = 0; k < j; k++)
			{
				dropItem(Items.coal, 1);
			}

			if (this.getMinionTypeInt() >= 1)
			{
				j = 1 + this.rand.nextInt(4);
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
						if (this.rand.nextInt(10) == 0)
						{
							entityDropItem(new ItemStack(Items.golden_apple, 1, 1), 0.0F);
						}

						else
						{
							dropItem(Items.golden_apple, 1);
						}
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
								case 0:entityDropItem(new ItemStack(Blocks.emerald_block, 1, 0), 0.0F);
								break;
								case 1:entityDropItem(new ItemStack(Blocks.diamond_block, 1, 0), 0.0F);
								break;
								case 2:entityDropItem(new ItemStack(Blocks.gold_block, 1, 0), 0.0F);
								break;
								case 3:entityDropItem(new ItemStack(Blocks.gold_block, 1, 0), 0.0F);
								break;
								case 4:entityDropItem(new ItemStack(Blocks.gold_block, 1, 0), 0.0F);
							}
						}

						entityDropItem(new ItemStack(Blocks.obsidian), 0.0F);
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
									case 0:entityDropItem(new ItemStack(Blocks.emerald_block, 1, 0), 0.0F);
									break;
									case 1:entityDropItem(new ItemStack(Blocks.diamond_block, 1, 0), 0.0F);
									break;
									case 2:entityDropItem(new ItemStack(Blocks.gold_block, 1, 0), 0.0F);
								}
							}

							entityDropItem(new ItemStack(Blocks.obsidian), 0.0F);
						}
					}
				}
			}
		}

		else
		{
			j = this.rand.nextInt(3 + p_70628_2_);
			for (int k = 0; k < j; k++)
			{
				dropItem(Items.arrow, 1);
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
	}

	protected void dropRareDrop(int p_70600_1_)
	{
		if (getSkeletonType() == 1)
		{
			entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
		}

		else
		{
			entityDropItem(new ItemStack(Items.skull, 1, 0), 0.0F);
		}
	}

	protected void addRandomArmor()
	{
		if (this.rand.nextFloat() < (this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 0.25F : this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.95F : 0.05F))
		{
			int i = this.rand.nextInt(2);
			float f = this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.75F : 0.05F;
			if (this.rand.nextFloat() < 0.25F)
			{
				i++;
			}

			if (this.rand.nextFloat() < 0.25F)
			{
				i++;
			}

			if (this.rand.nextFloat() < 0.25F)
			{
				i++;
			}

			for (int j = 3; j >= 0; j--)
			{
				ItemStack itemstack = func_130225_q(j);
				if ((j < 3) && (this.rand.nextFloat() < f))
				{
					break;
				}

				if (itemstack == null)
				{
					Item item = getArmorItemForSlot(j + 1, i);
					if (item != null)
					{
						setCurrentItemOrArmor(j + 1, new ItemStack(item));
					}
				}
			}
		}

		setCurrentItemOrArmor(0, new ItemStack(Items.bow));
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		addRandomArmor();
		enchantEquipment();
		if (((this.worldObj.provider instanceof WorldProviderHell)) && (getRNG().nextInt(5) > 0) || this.getSkeletonType() == 1)
		{
			this.tasks.addTask(3, this.aiAttackOnCollide);
			setSkeletonType(1);
			setCurrentItemOrArmor(0, new ItemStack(getDefaultEquipment()));
		}

		else
		{
			this.tasks.addTask(3, this.aiArrowAttack);
			setSkeletonType(0);
			setCurrentItemOrArmor(0, new ItemStack(Items.bow));
		}

		float f = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
		setCanPickUpLoot(true);
		Calendar calendar = this.worldObj.getCurrentDate();
		if (!this.isChild() && (calendar.get(2) + 1 == 10 && calendar.get(5) >= 1 && calendar.get(5) <= 31) && (this.rand.nextFloat() < 0.5F))
		{
			setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
			this.equipmentDropChances[4] = 0.0F;
		}

		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.1D, 0));
		double d0 = this.rand.nextDouble() * 1.5D * f;
		if (d0 > 1.0D)
		{
			getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
		}

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

		getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
		return p_180482_2_;
	}

	public void setCombatTask() 
	{
		 
	}


	public void setAttackTask()
	{
		this.tasks.removeTask(this.aiAttackOnCollide);
		this.tasks.removeTask(this.aiArrowAttack);
		ItemStack itemstack = getHeldItem();
		if ((itemstack != null) && (itemstack.getItem() == Items.bow))
		{
			this.tasks.addTask(3, this.aiArrowAttack);
		}

		else
		{
			this.tasks.addTask(3, this.aiAttackOnCollide);
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
		setAttackTask();
	}

	public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_)
	{
		super.setCurrentItemOrArmor(p_70062_1_, p_70062_2_);
		if ((!this.worldObj.isRemote) && (p_70062_1_ == 0))
		{
			setAttackTask();
		}
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		if (this.getMinionTypeInt() == 4)
		{
			if (getDistanceSqToEntity(p_82196_1_) < (p_82196_1_.width * p_82196_1_.width) + 36D)
			attackEntityAsMob(p_82196_1_);
			else
			if (this.getSkeletonType() == 1)
			{
				switch (this.rand.nextInt(5))
				{
					case 0:for (int i = 0; i < 100; i++)
					{
						EntityHarcadiumArrow entityarrow = new EntityHarcadiumArrow(this.worldObj, this, p_82196_1_, 4F, 45F);
						entityarrow.setIsCritical(true);
						entityarrow.setDamage(p_82196_2_ * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting.getDifficultyId() * 0.11F);
						this.worldObj.spawnEntityInWorld(entityarrow);
						double d8 = 4D;
						Vec3 vec3 = getLook(1.0F);
						entityarrow.posX = (this.posX + vec3.xCoord * d8);
						entityarrow.posY = (this.posY + vec3.yCoord * d8 + 1D);
						entityarrow.posZ = (this.posZ + vec3.zCoord * d8);
					}

					break;
					case 1:for (int i = 0; i < 200; i++)
					{
						EntityPotion entitypotion = new EntityPotion(this.worldObj, this, 32732);
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
						entitypotion.setThrowableHeading(d1, d2 + f1 * 0.2F, d3, 2F, 25F);
						this.worldObj.spawnEntityInWorld(entitypotion);
					}

					break;
					case 2:for (int i = 0; i < 50; i++)
					{
						getDistanceSqToEntity(p_82196_1_);
						double d11 = p_82196_1_.posX - this.posX;
						double d21 = p_82196_1_.boundingBox.minY + p_82196_1_.height / 2.0F - (this.posY + p_82196_1_.height / 2.0F);
						double d31 = p_82196_1_.posZ - this.posZ;
						EntityGhastMinionFireball entitysmallfireball = new EntityGhastMinionFireball(this.worldObj, this, d11 + (getRNG().nextGaussian() * 9F), d21, d31 + (getRNG().nextGaussian() * 9F));
						double d8 = 2;
						Vec3 vec3 = getLook(1.0F);
						entitysmallfireball.posX = (this.posX + vec3.xCoord * d8);
						entitysmallfireball.posY = (this.posY + vec3.yCoord * d8 + 1D);
						entitysmallfireball.posZ = (this.posZ + vec3.zCoord * d8);
						entitysmallfireball.field_92057_e = 2;
						this.worldObj.spawnEntityInWorld(entitysmallfireball);
					}

					break;
					case 3:this.worldObj.newExplosion(this, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ, 2.0F * p_82196_1_.width, false, false);
					p_82196_1_.attackEntityFrom(DamageSourceExtra.lightningBolt, 100.0F);
					this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
					this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
					this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
					this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
					break;
					case 4:playSound("mob.skeleton.death", 1.0F, 0.5F);
					this.worldObj.playSoundEffect(p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
					for (int i1 = 0; i1 < 256; i1++)
					{
						EntityItem entityitem = p_82196_1_.dropItem(Items.bone, 1);
						entityitem.motionY = 1.0D;
						entityitem.delayBeforeCanPickup = 6000;
						entityitem.lifespan = (40 + this.rand.nextInt(20));
					}

					p_82196_1_.addPotionEffect(new PotionEffect(Potion.wither.id, 400, 3));
					p_82196_1_.attackEntityFrom(DamageSource.wither, 20.0F);
					p_82196_1_.hurtResistantTime = 1;
				}
			}

			else
			{
				switch (this.rand.nextInt(5))
				{
					case 0:EntityArrow entityarrow = new EntityArrow(this.worldObj, this, p_82196_1_, 1.6F, 14 - this.worldObj.difficultySetting.getDifficultyId() * 4);
					int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
					int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
					entityarrow.setDamage(p_82196_2_ * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting.getDifficultyId() * 0.11F);
					entityarrow.setIsCritical(true);
					if (i > 0)
					{
						entityarrow.setDamage(entityarrow.getDamage() + i * 0.5D + 0.5D);
					}

					if (j > 0)
					{
						entityarrow.setKnockbackStrength(j);
					}

					if ((EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0) || (getSkeletonType() == 1))
					{
						entityarrow.setFire(100);
					}

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
					entitypotion.setThrowableHeading(d1, d2 + f1 * 0.2F, d3, 1.6F, f1 / 20.0F);
					this.worldObj.spawnEntityInWorld(entitypotion);
					break;
					case 2:double d011 = getDistanceSqToEntity(p_82196_1_);
					double d111 = p_82196_1_.posX - this.posX;
					double d211 = p_82196_1_.boundingBox.minY + p_82196_1_.height / 2.0F - (this.posY + p_82196_1_.height / 2.0F);
					double d311 = p_82196_1_.posZ - this.posZ;
					float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d011)) * 0.1F;
					this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
					EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.worldObj, this, d111 + getRNG().nextGaussian() * f, d211, d311 + getRNG().nextGaussian() * f);
					entitysmallfireball.posY = (this.posY + 1.6D);
					this.worldObj.spawnEntityInWorld(entitysmallfireball);
					break;
					case 3:playSound("mob.skeleton.death", 1.0F, 0.5F);
					this.worldObj.playSoundEffect(p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
					for (int i1 = 0; i1 < 256; i1++)
					{
						EntityItem entityitem = p_82196_1_.dropItem(Items.bone, 1);
						entityitem.motionY = 1.0D;
						entityitem.delayBeforeCanPickup = 6000;
						entityitem.lifespan = (40 + this.rand.nextInt(20));
					}

					p_82196_1_.addPotionEffect(new PotionEffect(Potion.wither.id, 100, 2));
					p_82196_1_.attackEntityFrom(DamageSource.wither, 5.0F);
					p_82196_1_.hurtResistantTime = 1;
					break;
					case 4:EntityHarcadiumArrow entityarrow1 = new EntityHarcadiumArrow(this.worldObj, this, p_82196_1_, 1.6F, 1.0F);
					entityarrow1.setIsCritical(true);
					playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
					this.worldObj.spawnEntityInWorld(entityarrow1);
				}
			}
		}

		else
		{
			EntityArrow entityarrow = new EntityArrow(this.worldObj, this, p_82196_1_, 1.6F, 14 - this.worldObj.difficultySetting.getDifficultyId() * 4);
			int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
			int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
			entityarrow.setDamage(p_82196_2_ * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting.getDifficultyId() * 0.11F + (this.getMinionTypeInt() * 3));
			if (i > 0)
			{
				entityarrow.setDamage(entityarrow.getDamage() + i * 0.5D + 0.5D);
			}

			if (j > 0)
			{
				entityarrow.setKnockbackStrength(j);
			}

			if ((EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0) || (getSkeletonType() == 1))
			{
				entityarrow.setFire(100);
			}

			playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
			this.worldObj.spawnEntityInWorld(entityarrow);
		}
	}

	public int getSkeletonType()
	{
		return this.dataWatcher.getWatchableObjectByte(13);
	}

	public void setSkeletonType(int p_82201_1_)
	{
		this.dataWatcher.updateObject(13, Byte.valueOf((byte)p_82201_1_));
		this.isImmuneToFire = (p_82201_1_ == 1) || this.getMinionTypeInt() >= 3;
		if (p_82201_1_ == 1)
		{
			setSize(0.6F, 2.39F);
			setCurrentItemOrArmor(0, new ItemStack(getDefaultEquipment()));
		}

		else
		{
			setSize(0.5F, 1.95F);
			setCurrentItemOrArmor(0, new ItemStack(Items.bow));
		}
	}

	public float getEyeHeight()
	{
		return getSkeletonType() == 1 ? 2.088F : 1.74F;
	}

	public double getYOffset()
	{
		if (getSkeletonType() == 1)
		{
			return super.getYOffset() - 0.28D;
		}

		return super.getYOffset() - 0.15D;
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
					if ((entity1 instanceof EntitySkeletonMinion))
					{
						EntitySkeletonMinion entitypigzombie = (EntitySkeletonMinion)entity1;
						if (entitypigzombie.getAttackTarget() == null)
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

	public class EntityAIFindEntityNearestInjuredAlly extends EntityAIBase
	{
		private EntitySkeletonMinion field_179434_b;
		private EntityLivingBase field_179433_e;
		public EntityAIFindEntityNearestInjuredAlly(EntitySkeletonMinion entityCaveSpiderPriest)
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
			List<?> list = this.field_179434_b.worldObj.getEntitiesWithinAABB(EntitySkeletonMinion.class, this.field_179434_b.boundingBox.expand(d0, d0, d0));
			if (list.isEmpty())
			{
				return false;
			}

			for (int i = 0; i < list.size(); i++)
			{
				EntitySkeletonMinion entity = (EntitySkeletonMinion)list.get(i);
				if (entity.getHealth() < entity.getMaxHealth() && entity.isEntityAlive() && entity.getSkeletonType() == field_179434_b.getSkeletonType())
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
			return 32.0D;
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
		return entity instanceof EntitySkeletonMinion || entity instanceof EntitySkeletonTitan || entity instanceof EntityWitherMinion;
	}
}


