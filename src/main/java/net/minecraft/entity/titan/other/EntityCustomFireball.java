package net.minecraft.entity.titan.other;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityCustomFireball extends EntityFireball
{
	public float explosionRadius;
	public float impactDamage;
	public boolean canCauseFires;
	public EntityCustomFireball(World worldIn)
	{
		super(worldIn);
	}

	public EntityCustomFireball(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_)
	{
		super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
	}

	public EntityCustomFireball(World worldIn, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_)
	{
		super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
	}

	public EntityCustomFireball(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_, int id)
	{
		this(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
		this.setFireballID(id);
	}

	public void onImpactPublic(EntityLivingBase movingObject)
	{
		if (movingObject != null)
		{
			if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving && ((EntityLiving)this.shootingEntity).canAttackClass(movingObject.getClass()))
			{
				if (this.shootingEntity instanceof EntityTitan)
				((EntityTitan)this.shootingEntity).attackChoosenEntity(movingObject, impactDamage, (int)explosionRadius);
				else
				((EntityLiving)this.shootingEntity).attackEntityAsMob(movingObject);
				playSound("random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
				this.setDead();
			}
		}
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
		if (!this.worldObj.isRemote)
		{
			if (movingObject.entityHit != null && movingObject.entityHit instanceof EntityFireball)
			{
				return;
			}

			if (movingObject.entityHit != null)
			{
				if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving && ((EntityLiving)this.shootingEntity).canAttackClass(movingObject.entityHit.getClass()))
				{
					this.onImpactPublic((EntityLivingBase) movingObject.entityHit);
				}
			}

			else
			{
				this.setDead();
			}
		}
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return false;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(21, new Integer(0));
	}

	public int getFireballID()
	{
		return this.dataWatcher.getWatchableObjectInt(21);
	}

	public void setFireballID(int p_82215_1_)
	{
		this.dataWatcher.updateObject(21, Integer.valueOf(p_82215_1_));
	}

	/**
	* (abstract) Protected helper method to write subclass entity data to NBT.
	*/
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("FireballID", this.getFireballID());
	}

	/**
	* (abstract) Protected helper method to read subclass entity data from NBT.
	*/
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		if (p_70037_1_.hasKey("FireballID", 99))
		{
			this.setFireballID(p_70037_1_.getInteger("FireballID"));
		}
	}

	public boolean isBurning()
	{
		return this.canCauseFires;
	}

	public void setFire(int p_70015_1_)
	{
		if (this.canCauseFires)
		super.setFire(p_70015_1_);
	}

	public void onUpdate()
	{
		if (this.shootingEntity != null)
		{
			if (this.shootingEntity instanceof EntityMagicUser)
			this.setFireballID(0);
		}

		else
		{
			if (!this.worldObj.isRemote)
			this.setDead();
		}

		switch (this.getFireballID())
		{
			case 1:this.setSize(1.5F, 1.5F);
			this.impactDamage = 200F;
			this.canCauseFires = false;
			this.explosionRadius = 3F;
			break;
			case 2:this.setSize(2F, 2F);
			this.impactDamage = 600F;
			this.canCauseFires = true;
			this.explosionRadius = 3F;
			break;
			case 3:this.setSize(1.5F, 1.5F);
			this.impactDamage = 300F;
			this.canCauseFires = true;
			this.explosionRadius = 4F;
			break;
			case 4:this.setSize(4F, 4F);
			this.impactDamage = 1500F;
			this.canCauseFires = false;
			this.explosionRadius = 8F;
			break;
			case 5:this.setSize(6F, 6F);
			this.impactDamage = 10000F;
			this.canCauseFires = false;
			this.explosionRadius = 12F;
			break;
			case 6:this.setSize(2F, 2F);
			this.impactDamage = 60F;
			this.canCauseFires = false;
			this.explosionRadius = 1F;
			break;
			default:this.setSize(6F, 6F);
			this.impactDamage = 10000F;
			this.canCauseFires = true;
			this.explosionRadius = 12F;
		}

		super.onUpdate();
	}
}


