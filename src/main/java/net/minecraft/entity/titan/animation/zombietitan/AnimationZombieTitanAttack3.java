package net.minecraft.entity.titan.animation.zombietitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationZombieTitanAttack3
extends AIAnimation
{
	private EntityZombieTitan entity;
	public AnimationZombieTitanAttack3(EntityZombieTitan test)
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
		return 230;
	}

	public boolean continueExecuting()
	{
		return (this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned || !this.entity.isArmed()) ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		this.entity.renderYawOffset = this.entity.rotationYaw = this.entity.rotationYawHead;
		if ((this.entity.getAnimTick() < 10) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 180F, 40.0F);
		}

		if (this.entity.getAnimTick() == 120)
		{
			if (entity.isClient())
			{
				this.entity.shakeNearbyPlayerCameras(10D);
				this.entity.playSound("thetitans:titanSlam", 100F, 1F);
				this.entity.playSound("thetitans:titanPress", 100F, 1F);
			}

			double d8 = 2.25D * this.entity.getTitanSizeMultiplier();
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(this.entity.getTitanSizeMultiplier(), 8.0D, this.entity.getTitanSizeMultiplier())));
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(this.entity.getTitanSizeMultiplier(), 2.0D, this.entity.getTitanSizeMultiplier()).offset(dx, -8.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackChoosenEntity(entity1, f * 10.0F, i);
					}
				}
			}
		}
	}
}


