package net.minecraft.theTitans;
import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
public final class CraftingRecipes
{
	private static String[] recipePatterns = new String[] {"X,X,#", "XXX, # , # ", "X,#,#", "XX,X#, #", "XX, #, #", "XXX,X X", "X X,XXX,XXX", "XXX,X X,X X", "X X,X X"};
	private static Item[] copperStuff = new Item[] {TitanItems.copperSword, TitanItems.copperPickaxe, TitanItems.copperSpade, TitanItems.copperAxe, TitanItems.copperHoe, TitanItems.copperHelmet, TitanItems.copperChestplate, TitanItems.copperLeggings, TitanItems.copperBoots};
	private static Item[] tinStuff = new Item[] {TitanItems.tinSword, TitanItems.tinPickaxe, TitanItems.tinSpade, TitanItems.tinAxe, TitanItems.tinHoe, TitanItems.tinHelmet, TitanItems.tinChestplate, TitanItems.tinLeggings, TitanItems.tinBoots};
	private static Item[] bronzeStuff = new Item[] {TitanItems.bronzeSword, TitanItems.bronzePickaxe, TitanItems.bronzeSpade, TitanItems.bronzeAxe, TitanItems.bronzeHoe, TitanItems.bronzeHelmet, TitanItems.bronzeChestplate, TitanItems.bronzeLeggings, TitanItems.bronzeBoots};
	private static Item[] silverStuff = new Item[] {TitanItems.silverSword, TitanItems.silverPickaxe, TitanItems.silverSpade, TitanItems.silverAxe, TitanItems.silverHoe, TitanItems.silverHelmet, TitanItems.silverChestplate, TitanItems.silverLeggings, TitanItems.silverBoots};
	private static Item[] leadStuff = new Item[] {TitanItems.leadSword, TitanItems.leadPickaxe, TitanItems.leadSpade, TitanItems.leadAxe, TitanItems.leadHoe, TitanItems.leadHelmet, TitanItems.leadChestplate, TitanItems.leadLeggings, TitanItems.leadBoots};
	private static Item[] steelStuff = new Item[] {TitanItems.steelSword, TitanItems.steelPickaxe, TitanItems.steelSpade, TitanItems.steelAxe, TitanItems.steelHoe, TitanItems.steelHelmet, TitanItems.steelChestplate, TitanItems.steelLeggings, TitanItems.steelBoots};
	private static Item[] platinumStuff = new Item[] {TitanItems.platinumSword, TitanItems.platinumPickaxe, TitanItems.platinumSpade, TitanItems.platinumAxe, TitanItems.platinumHoe, TitanItems.platinumHelmet, TitanItems.platinumChestplate, TitanItems.platinumLeggings, TitanItems.platinumBoots};
	private static Item[] titaniumStuff = new Item[] {TitanItems.titaniumSword, TitanItems.titaniumPickaxe, TitanItems.titaniumSpade, TitanItems.titaniumAxe, TitanItems.titaniumHoe, TitanItems.titaniumHelmet, TitanItems.titaniumChestplate, TitanItems.titaniumLeggings, TitanItems.titaniumBoots};
	private static Item[] pigIronStuff = new Item[] {TitanItems.pigIronSword, TitanItems.pigIronPickaxe, TitanItems.pigIronSpade, TitanItems.pigIronAxe, TitanItems.pigIronHoe, TitanItems.pigIronHelmet, TitanItems.pigIronChestplate, TitanItems.pigIronLeggings, TitanItems.pigIronBoots};
	private static Item[] rubyStuff = new Item[] {TitanItems.rubySword, TitanItems.rubyPickaxe, TitanItems.rubySpade, TitanItems.rubyAxe, TitanItems.rubyHoe, TitanItems.rubyHelmet, TitanItems.rubyChestplate, TitanItems.rubyLeggings, TitanItems.rubyBoots};
	//private static Item[] demontiumStuff = new Item[] {TitanItems.demontiumSword, TitanItems.demontiumPickaxe, TitanItems.demontiumSpade, TitanItems.demontiumAxe, TitanItems.demontiumHoe, TitanItems.demontiumHelmet, TitanItems.demontiumChestplate, TitanItems.demontiumLeggings, TitanItems.demontiumBoots};
	private static Item[] harcadiumStuff = new Item[] {TitanItems.harcadiumSword, TitanItems.harcadiumPickaxe, TitanItems.harcadiumSpade, TitanItems.harcadiumAxe, TitanItems.harcadiumHoe, TitanItems.harcadiumHelmet, TitanItems.harcadiumChestplate, TitanItems.harcadiumLeggings, TitanItems.harcadiumBoots};
	private static Item[] withireniumStuff = new Item[] {TitanItems.withireniumSword, TitanItems.withireniumPickaxe, TitanItems.withireniumSpade, TitanItems.withireniumAxe, TitanItems.withireniumHoe, TitanItems.withireniumHelmet, TitanItems.withireniumChestplate, TitanItems.withireniumLeggings, TitanItems.withireniumBoots};
	private static Item[] voidStuff = new Item[] {TitanItems.voidSword, TitanItems.voidPickaxe, TitanItems.voidSpade, TitanItems.voidAxe, TitanItems.voidHoe, TitanItems.voidHelmet, TitanItems.voidChestplate, TitanItems.voidLeggings, TitanItems.voidBoots};
	
