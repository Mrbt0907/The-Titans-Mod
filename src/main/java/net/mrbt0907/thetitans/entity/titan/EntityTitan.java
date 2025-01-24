package net.mrbt0907.thetitans.entity.titan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.config.ConfigMain;
import net.mrbt0907.thetitans.entity.EntityMultiPart;
import net.mrbt0907.thetitans.registries.SoundRegistry;
import net.mrbt0907.thetitans.util.DamageSources;
import net.mrbt0907.util.mixin.CameraHandler;
import net.mrbt0907.util.util.TranslateUtil;
import net.mrbt0907.util.util.WorldUtil;
import net.mrbt0907.util.util.chunk.MobChunkLoader;
import net.mrbt0907.util.util.math.Maths;
import net.mrbt0907.util.util.math.Vec;

public abstract class EntityTitan extends EntityLiving implements IEntityMultiPart
{
	private static final IAttribute HEALTH = new RangedAttribute(null, "titan.health", 2000.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true);
	private static final IAttribute STAMINA = new RangedAttribute(null, "titan.stamina", 2000.0D, 0.0D, Double.MAX_VALUE).setDescription("Stamina").setShouldWatch(true);
	
	protected EntityMultiPart[] hitboxes;
	protected int stunTicks;
	protected int deathTicks;
	protected int stage;
	protected double previousDamage;
	protected Entity previousAttackingEntity;
	protected long ticksTalked;
	
	protected Map<Float, Integer> cameraShakeDuration = new HashMap<Float, Integer>();
	
	public static final Predicate<Entity> IS_NOT_TITAN = new Predicate<Entity>()
	{
		public boolean apply(Entity input)
		{
			return !(input instanceof EntityTitan);
		}
	};
	
	public static final Predicate<Entity> IS_VALID_TARGET = new Predicate<Entity>()
	{
		public boolean apply(Entity input)
		{
			return input instanceof EntityLivingBase && input.isEntityAlive() && (input instanceof EntityPlayer ? !((EntityPlayer)input).isSpectator() : true);
		}
	};
	
