package net.mrbt0907.thetitans;

import org.apache.logging.log4j.Logger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.mrbt0907.thetitans.config.ConfigMain;
import net.mrbt0907.thetitans.event.GameEventHandler;
import net.mrbt0907.thetitans.network.NetworkReciever;
import net.mrbt0907.thetitans.registries.*;
import net.mrbt0907.thetitans.world.WorldProviderVoid;
import net.mrbt0907.util.MrbtAPI;
import net.mrbt0907.util.network.NetworkHandler;

@Mod(modid=TheTitans.MODID, name=TheTitans.MODNAME, version=TheTitans.VERSION, acceptedMinecraftVersions="[1.12.2]", dependencies="required-after:mac@[2.6,)")
public class TheTitans 
{
	public static final String MODNAME = "The Titans Mod";
	public static final String MODID = "thetitans";
	public static final String VERSION = "0.6.3-indev";
	public static final String CLIENT = "net.mrbt0907.thetitans.ClientProxy";
	public static final String SERVER = "net.mrbt0907.thetitans.CommonProxy";
	@SidedProxy(clientSide=CLIENT, serverSide=SERVER)
	public static CommonProxy proxy;
	@Mod.Instance
	public static TheTitans instance;
	public static final NetworkReciever NETWORK = new NetworkReciever();
	private static Logger logger;
	public static boolean debug_mode = true;
	
	public static final CreativeTabs TAB_BLOCKS = new CreativeTabs(MODID + "_blocks") {@Override public ItemStack getTabIconItem() {return new ItemStack(BlockRegistry.harcadium_ore[0]);}};
	public static final CreativeTabs TAB_ITEMS = new CreativeTabs(MODID + "_items") {@Override public ItemStack getTabIconItem() {return new ItemStack(ItemRegistry.harcadium);}};
	public static final CreativeTabs TAB_COMBAT = new CreativeTabs(MODID + "_weapons") {@Override public ItemStack getTabIconItem() {return new ItemStack(ItemRegistry.harcadiumTools[4]);}};
	public static final CreativeTabs TAB_TOOLS = new CreativeTabs(MODID + "_tools") {@Override public ItemStack getTabIconItem() {return new ItemStack(ItemRegistry.harcadiumTools[0]);}};
	public static final CreativeTabs TAB_MOBS = new CreativeTabs(MODID + "_mobs") {@Override public ItemStack getTabIconItem() {return new ItemStack(Blocks.SKULL, 1, 1);}};
	//public static final EnumCreatureType VOID = EnumHelper.addCreatureType("VOID", IEndMob.class, 20, Material.AIR, true, false);
	
	public static final int DIMENSION_VOID_ID = 312;
	public static final int DIMENSION_NOWHERE_ID = 313;
	public static final DimensionType DIMENSION_VOID = DimensionType.register("The Void", "_void", DIMENSION_VOID_ID, WorldProviderVoid.class, false);
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		MrbtAPI.initialize(MODID, instance);
		logger = e.getModLog();
		ConfigManager.sync(MODID, Config.Type.INSTANCE);
		info("Loading The Titans Mod...");
		debug("Pre-Initialization started");
		NetworkHandler.register(NETWORK);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new GameEventHandler());
		MinecraftForge.EVENT_BUS.register(BlockRegistry.class);
		MinecraftForge.EVENT_BUS.register(ItemRegistry.class);
		MinecraftForge.EVENT_BUS.register(BiomeRegistry.class);
		RecipeRegistry.INSTANCE.init();
		
		DimensionManager.registerDimension(DIMENSION_VOID_ID, DIMENSION_VOID);
		//DimensionManager.registerDimension(DIMENSION_NOWHERE_ID, DIMENSION_NOWHERE);
		
		MrbtAPI.preInit(e);
		proxy.preInit(e);
		debug("Pre-Initialization finished");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		debug("Initialization started");
		MrbtAPI.init(e);
		proxy.init(e);
		debug("Initialization finished");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		debug("Post-Initialization started!");
		MrbtAPI.postInit(e);
		proxy.postInit(e);
		debug("Post-Initialization finished");
		info("Finished The Titans Mod!");
	}
	
	@SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID))
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
    }
	
	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		
	}
	
	public static void info(Object message)
	{
		logger.info(message);
	}
	
	public static void debug(Object message)
	{
		boolean isDebug = ConfigMain.debug_mode;
		if (isDebug)
			logger.info("[DEBUG] " + message);
	}
	
	public static void warn(Object message)
	{
		boolean isDebug = ConfigMain.debug_mode;
		if (isDebug)
			logger.warn(message);
	}

	public static void error(Object message)
	{
			Throwable exception;
			
			if (message instanceof Throwable)
				exception = (Throwable) message;
			else
				exception = new Exception(String.valueOf(message));

			exception.printStackTrace();
	}
	
	public static void fatal(Object message)
	{
		Error error;
		
		if (message instanceof Error)
			error = (Error) message;
		else
			error = new Error(String.valueOf(message));
		
		throw error;
	}
}