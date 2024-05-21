package net.minecraft.titans;

import net.minecraft.titans.client.TitanManagerClient;
import net.minecraft.titans.client.entity.renders.RenderGammaLightning;
import net.minecraft.titans.client.entity.renders.RenderUrLightning;
import net.minecraft.titans.client.entity.renders.RenderWitherzilla;
import net.minecraft.titans.entity.EntityGammaLightning;
import net.minecraft.titans.entity.EntityUrLightning;
import net.minecraft.titans.entity.animal.*;
import net.minecraft.titans.entity.god.EntityWitherzilla;
import net.minecraft.titans.entity.render.*;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	public static TitanManagerClient manager;
	
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		manager = new TitanManagerClient();
		renderEntities();
	}

	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) 
	{
		super.postInit(e);
	}
	
	public void renderEntities()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityWitherzilla.class, manager -> new RenderWitherzilla(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityGammaLightning.class, manager -> new RenderGammaLightning(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityUrLightning.class, manager -> new RenderUrLightning(manager));
	}
}
