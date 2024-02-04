package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titan.ai.EntityAITitanLookIdle;
import net.minecraft.entity.titanminion.EntityGhastMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityGhastTitan
extends EntityTitan
{
	public int courseChangeCooldown;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	public int prevAttackCounter;
	public int attackCounter;
	public EntityGhastTitan(World worldIn)
	{
		super(worldIn);
		shouldParticlesBeUpward = true;
		noClip = true;
		setSize(110F, 110F);
		experienceValue = 750000;
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		tasks.removeTask(new EntityAITitanLookIdle(this));
	}

	protected void applyEntityAI()
	{
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.GhastTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted % 5 == 0)
		{
			setSize(4.5F * getTitanSizeMultiplier(), 4.5F * getTitanSizeMultiplier());
		}

		super.onHitboxUpdate();
	}
	
	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 10.0F;
	}

	public float getEyeHeight()
	{
		return height * 0.5454545454545455F;
	}

	@SideOnly(Side.CLIENT)
	public boolean func_110182_bF()
	{
		return dataWatcher.getWatchableObjectByte(16) != 0;
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityGhastMinion.class) && (p_70686_1_ != EntityGhastTitan.class) && (p_70686_1_ != EntityTitanFireball.class);
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(250) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (super.getCanSpawnHere());
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.GhastTitanMinionSpawnrate;
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte)0));
	}

	public int getParticleCount()
	{
		return 100;
	}

	public String getParticles()
	{
		return "largesmoke";
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.GREATER;
	}

	protected String getLivingSound()
	{
		return "thetitans:titanGhastLiving";
	}

	protected String getHurtSound()
	{
		return "thetitans:titanGhastGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:titanGhastDeath";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) 
	{
		 
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

	public double getSpeed()
	{
		return 0.5D + getExtraPower() * 0.001D;
	}

	protected void fall(float p_70069_1_) 
	{
		 
	}


	protected void updateFallState(double p_70064_1_, boolean p_70064_3_) 
	{
		 
	}


	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.91F;
		motionY *= 0.91F;
		motionZ *= 0.91F;
		prevLimbSwingAmount = limbSwingAmount;
		double d1 = posX - prevPosX;
		double d0 = posZ - prevPosZ;
		float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
		if (f4 > 1.0F)
		{
			f4 = 1.0F;
		}

		limbSwingAmount += (f4 - limbSwingAmount) * 0.4F;
		limbSwing += limbSwingAmount;
	}

	public boolean isOnLadder()
	{
		return false;
	}

	public String getCommandSenderName()
	{
		switch (getTitanVariant())
		{
			case 0:
			return "\u00A7f" + StatCollector.translateToLocal("entity.GhastTitan.name") + "\u00A7f";
			case 1:
			return "\u00A7b" + StatCollector.translateToLocal("entity.GhastTitanLightning.name") + "\u00A7f";
			case 2:
			return "\u00A70" + StatCollector.translateToLocal("entity.GhastTitanObsidian.name") + "\u00A7f";
		}

		return null;
	}

	public void onLivingUpdate()
	{
		noClip = true;
		
		for (int i = 0; i < 90; i++)
		{
			double d0 = posX + (rand.nextFloat() * 90.0F - 45.0F);
			double d1 = posY + rand.nextFloat() * 30.0F;
			double d2 = posZ + (rand.nextFloat() * 90.0F - 45.0F);
			if ((!worldObj.isRemote) && (worldObj.getBlock((int)d0, (int)d1 + (int)getEyeHeight(), (int)d2).getMaterial() != Material.air))
			{
				setPosition(posX, posY + 0.1D, posZ);
			}
		}

		EntityPlayer entity = worldObj.getClosestPlayerToEntity(this, -1.0D);
		if (((entity instanceof EntityPlayer)) && (entity != null) && (entity == getAttackTarget()) && (getAttackTarget() != null))
		{
			entity.setFire(50);
			if ((rand.nextInt(200) == 0) && (getAttackTarget() != null) && (getHealth() <= getMaxHealth() / 100.0F))
			{
				entity.attackEntityFrom(DamageSourceExtra.onFire.setDamageIsAbsolute().setDamageAllowedInCreativeMode(), Float.MAX_VALUE);
			}

			if ((entity.getAbsorptionAmount() <= 0.0F) && (ticksExisted % 10 == 0))
			{
				entity.attackEntityFrom(DamageSourceExtra.onFire.setDamageIsAbsolute().setDamageAllowedInCreativeMode(), 12.0F);
				entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400, 9));
				if (entity.getHealth() <= 5.0F)
				{
					entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 1));
				}
			}

			else if ((entity.getAbsorptionAmount() >= 0.0F) && (ticksExisted % 20 == 0))
			{
				entity.attackEntityFrom(DamageSourceExtra.onFire.setDamageIsAbsolute().setDamageAllowedInCreativeMode(), 12.0F);
			}
		}

		if (worldObj.isRemote)
		{
			for (int i1 = 0; i1 < getParticleCount(); i1++)
			{
				worldObj.spawnParticle(getParticles(), posX + (rand.nextDouble() - 0.5D) * 96D, posY + rand.nextDouble() * 96D, posZ + (rand.nextDouble() - 0.5D) * 96D, 0.0D, 0.5D, 0.0D);
			}
		}

		super.onLivingUpdate();
	}

	protected void updateAITasks()
	{
		prevAttackCounter = attackCounter;
		double d0 = waypointX - posX;
		double d1 = waypointY - posY;
		double d2 = waypointZ - posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		if (d3 < 36D || d3 > 40000.0D)
		{
			if (getAttackTarget() != null)
			{
				waypointX = getAttackTarget().posX + ((rand.nextDouble() * 2.0D - 1.0D) * 96D);
				waypointY = 160D + ((rand.nextDouble() * 2.0D - 1.0D) * 32D);
				waypointZ = getAttackTarget().posZ + ((rand.nextDouble() * 2.0D - 1.0D) * 96D);
			}

			else
			{
				EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 512D);
				if (player != null)
				{
					waypointX = player.posX + ((rand.nextDouble() * 2.0D - 1.0D) * 96D);
					waypointY = 160D + ((rand.nextDouble() * 2.0D - 1.0D) * 32D);
					waypointZ = player.posZ + ((rand.nextDouble() * 2.0D - 1.0D) * 96D);
				}

				else
				{
					waypointX = posX + ((rand.nextDouble() * 2.0D - 1.0D) * 96D);
					waypointY = 160D + ((rand.nextDouble() * 2.0D - 1.0D) * 32D);
					waypointZ = posZ + ((rand.nextDouble() * 2.0D - 1.0D) * 96D);
				}
			}
		}

		if (courseChangeCooldown-- <= 0)
		{
			courseChangeCooldown += rand.nextInt(5) + 2;
			d3 = MathHelper.sqrt_double(d3);
			motionX += d0 / d3 * 0.3D;
			motionY += d1 / d3 * 0.3D;
			motionZ += d2 / d3 * 0.3D;
		}

		double d4 = 1024.0D;
		renderYawOffset = rotationYaw = rotationYawHead;
		if ((getAttackTarget() != null) && (getAttackTarget().getDistanceToEntity(this) < d4))
		{
			getLookHelper().setLookPositionWithEntity(getAttackTarget(), 180F, 180F);
			double d8 = width * 0.5D;
			Vec3 vec3 = getLook(1.0F);
			double px = (posX + vec3.xCoord * d8);
			double py = (posY + vec3.yCoord * d8) + 10D;
			double pz = (posZ + vec3.zCoord * d8);
			double d11 = getAttackTarget().posX - (px);
			double d21 = getAttackTarget().posY - (py);
			double d31 = getAttackTarget().posZ - (pz);
			if (canEntityBeSeen(getAttackTarget()))
			{
				if (attackCounter == 10)
				{
					playSound("thetitans:titanGhastCharge", Float.MAX_VALUE, getSoundPitch());
				}

				attackCounter += 1;
				if (attackCounter >= 50)
				{
					faceEntity(getAttackTarget(), 180F, 180F);
					if (getDistanceToEntity(getAttackTarget()) <= getMeleeRange() && ticksExisted % 10 == 0)
					attackChoosenEntity(getAttackTarget(), (float)getAttackValue(3.0F), getKnockbackAmount());
					playSound("thetitans:titanGhastFireball", Float.MAX_VALUE, 1F);
					EntityTitanFireball entitysmallfireball = new EntityTitanFireball(worldObj, this, d11 + (getRNG().nextGaussian() * 16D), d21, d31 + (getRNG().nextGaussian() * 16D));
					entitysmallfireball.posX = (px);
					entitysmallfireball.posY = (py);
					entitysmallfireball.posZ = (pz);
					worldObj.spawnEntityInWorld(entitysmallfireball);
					if (attackCounter == 100)
					attackCounter = -80;
				}
			}

			else if (attackCounter > 0)
			{
				attackCounter -= 1;
			}
		}

		else
		{
			if (getAttackTarget() == null)
			renderYawOffset = rotationYaw = rotationYawHead = -(float)Math.atan2(motionX, motionZ) * 180.0F / 3.1415927F;
			if (attackCounter > 0)
			{
				attackCounter -= 1;
			}
		}

		if (!worldObj.isRemote)
		{
			byte b1 = dataWatcher.getWatchableObjectByte(16);
			byte b0 = (byte)(attackCounter > 20 ? 1 : 0);
			if (b1 != b0)
			{
				dataWatcher.updateObject(16, Byte.valueOf(b0));
			}
		}

		super.updateAITasks();
	}

	public int getVerticalFaceSpeed()
	{
		return 180;
	}

	protected Item getDropItem()
	{
		return Items.blaze_rod;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < 80; x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
				entitylargefireball.setPosition(posX, posY + 4D, posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(26000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			for (int x = 0; x < 16; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.gunpowder));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 16; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.ghast_tear));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 12; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.end_stone));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 12; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.obsidian));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.coal_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 6; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.iron_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.gold_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.emerald_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.diamond_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanBlocks.harcadium_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
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

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.golden_apple, 1, 1));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}
	}

	protected void addRandomArmor()
	{
		dropItem(Items.brewing_stand, 64);
	}

	public boolean func_70845_n()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
	}

	public void func_70844_e(boolean p_70844_1_)
	{
		byte b0 = dataWatcher.getWatchableObjectByte(16);
		if (p_70844_1_)
		{
			b0 = (byte)(b0 | 0x1);
		}

		else
		{
			b0 = (byte)(b0 & 0xFFFFFFFE);
		}

		dataWatcher.updateObject(16, Byte.valueOf(b0));
	}

	protected boolean isValidLightLevel()
	{
		return true;
	}

	protected void collideWithEntity(Entity p_82167_1_)
	{
		if ((!(p_82167_1_ instanceof EntitySmallFireball)) || (!(p_82167_1_ instanceof EntityLargeFireball)))
		{
			p_82167_1_.applyEntityCollision(this);
		}
	}

	public StatBase getAchievement()
	{
		return TitansAchievments.ghasttitan;
	}

	public boolean canBeCollidedWith()
	{
		return true;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		if (((source.getEntity() instanceof EntityGhastMinion)) || ((source.getEntity() instanceof EntityGhastTitan)))
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

	protected void inactDeathAction()
	{
		if (!worldObj.isRemote)
		{
			playSound("mob.ghast.death", getSoundVolume(), getSoundPitch());
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
			entitytitan.setSpiritType(11);
		}
	}

	protected EntityLiving getMinion()
	{
		return new EntityGhastMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}
}


