package net.minecraft.entity.titan.animation.pigzombietitan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationPigZombieTitanRoar
extends AIAnimation
{
	private EntityPigZombieTitan entity;
	public AnimationPigZombieTitanRoar(EntityPigZombieTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 11;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 200;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if ((this.entity.getAnimTick() < 20) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if ((this.entity.getAnimTick() >= 20))
		{
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(64.0D, 64.0D, 64.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						entity1.hurtResistantTime = 0;
					}

					if (!entity.worldObj.isRemote && (entity1 instanceof EntityLivingBase) && !this.entity.canAttackClass(entity1.getClass()))
					{
						((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 4));
					}
				}
			}
		}

		if ((this.entity.getAnimTick() == 20))
		{
			if (entity.isClient())
			this.entity.playSound("thetitans:titanZombieRoar", 1000F, 1F);
			this.entity.collideWithEntities(this.entity.head, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.head.boundingBox.expand(64.0D, 64.0D, 64.0D)));
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(64.0D, 64.0D, 64.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						entity1.attackEntityFrom(DamageSource.causeThornsDamage(entity), 40F);
						++entity1.motionY;
					}
				}
			}
		}
	}
}


