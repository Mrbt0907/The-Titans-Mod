package net.minecraft.entity.titan.other;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityUndeadOther extends EntityZombie
{
	public EntityUndeadOther(World p_i1745_1_)
	{
		super(p_i1745_1_);
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityGolem.class, 0, false));
	}

	public boolean isAIEnabled()
	{
		return true;
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

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(19, Integer.valueOf(0));
	}

	public int getMobType()
	{
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	public void setMobType(int miniontype)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(miniontype));
		switch (this.getMobType())
		{
			case 1:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.225D);
				break;
			}

			case 2:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22D);
				break;
			}

			case 3:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
				break;
			}

			case 4:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(22D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
				break;
			}

			case 5:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
				break;
			}

			case 6:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
				break;
			}

			case 7:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2325D);
				break;
			}

			case 8:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
				break;
			}

			case 9:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D);
				break;
			}

			case 10:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(32D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22D);
				break;
			}

			case 11:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
				break;
			}

			case 12:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(240D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
				break;
			}

			case 13:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(48D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
				break;
			}

			default:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
			}
		}

		this.setHealth(this.getMaxHealth());
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		boolean flag = super.attackEntityAsMob(p_70652_1_);
		if (flag)
		{
			this.worldObj.difficultySetting.getDifficultyId();
			if (this.getMobType() == 11 && p_70652_1_ instanceof EntityLivingBase)
			{
				byte b0 = 0;
				if (this.worldObj.difficultySetting == EnumDifficulty.EASY)
				{
					b0 = 5;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					b0 = 15;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					b0 = 40;
				}

				if (b0 > 0)
				{
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, b0 * 20, 3));
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.blindness.id, b0 * 20, 0));
				}
			}

			if (this.getMobType() == 12 && p_70652_1_ instanceof EntityLivingBase)
			{
				byte b0 = 0;
				if (this.worldObj.difficultySetting == EnumDifficulty.EASY)
				{
					b0 = 5;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					b0 = 15;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					b0 = 40;
				}

				if (b0 > 0)
				{
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, b0 * 20, 11));
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, b0 * 20, 11));
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.blindness.id, b0 * 20, 0));
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.confusion.id, b0 * 20, 0));
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.hunger.id, b0 * 20, 0));
				}
			}

			return true;
		}

		return flag;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if (this.getMobType() < 0)
		this.setMobType(0);
		if (this.getEntityAttribute(SharedMonsterAttributes.followRange).getBaseValue() < 32D)
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32D);
		switch (this.getMobType())
		{
			case 1:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.225D);
				break;
			}

			case 2:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22D);
				break;
			}

			case 3:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
				break;
			}

			case 4:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(22D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
				break;
			}

			case 5:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
				break;
			}

			case 6:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
				break;
			}

			case 7:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2325D);
				break;
			}

			case 8:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
				break;
			}

			case 9:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D);
				break;
			}

			case 10:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(32D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22D);
				break;
			}

			case 11:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.26D);
				break;
			}

			case 12:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(240D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
				break;
			}

			case 13:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(48D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
				break;
			}

			default:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
			}
		}
	}

	public int getTotalArmorValue()
	{
		switch (this.getMobType())
		{
			case 0:
			return super.getTotalArmorValue() + 8;
			case 1:
			return super.getTotalArmorValue() + 2;
			case 11:
			return super.getTotalArmorValue() + 6;
			case 12:
			return super.getTotalArmorValue() + 14;
			default:
			return super.getTotalArmorValue() + 2;
		}
	}

	public String getCommandSenderName()
	{
		switch (this.getMobType())
		{
			case 1:
			return StatCollector.translateToLocal("entity.ZombieGray.name");
			case 2:
			return StatCollector.translateToLocal("entity.ZombieGrunt.name");
			case 3:
			return StatCollector.translateToLocal("entity.ZombieStar.name");
			case 4:
			return StatCollector.translateToLocal("entity.ZombieScoffer.name");
			case 5:
			return StatCollector.translateToLocal("entity.ZombieMorge.name");
			case 6:
			return StatCollector.translateToLocal("entity.ZombieFresh.name");
			case 7:
			return StatCollector.translateToLocal("entity.ZombieRotting.name");
			case 8:
			return StatCollector.translateToLocal("entity.ZombieBrat.name");
			case 9:
			return StatCollector.translateToLocal("entity.ZombieMutilated.name");
			case 10:
			return StatCollector.translateToLocal("entity.ZombieHeadless.name");
			case 11:
			return StatCollector.translateToLocal("entity.ZombieGhoul.name");
			case 12:
			return StatCollector.translateToLocal("entity.ZombieWight.name");
			case 13:
			return StatCollector.translateToLocal("entity.ZombieEnd.name");
			default:
			return StatCollector.translateToLocal("entity.GoldenGuard.name");
		}
	}

	protected String getLivingSound()
	{
		switch (this.getMobType())
		{
			case 11:
			return "thetitans:ghoulLiving";
			case 12:
			return "thetitans:wightLiving";
			default:
			return "mob.zombie.say";
		}
	}

	protected String getHurtSound()
	{
		switch (this.getMobType())
		{
			case 0:
			return "mob.irongolem.hit";
			case 11:
			return "thetitans:ghoulGrunt";
			case 12:
			return "thetitans:wightGrunt";
			default:
			return "mob.zombie.hurt";
		}
	}

	protected String getDeathSound()
	{
		switch (this.getMobType())
		{
			case 0:
			return "mob.irongolem.death";
			case 11:
			return "thetitans:ghoulDeath";
			case 12:
			return "thetitans:wightDeath";
			default:
			return "mob.zombie.death";
		}
	}
}


