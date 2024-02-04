package net.minecraft.theTitans;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityHarcadiumArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
public class DamageSourceExtra
extends DamageSource
{
	public static DamageSource wip = new DamageSource("wip").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute();
	public static DamageSource destroy = new DamageSource("outOfWorld").setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute().setDifficultyScaled();
	public static DamageSource lightningBolt = new DamageSource("lightningBolt").setFireDamage().setDamageBypassesArmor().setDamageIsAbsolute();
	public static DamageSource mindCrush = new DamageSource("mindcrush").setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode();
	public static DamageSource radiation = new DamageSource("radiation").setDamageBypassesArmor().setDamageIsAbsolute();
	public DamageSourceExtra(String damageTypeIn)
	{
		super(damageTypeIn);
	}

	public static DamageSource causeSoulStealingDamage(Entity p_92087_0_)
	{
		return new EntityDamageSource("soulSucking", p_92087_0_).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode().setMagicDamage();
	}

	public static DamageSource causeSquishingDamage(Entity p_92087_0_)
	{
		return new EntityDamageSource("squash", p_92087_0_).setDamageBypassesArmor().setDifficultyScaled();
	}

	public static DamageSource causeArmorPiercingMobDamage(Entity p_92087_0_)
	{
		return new EntityDamageSource("mob", p_92087_0_).setDamageBypassesArmor().setDamageIsAbsolute();
	}

	public static DamageSource causeAntiTitanDamage(Entity p_92087_0_)
	{
		return new EntityDamageSource("mob", p_92087_0_).setDamageBypassesArmor().setDamageIsAbsolute();
	}

	public static DamageSource causeHarcadiumArrowDamage(EntityHarcadiumArrow arrow, Entity p_76353_1_)
	{
		return new EntityDamageSourceIndirect("arrow", arrow, p_76353_1_).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();
	}

	public static DamageSource causeHomingSkullDamage(Entity p_92087_0_)
	{
		return new EntityDamageSource("arrow", p_92087_0_).setDamageBypassesArmor();
	}

	public static DamageSource causeHomingSkullDamageVSEnderDragon(Entity p_92087_0_)
	{
		return new EntityDamageSource("arrow", p_92087_0_).setDamageBypassesArmor().setExplosion();
	}

	public static DamageSource causeVaroizationDamage(Entity p_92087_0_)
	{
		return new EntityDamageSource("vaporize", p_92087_0_).setDamageBypassesArmor().setMagicDamage();
	}

	public static DamageSource causeCreeperTitanExplosionDamage(Entity p_92087_0_)
	{
		return new EntityDamageSource("explosion.player", p_92087_0_).setDifficultyScaled().setDamageBypassesArmor().setExplosion();
	}
}


