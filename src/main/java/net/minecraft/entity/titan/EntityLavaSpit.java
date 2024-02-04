package net.minecraft.entity.titan;
import net.minecraft.block.Block;
import net.minecraft.theTitans.core.TheCore;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityLavaSpit
extends EntityFireball
{
	public EntityLavaSpit(World worldIn)
	{
		super(worldIn);
		this.setSize(3F, 3F);
	}

	public EntityLavaSpit(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_)
	{
		super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
		this.setSize(3F, 3F);
	}

	public EntityLavaSpit(World worldIn, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_)
	{
		super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
		this.setSize(3F, 3F);
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
		if (!this.worldObj.isRemote)
		{
			float f;
			if (TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) == true)
			{
				f = 3000.0F;
			}

			else
			{
				f = 1000.0F;
			}

			if (movingObject.entityHit != null)
			{
				if (this.shootingEntity instanceof EntityTitan)
				((EntityTitan)this.shootingEntity).attackChoosenEntity(movingObject.entityHit, f, 3);
				playSound("random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			}

			else
			{
				playSound("random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			}

			int i1 = MathHelper.floor_double(this.posX);
			int i = MathHelper.floor_double(this.posY);
			int j1 = MathHelper.floor_double(this.posZ);
			for (int l1 = -2; l1 <= 2; l1++)
			{
				for (int i2 = -2; i2 <= 2; i2++)
				{
					for (int j = 0; j < 1; j++)
					{
						int j2 = i1 + l1;
						int l = j1 + i2;
						for (int y = 0; y <= 256 && this.worldObj.getBlock(j2, i - 1, l).getMaterial() == Material.air; y++)
						--i;
						Block block = this.worldObj.getBlock(j2, i, l);
						if (block.isAir(this.worldObj, j2, i, l))
						{
							this.worldObj.setBlock(j2, i, l, Blocks.flowing_lava, 3, 3);
						}
					}
				}
			}

			setDead();
		}
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean isBurning()
	{
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return false;
	}

	public boolean isInWater()
	{
		return false;
	}

	protected float getMotionFactor()
	{
		return 0.975F;
	}
}


