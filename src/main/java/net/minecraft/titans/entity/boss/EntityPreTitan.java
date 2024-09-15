package net.minecraft.titans.entity.boss;

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
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.titans.entity.titan.EntityTitan;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityPreTitan extends EntityLiving implements IRangedAttackMob, ISoundSupport, IBossBar, IVariedMob, IGendered, IMobTier, IMob, IMusicInteractable
{
	public int attackTimer;
	public int deathTicks;
    private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.<Integer>createKey(EntityPreTitan.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.<Integer>createKey(EntityPreTitan.class, DataSerializers.VARINT);

	public EntityPreTitan(World worldIn)
	{
		super(worldIn);
		if (worldIn != null && worldIn.isRemote)
			net.endermanofdoom.mac.internal.music.MusicManager.addMusicInteractable((IMusicInteractable) this);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(INVULNERABILITY_TIME, Integer.valueOf(0));
        this.dataManager.register(VARIANT, Integer.valueOf(0));
    }

    /**
     * Initializes this Wither's explosion sequence and makes it invulnerable. Called immediately after spawning.
     */
    public void ignite()
    {
        this.setInvulTime(50 * (1 + this.getVariant()) - 49);
    	this.playLivingSound();
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

		public void onLivingUpdate()
		{
			this.setAir(100);
			if (this.getInvulTime() <= 0)
			{
			super.onLivingUpdate();
			
			this.hurtResistantTime = 0;
			motionY -= 0.3D;
			
	        if (this.isEntityAlive())
	        {
	            this.heal(0.001F * this.getSizeMultiplier() * this.getTier().getMultiplier());

	        	if (deathTicks <= 0)
	        	world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().offset(-motionX, motionY, -motionZ)).forEach(entity ->
	    		{
	    			if (entity instanceof EntityArrow && ((EntityArrow)entity).shootingEntity != null && ((EntityArrow)entity).shootingEntity instanceof EntityPreTitan)
	    			{}
	    			else if (entity.height <= (entity instanceof EntityFireball ? 1F : 8F) && !(entity instanceof EntityPreTitan) && entity.isNonBoss() && entity.getEntityBoundingBox().intersects(getEntityBoundingBox()))
	    			{
	    				if (entity instanceof EntityLivingBase)
	    				entity.attackEntityFrom(DamageSource.causeMobDamage(this), (getSizeMultiplier() * 10) - this.getDistance(entity));
	                    double d2 = entity.posX - this.posX;
	                    double d3 = entity.posZ - this.posZ;
	                    double d4 = d2 * d2 + d3 * d3;
	                    if (this.getDistance(entity) >= 1D)
	                    entity.addVelocity(d2 / d4 * this.getSizeMultiplier() * 0.25D, this.getSizeMultiplier() / 24D, d3 / d4 * this.getSizeMultiplier() * 0.25D);
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
			
			if (this.getRevengeTarget() != null && this.getAttackTarget() != this.getRevengeTarget() && !this.isOnSameTeam(getRevengeTarget()))
				this.setAttackTarget(getRevengeTarget());
			
			if (this.isEntityAlive() && this.getAttackTarget() != null)
			{
				this.getLookHelper().setLookPositionWithEntity(getAttackTarget(), 10, 50);
				this.getMoveHelper().setMoveTo(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 1D);

				if (this.getDistance(this.getAttackTarget()) <= width + getAttackTarget().width + getSizeMultiplier() && attackTimer <= 0)
					this.attackEntityAsMob(getAttackTarget());
				
				this.attackEntityWithRangedAttack(getAttackTarget(), 1F);
				
				if ((this.getAttackTarget().posY > this.posY + height + getAttackTarget().height + 4D || this.getDistance(this.getAttackTarget()) <= width + getAttackTarget().width + getSizeMultiplier()) && rand.nextInt(200 - (int)((getAttackTarget().posY - posY >= 190 ? 190 : getAttackTarget().posY - posY))) == 0 && (this.onGround || posY <= 1.0D))
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
					motionY += this.getSizeMultiplier();
		        	if (motionY > 8)
		        	this.motionY = 8;
				}
					
				if (!this.getAttackTarget().isEntityAlive() || getDistance(getAttackTarget()) > 32D * this.getSizeMultiplier())
				this.setAttackTarget(null);
			}
	        else
	        {
	        	if (world.getClosestPlayerToEntity(this, 16D * this.getSizeMultiplier()) != null)
	        		this.getLookHelper().setLookPositionWithEntity(world.getClosestPlayerToEntity(this, 16D * this.getSizeMultiplier()), 5, 50);
	        	

	        	if (rand.nextInt(20) == 0)
	        	world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(this.getSizeMultiplier() * 16D)).forEach(entity ->
	            {
	                if (entity.isEntityAlive() && !this.isOnSameTeam(entity) && entity instanceof EntityLivingBase && this.getAttackTarget() == null && rand.nextInt(5) == 0 && (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).capabilities.disableDamage || (entity instanceof EntityPreTitan && ((EntityPreTitan)entity).getInvulTime() <= 0)) && entity != this)
	                {
	                	this.setAttackTarget((EntityLivingBase) entity);
	                }
	            });
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
	                this.prevLimbSwingAmount = 0;
	                this.hurtResistantTime = 40;
	                this.rotationPitch = 0;
	                if (ticksExisted > 10)
	                this.ticksExisted = 10;
	                this.posX = this.prevPosX;
	                this.posY = this.prevPosY;
	                this.posZ = this.prevPosZ;
	                if (j1 <= 0)
	                {
	                	if (!this.world.isRemote)
	                    this.world.newExplosion(this, this.posX, this.posY + (height * 0.5F), this.posZ, this.getSizeMultiplier() + 2, false, false);
	                    this.world.playBroadcastSound(1023, new BlockPos(this), 0);
	                }

	                this.setInvulTime(j1);
	            }
			}

			this.lastDamage = Float.MAX_VALUE;
			this.setSize(this.getBaseWidth() * this.getSizeMultiplier(), this.getBaseHeight() * this.getSizeMultiplier());
			
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

	    /**
	     * Sets the width and height of the entity.
	     */
	    protected void setSize(float width, float height)
	    {
	        if (width != this.width || height != this.height)
	        {
	            float f = this.width;
	            this.width = width;
	            this.height = height;

	            if (this.width < f)
	            {
	                double d0 = (double)width * 0.5D;
	                this.setEntityBoundingBox(new AxisAlignedBB(this.posX - d0, this.posY, this.posZ - d0, this.posX + d0, this.posY + (double)this.height, this.posZ + d0));
	                return;
	            }

	            AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
	            this.setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)this.width, axisalignedbb.minY + (double)this.height, axisalignedbb.minZ + (double)this.width));
	        }
	    }

	    public boolean isWet()
	    {
	    	return false;
	    }

	    public boolean isInWater()
	    {
	    	return false;
	    }

	    public boolean isInLava()
	    {
	    	return false;
	    }
	    
	    /**
	     * Checks if the entity is in range to render.
	     */
	    @SideOnly(Side.CLIENT)
	    public boolean isInRangeToRenderDist(double distance)
	    {
	        double d0 = this.getEntityBoundingBox().getAverageEdgeLength();

	        if (Double.isNaN(d0))
	        {
	            d0 = 1.0D;
	        }

	        d0 = d0 * 64.0D * this.getSizeMultiplier();
	        return distance < d0 * d0;
	    }
		
		public boolean attackEntityAsMob(Entity entityIn)
		{
	        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()) * (entityIn.getEyeHeight() >= 8 || entityIn.height >= 8 || entityIn instanceof EntityPreTitan || entityIn instanceof EntityTitan ? 20F : 1F));

	        if (entityIn instanceof EntityDragon && ((EntityDragon)entityIn).getHealth() > 1)
	        {
	        	flag = entityIn.attackEntityFrom(DamageSource.GENERIC.setExplosion(), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
	        	world.createExplosion(null, entityIn.posX, entityIn.posY + 2D, entityIn.posZ, 3, true);
	        }
	        
	        if (!this.isEntityAlive())
	        	flag = false;
	        
	        if (flag)
	        {
	        	this.swingArm(EnumHand.MAIN_HAND);
	            this.applyEnchantments(this, entityIn);
				this.attackTimer = 10;
				this.world.setEntityState(this, (byte)4);
				if (!(entityIn instanceof EntityPreTitan))
				attackWithAdditionalEffects(entityIn);
	    		world.getEntitiesWithinAABBExcludingEntity(this, entityIn.getEntityBoundingBox().grow(this.getSizeMultiplier() * 0.25D)).forEach(entity ->
	    		{
	    			entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 0.25D));
    				if (!(entity instanceof EntityPreTitan))
    					attackWithAdditionalEffects(entity);
	    		});
	        }

	        return flag;
		}
		
	    public void onKillEntity(EntityLivingBase entityLivingIn)
	    {
	        super.onKillEntity(entityLivingIn);
	        
    		if (entityLivingIn instanceof EntityPreTitan && entityLivingIn.getClass() == this.getClass())
    		{
    			if (getVariant() >= 63)
    				this.heal(((EntityPreTitan)entityLivingIn).getMaxHealth());
    			else
    			{
	    			this.setVariant(this.getVariant() + ((EntityPreTitan)entityLivingIn).getVariant() + 1);
	    			this.setInvulTime(50 * (((EntityPreTitan)entityLivingIn).getVariant() + 1));
	    			world.removeEntity(entityLivingIn);
    			}
    		}
	    }
		
		public void attackWithAdditionalEffects(Entity entity)
		{
			double amount = this.getSizeMultiplier() / 16D;
			if (this.world.getDifficulty() == EnumDifficulty.EASY)
			amount *= 0.75D;
			if (this.world.getDifficulty() == EnumDifficulty.HARD)
			amount *= 1.5D;
			if (entity instanceof EntityLivingBase)
			{
				((EntityLivingBase)entity).prevRenderYawOffset = ((EntityLivingBase)entity).renderYawOffset = ((EntityLivingBase)entity).prevRotationYaw = ((EntityLivingBase)entity).rotationYaw = ((EntityLivingBase)entity).prevRotationYawHead = ((EntityLivingBase)entity).rotationYawHead = this.rotationYawHead;
			}
				float xRatio = MathHelper.sin(this.rotationYawHead * 0.017453292F);
				float zRatio = -MathHelper.cos(this.rotationYawHead * 0.017453292F);
				entity.isAirBorne = true;
				float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
				entity.motionX /= 3.0D;
				entity.motionZ /= 3.0D;
				entity.motionX -= xRatio / (double)f * amount;
				entity.motionZ -= zRatio / (double)f * amount;
				entity.motionY /= 2.0D;
				entity.motionY += amount;
				if (entity instanceof EntityPlayerMP)
				((EntityPlayerMP)entity).connection.sendPacket(new SPacketEntityVelocity((EntityPlayerMP)entity));
		}
		
		@SideOnly(Side.CLIENT)
		public void handleStatusUpdate(byte id)
		{
			if (id == 4)
			{
				this.attackTimer = 10;
			}
			else
			{
				super.handleStatusUpdate(id);
			}
		}
		
		public void fall(float distance, float damageMultiplier) 
		{
			if (distance > 2 * this.getSizeMultiplier())
			{
				this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, this.getSoundVolume(), this.getSoundPitch() / this.getSizeMultiplier());
				
	    		world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(getSizeMultiplier() * 4D)).forEach(entity ->
	    		{
    				if (entity instanceof EntityLivingBase)
    				entity.attackEntityFrom(DamageSource.causeMobDamage(this), (180F + distance - this.getDistance(entity)) * getSizeMultiplier());
                    double d2 = entity.posX - this.posX;
                    double d3 = entity.posZ - this.posZ;
                    double d4 = d2 * d2 + d3 * d3;
                    entity.addVelocity(d2 / d4 * this.getSizeMultiplier() * (0.5D + (distance * 0.05D)), this.getSizeMultiplier() * (0.1D + (distance * 0.01D)), d3 / d4 * this.getSizeMultiplier() * (0.5D + (distance * 0.05D)));
	    		});
			}
		}

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
				
    			this.motionX = this.motionY = this.motionZ = 0;
                this.limbSwingAmount = 0;
                this.limbSwing = 0;
                this.prevLimbSwingAmount = 0;
                this.hurtResistantTime = 40;
                if (ticksExisted > 10)
                this.ticksExisted = 10;
	        	if (deathTicks <= 15)
				this.rotationPitch = 50;
	        	else if (deathTicks <= 25 && deathTicks > 15)
					this.rotationPitch = 55 - (deathTicks * 5);
	        	else
					this.rotationPitch = -50;
				
				if (this.deathTicks == 60)
				{
	                if (!this.world.isRemote && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))
	                {
	                    this.dropLoot(true, 0, getLastDamageSource());
						for (EntityPlayer entityplayer : world.playerEntities)
						{
			                if (entityplayer.isEntityAlive() && !entityplayer.isSpectator())
	    	                	MCA.dropXP(this, entityplayer.posX, entityplayer.posY, entityplayer.posZ, (experienceValue * ((int)getVariant() + 1)));
						}
	                }
					this.setDead();
					this.spawnExplosionParticle();
				}
				
		        if (this.deathTicks <= 60)
		        {
	            	if (this.deathTicks >= 40 && this.deathTime < 20)
	            		++this.deathTime;
	            	else if (this.deathTicks < 40)
	            		this.deathTime = 0;
	            	
	            	if (this.deathTime == 15)
	            		this.playSound(this.getSizeMultiplier() > 7 ? TSounds.get("titan.fall") : MCASounds.heavyfall, this.getSizeMultiplier(), 1F);
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
		        
		        return super.isOnSameTeam(entityIn);
		    }

		    /**
		     * Called when the entity is attacked.
		     */
		    public boolean attackEntityFrom(DamageSource source, float amount)
		    {
		        if (this.isEntityInvulnerable(source))
		        {
		            return false;
		        }
		        else if (source.getTrueSource() != null && this.isOnSameTeam(source.getTrueSource()))
		        {
		            return false;
		        }
		        else
		        {
		            return super.attackEntityFrom(source, amount);
		        }
		    }

		    public boolean isImmuneToExplosions()
		    {
		        return true;
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

			public void setVariant(int type) 
			{
				if (type >= 63)
					type = 63;
		        this.dataManager.set(VARIANT, Integer.valueOf(type));
			}

			public int getVariant() 
			{
		        return ((Integer)this.dataManager.get(VARIANT)).intValue();
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

		    /**
		     * Get the name of this object. For players this returns their username
		     */
		    public String getName()
		    {
		    	String supression = null;
		    	
		    	if (this.getVariant() >= 63)
		    		supression = I18n.format("entity.titan.level4.name");
		    	else if (this.getVariant() >= 31)
		    		supression = I18n.format("entity.titan.level3.name");
		    	else if (this.getVariant() >= 15)
		    		supression = I18n.format("entity.titan.level2.name");
		    	else if (this.getVariant() >= 7)
		    		supression = I18n.format("entity.titan.level1.name");
		    	
		    	String name = I18n.format("entity." + EntityList.getEntityString(getBaseMob()) + ".name");
		        if (this.hasCustomName())
		        	name = getCustomNameTag();

		        if (supression != null)
		        	return name + " " + I18n.format("entity.titan.name") + " " + supression;
		        else
		        	return name + " " + I18n.format("entity.demititan.name");
		    }

			protected EntityLiving getBaseMob() 
			{
				return null;
			}
		}