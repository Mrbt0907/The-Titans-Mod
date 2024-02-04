package net.minecraft.entity.titan.ai;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityCreeperTitan;
import net.minecraft.entity.titan.EntityGargoyleTitan;
import net.minecraft.entity.titan.EntityIronGolemTitan;
import net.minecraft.entity.titan.EntitySnowGolemTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.entity.titanminion.EntityCreeperMinion;
public class EntityAINearestTargetTitan
extends EntityAITarget
{
	protected final Class<?> targetClass;
	protected final Sorter theNearestAttackableTargetSorter;
	protected IEntitySelector targetEntitySelector;
	protected EntityLivingBase targetEntity;
	public EntityAINearestTargetTitan(EntityCreature p_i1663_1_, Class<?> p_i1663_2_, int p_i1663_3_, boolean p_i1663_4_)
	{
		this(p_i1663_1_, p_i1663_2_, p_i1663_3_, p_i1663_4_, false);
	}

	public EntityAINearestTargetTitan(EntityCreature p_i1664_1_, Class<?> p_i1664_2_, int p_i1664_3_, boolean p_i1664_4_, boolean p_i1664_5_)
	{
		this(p_i1664_1_, p_i1664_2_, p_i1664_3_, p_i1664_4_, p_i1664_5_, (IEntitySelector)null);
	}

	public EntityAINearestTargetTitan(EntityCreature p_i1665_1_, Class<?> p_i1665_2_, int p_i1665_3_, boolean p_i1665_4_, boolean p_i1665_5_, final IEntitySelector p_i1665_6_)
	{
		super(p_i1665_1_, p_i1665_4_, p_i1665_5_);
		this.targetClass = p_i1665_2_;
		this.theNearestAttackableTargetSorter = new Sorter(p_i1665_1_);
		setMutexBits(1);
		this.targetEntitySelector = new IEntitySelector()
		{
			public boolean isEntityApplicable(Entity p_82704_1_)
			{
				return !(p_82704_1_ instanceof EntityLivingBase) ? false : (p_i1665_6_ != null && !p_i1665_6_.isEntityApplicable(p_82704_1_) ? false : EntityAINearestTargetTitan.this.isSuitableTarget((EntityLivingBase)p_82704_1_, false));
			}
		};
	}

	public boolean shouldExecute()
	{
		double d0 = getTargetDistance();
		List<?> list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, d0, d0), this.targetEntitySelector);
		Collections.sort(list, this.theNearestAttackableTargetSorter);
		if (list.isEmpty())
		{
			return false;
		}

		this.targetEntity = ((EntityLivingBase)list.get(0));
		return true;
	}

	public void startExecuting()
	{
		if (!this.taskOwner.worldObj.isRemote)
		this.taskOwner.setAttackTarget(this.targetEntity);
		super.startExecuting();
	}

	protected boolean isSuitableTarget(EntityLivingBase p_75296_1_, boolean p_75296_2_)
	{
		if (p_75296_1_ instanceof EntityTitan && (this.taskOwner instanceof EntitySnowGolemTitan || this.taskOwner instanceof EntityIronGolemTitan || this.taskOwner instanceof EntityGargoyleTitan))
		{
			return true;
		}

		if (p_75296_1_ == null)
		{
			return false;
		}

		else if (p_75296_1_ == this.taskOwner)
		{
			return false;
		}

		else if (!p_75296_1_.isEntityAlive())
		{
			return false;
		}

		else if (!this.taskOwner.canAttackClass(p_75296_1_.getClass()))
		{
			return false;
		}

		else if (EntityTitan.isADudEntity(p_75296_1_))
		{
			return false;
		}

		else if (p_75296_1_ instanceof EntityTitanSpirit)
		{
			return false;
		}

		else if ((this.taskOwner instanceof EntityCreeperTitan || this.taskOwner instanceof EntityCreeperMinion) && p_75296_1_ instanceof EntityPlayer && ((EntityPlayer)p_75296_1_).getCommandSenderName() == "Boom337317")
		{
			return false;
		}

		else if (p_75296_1_ instanceof EntityPlayer && ((EntityPlayer)p_75296_1_).getCommandSenderName() == "Umbrella_Ghast")
		{
			return false;
		}

		else if (p_75296_1_ instanceof EntityTitan && (((EntityTitan)p_75296_1_).getInvulTime() > 0 || ((EntityTitan)p_75296_1_).getWaiting() || ((EntityTitan)p_75296_1_).getAnimID() == 13))
		{
			return false;
		}

		else if (this.taskOwner instanceof EntityTitan && this.taskOwner.getAITarget() != null)
		{
			return false;
		}

		else
		{
			return super.isSuitableTarget(p_75296_1_, p_75296_2_);
		}
	}

	public static class Sorter implements Comparator<Object>
	{
		private final Entity theEntity;
		public Sorter(Entity p_i1662_1_)
		{
			this.theEntity = p_i1662_1_;
		}

		public int compare(Entity p_compare_1_, Entity p_compare_2_)
		{
			double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
			double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
			return d0 > d1 ? 1 : d0 < d1 ? -1 : 0;
		}

		public int compare(Object p_compare_1_, Object p_compare_2_)
		{
			return compare((Entity)p_compare_1_, (Entity)p_compare_2_);
		}
	}
}


