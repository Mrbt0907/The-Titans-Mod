package net.minecraft.theTitans.blocks;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.world.IBlockAccess;
public class BlockAdamantiumOre
extends BlockNormalOre
{
	public BlockAdamantiumOre(String name)
	{
		this(null, name);
	}

	public BlockAdamantiumOre(Block base, String name)
	{
		super(base, name, 0, -1.0F, 1000000000.0F);
		setItemDropped(TitanItems.adamantium);
		setExpDrop(1000000000, 1000000000);
	}

	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		return this == TitanBlocks.adamantium_ore ? false : super.canEntityDestroy(world, x, y, z, entity);
	}
}


