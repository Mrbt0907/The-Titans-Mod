package net.minecraft.entity.titan.animation.skeletontitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSkeletonTitanAttack3
extends AIAnimation
{
	private EntitySkeletonTitan entity;
	public AnimationSkeletonTitanAttack3(EntitySkeletonTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 7;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 260;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ||this.entity.worldObj.getBlock((int)this.entity.posX, (int)this.entity.posY - 1, (int)this.entity.posZ).getExplosionResistance(this.entity) > 500.0F ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		this.entity.renderYawOffset = this.entity.rotationYaw = this.entity.rotationYawHead;
		if ((this.entity.getAnimTick() < 10) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 180F, 40.0F);
		}

		if (this.entity.getAnimTick() == 160 && this.entity.getSkeletonType() == 1)
		{
			if (entity.isClient())
			this.entity.playSound("thetitans:swordDrag", 10.0F, 1.0F);
		}

		if (this.entity.getAnimTick() == 90)
		{
			if (entity.isClient())
			{
				this.entity.shakeNearbyPlayerCameras(10D);
				this.entity.playSound("thetitans:titanSlam", 100F, 1F);
				this.entity.playSound("thetitans:titanPress", 100F, 1F);
			}

			float d0 = 2.25F * this.entity.getTitanSizeMultiplier();
			float f3 = this.entity.renderYawOffset * (float)Math.PI / 180.0F;
			float f11 = MathHelper.sin(f3);
			float f4 = MathHelper.cos(f3);
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			this.entity.collideWithEntities(this.entity.pelvis, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.pelvis.boundingBox.expand(16.0D, 8.0D, 16.0D)));
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(this.entity.getTitanSizeMultiplier(), 2.0D, this.entity.getTitanSizeMultiplier()).offset(-(double)(f11 * d0), -8.0D, (double)(f4 * d0)));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackChoosenEntity(entity1, f * 15.0F, i);
					}
				}
			}
		}
	}
}


