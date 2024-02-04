package net.minecraft.theTitans.items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.world.World;
public class ItemHarcadiumSword
extends ItemTitanSword
{
	public ItemHarcadiumSword(String unlocalizedName, Item.ToolMaterial material)
	{
		super(unlocalizedName, material);
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if ((entityIn instanceof EntityPlayer))
		((EntityPlayer)entityIn).triggerAchievement(TitansAchievments.harcadiumSword);
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
}


