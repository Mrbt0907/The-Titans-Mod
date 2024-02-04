package net.minecraft.entity.titan;
import java.util.List;
import net.minecraft.theTitans.core.TheCore;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
public class EntitySlimeTitan
extends EntityTitan
{
	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;
	private boolean field_175452_bi;
	private int slimeJumpDelay;
	public boolean doubleJumped;
	public EntitySlimeTitan(World worldIn)
	{
		super(worldIn);
		setSize(8.0F * getSlimeSize(), 8.0F * getSlimeSize());
		slimeJumpDelay = 0;
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted % 5 == 0)
		{
			destroyBlocksInAABB(boundingBox.offset(motionX, motionY > 0D ? motionY : 0D, motionZ));
		}
		super.onHitboxUpdate();
	}

	public float getTitanSizeMultiplier()
	{
		return 16F;
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 16.0F;
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.SIMPLE;
	}

	public boolean canAttack()
	{
		return true;
	}

	protected void applyEntityAI()
	{
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SlimeTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	protected void updateAITasks()
	{
		super.updateAITasks();
		EntityLivingBase entity = getAttackTarget();
		if ((onGround) && (slimeJumpDelay-- <= 0) && (getInvulTime() <= 0))
		{
			slimeJumpDelay = getJumpDelay();
			if (entity != null)
			{
				slimeJumpDelay /= 3;
				getLookHelper().setLookPositionWithEntity(entity, 180.0F, 60.0F);
			}

			jump();
			if (makesSoundOnJump())
			{
				playSound(getJumpSound(), getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
			}

			moveForward = (2 * getSlimeSize());
			moveEntityWithHeading(moveStrafing, moveForward);
		}

		else
		{
			isJumping = false;
			if (onGround)
			{
				moveStrafing = (moveForward = 0.0F);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ == EntityMagmaCube.class || p_70686_1_ != EntitySlime.class) && (p_70686_1_ == EntityMagmaCubeTitan.class || p_70686_1_ != EntitySlimeTitan.class);
	}

	public boolean hasNoSoul()
	{
		return true;
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(20) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL);
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.SlimeTitanMinionSpawnrate;
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte)1));
	}

	protected void setSlimeSize(int p_70799_1_)
	{
		dataWatcher.updateObject(16, Byte.valueOf((byte)p_70799_1_));
		setSize(8.0F * p_70799_1_, 8.0F * p_70799_1_);
		setPosition(posX, posY, posZ);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5F + 0.1F * p_70799_1_);
		setTitanHealth(getMaxHealth());
		if ((this instanceof EntityMagmaCubeTitan))
		{
			experienceValue = (p_70799_1_ * 3000);
		}

		else
		{
			experienceValue = (p_70799_1_ * 1000);
		}
	}

	public int getParticleCount()
	{
		return 4;
	}

	public String getParticles()
	{
		return func_180487_n();
	}

	public int getSlimeSize()
	{
		return dataWatcher.getWatchableObjectByte(16);
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("Size", getSlimeSize() - 1);
		tagCompound.setBoolean("wasOnGround", field_175452_bi);
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		int i = tagCompund.getInteger("Size");
		if (i < 0)
		{
			i = 0;
		}

		setSlimeSize(i + 1);
		field_175452_bi = tagCompund.getBoolean("wasOnGround");
	}

	protected String func_180487_n()
	{
		return "slime";
	}

	protected String getJumpSound()
	{
		return "mob.slime.big";
	}

	protected String getSlimeParticle()
	{
		return "slime";
	}

	public void onUpdate()
	{
		squishFactor += (squishAmount - squishFactor) * 0.5F;
		prevSquishFactor = squishFactor;
		boolean flag = onGround;
		super.onUpdate();
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.5D, 0.5D, 0.5D));
		if (list != null && !list.isEmpty())
		{
			for (int j = 0; j < list.size(); ++j)
			{
				Entity entity = (Entity)list.get(j);
				if (entity instanceof EntityFireball && !(entity instanceof EntityLightningBall) && !(entity instanceof EntityGargoyleTitanFireball) && !(entity instanceof EntityWebShot) && !(entity instanceof EntityMortarWitherSkull))
				{
					((EntityFireball)entity).setDead();
				}

				if (entity instanceof EntityTitanFireball && ((EntityTitanFireball)entity).shootingEntity != null)
				{
					((EntityTitanFireball)entity).onImpactPublic(this);
				}

				if (entity instanceof EntityGargoyleTitanFireball)
				{
					playSound("thetitans:titanpunch", 10.0F, 1.0F);
					worldObj.newExplosion(((EntityGargoyleTitanFireball)entity).shootingEntity != null ? ((EntityGargoyleTitanFireball)entity).shootingEntity : ((EntityGargoyleTitanFireball)entity), ((EntityGargoyleTitanFireball)entity).posX, ((EntityGargoyleTitanFireball)entity).posY, ((EntityGargoyleTitanFireball)entity).posZ, 8F, false, false);
					attackEntityFrom(DamageSource.causeFireballDamage((EntityGargoyleTitanFireball)entity, ((EntityGargoyleTitanFireball)entity).shootingEntity != null ? ((EntityGargoyleTitanFireball)entity).shootingEntity : (EntityGargoyleTitanFireball)entity), 1000F);
					entity.setDead();
				}

				if (entity instanceof EntityGargoyleTitanFireball)
				{
					playSound("thetitans:titanpunch", 10.0F, 1.0F);
					worldObj.newExplosion(((EntityGargoyleTitanFireball)entity).shootingEntity != null ? ((EntityGargoyleTitanFireball)entity).shootingEntity : ((EntityGargoyleTitanFireball)entity), ((EntityGargoyleTitanFireball)entity).posX, ((EntityGargoyleTitanFireball)entity).posY, ((EntityGargoyleTitanFireball)entity).posZ, 8F, false, false);
					attackEntityFrom(DamageSource.causeFireballDamage((EntityGargoyleTitanFireball)entity, ((EntityGargoyleTitanFireball)entity).shootingEntity != null ? ((EntityGargoyleTitanFireball)entity).shootingEntity : (EntityGargoyleTitanFireball)entity), 1000F);
					entity.setDead();
				}

				if (entity instanceof EntityHarcadiumArrow)
				{
					playSound("thetitans:titanpunch", 10.0F, 1.0F);
					attackEntityFrom(DamageSourceExtra.causeHarcadiumArrowDamage((EntityHarcadiumArrow)entity, ((EntityHarcadiumArrow)entity).shootingEntity != null ? ((EntityHarcadiumArrow)entity).shootingEntity : (EntityHarcadiumArrow)entity), 500F);
					entity.setDead();
				}

				if (entity instanceof EntityWebShot)
				{
					playSound("thetitans:titanpunch", 10.0F, 1.0F);
					attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(((EntityWebShot)entity).shootingEntity), 300F);
					int i1 = MathHelper.floor_double(posY);
					int i11 = MathHelper.floor_double(posX);
					int j1 = MathHelper.floor_double(posZ);
					for (int l1 = -2 - rand.nextInt(4); l1 <= 2 + rand.nextInt(4); l1++)
					{
						for (int i2 = -2 - rand.nextInt(4); i2 <= 2 + rand.nextInt(4); i2++)
						{
							for (int h = -2; h <= 2 + rand.nextInt(5); h++)
							{
								int j2 = i11 + l1;
								int k = i1 + h;
								int l = j1 + i2;
								Block block1 = worldObj.getBlock(j2, k, l);
								if (!block1.isOpaqueCube())
								{
									worldObj.setBlock(j2, k, l, Blocks.web);
								}
							}
						}
					}

					entity.setDead();
				}
			}
		}

		if ((onGround) && (!flag))
		{
			int i = getSlimeSize();
			for (int j = 0; j < i * 32; j++)
			{
				float f = rand.nextFloat() * 3.1415927F * 2.0F;
				float f1 = rand.nextFloat() * 8.0F + 8.0F;
				float f2 = MathHelper.sin(f) * i * 0.5F * f1;
				float f3 = MathHelper.cos(f) * i * 0.5F * f1;
				worldObj.spawnParticle(getSlimeParticle(), posX + f2, boundingBox.minY, posZ + f3, 0.0D, 0.0D, 0.0D);
			}

			if (makesSoundOnLand())
			{
				playSound(getJumpSound(), getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			}

			squishAmount = -0.5F;
		}

		else if ((!onGround) && (flag))
		{
			squishAmount = 1.0F;
		}

		alterSquishAmount();
	}

	public boolean shouldMove()
	{
		return false;
	}

	public String getCommandSenderName()
	{
		return (createProtoInstance() instanceof EntityMagmaCube ? "\u00A74" : "\u00A72") + super.getCommandSenderName();
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		renderYawOffset = rotationYaw;
		if (!isEntityAlive())
		{
			if (this instanceof EntityMagmaCubeTitan)
			squishAmount = 1.0F;
			else
			squishAmount = -0.5F;
		}

		
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.1D, 0.1D, 0.1D));
		if (list != null && !list.isEmpty() && isEntityAlive())
		{
			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity = (Entity)list.get(i);
				if (entity != null && entity instanceof EntityLivingBase && canAttackClass(entity.getClass()))
				{
					func_175451_e((EntityLivingBase) entity);
				}
			}
		}

		meleeTitan = true;
		if ((createProtoInstance() instanceof EntityMagmaCube))
		{
			jumpMovementFactor = 0.75F;
			List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(128D, 128D, 128D));
			if ((list1 != null) && (!list1.isEmpty()))
			{
				for (int i1 = 0; i1 < list1.size(); i1++)
				{
					Entity entity = (Entity)list1.get(i1);
					if ((entity != null) && (entity instanceof EntityMagmaCube))
					{
						if (getAttackTarget() != null)
						{
							((EntityMagmaCube)entity).faceEntity(getAttackTarget(), 180.0F, 180.0F);
							if (((EntityMagmaCube)entity).onGround || ((EntityMagmaCube)entity).isInWater() || ((EntityMagmaCube)entity).handleLavaMovement())
							((EntityMagmaCube)entity).motionY = (double)(0.6F + ((float)((EntityMagmaCube)entity).getSlimeSize() * 0.1F));
							((EntityMagmaCube)entity).setMoveForward(2F);
						}
					}
				}
			}
		}

		else
		{
			jumpMovementFactor = 0.5F;
			List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(128D, 128D, 128D));
			if ((list1 != null) && (!list1.isEmpty()))
			{
				for (int i1 = 0; i1 < list1.size(); i1++)
				{
					Entity entity = (Entity)list1.get(i1);
					if ((entity != null) && (entity instanceof EntitySlime) && !(entity instanceof EntityMagmaCube))
					{
						if (getAttackTarget() != null)
						{
							((EntitySlime)entity).faceEntity(getAttackTarget(), 180.0F, 180.0F);
							if (((EntitySlime)entity).onGround || ((EntitySlime)entity).isInWater() || ((EntitySlime)entity).handleLavaMovement())
							((EntitySlime)entity).motionY = 0.5D;
							((EntitySlime)entity).setMoveForward(2F);
						}
					}
				}
			}
		}

		if (getAttackTarget() != null)
		{
			faceEntity(getAttackTarget(), 180F, 40F);
		}

		if (((getAttackTarget() == null) && (doubleJumped == true)) || (onGround))
		{
			doubleJumped = false;
		}

		if ((getAttackTarget() != null) && (!doubleJumped) && (rand.nextInt(100) == 0) && (!onGround))
		{
			squishAmount = 2.0F;
			jump();
			doubleJumped = true;
			playSound(getJumpSound(), getSoundVolume(), ((getRNG().nextFloat() - getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
		}

		if ((rand.nextInt(getMinionSpawnRate()) == 0) && (getHealth() > 0.0F) && (!worldObj.isRemote))
		{
			EntitySlime entitychicken = createProtoInstance();
			entitychicken.onSpawnWithEgg((IEntityLivingData)null);
			worldObj.spawnEntityInWorld(entitychicken);
			entitychicken.addPotionEffect(new PotionEffect(Potion.resistance.id, 40, 4, false));
			entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
		}
	}

	protected void alterSquishAmount()
	{
		squishAmount *= 0.85F;
	}

	protected int getJumpDelay()
	{
		return rand.nextInt(40) + 20;
	}

	protected EntitySlimeTitan createInstance()
	{
		return new EntitySlimeTitan(worldObj);
	}

	protected EntitySlime createProtoInstance()
	{
		return new EntitySlime(worldObj);
	}

	public void func_145781_i(int p_145781_1_)
	{
		if (p_145781_1_ == 16)
		{
			int j = getSlimeSize();
			setSize(8.0F * j, 8.0F * j);
		}

		super.func_145781_i(p_145781_1_);
	}

	public void applyEntityCollision(Entity entityIn)
	{
		if (entityIn instanceof EntityTitan)
		super.applyEntityCollision(entityIn);
		if (canAttackClass(entityIn.getClass()))
		{
			getSlimeSize();
			if (ticksExisted % 5 == 0 && isEntityAlive())
			{
				float f = (float)getAttackValue(getSlimeSize());
				int i1 = getKnockbackAmount();
				renderYawOffset = rotationYaw = rotationYawHead;
				attackChoosenEntity(entityIn, f, i1);
				if (!entityIn.isEntityAlive() && !(entityIn instanceof EntityTitan))
				{
					playSound("mob.slime.attack", 100F, 0.5F);
					if (entityIn instanceof EntityLivingBase)
					heal(((EntityLivingBase)entityIn).getMaxHealth());
					entityIn.isDead = true;
					if (!worldObj.isRemote)
					{
						EntitySlime entitychicken = createProtoInstance();
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
						entitychicken.addPotionEffect(new PotionEffect(Potion.resistance.id, 100, 4, false));
						entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
						worldObj.spawnEntityInWorld(entitychicken);
					}
				}
			}
		}
	}

	public void onCollideWithPlayer(EntityPlayer entityIn)
	{
		func_175451_e(entityIn);
	}

	protected void func_175451_e(EntityLivingBase p_175451_1_)
	{
	}

	public float getEyeHeight()
	{
		return 0.625F * height;
	}

	protected int getAttackStrength()
	{
		if (TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) == true)
		{
			return getSlimeSize() * 30;
		}

		return getSlimeSize() * 10;
	}

	protected String getHurtSound()
	{
		return "mob.slime.big";
	}

	protected String getDeathSound()
	{
		return "mob.slime.big";
	}

	protected Item getDropItem()
	{
		return Items.slime_ball;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < getSlimeSize(); x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
				entitylargefireball.setPosition(posX, posY + 4D, posZ);
				entitylargefireball.motionY += 0.5D;
				entitylargefireball.setXPCount(this instanceof EntityMagmaCubeTitan ? 2000 : 1000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			if (getSlimeSize() <= 1)
			{
				for (int x = 0; x < 2; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(getDropItem()));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 1; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.coal));
					itembomb.setItemCount(8 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 1; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.emerald));
					itembomb.setItemCount(2 + rand.nextInt(4 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 1; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.diamond));
					itembomb.setItemCount(2 + rand.nextInt(4 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				if ((rand.nextInt(2) == 0 || rand.nextInt(1 + p_70628_2_) > 0))
				for (int x = 0; x < 1; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(TitanItems.harcadiumWaflet));
					itembomb.setItemCount(1 + rand.nextInt(1 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}
			}
		}
	}

	protected boolean isValidLightLevel()
	{
		return true;
	}

	public int getVerticalFaceSpeed()
	{
		return 30;
	}

	protected boolean makesSoundOnJump()
	{
		return true;
	}

	protected boolean makesSoundOnLand()
	{
		return true;
	}

	protected void jump()
	{
		motionY = (1.5D + getSlimeSize() * 0.2F);
		isAirBorne = true;
		if (getAttackTarget() != null)
		{
			double d01 = getAttackTarget().posX - posX;
			double d11 = getAttackTarget().posZ - posZ;
			float f21 = MathHelper.sqrt_double(d01 * d01 + d11 * d11);
			double hor = 1.5D + (getSlimeSize() * 0.25F);
			motionX = (d01 / f21 * hor * hor + motionX * hor);
			motionZ = (d11 / f21 * hor * hor + motionZ * hor);
		}
	}

	protected void fall(float p_70069_1_)
	{
		onGround = true;
		isAirBorne = false;
		if (!worldObj.isRemote && rand.nextInt(5) == 0 && getAttackTarget() == null)
		{
			renderYawOffset = rotationYaw = rotationYawHead = rand.nextFloat() * 360F;
		}

		p_70069_1_ = ForgeHooks.onLivingFall(this, p_70069_1_);
		if (p_70069_1_ <= 0.0F) return;
		PotionEffect potioneffect = getActivePotionEffect(Potion.jump);
		float f1 = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0.0F;
		int i = MathHelper.ceiling_float_int(p_70069_1_ - 12F - f1);
		if (i > 0)
		{
			shakeNearbyPlayerCameras(50D);
			playSound("thetitans:titanSlam", 5F * getSlimeSize(), 2F - (getSlimeSize() / 4));
			playSound("mob.slime.big", 5F * getSlimeSize(), 2F - (getSlimeSize() / 8));
			List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(6.0D * getSlimeSize(), 6.0D * getSlimeSize(), 6.0D * getSlimeSize()));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (((entity instanceof EntityLivingBase)) && canAttackClass(entity.getClass()) && getDistanceToEntity(entity) <= getMeleeRange())
					{
						float f = (float)getAttackValue(getSlimeSize());
						int i11 = getKnockbackAmount();
						attackChoosenEntity(entity, f, i11);
						double d0 = boundingBox.minX + boundingBox.maxX;
						double d1 = boundingBox.minZ + boundingBox.maxZ;
						double d2 = entity.posX - d0;
						double d3 = entity.posZ - d1;
						double d4 = d2 * d2 + d3 * d3;
						if (doubleJumped == true)
						{
							entity.addVelocity(d2 / d4 * 16.0D, 2.0D, d3 / d4 * 16.0D);
						}

						else
						{
							entity.addVelocity(d2 / d4 * 8.0D, 1.0D, d3 / d4 * 8.0D);
						}
					}
				}
			}
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (!worldObj.isRemote)
		{
			EntitySlime entitychicken = createProtoInstance();
			entitychicken.onSpawnWithEgg((IEntityLivingData)null);
			entitychicken.addPotionEffect(new PotionEffect(Potion.resistance.id, 100, 4, false));
			entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.spawnEntityInWorld(entitychicken);
		}

		if (isEntityInvulnerable())
		{
			return false;
		}

		else if (this instanceof EntityMagmaCubeTitan ? source.getEntity() instanceof EntityMagmaCubeTitan : source.getEntity() instanceof EntitySlimeTitan && !(source.getEntity() instanceof EntityMagmaCubeTitan))
		{
			return false;
		}

		else
		{
			Entity entity = source.getEntity();
			if (((entity instanceof EntityLivingBase)) && (!isEntityInvulnerable()) && (amount > 25.0F))
			{
				List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(100.0D, 100.0D, 100.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if (this instanceof EntityMagmaCubeTitan ? (entity1 instanceof EntityMagmaCubeTitan) : (entity1 instanceof EntitySlimeTitan && !(entity1 instanceof EntityMagmaCubeTitan)))
					{
						EntitySlimeTitan entitypigzombie = (EntitySlimeTitan)entity1;
						entitypigzombie.setAttackTarget((EntityLivingBase)entity);
						entitypigzombie.setRevengeTarget((EntityLivingBase)entity);
					}

					setAttackTarget((EntityLivingBase)entity);
					setRevengeTarget((EntityLivingBase)entity);
				}
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		int i = rand.nextInt(3);
		if ((i < 2) && (rand.nextFloat() < 0.5F))
		{
			i++;
		}

		int j = 1 << i;
		setSlimeSize(j);
		return super.onSpawnWithEgg(p_180482_2_);
	}

	public StatBase getAchievement()
	{
		if ((createProtoInstance() instanceof EntityMagmaCube))
		{
			return TitansAchievments.magmacubetitan;
		}

		return TitansAchievments.slimetitan;
	}

	protected void inactDeathAction()
	{
		worldObj.newExplosion(this, posX, posY + 3.0D, posZ, 0.0F, false, false);
		if (!worldObj.isRemote && worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
		{
			dropFewItems(true, 0);
			dropEquipment(true, 0);
			dropRareDrop(1);
		}

		int i = getSlimeSize();
		if (!worldObj.isRemote)
		for (int i1 = 0; i1 < 8 * getSlimeSize() + worldObj.rand.nextInt(8 * getSlimeSize()); i1++)
		{
			EntitySlime entitychicken = createProtoInstance();
			entitychicken.onSpawnWithEgg((IEntityLivingData)null);
			entitychicken.addPotionEffect(new PotionEffect(Potion.resistance.id, 100, 4, false));
			entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.spawnEntityInWorld(entitychicken);
		}

		if ((!worldObj.isRemote) && (i > 1))
		{
			int j = 2 + rand.nextInt(3);
			for (int k = 0; k < j; k++)
			{
				float f = (k % 2 - 0.5F) * i / 4.0F;
				float f1 = (k / 2 - 0.5F) * i / 4.0F;
				EntitySlimeTitan entityslime = createInstance();
				if (hasCustomNameTag())
				{
					entityslime.setCustomNameTag(getCustomNameTag());
				}

				if (isNoDespawnRequired())
				{
					entityslime.func_110163_bv();
				}

				entityslime.setSlimeSize(i / 2);
				entityslime.setLocationAndAngles(posX + f, posY + 0.5D, posZ + f1, rand.nextFloat() * 360.0F, 0.0F);
				worldObj.spawnEntityInWorld(entityslime);
			}
		}
	}

	protected double cap()
	{
		return super.cap();
	}
	
	@Override
	public boolean shouldCrush()
	{
		return true;
	}
}


