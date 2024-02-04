package net.minecraft.entity.titan.animation.creepertitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityCreeperTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationCreeperTitanAttack1
extends AIAnimation
{
	private EntityCreeperTitan entity;
	public AnimationCreeperTitanAttack1(EntityCreeperTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 2;
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
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 60) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if ((this.entity.getAnimTick() == 60) || (this.entity.getAnimTick() == 104))
		{
			float f = (float)entity.getAttackValue(1.0F);
			int i = 0;
			this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(16.0D, 8.0D, 16.0D)));
			if (entity.isClient())
			this.entity.shakeNearbyPlayerCameras(20D);
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(32.0D, 8.0D, 32.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						entity1.motionY += 1.0F + entity.getRNG().nextFloat();
						this.entity.attackChoosenEntity(entity1, f, i);
					}
				}
			}

			if (entity.isClient())
			this.entity.playSound("thetitans:titanSlam", 20.0F, 1.0F);
			List<?> list111 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(32.0D, 8.0D, 32.0D));
			if ((list111 != null) && (!list111.isEmpty()))
			{
				for (int i1 = 0; i1 < list111.size(); i1++)
				{
					Entity entity1 = (Entity)list111.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						entity1.motionY += 1.0F + entity.getRNG().nextFloat();
						this.entity.attackChoosenEntity(entity1, f, i);
					}
				}
			}
		}
	}
}


