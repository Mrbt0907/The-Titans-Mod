package net.mrbt0907.thetitans.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BaseResource extends BaseBlock 
{
	public BaseResource() 
	{
		super(Material.IRON, 3.0F, 5.0F, 2, 0);
	}
	
	public BaseResource(Material material) 
	{
		super(material, 3.0F, 5.0F, 2, 0);
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
	{
		return true;
	}
}
