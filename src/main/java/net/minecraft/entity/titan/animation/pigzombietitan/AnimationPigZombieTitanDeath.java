package net.minecraft.entity.titan.animation.pigzombietitan;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationPigZombieTitanDeath
extends AIAnimation
{
	private EntityPigZombieTitan entity;
	public AnimationPigZombieTitanDeath(EntityPigZombieTitan test)
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


