package net.minecraft.titans.items;

import net.minecraft.item.ItemHoe;
import net.minecraft.titans.TheTitans;

public class BaseHoe extends ItemHoe {
	public BaseHoe(ItemMaterial material) 
	{
		super(material.getToolMaterial());
		this.setCreativeTab(TheTitans.TAB_TOOLS);
	}
}