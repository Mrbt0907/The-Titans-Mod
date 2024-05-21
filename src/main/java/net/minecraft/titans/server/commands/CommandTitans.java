package net.minecraft.titans.server.commands;

import java.util.List;

import net.endermanofdoom.mac.util.math.Vec3;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.titans.CommonProxy;
import net.minecraft.titans.manager.EnumEvents;
import net.minecraft.titans.manager.EventObject;

public class CommandTitans extends CommandBase
{

	@Override
	public String getName()
	{
		return "titans";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/" + getName() + " <debug>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		
		if (exists(args, 0))
			switch (args[0].toLowerCase())
			{
				case "debug":
					if (exists(args, 1))
						switch(args[1].toLowerCase())
						{
							case "manager":
								if (exists(args, 2))
									switch(args[2].toLowerCase())
									{
										case "dragon":
											CommonProxy.manager.defeatedDragon = !CommonProxy.manager.defeatedDragon;
											say(sender, "debug.manager.success", "defeatedDragon", CommonProxy.manager.defeatedDragon);
											if (sender instanceof EntityPlayerMP)
												CommonProxy.manager.syncData((EntityPlayerMP) sender);
											break;
										case "wither":
											CommonProxy.manager.defeatedWither = !CommonProxy.manager.defeatedWither;
											say(sender, "debug.manager.success", "defeatedWither", CommonProxy.manager.defeatedWither);
											if (sender instanceof EntityPlayerMP)
												CommonProxy.manager.syncData((EntityPlayerMP) sender);
											break;
										case "skully":
											CommonProxy.manager.defeatedSkully = !CommonProxy.manager.defeatedSkully;
											say(sender, "debug.manager.success", "defeatedSkully", CommonProxy.manager.defeatedSkully);
											if (sender instanceof EntityPlayerMP)
												CommonProxy.manager.syncData((EntityPlayerMP) sender);
											break;
										default:
											say(sender, "debug.manager.usage");
									}
								else
									say(sender, "debug.manager.usage");
								break;
							default:
								say(sender, "debug.usage");	
						}
					else
						say(sender, "debug.usage");
					break;
				case "spawn":
					if (exists(args, 1))
						switch(args[1].toLowerCase())
						{
							case "event":
								String events = "";
								for (String unlocalizedName : EnumEvents.getNames())
									if (events.equals(""))
										events = unlocalizedName;
									else
										events += "/" + unlocalizedName;
								
								if (exists(args, 2))
								{
									if (EnumEvents.contains(args[2]))
									{
										EventObject event = CommonProxy.manager.createEvent(sender.getEntityWorld(), new Vec3(sender.getPosition()), EnumEvents.getEvent(args[2]));
										if (event != null)
											say(sender, "spawn.event.success", event.getName(), sender.getEntityWorld().provider.getDimensionType().getName());
										else
											say(sender, "spawn.event.fail", args[2], sender.getEntityWorld().provider.getDimensionType().getName());
									}
									else
										say(sender, "spawn.event.fail", args[2], sender.getEntityWorld().provider.getDimensionType().getName());
								}
								else
									say(sender, "spawn.event.usage", events);
								break;
							default:
								say(sender, "spawn.usage");
						}
					else
						say(sender, "spawn.usage");
					break;
				default:
					say(sender, "usage");
			}
		else
			say(sender, "usage");
	}

	private void say(ICommandSender sender, String translation_id, Object... args)
	{
		notifyCommandListener(sender, this, "command." + getName() + "." + translation_id, args);
	}
	
	private boolean exists(String[] args, int index)
	{
		return args != null && args.length > index;
	}
	
}
