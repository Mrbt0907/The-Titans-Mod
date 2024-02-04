package net.minecraft.titans.registries;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.init.Biomes;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.entity.animal.*;
import net.minecraft.titans.entity.god.EntityWitherzilla;
import net.minecraft.titans.entity.titan.EntityCreeperTitan;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class TEntities
{
	private static int id = 0;

	public static void registerEntity()
	{
		createEntityWithEgg(EntityEndSquid.class, "end_squid", 0x000000, 0xff00ea, 128);
		createEntityWithEgg(EntityWitherzilla.class, "witherzilla", 0x000000, 0xffffea, 1028);
		createEntityWithEgg(EntityCreeperTitan.class, "creeper_titan", 0x05FF22, 0x101010, 1028);
		
		EntitySpawnPlacementRegistry.setPlacementType(EntityEndSquid.class, EntityLiving.SpawnPlacementType.IN_AIR);
		EntityRegistry.addSpawn(EntityEndSquid.class, 5, 1, 1, TheTitans.VOID, Biomes.SKY);
		EntityRegistry.addSpawn(EntityEndermite.class, 15, 4, 4, EnumCreatureType.MONSTER, Biomes.SKY);
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
