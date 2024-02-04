package net.minecraft.entity.titan.animation.endercolossus;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.titan.EntityEnderColossus;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationEnderColossusAttack3
extends AIAnimation
{
	private EntityEnderColossus entity;
	public AnimationEnderColossusAttack3(EntityEnderColossus test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 6;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 70;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAnimTick() > 70)
		{
			this.entity.setAttackTarget(null);
		}

		if ((this.entity.getAnimTick() < 30) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (entity.isClient())
		{
			if (this.entity.getAnimTick() == 10)
			this.entity.playSound("thetitans:titanEnderColossusScream", 100.0F, (this.entity.getRNG().nextFloat() - this.entity.getRNG().nextFloat()) * 0.2F + 1.1F);
			if (this.entity.getAnimTick() == 32)
			this.entity.playSound("thetitans:titanSwing", 10F, 1F);
		}

		if (this.entity.getAnimTick() == 36)
		{
			if (entity.isClient())
			this.entity.shakeNearbyPlayerCameras(20D);
			double d8 = 1.5D * this.entity.getTitanSizeMultiplier();
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(1.5D * this.entity.getTitanSizeMultiplier(), 12.0D, 1.5D * this.entity.getTitanSizeMultiplier()).offset(dx, -6.0D, dz)));
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(1.5D * this.entity.getTitanSizeMultiplier(), 8.0D, 1.5D * this.entity.getTitanSizeMultiplier()).offset(dx, -4.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackChoosenEntity(entity1, f, i);
					}
				}
			}

			if (entity.isClient())
			this.entity.playSound("thetitans:titanStrike", 20.0F, 1.0F);
		}

		if ((this.entity.getAnimTick() == 36))
		{
			this.entity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
			this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(64.0D, 8.0D, 64.0D)));
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(64.0D, 8.0D, 64.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						entity1.motionY += 2.0F + entity.getRNG().nextFloat() + entity.getRNG().nextFloat();
					}
				}
			}

			if (entity.isClient())
			this.entity.playSound("thetitans:titanSlam", 20.0F, 1.0F);
		}
	}
}


