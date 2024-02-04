package net.minecraft.entity.orespawnaddon;
import danger.orespawn.EmperorScorpion;
import danger.orespawn.IceBall;
import danger.orespawn.Kraken;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityIceBall
extends IceBall
{
	public EntityIceBall(World par1World)
	{
		super(par1World);
	}

	public EntityIceBall(World par1World, EntityLivingBase par3EntityPlayer)
	{
		super(par1World, par3EntityPlayer);
	}

	public EntityIceBall(World par1World, EntityLivingBase par2EntityLiving, int par3)
	{
		super(par1World, par2EntityLiving);
	}

	public EntityIceBall(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}

	protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if (par1MovingObjectPosition.entityHit != null && this.getThrower() != null && par1MovingObjectPosition.entityHit == this.getThrower())
		{
			return;
		}

		if (par1MovingObjectPosition.entityHit != null && this.getThrower() != null && this.getThrower() instanceof EntityOverlordScorpion && par1MovingObjectPosition.entityHit instanceof EmperorScorpion)
		{
			return;
		}

		if (par1MovingObjectPosition.entityHit != null && this.getThrower() != null && this.getThrower() instanceof EntityMethuselahKraken && par1MovingObjectPosition.entityHit instanceof Kraken)
		{
			return;
		}

		if (par1MovingObjectPosition.entityHit != null && this.getThrower() != null)
		{
			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 50F);
			par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 50F);
			++par1MovingObjectPosition.entityHit.motionY;
		}

		playSound("random.explode", 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
		if (!this.worldObj.isRemote)
		{
			this.worldObj.createExplosion(this.getThrower() != null ? this.getThrower() : this, this.posX, this.posY, this.posZ, 3.0F, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			for (int i = 0; i < 5; i++)
			{
				int x = this.worldObj.rand.nextInt(3);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					x = -x;
				}

				int y = this.worldObj.rand.nextInt(3);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					y = -y;
				}

				int z = this.worldObj.rand.nextInt(3);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					z = -z;
				}

				x = (int)(x + par1MovingObjectPosition.hitVec.xCoord);
				y = (int)(y + par1MovingObjectPosition.hitVec.yCoord);
				z = (int)(z + par1MovingObjectPosition.hitVec.zCoord);
				if (!this.worldObj.getBlock(x, y, z).isOpaqueCube())
				this.worldObj.setBlock(x, y, z, Blocks.ice);
			}
		}

		setDead();
	}

	public void onUpdate()
	{
		super.onUpdate();
		int mx = 4;
		for (int i = 0; i < mx; i++)
		{
			this.worldObj.spawnParticle("fireworksSpark", this.posX, this.posY, this.posZ, this.worldObj.rand.nextGaussian() / 10.0D, this.worldObj.rand.nextGaussian() / 10.0D, this.worldObj.rand.nextGaussian() / 10.0D);
		}
	}
}


