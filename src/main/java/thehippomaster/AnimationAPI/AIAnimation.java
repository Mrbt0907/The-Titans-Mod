package thehippomaster.AnimationAPI;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
public abstract class AIAnimation extends EntityAIBase 
{

	public AIAnimation(IAnimatedEntity entity)
	{
		animatedEntity = entity;
		setMutexBits(7);
	}

	public abstract int getAnimID();
	public EntityLiving getEntity()
	{
		return (EntityLiving)animatedEntity;
	}

	public abstract boolean isAutomatic();
	public abstract int getDuration();
	public boolean shouldAnimate()
	{
		return false;
	}

	public boolean shouldExecute()
	{
		if(isAutomatic()) return animatedEntity.getAnimID() == getAnimID();
		return shouldAnimate();
	}

	public void startExecuting()
	{
		if(!isAutomatic()) AnimationAPI.sendAnimPacket(animatedEntity, getAnimID());
		animatedEntity.setAnimTick(0);
	}

	public boolean continueExecuting()
	{
		return animatedEntity.getAnimTick() < getDuration() && getEntity().ticksExisted > 0;
	}

	public void resetTask()
	{
		AnimationAPI.sendAnimPacket(animatedEntity, 0);
		animatedEntity.setAnimTick(0);
	}

	private IAnimatedEntity animatedEntity;
}


