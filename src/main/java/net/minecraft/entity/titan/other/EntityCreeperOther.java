package net.minecraft.entity.titan.other;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
public class EntityCreeperOther extends EntityCreeper
{
	private final ItemStack[] previousEquipment = new ItemStack[5];
	private int timeSinceIgnited;
	private int fuseTime = 30;
	private int explosionRadius = 3;
	public EntityCreeperOther(World p_i1733_1_)
	{
		super(p_i1733_1_);
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(19, Integer.valueOf(0));
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		this.setMobType(tagCompund.getInteger("Type"));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("Type", this.getMobType());
	}

	public int getMobType()
	{
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	public void setMobType(int miniontype)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(miniontype));
	}

	protected String getHurtSound()
	{
		return "mob.creeper.say";
	}

	protected String getDeathSound()
	{
		return "mob.creeper.death";
	}

	private void explode()
	{
		if (!this.worldObj.isRemote)
		{
			boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			if (this.getPowered())
			{
				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(this.explosionRadius * 2), flag);
			}

			else
			{
				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, flag);
			}

			this.setDead();
		}
	}

	public void onUpdate()
	{
		if (ForgeHooks.onLivingUpdate(this)) return;
		this.onEntityUpdate();
		if (this.isEntityAlive())
		{
			if (this.func_146078_ca())
			{
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();
			if (i > 0 && this.timeSinceIgnited == 0)
			{
				this.playSound("creeper.primed", 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;
			if (this.timeSinceIgnited < 0)
			{
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime)
			{
				this.timeSinceIgnited = this.fuseTime;
				this.explode();
			}
		}

		if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
		{
			this.setDead();
		}

		if (!this.worldObj.isRemote)
		{
			this.updateLeashedState();
		}

		if (!this.worldObj.isRemote)
		{
			int i = this.getArrowCountInEntity();
			if (i > 0)
			{
				if (this.arrowHitTimer <= 0)
				{
					this.arrowHitTimer = 20 * (30 - i);
				}

				--this.arrowHitTimer;
				if (this.arrowHitTimer <= 0)
				{
					this.setArrowCountInEntity(i - 1);
				}
			}

			for (int j = 0; j < 5; ++j)
			{
				ItemStack itemstack = this.previousEquipment[j];
				ItemStack itemstack1 = this.getEquipmentInSlot(j);
				if (!ItemStack.areItemStacksEqual(itemstack1, itemstack))
				{
					((WorldServer)this.worldObj).getEntityTracker().func_151247_a(this, new S04PacketEntityEquipment(this.getEntityId(), j, itemstack1));
					if (itemstack != null)
					{
						this.getAttributeMap().removeAttributeModifiers(itemstack.getAttributeModifiers());
					}

					if (itemstack1 != null)
					{
						this.getAttributeMap().applyAttributeModifiers(itemstack1.getAttributeModifiers());
					}

					this.previousEquipment[j] = itemstack1 == null ? null : itemstack1.copy();
				}
			}

			if (this.ticksExisted % 20 == 0)
			{
				this.func_110142_aN().func_94549_h();
			}
		}

		this.onLivingUpdate();
		double d0 = this.posX - this.prevPosX;
		double d1 = this.posZ - this.prevPosZ;
		float f = (float)(d0 * d0 + d1 * d1);
		float f1 = this.renderYawOffset;
		float f2 = 0.0F;
		this.field_70768_au = this.field_110154_aX;
		float f3 = 0.0F;
		if (f > 0.0025000002F)
		{
			f3 = 1.0F;
			f2 = (float)Math.sqrt((double)f) * 3.0F;
			f1 = (float)Math.atan2(d1, d0) * 180.0F / (float)Math.PI - 90.0F;
		}

		if (this.swingProgress > 0.0F)
		{
			f1 = this.rotationYaw;
		}

		if (!this.onGround)
		{
			f3 = 0.0F;
		}

		this.field_110154_aX += (f3 - this.field_110154_aX) * 0.3F;
		this.worldObj.theProfiler.startSection("headTurn");
		f2 = this.func_110146_f(f1, f2);
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("rangeChecks");
		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F)
		{
			this.prevRenderYawOffset -= 360.0F;
		}

		while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F)
		{
			this.prevRenderYawOffset += 360.0F;
		}

		while (this.rotationPitch - this.prevRotationPitch < -180.0F)
		{
			this.prevRotationPitch -= 360.0F;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYawHead - this.prevRotationYawHead < -180.0F)
		{
			this.prevRotationYawHead -= 360.0F;
		}

		while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F)
		{
			this.prevRotationYawHead += 360.0F;
		}

		this.worldObj.theProfiler.endSection();
		this.field_70764_aw += f2;
	}
}


