package net.minecraft.entity.titan.animation.skeletontitan;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSkeletonTitanStun
extends AIAnimation
{
	private EntitySkeletonTitan entity;
	public AnimationSkeletonTitanStun(EntitySkeletonTitan test)
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
		return 540;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 540 ? false : super.continueExecuting();
	}
}


