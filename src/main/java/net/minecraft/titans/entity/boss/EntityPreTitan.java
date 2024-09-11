package net.minecraft.titans.entity.boss;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import net.endermanofdoom.mac.enums.EnumGender;
import net.endermanofdoom.mac.enums.EnumLevel;
import net.endermanofdoom.mac.interfaces.IBossBar;
import net.endermanofdoom.mac.interfaces.IGendered;
import net.endermanofdoom.mac.interfaces.IMobTier;
import net.endermanofdoom.mac.interfaces.IVariedMob;
import net.endermanofdoom.mac.music.IMusicInteractable;
import net.endermanofdoom.mac.util.chunk.MobChunkLoader;
import net.endermanofdoom.mca.EnumSoundType;
import net.endermanofdoom.mca.ISoundSupport;
import net.endermanofdoom.mca.MCA;
import net.endermanofdoom.mca.registrey.MCASounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityPreTitan extends EntityLiving implements IRangedAttackMob, ISoundSupport, IBossBar, IVariedMob, IGendered, IMobTier, IMob, IMusicInteractable
{
	public int attackTimer;
	public int deathTicks;
    private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.<Integer>createKey(EntityPreTitan.class, DataSerializers.VARINT);
	
	public EntityPreTitan(World worldIn)
	{
		super(worldIn);
		if (worldIn != null && worldIn.isRemote)
			net.endermanofdoom.mac.internal.music.MusicManager.addMusicInteractable((IMusicInteractable) this);
	}

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(INVULNERABILITY_TIME, Integer.valueOf(0));
    }

    /**
     * Initializes this Wither's explosion sequence and makes it invulnerable. Called immediately after spawning.
     */
    public void ignite()
    {
    	playSound(TSounds.get("titan.birth"), 10F, 1F);
    }

    public int getInvulTime()
    {
        return ((Integer)this.dataManager.get(INVULNERABILITY_TIME)).intValue();
    }

    public void setInvulTime(int time)
    {
        this.dataManager.set(INVULNERABILITY_TIME, Integer.valueOf(time));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Invul", this.getInvulTime());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setInvulTime(compound.getInteger("Invul"));
    }
	
	public int getAttackTimer()
	{
	return attackTimer;
	}

	public boolean isNonBoss()
	{
		return false;
	}

    public float getBaseWidth()
    {
    	return 0.5F;
    }

    public float getBaseHeight()
    {
    	return 2F;
    }

    /**
     * Makes the entity despawn if requirements are reached
     */
    protected void despawnEntity()
    {
        this.idleTime = 0;
    }

	/**
	* Sets the Entity inside a web block.
	*/
	public void setInWeb() {}
	
	public float getRenderSizeMultiplier()
    {
        return getSizeMultiplier();
    }
	
	public abstract float getSizeMultiplier();

    protected boolean canBeRidden(Entity entityIn)
    {
        return false;
    }

    /**
     * adds a PotionEffect to the entity
     */
    public void addPotionEffect(PotionEffect potioneffectIn)
    {
    }

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(4.0D);
	}

    public AxisAlignedBB getCollisionBoundingBox()
    {
        return this.isEntityAlive() ? this.getEntityBoundingBox() : null;
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.ignite();
        return livingdata;
    }

		public void onLivingUpdate()
		{
			if (this.getInvulTime() <= 0)
			{
			super.onLivingUpdate();
			
			motionY -= 0.25D;
			
	        if (this.isEntityAlive())
	        {
		        if (this.ticksExisted % 20 == 0)
		        {
		            this.heal(10F);
		        }
	        	
	    		world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.1D)).forEach(entity ->
	    		{
	    			if (entity.height <= 8F)
	    			{
	    				if (entity instanceof EntityLivingBase)
	    				entity.attackEntityFrom(DamageSource.causeMobDamage(this), 40F - this.getDistance(entity));
	                    double d2 = entity.posX - this.posX;
	                    double d3 = entity.posZ - this.posZ;
	                    double d4 = d2 * d2 + d3 * d3;
	                    entity.addVelocity(d2 / d4 * 10D, 1D, d3 / d4 * 10D);
	    			}
	    		});
	        }
			
			if ((this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D) && (this.rand.nextInt(5) == 0))
			{
				int i = MathHelper.floor(this.posX);
				int j = MathHelper.floor(this.posY - 0.20000000298023224D);
				int k = MathHelper.floor(this.posZ);
				IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
				if (iblockstate.getMaterial() != Material.AIR)
				{
					this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, getEntityBoundingBox().minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(iblockstate) });
				}
			}
			
			if (this.getAttackTarget() != null)
			{
				this.getLookHelper().setLookPositionWithEntity(getAttackTarget(), 10, 50);
				this.getMoveHelper().setMoveTo(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1D);

				if (this.getDistance(this.getAttackTarget()) <= width + getAttackTarget().width + 12D && attackTimer <= 0)
					this.attackEntityAsMob(getAttackTarget());
				
				if (this.getRevengeTarget() != null && this.getAttackTarget() != this.getRevengeTarget())
					this.setAttackTarget(getRevengeTarget());
				
				if (this.getAttackTarget().posY > this.posY + width + getAttackTarget().width + 12D && rand.nextInt(200) == 0 && (this.onGround || posY <= 1.0D))
				{
					this.jump();
		        	double d01 = this.getAttackTarget().posX - this.posX;
		        	double d11 = this.getAttackTarget().posZ - this.posZ;
		        	float f21 = MathHelper.sqrt(d01 * d01 + d11 * d11);
		        	double hor = f21 / this.getDistance(getAttackTarget()) * (this.getDistance(getAttackTarget()) * 0.1D);
		        	this.motionX = (d01 / f21 * hor * hor + this.motionX * hor);
		        	this.motionZ = (d11 / f21 * hor * hor + this.motionZ * hor);
		        	if (motionX > 4)
		        	this.motionX = 4;
		        	if (motionZ > 4)
		        	this.motionZ = 4;
		        	if (motionX < -4)
		        	this.motionX = -4;
		        	if (motionZ < -4)
		        	this.motionZ = -4;
					motionY += 5;
				}
					
				if (!this.getAttackTarget().isEntityAlive())
				this.setAttackTarget(null);
			}
	        else
	        {
	        	if (world.getClosestPlayerToEntity(this, 64D) != null)
	        		this.getLookHelper().setLookPositionWithEntity(world.getClosestPlayerToEntity(this, 64D), 5, 50);
	        	
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
			else
			{
	            if (this.getInvulTime() > 0)
	            {
	                int j1 = this.getInvulTime() - 1;
		            float f = (this.rand.nextFloat() - 0.5F) * (this.getBaseWidth() * getSizeMultiplier());
		            float f1 = this.rand.nextFloat() * height;
		            float f2 = (this.rand.nextFloat() - 0.5F) * (this.getBaseWidth() * getSizeMultiplier());
		            this.world.spawnParticle(this.getSizeMultiplier() > 8 ? EnumParticleTypes.EXPLOSION_HUGE : this.getSizeMultiplier() > 4 ? EnumParticleTypes.EXPLOSION_LARGE : EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)f, this.posY + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
		            for (int i = 0; i <= this.getSizeMultiplier() + 2 * 4; ++i)
		            this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX + (double)f, this.posY + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
	    			this.motionX = this.motionY = this.motionZ = 0;
	                this.limbSwingAmount = 0;
	                this.limbSwing = 0;
	                this.hurtResistantTime = 40;
	                if (ticksExisted > 10)
	                this.ticksExisted = 10;
	                if (j1 <= 0)
	                {
	                    this.world.newExplosion(this, this.posX, this.posY + (double)this.getEyeHeight(), this.posZ, 7.0F + height, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this));
	                    this.world.playBroadcastSound(1023, new BlockPos(this), 0);
	                }

	                this.setInvulTime(j1);
	            }
			}

			this.lastDamage = Float.MAX_VALUE;
			this.setSize(this.getBaseWidth() * (this.getInvulTime() > 0 ? 1F : this.getSizeMultiplier()), this.getBaseHeight() * this.getSizeMultiplier());
			
			this.isImmuneToFire = true;
			if (attackTimer > 0) --attackTimer;
			
			this.stepHeight = height * 0.5F;
			
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
			
	        if (this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() != this.getMobHealth() && this.isEntityAlive())
	        {
	            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getMobHealth());
	            this.setHealth((float) getMobHealth());
	        }
	        if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() != this.getMobAttack())
	        	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getMobAttack());

	        if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() != this.getMobSpeed())
	        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getMobSpeed());
	        
	        if (this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue() != 200D)
	        	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(200D);
	        
	        switch (this.getTier())
	        {
	        case GREATER_TITAN:
	        	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20D);
	        	this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10D);
	        case GRAND_TITAN:
	        	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30D);
	        	this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20D);
        	default:
	        	this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10D);
	        	this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(5D);
	        }

			if (!world.isRemote)
				if (isEntityAlive())
					MobChunkLoader.updateLoaded(this);
				else
					MobChunkLoader.stopLoading(this);
		}
		
		public boolean attackEntityAsMob(Entity entityIn)
		{
	        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

	        if (entityIn instanceof EntityDragon && ((EntityDragon)entityIn).getHealth() > 1)
	        {
	        	flag = entityIn.attackEntityFrom(DamageSource.GENERIC.setExplosion(), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
	        	world.createExplosion(null, entityIn.posX, entityIn.posY + 2D, entityIn.posZ, 3, true);
	        }
	        
	        if (flag)
	        {
	        	this.swingArm(EnumHand.MAIN_HAND);
	            this.applyEnchantments(this, entityIn);
				this.attackTimer = 20;
				this.world.setEntityState(this, (byte)4);
				attackWithAdditionalEffects(entityIn);
	    		world.getEntitiesWithinAABBExcludingEntity(this, entityIn.getEntityBoundingBox().grow(8D)).forEach(entity ->
	    		{
    				entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 0.25D));
	    		});
	        }

	        return flag;
		}
		
		public void attackWithAdditionalEffects(Entity entity)
		{
			double amount = 1D;
			if (this.world.getDifficulty() == EnumDifficulty.EASY)
			amount *= 0.75D;
			if (this.world.getDifficulty() == EnumDifficulty.HARD)
			amount *= 1.5D;
			if (entity instanceof EntityLivingBase)
			{
				((EntityLivingBase)entity).prevRenderYawOffset = ((EntityLivingBase)entity).renderYawOffset = ((EntityLivingBase)entity).prevRotationYaw = ((EntityLivingBase)entity).rotationYaw = ((EntityLivingBase)entity).prevRotationYawHead = ((EntityLivingBase)entity).rotationYawHead = this.rotationYawHead;
				float xRatio = MathHelper.sin(this.rotationYawHead * 0.017453292F);
				float zRatio = -MathHelper.cos(this.rotationYawHead * 0.017453292F);
				entity.isAirBorne = true;
				float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
				entity.motionX /= 3.0D;
				entity.motionZ /= 3.0D;
				entity.motionX -= xRatio / (double)f * 2F;
				entity.motionZ -= zRatio / (double)f * 2F;
				entity.motionY /= 2.0D;
				entity.motionY += amount;
				if (entity instanceof EntityPlayerMP)
				((EntityPlayerMP)entity).connection.sendPacket(new SPacketEntityVelocity((EntityPlayerMP)entity));
			}
			entity.motionY += amount;
		}
		
		@SideOnly(Side.CLIENT)
		public void handleStatusUpdate(byte id)
		{
			if (id == 4)
			{
				this.attackTimer = 20;
			}
			else
			{
				super.handleStatusUpdate(id);
			}
		}
		
		public void fall(float distance, float damageMultiplier) {}

	    /**
	     * Returns the volume for the sounds this mob makes.
	     */
	    protected float getSoundVolume()
	    {
	        return this.getSizeMultiplier();
	    }

	    /**
	     * drops the loot of this entity upon death
	     */
	    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) 
	    {
	    	if (this.deathTime > 2 || this.deathTicks > 2)
	    	this.dropFewItems(wasRecentlyHit, lootingModifier);
	    }

			protected void onDeathUpdate()
			{
				++this.deathTicks;
				
	            if (this.deathTicks > 150 && this.deathTicks % 5 == 0)
	            {
	                if (!this.world.isRemote && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))
	                {
	                	MCA.dropXP(this, posX, posY + 0.5D, posZ, this.experienceValue / 25);
	                }
	            }
				
				if (this.deathTicks == 200)
				{
	                if (!this.world.isRemote && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))
	                {
	                	if (this.world.getDifficulty() == EnumDifficulty.NORMAL)
	                		experienceValue *= 1.5;
	                	if (this.world.getDifficulty() == EnumDifficulty.HARD)
	                		experienceValue *= 3;
	                	MCA.dropXP(this, posX, posY + 0.5D, posZ, this.experienceValue);
	                    this.dropLoot(true, 0, getLastDamageSource());
	                }

					//MCA.completeThreshhold(world, "postSnowman", 0.125F);
					this.setDead();
				}
				
		        if (this.deathTicks <= 200)
		        {
	            	if (this.deathTicks >= 180 && this.deathTime < 20)
	            		++this.deathTime;
	            	else
	            		this.deathTime = 0;
		            float f = (this.rand.nextFloat() - 0.5F) * width;
		            float f1 = this.rand.nextFloat() * height;
		            float f2 = (this.rand.nextFloat() - 0.5F) * width;
		            this.world.spawnParticle(this.getSizeMultiplier() > 8 ? EnumParticleTypes.EXPLOSION_HUGE : this.getSizeMultiplier() > 4 ? EnumParticleTypes.EXPLOSION_LARGE : EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)f, this.posY + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
		            for (int i = 0; i <= this.getSizeMultiplier() + 2 * 4; ++i)
		            this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX + (double)f, this.posY + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
		        }

				if (!this.world.isRemote)
				{
					if (this.deathTicks == 1)
					{
						for (EntityPlayer entityplayer : world.playerEntities)
						{
							world.playSound(null, entityplayer.getPosition(), getDeathSound(), this.getSoundCategory(), getSoundVolume(), 1.0F);
						}
					}
				}
			}

			public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_)
			{
			}
		    
		    public void onKillCommand()
		    {
		        this.isDead = true;
		    }

		    public void setDead() 
		    {
	    		super.setDead();            
	    		for (int i = 0; i < this.width + this.height * 20; ++i)
	            {
	                double d0 = (double)((float)this.getPosition().getX() + this.world.rand.nextFloat());
	                double d1 = (double)((float)this.getPosition().getY() + this.world.rand.nextFloat());
	                double d2 = (double)((float)this.getPosition().getZ() + this.world.rand.nextFloat());
	                double d3 = d0 - posX;
	                double d4 = d1 - posY;
	                double d5 = d2 - posZ;
	                double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
	                d3 = d3 / d6;
	                d4 = d4 / d6;
	                d5 = d5 / d6;
	                double d7 = 0.5D / (d6 / (double)this.height + 0.1D);
	                d7 = d7 * (double)(this.world.rand.nextFloat() * this.world.rand.nextFloat() + 0.3F);
	                d3 = d3 * d7;
	                d4 = d4 * d7;
	                d5 = d5 * d7;
	                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + posX) / 2.0D, (d1 + posY + this.getEyeHeight()) / 2.0D, (d2 + posZ) / 2.0D, d3, d4, d5);
	                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (d0 + posX) / 2.0D, (d1 + posY + this.getEyeHeight()) / 2.0D, (d2 + posZ) / 2.0D, d3, d4, d5);
	                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, d3, d4, d5);
	            }
		    }

		    /**
		     * Returns whether this Entity is on the same team as the given Entity.
		     */
		    public boolean isOnSameTeam(Entity entityIn)
		    {
		        if (entityIn == this)
		        {
		            return true;
		        }
		        
		        if (entityIn.getClass() == this.getClass())
		        {
		            return true;
		        }
		        
		        return super.isOnSameTeam(entityIn);
		    }
			@Override
			public void setSwingingArms(boolean swingingArms) {}

			public EnumLevel getTier() 
			{
				return EnumLevel.LESSER_TITAN;
			}

			public EnumGender getGender() 
			{
				return EnumGender.MALE;
			}

			public void setVariant(int type) {}

			public int getVariant() 
			{
				return 0;
			}

			public EnumSoundType getSoundType() 
			{
				return EnumSoundType.NORMAL;
			}
			
			@Override
			public boolean canRenderBar()
			{
				return true;
			}
			
			@Override
			public double getBarHealth()
			{
				return getHealth();
			}

			@Override
			public double getBarMaxHealth()
			{
				return getMaxHealth();
			}

			@Override
			public String getBarName()
			{
				return getDisplayName().getFormattedText();
			}

			@Override
			public boolean isDead()
			{
				return isDead;
			}

		    /**
		     * Called when the mob's health reaches 0.
		     */
		    public void onDeath(DamageSource cause)
		    {

		        super.onDeath(cause);
		    }
		    
		    @Override
		    @SideOnly(Side.CLIENT)
		    public boolean isMusicDead()
		    {
		    	return isDead;
		    }

		    @Override
		    @SideOnly(Side.CLIENT)
		    public int getMusicPriority()
		    {
		    	return 40;
		    }

		    @Override
		    @SideOnly(Side.CLIENT)
		    public SoundEvent getMusic()
		    {
		    	return MCASounds.MUSIC_BOSS_DRAGON;
		    }
		}