	public static void initCrafting()
	{
		smeltOre(TitanBlocks.copper_ore, TitanItems.copperIngot, 2);
		smeltOre(TitanBlocks.tin_ore, TitanItems.tinIngot, 2);
		smeltOre(TitanBlocks.chromium_ore, TitanItems.chromiumIngot, 3);
		smeltOre(TitanBlocks.magnesium_ore, TitanItems.magnesiumIngot, 3);
		smeltOre(TitanBlocks.lead_ore, TitanItems.leadIngot, 3);
		smeltOre(TitanBlocks.silver_ore, TitanItems.silverIngot, 5);
		smeltOre(TitanBlocks.platinum_ore, TitanItems.platinumIngot, 10);
		smeltOre(TitanBlocks.titanium_ore, TitanItems.titanium_ingot, 10);
		smeltOre(TitanBlocks.coal_ore, Items.coal, 2);
		smeltOre(TitanBlocks.iron_ore, Items.iron_ingot, 2);
		smeltOre(TitanBlocks.gold_ore, Items.gold_ingot, 2);
		smeltOre(TitanBlocks.redstone_ore, Items.redstone, 3);
		smeltOre(TitanBlocks.diamond_ore, Items.diamond, 5);
		smeltOre(TitanBlocks.emerald_ore, Items.emerald, 5);
		smeltOre(TitanBlocks.jadeite_ore, TitanItems.jadeite, 5);
		smeltOre(TitanBlocks.molten_fuel_ore, TitanItems.moltenFuel, 2);
		smeltOre(TitanBlocks.pig_iron_ore, TitanItems.pigIronIngot, 2);
		smeltOre(TitanBlocks.ruby_ore, TitanItems.ruby, 5);
		smeltOre(TitanBlocks.sapphire_ore, TitanItems.sapphire, 5);
		craftingShaped(Blocks.torch, 6, "#,X", '#', TitanItems.moltenFuel, 'X', Items.stick);
		craftingShaped(Items.ender_pearl, 64, "HHH,H H,HHH", 'H', TitanItems.harcadiumWaflet);
		craftingShaped(TitanItems.goldenCookie, 1, 0, "GGG,GPG,GGG", 'G', Items.gold_ingot, 'P', Items.cookie);
		craftingShaped(TitanItems.goldenCookie, 1, 1, "GGG,GPG,GGG", 'G', Blocks.gold_block, 'P', Items.cookie);
		craftingShaped(TitanItems.goldenBread, 1, 0, "GGG,GPG,GGG", 'G', Items.gold_ingot, 'P', Items.bread);
		craftingShaped(TitanItems.goldenBread, 1, 1, "GGG,GPG,GGG", 'G', Blocks.gold_block, 'P', Items.bread);
		craftingShaped(TitanItems.goldenPotatoe, 1, 0, "GGG,GPG,GGG", 'G', Items.gold_ingot, 'P', Items.baked_potato);
		craftingShaped(TitanItems.goldenPotatoe, 1, 1, "GGG,GPG,GGG", 'G', Blocks.gold_block, 'P', Items.baked_potato);
		craftingShaped(TitanItems.goldenMelon, 1, 0, "GGG,GPG,GGG", 'G', Items.gold_ingot, 'P', Items.melon);
		craftingShaped(TitanItems.goldenMelon, 1, 1, "GGG,GPG,GGG", 'G', Blocks.gold_block, 'P', Items.melon);
		craftingShaped(TitanItems.goldenPumpkinPie, 1, 0, "GGG,GPG,GGG", 'G', Items.gold_ingot, 'P', Items.pumpkin_pie);
		craftingShaped(TitanItems.goldenPumpkinPie, 1, 1, "GGG,GPG,GGG", 'G', Blocks.gold_block, 'P', Items.pumpkin_pie);
		craftingShaped(TitanItems.diamondApple, 1, 0, "GGG,GPG,GGG", 'G', Items.diamond, 'P', Items.apple);
		craftingShaped(TitanItems.diamondApple, 1, 1, "GGG,GPG,GGG", 'G', Blocks.diamond_block, 'P', Items.apple);
		craftingShaped(TitanItems.diamondCookie, 1, 0, "GGG,GPG,GGG", 'G', Items.diamond, 'P', Items.cookie);
		craftingShaped(TitanItems.diamondCookie, 1, 1, "GGG,GPG,GGG", 'G', Blocks.diamond_block, 'P', Items.cookie);
		craftingShaped(TitanItems.diamondBread, 1, 0, "GGG,GPG,GGG", 'G', Items.diamond, 'P', Items.bread);
		craftingShaped(TitanItems.diamondBread, 1, 1, "GGG,GPG,GGG", 'G', Blocks.diamond_block, 'P', Items.bread);
		craftingShaped(TitanItems.diamondPotatoe, 1, 0, "GGG,GPG,GGG", 'G', Items.diamond, 'P', Items.baked_potato);
		craftingShaped(TitanItems.diamondPotatoe, 1, 1, "GGG,GPG,GGG", 'G', Blocks.diamond_block, 'P', Items.baked_potato);
		craftingShaped(TitanItems.diamondMelon, 1, 0, "GGG,GPG,GGG", 'G', Items.diamond, 'P', Items.melon);
		craftingShaped(TitanItems.diamondMelon, 1, 1, "GGG,GPG,GGG", 'G', Blocks.diamond_block, 'P', Items.melon);
		craftingShaped(TitanItems.diamondPumpkinPie, 1, 0, "GGG,GPG,GGG", 'G', Items.diamond, 'P', Items.pumpkin_pie);
		craftingShaped(TitanItems.diamondPumpkinPie, 1, 1, "GGG,GPG,GGG", 'G', Blocks.diamond_block, 'P', Items.pumpkin_pie);
		craftingShaped(TitanItems.jadeiteApple, 1, 0, "GGG,GPG,GGG", 'G', TitanItems.jadeite, 'P', Items.apple);
		craftingShaped(TitanItems.jadeiteApple, 1, 1, "GGG,GPG,GGG", 'G', TitanBlocks.jadeite_block, 'P', Items.apple);
		craftingShaped(TitanItems.jadeiteCookie, 1, 0, "GGG,GPG,GGG", 'G', TitanItems.jadeite, 'P', Items.cookie);
		craftingShaped(TitanItems.jadeiteCookie, 1, 1, "GGG,GPG,GGG", 'G', TitanBlocks.jadeite_block, 'P', Items.cookie);
		craftingShaped(TitanItems.jadeiteBread, 1, 0, "GGG,GPG,GGG", 'G', TitanItems.jadeite, 'P', Items.bread);
		craftingShaped(TitanItems.jadeiteBread, 1, 1, "GGG,GPG,GGG", 'G', TitanBlocks.jadeite_block, 'P', Items.bread);
		craftingShaped(TitanItems.jadeitePotato, 1, 0, "GGG,GPG,GGG", 'G', TitanItems.jadeite, 'P', Items.baked_potato);
		craftingShaped(TitanItems.jadeitePotato, 1, 1, "GGG,GPG,GGG", 'G', TitanBlocks.jadeite_block, 'P', Items.baked_potato);
		craftingShaped(TitanItems.jadeiteMelon, 1, 0, "GGG,GPG,GGG", 'G', TitanItems.jadeite, 'P', Items.melon);
		craftingShaped(TitanItems.jadeiteMelon, 1, 1, "GGG,GPG,GGG", 'G', TitanBlocks.jadeite_block, 'P', Items.melon);
		craftingShaped(TitanItems.jadeitePumpkinPie, 1, 0, "GGG,GPG,GGG", 'G', TitanItems.jadeite, 'P', Items.pumpkin_pie);
		craftingShaped(TitanItems.jadeitePumpkinPie, 1, 1, "GGG,GPG,GGG", 'G', TitanBlocks.jadeite_block, 'P', Items.pumpkin_pie);
		craftingShaped(TitanItems.growthSerum, 2, "RNR,FBF,GRG", 'R', Items.rotten_flesh, 'N', Items.nether_star, 'F', Items.fire_charge, 'B', Items.glass_bottle, 'G', Items.ghast_tear);
		craftingShaped(TitanItems.teleporter, "WWW,SSS, S ", 'W', new ItemStack(Items.skull, 1, 1), 'S', Blocks.soul_sand);
		craftingShaped(TitanItems.teleporter2, "GOG,ONO,GOG", 'G', Blocks.gold_block, 'O', Blocks.obsidian, 'N', Items.nether_star);
		craftingShaped(TitanItems.goodTurret, "S,O,B", 'S', new ItemStack(Items.skull, 1, 1), 'O', Blocks.obsidian, 'B', Blocks.bedrock);
		craftingShaped(TitanItems.goodTurret2, "SOS, B ", 'S', new ItemStack(Items.skull, 1, 1), 'O', Blocks.obsidian, 'B', Blocks.bedrock);
		craftingShaped(TitanItems.goodTurret3, " A ,NBN, G ", 'A', TitanItems.goodTurret, 'G', TitanItems.goodTurret2, 'B', Blocks.beacon, 'N', Items.nether_star);
		craftingShaped(TitanItems.chaff, 8, "PWP,WPW,PWP", 'P', Items.paper, 'W', Items.wheat);
		craftingShaped(TitanItems.harcadiumBow, " HS,H S, HS", 'S', 'H', TitanItems.diamondString, TitanItems.harcadium);
		craftingShaped(TitanItems.harcadiumArrow, 4, "H,D,F", 'H', TitanItems.harcadium, 'D', Items.diamond, 'F', Items.feather);
		craftingShaped(TitanItems.voidBow, " HS,H S, HS", 'S', TitanItems.harcadiumWaflet, 'H', TitanItems.voidItem);
		craftingShaped(TitanItems.voidArrow, 4, "H,D,F", 'H', TitanItems.voidItem, 'D', TitanItems.harcadium, 'F', Items.feather);
		craftingShaped(TitanItems.adminiumAxe, "HH ,HD , D ", 'D', Blocks.bedrock, 'H', TitanBlocks.bedrock_block);
		craftingShaped(TitanItems.adminiumPickaxe, "HHH, D , D ", 'D', Blocks.bedrock, 'H', TitanBlocks.bedrock_block);
		craftingShaped(TitanItems.adminiumSpade, " H , D , D ", 'D', Blocks.bedrock, 'H', TitanBlocks.bedrock_block);
		craftingShaped(TitanItems.adminiumHoe, "HH , D , D ", 'D', Blocks.bedrock, 'H', TitanBlocks.bedrock_block);
		craftingShaped(TitanItems.adminiumSword, " H , H , D ", 'D', Blocks.bedrock, 'H', TitanBlocks.bedrock_block);
		craftingShaped(TitanItems.adminiumHelmet, "BBB,B B", 'B', TitanBlocks.bedrock_block);
		craftingShaped(TitanItems.adminiumChestplate, "H H,BVB,BBB", 'B', TitanBlocks.bedrock_block, 'V', TitanBlocks.void_block, 'H', TitanBlocks.harcadium_block);
		craftingShaped(TitanItems.adminiumLeggings, "BHB,B B,B B", 'B', TitanBlocks.bedrock_block, 'H', TitanBlocks.harcadium_block);
		craftingShaped(TitanItems.adminiumBoots, "B B,B B", 'B', TitanBlocks.bedrock_block);
		craftToolArmorSet(TitanItems.copperIngot, Items.stick, copperStuff);
		craftToolArmorSet(TitanItems.tinIngot, Items.stick, tinStuff);
		craftToolArmorSet(TitanItems.bronzeIngot, Items.stick, bronzeStuff);
		craftToolArmorSet(TitanItems.leadIngot, Items.stick, leadStuff);
		craftToolArmorSet(TitanItems.silverIngot, Items.stick, silverStuff);
		craftToolArmorSet(TitanItems.steelIngot, Items.stick, steelStuff);
		craftToolArmorSet(TitanItems.platinumIngot, Items.stick, platinumStuff);
		craftToolArmorSet(TitanItems.titanium_ingot, Items.stick, titaniumStuff);
		craftToolArmorSet(TitanItems.pigIronIngot, Items.stick, pigIronStuff);
		craftToolArmorSet(TitanItems.ruby, Items.stick, rubyStuff);
		//craftToolArmorSet(TitanItems.demontiumIngot, Items.diamond, demontiumStuff);
		craftToolArmorSet(TitanItems.harcadium, Items.diamond, harcadiumStuff);
		craftToolArmorSet(TitanItems.withirenium, Items.diamond, withireniumStuff);
		craftToolArmorSet(TitanItems.voidItem, TitanItems.harcadium, voidStuff);
		if (Loader.isModLoaded("OreSpawn"))
		{
			craftingShapeless(TitanItems.OverlordScorpionEgg, 1, TitanBlocks.MyOverlordScorpionSpawnBlock, Items.water_bucket);
			craftingShapeless(TitanItems.MethuselahKrakenEgg, 1, TitanBlocks.MyMethuselahKrakenSpawnBlock, Items.water_bucket);
			craftingShapeless(TitanItems.BurningMobzillaEgg, 1, TitanBlocks.MyBurningMobzillaSpawnBlock, Items.water_bucket);
			craftingShaped(TitanBlocks.MyMethuselahKrakenSpawnBlock, "HHH,HHH,HHH", Character.valueOf('H'), TitanBlocks.MyMethuselahKrakenPartSpawnBlock);
			craftingShaped(TitanBlocks.MyOverlordScorpionSpawnBlock, "HHH,HHH,HHH", Character.valueOf('H'), TitanBlocks.MyOverlordScorpionPartSpawnBlock);
			craftingShaped(TitanBlocks.MyBurningMobzillaSpawnBlock, "HHH,HHH,HHH", Character.valueOf('H'), TitanBlocks.MyBurningMobzillaPartSpawnBlock);
		}

		craftingShapeless(TitanItems.pleasantBladeBrew, 1, TitanItems.pleasantBladeLeaf, Items.glass_bottle, Items.carrot, Items.fermented_spider_eye);
		craftingShapeless(TitanItems.steelIngot, 1, TitanItems.chromiumIngot, TitanItems.magnesiumIngot, Items.iron_ingot, Items.coal);
		craftingShapeless(TitanItems.bronzeIngot, 1, TitanItems.copperIngot, TitanItems.copperIngot, TitanItems.tinIngot);
		craftingShapeless(TitanItems.diamondString, 4, Items.string, Items.diamond, Items.diamond);
		craftCompressedBlock(TitanBlocks.bedrock_block, Blocks.bedrock);
		craftCompressedBlock(TitanBlocks.void_block, TitanItems.voidItem);
		craftCompressedBlock(TitanBlocks.withirenium_block, TitanItems.withirenium);
		craftCompressedBlock(TitanBlocks.harcadium_block, TitanItems.harcadium);
		craftCompressedBlock(TitanItems.harcadium, TitanItems.harcadiumNugget);
		craftCompressedBlock(TitanItems.harcadiumNugget, TitanItems.harcadiumWafer);
		craftCompressedBlock(TitanItems.harcadiumWafer, TitanItems.harcadiumWaflet);
		craftCompressedBlock(TitanBlocks.jadeite_block, TitanItems.jadeite);
		craftCompressedBlock(TitanBlocks.copper_block, TitanItems.copperIngot);
		craftCompressedBlock(TitanBlocks.tin_block, TitanItems.tinIngot);
		craftCompressedBlock(TitanBlocks.bronze_block, TitanItems.bronzeIngot);
		craftCompressedBlock(TitanBlocks.silver_block, TitanItems.silverIngot);
		craftCompressedBlock(TitanBlocks.lead_block, TitanItems.leadIngot);
		craftCompressedBlock(TitanBlocks.steel_block, TitanItems.steelIngot);
		craftCompressedBlock(TitanBlocks.chromium_block, TitanItems.chromiumIngot);
		craftCompressedBlock(TitanBlocks.magnesium_block, TitanItems.magnesiumIngot);
		craftCompressedBlock(TitanBlocks.platinum_block, TitanItems.platinumIngot);
		craftCompressedBlock(TitanBlocks.titanium_block, TitanItems.titanium_ingot);
		craftCompressedBlock(TitanBlocks.molten_fuel_block, TitanItems.moltenFuel);
		craftCompressedBlock(TitanBlocks.pig_iron_block, TitanItems.pigIronIngot);
		craftCompressedBlock(TitanBlocks.ruby_block, TitanItems.ruby);
		craftCompressedBlock(TitanBlocks.sapphire_block, TitanItems.sapphire);
	}

