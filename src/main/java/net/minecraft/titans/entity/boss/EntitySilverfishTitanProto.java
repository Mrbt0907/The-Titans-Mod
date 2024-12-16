package net.minecraft.titans.entity.boss;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.endermanofdoom.mac.util.math.Maths;
import net.endermanofdoom.mca.MCA;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySilverfishTitanProto extends EntityPreTitan
{
    public EntitySilverfishTitanProto(World worldIn) {
		super(worldIn);
		this.experienceValue = 600;
	}
	
    public void ignite()
    {
    	super.ignite();
    	
    	playSound(SoundEvents.ENTITY_SILVERFISH_AMBIENT, 10F, 1F);
    }
	
	protected SoundEvent getAmbientSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SILVERFISH_AMBIENT : TSounds.get("titan.silverfish.living");
	}
	
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SILVERFISH_HURT : TSounds.get("titan.silverfish.grunt");
	}
	
	protected SoundEvent getDeathSound()
	{
		return this.getSizeMultiplier() <= 7 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_SILVERFISH_DEATH : TSounds.get("titan.silverfish.death");
	}
	
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
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

	public double getMobHealth() 
	{
		double hp = MCA.caclculateValue(world, 2000D * this.getSizeMultiplier() * this.getTier().getMultiplier());

    	if (this.getVariant() >= 63)
    		hp *= 10;
    	if (this.getVariant() >= 31)
    		hp *= 5;
    	if (this.getVariant() >= 15)
    		hp *= 2;
    	if (this.getVariant() >= 7)
    		hp *= 2;
		
		return hp <= 8 ? 8 : hp;
	}

	public double getMobAttack() 
	{
		double attack = 10D * this.getSizeMultiplier() * this.getTier().getMultiplier();

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
		double speed = 0.9D - (getSizeMultiplier() * 0.0125);
		
		if (speed <= 0.5D)
			speed = 0.5D;
		
		if (this.getHealth() <= this.getMaxHealth() * 0.5)
			speed *= 2D;
		
		if (this.getHealth() <= this.getMaxHealth() * 0.25)
			speed *= 2D;
		
		return speed;
	}
    
	public int[] getBarColor() 
	{
		return new int[] {180 - getVariant(), 180 - getVariant(), 180 - getVariant()};
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
		return new EntitySilverfish(world);
	}

    public float getBaseWidth()
    {
    	return 0.4F;
    }

    public float getBaseHeight()
    {
    	return 0.3F;
    }

    public float getEyeHeight()
    {
        return 0.1F * getSizeMultiplier();
    }

    @Nullable
    protected Item getDropItem()
    {
		return Item.getItemFromBlock(Blocks.COBBLESTONE);
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
    	String supression = null;
    	
    	if (this.getVariant() >= 63)
    		supression = I18n.format("entity.suppression.1.name");
    	else if (this.getVariant() >= 31)
    		supression = I18n.format("entity.suppression.2.name");
    	else if (this.getVariant() >= 15)
    		supression = I18n.format("entity.suppression.3.name");
    	else if (this.getVariant() >= 7)
    		supression = I18n.format("entity.suppression.4.name");
    	
    	String name = I18n.format("entity." + EntityList.getEntityString(getBaseMob()) + ".name");
        if (this.hasCustomName())
        	name = getCustomNameTag();

        if (supression != null)
        	return I18n.format("entity.omegafish.name") + " " + supression;
        else
        	return name + " " + I18n.format("entity.demi.name") + "-" + I18n.format("entity.titan.name");
    }
}
