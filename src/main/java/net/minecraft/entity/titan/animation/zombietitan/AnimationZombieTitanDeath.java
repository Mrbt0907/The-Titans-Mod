package net.minecraft.entity.titan.animation.zombietitan;
import net.minecraft.entity.titan.EntityZombieTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationZombieTitanDeath
extends AIAnimation
{
	private EntityZombieTitan entity;
	public AnimationZombieTitanDeath(EntityZombieTitan test)
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


