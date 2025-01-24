package net.mrbt0907.thetitans.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mrbt0907.util.item.ItemSwordEX;

/*
 * TODO:
 * - Titan Weapons have a special right click function
 * - Left clicking creates a visual slash to indicate the range of the blade
 * - Landing hits and getting kills builds a meter which when maxed unleashes a special ability when right clicked
 * 
 * > Demontium Titan-Rending Blade
 *   - Passive Ability: 
 *   - Ability: 
 *   - Ability: 
 *   - Special Ability: 
 * > Hardacium Titan-Rending Blade
 *   - Passive Ability: Ultra-Teleportation - Automatically teleports the user away from most attacks
 *   - Ability: Mirage - Send your blade into a phasing mode. Attacks can now be made anywhere at any distance and each attack sends down a barrage of blade slashes
 *   - Ability: Vanishing Blade - Teleport to the location pointed at while leaving behind a barrage of slashes behind you
 *   - Special Ability: 
 * > Hellsite Titan-Rending Blade
 *   - Passive Ability: Hellfire Aura - Holding the blade grants the user a fiery aura that burns any mob nearby for insane damage 
 *   - Ability: 
 *   - Ability: Supernova - Cast a huge explosion of hellfire at a point looked at
 *   - Special Ability: - Unleash a torrent of chaotic energy in a wide area in front of you
 * > Void Titan-Rending Blade
 *   - Passive Ability: 
 *   - Ability: 
 *   - Ability: 
 *   - Special Ability: Absolute Collapse - Unleash a gigantic aura of void energy, collapsing space time around you to instantly vanquish enemies around you.
 * > Adminium Titan-Rending Blade
 *   - Passive Ability: 
 *   - Ability: 
 *   - Ability: 
 *   - Special Ability: 
 * > Adamantium Titan-Rending Blade
 *   - Passive Ability: Piercing - Attacks completely ignore armor and titan armor
 *   - Ability: 
 *   - Ability: Shapeshift - Transforms into other tools
 *   - Special Ability:
 * */
public class AbstractTitanWeapon extends ItemSwordEX
{
	public AbstractTitanWeapon(ToolMaterial material, double extendedAttackReach)
	{
		super(material, extendedAttackReach);
	}

	@Override
	public boolean onAttackPre(ItemStack stack, World world, EntityPlayer attacker, Entity victim, int victimIndex, boolean shouldCancel)
	{
		return shouldCancel;
	}

	@Override
	public boolean onAttack(ItemStack stack, World world, EntityPlayer attacker, Entity victim, int victimIndex)
	{
		return true;
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
