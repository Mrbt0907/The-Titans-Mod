package net.minecraft.theTitans.commands;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
public class CommandNewKill extends CommandBase
{
	public String getCommandName()
	{
		return "kill";
	}

	/**
	* Return the required permission level for this command.
	*/
	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "commands.kill.usage";
	}

	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		EntityPlayerMP entityplayermp = getCommandSenderAsPlayer(p_71515_1_);
		if (p_71515_2_.length > 0)
		{
			int killed = 0;
			Entity entity;
			boolean forced = false;
			if (p_71515_2_.length > 1 && p_71515_2_[p_71515_2_.length - 1].equals("true"))
			{
				forced = true;
				String[] arg = new String[p_71515_2_.length - 1];
				for (int i = 0; i < p_71515_2_.length - 1; i++)
				arg[i] = p_71515_2_[i];
				p_71515_2_ = arg;
			}

			List<?> list = entityplayermp.worldObj.loadedEntityList;
			if (list != null && !list.isEmpty())
			{
				for (int i = 0; i < list.size(); i++)
				{
					entity = (Entity)list.get(i);
					switch(String.join(" ", p_71515_2_))
					{
						case "all": 
						{
							if (entity.isEntityAlive() && !(entity instanceof EntityPlayerMP)) 
							{
								kill(entity, forced); killed ++;
							}

							 break;
						}


						case "players": 
						{
							if (entity.isEntityAlive() && entity instanceof EntityPlayerMP) 
							{
								kill(entity, forced); killed ++;
							}

							 break;
						}


						case "mobs": ; 
						{
							if (entity.isEntityAlive() && !(entity instanceof EntityPlayerMP) && entity instanceof EntityLivingBase) 
							{
								kill(entity, forced); killed ++;
							}

							 break;
						}


						case "hostile": ; 
						{
							if (entity.isEntityAlive() && !(entity instanceof EntityPlayerMP) && entity instanceof EntityMob) 
							{
								kill(entity, forced); killed ++;
							}

							 break;
						}


						case "passive": ; 
						{
							if (entity.isEntityAlive() && !(entity instanceof EntityPlayerMP) && entity instanceof EntityAnimal) 
							{
								kill(entity, forced); killed ++;
							}

							 break;
						}


						case "neutral": ; 
						{
							if (entity.isEntityAlive() && !(entity instanceof EntityPlayerMP) && (entity instanceof EntityTameable || entity instanceof EntityGolem)) 
							{
								kill(entity, forced); killed ++;
							}

							 break;
						}


						case "items": ; 
						{
							if (entity instanceof EntityItem || entity instanceof EntityXPOrb) 
							{
								kill(entity, true); killed ++;
							}

							 break;
						}


						default: 
						{
							if (entity.isEntityAlive() && entity.getCommandSenderName().equals(String.join(" ", p_71515_2_))) 
							{
								kill(entity, forced); killed ++;
							}

							 break;
						}
					}
				}

				if (killed < 1)
				p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.kill.fail.b", new Object[0]));
				else
				p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.kill.success", new Object[] 
				{
					killed
				}
				));
			}

			else
			p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.kill.fail.a", new Object[0]));
		}

		else
		p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.kill.usage", new Object[0]));
	}

	private void kill(Entity entity, boolean forced)
	{
		if (forced) 
		entity.isDead = true; 
		else 
		entity.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
	}
}


