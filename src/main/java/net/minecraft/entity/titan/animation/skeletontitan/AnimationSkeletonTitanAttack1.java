package net.minecraft.entity.titan.animation.skeletontitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSkeletonTitanAttack1
extends AIAnimation
{
	private EntitySkeletonTitan entity;
	public AnimationSkeletonTitanAttack1(EntitySkeletonTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 3;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 100;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 20) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (this.entity.getAnimTick() == 58)
		{
			float d0 = 0.5F * entity.getTitanSizeMultiplier();
			float f3 = this.entity.renderYawOffset * (float)Math.PI / 180.0F;
			float f11 = MathHelper.sin(f3);
			float f4 = MathHelper.cos(f3);
			this.entity.worldObj.newExplosion(this.entity, this.entity.posX - (double)(f11 * d0), this.entity.posY, this.entity.posZ + (double)(f4 * d0), 5F, false, false);
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			this.entity.collideWithEntities(this.entity.pelvis, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.pelvis.boundingBox.expand(16.0D, 8.0D, 16.0D)));
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(entity.getTitanSizeMultiplier(), 2.0D, entity.getTitanSizeMultiplier()).offset(-(double)(f11 * d0), -8.0D, (double)(f4 * d0)));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackChoosenEntity(entity1, f, i);
					}
				}
			}

			if (entity.isClient())
			if (this.entity.getSkeletonType() == 1)
			this.entity.playSound("thetitans:titanStrike", 20.0F, 0.9F);
			else
			this.entity.playSound("thetitans:titanStrike", 20.0F, 1.0F);
		}
	}
}


