package net.minecraft.titans.items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.titans.CommonProxy;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.utils.Maths;
import net.minecraft.titans.utils.TranslateUtil;
import net.minecraft.titans.world.TeleporterNull;
import net.minecraft.titans.world.WorldProviderVoid;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldServer;

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
					if (!CommonProxy.manager.defeatedDragon)
					{
						playerIn.sendMessage(TranslateUtil.translateChatMult("dialog.teleporter.fail.a", 10, playerIn.getName()));
						playerIn.timeUntilPortal = 20;
					}
					else
					{
						if (CommonProxy.manager.defeatedWitherzilla)
						{
							playerIn.sendMessage(TranslateUtil.translateChatMult("dialog.teleporter.fail.c", 10, playerIn.getName()));
						}
						else if (Maths.distance(playerIn.posX, playerIn.posZ, 0, 0) > 5.0D)
						{
							playerIn.sendMessage(TranslateUtil.translateChatMult("dialog.teleporter.fail.b", 10, playerIn.getName()));
							playerIn.timeUntilPortal = 20;
						}
						else
						{
							playerIn.sendMessage(TranslateUtil.translateChatMult("dialog.teleporter.success.a", 10, playerIn.getName()));
							playerIn.motionY = 3.0D;
							playerIn.changeDimension(TheTitans.DIMENSION_VOID_ID, new TeleporterNull((WorldServer) worldIn));
							playerIn.timeUntilPortal = 300;
							return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
						}
					}
				}
			}
			else if ((playerIn.world.provider instanceof WorldProviderVoid))
			{
				if (!CommonProxy.manager.defeatedWitherzilla)
					playerIn.sendMessage(TranslateUtil.translateChatMult("dialog.teleporter.info.z", 10, playerIn.getName()));
				else
				{
					playerIn.sendMessage(TranslateUtil.translateChatMult("dialog.teleporter.success.b", 10, playerIn.getName()));
					playerIn.changeDimension(0, new TeleporterNull((WorldServer) worldIn));
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
				}
			}
			else
			{
				if (!CommonProxy.manager.defeatedWitherzilla)
					playerIn.sendMessage(TranslateUtil.translateChatMult("dialog.teleporter.info.a", 10, playerIn.getName()));
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
	}
}