	public EntityTitan(World worldIn)
	{
		super(worldIn);
		width = 5.0F;
		height = 5.0F;
		isImmuneToFire = true;
		ignoreFrustumCheck = true;
		
		List<EntityMultiPart> hitboxes = onHitboxCreate(new ArrayList<EntityMultiPart>()); 
		this.hitboxes = hitboxes.toArray(new EntityMultiPart[hitboxes.size()]);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getTrueMaxHealth());
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(512D);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		getAttributeMap().registerAttribute(HEALTH);
		getAttributeMap().registerAttribute(STAMINA);
		setStamina(getMaxStamina());
		setHealth(getTrueMaxHealth());
	}

	protected void entityInit()
	{
		super.entityInit();
	}
	
	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		if (tagCompund.hasKey("healthTitan"))
		{
			setHealth(tagCompund.getDouble("healthTitan"));
			setStamina(tagCompund.getDouble("stamina"));
			stage = tagCompund.getInteger("stage");
		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setDouble("healthTitan", getTrueHealth());
		tagCompound.setDouble("stamina", getStamina());
		tagCompound.setInteger("stage", stage);
		super.writeEntityToNBT(tagCompound);
	}

	
	//------- TICK METHODS -------\\
	public void onUpdate()
	{
		super.onUpdate();
		
		onHitboxUpdate();
		onDeathUpdate();
		
		if (isStunned())
			onStunUpdate();
		
		if (world.isRemote && !cameraShakeDuration.isEmpty())
		{
			Iterator<Entry<Float, Integer>> durations = cameraShakeDuration.entrySet().iterator();
			Entry<Float, Integer> entry;
			while (durations.hasNext())
			{
				entry = durations.next();
				shakeCamera(getSoundVolume(), entry.getKey());
				if (ticksExisted >= entry.getValue())
					durations.remove();
			}
		}	
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		boolean isStunned = isStunned();
			isInWeb = false;
			isAirBorne = !onGround;
			lastDamage = 2.14748365E9F;
			stepHeight = (height / 2.0F);
			if (ticksTalked > 0L)
				ticksTalked--;
			
			if (posY > 300.0D)
			{
				motionY = 0F;
				setPosition(posX, 300.0D, posZ);
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
			
			if (isStunned && maxHurtResistantTime != 5)
				maxHurtResistantTime = 5;
			else if (!isStunned && maxHurtResistantTime != 25)
				maxHurtResistantTime = 25;

				if (isEntityAlive())
					MobChunkLoader.updateLoaded(this);
				else
					MobChunkLoader.stopLoading(this);
			
			if (getAttackTarget() != null)
			{
				if(!getAttackTarget().isEntityAlive())
					setAttackTarget(null);
				else
				getLookHelper().setLookPositionWithEntity(getAttackTarget(), 1.0F, 1.0F);
				
				if (ConfigMain.tab_other.enable_nightmare_mode && getAttackTarget() instanceof EntityLiving && !(getAttackTarget() instanceof EntityTitan) && getAttackTarget().getMaxHealth() > 1000000000F)
				{
					if (world.isRemote)
					getAttackTarget().playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2F, 1F + rand.nextFloat());
					
					if (getAttackTarget().getHealth() <= 1F)
					{
						world.createExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 14F, false);
						getAttackTarget().setDead();
					}
				}

				if (ConfigMain.tab_other.enable_nightmare_mode && !world.isRemote && this != null && getAttackTarget() instanceof EntityPlayerMP)
				if (((EntityPlayerMP)getAttackTarget()).capabilities.disableDamage && !((EntityPlayer)getAttackTarget()).capabilities.isCreativeMode)
					((EntityPlayerMP)getAttackTarget()).connection.disconnect(TranslateUtil.translateChat("event.kick.cheat"));
			}
			else
			{
				List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(100D), Predicates.and(EntitySelectors.IS_ALIVE, EntitySelectors.CAN_AI_TARGET));
				
				for (Entity entity : list)
				{
					if (getAttackTarget() == null && entity instanceof EntityLivingBase && this.getDistance(entity) <= 200D && !(entity instanceof EntityAmbientCreature) && entity.getClass() != this.getClass())
					{
						this.setAttackTarget((EntityLivingBase) entity);
					}
				}
			}

		updateArmSwingProgress();
	}

	/**Called upon entity creation to register hitboxes for the titan*/
	protected abstract List<EntityMultiPart> onHitboxCreate(List<EntityMultiPart> hitboxes);
	
	protected void updateAITasks() {}
	
	@Override
	protected void onDeathUpdate()
	{
		if (isEntityAlive()) return;
		
		if (world.isRemote);
		{
			if (deathTicks == 0)
			{
				shakeCamera(getSoundVolume(), 0.25F, 100);
				playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
			}
			performHurtAnimation();
			spawnExplosionParticle();
		}
		
		motionX = 0.0D;
		motionZ = 0.0D;
		renderYawOffset = rotationYaw = rotationYawHead += 10F;
		setAttackTarget(null);

		if (deathTicks > 200)
			setDead();
		
		deathTicks++;
	}

	@Override
	public void onDeath(DamageSource source)
	{
		if (isEntityAlive() || net.minecraftforge.common.ForgeHooks.onLivingDeath(this, source)) return;
		Entity entity = source.getTrueSource();
		
		if (entity != null && entity instanceof EntityLivingBase)
		{
			EntityLivingBase killer = (EntityLivingBase) entity;
			if (!world.isRemote)
			{
				if (getTrueHealth() > 0.0D)
					TranslateUtil.sendChatAll("entity.titan.kill.cheat", getName(), killer.getName());
				else
					TranslateUtil.sendChatAll("entity.titan.kill", getName(), killer.getName());
				
				int i = EnchantmentHelper.getLootingModifier((EntityLivingBase)killer);

				captureDrops = true;
				capturedDrops.clear();
				
				if (world.getGameRules().getBoolean("doMobLoot"))
				{
					dropFewItems(recentlyHit > 0, i);
					dropEquipment(recentlyHit > 0, i);
				}

				captureDrops = false;
				
				if (!ForgeHooks.onLivingDrops(this, source, capturedDrops, i, recentlyHit > 0))
					for (EntityItem item : capturedDrops)
						world.spawnEntity(item);
			}
			
			if (scoreValue >= 0)
				killer.awardKillScore(this, scoreValue, source);
			killer.onKillEntity(this);
			
			world.setEntityState(this, (byte)3);
		}
	}
	
	protected void onStunUpdate() {}
	
	protected void onHitboxUpdate()
	{
		Vec newPos;
		
		for(EntityMultiPart hitbox : hitboxes)
		{
			hitbox.onUpdate();
			newPos = Maths.rotate(new Vec(posX, posZ), new Vec(hitbox.xOffset, hitbox.zOffset), (float) Math.toRadians(renderYawOffset));
			hitbox.setLocationAndAngles(newPos.posX, posY + hitbox.yOffset, newPos.posZ, 0.0F, 0.0F);
			
			if (hitbox.canCrush && isEntityAlive())
				onCrushingEntity(hitbox.getEntityBoundingBox());
		}
	}
	
	protected void onCrushingEntity(AxisAlignedBB boundingBox)
	{
		if (boundingBox == null || Maths.speedSq(motionX, motionY, motionZ) <= 0.1D) return;
		
		WorldUtil.getEntities(this, boundingBox, IS_NOT_TITAN).forEach(entity ->
		{
			if (entity instanceof EntityLivingBase && !(entity instanceof EntityTitan) && entity.onGround)
				entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
		});
	}
	
	//------- EVENTS -------\\
	/**Sends dialog to the player depending on what index is set.<br>
	 * 0 - On Spawn<br>
	 * 1 - On Failed Spawn<br>
	 * 2 - On Hit By Player<br>
	 * 3 - On Hit<br>
	 * 4 - On Living Tick<br>
	 * 10 - On Death*/
	public void onQuote(EntityPlayer player, int index, int subIndex) {}
	protected abstract void dropLoot();
	
	//------- FUNCTIONAL METHODS -------\\
	public boolean attackChoosenEntity(Entity victim, float damage, int knockbackAmount, boolean bypass)
	{
		DamageSource source = DamageSources.causeTitanDamage(this, bypass);
		if (victim == null || isEntityBlacklisted(victim) || (!bypass && victim.isEntityInvulnerable(source)))
			return false;
			
		switch(world.getDifficulty())
		{
			case HARD:
				damage *= 1.5F;
				break;
			case EASY:
				damage *= 0.5F;
				break;
			case PEACEFUL:
				damage *= 0.25F;
				break;
			default:
		}
		
		victim.hurtResistantTime = 0;
		knockbackAmount += EnchantmentHelper.getKnockbackModifier(this);

		if (EnchantmentHelper.getFireAspectModifier(this) > 0)
			victim.setFire(EnchantmentHelper.getFireAspectModifier(this) * 100);
		
		if (victim instanceof EntityLivingBase)
			damage = MathHelper.clamp(damage + EnchantmentHelper.getModifierForCreature(getHeldItemMainhand(), ((EntityLivingBase)victim).getCreatureAttribute()), 0.0F, Float.MAX_VALUE);
		
		if (knockbackAmount > 0.0F)
		{
			victim.motionY += rand.nextDouble();
			victim.addVelocity(-MathHelper.sin(renderYawOffset * (float)Math.PI / 180.0F) * (1 + knockbackAmount) * 0.2D, (1 + knockbackAmount) * 0.2D, MathHelper.cos(renderYawOffset * (float)Math.PI / 180.0F) * (1 + knockbackAmount) * 0.2D);
		}
		victim.attackEntityFrom(source, damage);
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void shakeCamera(double distance)
	{
		shakeCamera(distance, 0.25F, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public void shakeCamera(double distance, float strength)
	{
		shakeCamera(distance, strength, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public void shakeCamera(double distance, float strength, int duration)
	{
		EntityPlayer player = net.minecraft.client.Minecraft.getMinecraft().player;
		
		if (player != null)
		{
			double size = getSizeMultiplier();
			float multiplier = 1.0F - MathHelper.clamp((float)((player.getDistance(this) - size) / size), 0.0F, 1.0F);
			CameraHandler.shakeCamera(strength * multiplier);
			
			if (duration > 0 && (!cameraShakeDuration.containsKey(strength) || cameraShakeDuration.get(strength) < duration + ticksExisted))
				cameraShakeDuration.put(strength, duration + ticksExisted);
		}
	}
	
//------- GETTER/SETTER METHODS -------\\
	public abstract double getTrueMaxHealth();
	public abstract double getMaxStamina();
	protected abstract SoundEvent getHurtSound(DamageSource damageSourceIn);
	protected abstract SoundEvent getDeathSound();
	protected abstract SoundEvent getAmbientSound();
	
	@SideOnly(Side.CLIENT)
	public float getRenderSizeMultiplier()
	{
		return getSizeMultiplier();
	}
	
	public abstract float getSizeMultiplier();
	
	@Override
	protected SoundEvent getSwimSound()
	{
		return SoundEvents.ENTITY_HOSTILE_SWIM;
	}

	@Override
	protected SoundEvent getSplashSound()
	{
		return SoundEvents.ENTITY_HOSTILE_SPLASH;
	}
	
	protected SoundEvent getSpawnSound()
	{
		return SoundRegistry.get("titan.birth");
	}

	@Override
	public void playLivingSound()
	{
		shakeCamera(getSoundVolume(), 0.15F, 40);
		super.playLivingSound();
	}
	
	@Override
	protected void playHurtSound(DamageSource source)
	{
		shakeCamera(getSoundVolume(), 0.15F);
		super.playHurtSound(source);
	}
	
	@Override
	protected float getSoundVolume()
	{
		return 1000.0F;
	}
	
	public final double getTrueHealth()
	{
		return getEntityAttribute(HEALTH).getAttributeValue();
	}

	@Override
	public float getHealth()
	{
		return (float) MathHelper.clamp(getTrueHealth(), -Float.MAX_VALUE, Float.MAX_VALUE);
	}

	@Override
	public void setHealth(float health)
    {
        setHealth((double) health);
    }
	
	protected void setHealth(double value)
	{
		double health = getTrueHealth();
		double maxHealth = getTrueMaxHealth();
		
		if (maxHealth < health)
			getEntityAttribute(HEALTH).setBaseValue(maxHealth);
		else if (health < value)
			getEntityAttribute(HEALTH).setBaseValue(value);
	}
	
	@Override
	public float getMaxHealth()
	{
		return (float) MathHelper.clamp(getTrueMaxHealth(), -Float.MAX_VALUE, Float.MAX_VALUE);
	}
	
	public void heal(float value)
	{
		heal((double) value);
	}
	
	public void heal(double value)
	{
		double oldHealth = getEntityAttribute(HEALTH).getBaseValue();
		double newHealth = oldHealth + Math.abs(value);
		
		if (newHealth < oldHealth)
			newHealth = getTrueMaxHealth();
		
		getEntityAttribute(HEALTH).setBaseValue(newHealth);
	}

	public void hurt(double value)
	{
		if (isEntityInvulnerable(DamageSource.GENERIC)) return;
		attackEntityFrom(DamageSource.GENERIC, (float) value);
	}

	public boolean isStunned()
	{
		return getStamina() <= 0.0D;
	}
	
	protected void setStamina(double value)
	{
		getEntityAttribute(STAMINA).setBaseValue(value);
	}
	
	public double getStamina()
	{
		return getEntityAttribute(STAMINA).getAttributeValue();
	}
	
	public double getAttackDamage()
	{
		return getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	}
	
	public double getBaseAttackDamage()
	{
		return getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
	}
	
	protected boolean isEntityBlacklisted(Entity entity)
	{
		if (entity instanceof EntityFallingBlock || entity instanceof EntityItem || entity instanceof EntityXPOrb)
			return true;
		return false;
	}
	
//------- OVERRIDES -------\\
	@Override
	public Entity[] getParts()
	{
		return hitboxes;
	}
	
	@Override
	public boolean isEntityAlive()
	{
		return getTrueHealth() > 0.0F;
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart entity, DamageSource source, float damage)
	{
		return attackEntityFrom(source, damage);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		TheTitans.info("Hi");
		Entity attacker = source.getTrueSource();
		boolean flag = true;
		
		if (isServerWorld() && !isEntityInvulnerable(source) && attacker != null && amount <= 100.0F)
		{
			previousAttackingEntity = attacker;
			world.playerEntities.forEach(player -> onQuote(player, 3, 0));
		}
		
		if (world.isRemote || isEntityInvulnerable(source) || (ConfigMain.tab_other.enable_nightmare_mode ? amount <= 100.0F : false) || (attacker != null && attacker.equals(getRidingEntity())))
			return false;
		
		previousAttackingEntity = attacker;
		if (hurtResistantTime > maxHurtResistantTime / 2)
		{
			if (amount <= lastDamage) return false;
			lastDamage = amount;
			flag = false;
			damageEntity(source, amount - lastDamage);
		}
		else
		{
			lastDamage = amount;
			hurtResistantTime = maxHurtResistantTime;
			damageEntity(source, amount);
		}
		
		if (attacker != null)
		{
			if (attacker instanceof EntityLivingBase)
			{
				setAttackTarget((EntityLivingBase)attacker);
				setRevengeTarget((EntityLivingBase)attacker);
				
				if (attacker instanceof EntityPlayer)
					attackingPlayer = (EntityPlayer)attacker;
				else
					attackingPlayer = null;
			}
			
			recentlyHit = 200;
			
			if (flag)
			{
				double d0, d1 = attacker.posX - posX;
				world.setEntityState(this, (byte)2);
				
				for (d0 = attacker.posZ - posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
					d1 = (Math.random() - Math.random()) * 0.01D;
				attackedAtYaw = (float)(Math.atan2(d0, d1) * 180.0D / Math.PI) - rotationYaw;
				knockBack(attacker, amount, d1, d0);
			}
		}
		else
			attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);

		if (!isEntityAlive())
		{
			if (flag)
			{
				shakeCamera(getSoundVolume(), 0.25F, 100);
				playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
			}
			onDeath(source);
		}
		else if (flag)
			playHurtSound(source);

		attackedAtYaw = 0.0F;
		return true;
	}
	
	@Override
	protected float applyArmorCalculations(DamageSource source, float damage)
	{
		this.damageArmor(damage);
		damage = CombatRules.getDamageAfterAbsorb(damage, (float)this.getTotalArmorValue(), (float)this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
		return damage;
	}
	
	@Override
	protected void damageEntity(DamageSource source, float damage)
	{
		if (!this.isEntityInvulnerable(source))
		{
			damage = (float) Math.min(ForgeHooks.onLivingHurt(this, source, damage), Double.MAX_VALUE);
			if (damage <= 0.0F)
			{
				previousDamage = 0.0D;
				return;
			}
			
			damage = applyPotionDamageCalculations(source, damage);	
			previousDamage = damage;
			
			float f = damage;
			damage = Math.max(damage - getAbsorptionAmount(), 0.0F);
			
			setAbsorptionAmount(getAbsorptionAmount() - (f - damage));
			damage = (float) Math.min(ForgeHooks.onLivingDamage(this, source, damage), Double.MAX_VALUE);

			if (damage > 0.0F)
			{
				getCombatTracker().trackDamage(source, getHealth(), damage);
				getEntityAttribute(HEALTH).setBaseValue(getTrueHealth() - damage);
				setAbsorptionAmount(getAbsorptionAmount() - damage);
			}
			
			if (isServerWorld() && ticksTalked == 0L)
				world.playerEntities.forEach(player -> onQuote(player, source.getTrueSource() == null || !(source.getTrueSource() instanceof EntityPlayer) ? 3 : 2, previousDamage >= 5000000.0D ? 3 : previousDamage >= 750000.0D ? 2 : previousDamage > 100.0D ? 1 : 0));
		}
	}

	@Override
	public void onKillCommand()
	{
		if (isEntityAlive()) return;
		playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
		setDead();
	}
	
	@Override
	public void setDead()
	{
		if (world.isRemote)
			super.setDead();
		if (isEntityAlive()) return;
		else if (deathTicks > 0)
		{
			world.playerEntities.forEach(player -> onQuote(player, 10, 0));
			world.newExplosion(this, posX, posY + 3.0D, posZ, 0.0F, false, false);
			dropLoot();
			
			super.setDead();
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity victim)
	{
		attackChoosenEntity(victim, (float) getAttackDamage(), 1, false);
		getLookHelper().setLookPositionWithEntity(victim, 180.0F, getVerticalFaceSpeed());
		return true;
	}

	@Override
	public boolean isEntityInvulnerable(DamageSource source)
	{
		return source.damageType.equals("titan_damage") ? false : (ConfigMain.tab_other.enable_nightmare_mode ? (source.canHarmInCreative() || source.isExplosion() || source.isMagicDamage() || source.isProjectile()) : false)  || source.isDamageAbsolute() || source.isFireDamage() || source.damageType.equals("cactus") || source.damageType.equals("anvil") || source.damageType.equals("fallingBlock") || super.isEntityInvulnerable(source);
	}

	

	@Override
	public float getEyeHeight()
	{
		return getSizeMultiplier();
	}
	
	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	@Override
	public boolean isNoDespawnRequired()
	{
		return true;
	}

	@Override
	public boolean isPotionApplicable(PotionEffect p_70687_1_)
	{
		return false;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		return true;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEFINED;
	}
	
	@Override
	public World getWorld()
	{
		return world;
	}
}