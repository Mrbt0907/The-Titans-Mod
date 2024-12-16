package net.minecraft.titans.entity.boss;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.endermanofdoom.mac.util.math.Maths;
import net.endermanofdoom.mca.MCA;
import net.endermanofdoom.mca.registrey.MCASounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZombieTitanProto extends EntityPreTitan
{
    public EntityZombieTitanProto(World worldIn) {
		super(worldIn);
		this.experienceValue = 3000;
	}
	
    public void ignite()
    {
    	super.ignite();
    	
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
		playSound(this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? (this.getSizeMultiplier()  > 2 ? MCASounds.largefoostep : SoundEvents.ENTITY_ZOMBIE_STEP) : TSounds.get("titan.step"), this.getSoundVolume(), this.getSoundPitch());
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

        if (this.rand.nextFloat() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F))
        {
            int i = this.rand.nextInt(3);

            if (i == 0)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            }
            else
            {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
            }
        }
    }

	public double getMobHealth() 
	{
		double hp = MCA.caclculateValue(world, 7000D * this.getSizeMultiplier() * this.getTier().getMultiplier());

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
		double attack = 70D * this.getSizeMultiplier() * this.getTier().getMultiplier();

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
		double speed = 0.75D - (getSizeMultiplier() * 0.05);
		
		if (speed <= 0.25D)
			speed = 0.25D;
		
		if (this.getHealth() <= this.getMaxHealth() * 0.5)
			speed *= 2D;
		
		if (this.getHealth() <= this.getMaxHealth() * 0.25)
			speed *= 2D;
		
		return speed;
	}
    
	public int[] getBarColor() 
	{
		return new int[] {0, 220 - getVariant(), 100 - (getVariant() / 3)};
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
		return new EntityZombie(world);
	}

    @Nullable
    protected Item getDropItem()
    {
    	switch (rand.nextInt(20))
    	{
    	case 0:
    		return Item.getItemFromBlock(Blocks.IRON_BLOCK);
    	case 1:
    	case 2:
    	case 3:
    	case 4:
    	case 5:
    		return Items.CARROT;
    	case 6:
    	case 7:
    	case 8:
    	case 9:
    	case 10:
    		return Items.POTATO;
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
