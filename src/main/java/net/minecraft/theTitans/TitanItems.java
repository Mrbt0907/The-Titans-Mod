package net.minecraft.theTitans;
import java.util.HashMap;
import java.util.Map;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.theTitans.items.*;
import net.minecraft.theTitans.materials.ItemMaterial;
import net.minecraftforge.oredict.OreDictionary;
import net.mrbt0907.utils.RegistryHelper.Registry;
public class TitanItems
{
	public static final Registry<Item, Boolean> itemRegistry = new Registry<Item, Boolean>(true);
	private static Map<Item, String> queuedDicts = new HashMap<Item, String>();
	public static ItemMaterial enumArmorMaterialAdminium = new ItemMaterial("AdminiumArmor", 100000000, 100000, 100000, 100000, 100000, 60);
	public static ItemMaterial enumArmorMaterialAbsence = new ItemMaterial("AbsenceArmor", 100000, 11, 17, 13, 9, 50);
	public static ItemMaterial enumArmorMaterialWithirenium = new ItemMaterial("WithireniumArmor", 50000, 10, 15, 12, 9, 40);
	public static ItemMaterial enumArmorMaterialHarcadium = new ItemMaterial("HarcadiumArmor", 20000, 9, 13, 10, 8, 30);
	public static ItemMaterial enumArmorMaterialDemontium = new ItemMaterial("DemontiumArmor", 20000, 9, 13, 10, 8, 30);
	public static ItemMaterial enumArmorMaterialPigIron = new ItemMaterial("PigIronArmor", 18, 3, 6, 5, 2, 9);
	public static ItemMaterial enumArmorMaterialRuby = new ItemMaterial("RubyArmor", 35, 4, 7, 10, 3, 10);
	public static ItemMaterial enumArmorMaterialTitanium = new ItemMaterial("TitaniumArmor", 36, 3, 8, 6, 3, 18);
	public static ItemMaterial enumArmorMaterialPlatinum = new ItemMaterial("PlatinumArmor", 36, 3, 8, 6, 3, 18);
	public static ItemMaterial enumArmorMaterialSteel = new ItemMaterial("SteelArmor", 30, 3, 8, 6, 3, 20);
	public static ItemMaterial enumArmorMaterialLead = new ItemMaterial("LeadArmor", 24, 2, 6, 4, 2, 8);
	public static ItemMaterial enumArmorMaterialSilver = new ItemMaterial("SilverArmor", 16, 2, 6, 4, 2, 25);
	public static ItemMaterial enumArmorMaterialBronze = new ItemMaterial("BronzeArmor", 12, 2, 6, 4, 2, 14);
	public static ItemMaterial enumArmorMaterialTin = new ItemMaterial("TinArmor", 6, 1, 3, 2, 1, 18);
	public static ItemMaterial enumArmorMaterialCopper = new ItemMaterial("CopperArmor", 6, 1, 3, 2, 1, 12);
	public static ItemMaterial enumToolMaterialAdminium = new ItemMaterial("AdminiumTools", Integer.MAX_VALUE - 1, 1000000000, 1000000000.0F, 799999999999999999999999999999999996.0F, 60);
	public static ItemMaterial enumToolMaterialAbsence = new ItemMaterial("AbsenceTools", 10000000, 5000000, 4800.0F, 44999999999999996.0F, 50);
	public static ItemMaterial enumToolMaterialWithirenium = new ItemMaterial("WithireniumTools", 100000, 225000, 960.0F, 314999996.0F, 40);
	public static ItemMaterial enumToolMaterialHarcadium = new ItemMaterial("HarcadiumTools", 1000, 75000, 120.0F, 2499996.0F, 30);
	public static ItemMaterial enumToolMaterialPigIron = new ItemMaterial("PigIronTools", 2, 400, 7, 3, 9);
	public static ItemMaterial enumToolMaterialRuby = new ItemMaterial("RubyTools", 2, 1675, 9, 4, 9);
	public static ItemMaterial enumToolMaterialPlatinum = new ItemMaterial("PlatinumTools", 4, 3274, 10.0F, 6.0F, 18);
	public static ItemMaterial enumToolMaterialTitanium = new ItemMaterial("TitaniumTools", 4, 3274, 10.0F, 6.0F, 18);
	public static ItemMaterial enumToolMaterialSteel = new ItemMaterial("SteelTools", 3, 1000, 8.0F, 3.0F, 12);
	public static ItemMaterial enumToolMaterialLead = new ItemMaterial("LeadTools", 2, 800, 7.0F, 2.0F, 6);
	public static ItemMaterial enumToolMaterialSilver = new ItemMaterial("SilverTools", 2, 500, 6.0F, 2.0F, 22);
	public static ItemMaterial enumToolMaterialBronze = new ItemMaterial("BronzeTools", 2, 200, 6.0F, 2.0F, 14);
	public static ItemMaterial enumToolMaterialTin = new ItemMaterial("TinTools", 1, 100, 3.0F, 1.0F, 18);
	public static ItemMaterial enumToolMaterialCopper = new ItemMaterial("CopperTools", 0, 80, 3.0F, 0.0F, 12);
	public static ItemMaterial GoodTurret = new ItemMaterial("GoodTurret", 0, 80, 0.0F, 26.0F, 50);
	public static ItemMaterial SacredSword = new ItemMaterial("SacredSword", 0, 5000, 0.0F, 36.0F, 50);
	public static Item OverlordScorpionEgg;
	public static Item MethuselahKrakenEgg;
	public static Item BurningMobzillaEgg;
	public static final Item ultimaBlade = new ItemUltimaBlade();
	public static final Item optimaAxe = new ItemOptimaAxe();
	public static final Item adamantiumSword = new ItemAdamantiumSword();
	public static final ItemBase jadeite = new ItemBase("jadeite_gem");
	public static final ItemBase moltenFuel = new ItemBase("molten_fuel");
	public static final Item pigIronIngot = new ItemBase("pig_iron_ingot");
	public static final Item ruby = new ItemBase("ruby");
	public static final Item sapphire = new ItemBase("sapphire");
	public static final Item copperIngot = new ItemBase("copper_ingot");
	public static final Item tinIngot = new ItemBase("tin_ingot");
	public static final Item bronzeIngot = new ItemBase("bronze_ingot");
	public static final Item silverIngot = new ItemBase("silver_ingot");
	public static final Item leadIngot = new ItemBase("lead_ingot");
	public static final Item chromiumIngot = new ItemBase("chromium_ingot");
	public static final Item magnesiumIngot = new ItemBase("magnesium_ingot");
	public static final Item steelIngot = new ItemBase("steel_ingot");
	public static final Item platinumIngot = new ItemBase("platinum_ingot");
	public static final Item titanium_ingot = new ItemBase("titanium_ingot");
	public static final Item demontiumIngot = new ItemBase("demontium_ingot");
	public static final Item harcadium = new ItemHarcadium("harcadium");
	public static final Item harcadiumNugget = new ItemHarcadium("harcadium_nugget");
	public static final Item harcadiumWafer = new ItemHarcadium("harcadium_wafer");
	public static final Item harcadiumWaflet = new ItemHarcadium("harcadium_waflet");
	public static final Item withirenium = new ItemWithirenium("withirenium");
	public static final Item voidItem = new ItemVoid("void");
	public static final Item adamantium = new ItemAdamantium("adamantium");
	public static final Item sacredSword = new ItemNormalSword("sacred", SacredSword.getToolMaterial());
	public static final Item copperSword = new ItemNormalSword("copper", enumToolMaterialCopper.getToolMaterial());
	public static final Item copperSpade = new ItemNormalSpade("copper", enumToolMaterialCopper.getToolMaterial());
	public static final Item copperPickaxe = new ItemNormalPickaxe("copper", enumToolMaterialCopper.getToolMaterial());
	public static final Item copperAxe = new ItemNormalAxe("copper", enumToolMaterialCopper.getToolMaterial());
	public static final Item copperHoe = new ItemNormalHoe("copper", enumToolMaterialCopper.getToolMaterial());
	public static final Item tinSword = new ItemNormalSword("tin", enumToolMaterialTin.getToolMaterial());
	public static final Item tinSpade = new ItemNormalSpade("tin", enumToolMaterialTin.getToolMaterial());
	public static final Item tinPickaxe = new ItemNormalPickaxe("tin", enumToolMaterialTin.getToolMaterial());
	public static final Item tinAxe = new ItemNormalAxe("tin", enumToolMaterialTin.getToolMaterial());
	public static final Item tinHoe = new ItemNormalHoe("tin", enumToolMaterialTin.getToolMaterial());
	public static final Item bronzeSword = new ItemNormalSword("bronze", enumToolMaterialBronze.getToolMaterial());
	public static final Item bronzeSpade = new ItemNormalSpade("bronze", enumToolMaterialBronze.getToolMaterial());
	public static final Item bronzePickaxe = new ItemNormalPickaxe("bronze", enumToolMaterialBronze.getToolMaterial());
	public static final Item bronzeAxe = new ItemNormalAxe("bronze", enumToolMaterialBronze.getToolMaterial());
	public static final Item bronzeHoe = new ItemNormalHoe("bronze", enumToolMaterialBronze.getToolMaterial());
	public static final Item silverSword = new ItemSilverSword("silver", enumToolMaterialSilver.getToolMaterial()).setDamageMultiplier(2.0F);
	public static final Item silverSpade = new ItemNormalSpade("silver", enumToolMaterialSilver.getToolMaterial());
	public static final Item silverPickaxe = new ItemNormalPickaxe("silver", enumToolMaterialSilver.getToolMaterial());
	public static final Item silverAxe = new ItemNormalAxe("silver", enumToolMaterialSilver.getToolMaterial());
	public static final Item silverHoe = new ItemNormalHoe("silver", enumToolMaterialSilver.getToolMaterial());
	public static final Item leadSword = new ItemNormalSword("lead", enumToolMaterialLead.getToolMaterial());
	public static final Item leadSpade = new ItemNormalSpade("lead", enumToolMaterialLead.getToolMaterial());
	public static final Item leadPickaxe = new ItemNormalPickaxe("lead", enumToolMaterialLead.getToolMaterial());
	public static final Item leadAxe = new ItemNormalAxe("lead", enumToolMaterialLead.getToolMaterial());
	public static final Item leadHoe = new ItemNormalHoe("lead", enumToolMaterialLead.getToolMaterial());
	public static final Item steelSword = new ItemNormalSword("steel", enumToolMaterialSteel.getToolMaterial());
	public static final Item steelSpade = new ItemNormalSpade("steel", enumToolMaterialSteel.getToolMaterial());
	public static final Item steelPickaxe = new ItemNormalPickaxe("steel", enumToolMaterialSteel.getToolMaterial());
	public static final Item steelAxe = new ItemNormalAxe("steel", enumToolMaterialSteel.getToolMaterial());
	public static final Item steelHoe = new ItemNormalHoe("steel", enumToolMaterialSteel.getToolMaterial());
	public static final Item platinumSword = new ItemNormalSword("platinum", enumToolMaterialPlatinum.getToolMaterial());
	public static final Item platinumSpade = new ItemNormalSpade("platinum", enumToolMaterialPlatinum.getToolMaterial());
	public static final Item platinumPickaxe = new ItemNormalPickaxe("platinum", enumToolMaterialPlatinum.getToolMaterial());
	public static final Item platinumAxe = new ItemNormalAxe("platinum", enumToolMaterialPlatinum.getToolMaterial());
	public static final Item platinumHoe = new ItemNormalHoe("platinum", enumToolMaterialPlatinum.getToolMaterial());
	public static final Item titaniumSword = new ItemNormalSword("titanium", enumToolMaterialTitanium.getToolMaterial());
	public static final Item titaniumSpade = new ItemNormalSpade("titanium", enumToolMaterialTitanium.getToolMaterial());
	public static final Item titaniumPickaxe = new ItemNormalPickaxe("titanium", enumToolMaterialTitanium.getToolMaterial());
	public static final Item titaniumAxe = new ItemNormalAxe("titanium", enumToolMaterialTitanium.getToolMaterial());
	public static final Item titaniumHoe = new ItemNormalHoe("titanium", enumToolMaterialTitanium.getToolMaterial());
	public static final Item pigIronSword = new ItemSilverSword("pig_iron", enumToolMaterialPigIron.getToolMaterial()).setDamageMultiplier(2.0F);
	public static final Item pigIronSpade = new ItemNormalSpade("pig_iron", enumToolMaterialPigIron.getToolMaterial());
	public static final Item pigIronPickaxe = new ItemNormalPickaxe("pig_iron", enumToolMaterialPigIron.getToolMaterial());
	public static final Item pigIronAxe = new ItemNormalAxe("pig_iron", enumToolMaterialPigIron.getToolMaterial());
	public static final Item pigIronHoe = new ItemNormalHoe("pig_iron", enumToolMaterialPigIron.getToolMaterial());
	public static final Item rubySword = new ItemNormalSword("ruby", enumToolMaterialRuby.getToolMaterial()).setDamageMultiplier(2.0F);
	public static final Item rubySpade = new ItemNormalSpade("ruby", enumToolMaterialRuby.getToolMaterial());
	public static final Item rubyPickaxe = new ItemNormalPickaxe("ruby", enumToolMaterialRuby.getToolMaterial());
	public static final Item rubyAxe = new ItemNormalAxe("ruby", enumToolMaterialRuby.getToolMaterial());
	public static final Item rubyHoe = new ItemNormalHoe("ruby", enumToolMaterialRuby.getToolMaterial());
	public static final Item harcadiumSword = new ItemHarcadiumSword("harcadium", enumToolMaterialHarcadium.getToolMaterial());
	public static final Item harcadiumArrow = new ItemHarcadiumArrow("harcadium");
	public static final Item harcadiumBow = new ItemHarcadiumBow("harcadium", enumToolMaterialHarcadium.getToolMaterial());
	public static final Item harcadiumSpade = new ItemHarcadiumSpade("harcadium", enumToolMaterialHarcadium.getToolMaterial());
	public static final Item harcadiumPickaxe = new ItemHarcadiumPickaxe("harcadium", enumToolMaterialHarcadium.getToolMaterial());
	public static final Item harcadiumAxe = new ItemHarcadiumAxe("harcadium", enumToolMaterialHarcadium.getToolMaterial());
	public static final Item harcadiumHoe = new ItemHarcadiumHoe("harcadium", enumToolMaterialHarcadium.getToolMaterial());
	public static final Item withireniumSword = new ItemWithireniumSword("withirenium", enumToolMaterialWithirenium.getToolMaterial());
	public static final Item withireniumSpade = new ItemWithireniumSpade("withirenium", enumToolMaterialWithirenium.getToolMaterial());
	public static final Item withireniumPickaxe = new ItemWithireniumPickaxe("withirenium", enumToolMaterialWithirenium.getToolMaterial());
	public static final Item withireniumAxe = new ItemWithireniumAxe("withirenium", enumToolMaterialWithirenium.getToolMaterial());
	public static final Item withireniumHoe = new ItemWithireniumHoe("withirenium", enumToolMaterialWithirenium.getToolMaterial());
	public static final Item voidArrow = new ItemVoidArrow("void");
	public static final Item voidBow = new ItemVoidBow("void", enumToolMaterialAbsence.getToolMaterial());
	public static final Item voidSword = new ItemVoidSword("void", enumToolMaterialAbsence.getToolMaterial());
	public static final Item voidSpade = new ItemVoidSpade("void", enumToolMaterialAbsence.getToolMaterial());
	public static final Item voidPickaxe = new ItemVoidPickaxe("void", enumToolMaterialAbsence.getToolMaterial());
	public static final Item voidAxe = new ItemVoidAxe("void", enumToolMaterialAbsence.getToolMaterial());
	public static final Item voidHoe = new ItemVoidHoe("void", enumToolMaterialAbsence.getToolMaterial());
	public static final Item adminiumSword = new ItemAdminiumSword("adminium", enumToolMaterialAdminium.getToolMaterial());
	public static final Item adminiumSpade = new ItemAdminiumSpade("adminium", enumToolMaterialAdminium.getToolMaterial());
	public static final Item adminiumPickaxe = new ItemAdminiumPickaxe("adminium", enumToolMaterialAdminium.getToolMaterial());
	public static final Item adminiumAxe = new ItemAdminiumAxe("adminium", enumToolMaterialAdminium.getToolMaterial());
	public static final Item adminiumHoe = new ItemAdminiumHoe("adminium", enumToolMaterialAdminium.getToolMaterial());
	public static final Item copperHelmet = new ItemNormalArmor("copper", enumArmorMaterialCopper.getArmorMaterial(), 0);
	public static final Item copperChestplate = new ItemNormalArmor("copper", enumArmorMaterialCopper.getArmorMaterial(), 1);
	public static final Item copperLeggings = new ItemNormalArmor("copper", enumArmorMaterialCopper.getArmorMaterial(), 2);
	public static final Item copperBoots = new ItemNormalArmor("copper", enumArmorMaterialCopper.getArmorMaterial(), 3);
	public static final Item tinHelmet = new ItemNormalArmor("tin", enumArmorMaterialTin.getArmorMaterial(), 0);
	public static final Item tinChestplate = new ItemNormalArmor("tin", enumArmorMaterialTin.getArmorMaterial(), 1);
	public static final Item tinLeggings = new ItemNormalArmor("tin", enumArmorMaterialTin.getArmorMaterial(), 2);
	public static final Item tinBoots = new ItemNormalArmor("tin", enumArmorMaterialTin.getArmorMaterial(), 3);
	public static final Item bronzeHelmet = new ItemNormalArmor("bronze", enumArmorMaterialBronze.getArmorMaterial(), 0);
	public static final Item bronzeChestplate = new ItemNormalArmor("bronze", enumArmorMaterialBronze.getArmorMaterial(), 1);
	public static final Item bronzeLeggings = new ItemNormalArmor("bronze", enumArmorMaterialBronze.getArmorMaterial(), 2);
	public static final Item bronzeBoots = new ItemNormalArmor("bronze", enumArmorMaterialBronze.getArmorMaterial(), 3);
	public static final Item silverHelmet = new ItemNormalArmor("silver", enumArmorMaterialSilver.getArmorMaterial(), 0);
	public static final Item silverChestplate = new ItemNormalArmor("silver", enumArmorMaterialSilver.getArmorMaterial(), 1);
	public static final Item silverLeggings = new ItemNormalArmor("silver", enumArmorMaterialSilver.getArmorMaterial(), 2);
	public static final Item silverBoots = new ItemNormalArmor("silver", enumArmorMaterialSilver.getArmorMaterial(), 3);
	public static final Item leadHelmet = new ItemNormalArmor("lead", enumArmorMaterialLead.getArmorMaterial(), 0);
	public static final Item leadChestplate = new ItemNormalArmor("lead", enumArmorMaterialLead.getArmorMaterial(), 1);
	public static final Item leadLeggings = new ItemNormalArmor("lead", enumArmorMaterialLead.getArmorMaterial(), 2);
	public static final Item leadBoots = new ItemNormalArmor("lead", enumArmorMaterialLead.getArmorMaterial(), 3);
	public static final Item steelHelmet = new ItemNormalArmor("steel", enumArmorMaterialSteel.getArmorMaterial(), 0);
	public static final Item steelChestplate = new ItemNormalArmor("steel", enumArmorMaterialSteel.getArmorMaterial(), 1);
	public static final Item steelLeggings = new ItemNormalArmor("steel", enumArmorMaterialSteel.getArmorMaterial(), 2);
	public static final Item steelBoots = new ItemNormalArmor("steel", enumArmorMaterialSteel.getArmorMaterial(), 3);
	public static final Item titaniumHelmet = new ItemNormalArmor("titanium", enumArmorMaterialTitanium.getArmorMaterial(), 0);
	public static final Item titaniumChestplate = new ItemNormalArmor("titanium", enumArmorMaterialTitanium.getArmorMaterial(), 1);
	public static final Item titaniumLeggings = new ItemNormalArmor("titanium", enumArmorMaterialTitanium.getArmorMaterial(), 2);
	public static final Item titaniumBoots = new ItemNormalArmor("titanium", enumArmorMaterialTitanium.getArmorMaterial(), 3);
	public static final Item platinumHelmet = new ItemNormalArmor("platinum", enumArmorMaterialPlatinum.getArmorMaterial(), 0);
	public static final Item platinumChestplate = new ItemNormalArmor("platinum", enumArmorMaterialPlatinum.getArmorMaterial(), 1);
	public static final Item platinumLeggings = new ItemNormalArmor("platinum", enumArmorMaterialPlatinum.getArmorMaterial(), 2);
	public static final Item platinumBoots = new ItemNormalArmor("platinum", enumArmorMaterialPlatinum.getArmorMaterial(), 3);
	public static final Item pigIronHelmet = new ItemPigIronArmor("pig_iron", enumArmorMaterialPigIron.getArmorMaterial(), 0);
	public static final Item pigIronChestplate = new ItemPigIronArmor("pig_iron", enumArmorMaterialPigIron.getArmorMaterial(), 1);
	public static final Item pigIronLeggings = new ItemPigIronArmor("pig_iron", enumArmorMaterialPigIron.getArmorMaterial(), 2);
	public static final Item pigIronBoots = new ItemPigIronArmor("pig_iron", enumArmorMaterialPigIron.getArmorMaterial(), 3);
	public static final Item rubyHelmet = new ItemNormalArmor("ruby", enumArmorMaterialRuby.getArmorMaterial(), 0);
	public static final Item rubyChestplate = new ItemNormalArmor("ruby", enumArmorMaterialRuby.getArmorMaterial(), 1);
	public static final Item rubyLeggings = new ItemNormalArmor("ruby", enumArmorMaterialRuby.getArmorMaterial(), 2);
	public static final Item rubyBoots = new ItemNormalArmor("ruby", enumArmorMaterialRuby.getArmorMaterial(), 3);
	public static final Item demontiumHelmet = new ItemDemontiumArmor("demontium", enumArmorMaterialDemontium.getArmorMaterial(), 0, 0.75F, 0.0F);
	public static final Item demontiumChestplate = new ItemDemontiumArmor("demontium", enumArmorMaterialDemontium.getArmorMaterial(), 1, 2.0F, 0.0F);
	public static final Item demontiumLeggings = new ItemDemontiumArmor("demontium", enumArmorMaterialDemontium.getArmorMaterial(), 2, 1.5F, 0.0F);
	public static final Item demontiumBoots = new ItemDemontiumArmor("demontium", enumArmorMaterialDemontium.getArmorMaterial(), 3, 0.75F, 0.0F);
	public static final Item harcadiumHelmet = new ItemHarcadiumArmor("harcadium", enumArmorMaterialHarcadium.getArmorMaterial(), 0, 2.25F, 0.1F);
	public static final Item harcadiumChestplate = new ItemHarcadiumArmor("harcadium", enumArmorMaterialHarcadium.getArmorMaterial(), 1, 6.0F, 0.1F);
	public static final Item harcadiumLeggings = new ItemHarcadiumArmor("harcadium", enumArmorMaterialHarcadium.getArmorMaterial(), 2, 4.5F, 0.1F);
	public static final Item harcadiumBoots = new ItemHarcadiumArmor("harcadium", enumArmorMaterialHarcadium.getArmorMaterial(), 3, 2.25F, 0.1F);
	public static final Item withireniumHelmet = new ItemWithireniumArmor("withirenium", enumArmorMaterialWithirenium.getArmorMaterial(), 0, 5.25F, 0.12F);
	public static final Item withireniumChestplate = new ItemWithireniumArmor("withirenium", enumArmorMaterialWithirenium.getArmorMaterial(), 1, 14.0F, 0.12F);
	public static final Item withireniumLeggings = new ItemWithireniumArmor("withirenium", enumArmorMaterialWithirenium.getArmorMaterial(), 2, 10.5F, 0.12F);
	public static final Item withireniumBoots = new ItemWithireniumArmor("withirenium", enumArmorMaterialWithirenium.getArmorMaterial(), 3, 5.25F, 0.12F);
	public static final Item voidHelmet = new ItemVoidArmor("void", enumArmorMaterialAbsence.getArmorMaterial(), 0, 15.0F, 0.2F);
	public static final Item voidChestplate = new ItemVoidArmor("void", enumArmorMaterialAbsence.getArmorMaterial(), 1, 40.0F, 0.2F);
	public static final Item voidLeggings = new ItemVoidArmor("void", enumArmorMaterialAbsence.getArmorMaterial(), 2, 30.0F, 0.2F);
	public static final Item voidBoots = new ItemVoidArmor("void", enumArmorMaterialAbsence.getArmorMaterial(), 3, 15.0F, 0.2F);
	public static final Item adminiumHelmet = new ItemAdminiumArmor("adminium", enumArmorMaterialAdminium.getArmorMaterial(), 0, 38.4F, 0.34F);
	public static final Item adminiumChestplate = new ItemAdminiumArmor("adminium", enumArmorMaterialAdminium.getArmorMaterial(), 1, 102.4F, 0.34F);
	public static final Item adminiumLeggings = new ItemAdminiumArmor("adminium", enumArmorMaterialAdminium.getArmorMaterial(), 2, 76.8F, 0.34F);
	public static final Item adminiumBoots = new ItemAdminiumArmor("adminium", enumArmorMaterialAdminium.getArmorMaterial(), 3, 38.4F, 0.34F);
	public static final Item diamondString = new ItemBase("diamond_string");
	public static final Item growthSerum = new ItemGrowthSerum();
	public static final Item teleporter = new ItemTeleporter();
	public static final Item teleporter2 = new ItemTeleporter2();
	public static final Item goodTurret = new ItemGoodTurret("turret_good", GoodTurret.getToolMaterial());
	public static final Item goodTurret2 = new ItemGoodTurretGround("machine_gun_good", GoodTurret.getToolMaterial());
	public static final Item goodTurret3 = new ItemGoodTurretMortar("mortar_good", GoodTurret.getToolMaterial());
	public static final Item goldenPotatoe = new ItemAppleGold(6, 1.2F, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("golden_potatoe").setTextureName(TheTitans.getTextures("golden_potatoe")).setCreativeTab(TheTitans.titansTab);
	public static final Item goldenBread = new ItemAppleGold(5, 1.2F, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("golden_bread").setTextureName(TheTitans.getTextures("golden_bread")).setCreativeTab(TheTitans.titansTab);
	public static final Item goldenCookie = new ItemAppleGold(2, 1.2F, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("golden_cookie").setTextureName(TheTitans.getTextures("golden_cookie")).setCreativeTab(TheTitans.titansTab);
	public static final Item goldenMelon = new ItemAppleGold(2, 1.2F, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("golden_melon").setTextureName(TheTitans.getTextures("golden_melon")).setCreativeTab(TheTitans.titansTab);
	public static final Item goldenPumpkinPie = new ItemAppleGold(8, 1.2F, false).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("golden_pumpkin_pie").setTextureName(TheTitans.getTextures("golden_pumpkin_pie")).setCreativeTab(TheTitans.titansTab);
	public static final Item jadeiteApple = new ItemAppleDiamond(4, 2.4F, false).setAlwaysEdible().setUnlocalizedName("apple_jadeite").setTextureName(TheTitans.getTextures("apple_jadeite")).setCreativeTab(CreativeTabs.tabFood);
	public static final Item jadeiteBread = new ItemAppleDiamond(8, 2.4F, false).setAlwaysEdible().setUnlocalizedName("jadeite_bread").setTextureName(TheTitans.getTextures("jadeite_bread")).setCreativeTab(TheTitans.titansTab);
	public static final Item jadeitePotato = new ItemAppleDiamond(10, 2.4F, false).setAlwaysEdible().setUnlocalizedName("jadeite_potato").setTextureName(TheTitans.getTextures("jadeite_potato")).setCreativeTab(TheTitans.titansTab);
	public static final Item jadeiteCookie = new ItemAppleDiamond(3, 2.4F, false).setAlwaysEdible().setUnlocalizedName("jadeite_cookie").setTextureName(TheTitans.getTextures("jadeite_cookie")).setCreativeTab(TheTitans.titansTab);
	public static final Item jadeiteMelon = new ItemAppleDiamond(3, 2.4F, false).setAlwaysEdible().setUnlocalizedName("jadeite_melon").setTextureName(TheTitans.getTextures("jadeite_melon")).setCreativeTab(TheTitans.titansTab);
	public static final Item jadeitePumpkinPie = new ItemAppleDiamond(16, 2.4F, false).setAlwaysEdible().setUnlocalizedName("jadeite_pumpkin_pie").setTextureName(TheTitans.getTextures("jadeite_pumpkin_pie")).setCreativeTab(TheTitans.titansTab);
	public static final Item diamondApple = new ItemAppleDiamond(4, 2.4F, false).setAlwaysEdible().setUnlocalizedName("apple_diamond").setTextureName(TheTitans.getTextures("apple_diamond")).setCreativeTab(CreativeTabs.tabFood);
	public static final Item diamondPotatoe = new ItemAppleDiamond(6, 2.4F, false).setAlwaysEdible().setUnlocalizedName("diamond_potatoe").setTextureName(TheTitans.getTextures("diamond_potatoe")).setCreativeTab(TheTitans.titansTab);
	public static final Item diamondBread = new ItemAppleDiamond(5, 2.4F, false).setAlwaysEdible().setUnlocalizedName("diamond_bread").setTextureName(TheTitans.getTextures("diamond_bread")).setCreativeTab(TheTitans.titansTab);
	public static final Item diamondCookie = new ItemAppleDiamond(2, 2.4F, false).setAlwaysEdible().setUnlocalizedName("diamond_cookie").setTextureName(TheTitans.getTextures("diamond_cookie")).setCreativeTab(TheTitans.titansTab);
	public static final Item diamondMelon = new ItemAppleDiamond(2, 2.4F, false).setAlwaysEdible().setUnlocalizedName("diamond_melon").setTextureName(TheTitans.getTextures("diamond_melon")).setCreativeTab(TheTitans.titansTab);
	public static final Item diamondPumpkinPie = new ItemAppleDiamond(8, 2.4F, false).setAlwaysEdible().setUnlocalizedName("diamond_pumpkin_pie").setTextureName(TheTitans.getTextures("diamond_pumpkin_pie")).setCreativeTab(TheTitans.titansTab);
	public static final Item pleasantBladeSeed = new ItemPleasantBladeSeed();
	public static final Item pleasantBladeLeaf = new ItemBase("pleasant_blade_leaf");
	public static final Item pleasantBladeFlower = new ItemPleasantBladeFlower(2, 1.2F, false);
	public static final Item pleasantBladeBrew = new ItemPleasantBladeBrew();
	public static final Item malgrum = new ItemFoodMalgrum(20, 20F, false).setAlwaysEdible().setUnlocalizedName("malgrum_fruit").setTextureName(TheTitans.getTextures("malgrum_fruit")).setCreativeTab(TheTitans.titansTab);
	public static final Item malgrumSeeds = new ItemMalgrumSeeds();
	public static final Item chaff = new ItemChaff();
	public static final Item witherSkeletonSpawner = new ItemForTheChallengeGames1();
	public static final Item eggIronGolemBetter = new ItemEggReinforcedIronGolem();
	public static final Item eventSpawnItem = new ItemEventSpawner();
	public static final Item regularTitansModSpawnEgg = new ItemSpawnEggRegular();
	public static final Item eggSnowGolemTitan = new ItemTitanSpawnEgg("spawn_egg_snow_golem_titan");
	public static final Item eggSlimeTitan = new ItemTitanSpawnEgg("spawn_egg_slime_titan");
	public static final Item eggMagmaCubeTitan = new ItemTitanSpawnEgg("spawn_egg_magma_cube_titan");
	public static final Item eggOmegafish = new ItemTitanSpawnEgg("spawn_egg_omegafish");
	public static final Item eggCaveSpiderTitan = new ItemTitanSpawnEgg("spawn_egg_cave_spider_titan");
	public static final Item eggSpiderTitan = new ItemTitanSpawnEgg("spawn_egg_spider_titan");
	public static final Item eggSpiderJockeyTitan = new ItemTitanSpawnEgg("spawn_egg_spider_jockey_titan");
	public static final Item eggZombieTitan = new ItemTitanSpawnEgg("spawn_egg_zombie_titan");
	public static final Item eggSkeletonTitan = new ItemTitanSpawnEgg("spawn_egg_skeleton_titan");
	public static final Item eggCreeperTitan = new ItemTitanSpawnEgg("spawn_egg_creeper_titan");
	public static final Item eggChargedCreeperTitan = new ItemTitanSpawnEgg("spawn_egg_charged_creeper_titan");
	public static final Item eggZombiePigmanTitan = new ItemTitanSpawnEgg("spawn_egg_zombie_pigman_titan");
	public static final Item eggBlazeTitan = new ItemTitanSpawnEgg("spawn_egg_blaze_titan");
	public static final Item eggWitherSkeletonTitan = new ItemTitanSpawnEgg("spawn_egg_wither_skeleton_titan");
	public static final Item eggWitherJockeyTitan = new ItemTitanSpawnEgg("spawn_egg_wither_jockey_titan");
	public static final Item eggGargoyleKing = new ItemTitanSpawnEgg("spawn_egg_gargoyle_king");
	public static final Item eggGhastTitan = new ItemTitanSpawnEgg("spawn_egg_ghast_titan");
	public static final Item eggEnderColossus = new ItemTitanSpawnEgg("spawn_egg_ender_colossus");
	public static final Item eggUltimaIronGolemTitan = new ItemTitanSpawnEgg("spawn_egg_iron_golem_titan");
	public static final Item eggWitherzilla = new ItemTitanSpawnEgg("spawn_egg_witherzilla");
	
	public static final Item[] copperArmorSet = new Item [] {copperHelmet, copperChestplate, copperLeggings, copperBoots};
	public static final Item[] tinArmorSet = new Item [] {tinHelmet, tinChestplate, tinLeggings, tinBoots};
	public static final Item[] bronzeArmorSet = new Item [] {bronzeHelmet, bronzeChestplate, bronzeLeggings, bronzeBoots};
	public static final Item[] silverArmorSet = new Item [] {silverHelmet, silverChestplate, silverLeggings, silverBoots};
	public static final Item[] leadArmorSet = new Item [] {leadHelmet, leadChestplate, leadLeggings, leadBoots};
	public static final Item[] steelArmorSet = new Item [] {steelHelmet, steelChestplate, steelLeggings, steelBoots};
	public static final Item[] titaniumArmorSet = new Item [] {titaniumHelmet, titaniumChestplate, titaniumLeggings, titaniumBoots};
	public static final Item[] platinumArmorSet = new Item [] {platinumHelmet, platinumChestplate, platinumLeggings, platinumBoots};
	public static final Item[] pigIronArmorSet = new Item [] {pigIronHelmet, pigIronChestplate, pigIronLeggings, pigIronBoots};
	public static final Item[] rubyArmorSet = new Item [] {rubyHelmet, rubyChestplate, rubyLeggings, rubyBoots};
	public static final Item[] demontiumArmorSet = new Item [] {demontiumHelmet, demontiumChestplate, demontiumLeggings, demontiumBoots};
	public static final Item[] harcadiumArmorSet = new Item [] {harcadiumHelmet, harcadiumChestplate, harcadiumLeggings, harcadiumBoots};
	public static final Item[] withireniumArmorSet = new Item [] {withireniumHelmet, withireniumChestplate, withireniumLeggings, withireniumBoots};
	public static final Item[] voidArmorSet = new Item [] {voidHelmet, voidChestplate, voidLeggings, voidBoots};
	public static final Item[] adminiumArmorSet = new Item [] {adminiumHelmet, adminiumChestplate, adminiumLeggings, adminiumBoots};
	public static final Item[] spawnEggs = new Item []
	{
		eggSnowGolemTitan,
		eggSlimeTitan,
		eggMagmaCubeTitan,
		eggOmegafish,
		eggCaveSpiderTitan,
		eggSpiderTitan,
		eggSpiderJockeyTitan,
		eggZombieTitan,
		eggSkeletonTitan,
		eggCreeperTitan,
		eggChargedCreeperTitan,
		eggZombiePigmanTitan,
		eggBlazeTitan,
		eggWitherSkeletonTitan,
		eggWitherJockeyTitan,
		eggGargoyleKing,
		eggGhastTitan,
		eggEnderColossus,
		eggUltimaIronGolemTitan,
		eggWitherzilla
	};
	
	public static void setup()
	{
		register(ultimaBlade);
		register(optimaAxe);
		register(adamantiumSword);
		register(sacredSword);
		register(harcadiumArrow);
		register(harcadiumBow);
		register(voidArrow);
		register(voidBow);
		register(pleasantBladeSeed);
		register(pleasantBladeLeaf);
		register(pleasantBladeFlower);
		register(pleasantBladeBrew);
		register("gemJadeite", jadeite);
		register("moltenFuel", moltenFuel);
		register("ingotPigIron", pigIronIngot);
		register("gemSapphire", sapphire);
		register("gemRuby", ruby);
		register("ingotCopper", copperIngot);
		register("ingotTin", tinIngot);
		register("ingotBronze", bronzeIngot);
		register("ingotSilver", silverIngot);
		register("ingotLead", leadIngot);
		register("ingotChromium", chromiumIngot);
		register("ingotMagnesium", magnesiumIngot);
		register("ingotSteel", steelIngot);
		register("ingotPlatinum", platinumIngot);
		register("ingotTitanium", titanium_ingot);
		register("ingotDemontium", demontiumIngot);
		register("harcadium", harcadium);
		register("nuggetHarcadium", harcadiumNugget);
		register("waferHarcadium", harcadiumWafer);
		register("wafletHarcadium", harcadiumWaflet);
		register("withirenium", withirenium);
		register("void", voidItem);
		register("adamantium", adamantium);
		register(copperSword);
		register(copperSpade);
		register(copperPickaxe);
		register(copperAxe);
		register(tinSword);
		register(tinSpade);
		register(tinPickaxe);
		register(tinAxe);
		register(bronzeSword);
		register(bronzeSpade);
		register(bronzePickaxe);
		register(bronzeAxe);
		register(silverSword);
		register(silverSpade);
		register(silverPickaxe);
		register(silverAxe);
		register(leadSword);
		register(leadSpade);
		register(leadPickaxe);
		register(leadAxe);
		register(steelSword);
		register(steelSpade);
		register(steelPickaxe);
		register(steelAxe);
		register(platinumSword);
		register(platinumSpade);
		register(platinumPickaxe);
		register(platinumAxe);
		register(titaniumSword);
		register(titaniumSpade);
		register(titaniumPickaxe);
		register(titaniumAxe);
		register(pigIronSword);
		register(pigIronSpade);
		register(pigIronPickaxe);
		register(pigIronAxe);
		register(rubySword);
		register(rubySpade);
		register(rubyPickaxe);
		register(rubyAxe);
		register(harcadiumSword);
		register(harcadiumSpade);
		register(harcadiumPickaxe);
		register(harcadiumAxe);
		register(withireniumSword);
		register(withireniumSpade);
		register(withireniumPickaxe);
		register(withireniumAxe);
		register(voidSword);
		register(voidSpade);
		register(voidPickaxe);
		register(voidAxe);
		register(adminiumSword);
		register(adminiumSpade);
		register(adminiumPickaxe);
		register(adminiumAxe);
		register(copperHoe);
		register(tinHoe);
		register(bronzeHoe);
		register(silverHoe);
		register(leadHoe);
		register(steelHoe);
		register(platinumHoe);
		register(titaniumHoe);
		register(pigIronHoe);
		register(rubyHoe);
		register(harcadiumHoe);
		register(withireniumHoe);
		register(voidHoe);
		register(adminiumHoe);
		register(copperArmorSet);
		register(tinArmorSet);
		register(bronzeArmorSet);
		register(silverArmorSet);
		register(leadArmorSet);
		register(steelArmorSet);
		register(titaniumArmorSet);
		register(platinumArmorSet);
		register(pigIronArmorSet);
		register(rubyArmorSet);
		register(demontiumArmorSet);
		register(harcadiumArmorSet);
		register(withireniumArmorSet);
		register(voidArmorSet);
		register(adminiumArmorSet);
		register(diamondString);
		register(growthSerum);
		register(teleporter);
		register(teleporter2);
		register(goodTurret);
		register(goodTurret2);
		register(goodTurret3);
		register("food", goldenPotatoe);
		register("food", goldenBread);
		register("food", goldenCookie);
		register("food", goldenMelon);
		register("food", goldenPumpkinPie);
		register("food", diamondApple);
		register("food", diamondPotatoe);
		register("food", diamondBread);
		register("food", diamondCookie);
		register("food", diamondMelon);
		register("food", diamondPumpkinPie);
		register("food", jadeiteApple);
		register("food", jadeitePotato);
		register("food", jadeiteBread);
		register("food", jadeiteCookie);
		register("food", jadeiteMelon);
		register("food", jadeitePumpkinPie);
		register("food", malgrum);
		register("seed", malgrumSeeds);
		register(chaff);
		register(witherSkeletonSpawner);
		register(eggIronGolemBetter);
		register(eventSpawnItem);
		register(regularTitansModSpawnEgg);
		register(spawnEggs);
		if (Loader.isModLoaded("OreSpawn"))
		{
			OverlordScorpionEgg = new ItemSpawnEggTitans("eggoverlordscorpion", "OverlordScorpion");
			MethuselahKrakenEgg = new ItemSpawnEggTitans("eggelderkraken", "MethuselahKraken");
			BurningMobzillaEgg = new ItemSpawnEggTitans("egggodzillaburning", "BurningMobzilla");
			register(OverlordScorpionEgg);
			register(MethuselahKrakenEgg);
			register(BurningMobzillaEgg);
		}

		enumToolMaterialCopper.getToolMaterial().setRepairItem(new ItemStack(copperIngot));
		enumArmorMaterialCopper.getArmorMaterial().customCraftingMaterial = copperIngot;
		enumToolMaterialTin.getToolMaterial().setRepairItem(new ItemStack(tinIngot));
		enumArmorMaterialTin.getArmorMaterial().customCraftingMaterial = tinIngot;
		enumToolMaterialBronze.getToolMaterial().setRepairItem(new ItemStack(bronzeIngot));
		enumArmorMaterialBronze.getArmorMaterial().customCraftingMaterial = bronzeIngot;
		enumToolMaterialSteel.getToolMaterial().setRepairItem(new ItemStack(steelIngot));
		enumArmorMaterialSteel.getArmorMaterial().customCraftingMaterial = steelIngot;
		enumToolMaterialHarcadium.getToolMaterial().setRepairItem(new ItemStack(harcadium));
		enumArmorMaterialHarcadium.getArmorMaterial().customCraftingMaterial = harcadium;
		enumToolMaterialAbsence.getToolMaterial().setRepairItem(new ItemStack(voidItem));
		enumArmorMaterialAbsence.getArmorMaterial().customCraftingMaterial = voidItem;
	}

	public static void preInit()
	{
		processRegistered();
		moltenFuel.setBurnTime(2000);
	}

	public static void init()
	{
		processQueued();
		TheTitans.theFuel.add(moltenFuel);
	}

	public static void postInit()
	{
		for(Map.Entry<Item, Boolean> entry : itemRegistry.keySet())
		if(entry.getKey().getItemStackDisplayName(new ItemStack(entry.getKey())).equalsIgnoreCase(entry.getKey().getUnlocalizedName() + ".name"))
		TheTitans.logger.addError("Localization key returned null: " + entry.getKey().getUnlocalizedName() + ".name=");
	}

	private static void processRegistered()
	{
		TitanConfig.load();
		int id = 0;
		while (id < itemRegistry.size())
		{
			for (Map.Entry<Object[], Boolean> entry : itemRegistry.entrySet())
			if (id == (int)entry.getKey()[0])
			{
				if (entry.getValue())
				{
					GameRegistry.registerItem((Item)entry.getKey()[1], ((Item)entry.getKey()[1]).getUnlocalizedName().substring(5));
					id++;
					break;
				}

				else
				{
					if (queuedDicts.containsKey((Item)entry.getKey()[1]))
					queuedDicts.remove((Item)entry.getKey()[1]);
					id++;
					break;
				}
			}
		}
	}

	private static void processQueued()
	{
		for (Map.Entry<Item, String> entry : queuedDicts.entrySet())
		OreDictionary.registerOre(entry.getValue(), entry.getKey());
		queuedDicts.clear();
	}

	private static void oreDict(String oreDictionary, Item... items)
	{
		for (Item item : items)
		if (item != null)
		OreDictionary.registerOre(oreDictionary, item);
	}

	private static void register(String oreDictionary, Item... items)
	{
		register(items);
		for (Item item : items)
		if (item != null)
		queuedDicts.put(item, oreDictionary);
	}

	private static void register(Item... items)
	{
		for (Item item : items)
		if (item != null)
		itemRegistry.add(item);
	}

	public static boolean exists(Item item)
	{
		return itemRegistry.contains(item) ? Item.itemRegistry.containsKey(TheTitans.MODID + ":" + item.getUnlocalizedName().substring(5)) : true;
	}
}


