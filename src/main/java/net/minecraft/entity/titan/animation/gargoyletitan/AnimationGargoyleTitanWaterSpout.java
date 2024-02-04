package net.minecraft.entity.titan.animation.gargoyletitan;
import net.minecraft.entity.titan.EntityGargoyleTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationGargoyleTitanWaterSpout
extends AIAnimation
{
	private EntityGargoyleTitan entity;
	public AnimationGargoyleTitanWaterSpout(EntityGargoyleTitan test)
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
		return 60;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 60 ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 40F, 40F);
	}
}


