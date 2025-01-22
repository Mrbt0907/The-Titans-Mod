package net.mrbt0907.thetitans.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mrbt0907.util.item.ItemSwordEX;

public class AbstractTitanWeapon extends ItemSwordEX
{
	public AbstractTitanWeapon(ToolMaterial material, double extendedAttackReach)
	{
		super(material, extendedAttackReach);
	}

	@Override
	public boolean onAttackPre(ItemStack stack, World world, EntityPlayer attacker, Entity victim, int victimIndex, boolean shouldCancel)
	{
		return false;
	}

	@Override
	public boolean onAttack(ItemStack stack, World world, EntityPlayer attacker, Entity victim, int victimIndex)
	{
		return false;
	}

	@Override
	public void onSwing(ItemStack stack, World world, EntityPlayer attacker) {}

	@Override
	public void onStartUse(ItemStack stack, World world, EntityPlayer attacker) {}

	@Override
	public void onTickUse(ItemStack stack, World world, EntityPlayer attacker, int timeLeft) {}

	@Override
	public void onStopUse(ItemStack stack, World world, EntityPlayer attacker, int timeLeft) {}
}
