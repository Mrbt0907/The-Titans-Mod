package net.minecraft.entity.titan.animation.gargoyletitan;
import net.minecraft.entity.titan.EntityGargoyleTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationGargoyleTitanMeteorRain
extends AIAnimation
{
	private EntityGargoyleTitan entity;
	public AnimationGargoyleTitanMeteorRain(EntityGargoyleTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 4;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 100;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 100 ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
	}
}


