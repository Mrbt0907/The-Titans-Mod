package net.minecraft.entity.titan;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityWitherzillaSkull extends Entity
{
	public EntityLivingBase shootingEntity;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double p_70112_1_)
	{
		return true;
	}

	protected void entityInit() 
	{
		 
	}


	public EntityWitherzillaSkull(World worldIn)
	{
		super(worldIn);
		this.setSize(64F, 64F);
	}

	public EntityWitherzillaSkull(World p_i1761_1_, EntityLivingBase p_i1761_2_, double p_i1761_3_, double p_i1761_5_, double p_i1761_7_)
	{
		super(p_i1761_1_);
		this.shootingEntity = p_i1761_2_;
		this.setLocationAndAngles(p_i1761_2_.posX, p_i1761_2_.posY, p_i1761_2_.posZ, p_i1761_2_.rotationYaw, p_i1761_2_.rotationPitch);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = this.motionY = this.motionZ = 0.0D;
		p_i1761_3_ += this.rand.nextGaussian() * 0.4D;
		p_i1761_5_ += this.rand.nextGaussian() * 0.4D;
		p_i1761_7_ += this.rand.nextGaussian() * 0.4D;
		double d3 = (double)MathHelper.sqrt_double(p_i1761_3_ * p_i1761_3_ + p_i1761_5_ * p_i1761_5_ + p_i1761_7_ * p_i1761_7_);
		this.accelerationX = p_i1761_3_ / d3 * 0.1D;
		this.accelerationY = p_i1761_5_ / d3 * 0.1D;
		this.accelerationZ = p_i1761_7_ / d3 * 0.1D;
		this.setSize(64F, 64F);
	}

	public EntityWitherzillaSkull(World p_i1760_1_, double p_i1760_2_, double p_i1760_4_, double p_i1760_6_, double p_i1760_8_, double p_i1760_10_, double p_i1760_12_)
	{
		super(p_i1760_1_);
		this.setSize(4F, 4F);
		this.setLocationAndAngles(p_i1760_2_, p_i1760_4_, p_i1760_6_, this.rotationYaw, this.rotationPitch);
		this.setPosition(p_i1760_2_, p_i1760_4_, p_i1760_6_);
		double d6 = (double)MathHelper.sqrt_double(p_i1760_8_ * p_i1760_8_ + p_i1760_10_ * p_i1760_10_ + p_i1760_12_ * p_i1760_12_);
		this.accelerationX = p_i1760_8_ / d6 * 0.1D;
		this.accelerationY = p_i1760_10_ / d6 * 0.1D;
		this.accelerationZ = p_i1760_12_ / d6 * 0.1D;
	}

	public void onImpact(MovingObjectPosition movingObject)
	{
		if (!this.worldObj.isRemote)
		{
			if ((movingObject.entityHit != null) && this.shootingEntity != null && this.shootingEntity instanceof EntityLiving && movingObject.entityHit instanceof EntityLivingBase && ((EntityLiving)this.shootingEntity).canAttackClass(movingObject.entityHit.getClass()))
			{
				if (this.shootingEntity instanceof EntityTitan)
				((EntityTitan)this.shootingEntity).attackChoosenEntity(movingObject.entityHit, 30000, 10);
				else
				movingObject.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.shootingEntity), 30000);
				this.playSound("thetitans:slashFlesh", 2F, 1.5F);
				if (movingObject.entityHit instanceof EntityTitan || movingObject.entityHit instanceof EntityTitanPart)
				{
					if (this.shootingEntity instanceof EntityTitan)
					{
						((EntityTitan)this.shootingEntity).attackChoosenEntity(movingObject.entityHit, 30000, 10);
						((EntityTitan)this.shootingEntity).attackChoosenEntity(movingObject.entityHit, 30000, 10);
						((EntityTitan)this.shootingEntity).attackChoosenEntity(movingObject.entityHit, 30000, 10);
						((EntityTitan)this.shootingEntity).destroyBlocksInAABB(this.boundingBox.expand(5D, 5D, 5D));
					}

					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10F, false);
					this.setDead();
				}
			}
		}
	}

	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setTag("direction", this.newDoubleNBTList(new double[] 
		{
			this.motionX, this.motionY, this.motionZ
		}
		));
	}

	/**
	* (abstract) Protected helper method to read subclass entity data from NBT.
	*/
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		if (p_70037_1_.hasKey("direction", 9))
		{
			NBTTagList nbttaglist = p_70037_1_.getTagList("direction", 6);
			this.motionX = nbttaglist.func_150309_d(0);
			this.motionY = nbttaglist.func_150309_d(1);
			this.motionZ = nbttaglist.func_150309_d(2);
		}

		else
		{
			this.setDead();
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

	protected float getMotionFactor()
	{
		return 0.99F;
	}

	/**
	* Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
	*/
	public boolean isBurning()
	{
		return false;
	}

	@SuppressWarnings("unchecked")
	public void onUpdate()
	{
		this.onEntityUpdate();
		if (!this.worldObj.isRemote && (this.posY <= -100D || this.ticksExisted > 300 || this.shootingEntity == null || (this.shootingEntity != null && !this.shootingEntity.isEntityAlive())))
		this.setDead();
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
		for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
		{
			;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float f2 = this.getMotionFactor();
		this.motionX += this.accelerationX * 5D;
		this.motionY += this.accelerationY * 5D;
		this.motionZ += this.accelerationZ * 5D;
		this.motionX *= (double)f2;
		this.motionY *= (double)f2;
		this.motionZ *= (double)f2;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.noClip = true;
		ArrayList<?> list = Lists.newArrayList(this.worldObj.loadedEntityList);
		if (list != null && !list.isEmpty())
		{
			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity = (Entity)list.get(i);
				if (this.isEntityAlive() && this.getDistanceSqToEntity(entity) <= (this.width * this.width) + (entity.width * entity.width) + 9D)
				{
					this.onImpact(new MovingObjectPosition(entity));
				}
			}
		}
	}
}


