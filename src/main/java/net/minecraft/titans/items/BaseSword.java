package net.minecraft.titans.items;

import net.minecraft.item.ItemSword;

public class BaseSword extends ItemSword 
{	
	public BaseSword(ItemMaterial material) 
	{
		super(material.getToolMaterial());
	}
}