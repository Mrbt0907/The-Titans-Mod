package net.minecraft.titans.registries;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSword;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.items.BaseArmor;
import net.minecraft.titans.items.BaseAxe;
import net.minecraft.titans.items.BaseHoe;
import net.minecraft.titans.items.BasePickaxe;
import net.minecraft.titans.items.BaseShovel;
import net.minecraft.titans.items.BaseSword;
import net.minecraft.titans.items.BaseTitanSword;
import net.minecraft.titans.items.ItemMaterial;
import net.minecraft.titans.items.ItemTeleporter;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

public class TItems 
{
	private static RegistryEvent.Register<Item> registry;
	private static final List<Block> item_blocks = new ArrayList<Block>();

	public static final ItemMaterial armorMaterialAdminium = new ItemMaterial("adminium", 100000000, 100000, 100000, 100000, 100000, 0.0F, 60, null);
	public static final ItemMaterial armorMaterialVoid = new ItemMaterial("void", 100000, 11, 17, 13, 9, 0.0F, 50, null);
	public static final ItemMaterial armorMaterialHellsite = new ItemMaterial("hellsite", 50000, 10, 15, 12, 9, 0.0F, 40, null);
	public static final ItemMaterial armorMaterialHarcadium = new ItemMaterial("harcadium", 20000, 9, 13, 10, 8, 0.0F, 30, null);
	public static final ItemMaterial armorMaterialDemontium = new ItemMaterial("demontium", 20000, 9, 13, 10, 8, 0.0F, 30, null);
	public static final ItemMaterial armorMaterialPigIron = new ItemMaterial("pig_iron", 18, 3, 6, 5, 2, 0.0F, 9, null);
	public static final ItemMaterial armorMaterialRuby = new ItemMaterial("ruby", 35, 4, 7, 10, 3, 0.0F, 10, null);
	public static final ItemMaterial armorMaterialTitanium = new ItemMaterial("titanium", 36, 3, 8, 6, 3, 0.0F, 18, null);
	public static final ItemMaterial armorMaterialPlatinum = new ItemMaterial("platinum", 36, 3, 8, 6, 3, 0.0F, 18, null);
	public static final ItemMaterial armorMaterialSteel = new ItemMaterial("steel", 30, 3, 8, 6, 3, 0.0F, 20, null);
	public static final ItemMaterial armorMaterialLead = new ItemMaterial("lead", 24, 2, 6, 4, 2, 0.0F, 8, null);
	public static final ItemMaterial armorMaterialSilver = new ItemMaterial("silver", 16, 2, 6, 4, 2, 0.0F, 25, null);
	public static final ItemMaterial armorMaterialBronze = new ItemMaterial("bronze", 12, 2, 6, 4, 2, 0.0F, 14, null);
	public static final ItemMaterial armorMaterialTin = new ItemMaterial("tin", 6, 1, 3, 2, 1, 0.0F, 18, null);
	public static final ItemMaterial armorMaterialCopper = new ItemMaterial("copper", 6, 1, 3, 2, 1, 0.0F, 12, null);
	public static final ItemMaterial armorMaterialNickel = new ItemMaterial("nickel", 7, 2, 3, 5, 2, 0.0F, 20, null);
	public static final ItemMaterial armorMaterialZinc = new ItemMaterial("zinc", 7, 2, 5, 6, 2, 0.0F, 0, null);
	public static final ItemMaterial armorMaterialSapphire = new ItemMaterial("sapphire", 16, 1, 4, 3, 1, 0.0F, 12, null);
	public static final ItemMaterial armorMaterialOnyx = new ItemMaterial("onyx", 20, 2, 5, 4, 2, 0.0F, 12, null);
	public static final ItemMaterial armorMaterialDragon = new ItemMaterial("dragon", 375, 13, 40, 30, 13, 0.0F, 12, null);
	public static final ItemMaterial armorMaterialChromium = new ItemMaterial("chromium", 9, 1, 3, 2, 1, 0.0F, 25, null);
	public static final ItemMaterial armorMaterialBrass = new ItemMaterial("brass", 12, 2, 5, 6, 2, 0.0F, 10, null);
	public static final ItemMaterial armorMaterialEntropy = new ItemMaterial("entropy", 182, 7, 22, 17, 7, 0.0F, 12, null);
	public static final ItemMaterial toolMaterialOmni = new ItemMaterial("omni", Integer.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, 10000000.0F, 256);
	public static final ItemMaterial toolMaterialAdamantium = new ItemMaterial("adamantium", Integer.MAX_VALUE - 1, Integer.MAX_VALUE, 100000000000000.0F, 2500000.0F, 100);
	public static final ItemMaterial toolMaterialAdminium = new ItemMaterial("adminium", Integer.MAX_VALUE - 2, 1000000000, 1000000000.0F, 999996.0F, 60);
	public static final ItemMaterial toolMaterialVoid = new ItemMaterial("void", Integer.MAX_VALUE - 25, 5000000, 4800.0F, 249996.0F, 50);
	public static final ItemMaterial toolMaterialTitanus = new ItemMaterial("titanus", 20, 5000000, 4800.0F, 119996.0F, 50);
	public static final ItemMaterial toolMaterialHellsite = new ItemMaterial("hellsite", 19, 225000, 960.0F, 34996.0F, 40);
	public static final ItemMaterial toolMaterialHarcadium = new ItemMaterial("harcadium", 17, 75000, 120.0F, 14996.0F, 30);
	public static final ItemMaterial toolMaterialPigIron = new ItemMaterial("pig_iron", 2, 400, 7, 3, 9);
	public static final ItemMaterial toolMaterialRuby = new ItemMaterial("ruby", 2, 1675, 9, 4, 9);
	public static final ItemMaterial toolMaterialPlatinum = new ItemMaterial("platinum", 3, 3274, 10.0F, 6.0F, 18);
	public static final ItemMaterial toolMaterialTitanium = new ItemMaterial("titanium", 4, 3274, 10.0F, 6.0F, 18);
	public static final ItemMaterial toolMaterialMithril = new ItemMaterial("mithril", 16, 3274, 10.0F, 6.0F, 18);
	public static final ItemMaterial toolMaterialSteel = new ItemMaterial("steel", 3, 1000, 8.0F, 3.0F, 12);
	public static final ItemMaterial toolMaterialLead = new ItemMaterial("lead", 2, 800, 7.0F, 2.0F, 6);
	public static final ItemMaterial toolMaterialSilver = new ItemMaterial("silver", 2, 500, 6.0F, 2.0F, 22);
	public static final ItemMaterial toolMaterialBronze = new ItemMaterial("bronze", 2, 200, 6.0F, 2.0F, 14);
	public static final ItemMaterial toolMaterialTin = new ItemMaterial("tin", 1, 100, 3.0F, 1.0F, 18);
	public static final ItemMaterial toolMaterialCopper = new ItemMaterial("copper", 0, 80, 3.0F, 0.0F, 12);
	public static final ItemMaterial toolMaterialNickel = new ItemMaterial("nickel", 1, 121, 4.0F, 1.0F, 20);
	public static final ItemMaterial toolMaterialZinc = new ItemMaterial("zinc", 1, 183, 6.0F, 2.0F, 0);
	public static final ItemMaterial toolMaterialSapphire = new ItemMaterial("sapphire", 1, 452, 3.0F, 5.0F, 12);
	public static final ItemMaterial toolMaterialOnyx = new ItemMaterial("onyx", 1, 950, 3.0F, 6.5F, 12);
	public static final ItemMaterial toolMaterialDragon = new ItemMaterial("dragon", 16, 24450, 11.0F, 375.0F, 12);
	public static final ItemMaterial toolMaterialChromium = new ItemMaterial("chromium", 1, 42, 9.0F, 0.0F, 25);
	public static final ItemMaterial toolMaterialBrass = new ItemMaterial("brass", 1, 357, 6.0F, 2.0F, 10);
	public static final ItemMaterial toolMaterialEntropy = new ItemMaterial("entropy", 14, 7100, 7.0F, 182.0F, 12);
	
