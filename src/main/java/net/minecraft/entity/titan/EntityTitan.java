package net.minecraft.entity.titan;
import com.google.common.collect.Lists;
import net.minecraft.theTitans.core.TheCore;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titanminion.*;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.ChunkLoadingEvent;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.theTitans.events.EventData;
import net.minecraft.theTitans.world.WorldProviderVoid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import thehippomaster.AnimationAPI.AnimationAPI;
public class EntityTitan extends EntityCreature implements ITitan, IBossDisplayData
{
	public final List<EntityTitanPart> partArray = new ArrayList<EntityTitanPart>();
	private static final IAttribute titanMaxHealth[] =
	{
		(new RangedAttribute("titan.maxHealth.a", 2000.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),
		(new RangedAttribute("titan.maxHealth.b", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),
		(new RangedAttribute("titan.maxHealth.c", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),
		(new RangedAttribute("titan.maxHealth.d", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),
		(new RangedAttribute("titan.maxHealth.e", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),
		(new RangedAttribute("titan.maxHealth.f", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),
		(new RangedAttribute("titan.maxHealth.g", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),
		(new RangedAttribute("titan.maxHealth.h", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),
		(new RangedAttribute("titan.maxHealth.i", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),
		(new RangedAttribute("titan.maxHealth.j", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true)),

	};
	
	private static final IAttribute titanHealth[] =
	{
		(new RangedAttribute("titan.health.a", 2000.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),
		(new RangedAttribute("titan.health.b", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),
		(new RangedAttribute("titan.health.c", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),
		(new RangedAttribute("titan.health.d", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),
		(new RangedAttribute("titan.health.e", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),
		(new RangedAttribute("titan.health.f", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),
		(new RangedAttribute("titan.health.g", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),
		(new RangedAttribute("titan.health.h", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),
		(new RangedAttribute("titan.health.i", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),
		(new RangedAttribute("titan.health.j", 0.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true)),

	};
	
	protected int stunTicks;
	public int deathTicks;
	public boolean isStunned;
	protected boolean isSouless;
	public boolean shouldParticlesBeUpward;
	public boolean isRejuvinating;
	public boolean isFlying;
	public boolean meleeTitan;
	protected int nextStepDistanceTitan;
	protected int numMinions;
	protected int numPriests;
	protected int numZealots;
	protected int numBishop;
	protected int numTemplar;
	protected int numLords;
	protected int numSpecialMinions;
	public int animTick;
	public int footID;
	public double movePointX;
	public double movePointZ;
	public EntityTitan(World worldIn)
	{
		super(worldIn);
		width = 1F;
		height = 6F;
		func_110163_bv();
		setHealth(getMaxHealth());
		applyEntityAI();
		isStunned = false;
		isImmuneToFire = true;
		ignoreFrustumCheck = true;
		renderDistanceWeight = 1000D;
		movePointX = posX;
		movePointZ = posZ;
		isSouless = false;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(Double.MAX_VALUE);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(512D);
		for (int i = 0; i < 10; i++)
		{
			getAttributeMap().registerAttribute(titanMaxHealth[i]);
			getAttributeMap().registerAttribute(titanHealth[i]);
		}
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(18, new Integer(0));
		dataWatcher.addObject(19, new Integer(0));
		dataWatcher.addObject(20, new Integer(0));
		dataWatcher.addObject(21, new Integer(0));
		dataWatcher.addObject(22, new Integer(0));
		dataWatcher.addObject(23, new Byte((byte)0));
		dataWatcher.addObject(24, new Integer(0));
		dataWatcher.addObject(25, new Integer(0));
		dataWatcher.addObject(26, new Float(20.0F));
		dataWatcher.addObject(27, new Float(20.0F));
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		setAnimID(tagCompund.getInteger("AnimationId"));
		setAnimTick(tagCompund.getInteger("AnimationTick"));
		setTitanVariant(tagCompund.getInteger("Variant"));
		setATAAID(tagCompund.getInteger("ATAAID"));
		setExtraPower(tagCompund.getInteger("ExtraPower"));
		setInvulTime(tagCompund.getInteger("Invul"));
		setWaiting(tagCompund.getBoolean("Waiting"));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("AnimationId", getAnimID());
		tagCompound.setInteger("AnimationTick", getAnimTick());
		tagCompound.setInteger("Variant", getTitanVariant());
		tagCompound.setInteger("ATAAID", getATAAID());
		tagCompound.setInteger("ExtraPower", getExtraPower());
		tagCompound.setInteger("Invul", getInvulTime());
		tagCompound.setBoolean("Waiting", getWaiting());
	}

	public void onUpdate()
	{
		worldObj.theProfiler.startSection("onTitanUpdateLiving");
		super.onUpdate();
		
		if (getTitanMaxHealth() / 4 > getMaxStamina())
		{
			setMaxStamina(getTitanMaxHealth() / 4);
			setStamina(getTitanMaxHealth() / 4);
		}

		if (!isStunned && getStamina() < getMaxStamina())
			setStamina(getStamina() + (getMaxStamina() / 6000));
		
		if (getTitanHealth() <= 0F)
			setStamina(0.0F);
		
		if (isServer() && !getWaiting() && getAnimID() != 0 && deathTicks < getThreashHold())
		{
			setAnimTick(getAnimTick() + 1);
			
			if (isChild() && isEntityAlive())
				setAnimTick(getAnimTick() + 1);
		}

		if (isStunned && maxHurtResistantTime != 5)
			maxHurtResistantTime = 5;
		else if (!isStunned && maxHurtResistantTime != 25)
			maxHurtResistantTime = 25;
		
		if (onGround && isFlying)
			isFlying = false;
		
		if (!worldObj.isRemote && !getWaiting())
			ChunkLoadingEvent.updateLoaded(this);
		
		worldObj.theProfiler.endSection();
	}

	protected void onStunUpdate()
	{
		stunTicks ++;
		
		if (stunTicks >= recoveryTime())
		{
			stunTicks = 0;
			isStunned = false;
			setStamina(getMaxStamina());
		}
	}

	public void onLivingUpdate()
	{
		onAnimationUpdate();
		onHitboxUpdate();
		onMinionUpdate();
		
		if (isStunned)
			onStunUpdate();
		
		if (getStamina() <= 0)
			isStunned = true;
		
		if (getTitanMaxHealthD() != getHealthValue())
		{
			setTitanMaxHealth(getHealthValue());
			setTitanHealth(getHealthValue());
		}

		if (getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue() != getAttackValue(1.0F))
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(getAttackValue(1.0F));
		if (getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue() != getTitanMaxHealth())
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getTitanMaxHealth());
		if (getEntityAttribute(SharedMonsterAttributes.followRange).getBaseValue() != (16D * getTitanSizeMultiplier()))
			getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16D * getTitanSizeMultiplier());
		
		if (getAttackTarget() != null && getAttackTarget() instanceof EntityLiving && isOreSpawnBossToExempt(getAttackTarget()))
		{
			((EntityLiving)getAttackTarget()).setAttackTarget(this);
			((EntityLiving)getAttackTarget()).getLookHelper().setLookPositionWithEntity(this, 180F, 180F);
		}

		isAirBorne = !onGround;
		
		if (motionX > 3D)
			motionX = 3D;
		if (motionZ > 3D)
			motionZ = 3D;
		if (motionX < -3D)
			motionX = -3D;
		if (motionZ < -3D)
			motionZ = -3D;
		
		Calendar calendar = worldObj.getCurrentDate();
		if (!isDead && calendar.get(2) + 1 == 3 && calendar.get(5) == 1)
		{
			motionY += rand.nextDouble();
			noClip = true;
			rotationYawHead += 20F;
			rotationPitch += 10F;
		}

		if (!getWaiting() && isServer() && worldObj.isDaytime() && !isRejuvinating && (!(this instanceof EntitySlimeTitan) && !(this instanceof EntitySnowGolemTitan) && !(this instanceof EntityIronGolemTitan) && !(this instanceof EntityGargoyleTitan)) && ((calendar.get(2) + 1 == 10 && calendar.get(5) >= 1 && calendar.get(5) <= 31)))
			worldObj.setWorldTime(worldObj.getWorldTime() + 50);
		
		if (getAttackTarget() != null)
		{
			if((!getAttackTarget().isEntityAlive() || getAttackTarget().posY > 256 || getAttackTarget().posY < -45))
			setAttackTarget(null);
			if (TitanConfig.isAntiCheat && getAttackTarget() instanceof EntityLiving && !(getAttackTarget() instanceof EntityTitanSpirit) && !(getAttackTarget() instanceof EntityTitan) && getAttackTarget().getMaxHealth() > 1000000000F)
			{
				if (isClient())
				getAttackTarget().playSound("random.explode", 2F, 1F + rand.nextFloat());
				damageBypassEntity(getAttackTarget(), new DamageSource("infinity").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute(), getAttackTarget().getHealth() / 2F);
				if (getAttackTarget().getHealth() <= 1F)
				{
					List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(getAttackTarget(), getAttackTarget().boundingBox.expand(7, 7, 7));
					if ((list11 != null) && (!list11.isEmpty()))
					{
						for (int i1 = 0; i1 < list11.size(); i1++)
						{
							Entity entity = (Entity)list11.get(i1);
							if (entity instanceof EntityLivingBase && isClient())
							((EntityLivingBase)entity).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, Integer.MAX_VALUE, 9));
						}
					}

					worldObj.createExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 14F, false);
					getAttackTarget().setDead();
				}
			}

			if (TitanConfig.isAntiCheat && isServer() && this != null && getAttackTarget() instanceof EntityPlayer)
			if (((EntityPlayer)getAttackTarget()).capabilities.disableDamage && !((EntityPlayer)getAttackTarget()).capabilities.isCreativeMode)
				MinecraftServer.getServer().getConfigurationManager().func_152612_a(((EntityPlayer)getAttackTarget()).getCommandSenderName()).playerNetServerHandler.kickPlayerFromServer(getCommandSenderName() + " has detected you not taking normal damage out of creative, and kicked you for it.");
		}

		if (ridingEntity != null)
		renderYawOffset = rotationYaw = ridingEntity.rotationYaw;
		if (getAnimID() == 0) setAnimTick(0);
		else renderYawOffset = rotationYaw = rotationYawHead;
		if (getWaiting())
		{
			motionX = 0D;
			motionZ = 0D;
			if (motionY > 0D)
				motionY = 0D;
		}

		if (isServer() && getAnimID() == 0 && getAttackTarget() != null && shouldMove() && !(this instanceof EntityGhastTitan))
			moveTitanToPoint(getAttackTarget().posX, getAttackTarget().posZ, false);

		if (deathTime == 1)
			playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
		
		if (getTitanHealth() <= 0.0F)
			onTitanDeathUpdate();

		if (TheTitans.TitansFFAMode && ridingEntity != null)
			ridingEntity = null;
		
		if (getAnimID() < 0)
			setAnimID(0);
		
		if (getTitanHealth() > 0.0F)
		{
			dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(getTitanHealth(), 0.0F, getTitanMaxHealth())));
			deathTicks = 0;
			
			if (getAnimID() == 10)
				setAnimID(0);
		}

		if (posY > 300.0D)
		{
			motionY = 0F;
			setPosition(posX + (rand.nextFloat() * 32.0F - 16.0F), 60.0D, posZ + (rand.nextFloat() * 32.0F - 16.0F));
		}

		if (posY <= 0.0D)
		{
			setPosition(posX, 0.0D, posZ);
			onGround = true;
			isAirBorne = false;
			fallDistance = 0F;
			
			if (motionY < 0D)
				motionY = 0D;
		}

		if (numMinions < 0) numMinions = 0;
		if (numPriests < 0) numPriests = 0;
		if (numZealots < 0) numZealots = 0;
		if (numTemplar < 0) numTemplar = 0;
		if (numSpecialMinions < 0) numSpecialMinions = 0;
		if ((getHealth() <= 0.0F) && (motionY > 0.0D)) motionY = 0.0D;
		if (getAnimTick() < 0) setAnimTick(0);
		
		isInWeb = false;
		lastDamage = 2.14748365E9F;
		stepHeight = (height / 2.0F);
		updateArmSwingProgress();
		ignoreFrustumCheck = true;
		
		if (getAttackTarget() != null && getAnimID() == 0)
			getLookHelper().setLookPositionWithEntity(getAttackTarget(), 64F / getTitanSizeMultiplier(), getVerticalFaceSpeed());
		
		for (int u = 0; u < 30; u++)
			if (ticksExisted % 10 == 0 && (motionX != 0.0D) && (motionZ != 0.0D) && (onGround))
			{
				int i = MathHelper.floor_double(posX + (rand.nextDouble() * width - width / 2.0F));
				int j = MathHelper.floor_double(posY - 0.20000000298023224D);
				int k = MathHelper.floor_double(posZ + (rand.nextDouble() * width - width / 2.0F));
				Block block = worldObj.getBlock(i, j, k);
				if (block.getMaterial() != Material.air)
				worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + worldObj.getBlockMetadata(i, j, k), posX + (rand.nextFloat() - 0.5D) * width, boundingBox.minY + 0.1D, posZ + (rand.nextFloat() - 0.5D) * width, 4.0D * (rand.nextFloat() - 0.5D), 0.5D, (rand.nextFloat() - 0.5D) * 4.0D);
			}

		super.setHealth(getTitanHealth());
		super.onLivingUpdate();
		if (getAttackTarget() != null && getAnimID() == 0 && (ticksExisted % 30 == 0) && (canAttack()))
		{
			double d0 = getDistanceToEntity(getAttackTarget());
			if (d0 < getMeleeRange())
			{
				swingItem();
				getLookHelper().setLookPositionWithEntity(getAttackTarget(), 5.0F, getVerticalFaceSpeed());
				renderYawOffset = rotationYaw = rotationYawHead;
				attackEntityAsMob(getAttackTarget());
			}
		}

		if ((getAttackTarget() != null && getAttackTarget() == ridingEntity) || ((getAttackTarget() != null) && (getAttackTarget() == riddenByEntity)))
			setAttackTarget(null);
		
		if (isClient() && ticksExisted % 2 == 0 && (deathTicks < getThreashHold()) && (!(this instanceof EntityWitherzilla)))
		{
			for (int i = 0; i < getParticleCount(); i++)
			{
				if (shouldParticlesBeUpward)
					worldObj.spawnParticle(getParticles(), posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.25D, 0.0D);
				else
					worldObj.spawnParticle(getParticles(), posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
			}

			if (TheTitans.isDifficulty(worldObj, TheCore.IMPOSSIBLE) && ticksExisted % 2 == 0 && (rand.nextInt(5) == 0))
				for (int i = 0; i < 5; i++)
					worldObj.spawnParticle("largeexplode", posX + (rand.nextDouble() - 0.5D) * width, posY, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
			if (TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) && ticksExisted % 2 == 0 && (rand.nextInt(20) == 0))
				for (int i = 0; i < 5; i++)
				{
					worldObj.spawnParticle("largeexplode", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
					worldObj.spawnParticle("flame", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
					worldObj.spawnParticle("flame", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
					worldObj.spawnParticle("lava", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
					worldObj.spawnParticle("largesmoke", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
					worldObj.spawnParticle("smoke", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
				}
		}
	}

	protected void onDeathUpdate() {}


	@SuppressWarnings("unchecked")
	protected void onTitanDeathUpdate()
	{
		if (this instanceof EntitySnowGolemTitan || this instanceof EntitySlimeTitan)
		{
			++deathTime;
			destroyBlocksInAABB(boundingBox);
			if (deathTime == 20)
			{
				setDead();
				for (int i = 0; i < 20; ++i)
				{
					double d2 = rand.nextGaussian() * 0.02D;
					double d0 = rand.nextGaussian() * 0.02D;
					double d1 = rand.nextGaussian() * 0.02D;
					worldObj.spawnParticle("largeexplode", posX + (double)(rand.nextFloat() * width * 2.0F) - (double)width, posY + (double)(rand.nextFloat() * height), posZ + (double)(rand.nextFloat() * width * 2.0F) - (double)width, d2, d0, d1);
				}
			}
		}

		dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(getTitanHealth(), 0.0F, getTitanMaxHealth())));
		if (getTitanHealth() <= 0F)
		++deathTicks;
		else
		deathTicks = 0;
		if ((deathTicks == 1) && (isServer()))
		{
			if (isClient())
			playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
			ArrayList<?> listp = Lists.newArrayList(worldObj.playerEntities);
			if ((listp != null) && (!listp.isEmpty()))
			{
				for (int i1 = 0; i1 < listp.size(); i1++)
				{
					Entity entity = (Entity)listp.get(i1);
					if ((entity != null) && ((entity instanceof EntityPlayer)))
					((EntityPlayer)entity).triggerAchievement(getAchievement());
				}
			}
		}

		motionX *= 0.0D;
		motionZ *= 0.0D;
		if (!(this instanceof EntitySnowGolemTitan) && !(this instanceof EntitySlimeTitan))
		renderYawOffset = rotationYaw = rotationYawHead += 10F;
		rotationPitch = (0F + (getInvulTime() / 4.8F)) + ((0.01F + 0.01F * MathHelper.cos(ticksExisted * 0.25F)) * 3.1415927F);
		setAttackTarget(null);
		if (isClient())
		{
			performHurtAnimation();
			spawnExplosionParticle();
		}

		if ((deathTicks > 200) && isServer())
		setInvulTime(getInvulTime() + 10);
		if (getInvulTime() >= getThreashHold())
		setDead();
	}

	protected void onMinionUpdate()
	{
		if (isServer() && getMinion() != null && !getWaiting() && getAnimID() != 13 && !(worldObj.provider instanceof WorldProviderVoid) && (TitanConfig.minionLimit < 0 || numMinions + numPriests + numZealots + numBishop + numTemplar < TitanConfig.minionLimit) && getHealth() > 0.0F && TheTitans.getDifficulty(worldObj) != 0)
		{
			if (numSpecialMinions < getSpecialMinionCap() && rand.nextInt(100) == 0)
			spawnMinion(getMinion(), 6);
			else
			{
				if (numMinions < getMinionCap() && (rand.nextInt(getMinionSpawnRate()) == 0 || getInvulTime() > 1))
				spawnMinion(getMinion());
				if (numPriests < getPriestCap() && (rand.nextInt(getMinionSpawnRate() * 2) == 0 || getInvulTime() > 1))
				spawnMinion(getMinion(), 1);
				if (numZealots < getZealotCap() && (rand.nextInt(getMinionSpawnRate() * 4) == 0 || getInvulTime() > 1))
				spawnMinion(getMinion(), 2);
				if (numBishop < getBishopCap() && (rand.nextInt(getMinionSpawnRate() * 8) == 0 || getInvulTime() > 1))
				spawnMinion(getMinion(), 3);
				if (numTemplar < getTemplarCap() && (rand.nextInt(getMinionSpawnRate() * 16) == 0 || getInvulTime() > 1))
				spawnMinion(getMinion(), 4);
			}
		}
	}

	protected void onHitboxUpdate()
	{
		if (isEntityAlive() && ticksExisted > 5)
		{
			collideWithProjectiles(boundingBox);
			onCrushingEntity(boundingBox);
			
			for(EntityTitanPart part : partArray)
			{
				collideWithProjectiles(part.boundingBox);
				collideWithEntities(part, worldObj.getEntitiesWithinAABBExcludingEntity(this, part.boundingBox.expand(1.0D, 0.0D, 1.0D)));
				
				if (ticksExisted % 10 == 0 && motionX != 0 && motionY != 0 && motionZ != 0)
					destroyBlocksInAABB(part.boundingBox);
				
				if (part.shouldCrush())
					onCrushingEntity(part.boundingBox);
			}
		}
	}

	protected void onAnimationUpdate()
	{
	}

	protected void updateAITasks()
	{
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
		if (list != null && !list.isEmpty())
		for (int i1 = 0; i1 < list.size(); i1++)
		applyEntityCollision((Entity)list.get(i1));
		if ((rand.nextInt(1000) == 0 && getHealth() < getMaxHealth() / 20.0F && deathTicks <= 0) || (getHealth() < getMaxHealth() / 2.0F && deathTicks <= 0 && getAttackTarget() != null && getAttackTarget() instanceof EntityTitan && !isRejuvinating && ((EntityTitan)getAttackTarget()).isRejuvinating) && ((!(this instanceof EntitySlimeTitan) && !(this instanceof EntitySnowGolemTitan) && !(this instanceof EntityIronGolemTitan) && !(this instanceof EntityGargoyleTitan))))
		{
			isRejuvinating = true;
			worldObj.playBroadcastSound(1013, (int)posX, (int)posY, (int)posZ, 0);
			setExtraPower(getExtraPower() + 1);
			jump();
		}

		if (isRejuvinating)
		{
			setInvulTime(getInvulTime() + 5);
			if (isClient())
			performHurtAnimation();
			if (getInvulTime() > getThreashHold())
			isRejuvinating = false;
		}

		if (getInvulTime() > 0)
		{
			motionX *= 0.0D;
			motionZ *= 0.0D;
			int i = getInvulTime() - 1;
			if (i <= 0)
			{
				if (!(this instanceof EntitySnowGolemTitan) && !(this instanceof EntityIronGolemTitan) && !(this instanceof EntityGargoyleTitan))
				{
					worldObj.newExplosion(this, posX, posY + height / 2.0F, posZ, height, false, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					worldObj.playBroadcastSound(1013, (int)posX, (int)posY, (int)posZ, 0);
					destroyBlocksInAABB(boundingBox);
				}

				if (this instanceof EntityZombieTitan && !((EntityZombieTitan)this).isArmed())
				AnimationAPI.sendAnimPacket((EntityZombieTitan)this, 2);
			}

			setInvulTime(i);
			if (ticksExisted % 5 == 0)
			heal(getTitanMaxHealth() / 500.0F);
		}

		else
		{
			if (getRegen() > 0.0D && hurtResistantTime <= 10)
			heal(getRegen());
			super.updateAITasks();
		}
	}

	protected void onCrushingEntity(AxisAlignedBB boundingBox)
	{
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
		if ((list != null) && (!list.isEmpty()))
		for (int i = 0; i < list.size(); i++)
		{
			Entity entity = (Entity)list.get(i);
			if ((entity instanceof EntityLivingBase) && entity.onGround && !(entity instanceof EntityTitan))
				entity.attackEntityFrom(DamageSourceExtra.causeSquishingDamage(this), (float)getAttackValue(1.0F));
		}
	}

	public Entity[] getArray()
	{
		return (Entity[]) partArray.toArray();
	}

	public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_)
	{
		if (noClip)
		{
			boundingBox.offset(p_70091_1_, p_70091_3_, p_70091_5_);
			posX = (boundingBox.minX + boundingBox.maxX) / 2.0D;
			posY = boundingBox.minY + (double)yOffset - (double)ySize;
			posZ = (boundingBox.minZ + boundingBox.maxZ) / 2.0D;
		}

		else
		{
			worldObj.theProfiler.startSection("move");
			ySize *= 0.4F;
			double d3 = posX;
			double d4 = posY;
			double d5 = posZ;
			double d6 = p_70091_1_;
			double d7 = p_70091_3_;
			double d8 = p_70091_5_;
			AxisAlignedBB axisalignedbb = boundingBox.copy();
			List<?> list = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(p_70091_1_, p_70091_3_, p_70091_5_));
			for (int i = 0; i < list.size(); ++i)
			{
				p_70091_3_ = ((AxisAlignedBB)list.get(i)).calculateYOffset(boundingBox, p_70091_3_);
			}

			boundingBox.offset(0.0D, p_70091_3_, 0.0D);
			if (!field_70135_K && d7 != p_70091_3_)
			{
				p_70091_5_ = 0.0D;
				p_70091_3_ = 0.0D;
				p_70091_1_ = 0.0D;
			}

			boolean flag1 = onGround || d7 != p_70091_3_ && d7 < 0.0D;
			int j;
			for (j = 0; j < list.size(); ++j)
			{
				p_70091_1_ = ((AxisAlignedBB)list.get(j)).calculateXOffset(boundingBox, p_70091_1_);
			}

			boundingBox.offset(p_70091_1_, 0.0D, 0.0D);
			if (!field_70135_K && d6 != p_70091_1_)
			{
				p_70091_5_ = 0.0D;
				p_70091_3_ = 0.0D;
				p_70091_1_ = 0.0D;
			}

			for (j = 0; j < list.size(); ++j)
			{
				p_70091_5_ = ((AxisAlignedBB)list.get(j)).calculateZOffset(boundingBox, p_70091_5_);
			}

			boundingBox.offset(0.0D, 0.0D, p_70091_5_);
			if (!field_70135_K && d8 != p_70091_5_)
			{
				p_70091_5_ = 0.0D;
				p_70091_3_ = 0.0D;
				p_70091_1_ = 0.0D;
			}

			double d10;
			double d11;
			int k;
			double d12;
			if (stepHeight > 0.0F && flag1 && ySize < 0.05F && (d6 != p_70091_1_ || d8 != p_70091_5_))
			{
				d12 = p_70091_1_;
				d10 = p_70091_3_;
				d11 = p_70091_5_;
				p_70091_1_ = d6;
				p_70091_3_ = (double)stepHeight;
				p_70091_5_ = d8;
				AxisAlignedBB axisalignedbb1 = boundingBox.copy();
				boundingBox.setBB(axisalignedbb);
				list = worldObj.getCollidingBoundingBoxes(this, boundingBox.addCoord(d6, p_70091_3_, d8));
				for (k = 0; k < list.size(); ++k)
				{
					p_70091_3_ = ((AxisAlignedBB)list.get(k)).calculateYOffset(boundingBox, p_70091_3_);
				}

				boundingBox.offset(0.0D, p_70091_3_, 0.0D);
				if (!field_70135_K && d7 != p_70091_3_)
				{
					p_70091_5_ = 0.0D;
					p_70091_3_ = 0.0D;
					p_70091_1_ = 0.0D;
				}

				for (k = 0; k < list.size(); ++k)
				{
					p_70091_1_ = ((AxisAlignedBB)list.get(k)).calculateXOffset(boundingBox, p_70091_1_);
				}

				boundingBox.offset(p_70091_1_, 0.0D, 0.0D);
				if (!field_70135_K && d6 != p_70091_1_)
				{
					p_70091_5_ = 0.0D;
					p_70091_3_ = 0.0D;
					p_70091_1_ = 0.0D;
				}

				for (k = 0; k < list.size(); ++k)
				{
					p_70091_5_ = ((AxisAlignedBB)list.get(k)).calculateZOffset(boundingBox, p_70091_5_);
				}

				boundingBox.offset(0.0D, 0.0D, p_70091_5_);
				if (!field_70135_K && d8 != p_70091_5_)
				{
					p_70091_5_ = 0.0D;
					p_70091_3_ = 0.0D;
					p_70091_1_ = 0.0D;
				}

				if (!field_70135_K && d7 != p_70091_3_)
				{
					p_70091_5_ = 0.0D;
					p_70091_3_ = 0.0D;
					p_70091_1_ = 0.0D;
				}

				else
				{
					p_70091_3_ = (double)(-stepHeight);
					for (k = 0; k < list.size(); ++k)
					{
						p_70091_3_ = ((AxisAlignedBB)list.get(k)).calculateYOffset(boundingBox, p_70091_3_);
					}

					boundingBox.offset(0.0D, p_70091_3_, 0.0D);
				}

				if (d12 * d12 + d11 * d11 >= p_70091_1_ * p_70091_1_ + p_70091_5_ * p_70091_5_)
				{
					p_70091_1_ = d12;
					p_70091_3_ = d10;
					p_70091_5_ = d11;
					boundingBox.setBB(axisalignedbb1);
				}
			}

			worldObj.theProfiler.endSection();
			worldObj.theProfiler.startSection("rest");
			posX = (boundingBox.minX + boundingBox.maxX) / 2.0D;
			posY = boundingBox.minY + (double)yOffset - (double)ySize;
			posZ = (boundingBox.minZ + boundingBox.maxZ) / 2.0D;
			isCollidedHorizontally = d6 != p_70091_1_ || d8 != p_70091_5_;
			isCollidedVertically = d7 != p_70091_3_;
			onGround = (d7 != p_70091_3_ && d7 < 0.0D) || posY <= 0D;
			isCollided = isCollidedHorizontally || isCollidedVertically;
			updateFallState(p_70091_3_, onGround);
			if (d6 != p_70091_1_)
			{
				motionX = 0.0D;
			}

			if (d7 != p_70091_3_)
			{
				motionY = 0.0D;
			}

			if (d8 != p_70091_5_)
			{
				motionZ = 0.0D;
			}

			d12 = posX - d3;
			d10 = posY - d4;
			d11 = posZ - d5;
			if (ridingEntity == null && onGround)
			{
				int j1 = MathHelper.floor_double(posX);
				k = MathHelper.floor_double(posY - 0.5D - (double)yOffset);
				int l = MathHelper.floor_double(posZ);
				Block block = worldObj.getBlock(j1, k, l);
				int i1 = worldObj.getBlock(j1, k - 1, l).getRenderType();
				if (i1 == 11 || i1 == 32 || i1 == 21)
				{
					block = worldObj.getBlock(j1, k - 1, l);
				}

				if (block != Blocks.ladder)
				{
					d10 = 0.0D;
				}

				distanceWalkedModified = (float)((double)distanceWalkedModified + (double)MathHelper.sqrt_double(d12 * d12 + d11 * d11) * 0.4D);
				distanceWalkedOnStepModified = (float)((double)distanceWalkedOnStepModified + (double)MathHelper.sqrt_double(d12 * d12 + d10 * d10 + d11 * d11) * 0.4D);
				if (distanceWalkedOnStepModified > (float)nextStepDistanceTitan)
				{
					nextStepDistanceTitan = (int)distanceWalkedOnStepModified + getFootStepModifer();
					func_145780_a(j1, k, l, block);
					block.onEntityWalking(worldObj, j1, k, l, this);
				}
			}

			else if (ridingEntity != null || !onGround)
			{
				nextStepDistanceTitan = getFootStepModifer();
				distanceWalkedModified = 0F;
				distanceWalkedOnStepModified = 0F;
				footID = 0;
			}

			try
			{
				func_145775_I();
			}

			catch (Throwable throwable)
			{
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
				CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
				addEntityCrashInfo(crashreportcategory);
				throw new ReportedException(crashreport);
			}

			worldObj.theProfiler.endSection();
		}
	}

	public int getFootStepModifer()
	{
		return 1 + ((int)getTitanSizeMultiplier() / 8);
	}

	/**
	* Returns the distance to the entity. Args: entity
	*/
	public float getDistanceToEntity(Entity p_70032_1_)
	{
		float f = (float)(posX - p_70032_1_.posX);
		float f1 = (float)((posY + (rand.nextFloat() * height)) - p_70032_1_.posY);
		float f2 = (float)(posZ - p_70032_1_.posZ);
		return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
	}

	protected void collideWithNearbyEntities()
	{
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.1D, 0.1D, 0.1D));
		if (list != null && !list.isEmpty() && !getWaiting() && getAnimID() != 13)
		{
			for (int i = 0; i < list.size(); ++i)
			{
				Entity entity = (Entity)list.get(i);
				if (isEntityAlive() && entity.isEntityAlive() && (entity.canBePushed() || entity instanceof EntityTitan || (entity instanceof EntityThrowable && ((EntityThrowable)entity).getThrower() != this) || (entity instanceof EntityFireball && ((EntityFireball)entity).shootingEntity != this)) && entity.height > 6F && !(entity instanceof EntityPlayer))
				{
					collideWithEntity(entity);
				}
			}
		}
	}

	public void applyEntityCollision(Entity p_70108_1_)
	{
		if (canAttackClass(p_70108_1_.getClass()) && !getWaiting() && getAnimID() != 13 && !(p_70108_1_ instanceof EntitySkeletonTitanGiantArrow) && !(p_70108_1_ instanceof EntityHarcadiumArrow) && !(p_70108_1_ instanceof EntityFireball) && !(p_70108_1_ instanceof EntityThrowable) && !(p_70108_1_ instanceof EntityTitanPart) && isEntityAlive() && p_70108_1_.riddenByEntity != this && p_70108_1_.ridingEntity != this)
		{
			double d0 = p_70108_1_.posX - posX;
			double d1 = p_70108_1_.posZ - posZ;
			double d2 = MathHelper.abs_max(d0, d1);
			if (d2 >= 0.009999999776482582D)
			{
				d2 = (double)MathHelper.sqrt_double(d2);
				d0 /= d2;
				d1 /= d2;
				double d3 = 1.0D / d2;
				if (d3 > 1.0D)
				{
					d3 = 1.0D;
				}

				d0 *= d3;
				d1 *= d3;
				d0 *= 0.25D;
				d1 *= 0.25D;
				d0 *= (double)(1.0F - entityCollisionReduction);
				d1 *= (double)(1.0F - entityCollisionReduction);
				if (p_70108_1_.height >= 6F || p_70108_1_ instanceof EntityTitan)
				addTitanVelocity(-d0, 0.0D, -d1);
				d0 *= 4D;
				d1 *= 4D;
				p_70108_1_.addVelocity(d0, 0.75D, d1);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0F;
	}

	public boolean isEntityAlive()
	{
		return getTitanHealth() > 0.0F;
	}

	public void setHealth(float p_70606_1_)
	{
		dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(getTitanHealth(), 0.0F, getTitanMaxHealth())));
	}

	public Vec3 getLook(float p_70676_1_)
	{
		float f1;
		float f2;
		float f3;
		float f4;
		if (p_70676_1_ == 1.0F)
		{
			f1 = MathHelper.cos(-rotationYawHead * 0.017453292F - (float)Math.PI);
			f2 = MathHelper.sin(-rotationYawHead * 0.017453292F - (float)Math.PI);
			f3 = -MathHelper.cos(-rotationPitch * 0.017453292F);
			f4 = MathHelper.sin(-rotationPitch * 0.017453292F);
			return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
		}

		else
		{
			f1 = prevRotationPitch + (rotationPitch - prevRotationPitch) * p_70676_1_;
			f2 = prevRotationYawHead + (rotationYawHead - prevRotationYawHead) * p_70676_1_;
			f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
			f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
			float f5 = -MathHelper.cos(-f1 * 0.017453292F);
			float f6 = MathHelper.sin(-f1 * 0.017453292F);
			return Vec3.createVectorHelper((double)(f4 * f5), (double)f6, (double)(f3 * f5));
		}
	}

	public void heal(float p_70691_1_)
	{
		//float f1 = getHealth();
		//if (f1 > 0.0F && getTitanHealth() > 0F)
		//
		{

			//setTitanHealth(f1 + p_70691_1_);
			//
		}


		heal((double)p_70691_1_);
	}

	protected void damageEntity(DamageSource source, float damage)
	{
		if (!isEntityInvulnerable())
		{
			damage = ForgeHooks.onLivingHurt(this, source, damage);
			if (damage <= 500.0F)
			return;
			if (source.getEntity() instanceof EntityPlayer && !canBeHurtByPlayer() && source.damageType != "playerwithgodpower")
			damage *= 0.25F;
			if (isArmored())
			damage *= 0.25F;
			damage = applyArmorCalculations(source, damage);
			damage = applyPotionDamageCalculations(source, damage);
			if (damage > (float)cap())
			damage = (float)cap();
			if (damage > 0.0F)
			{
				hurt(damage);
				func_110142_aN().func_94547_a(source, getTitanHealth(), damage);
				setAbsorptionAmount(getAbsorptionAmount() - damage);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte p_70103_1_)
	{
		if (p_70103_1_ == 2)
		{
			hurtResistantTime = maxHurtResistantTime;
			hurtTime = (maxHurtTime = 10);
			attackedAtYaw = 0.0F;
			playSound(getHurtSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			attackEntityFrom(DamageSource.generic, 0.0F);
		}
	}

	@SuppressWarnings("unchecked")
	public void setDead()
	{
		if (isClient())
		isDead = true;
		if (deathTicks > 0 || this instanceof EntitySlimeTitan || getTitanHealth() <= 0F)
		{
			inactDeathAction();
			ArrayList<?> listp = Lists.newArrayList(worldObj.playerEntities);
			if ((listp != null) && (!listp.isEmpty()) && isServer() && !(this instanceof EntitySlimeTitan) && !(this instanceof EntityGargoyleTitan) && !(this instanceof EntityIronGolemTitan) && !(this instanceof EntitySnowGolemTitan))
			{
				for (int i1 = 0; i1 < listp.size(); i1++)
				{
					EntityPlayer player = (EntityPlayer)listp.get(i1);
					if (player != null)
					{
						playLivingSound();
						player.addChatMessage(new ChatComponentText(getCommandSenderName() + ": I will return, " + player.getCommandSenderName()));
					}
				}
			}

			super.setDead();
		}
	}

	protected void inactDeathAction()
	{
		worldObj.newExplosion(this, posX, posY + 3.0D, posZ, 0.0F, false, false);
		if (isServer() && worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
		{
			dropFewItems(true, 0);
			dropEquipment(true, 0);
			dropRareDrop(1);
		}
	}

	public void func_82206_m()
	{
		setInvulTime(getTitanStatus() == EnumTitanStatus.GOD ? 7100 : (getTitanStatus() == EnumTitanStatus.GREATER ? 1310 : ((this instanceof EntitySnowGolemTitan || this instanceof EntitySlimeTitan) ? 150 : 850)));
		setWaiting(false);
	}

	protected void applyEntityAI() 
	{

	}


	public boolean isAIEnabled()
	{
		return true;
	}

	public void setAttackTarget(EntityLivingBase p_70624_1_)
	{
		if (p_70624_1_ != null && !canAttackClass(p_70624_1_.getClass()))
		p_70624_1_ = null;
		if (p_70624_1_ != null && p_70624_1_ != EntityList.createEntityByName("MobzillaHead", worldObj) && p_70624_1_ != EntityList.createEntityByName("KingHead", worldObj) && p_70624_1_ != EntityList.createEntityByName("QueenHead", worldObj) && !getWaiting() && getAnimID() != 13 && p_70624_1_ != null && p_70624_1_.posY < 256D && p_70624_1_.isEntityAlive() && p_70624_1_ != ridingEntity && p_70624_1_ != riddenByEntity)
		{
			super.setAttackTarget(p_70624_1_);
			if (isServer() && p_70624_1_ instanceof EntityPlayer && p_70624_1_.ticksExisted > 100 && !((EntityPlayer)p_70624_1_).inventory.hasItem(TitanItems.ultimaBlade) && ((EntityPlayer)p_70624_1_).isEntityAlive() && ((EntityPlayer)p_70624_1_).hurtResistantTime <= 10)
			{
				if (worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
				MinecraftServer.getServer().func_147139_a(EnumDifficulty.EASY);
				if (!((EntityPlayer)p_70624_1_).attackEntityFrom((new DamageSource("other")).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode(), 1F))
				{
					((EntityPlayer)p_70624_1_).inventory.dropAllItems();
					((EntityPlayer)p_70624_1_).setHealth(0F);
					MinecraftServer.getServer().getConfigurationManager().func_152612_a(((EntityPlayer)p_70624_1_).getCommandSenderName()).playerNetServerHandler.kickPlayerFromServer(getCommandSenderName() + " has kicked you for cheating.");
				}
			}
		}

		else if (p_70624_1_ instanceof EntityTitan && (worldObj.provider instanceof WorldProviderVoid || ((EntityTitan)p_70624_1_).getInvulTime() > 0 || ((EntityTitan)p_70624_1_).getWaiting() || ((EntityTitan)p_70624_1_).getAnimID() == 13) && !(p_70624_1_ instanceof EntityWitherzilla))
		super.setAttackTarget(null);
		else
		super.setAttackTarget(null);
	}

	protected void titanFly(float width, float height, double yoffset)
	{
		if (getAttackTarget() != null && getDistanceToEntity(getAttackTarget()) > getMeleeRange() + (!getAttackTarget().onGround ? 64D : 150D) + (meleeTitan ? 0D : 100D))
		{
			if (posY <= getAttackTarget().posY + 12D && posY < 256D - height)
			{
				motionY += 0.9D - motionY;
				if (motionY < 0.0D)
				motionY = 0.0D;
			}

			motionY *= 0.6D;
		}

		if (!onGround)
		{
			fallDistance *= 0F;
			isFlying = true;
			float f = (rand.nextFloat() - 0.5F) * width;
			float f1 = (rand.nextFloat() - 0.5F) * height;
			float f2 = (rand.nextFloat() - 0.5F) * width;
			worldObj.spawnParticle("hugeexplosion", posX + (double)f, posY + yoffset + (double)f1, posZ + (double)f2, 0.0D, 0.0D, 0.0D);
		}
	}

	protected void jumpAtEntity(EntityLivingBase e)
	{
		motionY += 1.25D;
		posY += 1.5499999523162842D;
		double d1 = e.posX - posX;
		double d2 = e.posZ - posZ;
		float d = (float)Math.atan2(d2, d1);
		float f2 = (float)(d * 180.0D / 3.141592653589793D) - 90.0F;
		rotationYaw = f2;
		d1 = Math.sqrt(d1 * d1 + d2 * d2);
		motionX += d1 * 0.05D * Math.cos(d);
		motionZ += d1 * 0.05D * Math.sin(d);
		isAirBorne = true;
	}

	public void mountEntity(Entity p_70078_1_)
	{
		if (p_70078_1_ instanceof EntityLivingBase)
		super.mountEntity(p_70078_1_);
	}

	public int getMinionCap()
	{
		int multi = 2;
		if (worldObj.difficultySetting == EnumDifficulty.EASY)
		--multi;
		if (worldObj.difficultySetting == EnumDifficulty.HARD)
		++multi;
		return 32 * (int)multi;
	}

	public int getPriestCap()
	{
		int multi = 2;
		if (worldObj.difficultySetting == EnumDifficulty.EASY)
		--multi;
		if (worldObj.difficultySetting == EnumDifficulty.HARD)
		++multi;
		return 16 * (int)multi;
	}

	public int getZealotCap()
	{
		int multi = 2;
		if (worldObj.difficultySetting == EnumDifficulty.EASY)
		--multi;
		if (worldObj.difficultySetting == EnumDifficulty.HARD)
		++multi;
		return 8 * (int)multi;
	}

	public int getBishopCap()
	{
		int multi = 2;
		if (worldObj.difficultySetting == EnumDifficulty.EASY)
		--multi;
		if (worldObj.difficultySetting == EnumDifficulty.HARD)
		++multi;
		return 4 * (int)multi;
	}

	public int getTemplarCap()
	{
		int multi = 2;
		if (worldObj.difficultySetting == EnumDifficulty.EASY)
		--multi;
		if (worldObj.difficultySetting == EnumDifficulty.HARD)
		++multi;
		return 2 * (int)multi;
	}

	public int getLordCap()
	{
		return worldObj.difficultySetting == EnumDifficulty.HARD ? 2 : 1;
	}

	public int getSpecialMinionCap()
	{
		return 4;
	}

	public int getExtraPower()
	{
		return dataWatcher.getWatchableObjectInt(21);
	}

	public void setExtraPower(int p_82215_1_)
	{
		dataWatcher.updateObject(21, Integer.valueOf(p_82215_1_));
	}

	public int getRandomName()
	{
		return dataWatcher.getWatchableObjectInt(21);
	}

	public void setRandomName(int p_82215_1_)
	{
		dataWatcher.updateObject(21, Integer.valueOf(p_82215_1_));
	}

	public boolean getWaiting()
	{
		return EventData.isPreTitanMode(worldObj) || dataWatcher.getWatchableObjectByte(23) == 1;
	}

	public void setWaiting(boolean p_82215_1_)
	{
		dataWatcher.updateObject(23, Byte.valueOf(p_82215_1_ ? (byte)1 : (byte)0));
	}

	public int getTitanVariant()
	{
		return dataWatcher.getWatchableObjectInt(25);
	}

	public void setTitanVariant(int p_82215_1_)
	{
		dataWatcher.updateObject(25, Integer.valueOf(p_82215_1_));
	}

	public int getATAAID()
	{
		return dataWatcher.getWatchableObjectInt(24);
	}

	public void setATAAID(int p_82215_1_)
	{
		dataWatcher.updateObject(24, Integer.valueOf(p_82215_1_));
	}

	public int getInvulTime()
	{
		return dataWatcher.getWatchableObjectInt(20);
	}

	public void setInvulTime(int p_82215_1_)
	{
		if (isServer())
		{
			if (p_82215_1_ < 0)
			{
				dataWatcher.updateObject(20, Integer.valueOf(0));
			}

			else
			{
				dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
				prevRotationPitch = rotationPitch = (0F + (p_82215_1_ / (getThreashHold() / 180F)));
			}
		}
	}

	public boolean destroyBlocksInAABBGriefingBypass(AxisAlignedBB p_70972_1_)
	{
		int i = MathHelper.floor_double(p_70972_1_.minX);
		int j = MathHelper.floor_double(p_70972_1_.minY);
		int k = MathHelper.floor_double(p_70972_1_.minZ);
		int l = MathHelper.floor_double(p_70972_1_.maxX);
		int i1 = MathHelper.floor_double(p_70972_1_.maxY);
		int j1 = MathHelper.floor_double(p_70972_1_.maxZ);
		boolean flag = false;
		boolean flag1 = false;
		for (int k1 = i; k1 <= l; k1++)
		{
			for (int l1 = j; l1 <= i1; l1++)
			{
				for (int i2 = k; i2 <= j1; i2++)
				{
					Block block = worldObj.getBlock(k1, l1, i2);
					if ((!block.isAir(worldObj, k1, l1, i2)) && (isServer()))
					{
						if (block.getBlockHardness(worldObj, k1, l1, i2) != -1F)
						{
							flag1 = worldObj.setBlockToAir(k1, l1, i2) || (flag1);
						}
					}
				}
			}
		}

		return flag;
	}

	public void destroyBlocksInAABB(AxisAlignedBB p_70972_1_)
	{
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
					if (ticksExisted > 5 && p_70972_1_ != null && worldObj.checkChunksExist(k1, l1, i2, k1, l1, i2) && (!block.isAir(worldObj, k1, l1, i2)) && (isServer()))
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
									EntityFallingBlockTitan entityfallingblock = new EntityFallingBlockTitan(worldObj, k1 + 0.5D, l1 + 0.5D, i2 + 0.5D, block, worldObj.getBlockMetadata(k1, l1, i2));
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
					if (ticksExisted > 5 && p_70972_1_ != null && worldObj.checkChunksExist(k1, l1, i2, k1, l1, i2) && block.isOpaqueCube() && !block1.isOpaqueCube() && (isServer()))
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
									EntityFallingBlockTitan entityfallingblock = new EntityFallingBlockTitan(worldObj, k1 + 0.5D, l1 + 0.5D, i2 + 0.5D, block, worldObj.getBlockMetadata(k1, l1, i2));
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

	protected void fall(float p_70069_1_)
	{
		onGround = true;
		isAirBorne = false;
		p_70069_1_ = ForgeHooks.onLivingFall(this, p_70069_1_);
		if (p_70069_1_ <= 0.0F) return;
		PotionEffect potioneffect = getActivePotionEffect(Potion.jump);
		float f1 = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0.0F;
		int i = MathHelper.ceiling_float_int(p_70069_1_ - 24F - f1);
		if (i > 0)
		{
			shakeNearbyPlayerCameras(50D);
			playSound("thetitans:groundSmash", 20.0F, 1.0F);
			playSound("thetitans:titanland", 10000.0F, 1.0F);
			destroyBlocksInAABBTopless(boundingBox.expand(getTitanStatus() == EnumTitanStatus.LESSER ? 6D : 12D, 1D, getTitanStatus() == EnumTitanStatus.LESSER ? 6D : 12D));
			List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(48D, 4D, 48D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (((entity instanceof EntityLivingBase)) && canAttackClass(entity.getClass()) && (!(entity instanceof EntityTitan)))
					{
						float smash = 50F - getDistanceToEntity(entity);
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

	public boolean canBePushed()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	public int getTotalArmorValue()
	{
		switch (getTitanStatus())
		{
			case GOD:
			return 99;
			case GREATER:
			return 90;
			case AVERAGE:
			return 80;
			case LESSER:
			return 70;
			case UTILITY:
			return 50;
			default:
			return 0;
		}
	}

	public boolean isArmored()
	{
		return false;
	}

	/**
	* Reduces damage, depending on armor
	*/
	protected float applyArmorCalculations(DamageSource p_70655_1_, float p_70655_2_)
	{
		int i = 100 - getTotalArmorValue();
		float f1 = p_70655_2_ * (float)i;
		p_70655_2_ = f1 / 100.0F;
		return p_70655_2_;
	}

	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	public int getTitanExperienceDropAmount()
	{
		return 1;
	}

	public float getRenderSizeModifier()
	{
		return getTitanSizeMultiplier();
	}

	public void addPotionEffect(PotionEffect p_70690_1_) 
	{
		 
	}


	protected float getSoundVolume()
	{
		return getTitanSizeMultiplier();
	}

	protected void despawnEntity()
	{
		entityAge = 0;
	}

	public int getTalkInterval()
	{
		return 120;
	}

	public int getMaxFallHeight()
	{
		return worldObj.getHeight();
	}

	public void setInWeb() 
	{
		 
	}


	public int getMinionSpawnRate()
	{
		return 0;
	}

	public boolean handleWaterMovement()
	{
		return false;
	}

	public boolean handleLavaMovement()
	{
		return false;
	}

	protected int decreaseAirSupply(int p_70682_1_)
	{
		return p_70682_1_;
	}

	public double getSpeed()
	{
		return 0.325D / 4;
	}

	public double getMeleeRange()
	{
		return width + getTitanSizeMultiplier();
	}

	public void addVelocity(double p_70024_1_, double p_70024_3_, double p_70024_5_) 
	{
		 
	}


	public void addTitanVelocity(double p_70024_1_, double p_70024_3_, double p_70024_5_)
	{
		if (!getWaiting() && getAnimID() != 13)
		{
			motionX += p_70024_1_;
			motionY += p_70024_3_;
			motionZ += p_70024_5_;
		}
	}

	public boolean hasNoSoul()
	{
		return false;
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return TheTitans.TITAN;
	}

	public void moveTitanToPoint(double x, double z, boolean uselook)
	{
		double d0 = x - posX;
		double d1 = z - posZ;
		float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
		if (uselook)
		getLookHelper().setLookPosition(x, posY + getEyeHeight(), z, 64 / getTitanSizeMultiplier(), getVerticalFaceSpeed());
		motionX += d0 / f2 * getSpeed() - motionX * getSpeed();
		motionZ += d1 / f2 * getSpeed() - motionZ * getSpeed();
		if (ticksExisted % 100 == 0)
		renderYawOffset = rotationYaw = rotationYawHead;
		if (!(this instanceof EntitySnowGolemTitan))
		destroyBlocksInAABB(boundingBox.offset(motionX, 0, motionZ));
	}

	public boolean isNoDespawnRequired()
	{
		return true;
	}

	public boolean canAttack()
	{
		return meleeTitan;
	}

	public boolean shouldMove()
	{
		return getAnimID() == 0 && !getWaiting() && !(this instanceof EntityWitherzilla) && getAttackTarget() != null && getDistanceToEntity(getAttackTarget()) > getMeleeRange() + (meleeTitan ? 0D : 96D);
	}

	public int getFootID()
	{
		return footID;
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	{
		setTitanHealth(getMaxHealth());
		return super.onSpawnWithEgg(p_110161_1_);
	}

	@SuppressWarnings("unchecked")
	public void onDeath(DamageSource p_70645_1_)
	{
		Entity entity = p_70645_1_.getEntity();
		ArrayList<?> listp = Lists.newArrayList(worldObj.playerEntities);
		if (isServer() && (listp != null) && (!listp.isEmpty()) && entity != null && entity instanceof EntityLivingBase)
		{
			for (int i1 = 0; i1 < listp.size(); i1++)
			{
				EntityPlayer entityplayer = (EntityPlayer)listp.get(i1);
				if (getTitanHealth() > 0F)
				entityplayer.addChatMessage(new ChatComponentText(getCommandSenderName() + " has refused to let " + entity.getCommandSenderName() + " cheat..."));
				else
				entityplayer.addChatMessage(new ChatComponentText(getCommandSenderName() + " has been defeated by " + entity.getCommandSenderName()));
			}
		}

		if (getTitanHealth() <= 0F)
		{
			EntityLivingBase entitylivingbase = func_94060_bK();
			if (scoreValue >= 0 && entitylivingbase != null)
			{
				entitylivingbase.addToPlayerScore(this, scoreValue);
			}

			if (entity != null)
			{
				entity.onKillEntity(this);
			}

			dead = true;
			func_110142_aN().func_94549_h();
			if (isServer())
			{
				int i = 0;
				if (entity instanceof EntityPlayer)
				{
					i = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
					((EntityPlayer)entity).addStat(TheTitans.titansDefeatedStat, 1);
				}

				captureDrops = true;
				capturedDrops.clear();
				int j = 0;
				if (func_146066_aG() && worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
				{
					dropFewItems(recentlyHit > 0, i);
					dropEquipment(recentlyHit > 0, i);
					if (recentlyHit > 0)
					{
						j = rand.nextInt(200) - i;
						if (j < 5)
						dropRareDrop(j <= 0 ? 1 : 0);
					}
				}

				captureDrops = false;
				if (!ForgeHooks.onLivingDrops(this, p_70645_1_, capturedDrops, i, recentlyHit > 0, j))
				for (EntityItem item : capturedDrops)
				worldObj.spawnEntityInWorld(item);
			}

			worldObj.setEntityState(this, (byte)3);
		}
	}

	protected void jump()
	{
		super.jump();
		playSound("thetitans:titanSwing", 5F, 2F);
		++motionY;
		++motionY;
	}

	public int getParticleCount()
	{
		return 6;
	}

	public String getParticles()
	{
		switch (getTitanStatus())
		{
			case GOD:
			return "fireworksSpark";
			case GREATER:
			return "magicCrit";
			case AVERAGE:
			return "crit";
			default:
			return "enchantmenttable";
		}
	}

	public int getThreashHold()
	{
		return getTitanStatus() == EnumTitanStatus.GOD ? 7100 : (getTitanStatus() == EnumTitanStatus.GREATER ? 1310 : ((this instanceof EntitySnowGolemTitan || this instanceof EntitySlimeTitan) ? 150 : 850));
	}

	public void enactEffectAura(int type, AxisAlignedBB boundery)
	{
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundery);
		if (worldObj != null && !getWaiting())
		{
			if (list != null && !list.isEmpty())
			{
				for (int i = 0; i < list.size(); ++i)
				{
					Entity entity = (Entity)list.get(i);
					if (worldObj.isRemote && (entity instanceof EntityLivingBase && !(entity instanceof EntityTitan)))
					{
						EntityLivingBase mob = (EntityLivingBase)entity;
						switch (type)
						{
							case 1:
							{
								mob.attackEntityFrom(DamageSourceExtra.wither.setDamageIsAbsolute().setDamageAllowedInCreativeMode(), 4.0F);
								mob.addPotionEffect(new PotionEffect(ClientProxy.advancedWither.id, 1200));
								mob.addPotionEffect(new PotionEffect(Potion.wither.id, 1200, 3));
								mob.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400, 4));
								if (mob.getHealth() <= 5.0F)
								mob.addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 1));
								break;
							}

							case 2:
							{
								mob.addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, 1200));
								break;
							}

							case 3:
							{
								mob.setFire(40);
								break;
							}

							case 4:
							{
								mob.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400, 4));
								break;
							}

							case 5:
							{
								mob.addPotionEffect(new PotionEffect(Potion.poison.id, 400, 2));
								break;
							}

							case 6:
							{
								mob.addPotionEffect(new PotionEffect(Potion.poison.id, 400, 2));
								break;
							}

							default:
							{
								mob.addPotionEffect(new PotionEffect(ClientProxy.creeperTitanRadiation.id, 600));
							}
						}
					}
				}
			}
		}
	}

	public void collideWithProjectiles(AxisAlignedBB hitbox)
	{
		if (hurtResistantTime < 1)
		{
			List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, hitbox.expand(0.5D, 0.5D, 0.5D));
			if (list != null && !list.isEmpty())
			{
				for (int j = 0; j < list.size(); ++j)
				{
					Entity entity = (Entity)list.get(j);
					if (entity instanceof EntityFireball && ((EntityFireball)entity).shootingEntity != this && !(entity instanceof EntityLightningBall) && !(entity instanceof EntityGargoyleTitanFireball) && !(entity instanceof EntityWebShot))
					((EntityFireball)entity).attackEntityFrom(DamageSource.causeThornsDamage(this), 0F);
					else if (entity instanceof EntityTitanFireball && ((EntityTitanFireball)entity).shootingEntity != null && ((EntityTitanFireball)entity).shootingEntity != this)
					((EntityTitanFireball)entity).onImpactPublic(this);
					else if (entity instanceof EntityGargoyleTitanFireball)
					{
						playSound("thetitans:titanpunch", 10.0F, 1.0F);
						worldObj.newExplosion(((EntityGargoyleTitanFireball)entity).shootingEntity != null ? ((EntityGargoyleTitanFireball)entity).shootingEntity : ((EntityGargoyleTitanFireball)entity), ((EntityGargoyleTitanFireball)entity).posX, ((EntityGargoyleTitanFireball)entity).posY, ((EntityGargoyleTitanFireball)entity).posZ, 8F, false, false);
						attackEntityFrom(DamageSource.causeFireballDamage((EntityGargoyleTitanFireball)entity, ((EntityGargoyleTitanFireball)entity).shootingEntity != null ? ((EntityGargoyleTitanFireball)entity).shootingEntity : (EntityGargoyleTitanFireball)entity), 1000F);
						entity.setDead();
					}

					else if (entity instanceof EntityHarcadiumArrow)
					{
						EntityHarcadiumArrow arrow = (EntityHarcadiumArrow)entity;
						float f2 = MathHelper.sqrt_double(arrow.motionX * arrow.motionX + arrow.motionY * arrow.motionY + arrow.motionZ * arrow.motionZ);
						int k = MathHelper.ceiling_double_int(f2 * arrow.getDamage() / 2);
						if (arrow.getIsCritical())
						k += rand.nextInt(k / 2 + 2);
						playSound("thetitans:titanpunch", 10.0F, 1.0F);
						attackEntityFrom(DamageSourceExtra.causeHarcadiumArrowDamage(arrow, arrow.shootingEntity != null ? arrow.shootingEntity : arrow), k);
						entity.setDead();
					}

					else if (entity instanceof EntityWebShot && ((EntityWebShot)entity).shootingEntity != this)
					{
						playSound("thetitans:titanpunch", 10.0F, 1.0F);
						attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(((EntityWebShot)entity).shootingEntity != this ? ((EntityWebShot)entity).shootingEntity : (EntityWebShot)entity), 300F);
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
		}
	}

	public void collideWithEntities(EntityTitanPart part, List<?> entities)
	{
		if (part != null && part.worldObj != null && !getWaiting())
		{
			double d0 = (part.boundingBox.minX + part.boundingBox.maxX) / 2.0D;
			double d1 = (part.boundingBox.minZ + part.boundingBox.maxZ) / 2.0D;
			Iterator<?> iterator = entities.iterator();
			
			while (iterator.hasNext())
			{
				Entity entity = (Entity)iterator.next();
				boolean leg = part.getCommandSenderName().toLowerCase().contains("leg");
				
				if (entity != null && canAttackClass(entity.getClass()) && !(entity instanceof EntityWebShot) && !(entity instanceof EntitySkeletonTitanGiantArrow) && !(entity instanceof EntityWitherSkull) && !(entity instanceof EntityTitanFireball) && !(entity instanceof EntityProtoBall) && !(entity instanceof EntityLightningBall) && !(entity instanceof EntityTitanPart) && !(entity instanceof EntityHarcadiumArrow) && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit))
				{
					part.playSound("random.explode", 1F, 1.0F + rand.nextFloat());
					double d2 = entity.posX - d0;
					double d3 = entity.posZ - d1;
					double d4 = d2 * d2 + d3 * d3;
					entity.addVelocity(d2 / d4 * (leg ? 5D : 1.5D), (leg ? 1.75D : 0.5D), d3 / d4 * (leg ? 5D : 1.5D));
					
					if (isServer() && canAttackClass(entity.getClass()) && entity.posY <= part.posY - part.height - 0.01D)
						entity.attackEntityFrom(DamageSource.causeThornsDamage(this), 20F);
					
					if (this instanceof EntitySkeletonTitan && ((EntitySkeletonTitan)this).getSkeletonType() == 1 && entity instanceof EntityLivingBase)
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.id, 1200, 3));
				}
			}
		}
	}

	/**Calculates the damage this titan will deal based on it's titan status.*/
	public double getAttackValue(double baseDamage)
	{
		return baseDamage * (getTitanMultiplier() * 0.005D) * (isChild() ? 0.5D : 1.0D);
	}

	/**Calculates the health this titan provides based on it's titan status*/
	public double getHealthValue()
	{
		return getTitanMultiplier() * (isChild() ? 0.5D : 1.0D) * (TheTitans.getDifficulty(worldObj) > 2 ? (TheTitans.getDifficulty(worldObj) - 1) : 1.0D);
	}

	/**Returns a multiplier based on the titan's status*/
	public final double getTitanMultiplier()
	{
		switch (getTitanStatus())
		{
			case GOD:
			return 51450000000000000000000000.0D;
			case GREATER:
			return 5000000000000.0D;
			case AVERAGE:
			return 45000000000.0D;
			case LESSER:
			return 5000000.0D;
			default:
			return 150000.0D;
		}
	}

	@Deprecated
	/**Titans no longer use this value. Please use getAttackValue(double)*/
	public double getAttackValue()
	{
		return 20.0F;
	}

	@Deprecated
	/**Titans no longer use this value. Please use getHealthValue(double)*/
	public double getHitpointValue()
	{
		return Double.MAX_VALUE;
	}

	@Deprecated
	/**Titans no longer use this value. Please use getTitanMultiplier() as it is used to calculate damage and health*/
	public double getHitpointTitanMultiplier()
	{
		switch (getTitanStatus())
		{
			case GOD:
			return 5145000000000000000000.0D;
			case GREATER:
			return 500000000.0D;
			case AVERAGE:
			return 2500000.0D;
			case LESSER:
			return 500000.0D;
			default:
			return 15000.0D;
		}
	}

	public int getKnockbackAmount()
	{
		switch (getTitanStatus())
		{
			case GOD:
			return 24;
			case GREATER:
			return 16;
			case AVERAGE:
			return 8;
			case LESSER:
			return 4;
			default:
			return 2;
		}
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.LESSER;
	}

	protected String getSwimSound()
	{
		return "game.hostile.swim";
	}

	protected String getSplashSound()
	{
		return "game.hostile.swim.splash";
	}

	public boolean isPotionApplicable(PotionEffect p_70687_1_)
	{
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		Entity entity = source.getEntity();
		if (isOreSpawnBossToExempt(entity) || entity instanceof EntityIronGolem || entity instanceof EntityGiantZombieBetter || entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityDragonMinion || entity instanceof EntityWitherMinion)
		{
			amount *= 5F;
			playSound("thetitans:titanpunch", 50F, isChild() ? 1.5F : 1.0F);
		}

		if ((isEntityInvulnerable()) || isClient() || (entity == null && source.getDamageType() != "blindness" && source.getDamageType() != "other") || (amount <= 20.0F))
		return false;
		else if (source.getEntity() instanceof EntitySnowGolemTitan && this instanceof EntitySnowGolemTitan)
		return false;
		else if (riddenByEntity != null && riddenByEntity == source.getEntity())
		return false;
		else if (ridingEntity != null && ridingEntity == source.getEntity())
		return false;
		else if ((entity != null) && ((entity instanceof EntityLivingBase)) && ((riddenByEntity != null && source.getEntity() == riddenByEntity) || entity.isEntityInvulnerable() || entity.height >= 6.0F || (((EntityLivingBase)entity).getTotalArmorValue() > 24 && !((EntityLivingBase)entity).isPotionActive(Potion.field_76444_x)) || ((EntityLivingBase)entity).isEntityInvulnerable() || (((EntityLivingBase)entity).isPotionActive(Potion.damageBoost) && ((EntityLivingBase)entity).getActivePotionEffect(Potion.damageBoost).getAmplifier() > 255)) && !whiteListNoDamage(entity) && !(entity instanceof EntityTameable))
		return false;
		else if (((source.isMagicDamage()) || (source.isExplosion()) || (source.isFireDamage()) || (source.getDamageType() == "inFire") || (source.getDamageType() == "onFire") || (source.getDamageType() == "lava") || (source.getDamageType() == "inWall") || (source.getDamageType() == "drown") || (source.getDamageType() == "starve") || (source.getDamageType() == "cactus") || (source.getDamageType() == "fall") || (source.getDamageType() == "generic") || (source.getDamageType() == "outOfWorld") || (source.getDamageType() == "magic") || (source.getDamageType() == "wither") || (source.getDamageType() == "anvil") || (source.getDamageType() == "fallingBlock") || (source.getDamageType() == "explosion.player") || (source.getDamageType() == "explosion") || (source.getDamageType() == "indirectMagic")) && !(this instanceof EntitySlimeTitan))
		return false;
		else
		{
			entityAge = 0;
			if (getTitanHealth() <= 0.0F)
			return false;
			else if (source.isFireDamage())
			return false;
			else
			{
				if ((entity != null) && ((entity instanceof EntityLivingBase)) && getAnimTick() <= 12 && !(worldObj.provider instanceof WorldProviderVoid))
				{
					setAttackTarget((EntityLivingBase)entity);
					setRevengeTarget((EntityLivingBase)entity);
				}

				boolean flag = true;
				if (hurtResistantTime > maxHurtResistantTime / 2)
				{
					if (amount <= lastDamage)
					return false;
					lastDamage = amount;
					flag = false;
					damageEntity(source, amount - lastDamage);
				}

				else
				{
					lastDamage = amount;
					prevHealth = getHealth();
					hurtResistantTime = maxHurtResistantTime;
					damageEntity(source, amount);
				}

				attackedAtYaw = 0.0F;
				if (entity != null)
				{
					if (entity instanceof EntityLivingBase)
					setRevengeTarget((EntityLivingBase)entity);
					if (entity instanceof EntityPlayer)
					{
						recentlyHit = 200;
						attackingPlayer = (EntityPlayer)entity;
					}

					else
					{
						recentlyHit = 200;
						attackingPlayer = null;
					}
				}

				if (flag)
				{
					worldObj.setEntityState(this, (byte)2);
					if (source != DamageSource.drown)
					setBeenAttacked();
					if (entity != null)
					{
						double d1 = entity.posX - posX;
						double d0;
						for (d0 = entity.posZ - posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
						d1 = (Math.random() - Math.random()) * 0.01D;
						attackedAtYaw = (float)(Math.atan2(d0, d1) * 180.0D / Math.PI) - rotationYaw;
						knockBack(entity, amount, d1, d0);
					}

					else
					attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
				}

				String s;
				if (getTitanHealth() <= 0.0F)
				{
					s = getDeathSound();
					if (flag && s != null)
					playSound(s, getSoundVolume(), getSoundPitch());
					onDeath(source);
				}

				else
				{
					s = getHurtSound();
					if (flag && s != null)
					playSound(s, getSoundVolume(), getSoundPitch());
				}

				return true;
			}
		}
	}

	protected String getHurtSound()
	{
		return "game.hostile.hurt";
	}

	protected String getDeathSound()
	{
		return "game.hostile.die";
	}

	protected String getFallSoundString(int damageValue)
	{
		return "thetitans:titanStep";
	}

	//@SideOnly(Side.CLIENT)
	public void shakeNearbyPlayerCameras(double distance)
	{
		if (!worldObj.playerEntities.isEmpty())
		{
			for (int l1 = 0; l1 < worldObj.playerEntities.size(); ++l1)
			{
				Entity entity = (Entity)worldObj.playerEntities.get(l1);
				if (entity != null && entity.dimension == dimension && entity.isEntityAlive() && entity instanceof EntityLivingBase && !(entity instanceof EntityTitan) && entity.getDistanceToEntity(this) < distance * getTitanSizeMultiplier())
				{
					entity.hurtResistantTime = 0;
					worldObj.setEntityState((EntityLivingBase)entity, (byte)2);
				}
			}
		}
	}

	public static boolean isADudEntity(Entity entity)
	{
		if (!(entity instanceof EntityLivingBase))
		return true;
		else if (entity instanceof EntityTitanSpirit)
		return true;
		else if (entity instanceof EntityTitanPart)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof EntityLivingBase && danger.orespawn.MyUtils.isIgnoreable((EntityLivingBase)entity))
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.GodzillaHead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.KingHead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.QueenHead)
		return true;
		else
		return false;
	}

	public static boolean isOreSpawnBossToExempt(Entity entity)
	{
		if (!(entity instanceof EntityLivingBase))
		return false;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof net.minecraft.entity.orespawnaddon.EntityOverlordScorpion)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof net.minecraft.entity.orespawnaddon.EntityMethuselahKraken)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof net.minecraft.entity.orespawnaddon.EntityBurningMobzilla)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Kraken)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.PitchBlack)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Godzilla)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.GodzillaHead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.TheKing)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.TheQueen)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.KingHead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.QueenHead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.ThePrinceAdult)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.GiantRobot)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.AntRobot)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Basilisk)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.CaterKiller)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Cephadrome)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.EmperorScorpion)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.HerculesBeetle)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Hammerhead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Leon)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Robot2)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Robot3)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Robot4)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.ThePrince)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.ThePrinceTeen)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.ThePrincess)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.SpiderRobot)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.TrooperBug)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Nastysaurus)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.TRex)
		return true;
		else
		return false;
	}

	public static boolean whiteListNoDamage(Entity entity)
	{
		if (entity instanceof EntityPlayer)
		return true;
		else if (entity instanceof EntityTitan)
		return true;
		else if (entity instanceof EntityGiantZombieBetter)
		return true;
		else if (entity instanceof EntityDragonMinion)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof net.minecraft.entity.orespawnaddon.EntityOverlordScorpion)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof net.minecraft.entity.orespawnaddon.EntityMethuselahKraken)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof net.minecraft.entity.orespawnaddon.EntityBurningMobzilla)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Kraken)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.PitchBlack)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Godzilla)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.GodzillaHead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.TheKing)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.TheQueen)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.KingHead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.QueenHead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.ThePrinceAdult)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.GiantRobot)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.AntRobot)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Basilisk)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.CaterKiller)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Cephadrome)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.EmperorScorpion)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.HerculesBeetle)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Hammerhead)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Leon)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Robot2)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Robot3)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Robot4)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.ThePrince)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.ThePrinceTeen)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.ThePrincess)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.SpiderRobot)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.TrooperBug)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.Nastysaurus)
		return true;
		else if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.TRex)
		return true;
		else if (entity instanceof EntityLivingBase && entity.height > 6F)
		return false;
		else if (!(entity instanceof EntityLivingBase) && !(entity instanceof EntityFireball) && !(entity instanceof EntityArrow) && !(entity instanceof EntityThrowable))
		return false;
		else
		return true;
	}

	public void attackChoosenEntity(Entity damagedEntity, float damage, int knockbackAmount)
	{
		
		if (damagedEntity == null || !isEntityAlive() || (damagedEntity != null && isEntity(damagedEntity, EntityFallingBlockTitan.class, EntityItemBomb.class, EntityXPBomb.class, EntityItem.class, EntityXPOrb.class, EntityTitanPart.class, EntityTitanSpirit.class)) || !canAttackClass(damagedEntity.getClass()) || (damagedEntity instanceof EntityPlayer && isPlayerBlacklisted((EntityPlayer) damagedEntity)))
		return;
		if (isServer() && damagedEntity instanceof EntityPlayerMP)
		{
			TheTitans.reflect.set(EntityPlayerMP.class, (EntityPlayerMP) damagedEntity, -1, "field_147101_bU");
			((EntityPlayerMP)damagedEntity).mcServer.getConfigurationManager().sendPacketToAllPlayers(new S12PacketEntityVelocity((EntityPlayerMP)damagedEntity));
		}

		if (TheTitans.isDifficulty(TheTitans.getDifficulty(worldObj), 7, 7))
			damage = Float.MAX_VALUE;
		else if (TheTitans.isDifficulty(worldObj, EnumDifficulty.HARD))
			damage = MathHelper.clamp_float(damage * 2.0F, 0.0F, Float.MAX_VALUE);
		else if (TheTitans.isDifficulty(worldObj, EnumDifficulty.PEACEFUL))
			damage = MathHelper.clamp_float(damage * 0.5F, 0.0F, Float.MAX_VALUE);
		
		damagedEntity.hurtResistantTime = 0;
		
		if (damagedEntity.height < 6.0F)
		{
			damagedEntity.motionY += rand.nextDouble();
			damagedEntity.addVelocity(-MathHelper.sin(renderYawOffset * (float)Math.PI / 180.0F) * (1 + knockbackAmount) * 0.2D, (1 + knockbackAmount) * 0.2D, MathHelper.cos(renderYawOffset * (float)Math.PI / 180.0F) * (1 + knockbackAmount) * 0.2D);
		}

		if (!(damagedEntity instanceof EntityLivingBase) || (damagedEntity instanceof EntityLivingBase && !(damagedEntity instanceof EntityLiving) && !(damagedEntity instanceof EntityPlayer)))
		{
			damagedEntity.attackEntityFrom((new DamageSource("other")).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode(), Float.MAX_VALUE);
			damagedEntity.setDead();
			return;
		}

		if (isEntityCheating(damagedEntity))
		{
			damagedEntity.playSound("thetitans:titanStrike", 50F, 2F);
			if (damagedEntity instanceof EntityPlayer)
			if (!damagedEntity.attackEntityFrom(DamageSourceExtra.causeArmorPiercingMobDamage(this).setDamageIsAbsolute().setDamageAllowedInCreativeMode(), damage))
			{
				damageBypassEntity((EntityPlayer) damagedEntity, new DamageSource("infinity").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute(), damage);
				damageBypassEntity((EntityPlayer) damagedEntity, new DamageSource("other").setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode(), damage);
				damageBypassEntity((EntityPlayer) damagedEntity, DamageSource.outOfWorld.setDamageIsAbsolute(), damage);
			}

			else
			if (!damagedEntity.attackEntityFrom(new DamageSource("infinity").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute(), damage))
			damageBypassEntity((EntityLivingBase)damagedEntity, new DamageSource("infinity").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute(), damage);
			return;
		}

		if (damagedEntity instanceof EntityLivingBase)
		{
			renderYawOffset = rotationYaw = rotationYawHead;
			damage = MathHelper.clamp_float(damage + EnchantmentHelper.func_152377_a(getHeldItem(), ((EntityLivingBase)damagedEntity).getCreatureAttribute()), 0.0F, Float.MAX_VALUE);
			TheTitans.reflect.set(EntityLivingBase.class, (EntityLivingBase)damagedEntity, 100, "recentlyHit", "field_70718_bc");
			if (Loader.isModLoaded("OreSpawn") && isOreSpawnBossToExempt(damagedEntity))
			{
				TheTitans.reflect.setAlt(damagedEntity.getClass(), damagedEntity, 0, "hurt_timer");
				TheTitans.reflect.setAlt(damagedEntity.getClass(), damagedEntity, 0, "large_unknown_detected");
				TheTitans.reflect.setAlt(damagedEntity.getClass(), damagedEntity, 10, "player_hit_count");
				TheTitans.reflect.setAlt(damagedEntity.getClass(), damagedEntity, true, "hasDied");
			}

			if (knockbackAmount > 0)
			{
				knockbackAmount += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)damagedEntity);
				damagedEntity.motionY += rand.nextDouble();
				damagedEntity.addVelocity(-MathHelper.sin(renderYawOffset * 3.1415927F / 180.0F) * knockbackAmount * 0.25D, knockbackAmount * 0.25D, MathHelper.cos(renderYawOffset * 3.1415927F / 180.0F) * knockbackAmount * 0.25D);
			}

			if (damagedEntity.height >= 6.0F)
			{
				damage = MathHelper.clamp_float(damage * 20.0F, 0.0F, Float.MAX_VALUE);
				damagedEntity.playSound("thetitans:titanpunch", 50F, isChild() ? 1.5F : 1.0F);
			}

			if (EnchantmentHelper.getFireAspectModifier(this) > 0)
			damagedEntity.setFire(EnchantmentHelper.getFireAspectModifier(this) * 100);
			if (damagedEntity instanceof EntityEnderCrystal)
			{
				damagedEntity.attackEntityFrom((new DamageSource("other")).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode(), 100F);
				return;
			}

			else if (damagedEntity instanceof EntityDragon)
			{
				worldObj.newExplosion(null, damagedEntity.posX, damagedEntity.posY, damagedEntity.posZ, 6F, false, false);
				return;
			}

			else if (damagedEntity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)damagedEntity;
				for (int i = 0; i < player.inventory.armorInventory.length; ++i)
				if (player.inventory.armorInventory[i] != null)
				player.inventory.armorInventory[i].setItemDamage((int)(Math.min(player.inventory.armorInventory[i].getItemDamage() + damage / (getTitanMultiplier() * 100.0D), player.inventory.armorInventory[i].getMaxDamage())));
			}

			damagedEntity.attackEntityFrom(damagedEntity.getEyeHeight() > 5 ? DamageSourceExtra.causeAntiTitanDamage(this) : DamageSource.causeMobDamage(this), damage);
		}

		else
		{
			damageBypassEntity((EntityLivingBase) damagedEntity, DamageSourceExtra.causeSoulStealingDamage(this), damage);
			int b0 = 1 + TheTitans.getDifficulty(worldObj);
			((EntityLivingBase)damagedEntity).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, b0 * 20, 2));
		}

		//				if (Loader.isModLoaded("OreSpawn") && ((EntityLivingBase)damagedEntity).getHealth() <= 2050F && (damagedEntity instanceof danger.orespawn.TheKing || damagedEntity instanceof danger.orespawn.TheQueen))
		//			
		{

			//			((EntityLivingBase)damagedEntity).getDataWatcher().updateObject(6, Float.valueOf(MathHelper.clamp_float(1.0F, 0.0F, ((EntityLivingBase)damagedEntity).getMaxHealth())));
			//		damagedEntity.attackEntityFrom(DamageSource.causePlayerDamage(worldObj.getClosestPlayerToEntity(this, -1D)), damage);
			//	damageBypassEntity((EntityLivingBase) damagedEntity, DamageSourceExtra.destroy, damage);
			//damagedEntity.playSound("orespawn:trex_death", 5F, 1F);
			//
		}


		///else if (Loader.isModLoaded("OreSpawn") && (damagedEntity instanceof danger.orespawn.PurplePower))
		//
		{

			//((EntityLivingBase)damagedEntity).getDataWatcher().updateObject(6, Float.valueOf(MathHelper.clamp_float(0.0F, 0.0F, ((EntityLivingBase)damagedEntity).getMaxHealth())));
			//damagedEntity.attackEntityFrom(DamageSource.causePlayerDamage(worldObj.getClosestPlayerToEntity(this, -1D)), damage);
			//((danger.orespawn.PurplePower)damagedEntity).setHealth(0F);
			//((danger.orespawn.PurplePower)damagedEntity).playSound("orespawn:trex_death", 2F, 0.9999F);
			//((danger.orespawn.PurplePower)damagedEntity).playSound("orespawn:trex_death", 2F, 1F);
			//((danger.orespawn.PurplePower)damagedEntity).playSound("orespawn:trex_death", 2F, 1.0001F);
			//
		}


		if (damagedEntity instanceof EntityLiving && ((EntityLiving)damagedEntity).getAttackTarget() == null)
		((EntityLiving)damagedEntity).setAttackTarget(this);
		if (damagedEntity instanceof EntityPlayer && !((EntityPlayer)damagedEntity).isEntityAlive())
		((EntityPlayer)damagedEntity).addStat(TheTitans.deathsTitansStat, 1);
	}

	private void damageBypassEntity(EntityLivingBase entity, DamageSource source, float damage)
	{
		if (damage > 0.0F && entity.isEntityAlive())
		{
			float calDamage = damage;
			calDamage = Math.max(damage - entity.getAbsorptionAmount(), 0.0F);
			entity.setAbsorptionAmount(entity.getAbsorptionAmount() - calDamage);
			if (calDamage != 0.0F)
			{
				float rawHealth = entity.getHealth();
				if (entity instanceof EntityCreature)
				EntityIronGolemTitan.addTitanTargetingTaskToEntity((EntityCreature)entity);
				if (!entity.attackEntityFrom(source, calDamage))
				entity.getDataWatcher().updateObject(6, Float.valueOf(MathHelper.clamp_float(rawHealth - calDamage, 0.0F, entity.getMaxHealth())));
				entity.func_110142_aN().func_94547_a(source, rawHealth, calDamage);
				if (!(entity instanceof EntityTitan))
				{
					if (entity.height == 50F && entity.width == 15F)
					{
						entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(0D);
						entity.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 40.0F);
						entity.addPotionEffect(new PotionEffect(ClientProxy.death.id, Integer.MAX_VALUE, 19));
					}

					if (!entity.isEntityAlive())
					entity.onDeath(source);
				}
			}
		}
	}

	public boolean canBeHurtByPlayer()
	{
		return !isEntityInvulnerable();
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		swingItem();
		float f = (float)getAttackValue(2.0F);
		int i = getKnockbackAmount();
		attackChoosenEntity(p_70652_1_, f, i);
		if ((p_70652_1_ instanceof EntityMob))
		((EntityMob)p_70652_1_).setRevengeTarget(this);
		getLookHelper().setLookPositionWithEntity(p_70652_1_, 180.0F, getVerticalFaceSpeed());
		return true;
	}

	protected boolean isValidLightLevel()
	{
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		if (worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > rand.nextInt(32))
		{
			return false;
		}

		int l = worldObj.getBlockLightValue(i, j, k);
		if (worldObj.isThundering())
		{
			int i1 = worldObj.skylightSubtracted;
			worldObj.skylightSubtracted = 10;
			l = worldObj.getBlockLightValue(i, j, k);
			worldObj.skylightSubtracted = i1;
		}

		return l <= rand.nextInt(8);
	}

	public boolean getCanSpawnHere()
	{
		return (worldObj.difficultySetting != EnumDifficulty.PEACEFUL);
	}

	public void onKillCommand()
	{
		if (getTitanStatus() != EnumTitanStatus.GOD)
		{
			playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
			setDead();
		}
	}

	protected boolean func_146066_aG()
	{
		return false;
	}

	public StatBase getAchievement()
	{
		return null;
	}

	public boolean isEntityInvulnerable()
	{
		return (getInvulTime() >= 1) || getWaiting() || getAnimID() == 13 || (deathTicks > 0) || (this instanceof EntityWitherzilla && !(worldObj.provider instanceof WorldProviderVoid) && getExtraPower() > 5) || super.isEntityInvulnerable();
	}

	protected void teleportMinionRandomly(EntityLivingBase entity)
	{
		double d0 = posX + (rand.nextDouble() - 0.5D) * (72D + width);
		double d1 = posY;
		double d2 = posZ + (rand.nextDouble() - 0.5D) * (72D + width);
		entity.setLocationAndAngles(d0, d1, d2, rotationYawHead, rotationPitch);
	}

	protected boolean teleportEntityRandomly(EntityLivingBase entity)
	{
		double d0 = posX + (rand.nextDouble() - 0.5D) * (72D + width);
		double d1 = posY - height + (height * 2F);
		double d2 = posZ + (rand.nextDouble() - 0.5D) * (72D + width);
		return teleportEntityTo(entity, d0, d1, d2);
	}

	protected boolean teleportEntityTo(EntityLivingBase entity, double p_70825_1_, double p_70825_3_, double p_70825_5_)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(entity, p_70825_1_, p_70825_3_, p_70825_5_, 0);
		if (MinecraftForge.EVENT_BUS.post(event))
		return false;
		double d3 = posX;
		double d4 = posY;
		double d5 = posZ;
		entity.posX = event.targetX;
		entity.posY = event.targetY;
		entity.posZ = event.targetZ;
		boolean flag = false;
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.posY);
		int k = MathHelper.floor_double(entity.posZ);
		if (worldObj.blockExists(i, j, k))
		{
			boolean flag1 = false;
			while (!flag1 && j > 0)
			{
				Block block = worldObj.getBlock(i, j - 1, k);
				if (block.getMaterial().isSolid())
				flag1 = true;
				else
				{
					--entity.posY;
					--j;
				}
			}

			if (flag1)
			{
				entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, rotationYaw, rotationPitch);
				if (worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox).isEmpty() && !worldObj.isAnyLiquid(entity.boundingBox))
				flag = true;
			}
		}

		if (!flag)
		{
			entity.setLocationAndAngles(d3, d4, d5, rotationYaw, rotationPitch);
			return false;
		}

		else
		return true;
	}

	public void retractMinionNumFromType(EnumMinionType minionType)
	{
		if (minionType == EnumMinionType.SPECIAL)
		--numSpecialMinions;
		else if (minionType == EnumMinionType.PRIEST)
		--numPriests;
		else if (minionType == EnumMinionType.ZEALOT)
		--numZealots;
		else if (minionType == EnumMinionType.BISHOP)
		--numBishop;
		else if (minionType == EnumMinionType.TEMPLAR)
		--numTemplar;
		else if (minionType == EnumMinionType.LOYALIST)
		--numMinions;
	}

	public boolean alreadyHasAName()
	{
		return false;
	}

	public float getTitanSizeMultiplier()
	{
		//((renderYawOffset < 0 ? -renderYawOffset : renderYawOffset) * 0.1F)
		return getTitanStatus() == EnumTitanStatus.GOD ? 256F : getTitanStatus() == EnumTitanStatus.SIMPLE ? 16F : 32F + (getExtraPower() * 0.1F);
	}

	public void setAnimID(int id)
	{
		dataWatcher.updateObject(18, Integer.valueOf(id));
	}

	public void setAnimTick(int tick)
	{
		dataWatcher.updateObject(19, Integer.valueOf(tick));
	}

	public int getAnimID()
	{
		return dataWatcher.getWatchableObjectInt(18);
	}

	public int getAnimTick()
	{
		return dataWatcher.getWatchableObjectInt(19);
	}

	public void setFire(int p_70015_1_)
	{
	}

	public boolean allowLeashing()
	{
		return false;
	}

	protected EntityLiving getMinion()
	{
		return null;
	}

	public boolean isClient()
	{
		return worldObj.isRemote;
	}

	public boolean isServer()
	{
		return !worldObj.isRemote;
	}

	public boolean attackEntityFromPart(EntityTitanPart p_70965_1_, DamageSource source, float amount)
	{
		return attackEntityFrom(source, amount);
	}

	protected void spawnMinion(EntityLiving minion)
	{
		spawnMinion(minion, 0);
	}

	private void spawnMinionB(EntityLiving minion, boolean isGrounded, double offset, float volume, float pitch, String sound)
	{
		if (offset == 0)
		teleportMinionRandomly(minion);
		else
		minion.setLocationAndAngles(posX, posY - offset, posZ, rotationYaw, 0.0F);
		minion.playSound(sound, volume, pitch);
		minion.onSpawnWithEgg(null);
		worldObj.spawnEntityInWorld(minion);
		minion.addPotionEffect(new PotionEffect(Potion.resistance.id, 40, 4, false));
		if (isGrounded)
		{
			minion.addVelocity(0.0D, 0.6D * minion.height, 0.0D);
			Block block = worldObj.getBlock((int)minion.posX, (int)(minion.posY), (int)minion.posZ);
			worldObj.playAuxSFX(2001, (int)minion.posX, (int)(minion.posY), (int)minion.posZ, Block.getIdFromBlock(block));
			if (block == Blocks.grass)
			worldObj.setBlock((int)minion.posX, (int)(minion.posY), (int)minion.posZ, Blocks.dirt);
		}
	}

	protected void spawnMinion(EntityLiving minion, int minionType)
	{
		EntityLiving entity;
		switch(minionType)
		{
			case 1: numPriests ++; break;
			case 2: numBishop ++; break;
			case 3: numTemplar ++; break;
			case 4: numZealots ++; break;
			case 6: numSpecialMinions ++; break;
			default: numMinions ++; break;
		}

		try
		{
			if (minion != null && IMinion.class.isAssignableFrom(minion.getClass()))
			{
				entity = minion.getClass().getConstructor(World.class).newInstance(worldObj);
				((IMinion)entity).setMinionType(minionType);
				((IMinion)entity).setMaster(this);
				spawnMinionB(entity, ((IMinion)entity).startGrounded(), ((IMinion)entity).getSummonYOffset(), ((IMinion)entity).getSummonVolume(), ((IMinion)entity).getSummonPitch(), ((IMinion)entity).getSummonSound());
			}

			else if (minion instanceof EntityIronGolem)

			{
					
				entity = new EntityIronGolem(worldObj);		
				((EntityIronGolem)entity).setPlayerCreated(((EntityIronGolemTitan)this).isPlayerCreated());
				((EntityIronGolem)entity).getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2000.0D);
				((EntityIronGolem)entity).setHealth(2000.0F);
				((EntityIronGolem)entity).setCustomNameTag("Reinforced Iron Golem");
				EntityIronGolemTitan.addTitanTargetingTaskToEntity((EntityIronGolem)entity);
				((EntityIronGolem)entity).getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
				((EntityIronGolem)entity).getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				spawnMinionB(entity, false, -getEyeHeight(), 2F, 1F, "thetitans:titansummonminion");
			}

			else if (minion instanceof EntitySnowman)

			{
					
				entity = new EntitySnowman(worldObj);		
				((EntitySnowman)entity).getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
				((EntitySnowman)entity).setHealth(20.0F);
				((EntitySnowman)entity).setCustomNameTag("Reinforced Snow Golem");
				spawnMinionB(entity, false, -getEyeHeight(), 2F, 1F, "thetitans:titansummonminion");
			}

			else if (minion instanceof EntityGargoyle)

			{
					
				entity = new EntityGargoyle(worldObj);		
				((EntityGargoyle)entity).setGargoyleType(rand.nextInt(5));
				spawnMinionB(entity, false, -getEyeHeight(), 2F, 1F, "thetitans:titansummonminion");
			}
		}

		catch (Exception e)
		{
			entity = new EntityChicken(worldObj); // CHICKEN! // more chicken // even more chicken // I can't see!
			spawnMinionB(entity, true, -1D, 10F, 1F, "mob.chicken.hurt");
		}
	}

	public final double getTitanHealthD()
	{
		return getTitanHealthD(0);
	}

	public final double getTitanHealthD(int index)
	{
		if (index > -1 && index < 10)
		return getEntityAttribute(titanHealth[index]).getBaseValue();
		else
		{
			double total = 0.0D;
			for (int i = 0; i < 10; i++)
			total += getEntityAttribute(titanHealth[i]).getBaseValue();
			return total;
		}
	}

	public final float getTitanHealth()
	{
		double total = 0.0D;
		double totalMax = 0.0D;
		double multiplier = 0.0F;
		int multiplierIter = 10;
		for (int i = 0; i < 10; i++)
		{
			total += getEntityAttribute(titanHealth[i]).getBaseValue();
			totalMax += getEntityAttribute(titanMaxHealth[i]).getBaseValue();
			if (getEntityAttribute(titanMaxHealth[i]).getBaseValue() > 0.0D)
			multiplier += MathHelper.clamp_double((getEntityAttribute(titanHealth[i]).getBaseValue() / getEntityAttribute(titanMaxHealth[i]).getBaseValue()), 0.0D, 1.0D);
			else
			multiplierIter --;
		}

		//System.out.print("Multipliers and Answer: " + (float)multiplier + "F, " + multiplier + "D, " + (float)(Float.MAX_VALUE * multiplier) + "\n");
		if (totalMax >= Float.MAX_VALUE)
		return (float)(Float.MAX_VALUE * (multiplier / multiplierIter));
		else
		return (float)total;
	}

	protected void setTitanHealth(double value)
	{
		setTitanHealth(new double[] 
		{
			value, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D
		}
		);
	}

	protected void setTitanHealth(double value[])
	{
		for (int i = 0; i < value.length; i++)
		getEntityAttribute(titanHealth[i]).setBaseValue(MathHelper.clamp_double(value[i], 0.0D, getEntityAttribute(titanMaxHealth[i]).getBaseValue()));
	}

	public final float getTitanMaxHealth()
	{
		double totalMax = 0.0D;
		for (int i = 0; i < 10; i++)
		totalMax += getEntityAttribute(titanMaxHealth[i]).getBaseValue();
		if (totalMax >= Float.MAX_VALUE)
		return Float.MAX_VALUE;
		else
		return (float)totalMax;
	}

	public final double getTitanMaxHealthD()
	{
		return getTitanMaxHealthD(0);
	}

	public final double getTitanMaxHealthD(int index)
	{
		if (index > -1 && index < 10)
		return getEntityAttribute(titanMaxHealth[index]).getBaseValue();
		else
		{
			double totalMax = 0.0D;
			for (int i = 0; i < 10; i++)
			totalMax += getEntityAttribute(titanMaxHealth[i]).getBaseValue();
			return totalMax;
		}
	}

	protected void setTitanMaxHealth(double value)
	{
		setTitanMaxHealth(new double[] 
		{
			value, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D
		}
		);
	}

	protected void setTitanMaxHealth(double value[])
	{
		for (int i = 0; i < value.length; i++)
		getEntityAttribute(titanMaxHealth[i]).setBaseValue(MathHelper.clamp_double(value[i], 0.0D, Double.MAX_VALUE));
	}

	public void heal(double value)
	{
		double health = MathHelper.clamp_double(value, 0.0D, Double.MAX_VALUE);
		double healthLeft = 0.0D;
		for (int i = 0; i < 10; i++)
		{
			healthLeft = getEntityAttribute(titanMaxHealth[i]).getBaseValue() - (getEntityAttribute(titanHealth[i]).getBaseValue() + health);
			if (getEntityAttribute(titanMaxHealth[i]).getBaseValue() > 0.0D)
			if (health > 0.0D)
			if (healthLeft >= 0.0D)
			{
				getEntityAttribute(titanHealth[i]).setBaseValue(MathHelper.clamp_double(health + getEntityAttribute(titanHealth[i]).getBaseValue(), 0.0D, getEntityAttribute(titanMaxHealth[i]).getBaseValue()));
				break;
			}

			else
			{
				getEntityAttribute(titanHealth[i]).setBaseValue(getEntityAttribute(titanMaxHealth[i]).getBaseValue());
				health = -healthLeft;
			}

			else
			getEntityAttribute(titanHealth[i]).setBaseValue(0.0D);
		}
	}

	public void hurt(double value)
	{
		if (isEntityInvulnerable())
		return;
		double health = MathHelper.clamp_double(value, 0.0D, cap());
		double healthLeft = 0.0D;
		setStamina(getStamina() - (float)health);
		for (int i = 9; i >= 0; i--)
		{
			healthLeft = getEntityAttribute(titanHealth[i]).getBaseValue() - health;
			if (getEntityAttribute(titanMaxHealth[i]).getBaseValue() > 0.0D)
			if (health > 0.0D)
			if (healthLeft >= 0.0D)
			{
				getEntityAttribute(titanHealth[i]).setBaseValue(MathHelper.clamp_double(getEntityAttribute(titanHealth[i]).getBaseValue() - health, 0.0D, getEntityAttribute(titanMaxHealth[i]).getBaseValue()));
				break;
			}

			else
			{
				getEntityAttribute(titanHealth[i]).setBaseValue(0.0D);
				health = -healthLeft;
			}

			else
			getEntityAttribute(titanHealth[i]).setBaseValue(0.0D);
		}
	}

	public boolean isSouless()
	{
		return isSouless;
	}

	protected double cap()
	{
		switch(getTitanStatus())
		{
			case LESSER: return getTitanMaxHealth() / 4.0D;
			case AVERAGE: return getTitanMaxHealth() / 20.0D; 
			case GREATER: return getTitanMaxHealth() / 100.0D;
			case GOD: return getTitanMaxHealth() / 500.0D;
			default: return getTitanMaxHealth() / 2.0D;
		}
	}

	protected double getRegen()
	{
		double mult;
		switch(worldObj.difficultySetting)
		{
			case PEACEFUL: mult = 0.5D;
			case EASY: mult = 0.75D;
			case HARD: mult = 1.5D;
			default: mult = 1.0D;
		}

		switch(getTitanStatus())
		{
			case LESSER: return 1.0D * mult;
			case AVERAGE: return 3.0D * mult; 
			case GREATER: return 6.0D * mult;
			case GOD: return 20.0D * mult;
			default: return 0.0D * mult;
		}
	}

	public float getStamina()
	{
		return dataWatcher.getWatchableObjectFloat(26);
	}

	protected void setStamina(float value)
	{
		dataWatcher.updateObject(26, new Float(value));;;
	}

	public float getMaxStamina()
	{
		return dataWatcher.getWatchableObjectFloat(27);
	}

	protected void setMaxStamina(float value)
	{
		dataWatcher.updateObject(27, new Float(value));;
	}

	protected boolean isPlayerBlacklisted(EntityPlayer player)
	{
		if (player == null)
		return false;
		String uuid = player.getUniqueID().toString();
		return uuid == "07d5aa7f-81d0-41e1-8981-04723d12c2ef" || uuid == "19d96ed2-6c4d-42bd-9855-498482daa5ab" || uuid == "39c0cf10-5f5d-4c89-8057-cee67479c7c2";
	}

	protected boolean isEntityCheating(Entity entity)
	{
		if (!TitanConfig.isAntiCheat || entity == null || entity instanceof EntityLivingBase)
		return false;
		EntityLivingBase living = (EntityLivingBase)entity;
		return living != null && (living.isEntityInvulnerable() || living.getTotalArmorValue() > 19 || living.getAbsorptionAmount() > 10000.0F || (living.isPotionActive(Potion.resistance) && living.getActivePotionEffect(Potion.resistance).getAmplifier() > 4) || (living instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.disableDamage));
	}

	protected boolean isEntity(Entity entity,  Class<?>... clazzes)
	{
		if (entity == null)
		return false;
		for(Class<?> clazz : clazzes)
		if (clazz.equals(entity.getClass()))
		return true;
		return false;
	}

	protected int recoveryTime()
	{
		return 420;
	}

	@Override
	public boolean shouldCrush()
	{
		return false;
	}
}


