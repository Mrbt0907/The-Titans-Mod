package net.minecraft.theTitans.commands;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.*;
import net.minecraft.entity.titan.other.EntityEvilGolem;
import net.minecraft.entity.titan.other.EntityUndeadOther;
import net.minecraft.entity.titan.other.EntityWraith;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.events.EventObject;
public class CommandEvent
extends CommandBase
{
	public String getCommandName()
	{
		return "event";
	}

	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "command." + TheTitans.MODID + ".event.usage";
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
					case "zombiehoard":
					{
						EventObject.create(entityplayermp, entityplayermp.worldObj, "zombie_hoard", 5.0D, 256.0D).addMiniBosses(EntityEvilGolem.class).addCommon(EntityZombie.class, EntitySkeleton.class, EntityUndeadOther.class).addRare(EntityWraith.class).setTimeRange(false, false, true, true).start(0, 0, 50);
						break;
					}

					case "spiderhoard":
					{
						EventObject.create(entityplayermp, entityplayermp.worldObj, "spider_hoard", 5.0D, 256.0D).addCommon(EntitySpider.class).addRare(EntityWraith.class).start(0, 0, 200);
						break;
					}
				}
			}
		}
	}
}


