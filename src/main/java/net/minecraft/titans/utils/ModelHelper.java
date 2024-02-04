package net.minecraft.titans.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.client.entity.models.BaseItemModel;
import net.minecraft.titans.client.entity.models.BaseRenderer;
import net.minecraft.util.ResourceLocation;

public class ModelHelper
{
	private static BaseRenderer renderer;
	
	public static void render(Item item, BaseItemModel model, String location)
	{
		renderer = new BaseRenderer();
		item.setTileEntityItemStackRenderer(renderer);
		renderer.render(new ItemStack(item), model, new ResourceLocation(TheTitans.MODID, location + ".png"));
	}
}
