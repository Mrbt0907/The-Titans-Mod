package net.minecraft.theTitans.items;
import net.minecraft.theTitans.TitansAchievments;
public class ItemHarcadiumArrow extends ItemBase
{
	public ItemHarcadiumArrow(String materialName)
	{
		super(materialName + "_arrow");
		setAchievement(TitansAchievments.harcadiumArrows);
	}
}


