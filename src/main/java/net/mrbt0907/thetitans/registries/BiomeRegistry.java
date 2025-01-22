package net.mrbt0907.thetitans.registries;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.world.biome.BiomeVoid;

public class BiomeRegistry {
	//public static final DimensionType DIMENSION_NOWHERE = DimensionType.register("The Nowhere", "_nowhere", DIMENSION_NOWHERE_ID, WorldProviderNowhere.class, false);
	public static final BiomeVoid BIOME_VOID = new BiomeVoid(new Biome.BiomeProperties("Void Island").setWaterColor(986895).setRainDisabled().setTemperature(2.0F));
	private static RegistryEvent.Register<Biome> registry;
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Biome> event)
    {
		registry = event;
		add("void", BIOME_VOID, BiomeType.WARM, 0);
		registry = null;
    }
	
	private static void add(String registryName, Biome biome, BiomeType type, int weight)
	{
		BiomeManager.addBiome(type, new BiomeEntry(biome , weight));
		BiomeManager.removeSpawnBiome(biome);
		registry.getRegistry().register(biome.setRegistryName(TheTitans.MODID, registryName));
	}
}
