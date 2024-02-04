package net.minecraft.entity.titanminion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.entity.titan.ITitan;
import net.minecraft.entity.titan.ai.EntityAIHurtByTargetTitan;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityGhastGuard
extends EntityCreature
implements IMob, IMinion
{
	public EntityLiving master;
	public int courseChangeCooldown;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private Entity targetedEntity;
	private int aggroCooldown;
	public int prevAttackCounter;
	public int attackCounter;
	private int explosionStrength = 3;
	public EntityGhastGuard(World worldIn)
	{
		super(worldIn);
		func_110163_bv();
		setSize(4.5F, 4.5F);
		this.isImmuneToFire = true;
		this.experienceValue = 20;
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

	public boolean isAIEnabled()
	{
		return true;
	}

	public void setDead()
	{
		super.setDead();
		if ((this.master != null) && ((this.master instanceof EntityTitan)))
		{
			((EntityTitan)this.master).retractMinionNumFromType(getMinionType());
		}
	}

	public EnumMinionType getMinionType()
	{
		return EnumMinionType.SPECIAL;
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityPigZombieMinion.class) && (p_70686_1_ != EntityGhastGuard.class) && ((p_70686_1_ != EntityPigZombieTitan.class) || (p_70686_1_ == EntityZombieTitan.class));
	}

	protected void despawnEntity()
	{
		this.entityAge = 0;
	}

	protected void fall(float p_70069_1_) 
	{
		 
	}


	protected void updateFallState(double p_70064_1_, boolean p_70064_3_) 
	{
		 
	}


	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		if (isInWater())
		{
			moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.800000011920929D;
			this.motionY *= 0.800000011920929D;
			this.motionZ *= 0.800000011920929D;
		}

		else if (handleLavaMovement())
		{
			moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		}

		else
		{
			float f2 = 0.91F;
			if (this.onGround)
			{
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
			}

			float f3 = 0.16277136F / (f2 * f2 * f2);
			moveFlying(p_70612_1_, p_70612_2_, this.onGround ? 0.1F * f3 : 0.02F);
			f2 = 0.91F;
			if (this.onGround)
			{
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
			}

			moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= f2;
			this.motionY *= f2;
			this.motionZ *= f2;
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double d1 = this.posX - this.prevPosX;
		double d0 = this.posZ - this.prevPosZ;
		float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
		if (f4 > 1.0F)
		{
			f4 = 1.0F;
		}

		this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

	public boolean isOnLadder()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean func_110182_bF()
	{
		return this.dataWatcher.getWatchableObjectByte(16) != 0;
	}

	public void func_175454_a(boolean p_175454_1_)
	{
		this.dataWatcher.updateObject(16, Byte.valueOf((byte)(p_175454_1_ ? 1 : 0)));
	}

	public int func_175453_cd()
	{
		return this.explosionStrength;
	}

	public void onUpdate()
	{
		super.onUpdate();
		this.ignoreFrustumCheck = true;
		if ((!this.worldObj.isRemote) && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL))
		{
			setDead();
		}

		if (getAttackTarget() != null)
		{
			getLookHelper().setLookPosition(getAttackTarget().posX, getAttackTarget().posY + getAttackTarget().getEyeHeight(), getAttackTarget().posZ, 180.0F, 180.0F);
		}
	}

	protected void updateAITasks()
	{
		this.explosionStrength = 3;
		if (this.master != null)
		{
			if ((this.master.getAttackTarget() != null))
			{
				setAttackTarget(this.master.getAttackTarget());
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

		if ((getAttackTarget() != null) && (!canEntityBeSeen(getAttackTarget())) && (this.rand.nextInt(150) == 0))
		{
			setAttackTarget(null);
		}

		if ((!this.worldObj.isRemote) && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL))
		{
			setDead();
		}

		despawnEntity();
		this.prevAttackCounter = this.attackCounter;
		double d0 = this.waypointX - this.posX;
		double d1 = this.waypointY - this.posY;
		double d2 = this.waypointZ - this.posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		if ((d3 < 1.0D) || (d3 > 3600.0D))
		{
			if (this.master != null)
			{
				double extra = 0.0D;
				if ((getAttackTarget() != null) && (getAttackTarget().height > 4.0F))
				{
					extra = 64.0D;
				}

				double x = this.master.posX;
				double y = this.master.posY + 32.0D + extra;
				double z = this.master.posZ;
				d0 += this.rand.nextDouble() * 96D - 48D;
				d1 += this.rand.nextDouble() * 96D - 48D;
				d2 += this.rand.nextDouble() * 96D - 48D;
				this.waypointX = (x + d0);
				this.waypointY = (y + d1);
				this.waypointZ = (z + d2);
			}

			else if (getAttackTarget() != null)
			{
				this.waypointX = getAttackTarget().posX + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
				this.waypointY = getAttackTarget().posY + 32D + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
				this.waypointZ = getAttackTarget().posZ + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
			}

			else
			{
				EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 512D);
				if (player != null)
				{
					this.waypointX = player.posX + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
					this.waypointY = player.posY + 32D + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
					this.waypointZ = player.posZ + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
				}

				else
				{
					this.waypointX = this.posX + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
					this.waypointY = this.posY + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
					this.waypointZ = this.posZ + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
				}
			}
		}

		if (this.courseChangeCooldown-- <= 0)
		{
			this.courseChangeCooldown += this.rand.nextInt(5) + 2;
			d3 = MathHelper.sqrt_double(d3);
			if (isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3))
			{
				if ((this.master != null) && (getDistanceSqToEntity(this.master) > 2304.0D))
				{
					this.motionX += d0 / d3 * 0.3D;
					this.motionY += d1 / d3 * 0.3D;
					this.motionZ += d2 / d3 * 0.3D;
				}

				else
				{
					this.motionX += d0 / d3 * 0.1D;
					this.motionY += d1 / d3 * 0.1D;
					this.motionZ += d2 / d3 * 0.1D;
				}
			}

			else
			{
				this.waypointX = this.posX;
				this.waypointY = this.posY;
				this.waypointZ = this.posZ;
			}
		}

		if ((this.targetedEntity != null) && (this.targetedEntity.isDead))
		{
			this.targetedEntity = null;
		}

		if ((this.targetedEntity == null) || (this.aggroCooldown-- <= 0))
		{
			this.targetedEntity = getAttackTarget();
			if (this.targetedEntity != null)
			{
				this.aggroCooldown = 20;
			}
		}

		double d4 = 100.0D;
		if ((this.targetedEntity != null) && (this.targetedEntity.getDistanceSqToEntity(this) < d4 * d4))
		{
			double d8 = 2D;
			Vec3 vec3 = getLook(1.0F);
			double d5 = this.targetedEntity.posX - (this.posX + vec3.xCoord * d8);
			double d6 = this.targetedEntity.posY - (this.posY + vec3.yCoord * d8 + 1D);
			double d7 = this.targetedEntity.posZ - (this.posZ + vec3.zCoord * d8);
			this.renderYawOffset = (this.rotationYaw = -(float)Math.atan2(d5, d7) * 180.0F / 3.1415927F);
			if (canEntityBeSeen(this.targetedEntity))
			{
				if (this.attackCounter == 10)
				{
					this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1007, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				}

				this.attackCounter += 1;
				if (this.attackCounter == 20)
				{
					this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
					EntityGhastGuardFireball entitylargefireball = new EntityGhastGuardFireball(this.worldObj, this, d5, d6, d7);
					entitylargefireball.field_92057_e = this.explosionStrength;
					entitylargefireball.posX = (this.posX + vec3.xCoord * d8);
					entitylargefireball.posY = (this.posY + vec3.yCoord * d8 + 1D);
					entitylargefireball.posZ = (this.posZ + vec3.zCoord * d8);
					this.worldObj.spawnEntityInWorld(entitylargefireball);
					this.attackCounter = -40;
				}
			}

			else if (this.attackCounter > 0)
			{
				this.attackCounter -= 1;
			}
		}

		else
		{
			this.renderYawOffset = (this.rotationYaw = -(float)Math.atan2(this.motionX, this.motionZ) * 180.0F / 3.1415927F);
			if (this.attackCounter > 0)
			{
				this.attackCounter -= 1;
			}
		}

		if (!this.worldObj.isRemote)
		{
			byte b1 = this.dataWatcher.getWatchableObjectByte(16);
			byte b0 = (byte)(this.attackCounter > 10 ? 1 : 0);
			if (b1 != b0)
			{
				this.dataWatcher.updateObject(16, Byte.valueOf(b0));
			}
		}

		super.updateAITasks();
	}

	private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_)
	{
		double d4 = (this.waypointX - this.posX) / p_70790_7_;
		double d5 = (this.waypointY - this.posY) / p_70790_7_;
		double d6 = (this.waypointZ - this.posZ) / p_70790_7_;
		AxisAlignedBB axisalignedbb = this.boundingBox.copy();
		for (int i = 1; i < p_70790_7_; i++)
		{
			axisalignedbb.offset(d4, d5, d6);
			if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
	}

	protected String getLivingSound()
	{
		return "mob.ghast.moan";
	}

	protected String getHurtSound()
	{
		return "mob.ghast.scream";
	}

	protected String getDeathSound()
	{
		return "mob.ghast.death";
	}

	protected Item getDropItem()
	{
		return Items.gunpowder;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		int j = this.rand.nextInt(2) + this.rand.nextInt(1 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.ghast_tear, 1);
		}

		j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.gunpowder, 1);
		}
	}

	protected float getSoundVolume()
	{
		return 10.0F;
	}

	public boolean getCanSpawnHere()
	{
		return (this.rand.nextInt(20) == 0) && (super.getCanSpawnHere()) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL);
	}

	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("ExplosionPower", this.explosionStrength);
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		if (tagCompund.hasKey("ExplosionPower", 99))
		{
			this.explosionStrength = tagCompund.getInteger("ExplosionPower");
		}
	}

	public float getEyeHeight()
	{
		return 3.0F;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		if (((source.getEntity() instanceof EntityPigZombieMinion)) || ((source.getEntity() instanceof EntityPigZombieTitan)) || ((source.getEntity() instanceof EntityGhastGuard)))
		{
			return false;
		}

		if (("fireball".equals(source.getDamageType())) && ((source.getEntity() instanceof EntityPlayer)))
		{
			super.attackEntityFrom(source, 1000.0F);
			((EntityPlayer)source.getEntity()).triggerAchievement(AchievementList.ghast);
			return true;
		}

		return super.attackEntityFrom(source, amount);
	}

	class AILookAround
	extends EntityAIBase
	{
		private EntityGhastGuard field_179472_a = EntityGhastGuard.this;
		public AILookAround()
		{
			setMutexBits(2);
		}

		public boolean shouldExecute()
		{
			return true;
		}

		public void updateTask()
		{
			if (this.field_179472_a.getAttackTarget() == null)
			{
				this.field_179472_a.renderYawOffset = (this.field_179472_a.rotationYaw = -(float)Math.atan2(this.field_179472_a.motionX, this.field_179472_a.motionZ) * 180.0F / 3.1415927F);
			}

			else
			{
				EntityLivingBase entitylivingbase = this.field_179472_a.getAttackTarget();
				double d0 = 100.0D;
				if (entitylivingbase.getDistanceSqToEntity(this.field_179472_a) < d0 * d0)
				{
					double d1 = entitylivingbase.posX - this.field_179472_a.posX;
					double d2 = entitylivingbase.posZ - this.field_179472_a.posZ;
					this.field_179472_a.renderYawOffset = (this.field_179472_a.rotationYaw = -(float)Math.atan2(d1, d2) * 180.0F / 3.1415927F);
				}
			}
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
	public void setMinionType(int type) 
	{

	}
}


