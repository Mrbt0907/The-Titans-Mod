package net.minecraft.entity.titan.animation.zombietitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityZombieTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationZombieTitanAttack5
extends AIAnimation
{
	private EntityZombieTitan entity;
	public AnimationZombieTitanAttack5(EntityZombieTitan test)
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
			this.entity.faceEntity(this.entity.getAttackTarget(), 180.0F, 180.0F);
		}

		if ((this.entity.getAnimTick() == 30) && (this.entity.getAttackTarget() != null))
		{
			if (entity.isClient())
			this.entity.playSound("thetitans:titanSwing", 5F, 1F);
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount() * 20;
			this.entity.getAttackTarget().motionY += 2D;
			this.entity.collideWithEntities(this.entity.head, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.head.boundingBox.expand(3.0D, 3.0D, 3.0D)));
			this.entity.attackChoosenEntity(this.entity.getAttackTarget(), f, i);
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity.getAttackTarget(), this.entity.getAttackTarget().boundingBox.expand(3.0D, 3.0D, 3.0D));
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
		}
	}
}


