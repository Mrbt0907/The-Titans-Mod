package net.minecraft.entity.titan.animation.pigzombietitan;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationPigZombieTitanStun
extends AIAnimation
{
	private EntityPigZombieTitan entity;
	public AnimationPigZombieTitanStun(EntityPigZombieTitan test)
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


