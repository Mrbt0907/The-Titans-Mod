package net.minecraft.titans.items;

import net.minecraft.item.ItemSpade;
import net.minecraft.titans.TheTitans;

public class BaseShovel extends ItemSpade 
{
	public BaseShovel(ItemMaterial material) 
	{
		super(material.getToolMaterial());
		this.setCreativeTab(TheTitans.TAB_TOOLS);
	}
}