package net.mrbt0907.thetitans.items;

import net.minecraft.item.ItemSpade;
import net.mrbt0907.thetitans.TheTitans;

public class BaseShovel extends ItemSpade 
{
	public BaseShovel(ItemMaterial material) 
	{
		super(material.getToolMaterial());
		this.setCreativeTab(TheTitans.TAB_TOOLS);
	}
}