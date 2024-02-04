package net.minecraft.entity.titan;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titanminion.EntityCaveSpiderMinion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import thehippomaster.AnimationAPI.AnimationAPI;
public class EntityCaveSpiderTitan
extends EntitySpiderTitan
{
	public boolean isSubdued;
	public EntityCaveSpiderTitan(World worldIn)
	{
		super(worldIn);
		experienceValue = (9000 + getExtraPower() * 350);
	}

	public float getTitanSizeMultiplier()
	{
		return super.getTitanSizeMultiplier() * 0.7F;
	}

	protected void applyEntityAI()
	{
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.CaveSpiderTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 12.0F;
	}

	public void updateRiderPosition()
	{
		if (riddenByEntity != null)
		{
			riddenByEntity.setPosition(posX, posY + (8.8D + getExtraPower() * 0.1D), posZ);
		}
	}

	protected boolean isMovementBlocked()
	{
		return (isSubdued) && (riddenByEntity == null) ? true : super.isMovementBlocked();
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		isSubdued = tagCompund.getBoolean("Subdued");
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("Subdued", isSubdued);
	}

	public boolean canBeCollidedWith()
	{
		return isStunned || isSubdued;
	}

	public double getSpeed()
	{
		return (getBonusID() == 1 ? 0.65D : 0.6D) + getExtraPower() * 0.001D;
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return p_70686_1_ != head.getClass() && p_70686_1_ != thorax.getClass() && p_70686_1_ != abdomen.getClass() && p_70686_1_ != rightlegs.getClass() && p_70686_1_ != leftlegs.getClass() && p_70686_1_ != EntityWebShot.class && (p_70686_1_ != EntityCaveSpiderMinion.class) && (p_70686_1_ != EntityCaveSpiderTitan.class);
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(50) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (isValidLightLevel());
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.CaveSpiderTitanMinionSpawnrate;
	}

	public void attackChoosenEntity(Entity damagedEntity, float damage, int knockbackAmount)
	{
		super.attackChoosenEntity(damagedEntity, damage, knockbackAmount);
		if (((damagedEntity instanceof EntityLivingBase)))
		{
			((EntityLivingBase)damagedEntity).addPotionEffect(new PotionEffect(Potion.poison.id, 800, 3));
		}
	}

	public boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
		p_70085_1_.swingItem();
		if ((isStunned) && (!isSubdued))
		{
			if ((itemstack != null) && (itemstack.getItem() == Items.golden_apple))
			{
				isSubdued = true;
				worldObj.playSoundAtEntity(this, "random.levelup", 10.0F, 1.0F);
				p_70085_1_.addChatMessage(new ChatComponentText(getCustomNameTag() + " has been subdued by " + p_70085_1_.getCommandSenderName()));
				return super.interact(p_70085_1_);
			}
		}

		else if (isSubdued)
		{
			if ((itemstack == null) && (p_70085_1_.ridingEntity == null))
			{
				p_70085_1_.mountEntity(this);
			}

			else if (itemstack != null)
			{
				if (itemstack.getItem() == Items.cooked_chicken)
				{
					AnimationAPI.sendAnimPacket(this, 3);
					setAnimID(3);
				}

				if (itemstack.getItem() == Items.bone)
				{
					AnimationAPI.sendAnimPacket(this, 9);
					setAnimID(9);
				}
			}
		}

		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		else
		{
			return super.attackEntityFrom(source, amount);
		}
	}

	public String getCommandSenderName()
	{
		switch (getTitanVariant())
		{
			case 1:
			return StatCollector.translateToLocal("entity.CaveSpiderTitanJesus.name");
			case 2:
			return StatCollector.translateToLocal("entity.CaveSpiderTitanOre.name");
			case 3:
			return StatCollector.translateToLocal("entity.CaveSpiderTitanVenomous.name");
			case 4:
			return StatCollector.translateToLocal("entity.CaveSpiderTitanIce.name");
			case 5:
			return StatCollector.translateToLocal("entity.CaveSpiderTitanVoid.name");
			default:
			return StatCollector.translateToLocal("entity.CaveSpiderTitan.name");
		}
	}

	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		if ((riddenByEntity != null) && ((riddenByEntity instanceof EntityLivingBase)) && (isSubdued))
		{
			setAttackTarget(null);
			rotationPitch = riddenByEntity.rotationPitch;
			rotationYawHead = ((EntityLivingBase)riddenByEntity).rotationYawHead;
			setRotation(rotationYaw, rotationPitch);
			p_70612_1_ = ((EntityLivingBase)riddenByEntity).moveStrafing;
			p_70612_2_ = ((EntityLivingBase)riddenByEntity).moveForward;
			if (((EntityLivingBase)riddenByEntity).moveForward > 0.0F)
			{
				addVelocity(-MathHelper.sin(((EntityLivingBase)riddenByEntity).rotationYawHead * 3.1415927F / 180.0F) * getSpeed(), 0.0D, MathHelper.cos(((EntityLivingBase)riddenByEntity).rotationYawHead * 3.1415927F / 180.0F) * getSpeed());
			}

			if (((EntityLivingBase)riddenByEntity).moveForward < 0.0F)
			{
				addVelocity(-MathHelper.sin(((EntityLivingBase)riddenByEntity).rotationYawHead * 3.1415927F / 180.0F) * -getSpeed(), 0.0D, MathHelper.cos(((EntityLivingBase)riddenByEntity).rotationYawHead * 3.1415927F / 180.0F) * -getSpeed());
			}

			if ((onGround) && (((EntityLivingBase)riddenByEntity).rotationPitch < -80.0F))
			{
				jump();
			}

			if (!worldObj.isRemote)
			{
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}

			prevLimbSwingAmount = limbSwingAmount;
			double d1 = posX - prevPosX;
			double d0 = posZ - prevPosZ;
			float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
			if (f4 > 1.0F)
			{
				f4 = 1.0F;
			}

			limbSwingAmount += (f4 - limbSwingAmount) * 0.4F;
			limbSwing += limbSwingAmount;
		}

		else
		{
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}
	}

	protected EntityLiving getMinion()
	{
		return new EntityCaveSpiderMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}
}


