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
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySpiderTitanProto extends EntityPreTitan
{
    public EntitySpiderTitanProto(World worldIn) {
		super(worldIn);
	}
	
    public void ignite()
    {
    	super.ignite();
    	
    	playSound(SoundEvents.ENTITY_SPIDER_AMBIENT, 10F, 1F);
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
    	return 1.2F;
    }

    public float getBaseHeight()
    {
    	return 0.75F;
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
		double hp = MCA.caclculateValue(world, 5000D * this.getSizeMultiplier() * this.getTier().getMultiplier());
		
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
		double speed = 1.5D - (getSizeMultiplier() * 0.075);

		if (speed <= 0.325D)
			speed = 0.325D;
		
		return speed;
	}
    
	public int[] getBarColor() 
	{
		if (getVariant() >= 63)
			return new int[] {140, 0, 0};
		if (getVariant() >= 15)
			return new int[] {170, 0, 0};
		else if (getVariant() >= 7)
			return new int[] {200, 0, 0};
		else
			return new int[] {230, 0, 0};
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
		super.setVariant(type);

		this.experienceValue = 2000 * (1 + type);
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
                return I18n.format("entity.spider_titan_proto.supreme.name");
        	if (this.getVariant() >= 31)
                return I18n.format("entity.spider_titan_proto.og1710.name");
        	else if (this.getVariant() >= 15)
                return I18n.format("entity.spider_titan_proto.og18.name");
        	else
        		return I18n.format("entity.spider_titan_proto.demititan.name");
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

        for (int i = 0; i < 7; ++i)
        {
            this.setVariant(i);
            if (rand.nextFloat() >= 0.25F)
            	break;
        }

    	if (this.getVariant() >= 7)
            this.setVariant(7);

        this.ignite();
    	
        return super.onInitialSpawn(difficulty, livingdata);
    }
}
