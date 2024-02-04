package net.minecraft.entity.titan.animation.pigzombietitan;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationPigZombieTitanCreation
extends AIAnimation
{
	private EntityPigZombieTitan entity;
	public AnimationPigZombieTitanCreation(EntityPigZombieTitan entityPigZombieTitan)
	{
		super(entityPigZombieTitan);
		this.entity = entityPigZombieTitan;
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
		return 360;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 360 ? false : super.continueExecuting();
	}
}


