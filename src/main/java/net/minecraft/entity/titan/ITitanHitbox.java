package net.minecraft.entity.titan;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
public interface ITitanHitbox
{
	boolean attackEntityFromPart(EntityTitanPart p_70965_1_, DamageSource p_70965_2_, float p_70965_3_);
	Entity[] getArray();
}


