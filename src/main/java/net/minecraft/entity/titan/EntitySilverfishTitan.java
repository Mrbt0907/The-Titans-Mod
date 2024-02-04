package net.minecraft.entity.titan;
import com.google.common.collect.Lists;
import net.minecraft.theTitans.core.TheCore;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titan.ai.EntityAITitanWander;
import net.minecraft.entity.titan.ai.EntityAITitanWatchClosest;
import net.minecraft.entity.titan.animation.omegafish.*;
import net.minecraft.entity.titanminion.EntitySilverfishMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import thehippomaster.AnimationAPI.AnimationAPI;
import thehippomaster.AnimationAPI.IAnimatedEntity;
public class EntitySilverfishTitan
extends EntityTitan
implements IAnimatedEntity, ITitanHitbox
{
	public boolean isSubdued;
	public boolean isBurrowing;
	public boolean isStunned;
	public EntityTitanPart head;
	public EntityTitanPart body;
	public EntityTitanPart tailbase;
	public EntityTitanPart tail1;
	public EntityTitanPart tail2;
	public EntityTitanPart tailtip;
	public EntitySilverfishTitan(World worldIn)
	{
		super(worldIn);
		head = new EntityTitanPart(worldIn, this, "head", 3.0F, 2.0F);
		body = new EntityTitanPart(worldIn, this, "body", 5.0F, 4.0F);
		tailbase = new EntityTitanPart(worldIn, this, "tailbase", 3.0F, 3.0F);
		tail1 = new EntityTitanPart(worldIn, this, "tail1", 3.0F, 2.0F);
		tail2 = new EntityTitanPart(worldIn, this, "tail2", 2.0F, 1.0F);
		tailtip = new EntityTitanPart(worldIn, this, "tailtip", 2.0F, 1.0F);
		partArray.add(head);
		partArray.add(body);
		partArray.add(tailbase);
		partArray.add(tail1);
		partArray.add(tail2);
		partArray.add(tailtip);
		setSize(9.0F, 6.0F);
		experienceValue = (6000 + getExtraPower() * 200);
		worldObj.spawnEntityInWorld(head);
		worldObj.spawnEntityInWorld(body);
		worldObj.spawnEntityInWorld(tailbase);
		worldObj.spawnEntityInWorld(tail1);
		worldObj.spawnEntityInWorld(tail2);
		worldObj.spawnEntityInWorld(tailtip);
	}

	protected void onHitboxUpdate()
	{
			if (ticksExisted > 5)
			{
				setSize(0.5625F * getTitanSizeMultiplier(), 0.375F * getTitanSizeMultiplier());
				
				float f = renderYawOffset * 3.1415927F / 180.0F;
				float f1 = MathHelper.sin(f);
				float f2 = MathHelper.cos(f);
				body.height = 0.275F * getTitanSizeMultiplier();
				body.width = 0.35F * getTitanSizeMultiplier();
				head.width = tailbase.height = tailbase.width = tail1.width = 0.1875F * getTitanSizeMultiplier();
				head.height = tail1.height = tail2.width = tailtip.width = 0.125F * getTitanSizeMultiplier();
				tail2.height = tailtip.height = 0.0625F * getTitanSizeMultiplier();
				head.setLocationAndAngles(posX - MathHelper.sin(rotationYawHead * 3.1415927F / 180.0F) * (0.375F * width), posY - MathHelper.sin(rotationPitch * 3.1415927F / 180.0F) * (0.335F * height), posZ + MathHelper.cos(rotationYawHead * 3.1415927F / 180.0F) * (0.375F * width), 0.0F, 0.0F);
				body.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
				tailbase.setLocationAndAngles(posX + f1 * (0.375F * width), posY, posZ - f2 * (0.375F * width), 0.0F, 0.0F);
				tail1.setLocationAndAngles(posX + f1 * (0.775F * width), posY, posZ - f2 * (0.775F * width), 0.0F, 0.0F);
				tail2.setLocationAndAngles(posX + f1 * (1.05F * width), posY, posZ - f2 * (1.05F * width), 0.0F, 0.0F);
				tailtip.setLocationAndAngles(posX + f1 * (1.275F * width), posY, posZ - f2 * (1.275F * width), 0.0F, 0.0F);
				
				if (isBurrowing)
					destroyBlocksInAABB(boundingBox.expand(1.0D, 0.0D, 1.0D));
			}
		
		super.onHitboxUpdate();
	}

	protected void applyEntityAI()
	{
		tasks.addTask(1, new AnimationOmegafishCreation(this));
		tasks.addTask(1, new AnimationOmegafishDeath(this));
		tasks.addTask(1, new AnimationOmegafishStunned(this));
		tasks.addTask(1, new AnimationOmegafishAntiTitanAttack(this));
		tasks.addTask(1, new AnimationOmegafishBodySlam(this));
		tasks.addTask(1, new AnimationOmegafishTailSmash(this));
		tasks.addTask(1, new AnimationOmegafishLightningAttack(this));
		tasks.addTask(1, new AnimationOmegafishAttack2(this));
		tasks.addTask(1, new AnimationOmegafishAttack3(this));
		tasks.addTask(1, new AnimationOmegafishAttack1(this));
		tasks.addTask(1, new AnimationOmegafishUnburrow(this));
		tasks.addTask(1, new AnimationOmegafishBurrow(this));
		tasks.addTask(6, new EntityAITitanWander(this, 50));
		tasks.addTask(7, new EntityAITitanWatchClosest(this, EntityTitanSpirit.class, 128.0F));
		tasks.addTask(8, new EntityAITitanWatchClosest(this, EntityTitan.class, 128.0F));
		tasks.addTask(9, new EntityAITitanWatchClosest(this, EntityPlayer.class, 64.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (!isSubdued)
		{
			if (TheTitans.TitansFFAMode)
			targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SilverfishTitanSorter));
			else
			targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
			targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
			targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		}
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		isStunned = tagCompund.getBoolean("Stunned");
		isBurrowing = tagCompund.getBoolean("Burrowing");isSubdued = tagCompund.getBoolean("Subdued");
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("Stunned", isStunned);
		tagCompound.setBoolean("Burrowing", isBurrowing);
		tagCompound.setBoolean("Subdued", isSubdued);
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 8.0F;
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != head.getClass() && p_70686_1_ != body.getClass() && p_70686_1_ != tailbase.getClass() && p_70686_1_ != tail1.getClass() && p_70686_1_ != tail2.getClass() && p_70686_1_ != tailtip.getClass() && (p_70686_1_ != EntitySilverfishMinion.class) && (p_70686_1_ != EntitySilverfishTitan.class));
	}

	public boolean isArmored()
	{
		return getHealth() <= getMaxHealth() / 4.0F || TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) || TheTitans.isDifficulty(worldObj, TheCore.IMPOSSIBLE);
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(10) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (isValidLightLevel());
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.OmegafishMinionSpawnrate;
	}

	public float getEyeHeight()
	{
		return 0.3333333333333333F * height;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
	}

	protected String getLivingSound()
	{
		return isStunned || getWaiting() || getAnimID() == 2 ? null : "thetitans:titanSilverfishLiving";
	}

	protected String getHurtSound()
	{
		return "thetitans:titanSilverfishGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:titanSilverfishDeath";
	}

	protected Item getDropItem()
	{
		return Items.paper;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < 6; x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 6D, posZ);
				entitylargefireball.setPosition(posX, posY + 6D, posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(3000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.paper));
				itembomb.setItemCount(24 + rand.nextInt(24 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.coal));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.emerald));
				itembomb.setItemCount(4 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.diamond));
				itembomb.setItemCount(4 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.cobblestone));
				itembomb.setItemCount(8 + rand.nextInt(24 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.stone));
				itembomb.setItemCount(8 + rand.nextInt(24 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.mossy_cobblestone));
				itembomb.setItemCount(8 + rand.nextInt(24 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.stonebrick));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.monster_egg));
				itembomb.setItemCount(4 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			if ((rand.nextInt(2) == 0 || rand.nextInt(1 + p_70628_2_) > 0))
			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanItems.harcadiumNugget));
				itembomb.setItemCount(1 + rand.nextInt(3 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}
	}

	public boolean canBeHurtByPlayer()
	{
		return isStunned && !isBurrowing && (!isEntityInvulnerable());
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return false;
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) 
	{
		 
	}


	public boolean shouldMove()
	{
		return !isStunned && super.shouldMove();
	}

	public double getSpeed()
	{
		return isBurrowing ? 0.9D + getExtraPower() * 0.001D : 0.7D + getExtraPower() * 0.001D;
	}

	public void onLivingUpdate()
	{
		setInvisible(isBurrowing);
		if (!isRiding() && !isBurrowing && !getWaiting() && !isStunned && getAnimID() == 0)
		{
			titanFly(5F, 1F, 2D);
		}

		if (getWaiting())
		{
			AnimationAPI.sendAnimPacket(this, 13);
			EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 24D);
			if (player != null)
			{
				setWaiting(false);
				AnimationAPI.sendAnimPacket(this, 13);
				player.triggerAchievement(TitansAchievments.locateTitan);
			}
		}

		else
		{
			if (getAnimID() == 13)
			{
				motionX = 0D;
				motionZ = 0D;
				if (motionY > 0D)
				motionY = 0D;
			}

			if (getAnimID() == 13 && worldObj.isRemote)
			switch(getAnimTick())
			{
				case 1: 
				{
					playSound("thetitans:titanBirth", 100F, 1.25F);
				}


				case 10: 
				{
					playSound("thetitans:titanSilverfishLiving", getSoundVolume(), 0.7F);
				}


				case 150:
				{
					playSound("thetitans:titanPress", getSoundVolume(), 1F);
					shakeNearbyPlayerCameras(4D);
				}
			}
		}

		if (!isBurrowing && !isStunned && getAnimID() != 2 && getAnimID() != 9 && (getAttackTarget() != null) && getDistanceToEntity(getAttackTarget()) > 200D)
		{
			if (posY <= getAttackTarget().posY + 24D)
			{
				motionY += 0.9D - motionY;
				if (motionY < 0.0D)
				motionY = 0.0D;
			}

			motionY *= 0.9D;
		}

		if (!isBurrowing && !onGround)
		{
			float f = (rand.nextFloat() - 0.5F) * 8.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 1.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 8.0F;
			worldObj.spawnParticle("largeexplode", posX + (double)f, posY + (double)f1, posZ + (double)f2, 0.0D, 0.0D, 0.0D);
		}

		if (isSubdued)
		renderYawOffset = rotationYaw = rotationYawHead;
		EntityPlayer player = worldObj.getClosestPlayerToEntity(head, 4D);
		if (player != null && head.posY < player.posY - 2D)
		{
			rotationYawHead += MathHelper.sin(ticksExisted) * 40F;
			rotationPitch -= MathHelper.cos(ticksExisted) * 40F;
		}

		if (getAnimID() == 1 && getAnimTick() > 80)
		isBurrowing = true;
		else if (getAnimID() == 2)
		isBurrowing = false;
		if (isBurrowing)
		{
			destroyBlocksInAABB(boundingBox.expand(2, 0, 2));
			worldObj.playAuxSFX(2006, MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.20000000298023224D - (double)yOffset), MathHelper.floor_double(posZ), MathHelper.ceiling_float_int(128.0F));
			if (ticksExisted % 40 == 0)
			{
				playSound("thetitans:titanRumble", 10.0F, 1.0F);
				playSound("thetitans:titanRumble", 9.0F, 1.0F);
				playSound("thetitans:titanRumble", 8.0F, 1.0F);
				playSound("thetitans:titanRumble", 7.0F, 1.0F);
				playSound("thetitans:titanRumble", 6.0F, 1.0F);
				playSound("thetitans:titanQuake", 5.0F, 1.0F);
				playSound("thetitans:titanQuake", 4.0F, 1.0F);
				playSound("thetitans:titanQuake", 3.0F, 1.0F);
				playSound("thetitans:titanQuake", 2.0F, 1.0F);
				playSound("thetitans:titanQuake", 1.0F, 1.0F);
			}
		}

		if ((isSubdued) && (ticksExisted % 40 == 0))
		{
			targetTasks.removeTask(new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
			targetTasks.removeTask(new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
			targetTasks.removeTask(new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		}

		if (getAnimID() == 7)
		{
			if (getAnimTick() == 20)
			{
				double d8 = -3.0D;
				Vec3 vec3 = getLook(1.0F);
				double dx = vec3.xCoord * d8;
				double dz = vec3.zCoord * d8;
				float f = (float)getAttackValue(3.0F);
				int i = getKnockbackAmount();
				worldObj.newExplosion(this, posX + dx, posY + 8.0D, posZ + dz, 1.0F, false, false);
				worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, posX + dx, posY + 8.0D, posZ + dz, 0.5F, 0.5F, 0.5F));
				if (getAttackTarget() != null)
				{
					attackChoosenEntity(getAttackTarget(), f, i);
					getAttackTarget().motionY += 2.0D;
					worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2.0F, false, false);
					getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, f);
					worldObj.addWeatherEffect(new EntityGammaLightning(worldObj, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 0.25F, 0.25F, 0.25F));
					List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(getAttackTarget(), getAttackTarget().boundingBox.expand(2.0D, 2.0D, 2.0D));
					if ((list1 != null) && (!list1.isEmpty()))
					{
						for (int i1 = 0; i1 < list1.size(); i1++)
						{
							Entity entity1 = (Entity)list1.get(i1);
							if (((entity1 instanceof EntityLivingBase)) && (!(entity1 instanceof EntitySilverfishMinion)) && (!(entity1 instanceof EntitySilverfishTitan)))
							{
								attackChoosenEntity(entity1, f, i);
								entity1.motionY += 1.0D;
							}
						}
					}
				}
			}
		}

		meleeTitan = true;
		

		if (isStunned)
		{
			setAttackTarget(null);
			AnimationAPI.sendAnimPacket(this, 8);
		}

		if ((!AnimationAPI.isEffectiveClient()) && (getAttackTarget() != null) && (!isStunned) && isBurrowing && posY > getAttackTarget().posY)
		{
			destroyBlocksInAABBTopless(boundingBox.offset(0D, -1D, 0D));
		}

		if ((!AnimationAPI.isEffectiveClient()) && (getAttackTarget() != null) && (!isStunned) && getAnimID() == 0)
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (!isBurrowing && canEntityBeSeen(getAttackTarget()) && d0 > 200D && onGround)
			AnimationAPI.sendAnimPacket(this, 1);
			if (d0 < getMeleeRange())
			{
				if (isBurrowing)
				{
					AnimationAPI.sendAnimPacket(this, 2);
					setAnimID(2);
				}

				else
				{
					if (((getAttackTarget() instanceof EntityTitan)) || (getAttackTarget().height >= 6.0F) || (getAttackTarget().posY > posY + 6D))
					{
						AnimationAPI.sendAnimPacket(this, 11);
						setAnimID(11);
					}

					else
					{
						switch (rand.nextInt(5))
						{
							case 0:AnimationAPI.sendAnimPacket(this, 6);
							setAnimID(6);
							break;
							case 1:AnimationAPI.sendAnimPacket(this, 5);
							setAnimID(5);
							break;
							case 2:AnimationAPI.sendAnimPacket(this, 4);
							setAnimID(4);
						}
					}
				}
			}

			else
			{
				int i = 60;
				if (isArmored())
				{
					i = 20;
				}

				if (getAnimID() == 0 && !isBurrowing && (getRNG().nextInt(i) == 0))
				{
					if (getAttackTarget().posY > posY + 12D)
					{
						AnimationAPI.sendAnimPacket(this, 7);
						setAnimID(7);
					}

					else
					{
						switch (rand.nextInt(4))
						{
							case 0:AnimationAPI.sendAnimPacket(this, 3);
							setAnimID(3);
							break;
							case 1:AnimationAPI.sendAnimPacket(this, 7);
							setAnimID(7);
							break;
							case 2:if (!isBurrowing)
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
							case 3:if (getRNG().nextInt(3) == 0)
							{
								AnimationAPI.sendAnimPacket(this, 9);
								setAnimID(9);
							}

							else
							{
								AnimationAPI.sendAnimPacket(this, 3);
								setAnimID(3);
							}

							break;
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

		if (getAnimID() == 8)
		{
			setAttackTarget(null);
		}

		if (getAnimID() == 8)
		{
			if (getAnimTick() == 37)
			{
				playSound("thetitans:largeFall", 4.0F, 1.0F);
			}

			if (getAnimTick() == 37)
			{
				playSound("thetitans:distantLargeFall", 10000.0F, 1.0F);
			}

			if (getAnimTick() == 380)
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
			if ((getAnimTick() == 74) || (getAnimTick() == 216))
			{
				playSound("thetitans:titanFall", 10.0F, 1.0F);
			}

			if (getAnimTick() == 76)
			{
				playSound("thetitans:distantLargeFall", 10000.0F, 1.0F);
			}
		}

		switch (getTitanVariant())
		{
			case 1:
			setCustomNameTag(StatCollector.translateToLocal("entity.SilverfishTitanDesert.name"));
			break;
			case 2:
			setCustomNameTag(StatCollector.translateToLocal("entity.SilverfishTitanFrost.name"));
			break;
			case 3:
			setCustomNameTag(StatCollector.translateToLocal("entity.SilverfishTitanSea.name"));
			break;
			case 4:
			setCustomNameTag(StatCollector.translateToLocal("entity.SilverfishTitanPoison.name"));
			break;
			case 5:
			setCustomNameTag(StatCollector.translateToLocal("entity.SilverfishTitanMagma.name"));
			break;
			case 6:
			setCustomNameTag(StatCollector.translateToLocal("entity.SilverfishTitanVoid.name"));
			break;
			default:
			setCustomNameTag(StatCollector.translateToLocal("entity.SilverfishTitan.name"));
		}

		if ((rand.nextInt(100) == 0) && (getAttackTarget() != null) && getAnimID() == 0)
		{
			faceEntity(getAttackTarget(), 180.0F, 180.0F);
			double d0 = getAttackTarget().posX - posX;
			double d1 = getAttackTarget().posZ - posZ;
			float f21 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
			motionX = (d0 / f21 * 2.0D * 2.0D + motionX * 2.0D);
			motionZ = (d1 / f21 * 2.0D * 2.0D + motionZ * 2.0D);
		}

		if ((isEntityAlive()) && (rand.nextInt(40) == 0) && (getAttackTarget() != null) && (isArmored()) && !(getAttackTarget() instanceof EntityTitan))
		{
			rotationPitch = -90F;
			worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 2.0F, false, false);
		}

		int i;
		int j;
		int k;
		int i1;
		if (!worldObj.isRemote && !isStunned && !getWaiting() && getAnimID() != 13)
		{
			i = MathHelper.floor_double(posX);
			j = MathHelper.floor_double(posY);
			k = MathHelper.floor_double(posZ);
			boolean flag = false;
			for (int l = 0; !flag && l <= 20 && l >= -20; l = l <= 0 ? 1 - l : 0 - l)
			{
				for (i1 = 0; !flag && i1 <= 20 && i1 >= -20; i1 = i1 <= 0 ? 1 - i1 : 0 - i1)
				{
					for (int j1 = 0; !flag && j1 <= 20 && j1 >= -20; j1 = j1 <= 0 ? 1 - j1 : 0 - j1)
					{
						if (worldObj.getBlock(i + i1, j + l, k + j1) == Blocks.monster_egg)
						{
							worldObj.func_147480_a(i + i1, j + l, k + j1, false);
							EntitySilverfishMinion entitysilverfish = new EntitySilverfishMinion(worldObj);
							entitysilverfish.setLocationAndAngles(i + i1 + 0.5D, j + l, k + j1 + 0.5D, 0.0F, 0.0F);
							entitysilverfish.onSpawnWithEgg(null);
							if (!worldObj.isRemote)
							worldObj.spawnEntityInWorld(entitysilverfish);
							worldObj.createExplosion(entitysilverfish, entitysilverfish.posX, entitysilverfish.posY, entitysilverfish.posZ, 2F, false);
							entitysilverfish.spawnExplosionParticle();
							worldObj.setBlockToAir(i + i1, j + l, k + j1);
							if (rand.nextBoolean())
							{
								flag = true;
								break;
							}
						}
					}
				}
			}
		}

		super.onLivingUpdate();
	}

	public StatBase getAchievement()
	{
		return TitansAchievments.omegafish;
	}

	public boolean attackOmegafishFrom(DamageSource source, float amount)
	{
		if (source.getEntity() != null && source.getEntity() instanceof EntityPlayer && source.canHarmInCreative())
		{
			playSound(getHurtSound(), getSoundVolume(), getSoundPitch());
			isStunned = true;
			setAttackTarget(null);
		}

		if (isEntityInvulnerable() || isBurrowing)
		{
			return false;
		}

		else if (((source.getEntity() instanceof EntitySilverfishMinion)) || ((source.getEntity() instanceof EntitySilverfishTitan)))
		{
			return false;
		}

		else
		{
			recentlyHit = 200;
			Entity entity = source.getEntity();
			if (((entity instanceof EntityLivingBase)) && (!isEntityInvulnerable()) && (amount > 25.0F))
			{
				List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(100.0D, 100.0D, 100.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if ((entity1 instanceof EntitySilverfishTitan))
					{
						EntitySilverfishTitan entitypigzombie = (EntitySilverfishTitan)entity1;
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
		return true;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return attackOmegafishFrom(source, amount);
	}

	protected boolean isMovementBlocked()
	{
		return (isSubdued) && (riddenByEntity == null) ? true : super.isMovementBlocked();
	}

	public void updateRiderPosition()
	{
		if (riddenByEntity != null)
		{
			double d8 = 0.4D + getExtraPower() * 0.05D;
			Vec3 vec3 = getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			riddenByEntity.setPosition(posX + dx, posY + (isBurrowing ? 1D : (0.3125D * getTitanSizeMultiplier())), posZ + dz);
		}
	}

	public boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();
		p_70085_1_.swingItem();
		if ((isStunned) && (!isSubdued))
		{
			if ((itemstack != null) && (itemstack.getItem() == Items.golden_apple))
			{
				isSubdued = true;
				worldObj.playSoundAtEntity(this, "random.levelup", 10.0F, 1.0F);
				p_70085_1_.addChatMessage(new ChatComponentText(getCustomNameTag() + " has been subdued by " + p_70085_1_.getCommandSenderName()));
				return super.interact(p_70085_1_);
			}
		}

		else if (isSubdued)
		{
			if ((itemstack == null) && (p_70085_1_.ridingEntity == null))
			{
				p_70085_1_.mountEntity(this);
			}

			else if (itemstack != null)
			{
				if (itemstack.getItem() == Items.diamond)
				{
					AnimationAPI.sendAnimPacket(this, 9);
					setAnimID(9);
				}

				if (itemstack.getItem() == Items.iron_shovel)
				{
					if (isBurrowing)
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

				if (itemstack.getItem() == Items.cooked_chicken)
				{
					AnimationAPI.sendAnimPacket(this, 3);
					setAnimID(3);
				}

				if (itemstack.getItem() == Items.bone)
				{
					switch (rand.nextInt(2))
					{
						case 0:AnimationAPI.sendAnimPacket(this, 5);
						setAnimID(5);
						break;
						case 1:AnimationAPI.sendAnimPacket(this, 6);
						setAnimID(6);
					}
				}
			}
		}

		return false;
	}

	protected void inactDeathAction()
	{
		if (!worldObj.isRemote)
		{
			playSound("mob.silverfish.death", getSoundVolume(), getSoundPitch());
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
			entitytitan.setSpiritType(1);
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
		isBurrowing = false;
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

		if (deathTicks >= 300)
		{
			setInvulTime(getInvulTime() + 8);
			float f = (rand.nextFloat() - 0.5F) * 8.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 4.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 8.0F;
			worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}

		if (getInvulTime() >= getThreashHold())
		{
			setDead();
		}
	}

	public boolean attackEntityFromPart(EntityTitanPart p_70965_1_, DamageSource source, float amount)
	{
		func_82195_e(source, amount);
		return true;
	}

	protected boolean func_82195_e(DamageSource p_82195_1_, float p_82195_2_)
	{
		return attackOmegafishFrom(p_82195_1_, p_82195_2_);
	}

	public World func_82194_d()
	{
		return worldObj;
	}

	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_180482_2_)
	{
		Object p_180482_2_1 = super.onSpawnWithEgg(p_180482_2_);
		setWaiting(true);
		return (IEntityLivingData)p_180482_2_1;
	}

	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		if ((riddenByEntity != null) && ((riddenByEntity instanceof EntityLivingBase)) && (isSubdued))
		{
			setAttackTarget(null);
			rotationPitch = riddenByEntity.rotationPitch;
			rotationYawHead = ((EntityLivingBase)riddenByEntity).rotationYawHead;
			setRotation(rotationYaw, rotationPitch);
			p_70612_1_ = ((EntityLivingBase)riddenByEntity).moveStrafing;
			p_70612_2_ = ((EntityLivingBase)riddenByEntity).moveForward;
			if (((EntityLivingBase)riddenByEntity).moveForward > 0.0F)
			{
				addTitanVelocity(-MathHelper.sin(((EntityLivingBase)riddenByEntity).rotationYawHead * 3.1415927F / 180.0F) * getSpeed(), 0.0D, MathHelper.cos(((EntityLivingBase)riddenByEntity).rotationYawHead * 3.1415927F / 180.0F) * getSpeed());
			}

			if (((EntityLivingBase)riddenByEntity).moveForward < 0.0F)
			{
				addTitanVelocity(-MathHelper.sin(((EntityLivingBase)riddenByEntity).rotationYawHead * 3.1415927F / 180.0F) * -getSpeed(), 0.0D, MathHelper.cos(((EntityLivingBase)riddenByEntity).rotationYawHead * 3.1415927F / 180.0F) * -getSpeed());
			}

			if ((onGround) && (((EntityLivingBase)riddenByEntity).rotationPitch < -80.0F))
			{
				jump();
			}

			if (!worldObj.isRemote)
			{
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}

			prevLimbSwingAmount = limbSwingAmount;
			double do1 = posX - prevPosX;
			double do0 = posZ - prevPosZ;
			float f4 = MathHelper.sqrt_double(do1 * do1 + do0 * do0) * 4.0F;
			if (f4 > 1.0F)
			{
				f4 = 1.0F;
			}

			limbSwingAmount += (f4 - limbSwingAmount) * 0.4F;
			limbSwing += limbSwingAmount;
		}

		else
		{
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}
	}

	protected EntityLiving getMinion()
	{
		return new EntitySilverfishMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}
}


