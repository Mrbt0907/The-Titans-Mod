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
	public static final ItemMaterial toolMaterialOmni = new ItemMaterial("omni", Integer.MAX_VALUE, Integer.MAX_VALUE, Float.MAX_VALUE, 10000000.0F, 256);
	public static final ItemMaterial toolMaterialAdamantium = new ItemMaterial("adamantium", Integer.MAX_VALUE - 1, Integer.MAX_VALUE, 100000000000000.0F, 2500000.0F, 100);
	public static final ItemMaterial toolMaterialAdminium = new ItemMaterial("adminium", Integer.MAX_VALUE - 2, 1000000000, 1000000000.0F, 999996.0F, 60);
	public static final ItemMaterial toolMaterialVoid = new ItemMaterial("void", Integer.MAX_VALUE - 25, 5000000, 4800.0F, 249996.0F, 50);
	public static final ItemMaterial toolMaterialTitanus = new ItemMaterial("titanus", 20, 5000000, 4800.0F, 119996.0F, 50);
	public static final ItemMaterial toolMaterialHellsite = new ItemMaterial("hellsite", 19, 225000, 960.0F, 34996.0F, 40);
	public static final ItemMaterial toolMaterialHarcadium = new ItemMaterial("harcadium", 17, 75000, 120.0F, 14996.0F, 30);
	
	public static final Item MUSKET = new Item();
	public static final Item POWER_CELL = new Item();
	public static final Item DIAMOND_CORE = new Item();
	public static final Item DRAGON_SCALE = new Item();
	public static final Item END_BARNICLE = new Item();
	
	public static final Item ultimaBlade = new BaseTitanSword(toolMaterialOmni);
	public static final Item optimaAxe = new BaseTitanSword(toolMaterialOmni);
	public static final Item adamantiumSword = new BaseTitanSword(toolMaterialAdamantium);
	public static final Item demontiumIngot = new Item();
	public static final Item harcadium = new Item();
	public static final Item harcadiumNugget = new Item();
	public static final Item harcadiumWafer = new Item();
	public static final Item harcadiumWaflet = new Item();
	public static final Item hellsite = new Item();
	public static final Item voidItem = new Item();
	public static final Item adamantium = new Item();
	public static Item[] harcadiumTools;
	public static final Item harcadiumArrow = new Item();
	public static final Item harcadiumBow = new Item();
	public static Item[] hellsiteTools;
	public static Item[] voidTools;
	public static final Item voidArrow = new Item();
	public static final Item voidBow = new Item();
	public static Item[] adminiumTools;
	
	public static Item[] demontiumArmorSet;
	public static Item[] harcadiumArmorSet;
	public static Item[] hellsiteArmorSet;
	public static Item[] voidArmorSet;
	public static Item[] adminiumArmorSet;
	
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
		add("demontium_ingot", demontiumIngot, TheTitans.TAB_ITEMS);
		add("harcadium", harcadium, TheTitans.TAB_ITEMS);
		add("harcadium_nugget", harcadiumNugget, TheTitans.TAB_ITEMS);
		add("harcadium_wafer", harcadiumWafer, TheTitans.TAB_ITEMS);
		add("harcadium_waflet", harcadiumWaflet, TheTitans.TAB_ITEMS);
		add("hellsite", hellsite, TheTitans.TAB_ITEMS);
		add("void", voidItem, TheTitans.TAB_ITEMS);
		add("adamantium", adamantium, TheTitans.TAB_ITEMS);
		harcadiumTools = addTools(toolMaterialHarcadium);
		add("harcadium_arrow", harcadiumArrow, TheTitans.TAB_COMBAT);
		add("harcadium_bow", harcadiumBow, TheTitans.TAB_COMBAT);
		hellsiteTools = addTools(toolMaterialHellsite);
		voidTools = addTools(toolMaterialVoid);
		add("void_arrow", voidArrow, TheTitans.TAB_COMBAT);
		add("void_bow", voidBow, TheTitans.TAB_COMBAT);
		adminiumTools = addTools(toolMaterialAdminium);
		
		demontiumArmorSet = addArmor(armorMaterialDemontium);
		harcadiumArmorSet = addArmor(armorMaterialHarcadium);
		hellsiteArmorSet = addArmor(armorMaterialHellsite);
		voidArmorSet = addArmor(armorMaterialVoid);
		adminiumArmorSet = addArmor(armorMaterialAdminium);
		
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