	private static void smeltOre(Block[] ores, Item item)
	{
		smeltOre(ores, item, 1, 0);
	}

	private static void smeltOre(Block[] ores, Item item, int exp)
	{
		smeltOre(ores, item, 1, exp);
	}

	private static void smeltOre(Block[] ores, Item item, int amount, int exp)
	{
		for (int i = 0; i < ores.length; i++)
		if (ores[i] != null)
		GameRegistry.addSmelting(ores[i], new ItemStack(item, amount), exp);
	}

	private static void craftingShapeless(Object output, int amount, Object... input)
	{
		craftingShapeless(output, amount, 0, input);
	}

	private static void craftingShapeless(Object output, int amount, int meta, Object... input)
	{
		if (output == null)
		{
			TheTitans.warn("Recipe output is null. Skipping shaped recipe...");
			return;
		}

		else if (!(output instanceof Item || output instanceof Block))
		{
			TheTitans.warn("Recipe output of " + output.getClass().getSimpleName() + " is not an instance of Item or Block. Skipping shaped recipe...");
			return;
		}

		else if (output instanceof Item)
		{
			if (!TitanItems.exists((Item)output))
			return;
			for (Object entry : input)
			if (entry != null)
			{
				if (entry instanceof Item && !TitanItems.exists((Item)entry))
				return;
				else if (entry instanceof Block && !TitanBlocks.exists((Block)entry))
				return;
			}

			else
			return;
			TheTitans.debug("Registering shapeless recipe for: " + ((Item)output).getUnlocalizedName() + ":" + meta);
			GameRegistry.addShapelessRecipe(new ItemStack((Item)output, amount, meta), input);
		}

		else if (output instanceof Block)
		{
			if (!TitanBlocks.exists((Block)output))
			return;
			for (Object entry : input)
			if (entry != null)
			if (entry instanceof Item && !TitanItems.exists((Item)entry))
			return;
			else if (entry instanceof Block && !TitanBlocks.exists((Block)entry))
			return;
			TheTitans.debug("Registering shapeless recipe for: " + ((Block)output).getUnlocalizedName() + ":" + meta);
			GameRegistry.addShapelessRecipe(new ItemStack((Block)output, amount, meta), input);
		}
	}

