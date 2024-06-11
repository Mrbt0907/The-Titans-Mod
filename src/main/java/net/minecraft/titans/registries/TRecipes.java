package net.minecraft.titans.registries;

import net.endermanofdoom.mac.registry.AbstractRecipeRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.titans.TheTitans;

public class TRecipes extends AbstractRecipeRegistry
{
	public static final TRecipes INSTANCE = new TRecipes(TheTitans.MODID);
	
	private TRecipes(String modid)
	{
		super(modid);
	}

	@Override
	public void init() {}
	
	@Override
	public void register()
	{
		addShapedRecipe(TBlocks.harcadium_ore[0], "hb_to_hi", "000,000,000", TItems.harcadium);
		addShapelessRecipe(TItems.harcadium, 9, "hi_to_hb", TBlocks.harcadium_ore[0]);
		addShapedRecipe(TItems.harcadium, "hi_to_hn", "000,000,000", TItems.harcadiumNugget);
		addShapelessRecipe(TItems.harcadiumNugget, 9, "hn_to_hi", TItems.harcadium);
		addShapedRecipe(TItems.harcadiumNugget, "hn_to_hw", "000,000,000", TItems.harcadiumWafer);
		addShapelessRecipe(TItems.harcadiumWafer, 9, "hw_to_hn", TItems.harcadiumNugget);
		addShapedRecipe(TItems.harcadiumWafer, "hw_to_hwl", "000,000,000", TItems.harcadiumWaflet);
		addShapelessRecipe(TItems.harcadiumWaflet, 9, "hwl_to_hw", TItems.harcadiumWafer);

		String material = "harcadium";
		Item item = TItems.harcadium;
		
		addShapedRecipe(TItems.harcadiumTools[0], material + "_pickaxe", "000, 1 , 1 ", item, Items.DIAMOND);
		addShapedRecipe(TItems.harcadiumTools[1], material + "_axe", "00,01, 1", item, Items.DIAMOND);
		addShapedRecipe(TItems.harcadiumTools[2], material + "_shovel", "0,1,1", item, Items.DIAMOND);
		addShapedRecipe(TItems.harcadiumTools[3], material + "_hoe", "00, 1, 1", item, Items.DIAMOND);
		addShapedRecipe(TItems.harcadiumTools[4], material + "_sword", "0,0,1", item, Items.DIAMOND);
		addShapedRecipe(TItems.harcadiumArmorSet[0], material + "_helmet", "000,0 0", item);
		addShapedRecipe(TItems.harcadiumArmorSet[1], material + "_chestplate", "0 0,000,000", item);
		addShapedRecipe(TItems.harcadiumArmorSet[2], material + "_leggings", "000,0 0,0 0", item);
		addShapedRecipe(TItems.harcadiumArmorSet[3], material + "_boots", "0 0,0 0", item);
	}
}
