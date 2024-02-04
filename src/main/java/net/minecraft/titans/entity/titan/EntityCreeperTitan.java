package net.minecraft.titans.entity.titan;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.api.EnumMobTier;
import net.minecraft.titans.entity.EntityMultiPart;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityCreeperTitan extends EntityTitan
{
	public EntityCreeperTitan(World world)
	{
		super(world);
		
		setMaxHealth(150000.0D);
		setHealthD(getMaxHealthD());
	}
	
	protected void initEntityAI()
	{
		tasks.addTask(0, new EntityAIAttackMelee(this, 0.9F, true));
		tasks.addTask(1, new EntityAIWander(this, 0.5F));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class, 1, false, false, IS_NOT_TITAN));
	}
	
	protected List<EntityMultiPart> onHitboxCreate(List<EntityMultiPart> hitboxes)
	{
		hitboxes.add(new EntityMultiPart(this, "Test", 0, 2, 10, 4.0F, 4.0F, true));
		return super.onHitboxCreate(hitboxes);
	}
	
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if (ticksExisted % 200 == 0)
		{
		
		}
	}
	
	public float getRenderSizeMultiplier()
    {
        return 4.0F;
    }
	
	@Override
	public float getSizeMultiplier()
	{
		return 52.0F;
	}

	@Override
	public EnumMobTier getTier()
	{
		return EnumMobTier.GREATER_TITAN;
	}

	@Override
	protected void dropLoot() {}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return TSounds.get("titan.creeper.grunt");
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return TSounds.get("titan.creeper.death");
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return TSounds.get("titan.creeper.living");
	}

	@Override
	public int[] getBarColor() {return new int[] {75, 152, 0, 255, 135, 0};}
	
	@Override
	public int getNameBarStart() {return 30;}
	
	@Override
	public int getHealthNameStart() {return 18;}
	
	@Override
	public int getHealthBarLength() {return 226;}
	
	@Override
	public ResourceLocation getBarTexture()
	{
		return getHealthD() / getMaxHealthD() > 0.35D ? new ResourceLocation(TheTitans.MODID, "textures/gui/titanbars/nuclear_creeper_titan.png") : new ResourceLocation(TheTitans.MODID, "textures/gui/titanbars/nuclear_creeper_titan_enraged.png");
	}
}
