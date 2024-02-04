package net.minecraft.theTitans.ench;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
public class EnchantmentManiac
extends Enchantment
{
	public EnchantmentManiac(int p_i45768_1_, ResourceLocation p_i45768_2_, int p_i45768_3_, EnumEnchantmentType weapon)
	{
		super(p_i45768_1_, p_i45768_3_, weapon);
		setName("maniac");
	}

	public int getMinEnchantability(int enchantmentLevel)
	{
		return 12 + (enchantmentLevel - 1) * 20;
	}

	public int getMaxEnchantability(int enchantmentLevel)
	{
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	public int getMaxLevel()
	{
		return 3;
	}

	public String getName()
	{
		return "enchantment.turretmaniac";
	}

	public float func_152376_a(int level, EnumCreatureAttribute creatureType)
	{
		return level * 3.0F;
	}

	public void func_151368_a(EntityLivingBase user, Entity target, int level)
	{
		user.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20 * (10 * level), level));
		user.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 20 * (10 * level), level));
	}
}


