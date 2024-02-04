package net.minecraft.theTitans;
import java.util.HashMap;
import java.util.Map;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.theTitans.blocks.*;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraftforge.oredict.OreDictionary;
import net.mrbt0907.utils.RegistryHelper.Registry;
public class TitanBlocks
{
	public static final Registry<Block, Boolean> blockRegistry = new Registry<Block, Boolean>(true);
	private static Map<Block, String> queuedDicts = new HashMap<Block, String>();
	//Titan Blocks
	public static final Block stoneperch = new BlockPerch("stoneperch", "cobblestone", 1.5F, 10F);
	public static final Block sandstoneperch = new BlockPerch("sandstoneperch", "sandstone_bottom", 0.8F, 5F);
	public static final Block obsidianperch = new BlockPerch("obsidianperch", "obsidian", 50F, 2000F);
	public static final Block goldperch = new BlockPerch("goldperch", "gold_block", 3F, 10F);
	public static final Block ironperch = new BlockPerch("ironperch", "iron_block", 5F, 10F);
	public static final Block endstoneperch = new BlockPerch("endstoneperch", "end_stone", 3F, 15F);
	public static final Block netherbrickperch = new BlockPerch("netherbrickperch", "nether_brick", 2F, 10F);
	public static final Block netherite = new BlockNormal("netherite", 0, 1.5F, 10.0F);
	public static final Block bedrock_block = new BlockCompactBedrock(Material.rock, "bedrock_compact");
	public static final Block corminium = new BlockCorminium(Material.rock, "corminium");
	private static final Block baseBlocks[] = {Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.obsidian, Blocks.bedrock, netherite, bedrock_block};
	
