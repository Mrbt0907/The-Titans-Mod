package net.mrbt0907.thetitans.registries;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.entity.*;
import net.mrbt0907.thetitans.entity.boss.*;
import net.mrbt0907.thetitans.entity.god.*;

public class EntityRegistry
{
	private static int id = 0;

	public static void registerEntity()
	{
		createEntityWithEgg(EntitySilverfishTitanProto.class, "silverfish_titan_proto", 7237230, 3158064);
		createEntityWithEgg(EntityCaveSpiderTitanProto.class, "cave_spider_titan_proto", 803406, 11013646);
		createEntityWithEgg(EntitySpiderTitanProto.class, "spider_titan_proto", 3419431, 11013646);
		createEntityWithEgg(EntitySkeletonTitanProto.class, "skeleton_titan_proto", 12698049, 4802889);
		createEntityWithEgg(EntityZombieTitanProto.class, "zombie_titan_proto", 44975, 7969893);
		createEntityWithEgg(EntityHuskTitanProto.class, "husk_titan_proto", 7958625, 15125652);
		createEntityWithEgg(EntityPigZombieTitanProto.class, "pigzombie_titan_proto", 15373203, 5009705);
		createEntityWithEgg(EntityWitherzilla.class, "witherzilla", 0x000000, 0xffffea);
		createEntity(EntityGammaLightning.class, "gamma_lightning");
		createEntity(EntityUrLightning.class, "ur_lightning");
	}
	
	public static void createEntityWithEgg(Class<? extends Entity> entityClass, String entityName, int primary, int secondary)
	{
		createEntity(entityClass, entityName);
		net.minecraftforge.fml.common.registry.EntityRegistry.registerEgg(new ResourceLocation(TheTitans.MODID, entityName), primary, secondary);
	}

	public static void createEntity(Class<? extends Entity> entityClass, String entityName)
	{
		net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(TheTitans.MODID, entityName), entityClass, entityName, ++id, TheTitans.instance, 2048, 1, true);
	}
}
