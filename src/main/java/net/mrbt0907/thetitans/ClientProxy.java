package net.mrbt0907.thetitans;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.mrbt0907.thetitans.client.entity.renders.*;
import net.mrbt0907.thetitans.client.keybind.Keybinds;
import net.mrbt0907.thetitans.entity.*;
import net.mrbt0907.thetitans.entity.boss.*;
import net.mrbt0907.thetitans.entity.god.*;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		Keybinds.preInit();
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
		Keybinds.postInit();
	}
	
	public void renderEntities()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntitySilverfishTitanProto.class, manager -> new RenderSilverfishTitanProto(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityCaveSpiderTitanProto.class, manager -> new RenderCaveSpiderTitanProto(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpiderTitanProto.class, manager -> new RenderSpiderTitanProto(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonTitanProto.class, manager -> new RenderSkeletonTitanProto(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieTitanProto.class, manager -> new RenderZombieTitanProto(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityHuskTitanProto.class, manager -> new RenderHuskTitanProto(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityPigZombieTitanProto.class, manager -> new RenderPigZombieTitanProto(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityWitherzilla.class, manager -> new RenderWitherzilla(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityGammaLightning.class, manager -> new RenderGammaLightning(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityUrLightning.class, manager -> new RenderUrLightning(manager));
	}
}
