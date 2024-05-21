package net.minecraft.titans.registries;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.titans.TheTitans;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;

public class TRecipes 
{
	public static void postInit()
	{

	}
	
	@SuppressWarnings("unused")
	private static void addShaped(boolean disabled, ItemStack output, String pattern, Object... inputs)
	{
		if (output != null && pattern != null && pattern.length() > 0 && inputs.length > 0)
		{
			if (disabled)
			{
				TheTitans.debug("Recipe " + output.getItem().getRegistryName().getResourceDomain() + ":" + output.getItem().getRegistryName().getResourcePath() + " is disabled. Skipping...");
				return;
			}
			
			Object[] args = null;
			List<Character> count = new ArrayList<Character>();
			int in = 0; int index = 0; Character c; String[] s = {"", null, null};
			
			for (int i = 0; i < pattern.length(); i++)
			{
				c = pattern.charAt(i);
				
				if (c.equals(','))
				{
					index++;
					in = 0;
					s[index] = "";
				}
				else if (in < 3)
				{
					s[index] += c;
					if (!c.equals(' ') && !count.contains(c))
						count.add(c);
					in++;
				}
			}
			
			if (count.size() <= 0)
			{
				TheTitans.error("Pattern returned null for recipe " + output.getItem().getRegistryName().getResourceDomain() + ":" + output.getItem().getRegistryName().getResourcePath() + ".  Skipping recipe...");
				return;
			}
			
			in = 0;
			for (String str : s)
				if (str != null)
					in++;
			
			args = new Object[(count.size() * 2) + in];
			for (int i = 0; i < 3; i++)
				if (s[i] != null)
					args[i] = s[i];
			
			for (int i = 0; i < inputs.length && i < count.size(); i++)
			{
				args[in] = count.get(i);
				if (inputs[i] instanceof String)
					args[in + 1] = new OreIngredient(inputs[i].toString());
				else
					args[in + 1] = inputs[i];
				in += 2;
			}
			
			TheTitans.debug("Adding shaped recipe for "  + output.getItem().getRegistryName().getResourceDomain() + ":" + output.getItem().getRegistryName().getResourcePath());
			GameRegistry.addShapedRecipe(output.getItem().getRegistryName(), null, output, args);
		}
		else
			TheTitans.error("Recipe is missing parameters");
	}
}
