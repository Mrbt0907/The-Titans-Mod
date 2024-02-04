package net.minecraft.entity.titan.animation.spidertitan;
import net.minecraft.entity.titan.EntitySpiderTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSpiderTitanCreation
extends AIAnimation
{
	private EntitySpiderTitan entity;
	public AnimationSpiderTitanCreation(EntitySpiderTitan test)
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


