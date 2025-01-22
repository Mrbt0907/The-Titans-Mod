package net.mrbt0907.thetitans.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.util.util.math.Maths;

public class BaseBlock extends Block 
{
	private Item drop;
	private int count;
	private float chance;
	private int[] exp;
	
	public BaseBlock() 
	{
		this(Material.ROCK, 3.0F, 5.0F, 0, 0);
	}
	
	public BaseBlock(float hardness) 
	{
		this(Material.ROCK, hardness, 5.0F, 0, 0);
	}
	
	public BaseBlock(float hardness, float blast_resistance)
	{
		this(Material.ROCK, hardness, blast_resistance, 0, 0);
	}
	
	public BaseBlock(float hardness, float blast_resistance, int harvest_level, int harvest_type)
	{
		this(Material.ROCK, hardness, blast_resistance, harvest_level, harvest_type);
	}
	
	public BaseBlock(Material material) 
	{
		this(material, 3.0F, 5.0F, 0, 0);
	}
	
	public BaseBlock(Material material, float hardness) 
	{
		this(material, hardness, 5.0F, 0, 0);
	}
	
	public BaseBlock(Material material, float hardness, float blast_resistance)
	{
		this(material, hardness, blast_resistance, 0, 0);
	}
	
	public BaseBlock(Material material, float hardness, float blast_resistance, int harvest_level, int harvest_type)
	{
		super(material);
		String tool;
		
		switch (harvest_type)
		{
			case 1:
				tool = "axe";
			case 2:
				tool = "shovel";
			case 3:
				tool = "sword";
			default:
				tool = "pickaxe";
		}

		setResistance(blast_resistance / 3);
		setHardness(hardness);
		setHarvestLevel(tool, harvest_level);
		this.setCreativeTab(TheTitans.TAB_BLOCKS);
		
		if (material == Material.GROUND)
			this.setSoundType(SoundType.GROUND);
		if (material == Material.GRASS)
			this.setSoundType(SoundType.PLANT);
		if (material == Material.WOOD)
			this.setSoundType(SoundType.WOOD);
		if (material == Material.ROCK)
			this.setSoundType(SoundType.STONE);
		if (material == Material.IRON)
			this.setSoundType(SoundType.METAL);
		
		chance = 1.0F;
	}
	
	public BaseBlock setItemDropped(Item drop, int drop_count, float drop_perc, int min_drop_exp, int max_drop_exp)
	{
		if (this.drop == null)
		{
			this.drop = drop;
			count = drop_count;
			chance = drop_perc / 10.0f;
			exp = new int[] {min_drop_exp, max_drop_exp};
		}
		
		return this;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return drop != null ? drop : super.getItemDropped(state, rand, fortune);
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		int count = 1;
		Maths.updateRandom(random);
		count = Math.max(1, Maths.random(1, this.count));
		Maths.updateRandom();
		return count;
	}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(worldIn, pos, state, this.chance, fortune);
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		if (exp != null && this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
			return MathHelper.getInt(rand, exp[0], exp[1]);
		return 0;
	}
}