package net.minecraft.theTitans.commands;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.*;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.network.NetworkHandler;
import net.minecraft.theTitans.network.packets.PacketPrintDebug;
public class CommandDebug
extends CommandBase
{
	public String getCommandName()
	{
		return "debugger";
	}

	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "command." + TheTitans.MODID + ".debugger.usage";
	}

	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		EntityPlayerMP entityplayermp = getCommandSenderAsPlayer(p_71515_1_);
		if (entityplayermp != null)
		{
			if (p_71515_2_.length > 0)
			{
				switch(p_71515_2_[0].toLowerCase())
				{
					case "print":
					{
						NetworkHandler.sendClientPacket(new PacketPrintDebug(), entityplayermp);
						break;
					}

					default: TheTitans.reflect.view(p_71515_2_);
				}
			}
		}
	}
}


