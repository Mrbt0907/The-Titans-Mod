package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import thehippomaster.AnimationAPI.AnimationAPI;
import thehippomaster.AnimationAPI.IAnimatedEntity;
import net.minecraft.entity.titan.animation.gargoyletitan.*;
public class EntityGargoyleTitan
extends EntityTitan
implements IAnimatedEntity
{
	private static final IEntitySelector attackEntitySelector = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_180027_1_)
		{
			return (!(p_180027_1_ instanceof EntityGargoyleTitan)) && (!(p_180027_1_ instanceof EntityIronGolemTitan)) && (!(p_180027_1_ instanceof EntitySnowGolemTitan)) && (!(p_180027_1_ instanceof EntityWitherTurret)) && (!(p_180027_1_ instanceof EntityWitherTurretGround));
		}
	};
	public EntityGargoyleTitan(World worldIn)
	{
		super(worldIn);
		meleeTitan = true;
		setSize(32F, 74F);
		getNavigator().setAvoidsWater(true);
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, IMob.mobSelector));
		addTitanTargetingTaskToEntity(this);
		tasks.addTask(1, new AnimationGargoyleTitanLavaSpit(this));
		tasks.addTask(1, new AnimationGargoyleTitanWaterSpout(this));
		tasks.addTask(1, new AnimationGargoyleTitanWingBuffet(this));
		tasks.addTask(1, new AnimationGargoyleTitanMeteorRain(this));
		tasks.addTask(1, new AnimationGargoyleTitanAttack4(this));
		tasks.addTask(1, new AnimationGargoyleTitanAttack3(this));
		tasks.addTask(1, new AnimationGargoyleTitanAttack2(this));
		tasks.addTask(1, new AnimationGargoyleTitanAttack1(this));
		tasks.addTask(1, new AnimationGargoyleTitanAntiTitanAttack(this));
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted % 5 == 0)
		{
			setSize(32F, 74F);
		}

		super.onHitboxUpdate();
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 20.0F;
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

	public static void addTitanTargetingTaskToEntity(EntityCreature entity)
	{
		entity.targetTasks.addTask(0, new EntityAINearestTargetTitan(entity, EntityTitan.class, 0, false, false, attackEntitySelector));
	}

	public float getEyeHeight()
	{
		return 0.9189189189189189F * height;
	}

	public float getTitanSizeMultiplier()
	{
		return 32F;
	}

	public void onKillCommand()
	{
		playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
		setDead();
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.UTILITY;
	}

	protected int decreaseAirSupply(int p_70682_1_)
	{
		return p_70682_1_;
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.GargoyleKingMinionSpawnrate;
	}

	protected void fall(float p_70069_1_)
	{
		p_70069_1_ = ForgeHooks.onLivingFall(this, p_70069_1_);
		if (p_70069_1_ <= 0.0F) return;
		super.fall(p_70069_1_);
		PotionEffect potioneffect = getActivePotionEffect(Potion.jump);
		float f1 = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0.0F;
		int i = MathHelper.ceiling_float_int(p_70069_1_ - 20F - f1);
		if (i > 0)
		{
			func_145780_a(0, 0, 0, Blocks.stone);
			List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(48.0D, 2.0D, 48.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (((entity instanceof EntityLivingBase)) && canAttackClass(entity.getClass()) && (!(entity instanceof EntityTitan)))
					{
						float smash = 100F - getDistanceToEntity(entity);
						if (smash <= 1F)
						smash = 1F;
						entity.attackEntityFrom(DamageSource.causeMobDamage(this), smash);
						double d0 = boundingBox.minX + boundingBox.maxX;
						double d1 = boundingBox.minZ + boundingBox.maxZ;
						double d2 = entity.posX - d0;
						double d3 = entity.posZ - d1;
						double d4 = d2 * d2 + d3 * d3;
						entity.addVelocity(d2 / d4 * 8.0D, 1.0D, d3 / d4 * 8.0D);
					}
				}
			}
		}
	}

	public boolean hasNoSoul()
	{
		return true;
	}

	public String getCommandSenderName()
	{
		return "\u00A7l" + super.getCommandSenderName();
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if ((getAttackTarget() != null) && getAnimID() == 0 && posY <= getAttackTarget().posY + 16D && getDistanceToEntity(getAttackTarget()) > (!getAttackTarget().onGround ? getMeleeRange() : 6400D))
		motionY += 0.6D - motionY;
		if (!worldObj.isRemote && getAnimID() == 5 && getAnimTick() == 30)
		{
			if ((getAttackTarget() != null))
			{
				int i1 = MathHelper.floor_double(getAttackTarget().posX);
				int i = MathHelper.floor_double(getAttackTarget().posY);
				int j1 = MathHelper.floor_double(getAttackTarget().posZ);
				for (int l1 = -16; l1 <= 16; l1++)
				{
					for (int i2 = -16; i2 <= 16; i2++)
					{
						for (int j = 0; j <= 1; j++)
						{
							int j2 = i1 + l1;
							int k = i + j;
							int l = j1 + i2;
							for (int y = 0; y <= 256 && worldObj.getBlock(j2, k - 1, l).getMaterial() == Material.air; y++)
							--k;
							Block block = worldObj.getBlock(j2, k, l);
							if (block.getMaterial() == Material.air)
							{
								worldObj.setBlock(j2, k, l, Blocks.flowing_water, 7, 3);
							}
						}
					}
				}

				attackChoosenEntity(getAttackTarget(), 200F, 3);
			}
		}

		if (!worldObj.isRemote && getAnimID() == 6 && getAnimTick() == 20)
		{
			if ((getAttackTarget() != null))
			{
				double d8 = 10D;
				Vec3 vec3 = getLook(1.0F);
				double dx = vec3.xCoord * d8;
				double dz = vec3.zCoord * d8;
				double d5 = (getAttackTarget().posX) - (posX + dx);
				double d6 = getAttackTarget().posY - (posY + 28D);
				double d7 = (getAttackTarget().posZ) - (posZ + dz);
				EntityLavaSpit entitylargefireball = new EntityLavaSpit(worldObj, this, d5, d6, d7);
				entitylargefireball.setPosition(posX + dx, posY + 28D, posZ + dz);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}
		}

		if (!worldObj.isRemote && getAnimID() == 2 && getAnimTick() > 60)
		{
			if ((getAttackTarget() != null))
			{
				for (int i = 0; i < 2; i++)
				{
					double ranX = rand.nextGaussian() * 100;
					double ranZ = rand.nextGaussian() * 100;
					double ranTargetX = rand.nextGaussian() * 16;
					double ranTargetZ = rand.nextGaussian() * 16;
					double d5 = (getAttackTarget().posX + ranTargetX) - (posX + ranX);
					double d6 = getAttackTarget().posY - (200D);
					double d7 = (getAttackTarget().posZ + ranTargetZ) - (posZ + ranZ);
					EntityGargoyleTitanFireball entitylargefireball = new EntityGargoyleTitanFireball(worldObj, this, d5, d6, d7);
					entitylargefireball.setPosition(posX + ranX, 200D, posZ + ranZ);
					worldObj.spawnEntityInWorld(entitylargefireball);
				}
			}
		}

		if (!worldObj.isRemote && getAnimID() == 4 && getAnimTick() > 20)
		{
			if ((getAttackTarget() != null))
			{
				for (int i = 0; i < 2; i++)
				{
					double ranX = rand.nextGaussian() * 100;
					double ranZ = rand.nextGaussian() * 100;
					double ranTargetX = rand.nextGaussian() * 16;
					double ranTargetZ = rand.nextGaussian() * 16;
					double d5 = (getAttackTarget().posX + ranTargetX) - (posX + ranX);
					double d6 = getAttackTarget().posY - (200D);
					double d7 = (getAttackTarget().posZ + ranTargetZ) - (posZ + ranZ);
					EntityGargoyleTitanFireball entitylargefireball = new EntityGargoyleTitanFireball(worldObj, this, d5, d6, d7);
					entitylargefireball.setPosition(posX + ranX, 200D, posZ + ranZ);
					worldObj.spawnEntityInWorld(entitylargefireball);
				}
			}
		}

		if ((!AnimationAPI.isEffectiveClient()) && (getAttackTarget() != null) && (getAnimID() == 0))
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 < getMeleeRange())
			{
				switch (rand.nextInt(6))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 3);
					setAnimID(3);
					break;
					case 1:AnimationAPI.sendAnimPacket(this, 7);
					setAnimID(7);
					break;
					case 2:AnimationAPI.sendAnimPacket(this, 8);
					setAnimID(8);
					break;
					case 3:AnimationAPI.sendAnimPacket(this, 9);
					setAnimID(9);
					break;
					case 4:if (((getAttackTarget() instanceof EntityTitan)) || (getAttackTarget().height >= 6.0F) || (getAttackTarget().posY > posY + 6D))
					{
						if (rand.nextInt(7) == 0)
						{
							AnimationAPI.sendAnimPacket(this, 2);
							setAnimID(2);
						}

						else
						{
							AnimationAPI.sendAnimPacket(this, 1);
							setAnimID(1);
						}
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 7);
						setAnimID(7);
					}

					break;
				}
			}

			else if ((getAnimID() == 0) && (getRNG().nextInt(60) == 0))
			{
				switch (rand.nextInt(2))
				{
					case 0:if (getAttackTarget() instanceof EntityTitan)
					{
						AnimationAPI.sendAnimPacket(this, 4);
						setAnimID(4);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 5);
						setAnimID(5);
					}

					break;
					case 1:AnimationAPI.sendAnimPacket(this, 6);
					setAnimID(6);
					break;
				}
			}
		}

		List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(256.0D, 256.0D, 256.0D));
		if ((list1 != null) && (!list1.isEmpty()) && (rand.nextInt(60) == 0))
		{
			for (int i1 = 0; i1 < list1.size(); i1++)
			{
				Entity entity = (Entity)list1.get(i1);
				if ((entity != null) && ((entity instanceof EntityGargoyle)))
				{
					if (getAttackTarget() != null && getAttackTarget().height <= 6F)
					{
						((EntityGargoyle)entity).setAttackTarget(getAttackTarget());
						((EntityGargoyle)entity).getLookHelper().setLookPositionWithEntity(getAttackTarget(), 30.0F, 30.0F);
						((EntityGargoyle)entity).getMoveHelper().setMoveTo(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1.0D);
						((EntityGargoyle)entity).getNavigator().tryMoveToXYZ(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1.0D);
					}
				}
			}
		}
	}

	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ == EntityGargoyle.class) || (p_70686_1_ == EntityGargoyleTitan.class) ? false : (isPlayerCreated() && EntityPlayer.class.isAssignableFrom(p_70686_1_) ? false : super.canAttackClass(p_70686_1_));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("PlayerCreated", isPlayerCreated());
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		setPlayerCreated(tagCompund.getBoolean("PlayerCreated"));
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		if (source.isExplosion())
		{
			return false;
		}

		if ((source.getEntity() != null) && ((source.getEntity() instanceof EntityPlayer)) && (isPlayerCreated()))
		{
			return false;
		}

		return super.attackEntityFrom(source, amount);
	}

	protected String getLivingSound()
	{
		return "thetitans:gargoyleLiving";
	}

	protected String getHurtSound()
	{
		return "thetitans:gargoyleGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:gargoyleDeath";
	}

	protected float getSoundPitch()
	{
		return isChild() ? (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F : (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F;
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		if (onGround)
		{
			playSound("mob.irongolem.walk", 10.0F, 0.5F);
			playSound("mob.irongolem.walk", 10.0F, 0.5F);
			playSound("mob.irongolem.walk", 10.0F, 0.5F);
			playSound("mob.irongolem.walk", 10.0F, 0.5F);
			playSound("mob.irongolem.walk", 10.0F, 0.5F);
			playSound("mob.irongolem.walk", 10.0F, 0.5F);
			playSound("mob.irongolem.walk", 10.0F, 0.5F);
			playSound("mob.irongolem.walk", 10.0F, 0.5F);
			playSound("thetitans:titanStep", 10.0F, 1.0F);
			shakeNearbyPlayerCameras(6D);
		}
	}

	protected float getSoundVolume()
	{
		return 100.0F;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		for (int l = 0; l < 512 + rand.nextInt(512 + p_70628_2_); l++)
		{
			entityDropItem(new ItemStack(Blocks.stone), 12.0F);
		}

		for (int l = 0; l < 32 + rand.nextInt(96 + p_70628_2_); l++)
		{
			entityDropItem(new ItemStack(Items.emerald), 12.0F);
		}

		for (int l = 0; l < 32 + rand.nextInt(96 + p_70628_2_); l++)
		{
			entityDropItem(new ItemStack(Items.diamond), 12.0F);
		}

		for (int l = 0; l < 0 + rand.nextInt(16 + p_70628_2_); l++)
		{
			entityDropItem(new ItemStack(TitanItems.harcadium), 12.0F);
		}

		for (int l = 0; l < 0 + rand.nextInt(8); l++)
		{
			entityDropItem(new ItemStack(Blocks.bedrock), 12.0F);
		}
	}

	public boolean isPlayerCreated()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
	}

	public void setPlayerCreated(boolean p_70849_1_)
	{
		byte b0 = dataWatcher.getWatchableObjectByte(16);
		if (p_70849_1_)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
		}

		else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
		}
	}

	protected EntityLiving getMinion()
	{
		return new EntityGargoyle(worldObj);
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


