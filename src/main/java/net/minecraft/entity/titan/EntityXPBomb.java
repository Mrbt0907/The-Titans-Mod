package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityXPBomb
extends EntityThrowable
{
	public int xpColor;
	private int xpMax;
	public EntityXPBomb(World p_i1773_1_)
	{
		super(p_i1773_1_);
		xpMax = 0;
		rotationYaw = (float)(Math.random() * 360.0D);
		motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 3F);
		motionY = (double)((float)(Math.random() * 0.2D) * 2.0F) + 0.5D;
		motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 3F);
	}

	public EntityXPBomb(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
	{
		this(p_i1775_1_);
		setPosition(p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(20, new Integer(0));
	}

	public int getXPCount()
	{
		return dataWatcher.getWatchableObjectInt(20);
	}

	public void setXPCount(int p_82215_1_)
	{
		dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
	}

	/**
	* (abstract) Protected helper method to write subclass entity data to NBT.
	*/
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setShort("Value", (short)getXPCount());
	}

	/**
	* (abstract) Protected helper method to read subclass entity data from NBT.
	*/
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		setXPCount(p_70037_1_.getShort("Value"));
	}

	public boolean isBurning()
	{
		return false;
	}

	protected float getGravityVelocity()
	{
		return 0.05F;
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
	}

	public boolean canBeCollidedWith()
	{
		return true;
	}

	protected void onCollide(EntityPlayer player)
	{
		if (getXPCount() > 0)
		{
			player.addExperience(MathHelper.clamp_int(getXPCount() - 1000, 0, 1000));
			setXPCount(Math.max(getXPCount() - 1000, 0));
		}

		else
		setDead();
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		return false;
		else
		{
			if (!worldObj.isRemote && !isDead && source.getEntity() != null && !source.isExplosion())
			{
				for (int i1 = 0; i1 < 5; i1++)
				{
					int i = getXPCount() / 5;
					EntityXPOrb orb = new EntityXPOrb(worldObj, posX, posY + 1D, posZ, i);
					orb.motionY += 0.5D;
					orb.xpValue = i;
					worldObj.spawnEntityInWorld(orb);
				}

				setDead();
			}

			return true;
		}
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_)
	{
		float f1 = 0.5F;
		if (f1 < 0.0F)
		{
			f1 = 0.0F;
		}

		if (f1 > 1.0F)
		{
			f1 = 1.0F;
		}

		int i = super.getBrightnessForRender(p_70070_1_);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int)(f1 * 15.0F * 16.0F);
		if (j > 240)
		{
			j = 240;
		}

		return j | k << 16;
	}

	public void setDead()
	{
		super.setDead();
		playSound("random.explode", 5F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		playSound("random.orb", 5F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		worldObj.spawnParticle(getXPCount() >= 2000 ? "hugeexplosion" : "largeexplode", posX + ((rand.nextDouble() - 0.5F) * width), posY + 3D + ((rand.nextDouble() - 0.5F) * height), posZ + ((rand.nextDouble() - 0.5F) * width), 0.0D, 0.0D, 0.0D);
	}

	public void onUpdate()
	{
		if (getXPCount() > xpMax)
		xpMax = getXPCount();
		setSize(3F * Math.max((float)getXPCount() / (float)xpMax, 0.0F), 3F * Math.max((float)getXPCount() / (float)xpMax, 0.0F));
		EntityPlayer closestPlayer = worldObj.getClosestPlayerToEntity(this, 16D);
		if (closestPlayer != null)
		{
			double d1 = (closestPlayer.posX - posX) / 16D;
			double d2 = (closestPlayer.posY + (double)closestPlayer.getEyeHeight() - posY) / 16D;
			double d3 = (closestPlayer.posZ - posZ) / 16D;
			double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
			double d5 = 1.0D - d4;
			if (d5 > 0.0D)
			{
				d5 *= d5;
				motionX += d1 / d4 * d5 * 0.1D;
				motionY += d2 / d4 * d5 * 0.1D;
				motionZ += d3 / d4 * d5 * 0.1D;
			}
		}

		closestPlayer = worldObj.getClosestPlayerToEntity(this, 2D); 
		if (closestPlayer != null)
		onCollide(closestPlayer);
		onEntityUpdate();
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033D;
		noClip = func_145771_j(posX, (boundingBox.minY + boundingBox.maxY) / 2.0D, posZ);
		moveEntity(motionX, motionY, motionZ);
		float f = 0.98F;
		if (onGround)
		{
			f = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ)).slipperiness * 0.98F;
		}

		motionX *= (double)f;
		motionY *= 0.9800000190734863D;
		motionZ *= (double)f;
		if (onGround)
		motionY *= -0.5D;
		++xpColor;
	}
}


