package net.minecraft.entity.titan;
import java.util.List;
import net.minecraft.theTitans.core.TheCore;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityTitanFireball extends EntityFireball
{
	public float explosionRadius;
	public float impactDamage;
	public boolean canCauseFires;
	public EntityTitanFireball(World worldIn)
	{
		super(worldIn);
	}

	public EntityTitanFireball(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_)
	{
		super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
	}

	public EntityTitanFireball(World worldIn, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_)
	{
		super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
	}

	public EntityTitanFireball(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_, int id)
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
				{
					((EntityTitan)this.shootingEntity).attackChoosenEntity(movingObject, impactDamage, 0);
					((EntityTitan)this.shootingEntity).destroyBlocksInAABB(boundingBox.expand((double)(explosionRadius - this.width) + 1D, (double)(explosionRadius - this.width) + 1D, (double)(explosionRadius - this.width) + 1D));
				}

				else
				{
					if (((EntityLiving)this.shootingEntity) instanceof net.minecraft.entity.orespawnaddon.EntityOverlordScorpion && movingObject.getClass() != (Class<?>)EntityList.stringToClassMapping.get("Emperor Scorpion"))
					((EntityLiving)this.shootingEntity).attackEntityAsMob(movingObject);
					if (((EntityLiving)this.shootingEntity) instanceof net.minecraft.entity.orespawnaddon.EntityBurningMobzilla && (movingObject.getClass() != (Class<?>)EntityList.stringToClassMapping.get("Mobzilla") || movingObject.getClass() != (Class<?>)EntityList.stringToClassMapping.get("MobzillaHead")))
					((EntityLiving)this.shootingEntity).attackEntityAsMob(movingObject);
				}

				playSound("random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
				this.setDead();
			}
		}
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
		float f = this.impactDamage * (TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) ? 3F : 1F);
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
					if (this.shootingEntity instanceof EntityTitan)
					{
						((EntityTitan)this.shootingEntity).attackChoosenEntity(movingObject.entityHit, f, 3);
						((EntityTitan)this.shootingEntity).destroyBlocksInAABB(boundingBox.expand((double)(explosionRadius - this.width) + 1D, (double)(explosionRadius - this.width) + 1D, (double)(explosionRadius - this.width) + 1D));
					}

					else
					{
						if (((EntityLiving)this.shootingEntity) instanceof net.minecraft.entity.orespawnaddon.EntityOverlordScorpion && movingObject.entityHit.getClass() != (Class<?>)EntityList.stringToClassMapping.get("Emperor Scorpion"))
						((EntityLiving)this.shootingEntity).attackEntityAsMob(movingObject.entityHit);
						if (((EntityLiving)this.shootingEntity) instanceof net.minecraft.entity.orespawnaddon.EntityBurningMobzilla && (movingObject.entityHit.getClass() != (Class<?>)EntityList.stringToClassMapping.get("Mobzilla") || movingObject.entityHit.getClass() != (Class<?>)EntityList.stringToClassMapping.get("MobzillaHead")))
						((EntityLiving)this.shootingEntity).attackEntityAsMob(movingObject.entityHit);
						if (movingObject.entityHit instanceof EntityTitanPart)
						movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), f);
					}

					if (!(movingObject.entityHit instanceof EntityTitanPart) && !(movingObject.entityHit instanceof EntityTitan))
					this.worldObj.newExplosion(this.shootingEntity != null ? this.shootingEntity : this, this.posX, this.posY, this.posZ, this.explosionRadius, this.canCauseFires ? this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") : false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					else
					playSound("random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
					this.setDead();
				}
			}

			else
			{
				if (this.shootingEntity != null && this.shootingEntity instanceof EntityTitan && this.getFireballID() != 6)
				((EntityTitan)this.shootingEntity).destroyBlocksInAABB(boundingBox.expand((double)(explosionRadius - this.width), (double)(explosionRadius - this.width), (double)(explosionRadius - this.width)));
				if (!this.worldObj.isRemote && this.getFireballID() == 6)
				{
					for (int l = 0; l < 128; l++)
					{
						int i = MathHelper.floor_double(this.posX + (this.rand.nextFloat() - 0.5D) * this.width);
						int j = MathHelper.floor_double(this.posY);
						int k = MathHelper.floor_double(this.posZ + (this.rand.nextFloat() - 0.5D) * this.width);
						if ((this.worldObj.getBlock(i, j, k).getMaterial() == Material.air) && (Blocks.snow_layer.canPlaceBlockAt(this.worldObj, i, j, k)))
						{
							this.worldObj.setBlock(i, j, k, Blocks.snow_layer);
						}
					}
				}

				this.worldObj.newExplosion(this.shootingEntity != null ? this.shootingEntity : this, this.posX, this.posY, this.posZ, this.explosionRadius, this.canCauseFires ? this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") : false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				this.setDead();
			}

			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(this.explosionRadius, this.explosionRadius, this.explosionRadius));
			if (this.shootingEntity instanceof EntityTitan && list != null && !list.isEmpty())
			{
				for (int i = 0; i < list.size(); ++i)
				{
					Entity entity = (Entity)list.get(i);
					if (entity.isEntityAlive() && !(entity instanceof EntityTitanFireball))
					((EntityTitan)this.shootingEntity).attackChoosenEntity(entity, f, 3);
				}
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
			if (this.shootingEntity instanceof EntityGhastTitan)
			this.setFireballID(0);
			if (this.shootingEntity instanceof EntityCreeperTitan)
			this.setFireballID(1);
			if (this.shootingEntity instanceof EntityBlazeTitan)
			this.setFireballID(2);
			if (this.shootingEntity instanceof EntityPigZombieTitan)
			this.setFireballID(3);
			if (this.shootingEntity instanceof EntityEnderColossus)
			this.setFireballID(4);
			if (this.shootingEntity instanceof EntityIronGolemTitan)
			this.setFireballID(5);
			if (this.shootingEntity instanceof EntitySnowGolemTitan)
			this.setFireballID(6);
			if (this.shootingEntity instanceof EntityGargoyleTitan)
			this.setFireballID(7);
		}

		else
		{
			if (!this.worldObj.isRemote)
			this.setDead();
		}

		switch (this.getFireballID())
		{
			case 1:this.setSize(4F, 4F);
			this.impactDamage = 200F;
			this.canCauseFires = false;
			this.explosionRadius = 3F;
			break;
			case 2:this.setSize(4F, 4F);
			this.impactDamage = 600F;
			this.canCauseFires = true;
			this.explosionRadius = 3F;
			break;
			case 3:this.setSize(3F, 3F);
			this.impactDamage = 300F;
			this.canCauseFires = true;
			this.explosionRadius = 3F;
			break;
			case 4:this.setSize(8F, 8F);
			this.impactDamage = 1500F;
			this.canCauseFires = false;
			this.explosionRadius = 4F;
			break;
			case 5:this.setSize(8F, 8F);
			this.impactDamage = 10000F;
			this.canCauseFires = false;
			this.explosionRadius = 4F;
			break;
			case 6:this.setSize(3F, 3F);
			this.impactDamage = 60F;
			this.canCauseFires = false;
			this.explosionRadius = 2F;
			break;
			default:this.setSize(8F, 8F);
			this.impactDamage = 10000F;
			this.canCauseFires = true;
			this.explosionRadius = 6F;
		}

		super.onUpdate();
	}
}


