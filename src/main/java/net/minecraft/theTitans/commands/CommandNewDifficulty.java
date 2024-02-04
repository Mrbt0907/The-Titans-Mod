package net.minecraft.theTitans.commands;
import java.util.List;
import net.minecraft.theTitans.core.TheCore;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;
public class CommandNewDifficulty extends CommandBase
{
	public String getCommandName()
	{
		return "difficulty";
	}

	/**
	* Return the required permission level for this command.
	*/
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return "commands.difficulty.usage";
	}

	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if (p_71515_2_.length > 0)
		{
			EnumDifficulty enumdifficulty = getDifficulty(p_71515_1_, p_71515_2_);
			MinecraftServer.getServer().func_147139_a(enumdifficulty);
			func_152373_a(p_71515_1_, this, "commands.difficulty.success", new Object[] 
			{
				new ChatComponentTranslation(enumdifficulty.getDifficultyResourceKey(), new Object[0])
			}
			);
		}

		else
		{
			throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
		}
	}

	protected EnumDifficulty getDifficulty(ICommandSender sender, String[] arguments)
	{
		if (TitanConfig.useCoreDifficulty)
		switch(String.join(" ", arguments).toLowerCase())
		{
			case "p": return EnumDifficulty.PEACEFUL;
			case "peaceful": return EnumDifficulty.PEACEFUL;	
			case "e": return EnumDifficulty.EASY;
			case "easy": return EnumDifficulty.EASY;
			case "n": return EnumDifficulty.NORMAL;
			case "normal": return EnumDifficulty.NORMAL;
			case "h": return EnumDifficulty.HARD;
			case "hard": return EnumDifficulty.HARD;
			case "v": return EnumDifficulty.HARD;
			case "veryhard": return TheCore.VERYHARD;
			case "expert": return TheCore.EXPERT;
			case "nightmare": return TheCore.NIGHTMARE;
			case "i": return TheCore.IMPOSSIBLE;
			case "impossible": return TheCore.IMPOSSIBLE;
			default:
			{
				return EnumDifficulty.values()[parseIntBounded(sender, String.join(" ", arguments).toLowerCase(), 0, EnumDifficulty.values().length - 1)];
			}
		}

		else
		switch(String.join(" ", arguments).toLowerCase())
		{
			case "p": return EnumDifficulty.EASY;
			case "peaceful": return EnumDifficulty.EASY;	
			case "e": return EnumDifficulty.EASY;
			case "easy": return EnumDifficulty.EASY;
			case "n": return EnumDifficulty.NORMAL;
			case "normal": return EnumDifficulty.NORMAL;
			case "h": return EnumDifficulty.HARD;
			case "hard": return EnumDifficulty.HARD;
			default: return EnumDifficulty.getDifficultyEnum(parseIntBounded(sender, String.join(" ", arguments).toLowerCase(), 0, EnumDifficulty.values().length - 1));
		}
	}

	/**
	* Adds the strings available in this command to the given list of tab completion options.
	*/
	@SuppressWarnings("rawtypes")
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, new String[] 
		{
			"peaceful", "easy", "normal", "hard", "nightmare"
		}
		): null;
	}
}


