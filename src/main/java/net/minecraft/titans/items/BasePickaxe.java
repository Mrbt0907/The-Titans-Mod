package net.minecraft.titans.items;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.titans.TheTitans;

public class BasePickaxe extends ItemPickaxe 
{
	public BasePickaxe(ItemMaterial material) 
	{
		super(material.getToolMaterial());
		this.setCreativeTab(TheTitans.TAB_TOOLS);
	}
}