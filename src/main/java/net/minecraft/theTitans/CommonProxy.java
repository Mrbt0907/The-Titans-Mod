package net.minecraft.theTitans;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.potion.Potion;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
public class CommonProxy
{
	public static final Potion electricJudgment = new PotionElectricJudgement(29, true, 14270531).setIconIndex(2, 0).setPotionName("potion.electricJudgment");
	public static final Potion creeperTitanRadiation = new PotionRadiation(30, true, 28165).setIconIndex(1, 0).setPotionName("potion.radiation");
	public static final Potion advancedWither = new PotionAdvancedWither(31, true, 0).setIconIndex(0, 0).setPotionName("potion.advancedWither");
	public static final Potion death = new PotionDeath(28, true, 0).setIconIndex(0, 0).setPotionName("potion.death");
	public void registerRenders() 
	{
		 
	}


	public void preInit(FMLPreInitializationEvent e)
	{
		ChunkLoadingEvent.init();
		TitanItems.setup();
		TitanBlocks.setup();
		TitanItems.preInit();
		TitanBlocks.preInit();
		RenderTheTitans.setup();
		RenderTheTitans.preInit();
		registerItemRenderers();
		registerRenderThings();
		CraftingRecipes.initCrafting();
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(TitanItems.pleasantBladeSeed, 0, 2, 4, 10));
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(TitanItems.malgrumSeeds, 0, 1, 1, 1));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(TitanItems.pleasantBladeSeed, 0, 1, 4, 10));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(TitanItems.pleasantBladeSeed, 0, 1, 4, 10));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(TitanItems.malgrumSeeds, 0, 1, 1, 1));
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(TitanItems.malgrumSeeds, 0, 1, 1, 1));
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(TitanItems.pleasantBladeSeed, 0, 1, 4, 10));
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(TitanItems.pleasantBladeSeed, 0, 1, 4, 10));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(TitanItems.pleasantBladeSeed, 0, 2, 4, 10));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(TitanItems.pleasantBladeLeaf, 0, 2, 4, 10));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(TitanItems.pleasantBladeFlower, 0, 1, 1, 2));
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(TitanItems.pleasantBladeBrew, 0, 1, 1, 2));
	}

	public void init(FMLInitializationEvent e)
	{
		TitanItems.init();
		TitanBlocks.init();
	}

	public void postInit(FMLPostInitializationEvent e)
	{
		TitanItems.postInit();
		TitanBlocks.postInit();
	}

	public void registerItemRenderers() 
	{

	}


	public void registerRenderThings() 
	{

	}
}


