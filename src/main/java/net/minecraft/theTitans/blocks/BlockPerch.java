package net.minecraft.theTitans.blocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
public class BlockPerch extends BlockDragonEgg
{
	public BlockPerch(String blockname, String texture, float hardness, float resistance)
	{
		super();
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
		setHardness(hardness);
		setResistance(resistance);
		setStepSound(soundTypePiston);
		setBlockName(blockname);
		setBlockTextureName(texture);
		setCreativeTab(TheTitans.titansTab);
	}

	/**
	* Called whenever the block is added into the world. Args: world, x, y, z
	*/
	public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
	{
		 
	}


	/**
	* Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	* their own) Args: x, y, z, neighbor Block
	*/
	public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
	{
		 
	}


	/**
	* Ticks the block if it's been scheduled
	*/
	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
	{
		 
	}


	/**
	* Called upon block activation (right click on the block.)
	*/
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		return true;
	}

	/**
	* Called when a player hits the block. Args: world, x, y, z, player
	*/
	public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_)
	{
		 
	}


	/**
	* How many world ticks before ticking
	*/
	public int tickRate(World p_149738_1_)
	{
		return 10;
	}

	/**
	* Is this block (a) opaque and (b) a full 1m cube?This determines whether or not to render the shared face of two
	* adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	*/
	public boolean isOpaqueCube()
	{
		return false;
	}

	/**
	* If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	*/
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
	{
		int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int i1 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_) >> 2;
		++l;
		l %= 4;
		if (l == 0)
		{
			p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2 | i1 << 2, 2);
		}

		if (l == 1)
		{
			p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3 | i1 << 2, 2);
		}

		if (l == 2)
		{
			p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0 | i1 << 2, 2);
		}

		if (l == 3)
		{
			p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 1 | i1 << 2, 2);
		}
	}

	/**
	* Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	* coordinates.Args: blockAccess, x, y, z, side
	*/
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
	{
		return true;
	}

	public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
	{
		return super.canBlockStay(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
	}

	/**
	* The type of render function that is called for this block
	*/
	public int getRenderType()
	{
		return 27;
	}

	/**
	* Gets an item for the block being called on. Args: world, x, y, z
	*/
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	{
		return Item.getItemById(0);
	}
}


