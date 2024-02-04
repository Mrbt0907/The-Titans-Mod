package net.minecraft.theTitans.commands;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.titan.*;
import net.minecraft.entity.titanminion.*;
import net.minecraft.entity.*;
import net.minecraft.util.ChatComponentTranslation;
public class CommandClearMinions
extends CommandBase
{
	public String getCommandName()
	{
		return "clearMinions";
	}

	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "commands.killminions.usage";
	}

	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		EntityPlayerMP entityplayermp = getCommandSenderAsPlayer(p_71515_1_);
		p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.killminions.processing", new Object[0]));
		List<?> list = entityplayermp.worldObj.loadedEntityList;
		if ((list != null) && (!list.isEmpty()))
		{
			for (int i1 = 0; i1 < list.size(); i1++)
			{
				Entity entity = (Entity)list.get(i1);
				if ((entity != null) && ((!entity.isEntityAlive() && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit)) || entity.getClass() == (Class<?>)EntityList.stringToClassMapping.get("MutantCreatures.SkeletonPart") || entity.getClass() == (Class<?>)EntityList.stringToClassMapping.get("MutantCreatures.SpiderPig") || entity.getClass() == (Class<?>)EntityList.stringToClassMapping.get("Scorpion") || entity.getClass() == (Class<?>)EntityList.stringToClassMapping.get("PurplePower") || entity instanceof ITemplar || ((entity instanceof EntitySlime)) || ((entity instanceof EntityFallingBlock)) || ((entity instanceof EntityHarcadiumArrow)) || ((entity instanceof EntityArrow)) || ((entity instanceof EntityFireball)) || ((entity instanceof EntityTNTPrimed)) || ((entity instanceof EntityItem)) || ((entity instanceof EntityXPOrb)) || ((entity instanceof EntityThrowable))))
				{
					entity.setDead();
					entity.isDead = true;
				}

				if (entity != null && entity.isEntityAlive() && entity instanceof EntityTitanSpirit)
				{
					((EntityTitanSpirit)entity).setVesselHunting(false);
				}
			}
		}

		p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.killminions.success", new Object[0]));
	}
}


