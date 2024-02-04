package net.minecraft.theTitans.items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.theTitans.TheTitans;
public class ItemNormalAxe
extends ItemAxe
{
	public ItemNormalAxe(String unlocalizedName, Item.ToolMaterial material)
	{
		super(material);
		setTextureName(TheTitans.getTextures(unlocalizedName + "_axe"));
		setUnlocalizedName(unlocalizedName + "_axe");
		setCreativeTab(TheTitans.titansTab);
	}
}


