package net.minecraft.entity.titan.animation.omegafish;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationOmegafishLightningAttack
extends AIAnimation
{
	private EntitySilverfishTitan entity;
	public AnimationOmegafishLightningAttack(EntitySilverfishTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 7;
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
		if ((this.entity.getAnimTick() < 17) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}
	}
}


