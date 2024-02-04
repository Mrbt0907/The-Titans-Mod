package net.minecraft.theTitans.items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityGrowthSerum;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
public class ItemGrowthSerum extends ItemBase
{
	public ItemGrowthSerum()
	{
		super("growth_serum");
		this.maxStackSize = 16;
	}

	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		if (!playerIn.capabilities.isCreativeMode)
		{
			itemStackIn.stackSize -= 1;
		}

		worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote)
		{
			worldIn.spawnEntityInWorld(new EntityGrowthSerum(worldIn, playerIn));
		}

		playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
		return itemStackIn;
	}
}


