package net.minecraft.entity.titan;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class EntityItemBomb
extends EntityThrowable
{
	public int xpColor;
	private boolean startSpewing;
	private int xpTicks;
	public EntityItemBomb(World p_i1773_1_)
	{
		super(p_i1773_1_);
		ticksExisted += rand.nextInt(200);
		setSize(3F, 3F);
		rotationYaw = (float)(Math.random() * 360.0D);
		motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 3F);
		motionY = (double)((float)(Math.random() * 0.2D) * 2.0F) + 1D;
		motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 3F);
	}

	public EntityItemBomb(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
	{
		this(p_i1775_1_);
		setPosition(p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(19, new Byte((byte)0));
		dataWatcher.addObject(20, new Integer(0));
		getDataWatcher().addObjectByDataType(10, 5);
	}

	public int getItemAmount()
	{
		return dataWatcher.getWatchableObjectInt(20);
	}

	public void setItemCount(int p_82215_1_)
	{
		dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
	}

	/**
	* Returns the ItemStack corresponding to the Entity (Note: if no item exists, will log an error but still return an
	* ItemStack containing Block.stone)
	*/
	public ItemStack getEntityItem()
	{
		ItemStack itemstack = getDataWatcher().getWatchableObjectItemStack(10);
		return itemstack == null ? new ItemStack(Blocks.bedrock) : itemstack;
	}

	/**
	* Sets the ItemStack for this entity
	*/
	public void setEntityItemStack(ItemStack p_92058_1_)
	{
		getDataWatcher().updateObject(10, p_92058_1_);
		getDataWatcher().setObjectWatched(10);
	}

	public boolean getWildCard()
	{
		return dataWatcher.getWatchableObjectByte(19) == 1;
	}

	public void setWildCard(boolean p_82201_1_)
	{
		setEntityItemStack(new ItemStack(Blocks.bedrock));
		dataWatcher.updateObject(19, Byte.valueOf((byte) (p_82201_1_ ? 1 : 0)));
	}

	/**
	* (abstract) Protected helper method to write subclass entity data to NBT.
	*/
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setShort("Value", (short)getItemAmount());
		tagCompound.setBoolean("WildCard", getWildCard());
		if (getEntityItem() != null)
		{
			tagCompound.setTag("Item", getEntityItem().writeToNBT(new NBTTagCompound()));
		}
	}

	/**
	* (abstract) Protected helper method to read subclass entity data from NBT.
	*/
	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		setItemCount(tagCompund.getShort("Value"));
		setWildCard(tagCompund.getBoolean("WildCard"));
		NBTTagCompound nbttagcompound1 = tagCompund.getCompoundTag("Item");
		setEntityItemStack(ItemStack.loadItemStackFromNBT(nbttagcompound1));
		getDataWatcher().getWatchableObjectItemStack(10);
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

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		else
		{
			if (!isDead && !worldObj.isRemote && source.getEntity() != null && !source.isExplosion())
			{
				if (getWildCard())
				startSpewing = true;
				else
				for (int i1 = 0; i1 < getItemAmount(); i1++)
				{
					EntityItem entityitem = new EntityItem(worldObj, posX, posY + 1D, posZ, new ItemStack(getEntityItem().getItem(), 1, getEntityItem().getItemDamage()));
					entityitem.delayBeforeCanPickup = 40;
					entityitem.motionX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 3F);
					entityitem.motionY = (double)((float)(Math.random() * 0.2D) * 2.0F) + 0.5D;
					entityitem.motionZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 3F);
					worldObj.spawnEntityInWorld(entityitem);
					setDead();
				}
			}

			return true;
		}
	}

	public void setDead()
	{
		super.setDead();
		playSound("random.explode", 5F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		playSound("random.break", 5F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		worldObj.spawnParticle(getItemAmount() >= 32 ? "hugeexplosion" : "largeexplode", posX + ((rand.nextDouble() - 0.5F) * width), posY + 3D + ((rand.nextDouble() - 0.5F) * height), posZ + ((rand.nextDouble() - 0.5F) * width), 0.0D, 0.0D, 0.0D);
	}

	@SuppressWarnings("deprecation")
	public void onUpdate()
	{
		onEntityUpdate();
		xpTicks ++;
		if (getWildCard() && xpTicks % 5 == 0)
		{
			if (Maths.chance())
			{
				Iterator<?> list = Item.itemRegistry.iterator();
				List<Item> items = new ArrayList<Item>();
				Item i;
				while (list.hasNext())
				{
					i = (Item)list.next();
					if (i != null)
					if (i != TitanItems.ultimaBlade && i != TitanItems.optimaAxe)
					items.add(i);
				}

				if (items.size() > 0)
				{
					i = items.get(Maths.random(items.size() - 1));
					if (i != null)
					setEntityItemStack(new ItemStack(i, i.getItemStackLimit(), 0));
				}
			}

			else
			{
				Iterator<?> list = Block.blockRegistry.iterator();
				List<Block> blocks = new ArrayList<Block>();
				Block i;
				while (list.hasNext())
				{
					i = (Block)list.next();
					if (i != null)
					if (i != Blocks.air)
					blocks.add(i);
				}

				if (blocks.size() > 0)
				{
					i = blocks.get(Maths.random(blocks.size() - 1));
					if (i != null)
					{
						setEntityItemStack(new ItemStack(i, 64, 0));
					}
				}
			}

			if (startSpewing)
			{
				EntityItem entityitem = new EntityItem(worldObj, posX, posY + 1D, posZ, getEntityItem());
				entityitem.delayBeforeCanPickup = 40;
				entityitem.motionX = Maths.random(-1.0D, 1.0D) * 0.5D;//(double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 3F);
				entityitem.motionY = Maths.random(0.5D, 1.0D) * 0.5D;//(double)((float)(Math.random() * 0.2D) * 2.0F) + 0.5D;
				entityitem.motionZ = Maths.random(-1.0D, 1.0D) * 0.5D;//(double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 3F);
				worldObj.spawnParticle(getItemAmount() >= 32 ? "hugeexplosion" : "largeexplode", posX + ((rand.nextDouble() - 0.5F) * width), posY + 3D + ((rand.nextDouble() - 0.5F) * height), posZ + ((rand.nextDouble() - 0.5F) * width), 0.0D, 0.0D, 0.0D);
				playSound("mob.chicken.plop", 5F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.75F);
				worldObj.spawnEntityInWorld(entityitem);
				setItemCount(getItemAmount() - 1);
			}
		}

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033D;
		noClip = func_145771_j(posX, (boundingBox.minY + boundingBox.maxY) / 2.0D, posZ);
		moveEntity(motionX, motionY, motionZ);
		float f = 0.98F;
		if (onGround)
		f = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ)).slipperiness * 0.98F;
		motionX *= (double)f;
		motionY *= 0.9800000190734863D;
		motionZ *= (double)f;
		if (onGround)
		motionY *= -0.5D;
		if (getEntityItem() == null)
		setEntityItemStack(new ItemStack(Blocks.bedrock));
		if (getItemAmount() < 1)
		setDead();
	}
}


