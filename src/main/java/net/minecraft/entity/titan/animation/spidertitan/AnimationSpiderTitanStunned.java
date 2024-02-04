package net.minecraft.entity.titan.animation.spidertitan;
import net.minecraft.entity.titan.EntitySpiderTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSpiderTitanStunned
extends AIAnimation
{
	private EntitySpiderTitan entity;
	public AnimationSpiderTitanStunned(EntitySpiderTitan test)
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
		return 660;
	}

	public boolean continueExecuting()
	{
		return (!this.entity.isStunned) && (this.entity.getAnimTick() > 660) ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAnimTick() == 660)
		{
			this.entity.setAnimID(0);
			this.entity.setAnimTick(0);
		}

		if (this.entity.getAnimTick() == 420)
		{
			this.entity.isStunned = false;
		}

		else
		{
			this.entity.setAttackTarget(null);
		}
	}
}


