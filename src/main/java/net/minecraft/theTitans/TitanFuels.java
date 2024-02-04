package net.minecraft.theTitans;
import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.api.IBurnable;
public class TitanFuels implements IFuelHandler
{
	private final List<IBurnable> fuels = new ArrayList<IBurnable>();
	public TitanFuels() 
	{

	}


	@Override
	public int getBurnTime(ItemStack fuel)
	{
		for (IBurnable burnable : fuels)
		if (burnable instanceof Item && fuel.getItem().equals((Item) burnable))
		return burnable.getBurnTime();
		else if (burnable instanceof Block && fuel.getItem().equals(Item.getItemFromBlock((Block) burnable)))
		return burnable.getBurnTime();
		return 0;
	}

	public void add(Item... items)
	{
		for(Item item : items)
		if (item instanceof IBurnable)
		fuels.add((IBurnable) item);
	}

	public void add(Block... blocks)
	{
		for(Block block : blocks)
		if (block instanceof IBurnable)
		fuels.add((IBurnable) block);
	}
}


