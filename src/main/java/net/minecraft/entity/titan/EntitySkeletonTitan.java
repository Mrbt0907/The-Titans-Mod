package net.minecraft.entity.titan;
import com.google.common.collect.Lists;
import net.minecraft.theTitans.core.TheCore;
import cpw.mods.fml.common.Loader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titan.ai.EntityAITitanLookIdle;
import net.minecraft.entity.titan.ai.EntityAITitanWander;
import net.minecraft.entity.titan.ai.EntityAITitanWatchClosest;
import net.minecraft.entity.titan.animation.skeletontitan.*;
import net.minecraft.entity.titanminion.EntitySkeletonMinion;
import net.minecraft.entity.titanminion.EntityWitherMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.common.util.ForgeDirection;
import thehippomaster.AnimationAPI.AnimationAPI;
import thehippomaster.AnimationAPI.IAnimatedEntity;
public class EntitySkeletonTitan extends EntityTitan implements IRangedAttackMob, IAnimatedEntity, ITitanHitbox
{
	public boolean shouldBeWitherSkeleton;
	public int attackTimer;
	public boolean isStunned;
	public EntityTitanPart head;
	public EntityTitanPart pelvis;
	public EntityTitanPart spine;
	public EntityTitanPart ribCage;
	public EntityTitanPart rightArm;
	public EntityTitanPart leftArm;
	public EntityTitanPart rightLeg;
	public EntityTitanPart leftLeg;
	public EntitySkeletonTitan(World worldIn)
	{
		super(worldIn);
		head = new EntityTitanPart(worldIn, this, "head", 8.0F, 8.0F);
		pelvis = new EntityTitanPart(worldIn, this, "pelvis", 8.0F, 6.0F);
		spine = new EntityTitanPart(worldIn, this, "spine", 2.0F, 12.0F);
		ribCage = new EntityTitanPart(worldIn, this, "ribcage", 8.0F, 8.0F);
		rightArm = new EntityTitanPart(worldIn, this, "rightarm", 2.0F, 2.0F);
		leftArm = new EntityTitanPart(worldIn, this, "leftarm", 2.0F, 2.0F);
		rightLeg = new EntityTitanPart(worldIn, this, "rightleg", 2.0F, 12.0F);
		leftLeg = new EntityTitanPart(worldIn, this, "leftleg", 2.0F, 12.0F);
		partArray.add(head);
		partArray.add(pelvis);
		partArray.add(spine);
		partArray.add(ribCage);
		partArray.add(rightArm);
		partArray.add(leftArm);
		partArray.add(rightLeg);
		partArray.add(leftLeg);
		if (getSkeletonType() == 1)
		{
			setSize(14F, 56F);
			experienceValue = 50000;
		}

		setSize(8.0F, 32.0F);
		experienceValue = 20000;
		if ((worldIn != null) && (!worldIn.isRemote))
		{
			setCombatTask();
		}

		tasks.addTask(6, new EntityAITitanWander(this, 300));
		tasks.addTask(7, new EntityAITitanWatchClosest(this, EntityTitanSpirit.class, 128.0F));
		tasks.addTask(8, new EntityAITitanWatchClosest(this, EntityTitan.class, 128.0F));
		tasks.addTask(9, new EntityAITitanWatchClosest(this, EntityPlayer.class, 64.0F));
		tasks.addTask(10, new EntityAITitanLookIdle(this));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		worldIn.spawnEntityInWorld(head);
		worldIn.spawnEntityInWorld(pelvis);
		worldIn.spawnEntityInWorld(spine);
		worldIn.spawnEntityInWorld(ribCage);
		worldIn.spawnEntityInWorld(rightArm);
		worldIn.spawnEntityInWorld(leftArm);
		worldIn.spawnEntityInWorld(rightLeg);
		worldIn.spawnEntityInWorld(leftLeg);
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted > 5)
		{
			setSize(0.5F * getTitanSizeMultiplier(), 2F * getTitanSizeMultiplier());
			float f = renderYawOffset * 3.1415927F / 180.0F;
			float f1 = MathHelper.sin(f);
			float f2 = MathHelper.cos(f);
			
			head.width = head.height = 0.5F * getTitanSizeMultiplier();
			pelvis.width = 0.5F * getTitanSizeMultiplier();
			spine.height = 0.375F * getTitanSizeMultiplier();
			ribCage.height = 0.3125F * getTitanSizeMultiplier();
			ribCage.width = 0.4375F * getTitanSizeMultiplier();
			pelvis.height = spine.width = rightLeg.width = leftLeg.width = leftArm.width = rightArm.width = 0.125F * getTitanSizeMultiplier();
			rightLeg.height = leftLeg.height = leftArm.height = rightArm.height = 0.75F * getTitanSizeMultiplier();
			
			head.setLocationAndAngles(posX, posY + (0.75D * height), posZ, 0.0F, 0.0F);
			pelvis.setLocationAndAngles(posX, posY + (0.375D * height), posZ, 0.0F, 0.0F);
			spine.setLocationAndAngles(posX + f1 * (0.25D * width), posY + (0.425D * height), posZ - f2 * (0.25D * width), 0.0F, 0.0F);
			ribCage.setLocationAndAngles(posX, posY + (0.59375D * height), posZ, 0.0F, 0.0F);
			rightArm.setLocationAndAngles(posX + f2 * (0.725D * width), posY + (0.359375D * height), posZ + f1 * (0.725D * width), 0.0F, 0.0F);
			leftArm.setLocationAndAngles(posX - f2 * (0.725D * width), posY + (0.359375D * height), posZ - f1 * (0.725D * width), 0.0F, 0.0F);
			rightLeg.setLocationAndAngles(posX + f2 * (0.25D * width), posY, posZ + f1 * (0.25D * width), 0.0F, 0.0F);
			leftLeg.setLocationAndAngles(posX - f2 * (0.25D * width), posY, posZ - f1 * (0.25D * width), 0.0F, 0.0F);
		}
		