	public static final Block[] jadeite_ore = generateOres("jadeite", TitanItems.jadeite, 4, 4.0F, 3500.0F);
	public static final Block[] molten_fuel_ore = generateOres("molten_fuel", TitanItems.moltenFuel, 0, 3.0F, 5.0F);
	public static final Block[] pig_iron_ore = generateOres("pig_iron", 1, 3.0F, 5.0F);
	public static final Block[] ruby_ore = generateOres("ruby", TitanItems.ruby, 2, 3.0F, 5.0F);
	public static final Block[] sapphire_ore = generateOres("sapphire", TitanItems.sapphire, 2, 3.0F, 5.0F);
	public static final Block[] copper_ore = generateOres("copper", 0, 3.0F, 5.0F);
	public static final Block[] tin_ore = generateOres("tin", 0, 3.0F, 5.0F);
	public static final Block[] chromium_ore = generateOres("chromium", 1, 3.0F, 5.0F);
	public static final Block[] magnesium_ore = generateOres("magnesium", 1, 3.0F, 5.0F);
	public static final Block[] lead_ore = generateOres("lead", 1, 5.0F, 5.0F);
	public static final Block[] silver_ore = generateOres("silver", 1, 5.0F, 10.0F);
	public static final Block[] platinum_ore = generateOres("platinum", 2, 5.0F, 15.0F);
	public static final Block[] titanium_ore = generateOres("titanium", 3, 5.0F, 20.0F);
	public static final Block[] coal_ore = generateOres("coal", Items.coal, 0, 3.0F, 5.0F, Blocks.end_stone, Blocks.netherrack, Blocks.obsidian, Blocks.bedrock, netherite, bedrock_block);
	public static final Block[] iron_ore = generateOres("iron", 0, 3.0F, 5.0F, Blocks.end_stone, Blocks.netherrack, Blocks.obsidian, Blocks.bedrock, netherite, bedrock_block);
	public static final Block[] lapis_ore = generateOres("lapis", 1, 3.0F, 5.0F, Blocks.end_stone, Blocks.netherrack, Blocks.obsidian, Blocks.bedrock, netherite, bedrock_block);
	public static final Block[] redstone_ore = generateOres("redstone", Items.redstone, 5, 2, 3.0F, 5.0F, Blocks.end_stone, Blocks.netherrack, Blocks.obsidian, Blocks.bedrock, netherite, bedrock_block);
	public static final Block[] gold_ore = generateOres("gold", 2, 3.0F, 5.0F, Blocks.end_stone, Blocks.netherrack, Blocks.obsidian, Blocks.bedrock, netherite, bedrock_block);
	public static final Block[] diamond_ore = generateOres("diamond", Items.diamond, 2, 3.0F, 5.0F, Blocks.end_stone, Blocks.netherrack, Blocks.obsidian, Blocks.bedrock, netherite, bedrock_block);
	public static final Block[] emerald_ore = generateOres("emerald", Items.emerald, 2, 3.0F, 5.0F, Blocks.end_stone, Blocks.netherrack, Blocks.obsidian, Blocks.bedrock, netherite, bedrock_block);
	public static final Block copper_block = new BlockNormalCompressed(MapColor.redColor, "copper_block");
	public static final Block tin_block = new BlockNormalCompressed(MapColor.grayColor, "tin_block");
	public static final Block bronze_block = new BlockNormalCompressed(MapColor.brownColor, "bronze_block");
	public static final Block chromium_block = new BlockNormalCompressed(MapColor.grayColor, "chromium_block");
	public static final Block magnesium_block = new BlockNormalCompressed(MapColor.grayColor, "magnesium_block");
	public static final Block steel_block = new BlockNormalCompressed(MapColor.grayColor, "steel_block");
	public static final Block lead_block = new BlockNormalCompressed(MapColor.grayColor, "lead_block");
	public static final Block silver_block = new BlockNormalCompressed(MapColor.silverColor, "silver_block");
	public static final Block platinum_block = new BlockNormalCompressed(MapColor.silverColor, "platinum_block");
	public static final Block titanium_block = new BlockNormalCompressed(MapColor.silverColor, "titanium_block");
	public static final Block jadeite_block = new BlockNormalCompressed(MapColor.greenColor, "jadeite_block");
	public static final BlockNormalCompressed molten_fuel_block = new BlockNormalCompressed(MapColor.redColor, "molten_fuel_block");
	public static final Block pig_iron_block = new BlockNormalCompressed(MapColor.pinkColor, "pig_iron_block");
	public static final Block ruby_block = new BlockNormalCompressed(MapColor.redColor, "ruby_block");
	public static final Block sapphire_block = new BlockNormalCompressed(MapColor.blueColor, "sapphire_block");
	public static final Block[] demontium_ore = generateOres("demontium", 0, 0.0F, 0.0F);
	public static final Block harcadium_ore = new BlockHarcadiumOre("harcadium");
	public static final Block harcadium_ore_end_stone = new BlockHarcadiumOre(Blocks.end_stone, "harcadium");
	public static final Block harcadium_ore_obsidian = new BlockHarcadiumOre(Blocks.obsidian, "harcadium");
	public static final Block[] harcadium_ore_other = generateOres("harcadium", 0, 0.0F, 0.0F, Blocks.netherrack, Blocks.bedrock, netherite, bedrock_block);
	public static final Block[] withirenium_ore = generateOres("withirenium", 0, 0.0F, 0.0F);
	public static final Block void_ore = new BlockVoidOre("void");
	public static final Block void_ore_end_stone = new BlockVoidOre(Blocks.end_stone, "void");
	public static final Block void_ore_obsidian = new BlockVoidOre(Blocks.obsidian, "void");
	public static final Block[] void_ore_other = generateOres("void", 0, 0.0F, 0.0F, Blocks.netherrack, Blocks.bedrock, netherite, bedrock_block);
	public static final Block adamantium_ore = new BlockAdamantiumOre(bedrock_block, "adamantium");
	public static final Block[] adamantium_ore_other = generateOres("adamantium", 0, 0.0F, 0.0F, Blocks.stone, Blocks.end_stone, Blocks.netherrack, Blocks.obsidian, Blocks.bedrock, netherite);
	public static final Block demontium_block = new BlockDemontiumBlock("demontium_block");
	public static final Block harcadium_block = new BlockHarcadiumBlock("harcadium_block");
	public static final Block withirenium_block = new BlockWithireniumBlock("withirenium_block");
	public static final Block void_block = new BlockVoidBlock("void_block");
	public static final Block adamantium_block = new BlockAdamantiumBlock("adamantium_block");
	public static final Block magic_pumpkin = new BlockMagicPumpkin(false);
	public static final Block malgrumCrop = new BlockMalgrumCrop();
	public static final Block pleasantBladeCrop = new BlockPleasantBladeCrop();
	//Orespawn Blocks
	public static Block MyOverlordScorpionPartSpawnBlock;
	public static Block MyOverlordScorpionSpawnBlock;
	public static Block MyMethuselahKrakenPartSpawnBlock;
	public static Block MyMethuselahKrakenSpawnBlock;
	public static Block MyBurningMobzillaPartSpawnBlock;
	public static Block MyBurningMobzillaSpawnBlock;
	public static void setup()
	{
		Blocks.bedrock.setHardness(1000000000.0F).setHarvestLevel("pickaxe", Integer.MAX_VALUE - 1);
		register(corminium);
		register(bedrock_block);
		register("oreJadeite", jadeite_ore);
		register("oreMoltenFuel", molten_fuel_ore);
		register("orePigIron", pig_iron_ore);
		register("oreRuby", ruby_ore);
		register("oreSapphire", sapphire_ore);
		register("netherite", netherite);
		register("oreCoal", coal_ore);
		register("oreIron", iron_ore);
		register("oreGold", gold_ore);
		register("oreLapisLazuli", lapis_ore);
		register("oreRedstone", redstone_ore);
		register("oreDiamond", diamond_ore);
		register("oreEmerald", emerald_ore);
		register("oreCopper", copper_ore);
		register("oreTin", tin_ore);
		register("oreChromium", chromium_ore);
		register("oreMagnesium", magnesium_ore);
		register("oreLead", lead_ore);
		register("oreSilver", silver_ore);
		register("orePlatinum", platinum_ore);
		register("oreTitanium", titanium_ore);
		register("oreDemontium", demontium_ore);
		register("oreHarcadium", harcadium_ore);
		register("oreHarcadium", harcadium_ore_end_stone);
		register("oreHarcadium", harcadium_ore_obsidian);
		register("oreHarcadium", harcadium_ore_other);
		register("oreWithirenium", withirenium_ore);
		register("oreVoid", void_ore);
		register("oreVoid", void_ore_end_stone);
		register("oreVoid", void_ore_obsidian);
		register("oreVoid", void_ore_other);
		register("oreAdamantium", adamantium_ore);
		register("oreAdamantium", adamantium_ore_other);
		register("blockCopper", copper_block);
		register("blockTin", tin_block);
		register("blockBronze", bronze_block);
		register("blockChromium", chromium_block);
		register("blockMagnesium", magnesium_block);
		register("blockSteel", steel_block);
		register("blockLead", lead_block);
		register("blockSilver", silver_block);
		register("blockPlatinum", platinum_block);
		register("blockTitanium", titanium_block);
		register("blockJadeite", jadeite_block);
		register("blockMoltenFuel", molten_fuel_block);
		register("blockPigIron", pig_iron_block);
		register("blockRuby", ruby_block);
		register("blockSapphire", sapphire_block);
		register("blockDemontium", demontium_block);
		register("blockHarcadium", harcadium_block);
		register("blockWithirenium", withirenium_block);
		register("blockVoid", void_block);
		register("blockAdamantium", adamantium_block);
		register(stoneperch);
		register(sandstoneperch);
		register(obsidianperch);
		register(goldperch);
		register(ironperch);
		register(endstoneperch);
		register(netherbrickperch);
		register(magic_pumpkin);
		register(malgrumCrop);
		register(pleasantBladeCrop);
		if (Loader.isModLoaded("OreSpawn"))
		{
			MyOverlordScorpionPartSpawnBlock = new BlockOreEgg("oreoverlordscorpionpart");
			MyOverlordScorpionSpawnBlock = new BlockOreEgg("oreoverlordscorpion");
			MyMethuselahKrakenPartSpawnBlock = new BlockOreEgg("oreelderkrakenpart");
			MyMethuselahKrakenSpawnBlock = new BlockOreEgg("oreelderkraken");
			MyBurningMobzillaPartSpawnBlock = new BlockOreEgg("oregodzillaburningpart");
			MyBurningMobzillaSpawnBlock = new BlockOreEgg("oregodzillaburning");
			register(MyOverlordScorpionPartSpawnBlock);
			register(MyOverlordScorpionSpawnBlock);
			register(MyMethuselahKrakenPartSpawnBlock);
			register(MyMethuselahKrakenSpawnBlock);
			register(MyBurningMobzillaPartSpawnBlock);
			register(MyBurningMobzillaSpawnBlock);
		}
	}

