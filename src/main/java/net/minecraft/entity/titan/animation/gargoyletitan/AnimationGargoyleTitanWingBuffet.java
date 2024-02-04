package net.minecraft.entity.titan.animation.gargoyletitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.titan.EntityGargoyleTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationGargoyleTitanWingBuffet
extends AIAnimation
{
	private EntityGargoyleTitan entity;
	public AnimationGargoyleTitanWingBuffet(EntityGargoyleTitan test)
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
		return 140;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > 140 ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		if (this.entity.getAnimTick() == 16)
		{
			entity.addTitanVelocity(-MathHelper.sin(entity.renderYawOffset * 3.1415927F / 180.0F) * -5F, 1D, MathHelper.cos(entity.renderYawOffset * 3.1415927F / 180.0F) * -5F);
			double d8 = 24D;
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			this.entity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
			this.entity.getKnockbackAmount();
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(64D, 16D, 64D).offset(dx, 0.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						entity1.motionY += entity.getRNG().nextDouble();
						if (entity1 instanceof EntityTitan)
						((EntityTitan)entity1).addTitanVelocity(-MathHelper.sin(entity.renderYawOffset * 3.1415927F / 180.0F) * 15F, 2D, MathHelper.cos(entity.renderYawOffset * 3.1415927F / 180.0F) * 15F);
						entity1.addVelocity(-MathHelper.sin(entity.renderYawOffset * 3.1415927F / 180.0F) * 15F, 2D, MathHelper.cos(entity.renderYawOffset * 3.1415927F / 180.0F) * 15F);
					}
				}
			}
		}
	}
}


