package net.minecraft.theTitans.items;
import net.minecraft.theTitans.TitansAchievments;
public class ItemVoidArrow extends ItemBase
{
	public ItemVoidArrow(String materialName)
	{
		super(materialName + "_arrow");
		setAchievement(TitansAchievments.voidArrows);
	}
}


