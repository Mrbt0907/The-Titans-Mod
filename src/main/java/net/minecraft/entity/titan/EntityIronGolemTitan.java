package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titan.animation.ultimairongolemtitan.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import thehippomaster.AnimationAPI.AnimationAPI;
import thehippomaster.AnimationAPI.IAnimatedEntity;
public class EntityIronGolemTitan
extends EntityTitan
implements IAnimatedEntity
{
	private int homeCheckTimer;
	Village villageObj;
	private int attackTimer;
	private int holdRoseTick;
	private static final IEntitySelector attackEntitySelector = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_180027_1_)
		{
			return p_180027_1_.isEntityAlive() && p_180027_1_ instanceof EntityTitan && !((EntityTitan)p_180027_1_).getWaiting() && ((EntityTitan)p_180027_1_).getAnimID() != 11 && !(p_180027_1_ instanceof EntityGargoyleTitan) && !(p_180027_1_ instanceof EntityIronGolemTitan) && !(p_180027_1_ instanceof EntitySnowGolemTitan);
		}
	};
	protected void onHitboxUpdate()
	{
		if (ticksExisted % 5 == 0)
		{
			setSize(32F, 86F);
		}

		super.onHitboxUpdate();
	}

	public EntityIronGolemTitan(World worldIn)
	{
		super(worldIn);
		setSize(32F, 86F);
		getNavigator().setAvoidsWater(true);
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, IMob.mobSelector));
		addTitanTargetingTaskToEntity(this);
		tasks.addTask(1, new AnimationIronGolemTitanDeath(this));
		tasks.addTask(1, new AnimationIronGolemTitanRangedAttack(this));
		tasks.addTask(1, new AnimationIronGolemTitanAntiTitanAttack(this));
		tasks.addTask(1, new AnimationIronGolemTitanAttack4(this));
		tasks.addTask(1, new AnimationIronGolemTitanAttack3(this));
		tasks.addTask(1, new AnimationIronGolemTitanAttack2(this));
		tasks.addTask(1, new AnimationIronGolemTitanAttack1(this));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 100.0F;
	}

	public static void addTitanTargetingTaskToEntity(EntityCreature entity)
	{
		entity.targetTasks.addTask(0, new EntityAINearestTargetTitan(entity, EntityTitan.class, 0, false, false, attackEntitySelector));
	}

	public float getEyeHeight()
	{
		return 0.8682170542635659F * height;
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
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
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
		return TheTitans.UltimaIronGolemMinionSpawnrate;
	}

	public boolean canBeHurtByPlayer()
	{
		return !isPlayerCreated() && !isEntityInvulnerable();
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
		if (isPlayerCreated() && getAttackTarget() != null && getAttackTarget() instanceof EntityPlayer)
		setAttackTarget(null);
		isRejuvinating = false;
		if (getAnimID() == 10)
		{
			if ((getAnimTick() == 30) || (getAnimTick() == 70))
			{
				func_145780_a(0, 0, 0, Blocks.stone);
			}

			if (getAnimTick() == 190)
			{
				playSound("thetitans:titanFall", 20.0F, 0.9F);
				playSound("thetitans:groundSmash", 20.0F, 1.0F);
				shakeNearbyPlayerCameras(20D);
			}

			if (getAnimTick() == 200)
			{
				playSound("thetitans:distantLargeFall", 10000.0F, 0.5F);
			}
		}

		if (!worldObj.isRemote && getAnimID() == 5 && (getAnimTick() == 34) && (getAttackTarget() != null))
		{
			attackEntityAsMob(getAttackTarget());
			Vec3 vec3 = getLook(1F);
			double d5 = getAttackTarget().posX - (posX + vec3.xCoord * 30D);
			double d6 = getAttackTarget().posY - (posY + 30D);
			double d7 = getAttackTarget().posZ - (posZ + vec3.zCoord * 30D);
			EntityTitanFireball entitylargefireball = new EntityTitanFireball(worldObj, this, d5, d6, d7, 1);
			entitylargefireball.posX = (posX + vec3.xCoord * 30D);
			entitylargefireball.posY = (posY + 30D);
			entitylargefireball.posZ = (posZ + vec3.zCoord * 30D);
			worldObj.spawnEntityInWorld(entitylargefireball);
			entitylargefireball.setFireballID(5);
			entitylargefireball.playSound("thetitans:titanSwing", 10.0F, 1.0F);
		}

		if (deathTicks > 0)
		{
			motionX *= 0.0D;
			motionZ *= 0.0D;
		}

		if ((!AnimationAPI.isEffectiveClient()) && (getAttackTarget() != null) && (getAnimID() == 0) && ticksExisted > 5)
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 < getMeleeRange())
			{
				switch (rand.nextInt(5))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 6);
					setAnimID(6);
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
						AnimationAPI.sendAnimPacket(this, 1);
						setAnimID(1);
					}

					else
					{
						AnimationAPI.sendAnimPacket(this, 7);
						setAnimID(7);
					}

					break;
				}
			}

			else if ((getAnimID() == 0) && (getRNG().nextInt(160) == 0))
			{
				switch (rand.nextInt(2))
				{
					case 0:AnimationAPI.sendAnimPacket(this, 5);
					setAnimID(5);
					break;
					case 1:AnimationAPI.sendAnimPacket(this, 5);
					setAnimID(5);
					break;
				}
			}
		}

		if (motionY > 1.0D)motionY *= 0.8D;
		meleeTitan = true;
		List<?> list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(96D, 96D, 96D));
		if ((list1 != null) && (!list1.isEmpty()))
		{
			for (int i1 = 0; i1 < list1.size(); i1++)
			{
				Entity entity = (Entity)list1.get(i1);
				if ((entity != null) && ((entity instanceof EntityIronGolem)))
				{
					if (((EntityIronGolem)entity).isCollidedHorizontally)
					((EntityIronGolem)entity).motionY = 0.25D;
					if (((EntityIronGolem)entity).getAttackTarget() == null && ((EntityIronGolem)entity).getDistanceSqToEntity(this) > 4096D)
					{
						((EntityIronGolem)entity).getLookHelper().setLookPositionWithEntity(this, 180.0F, 40F);
						((EntityIronGolem)entity).getMoveHelper().setMoveTo(posX, posY, posZ, 1D);
					}

					if (((EntityIronGolem)entity).ticksExisted == 20)
					addTitanTargetingTaskToEntity(this);
				}
			}
		}

		if (attackTimer > 0)
		{
			attackTimer -= 1;
		}

		if (holdRoseTick > 0)
		{
			holdRoseTick -= 1;
		}
	}

	protected void updateAITasks()
	{
		if (--homeCheckTimer <= 0)
		{
			homeCheckTimer = (70 + rand.nextInt(50));
			villageObj = worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), 32);
			if (villageObj == null)
			{
				detachHome();
			}

			else
			{
				ChunkCoordinates chunkcoordinates = villageObj.getCenter();
				setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, (int)(villageObj.getVillageRadius() * 0.6F));
			}
		}

		super.updateAITasks();
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ == EntityVillager.class || p_70686_1_ == EntityIronGolem.class || p_70686_1_ == EntityIronGolemTitan.class) ? false : (isPlayerCreated() && EntityPlayer.class.isAssignableFrom(p_70686_1_) ? false : true);
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
		float f = (float)getAttackValue(3.0F);
		if ((p_70652_1_ instanceof EntityWitherzilla))
		f *= 5F;
		if ((p_70652_1_ instanceof EntityGhastTitan && p_70652_1_.posY > posY + 32D))
		--p_70652_1_.motionY;
		for (int l = 0; l < 4 + rand.nextInt(6); l++)
		attackChoosenEntity(p_70652_1_, f, getKnockbackAmount());
		return true;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		else if (source.getEntity() instanceof EntityPlayer && isPlayerCreated())
		{
			return false;
		}

		else if (source.getEntity() instanceof EntityIronGolem || source.getEntity() instanceof EntityIronGolemTitan)
		{
			return false;
		}

		else
		{
			return super.attackEntityFrom(source, amount);
		}
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte p_70103_1_)
	{
		if (p_70103_1_ == 4)
		{
			attackTimer = 10;
			playSound("mob.irongolem.throw", 100.0F, 0.5F);
		}

		else if (p_70103_1_ == 11)
		{
			holdRoseTick = 800;
		}

		else
		{
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	public Village getVillage()
	{
		return villageObj;
	}

	@SideOnly(Side.CLIENT)
	public int getAttackTimer()
	{
		return attackTimer;
	}

	public void setHoldingRose(boolean p_70851_1_)
	{
		holdRoseTick = (p_70851_1_ ? 800 : 0);
		worldObj.setEntityState(this, (byte)11);
	}

	protected String getHurtSound()
	{
		return "mob.irongolem.hit";
	}

	protected String getDeathSound()
	{
		return "mob.irongolem.death";
	}

	protected float getSoundPitch()
	{
		return isChild() ? (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F : (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F;
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
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
		shakeNearbyPlayerCameras(8D);
	}

	protected float getSoundVolume()
	{
		return 100.0F;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		for (int l = 0; l < 512 + rand.nextInt(512 + p_70628_2_); l++)
		{
			entityDropItem(new ItemStack(Blocks.iron_block), 12.0F);
		}

		for (int l = 0; l < 128 + rand.nextInt(128 + p_70628_2_); l++)
		{
			entityDropItem(new ItemStack(Blocks.red_flower), 12.0F);
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

	public int getHoldRoseTick()
	{
		return holdRoseTick;
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

	public void onDeath(DamageSource cause)
	{
		if ((!isPlayerCreated()) && (attackingPlayer != null) && (villageObj != null))
		{
			villageObj.setReputationForPlayer(attackingPlayer.getDisplayName(), -50000);
		}

		super.onDeath(cause);
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
		}

		if (getInvulTime() >= getThreashHold())
		{
			setDead();
		}
	}

	protected EntityLiving getMinion()
	{
		return new EntityIronGolem(worldObj);
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


