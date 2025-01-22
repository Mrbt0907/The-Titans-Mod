package net.mrbt0907.thetitans.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.mrbt0907.thetitans.TheTitans;

public class EntityAIKami extends EntityAIBase
{
	private EntityLiving entity;
	
	public EntityAIKami(EntityLiving entity)
	{
		
		this.entity = entity;
	}
	
	@Override
	public boolean shouldExecute()
	{
		return true;
	}
	
	public void startExecuting()
    {
		if (entity.ticksExisted % 20 == 0)
			TheTitans.info("I IS ALIVE!");
    }
}