	private static void craftingShaped(Object output, String pattern, Object... input)
	{
		craftingShaped(output, 1, 0, pattern, input);
	}

	private static void craftingShaped(Object output, int amount, String pattern, Object... input)
	{
		craftingShaped(output, amount, 0, pattern, input);
	}

	private static void craftingShaped(Object output, int amount, int meta, String pattern, Object... input)
	{
		if (output == null)
		{
			TheTitans.warn("Recipe output is null. Skipping shaped recipe...");
			return;
		}

		else if (!(output instanceof Item || output instanceof Block))
		{
			TheTitans.warn("Recipe output of " + output.getClass().getSimpleName() + " is not an instance of Item or Block. Skipping shaped recipe...");
			return;
		}

		else if (output instanceof Item)
		{
			if (!TitanItems.exists((Item)output))
			return;
			TheTitans.debug("Registering shaped recipe for: " + ((Item)output).getUnlocalizedName() + ":" + meta);
		}

		else if (output instanceof Block)
		{
			if (!TitanBlocks.exists((Block)output))
			return;
			TheTitans.debug("Registering shaped recipe for: " + ((Block)output).getUnlocalizedName() + ":" + meta);
		}

		String patterns[] = new String[3];
		List<Character> indexes = new ArrayList<Character>();
		List<Object> inputs = new ArrayList<Object>();
		List<Object> recipe = new ArrayList<Object>();
		if (pattern == null)
		{
			TheTitans.warn("Pattern failed. Skipping shaped recipe for " + ((Item)output).getUnlocalizedName() + "...");
			return;
		}

		patterns = pattern.split(",");
		if (patterns.length == 0)
		{
			TheTitans.warn("Pattern failed. Skipping shaped recipe for " + ((Item)output).getUnlocalizedName() + "...");
			return;
		}

		if (patterns.length > 0)
		recipe.add(patterns[0]);
		if (patterns.length > 1)
		recipe.add(patterns[1]);
		if (patterns.length > 2)
		recipe.add(patterns[2]);
		for (int i = 0; i < input.length; i++)
		if (input[i] instanceof Character)
		indexes.add((char)input[i]);
		else if (input[i] instanceof ItemStack)
		inputs.add(input[i]);
		else if (input[i] instanceof Item)
		inputs.add(input[i]);
		else if (input[i] instanceof Block)
		inputs.add(input[i]);
		for (int i = 0; i < inputs.size(); i++)
		{
			if (i >= inputs.size())
			{
				TheTitans.warn("There are more indexes than items provided. Skipping to registry of recipe...");
				break;
			}

			if (inputs.get(i) != null)
			{
				if (inputs.get(i) instanceof Item && !TitanItems.exists((Item)inputs.get(i)))
				{
					TheTitans.debug(((Item)inputs.get(i)).getUnlocalizedName() + " returned null. Skipping to registry of recipe...");
					return;
				}

				else if (inputs.get(i) instanceof Block && !TitanBlocks.exists((Block)inputs.get(i)))
				{
					TheTitans.debug(((Block)inputs.get(i)).getUnlocalizedName() + " returned null. Skipping to registry of recipe...");
					return;
				}
			}

			else
			return;
			recipe.add(indexes.get(i));
			recipe.add(inputs.get(i));
		}

		if (patterns.length > 0 && recipe.size() > 0)
		if (output instanceof Item)
		GameRegistry.addShapedRecipe(new ItemStack((Item)output, amount, meta), recipe.toArray());
		else if (output instanceof Block)
		GameRegistry.addShapedRecipe(new ItemStack((Block)output, amount, meta), recipe.toArray());
		else
		TheTitans.warn("Recipe has failed to finalize. Skipping shaped recipe...");
	}

	private static void craftToolArmorSet(Item ingot, Item handle, Item... list)
	{
		for (int i = 0; i < list.length; ++i)
		craftingShaped((Item)list[i], recipePatterns[i], '#', handle, 'X', ingot);
	}

	private static void craftCompressedBlock(Object parentItem, Object childItem)
	{
		Item itemA = parentItem instanceof Block ? Item.getItemFromBlock((Block)parentItem) : parentItem instanceof Item ? (Item)parentItem : null;
		Item itemB = childItem instanceof Block ? Item.getItemFromBlock((Block)childItem) : childItem instanceof Item ? (Item)childItem : null;
		if (itemA == null)
		TheTitans.warn("Compressed recipe parentItem is not an item or block. Skipping shaped recipe...");
		else if (itemB == null)
		TheTitans.warn("Compressed recipe childItem is not an item or block. Skipping shaped recipe...");
		craftingShapeless(itemA, 1, itemB, itemB, itemB, itemB, itemB, itemB, itemB, itemB, itemB);
		craftingShapeless(itemB, 9, itemA);
	}
}


