package net.minecraft.entity.titan.animation.ultimairongolemtitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.titan.EntityIronGolemTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationIronGolemTitanAttack2
extends AIAnimation
{
	private EntityIronGolemTitan entity;
	public AnimationIronGolemTitanAttack2(EntityIronGolemTitan test)
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
		return 150;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 150 ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 60) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if ((this.entity.getAnimTick() == 60) || (this.entity.getAnimTick() == 104))
		{
			if (entity.isClient())
			this.entity.shakeNearbyPlayerCameras(10D);
			this.entity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(48.0D, 16.0D, 48.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						entity1.motionY += 2D + entity.getRNG().nextDouble() + entity.getRNG().nextDouble();
						this.entity.attackEntityAsMob(entity1);
					}
				}
			}

			if (entity.isClient())
			this.entity.playSound("thetitans:titanSlam", 20.0F, 1.0F);
		}
	}
}


