package net.minecraft.entity.titan.animation.spidertitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySpiderTitan;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationSpiderTitanAttack4
extends AIAnimation
{
	private EntitySpiderTitan entity;
	public AnimationSpiderTitanAttack4(EntitySpiderTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 4;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 60;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 20) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 180.0F, 40.0F);
			this.entity.renderYawOffset = this.entity.rotationYaw = this.entity.rotationYawHead;
		}

		if (entity.isClient() && this.entity.getAnimTick() == 20)
		this.entity.playSound("thetitans:titanEnderColossusChomp", 10F, 1.1F);
		if (!entity.worldObj.isRemote && this.entity.getAnimTick() == 26 && this.entity.getAttackTarget() != null)
		{
			float f = (float)entity.getAttackValue(1.0F);
			int i = this.entity.getKnockbackAmount() * 2;
			this.entity.collideWithEntities(this.entity.head, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.head.boundingBox.expand(8.0D, 8.0D, 8.0D)));
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
					}
				}
			}
		}
	}
}


