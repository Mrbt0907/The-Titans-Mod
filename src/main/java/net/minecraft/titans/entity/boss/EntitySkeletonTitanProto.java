package net.minecraft.titans.entity.boss;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.endermanofdoom.mac.util.math.Maths;
import net.endermanofdoom.mca.EnumSoundType;
import net.endermanofdoom.mca.MCA;
import net.endermanofdoom.mca.entity.projectile.EntityArrowOther;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySkeletonTitanProto extends EntityPreTitan
{
    public EntitySkeletonTitanProto(World worldIn) {
		super(worldIn);
		this.experienceValue = 4000;
	}
	
    public void ignite()
    {
    	super.ignite();
    	
    	playSound(SoundEvents.ENTITY_SKELETON_AMBIENT, 10F, 1F);
    }
	
	protected SoundEvent getAmbientSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SKELETON_AMBIENT : TSounds.get("titan.skeleton.living");
	}
	
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SKELETON_HURT : TSounds.get("titan.skeleton.grunt");
	}
	
	protected SoundEvent getDeathSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SKELETON_DEATH : TSounds.get("titan.skeleton.death");
	}
	
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		playSound(this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SKELETON_STEP : TSounds.get("titan.step"), this.getSoundVolume(), this.getSoundPitch());
	}

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getSoundPitch()
    {
        return (this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.75F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.25F) - (this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? this.getSizeMultiplier() * 0.05F : this.getSizeMultiplier() * 0.01F);
    }

    public float getEyeHeight()
    {
        float f = 1.74F;

        if (this.isChild())
        {
            f = (float)((double)f - 0.81D);
        }

        return f * getSizeMultiplier();
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

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
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
		double attack = MCA.caclculateValue(world, 8D * this.getSizeMultiplier() * this.getTier().getMultiplier());

		if (this.getVariant() > 7)
			attack *= 5D;
		
		return attack;
	}

	public double getMobSpeed() 
	{
		double speed = 0.5D - (getSizeMultiplier() * 0.075);

		if (speed <= 0.25D)
			speed = 0.25D;
		
		return speed;
	}
    
	public int[] getBarColor() 
	{
		return new int[] {240 - (getVariant() / 2), 240 - (getVariant() / 2), 240 - (getVariant() / 2)};
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

	protected EntityLiving getBaseMob() 
	{
		return new EntitySkeleton(world);
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
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
    	
        return super.onInitialSpawn(difficulty, livingdata);
    }
    
    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
		if (!world.isRemote)
		for (int i = -1; i < this.getVariant(); ++i)
		{
	    	EntityArrowOther entityarrow = new EntityArrowOther(this.world, this);
	        entityarrow.setEnchantmentEffectsFromEntity(this, distanceFactor);
	        double d0 = target.posX - this.posX;
	        double d1 = target.getEntityBoundingBox().minY + (double)(target.getEyeHeight() * 0.2F) - entityarrow.posY;
	        double d2 = target.posZ - this.posZ;
	        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
	        entityarrow.shoot(d0, d1 + d3 * 0.25D, d2, this.getSizeMultiplier() * 0.1F + 1.6F, this.getSizeMultiplier() * 0.5F);
	        entityarrow.setDamage(getMobAttack());
	        entityarrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
	        this.world.spawnEntity(entityarrow);
		}
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
    }

	public EnumSoundType getSoundType() 
	{
		return EnumSoundType.WOOD;
	}
}
