package net.minecraft.theTitans.items;
import net.minecraft.item.Item;
public class ItemWithireniumSword extends ItemTitanSword
{
	public ItemWithireniumSword(String unlocalizedName, Item.ToolMaterial material)
	{
		super(unlocalizedName, material);
	}

	protected double getRange()
	{
		return 8.0D;
	}

	protected double getDashSpeed()
	{
		return 4.5D;
	}

	public int getHurtTime()
	{
		return 15;
	}

	protected int getMaxUses()
	{
		return 3;
	}
}


