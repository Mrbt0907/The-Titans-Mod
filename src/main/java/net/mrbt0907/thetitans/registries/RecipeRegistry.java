package net.mrbt0907.thetitans.registries;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.util.registry.AbstractRecipeRegistry;

public class RecipeRegistry extends AbstractRecipeRegistry
{
	public static final RecipeRegistry INSTANCE = new RecipeRegistry(TheTitans.MODID);
	
	private RecipeRegistry(String modid)
	{
		super(modid);
	}

	@Override
	public void init() {}
	
	@Override
	public void register()
	{
		addShapedRecipe(BlockRegistry.harcadium_ore[0], "hb_to_hi", "000,000,000", ItemRegistry.harcadium);
		addShapelessRecipe(ItemRegistry.harcadium, 9, "hi_to_hb", BlockRegistry.harcadium_ore[0]);
		addShapedRecipe(ItemRegistry.harcadium, "hi_to_hn", "000,000,000", ItemRegistry.harcadiumNugget);
		addShapelessRecipe(ItemRegistry.harcadiumNugget, 9, "hn_to_hi", ItemRegistry.harcadium);
		addShapedRecipe(ItemRegistry.harcadiumNugget, "hn_to_hw", "000,000,000", ItemRegistry.harcadiumWafer);
		addShapelessRecipe(ItemRegistry.harcadiumWafer, 9, "hw_to_hn", ItemRegistry.harcadiumNugget);
		addShapedRecipe(ItemRegistry.harcadiumWafer, "hw_to_hwl", "000,000,000", ItemRegistry.harcadiumWaflet);
		addShapelessRecipe(ItemRegistry.harcadiumWaflet, 9, "hwl_to_hw", ItemRegistry.harcadiumWafer);

		String material = "harcadium";
		Item item = ItemRegistry.harcadium;
		
		addShapedRecipe(ItemRegistry.harcadiumTools[0], material + "_pickaxe", "000, 1 , 1 ", item, Items.DIAMOND);
		addShapedRecipe(ItemRegistry.harcadiumTools[1], material + "_axe", "00,01, 1", item, Items.DIAMOND);
		addShapedRecipe(ItemRegistry.harcadiumTools[2], material + "_shovel", "0,1,1", item, Items.DIAMOND);
		addShapedRecipe(ItemRegistry.harcadiumTools[3], material + "_hoe", "00, 1, 1", item, Items.DIAMOND);
		addShapedRecipe(ItemRegistry.harcadiumTools[4], material + "_sword", "0,0,1", item, Items.DIAMOND);
		addShapedRecipe(ItemRegistry.harcadiumArmorSet[0], material + "_helmet", "000,0 0", item);
		addShapedRecipe(ItemRegistry.harcadiumArmorSet[1], material + "_chestplate", "0 0,000,000", item);
		addShapedRecipe(ItemRegistry.harcadiumArmorSet[2], material + "_leggings", "000,0 0,0 0", item);
		addShapedRecipe(ItemRegistry.harcadiumArmorSet[3], material + "_boots", "0 0,0 0", item);
	}
}
