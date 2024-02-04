package net.minecraft.entity.titan.animation.omegafish;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationOmegafishCreation
extends AIAnimation
{
	private EntitySilverfishTitan entity;
	public AnimationOmegafishCreation(EntitySilverfishTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 13;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 260;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 260 ? false : super.continueExecuting();
	}
}


