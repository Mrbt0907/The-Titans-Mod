package net.minecraft.titans.items;

import net.minecraft.item.ItemFood;
import net.minecraft.titans.TheTitans;

public class BaseFood extends ItemFood {
	public BaseFood(int hunger, float saturation, boolean isWolfFood) 
	{
		super(hunger, saturation, isWolfFood);
		this.setCreativeTab(TheTitans.TAB_ITEMS);
	}	
}