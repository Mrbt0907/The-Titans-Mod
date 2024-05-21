package net.minecraft.titans.registries;

import net.minecraft.entity.Entity;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.entity.god.EntityWitherzilla;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class TEntities
{
	private static int id = 0;

	public static void registerEntity()
	{
		createEntityWithEgg(EntityWitherzilla.class, "witherzilla", 0x000000, 0xffffea, 1028);
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
