package net.minecraft.entity.titan.other;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
public class EntityThinUndeadOther extends EntityZombie
{
	public EntityThinUndeadOther(World p_i1745_1_)
	{
		super(p_i1745_1_);
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
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7D);
				this.heal(80F);
			}

			case 2:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				this.heal(50F);
			}

			case 3:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.275D);
				this.heal(12F);
			}

			case 4:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.225D);
				this.heal(24F);
			}

			default:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
				this.heal(20F);
			}
		}
	}

	public int getTotalArmorValue()
	{
		int i = super.getTotalArmorValue() + (this.getMobType() == 4 ? 10 : 0);
		if (i > 20)
		{
			i = 20;
		}

		return i;
	}

	public String getCommandSenderName()
	{
		switch (this.getMobType())
		{
			case 1:
			return StatCollector.translateToLocal("entity.SpiderGiant.name");
			case 2:
			return StatCollector.translateToLocal("entity.SpiderLarge.name");
			case 3:
			return StatCollector.translateToLocal("entity.SpiderSmall.name");
			case 4:
			return StatCollector.translateToLocal("entity.SpiderArmored.name");
			case 5:
			return StatCollector.translateToLocal("entity.SpiderOrb.name");
			default:
			return StatCollector.translateToLocal("entity.SpiderUndead.name");
		}
	}

	protected String getLivingSound()
	{
		return "mob.zombie.say";
	}

	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}

	protected String getDeathSound()
	{
		return "mob.zombie.death";
	}
}


