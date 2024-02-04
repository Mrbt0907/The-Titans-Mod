package net.minecraft.theTitans.core.client;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.theTitans.configs.TitanGameSettings;
public class TheClientCore
{
	public static void preInit(FMLPreInitializationEvent event)
	{
		TheTitans.debug("Starting Client Coremodding");
		if (TitanConfig.isCoreModding)
		{
			if (TitanConfig.useCoreGameSettings)
			Minecraft.getMinecraft().gameSettings = new TitanGameSettings(Minecraft.getMinecraft(), Minecraft.getMinecraft().mcDataDir);
		}
	}

	public static void init(FMLInitializationEvent event)
	{
		if (TitanConfig.isCoreModding)
		{
		}
	}

	public static void postInit(FMLPostInitializationEvent event)
	{
		if (TitanConfig.isCoreModding)
		{
		}
	}
}


