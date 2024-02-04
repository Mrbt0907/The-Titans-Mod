package net.minecraft.entity.titan.animation.omegafish;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationOmegafishUnburrow
extends AIAnimation
{
	private EntitySilverfishTitan entity;
	public AnimationOmegafishUnburrow(EntitySilverfishTitan test)
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
		return 70;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAnimTick() == 10)
		{
			if (entity.isClient())
			this.entity.playSound("thetitans:quickApperence", 20.0F, 1.0F);
			this.entity.motionX = -MathHelper.sin(this.entity.rotationYawHead * 3.1415927F / 180.0F) * 1.5F;
			this.entity.motionY = 1D;
			this.entity.motionZ = MathHelper.cos(this.entity.rotationYawHead * 3.1415927F / 180.0F) * 1.5F;
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(16.0D, 16.0D, 16.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						float f = (float)entity.getAttackValue(1.0F);
						int i = this.entity.getKnockbackAmount();
						this.entity.attackChoosenEntity(entity1, f, i);
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(32.0D, 16.0D, 32.0D)));
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(32.0D, 16.0D, 32.0D)));
					}
				}
			}
		}

		if (this.entity.getAnimTick() == 45)
		{
			if (entity.isClient())
			this.entity.playSound("thetitans:groundSmash", 20.0F, 1.25F);
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(16.0D, 16.0D, 16.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						float f = (float)entity.getAttackValue(1.0F);
						int i = this.entity.getKnockbackAmount() * 3;
						this.entity.attackChoosenEntity(entity1, f, i);
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(32.0D, 16.0D, 32.0D)));
					}
				}
			}
		}
	}
}


