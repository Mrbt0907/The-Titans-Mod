package net.minecraft.titans;

import net.minecraft.titans.registries.TEntities;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.titans.server.TitanManagerServer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy 
{
	public static TitanManagerServer manager;
	
	public void preInit(FMLPreInitializationEvent e)
	{
		TEntities.registerEntity();
	}
	
	public void init(FMLInitializationEvent e)
	{
		TSounds.init();
	}
	
	public void postInit(FMLPostInitializationEvent e) 
	{
	}
}
