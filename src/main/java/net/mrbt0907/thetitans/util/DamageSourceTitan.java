package net.mrbt0907.thetitans.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceTitan extends DamageSource
{
	
	public DamageSourceTitan(String damageTypeIn)
	{
		super(damageTypeIn);
	}
	
	public static DamageSource causeTitanDamage(Entity source)
	{
		return new EntityDamageSource("titan_damage", source).setDamageBypassesArmor().setDamageIsAbsolute();
	}
}
