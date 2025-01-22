package net.mrbt0907.thetitans.items;

import net.minecraft.item.ItemFood;
import net.mrbt0907.thetitans.TheTitans;

public class BaseFood extends ItemFood {
	public BaseFood(int hunger, float saturation, boolean isWolfFood) 
	{
		super(hunger, saturation, isWolfFood);
		this.setCreativeTab(TheTitans.TAB_ITEMS);
	}	
}