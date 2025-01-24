package net.mrbt0907.thetitans.capability;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.items.AbstractTitanWeapon;
import net.mrbt0907.util.MrbtAPI;
import net.mrbt0907.util.capabilities.CapabilityCrossbow;
import net.mrbt0907.util.item.ItemCrossbow;

public class CapabilityHandler
{
	public static final ResourceLocation CROSSBOW = new ResourceLocation(TheTitans.MODID, "titan_weapon");
	
	@SubscribeEvent
	public static void attach(AttachCapabilitiesEvent<ItemStack> event)
	{
		ItemStack stack = event.getObject();
		Item item = stack.getItem();
		if (stack.getItem() instanceof AbstractTitanWeapon)
			event.addCapability(CROSSBOW, new CapabilitySpecial.Provider());
	}
}