package net.minecraft.theTitans.items;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
public class ItemMalgrumSeeds extends ItemBase implements IPlantable
{
	public ItemMalgrumSeeds()
	{
		super("malgrum_seeds");
	}

	/**
	* Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	* True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	*/
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if (p_77648_7_ != 1)
		{
			return false;
		}

		else if (p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_))
		{
			if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_).canSustainPlant(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, ForgeDirection.UP, this) && p_77648_3_.isAirBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_))
			{
				p_77648_3_.playSound(p_77648_4_, p_77648_5_, p_77648_6_, "thetitans:titanPress", 0.5F, 1F, false);
				p_77648_3_.setBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_, TitanBlocks.malgrumCrop, 0, 3);
				--p_77648_1_.stackSize;
				return true;
			}

			else
			{
				return false;
			}
		}

		else
		{
			return false;
		}
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
	{
		return EnumPlantType.Crop;
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z)
	{
		return TitanBlocks.malgrumCrop;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
	{
		return 0;
	}
}


