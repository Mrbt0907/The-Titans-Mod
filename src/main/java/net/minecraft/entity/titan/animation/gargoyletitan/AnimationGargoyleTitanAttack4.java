package net.minecraft.entity.titan.animation.gargoyletitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityGargoyleTitan;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationGargoyleTitanAttack4
extends AIAnimation
{
	private EntityGargoyleTitan entity;
	public AnimationGargoyleTitanAttack4(EntityGargoyleTitan test)
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
		return 150;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 150 ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 40) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (this.entity.getAnimTick() == 54)
		{
			if (entity.isClient())
			this.entity.shakeNearbyPlayerCameras(10D);
			double d8 = 8.0D;
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(32.0D, 2.0D, 32.0D).offset(dx, 0.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackChoosenEntity(entity1, f * 3, i);
					}
				}
			}

			if (entity.isClient())
			this.entity.playSound("thetitans:titanStrike", 20.0F, 1.0F);
		}
	}
}


