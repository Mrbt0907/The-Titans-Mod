package net.minecraft.theTitans.ench;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.ResourceLocation;
public class EnchantmentFerocity
extends Enchantment
{
	public EnchantmentFerocity(int p_i45768_1_, ResourceLocation p_i45768_2_, int p_i45768_3_, EnumEnchantmentType weapon)
	{
		super(p_i45768_1_, p_i45768_3_, weapon);
		setName("ferocity");
	}

	public int getMinEnchantability(int enchantmentLevel)
	{
		return 5 + (enchantmentLevel - 1) * 8;
	}

	public int getMaxEnchantability(int enchantmentLevel)
	{
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	public int getMaxLevel()
	{
		return 5;
	}

	public String getName()
	{
		return "enchantment.turretferocity";
	}

	public float func_152376_a(int level, EnumCreatureAttribute creatureType)
	{
		return level * 2.0F;
	}
}


