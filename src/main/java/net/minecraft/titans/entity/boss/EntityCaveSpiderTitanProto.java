package net.minecraft.titans.entity.boss;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.endermanofdoom.mac.util.math.Maths;
import net.endermanofdoom.mca.MCA;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityCaveSpiderTitanProto extends EntityPreTitan
{
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityCaveSpiderTitanProto.class, DataSerializers.BYTE);

    public EntityCaveSpiderTitanProto(World worldIn) {
		super(worldIn);
		this.experienceValue = 1000;
	}

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote)
        {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    /**
     * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
     * for AI reasons)
     */
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    /**
     * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
     * setBesideClimableBlock.
     */
    public boolean isBesideClimbableBlock()
    {
        return (((Byte)this.dataManager.get(CLIMBING)).byteValue() & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setBesideClimbableBlock(boolean climbing)
    {
        byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();

        if (climbing)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(CLIMBING, Byte.valueOf(b0));
    }
	
	protected SoundEvent getAmbientSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SPIDER_AMBIENT : TSounds.get("titan.spider.living");
	}
	
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SPIDER_HURT : TSounds.get("titan.spider.grunt");
	}
	
	protected SoundEvent getDeathSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SPIDER_DEATH : TSounds.get("titan.spider.death");
	}
	
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		playSound(this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SPIDER_STEP : TSounds.get("titan.step"), this.getSoundVolume(), this.getSoundPitch() + 0.5F);
	}

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return (this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.75F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.25F) - (this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? this.getSizeMultiplier() * 0.05F : this.getSizeMultiplier() * 0.01F);
    }

    public float getBaseWidth()
    {
    	return 1.4F;
    }

    public float getBaseHeight()
    {
    	return 0.8F;
    }

    public float getEyeHeight()
    {
        return 0.65F * getSizeMultiplier();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
    }
    
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (this.getVariant() >= 63 && !world.isRemote)
		{
			if (ticksExisted % 10 == 0)
			{
				List<Entity> targets = new ArrayList<Entity>(world.loadedEntityList);
				targets.forEach(victim ->
				{
					if (Maths.random(6000) == 0)
						if (victim instanceof EntityLivingBase && victim.isEntityAlive() && victim != this)
						{
							victim.attackEntityFrom(DamageSource.LIGHTNING_BOLT.setMagicDamage().setDamageBypassesArmor(), 100F);
							EntityLightningBolt entitylightning = new EntityLightningBolt(world, victim.posX, victim.posY, victim.posZ, false);
							world.addWeatherEffect(entitylightning);
						}
				});
			}
			
			if (rand.nextInt(800) == 0)
			{
				for (int l = 0; l < 20; l++)
				{
					int i = MathHelper.floor(posX);
					int j = MathHelper.floor(posY);
					int k = MathHelper.floor(posZ);
					
					Maths.updateRandom(rand);
					int i1 = (int) (i + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 2F);
					int j1 = (int) (j + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 2F);
					int k1 = (int) (k + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 2F);
					Maths.updateRandom();
					
					EntityLightningBolt entitylightning = new EntityLightningBolt(world, i1, j1, k1, false);
					
					if (world.getTopSolidOrLiquidBlock(new BlockPos(i1, j1 - 1, k1)).getY() > 0 && world.checkNoEntityCollision(entitylightning.getEntityBoundingBox()) && world.getCollisionBoxes(entitylightning, entitylightning.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(entitylightning.getEntityBoundingBox()))
						world.addWeatherEffect(entitylightning);
				}
			}
		}
	}

	public double getMobHealth() 
	{
		double hp = MCA.caclculateValue(world, 4000D * this.getSizeMultiplier() * this.getTier().getMultiplier());
		
		if (this.getVariant() > 7)
			hp *= 4D;
		
		if (this.getVariant() >= 63)
			hp *= 20D;
		
		if (world.playerEntities.size() > 1)
			hp *= world.playerEntities.size();
		
		return hp <= 16 ? 16 : hp;
	}

	public double getMobAttack() 
	{
		double attack = MCA.caclculateValue(world, 6D * this.getSizeMultiplier() * this.getTier().getMultiplier());

		if (this.getVariant() > 7)
			attack *= 5D;
		
		return attack;
	}

	public double getMobSpeed() 
	{
		double speed = 1.75D - (getSizeMultiplier() * 0.075);

		if (speed <= 0.325D)
			speed = 0.325D;
		
		return speed;
	}
    
	public int[] getBarColor() 
	{
		return new int[] {180 - (getVariant() / 5), 0, 0};
	}

	@Override
	public float getSizeMultiplier() 
	{
		float size = this.getVariant() + 1;
		
		if (this.getInvulTime() > 0)
			size -= this.getInvulTime() * 0.02F;
		
		if (size <= 1F)
			size = 1F;
		size *= 0.7F;
		
		return size;
	}

	protected EntityLiving getBaseMob() 
	{
		return new EntityCaveSpider(world);
	}
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setVariant(0);

        for (int i = 0; i < 7; ++i)
        {
            this.setVariant(i);
            if (rand.nextFloat() >= 0.5F)
            	break;
        }

    	if (this.getVariant() >= 7)
            this.setVariant(7);

        this.ignite();
    	
        return super.onInitialSpawn(difficulty, livingdata);
    }
}
