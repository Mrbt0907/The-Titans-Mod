package net.minecraft.entity.titan.animation.zombietitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationZombieTitanAttack1
extends AIAnimation
{
	private EntityZombieTitan entity;
	public AnimationZombieTitanAttack1(EntityZombieTitan test)
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
		return 70;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 10) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 10.0F, 40.0F);
		}

		if (this.entity.getAnimTick() == 27)
		if (entity.isClient())
		this.entity.playSound("thetitans:titanSwing", 10F, 1F);
		if (this.entity.getAnimTick() == 30)
		{
			double d8 = 0.75D * this.entity.getTitanSizeMultiplier();
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			if (entity.isClient())
			this.entity.shakeNearbyPlayerCameras(10D);
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(1.5D * this.entity.getTitanSizeMultiplier(), 4D, 1.5D * this.entity.getTitanSizeMultiplier()).offset(dx, -4D, dz));
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
			this.entity.playSound("thetitans:titanStrike", 20.0F, 1.0F);
		}
	}
}


