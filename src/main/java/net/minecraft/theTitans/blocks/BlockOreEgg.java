package net.minecraft.theTitans.blocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
public class BlockOreEgg
extends Block
{
	public BlockOreEgg(String name)
	{
		super(Material.ground);
		setHardness(0.5F);
		setResistance(1.0F);
		setStepSound(Block.soundTypeGravel);
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockName(name);
		setBlockTextureName(TheTitans.getTextures(name));
	}

	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
		int j1 = 5 + par1World.rand.nextInt(3) + par1World.rand.nextInt(3);
		if (par1World.rand.nextInt(2) == 1)
		{
			dropXpOnBlockBreak(par1World, par2, par3, par4, j1);
		}
	}

	public boolean isOpaqueCube()
	{
		return true;
	}

	public boolean renderAsNormalBlock()
	{
		return true;
	}
}


