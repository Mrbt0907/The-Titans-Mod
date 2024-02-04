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
public class ItemTeleporter extends ItemBase
{
	public ItemTeleporter()
	{
		super("teleporter");
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
						playerIn.addChatMessage(new ChatComponentText("I can't do that when the Ender Dragon's still here! Kill it first."));
						playerIn.timeUntilPortal = 20;
					}

					else if ((playerIn.posX > 5.0D) && (playerIn.posX < -5.0D) && (playerIn.posZ > 5.0D) && (playerIn.posZ < -5.0D))
					{
						playerIn.addChatMessage(new ChatComponentText("I can't do that this far away from the center of the island. Get closer."));
						playerIn.timeUntilPortal = 20;
					}

					else if ((list.isEmpty()) && (playerIn.posX < 5.0D) && (playerIn.posX > -5.0D) && (playerIn.posZ < 5.0D) && (playerIn.posZ > -5.0D))
					{
						playerIn.addChatMessage(new ChatComponentText("Master! More food is coming for you!"));
						playerIn.motionY = 3.0D;
						MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) playerIn, TheTitans.VOID_DIMENSION_ID, new TeleporterNull((WorldServer)playerIn.worldObj));
						playerIn.timeUntilPortal = 300;
						playerIn.triggerAchievement(TitansAchievments.voidTime);
						return itemStackIn;
					}
				}
			}

			else if ((playerIn.worldObj.provider instanceof WorldProviderHell))
			{
				playerIn.addChatMessage(new ChatComponentText("(You hear the distant crys of a long, forgotten voice. It's very ancient.)"));
			}

			else if ((playerIn.worldObj.provider instanceof WorldProviderVoid))
			{
				playerIn.addChatMessage(new ChatComponentText("Stop talking to me and feed my Master!"));
			}

			else if ((playerIn.worldObj.provider instanceof WorldProviderNowhere))
			{
				playerIn.addChatMessage(new ChatComponentText("Why have you brought me here? Get me out!"));
			}

			else if ((!(playerIn.worldObj.provider instanceof WorldProviderVoid)) && (!(playerIn.worldObj.provider instanceof WorldProviderHell)) && (!(playerIn.worldObj.provider instanceof WorldProviderEnd)))
			{
				playerIn.addChatMessage(new ChatComponentText("(The ancient crys still abode. It's as if they are saying 'All things must End')"));
			}

			return itemStackIn;
		}

		itemStackIn.hasTagCompound();
		playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
		return itemStackIn;
	}
}


