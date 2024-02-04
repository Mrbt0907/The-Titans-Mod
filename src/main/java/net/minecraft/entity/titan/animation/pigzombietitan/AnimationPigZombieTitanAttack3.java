package net.minecraft.entity.titan.animation.pigzombietitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationPigZombieTitanAttack3
extends AIAnimation
{
	private EntityPigZombieTitan entity;
	public AnimationPigZombieTitanAttack3(EntityPigZombieTitan test)
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
		return (this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned || this.entity.worldObj.getBlock((int)this.entity.posX, (int)this.entity.posY - 1, (int)this.entity.posZ).getExplosionResistance(this.entity) <= 1.5F) ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		this.entity.renderYawOffset = this.entity.rotationYaw = this.entity.rotationYawHead;
		if (this.entity.getAnimTick() == 120)
		{
			if (entity.isClient())
			{
				this.entity.shakeNearbyPlayerCameras(10D);
				this.entity.playSound("thetitans:titanSlam", 100F, 1F);
				this.entity.playSound("thetitans:titanPress", 100F, 1F);
			}

			double d8 = 36.0D;
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(16.0D, 8.0D, 16.0D)));
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(32.0D, 2.0D, 32.0D).offset(dx, 0.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackChoosenEntity(entity1, f * 15.0F, i);
					}
				}
			}
		}
	}
}


