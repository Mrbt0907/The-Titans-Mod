package net.minecraft.theTitans.ench;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.ResourceLocation;
public class EnchantmentShurakin
extends Enchantment
{
	public EnchantmentShurakin(int p_i45768_1_, ResourceLocation p_i45768_2_, int p_i45768_3_, EnumEnchantmentType weapon)
	{
		super(p_i45768_1_, p_i45768_3_, weapon);
		setName("shurakin");
	}

	public int getMinEnchantability(int enchantmentLevel)
	{
		return 20;
	}

	public int getMaxEnchantability(int enchantmentLevel)
	{
		return 50;
	}

	public int getMaxLevel()
	{
		return 1;
	}

	public String getName()
	{
		return "enchantment.turretshurakin";
	}

	public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
	{
		return level * 1.5F;
	}
}


