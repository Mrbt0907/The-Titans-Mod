package net.minecraft.entity.titan.animation.ultimairongolemtitan;
import net.minecraft.entity.titan.EntityIronGolemTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationIronGolemTitanRangedAttack
extends AIAnimation
{
	private EntityIronGolemTitan entity;
	public AnimationIronGolemTitanRangedAttack(EntityIronGolemTitan test)
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
		return 80;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
	}
}


