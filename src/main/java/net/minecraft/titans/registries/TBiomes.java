package net.minecraft.titans.registries;

import net.minecraft.titans.TheTitans;
import net.minecraft.titans.world.biome.BiomeVoid;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TBiomes {
	//public static final DimensionType DIMENSION_NOWHERE = DimensionType.register("The Nowhere", "_nowhere", DIMENSION_NOWHERE_ID, WorldProviderNowhere.class, false);
	public static final BiomeVoid BIOME_VOID = new BiomeVoid(new Biome.BiomeProperties("Void Island").setWaterColor(986895).setRainDisabled().setTemperature(2.0F));
	private static RegistryEvent.Register<Biome> registry;
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Biome> event)
    {
		registry = event;
		add("void", BIOME_VOID, BiomeType.WARM, 100);
		registry = null;
    }
	
	private static void add(String registryName, Biome biome, BiomeType type, int weight)
	{
		BiomeManager.addBiome(type, new BiomeEntry(biome , weight));
		BiomeManager.removeSpawnBiome(biome);
		registry.getRegistry().register(biome.setRegistryName(TheTitans.MODID, registryName));
	}
}
