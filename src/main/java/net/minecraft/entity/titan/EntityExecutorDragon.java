package net.minecraft.entity.titan;
import com.google.common.collect.Lists;
import net.minecraft.theTitans.core.TheCore;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.minecraft.client.particle.EntityFX;
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
import net.minecraft.theTitans.world.WorldProviderNowhere;
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
public final class EntityExecutorDragon extends EntityTitan implements IRangedAttackMob
{
	private float[] field_82220_d = new float[2];
	private float[] field_82221_e = new float[2];
	private float[] field_82217_f = new float[2];
	private float[] field_82218_g = new float[2];
	private int blockBreakCounter;
	public int affectTicks;
	private int attackTimer;
	@SuppressWarnings("unchecked")
	ArrayList<?> allPlayerList = Lists.newArrayList(this.worldObj.playerEntities);
	private static final IEntitySelector attackEntitySelector = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_180027_1_)
		{
			return (!(p_180027_1_ instanceof EntityTitanSpirit)) && (!(p_180027_1_ instanceof EntityWitherzillaMinion)) && (!(p_180027_1_ instanceof EntityWitherTurret)) && (!(p_180027_1_ instanceof EntityWitherTurretGround)) && (!(p_180027_1_ instanceof EntityWitherTurretMortar));
		}
	};
	public EntityExecutorDragon(World worldIn)
	{
		super(worldIn);
		this.noClip = true;
		this.experienceValue = 5000000;
		this.playSound("thetitans:witherzillaSpawn", Float.MAX_VALUE, 1F);
	}

	protected void onHitboxUpdate()
	{
		super.onHitboxUpdate();
	}

	protected void applyEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 250.0F;
	}

	public float getTitanSizeMultiplier()
	{
		return this.isInOmegaForm() ? 512F : 256F;
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
		this.dataWatcher.addObject(15, new Integer(0));
		this.dataWatcher.addObject(16, new Integer(0));
		this.dataWatcher.addObject(17, new Integer(0));
	}

	protected String getLivingSound()
	{
		return "thetitans:executorDragonLiving";
	}

	protected String getHurtSound()
	{
		return "thetitans:executorDragonGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:executorDragonDeath";
	}

	public boolean getCanSpawnHere()
	{
		return false;
	}

	public boolean canBeHurtByPlayer()
	{
		return (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (!isEntityInvulnerable());
	}

	protected void kill()
	{
		this.worldObj.playBroadcastSound(1013, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
		if (((this.worldObj.provider instanceof WorldProviderNowhere)) || ((this.worldObj.provider instanceof WorldProviderEnd)))
		{
			teleportRandomly(true);
		}

		else
		{
			teleportRandomly(false);
		}
	}

	public void doLightningAttackTo(Entity entity)
	{
		if (entity != null && entity.isEntityAlive() && !(entity instanceof EntityFX) && !(entity instanceof EntityTitanPart))
		{
			if (entity instanceof EntityPlayer)
			{
				if (rand.nextInt(1000) == 0)
				{
					this.attackChoosenEntity(entity, ((EntityPlayer)entity).getHealth() / 2, 5);
					entity.setLocationAndAngles(entity.posX, this.posY + this.getEyeHeight(), entity.posZ, this.rotationYawHead + 180, 0F);
				}

				((EntityPlayer)entity).addChatMessage(new ChatComponentText("\u00A7k" + this.rand.nextInt(1234567890)));
				if (((EntityPlayer)entity).inventory.hasItem(TitanItems.optimaAxe))
				{
					((EntityPlayer)entity).motionX = 0D;
					((EntityPlayer)entity).motionY = 0D;
					((EntityPlayer)entity).motionZ = 0D;
					return;
				}
			}

			this.worldObj.addWeatherEffect(new EntityUrLightning(this.worldObj, entity.posX, entity.posY, entity.posZ));
			if ((entity != null) && entity instanceof EntityLivingBase && ((this.affectTicks >= 600) || (entity.height >= 6.0F)) && (!(entity instanceof EntityTitan)))
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
						this.worldObj.addWeatherEffect(new EntityUrLightning(this.worldObj, parts[j].posX, parts[j].posY, parts[j].posZ));
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
		if (((this.worldObj.provider instanceof WorldProviderNowhere)) && (p_70624_1_ != null) && (this.posX == 0.0D) && (this.posY == 200.0D) && (this.posZ == 0.0D))
		{
			p_70624_1_ = null;

		}

		 else 
		{

			super.setAttackTarget(p_70624_1_);
		}
	}

	@SuppressWarnings("unchecked")
	public void onLivingUpdate()
	{
		this.noClip = true;
		if (!(this.worldObj.provider instanceof WorldProviderNowhere))
		{
			WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
			WorldInfo worldinfo = worldserver.getWorldInfo();
			if (this.getAttackTarget() != null && this.getAttackTarget() instanceof EntityEnderColossus)
			{
				worldinfo.setRainTime(0);
				worldinfo.setThunderTime(0);
				worldinfo.setRaining(false);
				worldinfo.setThundering(false);
			}

			else
			{
				worldinfo.setRainTime(9999999);
				worldinfo.setThunderTime(1000000);
				worldinfo.setRaining(true);
				worldinfo.setThundering(true);
			}
		}

		if (!(this.worldObj.provider instanceof WorldProviderNowhere))
		{
			if (this.rand.nextInt(2) == 0)
			{
				for (int l = 0; l < 20; l++)
				{
					int i = MathHelper.floor_double(this.posX);
					int j = MathHelper.floor_double(this.posY);
					int k = MathHelper.floor_double(this.posZ);
					int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, 10, 100) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
					int j1 = j + MathHelper.getRandomIntegerInRange(this.rand, 10, 100) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
					int k1 = k + MathHelper.getRandomIntegerInRange(this.rand, 10, 100) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
					EntityUrLightning entitylightning = new EntityUrLightning(this.worldObj, i1, j1, k1);
					if (this.rand.nextInt(5) == 0 && (World.doesBlockHaveSolidTopSurface(this.worldObj, i1, j1 - 1, k1)) && (this.worldObj.checkNoEntityCollision(entitylightning.boundingBox)) && (this.worldObj.getCollidingBoundingBoxes(entitylightning, entitylightning.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(entitylightning.boundingBox)))
					{
						this.worldObj.addWeatherEffect(entitylightning);
					}
				}
			}
		}

		else
		{
			if (this.getDistanceSq(0D, 200D, 0D) > 50000D)
			this.setPosition(0D, 200D, 0D);
			ArrayList<?> listp = Lists.newArrayList(this.worldObj.loadedEntityList);
			if (!this.worldObj.isRemote && listp != null && !listp.isEmpty() && rand.nextInt(4) == 0)
			{
				for (int i1 = 0; i1 < listp.size(); i1++)
				{
					Entity entity = (Entity)listp.get(i1);
					if (entity != null && entity.isEntityAlive())
					{
						if (entity instanceof EntityWitherTurret && !((EntityWitherTurret)entity).isPlayerCreated())
						{
							this.setPosition(0D, 200D, 0D);
							this.renderYawOffset = this.rotationYaw = this.rotationYawHead = 0F;
							this.setAttackTarget(null);
						}

						if (entity instanceof EntityWitherTurretGround && !((EntityWitherTurretGround)entity).isPlayerCreated())
						{
							this.setPosition(0D, 200D, 0D);
							this.renderYawOffset = this.rotationYaw = this.rotationYawHead = 0F;
							this.setAttackTarget(null);
						}

						if (entity instanceof EntityWitherTurretMortar && !((EntityWitherTurretMortar)entity).isPlayerCreated())
						{
							this.setPosition(0D, 200D, 0D);
							this.renderYawOffset = this.rotationYaw = this.rotationYawHead = 0F;
							this.setAttackTarget(null);
						}
					}
				}
			}
		}

		setSize(0.45F * this.getTitanSizeMultiplier(), 1.6F * this.getTitanSizeMultiplier());
		this.worldObj.setWorldTime(18000);
		ArrayList<?> listp = Lists.newArrayList(this.worldObj.playerEntities);
		if ((listp != null) && (!listp.isEmpty()) && rand.nextInt(4) == 0)
		{
			for (int i1 = 0; i1 < listp.size(); i1++)
			{
				Entity entity = (Entity)listp.get(i1);
				if ((entity != null) && ((entity instanceof EntityPlayer)) && (this.rand.nextInt(100) == 0))
				{
					playLivingSound();
					if (!(this.worldObj.provider instanceof WorldProviderNowhere))
					{
						((EntityPlayer)entity).attackEntityFrom((new DamageSource("generic")).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute(), 10.0F);
						((EntityPlayer)entity).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.executordragon.taunt." + this.rand.nextInt(6))));
					}

					else
					{
						((EntityPlayer)entity).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.executordragon.plead." + this.rand.nextInt(6))));
					}
				}
			}
		}

		List<?> list = this.worldObj.loadedEntityList;
		if ((list != null) && (!list.isEmpty()) && !(this.worldObj.provider instanceof WorldProviderNowhere))
		{
			for (int i1 = 0; i1 < list.size(); i1++)
			{
				Entity entity = (Entity)list.get(i1);
				if ((entity != null) && entity.isEntityAlive() && (getAttackTarget() != null) && ((entity instanceof EntityWitherzillaMinion)))
				{
					((EntityWitherzillaMinion)entity).setAttackTarget(getAttackTarget());
				}

				if ((entity != null) && entity.isEntityAlive() && !(entity instanceof EntityWitherzillaMinion) && !(entity instanceof EntityWitherSkull) && !(entity instanceof EntityWitherTurret) && !(entity instanceof EntityWitherTurretGround) && !(entity instanceof EntityWitherTurretMortar) && !(entity instanceof EntityWeatherEffect) && !(entity instanceof EntityWitherzillaMinion) && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit) && !(entity instanceof EntityPlayer))
				{
					if (this.getInvulTime() > 1)
					{
						if (entity instanceof EntityLivingBase)
						{
							if (entity instanceof EntityLiving)
							{
								((EntityLiving)entity).getNavigator().clearPathEntity();
							}

							entity.motionY = 0.05D - this.motionY * 0.2D;
							((EntityLivingBase)entity).hurtResistantTime = (int) rand.nextGaussian() * 20;
							((EntityLivingBase)entity).moveForward = (float)rand.nextGaussian();
							((EntityLivingBase)entity).moveStrafing = (float)rand.nextGaussian();
							((EntityLivingBase)entity).renderYawOffset = ((EntityLivingBase)entity).rotationYaw = ((EntityLivingBase)entity).rotationYawHead += (float) rand.nextGaussian() * 10F;
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
				}
			}
		}

		if ((this.ticksExisted % 3 + this.rand.nextInt(3) == 0) || this.getInvulTime() >= 1000)
		setCustomNameTag("\u00A7kFrotheru");
		else
		setCustomNameTag("\u00A7l" + StatCollector.translateToLocal("entity.EnderDragonTitan.name"));
		if (getAttackTarget() != null && !(this.worldObj.provider instanceof WorldProviderNowhere) && this.ticksExisted % 100 == 0)
		doLightningAttackTo(getAttackTarget());
		this.affectTicks += 1;
		if (this.affectTicks >= 1010)
		{
			this.affectTicks = 0;
		}

		if ((this.numMinions < getMinionCap()) && (this.rand.nextInt(getMinionSpawnRate()) == 0) && (getHealth() > 0.0F) && (!this.worldObj.isRemote))
		{
			EntityWitherzillaMinion entitychicken = new EntityWitherzillaMinion(this.worldObj);
			entitychicken.setLocationAndAngles(this.posX, this.posY + getEyeHeight(), this.posZ, this.rotationYaw, 0.0F);
			entitychicken.func_82206_m();
			this.worldObj.spawnEntityInWorld(entitychicken);
			entitychicken.addVelocity(-MathHelper.sin(this.rotationYawHead * 3.1415927F / 180.0F) * 5F, -2D, MathHelper.cos(this.rotationYawHead * 3.1415927F / 180.0F) * 5F);
			this.numMinions += 1;
		}

		if ((this.rand.nextInt(120) == 0) && (getAttackTarget() != null) && (!this.worldObj.isRemote))
		{
			if (TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) == true)
			{
				this.worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 14.0F, true, true);
			}

			else
			{
				this.worldObj.newExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			}
		}

		if (!isArmored())
		{
			this.motionY *= 0.1D;
		}

		else
		{
			this.motionY *= 0.9D;
		}

		if ((!this.worldObj.isRemote) && (getWatchedTargetId(0) > 0))
		{
			Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
			if (entity != null)
			{
				double d0 = entity.posX - this.posX;
				double d1 = (entity.posY + (this.isArmored() ? 2D : 12D)) - this.posY;
				double d2 = entity.posZ - this.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				getLookHelper().setLookPositionWithEntity(entity, 180.0F, 40.0F);
				if (((entity instanceof EntityLivingBase)) && (d3 < 10000.0D))
				{
					attackEntityWithRangedAttack((EntityLivingBase)entity, 0.0F);
				}

				if (d3 > 64.0D)
				{
					double d5 = MathHelper.sqrt_double(d3);
					this.motionX += d0 / d5 * 1.5D - this.motionX;
					this.motionY += d1 / d5 * 1.5D - this.motionY;
					this.motionZ += d2 / d5 * 1.5D - this.motionZ;
					this.renderYawOffset = (this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F);
				}
			}
		}

		super.onLivingUpdate();
		for (int i = 0; i < 2; i++)
		{
			this.field_82218_g[i] = this.field_82221_e[i];
			this.field_82217_f[i] = this.field_82220_d[i];
		}

		for (int i = 0; i < 2; i++)
		{
			int j = getWatchedTargetId(i + 1);
			Entity entity1 = null;
			if (j > 0)
			{
				entity1 = this.worldObj.getEntityByID(j);
			}

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
				this.field_82220_d[i] = func_82204_b(this.field_82220_d[i], f1, 5.0F);
				this.field_82221_e[i] = func_82204_b(this.field_82221_e[i], f, 5.0F);
			}

			else
			{
				if (this.rand.nextInt(120) == 0)
				{
					for (j = 0; j < 36; j++)
					{
						this.field_82220_d[i] = func_82204_b(this.field_82220_d[i], this.rand.nextFloat() * 360.0F - 180.0F, 5.0F);
					}
				}

				if (this.rand.nextInt(120) == 0)
				{
					for (j = 0; j < 36; j++)
					{
						this.field_82221_e[i] = func_82204_b(this.field_82221_e[i], this.rand.nextFloat() * 360.0F - 180.0F, 5.0F);
					}
				}
			}
		}

		this.shouldParticlesBeUpward = false;
		boolean flag = isArmored();
		for (int j = 0; j < 3; j++)
		{
			double d10 = func_82214_u(j);
			double d2 = func_82208_v(j);
			double d4 = func_82213_w(j);
			for (int j1 = 0; j1 < 15; j1++)
			this.worldObj.spawnParticle("largesmoke", d10 + this.rand.nextGaussian() * 32.0D, d2 + this.rand.nextGaussian() * 32.0D, d4 + this.rand.nextGaussian() * 32.0D, 0.0D, 0.0D, 0.0D);
			if ((flag) && (this.worldObj.rand.nextInt(4) == 0))
			{
				this.worldObj.spawnParticle("mobSpell", d10 + this.rand.nextGaussian() * 32.0D, d2 + this.rand.nextGaussian() * 32.0D, d4 + this.rand.nextGaussian() * 32.0D, 0.699999988079071D, 0.699999988079071D, 0.5D);
			}
		}

		if (getInvulTime() > 0)
		{
			for (int j = 0; j < 3; j++)
			{
				this.worldObj.spawnParticle("mobSpell", this.posX + this.rand.nextGaussian() * 32.0D, this.posY + this.rand.nextFloat() * 210.0F, this.posZ + this.rand.nextGaussian() * 32.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
			}
		}

		if (this.worldObj.isRemote)
		{
			for (int i1 = 0; i1 < getParticleCount(); i1++)
			{
				this.worldObj.spawnParticle(getParticles(), this.posX + (this.rand.nextDouble() - 0.5D) * (this.width * 3.0D), this.posY + this.rand.nextDouble() * 210.0D, this.posZ + (this.rand.nextDouble() - 0.5D) * (this.width * 3.0D), 0.0D, 0.5D, 0.0D);
			}
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		if ((p_70652_1_.height >= 6.0F) || ((p_70652_1_ instanceof EntityTitan)))
		{
			float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
			getKnockbackAmount();
			for (int it = 0; it < 10; it++)
			{
				attackChoosenEntity(p_70652_1_, f, 2);
				if (p_70652_1_ instanceof EntityTitan)
				{
					if (((EntityTitan)p_70652_1_).getInvulTime() > 1)
					((EntityTitan)p_70652_1_).setInvulTime(((EntityTitan)p_70652_1_).getInvulTime() - 20);
				}
			}

			return super.attackEntityAsMob(p_70652_1_);
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	protected void updateAITasks()
	{
		this.attackTimer -= 1;
		if (getInvulTime() > 0)
		{
			if (this.ticksExisted % 1 == 0)
			{
				heal(getMaxHealth() / 800.0F);
			}

			int i = getInvulTime() - 1;
			setInvulTime(i);
			if (i <= 0)
			{
				this.blockBreakCounter = 1;
				this.worldObj.playBroadcastSound(1013, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				if ((this.allPlayerList != null) && (!this.allPlayerList.isEmpty()))
				{
					for (int i1 = 0; i1 < this.allPlayerList.size(); i1++)
					{
						Entity entity = (Entity)allPlayerList.get(i1);
						if (entity instanceof EntityPlayer)
						this.worldObj.playSound(entity.posX, entity.posY, entity.posZ, "thetitans:witherzillaSpawn", 10F, 1F, false);
					}
				}
			}
		}

		else
		{
			super.updateAITasks();
			if (!(this.worldObj.provider instanceof WorldProviderNowhere)) 
			{

			}

			if ((this.ticksExisted % 400 == 0) && (!(this.worldObj.provider instanceof WorldProviderNowhere)))
			{
				EntityPlayer entity = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
				if ((this.allPlayerList != null) && (!this.allPlayerList.isEmpty()) && (!(this.worldObj.provider instanceof WorldProviderNowhere)) && (getAttackTarget() == null) && (this.worldObj.provider == entity.worldObj.provider))
				{
					for (int i1 = 0; i1 < this.allPlayerList.size(); i1++)
					{
						teleportToEntity(entity, true);
						if (!entity.capabilities.disableDamage)
						{
							setAttackTarget(entity);
						}
					}
				}

				if (this.deathTicks <= 0 && (this.allPlayerList != null) && (!this.allPlayerList.isEmpty()) && (!(this.worldObj.provider instanceof WorldProviderNowhere)) && (this.rand.nextInt(20) == 0) && this.getAttackTarget() != null && this.getAttackTarget() == entity && (isArmored()))
				{
					for (int i1 = 0; i1 < this.allPlayerList.size(); i1++)
					{
						if (!this.worldObj.isRemote)
						{
							MinecraftServer minecraftserver = MinecraftServer.getServer();
							GameProfile gameprofile = minecraftserver.func_152358_ax().func_152655_a(entity.getCommandSenderName());
							EntityPlayerMP entityplayermp = minecraftserver.getConfigurationManager().func_152612_a(entity.getCommandSenderName());
							if ((entityplayermp != null) && entity.getCommandSenderName() != "Umbrella_Ghast")
							{
								this.attackChoosenEntity(entity, Integer.MAX_VALUE, 0);
								entity.setDead();
								UserListBansEntry userlistbansentry = new UserListBansEntry(gameprofile, (Date)null, entity.getCommandSenderName(), (Date)null, null);
								minecraftserver.getConfigurationManager().func_152608_h().func_152687_a(userlistbansentry);
								entityplayermp.playerNetServerHandler.kickPlayerFromServer("You've been banned from this server by Witherzilla for being annoying.");
							}
						}
					}
				}
			}

			if ((getAttackTarget() != null) && (canAttack()) && ((getAttackTarget() instanceof EntityLivingBase)))
			{
				double d0 = getDistanceSqToEntity(getAttackTarget());
				if (d0 < this.width * this.width + getAttackTarget().width * getAttackTarget().width + 6000.0D)
				{
					swingItem();
					attackEntityAsMob(getAttackTarget());
				}
			}

			if (this.getAttackTarget() != null)
			{
				ArrayList<?> listp = Lists.newArrayList(this.worldObj.playerEntities);
				if ((listp != null) && (!listp.isEmpty()))
				{
					for (int i1 = 0; i1 < listp.size(); i1++)
					{
						Entity entity = (Entity)listp.get(i1);
						if ((entity != null) && ((entity instanceof EntityPlayer)))
						{
							if (this.getAttackTarget() instanceof EntityExecutorDragon)
							{
								((EntityPlayer)entity).addChatMessage(new ChatComponentText("\u00A7l\u00A7kFrotheru: There's another me. This is a paradox!"));
							}
						}
					}
				}
			}

			if (this.posY <= 0.0D)
			{
				if (((this.worldObj.provider instanceof WorldProviderNowhere)) || ((this.worldObj.provider instanceof WorldProviderEnd)))
				{
					teleportRandomly(true);
				}

				else
				{
					teleportRandomly(false);
				}
			}

			List<?> list = this.worldObj.loadedEntityList;
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if ((!(this.worldObj.provider instanceof WorldProviderNowhere)) && (entity != null) && entity instanceof EntityPlayer && ((EntityPlayer)entity).getHeldItem() != null && ((EntityPlayer)entity).getHeldItem().getItem() != TitanItems.ultimaBlade && ((EntityPlayer)entity).getHeldItem().getItem() != TitanItems.optimaAxe && !(((EntityPlayer)entity).getHeldItem().getItem() instanceof ItemTitanSpawnEgg))
					{
						((EntityPlayer)entity).renderBrokenItemStack(((EntityPlayer)entity).getHeldItem());
						((EntityPlayer)entity).getHeldItem().stackSize = 0;
						((EntityPlayer)entity).destroyCurrentEquippedItem();
						this.doLightningAttackTo(entity);
					}

					if (entity instanceof EntityAgeable || entity instanceof EntityAmbientCreature || entity instanceof EntityWaterMob)
					{
						list.remove(entity);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void onKillCommand()
	{
		ArrayList<?> list11 = Lists.newArrayList(this.worldObj.playerEntities);
		if ((list11 != null) && (!list11.isEmpty()))
		{
			for (int i1 = 0; i1 < list11.size(); i1++)
			{
				Entity entity = (Entity)list11.get(i1);
				if ((entity != null) && ((entity instanceof EntityPlayer)))
				{
					((EntityPlayer)entity).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.executordragon.killattempt")));
				}
			}
		}
	}

	private double func_82214_u(int p_82214_1_)
	{
		return this.posX;
	}

	private double func_82208_v(int p_82208_1_)
	{
		return this.posY + 64D;
	}

	private double func_82213_w(int p_82213_1_)
	{
		return this.posZ;
	}

	private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_)
	{
		float f3 = MathHelper.wrapAngleTo180_float(p_82204_2_ - p_82204_1_);
		if (f3 > p_82204_3_)
		{
			f3 = p_82204_3_;
		}

		if (f3 < -p_82204_3_)
		{
			f3 = -p_82204_3_;
		}

		return p_82204_1_ + f3;
	}

	private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
	{
		if (((p_82216_2_ instanceof EntityTitan)) || (p_82216_2_.height >= 6.0F))
		{
			double d0 = getDistanceToEntity(p_82216_2_);
			if (d0 < width)
			{
				if (this.attackTimer <= 0)
				{
					this.attackTimer = 10;
					attackEntityAsMob(p_82216_2_);
					if (rand.nextInt(20) == 0)
					this.doLightningAttackTo(p_82216_2_);
				}
			}
		}

		else
		{
			launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
			p_82216_2_.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 100F);
			if (rand.nextInt(200) == 0)
			this.doLightningAttackTo(p_82216_2_);
			p_82216_2_.hurtResistantTime = 0;
		}

		launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
	}

	private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
	{
		double d3 = func_82214_u(p_82209_1_);
		double d4 = func_82208_v(p_82209_1_);
		double d5 = func_82213_w(p_82209_1_);
		double d6 = p_82209_2_ - d3;
		double d7 = p_82209_4_ - d4;
		double d8 = p_82209_6_ - d5;
		EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.worldObj, this, d6, d7, d8);
		if (p_82209_8_)
		{
			entitywitherskull.setInvulnerable(true);
		}

		entitywitherskull.posY = d4;
		entitywitherskull.posX = d3;
		entitywitherskull.posZ = d5;
		this.worldObj.playSoundEffect(d3, d4, d5, "mob.wither.shoot", 3.0F, 0.8F);
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		if (((p_82196_1_ instanceof EntityTitan)) || (p_82196_1_.height >= 6.0F))
		{
			double d0 = getDistanceSqToEntity(p_82196_1_);
			if (d0 < 1000.0D)
			{
				if (this.attackTimer <= 0)
				{
					this.attackTimer = (1 + this.rand.nextInt(9));
					attackEntityAsMob(p_82196_1_);
				}
			}
		}

		else
		{
			launchWitherSkullToEntity(0, p_82196_1_);
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if ((this.rand.nextInt(10) == 0) && (!(this.worldObj.provider instanceof WorldProviderNowhere)))
		{
		}

		if (this.isEntityInvulnerable() || this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL || this.getExtraPower() > 5)
		{
			return false;
		}

		if (((source.getEntity() instanceof EntityWitherzillaMinion)) || ((source.isExplosion()) && (!(source.getEntity() instanceof EntityWitherTurret))))
		{
			return false;
		}

		if ((isArmored()) && (!(source.getEntity() instanceof EntityPlayer)) && (!(source.getEntity() instanceof EntityTitan)))
		{
			if ((source.getEntity() != null) && (this.rand.nextInt(10) == 0))
			{
				teleportToEntity(source.getEntity(), false);
			}

			return false;
		}

		if (this.blockBreakCounter <= 0)
		{
			this.blockBreakCounter = 1;
		}

		++this.ticksExisted;
		return super.attackEntityFrom(source, amount);
	}

	public boolean attackWitherzillaFrom(DamageSource source, float amount)
	{
		if (((source.getEntity() instanceof EntityWitherzillaMinion)) || ((source.getEntity() instanceof EntityExecutorDragon)) || ((source.isExplosion()) && (!(source.getEntity() instanceof EntityWitherTurret))))
		{
			return false;
		}

		if ((isArmored()) && (!(source.getEntity() instanceof EntityPlayer)) && (!(source.getEntity() instanceof EntityTitan)))
		{
			return false;
		}

		if (this.blockBreakCounter <= 0)
		{
			this.blockBreakCounter = 1;
		}

		return super.attackEntityFrom(source, amount);
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (this.deathTicks > 0)
		{
			for (int x = 0; x < 250; x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(this.worldObj, this.posX, this.posY + 4D, this.posZ);
				entitylargefireball.setPosition(this.posX, this.posY + 4D, this.posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(32000);
				this.worldObj.spawnEntityInWorld(entitylargefireball);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setWildCard(true);
				itembomb.setItemCount(512);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 16; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.coal_block));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.iron_block));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.gold_block));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.emerald_block));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.diamond_block));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanBlocks.harcadium_block));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanBlocks.void_block));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanBlocks.bedrock_block));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.dragon_egg));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 4; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(this.worldObj, this.posX, this.posY + 6D, this.posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.nether_star));
				itembomb.setItemCount(64 + this.rand.nextInt(64 + p_70628_2_) + p_70628_2_);
				this.worldObj.spawnEntityInWorld(itembomb);
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
		return this.field_82221_e[p_82207_1_];
	}

	@SideOnly(Side.CLIENT)
	public float func_82210_r(int p_82210_1_)
	{
		return this.field_82220_d[p_82210_1_];
	}

	public int getWatchedTargetId(int p_82203_1_)
	{
		return this.dataWatcher.getWatchableObjectInt(15 + p_82203_1_);
	}

	public void func_82211_c(int p_82211_1_, int p_82211_2_)
	{
		this.dataWatcher.updateObject(15 + p_82211_1_, Integer.valueOf(p_82211_2_));
	}

	public boolean isArmored()
	{
		return getHealth() <= getMaxHealth() / 2.0F;
	}

	public void mountEntity(Entity entityIn)
	{
		this.ridingEntity = null;
	}

	public StatBase getAchievement()
	{
		if ((this.worldObj.provider instanceof WorldProviderNowhere))
		{
			return TitansAchievments.executorDragon;
		}

		return TitansAchievments.executorDragon2;
	}

	public int getThreashHold()
	{
		return 210;
	}

	protected void inactDeathAction()
	{
		if (!this.worldObj.isRemote)
		{
			createBeaconPortal(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
			if ((this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
			{
				dropFewItems(true, 0);
				dropEquipment(true, 0);
				dropRareDrop(1);
			}

			EntityTitanSpirit entitytitan = new EntityTitanSpirit(this.worldObj);
			entitytitan.setLocationAndAngles(this.posX, this.posY + 48D, this.posZ, this.rotationYaw, 0.0F);
			this.worldObj.spawnEntityInWorld(entitytitan);
			entitytitan.setVesselHunting(true);
			entitytitan.setSpiritType(13);
		}
	}

	@SuppressWarnings("unchecked")
	protected void onTitanDeathUpdate()
	{
		this.dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(this.dataWatcher.getWatchableObjectFloat(5), 0.0F, getMaxHealth())));
		this.dead = false;
		if (this.dataWatcher.getWatchableObjectFloat(5) <= 0F)
		{
			++this.deathTicks;
		}

		if ((this.deathTicks > 180) && (this.deathTicks % 1 == 0))
		{
			float f = (this.rand.nextFloat() - 0.5F) * 24.0F;
			float f1 = (this.rand.nextFloat() - 0.5F) * 80.0F;
			float f2 = (this.rand.nextFloat() - 0.5F) * 24.0F;
			this.worldObj.spawnParticle("hugeexplosion", this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
		}

		if (this.worldObj.provider instanceof WorldProviderNowhere)
		this.setLocationAndAngles(0D, 120D, 0D, this.deathTicks * 10, 0F);
		if (!this.worldObj.isRemote)
		{
			if (this.deathTicks == 1)
			{
				this.playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
				this.worldObj.playBroadcastSound(1018, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				this.worldObj.playBroadcastSound(1018, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				this.worldObj.playBroadcastSound(1018, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				this.worldObj.playBroadcastSound(1018, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				MinecraftServer.getServer().func_147139_a(EnumDifficulty.PEACEFUL);
				ArrayList<?> listp = Lists.newArrayList(this.worldObj.playerEntities);
				if ((listp != null) && (!listp.isEmpty()))
				{
					for (int i1 = 0; i1 < listp.size(); i1++)
					{
						Entity entity = (Entity)listp.get(i1);
						if ((entity != null) && ((entity instanceof EntityPlayer)))
						{
							((EntityPlayer)entity).triggerAchievement(getAchievement());
							((EntityPlayer)entity).triggerAchievement(AchievementList.field_150964_J);
							ItemStack item = new ItemStack(TitanItems.ultimaBlade, 1, 1);
							((EntityPlayer)entity).entityDropItem(item, 0.0F);
							playLivingSound();
							if (this.worldObj.provider instanceof WorldProviderNowhere)
							((EntityPlayer)entity).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.witherzilla.death")));
							else
							((EntityPlayer)entity).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("dialog.witherzilla.defeat")));
						}
					}
				}
			}
		}

		if ((this.deathTicks >= 200))
		{
			this.setInvulTime(2000);
		}

		if ((this.deathTicks >= 400))
		{
			this.setDead();
		}
	}

	private void createBeaconPortal(int p_70975_1_, int p_70975_2_)
	{
		byte b0 = 64;
		net.minecraft.block.BlockEndPortal.field_149948_a = true;
		byte b1 = 4;
		for (int k = b0 - 1; k <= b0 + 32; k++)
		{
			for (int l = p_70975_1_ - b1; l <= p_70975_1_ + b1; l++)
			{
				for (int i1 = p_70975_2_ - b1; i1 <= p_70975_2_ + b1; i1++)
				{
					double d0 = l - p_70975_1_;
					double d1 = i1 - p_70975_2_;
					double d2 = d0 * d0 + d1 * d1;
					if (d2 <= (b1 - 0.5D) * (b1 - 0.5D))
					{
						if (k < b0)
						{
							if (d2 <= (b1 - 1 - 0.5D) * (b1 - 1 - 0.5D))
							{
								this.worldObj.setBlock(l, k, i1, Blocks.bedrock);
							}
						}

						else if (k > b0)
						{
							this.worldObj.setBlock(l, k, i1, Blocks.air);
						}

						else if (d2 > (b1 - 1 - 0.5D) * (b1 - 1 - 0.5D))
						{
							this.worldObj.setBlock(l, k, i1, Blocks.bedrock);
						}

						else
						{
							this.worldObj.setBlock(l, k, i1, Blocks.end_portal);
						}
					}
				}
			}
		}

		this.worldObj.setBlock(p_70975_1_, b0 + 0, p_70975_2_, Blocks.bedrock);
		this.worldObj.setBlock(p_70975_1_, b0 + 1, p_70975_2_, Blocks.bedrock);
		this.worldObj.setBlock(p_70975_1_, b0 + 2, p_70975_2_, Blocks.bedrock);
		this.worldObj.setBlock(p_70975_1_ - 1, b0 + 2, p_70975_2_, Blocks.torch);
		this.worldObj.setBlock(p_70975_1_ + 1, b0 + 2, p_70975_2_, Blocks.torch);
		this.worldObj.setBlock(p_70975_1_, b0 + 2, p_70975_2_ - 1, Blocks.torch);
		this.worldObj.setBlock(p_70975_1_, b0 + 2, p_70975_2_ + 1, Blocks.torch);
		this.worldObj.setBlock(p_70975_1_, b0 + 3, p_70975_2_, Blocks.bedrock);
		this.worldObj.setBlock(p_70975_1_, b0 + 4, p_70975_2_, Blocks.diamond_block);
		this.worldObj.setBlock(p_70975_1_ + 1, b0 + 4, p_70975_2_ + 1, Blocks.diamond_block);
		this.worldObj.setBlock(p_70975_1_ + 1, b0 + 4, p_70975_2_, Blocks.diamond_block);
		this.worldObj.setBlock(p_70975_1_ + 1, b0 + 4, p_70975_2_ - 1, Blocks.diamond_block);
		this.worldObj.setBlock(p_70975_1_ - 1, b0 + 4, p_70975_2_ + 1, Blocks.diamond_block);
		this.worldObj.setBlock(p_70975_1_ - 1, b0 + 4, p_70975_2_, Blocks.diamond_block);
		this.worldObj.setBlock(p_70975_1_ - 1, b0 + 4, p_70975_2_ - 1, Blocks.diamond_block);
		this.worldObj.setBlock(p_70975_1_, b0 + 4, p_70975_2_ + 1, Blocks.diamond_block);
		this.worldObj.setBlock(p_70975_1_, b0 + 4, p_70975_2_ - 1, Blocks.diamond_block);
		this.worldObj.setBlock(p_70975_1_, b0 + 5, p_70975_2_, Blocks.beacon);
	}

	protected void collideWithNearbyEntities() 
	{
		 
	}


	public float getEyeHeight()
	{
		return 1.4F * this.getTitanSizeMultiplier();
	}

	protected boolean teleportRandomly(boolean bool)
	{
		double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
		double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
		if (bool)
		{
			return teleportTo(0.0D, 200.0D, 0.0D);
		}

		return teleportTo(d0, 200.0D, d2);
	}

	protected boolean teleportToEntity(Entity p_70816_1_, boolean bool)
	{
		Vec3 vec3 = Vec3.createVectorHelper(this.posX - p_70816_1_.posX, this.boundingBox.minY + this.height / 2.0F - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
		vec3 = vec3.normalize();
		double d0 = 32.0D;
		double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 16.0D - vec3.xCoord * d0;
		double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
		double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 16.0D - vec3.zCoord * d0;
		if (bool)
		{
			return teleportTo(p_70816_1_.posX, p_70816_1_.posY, p_70816_1_.posZ);
		}

		return teleportTo(d1, d2, d3);
	}

	protected boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_, 0.0F);
		if (MinecraftForge.EVENT_BUS.post(event))
		{
			return false;
		}

		if (!this.worldObj.isRemote)
		{
			setPosition(p_70825_1_, p_70825_3_, p_70825_5_);
		}

		return true;
	}

	public boolean handleLavaMovement()
	{
		return false;
	}

	public boolean isInOmegaForm()
	{
		return !(this.worldObj.provider instanceof WorldProviderNowhere);
	}
}


