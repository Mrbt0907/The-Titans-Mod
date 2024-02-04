package net.minecraft.entity.titan.ai;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.util.Vec3;
public class EntityAITitanWander extends net.minecraft.entity.ai.EntityAIBase
{
	private EntityTitan entity;
	private double xPosition;
	private double zPosition;
	private int randTimer;
	private boolean field_179482_g;
	public EntityAITitanWander(EntityTitan p_i45887_1_, int p_i45887_4_)
	{
		this.entity = p_i45887_1_;
		this.randTimer = p_i45887_4_;
		setMutexBits(1);
	}

	public boolean shouldExecute()
	{
		if (!this.field_179482_g)
		if (this.entity.getRNG().nextInt(this.randTimer) != 0)
		return false;
		if (this.entity.getAttackTarget() != null)
		{
			return false;
		}

		Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 128, 5);
		if (vec3 == null)
		{
			return false;
		}

		if (this.entity.getDistance(vec3.xCoord, entity.posY, vec3.zCoord) <= entity.width * 2)
		{
			return false;
		}

		this.xPosition = vec3.xCoord;
		this.zPosition = vec3.zCoord;
		this.field_179482_g = false;
		return true;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAttackTarget() == null && this.entity.getDistance(xPosition, entity.posY, zPosition) > 3D;
	}

	public void updateTask()
	{
		this.entity.moveTitanToPoint(xPosition, zPosition, true);
		this.entity.renderYawOffset = this.entity.rotationYaw = this.entity.rotationYawHead;
	}
}


