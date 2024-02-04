package net.minecraft.entity.titan;
import net.minecraft.block.Block;
import net.minecraft.theTitans.core.TheCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityGargoyleTitanFireball extends EntityFireball
{
	public EntityGargoyleTitanFireball(World worldIn)
	{
		super(worldIn);
		this.setSize(3F, 3F);
		this.setModelVarient(this.rand.nextInt(6));
		int i = this.rand.nextInt(100);
		if (i >= 40)
		this.setBlockType(0);
		else if (i < 40 && i >= 20)
		this.setBlockType(1);
		else if (i < 20 && i >= 10)
		this.setBlockType(2);
		else if (i < 10 && i >= 5)
		this.setBlockType(3);
		else if (i < 5 && i >= 2)
		this.setBlockType(4);
		else if (i == 1)
		this.setBlockType(5);
		else if (i == 0)
		this.setBlockType(6);
	}

	public EntityGargoyleTitanFireball(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_)
	{
		super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
		this.setSize(3F, 3F);
		this.setModelVarient(this.rand.nextInt(6));
		int i = this.rand.nextInt(100);
		if (i >= 40)
		this.setBlockType(0);
		else if (i < 40 && i >= 20)
		this.setBlockType(1);
		else if (i < 20 && i >= 10)
		this.setBlockType(2);
		else if (i < 10 && i >= 5)
		this.setBlockType(3);
		else if (i < 5 && i >= 2)
		this.setBlockType(4);
		else if (i == 1)
		this.setBlockType(5);
		else if (i == 0)
		this.setBlockType(6);
	}

	public EntityGargoyleTitanFireball(World worldIn, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_)
	{
		super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
		this.setSize(3F, 3F);
		this.setModelVarient(this.rand.nextInt(6));
		int i = this.rand.nextInt(100);
		if (i >= 40)
		this.setBlockType(0);
		else if (i < 40 && i >= 20)
		this.setBlockType(1);
		else if (i < 20 && i >= 10)
		this.setBlockType(2);
		else if (i < 10 && i >= 5)
		this.setBlockType(3);
		else if (i < 5 && i >= 2)
		this.setBlockType(4);
		else if (i == 1)
		this.setBlockType(5);
		else if (i == 0)
		this.setBlockType(6);
	}

	public boolean isBurning()
	{
		return false;
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
		if (!this.worldObj.isRemote)
		{
			float f;
			if (TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) == true)
			{
				f = 9000.0F;
			}

			else
			{
				f = 3000.0F;
			}

			if ((movingObject.entityHit != null) && !(movingObject.entityHit instanceof EntityGargoyleTitan))
			{
				if (this.shootingEntity != null && this.shootingEntity instanceof EntityGargoyleTitan)
				((EntityGargoyleTitan)this.shootingEntity).attackChoosenEntity(movingObject.entityHit, 2000, 0);
				this.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity != null ? this.shootingEntity : this), f);
			}

			if (movingObject.entityHit == null)
			{
				int i = MathHelper.floor_double(this.posY);
				int i1 = MathHelper.floor_double(this.posX);
				int j1 = MathHelper.floor_double(this.posZ);
				for (int l1 = -1; l1 <= 1; l1++)
				{
					for (int i2 = -1; i2 <= 1; i2++)
					{
						for (int j = 0; j <= 2; j++)
						{
							int j2 = i1 + l1;
							int k = i + j;
							int l = j1 + i2;
							Block block = this.worldObj.getBlock(j2, k, l);
							if (!block.isOpaqueCube())
							{
								switch (this.getBlockType())
								{
									case 0:
									this.worldObj.setBlock(j2, k, l, Blocks.stone, 0, 3);
									break;
									case 1:
									this.worldObj.setBlock(j2, k, l, Blocks.coal_ore, 0, 3);
									break;
									case 2:
									this.worldObj.setBlock(j2, k, l, Blocks.iron_ore, 0, 3);
									break;
									case 3:
									this.worldObj.setBlock(j2, k, l, Blocks.redstone_ore, 0, 3);
									break;
									case 4:
									this.worldObj.setBlock(j2, k, l, Blocks.gold_ore, 0, 3);
									break;
									case 5:
									this.worldObj.setBlock(j2, k, l, Blocks.diamond_ore, 0, 3);
									break;
									case 6:
									this.worldObj.setBlock(j2, k, l, Blocks.emerald_ore, 0, 3);
								}
							}
						}
					}
				}

				this.worldObj.newExplosion(this.shootingEntity != null ? this.shootingEntity : this, this.posX, this.posY + 3D, this.posZ, 2F, true, false);
			}

			setDead();
		}
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return false;
	}

	protected void entityInit()
	{
		this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
		this.dataWatcher.addObject(11, Byte.valueOf((byte)0));
	}

	public int getBlockType()
	{
		return this.dataWatcher.getWatchableObjectByte(10);
	}

	public void setBlockType(int p_82201_1_)
	{
		this.dataWatcher.updateObject(10, Byte.valueOf((byte)p_82201_1_));
	}

	public int getModelVarient()
	{
		return this.dataWatcher.getWatchableObjectByte(11);
	}

	public void setModelVarient(int p_82201_1_)
	{
		this.dataWatcher.updateObject(11, Byte.valueOf((byte)p_82201_1_));
	}
}


