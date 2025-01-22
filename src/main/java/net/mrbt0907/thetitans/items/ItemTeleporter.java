package net.mrbt0907.thetitans.items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.mrbt0907.thetitans.world.WorldProviderVoid;

public class ItemTeleporter extends Item
{
	public ItemTeleporter()
	{
		this.maxStackSize = 1;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if (!worldIn.isRemote)
		{
			if ((playerIn.world.provider instanceof WorldProviderEnd))
			{
				if (playerIn.timeUntilPortal <= 0)
				{
				}
			}
			else if ((playerIn.world.provider instanceof WorldProviderVoid))
			{
				
			}
			else
			{
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
	}
}