	public static void preInit()
	{
		processRegistered();
		molten_fuel_block.setBurnTime(18000);
	}

	public static void init()
	{
		processQueued();
		TheTitans.theFuel.add(molten_fuel_block);
	}

	public static void postInit()
	{
		for (Map.Entry<Block, Boolean> entry : blockRegistry.keySet())
		if(entry.getKey().getLocalizedName().equalsIgnoreCase(entry.getKey().getUnlocalizedName() + ".name"))
		TheTitans.logger.addError("Localization key returned null: " + entry.getKey().getUnlocalizedName() + ".name=");
	}

	private static Block[] generateOres(String materialName, int toolMaterial, float hardness, float resistance, Block... bases)
	{
		return generateOres(materialName, null, 1, toolMaterial, hardness, resistance, bases);
	}

	private static Block[] generateOres(String materialName, Item item, int toolMaterial, float hardness, float resistance, Block... bases)
	{
		return generateOres(materialName, item, 1, toolMaterial, hardness, resistance, bases);
	}

	private static Block[] generateOres(String materialName, Item item, int amount, int toolMaterial, float hardness, float resistance, Block... bases)
	{
		if (bases.length == 0)
		bases = baseBlocks;
		Block[] ores = new Block[bases.length];
		for (int i = 0; i < bases.length; i++)
		ores[i] = newOre(bases[i], materialName, item, amount, toolMaterial, hardness, resistance);
		return ores;
	}

