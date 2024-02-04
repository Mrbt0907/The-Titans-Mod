package net.minecraft.entity.titan.animation.omegafish;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationOmegafishTailSmash
extends AIAnimation
{
	private EntitySilverfishTitan entity;
	public AnimationOmegafishTailSmash(EntitySilverfishTitan test)
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
		return 60;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 36) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (this.entity.getAnimTick() == 36)
		{
			if (entity.isClient())
			this.entity.playSound("thetitans:groundSmash", 20.0F, 1.5F);
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(64.0D, 4.0D, 64.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						float f = (float)entity.getAttackValue(1.0F);
						int i = this.entity.getKnockbackAmount();
						this.entity.attackChoosenEntity(entity1, f * 4.0F, i);
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(96.0D, 16.0D, 96.0D)));
					}
				}
			}
		}
	}
}


