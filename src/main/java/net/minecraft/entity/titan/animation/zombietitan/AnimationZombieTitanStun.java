package net.minecraft.entity.titan.animation.zombietitan;
import net.minecraft.entity.titan.EntityZombieTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationZombieTitanStun
extends AIAnimation
{
	private EntityZombieTitan entity;
	public AnimationZombieTitanStun(EntityZombieTitan test)
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
		return 180;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 180 ? false : super.continueExecuting();
	}
}


