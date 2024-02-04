package net.minecraft.theTitans;
import java.util.Iterator;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class EntityImmortalItem
extends EntityItem
{
	public EntityImmortalItem(World world, Entity original, ItemStack stack)
	{
		this(world, original.posX, original.posY, original.posZ, stack);
		this.delayBeforeCanPickup = 20;
		this.motionX = original.motionX;
		this.motionY = original.motionY;
		this.motionZ = original.motionZ;
		setEntityItemStack(stack);
		this.ignoreFrustumCheck = true;
	}

	public EntityImmortalItem(World world, double x, double y, double z, ItemStack stack)
	{
		super(world, x, y, z);
		setEntityItemStack(stack);
	}

	public EntityImmortalItem(World world, double x, double y, double z)
	{
		super(world, x, y, z);
		this.isImmuneToFire = true;
	}

	public EntityImmortalItem(World world)
	{
		super(world);
		this.isImmuneToFire = true;
	}

	protected void dealFireDamage(int damage) 
	{
		 
	}


	public boolean isEntityInvulnerable()
	{
		return true;
	}

	public boolean attackEntityFrom(DamageSource source, float damage)
	{
		EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, -1D);
		if (player != null)
		{
			this.delayBeforeCanPickup = 0;
			this.copyLocationAndAnglesFrom(player);
		}

		return false;
	}

	public void onUpdate()
	{
		ItemStack stack = getDataWatcher().getWatchableObjectItemStack(10);
		if ((stack != null) && (stack.getItem() != null))
		{
			if (stack.getItem().onEntityItemUpdate(this))
			{
				return;
			}
		}

		if (getEntityItem() == null)
		{
			setDead();
		}

		else
		{
			this.onEntityUpdate();
			if (this.delayBeforeCanPickup > 0)
			{
				this.delayBeforeCanPickup -= 1;
			}

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			this.motionY -= 0.03999999910593033D;
			this.noClip = func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
			moveEntity(this.motionX, this.motionY, this.motionZ);
			boolean flag = ((int)this.prevPosX != (int)this.posX) || ((int)this.prevPosY != (int)this.posY) || ((int)this.prevPosZ != (int)this.posZ);
			if ((flag) || (this.ticksExisted % 25 == 0))
			{
				if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava)
				{
					this.motionY = 0.20000000298023224D;
					this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
					this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
					playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
				}

				if (!this.worldObj.isRemote)
				{
					searchForOtherItemsNearby2();
				}
			}

			float f = 0.98F;
			if (this.onGround)
			{
				f = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.98F;
			}

			this.motionX *= f;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= f;
			if (this.onGround)
			{
				this.motionY *= -0.5D;
			}

			this.age += 1;
			ItemStack item = getDataWatcher().getWatchableObjectItemStack(10);
			if ((!this.worldObj.isRemote) && (this.age >= this.lifespan))
			{
				if (item == null)
				{
					setDead();
				}
			}

			if ((item != null) && (item.stackSize <= 0))
			{
				setDead();
			}
		}
	}

	private void searchForOtherItemsNearby2()
	{
		Iterator<?> iterator = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5D, 0.0D, 0.5D)).iterator();
		while (iterator.hasNext())
		{
			EntityItem entityitem = (EntityItem)iterator.next();
			combineItems(entityitem);
		}
	}
}


