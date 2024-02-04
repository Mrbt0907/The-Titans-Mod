package net.minecraft.entity.titan.animation.endercolossus;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityEnderColossus;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationEnderColossusAttack1
extends AIAnimation
{
	private EntityEnderColossus entity;
	public AnimationEnderColossusAttack1(EntityEnderColossus test)
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
		return 60;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAnimTick() > 60)
		{
			this.entity.setAttackTarget(null);
		}

		if ((this.entity.getAnimTick() < 30) && (this.entity.getAttackTarget() != null))
		{
			this.entity.faceEntity(this.entity.getAttackTarget(), 180.0F, 180.0F);
		}

		if ((this.entity.getAnimTick() == 26))
		this.entity.playSound("thetitans:titanSwing", 10F, 1F);
		if ((this.entity.getAnimTick() == 28) && (this.entity.getAttackTarget() != null))
		{
			if (entity.isClient())
			this.entity.playSound("thetitans:titanSwing", 10F, 1F);
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			this.entity.collideWithEntities(this.entity.head, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.head.boundingBox.expand(4.0D, 2.0D, 4.0D)));
			this.entity.attackChoosenEntity(this.entity.getAttackTarget(), f, i);
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity.getAttackTarget(), this.entity.getAttackTarget().boundingBox.expand(6.0D, 2.0D, 6.0D));
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


