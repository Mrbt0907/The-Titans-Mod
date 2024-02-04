package net.minecraft.entity.titan.animation.skeletontitan;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSkeletonTitanRangedAttack1
extends AIAnimation
{
	private EntitySkeletonTitan entity;
	public AnimationSkeletonTitanRangedAttack1(EntitySkeletonTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 5;
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
		return (this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned) || (this.entity.getSkeletonType() == 1) || (this.entity.getAttackTarget() != null && this.entity.getDistanceSqToEntity(this.entity.getAttackTarget()) < 800D) ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
	}
}


