package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
public class EntityMortarWitherSkull extends EntityWitherSkull
{
	public int lifetime;
	public int explosivePower;
	public int extraDamage;
	public float speedFactor;
	public boolean isInvulnerable()
	{
		return true;
	}

	public EntityMortarWitherSkull(World worldIn)
	{
		super(worldIn);
		setSize(0.5F, 0.5F);
	}

	public EntityMortarWitherSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
	{
		super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
		setSize(0.5F, 0.5F);
	}

	@SideOnly(Side.CLIENT)
	public EntityMortarWitherSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
	{
		super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
		setSize(0.5F, 0.5F);
	}

	public float func_145772_a(Explosion p_145772_1_, World p_145772_2_, int p_145772_3_, int p_145772_4_, int p_145772_5_, Block p_145772_6_)
	{
		float f = super.func_145772_a(p_145772_1_, p_145772_2_, p_145772_3_, p_145772_4_, p_145772_5_, p_145772_6_);
		if ((isInvulnerable()) && (p_145772_6_ != Blocks.bedrock) && (p_145772_6_ != Blocks.end_portal) && (p_145772_6_ != Blocks.end_portal_frame) && (p_145772_6_ != Blocks.command_block))
		{
			f = Math.min(0F, f);
		}

		return f;
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
						movingObject.entityHit.hurtResistantTime = 0;
						movingObject.entityHit.addVelocity(-MathHelper.sin(this.shootingEntity.rotationYawHead * 3.1415927F / 180.0F) * 1.5F, 0.9D, MathHelper.cos(this.shootingEntity.rotationYawHead * 3.1415927F / 180.0F) * 1.5F);
						if (((movingObject.entityHit.isEntityInvulnerable()) || (movingObject.entityHit.height >= 6.0F) || (((EntityLivingBase)movingObject.entityHit).getTotalArmorValue() > 24)) && (!(movingObject.entityHit instanceof EntityTitan)) && (!(movingObject.entityHit instanceof EntityDragon)) && (!(movingObject.entityHit instanceof EntityDragonPart)) && (!(movingObject.entityHit instanceof EntityPlayer)))
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.destroy, 2.14748365E9F);
						}
					}

					if (((movingObject.entityHit instanceof EntityDragon)) || ((movingObject.entityHit instanceof EntityDragonPart)))
					{
						if (movingObject.entityHit.height >= 6.0F)
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamageVSEnderDragon(this.shootingEntity), 5000.0F + this.extraDamage * 1000);
							movingObject.entityHit.playSound("thetitans:titanpunch", 10.0F, 1.0F);
						}

						else
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamageVSEnderDragon(this.shootingEntity), 500.0F + this.extraDamage * 100);
						}
					}

					else if (((movingObject.entityHit instanceof EntityLivingBase)) && (((EntityLivingBase)movingObject.entityHit).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD))
					{
						if ((movingObject.entityHit.height >= 6.0F) || (((EntityLivingBase)movingObject.entityHit).getTotalArmorValue() > 24))
						{
							movingObject.entityHit.attackEntityFrom(DamageSource.generic, 5000.0F + this.extraDamage * 1000);
							movingObject.entityHit.playSound("thetitans:titanpunch", 10.0F, 0.9F);
						}

						else
						{
							movingObject.entityHit.attackEntityFrom(DamageSourceExtra.generic, 500.0F + this.extraDamage * 100);
						}
					}

					else if (movingObject.entityHit.height >= 6.0F)
					{
						movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamage(this.shootingEntity), 5000.0F + this.extraDamage * 1000);
						movingObject.entityHit.playSound("thetitans:titanpunch", 10.0F, 0.9F);
					}

					else
					{
						movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causeHomingSkullDamage(this.shootingEntity), 500.0F + this.extraDamage * 100);
					}
				}

				else
				{
					movingObject.entityHit.attackEntityFrom(DamageSource.magic, 500.0F);
				}

				if ((movingObject.entityHit instanceof EntityLivingBase))
				{
					byte b0 = 30;
					if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
					{
						b0 = 60;
					}

					else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
					{
						b0 = 90;
					}

					if (b0 > 0)
					{
						((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 120, 3));
						((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 40 * b0, 3));
						((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.blindness.id, 120, 0));
						((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.confusion.id, 120, 0));
					}
				}
			}

			this.worldObj.newExplosion(this.shootingEntity != null ? this.shootingEntity : this, this.posX, this.posY, this.posZ, 14F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.6F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thetitans:mortarHit", 0.5F, 1.0F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thetitans:mortarHit", 2.0F, 1.0F);
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
		if (this.isCollided)
		{
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.5D, 0.5D, 0.5D));
			if (list != null && !list.isEmpty())
			{
				for (int j = 0; j < list.size(); ++j)
				{
					Entity entity = (Entity)list.get(j);
					this.onImpact(new MovingObjectPosition(entity));
				}
			}
		}

		this.lifetime += 1;
		if (this.lifetime >= 1000)
		{
			this.worldObj.newExplosion(this.shootingEntity != null ? this.shootingEntity : this, this.posX, this.posY, this.posZ, 14F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.6F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thetitans:mortarHit", 0.5F, 1.0F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thetitans:mortarHit", 2.0F, 1.0F);
			setDead();
		}
	}
}


