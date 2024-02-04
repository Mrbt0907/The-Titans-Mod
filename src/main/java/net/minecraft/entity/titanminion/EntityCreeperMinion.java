package net.minecraft.entity.titanminion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
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
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titan.EntityCreeperTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.entity.titan.ITitan;
import net.minecraft.entity.titan.ai.EntityAIBreakDoorMinion;
import net.minecraft.entity.titan.ai.EntityAICreeperLoyalistSwell;
import net.minecraft.entity.titan.ai.EntityAIHurtByTargetTitan;
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
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class EntityCreeperMinion
extends EntityCreeper
implements IRangedAttackMob, ITemplar
{
	public int randomSoundDelay;
	private int lastActiveTime;
	private int timeSinceIgnited;
	public int fuseTime = 30;
	public int explosionRadius = 3;
	public EntityLiving master;
	public boolean shouldMelee = true;
	public EntityLiving entityToHeal;
	public boolean isSelfSacrificing;
	private int attackPattern;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 10, 64F);
	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	public EntityCreeperMinion(World worldIn)
	{
		super(worldIn);
		setSize(0.5F, 1.625F);
		getNavigator().setBreakDoors(true);
		getNavigator().setEnterDoors(true);
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityWitherSkull.class, 2.0F, 1.2D, 1.75D));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityTitanSpirit.class, 48.0F, 1.5D, 1.5D));
		this.tasks.addTask(0, new EntityAIBreakDoorMinion(this));
		this.tasks.addTask(0, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.removeTask(new EntityAICreeperSwell(this));
		this.tasks.addTask(0, new EntityAICreeperLoyalistSwell(this));
		this.tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
		this.targetTasks.addTask(0, new EntityAIHurtByTargetTitan(this, true, new Class[] 
		{
			EntityCreeperMinion.class, EntityCreeperTitan.class
		}
		));
		if (TheTitans.TitansFFAMode)
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.CreeperTitanSorter));
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

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityCreeperMinion.class) && (p_70686_1_ != EntityCreeperTitan.class);
	}

	protected void fall(float p_70069_1_)
	{
		if (this.getMinionTypeInt() != 4)
		super.fall(p_70069_1_);
		this.moveForward = 0F;
		this.moveStrafing = 0F;
		this.timeSinceIgnited = ((int)(this.timeSinceIgnited + p_70069_1_ * 1.5F));
		if (this.timeSinceIgnited > this.fuseTime - 5)
		{
			this.timeSinceIgnited = (this.fuseTime - 5);
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
		return this.getMinionTypeInt() == 4 ? "thetitans:titanCreeperLiving" : null;
	}

	protected String getHurtSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanCreeperGrunt" : "mob.creeper.say";
	}

	protected String getDeathSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanCreeperDeath" : "mob.creeper.death";
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
		if (miniontype == 1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
			this.setHealth(40F);
			this.experienceValue = 20;
		}

		else if (miniontype == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.setHealth(150F);
			this.experienceValue = 100;
		}

		else if (miniontype == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
			this.isImmuneToFire = true;
			this.setHealth(400F);
			this.experienceValue = 200;
		}

		else if (miniontype == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1800.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.isImmuneToFire = true;
			this.setHealth(1800F);
			this.experienceValue = 1000;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
			this.setHealth(26F);
			this.experienceValue = 10;
		}
	}

	public void TransformEntity(Entity entity)
	{
		entity.worldObj.newExplosion(entity, entity.posX, entity.posY, entity.posZ, 18.0F, true, entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		EntityCreeperTitan entitytitan = new EntityCreeperTitan(entity.worldObj);
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
			return StatCollector.translateToLocal("entity.CreeperPriest.name");
			case 2:
			return StatCollector.translateToLocal("entity.CreeperZealot.name");
			case 3:
			return StatCollector.translateToLocal("entity.CreeperBishop.name");
			case 4:
			return StatCollector.translateToLocal("entity.CreeperTemplar.name");
			default:
			return StatCollector.translateToLocal("entity.CreeperLoyalist.name");
		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setShort("Fuse", (short)this.fuseTime);
		tagCompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
		tagCompound.setBoolean("ignited", func_146078_ca());
		tagCompound.setBoolean("Suicidal", this.isSelfSacrificing);
		tagCompound.setInteger("MinionType", this.getMinionTypeInt());
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		if (tagCompund.hasKey("Fuse", 99))
		this.fuseTime = tagCompund.getShort("Fuse");
		if (tagCompund.hasKey("ExplosionRadius", 99))
		this.explosionRadius = tagCompund.getByte("ExplosionRadius");
		if (tagCompund.getBoolean("ignited"))
		func_146079_cb();
		this.setMinionType(tagCompund.getInteger("MinionType"));
		this.isSelfSacrificing = tagCompund.getBoolean("Suicidal");
	}

	public void onUpdate()
	{
		if (isEntityAlive())
		{
			if (getHealth() < getMaxHealth() / 4.0F || func_146078_ca())
			{
				this.shouldMelee = false;
			}

			else
			{
				this.shouldMelee = true;
			}

			this.lastActiveTime = this.timeSinceIgnited;
			if (func_146078_ca())
			{
				setCreeperState(1);
			}

			int i = getCreeperState();
			if (i > 0 && !this.shouldMelee && this.timeSinceIgnited == 0)
			{
				playSound("creeper.primed", 1.0F, 0.5F);
			}

			if (!this.shouldMelee)
			this.timeSinceIgnited += i;
			else
			this.timeSinceIgnited = -1;
			if (this.timeSinceIgnited < 0)
			{
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime)
			{
				this.timeSinceIgnited = this.fuseTime;
				explode();
			}
		}

		super.onUpdate();
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		Object p_180482_2_1 = super.onSpawnWithEgg(p_180482_2_);
		if (this.rand.nextInt(3) == 0)
		this.isSelfSacrificing = true;
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

	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		if ((cause.getEntity() instanceof EntitySkeleton))
		{
			int i = Item.getIdFromItem(Items.record_13);
			int j = Item.getIdFromItem(Items.record_wait);
			int k = i + this.rand.nextInt(j - i + 1);
			dropItem(Item.getItemById(k), 1);
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		if (this.shouldMelee)
		{
			float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
			int i = 0;
			if (p_70652_1_ instanceof EntityLivingBase)
			{
				f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)p_70652_1_);
				i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)p_70652_1_);
			}

			boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), f);
			if (flag)
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

				if (i > 0)
				{
					p_70652_1_.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
					this.motionX *= 0.6D;
					this.motionZ *= 0.6D;
				}

				int j = EnchantmentHelper.getFireAspectModifier(this);
				if (j > 0)
				{
					p_70652_1_.setFire(j * 4);
				}

				if (p_70652_1_ instanceof EntityLivingBase)
				{
					EnchantmentHelper.func_151384_a((EntityLivingBase)p_70652_1_, this);
				}

				EnchantmentHelper.func_151385_b(this, p_70652_1_);
			}

			return flag;
		}

		return true;
	}

	@SideOnly(Side.CLIENT)
	public float getCreeperFlashIntensity(float p_70831_1_)
	{
		return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		super.dropFewItems(p_70628_1_, p_70628_2_);
		int j = this.rand.nextInt(3 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			entityDropItem(new ItemStack(Blocks.leaves, 1, 0 + this.rand.nextInt(3)), 0.0F);
		}

		if ((this.rand.nextInt(60) == 0) || (this.rand.nextInt(1 + p_70628_2_) > 0))
		{
			entityDropItem(new ItemStack(Blocks.tnt), 0.0F);
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

	protected void dropRareDrop(int p_70600_1_)
	{
		entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0F);
	}

	public void onStruckByLightning(EntityLightningBolt lightningBolt)
	{
		this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
	}

	protected boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		if ((itemstack != null) && (itemstack.getItem() == Items.flint_and_steel))
		{
			this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			player.swingItem();
			if (!this.worldObj.isRemote)
			{
				func_146079_cb();
				itemstack.damageItem(1, player);
				return true;
			}
		}

		return super.interact(player);
	}

	public void explode()
	{
		if (!this.worldObj.isRemote)
		{
			switch (this.getMinionTypeInt())
			{
				case 1:
				{
					float f = getPowered() ? 2.5F : 1.25F;
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * f, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					setDead();
					break;
				}

				case 2:
				{
					float f = getPowered() ? 4.0F : 2.0F;
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * f, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					setDead();
					break;
				}

				case 3:
				{
					float f = getPowered() ? 10.0F : 5.0F;
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * f, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					setDead();
					break;
				}

				default:
				{
					float f = getPowered() ? 2.0F : 1.0F;
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * f, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					setDead();
					break;
				}
			}
		}
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	public void setCreeperState(int p_70829_1_)
	{
		if (!this.shouldMelee || func_146078_ca())
		super.setCreeperState(p_70829_1_);
		else
		super.setCreeperState(-1);
	}

	public void onLivingUpdate()
	{
		if (this.getMinionTypeInt() == 1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
			this.experienceValue = 20;
		}

		else if (this.getMinionTypeInt() == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.experienceValue = 100;
		}

		else if (this.getMinionTypeInt() == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
			this.isImmuneToFire = true;
			this.experienceValue = 200;
		}

		else if (this.getMinionTypeInt() == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1800.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.isImmuneToFire = true;
			this.experienceValue = 1000;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
			this.experienceValue = 10;
		}

		if (this.isEntityAlive() || this.getMinionTypeInt() != 4)
		super.onLivingUpdate();
		if (this.getMinionTypeInt() == 3)
		{
			if (this.rand.nextInt(120) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityCreeperMinion entitychicken = new EntityCreeperMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(0);
				this.worldObj.spawnEntityInWorld(entitychicken);
			}

			if (this.rand.nextInt(240) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityCreeperMinion entitychicken = new EntityCreeperMinion(this.worldObj);
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
					EntityCreeperMinion entitychicken = new EntityCreeperMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(0);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(120) == 0)
				{
					EntityCreeperMinion entitychicken = new EntityCreeperMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(1);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(240) == 0)
				{
					EntityCreeperMinion entitychicken = new EntityCreeperMinion(this.worldObj);
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
					if (this.isSelfSacrificing)
					{
						explode();
					}

					else
					{
						swingItem();
						attackEntityAsMob(getAttackTarget());
					}
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

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (source.isFireDamage() && !this.isImmuneToFire)
		this.func_146079_cb();
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
					if ((entity1 instanceof EntityCreeperMinion))
					{
						EntityCreeperMinion entitypigzombie = (EntityCreeperMinion)entity1;
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

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		this.swingItem();
		if (getDistanceSqToEntity(p_82196_1_) < (p_82196_1_.width * p_82196_1_.width) + 36D)
		attackEntityAsMob(p_82196_1_);
		else
		switch (this.rand.nextInt(5))
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
			case 3:this.worldObj.newExplosion(this, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ, 1.0F * p_82196_1_.width, false, false);
			p_82196_1_.attackEntityFrom(DamageSourceExtra.lightningBolt, 49.0F);
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
			break;
			case 4:if (!this.worldObj.isRemote)
			{
				EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(this.worldObj, p_82196_1_.posX + 0.5D, p_82196_1_.posY + 20.0D, p_82196_1_.posZ + 0.5D, this);
				this.worldObj.spawnEntityInWorld(entitytntprimed);
				playSound("game.tnt.primed", 1.0F, 1.0F);
				entitytntprimed.fuse = 80;
			}

			break;
		}
	}

	protected void updateAITasks()
	{
		if ((this.randomSoundDelay > 0) && (--this.randomSoundDelay == 0))
		{
			playSound(getHurtSound(), getSoundVolume(), getSoundPitch() + 0.25F);
		}

		if (this.getAttackTarget() != null)
		{
			if (this.worldObj.rand.nextInt(5) == 1)
			{
				EntityLivingBase e = this.getAttackTarget();
				if (e != null)
				{
					if (getDistanceToEntity(e) < this.width + e.width)
					{
						if ((this.worldObj.rand.nextInt(3) == 0) || (this.worldObj.rand.nextInt(2) == 1))
						{
							attackEntityAsMob(e);
						}
					}
				}
			}
		}

		if (this.isCollidedHorizontally && this.master != null)
		this.motionY = 0.2D;
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
					if ((entity != null) && ((entity instanceof EntityCreeperTitan)))
					{
						this.master = ((EntityCreeperTitan)entity);
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

	public class EntityAIFindEntityNearestInjuredAlly extends EntityAIBase
	{
		private EntityCreeperMinion field_179434_b;
		private EntityLivingBase field_179433_e;
		public EntityAIFindEntityNearestInjuredAlly(EntityCreeperMinion entityCaveSpiderPriest)
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
			List<?> list = this.field_179434_b.worldObj.getEntitiesWithinAABB(EntityCreeperMinion.class, this.field_179434_b.boundingBox.expand(d0, d0, d0));
			if (list.isEmpty())
			{
				return false;
			}

			for (int i = 0; i < list.size(); i++)
			{
				EntityCreeperMinion entity = (EntityCreeperMinion)list.get(i);
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

			if (entitylivingbase.getHealth() > entitylivingbase.getMaxHealth())
			{
				return false;
			}

			double d0 = func_179431_f();
			return this.field_179434_b.getDistanceSqToEntity(entitylivingbase) <= d0 * d0;
		}

		public void startExecuting()
		{
			this.field_179434_b.entityToHeal = ((EntityLiving)this.field_179433_e);
			super.resetTask();
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
		return entity instanceof EntityCreeperMinion || entity instanceof EntityCreeperTitan;
	}
}


