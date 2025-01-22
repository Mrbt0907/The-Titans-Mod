package net.mrbt0907.thetitans.items;

import java.util.function.Predicate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mrbt0907.util.item.ItemBowEX;

public class AbstractTitanBow extends ItemBowEX
{
	public AbstractTitanBow(Predicate<ItemStack> arrowType, int enchantability)
	{
		super(arrowType, enchantability);
	}

	@Override
	public void onStartUse(ItemStack stack, World world, EntityPlayer shooter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTickUse(ItemStack stack, World world, EntityPlayer shooter, int timeLeft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopUse(ItemStack stack, World world, EntityPlayer shooter, int timeLeft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShootPre(ItemStack stack, World world, EntityPlayer shooter, int timeLeft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShoot(ItemStack stack, World world, EntityPlayer shooter, EntityArrow arrow, int timeLeft,
			int arrowIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShootPost(ItemStack stack, World world, EntityPlayer shooter, int timeLeft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShootFail(ItemStack stack, World world, EntityPlayer shooter, int timeLeft) {
		// TODO Auto-generated method stub
		
	}
}
