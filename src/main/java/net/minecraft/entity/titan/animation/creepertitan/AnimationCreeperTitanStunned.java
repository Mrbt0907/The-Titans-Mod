package net.minecraft.entity.titan.animation.creepertitan;
import net.minecraft.entity.titan.EntityCreeperTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationCreeperTitanStunned
extends AIAnimation
{
	private EntityCreeperTitan entity;
	public AnimationCreeperTitanStunned(EntityCreeperTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 8;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 520;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 500 ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAnimTick() == 520)
		{
			this.entity.setAnimID(0);
			this.entity.setAnimTick(0);
		}

		if (this.entity.getAnimTick() == 460)
		{
			this.entity.isStunned = false;
		}

		else
		{
			this.entity.setAttackTarget(null);
		}
	}
}


