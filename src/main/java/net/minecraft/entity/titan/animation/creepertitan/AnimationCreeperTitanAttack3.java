package net.minecraft.entity.titan.animation.creepertitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityCreeperTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationCreeperTitanAttack3
extends AIAnimation
{
	private EntityCreeperTitan entity;
	public AnimationCreeperTitanAttack3(EntityCreeperTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 4;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 70;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 32) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (this.entity.getAnimTick() == 32)
		{
			if (this.entity.getAttackTarget() != null)
			this.entity.doLightningAttackToEntity(this.entity.getAttackTarget());
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			if (entity.isClient())
			{
				this.entity.shakeNearbyPlayerCameras(10D);
				this.entity.playSound("thetitans:titanStrike", 20.0F, 1.0F);
			}

			float d0 = 16F;
			float f3 = this.entity.renderYawOffset * (float)Math.PI / 180.0F;
			float f11 = MathHelper.sin(f3);
			float f4 = MathHelper.cos(f3);
			this.entity.collideWithEntities(this.entity.head, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.head.boundingBox.expand(48.0D, 12.0D, 48.0D).offset(-(double)(f11 * d0), -8.0D, (double)(f4 * d0))));
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(32.0D, 8.0D, 32.0D).offset(-(double)(f11 * d0), -4.0D, (double)(f4 * d0)));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackChoosenEntity(entity1, f * 2.0F, i);
					}
				}
			}
		}
	}
}


