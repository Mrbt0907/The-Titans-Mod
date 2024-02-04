package net.minecraft.entity.titan.animation.creepertitan;
import net.minecraft.entity.titan.EntityCreeperTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationCreeperTitanCreation
extends AIAnimation
{
	private EntityCreeperTitan entity;
	public AnimationCreeperTitanCreation(EntityCreeperTitan test)
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
		return 480;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 480 ? false : super.continueExecuting();
	}
}


