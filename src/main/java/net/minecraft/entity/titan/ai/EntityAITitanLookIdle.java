package net.minecraft.entity.titan.ai;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.titan.EntityTitan;
public class EntityAITitanLookIdle
extends EntityAIBase
{
	private EntityTitan idleEntity;
	private double lookX;
	private double lookZ;
	private int idleTime;
	public EntityAITitanLookIdle(EntityTitan p_i1647_1_)
	{
		this.idleEntity = p_i1647_1_;
		setMutexBits(3);
	}

	public boolean shouldExecute()
	{
		return !this.idleEntity.getWaiting() && this.idleEntity.getAnimID() == 0 && (this.idleEntity.getAttackTarget() == null) && (this.idleEntity.getRNG().nextFloat() < 0.1F);
	}

	public boolean continueExecuting()
	{
		return this.idleTime >= 0;
	}

	public void startExecuting()
	{
		double d0 = 24D * this.idleEntity.getRNG().nextDouble();
		this.lookX = Math.cos(d0);
		this.lookZ = Math.sin(d0);
		this.idleTime = (80 + this.idleEntity.getRNG().nextInt(40));
	}

	public void updateTask()
	{
		this.idleTime -= 1;
		if (idleEntity.getAttackTarget() != null)
		this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.getAttackTarget().posX, this.idleEntity.getAttackTarget().posY + this.idleEntity.getAttackTarget().getEyeHeight(), this.idleEntity.getAttackTarget().posZ, 64F / this.idleEntity.getTitanSizeMultiplier(), this.idleEntity.getVerticalFaceSpeed());
		else
		this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, 64F / this.idleEntity.getTitanSizeMultiplier(), this.idleEntity.getVerticalFaceSpeed());
	}
}


