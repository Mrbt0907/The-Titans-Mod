package net.minecraft.titans;

import net.minecraft.titans.registries.TEntities;
import net.minecraft.titans.registries.TRecipes;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.titans.server.TitanManagerServer;
import net.minecraft.titans.utils.ChunkLoadingUtil;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy 
{
	public static TitanManagerServer manager;
	
	public void preInit(FMLPreInitializationEvent e)
	{
		ChunkLoadingUtil.preInit();
		TEntities.registerEntity();
	}
	
	public void init(FMLInitializationEvent e)
	{
		TSounds.init();
	}
	
	public void postInit(FMLPostInitializationEvent e) 
	{
		TRecipes.postInit();
	}
}