	public static final Item MUSKET = new Item();
	public static final Item POWER_CELL = new Item();
	public static final Item DIAMOND_CORE = new Item();
	public static final Item DRAGON_SCALE = new Item();
	public static final Item END_BARNICLE = new Item();
	
	public static final Item ultimaBlade = new BaseTitanSword(toolMaterialOmni);
	public static final Item optimaAxe = new BaseTitanSword(toolMaterialOmni);
	public static final Item adamantiumSword = new BaseTitanSword(toolMaterialAdamantium);
	public static final Item jadeite = new Item();
	public static final Item moltenFuel = new Item();
	public static final Item pigIronIngot = new Item();
	public static final Item ruby = new Item();
	public static final Item sapphire = new Item();
	public static final Item copperIngot = new Item();
	public static final Item tinIngot = new Item();
	public static final Item bronzeIngot = new Item();
	public static final Item silverIngot = new Item();
	public static final Item leadIngot = new Item();
	public static final Item chromiumIngot = new Item();
	public static final Item nickelIngot = new Item();
	public static final Item steelIngot = new Item();
	public static final Item platinumIngot = new Item();
	public static final Item titanium_ingot = new Item();
	public static final Item demontiumIngot = new Item();
	public static final Item harcadium = new Item();
	public static final Item harcadiumNugget = new Item();
	public static final Item harcadiumWafer = new Item();
	public static final Item harcadiumWaflet = new Item();
	public static final Item hellsite = new Item();
	public static final Item voidItem = new Item();
	public static final Item adamantium = new Item();
	public static final Item sacredSword = new BaseSword(toolMaterialCopper);
	public static Item[] nickelTools;
	public static Item[] zincTools;
	public static Item[] sapphireTools;
	public static Item[] onyxTools;
	public static Item[] dragonTools;
	
