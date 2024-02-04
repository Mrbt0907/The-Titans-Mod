package net.minecraft.entity.titan.animation.creepertitan;
import net.minecraft.entity.titan.EntityCreeperTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationCreeperTitanSpit
extends AIAnimation
{
	private EntityCreeperTitan entity;
	public AnimationCreeperTitanSpit(EntityCreeperTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 6;
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
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
	}
}


