package net.minecraft.titans.utils;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TranslateUtil
{
	public static void sendChatMult(EntityPlayer player, String key, int amount, Object... values)
	{
		if (amount > 1)
		{
			int index = Maths.random(amount);
			if (index > 0)
			{
				sendChat(player ,key + "." + index, values);
				return;
			}
		}

		sendChat(player, key, values);
	}

	public static void sendChat(EntityPlayer player, String key, Object... values)
	{
		player.sendMessage(new TextComponentTranslation(key, values));
	}
	
	public static void sendChatAllMult(String key, int amount, Object... values)
	{
		if (amount > 1)
		{
			int index = Maths.random(amount);
			if (index > 0)
			{
				sendChatAll(key + "." + index, values);
				return;
			}
		}

		sendChatAll(key, values);
	}

	public static void sendChatAll(String key, Object... values)
	{
		FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentTranslation(key, values));
	}
	
	public static ITextComponent translateChatMult(String key, int amount, Object... values)
	{
		if (amount > 1)
		{
			int index = Maths.random(amount);
			if (index > 0)
				return translateChat(key + "." + index, values);
		}

		return translateChat(key, values);
	}

	public static ITextComponent translateChat(String key, Object... values)
	{
		return new TextComponentTranslation(key, values);
	}
	
	public static String translateMult(String key, int amount, Object... values)
	{
		if (amount > 1)
		{
			int index = Maths.random(amount);
			if (index > 0)
			translate(key + "." + index, values);
		}

		return translate(key, values);
	}

	public static String translate(String key, Object... values)
	{
		String value = I18n.format(key, values); 
		return value;
	}
}


