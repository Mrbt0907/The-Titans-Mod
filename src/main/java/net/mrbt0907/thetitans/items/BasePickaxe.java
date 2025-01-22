package net.mrbt0907.thetitans.items;

import net.minecraft.item.ItemPickaxe;
import net.mrbt0907.thetitans.TheTitans;

public class BasePickaxe extends ItemPickaxe 
{
	public BasePickaxe(ItemMaterial material) 
	{
		super(material.getToolMaterial());
		this.setCreativeTab(TheTitans.TAB_TOOLS);
	}
}