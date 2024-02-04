package net.minecraft.entity.titan.animation.zombietitan;
import net.minecraft.entity.titan.EntityZombieTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationZombieTitanRangedAttack
extends AIAnimation
{
	private EntityZombieTitan entity;
	public AnimationZombieTitanRangedAttack(EntityZombieTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 12;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 110;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
	}
}


