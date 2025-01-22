package net.mrbt0907.thetitans.items;

import net.minecraft.item.ItemAxe;
import net.mrbt0907.thetitans.TheTitans;

public class BaseAxe extends ItemAxe
{
	public BaseAxe(ItemMaterial material) 
	{
		this(material, 0.0F);
	}
	public BaseAxe(ItemMaterial material, float extraSpeed) 
	{
		super(material.getToolMaterial(), material.getDamage() + 6.0F, -3.0F + extraSpeed);
		this.setCreativeTab(TheTitans.TAB_TOOLS);
	}
}