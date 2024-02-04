package net.minecraft.theTitans.items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.world.World;
public class ItemNormalArmor
extends ItemArmor
{
	private String materialName;
	private String types[] = 
	{
		"_helmet", "_chestplate", "_leggings", "_boots"
	};
	
	public ItemNormalArmor(String materialName, ItemArmor.ArmorMaterial material, int type)
	{
		super(material, 0, type);
		this.materialName = materialName;
		materialName = materialName + types[type];
		setUnlocalizedName(materialName);
		setTextureName(TheTitans.getTextures(materialName));
		setCreativeTab(TheTitans.titansTab);
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return TheTitans.getTextures("textures/models", "armor/" + materialName + "_layer_" + (armorType == 2 ? "2" : "1") + ".png");
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (TheTitans.equippedAll(player, TitanItems.leadArmorSet))
		player.removePotionEffect(ClientProxy.creeperTitanRadiation.id);
	}
}


