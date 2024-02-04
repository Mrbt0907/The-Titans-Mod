package net.minecraft.titans.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BaseCrop extends BaseBlock implements IGrowable
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
	private static boolean bonemeal;
    private static int bonemeal_value;
    
	public BaseCrop(Item seed)
	{
		this(seed, true, 5);
	}
	
	public BaseCrop(Item seed, boolean can_use_bonemeal)
	{
		this(seed, can_use_bonemeal, 5);
	}
	
	public BaseCrop(Item seed, boolean can_use_bonemeal, int bonemeal_value)
	{
		super(Material.PLANTS);
		setDefaultState(blockState.getBaseState().withProperty(getAgeProperty(), 0));
		setSoundType(SoundType.PLANT);
		setTickRandomly(true);
        disableStats();
        bonemeal = can_use_bonemeal;
        bonemeal_value = MathHelper.getInt(Block.RANDOM, 2, bonemeal_value <= 2 ? 3 : bonemeal_value);
	}

	protected int getAge(IBlockState state)
    {
        return ((Integer)state.getValue(this.getAgeProperty())).intValue();
    }
	
	protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }
	
	protected int getBonemealAgeIncrease(World worldIn)
    {
        return bonemeal_value;
    }
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CROPS_AABB[((Integer)state.getValue(this.getAgeProperty())).intValue()];
    }
	
	public IBlockState withAge(int age)
    {
        return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
    }
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) 
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		return bonemeal;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        if (i > 7)
            i = 7;
        worldIn.setBlockState(pos, this.withAge(i), 2);
	}
}
