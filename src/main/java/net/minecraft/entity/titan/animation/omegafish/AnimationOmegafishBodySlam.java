package net.minecraft.entity.titan.animation.omegafish;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationOmegafishBodySlam
extends AIAnimation
{
	private EntitySilverfishTitan entity;
	public AnimationOmegafishBodySlam(EntitySilverfishTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 9;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 410;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 80) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (this.entity.getAnimTick() == 80)
		{
			double ver = 9D;
			this.entity.motionY = ver;
			if (this.entity.getAttackTarget() != null)
			{
				double d01 = entity.getAttackTarget().posX - this.entity.posX;
				double d11 = entity.getAttackTarget().posZ - this.entity.posZ;
				float f21 = MathHelper.sqrt_double(d01 * d01 + d11 * d11);
				double hor = f21 / 16.0F;
				this.entity.motionX = (d01 / f21 * hor * hor + this.entity.motionX * hor);
				this.entity.motionZ = (d11 / f21 * hor * hor + this.entity.motionZ * hor);
			}
		}

		if (this.entity.getAnimTick() == 240)
		{
			if (entity.isClient())
			{
				this.entity.playSound("thetitans:titanSlam", 20.0F, 1.0F);
				this.entity.playSound("thetitans:groundSmash", 20.0F, 1.5F);
				this.entity.playSound("thetitans:titanland", 10000.0F, 1.0F);
			}

			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(96.0D, 32.0D, 96.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						float f = (float)entity.getAttackValue(1.0F);
						int i = this.entity.getKnockbackAmount();
						this.entity.attackChoosenEntity(entity1, f * 10.0F, i);
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(96.0D, 16.0D, 96.0D)));
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(96.0D, 16.0D, 96.0D)));
						entity1.motionY += 1.5D;
						if (this.entity.getRNG().nextInt(3) == 0)
						{
							entity1.setFire(20);
						}
					}
				}
			}
		}
	}
}


