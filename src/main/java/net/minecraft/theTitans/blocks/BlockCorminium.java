package net.minecraft.theTitans.blocks;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
public class BlockCorminium
extends Block
{
	private boolean broke; 
	public BlockCorminium(Material materialIn, String name)
	{
		super(materialIn);
		broke = false;
		setHarvestLevel("pickaxe", Integer.MAX_VALUE - 1);
		setBlockUnbreakable();
		setResistance(Float.MAX_VALUE);
		setCreativeTab(TheTitans.titansTab);
		setBlockName(name);
		setBlockTextureName(TheTitans.getTextures(name));
		setStepSound(soundTypeStone);
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		return false;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return null;
	}

	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
	{
		if (!broke)
		{
			p_149749_1_.setBlock(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
			List<?> list = p_149749_1_.loadedEntityList;
			if (list != null && !list.isEmpty())
			for (int i = 0 ; i < list.size(); i++)
			if (list.get(i) instanceof EntityItem)
			{
				EntityItem entity = (EntityItem)list.get(i);
				if (entity.getEntityItem() != null && entity.getEntityItem().getItem() != null && entity.getEntityItem().getItem() == Item.getItemFromBlock(this))
				entity.isDead = true;
			}
		}

		else
		broke = false;
	}

	public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_)
	{
		EntityPlayer player = p_149664_1_.getClosestPlayer(p_149664_2_, p_149664_3_, p_149664_4_, 10.0D);
		if (player != null && player.capabilities.isCreativeMode)
		broke = true;
		else
		broke = false;
	}

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return false;
	}
}


