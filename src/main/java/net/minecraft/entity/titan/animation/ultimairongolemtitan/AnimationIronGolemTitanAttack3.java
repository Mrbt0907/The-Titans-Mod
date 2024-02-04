package net.minecraft.entity.titan.animation.ultimairongolemtitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityIronGolemTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationIronGolemTitanAttack3
extends AIAnimation
{
	private EntityIronGolemTitan entity;
	public AnimationIronGolemTitanAttack3(EntityIronGolemTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 8;
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
		return (this.entity.getAnimTick() > 60) ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 24) && (this.entity.getAttackTarget() != null))
		{
			this.entity.faceEntity(this.entity.getAttackTarget(), 180.0F, 180.0F);
		}

		if (entity.isClient() && this.entity.getAnimTick() == 26)
		{
			this.entity.playSound("thetitans:titanSwing", 100F, 1F);
			this.entity.playSound("mob.irongolem.throw", 100.0F, 0.5F);
			this.entity.playSound("mob.irongolem.throw", 100.0F, 0.5F);
		}

		if ((this.entity.getAnimTick() == 28) && (this.entity.getAttackTarget() != null))
		{
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			this.entity.attackEntityAsMob(this.entity.getAttackTarget());
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