	public static Item[] copperTools;
	public static Item[] tinTools;
	public static Item[] bronzeTools;
	public static Item[] silverTools;
	public static Item[] leadTools;
	public static Item[] steelTools;
	public static Item[] platinumTools;
	public static Item[] titaniumTools;
	public static Item[] pigIronTools;
	public static Item[] rubyTools;
	public static Item[] harcadiumTools;
	public static final Item harcadiumArrow = new Item();
	public static final Item harcadiumBow = new Item();
	public static Item[] hellsiteTools;
	public static Item[] voidTools;
	public static final Item voidArrow = new Item();
	public static final Item voidBow = new Item();
	public static Item[] adminiumTools;
	public static Item[] chromiumTools;
	public static Item[] brassTools;
	public static Item[] entropyTools;
	public static final Item entropyBow = new Item();
	
	public static Item[] copperArmorSet;
	public static Item[] tinArmorSet;
	public static Item[] bronzeArmorSet;
	public static Item[] silverArmorSet;
	public static Item[] leadArmorSet;
	public static Item[] steelArmorSet;
	public static Item[] titaniumArmorSet;
	public static Item[] platinumArmorSet;
	public static Item[] pigIronArmorSet;
	public static Item[] rubyArmorSet;
	public static Item[] demontiumArmorSet;
	public static Item[] harcadiumArmorSet;
	public static Item[] hellsiteArmorSet;
	public static Item[] voidArmorSet;
	public static Item[] adminiumArmorSet;
	
	public static Item[] nickelArmorSet;
	public static Item[] zincArmorSet;
	public static Item[] sapphireArmorSet;
	public static Item[] onyxArmorSet;
	public static Item[] dragonArmorSet;
	public static Item[] chromiumArmorSet;
	public static Item[] brassArmorSet;
	public static Item[] entropyArmorSet;
	
