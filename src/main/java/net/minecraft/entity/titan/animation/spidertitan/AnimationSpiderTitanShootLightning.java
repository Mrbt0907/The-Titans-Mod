package net.minecraft.entity.titan.animation.spidertitan;
import net.minecraft.entity.titan.EntityGammaLightning;
import net.minecraft.entity.titan.EntitySpiderTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSpiderTitanShootLightning
extends AIAnimation
{
	private EntitySpiderTitan entity;
	public AnimationSpiderTitanShootLightning(EntitySpiderTitan test)
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
		return 140;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		if ((this.entity.getAnimTick() == 68))
		this.entity.worldObj.addWeatherEffect(new EntityGammaLightning(this.entity.worldObj, this.entity.posX , this.entity.posY + 3.0D, this.entity.posZ, 0.6F, 0.1F, 0.2F));
	}
}


