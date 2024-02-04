package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityHomingWitherSkull
extends EntityWitherSkull
{
	public int lifetime;
	public int explosivePower;
	public int extraDamage;
	public float speedFactor;
	public Entity assginedEntity;
	public EntityHomingWitherSkull(World worldIn)
	{
		super(worldIn);
		setSize(0.5F, 0.5F);
	}

	public EntityHomingWitherSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
	{
		super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
		setSize(0.5F, 0.5F);
	}

	@SideOnly(Side.CLIENT)
	public EntityHomingWitherSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
	{
		super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
		setSize(0.5F, 0.5F);
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
		if (!this.worldObj.isRemote)
		{
			if (movingObject.entityHit != null)
			{
				if (this.shootingEntity != null)
				{
					if ((movingObject.entityHit instanceof EntityLivingBase))
					{
						((EntityLivingBase)movingObject.entityHit).setRevengeTarget(this.shootingEntity);
						movingObject.entityHit.hurtResistantTime = 5;
						if (((movingObject.entityHit.height >= 6.0F) || (((EntityLivingBase)movingObject.entityHit).getTotalArmorValue() > 24)) && (!(movingObject.entityHit instanceof EntityTitan)) && (!(movingObject.entityHit instanceof EntityDragon)) && (!(movingObject.entityHit instanceof EntityDragonPart)) && (!(movingObject.entityHit instanceof EntityPlayer)))
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.destroy, Float.MAX_VALUE);
						}
					}

					if (((movingObject.entityHit instanceof EntityDragon)) || ((movingObject.entityHit instanceof EntityDragonPart)))
					{
						if (movingObject.entityHit.height >= 6.0F)
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamageVSEnderDragon(this.shootingEntity), 200.0F + this.extraDamage * 20);
							movingObject.entityHit.playSound("thetitans:titanpunch", 10.0F, 1.0F);
						}

						else
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamageVSEnderDragon(this.shootingEntity), 20.0F + this.extraDamage);
						}
					}

					else if (((movingObject.entityHit instanceof EntityLivingBase)) && (((EntityLivingBase)movingObject.entityHit).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD))
					{
						if ((movingObject.entityHit.height >= 6.0F) || (((EntityLivingBase)movingObject.entityHit).getTotalArmorValue() > 24))
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.radiation, 300.0F + this.extraDamage * 30);
							movingObject.entityHit.playSound("thetitans:titanpunch", 10.0F, 1.0F);
						}

						else
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.radiation, 20.0F + this.extraDamage);
						}
					}

					else if (movingObject.entityHit.height >= 6.0F)
					{
						movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamage(this.shootingEntity), 600.0F + this.extraDamage * 10);
						movingObject.entityHit.playSound("thetitans:titanpunch", 10.0F, 1.0F);
					}

					else
					{
						movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamage(this.shootingEntity), 20.0F + this.extraDamage);
					}
				}

				else
				{
					movingObject.entityHit.attackEntityFrom(DamageSource.magic, 20F);
				}

				if ((movingObject.entityHit instanceof EntityLivingBase))
				{
					byte b0 = 20;
					if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
					{
						b0 = 40;
					}

					else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
					{
						b0 = 80;
					}

					if (b0 > 0)
					{
						((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 1));
					}
				}
			}

			this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 2.0F + this.explosivePower, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			setDead();
		}
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public void onUpdate()
	{
		this.lifetime += 1;
		if ((this.assginedEntity != null) && (this.assginedEntity.isEntityAlive()))
		{
			double d0 = this.assginedEntity.posX - this.posX;
			double d1 = this.assginedEntity.posY + (this.assginedEntity instanceof EntityTitan ? (this.assginedEntity.height * 0.5D) : this.assginedEntity.getEyeHeight()) - this.posY;
			double d2 = this.assginedEntity.posZ - this.posZ;
			float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
			this.motionX = (d0 / f2 * (getMotionFactor() * 0.75F) * (getMotionFactor() * 0.75F) + this.motionX * 0.75F);
			this.motionY = (d1 / f2 * (getMotionFactor() * 0.75F) * (getMotionFactor() * 0.75F) + this.motionY * 0.75F);
			this.motionZ = (d2 / f2 * (getMotionFactor() * 0.75F) * (getMotionFactor() * 0.75F) + this.motionZ * 0.75F);
			if (this.getDistanceSq(this.assginedEntity.posX, this.assginedEntity.posY + (this.assginedEntity instanceof EntityTitan ? (this.assginedEntity.height * 0.5D) : this.assginedEntity.getEyeHeight()), this.assginedEntity.posZ) < 9D)
			{
				this.onImpact(new MovingObjectPosition(this.assginedEntity));
				this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 2.0F + this.explosivePower, false, false);
				setDead();
			}
		}

		if (this.lifetime >= 200)
		{
			this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 2.0F + this.explosivePower, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			setDead();
		}

		super.onUpdate();
		this.setPosition(this.posX, this.posY, this.posZ);
	}
}


