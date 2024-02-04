package net.minecraft.theTitans.blocks;
import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.world.World;
public class BlockMalgrumCrop extends BlockCrops
{
	public BlockMalgrumCrop()
	{
		super();
		setBlockName("malgrum");
		setBlockTextureName(TheTitans.getTextures("malgrum"));
		this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
	}

	protected Item func_149866_i()
	{
		return TitanItems.malgrumSeeds;
	}

	protected Item func_149865_P()
	{
		return TitanItems.malgrum;
	}

	public int getRenderType()
	{
		return 1;
	}

	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
	{
		super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
		int l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
		if (l < 7)
		{
			if (p_149674_5_.nextInt(2 * (1 + l)) == 0)
			{
				++l;
				p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, l, 2);
			}
		}
	}
}


