package net.mrbt0907.thetitans.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseOre extends BaseBlock 
{
	public BaseOre()
	{
		super();
	}
	
	public BaseOre(float hardness) 
	{
		super(hardness);
	}
	
	public BaseOre(float hardness, float blast_resistance)
	{
		super(hardness, blast_resistance);
	}
	
	public BaseOre(float hardness, float blast_resistance, int harvest_level, int harvest_type)
	{
		super(hardness, blast_resistance, harvest_level, harvest_type);
	}
	
	public BaseOre(Material material) 
	{
		super(material);
	}
	
	public BaseOre(Material material, float hardness) 
	{
		super(material, hardness);
	}
	
	public BaseOre(Material material, float hardness, float blast_resistance)
	{
		super(material, hardness, blast_resistance);
	}
	
	public BaseOre(Material material, float hardness, float blast_resistance, int harvest_level, int harvest_type)
	{
		super(material, hardness, blast_resistance, harvest_level, harvest_type);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}