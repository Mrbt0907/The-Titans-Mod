package net.minecraft.theTitans.items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class ItemPigIronArmor
extends ItemNormalArmor
{
	private int ticks;
	public ItemPigIronArmor(String materialName, ArmorMaterial material, int type)
	{
		super(materialName, material, type);
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (ticks % 100 == 0)
		{
			world.playSound(player.posX, player.posY, player.posZ, "mob.pig.say", 1.0F, Maths.random(0.75F, 1.0F), false);
		}

		ticks++;
	}
}


