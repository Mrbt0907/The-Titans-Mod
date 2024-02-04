package net.minecraft.entity.titan;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
public class EntitySnowGolemTitan
extends EntityTitan
implements IRangedAttackMob
{
	public EntitySnowGolemTitan(World worldIn)
	{
		super(worldIn);
		setSize(10.0F, 30.0F);
		getNavigator().setAvoidsWater(true);
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, IMob.mobSelector));
		EntityIronGolemTitan.addTitanTargetingTaskToEntity(this);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	protected void onHitboxUpdate()
	{
		super.onHitboxUpdate();
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 4.0F;
	}

	public void onKillCommand()
	{
		playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
		setDead();
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		double doub = getDistanceToEntity(p_70652_1_);
		if (doub < 30.0D)
		{
			return super.attackEntityAsMob(p_70652_1_);
		}

		return false;
	}

	public float getTitanSizeMultiplier()
	{
		return 16F;
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.UTILITY;
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.SnowGolemMinionSpawnrate;
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityPlayer.class) && (p_70686_1_ != EntitySnowman.class) && (p_70686_1_ != EntitySnowGolemTitan.class);
	}

	public boolean hasNoSoul()
	{
		return true;
	}

	public String getCommandSenderName()
	{
		return "\u00A7o" + super.getCommandSenderName();
	}

	public void onLivingUpdate()
	{
		if ((getAttackTarget() != null) && (ticksExisted % 30 == 0))
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 <= getMeleeRange())
			{
				swingItem();
				getLookHelper().setLookPositionWithEntity(getAttackTarget(), 180.0F, 30.0F);
				attackEntityAsMob(getAttackTarget());
			}
		}

		List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(128D, 128D, 128D));
		if ((list1 != null) && (!list1.isEmpty()) && !worldObj.isRemote)
		{
			for (int i1 = 0; i1 < list1.size(); i1++)
			{
				Entity entity = (Entity)list1.get(i1);
				if ((entity != null) && ((entity instanceof EntitySnowman)))
				{
					if (getAttackTarget() != null)
					{
						if (!getAttackTarget().canEntityBeSeen(entity))
						((EntitySnowman)entity).getMoveHelper().setMoveTo(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2D);
						((EntitySnowman)entity).setAttackTarget(getAttackTarget());
						((EntitySnowman)entity).getLookHelper().setLookPositionWithEntity(getAttackTarget(), 40.0F, 40.0F);
						if (((EntitySnowman)entity).isCollidedHorizontally)
						((EntitySnowman)entity).motionY = 0.25D;
						if ((entity.ticksExisted + entity.getEntityId()) % 20 == 0)
						((EntitySnowman)entity).attackEntityWithRangedAttack(getAttackTarget(), 1F);
					}
				}
			}
		}

		if (getAttackTarget() != null && (ticksExisted + getEntityId()) % 20 == 0 && !worldObj.isRemote)
		{
			attackEntityWithRangedAttack(getAttackTarget(), 0.0F);
		}

		if (!worldObj.isRemote)
		{
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(posY);
			int k = MathHelper.floor_double(posZ);
			if ((isWet()) && (ticksExisted % 40 == 0))
			{
				attackEntityFrom(DamageSource.drown, 1.0F);
			}

			if (worldObj.getBiomeGenForCoords(i, k).getFloatTemperature(i, j, k) > 1.5F)
			{
				attackEntityFrom(DamageSource.onFire, 1.0F);
			}

			for (int l = 0; l < 1024; l++)
			{
				i = MathHelper.floor_double(posX + (rand.nextFloat() - 0.5D) * width);
				j = MathHelper.floor_double(posY);
				k = MathHelper.floor_double(posZ + (rand.nextFloat() - 0.5D) * width);
				if ((worldObj.getBlock(i, j, k).getMaterial() == Material.air) && (worldObj.getBiomeGenForCoords(i, k).getFloatTemperature(i, j, k) < 1.3F) && (Blocks.snow_layer.canPlaceBlockAt(worldObj, i, j, k)))
				{
					worldObj.setBlock(i, j, k, Blocks.snow_layer);
				}
			}
		}

		super.onLivingUpdate();
	}

	protected Item getDropItem()
	{
		return Items.snowball;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		for (int x = 0; x < 1; x++)
		{
			EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
			itembomb.setEntityItemStack(new ItemStack(Blocks.lit_pumpkin));
			itembomb.setItemCount(1);
			worldObj.spawnEntityInWorld(itembomb);
		}

		for (int x = 0; x < 4; x++)
		{
			EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
			itembomb.setEntityItemStack(new ItemStack(Items.snowball));
			itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
			worldObj.spawnEntityInWorld(itembomb);
		}

		for (int x = 0; x < 2; x++)
		{
			EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
			itembomb.setEntityItemStack(new ItemStack(Items.emerald));
			itembomb.setItemCount(2 + rand.nextInt(2 + p_70628_2_) + p_70628_2_);
			worldObj.spawnEntityInWorld(itembomb);
		}

		for (int x = 0; x < 2; x++)
		{
			EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
			itembomb.setEntityItemStack(new ItemStack(Items.diamond));
			itembomb.setItemCount(2 + rand.nextInt(2 + p_70628_2_) + p_70628_2_);
			worldObj.spawnEntityInWorld(itembomb);
		}
	}

	protected String getHurtSound()
	{
		return "step.snow";
	}

	protected String getDeathSound()
	{
		return "dig.snow";
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		if ((getHealth() > 0.0F) && (!worldObj.isRemote))
		{
			EntitySnowman entitychicken = new EntitySnowman(worldObj);
			entitychicken.setLocationAndAngles(posX + (rand.nextFloat() - 0.5D) * width, posY + getEyeHeight(), posZ + (rand.nextFloat() - 0.5D) * width, rotationYaw, 0.0F);
			worldObj.spawnEntityInWorld(entitychicken);
			entitychicken.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
			entitychicken.setHealth(20.0F);
			entitychicken.setCustomNameTag("Reinforced Snow Golem");
		}

		faceEntity(p_82196_1_, 180.0F, 30.0F);
		getDistanceToEntity(p_82196_1_);
		double d8 = 6D;
		Vec3 vec3 = getLook(1.0F);
		double d1 = p_82196_1_.posX - (posX + vec3.xCoord * d8);
		double d2 = (p_82196_1_.posY + (p_82196_1_.getEyeHeight())) - (posY + 20D + vec3.yCoord * d8);
		double d3 = p_82196_1_.posZ - (posZ + vec3.zCoord * d8);
		EntityTitanFireball entitylargefireball = new EntityTitanFireball(worldObj, this, d1, d2, d3, 6);
		entitylargefireball.posX = (posX + vec3.xCoord * d8);
		entitylargefireball.posY = (posY + 20D + vec3.yCoord * d8);
		entitylargefireball.posZ = (posZ + vec3.zCoord * d8);
		worldObj.spawnEntityInWorld(entitylargefireball);
		entitylargefireball.setFireballID(6);
		playSound("random.bow", 10.0F, 0.5F);
		playSound("random.bow", 10.0F, 0.5F);
	}

	public int getTotalArmorValue()
	{
		return 15;
	}

	public float getEyeHeight()
	{
		return 27.2F;
	}

	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	protected double cap()
	{
		return super.cap();
	}

	protected double getRegen()
	{
		return 0.01F;
	}
	
	@Override
	public boolean shouldCrush()
	{
		return true;
	}
}


