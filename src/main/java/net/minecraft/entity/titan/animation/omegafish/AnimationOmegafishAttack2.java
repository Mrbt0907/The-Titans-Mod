package net.minecraft.entity.titan.animation.omegafish;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityIronGolemTitan;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import net.minecraft.entity.titan.EntitySnowGolemTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationOmegafishAttack2 extends AIAnimation
{
	private EntitySilverfishTitan entity;
	public AnimationOmegafishAttack2(EntitySilverfishTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 5;
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
		if ((this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (this.entity.getAnimTick() == 24)
		{
			if (entity.isClient())
			this.entity.playSound("thetitans:titanSwing", 10F, 1.75F);
			double d8 = 4.0D;
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(1.0D, 1.0D, 1.0D).offset(dx, 0.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						if (entity.isClient())
						entity1.worldObj.playSoundEffect(this.entity.posX + dx, this.entity.posY, this.entity.posZ + dz, "random.explode", 4.0F, (1.0F + (this.entity.worldObj.rand.nextFloat() - this.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
						float f = (float)entity.getAttackValue(1.0F);
						int i = this.entity.getKnockbackAmount();
						this.entity.attackChoosenEntity(entity1, f * 2.0F, i);
						if ((!(entity1 instanceof EntityTitan)) && (!(entity1 instanceof EntitySnowGolemTitan)) && (!(entity1 instanceof EntityIronGolemTitan)))
						{
							entity1.addVelocity(-MathHelper.sin(this.entity.rotationYawHead * 3.1415927F / 180.0F) * i, i * 0.5D, MathHelper.cos(this.entity.rotationYawHead * 3.1415927F / 180.0F) * i);
						}

						if (this.entity.getRNG().nextInt(3) == 0)
						{
							entity1.setFire(20);
						}
					}
				}
			}
		}

		if (this.entity.getAnimTick() == 26)
		{
			double d8 = 8.0D;
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(1.0D, 1.0D, 1.0D).offset(dx, 0.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						if (entity.isClient())
						entity1.worldObj.playSoundEffect(this.entity.posX + dx, this.entity.posY, this.entity.posZ + dz, "random.explode", 4.0F, (1.0F + (this.entity.worldObj.rand.nextFloat() - this.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
						float f = (float)entity.getAttackValue(1.0F);
						int i = this.entity.getKnockbackAmount();
						this.entity.attackChoosenEntity(entity1, f * 2.0F, i);
						if ((!(entity1 instanceof EntityTitan)) && (!(entity1 instanceof EntitySnowGolemTitan)) && (!(entity1 instanceof EntityIronGolemTitan)))
						{
							entity1.addVelocity(-MathHelper.sin(this.entity.rotationYawHead * 3.1415927F / 180.0F) * i, i * 0.5D, MathHelper.cos(this.entity.rotationYawHead * 3.1415927F / 180.0F) * i);
						}

						if (this.entity.getRNG().nextInt(3) == 0)
						{
							entity1.setFire(20);
						}
					}
				}
			}
		}

		if (this.entity.getAnimTick() == 28)
		{
			double d8 = 12.0D;
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(1.0D, 1.0D, 1.0D).offset(dx, 0.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						if (entity.isClient())
						entity1.worldObj.playSoundEffect(this.entity.posX + dx, this.entity.posY, this.entity.posZ + dz, "random.explode", 4.0F, (1.0F + (this.entity.worldObj.rand.nextFloat() - this.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
						float f = (float)entity.getAttackValue(1.0F);
						int i = this.entity.getKnockbackAmount();
						this.entity.attackChoosenEntity(entity1, f * 2.0F, i);
						if ((!(entity1 instanceof EntityTitan)) && (!(entity1 instanceof EntitySnowGolemTitan)) && (!(entity1 instanceof EntityIronGolemTitan)))
						{
							entity1.addVelocity(-MathHelper.sin(this.entity.rotationYawHead * 3.1415927F / 180.0F) * i, i * 0.5D, MathHelper.cos(this.entity.rotationYawHead * 3.1415927F / 180.0F) * i);
						}

						if (this.entity.getRNG().nextInt(3) == 0)
						{
							entity1.setFire(20);
						}
					}
				}
			}
		}

		if (this.entity.getAnimTick() == 30)
		{
			double d8 = 16.0D;
			Vec3 vec3 = this.entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(1.0D, 1.0D, 1.0D).offset(dx, 0.0D, dz));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						if (entity.isClient())
						entity1.worldObj.playSoundEffect(this.entity.posX + dx, this.entity.posY, this.entity.posZ + dz, "random.explode", 4.0F, (1.0F + (this.entity.worldObj.rand.nextFloat() - this.entity.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
						float f = (float)entity.getAttackValue(1.0F);
						int i = this.entity.getKnockbackAmount();
						this.entity.attackChoosenEntity(entity1, f * 2.0F, i);
						if ((!(entity1 instanceof EntityTitan)) && (!(entity1 instanceof EntitySnowGolemTitan)) && (!(entity1 instanceof EntityIronGolemTitan)))
						{
							entity1.addVelocity(-MathHelper.sin(this.entity.rotationYawHead * 3.1415927F / 180.0F) * i, i * 0.5D, MathHelper.cos(this.entity.rotationYawHead * 3.1415927F / 180.0F) * i);
						}

						if (this.entity.getRNG().nextInt(3) == 0)
						{
							entity1.setFire(20);
						}
					}
				}
			}
		}
	}
}


