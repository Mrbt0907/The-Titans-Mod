package net.minecraft.theTitans.blocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.IBlockAccess;
public class BlockCompactBedrock
extends Block
{
	public BlockCompactBedrock(Material materialIn, String name)
	{
		super(materialIn);
		setHarvestLevel("pickaxe", Integer.MAX_VALUE - 1);
		setHardness(9000000000.0F);
		setResistance(180000000.0F);
		setCreativeTab(TheTitans.titansTab);
		setBlockName(name);
		setBlockTextureName(TheTitans.getTextures(name));
		setStepSound(soundTypeStone);
	}

	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		return false;
	}
}


