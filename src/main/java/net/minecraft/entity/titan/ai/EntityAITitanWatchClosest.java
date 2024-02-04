package net.minecraft.entity.titan.ai;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
public class EntityAITitanWatchClosest extends net.minecraft.entity.ai.EntityAIBase
{
	private EntityTitan theWatcher;
	protected Entity closestEntity;
	private float maxDistanceForPlayer;
	private int lookTime;
	private float field_75331_e;
	private Class<?> watchedClass;
	public EntityAITitanWatchClosest(EntityTitan p_i1631_1_, Class<?> p_i1631_2_, float p_i1631_3_)
	{
		this.theWatcher = p_i1631_1_;
		this.watchedClass = p_i1631_2_;
		this.maxDistanceForPlayer = p_i1631_3_;
		this.field_75331_e = 0.025F;
		setMutexBits(2);
	}

	public EntityAITitanWatchClosest(EntityTitan p_i1632_1_, Class<?> p_i1632_2_, float p_i1632_3_, float p_i1632_4_)
	{
		this.theWatcher = p_i1632_1_;
		this.watchedClass = p_i1632_2_;
		this.maxDistanceForPlayer = p_i1632_3_;
		this.field_75331_e = p_i1632_4_;
		setMutexBits(2);
	}

	public boolean shouldExecute()
	{
		if (this.theWatcher.getRNG().nextFloat() >= this.field_75331_e)
		{
			return false;
		}

		if (this.theWatcher.motionX != 0 || this.theWatcher.motionZ != 0)
		{
			return false;
		}

		if (this.watchedClass == EntityPlayer.class)
		{
			this.closestEntity = this.theWatcher.worldObj.getClosestPlayerToEntity(this.theWatcher, this.maxDistanceForPlayer);
		}

		else
		{
			this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.boundingBox.expand(this.maxDistanceForPlayer, this.maxDistanceForPlayer, this.maxDistanceForPlayer), this.theWatcher);
		}

		return !this.theWatcher.getWaiting() && this.theWatcher.getAnimID() != 13 && (this.theWatcher.getAttackTarget() == null) && (this.closestEntity != null);
	}

	public boolean continueExecuting()
	{
		return this.closestEntity.isEntityAlive() && this.lookTime > 0;
	}

	public void startExecuting()
	{
		this.lookTime = (40 + this.theWatcher.getRNG().nextInt(40));
		if (closestEntity instanceof EntityGiantZombie)
		((EntityGiantZombie)closestEntity).faceEntity(theWatcher, 10F, 40F);
	}

	public void resetTask()
	{
		this.closestEntity = null;
	}

	public void updateTask()
	{
		if (theWatcher.getAttackTarget() != null)
		closestEntity = theWatcher.getAttackTarget();
		this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 64F / this.theWatcher.getTitanSizeMultiplier(), this.theWatcher.getVerticalFaceSpeed());
		this.lookTime -= 1;
	}
}


