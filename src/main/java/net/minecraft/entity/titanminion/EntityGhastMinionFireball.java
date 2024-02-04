package net.minecraft.entity.titanminion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityGhastMinionFireball
extends EntityLargeFireball
{
	public EntityGhastMinionFireball(World worldIn)
	{
		super(worldIn);
	}

	@SideOnly(Side.CLIENT)
	public EntityGhastMinionFireball(World worldIn, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_)
	{
		super(worldIn, p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_);
	}

	public EntityGhastMinionFireball(World worldIn, EntityLivingBase p_i1769_2_, double p_i1769_3_, double p_i1769_5_, double p_i1769_7_)
	{
		super(worldIn, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
		if (!this.worldObj.isRemote)
		{
			boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			if (movingObject.entityHit != null)
			{
				if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving && ((EntityLiving)this.shootingEntity).canAttackClass(movingObject.entityHit.getClass()))
				{
					movingObject.entityHit.hurtResistantTime = 0;
					((EntityLiving)this.shootingEntity).attackEntityAsMob(movingObject.entityHit);
					movingObject.entityHit.setFire((int)((EntityLiving)this.shootingEntity).getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue());
					this.worldObj.newExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, this.field_92057_e, flag, flag);
					this.setDead();
				}
			}

			else
			{
				int i = movingObject.blockX;
				int j = movingObject.blockY;
				int k = movingObject.blockZ;
				switch (movingObject.sideHit)
				{
					case 0:
					--j;
					break;
					case 1:
					++j;
					break;
					case 2:
					--k;
					break;
					case 3:
					++k;
					break;
					case 4:
					--i;
					break;
					case 5:
					++i;
				}

				if (this.worldObj.isAirBlock(i, j, k))
				{
					this.worldObj.setBlock(i, j, k, Blocks.fire);
					this.worldObj.newExplosion(this.shootingEntity != null ? this.shootingEntity : this, i, j, k, this.field_92057_e, flag, flag);
					this.setDead();
				}
			}
		}
	}
}


