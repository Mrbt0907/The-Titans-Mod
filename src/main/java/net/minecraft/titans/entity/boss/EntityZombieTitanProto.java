package net.minecraft.titans.entity.boss;

import net.endermanofdoom.mca.MCA;
import net.minecraft.block.Block;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZombieTitanProto extends EntityPreTitan{

	public EntityZombieTitanProto(World worldIn) {
		super(worldIn);
		setSize(8F, 32F);
		this.experienceValue = 25000;
	}
	
	protected SoundEvent getAmbientSound()
	{
		return TSounds.get("titan.zombie.living");
	}
	
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return TSounds.get("titan.zombie.grunt");
	}
	
	protected SoundEvent getDeathSound()
	{
		return TSounds.get("titan.zombie.death");
	}
	
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		playSound(TSounds.get("titan.step"), this.getSoundVolume(), this.getSoundPitch());
	}

	public double getMobHealth() 
	{
		double hp = MCA.caclculateValue(world, 7000000D);
		
		if (world.playerEntities.size() > 1)
			hp *= world.playerEntities.size();
		
		return hp;
	}

	public double getMobAttack() 
	{
		EnumDifficulty diff = world.getDifficulty();
		
		double attack = 14000D;
		
		if (diff == EnumDifficulty.NORMAL)
			attack *= 1.5D;
		
		if (diff == EnumDifficulty.HARD)
			attack *= 2D;
		
		return attack;
	}

	public double getMobSpeed() 
	{
		return 0.25D;
	}
    
	public int[] getBarColor() 
	{
		return new int[] {0, 200, 25, 0, 0, 0};
	}
}
