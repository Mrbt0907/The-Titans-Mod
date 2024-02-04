package net.minecraft.titans.api;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceTitans extends DamageSource
{
	
	public DamageSourceTitans(String damageTypeIn)
	{
		super(damageTypeIn);
	}
	
	public static DamageSource causeTitanDamage(Entity source)
	{
		return new EntityDamageSource("titan_damage", source).setDamageBypassesArmor().setDamageIsAbsolute();
	}
}