	private static Block newOre(Block base, String materialName, Item item, int amount, int toolMaterial, float hardness, float resistance)
	{
		switch(materialName.toLowerCase())
		{
			case "harcadium": return item == null ? new BlockHarcadiumOre(base, materialName) : new BlockHarcadiumOre(base, materialName).setItemDropped(item);
			case "withirenium": return item == null ? new BlockWithireniumOre(base, materialName) : new BlockWithireniumOre(base, materialName).setItemDropped(item);
			case "void": return item == null ? new BlockVoidOre(base, materialName) : new BlockVoidOre(base, materialName).setItemDropped(item);
			case "adamantium": return item == null ? new BlockAdamantiumOre(base, materialName) : new BlockAdamantiumOre(base, materialName).setItemDropped(item);
			default: return item == null ? new BlockNormalOre(base, materialName, toolMaterial, hardness, resistance).setAmountDrop(amount) : new BlockNormalOre(base, materialName, toolMaterial, hardness, resistance).setItemDropped(item).setAmountDrop(amount);
		}
	}

	private static void processQueued()
	{
		for (Map.Entry<Block, String> entry : queuedDicts.entrySet())
		OreDictionary.registerOre(entry.getValue(), entry.getKey());
		queuedDicts.clear();
	}

	private static void processRegistered()
	{
		TitanConfig.load();
		int id = 0;
		while (id < blockRegistry.size())
		{
			for (Map.Entry<Object[], Boolean> entry : blockRegistry.entrySet())
			if (id == (int)entry.getKey()[0])
			if (entry.getValue())
			{
				GameRegistry.registerBlock((Block)entry.getKey()[1], ((Block)entry.getKey()[1]).getUnlocalizedName().substring(5));
				id++;
				break;
			}

			else
			{
				if (queuedDicts.containsKey((Block)entry.getKey()[1]))
				queuedDicts.remove((Block)entry.getKey()[1]);
				id++;
				break;
			}
		}
	}

	private static void oreDict(String oreDictionary, Block... blocks)
	{
		for (Block block : blocks)
		if (block != null)
		OreDictionary.registerOre(oreDictionary, block);
	}

	private static void register(String oreDictionary, Block... blocks)
	{
		register(blocks);
		for (Block block : blocks)
		if (block != null)
		queuedDicts.put(block, oreDictionary);
	}

	private static void register(Block... blocks)
	{
		for (Block block : blocks)
		if (block != null)
		blockRegistry.add(block);
	}

	public static boolean exists(Block block)
	{
		return blockRegistry.contains(block) ? Block.blockRegistry.containsKey(TheTitans.MODID + ":" + block.getUnlocalizedName().substring(5)) : true;
	}
}


