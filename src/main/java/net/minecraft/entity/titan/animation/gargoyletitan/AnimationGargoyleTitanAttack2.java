package net.minecraft.entity.titan.animation.gargoyletitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityGargoyleTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationGargoyleTitanAttack2
extends AIAnimation
{
	private EntityGargoyleTitan entity;
	public AnimationGargoyleTitanAttack2(EntityGargoyleTitan test)
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
		return 150;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 150 ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 60) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if ((this.entity.getAnimTick() == 60) || (this.entity.getAnimTick() == 104))
		{
			if (entity.isClient())
			this.entity.shakeNearbyPlayerCameras(10D);
			float f = (float)entity.getAttackValue(1.0F);
			int i = 0;
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(32.0D, 8.0D, 32.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						entity1.motionY += 1.0F + entity.getRNG().nextFloat() + entity.getRNG().nextFloat();
						this.entity.attackChoosenEntity(entity1, f, i);
					}
				}
			}

			if (entity.isClient())
			this.entity.playSound("thetitans:titanSlam", 20.0F, 1.0F);
		}
	}
}


