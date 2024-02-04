package net.minecraft.entity.titanminion;
import java.util.IdentityHashMap;
import java.util.List;
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
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titan.EntityEnderColossus;
import net.minecraft.entity.titan.EntityEnderColossusCrystal;
import net.minecraft.entity.titan.EntityHarcadiumArrow;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.entity.titan.ITitan;
import net.minecraft.entity.titan.ai.EntityAIBreakDoorMinion;
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
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.mrbt0907.utils.Maths;
public class EntityEndermanMinion
extends EntityEnderman
implements IRangedAttackMob, ITemplar
{
	@Deprecated
	private static boolean[] carriableBlocks = new boolean[256];
	public EntityLiving master;
	public int randomSoundDelay;
	private static IdentityHashMap<Block, Boolean> carriable;
	public EntityLiving entityToHeal;
	private int attackPattern;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 5, 64F);
	public EntityEndermanMinion(World worldIn)
	{
		super(worldIn);
		setSize(0.5F, 2.88F);
		this.stepHeight = 1.0F;
		getNavigator().setBreakDoors(true);
		getNavigator().setEnterDoors(true);
		getNavigator().setCanSwim(true);
		getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityWitherSkull.class, 2.0F, 1.0D, 1.75D));
		this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityTitanSpirit.class, 48.0F, 1.5D, 1.5D));
		this.tasks.addTask(0, new EntityAIBreakDoorMinion(this));
		this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.2D));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, true));
		this.tasks.addTask(7, new EntityAIWander(this, 0.5D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTargetTitan(this, true, new Class[] 
		{
			EntityEndermanMinion.class, EntityEnderColossus.class, EntityDragonMinion.class
		}
		));
		if (TheTitans.TitansFFAMode)
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.EnderColossusSorter));
		else
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLiving.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	public String getCommandSenderName()
	{
		switch (this.getMinionTypeInt())
		{
			case 1:
			return StatCollector.translateToLocal("entity.EndermanPriest.name");
			case 2:
			return StatCollector.translateToLocal("entity.EndermanZealot.name");
			case 3:
			return StatCollector.translateToLocal("entity.EndermanBishop.name");
			case 4:
			return StatCollector.translateToLocal("entity.EndermanTemplar.name");
			default:
			return StatCollector.translateToLocal("entity.EndermanLoyalist.name");
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

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
		this.setMinionType(this.getMinionTypeInt());
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityEnderman.class) && (p_70686_1_ != EntityEndermanMinion.class) && (p_70686_1_ != EntityEnderColossus.class) && (p_70686_1_ != EntityDragon.class) && (p_70686_1_ != EntityDragonMinion.class) && (p_70686_1_ != EntityEnderColossusCrystal.class);
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
		return this.getMinionTypeInt() == 4 ? (isScreaming() ? "thetitans:titanEnderColossusScreamLong" : "thetitans:titanEnderColossusLiving") : (isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle");
	}

	protected String getHurtSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanEnderColossusGrunt" : "mob.endermen.hit";
	}

	protected String getDeathSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanEnderColossusDeath" : "mob.endermen.death";
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
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(90.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(9.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
			this.setHealth(90F);
			this.experienceValue = 30;
		}

		else if (miniontype == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(700.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.setHealth(700F);
			this.experienceValue = 200;
		}

		else if (miniontype == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1600.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
			this.isImmuneToFire = true;
			this.setHealth(1600F);
			this.experienceValue = 500;
		}

		else if (miniontype == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(3000.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(100.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
			this.isImmuneToFire = true;
			this.setHealth(3000F);
			this.experienceValue = 3000;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
			this.setHealth(60F);
			this.experienceValue = 15;
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

	protected Entity findPlayerToLookAt()
	{
		EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 64.0D);
		if (entityplayer != null)
		{
			if ((isPlayerRegistered(entityplayer)) && !entityplayer.capabilities.disableDamage && (getAttackTarget() == null))
			{
				playSound("mob.endermen.stare", 10.0F, 1.0F);
				setScreaming(true);
				setAttackTarget(entityplayer);
				faceEntity(entityplayer, 180.0F, 180.0F);
			}
		}

		return null;
	}

	protected boolean isPlayerRegistered(EntityPlayer p_70821_1_)
	{
		ItemStack itemstack = p_70821_1_.inventory.armorInventory[3];
		if ((itemstack != null) && (itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)))
		{
			return false;
		}

		Vec3 vec3 = p_70821_1_.getLook(1.0F).normalize();
		Vec3 vec31 = Vec3.createVectorHelper(this.posX - p_70821_1_.posX, this.boundingBox.minY + getEyeHeight() - (p_70821_1_.posY + p_70821_1_.getEyeHeight()), this.posZ - p_70821_1_.posZ);
		double d0 = vec31.lengthVector();
		vec31 = vec31.normalize();
		double d1 = vec3.dotProduct(vec31);
		return d1 > 1.0D - 0.025D / d0 ? p_70821_1_.canEntityBeSeen(this) : false;
	}

	public float getEyeHeight()
	{
		return 2.55F;
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		boolean flag = super.attackEntityAsMob(p_70652_1_);
		if (flag)
		{
			if ((p_70652_1_ instanceof EntityMob || p_70652_1_ instanceof EntityGolem || p_70652_1_ instanceof EntityPlayer))
			teleportRandomly();
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
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, b0 * 20, 1));
				}
			}
		}

		return flag;
	}

	public void onLivingUpdate()
	{
		this.entityToAttack = null;
		if (this.getMinionTypeInt() == 1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(90.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(9.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
			this.experienceValue = 30;
		}

		else if (this.getMinionTypeInt() == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(700.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.experienceValue = 200;
		}

		else if (this.getMinionTypeInt() == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1600.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
			this.isImmuneToFire = true;
			this.experienceValue = 500;
		}

		else if (this.getMinionTypeInt() == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(3000.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(100.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
			this.isImmuneToFire = true;
			this.experienceValue = 3000;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
			this.experienceValue = 15;
		}

		if (this.getMinionTypeInt() == 3)
		{
			if (this.rand.nextInt(120) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityEndermanMinion entitychicken = new EntityEndermanMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(0);
				this.worldObj.spawnEntityInWorld(entitychicken);
			}

			if (this.rand.nextInt(240) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityEndermanMinion entitychicken = new EntityEndermanMinion(this.worldObj);
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
					EntityEndermanMinion entitychicken = new EntityEndermanMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(0);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(120) == 0)
				{
					EntityEndermanMinion entitychicken = new EntityEndermanMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(1);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(240) == 0)
				{
					EntityEndermanMinion entitychicken = new EntityEndermanMinion(this.worldObj);
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

			if (this.onGround)
			{
				this.isAirBorne = false;
			}

			if (this.isEntityAlive() && (!this.worldObj.isRemote) && (this.rand.nextInt(1000) == 0) && (getAttackTarget() != null) && (getHealth() < getMaxHealth() / 2.0F) && (this.master == null))
			{
				for (int i = 0; i < 16; i++)
				{
					this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("magicCrit", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
				}

				playSound("thetitans:titanland", 10000.0F, 1.0F);
				TransformEntity(this);
			}

			EntityLivingBase entitylivingbase = getAttackTarget();
			if ((this.attackPattern == 0) && (entitylivingbase != null) && !this.worldObj.isRemote)
			{
				if (this.posY < entitylivingbase.posY + entitylivingbase.height + 4D)
				{
					if (this.motionY < 0.0D)
					{
						this.motionY = 0.0D;
					}

					this.motionY += (0.5D - this.motionY);
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

		if (this.getAttackTarget() != null)
		{
			if (this.worldObj.rand.nextInt(5) == 1)
			{
				EntityLivingBase e = this.getAttackTarget();
				if (e != null)
				{
					if (getDistanceToEntity(e) < this.width + e.width + 1D)
					{
						if ((this.worldObj.rand.nextInt(3) == 0) || (this.worldObj.rand.nextInt(2) == 1))
						{
							if (attackEntityAsMob(e) && this.rand.nextInt(5) == 0)
							{
								teleportRandomly();
							}
						}
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
					teleportToEntity(getAttackTarget());
					teleportRandomly();
					attackEntityAsMob(getAttackTarget());
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
					this.entityToHeal.heal(5.0F);
					this.entityToHeal.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 1));
					playSound("mob.wither.shoot", 1.0F, 3.0F);
					for (int i = 0; i < 10; i++)
					{
						double d0 = this.rand.nextGaussian() * 0.02D;
						double d1 = this.rand.nextGaussian() * 0.02D;
						double d2 = this.rand.nextGaussian() * 0.02D;
						this.worldObj.spawnParticle("heart", this.entityToHeal.posX + this.rand.nextFloat() * this.entityToHeal.width * 2.0F - this.entityToHeal.width, this.entityToHeal.posY + 0.5D + this.rand.nextFloat() * this.entityToHeal.height, this.entityToHeal.posZ + this.rand.nextFloat() * this.entityToHeal.width * 2.0F - this.entityToHeal.width, d0, d1, d2);
					}
				}

				else
				{
					this.entityToHeal = null;
				}
			}
		}

		findPlayerToLookAt();
		if (getAttackTarget() != null && this.getAttackTarget().getDistanceSqToEntity(this) > 256.0D && this.ticksExisted % 30 == 0 && !this.worldObj.isRemote)
		{
			this.teleportToEntity(this.getAttackTarget());
		}

		if ((this.worldObj.isDaytime()) && (!this.worldObj.isRemote))
		{
			float f = getBrightness(1.0F);
			if ((f > 0.5F) && (this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) && (this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F))
			{
				this.entityToAttack = null;
				setScreaming(false);
				teleportRandomly();
			}
		}

		if ((isWet() || isBurning()) && this.getMinionType() != EnumMinionType.TEMPLAR)
		{
			setAttackTarget(null);
			setScreaming(true);
			teleportRandomly();
		}

		if (isWet() && this.getMinionType() != EnumMinionType.TEMPLAR)
		{
			setScreaming(true);
			teleportRandomly();
			attackEntityFrom(DamageSource.onFire, 4.0F);
			this.hurtTime = 1;
			this.limbSwingAmount = 1.5F;
			if (this.worldObj.isRemote)
			{
				for (int i = 0; i < 15; i++)
				{
					if (getHealth() <= 20.0F)
					{
						this.worldObj.spawnParticle("lava", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);

					}

					 else 
					{

						this.worldObj.spawnParticle("flame", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
					}

					this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
				}
			}

			if (this.rand.nextInt(10) == 0)
			{
				setFire(1);
			}

			if (((this.rand.nextInt(60) == 0) || ((this.rand.nextInt(10) == 0) && (getHealth() <= 15.0F))) && !this.worldObj.isRemote)
			{
				this.worldObj.newExplosion(this, this.posX + (this.rand.nextDouble() * 1.0D - 0.5D), this.posY + this.rand.nextDouble() * 3.0D, this.posZ + (this.rand.nextDouble() * 1.0D - 0.5D), 0.0F, true, true);
			}

			if (((this.rand.nextInt(1000) == 0) || (getHealth() <= 1.0F)) && !this.worldObj.isRemote)
			{
				playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
				this.worldObj.newExplosion(this, this.posX, this.posY + 1.0D, this.posZ, 2.0F, true, true);
				this.motionY += 3.0D;
				attackEntityFrom(DamageSource.onFire, Float.MAX_VALUE);
			}
		}

		if (getAttackTarget() != null)
		{
			setScreaming(true);
		}

		else if (!this.isWet() && !this.isBurning() && this.getHealth() <= this.getMaxHealth() / 10F)
		{
			setScreaming(false);
		}

		if (this.worldObj.isRemote)
		{
			for (int i = 0; i < 2; i++)
			{
				this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
			}
		}

		this.isJumping = false;
		if ((getAttackTarget() != null) && (getRNG().nextInt(20) == 0))
		{
			if ((getAttackTarget().getDistanceSqToEntity(this) < 2.0D) && (!this.worldObj.isRemote) && !(this.getMinionType() == EnumMinionType.TEMPLAR))
			{
				teleportRandomly();
			}
		}

		if ((this.master != null) && (getDistanceSqToEntity(this.master) > 1024.0D) && (!this.worldObj.isRemote))
		{
			teleportToEntity(this.master);
		}

		super.onLivingUpdate();
	}

	protected void updateAITasks()
	{
		if ((this.randomSoundDelay > 0) && (--this.randomSoundDelay == 0))
		{
			playSound("mob.endermen.scream", getSoundVolume(), getSoundPitch() + 0.25F);
		}

		if (this.isCollidedHorizontally && this.master != null)
		this.motionY = 0.2D;
		if ((getAttackTarget() != null) && (!getAttackTarget().isEntityAlive()))
		{
			setAttackTarget(null);
		}

		if (this.master != null)
		{
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
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(256.0D, 256.0D, 256.0D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if ((entity != null) && ((entity instanceof EntityEnderColossus)))
					{
						this.master = ((EntityEnderColossus)entity);
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

	protected boolean teleportRandomly()
	{
		double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
		double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
		return this.isEntityAlive() && teleportTo(d0, d1, d2);
	}

	protected boolean teleportToEntity(Entity p_70816_1_)
	{
		Vec3 vec3 = Vec3.createVectorHelper(this.posX - p_70816_1_.posX, this.boundingBox.minY + this.height / 2.0F - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
		vec3 = vec3.normalize();
		double d0 = 16.0D;
		double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
		double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
		double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
		return this.isEntityAlive() && teleportTo(d1, d2, d3);
	}

	protected boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_, 0.0F);
		if (MinecraftForge.EVENT_BUS.post(event))
		{
			return false;
		}

		double d3 = this.posX;
		double d4 = this.posY;
		double d5 = this.posZ;
		this.posX = event.targetX;
		this.posY = event.targetY;
		this.posZ = event.targetZ;
		boolean flag = false;
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posY);
		int k = MathHelper.floor_double(this.posZ);
		if (this.worldObj.blockExists(i, j, k))
		{
			boolean flag1 = false;
			while ((!flag1) && (j > 0))
			{
				Block block = this.worldObj.getBlock(i, j - 1, k);
				if (block.getMaterial().blocksMovement())
				{
					flag1 = true;
				}

				else
				{
					this.posY -= 1.0D;
					j--;
				}
			}

			if (flag1)
			{
				setPosition(this.posX, this.posY, this.posZ);
				if ((this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(this.boundingBox)))
				{
					flag = true;
				}
			}
		}

		if (!flag)
		{
			setPosition(d3, d4, d5);
			return false;
		}

		short short1 = 128;
		for (int l = 0; l < short1; l++)
		{
			double d6 = l / (short1 - 1.0D);
			float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
			float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
			float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
			double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
			double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * this.height;
			double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
			this.worldObj.spawnParticle("portal", d7, d8, d9, f, f1, f2);
			if (this.isWet())
			{
				if (getHealth() <= 20.0F)
				{
					this.worldObj.spawnParticle("lava", d7, d8, d9, f, f1, f2);

				}

				 else 
				{

					this.worldObj.spawnParticle("flame", d7, d8, d9, f, f1, f2);
				}

				this.worldObj.spawnParticle("largesmoke", d7, d8, d9, f, f1, f2);
			}
		}

		this.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
		playSound("mob.endermen.portal", 1.0F, 1.0F);
		return true;
	}

	protected Item getDropItem()
	{
		return Items.ender_pearl;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		super.dropFewItems(p_70628_1_, p_70628_2_);
		int j = this.rand.nextInt(2 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.ender_eye, 1);
		}

		j = this.rand.nextInt(3 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.coal, 1);
		}

		if ((this.rand.nextInt(5) == 0) || (this.rand.nextInt(1 + p_70628_2_) > 0))
		{
			entityDropItem(new ItemStack(Blocks.end_stone), 0.0F);
		}

		if ((this.rand.nextInt(20) == 0) || (this.rand.nextInt(1 + p_70628_2_) > 0))
		{
			entityDropItem(new ItemStack(Blocks.obsidian), 0.0F);
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

	protected void fall(float p_70069_1_)
	{
		if (this.getMinionTypeInt() != 4)
		super.fall(p_70069_1_);
		this.moveForward = 0F;
		this.moveStrafing = 0F;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if ((source instanceof EntityDamageSourceIndirect))
		{
			if (!this.worldObj.isRemote && !(this.getMinionType() == EnumMinionType.TEMPLAR))
			{
				for (int i = 0; i < 64; ++i)
				{
					this.teleportRandomly();
				}
			}

			return false;
		}

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

			if (!this.worldObj.isRemote)
			{
				setScreaming(true);
			}

			if (((source instanceof EntityDamageSource)) && ((source.getEntity() instanceof EntityPlayer)))
			{
				if (((source.getEntity() instanceof EntityPlayerMP)) && (((EntityPlayerMP)source.getEntity()).theItemInWorldManager.isCreative()))
				{
					setScreaming(false);
				}

				else
				{
				}
			}
		}

		boolean flag = super.attackEntityFrom(source, amount);
		if (this.rand.nextInt(2) != 0 && !(this.getMinionType() == EnumMinionType.TEMPLAR))
		{
			teleportRandomly();
		}

		Entity entity = source.getEntity();
		if ((entity instanceof EntityLivingBase))
		{
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(64.0D, 64.0D, 64.0D));
			for (int i = 0; i < list.size(); i++)
			{
				Entity entity1 = (Entity)list.get(i);
				if ((entity1 instanceof EntityEndermanMinion))
				{
					EntityEndermanMinion entitypigzombie = (EntityEndermanMinion)entity1;
					entitypigzombie.setAttackTarget((EntityLivingBase)entity);
					entitypigzombie.setRevengeTarget((EntityLivingBase)entity);
					entitypigzombie.randomSoundDelay = this.rand.nextInt(40);
				}

				setAttackTarget((EntityLivingBase)entity);
				setRevengeTarget((EntityLivingBase)entity);
				randomSoundDelay = this.rand.nextInt(40);
			}
		}

		return flag;
	}

	static
	{
		carriableBlocks[Block.getIdFromBlock(Blocks.grass)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.dirt)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.sand)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.gravel)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.yellow_flower)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.red_flower)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.brown_mushroom)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.red_mushroom)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.tnt)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.cactus)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.clay)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.pumpkin)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.melon_block)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.mycelium)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.crafting_table)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.diamond_block)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.furnace)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.lit_furnace)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.chest)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.gold_block)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.log)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.log2)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.leaves)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.leaves2)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.iron_block)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.planks)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.torch)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.anvil)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.brewing_stand)] = true;
		carriableBlocks[Block.getIdFromBlock(Blocks.bookshelf)] = true;
		for (int x = 0; x < carriableBlocks.length; x++)
		{
			if (carriableBlocks[x]) setCarriable(Block.getBlockById(x), true);
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

	public static void setCarriable(Block block, boolean canCarry)
	{
		if (carriable == null) carriable = new IdentityHashMap<Block, Boolean>(4096);
		carriable.put(block, Boolean.valueOf(canCarry));
	}

	public static boolean getCarriable(Block block)
	{
		Boolean ret = (Boolean)carriable.get(block);
		return ret != null ? ret.booleanValue() : false;
	}

	public void TransformEntity(Entity entity)
	{
		entity.worldObj.newExplosion(entity, entity.posX, entity.posY, entity.posZ, 45.0F, true, entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		EntityEnderColossus entitytitan = new EntityEnderColossus(entity.worldObj);
		entitytitan.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0F);
		entity.setDead();
		entitytitan.func_82206_m();
		entity.worldObj.spawnEntityInWorld(entitytitan);
		entitytitan.playSound("thetitans:titanEnderColossusRoar", 100.0F, 0.5F);
		entitytitan.setRoarCooldownTimer(1310);
		entitytitan.setScreaming(true);
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		this.swingItem();
		if (getDistanceSqToEntity(p_82196_1_) < (this.width * this.width) + (p_82196_1_.width * p_82196_1_.width) + 45D)
		attackEntityAsMob(p_82196_1_);
		else
		switch (this.rand.nextInt(5))
		{
			case 0:this.worldObj.newExplosion(this, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ, 1.0F * p_82196_1_.width, false, false);
			p_82196_1_.attackEntityFrom(DamageSourceExtra.lightningBolt, 49.0F);
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
			break;
			case 1:for (int i = 0; i < 100; i++)
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
			case 2:for (int i = 0; i < 200; i++)
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
			case 3:for (int i = 0; i < 50; i++)
			{
				getDistanceSqToEntity(p_82196_1_);
				double d11 = p_82196_1_.posX - this.posX;
				double d21 = p_82196_1_.boundingBox.minY + p_82196_1_.height / 2.0F - (this.posY + p_82196_1_.height / 2.0F);
				double d31 = p_82196_1_.posZ - this.posZ;
				EntityGhastGuardFireball entitysmallfireball = new EntityGhastGuardFireball(this.worldObj, this, d11 + (getRNG().nextGaussian() * 9F), d21, d31 + (getRNG().nextGaussian() * 9F));
				entitysmallfireball.posY = (this.posY + 2D);
				this.worldObj.spawnEntityInWorld(entitysmallfireball);
			}

			break;
			case 4:this.worldObj.newExplosion(this, p_82196_1_.posX, p_82196_1_.posY + 1D, p_82196_1_.posZ, 2.0F, false, false);
			p_82196_1_.attackEntityFrom(DamageSourceExtra.lightningBolt, 100.0F);
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
		}
	}

	public class EntityAIFindEntityNearestInjuredAlly extends EntityAIBase
	{
		private EntityEndermanMinion field_179434_b;
		private EntityLivingBase field_179433_e;
		public EntityAIFindEntityNearestInjuredAlly(EntityEndermanMinion entityCaveSpiderPriest)
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
			List<?> list = this.field_179434_b.worldObj.getEntitiesWithinAABB(EntityEndermanMinion.class, this.field_179434_b.boundingBox.expand(d0, d0, d0));
			if (list.isEmpty())
			{
				return false;
			}

			for (int i = 0; i < list.size(); i++)
			{
				EntityEndermanMinion entity = (EntityEndermanMinion)list.get(i);
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
				this.field_179433_e = null;
				return false;
			}

			if (entitylivingbase.getHealth() >= entitylivingbase.getMaxHealth())
			{
				this.field_179433_e = null;
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
			return 48.0D;
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
	public String getSummonSound() 
	{

		return "mob.endermen.portal";
	}

	@Override
	public float getSummonVolume() 
	{

		return 10F;
	}

	@Override
	public float getSummonPitch() 
	{

		return 0.5F;
	}

	@Override
	public boolean startGrounded() 
	{
		return false;
	}

	@Override
	public boolean isFriendly(Entity entity)
	{
		if (entity == null)
		return false;
		return entity instanceof EntityEndermanMinion || entity instanceof EntityEnderColossus || entity instanceof EntityDragon;
	}
}


