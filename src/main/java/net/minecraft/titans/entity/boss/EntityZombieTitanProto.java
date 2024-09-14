package net.minecraft.titans.entity.boss;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.endermanofdoom.mac.util.math.Maths;
import net.endermanofdoom.mca.MCA;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
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

public class EntityZombieTitanProto extends EntityPreTitan
{
    private static final DataParameter<Integer> VARIANT = EntityDataManager.<Integer>createKey(EntityZombieTitanProto.class, DataSerializers.VARINT);

    public EntityZombieTitanProto(World worldIn) {
		super(worldIn);
	}
	
    public void ignite()
    {
    	super.ignite();

        this.setInvulTime(50 * (1 + this.getVariant()) - 49);
    	
    	playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, 10F, 1F);
    }
	
	protected SoundEvent getAmbientSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_AMBIENT : TSounds.get("titan.zombie.living");
	}
	
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_HURT : TSounds.get("titan.zombie.grunt");
	}
	
	protected SoundEvent getDeathSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_DEATH : TSounds.get("titan.zombie.death");
	}
	
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		playSound(this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_STEP : TSounds.get("titan.step"), this.getSoundVolume(), this.getSoundPitch());
	}

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return (this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.75F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.25F) - (this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? this.getSizeMultiplier() * 0.05F : this.getSizeMultiplier() * 0.01F);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(VARIANT, Integer.valueOf(0));
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
			if (ticksExisted % 2 == 0)
			{
				List<Entity> targets = new ArrayList<Entity>(world.loadedEntityList);
				targets.forEach(victim ->
				{
					if (Maths.random(3000) == 0)
						if (victim instanceof EntityLivingBase && victim.isEntityAlive() && victim != this)
						{
							victim.attackEntityFrom(DamageSource.LIGHTNING_BOLT.setMagicDamage().setDamageBypassesArmor(), 500F);
							EntityLightningBolt entitylightning = new EntityLightningBolt(world, victim.posX, victim.posY, victim.posZ, false);
							world.addWeatherEffect(entitylightning);
						}
				});
			}
			
			if (rand.nextInt(400) == 0)
			{
				for (int l = 0; l < 20; l++)
				{
					int i = MathHelper.floor(posX);
					int j = MathHelper.floor(posY);
					int k = MathHelper.floor(posZ);
					
					Maths.updateRandom(rand);
					int i1 = (int) (i + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 0.4F);
					int j1 = (int) (j + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 0.4F);
					int k1 = (int) (k + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 0.4F);
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
		double hp = MCA.caclculateValue(world, 7000D * this.getSizeMultiplier() * this.getTier().getMultiplier());
		
		if (this.getVariant() > 7)
			hp *= 4D;
		
		if (this.getVariant() >= 63)
			hp *= 20D;
		
		if (world.playerEntities.size() > 1)
			hp *= world.playerEntities.size();
		
		return hp <= 20 ? 20 : hp;
	}

	public double getMobAttack() 
	{
		double attack = MCA.caclculateValue(world, 14D * this.getSizeMultiplier() * this.getTier().getMultiplier());

		if (this.getVariant() > 7)
			attack *= 5D;
		
		return attack;
	}

	public double getMobSpeed() 
	{
		double speed = 0.75D - (getSizeMultiplier() * 0.075);

		if (speed <= 0.25D)
			speed = 0.25D;
		
		return speed;
	}
    
	public int[] getBarColor() 
	{
		if (getVariant() >= 63)
			return new int[] {0, 120, 40};
		if (getVariant() >= 15)
			return new int[] {0, 180, 40};
		else if (getVariant() >= 7)
			return new int[] {0, 200, 60};
		else
			return new int[] {0, 220, 80};
	}

	@Override
	public float getSizeMultiplier() 
	{
		float size = this.getVariant() + 1;
		
		if (this.getInvulTime() > 0)
			size -= this.getInvulTime() * 0.02F;
		
		if (size <= 1F)
			size = 1F;
		
		return size;
	}

	public void setVariant(int type) 
	{
		if (type >= 63)
			type = 63;
		
        this.dataManager.set(VARIANT, Integer.valueOf(type));

		this.experienceValue = 10000 * (1 + type);
	}

	public int getVariant() 
	{
        return ((Integer)this.dataManager.get(VARIANT)).intValue();
	}
    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        if (this.hasCustomName())
        {
            return this.getCustomNameTag();
        }
        else
        {
        	if (this.getVariant() >= 63)
                return I18n.format("entity.zombie_titan_proto.supreme.name");
        	if (this.getVariant() >= 31)
                return I18n.format("entity.zombie_titan_proto.og1710.name");
        	else if (this.getVariant() >= 15)
                return I18n.format("entity.zombie_titan_proto.og18.name");
        	else
        		return I18n.format("entity.zombie_titan_proto.demititan.name");
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setVariant(0);

        for (int i = 0; i < 32; ++i)
        {
            this.setVariant(i);
            if (rand.nextFloat() <= 0.5F)
            	break;
        }

    	if (this.getVariant() < 31 && this.getVariant() >= 15)
            this.setVariant(15);

    	if (this.getVariant() >= 31)
            this.setVariant(31);

        this.ignite();
    	
        return super.onInitialSpawn(difficulty, livingdata);
    }
}
