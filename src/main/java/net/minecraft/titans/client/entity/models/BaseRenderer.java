package net.minecraft.titans.client.entity.models;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BaseRenderer extends TileEntityItemStackRenderer 
{
	public BaseItemModel model;
	public ResourceLocation location;
	
	public void render(ItemStack itemstack, BaseItemModel model, ResourceLocation location)
	{
		this.model = model;
		this.location = location;
		renderByItem(itemstack);
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn)
	{
		this.renderByItem(itemStackIn, 1.0F);
	}
	
	@Override
	public void renderByItem(ItemStack p_192838_1_, float partialTicks)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(location);
		model.render();
	}
}