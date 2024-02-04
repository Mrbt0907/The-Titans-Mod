package net.minecraft.entity.titan.animation.skeletontitan;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSkeletonTitanRangedAttack2
extends AIAnimation
{
	private EntitySkeletonTitan entity;
	public AnimationSkeletonTitanRangedAttack2(EntitySkeletonTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 11;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 120;
	}

	public boolean continueExecuting()
	{
		return (this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned) || (this.entity.getSkeletonType() == 1) ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
	}
}


