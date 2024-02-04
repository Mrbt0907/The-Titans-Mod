package net.minecraft.theTitans;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.orespawnaddon.*;
import net.minecraft.entity.titan.*;
import net.minecraft.entity.titan.other.*;
import net.minecraft.entity.titanminion.*;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.theTitans.items.ItemSpawnEggRegular;
import net.minecraft.world.biome.BiomeGenBase;
import net.mrbt0907.utils.RegistryHelper.Registry;
public class RenderTheTitans
{
	private static int id = 0;
	public static final Registry<Class<? extends Entity>, Object[]> entityRegistry = new Registry<Class<? extends Entity>, Object[]>();
	public static void setup()
	{
		createEntity(EntitySilverfishMinion.class, "SilverfishMinion", 7237230, 3158064);
		createEntity(EntityCaveSpiderMinion.class, "CaveSpiderMinion", 803406, 11013646);
		createEntity(EntitySpiderMinion.class, "SpiderMinion", 3419431, 11013646);
		createEntity(EntityZombieMinion.class, "ZombieMinion", 44975, 7969893);
		createEntity(EntityGiantZombieBetter.class, "GiantMinion", 44975, 5870909);
		createEntity(EntitySkeletonMinion.class, "SkeletonMinion", 12698049, 4802889);
		createEntity(EntityWitherMinion.class, "WitherMinion", 0, 1447446);
		createEntity(EntityCreeperMinion.class, "CreeperMinion", 894731, 0);
		createEntity(EntityPigZombieMinion.class, "PigZombieMinion", 15373203, 5009705);
		createEntity(EntityGhastGuard.class, "GhastGuard", 16382457, 12369084);
		createEntity(EntityBlazeMinion.class, "BlazeMinion", 16167425, 16775294);
		createEntity(EntityGhastMinion.class, "GhastMinion", 16382457, 12369084);
		createEntity(EntityEndermanMinion.class, "EndermanMinion", 1447446, 0);
		createEntity(EntityDragonMinion.class, "EnderDragonMinion", 0x000000, 0xff00ea);
		createEntity(EntityEnderColossusCrystal.class, "EnderColossusCrystal", 0xffffff, 0xff00ea);
		createEntity(EntityWitherTurret.class, "WitherTurret", 0, 1447446);
		createEntity(EntityWitherTurretGround.class, "WitherTurretGround", 0, 1447446);
		createEntity(EntityWitherTurretMortar.class, "WitherTurretMortar", 0, 1447446);
		createEntity(EntityWitherzillaMinion.class, "WitherBossMinion", 0, 1447446);
		createEntity(EntityGargoyle.class, "Gargoyle", 0xd4d4d4, 7237230);
		createEntity(EntitySilverfishOther.class, "SilverfishCustom", 0xffffff, 0);
		createEntity(EntitySpiderOther.class, "SpiderCustom", 0xffffff, 0);
		createEntity(EntityUndeadOther.class, "UndeadCustom", 0xffffff, 0);
		createEntity(EntityThinUndeadOther.class, "ThinUndeadCustom", 0xffffff, 0);
		createEntity(EntityWraith.class, "Wraith", 0xaaaaaa, 0x050505);
		createEntity(EntityEvilGolem.class, "EvilGolem", 0xffffff, 0);
		createEntity(EntityMagicUser.class, "MagicUser", 0xffffff, 0);
		createEntity(EntityHarcadiumGolem.class, "HarcadiumGolem", 13369594, 1447446);
		createEntity(EntityVoidGolem.class, "VoidGolem", 0, 0);
		createEgg(53, 44975, 5870909);
		createEgg(63, 1447446, 13369594);
		createEgg(64, 1315860, 1842204);
		createProjectile(EntityGrowthSerum.class, "GrowthSerum");
		createEntity(EntityChaff.class, "Chaff");
		createProjectile(EntityTitanFireball.class, "TitanFireball");
		createProjectile(EntityGargoyleTitanFireball.class, "GargoyleTitanFireball");
		createProjectile(EntityLavaSpit.class, "LavaSpit");
		createEntity(EntityGammaLightning.class, "GammaLightning");
		createEntity(EntityTitanPart.class, "TitanPart");
		createProjectile(EntityWebShot.class, "WebShot");
		createProjectile(EntityXPBomb.class, "XPBomb");
		createProjectile(EntityItemBomb.class, "ItemBomb");
		createProjectile(EntityLightningBall.class, "LightningBall");
		createProjectile(EntityProtoBall.class, "ProtoBall");
		createEntity(EntityTitanSpirit.class, "TitanSpirit");
		createProjectile(EntityHarcadiumArrow.class, "ArrowHarcadium");
		createProjectile(EntitySkeletonTitanGiantArrow.class, "ArrowGiant");
		createTitan(EntitySnowGolemTitan.class, "SnowManTitan");
		createTitan(EntitySlimeTitan.class, "SlimeTitan");
		createTitan(EntityMagmaCubeTitan.class, "LavaSlimeTitan");
		createTitan(EntitySilverfishTitan.class, "SilverfishTitan");
		createTitan(EntityCaveSpiderTitan.class, "CaveSpiderTitan");
		createTitan(EntitySpiderTitan.class, "SpiderTitan");
		createTitan(EntitySkeletonTitan.class, "SkeletonTitan");
		createTitan(EntityZombieTitan.class, "ZombieTitan");
		createTitan(EntityCreeperTitan.class, "CreeperTitan");
		createTitan(EntityPigZombieTitan.class, "PigZombieTitan");
		createTitan(EntityBlazeTitan.class, "BlazeTitan");
		createTitan(EntityGargoyleTitan.class, "GargoyleTitan");
		createTitan(EntityGhastTitan.class, "GhastTitan");
		createTitan(EntityEnderColossus.class, "EndermanTitan");
		createTitan(EntityIronGolemTitan.class, "VillagerGolemTitan");
		createTitan(EntityWitherzilla.class, "WitherBossTitan");
		if (Loader.isModLoaded("OreSpawn"))
		{
			createEntity(EntityOverlordScorpion.class, "OverlordScorpion");
			createEntity(EntityMethuselahKraken.class, "MethuselahKraken");
			createEntity(EntityBurningMobzilla.class, "BurningMobzilla");
			if (danger.orespawn.OreSpawnMain.GodzillaEnable != 0 && danger.orespawn.OreSpawnMain.BiomeVillageID != 0)
			EntityRegistry.addSpawn(EntityBurningMobzilla.class, 1, 1, 1, EnumCreatureType.monster, new BiomeGenBase[] 
			{
				BiomeGenBase.hell, BiomeGenBase.getBiome(danger.orespawn.OreSpawnMain.BiomeVillageID)
			}
			);
		}

		EntityRegistry.addSpawn(EntityBlaze.class, 50, 2, 2, EnumCreatureType.monster, BiomeGenBase.hell);
		EntityRegistry.addSpawn(EntitySkeleton.class, 20, 1, 4, EnumCreatureType.monster, BiomeGenBase.hell);
		EntityRegistry.addSpawn(EntityWither.class, 1, 1, 1, EnumCreatureType.monster, BiomeGenBase.hell);
		EntityRegistry.addSpawn(EntityIronGolem.class, 1, 1, 1, EnumCreatureType.creature, BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.savanna);
		EntityRegistry.addSpawn(EntitySilverfish.class, 100, 4, 4, EnumCreatureType.monster, getSpawnBiomes());
		EntityRegistry.addSpawn(EntityCaveSpider.class, 100, 4, 4, EnumCreatureType.monster, getSpawnBiomes());
		EntityRegistry.addSpawn(EntityGiantZombieBetter.class, 1, 1, 1, EnumCreatureType.monster, getSpawnBiomes());
		EntityRegistry.addSpawn(EntityZombieMinion.class, 100, 4, 4, EnumCreatureType.monster, getSpawnBiomes());
		EntityRegistry.addSpawn(EntitySpiderMinion.class, 100, 4, 4, EnumCreatureType.monster, getSpawnBiomes());
		EntityRegistry.addSpawn(EntitySkeletonMinion.class, 100, 4, 4, EnumCreatureType.monster, getSpawnBiomePlusHell());
		EntityRegistry.addSpawn(EntityCreeperMinion.class, 100, 4, 4, EnumCreatureType.monster, getSpawnBiomes());
		EntityRegistry.addSpawn(EntitySilverfishMinion.class, 100, 4, 4, EnumCreatureType.monster, getSpawnBiomes());
		EntityRegistry.addSpawn(EntityCaveSpiderMinion.class, 100, 4, 4, EnumCreatureType.monster, getSpawnBiomes());
		EntityRegistry.addSpawn(EntityEndermanMinion.class, 10, 4, 4, EnumCreatureType.monster, getSpawnBiomesPlusEnd());
		EntityRegistry.addSpawn(EntityBlazeMinion.class, 100, 4, 4, EnumCreatureType.monster, BiomeGenBase.hell);
		EntityRegistry.addSpawn(EntityPigZombieMinion.class, 100, 4, 4, EnumCreatureType.monster, BiomeGenBase.hell);
		EntityRegistry.addSpawn(EntityGhastMinion.class, 100, 4, 4, EnumCreatureType.monster, BiomeGenBase.hell);
		EntityRegistry.addSpawn(EntityGhastGuard.class, 100, 1, 1, EnumCreatureType.monster, BiomeGenBase.hell);
	}

