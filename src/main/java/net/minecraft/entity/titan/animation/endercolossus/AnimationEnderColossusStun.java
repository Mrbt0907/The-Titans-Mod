package net.minecraft.entity.titan.animation.endercolossus;
import net.minecraft.entity.titan.EntityEnderColossus;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationEnderColossusStun
extends AIAnimation
{
	private EntityEnderColossus entity;
	public AnimationEnderColossusStun(EntityEnderColossus test)
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
		return 400;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 400 ? false : super.continueExecuting();
	}
}


