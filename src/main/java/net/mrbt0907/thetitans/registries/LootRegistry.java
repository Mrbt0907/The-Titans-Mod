package net.mrbt0907.thetitans.registries;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.mrbt0907.thetitans.TheTitans;

public class LootRegistry extends LootTableList
{
	public static void regLootTables()
	{
		
	}
	
	@SuppressWarnings("unused")
	private static ResourceLocation register(String id)
	{
		return register(new ResourceLocation(TheTitans.MODID, id));
	}
}
