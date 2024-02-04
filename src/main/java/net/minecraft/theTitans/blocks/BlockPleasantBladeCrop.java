package net.minecraft.theTitans.blocks;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.world.World;
public class BlockPleasantBladeCrop extends BlockCrops
{
	public BlockPleasantBladeCrop()
	{
		super();
		this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
		setBlockName("pleasant_blade");
		setBlockTextureName(TheTitans.getTextures("pleasant_blade"));
	}

	protected Item func_149866_i()
	{
		return TitanItems.pleasantBladeSeed;
	}

	protected Item func_149865_P()
	{
		return TitanItems.pleasantBladeFlower;
	}

	public int getRenderType()
	{
		return 1;
	}

	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
	{
		int l = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
		if (l >= 7 && p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) <= 7)
		{
			--l;
			p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, l, 3);
			this.checkAndDropBlock(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
		}

		else
		super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
	}

	/**
	* Called upon block activation (right click on the block.)
	*/
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		this.func_150036_b(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_);
		return super.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
	}

	/**
	* Called when a player hits the block. Args: world, x, y, z, player
	*/
	public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_)
	{
		this.func_150036_b(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
		super.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
	}

	private void func_150036_b(World p_150036_1_, int p_150036_2_, int p_150036_3_, int p_150036_4_, EntityPlayer p_150036_5_)
	{
		int l = p_150036_1_.getBlockMetadata(p_150036_2_, p_150036_3_, p_150036_4_);
		if (l >= 7)
		{
			p_150036_5_.swingItem();
			if (!p_150036_1_.isRemote)
			{
				EntityItem entityitem = new EntityItem(p_150036_1_, p_150036_5_.posX, p_150036_5_.posY + 1, p_150036_5_.posZ, new ItemStack(func_149865_P()));
				p_150036_1_.spawnEntityInWorld(entityitem);
				p_150036_1_.setBlockMetadataWithNotify(p_150036_2_, p_150036_3_, p_150036_4_, 6, 2);
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);
		if (metadata >= 6)
		{
			for (int i = 0; i < 1 + world.rand.nextInt(3) + fortune; ++i)
			{
				ret.add(new ItemStack(TitanItems.pleasantBladeLeaf, 1, 0));
			}
		}

		if (metadata >= 7)
		ret.add(new ItemStack(TitanItems.pleasantBladeSeed, 1, 0));
		return ret;
	}
}


