package net.minecraft.theTitans;
import net.minecraftforge.common.util.EnumHelper;
import net.mrbt0907.utils.DebugExtender;
import net.mrbt0907.utils.Maths;
import net.mrbt0907.utils.ReflectionHelper;
import net.mrbt0907.utils.Utils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityGargoyle;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatBasic;
import net.minecraft.theTitans.api.IReloadable;
import net.minecraft.theTitans.commands.CommandClearMinions;
import net.minecraft.theTitans.commands.CommandDebug;
import net.minecraft.theTitans.commands.CommandEvent;
import net.minecraft.theTitans.commands.CommandNewDifficulty;
import net.minecraft.theTitans.commands.CommandNewKill;
import net.minecraft.theTitans.commands.CommandTitans;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.theTitans.core.TheCore;
import net.minecraft.theTitans.core.client.TheClientCore;
import net.minecraft.theTitans.ench.EnchantmentDurability;
import net.minecraft.theTitans.ench.EnchantmentFerocity;
import net.minecraft.theTitans.ench.EnchantmentKnockup;
import net.minecraft.theTitans.ench.EnchantmentManiac;
import net.minecraft.theTitans.ench.EnchantmentShurakin;
import net.minecraft.theTitans.ench.EnchantmentTitanSlayer;
import net.minecraft.theTitans.ench.EnchantmentUnstablility;
import net.minecraft.theTitans.events.EventData;
import net.minecraft.theTitans.events.TickHandler;
import net.minecraft.theTitans.events.WorldHandler;
import net.minecraft.theTitans.network.NetworkHandler;
import net.minecraft.theTitans.world.BiomeGenNowhere;
import net.minecraft.theTitans.world.BiomeGenVoid;
import net.minecraft.theTitans.world.SpawnEggOreGeneration;
import net.minecraft.theTitans.world.WorldGenMobs;
import net.minecraft.theTitans.world.WorldGenStuffs;
import net.minecraft.theTitans.world.WorldProviderNowhere;
import net.minecraft.theTitans.world.WorldProviderVoid;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import java.io.File;
import java.util.Iterator;
@Mod(modid = TheTitans.MODID, name = TheTitans.MODNAME, version = TheTitans.VERSION, guiFactory = "net.minecraft.theTitans.configs.TitanConfigGui")
public class TheTitans
{
	public static final String MODNAME = "The Titans Mod";
	public static final String MODID = "thetitans";
	public static final String VERSION = "indev-0.5";
	public static final String CLIENT = "net.minecraft.theTitans.ClientProxy";
	public static final String SERVER = "net.minecraft.theTitans.CommonProxy";
	public static final String TEXTURES[] = {"default", "new"};
	public static final TitanFuels theFuel = new TitanFuels();
	public static DebugExtender logger;
	public static ReflectionHelper reflect;
	@SidedProxy(clientSide=CLIENT, serverSide=SERVER)
	public static CommonProxy proxy;
	@Mod.Instance(MODID)
	public static TheTitans modInstance;
	@Mod.Metadata
	public static ModMetadata meta;
	public static final ResourceLocation genericTitanWhiteTexture32x64 = new ResourceLocation(TheTitans.MODID, "textures/entities/32x64_disintigration.png");
	public static final ResourceLocation genericTitanWhiteTexture64x64 = new ResourceLocation(TheTitans.MODID, "textures/entities/64x64_disintigration.png");
	public static final EnumRarity godly = EnumHelper.addRarity("GODLY", EnumChatFormatting.DARK_PURPLE, "Godly");
	public static final CreativeTabs titansTab = new CreativeTabs("thetitans")
	{
		public Item getTabIconItem()
		{
			return TitanItems.growthSerum;
		}
	};
	public static Configuration config;
	public static final EnumCreatureAttribute TITAN = EnumHelper.addCreatureAttribute("TITAN");
	public static StatBase titansDefeatedStat = (new StatBasic("stat.titansDefeated", new ChatComponentTranslation("stat.titansDefeated", new Object[0]))).registerStat();
	public static StatBase deathsTitansStat = (new StatBasic("stat.deathsTitan", new ChatComponentTranslation("stat.deathsTitans", new Object[0]))).registerStat();
	public static final BiomeGenVoid voidland = (BiomeGenVoid) new BiomeGenVoid(169).setColor(986895).setBiomeName("Void Island").setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
	public static final BiomeGenNowhere nowhere = (BiomeGenNowhere) new BiomeGenNowhere(170).setColor(3278130).setBiomeName("The Nowhere").setDisableRain().setTemperatureRainfall(0.0F, 0.0F);
	public static final int VOID_DIMENSION_ID = 200;
	public static final int NOWHERE_DIMENSION_ID = 201;
	public static final Enchantment turretEnchant1 = new EnchantmentDurability(249, new ResourceLocation("healing"), 10, EnumEnchantmentType.weapon);
	public static final Enchantment turretEnchant2 = new EnchantmentFerocity(250, new ResourceLocation("damage"), 10, EnumEnchantmentType.weapon);
	public static final Enchantment turretEnchant3 = new EnchantmentManiac(251, new ResourceLocation("shootingSpeed"), 5, EnumEnchantmentType.weapon);
	public static final Enchantment turretEnchant4 = new EnchantmentUnstablility(252, new ResourceLocation("explosivePower"), 2, EnumEnchantmentType.weapon);
	public static final Enchantment turretEnchant5 = new EnchantmentShurakin(253, new ResourceLocation("skullSpeed"), 1, EnumEnchantmentType.weapon);
	public static final Enchantment titanSlaying = new EnchantmentTitanSlayer(254, new ResourceLocation("titankiller"), 10);
	public static final Enchantment hammer = new EnchantmentKnockup(255, new ResourceLocation("hammer"), 5);
	public static int OmegafishMinionSpawnrate;
	public static int SpiderTitanMinionSpawnrate;
	public static int CaveSpiderTitanMinionSpawnrate;
	public static int SlimeTitanMinionSpawnrate;
	public static int MagmaCubeTitanMinionSpawnrate;
	public static int ZombieTitanMinionSpawnrate;
	public static int PigZombieTitanMinionSpawnrate;
	public static int SkeletonTitanMinionSpawnrate;
	public static int CreeperTitanMinionSpawnrate;
	public static int BlazeTitanMinionSpawnrate;
	public static int WitherSkeletonTitanMinionSpawnrate;
	public static int EnderColossusMinionSpawnrate;
	public static int GhastTitanMinionSpawnrate;
	public static int WitherzillaMinionSpawnrate;
	public static int UltimaIronGolemMinionSpawnrate;
	public static int GargoyleKingMinionSpawnrate;
	public static int SnowGolemMinionSpawnrate;
	public static boolean TitansFFAMode;
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = new DebugExtender(event.getModLog(), event.getSide() == Side.SERVER ? null : MODID);
		reflect = new ReflectionHelper(logger);
		config = new Configuration(new File(event.getModConfigurationDirectory(), "titans/general.cfg"));
		TitanConfig.load();
		info(TitanVars.preInit[Maths.random(9)]);
		debug("Pre Initialization started!");
		
