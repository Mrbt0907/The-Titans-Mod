package net.minecraft.titans.registries;

import net.minecraft.entity.Entity;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.entity.*;
import net.minecraft.titans.entity.god.*;
import net.minecraft.titans.entity.boss.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class TEntities
{
	private static int id = 0;

	public static void registerEntity()
	{
		createEntityWithEgg(EntityZombieTitanProto.class, "zombie_titan_proto", 44975, 7969893, 512);
		createEntityWithEgg(EntityWitherzilla.class, "witherzilla", 0x000000, 0xffffea, 1028);
		createEntity(EntityGammaLightning.class, "gamma_lightning", 128);
		createEntity(EntityUrLightning.class, "ur_lightning", 128);
	}
	
	public static void createEntityWithEgg(Class<? extends Entity> entityClass, String entityName, int primary, int secondary, int updateDistance)
	{
		createEntity(entityClass, entityName, updateDistance);
		EntityRegistry.registerEgg(new ResourceLocation(TheTitans.MODID, entityName), primary, secondary);
	}

	public static void createEntity(Class<? extends Entity> entityClass, String entityName, int updateDistance)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(TheTitans.MODID, entityName), entityClass, entityName, ++id, TheTitans.instance, updateDistance, 1, true);
	}
}
