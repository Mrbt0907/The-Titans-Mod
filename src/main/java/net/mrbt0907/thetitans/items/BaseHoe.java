package net.mrbt0907.thetitans.items;

import net.minecraft.item.ItemHoe;
import net.mrbt0907.thetitans.TheTitans;

public class BaseHoe extends ItemHoe {
	public BaseHoe(ItemMaterial material) 
	{
		super(material.getToolMaterial());
		this.setCreativeTab(TheTitans.TAB_TOOLS);
	}
}