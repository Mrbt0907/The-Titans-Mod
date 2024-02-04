package net.minecraft.entity.titan.ai;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.titan.EntityGargoyle;
import net.minecraft.village.Village;
public class EntityAIDefendVillage extends EntityAITarget
{
	EntityGargoyle irongolem;
	/** The aggressor of the iron golem's village which is now the golem's attack target. */
	EntityLivingBase villageAgressorTarget;
	public EntityAIDefendVillage(EntityGargoyle p_i1659_1_)
	{
		super(p_i1659_1_, false, true);
		this.irongolem = p_i1659_1_;
		this.setMutexBits(1);
	}

	/**
	* Returns whether the EntityAIBase should begin execution.
	*/
	public boolean shouldExecute()
	{
		Village village = this.irongolem.getVillage();
		if (village == null)
		{
			return false;
		}

		else
		{
			this.villageAgressorTarget = village.findNearestVillageAggressor(this.irongolem);
			if (!this.isSuitableTarget(this.villageAgressorTarget, false))
			{
				if (this.taskOwner.getRNG().nextInt(20) == 0)
				{
					this.villageAgressorTarget = village.func_82685_c(this.irongolem);
					return this.isSuitableTarget(this.villageAgressorTarget, false);
				}

				else
				{
					return false;
				}
			}

			else
			{
				return true;
			}
		}
	}

	/**
	* Execute a one shot task or start executing a continuous task
	*/
	public void startExecuting()
	{
		this.irongolem.setAttackTarget(this.villageAgressorTarget);
		super.startExecuting();
	}
}


