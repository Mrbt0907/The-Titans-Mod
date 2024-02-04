package net.minecraft.entity.titan.animation.omegafish;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationOmegafishStunned
extends AIAnimation
{
	private EntitySilverfishTitan entity;
	public AnimationOmegafishStunned(EntitySilverfishTitan test)
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
		return 500;
	}

	public boolean continueExecuting()
	{
		return (!this.entity.isStunned) && (this.entity.getAnimTick() > 500) ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAnimTick() == 500)
		{
			this.entity.setAnimID(0);
			this.entity.setAnimTick(0);
		}

		if (this.entity.getAnimTick() == 380)
		{
			this.entity.isStunned = false;
		}

		else
		{
			this.entity.setAttackTarget(null);
		}
	}
}


