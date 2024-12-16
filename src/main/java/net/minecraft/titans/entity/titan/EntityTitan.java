package net.minecraft.titans.entity.titan;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.endermanofdoom.mac.interfaces.IBossBar;
import net.endermanofdoom.mac.interfaces.IGendered;
import net.endermanofdoom.mac.interfaces.IVariedMob;
import net.endermanofdoom.mac.music.IMusicInteractable;
import net.endermanofdoom.mac.util.TranslateUtil;
import net.endermanofdoom.mac.util.chunk.MobChunkLoader;
import net.endermanofdoom.mac.util.math.Maths;
import net.endermanofdoom.mac.util.math.Vec;
import net.endermanofdoom.mca.MCA;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.titans.TitanConfig;
import net.minecraft.titans.api.IMobTier;
import net.minecraft.titans.entity.EntityMultiPart;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityTitan extends EntityLiving implements IMobTier, IEntityMultiPart, IBossBar, IGendered, IVariedMob, IMusicInteractable
{
	private static final IAttribute titanMaxHealth = new RangedAttribute(null, "titan.maxHealth", 2000.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Health").setShouldWatch(true);
	private static final IAttribute titanHealth = new RangedAttribute(null, "titan.health", 2000.0D, 0.0D, Double.MAX_VALUE).setDescription("Health").setShouldWatch(true);
	private static final IAttribute titanMaxStamina = new RangedAttribute(null, "titan.maxStamina", 2000.0D, 0.0D, Double.MAX_VALUE).setDescription("Max Stamina").setShouldWatch(true);
	private static final IAttribute titanStamina = new RangedAttribute(null, "titan.stamina", 2000.0D, 0.0D, Double.MAX_VALUE).setDescription("Stamina").setShouldWatch(true);
	
	private final List<EntityMultiPart> hitboxes = new ArrayList<EntityMultiPart>();
	protected int stunTicks;
	protected int deathTicks;
	protected int stage;
	protected boolean isStunned;
	protected double previousDamage;
	protected Entity previousAttackingEntity;
	protected long ticksTalked;
	
	public static final Predicate<EntityLivingBase> IS_NOT_TITAN = new Predicate<EntityLivingBase>()
	{
		public boolean apply(@Nullable EntityLivingBase input)
		{
			return !(input instanceof EntityTitan);
		}
	};
	
	public EntityTitan(World worldIn)
	{
		super(worldIn);
		width = 5.0F;
		height = 5.0F;
		isImmuneToFire = true;
		ignoreFrustumCheck = true;
		hitboxes.addAll(onHitboxCreate(new ArrayList<EntityMultiPart>()));
		playSound(getSpawnSound(), 1000.0F, getSoundPitch());
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Double.MAX_VALUE);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(512D);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		getAttributeMap().registerAttribute(titanMaxHealth);
		getAttributeMap().registerAttribute(titanHealth);
		getAttributeMap().registerAttribute(titanMaxStamina);
		getAttributeMap().registerAttribute(titanStamina);
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
			setHealthD(tagCompund.getDouble("healthTitan"));
			setMaxHealth(tagCompund.getDouble("maxHealthTitan"));
			setMaxStamina(tagCompund.getDouble("maxStamina"));
			setStamina(tagCompund.getDouble("stamina"));
			stage = tagCompund.getInteger("stage");
		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setDouble("healthTitan", getHealthD());
		tagCompound.setDouble("maxHealthTitan", getMaxHealthD());
		tagCompound.setDouble("maxStamina", getMaxStamina());
		tagCompound.setDouble("stamina", getStamina());
		tagCompound.setInteger("stage", stage);
		super.writeEntityToNBT(tagCompound);
	}

	public void onUpdate()
	{
		super.onUpdate();
		
		onHitboxUpdate();
		
		if (!isEntityAlive())
			onNewDeathUpdate();
		
		if (isStunned)
			onStunUpdate();
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (getMaxHealth() != getMaxHealthF())
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getMaxHealthF());
		
		if (getHealth() != getHealthF())
			setHealth(getHealthF());

		if (world.isRemote)
		{
			ignoreFrustumCheck = true;
		}
		else
		{
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
				
				if (TitanConfig.enableAnticheat && getAttackTarget() instanceof EntityLiving && !(getAttackTarget() instanceof EntityTitan) && getAttackTarget().getMaxHealth() > 1000000000F)
				{
					if (world.isRemote)
					getAttackTarget().playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2F, 1F + rand.nextFloat());
					
					if (getAttackTarget().getHealth() <= 1F)
					{
						world.createExplosion(this, getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 14F, false);
						getAttackTarget().setDead();
					}
				}

				if (TitanConfig.enableAnticheat && !world.isRemote && this != null && getAttackTarget() instanceof EntityPlayerMP)
				if (((EntityPlayerMP)getAttackTarget()).capabilities.disableDamage && !((EntityPlayer)getAttackTarget()).capabilities.isCreativeMode)
					((EntityPlayerMP)getAttackTarget()).connection.disconnect(TranslateUtil.translateChat("event.kick.cheat"));
			}
	        else
	        {
	            List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(100D), Predicates.and(EntitySelectors.IS_ALIVE, EntitySelectors.CAN_AI_TARGET));
	            
	            for (Entity entity : list)
	            {
	                if (getAttackTarget() == null && entity instanceof EntityLivingBase && this.getDistance(entity) <= 200D && !(entity instanceof EntityAmbientCreature) && entity.getClass() != this.getClass() && !MCA.isVerminMob(entity))
	                {
	                	this.setAttackTarget((EntityLivingBase) entity);
	                }
	            }
	        }
		}

		updateArmSwingProgress();
	}

	/**Called upon entity creation to register hitboxes for the titan*/
	protected List<EntityMultiPart> onHitboxCreate(List<EntityMultiPart> hitboxes)
	{
		//hitboxes.add(new EntityMultiPart(this, "main", 0, 0, 0, width, height, true));
		return hitboxes;
	}
	
	protected void updateAITasks()
	{
		
	}
	
	@Override
	protected void onDeathUpdate() {}

	@Override
	public void onDeath(DamageSource source)
	{
		if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, source)) return;
		Entity entity = source.getTrueSource();
		
		if (entity != null && entity instanceof EntityLivingBase)
		{
			EntityLivingBase killer = (EntityLivingBase) entity;
			if (!world.isRemote)
			{
				if (getHealthD() > 0.0D)
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
					getName();
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
	
	protected void onStunUpdate()
	{
		
	}
	
	protected void onHitboxUpdate()
	{
        Vec newPos;
        
		for(EntityMultiPart hitbox : hitboxes)
		{
			hitbox.onUpdate();
			newPos = Maths.rotate(new Vec(posX, posZ), new Vec(hitbox.xOffset, hitbox.zOffset), (float) Math.toRadians(renderYawOffset));
			
			if (hitbox.partName.equals("main"))
			{
				hitbox.setLocationAndAngles(posX, posY + hitbox.yOffset, posZ, 0.0F, 0.0F);
				hitbox.height = getSizeMultiplier() * 0.25F;
				hitbox.width = getSizeMultiplier();
				
				if (isEntityAlive() && ticksExisted > 5)
					onCrushingEntity(hitbox.getEntityBoundingBox());
			}
			else
				hitbox.setLocationAndAngles(newPos.posX, posY + hitbox.yOffset, newPos.posZ, 0.0F, 0.0F);
		}
	}
	
	protected void onCrushingEntity(AxisAlignedBB boundingBox)
	{
		if (boundingBox == null || Maths.speedSq(motionX, motionY, motionZ) <= 0.1D) return;
		
		world.getEntitiesWithinAABBExcludingEntity(this, boundingBox).forEach(entity ->
		{
			if (entity instanceof EntityLivingBase && !(entity instanceof EntityTitan) && entity.onGround)
				entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
		});
	}
	
	protected void onNewDeathUpdate()
	{
		if (world.isRemote);
		{
			playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
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
	
	/**Sends dialog to the player depending on what index is set.<br>
	 * 0 - On Spawn<br>
	 * 1 - On Failed Spawn<br>
	 * 2 - On Hit By Player<br>
	 * 3 - On Hit<br>
	 * 4 - On Living Tick<br>
	 * 10 - On Death*/
	public void onQuote(EntityPlayer player, int index, int subIndex) {}
	
	@Override
	public void onKillCommand()
	{
		setHealthD(0.0D);
		playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
		setDead();
	}
	
	public float getRenderSizeMultiplier()
    {
        return getSizeMultiplier();
    }
	
	public abstract float getSizeMultiplier();

	public void heal(float p_70691_1_) {heal((double)p_70691_1_);}

	@Override
	public void setDead()
	{
		if (world.isRemote)
			super.setDead();
		else if (deathTicks > 0)
		{
			world.playerEntities.forEach(player -> onQuote(player, 10, 0));
			world.newExplosion(this, posX, posY + 3.0D, posZ, 0.0F, false, false);
			dropLoot();
			
			
			super.setDead();
		}
	}

	

	protected abstract void dropLoot();
	
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
            damage = (float) Math.min(ForgeHooks.onLivingHurt(this, source, damage), getDamageCap());
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
            damage = (float) Math.min(ForgeHooks.onLivingDamage(this, source, damage), getDamageCap());

            if (damage > 0.0F)
            {
            	
                getCombatTracker().trackDamage(source, getHealthF(), damage);
                setHealthD(getHealthD() - damage);
                setAbsorptionAmount(getAbsorptionAmount() - damage);
            }
            
            if (isServerWorld() && ticksTalked == 0L)
    			world.playerEntities.forEach(player -> onQuote(player, source.getTrueSource() == null || !(source.getTrueSource() instanceof EntityPlayer) ? 3 : 2, previousDamage >= 5000000.0D ? 3 : previousDamage >= 750000.0D ? 2 : previousDamage > 100.0D ? 1 : 0));
        }
    }
	
	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart entity, DamageSource source, float damage)
	{
		return attackEntityFrom(source, damage);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		Entity attacker = source.getTrueSource();
		boolean flag = true;
		
		if (isServerWorld() && !isEntityInvulnerable(source) && attacker != null && amount <= 100.0F)
		{
			previousAttackingEntity = attacker;
    		world.playerEntities.forEach(player -> onQuote(player, 3, 0));
		}
		
		if (world.isRemote || isEntityInvulnerable(source) || (TitanConfig.enableAnticheat ? amount <= 100.0F : false) || (attacker != null && attacker.equals(getRidingEntity())))
			return false;
		
		
		if (attacker instanceof EntityTitan)
			amount *= 5F;
	
		if (amount <= 100.0F)
			amount = 1.0F;
		
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
				playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
			onDeath(source);
		}
		else if (flag)
			playSound(getHurtSound(source), getSoundVolume(), getSoundPitch());

		attackedAtYaw = 0.0F;
		return true;
	}

	@SideOnly(Side.CLIENT)
		public void shakeNearbyPlayerCameras(double distance)
		{
			if (!world.playerEntities.isEmpty())
			{
				for (int l1 = 0; l1 < world.playerEntities.size(); ++l1)
				{
					Entity entity = (Entity)world.playerEntities.get(l1);
					if (entity != null && entity.dimension == dimension && entity.isEntityAlive() && entity instanceof EntityLivingBase && !(entity instanceof EntityTitan) && entity.getDistance(this) < distance)
					{
						entity.hurtResistantTime = 0;
						world.setEntityState((EntityLivingBase)entity, (byte)2);
					}
				}
			}
		}
	
	public boolean attackChoosenEntity(Entity victim, float damage, int knockbackAmount, boolean bypass)
	{
		DamageSource source = bypass ? new DamageSource("infinity").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute() : new DamageSource("other").setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode();
		if (victim == null || isEntityBlacklisted(victim) || (!bypass && victim.isEntityInvulnerable(source)))
			return false;

		if (TitanConfig.enableNightmareMode)
			damage *= 2.0F;
			
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
		
		if (victim.height < 6.0F)
		{
			victim.motionY += rand.nextDouble();
			victim.addVelocity(-MathHelper.sin(renderYawOffset * (float)Math.PI / 180.0F) * (1 + knockbackAmount) * 0.2D, (1 + knockbackAmount) * 0.2D, MathHelper.cos(renderYawOffset * (float)Math.PI / 180.0F) * (1 + knockbackAmount) * 0.2D);
		}
		else
			damage = MathHelper.clamp(damage * 20.0F, 0.0F, Float.MAX_VALUE);

		if (victim instanceof EntityLivingBase)
		{
			renderYawOffset = rotationYaw = rotationYawHead;
			damage = MathHelper.clamp(damage + EnchantmentHelper.getModifierForCreature(getHeldItemMainhand(), ((EntityLivingBase)victim).getCreatureAttribute()), 0.0F, Float.MAX_VALUE);

			if (knockbackAmount > 0)
			{
				knockbackAmount += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)victim);
				victim.motionY += rand.nextDouble();
				victim.addVelocity(-MathHelper.sin(renderYawOffset * 3.1415927F / 180.0F) * knockbackAmount * 0.25D, knockbackAmount * 0.25D, MathHelper.cos(renderYawOffset * 3.1415927F / 180.0F) * knockbackAmount * 0.25D);
			}

			if (EnchantmentHelper.getFireAspectModifier(this) > 0)
				victim.setFire(EnchantmentHelper.getFireAspectModifier(this) * 100);
			
			if (victim instanceof EntityEnderCrystal)
				victim.attackEntityFrom((new DamageSource("other")).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode(), 100F);
			else if (victim instanceof EntityDragon)
				world.newExplosion(null, victim.posX, victim.posY, victim.posZ, 6F, false, false);
			else
				victim.attackEntityFrom(source, damage);
			
			return true;
		}
		else
		{
			victim.attackEntityFrom(source, damage);
			return true;
		}
	}

	public boolean attackEntityAsMob(Entity victim)
	{
		float f = 1.0F;
		int i = 1;
		attackChoosenEntity(victim, f, i, false);
		if ((victim instanceof EntityMob))
		((EntityMob)victim).setRevengeTarget(this);
		getLookHelper().setLookPositionWithEntity(victim, 180.0F, getVerticalFaceSpeed());
		return true;
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
		entity.posX = event.getTargetX();
		entity.posY = event.getTargetY();
		entity.posZ = event.getTargetZ();
		boolean flag = false;
		BlockPos pos = new BlockPos(MathHelper.floor(entity.posX), MathHelper.floor(entity.posY), MathHelper.floor(entity.posZ));
		
		if (world.isBlockLoaded(pos));
		{
			boolean flag1 = false;
			while (!flag1 && entity.posY > 0)
			{
				IBlockState block = world.getBlockState(pos);
				
				if (block.getMaterial().isSolid())
					flag1 = true;
				else
					--entity.posY;
			}

			if (flag1)
			{
				entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, rotationYaw, rotationPitch);
				if (world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(entity.getEntityBoundingBox()))
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
	
	protected SoundEvent getSwimSound()
	{
		return SoundEvents.ENTITY_HOSTILE_SWIM;
	}

	protected SoundEvent getSplashSound()
	{
		return SoundEvents.ENTITY_HOSTILE_SPLASH;
	}
	
	protected SoundEvent getSpawnSound()
	{
		return TSounds.get("titan.birth");
	}
	
	@Override
	protected float getSoundVolume()
	{
		return 1000.0F;
	}
	
	protected abstract SoundEvent getHurtSound(DamageSource damageSourceIn);
	
	protected abstract SoundEvent getDeathSound();
	
    protected abstract SoundEvent getAmbientSound();
	
	public Entity[] getParts()
    {
		return hitboxes.toArray(new Entity[hitboxes.size()]);
    }
	
	public final double getHealthD()
	{
		return getEntityAttribute(titanHealth).getAttributeValue();
	}

	public final float getHealthF()
	{
		double value = getHealthD();
		return value > Float.MAX_VALUE ? Float.MAX_VALUE : (float) value;
	}

	protected void setHealthD(double value)
	{
		getEntityAttribute(titanHealth).setBaseValue(MathHelper.clamp(value, 0.0D, getEntityAttribute(titanMaxHealth).getBaseValue()));
	}

	public final double getMaxHealthD()
	{
		return getEntityAttribute(titanMaxHealth).getAttributeValue();
	}
	
	public final float getMaxHealthF()
	{
		double value = getMaxHealthD();
		return value > Float.MAX_VALUE ? Float.MAX_VALUE : (float) value;
	}

	protected final void setMaxHealth(double value)
	{
		getEntityAttribute(titanMaxHealth).setBaseValue(MathHelper.clamp(value, 0.0D, Double.MAX_VALUE));
	}
	
	protected double getDamageCap()
	{
		return TitanConfig.enableAnticheat ? getMaxHealthD() * 0.05D : Double.MAX_VALUE;
	}
	
	public void heal(double value)
	{
		getEntityAttribute(titanHealth).setBaseValue(getEntityAttribute(titanMaxHealth).getBaseValue() + MathHelper.clamp(value, 0.0D, Double.MAX_VALUE));
	}

	public void hurt(double value)
	{
		if (isEntityInvulnerable(DamageSource.GENERIC)) return;
	
		attackEntityFrom(DamageSource.GENERIC, (float) value);
	}

	@Override
	public boolean isEntityInvulnerable(DamageSource source)
	{
		return source.damageType.equals("titan_damage") ? false : (TitanConfig.enableAnticheat ? (source.canHarmInCreative() || source.isExplosion() || source.isMagicDamage() || source.isProjectile()) : false)  || source.isDamageAbsolute() || source.isFireDamage() || source.damageType.equals("cactus") || source.damageType.equals("anvil") || source.damageType.equals("fallingBlock") || super.isEntityInvulnerable(source);
	}

	
	protected boolean isEntityBlacklisted(Entity entity)
	{
		if (entity instanceof EntityFallingBlock || entity instanceof EntityItem || entity instanceof EntityXPOrb)
			return true;
		
		String uuid = entity.getUniqueID().toString();
		return uuid == "07d5aa7f-81d0-41e1-8981-04723d12c2ef" || uuid == "19d96ed2-6c4d-42bd-9855-498482daa5ab" || uuid == "39c0cf10-5f5d-4c89-8057-cee67479c7c2";
	}

	

	public boolean isEntityAlive()
	{
		return getHealthD() > 0.0F;
	}

	@Override
	public float getEyeHeight()
    {
        return getSizeMultiplier();
    }
	
	public boolean canBePushed()
	{
		return false;
	}

	public int getMaxSpawnedInChunk()
	{
		return 1;
	}
	
	public boolean isNoDespawnRequired()
	{
		return true;
	}
	
	public boolean isPotionApplicable(PotionEffect p_70687_1_)
	{
		return false;
	}
	
	public boolean getCanSpawnHere()
	{
		return world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire()
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public boolean isInRangeToRenderDist(double distance)
    {
		return true;
    }
	
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEFINED;
	}

	public boolean isStunned()
	{
		return isStunned;
	}
	
	public int getDeathTicks()
	{
		return deathTicks;
	}
	
	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public boolean hasStamina()
	{
		return true;
	}
	
	@Override
	public boolean canRenderBar()
	{
		return !isInvisible();
	}
	
	@Override
	public boolean canShowDamage()
	{
		return true;
	}
	
	@Override
	public double getBarHealth()
	{
		return getHealthD();
	}
	
	@Override
	public double getBarMaxHealth()
	{
		return getMaxHealthD();
	}
	
	@Override
	public double getBarStamina()
	{
		return getStamina();
	}
	
	@Override
	public double getBarMaxStamina()
	{
		return getMaxStamina();
	}
	
	@Override
	public String getBarName() {
		return getName();
	}
	
	protected void setStamina(double value)
	{
		getEntityAttribute(titanStamina).setBaseValue(value);
	}
	
	protected void setMaxStamina(double value)
	{
		getEntityAttribute(titanMaxStamina).setBaseValue(value);
	}
	
	public double getStamina()
	{
		return getEntityAttribute(titanStamina).getAttributeValue();
	}

	public double getMaxStamina()
	{
		return getEntityAttribute(titanMaxStamina).getAttributeValue();
	}
	
	public boolean isDead()
	{
		return true;
	}
}