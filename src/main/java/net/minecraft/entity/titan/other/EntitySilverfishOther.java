package net.minecraft.entity.titan.other;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntitySilverfishOther extends EntitySilverfish
{
	public EntitySilverfishOther(World p_i1740_1_)
	{
		super(p_i1740_1_);
		this.setSize(0.3F, 0.3F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, false));
		this.tasks.addTask(7, new EntitySilverfishOther.EntityAIWander(this, 1.0D));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(19, Integer.valueOf(0));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("Type", this.getMobType());
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		this.setMobType(tagCompund.getInteger("Type"));
	}

	public int getMobType()
	{
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	public void setMobType(int miniontype)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(miniontype));
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		if (super.attackEntityAsMob(p_70652_1_))
		{
			if (p_70652_1_ instanceof EntityLivingBase && getMobType() == 0)
			{
				byte b0 = 0;
				if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					b0 = 7;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					b0 = 15;
				}

				if (b0 > 0)
				{
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.poison.id, b0 * 20, 0));
				}
			}

			return true;
		}

		else
		{
			return false;
		}
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	public void onUpdate()
	{
		this.renderYawOffset = this.rotationYaw;
		super.onUpdate();
	}

	public boolean isPotionApplicable(PotionEffect p_70687_1_)
	{
		return p_70687_1_.getPotionID() == Potion.poison.id && getMobType() == 0 ? false : super.isPotionApplicable(p_70687_1_);
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	{
		Object p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
		if (this.worldObj.rand.nextInt(25) == 0)
		{
			EntityZombie entityskeleton = new EntityZombie(this.worldObj);
			entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
			entityskeleton.onSpawnWithEgg((IEntityLivingData)null);
			this.worldObj.spawnEntityInWorld(entityskeleton);
			entityskeleton.setChild(true);
			entityskeleton.mountEntity(this);
		}

		return (IEntityLivingData)p_110161_1_1;
	}

	public String getCommandSenderName()
	{
		switch (this.getMobType())
		{
			case 1:
			return StatCollector.translateToLocal("entity.Gigafish.name");
			case 2:
			return StatCollector.translateToLocal("entity.Ironfish.name");
			case 3:
			return StatCollector.translateToLocal("entity.Scalefish.name");
			case 4:
			return StatCollector.translateToLocal("entity.Motherfish.name");
			case 5:
			return StatCollector.translateToLocal("entity.Maggot.name");
			default:
			return StatCollector.translateToLocal("entity.Poisonfish.name");
		}
	}

	public class EntityAIWander extends EntityAIBase
	{
		private EntityCreature entity;
		private double xPosition;
		private double yPosition;
		private double zPosition;
		private double speed;
		public EntityAIWander(EntityCreature p_i1648_1_, double p_i1648_2_)
		{
			this.entity = p_i1648_1_;
			this.speed = p_i1648_2_;
			setMutexBits(1);
		}

		public boolean shouldExecute()
		{
			if (this.entity.getAge() >= 100)
			{
				return false;
			}

			else if (this.entity.getRNG().nextInt(10) != 0)
			{
				return false;
			}

			else
			{
				Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
				if (vec3 == null)
				{
					return false;
				}

				else
				{
					this.xPosition = vec3.xCoord;
					this.yPosition = vec3.yCoord;
					this.zPosition = vec3.zCoord;
					return true;
				}
			}
		}

		public boolean continueExecuting()
		{
			return !this.entity.getNavigator().noPath();
		}

		public void startExecuting()
		{
			this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
		}
	}
}


