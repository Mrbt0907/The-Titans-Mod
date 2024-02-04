package net.minecraft.entity.titan.animation.spidertitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySpiderTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSpiderTitanAttack3
extends AIAnimation
{
	private EntitySpiderTitan entity;
	public AnimationSpiderTitanAttack3(EntitySpiderTitan test)
	{
		super(test);
		entity = test;
	}

	public int getAnimID()
	{
		return 9;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 50;
	}

	public boolean continueExecuting()
	{
		return entity.getAnimTick() > getDuration() || entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((entity.getAnimTick() < 24) && (entity.getAttackTarget() != null))
		{
			entity.getLookHelper().setLookPositionWithEntity(entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (!entity.worldObj.isRemote && entity.getAnimTick() == 24)
		{
			float f = (float)entity.getAttackValue(1.0F);
			int i = entity.getKnockbackAmount();
			if (entity.isClient())
			{
				entity.shakeNearbyPlayerCameras(10D);
				entity.playSound("thetitans:titanSlam", 20.0F, 1.0F);
			}

			float d0 = 12F;
			float f3 = entity.renderYawOffset * (float)Math.PI / 180.0F;
			float f11 = MathHelper.sin(f3);
			float f4 = MathHelper.cos(f3);
			entity.collideWithEntities(entity.head, entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.head.boundingBox.expand(18.0D, 12.0D, 18.0D).offset(-(double)(f11 * d0), -8.0D, (double)(f4 * d0))));
			List<?> list11 = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(12.0D, 8.0D, 12.0D).offset(-(double)(f11 * d0), -4.0D, (double)(f4 * d0)));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (entity.canAttackClass(entity1.getClass()))
					{
						entity.attackChoosenEntity(entity1, f * 2.0F, i);
					}
				}
			}
		}
	}
}


