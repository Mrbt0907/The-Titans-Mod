package net.minecraft.theTitans.items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.theTitans.TheTitans;
public class ItemNormalPickaxe
extends ItemPickaxe
{
	public ItemNormalPickaxe(String unlocalizedName, Item.ToolMaterial material)
	{
		super(material);
		setTextureName(TheTitans.getTextures(unlocalizedName + "_pickaxe"));
		setUnlocalizedName(unlocalizedName + "_pickaxe");
		setCreativeTab(TheTitans.titansTab);
	}
}


