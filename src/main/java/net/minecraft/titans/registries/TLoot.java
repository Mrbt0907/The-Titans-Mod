package net.minecraft.titans.registries;

import net.minecraft.titans.TheTitans;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class TLoot extends LootTableList
{
	public static void regLootTables()
	{
		
	}
	
	private static ResourceLocation register(String id)
	{
		return register(new ResourceLocation(TheTitans.MODID, id));
	}
}
