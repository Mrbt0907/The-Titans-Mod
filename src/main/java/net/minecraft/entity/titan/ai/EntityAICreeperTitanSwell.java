package net.minecraft.entity.titan.ai;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.titan.EntityCreeperTitan;
public class EntityAICreeperTitanSwell
extends EntityAIBase
{
	EntityCreeperTitan swellingCreeper;
	EntityLivingBase creeperAttackTarget;
	public EntityAICreeperTitanSwell(EntityCreeperTitan p_i1655_1_)
	{
		this.swellingCreeper = p_i1655_1_;
		setMutexBits(0);
	}

	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
		return (this.swellingCreeper.getCreeperState() > 0) || ((entitylivingbase != null) && (this.swellingCreeper.getDistanceSqToEntity(entitylivingbase) < 1.0D));
	}

	public void startExecuting()
	{
		this.swellingCreeper.getNavigator().clearPathEntity();
		this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
	}

	public void resetTask()
	{
		this.creeperAttackTarget = null;
	}

	public void updateTask()
	{
		if (this.creeperAttackTarget == null)
		{
			this.swellingCreeper.setCreeperState(-1);
		}

		else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 1.0D)
		{
			this.swellingCreeper.setCreeperState(-1);
		}

		else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget))
		{
			this.swellingCreeper.setCreeperState(-1);
		}

		else
		{
			this.swellingCreeper.setCreeperState(1);
		}
	}
}


