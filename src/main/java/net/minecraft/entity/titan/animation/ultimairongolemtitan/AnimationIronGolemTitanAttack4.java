package net.minecraft.entity.titan.animation.ultimairongolemtitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.titan.EntityIronGolemTitan;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationIronGolemTitanAttack4
extends AIAnimation
{
	private EntityIronGolemTitan entity;
	public AnimationIronGolemTitanAttack4(EntityIronGolemTitan test)
	{
		super(test);
		this.entity = test;
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
		return 90;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 90 ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 40) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (entity.isClient() && this.entity.getAnimTick() == 44)
		{
			this.entity.playSound("thetitans:titanSwing", 100F, 1F);
			this.entity.playSound("mob.irongolem.throw", 100.0F, 0.5F);
		}

		if (this.entity.getAnimTick() == 48)
		{
			if (entity.isClient())
			this.entity.shakeNearbyPlayerCameras(10D);
			double d8 = entity.getTitanSizeMultiplier();
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			this.entity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
			this.entity.getKnockbackAmount();
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(32.0D, 4.0D, 32.0D).offset(dx, -2.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackEntityAsMob(entity1);
						entity1.addVelocity(-MathHelper.sin(this.entity.rotationYawHead * 3.1415927F / 180.0F) * 3F, 0D, MathHelper.cos(this.entity.rotationYawHead * 3.1415927F / 180.0F) * 3F);
					}
				}
			}

			if (entity.isClient())
			{
				this.entity.playSound("thetitans:titanSlam", 20.0F, 1.0F);
				this.entity.playSound("thetitans:titanStep", 20.0F, 1.0F);
				this.entity.playSound("thetitans:titanStep", 20.0F, 1.0F);
				this.entity.playSound("thetitans:titanStep", 20.0F, 1.0F);
				this.entity.playSound("thetitans:titanStep", 20.0F, 1.0F);
			}
		}
	}
}


