package net.minecraft.theTitans.ench;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
public class EnchantmentDurability
extends Enchantment
{
	public EnchantmentDurability(int p_i45768_1_, ResourceLocation p_i45768_2_, int p_i45768_3_, EnumEnchantmentType weapon)
	{
		super(p_i45768_1_, p_i45768_3_, weapon);
		setName("durability");
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
		return "enchantment.turretdurability";
	}

	public static boolean negateDamage(ItemStack p_92097_0_, int p_92097_1_, Random p_92097_2_)
	{
		return (!(p_92097_0_.getItem() instanceof ItemArmor)) || (p_92097_2_.nextFloat() >= 0.6F);
	}
}


