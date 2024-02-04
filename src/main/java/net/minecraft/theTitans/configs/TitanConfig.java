package net.minecraft.theTitans.configs;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.RenderTheTitans;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.theTitans.TitanItems;
public class TitanConfig
{
	private static int oldTextures;
	//Core Configurations
	public static int debug;
	public static int quality;
	public static int textures;
	public static int barTextures;
	public static boolean isAntiCheat;
	public static boolean isCoreModding;
	//Configurations
	public static int difficulty;
	public static boolean useCoreDifficulty;
	public static boolean useCoreGameSettings;
	public static boolean useCoreCommandDifficulty;
	public static boolean useCoreCommandKill;
	public static boolean useTitanBar;
	public static boolean useTitanBarPerm;
	public static int spawnProtection;
	public static int spawnSeparation;
	public static int minionLimit;
	public static void load()
	{
		TheTitans.config.load();
		//Core Configurations
		debug = TheTitans.config.get("general", "Debug", 0, TheTitans.translate("config", "integer.debug"), 0, 2).getInt(); TheTitans.logger.showDebug = debug;
		quality = TheTitans.config.get("general", "Quality", 0, TheTitans.translate("config", "integer.quality"), 0, 10).getInt();
		textures = TheTitans.config.get("general", "Texture Set", 0, TheTitans.translate("config", "integer.textures"), 0, TheTitans.TEXTURES.length - 1).getInt(); if (textures != oldTextures) 
		{
			TheTitans.reloadTextures(); oldTextures = textures;
		}


		isAntiCheat = TheTitans.config.get("general", "Enable Anti Cheat", true, TheTitans.translate("config", "boolean.anticheat")).getBoolean();
		isCoreModding = TheTitans.config.get("general", "Enable Core Modding", true, TheTitans.translate("config", "boolean.coremodding")).setRequiresMcRestart(true).getBoolean();
		//Categories
		TheTitans.config.getCategory("client").setComment(TheTitans.translate("config", "title.client"));
		TheTitans.config.getCategory("core").setShowInGui(isCoreModding).setComment(TheTitans.translate("config", "title.core"));
		TheTitans.config.getCategory("blocks").setComment(TheTitans.translate("config", "title.blocks"));
		TheTitans.config.getCategory("items").setComment(TheTitans.translate("config", "title.items"));
		TheTitans.config.getCategory("mobs").setComment(TheTitans.translate("config", "title.mobs"));
		TheTitans.config.getCategory("misc").setShowInGui(isAntiCheat).setComment(TheTitans.translate("config", "title.misc"));
		//Configurations		
		useCoreDifficulty = TheTitans.config.get("core", "Override Difficulty", false, TheTitans.translate("config", "boolean.core.difficulty")).setShowInGui(isCoreModding).setRequiresMcRestart(true).getBoolean();
		useCoreGameSettings = TheTitans.config.get("core", "Override Options Menu", false, TheTitans.translate("config", "boolean.core.gamesettings")).setShowInGui(isCoreModding).setRequiresMcRestart(true).getBoolean();
		useCoreCommandDifficulty = TheTitans.config.get("core", "Override command /difficulty", true, TheTitans.translate("config", "boolean.core.commanddifficulty")).setRequiresWorldRestart(true).setShowInGui(isCoreModding).getBoolean();
		useCoreCommandKill = TheTitans.config.get("core", "Override command /kill", true, TheTitans.translate("config", "boolean.core.commandkill")).setShowInGui(isCoreModding).setRequiresWorldRestart(true).getBoolean();
		useTitanBar = TheTitans.config.get("client", "Enable Custom Health Bar", true, TheTitans.translate("config", "boolean.client.healthbar")).getBoolean();
		useTitanBarPerm = TheTitans.config.get("client", "Permanent Custom Health Bar", false, TheTitans.translate("config", "boolean.client.permhealthbar")).setShowInGui(useTitanBar).getBoolean();
		barTextures = TheTitans.config.get("client", "Titan Gui Textures", 0, TheTitans.translate("config", "int.client.healthbar"), 0, 2).setShowInGui(useTitanBar).getInt();
		spawnProtection = TheTitans.config.get("mobs", "Titan Spawn Range", 1500, TheTitans.translate("config", "integer.mob.spawnprotection")).getInt();
		spawnSeparation = TheTitans.config.get("mobs", "Titan Spawn Separation", 600, TheTitans.translate("config", "integer.mob.spawnseparation")).getInt();
		minionLimit = TheTitans.config.get("mobs", "Minion Limit", -1, TheTitans.translate("config", "integer.mob.minionlimit")).setShowInGui(!isAntiCheat).getInt();
		difficulty = TheTitans.config.get("general", "Difficulty", 0, TheTitans.translate("config", "integer.difficulty"), 0, 7).setShowInGui(!(isCoreModding && useCoreDifficulty)).getInt();
		for (Map.Entry<Block, Boolean> entry : TitanBlocks.blockRegistry.keySet())
		TitanBlocks.blockRegistry.set(entry.getKey(), TheTitans.config.get("blocks", "Enable Block " + entry.getKey().getLocalizedName(), true, TheTitans.translate("config", "boolean.blocks.enable", entry.getKey().getLocalizedName())).setRequiresMcRestart(true).getBoolean());
		for (Map.Entry<Item, Boolean> entry : TitanItems.itemRegistry.keySet())
		TitanItems.itemRegistry.set(entry.getKey(), TheTitans.config.get("items", "Enable Item " + entry.getKey().getItemStackDisplayName(new ItemStack(entry.getKey())), true, TheTitans.translate("config", "boolean.items.enable", entry.getKey().getItemStackDisplayName(new ItemStack(entry.getKey())))).setRequiresMcRestart(true).getBoolean());
		int id = 0;
		while (id < RenderTheTitans.entityRegistry.size())
		{
			for (Map.Entry<Object[], Object[]> entry : RenderTheTitans.entityRegistry.entrySet())
			if (id == (int)entry.getKey()[0])
			{
				String name = ((Class<?>)entry.getKey()[1]).getSimpleName().toLowerCase().substring(6);
				TheTitans.config.getCategory("mobs." + name).setShowInGui(isAntiCheat).setComment(TheTitans.translate("config", "enable.mobs", name));
				Object values[] = new Object[3];
				values[0] = TheTitans.config.get("mobs." + name, "Max Health", 1.0D, TheTitans.translate("config", "double.mob.maxhealth", name)).getDouble();
				values[1] = TheTitans.config.get("mobs." + name, "Damage", 1.0D, TheTitans.translate("config", "double.mob.damage", name)).getDouble();
				values[2] = TheTitans.config.get("mobs." + name, "Armor", 1.0D, TheTitans.translate("config", "double.mob.armor", name)).getDouble();
				RenderTheTitans.entityRegistry.set((int)entry.getKey()[0], values);
				id++;
				break;
			}
		}

		//Compatability Variables. Cannot change locations of these in this update.
		TheTitans.TitansFFAMode = TheTitans.config.get("general", "Enable Free For All Mode", false, TheTitans.translate("config", "boolean.ffa")).getBoolean();
		TheTitans.SnowGolemMinionSpawnrate = TheTitans.config.get("mobs", "Snow Golem Spawnrate", 100, TheTitans.translate("config", "integer.mob.spawnrate.snowgolem")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.SlimeTitanMinionSpawnrate = TheTitans.config.get("mobs", "Slime Minion Spawnrate", 30, TheTitans.translate("config", "integer.mob.spawnrate.slime")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.MagmaCubeTitanMinionSpawnrate = TheTitans.config.get("mobs", "Magma Cube Minion Spawnrate", 30, TheTitans.translate("config", "integer.mob.spawnrate.magmacube")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.OmegafishMinionSpawnrate = TheTitans.config.get("mobs", "Silverfish Minion Spawnrate", 40, TheTitans.translate("config", "integer.mob.spawnrate.silverfish")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.CaveSpiderTitanMinionSpawnrate = TheTitans.config.get("mobs", "Cave Spider Minion Spawnrate", 60, TheTitans.translate("config", "integer.mob.spawnrate.cavespider")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.SpiderTitanMinionSpawnrate = TheTitans.config.get("mobs", "Spider Minion Spawnrate", 50, TheTitans.translate("config", "integer.mob.spawnrate.spider")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.ZombieTitanMinionSpawnrate = TheTitans.config.get("mobs", "Zombie Minion Spawnrate", 20, TheTitans.translate("config", "integer.mob.spawnrate.zombie")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.SkeletonTitanMinionSpawnrate = TheTitans.config.get("mobs", "Skeleton Minion Spawnrate", 60, TheTitans.translate("config", "integer.mob.spawnrate.skeleton")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.CreeperTitanMinionSpawnrate = TheTitans.config.get("mobs", "Creeper Minion Spawnrate", 60, TheTitans.translate("config", "integer.mob.spawnrate.creeper")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.PigZombieTitanMinionSpawnrate = TheTitans.config.get("mobs", "Pig Zombie Minion Spawnrate", 60, TheTitans.translate("config", "integer.mob.spawnrate.pigzombie")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.BlazeTitanMinionSpawnrate = TheTitans.config.get("mobs", "Blaze Minion Spawnrate", 40, TheTitans.translate("config", "integer.mob.spawnrate.blaze")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.GargoyleKingMinionSpawnrate = TheTitans.config.get("mobs", "Gargoyle Minion Spawnrate", 100, TheTitans.translate("config", "integer.mob.spawnrate.gargoyle")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.WitherSkeletonTitanMinionSpawnrate = TheTitans.config.get("mobs", "Wither Skeleton Minion Spawnrate", 40, TheTitans.translate("config", "integer.mob.spawnrate.witherskeleton")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.GhastTitanMinionSpawnrate = TheTitans.config.get("mobs", "Ghast Minion Spawnrate", 60, TheTitans.translate("config", "integer.mob.spawnrate.ghast")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.UltimaIronGolemMinionSpawnrate = TheTitans.config.get("mobs", "Iron Golem Minion Spawnrate", 100, TheTitans.translate("config", "integer.mob.spawnrate.irongolem")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.EnderColossusMinionSpawnrate = TheTitans.config.get("mobs", "Enderman Minion Spawnrate", 40, TheTitans.translate("config", "integer.mob.spawnrate.enderman")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.WitherzillaMinionSpawnrate = TheTitans.config.get("mobs", "Wither Minion Spawnrate", 20, TheTitans.translate("config", "integer.mob.spawnrate.wither")).setShowInGui(!isAntiCheat).getInt();
		TheTitans.config.save();
	}
}


