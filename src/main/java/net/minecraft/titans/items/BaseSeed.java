package net.minecraft.titans.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.titans.TheTitans;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BaseSeed extends ItemSeeds
{
	private SoundEvent sound;
	private Block crop;
	
	public BaseSeed(Block crop, Block soil)
	{
		super(crop, soil);
		this.crop = crop;
	}
	
	public BaseSeed(Block crop, Block soil, SoundEvent sound)
	{
		super(crop, soil);
		this.crop = crop;
		this.sound = sound;
		this.setCreativeTab(TheTitans.TAB_ITEMS);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up()))
        {
            worldIn.setBlockState(pos.up(), this.crop.getDefaultState());
            if (sound != null) worldIn.playSound(null, pos, sound, SoundCategory.BLOCKS, 1, 1);
            if (player instanceof EntityPlayerMP)
            {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos.up(), itemstack);
            }

            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }
}