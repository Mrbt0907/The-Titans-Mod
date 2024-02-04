package net.minecraft.entity.titanminion;
import java.util.Calendar;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.entity.titan.ITitan;
import net.minecraft.entity.titan.ai.EntityAIBreakDoorMinion;
import net.minecraft.entity.titan.ai.EntityAIHurtByTargetTitan;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
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
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class EntityPigZombieMinion extends EntityPigZombie implements IRangedAttackMob, ITemplar
{
	public EntityLiving master;
	public int angerLevel;
	public int randomSoundDelay;
	public EntityLiving entityToHeal;
	private int attackPattern;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 10, 64.0F);
	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	public EntityPigZombieMinion(World worldIn)
	{
		super(worldIn);
		for (int i = 0; i < this.equipmentDropChances.length; ++i)
		{
			this.equipmentDropChances[i] = 0.2F;
		}

		setSize(0.5F, 1.85F);
		getNavigator().setBreakDoors(true);
		getNavigator().setEnterDoors(true);
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityWitherSkull.class, 2.0F, 1.2D, 1.75D));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityTitanSpirit.class, 48.0F, 1.5D, 1.5D));
		this.tasks.addTask(0, new EntityAIBreakDoorMinion(this));
		this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.2D));
		this.tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, true));
		this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		this.tasks.addTask(6, new EntityAIWander(this, 0.8D));
		this.targetTasks.addTask(0, new EntityAIHurtByTargetTitan(this, true, new Class[] 
		{
			EntityPigZombieMinion.class, EntityPigZombieTitan.class, EntityGhastGuard.class
		}
		));
		if (TheTitans.TitansFFAMode)
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.PigZombieTitanSorter));
		else
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	public void setDead()
	{
		super.setDead();
		if ((this.master != null) && ((this.master instanceof EntityTitan)))
		{
			((EntityTitan)this.master).retractMinionNumFromType(getMinionType());
		}
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		playSound("mob.pig.step", 0.15F, 1.0F);
		playSound("mob.zombie.step", 0.15F, 1.0F);
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
		return this.getMinionTypeInt() == 4 ? "thetitans:titanPigZombieLiving" : "mob.zombiepig.zpig";
	}

	protected String getHurtSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanPigZombieGrunt" : "mob.zombiepig.zpighurt";
	}

	protected String getDeathSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanPigZombieDeath" : "mob.zombiepig.zpigdeath";
	}

	protected float getSoundPitch()
	{
		return this.getMinionTypeInt() == 4 ? super.getSoundPitch() + 0.3F : super.getSoundPitch();
	}

	public int getTotalArmorValue()
	{
		int i = 2;
		ItemStack[] aitemstack = this.getLastActiveItems();
		int j = aitemstack.length;
		for (int k = 0; k < j; ++k)
		{
			ItemStack itemstack = aitemstack[k];
			if (itemstack != null && itemstack.getItem() instanceof ItemArmor)
			{
				int l = ((ItemArmor)itemstack.getItem()).damageReduceAmount;
				i += l;
			}
		}

		switch (this.getMinionTypeInt())
		{
			case 1:
			i += 2;
			break;
			case 2:
			i += 15;
			break;
			case 3:
			i += 18;
			break;
			case 4:
			i += 22;
		}

		if (i > 24)
		{
			i = 24;
		}

		return i;
	}

	protected float applyPotionDamageCalculations(DamageSource p_70672_1_, float p_70672_2_)
	{
		if (this.getMinionTypeInt() >= 4)
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

	public String getCommandSenderName()
	{
		switch (this.getMinionTypeInt())
		{
			case 1:
			return StatCollector.translateToLocal("entity.PigZombiePriest.name");
			case 2:
			return StatCollector.translateToLocal("entity.PigZombieZealot.name");
			case 3:
			return StatCollector.translateToLocal("entity.PigZombieBishop.name");
			case 4:
			return StatCollector.translateToLocal("entity.PigZombieTemplar.name");
			default:
			return StatCollector.translateToLocal("entity.PigZombieLoyalist.name");
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
		if (miniontype == 1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
			this.setHealth(40F);
			this.experienceValue = 15;
		}

		else if (miniontype == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(180.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.setHealth(180F);
			this.experienceValue = 100;
		}

		else if (miniontype == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(420.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30.0D);
			this.setHealth(420F);
			this.experienceValue = 250;
			this.addRandomArmor();
			this.enchantEquipment();
		}

		else if (miniontype == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1800.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.setHealth(1800F);
			this.experienceValue = 1000;
			this.addRandomArmor();
			this.enchantEquipment();
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
			this.setHealth(26F);
			this.experienceValue = 7;
		}
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		int j = this.rand.nextInt(3 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.rotten_flesh, 1);
		}

		j = this.rand.nextInt(3 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.gold_nugget, 1);
		}

		j = this.rand.nextInt(2 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.bone, 1);
		}

		j = this.rand.nextInt(2 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.cooked_porkchop, 1);
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

					entityDropItem(new ItemStack(Blocks.glowstone), 0.0F);
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

	public boolean isAIEnabled()
	{
		return true;
	}

	public boolean isAngry()
	{
		return this.angerLevel > 0;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityPigZombieMinion.class) && (p_70686_1_ != EntityGhastGuard.class) && ((p_70686_1_ != EntityPigZombieTitan.class) || (p_70686_1_ == EntityZombieTitan.class));
	}

	public void onLivingUpdate()
	{
		if (this.getMinionTypeInt() == 1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
			this.experienceValue = 15;
		}

		else if (this.getMinionTypeInt() == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(180.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.experienceValue = 100;
		}

		else if (this.getMinionTypeInt() == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(420.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30.0D);
			this.experienceValue = 250;
		}

		else if (this.getMinionTypeInt() == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1800.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.experienceValue = 1000;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
			this.experienceValue = 7;
		}

		if (this.isEntityAlive() || this.getMinionTypeInt() != 4)
		super.onLivingUpdate();
		if (this.getMinionTypeInt() == 3)
		{
			if (this.rand.nextInt(120) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityPigZombieMinion entitychicken = new EntityPigZombieMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(0);
				this.worldObj.spawnEntityInWorld(entitychicken);
			}

			if (this.rand.nextInt(240) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityPigZombieMinion entitychicken = new EntityPigZombieMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(1);
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
					EntityPigZombieMinion entitychicken = new EntityPigZombieMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(0);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(120) == 0)
				{
					EntityPigZombieMinion entitychicken = new EntityPigZombieMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(1);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(240) == 0)
				{
					EntityPigZombieMinion entitychicken = new EntityPigZombieMinion(this.worldObj);
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
				if (d0 < 0.8D)
				{
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
	}

	protected void fall(float p_70069_1_)
	{
		if (this.getMinionTypeInt() != 4)
		super.fall(p_70069_1_);
		this.moveForward = 0F;
		this.moveStrafing = 0F;
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_)
	{
		return this.getMinionTypeInt() == 4 ? 15728880 : super.getBrightnessForRender(p_70070_1_);
	}

	/**
	* Gets how bright this entity is.
	*/
	public float getBrightness(float p_70013_1_)
	{
		return this.getMinionTypeInt() == 4 ? 1.0F : super.getBrightness(p_70013_1_);
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
					if ((entity1 instanceof EntityPigZombieMinion))
					{
						EntityPigZombieMinion entitypigzombie = (EntityPigZombieMinion)entity1;
						entitypigzombie.setAttackTarget((EntityLivingBase)entity);
						entitypigzombie.setRevengeTarget((EntityLivingBase)entity);
						entitypigzombie.becomeAngryAt(entity);
					}

					setAttackTarget((EntityLivingBase)entity);
					setRevengeTarget((EntityLivingBase)entity);
					becomeAngryAt(entity);
				}
			}
		}

		return super.attackEntityFrom(source, amount);
	}

	protected void updateAITasks()
	{
		if (this.isCollidedHorizontally && this.master != null)
		this.motionY = 0.2D;
		if ((this.randomSoundDelay > 0) && (--this.randomSoundDelay == 0))
		{
			playSound("mob.zombiepig.zpigangry", getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
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
				becomeAngryAt(this.master.getAttackTarget());
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
					if ((entity != null) && ((entity instanceof EntityPigZombieTitan)))
					{
						this.master = ((EntityPigZombieTitan)entity);
					}
				}
			}
		}

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

	public void TransformEntity(Entity entity)
	{
		entity.worldObj.newExplosion(entity, entity.posX, entity.posY, entity.posZ, 18.0F, true, entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		EntityPigZombieTitan entitytitan = new EntityPigZombieTitan(entity.worldObj);
		entitytitan.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0F);
		entity.setDead();
		entitytitan.func_82206_m();
		entity.worldObj.spawnEntityInWorld(entitytitan);
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
			entitypotion.setThrowableHeading(d1, d2 + f1 * 0.2F, d3, 1.6F, f1 / 20.0F);
			this.worldObj.spawnEntityInWorld(entitypotion);
			break;
			case 2:getDistanceSqToEntity(p_82196_1_);
			double d11 = p_82196_1_.posX - this.posX;
			double d21 = p_82196_1_.boundingBox.minY + p_82196_1_.height / 2.0F - (this.posY + p_82196_1_.height / 2.0F);
			double d31 = p_82196_1_.posZ - this.posZ;
			this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
			EntityLargeFireball entitylargefireball = new EntityLargeFireball(this.worldObj, this, d11, d21, d31);
			entitylargefireball.posY = (this.posY + 1.6D);
			this.worldObj.spawnEntityInWorld(entitylargefireball);
			break;
			case 3:playSound("mob.zombiepig.zpighurt", 1.0F, 0.5F);
			this.worldObj.playSoundEffect(p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			for (int i = 0; i < 128; i++)
			{
				EntityItem entityitem = p_82196_1_.dropItem(Items.rotten_flesh, 1);
				entityitem.delayBeforeCanPickup = 6000;
				entityitem.lifespan = (40 + this.rand.nextInt(20));
				EntityItem entityitem1 = p_82196_1_.dropItem(Items.gold_nugget, 1);
				entityitem1.delayBeforeCanPickup = 6000;
				entityitem1.lifespan = (40 + this.rand.nextInt(20));
			}

			p_82196_1_.addPotionEffect(new PotionEffect(Potion.hunger.id, 100, 2));
			p_82196_1_.attackEntityFrom(DamageSource.starve, 10F);
			p_82196_1_.hurtResistantTime = 1;
		}
	}

	public void becomeAngryAt(Entity p_70835_1_)
	{
		this.angerLevel = (400 + this.rand.nextInt(400));
		this.randomSoundDelay = this.rand.nextInt(40);
		this.playSound("mob.zombiepig.zpigangry", getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		if ((p_70835_1_ instanceof EntityLivingBase))
		{
			setRevengeTarget((EntityLivingBase)p_70835_1_);
			setAttackTarget((EntityLivingBase)p_70835_1_);
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	{
		super.onSpawnWithEgg(p_110161_1_);
		float f = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
		setCanPickUpLoot(true);
		Calendar calendar = this.worldObj.getCurrentDate();
		if (!this.isChild() && !this.isVillager() && (calendar.get(2) + 1 == 10 && calendar.get(5) >= 1 && calendar.get(5) <= 31) && (this.rand.nextFloat() < 0.5F))
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

		if (this.rand.nextFloat() < f * (this.getMinionTypeInt() * 0.1F + 0.1F))
		{
			getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
			getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 4.0D, 2));
		}

		this.setVillager(false);
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

		return p_110161_1_;
	}

	public class EntityAIFindEntityNearestInjuredAlly extends EntityAIBase
	{
		private EntityPigZombieMinion field_179434_b;
		private EntityLivingBase field_179433_e;
		public EntityAIFindEntityNearestInjuredAlly(EntityPigZombieMinion entityPigZombieMinion)
		{
			this.field_179434_b = entityPigZombieMinion;
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
			List<?> list = this.field_179434_b.worldObj.getEntitiesWithinAABB(EntityPigZombieMinion.class, this.field_179434_b.boundingBox.expand(d0, d0, d0));
			if (list.isEmpty())
			{
				return false;
			}

			for (int i = 0; i < list.size(); i++)
			{
				EntityPigZombieMinion entity = (EntityPigZombieMinion)list.get(i);
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
		return entity instanceof EntityPigZombieMinion || entity instanceof EntityPigZombieTitan || entity instanceof EntityGhastGuard;
	}
}


