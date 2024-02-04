package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.theTitans.core.TheCore;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityMagmaCubeTitan
extends EntitySlimeTitan
{
	public EntityMagmaCubeTitan(World worldIn)
	{
		super(worldIn);
		shouldParticlesBeUpward = true;
	}

	protected void applyEntityAI()
	{
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.MagmaCubeTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 16.0F;
	}

	protected String getSlimeParticle()
	{
		return "flame";
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityMagmaCube.class && p_70686_1_ != EntityMagmaCubeTitan.class);
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(50) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL);
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.MagmaCubeTitanMinionSpawnrate;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
	}

	protected boolean isValidLightLevel()
	{
		return true;
	}

	public boolean handleLavaMovement()
	{
		return false;
	}

	protected boolean makesSoundOnLand()
	{
		return true;
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

	protected String func_180487_n()
	{
		return "flame";
	}

	protected EntitySlime createProtoInstance()
	{
		return new EntityMagmaCube(worldObj);
	}

	protected EntitySlimeTitan createInstance()
	{
		return new EntityMagmaCubeTitan(worldObj);
	}

	protected Item getDropItem()
	{
		return Items.magma_cream;
	}

	public boolean isBurning()
	{
		return false;
	}

	protected int getJumpDelay()
	{
		return super.getJumpDelay() * 4;
	}

	protected String getDeathSound()
	{
		return "mob.magmacube.jump";
	}

	protected void alterSquishAmount()
	{
		squishAmount *= 0.95F;
	}

	protected void jump()
	{
		motionY = (4.0D + getSlimeSize() * 0.33F);
		isAirBorne = true;
		if (getAttackTarget() != null)
		{
			double d01 = getAttackTarget().posX - posX;
			double d11 = getAttackTarget().posZ - posZ;
			float f21 = MathHelper.sqrt_double(d01 * d01 + d11 * d11);
			double hor = 1.0D + getSlimeSize() * 0.25F;
			motionX = (d01 / f21 * hor * hor + motionX * hor);
			motionZ = (d11 / f21 * hor * hor + motionZ * hor);
		}
	}

	protected void func_180466_bG()
	{
		motionY = (2.5F + getSlimeSize() * 0.05F);
		isAirBorne = true;
		if (getAttackTarget() != null)
		{
			double d01 = getAttackTarget().posX - posX;
			double d11 = getAttackTarget().posZ - posZ;
			float f21 = MathHelper.sqrt_double(d01 * d01 + d11 * d11);
			double hor = 1.0D + getSlimeSize() * 0.25F;
			motionX = (d01 / f21 * hor * hor + motionX * hor);
			motionZ = (d11 / f21 * hor * hor + motionZ * hor);
		}
	}

	protected boolean canDamagePlayer()
	{
		return true;
	}

	protected int getAttackStrength()
	{
		if (TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) == true)
		{
			return getSlimeSize() * 180;
		}

		return getSlimeSize() * 60;
	}

	protected String getJumpSound()
	{
		return "mob.magmacube.big";
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		if (source.isFireDamage())
		{
			heal(amount);
			return false;
		}

		return super.attackEntityFrom(source, amount);
	}

	protected double cap()
	{
		return super.cap();
	}
}


