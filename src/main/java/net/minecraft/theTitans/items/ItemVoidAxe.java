package net.minecraft.theTitans.items;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.DamageSourceExtra;
public class ItemVoidAxe
extends ItemNormalAxe
{
	public ItemVoidAxe(String unlocalizedName, Item.ToolMaterial material)
	{
		super(unlocalizedName, material);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		if (target != null)
		{
			if ((target.height >= 7.0F) || ((target instanceof EntityTitan)) || (!target.onGround))
			{
				target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), 2500.0F);
				target.playSound("thetitans:titanpunch", 10.0F, 1.0F);
			}
		}

		return true;
	}
}


