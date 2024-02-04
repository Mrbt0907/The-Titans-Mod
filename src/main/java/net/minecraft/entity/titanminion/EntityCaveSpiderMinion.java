package net.minecraft.entity.titanminion;
import java.util.List;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titan.EntityCaveSpiderTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.entity.titan.ITitan;
import net.minecraft.entity.titan.ai.EntityAIBreakDoorMinion;
import net.minecraft.entity.titan.ai.EntityAIHurtByTargetTitan;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.mrbt0907.utils.Maths;
public class EntityCaveSpiderMinion extends EntityCaveSpider implements IRangedAttackMob, ITemplar
{
	public EntityLiving entityToHeal;
	public int randomSoundDelay;
	public EntityLiving master;
	public boolean isSpeedy;
	public boolean isStrong;
	public boolean isPredator;
	public boolean isWolverine;
	public boolean isSuperSwimmer;
	public boolean isLavaSwimmer;
	public boolean isJumper;
	public boolean isHealthy;
	public boolean isShielded;
	public boolean isTough;
	private int attackPattern;
	/** Random offset used in floating behaviour */
	private float heightOffset = 0.5F;
	/** ticks until heightOffset is randomized */
	private int heightOffsetUpdateTime;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 10, 64F);
	public EntityCaveSpiderMinion(World worldIn)
	{
		super(worldIn);
		getNavigator().setBreakDoors(true);
		getNavigator().setEnterDoors(true);
		getNavigator().setCanSwim(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityWitherSkull.class, 2.0F, 1.2D, 1.75D));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityTitanSpirit.class, 48.0F, 1.5D, 1.5D));
		this.tasks.addTask(0, new EntityAIBreakDoorMinion(this));
		this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.2D));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.2D, true));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTargetTitan(this, true, new Class[] 
		{
			EntityCaveSpiderMinion.class, EntityCaveSpiderTitan.class
		}
		));
		this.tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
		if (TheTitans.TitansFFAMode)
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.CaveSpiderTitanSorter));
		else
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
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
		return this.getMinionTypeInt() == 4 ? "thetitans:titanSpiderLiving" : (this.isPredator ? "thetitans:predatorSpider" : "mob.spider.say");
	}

	protected String getHurtSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanSpiderGrunt" : "mob.spider.say";
	}

	protected String getDeathSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanSpiderDeath" : "mob.spider.death";
	}

	protected float getSoundPitch()
	{
		return this.getMinionTypeInt() == 4 ? super.getSoundPitch() + 0.3F : ((this.isSpeedy) || (this.isJumper) ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F : (this.isTough) || (this.isStrong) ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.8F : super.getSoundPitch());
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
			return StatCollector.translateToLocal("entity.CaveSpiderPriest.name");
			case 2:
			return StatCollector.translateToLocal("entity.CaveSpiderZealot.name");
			case 3:
			return StatCollector.translateToLocal("entity.CaveSpiderBishop.name");
			case 4:
			return StatCollector.translateToLocal("entity.CaveSpiderTemplar.name");
			default:
			return StatCollector.translateToLocal("entity.CaveSpiderLoyalist.name");
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
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
			this.setHealth(20F);
			this.experienceValue = 15;
		}

		else if (miniontype == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.setHealth(80F);
			this.experienceValue = 50;
		}

		else if (miniontype == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(220.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
			this.isImmuneToFire = true;
			this.setHealth(220F);
			this.experienceValue = 100;
		}

		else if (miniontype == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1100.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.isImmuneToFire = true;
			this.setHealth(1100F);
			this.experienceValue = 500;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
			this.setHealth(16F);
			this.experienceValue = 6;
		}
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

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		this.isSpeedy = tagCompund.getBoolean("Speedy");
		this.isStrong = tagCompund.getBoolean("Strong");
		this.isPredator = tagCompund.getBoolean("Predator");
		this.isWolverine = tagCompund.getBoolean("Wolverine");
		this.isSuperSwimmer = tagCompund.getBoolean("SuperSwimmer");
		this.isLavaSwimmer = tagCompund.getBoolean("LavaSwimmer");
		this.isJumper = tagCompund.getBoolean("Jumper");
		this.isHealthy = tagCompund.getBoolean("Healthy");
		this.isShielded = tagCompund.getBoolean("Shielded");
		this.isTough = tagCompund.getBoolean("Tough");
		this.setMinionType(tagCompund.getInteger("MinionType"));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("Speedy", this.isSpeedy);
		tagCompound.setBoolean("Strong", this.isStrong);
		tagCompound.setBoolean("Predator", this.isPredator);
		tagCompound.setBoolean("Wolverine", this.isWolverine);
		tagCompound.setBoolean("SuperSwimmer", this.isSuperSwimmer);
		tagCompound.setBoolean("LavaSwimmer", this.isLavaSwimmer);
		tagCompound.setBoolean("Jumper", this.isJumper);
		tagCompound.setBoolean("Healthy", this.isHealthy);
		tagCompound.setBoolean("Shielded", this.isShielded);
		tagCompound.setBoolean("Tough", this.isTough);
		tagCompound.setInteger("MinionType", this.getMinionTypeInt());
	}

	protected void fall(float p_70069_1_)
	{
		this.moveForward = 0F;
		this.moveStrafing = 0F;
		p_70069_1_ = ForgeHooks.onLivingFall(this, p_70069_1_);
		if (p_70069_1_ <= 0.0F) return;
		PotionEffect potioneffect = getActivePotionEffect(Potion.jump);
		float f1 = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0.0F;
		int i = MathHelper.ceiling_float_int(p_70069_1_ - 3.0F - f1);
		if (i > 0)
		{
			playSound(func_146067_o(i), 1.0F, 1.0F);
			int j = MathHelper.floor_double(this.posX);
			int k = MathHelper.floor_double(this.posY - 0.20000000298023224D - this.yOffset);
			int l = MathHelper.floor_double(this.posZ);
			Block block = this.worldObj.getBlock(j, k, l);
			if (block.getMaterial() != Material.air)
			{
				Block.SoundType soundtype = block.stepSound;
				playSound(soundtype.getStepResourcePath(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
			}
		}
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

	public int getTalkInterval()
	{
		return this.isPredator ? 600 : 80;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		super.dropFewItems(p_70628_1_, p_70628_2_);
		if (p_70628_1_ && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
		{
			this.dropItem(Items.spider_eye, 1);
		}

		int j = this.rand.nextInt(3 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.melon_seeds, 1);
		}

		if ((this.rand.nextInt(60) == 0) || (this.rand.nextInt(1 + p_70628_2_) > 0))
		{
			entityDropItem(new ItemStack(Blocks.web), 0.0F);
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

	protected void jump()
	{
		this.motionY += 0.6D;
		if (isPotionActive(Potion.jump))
		{
			this.motionY += (getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
		}

		if (this.isJumper)
		{
			this.motionY += 0.6D;
			float f = this.rotationYaw * 0.017453292F;
			this.motionX -= MathHelper.sin(f) * 0.2F;
			this.motionZ += MathHelper.cos(f) * 0.2F;
		}

		this.isAirBorne = true;
		ForgeHooks.onLivingJump(this);
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityCaveSpiderMinion.class) && (p_70686_1_ != EntityCaveSpiderTitan.class);
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
					if ((entity1 instanceof EntityCaveSpiderMinion))
					{
						EntityCaveSpiderMinion entitypigzombie = (EntityCaveSpiderMinion)entity1;
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
		switch (this.rand.nextInt(6))
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
			int i = MathHelper.floor_double(p_82196_1_.posX + this.rand.nextDouble() * 2.0D);
			int j = MathHelper.floor_double(p_82196_1_.posY + this.rand.nextDouble() * 2.0D);
			int k = MathHelper.floor_double(p_82196_1_.posZ + this.rand.nextDouble() * 2.0D);
			Block block = this.worldObj.getBlock(i, j, k);
			if ((block.getMaterial() == Material.air) && (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
			{
				this.worldObj.setBlock(i, j, k, Blocks.web);
			}

			else
			{
				p_82196_1_.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 2));
			}

			break;
			case 4:p_82196_1_.setFire(10);
			p_82196_1_.attackEntityFrom(DamageSource.onFire, 20F);
			p_82196_1_.motionY = 1.0D;
			this.worldObj.playSoundEffect(p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			p_82196_1_.addPotionEffect(new PotionEffect(Potion.poison.id, 100, 2));
			break;
			case 5:this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 4f, false);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1017, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
			addPotionEffect(new PotionEffect(Potion.invisibility.id, 200));
			List<?> list11 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(64.0D, 64.0D, 64.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (((entity instanceof EntityCaveSpiderMinion)) && (entity != null))
					{
						((EntityCaveSpiderMinion)entity).addPotionEffect(new PotionEffect(Potion.invisibility.id, 200));
						this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1017, (int)entity.posX, (int)entity.posY, (int)entity.posZ, 0);
					}
				}
			}

			break;
		}
	}

	public void TransformEntity(Entity entity)
	{
		entity.worldObj.newExplosion(entity, entity.posX, entity.posY, entity.posZ, 12.0F, true, entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		EntityCaveSpiderTitan entitytitan = new EntityCaveSpiderTitan(entity.worldObj);
		entitytitan.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0F);
		entity.setDead();
		entitytitan.func_82206_m();
		entity.worldObj.spawnEntityInWorld(entitytitan);
		entitytitan.playSound("thetitans:titanSpiderLiving", 100.0F, 0.8F);
	}

	public void onLivingUpdate()
	{
		if (this.getMinionTypeInt() == 1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
			this.experienceValue = 15;
		}

		else if (this.getMinionTypeInt() == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.experienceValue = 50;
		}

		else if (this.getMinionTypeInt() == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(220.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
			this.isImmuneToFire = true;
			this.experienceValue = 100;
		}

		else if (this.getMinionTypeInt() == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1100.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.isImmuneToFire = true;
			this.experienceValue = 500;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
			this.experienceValue = 6;
		}

		if (this.isEntityAlive() || this.getMinionTypeInt() != 4)
		super.onLivingUpdate();
		if (this.getMinionTypeInt() == 3)
		{
			if (this.rand.nextInt(120) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityCaveSpiderMinion entitychicken = new EntityCaveSpiderMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(0);
				this.worldObj.spawnEntityInWorld(entitychicken);
			}

			if (this.rand.nextInt(240) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityCaveSpiderMinion entitychicken = new EntityCaveSpiderMinion(this.worldObj);
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
					EntityCaveSpiderMinion entitychicken = new EntityCaveSpiderMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(0);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(120) == 0)
				{
					EntityCaveSpiderMinion entitychicken = new EntityCaveSpiderMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(1);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(240) == 0)
				{
					EntityCaveSpiderMinion entitychicken = new EntityCaveSpiderMinion(this.worldObj);
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

	protected void updateAITasks()
	{
		if ((this.randomSoundDelay > 0) && (--this.randomSoundDelay == 0))
		{
			playSound(getHurtSound(), getSoundVolume(), getSoundPitch() + 0.25F);
		}

		if (this.isPredator)
		{
			setInvisible(true);
		}

		if ((isInWater()) && (this.isSuperSwimmer))
		{
			this.motionX *= 1.25D;
			this.motionY *= 1.25D;
			this.motionZ *= 1.25D;
			if (this.ticksExisted % 20 == 0)
			{
				heal(1.0F);
			}
		}

		if ((!handleLavaMovement()) && (this.isLavaSwimmer))
		{
			extinguish();
		}

		if ((handleLavaMovement()) && (this.isLavaSwimmer))
		{
			this.motionX *= 2.0D;
			this.motionY *= 2.0D;
			this.motionZ *= 2.0D;
			if (this.ticksExisted % 5 == 0)
			{
				heal(1.0F);
			}
		}

		if (this.isHealthy)
		{
			setSprinting(true);
		}

		if ((this.isShielded) && (getAbsorptionAmount() > 0.0F))
		{
			this.lastDamage = 300.0F;
			this.maxHurtResistantTime = 30;
		}

		if ((this.isWolverine) && (this.ticksExisted % 5 == 0))
		{
			heal(1.0F);
		}

		if (this.isTough)
		{
			getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		}

		this.stepHeight = 1.0F;
		setBesideClimbableBlock(this.isCollidedHorizontally);
		if ((getAttackTarget() != null) && (!getAttackTarget().isEntityAlive()))
		{
			setAttackTarget(null);
		}

		if (getAttackTarget() != null)
		{
			if (this.isPredator && (getAttackTarget().getDistanceSqToEntity(this) < 25.0D) && (this.rand.nextInt(10) == 0))
			{
				Vec3 vec3 = Vec3.createVectorHelper(this.posX - getAttackTarget().posX, this.boundingBox.minY + this.height / 2.0F - getAttackTarget().posY + getAttackTarget().getEyeHeight(), this.posZ - getAttackTarget().posZ);
				vec3 = vec3.normalize();
				double d0 = 16.0D;
				double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
				double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
				double d2 = this.worldObj.getTopSolidOrLiquidBlock((int)d1, (int)d3);
				EnderTeleportEvent event = new EnderTeleportEvent(this, d1, d2, d3, 0.0F);
				if (!MinecraftForge.EVENT_BUS.post(event))
				{
					this.attackEntityAsMob(getAttackTarget());
					this.posX = event.targetX;
					this.posY = event.targetY;
					this.posZ = event.targetZ;
					setPosition(this.posX, this.posY, this.posZ);
				}
			}

			if ((getAttackTarget().getDistanceSqToEntity(this) < 256.0D) && (getAttackTarget().getDistanceSqToEntity(this) > 9.0D) && (this.rand.nextInt(10) == 0))
			{
				if (this.onGround)
				{
					double d0 = getAttackTarget().posX - this.posX;
					double d1 = getAttackTarget().posZ - this.posZ;
					float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
					if (this.isSpeedy)
					{
						this.motionX = (d0 / f2 * 2.0D);
						this.motionZ = (d1 / f2 * 2.0D);
					}

					else
					{
						this.motionX = (d0 / f2 * 0.75D);
						this.motionZ = (d1 / f2 * 0.75D);
					}

					jump();
					getLookHelper().setLookPositionWithEntity(getAttackTarget(), 180.0F, 30.0F);
					getNavigator().tryMoveToEntityLiving(getAttackTarget(), 1F);
				}
			}

			if (!canEntityBeSeen(getAttackTarget()))
			{
				double d0 = getAttackTarget().posX - this.posX;
				double d1 = getAttackTarget().posZ - this.posZ;
				float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
				if (this.isSpeedy)
				{
					this.motionX = (d0 / f2 * 1.0D);
					this.motionZ = (d1 / f2 * 1.0D);
				}

				else
				{
					this.motionX = (d0 / f2 * 0.35D);
					this.motionZ = (d1 / f2 * 0.35D);
				}
			}
		}

		if ((getAttackTarget() != null) && (getDistanceSqToEntity(getAttackTarget()) > 256.0D))
		{
			getMoveHelper().setMoveTo(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1.0D);
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
					if ((entity != null) && ((entity instanceof EntityCaveSpiderTitan)))
					{
						this.master = ((EntityCaveSpiderTitan)entity);
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

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		if (this.isStrong)
		{
			p_70652_1_.motionY += 0.6D;
			playSound("mob.irongolem.throw", 1.0F, 1.0F);
		}

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

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	{
		if (this.rand.nextInt(this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 25 : this.worldObj.difficultySetting == EnumDifficulty.HARD ? 2 : 500) == 0)
		{
			switch (this.rand.nextInt(10))
			{
				case 0:addPotionEffect(new PotionEffect(Potion.moveSpeed.id, Integer.MAX_VALUE, 1));
				this.isSpeedy = true;
				break;
				case 1:addPotionEffect(new PotionEffect(Potion.damageBoost.id, Integer.MAX_VALUE, 4));
				this.isStrong = true;
				break;
				case 2:addPotionEffect(new PotionEffect(Potion.invisibility.id, Integer.MAX_VALUE, 0, false));
				this.isPredator = true;
				break;
				case 3:addPotionEffect(new PotionEffect(Potion.regeneration.id, Integer.MAX_VALUE, 9));
				this.isWolverine = true;
				break;
				case 4:addPotionEffect(new PotionEffect(Potion.waterBreathing.id, Integer.MAX_VALUE, 0));
				this.isSuperSwimmer = true;
				break;
				case 5:addPotionEffect(new PotionEffect(Potion.fireResistance.id, Integer.MAX_VALUE, 0));
				this.isLavaSwimmer = true;
				break;
				case 6:addPotionEffect(new PotionEffect(Potion.jump.id, Integer.MAX_VALUE, 2));
				this.isJumper = true;
				break;
				case 7:addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 9));
				addPotionEffect(new PotionEffect(Potion.field_76434_w.id, Integer.MAX_VALUE, 9));
				this.isHealthy = true;
				break;
				case 8:addPotionEffect(new PotionEffect(Potion.field_76444_x.id, Integer.MAX_VALUE, 9));
				this.isShielded = true;
				break;
				case 9:addPotionEffect(new PotionEffect(Potion.resistance.id, Integer.MAX_VALUE, 2));
				this.isTough = true;
			}
		}

		getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
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

	public static class GroupData
	implements IEntityLivingData
	{
		public int field_111105_a;
		public void func_111104_a(Random p_111104_1_) 
		{
			 
		}
	}

	public class EntityAIFindEntityNearestInjuredAlly extends EntityAIBase
	{
		private EntityCaveSpiderMinion field_179434_b;
		private EntityLivingBase field_179433_e;
		public EntityAIFindEntityNearestInjuredAlly(EntityCaveSpiderMinion entityCaveSpiderPriest)
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
			List<?> list = this.field_179434_b.worldObj.getEntitiesWithinAABB(EntityCaveSpiderMinion.class, this.field_179434_b.boundingBox.expand(d0, d0, d0));
			if (list.isEmpty())
			{
				return false;
			}

			for (int i = 0; i < list.size(); i++)
			{
				EntityCaveSpiderMinion entity = (EntityCaveSpiderMinion)list.get(i);
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
			return 32D;
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
		return entity instanceof EntityCaveSpiderMinion || entity instanceof EntityCaveSpiderTitan;
	}
}


