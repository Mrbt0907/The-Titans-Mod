package net.minecraft.entity.titan;
import java.util.List;
import net.minecraft.theTitans.core.TheCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.titanminion.EntityDragonMinion;
import net.minecraft.entity.titanminion.EntityEndermanMinion;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityLightningBall
extends EntityFireball
{
	public EntityLightningBall(World worldIn)
	{
		super(worldIn);
		this.setSize(4.0F, 4.0F);
	}

	public EntityLightningBall(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_)
	{
		super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
		this.setSize(4.0F, 4.0F);
	}

	public EntityLightningBall(World worldIn, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_)
	{
		super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
		this.setSize(4.0F, 4.0F);
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
		if (!this.worldObj.isRemote)
		{
			float f;
			if (TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) == true)
			{
				f = 6000.0F;
			}

			else
			{
				f = 2000.0F;
			}

			if ((movingObject.entityHit != null))
			{
				movingObject.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), f);
				boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
				this.worldObj.newExplosion((Entity)null, this.posX, this.posY, this.posZ, 7.0F, true, flag);
			}

			else
			{
				playSound("random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			}

			setDead();
		}
	}

	public void onUpdate()
	{
		super.onUpdate();
		this.setFire(4);
		if (this.ticksExisted % 600 == 0)
		{
			boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			this.worldObj.newExplosion((Entity)null, this.posX, this.posY, this.posZ, 7.0F, true, flag);
			setDead();
		}

		if (this.rand.nextInt(30) == 0)
		this.worldObj.addWeatherEffect(new EntityGammaLightning (this.worldObj, this.posX, this.posY, this.posZ, 1F, 0F, 1F));
		List<?> list11 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(8.0D, 8.0D, 8.0D));
		if ((list11 != null) && (!list11.isEmpty()) && this.shootingEntity != null)
		{
			for (int i1 = 0; i1 < list11.size(); i1++)
			{
				Entity entity = (Entity)list11.get(i1);
				if (entity != null && entity.isEntityAlive() && ((entity instanceof EntityLivingBase)) && this.shootingEntity instanceof EntityEnderColossus && !(entity instanceof EntityEndermanMinion) && !(entity instanceof EntityEnderColossus) && !(entity instanceof EntityDragon) && !(entity instanceof EntityDragonMinion) && !(entity instanceof EntityEnderColossusCrystal))
				{
					entity.setFire(15);
					entity.attackEntityFrom(DamageSourceExtra.lightningBolt, 100.0F);
					this.worldObj.addWeatherEffect(new EntityGammaLightning (this.worldObj, this.posX, this.posY, this.posZ, 1F, 0F, 1F));
					this.worldObj.addWeatherEffect(new EntityGammaLightning (this.worldObj, entity.posX, entity.posY, entity.posZ, 1F, 0F, 1F));
					((EntityEnderColossus)this.shootingEntity).attackChoosenEntity(entity, 5F, 1);
				}
			}
		}
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean isBurning()
	{
		return false;
	}

	protected float getMotionFactor()
	{
		return 0.75F;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return false;
	}
}