		super.onHitboxUpdate();
	}

	protected void applyEntityAI()
	{
		tasks.addTask(1, new AnimationSkeletonTitanCreation(this));
		tasks.addTask(1, new AnimationSkeletonTitanDeath(this));
		tasks.addTask(1, new AnimationSkeletonTitanLightningHand(this));
		tasks.addTask(1, new AnimationSkeletonTitanLightningSword(this));
		tasks.addTask(1, new AnimationSkeletonTitanStun(this));
		tasks.addTask(1, new AnimationSkeletonTitanAttack3(this));
		tasks.addTask(1, new AnimationSkeletonTitanAttack2(this));
		tasks.addTask(1, new AnimationSkeletonTitanRangedAttack1(this));
		tasks.addTask(1, new AnimationSkeletonTitanRangedAttack2(this));
		tasks.addTask(1, new AnimationSkeletonTitanAttack5(this));
		tasks.addTask(1, new AnimationSkeletonTitanAttack1(this));
		tasks.addTask(1, new AnimationSkeletonTitanAttack4(this));
		tasks.addTask(1, new AnimationSkeletonTitanAntiTitanAttack(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SkeletonTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 20.0F;
	}

	public boolean isArmored()
	{
		return getHealth() <= getMaxHealth() / 4.0F || TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) || TheTitans.isDifficulty(worldObj, TheCore.IMPOSSIBLE);
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return p_70686_1_ != head.getClass() && p_70686_1_ != pelvis.getClass() && p_70686_1_ != rightArm.getClass() && p_70686_1_ != leftArm.getClass() && p_70686_1_ != rightLeg.getClass() && p_70686_1_ != leftLeg.getClass() && (p_70686_1_ != EntitySkeletonMinion.class) && (p_70686_1_ != EntitySkeletonTitan.class) && (p_70686_1_ != EntitySkeletonTitanGiantArrow.class) && (p_70686_1_ != EntityWitherMinion.class) || (Loader.isModLoaded("MutantCreatures") && p_70686_1_ == thehippomaster.MutantCreatures.MutantSkeleton.class);
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(100) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (isValidLightLevel());
	}

	public int getMinionSpawnRate()
	{
		if (getSkeletonType() == 1)
		{
			return TheTitans.WitherSkeletonTitanMinionSpawnrate;
		}

		return TheTitans.SkeletonTitanMinionSpawnrate;
	}

	public float getRenderSizeModifier()
	{
		return super.getRenderSizeModifier();
	}

	public int getParticleCount()
	{
		if (getSkeletonType() == 1)
		{
			return 48;
		}

		return super.getParticleCount();
	}

	public String getParticles()
	{
		if (getSkeletonType() == 1)
		{
			shouldParticlesBeUpward = true;
			return "largesmoke";
		}

		return super.getParticles();
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(140.0D);
	}

	public boolean canBeHurtByPlayer()
	{
		return (isStunned) && (!isEntityInvulnerable());
	}

	public void destroyBlocksInAABB(AxisAlignedBB p_70972_1_)
	{
		boolean flag = getSkeletonType() == 1 && getTitanVariant() == 1;
		int i = MathHelper.floor_double(p_70972_1_.minX);
		int j = MathHelper.floor_double(p_70972_1_.minY);
		int k = MathHelper.floor_double(p_70972_1_.minZ);
		int l = MathHelper.floor_double(p_70972_1_.maxX);
		int i1 = MathHelper.floor_double(p_70972_1_.maxY);
		int j1 = MathHelper.floor_double(p_70972_1_.maxZ);
		for (int k1 = i; k1 <= l; k1++)
		{
			for (int l1 = j; l1 <= i1; l1++)
			{
				for (int i2 = k; i2 <= j1; i2++)
				{
					Block block = worldObj.getBlock(k1, l1, i2);
					if (getSkeletonType() == 0 && getTitanVariant() == 1 && (block == Blocks.grass || block == Blocks.dirt || block == Blocks.gravel))
					worldObj.setBlock(k1, l1, i2, Blocks.sand);
					if ((flag && block == Blocks.fire))
					{
						worldObj.setBlock(k1, l1, i2, Blocks.fire);
					}

					else if (ticksExisted > 5 && p_70972_1_ != null && worldObj.checkChunksExist(k1, l1, i2, k1, l1, i2) && (!block.isAir(worldObj, k1, l1, i2)) && (!worldObj.isRemote))
					{
						if (block.getBlockHardness(worldObj, k1, l1, i2) != -1F)
						{
							if (!worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops") || block.getMaterial().isLiquid() || block == Blocks.fire || block == Blocks.web)
							{
								worldObj.setBlockToAir(k1, l1, i2);
							}

							else
							{
								if (rand.nextInt(5) == 0)
								{
									EntityFallingBlockTitan entityfallingblock = new EntityFallingBlockTitan(worldObj, k1 + 0.5D, l1 + 0.5D, i2 + 0.5D, (getSkeletonType() == 0 && getTitanVariant() == 1 && (block == Blocks.grass || block == Blocks.dirt || block == Blocks.gravel)) ? Blocks.sand : block, worldObj.getBlockMetadata(k1, l1, i2));
									entityfallingblock.setPosition(k1 + 0.5D, l1 + 0.5D, i2 + 0.5D);
									double d0 = (boundingBox.minX + boundingBox.maxX) / 2.0D;
									double d1 = (boundingBox.minZ + boundingBox.maxZ) / 2.0D;
									double d2 = entityfallingblock.posX - d0;
									double d3 = entityfallingblock.posZ - d1;
									double d4 = d2 * d2 + d3 * d3;
									entityfallingblock.setFire(10);
									entityfallingblock.addVelocity(d2 / d4 * 10D, 2D + rand.nextGaussian(), d3 / d4 * 10D);
									worldObj.spawnEntityInWorld(entityfallingblock);
									worldObj.setBlockToAir(k1, l1, i2);
								}

								else
								{
									if (worldObj.getClosestPlayerToEntity(this, 16D) != null)
									worldObj.func_147480_a(k1, l1, i2, true);
									else
									{
										worldObj.setBlockToAir(k1, l1, i2);
										block.dropBlockAsItem(worldObj, k1, l1, i2, worldObj.getBlockMetadata(k1, l1, i2), 0);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void destroyBlocksInAABBTopless(AxisAlignedBB p_70972_1_)
	{
		boolean flag = getSkeletonType() == 1 && getTitanVariant() == 1;
		int i = MathHelper.floor_double(p_70972_1_.minX);
		int j = MathHelper.floor_double(p_70972_1_.minY);
		int k = MathHelper.floor_double(p_70972_1_.minZ);
		int l = MathHelper.floor_double(p_70972_1_.maxX);
		int i1 = MathHelper.floor_double(p_70972_1_.maxY);
		int j1 = MathHelper.floor_double(p_70972_1_.maxZ);
		for (int k1 = i; k1 <= l; k1++)
		{
			for (int l1 = j; l1 <= i1; l1++)
			{
				for (int i2 = k; i2 <= j1; i2++)
				{
					Block block = worldObj.getBlock(k1, l1, i2);
					Block block1 = worldObj.getBlock(k1, l1 + 1, i2);
					if ((flag && block == Blocks.fire))
					{
						worldObj.setBlock(k1, l1, i2, Blocks.fire);
					}

					else if (ticksExisted > 5 && p_70972_1_ != null && worldObj.checkChunksExist(k1, l1, i2, k1, l1, i2) && block.isOpaqueCube() && !block1.isOpaqueCube() && (!worldObj.isRemote))
					{
						if (block.getBlockHardness(worldObj, k1, l1, i2) != -1F)
						{
							if (!worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops") || block.getMaterial().isLiquid() || block == Blocks.fire || block == Blocks.web)
							{
								worldObj.setBlockToAir(k1, l1, i2);
							}

							else
							{
								if (rand.nextInt(3) == 0)
								{
									EntityFallingBlockTitan entityfallingblock = new EntityFallingBlockTitan(worldObj, k1 + 0.5D, l1 + 0.5D, i2 + 0.5D, (getSkeletonType() == 0 && getTitanVariant() == 1 && (block == Blocks.grass || block == Blocks.dirt || block == Blocks.gravel)) ? Blocks.sand : block, worldObj.getBlockMetadata(k1, l1, i2));
									entityfallingblock.setPosition(k1 + 0.5D, l1 + 0.5D, i2 + 0.5D);
									double d0 = (boundingBox.minX + boundingBox.maxX) / 2.0D;
									double d1 = (boundingBox.minZ + boundingBox.maxZ) / 2.0D;
									double d2 = entityfallingblock.posX - d0;
									double d3 = entityfallingblock.posZ - d1;
									double d4 = d2 * d2 + d3 * d3;
									entityfallingblock.setFire(10);
									entityfallingblock.addVelocity(d2 / d4 * 10D, 2D + rand.nextGaussian(), d3 / d4 * 10D);
									worldObj.spawnEntityInWorld(entityfallingblock);
									worldObj.setBlockToAir(k1, l1, i2);
								}

								else
								{
									if (worldObj.getClosestPlayerToEntity(this, 16D) != null)
									worldObj.func_147480_a(k1, l1, i2, true);
									else
									{
										worldObj.setBlockToAir(k1, l1, i2);
										block.dropBlockAsItem(worldObj, k1, l1, i2, worldObj.getBlockMetadata(k1, l1, i2), 0);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public boolean isEntityUndead()
	{
		return true;
	}

	public EnumTitanStatus getTitanStatus()
	{
		return getSkeletonType() == 1 ? EnumTitanStatus.GREATER : EnumTitanStatus.AVERAGE;
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(13, new Byte((byte)0));
	}

	public int getFootStepModifer()
	{
		return super.getFootStepModifer();
	}

	protected String getLivingSound()
	{
		return isStunned || getWaiting() || getAnimID() == 13 ? null : (getSkeletonType() == 1 ? "thetitans:titanWitherSkeletonLiving" : "thetitans:titanSkeletonLiving");
	}

	protected String getHurtSound()
	{
		return getSkeletonType() == 1 ? "thetitans:titanWitherSkeletonGrunt" : "thetitans:titanSkeletonGrunt";
	}

	protected String getDeathSound()
	{
		return getSkeletonType() == 1 ? "thetitans:titanWitherSkeletonDeath" : "thetitans:titanSkeletonDeath";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		playSound("thetitans:titanStep", 20.0F, 1.0F);
		playSound("mob.skeleton.step", 20.0F, 0.5F);
		shakeNearbyPlayerCameras(getSkeletonType() == 1 ? 6D : 4D);
		if (!getWaiting() && getAnimID() != 13)
		{
			float f3 = rotationYaw * (float)Math.PI / 180.0F;
			float f11 = MathHelper.sin(f3);
			float f4 = MathHelper.cos(f3);
			if (footID == 0)
			{
				if (getSkeletonType() == 1 && getTitanVariant() == 1)
				{
					for (int l = 0; l < getTitanSizeMultiplier(); l++)
					{
						int i1 = MathHelper.floor_double(rightLeg.posX + (rand.nextFloat() - 0.5D) * rightLeg.width);
						int j1 = MathHelper.floor_double(rightLeg.posY);
						int k1 = MathHelper.floor_double(rightLeg.posZ + (rand.nextFloat() - 0.5D) * rightLeg.width);
						if ((worldObj.getBlock(i1, j1, k1).getMaterial() == Material.air) && (Blocks.fire.canPlaceBlockAt(worldObj, i1, j1, k1)))
						{
							worldObj.setBlock(i1, j1, k1, Blocks.fire);
						}
					}
				}

				else
				{
					destroyBlocksInAABB(rightLeg.boundingBox.offset(0, -1, 0));
				}

				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(1.0D, 1.0D, 1.0D).offset((double)(f11 * 4F), 0, (double)(f4 * 4F))));
				++footID;
			}

			else
			{
				if (getSkeletonType() == 1 && getTitanVariant() == 1)
				{
					for (int l = 0; l < getTitanSizeMultiplier(); l++)
					{
						int i1 = MathHelper.floor_double(leftLeg.posX + (rand.nextFloat() - 0.5D) * leftLeg.width);
						int j1 = MathHelper.floor_double(leftLeg.posY);
						int k1 = MathHelper.floor_double(leftLeg.posZ + (rand.nextFloat() - 0.5D) * leftLeg.width);
						if ((worldObj.getBlock(i1, j1, k1).getMaterial() == Material.air) && (Blocks.fire.canPlaceBlockAt(worldObj, i1, j1, k1)))
						{
							worldObj.setBlock(i1, j1, k1, Blocks.fire);
						}
					}
				}

				else
				{
					destroyBlocksInAABB(leftLeg.boundingBox.offset(0, -1, 0));
				}

				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(1.0D, 1.0D, 1.0D).offset((double)(f11 * 4F), 0, (double)(f4 * 4F))));
				footID = 0;
			}
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return false;
	}

	public double getSpeed()
	{
		return getSkeletonType() == 1 ? 0.3D / 6 : 0.25D / 6;
	}

	public void becomeWitherSkeleton(boolean skelly)
	{
		if (skelly)
		{
			setSkeletonType(1);
			setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
		}
	}

	public boolean canAttack()
	{
		return false;
	}

	public boolean shouldMove()
	{
		return !isStunned && super.shouldMove();
	}

	@SuppressWarnings("rawtypes")
	public void collideWithEntities(EntityTitanPart part, List p_70970_1_)
	{
		super.collideWithEntities(part, p_70970_1_);if (rand.nextInt(5) == 0 && getSkeletonType() == 1 && part.isWet())
		part.playSound("random.fizz", 1.0F, 1.0F);
	}

	public float getTitanSizeMultiplier()
	{
		return getSkeletonType() == 1 ? super.getTitanSizeMultiplier() * 1.2F : super.getTitanSizeMultiplier();
	}

	public void onLivingUpdate()
	{
		float dis = getTitanSizeMultiplier();
		if (motionY > (getSkeletonType() == 1 ? 1.5D : 3.0D))motionY *= 0.8D;
		float xfac = MathHelper.sin(renderYawOffset * (float)Math.PI / 180.0F);
		float zfac = MathHelper.cos(renderYawOffset * (float)Math.PI / 180.0F);
		if (!isRiding() && !getWaiting() && !isStunned && getAnimID() == 0)
		{
			titanFly(0.625F * getTitanSizeMultiplier(), 0.125F * getTitanSizeMultiplier(), 0.25D * getTitanSizeMultiplier());
		}

		meleeTitan = getSkeletonType() == 1;
		if (getSkeletonType() == 1 && getTitanVariant() == 1)
		{
			if (!worldObj.isRemote)
			{
				int i = MathHelper.floor_double(posX);
				int j = MathHelper.floor_double(posY);
				int k = MathHelper.floor_double(posZ);
				for (int l = 0; l < dis * dis; l++)
				{
					i = MathHelper.floor_double(posX + (rand.nextFloat() - 0.5D) * width * 10F);
					j = MathHelper.floor_double(posY + (rand.nextFloat() - 0.5D) * height * 2F);
					k = MathHelper.floor_double(posZ + (rand.nextFloat() - 0.5D) * width * 10F);
					if (worldObj.getBlock(i, j, k) == Blocks.air && worldObj.getBlock(i, j - 1, k).isFlammable(worldObj, i, j - 1, k, ForgeDirection.UP))
					worldObj.setBlock(i, j, k, Blocks.fire);
					if (worldObj.getBlock(i, j, k) == Blocks.air && worldObj.getBlock(i, j + 1, k).isFlammable(worldObj, i, j + 1, k, ForgeDirection.DOWN))
					worldObj.setBlock(i, j, k, Blocks.fire);
				}
			}
		}

		if (getWaiting())
		{
			AnimationAPI.sendAnimPacket(this, 13);
			EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 16D);
			if (player != null)
			{
				setWaiting(false);
				AnimationAPI.sendAnimPacket(this, 13);
				player.triggerAchievement(TitansAchievments.locateTitan);
			}
		}

		else
		{
			if (getAnimID() != 13)
			{
				if (getSkeletonType() == 1)
				{
					switch (getTitanVariant())
					{
						default:
						{
							enactEffectAura(1, boundingBox.expand(super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier()));
						}

						case 1:
						{
							enactEffectAura(3, boundingBox.expand(super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier()));
							break;
						}

						case 2:
						{
							enactEffectAura(4, boundingBox.expand(super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier()));
							break;
						}
					}
				}

				else
				{
					switch (getTitanVariant())
					{
						case 3:
						{
							enactEffectAura(3, boundingBox.expand(super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier()));
							break;
						}

						case 4:
						{
							enactEffectAura(4, boundingBox.expand(super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier()));
							break;
						}
					}
				}
			}

			if (getAnimID() == 13 && (getAnimTick() == 130 || getAnimTick() == 390 || getAnimTick() == 430 || getAnimTick() == 450))
			{
				func_145780_a(0, 0, 0, Blocks.stone);
				playSound("thetitans:titanPress", getSoundVolume(), 1F);
			}
		}

		if (isStunned)
		{
			setAttackTarget(null);
			AnimationAPI.sendAnimPacket(this, 8);
			setAnimID(8);
		}

		if (getAnimID() == 8)
		{
			if (getAnimTick() == 70)
			{
				playSound("thetitans:groundSmash", 8.0F, 0.9F);
				playSound("thetitans:titanFall", 10.0F, 1.0F);
			}

			if (getAnimTick() == 74)
			{
				playSound("thetitans:distantLargeFall", 10000.0F, 1.0F);
				playSound(getHurtSound(), getSoundVolume(), getSoundPitch());
			}

			if (getAnimTick() == 480)
			{
				playSound("thetitans:titanFall", 10.0F, 1.0F);
				playSound("thetitans:titanStep", 10.0F, 1.0F);
				playSound("mob.skeleton.step", 10.0F, 0.5F);
				playSound("thetitans:titanStep", 10.0F, 1.0F);
				playSound("mob.skeleton.step", 10.0F, 0.5F);
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(16.0D, 1.0D, 16.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(16.0D, 1.0D, 16.0D)));
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(16.0D, 1.0D, 16.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(16.0D, 1.0D, 16.0D)));
			}

			if (getAnimTick() == 450)
			{
				isStunned = false;
			}

			else
			{
				setAttackTarget(null);
			}
		}

		if (getAnimID() == 10)
		{
			if ((getAnimTick() == 30) || (getAnimTick() == 70))
			{
				playSound("thetitans:titanStep", 10.0F, 1.0F);
			}

			if (getAnimTick() == 190)
			{
				playSound("thetitans:titanFall", 20.0F, 1.0F);
				playSound("thetitans:groundSmash", 20.0F, 1.0F);
				shakeNearbyPlayerCameras(10D);
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(leftLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, leftLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
				collideWithEntities(rightLeg, worldObj.getEntitiesWithinAABBExcludingEntity(this, rightLeg.boundingBox.expand(48.0D, 48.0D, 48.0D)));
			}

			if (getAnimTick() == 200)
			{
				playSound("thetitans:distantLargeFall", 10000.0F, 1.0F);
			}
		}

		if ((getAnimID() == 9) && (getAnimTick() <= 85) && (getAnimTick() >= 54))
		{
			renderYawOffset = (rotationYaw = rotationYawHead);
			double d8 = getAnimTick() - 30;
			Vec3 vec3 = getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + dis + 6D, posZ + dz, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
		}

		if (((getAnimID() == 9 && getAnimTick() == 90) || (getAnimID() == 5 && getAnimTick() == 230 + rand.nextInt(100))) && (getAttackTarget() != null))
		{
			double d8 = 1.8D * getTitanSizeMultiplier();
			Vec3 vec3 = getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			float da = (float)getAttackValue(10.0F);
			int i = getKnockbackAmount();
			attackChoosenEntity(getAttackTarget(), da * 3.0F, i);
			getAttackTarget().motionY += 2.0F + rand.nextFloat();
			worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 6.0F, false, false);
			worldObj.newExplosion(this, posX + dx, posY + dis + 4D, posZ + dz, 3.0F, false, false);
			getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, da);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + dis + 4D, posZ + dz, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + dis + 4D, posZ + dz, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + dis + 4D, posZ + dz, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + dis + 4D, posZ + dz, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
			for (int i1 = 0; i1 < 3; i1++)
			{
				List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(getAttackTarget(), getAttackTarget().boundingBox.expand(16.0D, 16.0D, 16.0D));
				if ((list1 != null) && (!list1.isEmpty()))
				{
					for (int i11 = 0; i11 < list1.size(); i11++)
					{
						Entity entity1 = (Entity)list1.get(i11);
						if (((entity1 instanceof EntityLivingBase)) && canAttackClass(entity1.getClass()))
						{
							worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
							worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
							worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
							worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, entity1.posX, entity1.posY, entity1.posZ, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
							attackChoosenEntity(entity1, da, i);
							entity1.motionY += 2D + rand.nextDouble();
						}
					}
				}
			}
		}

		if ((getAnimID() == 12 && getAnimTick() == 40))
		{
			playSound("random.explode", 20.0F, 2.0F);
			playSound("mob.wither.shoot", 20.0F, 1.0F);
			float da = (float)getAttackValue(4.0F);
			int i = getKnockbackAmount();
			worldObj.newExplosion(this, posX - (double)(xfac * (dis * 0.9)), posY + (dis * 1.25), posZ + (double)(zfac * (dis * 0.9)), 3.0F, false, false);
			worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX - (double)(xfac * (dis * 0.9)), posY + (dis * 1.25), posZ + (double)(zfac * (dis * 0.9)), getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
			if (getAttackTarget() != null)
			{
				attackChoosenEntity(getAttackTarget(), da * 5F, i);
				getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, da);
				getAttackTarget().motionY += 1.0D + rand.nextDouble();
				if (getAttackTarget().width <= 8F)
				worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 3.0F, false, false);
				worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F, getSkeletonType() == 1 ? 0F : 0.5F));
			}
		}

		if ((getAnimID() == 7) && (getAnimTick() == 92) && !AnimationAPI.isEffectiveClient())
		{
			int y = MathHelper.floor_double(posY);
			int x = MathHelper.floor_double(posX - (double)(xfac * (dis * 2)));
			int z = MathHelper.floor_double(posZ + (double)(zfac * (dis * 2)));
			if (worldObj.getBlock(x, y - 1, z).getMaterial() != Material.air)
			{
				playSound("thetitans:titanStrike", 20.0F, 1.0F);
				playSound("thetitans:titanSlam", 20.0F, 1.0F);
				playSound("thetitans:titanPress", 100F, 1F);
			}

			for (int l1 = -4; l1 <= 4; l1++)
			{
				for (int i2 = -4; i2 <= 4; i2++)
				{
					for (int j = -1; j <= 1; j++)
					{
						int j2 = x + l1;
						int k = y + j;
						int l = z + i2;
						Block block = worldObj.getBlock(j2, k, l);
						if (!block.isAir(worldObj, j2, k, l))
						{
							worldObj.playAuxSFX(2001, j2, k, l, Block.getIdFromBlock(block));
							if (block == Blocks.grass)
							{
								worldObj.setBlock(j2, k, l, Blocks.dirt);
							}
						}

						if (block.getExplosionResistance(this) > 500.0F && block.isOpaqueCube())
						{
							AnimationAPI.sendAnimPacket(this, 8);
							setAnimID(8);
							setAnimTick(0);
							playSound("random.anvil_land", 20.0F, 0.5F);
							isStunned = true;
						}
					}
				}
			}
		}

		

		if (isStunned || deathTicks > 0)
		{
			motionX *= 0.0D;
			motionZ *= 0.0D;
		}

		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(256.0D, 256.0D, 256.0D));
		if ((list != null) && (!list.isEmpty()) && (ticksExisted % 400 == 0))
		{
			for (int i1 = 0; i1 < list.size(); i1++)
			{
				Entity entity1 = (Entity)list.get(i1);
				if ((entity1 != null) && ((entity1 instanceof EntityArrow)))
				{
					entity1.setDead();
				}
			}
		}

		if ((getAnimID() == 5 || getAnimID() == 11) && (getAnimTick() >= 40))
		attackTimer += 1;
		if (attackTimer < 0 || (getAnimID() != 5 && getAnimID() != 11) || (getAnimID() == 11 && getAnimTick() >= 80) || (getAnimID() == 5 && getAnimTick() >= 320))
		attackTimer = 0;
		if (!worldObj.isRemote && (getAttackTarget() != null) && (getAnimID() == 5) && (getAnimTick() >= 100) && (getAnimTick() <= 300))
		{
			attackEntityWithRangedAttack(getAttackTarget(), 1F);
			attackEntityWithRangedAttack(getAttackTarget(), 1F);
			attackEntityWithRangedAttack(getAttackTarget(), 1F);
			attackEntityWithRangedAttack(getAttackTarget(), 1F);
			attackEntityWithRangedAttack(getAttackTarget(), 1F);
			attackEntityWithRangedAttack(getAttackTarget(), 1F);
			attackEntityWithRangedAttack(getAttackTarget(), 1F);
			attackEntityWithRangedAttack(getAttackTarget(), 1F);
		}

		if (!worldObj.isRemote && (getAttackTarget() != null) && (getAnimID() == 11) && (getAnimTick() == 78))
		{
			attackEntityWithEnlargedRangedAttack(getAttackTarget(), 1F);
		}

		if ((!AnimationAPI.isEffectiveClient()) && (getAttackTarget() != null) && (!isStunned) && ((getAnimID() == 0)))
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 < getMeleeRange())
			{
				switch (rand.nextInt(6))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 6);
					setAnimID(6);
					break;
					case 1:if (getAttackTarget().height >= height - 12F || (posY + getEyeHeight() < getAttackTarget().posY + getAttackTarget().getEyeHeight()))
					{
						AnimationAPI.sendAnimPacket(this, 1);
						setAnimID(1);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 3);
						setAnimID(3);
					}

					break;
					case 2:AnimationAPI.sendAnimPacket(this, 7);
					setAnimID(7);
					break;
					case 3:if (getAttackTarget().height >= height - 12F || (posY + getEyeHeight() < getAttackTarget().posY + getAttackTarget().getEyeHeight()))
					{
						AnimationAPI.sendAnimPacket(this, 1);
						setAnimID(1);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 2);
						setAnimID(2);
					}

					break;
					case 4:if (getAttackTarget().height >= height - 12F || (posY + getEyeHeight() < getAttackTarget().posY + getAttackTarget().getEyeHeight()))
					{
						AnimationAPI.sendAnimPacket(this, 1);
						setAnimID(1);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 4);
						setAnimID(4);
					}

					break;
				}
			}

			else if (getAnimID() == 0 && (getRNG().nextInt(getSkeletonType() == 1 ? 100 : 2) == 0))
			{
				switch (rand.nextInt(6))
				{
					case 0:if (getSkeletonType() != 1)
					{
						if (rand.nextFloat() < 0.5F || (((getAttackTarget() instanceof EntityTitan)) || (getAttackTarget().height >= 6.0F) || (getAttackTarget().posY > posY + 6D)))
						{
							AnimationAPI.sendAnimPacket(this, 11);
							setAnimID(11);
						}

						else
						{
							AnimationAPI.sendAnimPacket(this, 5);
							setAnimID(5);
						}
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 9);
						setAnimID(9);
					}

					break;
					case 1:AnimationAPI.sendAnimPacket(this, 12);
					setAnimID(12);
					break;
					case 2:if (getSkeletonType() != 1)
					{
						AnimationAPI.sendAnimPacket(this, 11);
						setAnimID(11);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 12);
						setAnimID(12);
					}
				}
			}
		}

		super.onLivingUpdate();
	}

	public String getCommandSenderName()
	{
		if (getSkeletonType() == 1)
		{
			switch (getTitanVariant())
			{
				case 1:
				return StatCollector.translateToLocal("entity.WitherSkeletonTitanSolar.name");
				case 2:
				return StatCollector.translateToLocal("entity.WitherSkeletonTitanReaper.name");
				default:
				return StatCollector.translateToLocal("entity.WitherSkeletonTitan.name");
			}
		}

		else
		{
			switch (getTitanVariant())
			{
				case 1:
				return StatCollector.translateToLocal("entity.SkeletonTitanSand.name");
				case 2:
				return StatCollector.translateToLocal("entity.SkeletonTitanGuitar.name");
				case 3:
				return StatCollector.translateToLocal("entity.SkeletonTitanSleep.name");
				case 4:
				return StatCollector.translateToLocal("entity.SkeletonTitanVoid.name");
				default:
				return StatCollector.translateToLocal("entity.SkeletonTitan.name");
			}
		}
	}

	public void attackChoosenEntity(Entity damagedEntity, float damage, int knockbackAmount)
	{
		if (getSkeletonType() == 1 && isEntityAlive())
		{
			damage = damage + (numSpecialMinions * 150F);
			knockbackAmount = knockbackAmount + numSpecialMinions;
		}

		super.attackChoosenEntity(damagedEntity, damage, knockbackAmount);
		if (((getSkeletonType() == 1) && (damagedEntity instanceof EntityLivingBase) && !(damagedEntity instanceof EntityEnderColossusCrystal)))
		{
			((EntityLivingBase)damagedEntity).addPotionEffect(new PotionEffect(Potion.wither.id, 800, 3));
			((EntityLivingBase)damagedEntity).addPotionEffect(new PotionEffect(ClientProxy.advancedWither.id, 100, 3));
		}
	}

	public void updateRidden()
	{
		super.updateRidden();
		if ((ridingEntity instanceof EntitySpiderTitan))
		{
			EntitySpiderTitan entitycreature = (EntitySpiderTitan)ridingEntity;
			renderYawOffset = entitycreature.renderYawOffset;
			if (getAttackTarget() != null)
			{
				entitycreature.setAttackTarget(getAttackTarget());
			}

			if (!entitycreature.isEntityAlive())
			{
				ridingEntity = null;
			}
		}
	}

	public StatBase getAchievement()
	{
		if ((ridingEntity != null) && ((ridingEntity instanceof EntitySpiderTitan)))
		{
			return TitansAchievments.spiderjockeytitan;
		}

		if (getSkeletonType() == 1)
		{
			return TitansAchievments.witherskeletontitan;
		}

		return TitansAchievments.skeletontitan;
	}

	protected Item getDropItem()
	{
		return Items.arrow;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			if (getSkeletonType() == 1)
			{
				for (int x = 0; x < 70; x++)
				{
					EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
					entitylargefireball.setPosition(posX, posY + 4D, posZ);
					++entitylargefireball.motionY;
					entitylargefireball.setXPCount(24000);
					worldObj.spawnEntityInWorld(entitylargefireball);
				}

				for (int x = 0; x < 1; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.diamond));
					itembomb.setItemCount(16);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 2; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.iron_ingot));
					itembomb.setItemCount(16);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 16; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.bone));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 12; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.dye, 1, 15));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 16; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Blocks.coal_block));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 12; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Blocks.iron_block));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 8; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Blocks.gold_block));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 4; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Blocks.emerald_block));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 4; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Blocks.diamond_block));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 2; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(TitanBlocks.harcadium_block));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 1; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(TitanItems.voidItem));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 1; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Blocks.bedrock));
					itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}
			}

			else
			{
				for (int x = 0; x < 16; x++)
				{
					EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
					entitylargefireball.setPosition(posX, posY + 4D, posZ);
					++entitylargefireball.motionY;
					entitylargefireball.setXPCount(14000);
					worldObj.spawnEntityInWorld(entitylargefireball);
				}

				for (int x = 0; x < 8; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.bone));
					itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 8; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.arrow));
					itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 4; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.coal));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 2; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.emerald));
					itembomb.setItemCount(8 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 2; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.diamond));
					itembomb.setItemCount(8 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 2; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Items.iron_ingot));
					itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				for (int x = 0; x < 1; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(TitanItems.harcadium));
					itembomb.setItemCount(4 + rand.nextInt(4 + p_70628_2_) + p_70628_2_);
					worldObj.spawnEntityInWorld(itembomb);
				}

				if (rand.nextInt(10) == 0)
				for (int x = 0; x < 1; x++)
				{
					EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
					itembomb.setEntityItemStack(new ItemStack(Blocks.bedrock));
					itembomb.setItemCount(1);
					worldObj.spawnEntityInWorld(itembomb);
				}
			}
		}
	}

	protected void dropRareDrop(int p_70600_1_)
	{
		if (getSkeletonType() == 1)
		{
			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.skull, 256, 1));
				itembomb.setItemCount(256);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}

		else
		{
			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.skull, 256, 0));
				itembomb.setItemCount(256);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}
	}

	protected void addRandomArmor()
	{
		super.addRandomArmor();
		if (getSkeletonType() == 1)
		{
			setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));

		}

		 else 
		{

			setCurrentItemOrArmor(0, new ItemStack(Items.bow));
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		p_180482_2_ = super.onSpawnWithEgg(p_180482_2_);
		setCanPickUpLoot(true);
		enchantEquipment();
		setWaiting(true);
		if ((((worldObj.provider instanceof WorldProviderHell)) && (getRNG().nextInt(5) > 0) && (!worldObj.isRemote)) || ((shouldBeWitherSkeleton) && (!worldObj.isRemote)))
		{
			setSkeletonType(1);
		}

		else
		{
			setSkeletonType(0);
		}

		if (getEquipmentInSlot(4) == null)
		{
			Calendar calendar = worldObj.getCurrentDate();
			if ((calendar.get(2) + 1 == 10) && (calendar.get(5) == 31) && (rand.nextFloat() < 0.25F))
			{
				setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				equipmentDropChances[4] = 0.0F;
			}
		}

		addRandomArmor();
		return p_180482_2_;
	}

	public void setCombatTask()
	{
		meleeTitan = getSkeletonType() == 1;
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		renderYawOffset = rotationYaw = rotationYawHead;
		faceEntity(p_82196_1_, 180.0F, 30.0F);
		float dis = 0.625F * getTitanSizeMultiplier();
		float xfac = MathHelper.sin(renderYawOffset * (float)Math.PI / 180.0F);
		float zfac = MathHelper.cos(renderYawOffset * (float)Math.PI / 180.0F);
		EntityHarcadiumArrow entityarrow = new EntityHarcadiumArrow(worldObj, this, p_82196_2_);
		double d0 = p_82196_1_.posX - (posX - (double)(xfac * dis));
		double d1 = p_82196_1_.posY - (posY + (1.175D * getTitanSizeMultiplier()));
		double d2 = p_82196_1_.posZ - (posZ + (double)(zfac * dis));
		float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
		entityarrow.setThrowableHeading(d0, d1 + f1, d2, f1 / (180F / (float)Math.PI) * 1.75F, 1F + (rand.nextFloat() * 36F));
		entityarrow.posX = (posX - (double)(xfac * dis));
		entityarrow.posY = (posY + (1.175D * getTitanSizeMultiplier()));
		entityarrow.posZ = (posZ + (double)(zfac * dis));
		int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
		int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
		int f = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem());
		entityarrow.setDamage(30.0D);
		entityarrow.setIsCritical(true);
		if (i > 0)
		entityarrow.setDamage(entityarrow.getDamage() + i * 10.0D + 1.0D);
		if (j > 1)
		entityarrow.setKnockbackStrength(j);
		else
		entityarrow.setKnockbackStrength(2);
		if ((f > 0) || (getSkeletonType() == 1))
		entityarrow.setFire(10000);
		worldObj.spawnEntityInWorld(entityarrow);
		playSound("random.bow", 3.0F, 1.9F / (getRNG().nextFloat() * 0.4F + 0.8F));
		if (getDistanceToEntity(p_82196_1_) <= getMeleeRange())
		{
			attackChoosenEntity(p_82196_1_, 10F, 5);
		}
	}

	public void attackEntityWithEnlargedRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		renderYawOffset = rotationYaw = rotationYawHead;
		faceEntity(p_82196_1_, 180.0F, 30.0F);
		float dis = 0.625F * getTitanSizeMultiplier();
		float xfac = MathHelper.sin(renderYawOffset * (float)Math.PI / 180.0F);
		float zfac = MathHelper.cos(renderYawOffset * (float)Math.PI / 180.0F);
		double d0 = p_82196_1_.posX - (posX - (double)(xfac * dis));
		double d1 = (p_82196_1_.posY + p_82196_1_.getEyeHeight()) - (posY + (1.125D * getTitanSizeMultiplier()));
		double d2 = p_82196_1_.posZ - (posZ + (double)(zfac * dis));
		EntitySkeletonTitanGiantArrow entityarrow = new EntitySkeletonTitanGiantArrow(worldObj, this, d0, d1, d2);
		MathHelper.sqrt_double(d0 * d0 + d2 * d2);
		entityarrow.posX = (posX - (double)(xfac * dis));
		entityarrow.posY = (posY + (1.1D * getTitanSizeMultiplier()));
		entityarrow.posZ = (posZ + (double)(zfac * dis));
		if (isArmored())
		entityarrow.setArrowType(1);
		if (getTitanVariant() == 4)
		entityarrow.setArrowType(2);
		worldObj.spawnEntityInWorld(entityarrow);
		playSound("random.bow", 20F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		if (getDistanceToEntity(p_82196_1_) <= getMeleeRange())
		{
			attackChoosenEntity(p_82196_1_, 10F, 5);
		}
	}

	public int getSkeletonType()
	{
		return dataWatcher.getWatchableObjectByte(13);
	}

	public EntitySkeletonTitan setSkeletonType(int p_82201_1_)
	{
		dataWatcher.updateObject(13, Byte.valueOf((byte)p_82201_1_));
		return this;
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		if (tagCompund.hasKey("SkeletonType", 99))
		{
			byte b0 = tagCompund.getByte("SkeletonType");
			setSkeletonType(b0);
		}

		setCombatTask();
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setByte("SkeletonType", (byte)getSkeletonType());
	}

	public void setCurrentItemOrArmor(int slotIn, ItemStack stack)
	{
		super.setCurrentItemOrArmor(slotIn, stack);
		if ((!worldObj.isRemote) && (slotIn == 0))
		{
			setCombatTask();
		}
	}

	public float getEyeHeight()
	{
		return 0.8625F * height;
	}

	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	public double getYOffset()
	{
		if (getSkeletonType() == 1)
		{
			return super.getYOffset() - 12.48D;
		}

		return super.getYOffset() - 10.4D;
	}

	protected void inactDeathAction()
	{
		if (!worldObj.isRemote)
		{
			playSound("mob.skeleton.death", getSoundVolume(), getSoundPitch());
			if ((worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
			{
				dropFewItems(true, 0);
				dropEquipment(true, 0);
				dropRareDrop(1);
			}

			EntityTitanSpirit entitytitan = new EntityTitanSpirit(worldObj);
			entitytitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.spawnEntityInWorld(entitytitan);
			entitytitan.setVesselHunting(false);
			if (getSkeletonType() == 1)
			{
				entitytitan.setSpiritType(5);

			}

			 else 
			{

				entitytitan.setSpiritType(4);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected void onTitanDeathUpdate()
	{
		dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(getTitanHealth(), 0.0F, getMaxHealth())));
		if (getTitanHealth() <= 0F)
		{
			++deathTicks;
			AnimationAPI.sendAnimPacket(this, 10);
			setAnimID(10);
			setTitanHealth(0.0F);
		}

		else
		{
			attackEntityFrom(DamageSource.outOfWorld, 25F);
			setTitanHealth(getTitanHealth());
			setHealth(getTitanHealth());
			deathTicks = 0;
			if (getAnimID() == 10)
			setAnimID(0);
		}

		motionX *= 0.0D;
		motionZ *= 0.0D;
		setAttackTarget(null);
		if ((deathTicks == 1) && (!worldObj.isRemote))
		{
			playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
			ArrayList<?> listp = Lists.newArrayList(worldObj.playerEntities);
			if ((listp != null) && (!listp.isEmpty()))
			{
				for (int i1 = 0; i1 < listp.size(); i1++)
				{
					Entity entity = (Entity)listp.get(i1);
					if ((entity != null) && ((entity instanceof EntityPlayer)))
					{
						((EntityPlayer)entity).triggerAchievement(getAchievement());
					}
				}
			}
		}

		if (deathTicks == 1)
		{
			setAnimTick(1);
		}

		if (deathTicks >= 500)
		{
			setInvulTime(getInvulTime() + 8);
			float f = (rand.nextFloat() - 0.5F) * 24.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 8.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 24.0F;
			worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}

		if (getInvulTime() >= (getSkeletonType() == 1 ? 1560 : getThreashHold()))
		{
			setDead();
		}
	}

	public boolean attackSkeletonFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		else if (((source.getEntity() instanceof EntitySkeletonMinion)) || ((ridingEntity != null) && (source.getEntity() == ridingEntity) && ((ridingEntity instanceof EntitySpiderTitan))) || ((source.getEntity() instanceof EntitySkeletonTitan)) || ((source.getEntity() instanceof EntityWitherMinion)))
		{
			return false;
		}

		else
		{
			recentlyHit = 200;
			Entity entity = source.getEntity();
			if (((entity instanceof EntityLivingBase)) && (!isEntityInvulnerable()) && (amount > 25.0F))
			{
				List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(256.0D, 256.0D, 256.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if ((entity1 instanceof EntitySkeletonTitan))
					{
						EntitySkeletonTitan entitypigzombie = (EntitySkeletonTitan)entity1;
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

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (source.getEntity() instanceof EntityPlayer && !worldObj.isRemote)
		{
			setAttackTarget((EntityLivingBase) source.getEntity());
			setRevengeTarget((EntityLivingBase) source.getEntity());
		}

		if (isEntityInvulnerable())
		{
			return false;
		}

		else if (((source.getEntity() instanceof EntitySkeletonMinion)) || ((ridingEntity != null) && (source.getEntity() == ridingEntity) && ((ridingEntity instanceof EntitySpiderTitan))) || ((source.getEntity() instanceof EntitySkeletonTitan)) || ((source.getEntity() instanceof EntityWitherMinion)))
		{
			return false;
		}

		else if (getSkeletonType() == 1 && source.isFireDamage())
		{
			heal(amount);
			return false;
		}

		else
		{
			recentlyHit = 200;
			Entity entity = source.getEntity();
			if (((entity instanceof EntityLivingBase)) && (!isEntityInvulnerable()) && (amount > 25.0F))
			{
				List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(256.0D, 256.0D, 256.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if ((entity1 instanceof EntitySkeletonTitan))
					{
						EntitySkeletonTitan entitypigzombie = (EntitySkeletonTitan)entity1;
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

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean attackEntityFromPart(EntityTitanPart p_70965_1_, DamageSource source, float amount)
	{
		func_82195_e(source, amount);
		return true;
	}

	protected boolean func_82195_e(DamageSource p_82195_1_, float p_82195_2_)
	{
		return attackEntityFrom(p_82195_1_, p_82195_2_);
	}

	public World func_82194_d()
	{
		return worldObj;
	}

	protected EntityLiving getMinion()
	{
		return new EntitySkeletonMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}
}


