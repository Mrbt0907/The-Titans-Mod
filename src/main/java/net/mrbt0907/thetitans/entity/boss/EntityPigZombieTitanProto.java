package net.mrbt0907.thetitans.entity.boss;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.mrbt0907.thetitans.registries.SoundRegistry;
import net.mrbt0907.thetitans.util.DamageSources;
import net.mrbt0907.util.util.math.Maths;

public class EntityPigZombieTitanProto extends EntityPreTitan
{
    public EntityPigZombieTitanProto(World worldIn) {
		super(worldIn);
		this.experienceValue = 5000;
	}
	
	protected SoundEvent getAmbientSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT : SoundRegistry.get("titan.pigzombie.living");
	}
	
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_PIG_HURT : SoundRegistry.get("titan.pigzombie.grunt");
	}
	
	protected SoundEvent getDeathSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_PIG_DEATH : SoundRegistry.get("titan.pigzombie.death");
	}
	
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		playSound(this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? (this.getSizeMultiplier()  > 2 ? SoundEvents.ENTITY_ZOMBIE_STEP : SoundEvents.ENTITY_ZOMBIE_STEP) : SoundRegistry.get("titan.step"), this.getSoundVolume(), this.getSoundPitch());
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
		
		if (this.getAttackTarget() != null && rand.nextFloat() <= 0.01F)
			this.playSound(SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, this.getSoundVolume(), 2F - (this.getSizeMultiplier() * 0.05F));
		
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
							victim.attackEntityFrom(DamageSources.LIGHTNING_BOLT, 500F);
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
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
    }

	public double getMobHealth() 
	{
		double hp = 7000D * this.getSizeMultiplier();

    	if (this.getVariant() >= 63)
    		hp *= 10;
    	if (this.getVariant() >= 31)
    		hp *= 5;
    	if (this.getVariant() >= 15)
    		hp *= 2;
    	if (this.getVariant() >= 7)
    		hp *= 2;
		
		return hp <= 20 ? 20 : hp;
	}

	public double getMobAttack() 
	{
		double attack = 140D * this.getSizeMultiplier();

    	if (this.getVariant() >= 63)
    		attack *= 10;
    	if (this.getVariant() >= 31)
    		attack *= 5;
    	if (this.getVariant() >= 15)
    		attack *= 2;
    	if (this.getVariant() >= 7)
    		attack *= 2;
		
		return attack;
	}

	public double getMobSpeed() 
	{
		double speed = 1D - (getSizeMultiplier() * 0.075);

		if (speed <= 0.325D)
			speed = 0.325D;
		
		return speed;
	}
    
	public int[] getBarColor() 
	{
		return new int[] {230 - (getVariant() / 2), 190 - (getVariant() / 2), 190 - (getVariant() / 2)};
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
		return new EntityPigZombie(world);
	}

    @Nullable
    protected Item getDropItem()
    {
    	switch (rand.nextInt(20))
    	{
    	case 0:
    		return Item.getItemFromBlock(Blocks.GOLD_BLOCK);
    	case 1:
    	case 2:
    	case 3:
    		return Items.GOLD_INGOT;
    	case 4:
    	case 5:
    	case 6:
    	case 7:
    	case 8:
    	case 9:
    	case 10:
    		return Items.GOLD_NUGGET;
    	default:
    		return Items.ROTTEN_FLESH;
    	}
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
    	
        return super.onInitialSpawn(difficulty, livingdata);
    }
}