	public static void preInit()
	{
		TitanConfig.load();
	}

	public static void init()
	{
	}

	public static void postInit()
	{
	}

	@SuppressWarnings("unchecked")
	private static void create(Class<? extends Entity> entityClass, String entityName, int range, int rate, boolean velocityUpdates, int... colors)
	{
		TheTitans.debug("Registering Entity: " + entityName);
		EntityRegistry.registerModEntity(entityClass, entityName, id, TheTitans.modInstance, 128, 1, false);
		if (colors.length > 1)
		ItemSpawnEggRegular.addEgg(entityClass, colors[0], colors[1]);
		if (EntityLivingBase.class.isAssignableFrom(entityClass))
		entityRegistry.add(entityClass);
		id++;
	}

	public static void createEntity(Class<? extends Entity> entityClass, String entityName, int... colors)
	{
		create(entityClass, entityName, 128, 1, false, colors);
	}

	public static void createProjectile(Class<? extends Entity> entityClass, String entityName)
	{
		create(entityClass, entityName, 256, 1, true);
	}

	public static void createTitan(Class<? extends Entity> entityClass, String entityName)
	{
		create(entityClass, entityName, 1024, 1, true);
	}

	@SuppressWarnings("unchecked")
	private static void createEgg(int id, int solidColor, int spotColor)
	{
		EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, solidColor, spotColor));
	}

	public static BiomeGenBase[] getSpawnBiomes()
	{
		return new BiomeGenBase[] 
		{
			BiomeGenBase.ocean,BiomeGenBase.plains,BiomeGenBase.desert,BiomeGenBase.extremeHills,BiomeGenBase.forest,BiomeGenBase.taiga,BiomeGenBase.swampland,BiomeGenBase.river,BiomeGenBase.frozenOcean,BiomeGenBase.frozenRiver,BiomeGenBase.icePlains,BiomeGenBase.iceMountains,BiomeGenBase.beach,BiomeGenBase.desertHills,BiomeGenBase.forestHills,BiomeGenBase.taigaHills,BiomeGenBase.extremeHillsEdge,BiomeGenBase.jungle,BiomeGenBase.jungleHills,BiomeGenBase.jungleEdge,BiomeGenBase.deepOcean,BiomeGenBase.stoneBeach,BiomeGenBase.coldBeach,BiomeGenBase.birchForest,BiomeGenBase.birchForestHills,BiomeGenBase.roofedForest,BiomeGenBase.coldTaiga,BiomeGenBase.coldTaigaHills,BiomeGenBase.megaTaiga,BiomeGenBase.megaTaigaHills,BiomeGenBase.extremeHillsPlus,BiomeGenBase.savanna,BiomeGenBase.savannaPlateau,BiomeGenBase.mesa,BiomeGenBase.mesaPlateau_F,BiomeGenBase.mesaPlateau
		};
	}

	public static BiomeGenBase[] getSpawnBiomePlusHell()
	{
		return new BiomeGenBase[] 
		{
			BiomeGenBase.ocean,BiomeGenBase.plains,BiomeGenBase.desert,BiomeGenBase.extremeHills,BiomeGenBase.forest,BiomeGenBase.taiga,BiomeGenBase.swampland,BiomeGenBase.river,BiomeGenBase.frozenOcean,BiomeGenBase.frozenRiver,BiomeGenBase.icePlains,BiomeGenBase.iceMountains,BiomeGenBase.beach,BiomeGenBase.desertHills,BiomeGenBase.forestHills,BiomeGenBase.taigaHills,BiomeGenBase.extremeHillsEdge,BiomeGenBase.jungle,BiomeGenBase.jungleHills,BiomeGenBase.jungleEdge,BiomeGenBase.deepOcean,BiomeGenBase.stoneBeach,BiomeGenBase.coldBeach,BiomeGenBase.birchForest,BiomeGenBase.birchForestHills,BiomeGenBase.roofedForest,BiomeGenBase.coldTaiga,BiomeGenBase.coldTaigaHills,BiomeGenBase.megaTaiga,BiomeGenBase.megaTaigaHills,BiomeGenBase.extremeHillsPlus,BiomeGenBase.savanna,BiomeGenBase.savannaPlateau,BiomeGenBase.mesa,BiomeGenBase.mesaPlateau_F,BiomeGenBase.mesaPlateau,BiomeGenBase.hell
		};
	}

	public static BiomeGenBase[] getSpawnBiomesPlusEnd()
	{
		return new BiomeGenBase[] 
		{
			BiomeGenBase.ocean,BiomeGenBase.plains,BiomeGenBase.desert,BiomeGenBase.extremeHills,BiomeGenBase.forest,BiomeGenBase.taiga,BiomeGenBase.swampland,BiomeGenBase.river,BiomeGenBase.frozenOcean,BiomeGenBase.frozenRiver,BiomeGenBase.icePlains,BiomeGenBase.iceMountains,BiomeGenBase.beach,BiomeGenBase.desertHills,BiomeGenBase.forestHills,BiomeGenBase.taigaHills,BiomeGenBase.extremeHillsEdge,BiomeGenBase.jungle,BiomeGenBase.jungleHills,BiomeGenBase.jungleEdge,BiomeGenBase.deepOcean,BiomeGenBase.stoneBeach,BiomeGenBase.coldBeach,BiomeGenBase.birchForest,BiomeGenBase.birchForestHills,BiomeGenBase.roofedForest,BiomeGenBase.coldTaiga,BiomeGenBase.coldTaigaHills,BiomeGenBase.megaTaiga,BiomeGenBase.megaTaigaHills,BiomeGenBase.extremeHillsPlus,BiomeGenBase.savanna,BiomeGenBase.savannaPlateau,BiomeGenBase.mesa,BiomeGenBase.mesaPlateau_F,BiomeGenBase.mesaPlateau,BiomeGenBase.sky
		};
	}
}


