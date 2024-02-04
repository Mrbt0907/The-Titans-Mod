package net.minecraft.titans.entity.animal;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class EntityEndSquid extends EntitySquid implements IEndMob, IShearable
{    
	protected int despawnTime;
    private static final DataParameter<Boolean> TAME = EntityDataManager.<Boolean>createKey(EntityEndSquid.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BARNACLES = EntityDataManager.<Integer>createKey(EntityEndSquid.class, DataSerializers.VARINT);
    public int timeUntilNextBarnacle;
    
	public EntityEndSquid(World worldIn) 
	{
		super(worldIn);
		this.experienceValue = 5;
        this.timeUntilNextBarnacle = this.rand.nextInt(8000) + 12000;
	}
	
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityEndSquid.AIReactToPlayers(this));
    }
	
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(TAME, Boolean.valueOf(false));
        this.dataManager.register(BARNACLES, Integer.valueOf(0));
    }
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
    }

    public boolean getTame()
    {
        return ((Boolean)this.dataManager.get(TAME)).booleanValue();
    }

    public void setTame(boolean saddled)
    {
        if (saddled)
        {
            this.dataManager.set(TAME, Boolean.valueOf(true));
        }
        else
        {
            this.dataManager.set(TAME, Boolean.valueOf(false));
        }
    }

    public int getBarnacles()
    {
        return ((Integer)this.dataManager.get(BARNACLES)).intValue();
    }

    public void setBarnacles(int num)
    {
    	if (num > 10)
    	num = 10;
    	if (num < 0)
    	num = 0;
    	
        this.dataManager.set(BARNACLES, Integer.valueOf(num));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("IsTamed", this.getTame());
        compound.setInteger("Barnacles", this.getBarnacles());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setTame(compound.getBoolean("IsTamed"));
        this.setBarnacles(compound.getInteger("Barnacles"));
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (!super.processInteract(player, hand))
        {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.getItem() == Items.NAME_TAG)
            {
                itemstack.interactWithEntity(player, this, hand);
                return true;
            }
            else if (itemstack.getItem() == Items.CHORUS_FRUIT && !this.getTame())
            {
                this.setTame(true);
                this.enablePersistence();
                this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundVolume(), this.getSoundPitch());
                this.playSound(SoundEvents.ENTITY_PLAYER_BURP, this.getSoundVolume(), this.getSoundPitch());
                itemstack.shrink(1);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    public boolean getCanSpawnHere()
    {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox()) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox());
    }

    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source)
    {
        this.dropFewItems(wasRecentlyHit, lootingModifier);
        this.dropEquipment(wasRecentlyHit, lootingModifier);
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
    }
    
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        int j = this.rand.nextInt(3 + p_70628_2_) + 1;

        for (int k = 0; k < j; ++k)
        {
            this.entityDropItem(new ItemStack(Items.DYE, 1, 0), 0.0F);
        }
        
        j = this.rand.nextInt(2 + p_70628_2_) + 1;

        for (int k = 0; k < j; ++k)
        {
            this.dropItem(Items.ENDER_PEARL, 1);
        }
        
        j = this.rand.nextInt(3 + p_70628_2_) + 1;

        for (int k = 0; k < j; ++k)
        {
            this.dropItem(Items.CHORUS_FRUIT, 1);
        }
        
        if (p_70628_1_ && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
        {
            this.dropItem(Items.ENDER_EYE, 1);
        }
    }
    
    /**
     * Teleport the enderman to a random nearby position
     */
    protected boolean teleportRandomly()
    {
        double d = 16.0D;
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * d;
        double d1 = this.posY + (this.rand.nextDouble() - 0.5D) * d;
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * d;
        return this.teleportTo(d0, d1, d2);
    }

    private boolean teleportTo(double x, double y, double z)
    {
        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) 
        	return false;
        
        this.posX = event.getTargetX();
        this.posY = event.getTargetY();
        this.posZ = event.getTargetZ();
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY);
        int k = MathHelper.floor(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);
        
        if (world.getBlockState(pos).isOpaqueCube())
            this.setPosition(this.posX, world.getTopSolidOrLiquidBlock(pos).getY(), this.posZ);
        else
        	this.setPosition(this.posX, this.posY, this.posZ);
        
        this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
        this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        
        for (i = 0; i < 20; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
        }

        return true;
    }
    
    public boolean isWet()
    {
        if (this.world.handleMaterialAcceleration(this.getEntityBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D), Material.WATER, this))
        {
            return true;
        }
        else
        {
            BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, this.posY, this.posZ);

            if (!this.world.isRainingAt(blockpos$pooledmutableblockpos) && !this.world.isRainingAt(blockpos$pooledmutableblockpos.setPos(this.posX, this.posY + (double)this.height, this.posZ)))
            {
                blockpos$pooledmutableblockpos.release();
                return false;
            }
            else
            {
                blockpos$pooledmutableblockpos.release();
                return true;
            }
        }
    }
    
    public boolean isInWater()
    {
        return true;
    }
    
    protected int getExperiencePoints(EntityPlayer p_70693_1_)
    {
        if (this.experienceValue > 0)
        {
            int i = this.experienceValue;

            return i + this.rand.nextInt(experienceValue);
        }
        else
        {
            return this.experienceValue;
        }
    }
    
    /**
     * Makes the entity despawn if requirements are reached
     */
    protected void despawnEntity()
    {
        net.minecraftforge.fml.common.eventhandler.Event.Result result = null;
        if (this.isNoDespawnRequired())
        {
            this.despawnTime = 0;
        }
        else if ((this.despawnTime & 0x1F) == 0x1F && (result = net.minecraftforge.event.ForgeEventFactory.canEntityDespawn(this)) != net.minecraftforge.fml.common.eventhandler.Event.Result.DEFAULT)
        {
            if (result == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY)
            {
                this.despawnTime = 0;
            }
            else
            {
                this.setDead();
            }
        }
        else
        {
            Entity entity = this.world.getClosestPlayerToEntity(this, -1.0D);

            if (entity != null)
            {
                double d0 = entity.posX - this.posX;
                double d1 = entity.posY - this.posY;
                double d2 = entity.posZ - this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.canDespawn() && d3 > 16384.0D)
                {
                    this.setDead();
                }

                if (this.despawnTime > 600 && this.rand.nextInt(800) == 0 && d3 > 1024.0D && this.canDespawn())
                {
                    this.setDead();
                }
                else if (d3 < 1024.0D)
                {
                    this.despawnTime = 0;
                }
            }
        }
    }
    
    public void onLivingUpdate()
    {
    	this.inWater = this.isInWater();
    	this.fallDistance = 0;
    	this.idleTime = 0;
    	super.onLivingUpdate();
    	if (!this.getTame())
    		++this.despawnTime;
    	
        if (this.world.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
        else
        {
            if (!this.world.isRemote && !this.isChild() && this.getBarnacles() < 10 && --this.timeUntilNextBarnacle <= 0)
            {
                this.playSound(SoundEvents.BLOCK_CHORUS_FLOWER_GROW, this.getSoundVolume(), this.getSoundPitch());
                this.setBarnacles(this.getBarnacles() + 1);
                this.timeUntilNextBarnacle = this.rand.nextInt(8000) + 12000;
            }
            
            if (this.isWet() || this.isBurning() || this.isEntityInsideOpaqueBlock())
            {
                this.teleportRandomly();
            }
            
            if (this.world.isDaytime())
            {
                float f = this.getBrightness();

                if (f > 0.5F && this.world.canSeeSky(new BlockPos(this)) && this.rand.nextFloat() * 80.0F < (f - 0.4F) * 2.0F)
                {
                    this.setAttackTarget((EntityLivingBase)null);
                    this.teleportRandomly();
                }
            }
        }
    	
        if (this.isWet())
        {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }
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
        else
        {
            if (source instanceof EntityDamageSourceIndirect)
            {
                for (int i = 0; i < 64; ++i)
                {
                    if (this.teleportRandomly())
                    {
                        return false;
                    }
                }
            }
            
            if (super.attackEntityFrom(source, amount))
            {
            	if (rand.nextInt(10) == 0 || amount >= 5)
            		this.teleportRandomly();
            	
            	return true;
            }
            else
            {
            	return false;
            }
        }
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setBarnacles(rand.nextInt(10));
        this.teleportTo(posX, posY + 48 + rand.nextInt(24), posZ);
        this.teleportRandomly();
        return livingdata;
    }

    @Override 
    public boolean isShearable(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos)
    { 
    	return this.getBarnacles() > 0 && this.getTame(); 
    }
    
    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        for (int j = 0; j < this.getBarnacles(); ++j)
            ret.add(new ItemStack(Items.CHORUS_FRUIT, 1, 0));

        this.playSound(SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 1.0F, 1.0F);
        this.setBarnacles(0);
        return ret;
    }

    static class AIReactToPlayers extends EntityAIBase
        {
            private final EntityEndSquid squid;

            public AIReactToPlayers(EntityEndSquid p_i45859_1_)
            {
                this.squid = p_i45859_1_;
            }

            /**
             * Returns whether the EntityAIBase should begin execution.
             */
            public boolean shouldExecute()
            {
                return true;
            }

            /**
             * Keep ticking a continuous task that has already been started
             */
            public void updateTask()
            {
                EntityPlayer player = this.squid.world.getClosestPlayerToEntity(squid, squid.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue());

                if (player != null && player.getHeldItemMainhand().getItem() == Items.CHORUS_FRUIT)
                {
    				double d01 = player.posX - this.squid.posX;
    				double d11 = player.posY - this.squid.posY;
    				double d21 = player.posZ - this.squid.posZ;
    				float fl2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
    				float f1 = (float)(d01 / fl2 * 0.2D);
    				float f2 = (float)(d11 / fl2 * 0.2D);
    				float f3 = (float)(d21 / fl2 * 0.2D);
    				if (squid.getDistance(player) <= 2D)
        				this.squid.setMovementVector(0, 0, 0);
    				else
    					this.squid.setMovementVector(f1, f2, f3);
                }
                else if (player != null && !player.capabilities.disableDamage && player.getHeldItemMainhand().getItem() != Items.CHORUS_FRUIT && !squid.getTame())
                {
    				double d01 = player.posX - this.squid.posX;
    				double d11 = player.posY - this.squid.posY;
    				double d21 = player.posZ - this.squid.posZ;
    				float fl2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
    				float f1 = (float)(d01 / fl2 * 0.2D);
    				float f2 = (float)(d11 / fl2 * 0.2D);
    				float f3 = (float)(d21 / fl2 * 0.2D);
    				this.squid.setMovementVector(-f1, -f2, -f3);
    				
    				if (squid.getDistance(player) <= 4D)
    					squid.teleportTo(squid.posX + (squid.getRNG().nextDouble() - 0.5D) * 8D, squid.posY + 12D, squid.posX + (squid.getRNG().nextDouble() - 0.5D) * 8D);
                }
                else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.inWater || !this.squid.hasMovementVector())
                {
                    float f = this.squid.getRNG().nextFloat() * ((float)Math.PI * 2F);
                    float f1 = MathHelper.cos(f) * 0.2F;
                    float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
                    float f3 = MathHelper.sin(f) * 0.2F;
                    this.squid.setMovementVector(f1, f2, f3);
                }
                
            }
        }
}
