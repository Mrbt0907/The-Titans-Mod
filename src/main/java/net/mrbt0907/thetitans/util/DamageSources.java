package net.mrbt0907.thetitans.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSources
{
	public static DamageSource causeTitanDamage(Entity source)
	{
		return causeTitanDamage(source, true);
	}
	
	public static DamageSource causeTitanDamage(Entity source, boolean bypass)
	{
		return bypass ? new EntityDamageSource("titan_damage", source).setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute() : new EntityDamageSource("titan_damage", source);
	}
}
