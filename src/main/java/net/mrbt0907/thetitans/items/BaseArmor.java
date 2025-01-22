package net.mrbt0907.thetitans.items;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.mrbt0907.thetitans.TheTitans;

public class BaseArmor extends ItemArmor {

	public BaseArmor(ItemMaterial material, EntityEquipmentSlot equipment_slot) 
	{
		super(material.getArmorMaterial(), 0, equipment_slot);
		this.setCreativeTab(TheTitans.TAB_COMBAT);
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
		return TheTitans.MODID + ":textures/armor/" + (slot.equals(EntityEquipmentSlot.LEGS) ? "overlay/" : "") + getArmorMaterial().getName().toLowerCase() + ".png";
    }
}
