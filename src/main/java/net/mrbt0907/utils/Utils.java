package net.mrbt0907.utils;
import net.minecraft.util.StatCollector;
public class Utils
{
	public static String translateMult(String key, int amount, Object... values)
	{
		if (amount > 1)
		{
			int index = Maths.random(1, amount);
			if (index > 1)
			translate(key + "." + index, values);
		}

		return translate(key, values);
	}

	public static String translate(String key, Object... values)
	{
		String value = StatCollector.translateToLocalFormatted(key, values); 
		return value;
	}
}


