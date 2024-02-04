package net.minecraft.theTitans.items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.theTitans.TheTitans;
public class ItemNormalHoe
extends ItemHoe
{
	public ItemNormalHoe(String unlocalizedName, Item.ToolMaterial material)
	{
		super(material);
		setTextureName(TheTitans.getTextures(unlocalizedName + "_hoe"));
		setUnlocalizedName(unlocalizedName + "_hoe");
		setCreativeTab(TheTitans.titansTab);
	}
}


