package net.minecraft.theTitans.items;
import net.minecraft.entity.titan.EntityHarcadiumArrow;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
public class ItemHarcadiumBow extends ItemTitanBow

{
		
	public ItemHarcadiumBow(String materialName, ToolMaterial material)
	{
		super(materialName, material, TitanItems.harcadiumArrow, EntityHarcadiumArrow.class, 2.0F);
		setAchievement(TitansAchievments.harcadiumBow);
	}

	public int getMaxItemUseDuration()
	{
		return 10;
	}

	public int getItemEnchantability()
	{
		return 30;
	}

	protected float getDamage()
	{
		return 10000000.0F;
	}
}