	public static final Item diamondString = new Item();
	public static final Item growthSerum = new Item();
	public static final Item teleporter = new ItemTeleporter();
	public static final Item teleporter2 = new Item();
	public static final Item goodTurret = new Item();
	public static final Item goodTurret2 = new Item();
	public static final Item goodTurret3 = new Item();
	public static final Item goldenPotatoe = new Item();
	public static final Item goldenBread = new Item();
	public static final Item goldenCookie = new Item();
	public static final Item goldenMelon = new Item();
	public static final Item goldenPumpkinPie = new Item();
	public static final Item jadeiteApple = new Item();
	public static final Item jadeiteBread = new Item();
	public static final Item jadeitePotato = new Item();
	public static final Item jadeiteCookie = new Item();
	public static final Item jadeiteMelon = new Item();
	public static final Item jadeitePumpkinPie = new Item();
	public static final Item diamondApple = new Item();
	public static final Item diamondPotatoe = new Item();
	public static final Item diamondBread = new Item();
	public static final Item diamondCookie = new Item();
	public static final Item diamondMelon = new Item();
	public static final Item diamondPumpkinPie = new Item();
	public static final Item pleasantBladeSeed = new Item();
	public static final Item pleasantBladeLeaf = new Item();
	public static final Item pleasantBladeFlower = new Item();
	public static final Item pleasantBladeBrew = new Item();
	public static final Item malgrum = new Item();
	public static final Item malgrumSeeds = new Item();
	public static final Item chaff = new Item();
	public static final Item witherSkeletonSpawner = new Item();
	public static final Item eggIronGolemBetter = new Item();
	public static final Item eventSpawnItem = new Item();
	public static final Item regularTitansModSpawnEgg = new Item();
	public static final Item eggTitan = new Item();
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Item> event)
	{
		TheTitans.debug("Registering items...");
		registry = event;
		add("musket", MUSKET, TheTitans.TAB_COMBAT);
		add("power_cell", POWER_CELL, TheTitans.TAB_COMBAT);
		add("diamond_core", DIAMOND_CORE, TheTitans.TAB_ITEMS);
		add("dragon_scale", DRAGON_SCALE, TheTitans.TAB_ITEMS);
		add("end_barnicle", END_BARNICLE, TheTitans.TAB_ITEMS);
		
		add("ultima_blade", ultimaBlade, TheTitans.TAB_COMBAT);
		add("optima_axe", optimaAxe, TheTitans.TAB_COMBAT);
		add("adamantium_sword", adamantiumSword, TheTitans.TAB_COMBAT);
		add("jadeite_gem", jadeite, TheTitans.TAB_ITEMS);
		add("molten_fuel", moltenFuel, TheTitans.TAB_ITEMS);
		add("pig_iron_ingot", pigIronIngot, TheTitans.TAB_ITEMS);
		add("ruby", ruby, TheTitans.TAB_ITEMS);
		add("sapphire", sapphire, TheTitans.TAB_ITEMS);
		add("copper_ingot", copperIngot, TheTitans.TAB_ITEMS);
		add("tin_ingot", tinIngot, TheTitans.TAB_ITEMS);
		add("bronze_ingot", bronzeIngot, TheTitans.TAB_ITEMS);
		add("silver_ingot", silverIngot, TheTitans.TAB_ITEMS);
		add("lead_ingot", leadIngot, TheTitans.TAB_ITEMS);
		add("chromium_ingot", chromiumIngot, TheTitans.TAB_ITEMS);
		add("nickel_ingot", nickelIngot, TheTitans.TAB_ITEMS);
		add("steel_ingot", steelIngot, TheTitans.TAB_ITEMS);
		add("platinum_ingot", platinumIngot, TheTitans.TAB_ITEMS);
		add("titanium_ingot", titanium_ingot, TheTitans.TAB_ITEMS);
		add("demontium_ingot", demontiumIngot, TheTitans.TAB_ITEMS);
		add("harcadium", harcadium, TheTitans.TAB_ITEMS);
		add("harcadium_nugget", harcadiumNugget, TheTitans.TAB_ITEMS);
		add("harcadium_wafer", harcadiumWafer, TheTitans.TAB_ITEMS);
		add("harcadium_waflet", harcadiumWaflet, TheTitans.TAB_ITEMS);
		add("hellsite", hellsite, TheTitans.TAB_ITEMS);
		add("void", voidItem, TheTitans.TAB_ITEMS);
		add("adamantium", adamantium, TheTitans.TAB_ITEMS);
		add("sacred_sword", sacredSword, TheTitans.TAB_COMBAT);
		copperTools = addTools(toolMaterialCopper);
		tinTools = addTools(toolMaterialTin);
		bronzeTools = addTools(toolMaterialBronze);
		silverTools = addTools(toolMaterialSilver);
		leadTools = addTools(toolMaterialLead);
		steelTools = addTools(toolMaterialSteel);
		platinumTools = addTools(toolMaterialPlatinum);
		titaniumTools = addTools(toolMaterialTitanium);
		pigIronTools = addTools(toolMaterialPigIron);
		rubyTools = addTools(toolMaterialRuby);
		harcadiumTools = addTools(toolMaterialHarcadium);
		add("harcadium_arrow", harcadiumArrow, TheTitans.TAB_COMBAT);
		add("harcadium_bow", harcadiumBow, TheTitans.TAB_COMBAT);
		hellsiteTools = addTools(toolMaterialHellsite);
		voidTools = addTools(toolMaterialVoid);
		add("void_arrow", voidArrow, TheTitans.TAB_COMBAT);
		add("void_bow", voidBow, TheTitans.TAB_COMBAT);
		adminiumTools = addTools(toolMaterialAdminium);
		add("entropy_bow", entropyBow, TheTitans.TAB_COMBAT);
		
		nickelTools = addTools(toolMaterialNickel);
		zincTools = addTools(toolMaterialZinc);
		sapphireTools = addTools(toolMaterialSapphire);
		onyxTools = addTools(toolMaterialOnyx);
		dragonTools = addTools(toolMaterialDragon);
		chromiumTools = addTools(toolMaterialChromium);
		brassTools = addTools(toolMaterialBrass);
		entropyTools = addTools(toolMaterialEntropy);
		
		copperArmorSet = addArmor(armorMaterialCopper);
		tinArmorSet = addArmor(armorMaterialTin);
		bronzeArmorSet = addArmor(armorMaterialBronze);
		silverArmorSet = addArmor(armorMaterialSilver);
		leadArmorSet = addArmor(armorMaterialLead);
		steelArmorSet = addArmor(armorMaterialSteel);
		titaniumArmorSet = addArmor(armorMaterialTitanium);
		platinumArmorSet = addArmor(armorMaterialPlatinum);
		pigIronArmorSet = addArmor(armorMaterialPigIron);
		rubyArmorSet = addArmor(armorMaterialRuby);
		demontiumArmorSet = addArmor(armorMaterialDemontium);
		harcadiumArmorSet = addArmor(armorMaterialHarcadium);
		hellsiteArmorSet = addArmor(armorMaterialHellsite);
		voidArmorSet = addArmor(armorMaterialVoid);
		adminiumArmorSet = addArmor(armorMaterialAdminium);
		
		nickelArmorSet = addArmor(armorMaterialNickel);
		zincArmorSet = addArmor(armorMaterialZinc);
		sapphireArmorSet = addArmor(armorMaterialSapphire);
		onyxArmorSet = addArmor(armorMaterialOnyx);
		dragonArmorSet = addArmor(armorMaterialDragon);
		chromiumArmorSet = addArmor(armorMaterialChromium);
		brassArmorSet = addArmor(armorMaterialBrass);
		entropyArmorSet = addArmor(armorMaterialEntropy);
		
		add("diamond_string", diamondString, TheTitans.TAB_ITEMS);
		add("growth_serum", growthSerum, TheTitans.TAB_ITEMS);
		add("teleporter", teleporter, TheTitans.TAB_ITEMS);
		add("teleporter2", teleporter2, TheTitans.TAB_ITEMS);
		add("good_turret", goodTurret, TheTitans.TAB_ITEMS);
		add("good_turret2", goodTurret2, TheTitans.TAB_ITEMS);
		add("good_turret3", goodTurret3, TheTitans.TAB_ITEMS);
		add("golden_potatoe", goldenPotatoe, TheTitans.TAB_ITEMS);
		add("golden_bread", goldenBread, TheTitans.TAB_ITEMS);
		add("golden_cookie", goldenCookie, TheTitans.TAB_ITEMS);
		add("golden_melon", goldenMelon, TheTitans.TAB_ITEMS);
		add("golden_pumpkin_pie", goldenPumpkinPie, TheTitans.TAB_ITEMS);
		add("jadeite_apple", jadeiteApple, TheTitans.TAB_ITEMS);
		add("jadeite_bread", jadeiteBread, TheTitans.TAB_ITEMS);
		add("jadeite_potato", jadeitePotato, TheTitans.TAB_ITEMS);
		add("jadeite_cookie", jadeiteCookie, TheTitans.TAB_ITEMS);
		add("jadeite_melon", jadeiteMelon, TheTitans.TAB_ITEMS);
		add("jadeite_pumpkin_pie", jadeitePumpkinPie, TheTitans.TAB_ITEMS);
		add("diamond_apple", diamondApple, TheTitans.TAB_ITEMS);
		add("diamond_potatoe", diamondPotatoe, TheTitans.TAB_ITEMS);
		add("diamond_bread", diamondBread, TheTitans.TAB_ITEMS);
		add("diamond_cookie", diamondCookie, TheTitans.TAB_ITEMS);
		add("diamond_melon", diamondMelon, TheTitans.TAB_ITEMS);
		add("diamond_pumpkin_pie", diamondPumpkinPie, TheTitans.TAB_ITEMS);
		add("pleasant_blade_seed", pleasantBladeSeed, TheTitans.TAB_ITEMS);
		add("pleasant_blade_leaf", pleasantBladeLeaf, TheTitans.TAB_ITEMS);
		add("pleasant_blade_flower", pleasantBladeFlower, TheTitans.TAB_ITEMS);
		add("pleasant_blade_brew", pleasantBladeBrew, TheTitans.TAB_ITEMS);
		add("malgrum", malgrum, TheTitans.TAB_ITEMS);
		add("malgrum_seeds", malgrumSeeds, TheTitans.TAB_ITEMS);
		add("chaff", chaff, TheTitans.TAB_ITEMS);
		add("egg_titan", eggTitan, TheTitans.TAB_ITEMS);

		for (Block block : item_blocks)
			add(block.getRegistryName().getResourcePath(), new ItemBlock(block), null);
		
		registry = null;
		TheTitans.debug("Finished registering items");
	}
	
	private static Item[] addTools(ItemMaterial material)
	{
		Item[] tools = new Item[5];
		for (int i = 0; i < 5; i++)
			switch(i)
			{
				case 0:
					tools[i] = new BasePickaxe(material);
					add(material.getName() + "_pickaxe", tools[i], TheTitans.TAB_TOOLS);
					break;
				case 1:
					tools[i] = new BaseAxe(material);
					add(material.getName() + "_axe", tools[i], TheTitans.TAB_TOOLS);
					break;
				case 2:
					tools[i] = new BaseShovel(material);
					add(material.getName() + "_spade", tools[i], TheTitans.TAB_TOOLS);
					break;
				case 3:
					tools[i] = new BaseHoe(material);
					add(material.getName() + "_hoe", tools[i], TheTitans.TAB_TOOLS);
					break;
				case 4:
					tools[i] = material.getDamage() >= toolMaterialHarcadium.getDamage() ? new BaseTitanSword(material) : new BaseSword(material);
					add(material.getName() + "_sword", tools[i], TheTitans.TAB_COMBAT);
					break;
			}
		return tools;
	}
	
	private static Item[] addArmor(ItemMaterial material)
	{
		Item[] armor = new Item[4];
		for (int i = 0; i < 4; i++)
			switch(i)
			{
				case 0:
					armor[i] = new BaseArmor(material, EntityEquipmentSlot.HEAD);
					add(material.getName() + "_helmet", armor[i], TheTitans.TAB_COMBAT);
					break;
				case 1:
					armor[i] = new BaseArmor(material, EntityEquipmentSlot.CHEST);
					add(material.getName() + "_chestplate", armor[i], TheTitans.TAB_COMBAT);
					break;
				case 2:
					armor[i] = new BaseArmor(material, EntityEquipmentSlot.LEGS);
					add(material.getName() + "_leggings", armor[i], TheTitans.TAB_COMBAT);
					break;
				case 3:
					armor[i] = new BaseArmor(material, EntityEquipmentSlot.FEET);
					add(material.getName() + "_boots", armor[i], TheTitans.TAB_COMBAT);
					break;
			}
		return armor;
	}
	
	public static void add(Block block)
	{
		item_blocks.add(block);
	}
	
	private static void add(String name, Item item, CreativeTabs tab)
	{
		add(name, null, item, tab);
	}
	
	private static void add(String name, String ore_dict_name, Item item, CreativeTabs tab)
	{
		if (registry != null)
		{
			item.setRegistryName(name);
			item.setUnlocalizedName(name);
			
			if (ore_dict_name != null)
				OreDictionary.registerOre(ore_dict_name, item);

			if (tab != null && (item.getCreativeTab() == null || item instanceof ItemSword))
				item.setCreativeTab(tab);
			
			registry.getRegistry().register(item);
			
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			
			TheTitans.debug("Registered item " + item.getRegistryName().getResourceDomain() +  ":" + item.getRegistryName().getResourcePath());
			return;
		}
		TheTitans.error("Registry event returned null");
	}
}