		NetworkHandler.preInit();
		TheCore.preInit(event);
		
		if (event.getSide() == Side.CLIENT)
			TheClientCore.preInit(event);
		
		DimensionManager.registerProviderType(VOID_DIMENSION_ID, WorldProviderVoid.class, false);
		DimensionManager.registerDimension(VOID_DIMENSION_ID, VOID_DIMENSION_ID);
		DimensionManager.registerProviderType(NOWHERE_DIMENSION_ID, WorldProviderNowhere.class, false);
		DimensionManager.registerDimension(NOWHERE_DIMENSION_ID, NOWHERE_DIMENSION_ID);
		proxy.preInit(event);
		
		debug("Finished pre-init for The Titans!");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		debug("Initialization started!");
		proxy.init(event);
		GameRegistry.registerFuelHandler(theFuel);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new WorldHandler());
		if (event.getSide() == Side.SERVER)
		FMLCommonHandler.instance().bus().register(new TickHandler(null));
		else
		FMLCommonHandler.instance().bus().register(new TickHandler(Minecraft.getMinecraft()));
		proxy.registerRenders();
		TitansAchievments.addAchievments();
		Enchantment.addToBookList(turretEnchant1);
		Enchantment.addToBookList(turretEnchant2);
		Enchantment.addToBookList(turretEnchant3);
		Enchantment.addToBookList(turretEnchant4);
		Enchantment.addToBookList(turretEnchant5);
		Enchantment.addToBookList(titanSlaying);
		Enchantment.addToBookList(hammer);
		BiomeGenBase.explorationBiomesList.remove(voidland);
		BiomeGenBase.explorationBiomesList.remove(nowhere);
		GameRegistry.registerWorldGenerator(new WorldGenStuffs(), 100);
		GameRegistry.registerWorldGenerator(new WorldGenMobs(), 10000);
		if (Loader.isModLoaded("OreSpawn"))
		GameRegistry.registerWorldGenerator(new SpawnEggOreGeneration(), 100);
		debug("Finished init for The Titans!");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		debug("Post Initialization started!");
		proxy.registerRenderThings();
		proxy.registerItemRenderers();
		proxy.postInit(event);
		debug("Finished post-init for The Titans!");
		info(TitanVars.postInit[Maths.random(9)]);
	}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		if (TitanConfig.isCoreModding)
		{
			if (TitanConfig.useCoreCommandDifficulty)
			event.registerServerCommand(new CommandNewDifficulty());
			if (TitanConfig.useCoreCommandKill)
			event.registerServerCommand(new CommandNewKill());
		}

		event.registerServerCommand(new CommandTitans());
		event.registerServerCommand(new CommandEvent());
		event.registerServerCommand(new CommandClearMinions());
		event.registerServerCommand(new CommandDebug());
	}

	@Mod.EventHandler
	public void serverStop(FMLServerStoppedEvent event)
	{
		EventData.saveInstance = null;
	}

	public static boolean equipped(EntityLivingBase entity, Item item, int slot)
	{
		slot = MathHelper.clamp_int(slot, 0, 4);
		int fixedSlot = slot == 0 ? 0 : 5 - slot;
		
		if (entity != null)
			return item != null && entity.getEquipmentInSlot(fixedSlot) != null && entity.getEquipmentInSlot(fixedSlot).getItem().getUnlocalizedName().equals(item.getUnlocalizedName());
		
		return false;
	}

	public static boolean equippedAll(EntityLivingBase entity, Item... items)
	{
		return equippedAll(entity, false, items);
	}

	public static boolean equippedAll(EntityLivingBase entity, boolean countHeld, Item... items)
	{
		int fixedSlot;
		
		if (entity != null)
			for (int i = countHeld ? 0 : 1; i < items.length && i < 5; i++)
			{
				fixedSlot = countHeld ? i == 0 ? 0 : 5 - i : 4 - i;
				if (entity.getEquipmentInSlot(fixedSlot) == null || !entity.getEquipmentInSlot(fixedSlot).getItem().getUnlocalizedName().equals(items[i].getUnlocalizedName()))
					return false;
			}
		return true;
	}

	public static String getTextures()
	{
		return getTextures(null, null);
	}

	public static String getTextures(String path)
	{
		return getTextures(null, path);
	}

	public static String getTextures(String prefixPath, String path)
	{
		return path == null ? TEXTURES[MathHelper.clamp_int(TitanConfig.textures, 0, TEXTURES.length - 1)] : prefixPath == null ? MODID + ":" + TEXTURES[MathHelper.clamp_int(TitanConfig.textures, 0, TEXTURES.length - 1)] + "/" + path : MODID + ":" + prefixPath + "/" + TEXTURES[MathHelper.clamp_int(TitanConfig.textures, 0, TEXTURES.length - 1)] + "/" + path;
	}

	public static void reloadTextures()
	{
		info("Reloading resource locations...");
		Iterator<?> registryItems = Item.itemRegistry.iterator();
		Iterator<?> registryBlocks = Block.blockRegistry.iterator();
		Object i;
		while (registryItems.hasNext())
		{
			i = registryItems.next();
			if (i != null && IReloadable.class.isAssignableFrom(i.getClass()))
			{
				debug("Found reloadable Item " + ((Item)i).getUnlocalizedName().substring(5) + ". Reloading texture...");
				((IReloadable)i).refreshTextures();
			}
		}

		while (registryBlocks.hasNext())
		{
			i = registryBlocks.next();
			if (i != null && IReloadable.class.isAssignableFrom(i.getClass()))
			{
				debug("Found reloadable Block " + ((Block)i).getUnlocalizedName().substring(5) + ". Reloading texture...");
				((IReloadable)i).refreshTextures();
			}
		}
	}

	public static boolean isDifficulty(World worldObj, EnumDifficulty difficulty)
	{
		return isDifficulty(worldObj, difficulty, null);
	}

	public static boolean isDifficulty(World worldObj, EnumDifficulty difficultyMin, EnumDifficulty difficultyMax)
	{
		int min = difficultyMin == null ? 0 : difficultyMin.getDifficultyId();
		int max = difficultyMax == null ? TheCore.DIFFICULTIES.length - 1 : difficultyMax.getDifficultyId();
		return isDifficulty(getDifficulty(worldObj), min, max);
	}

	public static boolean isDifficulty(int difficultyCur, int difficultyMin, int difficultyMax)
	{
		return difficultyMin <= difficultyMax && difficultyCur >= difficultyMin && difficultyCur <= difficultyMax ? true : false;
	}

	public static int getMaxDifficulty()
	{
		return TitanConfig.isCoreModding && TitanConfig.useCoreDifficulty ? TheCore.DIFFICULTIES.length - 1 : 3;
	}

	public static int getDifficulty(World worldObj)
	{
		int difficulty = 0;
		if (worldObj.difficultySetting != null)
		worldObj.difficultySetting.getDifficultyId();
		return !(TitanConfig.isCoreModding && TitanConfig.useCoreDifficulty) && TitanConfig.difficulty > difficulty ? TitanConfig.difficulty : difficulty;
	}

	public static EnumDifficulty getEnumDifficulty(World worldObj)
	{
		int difficulty = 0;
		if (worldObj.difficultySetting != null)
		worldObj.difficultySetting.getDifficultyId();
		return !(TitanConfig.isCoreModding && TitanConfig.useCoreDifficulty) && TitanConfig.difficulty > difficulty ? TheCore.DIFFICULTIES[TitanConfig.difficulty] : worldObj.difficultySetting;
	}

	public static String translate(String category, String key, Object... variables)
	{
		String value = Utils.translate((category + "." + MODID + "." + key).toLowerCase(), variables);
		if (value.equalsIgnoreCase(category + "." + MODID + "." + key))
		logger.addError("Localization key returned null: " + value + "=");
		return value;
	}

	public static String translateMult(String category, String key, int amount, Object... variables)
	{
		String value = Utils.translate((category + "." + MODID + "." + key).toLowerCase(), amount, variables);
		if (value.contains(category + "." + MODID + "." + key))
		logger.addError("Localization key returned null: " + value + "=");
		return value;
	}

	public static boolean checkEntityBlacklist(Entity entity, boolean refinedCheck)
	{
		if (refinedCheck)
		return entity != null && (entity instanceof EntityItem || entity instanceof EntityEnderPearl || entity instanceof EntityFallingBlock || entity instanceof EntityMinecart || entity instanceof EntityItemFrame || entity instanceof EntityPainting || entity instanceof EntityXPOrb || entity instanceof EntityWeatherEffect);
		else
		return entity != null && !(entity instanceof EntityLivingBase);
	}

	public static boolean checkFriendlyFire(EntityPlayer player, Entity entity, boolean refinedCheck)
	{
		if (refinedCheck)
			return entity != null && player != null && (entity instanceof EntityLivingBase && (player.isOnSameTeam((EntityLivingBase)entity) || (entity instanceof EntityTameable && (((EntityTameable)entity).getOwner() != null && (((EntityTameable)entity).getOwner().isEntityEqual(player) || player.isOnSameTeam(((EntityTameable)entity).getOwner())) || ((EntityTameable)entity).isTamed())) || (entity instanceof EntityIronGolem && ((EntityIronGolem)entity).isPlayerCreated()) || (entity instanceof EntityGargoyle && ((EntityGargoyle)entity).isPlayerCreated()) || entity instanceof EntitySnowman || entity instanceof EntityVillager || (entity instanceof EntityHorse && ((EntityHorse)entity).isTame())));
		else
			return entity != null && player != null && (entity instanceof EntityLivingBase && (player.isOnSameTeam((EntityLivingBase)entity) || entity instanceof EntityTameable || entity instanceof EntityGolem || entity instanceof EntityVillager || entity instanceof EntityHorse || entity instanceof EntityAnimal));
	}

	public static void info (String message)
	{
		logger.info(message);
	}

	public static void debug (String message)
	{
		logger.debug(message);
	}

	public static void warn (String message)
	{
		logger.warn(message);
	}

	public static void error (String message, Throwable e)
	{
		logger.error(message, e);
	}

	public static void fatal (String message, Throwable e)
	{
		logger.fatal(message, e);
	}
}


