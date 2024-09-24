package net.minecraft.titans;

import net.minecraft.titans.client.TitanManagerClient;
import net.minecraft.titans.client.entity.renders.*;
import net.minecraft.titans.entity.*;
import net.minecraft.titans.entity.god.*;
import net.minecraft.titans.entity.boss.*;
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
