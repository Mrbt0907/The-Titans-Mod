package net.minecraft.entity.titan.animation.endercolossus;
import net.minecraft.entity.titan.EntityEnderColossus;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationEnderColossusLightningAttack
extends AIAnimation
{
	private EntityEnderColossus entity;
	public AnimationEnderColossusLightningAttack(EntityEnderColossus test)
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
		return 80;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 40) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}
	}
}


