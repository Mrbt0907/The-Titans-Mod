package net.minecraft.titans;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.titans.entity.animal.IEndMob;
import net.minecraft.titans.event.GameEventHandler;
import net.minecraft.titans.network.NetworkHandler;
import net.minecraft.titans.registries.*;
import net.minecraft.titans.server.TitanManagerServer;
import net.minecraft.titans.server.commands.CommandTitans;
import net.minecraft.titans.world.WorldProviderVoid;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid=TheTitans.MODID, name=TheTitans.MODNAME, version=TheTitans.VERSION, acceptedMinecraftVersions="[1.12.2]", dependencies="required-after:mac@[2.6,)")
public class TheTitans 
{
	public static final String MODNAME = "The Titans Mod";
	public static final String MODID = "thetitans";
	public static final String VERSION = "0.61-indev";
	public static final String CLIENT = "net.minecraft.titans.ClientProxy";
	public static final String SERVER = "net.minecraft.titans.CommonProxy";
	@SidedProxy(clientSide=CLIENT, serverSide=SERVER)
	public static CommonProxy proxy;
	@Mod.Instance
	public static TheTitans instance;
	private static Logger logger;
	public static boolean debug_mode = true;
	
	public static final CreativeTabs TAB_BLOCKS = new CreativeTabs(MODID + "_blocks") {@Override public ItemStack getTabIconItem() {return new ItemStack(TBlocks.harcadium_ore[0]);}};
	public static final CreativeTabs TAB_COMBAT = new CreativeTabs(MODID + "_weapons") {@Override public ItemStack getTabIconItem() {return new ItemStack(TItems.harcadiumTools[4]);}};
	public static final CreativeTabs TAB_TOOLS = new CreativeTabs(MODID + "_tools") {@Override public ItemStack getTabIconItem() {return new ItemStack(TItems.harcadiumTools[0]);}};
	public static final CreativeTabs TAB_ITEMS = new CreativeTabs(MODID + "_items") {@Override public ItemStack getTabIconItem() {return new ItemStack(TItems.growthSerum);}};
	public static final CreativeTabs TAB_MOBS = new CreativeTabs(MODID + "_mobs") {@Override public ItemStack getTabIconItem() {return new ItemStack(Blocks.SKULL, 1, 1);}};
	public static final EnumCreatureType VOID = EnumHelper.addCreatureType("VOID", IEndMob.class, 20, Material.AIR, true, false);
	
	public static final int DIMENSION_VOID_ID = 312;
	public static final int DIMENSION_NOWHERE_ID = 313;
	public static final DimensionType DIMENSION_VOID = DimensionType.register("The Void", "_void", DIMENSION_VOID_ID, WorldProviderVoid.class, false);
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger = e.getModLog();
		info("Loading The Titans Mod...");
		debug("Pre-Initialization started");
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new GameEventHandler());
		MinecraftForge.EVENT_BUS.register(TBlocks.class);
		MinecraftForge.EVENT_BUS.register(TItems.class);
		MinecraftForge.EVENT_BUS.register(TBiomes.class);
		
		DimensionManager.registerDimension(DIMENSION_VOID_ID, DIMENSION_VOID);
		//DimensionManager.registerDimension(DIMENSION_NOWHERE_ID, DIMENSION_NOWHERE);
		
		NetworkHandler.preInit();
		proxy.preInit(e);
		debug("Pre-Initialization finished");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		debug("Initialization started");
		ConfigManager.sync(MODID, Config.Type.INSTANCE);
		proxy.init(e);
		debug("Initialization finished");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		debug("Post-Initialization started!");
		proxy.postInit(e);
		debug("Post-Initialization finished");
		info("Finished The Titans Mod!");
	}
	
	@SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID))
        {
        	debug("Config was updated!");
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }
	
	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandTitans());
	}
	
	@Mod.EventHandler
	public void serverStop(FMLServerStoppedEvent event)
	{
		if (CommonProxy.manager != null)
		{
			CommonProxy.manager.reset();
			CommonProxy.manager = null;
		}
	}
	
	public static void checkServerManager()
	{
		if (CommonProxy.manager == null)
		{
			CommonProxy.manager = new TitanManagerServer();
			CommonProxy.manager.loadFile();
		}
	}
	
	public static boolean checkFriendlyFire(EntityPlayer player, Entity entity, boolean refinedCheck)
	{
		if (refinedCheck)
			return entity != null && player != null && (entity instanceof EntityLivingBase && (player.isOnSameTeam((EntityLivingBase)entity) || (entity instanceof EntityTameable && (((EntityTameable)entity).getOwner() != null && (((EntityTameable)entity).getOwner().isEntityEqual(player) || player.isOnSameTeam(((EntityTameable)entity).getOwner())) || ((EntityTameable)entity).isTamed())) || (entity instanceof EntityIronGolem && ((EntityIronGolem)entity).isPlayerCreated()) || entity instanceof EntitySnowman || entity instanceof EntityVillager || (entity instanceof EntityHorse && ((EntityHorse)entity).isTame())));
		else
			return entity != null && player != null && (entity instanceof EntityLivingBase && (player.isOnSameTeam((EntityLivingBase)entity) || entity instanceof EntityTameable || entity instanceof EntityGolem || entity instanceof EntityVillager || entity instanceof EntityHorse || entity instanceof EntityAnimal));
	}
	
	public static void info(Object message)
    {
		logger.info(message);
    }
    
    public static void debug(Object message)
    {
        if (debug_mode)
        	logger.info("[DEBUG] " + message);
    }
    
    public static void warn(Object message)
    {
        if (debug_mode)
        	logger.warn(message);
    }

    public static void error(Object message)
    {
        if (debug_mode)
        	logger.error(new Exception(message.toString()));
    }
    
    public static void fatal(Object message) throws Exception
    {
        throw new Exception(message.toString());
    }
    
    public static <T> String getClassName(T clazz)
	{
		return clazz.getClass().getName();
	}
}
