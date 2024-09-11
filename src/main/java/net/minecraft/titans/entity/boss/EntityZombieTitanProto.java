package net.minecraft.titans.entity.boss;

import net.endermanofdoom.mca.MCA;
import net.endermanofdoom.mca.world.MCAWorldData;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityZombieTitanProto extends EntityPreTitan{

	public EntityZombieTitanProto(World worldIn) {
		super(worldIn);
		this.experienceValue = 25000;
	}
	
    public void ignite()
    {
    	super.ignite();
    	int invul = 380;
		if (MCAWorldData.progress.getBoolean("postMoWithersSuperBoss"))
			invul += 400;
    	
        this.setInvulTime(invul);
    	playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, 10F, 1F);
    }
	
	protected SoundEvent getAmbientSound()
	{
		return this.getSizeMultiplier() <= 6 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_AMBIENT : TSounds.get("titan.zombie.living");
	}
	
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return this.getSizeMultiplier() <= 6 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_HURT : TSounds.get("titan.zombie.grunt");
	}
	
	protected SoundEvent getDeathSound()
	{
		return this.getSizeMultiplier() <= 6 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_DEATH : TSounds.get("titan.zombie.death");
	}
	
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		playSound(this.getSizeMultiplier() <= 6 || this.ticksExisted <= 1 ? SoundEvents.ENTITY_ZOMBIE_STEP : TSounds.get("titan.step"), this.getSoundVolume(), this.getSoundPitch());
	}

	public double getMobHealth() 
	{
		double hp = MCA.caclculateValue(world, 28000D * this.getSizeMultiplier() * this.getTier().getMultiplier());
		
		if (world.playerEntities.size() > 1)
			hp *= world.playerEntities.size();
		
		return hp <= 20 ? 20 : hp;
	}

	public double getMobAttack() 
	{
		double attack = MCA.caclculateValue(world, 70D * this.getSizeMultiplier() * this.getTier().getMultiplier());

		return attack;
	}

	public double getMobSpeed() 
	{
		return 0.25D;
	}
    
	public int[] getBarColor() 
	{
		return new int[] {0, 200, 50, 0, 0, 0};
	}

	@Override
	public float getSizeMultiplier() 
	{
		float perc = 0.04F;

		if (MCAWorldData.progress.getBoolean("postMoWithersSuperBoss"))
			perc /= 2F;
		
		float size = this.getInvulTime() > 0 ? 16F - ((this.getInvulTime() * perc) <= 1F ? 1F :  (this.getInvulTime() * perc)) : 16F;

		if (MCAWorldData.progress.getBoolean("postMoWithersSuperBoss"))
			size *= 2F;
		
		return size;
	}
}
