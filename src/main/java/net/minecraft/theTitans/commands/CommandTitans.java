package net.minecraft.theTitans.commands;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.*;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.events.EventData;
import net.minecraft.theTitans.network.NetworkHandler;
import net.minecraft.theTitans.network.packets.PacketWorldData;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
public class CommandTitans
extends CommandBase
{
	public String getCommandName()
	{
		return "titans";
	}

	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "commands." + TheTitans.MODID + ".titans.usage";
	}

	public void processCommand(ICommandSender sender, String[] args)
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);

		if (player == null)
			return;
		
		World world = player.worldObj;
		
		if (args.length > 0)
			switch(args[0].toLowerCase())
			{
				case "progress":
				{
					try
					{
						if (args.length > 2)
							progress(player, args[1], Boolean.parseBoolean(args[2]));
						else
							player.addChatMessage(new ChatComponentText(TheTitans.translate("commands", "titans.usage")));
						
						break;
					}	
					catch (Exception e)
					{
						player.addChatMessage(new ChatComponentText(TheTitans.translate("commands", "titans.failed", args[1])));
					}
				}
				
				default:
					player.addChatMessage(new ChatComponentText(TheTitans.translate("commands", "titans.usage")));
			}
		else
			player.addChatMessage(new ChatComponentText(TheTitans.translate("commands", "titans.usage")));
	}
	
	private void progress(EntityPlayerMP player, String name, boolean flag)
	{
		EventData.setBool(player.worldObj, name, flag);
		NetworkHandler.sendClientPacket(new PacketWorldData(name, flag));
		player.addChatMessage(new ChatComponentText(TheTitans.translate("commands", "titans.success", name, flag)));
	}
}