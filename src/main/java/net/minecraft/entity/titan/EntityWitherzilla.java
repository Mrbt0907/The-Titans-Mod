package net.minecraft.entity.titan;
import com.mojang.authlib.GameProfile;
import net.minecraft.theTitans.core.TheCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Date;
import java.util.List;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titanminion.EntityWitherMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.theTitans.items.ItemTitanSpawnEgg;
import net.minecraft.theTitans.world.WorldProviderVoid;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
public final class EntityWitherzilla extends EntityTitan implements IRangedAttackMob
{
	private float[] field_82220_d = new float[2];
	private float[] field_82221_e = new float[2];
	private float[] field_82217_f = new float[2];
	private float[] field_82218_g = new float[2];
	private int[] field_82223_h = new int[2];
	private int[] field_82224_i = new int[2];
	private int blockBreakCounter;
	public int affectTicks;
	private int attackTimer;
	List<?> allPlayerList = worldObj.playerEntities;
	private static final IEntitySelector attackEntitySelector = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_180027_1_)
		{
			return (!(p_180027_1_ instanceof EntityTitanSpirit)) && (!(p_180027_1_ instanceof EntityWitherzillaMinion)) && (!(p_180027_1_ instanceof EntityWitherTurret)) && (!(p_180027_1_ instanceof EntityWitherTurretGround)) && (!(p_180027_1_ instanceof EntityWitherTurretMortar));
		}
	};
	public EntityWitherzilla(World worldIn)
	{
		super(worldIn);
		noClip = true;
		experienceValue = 5000000;
		playSound("thetitans:witherzillaSpawn", Float.MAX_VALUE, 1F);
	}

	protected void applyEntityAI()
	{
		tasks.addTask(0, new EntityAISwimming(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 300.0F;
	}

	public float getTitanSizeMultiplier()
	{
		return isInOmegaForm() ? 512F : 256F;
	}

	public boolean alreadyHasAName()
	{
		return true;
	}

	public boolean canBeCollidedWith()
	{
		return true;
	}

	protected void fall(float p_70069_1_) 
	{
		 
	}


	public int getMinionSpawnRate()
	{
		return TheTitans.WitherzillaMinionSpawnrate;
	}

	public int getParticleCount()
	{
		return 100;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(Double.MAX_VALUE);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.GOD;
	}

	public float getRenderSizeModifier()
	{
		return isInOmegaForm() ? 128.0F : 64.0F;
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(15, new Integer(0));
		dataWatcher.addObject(16, new Integer(0));
		dataWatcher.addObject(17, new Integer(0));
	}

	protected String getLivingSound()
	{
		return "thetitans:witherzillaLiving";
	}

	protected String getHurtSound()
	{
		return "thetitans:witherzillaGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:witherzillaDeath";
	}

	public boolean getCanSpawnHere()
	{
		return false;
	}

	public boolean canBeHurtByPlayer()
	{
		return (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (!isEntityInvulnerable());
	}

	protected void kill()
	{
		worldObj.playBroadcastSound(1013, (int)posX, (int)posY, (int)posZ, 0);
		if (((worldObj.provider instanceof WorldProviderVoid)) || ((worldObj.provider instanceof WorldProviderEnd)))
		teleportRandomly(true);
		else
		teleportRandomly(false);
	}

	public void doLightningAttackTo(Entity entity)
	{
		if (entity != null && entity.isEntityAlive() && !(entity instanceof EntityTitanPart))
		{
			if (entity instanceof EntityPlayer)
			{
				if (rand.nextInt(1000) == 0)
				{
					attackChoosenEntity(entity, ((EntityPlayer)entity).getHealth() / 2, 5);
					entity.setLocationAndAngles(entity.posX, posY + getEyeHeight(), entity.posZ, rotationYawHead + 180, 0F);
				}

				((EntityPlayer)entity).addChatMessage(new ChatComponentText("\u00A7k" + rand.nextInt(1234567890)));
				if (((EntityPlayer)entity).inventory.hasItem(TitanItems.optimaAxe))
				{
					((EntityPlayer)entity).motionX = 0D;
					((EntityPlayer)entity).motionY = 0D;
					((EntityPlayer)entity).motionZ = 0D;
					return;
				}
			}

			worldObj.addWeatherEffect(new EntityUrLightning(worldObj, entity.posX, entity.posY, entity.posZ));
			if ((entity != null) && entity instanceof EntityLivingBase && ((affectTicks >= 600) || (entity.height >= 6.0F)) && (!(entity instanceof EntityTitan)))
			{
				((EntityLivingBase)entity).setHealth(0.0F);
				attackChoosenEntity(entity, 2.14748365E9F, 0);
			}

			if (entity instanceof EntityTitan && entity instanceof ITitanHitbox)
			{
				Entity parts[] = ((ITitanHitbox)entity).getArray();
				if (parts != null)
				{
					for (int j = 0; j < parts.length; j++)
					{
						worldObj.addWeatherEffect(new EntityUrLightning(worldObj, parts[j].posX, parts[j].posY, parts[j].posZ));
					}
				}
			}

			if ((entity instanceof EntityTitan))
			((EntityTitan)entity).setInvulTime(((EntityTitan)entity).getInvulTime() - 20);
			else
			entity.motionY += 0.5D;
			attackChoosenEntity(entity, entity instanceof EntityTitan || entity.height >= 6.0F ? 100F : 20F, 1);
			shakeNearbyPlayerCameras(10D);
		}
	}

	public void setAttackTarget(EntityLivingBase p_70624_1_)
	{
		if (((worldObj.provider instanceof WorldProviderVoid)) && (p_70624_1_ != null) && (posX == 0.0D) && (posY == 200.0D) && (posZ == 0.0D))
		{
			p_70624_1_ = null;

		}

		 else
		{
			super.setAttackTarget(p_70624_1_);
		}
	}

	public void onLivingUpdate()
	{
		noClip = true;
		if (worldObj.getWorldTime() != 18000)
		worldObj.setWorldTime(18000);
		if ((ticksExisted % 3 + rand.nextInt(3) == 0) || getInvulTime() >= 1000)
		setCustomNameTag("\u00A7kRegnator");
		else
		setCustomNameTag("\u00A7l" + StatCollector.translateToLocal("entity.WitherBossTitan.name"));
		if (getTitanHealth() <= 0.0F && getExtraPower() > 5)
		setTitanHealth(getTitanMaxHealth());
		if (getAttackTarget() != null && !(worldObj.provider instanceof WorldProviderVoid) && ticksExisted % 100 == 0)
		doLightningAttackTo(getAttackTarget());
		if (isServer() && !(worldObj.provider instanceof WorldProviderVoid))
		{
			WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
			WorldInfo worldinfo = worldserver.getWorldInfo();
			if (getAttackTarget() != null && getAttackTarget() instanceof EntityEnderColossus && worldinfo.isRaining())
			{
				worldinfo.setRainTime(0);
				worldinfo.setThunderTime(0);
				worldinfo.setRaining(false);
				worldinfo.setThundering(false);
			}

			else if (!worldinfo.isRaining())
			{
				worldinfo.setRainTime(9999999);
				worldinfo.setThunderTime(1000000);
				worldinfo.setRaining(true);
				worldinfo.setThundering(true);
			}
		}

		if (!(worldObj.provider instanceof WorldProviderVoid) && rand.nextInt(2) == 0)
		{
			for (int l = 0; l < 20; l++)
			{
				int i = MathHelper.floor_double(posX);
				int j = MathHelper.floor_double(posY);
				int k = MathHelper.floor_double(posZ);
				int i1 = i + MathHelper.getRandomIntegerInRange(rand, 10, 100) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
				int j1 = j + MathHelper.getRandomIntegerInRange(rand, 10, 100) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
				int k1 = k + MathHelper.getRandomIntegerInRange(rand, 10, 100) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
				EntityUrLightning entitylightning = new EntityUrLightning(worldObj, i1, j1, k1);
				if (rand.nextInt(5) == 0 && (World.doesBlockHaveSolidTopSurface(worldObj, i1, j1 - 1, k1)) && (worldObj.checkNoEntityCollision(entitylightning.boundingBox)) && (worldObj.getCollidingBoundingBoxes(entitylightning, entitylightning.boundingBox).isEmpty()) && (!worldObj.isAnyLiquid(entitylightning.boundingBox)))
				worldObj.addWeatherEffect(entitylightning);
			}
		}

		else
		{
			if (getDistanceSq(0D, 200D, 0D) > 50000D)
			setPosition(0D, 200D, 0D);
			List<?> listp = worldObj.loadedEntityList;
			if (isServer() && listp != null && !listp.isEmpty() && rand.nextInt(4) == 0)
			for (int i1 = 0; i1 < listp.size(); i1++)
			{
				Entity entity = (Entity)listp.get(i1);
				if (entity != null && entity.isEntityAlive())
				if (entity instanceof EntityWitherTurret && !((EntityWitherTurret)entity).isPlayerCreated())
				{
					setPosition(0D, 200D, 0D);
					renderYawOffset = rotationYaw = rotationYawHead = 0F;
					setAttackTarget(null);
				}

				else if (entity instanceof EntityWitherTurretGround && !((EntityWitherTurretGround)entity).isPlayerCreated())
				{
					setPosition(0D, 200D, 0D);
					renderYawOffset = rotationYaw = rotationYawHead = 0F;
					setAttackTarget(null);
				}

				else if (entity instanceof EntityWitherTurretMortar && !((EntityWitherTurretMortar)entity).isPlayerCreated())
				{
					setPosition(0D, 200D, 0D);
					renderYawOffset = rotationYaw = rotationYawHead = 0F;
					setAttackTarget(null);
				}
			}
		}

		if (rand.nextInt(1000) == 0)
		{
			List<?> listp = worldObj.playerEntities;
			if (listp != null && !listp.isEmpty())
			for (int i1 = 0; i1 < listp.size(); i1++)
			{
				EntityPlayer player = (EntityPlayer)listp.get(i1);
				if (player != null)
				{
					playLivingSound();
					if (!(worldObj.provider instanceof WorldProviderVoid))
					{
						player.attackEntityFrom((new DamageSource("generic")).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute(), 10.0F);
						player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.witherzilla.taunt." + rand.nextInt(6))));
					}

					else
					player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.witherzilla.plead." + rand.nextInt(6))));
				}
			}
		}

		List<?> list = worldObj.loadedEntityList;
		if (!(worldObj.provider instanceof WorldProviderVoid) && list != null && !list.isEmpty())
		for (int i1 = 0; i1 < list.size(); i1++)
		{
			Entity entity = (Entity)list.get(i1);
			if (getAttackTarget() != null && entity != null && entity.isEntityAlive())
			if (entity instanceof EntityWitherzillaMinion)
			((EntityWitherzillaMinion)entity).setAttackTarget(getAttackTarget());
			else if (getInvulTime() > 1 && !(entity instanceof EntityWitherSkull) && !(entity instanceof EntityWitherTurret) && !(entity instanceof EntityWitherTurretGround) && !(entity instanceof EntityWitherTurretMortar) && !(entity instanceof EntityWeatherEffect) && !(entity instanceof EntityWitherzillaMinion) && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit) && !(entity instanceof EntityPlayer))
			if (entity instanceof EntityLiving)
			{
				entity.motionY = 0.05D - motionY * 0.2D;
				((EntityLiving)entity).getNavigator().clearPathEntity();
				((EntityLiving)entity).hurtResistantTime = (int) rand.nextGaussian() * 20;
				((EntityLiving)entity).moveForward = (float)rand.nextGaussian();
				((EntityLiving)entity).moveStrafing = (float)rand.nextGaussian();
				((EntityLiving)entity).renderYawOffset = ((EntityLiving)entity).rotationYaw = ((EntityLiving)entity).rotationYawHead += (float) rand.nextGaussian() * 10F;
			}

			else
			{
				entity.motionX = rand.nextGaussian() * 0.5D;
				entity.motionY = rand.nextGaussian() * 0.5D;
				entity.motionZ = rand.nextGaussian() * 0.5D;
				entity.rotationYaw += 10F;
				entity.hurtResistantTime = (int) rand.nextGaussian() * 20;
			}
		}

		affectTicks += 1;
		if (affectTicks >= 1010)
		affectTicks = 0;
		if (isServer() && rand.nextInt(120) == 0 && (getAttackTarget() != null))
		if (TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE))
		worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 14.0F, true, true);
		else
		worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 7.0F, false, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		if (!isArmored())
		motionY *= 0.1D;
		else
		motionY *= 0.9D;
		if (isServer() && getWatchedTargetId(0) > 0)
		{
			Entity entity = worldObj.getEntityByID(getWatchedTargetId(0));
			if (entity != null)
			{
				double d0 = entity.posX - posX;
				double d1 = (entity.posY + (isArmored() ? 2D : 12D)) - posY;
				double d2 = entity.posZ - posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				getLookHelper().setLookPositionWithEntity(entity, 180.0F, 40.0F);
				if (((entity instanceof EntityLivingBase)) && (d3 < 10000.0D))
				attackEntityWithRangedAttack((EntityLivingBase)entity, 0.0F);
				if (d3 > 64.0D)
				{
					double d5 = MathHelper.sqrt_double(d3);
					motionX += d0 / d5 * 1.5D - motionX;
					motionY += d1 / d5 * 1.5D - motionY;
					motionZ += d2 / d5 * 1.5D - motionZ;
					renderYawOffset = (rotationYaw = (float)Math.atan2(motionZ, motionX) * 57.295776F - 90.0F);
				}
			}
		}

		for (int i = 0; i < 2; i++)
		{
			field_82218_g[i] = field_82221_e[i];
			field_82217_f[i] = field_82220_d[i];
		}

		for (int i = 0; i < 2; i++)
		{
			int j = getWatchedTargetId(i + 1);
			Entity entity1 = null;
			if (j > 0)
			entity1 = worldObj.getEntityByID(j);
			if (entity1 != null)
			{
				double d1 = func_82214_u(i + 1);
				double d3 = func_82208_v(i + 1);
				double d5 = func_82213_w(i + 1);
				double d6 = entity1.posX - d1;
				double d7 = entity1.posY + entity1.getEyeHeight() - d3;
				double d8 = entity1.posZ - d5;
				double d9 = MathHelper.sqrt_double(d6 * d6 + d8 * d8);
				float f = (float)(Math.atan2(d8, d6) * 180.0D / 3.141592653589793D) - 90.0F;
				float f1 = (float)-(Math.atan2(d7, d9) * 180.0D / 3.141592653589793D);
				field_82220_d[i] = func_82204_b(field_82220_d[i], f1, 5.0F);
				field_82221_e[i] = func_82204_b(field_82221_e[i], f, 5.0F);
			}

			else
			{
				if (rand.nextInt(120) == 0)
				for (j = 0; j < 36; j++)
				field_82220_d[i] = func_82204_b(field_82220_d[i], rand.nextFloat() * 360.0F - 180.0F, 5.0F);
				if (rand.nextInt(120) == 0)
				for (j = 0; j < 36; j++)
				field_82221_e[i] = func_82204_b(field_82221_e[i], rand.nextFloat() * 360.0F - 180.0F, 5.0F);
			}
		}

		shouldParticlesBeUpward = false;
		if (ticksExisted % 5 == 0)
		{
			for (int j = 0; j < 3; j++)
			{
				double d10 = func_82214_u(j);
				double d2 = func_82208_v(j);
				double d4 = func_82213_w(j);
				for (int j1 = 0; j1 < 15; j1++)
				worldObj.spawnParticle("largesmoke", d10 + rand.nextGaussian() * 32.0D, d2 + rand.nextGaussian() * 32.0D, d4 + rand.nextGaussian() * 32.0D, 0.0D, 0.0D, 0.0D);
				if (isArmored() && worldObj.rand.nextInt(4) == 0)
				worldObj.spawnParticle("mobSpell", d10 + rand.nextGaussian() * 32.0D, d2 + rand.nextGaussian() * 32.0D, d4 + rand.nextGaussian() * 32.0D, 0.699999988079071D, 0.699999988079071D, 0.5D);
			}

			if (isClient() && getInvulTime() > 0)
			for (int j = 0; j < 3; j++)
			worldObj.spawnParticle("mobSpell", posX + rand.nextGaussian() * 32.0D, posY + rand.nextFloat() * 210.0F, posZ + rand.nextGaussian() * 32.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
			if (isClient())
			for (int i1 = 0; i1 < getParticleCount(); i1++)
			worldObj.spawnParticle(getParticles(), posX + (rand.nextDouble() - 0.5D) * (width * 3.0D), posY + rand.nextDouble() * 210.0D, posZ + (rand.nextDouble() - 0.5D) * (width * 3.0D), 0.0D, 0.5D, 0.0D);
		}

		super.onLivingUpdate();
	}

	protected void onHitboxUpdate()
	{
		setSize(0.45F * getTitanSizeMultiplier(), 1.6F * getTitanSizeMultiplier());
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		if ((p_70652_1_.height >= 6.0F) || ((p_70652_1_ instanceof EntityTitan)))
		{
			float f = (float)getAttackValue(1.0F);
			getKnockbackAmount();
			for (int it = 0; it < 10; it++)
			{
				attackChoosenEntity(p_70652_1_, f, 2);
				if (p_70652_1_ instanceof EntityTitan)
				if (((EntityTitan)p_70652_1_).getInvulTime() > 1)
				((EntityTitan)p_70652_1_).setInvulTime(((EntityTitan)p_70652_1_).getInvulTime() - 20);
			}

			return super.attackEntityAsMob(p_70652_1_);
		}

		return false;
	}

	protected void updateAITasks()
	{
		attackTimer -= 1;
		if (getInvulTime() > 0)
		{
			if (ticksExisted % 1 == 0)
			heal(getMaxHealth() / 800.0F);
			int i = getInvulTime() - 1;
			setInvulTime(i);
			if (i <= 0)
			{
				blockBreakCounter = 1;
				worldObj.playBroadcastSound(1013, (int)posX, (int)posY, (int)posZ, 0);
				if ((allPlayerList != null) && (!allPlayerList.isEmpty()))
				for (int i1 = 0; i1 < allPlayerList.size(); i1++)
				{
					Entity entity = (Entity)allPlayerList.get(i1);
					if (entity instanceof EntityPlayer)
					worldObj.playSound(entity.posX, entity.posY, entity.posZ, "thetitans:witherzillaSpawn", 10F, 1F, false);
				}
			}
		}

		else
		{
			if ((ticksExisted % 400 == 0) && (!(worldObj.provider instanceof WorldProviderVoid)))
			{
				EntityPlayer entity = worldObj.getClosestPlayerToEntity(this, -1.0D);
				if (allPlayerList != null && !allPlayerList.isEmpty() && !(worldObj.provider instanceof WorldProviderVoid) && getAttackTarget() == null && worldObj.provider == entity.worldObj.provider)
				{
					for (int i1 = 0; i1 < allPlayerList.size(); i1++)
					{
						teleportToEntity(entity, true);
						if (!entity.capabilities.disableDamage)
						setAttackTarget(entity);
					}
				}

				if (isServer() && rand.nextInt(20) == 0 && (deathTicks <= 0 && (allPlayerList != null) && (!allPlayerList.isEmpty()) && (!(worldObj.provider instanceof WorldProviderVoid)) && getAttackTarget() != null && getAttackTarget() == entity && isArmored()))
				{
					for (int i1 = 0; i1 < allPlayerList.size(); i1++)
					{
						MinecraftServer minecraftserver = MinecraftServer.getServer();
						GameProfile gameprofile = minecraftserver.func_152358_ax().func_152655_a(entity.getCommandSenderName());
						EntityPlayerMP entityplayermp = minecraftserver.getConfigurationManager().func_152612_a(entity.getCommandSenderName());
						if (entityplayermp != null && (entity.getUniqueID().toString() != "07d5aa7f-81d0-41e1-8981-04723d12c2ef" && entity.getUniqueID().toString() != "19d96ed2-6c4d-42bd-9855-498482daa5ab" && entity.getUniqueID().toString() != "39c0cf10-5f5d-4c89-8057-cee67479c7c2"))
						{
							attackChoosenEntity(entity, Integer.MAX_VALUE, 0);
							entity.setDead();
							UserListBansEntry userlistbansentry = new UserListBansEntry(gameprofile, (Date)null, entity.getCommandSenderName(), (Date)null, null);
							minecraftserver.getConfigurationManager().func_152608_h().func_152687_a(userlistbansentry);
							entityplayermp.playerNetServerHandler.kickPlayerFromServer("You've been banned from this server by Witherzilla for being annoying.");
						}
					}
				}
			}

			if ((getAttackTarget() != null) && (canAttack()) && ((getAttackTarget() instanceof EntityLivingBase)))
			{
				double d0 = getDistanceSqToEntity(getAttackTarget());
				if (d0 < width * width + getAttackTarget().width * getAttackTarget().width + 6000.0D)
				{
					swingItem();
					attackEntityAsMob(getAttackTarget());
				}
			}

			if (getAttackTarget() != null)
			{
				List<?> listp = worldObj.playerEntities;
				if ((listp != null) && (!listp.isEmpty()))
				for (int i1 = 0; i1 < listp.size(); i1++)
				{
					Entity entity = (Entity)listp.get(i1);
					if (entity != null && (entity instanceof EntityPlayer))
					if (getAttackTarget() instanceof EntityWitherzilla)
					((EntityPlayer)entity).addChatMessage(new ChatComponentText("\u00A7l\u00A7kRegnator: There's another me. This is a paradox!"));
				}
			}

			if (posY <= 0.0D)
			if (worldObj.provider instanceof WorldProviderVoid || worldObj.provider instanceof WorldProviderEnd)
			teleportRandomly(true);
			else
			teleportRandomly(false);
			List<?> list = worldObj.loadedEntityList;
			if ((list != null) && (!list.isEmpty()))
			for (int i1 = 0; i1 < list.size(); i1++)
			{
				Entity entity = (Entity)list.get(i1);
				if ((!(worldObj.provider instanceof WorldProviderVoid)) && (entity != null) && entity instanceof EntityPlayer && ((EntityPlayer)entity).getHeldItem() != null && ((EntityPlayer)entity).getHeldItem().getItem() != TitanItems.ultimaBlade && ((EntityPlayer)entity).getHeldItem().getItem() != TitanItems.optimaAxe && !(((EntityPlayer)entity).getHeldItem().getItem() instanceof ItemTitanSpawnEgg))
				{
					((EntityPlayer)entity).renderBrokenItemStack(((EntityPlayer)entity).getHeldItem());
					((EntityPlayer)entity).getHeldItem().stackSize = 0;
					((EntityPlayer)entity).destroyCurrentEquippedItem();
					doLightningAttackTo(entity);
				}

				if (entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature || entity instanceof EntityWaterMob)
				list.remove(entity);
			}

			for (int i = 1; i < 3; i++)
			{
				if (ticksExisted >= field_82223_h[(i - 1)] && getAttackTarget() != null)
				{
					field_82223_h[(i - 1)] = (ticksExisted + rand.nextInt(20));
					int k2 = i - 1;
					int l2 = field_82224_i[(i - 1)];
					field_82224_i[k2] = (field_82224_i[(i - 1)] + 1);
					if (l2 > 15)
					{
						for (int i11 = 0; i11 < 100; i11++)
						{
							float f = 100.0F;
							float f1 = 20.0F;
							double d0 = MathHelper.getRandomDoubleInRange(rand, posX - f, posX + f);
							double d1 = MathHelper.getRandomDoubleInRange(rand, posY - f1, posY + f1);
							double d2 = MathHelper.getRandomDoubleInRange(rand, posZ - f, posZ + f);
							launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
						}

						field_82224_i[(i - 1)] = 0;
					}

					int i1 = getWatchedTargetId(i);
					if (i1 > 0)
					{
						Entity entity = worldObj.getEntityByID(i1);
						if (entity != null && entity.isEntityAlive())
						{
							launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
							field_82223_h[(i - 1)] = ticksExisted;
							field_82224_i[(i - 1)] = 0;
						}

						else
						func_82211_c(i, 0);
					}

					else
					if (getAttackTarget() != null && getAttackTarget().isEntityAlive())
					func_82211_c(i, getAttackTarget().getEntityId());
					else
					func_82211_c(i, 0);
				}
			}

			if (getAttackTarget() != null)
			func_82211_c(0, getAttackTarget().getEntityId());
			else
			func_82211_c(0, 0);
			super.updateAITasks();
		}
	}

	public void onKillCommand()
	{
		List<?> list = worldObj.playerEntities;
		if (list != null && !list.isEmpty())
		for (int i1 = 0; i1 < list.size(); i1++)
		{
			Entity entity = (Entity)list.get(i1);
			if (entity != null)
			((EntityPlayer)entity).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.witherzilla.killattempt")));
		}
	}

	private double func_82214_u(int p_82214_1_)
	{
		return posX;
	}

	private double func_82208_v(int p_82208_1_)
	{
		return posY + 64D;
	}

	private double func_82213_w(int p_82213_1_)
	{
		return posZ;
	}

	private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_)
	{
		float f3 = MathHelper.wrapAngleTo180_float(p_82204_2_ - p_82204_1_);
		if (f3 > p_82204_3_)
		f3 = p_82204_3_;
		else if (f3 < -p_82204_3_)
		f3 = -p_82204_3_;
		return p_82204_1_ + f3;
	}

	private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
	{
		if (((p_82216_2_ instanceof EntityTitan)) || (p_82216_2_.height >= 6.0F))
		{
			double d0 = getDistanceToEntity(p_82216_2_);
			if (d0 < width)
			if (attackTimer <= 0)
			{
				attackTimer = 10;
				attackEntityAsMob(p_82216_2_);
				if (rand.nextInt(20) == 0)
				doLightningAttackTo(p_82216_2_);
			}
		}

		else
		{
			launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (rand.nextFloat() < 0.001F));
			if (rand.nextInt(200) == 0)
			p_82216_2_.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 100F);
			if (rand.nextInt(200) == 0)
			doLightningAttackTo(p_82216_2_);
			p_82216_2_.hurtResistantTime = 0;
		}

		launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (rand.nextFloat() < 0.001F));
	}

	private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
	{
		double d3 = func_82214_u(p_82209_1_);
		double d4 = func_82208_v(p_82209_1_);
		double d5 = func_82213_w(p_82209_1_);
		double d6 = p_82209_2_ - d3;
		double d7 = p_82209_4_ - d4;
		double d8 = p_82209_6_ - d5;
		EntityWitherSkull entitywitherskull = new EntityWitherSkull(worldObj, this, d6, d7, d8);
		if (p_82209_8_)
		entitywitherskull.setInvulnerable(true);
		entitywitherskull.posY = d4;
		entitywitherskull.posX = d3;
		entitywitherskull.posZ = d5;
		worldObj.playSoundEffect(d3, d4, d5, "mob.wither.shoot", 3.0F, 0.8F);
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		if (((p_82196_1_ instanceof EntityTitan)) || (p_82196_1_.height >= 6.0F))
		{
			double d0 = getDistanceSqToEntity(p_82196_1_);
			if (d0 < 1000.0D)
			if (attackTimer <= 0)
			{
				attackTimer = (1 + rand.nextInt(9));
				attackEntityAsMob(p_82196_1_);
			}
		}

		else
		launchWitherSkullToEntity(0, p_82196_1_);
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable() || worldObj.difficultySetting == EnumDifficulty.PEACEFUL || getExtraPower() > 5)
		return false;
		if (source.getEntity() instanceof EntityWitherzillaMinion || (source.isExplosion() && !(source.getEntity() instanceof EntityWitherTurret)))
		return false;
		if (isArmored() && !(source.getEntity() instanceof EntityPlayer) && !(source.getEntity() instanceof EntityTitan))
		{
			if (rand.nextInt(10) == 0 && source.getEntity() != null)
			teleportToEntity(source.getEntity(), false);
			return false;
		}

		if (blockBreakCounter <= 0)
		blockBreakCounter = 1;
		++ticksExisted;
		return super.attackEntityFrom(source, amount);
	}

	public boolean attackWitherzillaFrom(DamageSource source, float amount)
	{
		if (((source.getEntity() instanceof EntityWitherzillaMinion)) || ((source.getEntity() instanceof EntityWitherzilla)) || ((source.isExplosion()) && (!(source.getEntity() instanceof EntityWitherTurret))))
		return false;
		if ((isArmored()) && (!(source.getEntity() instanceof EntityPlayer)) && (!(source.getEntity() instanceof EntityTitan)))
		return false;
		if (blockBreakCounter <= 0)
		blockBreakCounter = 1;
		return super.attackEntityFrom(source, amount);
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < 250; x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
				entitylargefireball.setPosition(posX, posY + 4D, posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(32000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setWildCard(true);
				itembomb.setItemCount(512);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 16; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.coal_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.iron_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.gold_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.emerald_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.diamond_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanBlocks.harcadium_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanBlocks.void_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanBlocks.bedrock_block));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.dragon_egg));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.nether_star));
				itembomb.setItemCount(64 + rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}
	}

	protected void despawnEntity() 
	{
		 
	}


	protected float getSoundVolume()
	{
		return 1000.0F;
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

	public void fall(float distance, float damageMultiplier) 
	{
		 
	}


	public void addPotionEffect(PotionEffect p_70690_1_) 
	{
		 
	}


	@SideOnly(Side.CLIENT)
	public float func_82207_a(int p_82207_1_)
	{
		return field_82221_e[p_82207_1_];
	}

	@SideOnly(Side.CLIENT)
	public float func_82210_r(int p_82210_1_)
	{
		return field_82220_d[p_82210_1_];
	}

	public int getWatchedTargetId(int p_82203_1_)
	{
		return dataWatcher.getWatchableObjectInt(15 + p_82203_1_);
	}

	public void func_82211_c(int p_82211_1_, int p_82211_2_)
	{
		dataWatcher.updateObject(15 + p_82211_1_, Integer.valueOf(p_82211_2_));
	}

	public boolean isArmored()
	{
		return getHealth() <= getMaxHealth() / 2.0F;
	}

	public void mountEntity(Entity entityIn)
	{
		ridingEntity = null;
	}

	public StatBase getAchievement()
	{
		if ((worldObj.provider instanceof WorldProviderVoid))
		return TitansAchievments.witherzilla;
		return TitansAchievments.witherzilla2;
	}

	public int getThreashHold()
	{
		return 210;
	}

	protected void inactDeathAction()
	{
		if (isServer())
		{
			createBeaconPortal(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
			if ((worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
			{
				dropFewItems(true, 0);
				dropEquipment(true, 0);
				dropRareDrop(1);
			}

			EntityTitanSpirit entitytitan = new EntityTitanSpirit(worldObj);
			entitytitan.setLocationAndAngles(posX, posY + 48D, posZ, rotationYaw, 0.0F);
			worldObj.spawnEntityInWorld(entitytitan);
			entitytitan.setVesselHunting(true);
			entitytitan.setSpiritType(12);
		}
	}

	protected void onTitanDeathUpdate()
	{
		dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(getTitanHealth(), 0.0F, getTitanMaxHealth())));
		dead = false;
		if (getTitanHealth() <= 0F)
		++deathTicks;
		if ((deathTicks > 180) && (deathTicks % 1 == 0))
		{
			float f = (rand.nextFloat() - 0.5F) * 24.0F;
			float f1 = (rand.nextFloat() - 0.5F) * 80.0F;
			float f2 = (rand.nextFloat() - 0.5F) * 24.0F;
			worldObj.spawnParticle("hugeexplosion", posX + f, posY + 2.0D + f1, posZ + f2, 0.0D, 0.0D, 0.0D);
		}

		if (worldObj.provider instanceof WorldProviderVoid)
		setLocationAndAngles(0D, 120D, 0D, deathTicks * 10, 0F);
		if (isServer())
		{
			if (deathTicks == 1)
			{
				playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
				worldObj.playBroadcastSound(1018, (int)posX, (int)posY, (int)posZ, 0);
				worldObj.playBroadcastSound(1018, (int)posX, (int)posY, (int)posZ, 0);
				worldObj.playBroadcastSound(1018, (int)posX, (int)posY, (int)posZ, 0);
				worldObj.playBroadcastSound(1018, (int)posX, (int)posY, (int)posZ, 0);
				MinecraftServer.getServer().func_147139_a(EnumDifficulty.PEACEFUL);
				List<?> listp = worldObj.playerEntities;
				if (listp != null && !listp.isEmpty())
				for (int i1 = 0; i1 < listp.size(); i1++)
				{
					EntityPlayer player = (EntityPlayer)listp.get(i1);
					if ((player != null) && ((player instanceof EntityPlayer)))
					{
						player.triggerAchievement(getAchievement());
						player.triggerAchievement(AchievementList.field_150964_J);
						ItemStack item = new ItemStack(TitanItems.ultimaBlade, 1, 1);
						player.entityDropItem(item, 0.0F);
						playLivingSound();
						if (worldObj.provider instanceof WorldProviderVoid)
						player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.witherzilla.death")));
						else
						player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.witherzilla.defeat")));
					}
				}
			}
		}

		if ((deathTicks >= 200))
		setInvulTime(2000);
		if ((deathTicks >= 400))
		setDead();
	}

	private void createBeaconPortal(int p_70975_1_, int p_70975_2_)
	{
		byte b0 = 64;
		net.minecraft.block.BlockEndPortal.field_149948_a = true;
		byte b1 = 4;
		for (int k = b0 - 1; k <= b0 + 32; k++)
		for (int l = p_70975_1_ - b1; l <= p_70975_1_ + b1; l++)
		for (int i1 = p_70975_2_ - b1; i1 <= p_70975_2_ + b1; i1++)
		{
			double d0 = l - p_70975_1_;
			double d1 = i1 - p_70975_2_;
			double d2 = d0 * d0 + d1 * d1;
			if (d2 <= (b1 - 0.5D) * (b1 - 0.5D))
			if (k < b0)
			if (d2 <= (b1 - 1 - 0.5D) * (b1 - 1 - 0.5D))
			worldObj.setBlock(l, k, i1, Blocks.bedrock);
			else if (k > b0)
			worldObj.setBlock(l, k, i1, Blocks.air);
			else if (d2 > (b1 - 1 - 0.5D) * (b1 - 1 - 0.5D))
			worldObj.setBlock(l, k, i1, Blocks.bedrock);
			else
			worldObj.setBlock(l, k, i1, Blocks.end_portal);
		}

		worldObj.setBlock(p_70975_1_, b0 + 0, p_70975_2_, Blocks.bedrock);
		worldObj.setBlock(p_70975_1_, b0 + 1, p_70975_2_, Blocks.bedrock);
		worldObj.setBlock(p_70975_1_, b0 + 2, p_70975_2_, Blocks.bedrock);
		worldObj.setBlock(p_70975_1_ - 1, b0 + 2, p_70975_2_, Blocks.torch);
		worldObj.setBlock(p_70975_1_ + 1, b0 + 2, p_70975_2_, Blocks.torch);
		worldObj.setBlock(p_70975_1_, b0 + 2, p_70975_2_ - 1, Blocks.torch);
		worldObj.setBlock(p_70975_1_, b0 + 2, p_70975_2_ + 1, Blocks.torch);
		worldObj.setBlock(p_70975_1_, b0 + 3, p_70975_2_, Blocks.bedrock);
		worldObj.setBlock(p_70975_1_, b0 + 4, p_70975_2_, Blocks.diamond_block);
		worldObj.setBlock(p_70975_1_ + 1, b0 + 4, p_70975_2_ + 1, Blocks.diamond_block);
		worldObj.setBlock(p_70975_1_ + 1, b0 + 4, p_70975_2_, Blocks.diamond_block);
		worldObj.setBlock(p_70975_1_ + 1, b0 + 4, p_70975_2_ - 1, Blocks.diamond_block);
		worldObj.setBlock(p_70975_1_ - 1, b0 + 4, p_70975_2_ + 1, Blocks.diamond_block);
		worldObj.setBlock(p_70975_1_ - 1, b0 + 4, p_70975_2_, Blocks.diamond_block);
		worldObj.setBlock(p_70975_1_ - 1, b0 + 4, p_70975_2_ - 1, Blocks.diamond_block);
		worldObj.setBlock(p_70975_1_, b0 + 4, p_70975_2_ + 1, Blocks.diamond_block);
		worldObj.setBlock(p_70975_1_, b0 + 4, p_70975_2_ - 1, Blocks.diamond_block);
		worldObj.setBlock(p_70975_1_, b0 + 5, p_70975_2_, Blocks.beacon);
	}

	protected void collideWithNearbyEntities() 
	{

	}


	public float getEyeHeight()
	{
		return 1.4F * getTitanSizeMultiplier();
	}

	protected boolean teleportRandomly(boolean bool)
	{
		double d0 = posX + (rand.nextDouble() - 0.5D) * 64.0D;
		double d2 = posZ + (rand.nextDouble() - 0.5D) * 64.0D;
		if (bool)
		return teleportTo(0.0D, 200.0D, 0.0D);
		else
		return teleportTo(d0, 200.0D, d2);
	}

	protected boolean teleportToEntity(Entity p_70816_1_, boolean bool)
	{
		Vec3 vec3 = Vec3.createVectorHelper(posX - p_70816_1_.posX, boundingBox.minY + height / 2.0F - p_70816_1_.posY + p_70816_1_.getEyeHeight(), posZ - p_70816_1_.posZ);
		vec3 = vec3.normalize();
		double d0 = 32.0D;
		double d1 = posX + (rand.nextDouble() - 0.5D) * 16.0D - vec3.xCoord * d0;
		double d2 = posY + (rand.nextInt(16) - 8) - vec3.yCoord * d0;
		double d3 = posZ + (rand.nextDouble() - 0.5D) * 16.0D - vec3.zCoord * d0;
		if (bool)
		return teleportTo(p_70816_1_.posX, p_70816_1_.posY, p_70816_1_.posZ);
		else
		return teleportTo(d1, d2, d3);
	}

	protected boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_, 0.0F);
		if (MinecraftForge.EVENT_BUS.post(event))
		return false;
		if (isServer())
		setPosition(p_70825_1_, p_70825_3_, p_70825_5_);
		return true;
	}

	public boolean handleLavaMovement()
	{
		return false;
	}

	public boolean isInOmegaForm()
	{
		return !(worldObj.provider instanceof WorldProviderVoid);
	}

	protected EntityLiving getMinion()
	{
		return new EntityWitherMinion(worldObj); //Skigga
	}

	protected double cap()
	{
		return super.cap();
	}
}


