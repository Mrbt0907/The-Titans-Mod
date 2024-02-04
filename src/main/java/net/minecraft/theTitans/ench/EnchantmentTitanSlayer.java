package net.minecraft.theTitans.ench;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.util.ResourceLocation;
public class EnchantmentTitanSlayer
extends Enchantment
{
	public EnchantmentTitanSlayer(int enchID, ResourceLocation enchName, int enchWeight)
	{
		super(enchID, enchWeight, EnumEnchantmentType.weapon);
	}

	public int getMinEnchantability(int enchantmentLevel)
	{
		return 1 + (enchantmentLevel - 1) * 3;
	}

	public int getMaxEnchantability(int enchantmentLevel)
	{
		return super.getMinEnchantability(enchantmentLevel) + 30;
	}

	public int getMaxLevel()
	{
		return 10;
	}

	public String getName()
	{
		return "enchantment.damage.titanSlayer";
	}

	public boolean canApply(ItemStack stack)
	{
		return (stack.getItem() instanceof ItemAxe) ? true : super.canApply(stack);
	}

	public float func_152376_a(int level, EnumCreatureAttribute creatureType)
	{
		return 25F;
	}

	public void func_151368_a(EntityLivingBase user, Entity target, int level)
	{
		float f = (float)user.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		if ((target instanceof EntityLivingBase))
		{
			EntityLivingBase entitylivingbase1 = (EntityLivingBase)target;
			if ((target.height >= 6.0F) || ((target instanceof EntityTitan)) || (!target.onGround))
			{
				entitylivingbase1.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(user), f + level * 100.0F);
				entitylivingbase1.playSound("thetitans:titanpunch", 2F, 1F);
			}
		}
	}
}


