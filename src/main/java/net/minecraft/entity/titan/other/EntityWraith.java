package net.minecraft.entity.titan.other;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityWraith extends EntityMob implements IEntityLivingData
{
	public int courseChangeCooldown;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	public EntityWraith(World p_i1745_1_)
	{
		super(p_i1745_1_);
		this.isImmuneToFire = true;
		this.noClip = true;
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, false));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
		this.experienceValue = 20;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8D);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(19, Integer.valueOf(0));
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		this.setMobType(tagCompund.getInteger("Type"));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("Type", this.getMobType());
	}

	public int getMobType()
	{
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	public void setMobType(int miniontype)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(miniontype));
	}

	/**
	* Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
	*/
	public int getTotalArmorValue()
	{
		int i = super.getTotalArmorValue() + 4;
		if (i > 20)
		{
			i = 20;
		}

		return i;
	}

	public String getCommandSenderName()
	{
		switch (this.getMobType())
		{
			case 1:
			return StatCollector.translateToLocal("entity.SpiderGiant.name");
			case 2:
			return StatCollector.translateToLocal("entity.SpiderLarge.name");
			case 3:
			return StatCollector.translateToLocal("entity.SpiderSmall.name");
			case 4:
			return StatCollector.translateToLocal("entity.SpiderArmored.name");
			case 5:
			return StatCollector.translateToLocal("entity.SpiderOrb.name");
			default:
			return StatCollector.translateToLocal("entity.SpiderUndead.name");
		}
	}

	/**
	* Returns true if the newer Entity AI code should be run
	*/
	protected boolean isAIEnabled()
	{
		return true;
	}

	/**
	* Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	* use this to react to sunlight and start to burn.
	*/
	public void onLivingUpdate()
	{
		if (this.deathTime == 1)
		this.deathTime = 19;
		if (this.deathTime == 20)
		{
			for (int i = 0; i < 200; ++i)
			{
				double d2 = this.rand.nextGaussian() * 0.1D;
				double d0 = 0.1D + this.rand.nextGaussian() * 0.01D;
				double d1 = this.rand.nextGaussian() * 0.1D;
				this.worldObj.spawnParticle("largesmoke", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
			}
		}

		if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
		{
			float f = this.getBrightness(1.0F);
			if (f < 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				if (this.ticksExisted % 5 == 0)
				{
					this.heal(2F);
				}
			}
		}

		this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
		super.onLivingUpdate();
	}

	public boolean isEntityInsideOpaqueBlock()
	{
		return false;
	}

	protected void updateAITasks()
	{
		if (this.getAttackTarget() != null && this.ticksExisted % 20 == 0 && getDistanceSqToEntity(this.getAttackTarget()) <= (2F + this.getAttackTarget().width / 2.0F) * (2F + this.getAttackTarget().width / 2.0F))
		this.attackEntityAsMob(getAttackTarget());
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote)
		{
			float f = this.getBrightness(1.0F);
			if (f > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 2D), MathHelper.floor_double(this.posZ)))
			{
				this.motionX = 0.0F;
				this.motionY = 0.0F;
				this.motionZ = 0.0F;
				this.waypointX = this.posX;
				this.waypointY = this.posY - (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.5F);
				this.waypointZ = this.posZ;
				this.attackEntityFrom(DamageSource.outOfWorld, 4);
			}
		}

		if (this.getAttackTarget() == null)
		this.getLookHelper().setLookPosition(waypointX, waypointY, waypointZ, 20F, 0F);
		double d0 = this.waypointX - this.posX;
		double d1 = this.waypointY - this.posY;
		double d2 = this.waypointZ - this.posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		if (d3 < 10.0D || d3 > 3600.0D)
		{
			if (this.getAttackTarget() != null)
			{
				this.waypointX = this.getAttackTarget().posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 1F);
				this.waypointY = this.getAttackTarget().posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 2F);
				this.waypointZ = this.getAttackTarget().posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 1F);
			}

			else
			{
				this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16F);
				this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16F);
				this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16F);
			}
		}

		if (this.courseChangeCooldown-- <= 0)
		{
			this.courseChangeCooldown += this.rand.nextInt(5) + 2;
			d3 = (double)MathHelper.sqrt_double(d3);
			this.motionX += d0 / d3 * 0.1D;
			this.motionY += d1 / d3 * 0.1D;
			this.motionZ += d2 / d3 * 0.1D;
		}

		super.updateAITasks();
	}

	/**
	* Called when the mob is falling. Calculates and applies fall damage.
	*/
	protected void fall(float p_70069_1_) 
	{
		 
	}


	/**
	* Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
	* and deal fall damage if landing on the ground.Args: distanceFallenThisTick, onGround
	*/
	protected void updateFallState(double p_70064_1_, boolean p_70064_3_) 
	{
		 
	}


	/**
	* Moves the entity based on the specified heading.Args: strafe, forward
	*/
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		this.onGround = false;
		this.isAirBorne = true;
		if (this.isInWater())
		{
			this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.800000011920929D;
			this.motionY *= 0.800000011920929D;
			this.motionZ *= 0.800000011920929D;
		}

		else if (this.handleLavaMovement())
		{
			this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
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
			this.moveFlying(p_70612_1_, p_70612_2_, this.onGround ? 0.1F * f3 : 0.02F);
			f2 = 0.91F;
			if (this.onGround)
			{
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)f2;
			this.motionY *= (double)f2;
			this.motionZ *= (double)f2;
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

	/**
	* returns true if this entity is by a ladder, false otherwise
	*/
	public boolean isOnLadder()
	{
		return false;
	}

	/**
	* Sets the Entity inside a web block.
	*/
	public void setInWeb() 
	{
		 
	}


	/**
	* Called when the entity is attacked.
	*/
	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}

		else if (p_70097_1_ instanceof EntityDamageSourceIndirect)
		{
			return false;
		}

		else
		{
			return super.attackEntityFrom(p_70097_1_, p_70097_2_);
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		boolean flag = super.attackEntityAsMob(p_70652_1_);
		if (flag)
		{
			this.worldObj.difficultySetting.getDifficultyId();
			this.getHeldItem();
			if (p_70652_1_ instanceof EntityLivingBase)
			{
				byte b0 = 0;
				if (this.worldObj.difficultySetting == EnumDifficulty.EASY)
				{
					b0 = 10;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					b0 = 30;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					b0 = 60;
				}

				if (b0 > 0)
				{
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.blindness.id, b0 * 10, 0));
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.confusion.id, b0 * 20, 0));
				}
			}

			return true;
		}

		return flag;
	}

	/**
	* Returns the sound this mob makes while it's alive.
	*/
	protected String getLivingSound()
	{
		return "mob.wither.idle";
	}

	/**
	* Returns the sound this mob makes when it is hurt.
	*/
	protected String getHurtSound()
	{
		return "thetitans:wraithHurt";
	}

	/**
	* Returns the sound this mob makes on death.
	*/
	protected String getDeathSound()
	{
		return "thetitans:wraithDeath";
	}

	protected float getSoundVolume()
	{
		return 2F;
	}

	protected Item getDropItem()
	{
		return Items.rotten_flesh;
	}

	/**
	* Get this Entity's EnumCreatureAttribute
	*/
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	protected void dropRareDrop(int p_70600_1_)
	{
		switch (this.rand.nextInt(4))
		{
			case 0:
			this.dropItem(Items.iron_ingot, 1);
			break;
			case 1:
			this.dropItem(Items.gold_ingot, 1);
			break;
			case 2:
			this.dropItem(Items.emerald, 1);
			break;
			case 3:
			this.dropItem(Items.diamond, 1);
			break;
		}
	}

	/**
	* Determines if an entity can be despawned, used on idle far away entities
	*/
	protected boolean canDespawn()
	{
		float f = this.getBrightness(1.0F);
		return f > 0.5F;
	}
}


