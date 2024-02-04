package net.minecraft.entity.titanminion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.IMob;
public interface IMinion
extends IMob
{
	public void setMinionType(int type);
	public EnumMinionType getMinionType();
	public EntityLiving getMaster();
	public void setMaster(EntityLiving entity);
	default boolean isFriendly(Entity entity) 
	{
		return false;
	}


	default boolean startGrounded() 
	{
		return true;
	}


	default String getSummonSound() 
	{
		return "thetitans:titansummonminion";
	}


	default float getSummonVolume() 
	{
		return 2.0F;
	}


	default float getSummonPitch() 
	{
		return 1.0F;
	}


	default double getSummonYOffset() 
	{
		return 0.0D;
	}
}


