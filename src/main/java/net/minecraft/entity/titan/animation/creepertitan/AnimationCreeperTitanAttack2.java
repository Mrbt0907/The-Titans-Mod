package net.minecraft.entity.titan.animation.creepertitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityCreeperTitan;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationCreeperTitanAttack2
extends AIAnimation
{
	private EntityCreeperTitan entity;
	public AnimationCreeperTitanAttack2(EntityCreeperTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 3;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 170;
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

		if (this.entity.getAnimTick() == 90)
		{
			double d8 = 12.0D;
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			float f = (float)entity.getAttackValue(1.0F);
			int i = 0;
			this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(16.0D, 8.0D, 16.0D)));
			if (entity.isClient())
			this.entity.shakeNearbyPlayerCameras(10D);
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(32.0D, 2.0D, 32.0D).offset(dx, 0.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						entity1.motionY += 1.0F + entity.getRNG().nextFloat() + entity.getRNG().nextFloat();
						this.entity.attackChoosenEntity(entity1, f * 2.0F, i);
						this.entity.attackChoosenEntity(entity1, f * 2.0F, i);
					}
				}
			}

			if (entity.isClient())
			{
				this.entity.playSound("thetitans:titanSlam", 20.0F, 1.0F);
				this.entity.playSound("thetitans:groundSmash", 20.0F, 1.0F);
			}
		}
	}
}


