package net.minecraft.theTitans.items;
import net.minecraft.entity.titan.EntityHarcadiumArrow;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
public class ItemVoidBow extends ItemTitanBow
{
	public ItemVoidBow(String materialName, ToolMaterial material)
	{
		super(materialName, material, TitanItems.voidArrow, EntityHarcadiumArrow.class, 3.0F);
		setAchievement(TitansAchievments.voidBow);
	}

	public int getMaxItemUseDuration()
	{
		return 5;
	}

	public int getItemEnchantability()
	{
		return 30;
	}

	protected float getDamage()
	{
		return 100000000.0F;
	}
}


