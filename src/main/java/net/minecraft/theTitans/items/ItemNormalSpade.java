package net.minecraft.theTitans.items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.theTitans.TheTitans;
public class ItemNormalSpade
extends ItemSpade
{
	public ItemNormalSpade(String unlocalizedName, Item.ToolMaterial material)
	{
		super(material);
		setTextureName(TheTitans.getTextures(unlocalizedName + "_spade"));
		setUnlocalizedName(unlocalizedName + "_spade");
		setCreativeTab(TheTitans.titansTab);
	}
}


