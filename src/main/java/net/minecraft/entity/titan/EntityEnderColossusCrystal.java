package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.titanminion.EntityDragonMinion;
import net.minecraft.entity.titanminion.EntityEndermanMinion;
import net.minecraft.entity.titanminion.EnumMinionType;
import net.minecraft.entity.titanminion.IMinion;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class EntityEnderColossusCrystal
extends EntityFlying
implements IMinion
{
	public int courseChangeCooldown;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	public int innerRotation;
	public EntityEnderColossus owner;
	public EntityEnderColossusCrystal(World p_i1735_1_)
	{
		super(p_i1735_1_);
		func_110163_bv();
		setSize(2.0F, 2.0F);
		this.innerRotation = this.rand.nextInt(100000);
		this.experienceValue = 10;
		this.isAirBorne = true;
		this.onGround = false;
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_)
	{
		return 15728880;
	}

	public float getBrightness(float p_70013_1_)
	{
		return 1.0F;
	}

	public void setDead()
	{
		super.setDead();
		if (this.owner != null)
		{
			this.owner.numOfCrystals -= 1;
		}
	}

	protected void onDeathUpdate()
	{
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.99D;
		this.motionY *= 0.99D;
		this.motionZ *= 0.99D;
		this.motionY -= 0.1D;
		if (this.isBurning())
		this.onGround = true;
		if (!this.worldObj.isRemote && (this.isEntityInsideOpaqueBlock() || !this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ) || this.onGround || this.worldObj.getBlock((int)this.posX, (int)this.posY - 1, (int)this.posZ).getMaterial().isSolid()))
		{
			if ((!this.worldObj.isRemote) && (this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
			{
				entityDropItem(new ItemStack(Blocks.glass, 1), 20.0F);
				entityDropItem(new ItemStack(Blocks.glass, 1), 28.0F);
				int i = 50;
				while (i > 0)
				{
					int j = EntityXPOrb.getXPSplit(i);
					i -= j;
					this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 20.0D, this.posZ, j));
				}
			}

			for (int i = 0; i < 200; i++)
			{
				double d2 = this.rand.nextGaussian() * 0.02D;
				double d0 = this.rand.nextGaussian() * 0.02D;
				double d1 = this.rand.nextGaussian() * 0.02D;
				this.worldObj.spawnParticle("hugeexplosion", this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d2, d0, d1);
			}

			setDead();
			if (!this.worldObj.isRemote)
			{
				this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 6.0F, true);
			}
		}
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		this.updateEnderColossusEnderCrystal();
		this.ignoreFrustumCheck = true;
		this.renderDistanceWeight = 100.0D;
		this.innerRotation += 1;
	}

	protected void updateEntityActionState()
	{
		double d0 = this.waypointX - this.posX;
		double d1 = this.waypointY - this.posY;
		double d2 = this.waypointZ - this.posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		if (this.getDistanceSq(waypointX, waypointY, waypointZ) > 10000D)
		{
			double d5 = (double)MathHelper.sqrt_double(d3);
			this.motionX += (d0 / d5 * 0.8D - this.motionX);
			this.motionY += (d1 / d5 * 0.8D - this.motionY);
			this.motionZ += (d2 / d5 * 0.8D - this.motionZ);
		}

		if ((d3 < 1.0D) || (d3 > 20000.0D))
		{
			if (this.owner != null)
			{
				this.waypointX = (this.owner.posX + (this.rand.nextFloat() * 4.0F - 2.0F) * (this.owner.width * 2F));
				this.waypointY = (this.owner.posY + this.owner.height + 48F + (this.rand.nextFloat() * 2.0F - 1.0F) * 24.0F);
				this.waypointZ = (this.owner.posZ + (this.rand.nextFloat() * 4.0F - 2.0F) * (this.owner.width * 2F));
			}

			else
			{
				this.waypointX = (this.posX + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
				this.waypointY = (this.posY + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
				this.waypointZ = (this.posZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
			}
		}

		if (this.courseChangeCooldown-- <= 0)
		{
			this.courseChangeCooldown += this.rand.nextInt(5) + 2;
			d3 = MathHelper.sqrt_double(d3);
			if (isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3))
			{
				if (d3 > 4048.0D)
				{
					this.motionX += d0 / d3 * 0.2D;
					this.motionY += d1 / d3 * 0.2D;
					this.motionZ += d2 / d3 * 0.2D;
				}

				else
				{
					this.motionX += d0 / d3 * 0.1D;
					this.motionY += d1 / d3 * 0.1D;
					this.motionZ += d2 / d3 * 0.1D;
				}
			}

			else
			{
				this.waypointX = this.posX;
				this.waypointY = this.posY;
				this.waypointZ = this.posZ;
			}
		}

		if (this.owner == null)
		{
			List<?> list1111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(256.0D, 256.0D, 256.0D));
			if ((list1111 != null) && (!list1111.isEmpty()))
			{
				for (int i1 = 0; i1 < list1111.size(); i1++)
				{
					Entity entity1 = (Entity)list1111.get(i1);
					if ((entity1 != null) && ((entity1 instanceof EntityEnderColossus)) && (((EntityEnderColossus)entity1).numOfCrystals < 20))
					{
						this.owner = ((EntityEnderColossus)entity1);
						this.owner.numOfCrystals += 1;
					}
				}
			}
		}
	}

	private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_)
	{
		double d4 = (this.waypointX - this.posX) / p_70790_7_;
		double d5 = (this.waypointY - this.posY) / p_70790_7_;
		double d6 = (this.waypointZ - this.posZ) / p_70790_7_;
		AxisAlignedBB axisalignedbb = this.boundingBox.copy();
		for (int i = 1; i < p_70790_7_; i++)
		{
			axisalignedbb.offset(d4, d5, d6);
			if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	private void updateEnderColossusEnderCrystal()
	{
		if (this.owner != null)
		{
			if (this.isDead)
			{
				this.owner.attackEntityFromPart(owner.rightEye, (new DamageSource("blindness")).setDamageBypassesArmor().setDamageIsAbsolute(), 1000.0F);
				this.owner.hurtResistantTime = 0;
				this.owner.attackEntityFromPart(owner.leftEye, (new DamageSource("blindness")).setDamageBypassesArmor().setDamageIsAbsolute(), 1000.0F);
				this.owner.hurtResistantTime = 0;
				if (!this.owner.isStunned)
				++this.owner.destroyedCrystals;
				this.owner = null;
			}

			else if (this.isEntityAlive() && !this.owner.isStunned)
			{
				this.owner.heal(60F);
			}
		}

		if (this.owner == null && this.rand.nextInt(10) == 0)
		{
			float f = 256.0F;
			List<?> list = this.worldObj.getEntitiesWithinAABB(EntityEnderColossus.class, this.boundingBox.expand(f, f, f));
			EntityEnderColossus entityendercrystal = null;
			double d0 = Double.MAX_VALUE;
			Iterator<?> iterator = list.iterator();
			while (iterator.hasNext())
			{
				EntityEnderColossus entityendercrystal1 = (EntityEnderColossus)iterator.next();
				double d1 = entityendercrystal1.getDistanceSqToEntity(this);
				if (d1 < d0)
				{
					d0 = d1;
					entityendercrystal = entityendercrystal1;
				}
			}

			this.owner = entityendercrystal;
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		if (((source.getEntity() instanceof EntityEndermanMinion)) || ((source.getEntity() instanceof EntityEnderColossus)) || ((source.getEntity() instanceof EntityDragon)) || ((source.getEntity() instanceof EntityEnderColossusCrystal)) || ((source.getEntity() instanceof EntityDragonMinion)))
		{
			return false;
		}

		if (source.isExplosion() || source.isFireDamage())
		this.onGround = true;
		return super.attackEntityFrom(source, amount);
	}

	protected String getHurtSound()
	{
		return "mob.irongolem.hit";
	}

	protected String getDeathSound()
	{
		return "mob.irongolem.death";
	}

	public EnumMinionType getMinionType()
	{
		return EnumMinionType.SPECIAL;
	}

	@Override
	public void setMinionType(int type) 
	{

		// TODO Auto-generated method stub
	}

	@Override
	public EntityLiving getMaster() 
	{

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMaster(EntityLiving entity) 
	{

		// TODO Auto-generated method stub
	}
}


