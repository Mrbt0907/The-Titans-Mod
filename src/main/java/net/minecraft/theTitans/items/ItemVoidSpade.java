package net.minecraft.theTitans.items;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityCaveSpiderTitan;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.DamageSourceExtra;
public class ItemVoidSpade
extends ItemNormalSpade
{
	public ItemVoidSpade(String unlocalizedName, Item.ToolMaterial material)
	{
		super(unlocalizedName, material);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		if (target != null)
		{
			if ((target.height >= 6.0F) || ((target instanceof EntitySilverfishTitan)) || ((target instanceof EntityCaveSpiderTitan)))
			{
				target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), 750.0F);
				target.playSound("thetitans:titanpunch", 10.0F, 1.0F);
			}
		}

		return true;
	}
}


