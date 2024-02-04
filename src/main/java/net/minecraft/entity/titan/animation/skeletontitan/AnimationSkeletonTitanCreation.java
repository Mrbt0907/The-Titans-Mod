package net.minecraft.entity.titan.animation.skeletontitan;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSkeletonTitanCreation
extends AIAnimation
{
	private EntitySkeletonTitan entity;
	public AnimationSkeletonTitanCreation(EntitySkeletonTitan test)
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
		return 540;
	}

	public void startExecuting()
	{
		super.startExecuting();
		this.entity.playSound("thetitans:titanBirth", 1000.0F,(entity.getSkeletonType() == 1 ? 0.875F : 1.0F));
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
			this.entity.playSound("thetitans:titanSkeletonAwaken", entity.getTitanSizeMultiplier(), 1F);
			if (this.entity.getAnimTick() == 40)
			this.entity.playSound("thetitans:titanRumble", entity.getTitanSizeMultiplier(), 1F);
			if (this.entity.getAnimTick() == 60)
			this.entity.playSound("thetitans:titanSkeletonBeginMove", entity.getTitanSizeMultiplier(), 1F);
			if (this.entity.getAnimTick() == 180)
			this.entity.playSound("thetitans:titanQuake", entity.getTitanSizeMultiplier(), 1F);
			if (this.entity.getAnimTick() == 200)
			this.entity.playSound("thetitans:titanRumble", entity.getTitanSizeMultiplier(), 1F);
			if (this.entity.getAnimTick() == 360)
			this.entity.playSound("thetitans:titanSkeletonGetUp", entity.getTitanSizeMultiplier(), 1F);
		}
	}
}


