package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityBulletWitherSkull
extends EntityWitherSkull
{
	public int lifetime;
	public int explosivePower;
	public int extraDamage;
	public float speedFactor;
	protected float getMotionFactor()
	{
		return 0.95F;
	}

	public EntityBulletWitherSkull(World worldIn)
	{
		super(worldIn);
		setSize(0.3125F, 0.3125F);
	}

	public EntityBulletWitherSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
	{
		super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
		setSize(0.3125F, 0.3125F);
	}

	@SideOnly(Side.CLIENT)
	public EntityBulletWitherSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
	{
		super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
		setSize(0.3125F, 0.3125F);
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
						movingObject.entityHit.hurtResistantTime = 1;
						if ((((EntityLivingBase)movingObject.entityHit).getTotalArmorValue() > 24) && (!(movingObject.entityHit instanceof EntityTitan)) && (!(movingObject.entityHit instanceof EntityDragon)) && (!(movingObject.entityHit instanceof EntityDragonPart)) && (!(movingObject.entityHit instanceof EntityPlayer)))
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.destroy, 2.14748365E9F);
						}
					}

					if (((movingObject.entityHit instanceof EntityDragon)) || ((movingObject.entityHit instanceof EntityDragonPart)))
					{
						movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamageVSEnderDragon(this.shootingEntity), 10.0F + this.extraDamage);
					}

					else if (((movingObject.entityHit instanceof EntityLivingBase)) && (((EntityLivingBase)movingObject.entityHit).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD))
					{
						movingObject.entityHit.attackEntityFrom(DamageSourceExtra.generic, 10.0F + this.extraDamage);
					}

					else
					{
						movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamage(this.shootingEntity), 10.0F + this.extraDamage);
					}

					if ((!movingObject.entityHit.isEntityAlive()) && (!(movingObject.entityHit instanceof EntityTitan)) && (!(movingObject.entityHit instanceof EntityDragon)) && (!(movingObject.entityHit instanceof EntityDragonPart)) && (!(movingObject.entityHit instanceof EntityPlayer)))
					{
						movingObject.entityHit.setDead();
					}
				}

				else 
				{

					movingObject.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
				}

				if ((movingObject.entityHit instanceof EntityLivingBase))
				{
					byte b0 = 10;
					if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
					{
						b0 = 20;
					}

					else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
					{
						b0 = 40;
					}

					if (b0 > 0)
					{
						((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40, 3));
						((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 1));
						if (movingObject.entityHit.posY + movingObject.entityHit.getEyeHeight() - 0.2D < this.posY)
						{
							((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.blindness.id, 40, 0));
						}
					}
				}
			}

			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 1.8F);
			setDead();
		}
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public void onUpdate()
	{
		super.onUpdate();
		this.lifetime += 1;
		if (this.lifetime >= 100)
		{
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 1.8F);
			setDead();
		}
	}
}


