package net.mrbt0907.thetitans;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.mrbt0907.thetitans.registries.EntityRegistry;
import net.mrbt0907.thetitans.registries.SoundRegistry;

public class CommonProxy 
{
	public void preInit(FMLPreInitializationEvent e)
	{
		EntityRegistry.registerEntity();
	}
	
	public void init(FMLInitializationEvent e)
	{
		SoundRegistry.init();
	}
	
	public void postInit(FMLPostInitializationEvent e) {}
}
