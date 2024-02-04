package net.minecraft.entity.titan.animation.zombietitan;
import net.minecraft.entity.titan.EntityZombieTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationZombieTitanCreation
extends AIAnimation
{
	private EntityZombieTitan entity;
	public AnimationZombieTitanCreation(EntityZombieTitan test)
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
		return 520;
	}

	public void startExecuting()
	{
		super.startExecuting();
		this.entity.playSound("thetitans:titanBirth", 1000.0F, 1.0F);
	}

	public void updateTask()
	{
		this.entity.motionX = 0D;
		this.entity.motionZ = 0D;
		if (this.entity.motionY > 0D)
		this.entity.motionY = 0D;
		if (entity.isClient())
		{
			if (this.entity.getAnimTick() == 10)
			this.entity.playSound("thetitans:titanRumble", this.entity.getTitanSizeMultiplier(), 1F);
			if (this.entity.getAnimTick() == 160)
			this.entity.playSound("thetitans:titanZombieCreation", this.entity.getTitanSizeMultiplier(), this.entity.isChild() ? 1.5F : 1F);
			if (this.entity.getAnimTick() == 300)
			this.entity.playSound("thetitans:titanQuake", this.entity.getTitanSizeMultiplier(), 1F);
			if (this.entity.getAnimTick() == 420)
			this.entity.playSound("thetitans:titanSkeletonGetUp", this.entity.getTitanSizeMultiplier(), 1F);
		}
	}
}


