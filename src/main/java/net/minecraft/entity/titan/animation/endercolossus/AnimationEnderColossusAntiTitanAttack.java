package net.minecraft.entity.titan.animation.endercolossus;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityEnderColossus;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationEnderColossusAntiTitanAttack
extends AIAnimation
{
	private EntityEnderColossus entity;
	public AnimationEnderColossusAntiTitanAttack(EntityEnderColossus test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 1;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 30;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void startExecuting()
	{
		super.startExecuting();
		this.entity.setATAAID(this.entity.getRNG().nextInt(4));
	}

	public void updateTask()
	{
		if (entity.isClient())
		{
			if (this.entity.getAnimTick() == 4 && this.entity.getATAAID() == 0)
			this.entity.playSound("thetitans:titanEnderColossusChomp", 100F, 1F);
			if (this.entity.getAttackTarget() != null)
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if ((this.entity.getAnimTick() == 12) && (this.entity.getAttackTarget() != null))
		{
			if (entity.isClient())
			this.entity.shakeNearbyPlayerCameras(10D);
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount();
			this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(48.0D, 48.0D, 48.0D)));
			this.entity.attackChoosenEntity(this.entity.getAttackTarget(), f, i);
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity.getAttackTarget(), this.entity.getAttackTarget().boundingBox.expand(8.0D, 8.0D, 8.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackChoosenEntity(entity1, f, i);
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(6.0D, 6.0D, 6.0D)));
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(6.0D, 6.0D, 6.0D)));
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(6.0D, 6.0D, 6.0D)));
						this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(6.0D, 6.0D, 6.0D)));
					}
				}
			}
		}
	}
}


