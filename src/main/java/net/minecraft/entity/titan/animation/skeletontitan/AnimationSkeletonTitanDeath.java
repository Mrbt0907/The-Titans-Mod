package net.minecraft.entity.titan.animation.skeletontitan;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSkeletonTitanDeath
extends AIAnimation
{
	private EntitySkeletonTitan entity;
	public AnimationSkeletonTitanDeath(EntitySkeletonTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 10;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 2000;
	}

	public boolean shouldExecute()
	{
		return this.entity.deathTicks <= 0 || this.entity.isEntityAlive() ? false : super.shouldExecute();
	}

	public boolean continueExecuting()
	{
		return this.entity.deathTicks <= 0 || this.entity.isEntityAlive() ? false : super.continueExecuting();
	}
}


