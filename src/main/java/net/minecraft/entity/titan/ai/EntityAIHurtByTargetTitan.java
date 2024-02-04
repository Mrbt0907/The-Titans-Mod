package net.minecraft.entity.titan.ai;
import java.util.Iterator;
import java.util.List;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.AxisAlignedBB;
public class EntityAIHurtByTargetTitan extends EntityAITarget
{
	boolean entityCallsForHelp;
	private final Class<?>[] classesTargeted;
	public EntityAIHurtByTargetTitan(EntityCreature creatureIn, boolean entityCallsForHelpIn, Class<?>... targetClassesIn)
	{
		super(creatureIn, false);
		this.entityCallsForHelp = entityCallsForHelpIn;
		this.classesTargeted = targetClassesIn;
		this.setMutexBits(1);
	}

	/**
	* Returns whether the EntityAIBase should begin execution.
	*/
	public boolean shouldExecute()
	{
		return this.taskOwner.getAITarget() != null && this.taskOwner.canAttackClass(this.taskOwner.getAITarget().getClass());
	}

	/**
	* Execute a one shot task or start executing a continuous task
	*/
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
		this.taskOwner.func_142015_aE();
		if (this.entityCallsForHelp)
		{
			for (Class<?> oclass : this.classesTargeted)
			{
				double d0 = this.getTargetDistance();
				List<?> list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), AxisAlignedBB.getBoundingBox(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand(d0, d0, d0));
				Iterator<?> iterator = list.iterator();
				while (iterator.hasNext())
				{
					EntityCreature entitycreature = (EntityCreature)iterator.next();
					if (entitycreature.getClass() == oclass && this.taskOwner != entitycreature && entitycreature.getAttackTarget() == null && this.taskOwner.getAITarget() != null && entitycreature.canAttackClass(this.taskOwner.getAITarget().getClass()))
					{
						try
						{
							ReflectionHelper.findField(entitycreature.getClass(), new String[] 
							{
								 "randomSoundDelay" 
							}
							).setInt(entitycreature, entitycreature.getRNG().nextInt(40));
						}

						catch (Exception e)
						{
							entitycreature.playLivingSound();
						}

						entitycreature.setAttackTarget(this.taskOwner.getAITarget());
					}
				}
			}
		}

		super.startExecuting();
	}
}


