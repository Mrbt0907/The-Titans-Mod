package net.minecraft.theTitans.items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.world.World;
public class ItemVoidSword extends ItemTitanSword
{
	public ItemVoidSword(String unlocalizedName, Item.ToolMaterial material)
	{
		super(unlocalizedName, material);
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if ((entityIn instanceof EntityPlayer))
		((EntityPlayer)entityIn).triggerAchievement(TitansAchievments.voidSword);
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	protected double getRange()
	{
		return 9.0D;
	}

	protected double getDashSpeed()
	{
		return 5.0D;
	}

	public int getUseTime()
	{
		return 10;
	}

	public int getHurtTime()
	{
		return 10;
	}

	protected int getMaxUses()
	{
		return 5;
	}
}


