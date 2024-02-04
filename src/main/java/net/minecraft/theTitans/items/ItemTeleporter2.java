package net.minecraft.theTitans.items;
import java.util.List;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.theTitans.events.TeleporterNull;
import net.minecraft.theTitans.world.WorldProviderNowhere;
import net.minecraft.theTitans.world.WorldProviderVoid;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldServer;
public class ItemTeleporter2 extends ItemBase
{
	public ItemTeleporter2()
	{
		super("teleporter2");
		this.maxStackSize = 1;
	}

	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		if (!worldIn.isRemote)
		{
			if ((playerIn.worldObj.provider instanceof WorldProviderEnd))
			{
				List<?> list = playerIn.worldObj.getEntitiesWithinAABB(EntityDragon.class, playerIn.boundingBox.expand(256.0D, 256.0D, 256.0D));
				if ((list != null) && (playerIn.timeUntilPortal <= 0))
				{
					if (!list.isEmpty())
					{
						playerIn.addChatMessage(new ChatComponentText("(The Ender Dragon is blocking the use of the Item)"));
						playerIn.timeUntilPortal = 20;
					}

					else if ((playerIn.posX > 5.0D) && (playerIn.posX < -5.0D) && (playerIn.posZ > 5.0D) && (playerIn.posZ < -5.0D))
					{
						playerIn.addChatMessage(new ChatComponentText("(The item resinates, but nothing happens)"));
						playerIn.timeUntilPortal = 20;
					}

					else if ((list.isEmpty()) && (playerIn.posX < 5.0D) && (playerIn.posX > -5.0D) && (playerIn.posZ < 5.0D) && (playerIn.posZ > -5.0D))
					{
						playerIn.addChatMessage(new ChatComponentText("(A rush of energy surges through you as you are sent to another realm.)"));
						playerIn.motionY = 3.0D;
						MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) playerIn, TheTitans.NOWHERE_DIMENSION_ID, new TeleporterNull((WorldServer)playerIn.worldObj));
						playerIn.timeUntilPortal = 300;
						playerIn.triggerAchievement(TitansAchievments.nowhereTime);
						return itemStackIn;
					}
				}
			}

			else if ((playerIn.worldObj.provider instanceof WorldProviderHell))
			{
				playerIn.addChatMessage(new ChatComponentText("(All you hear is the sounds of the Nether.)"));
			}

			else if ((playerIn.worldObj.provider instanceof WorldProviderVoid))
			{
				playerIn.addChatMessage(new ChatComponentText("(Some great all-powerful force stops you from leaving.)"));
			}

			else if ((playerIn.worldObj.provider instanceof WorldProviderNowhere))
			{
				playerIn.addChatMessage(new ChatComponentText("(The item fails to function.)"));
			}

			else if ((!(playerIn.worldObj.provider instanceof WorldProviderVoid)) && (!(playerIn.worldObj.provider instanceof WorldProviderHell)) && (!(playerIn.worldObj.provider instanceof WorldProviderEnd)))
			{
				playerIn.addChatMessage(new ChatComponentText("(The item appears to resinate with quantum energy.)"));
			}
		}

		itemStackIn.hasTagCompound();
		playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
		return itemStackIn;
	}
}


