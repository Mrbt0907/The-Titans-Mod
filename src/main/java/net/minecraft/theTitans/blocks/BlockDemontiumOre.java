package net.minecraft.theTitans.blocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
public class BlockDemontiumOre
extends BlockNormalOre
{
	public BlockDemontiumOre(String name)
	{
		this(null, name);
	}

	public BlockDemontiumOre(Block base, String name)
	{
		super(base, name, 4, 50.0F, 2000.0F);
		setExpDrop(15, 50);
		setItemDropped(TitanItems.harcadium);
		setLightLevel(0.2F);
		setStepSound(soundTypeStone);
	}

	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		if ((entity instanceof EntityDragon))
		{
			return false;
		}

		return true;
	}

	public int tickRate(World p_149738_1_)
	{
		return 30;
	}

	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
	{
		randomDisplayTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
	{
		func_150186_m(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_);
		if (p_149734_5_.nextInt(200) == 0)
		p_149734_1_.playSound(p_149734_2_ + 0.5F, p_149734_3_ + 0.5F, p_149734_4_ + 0.5F, "thetitans:harcadiumBlockHum", 1.0F, 1.0F, false);
	}

	private void func_150186_m(World p_150186_1_, int p_150186_2_, int p_150186_3_, int p_150186_4_)
	{
		Random random = p_150186_1_.rand;
		double d0 = 0.0625D;
		for (int l = 0; l < 6; l++)
		{
			double d1 = p_150186_2_ + random.nextFloat();
			double d2 = p_150186_3_ + random.nextFloat();
			double d3 = p_150186_4_ + random.nextFloat();
			if ((l == 0) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube()))
			{
				d2 = p_150186_3_ + 1 + d0;
			}

			if ((l == 1) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube()))
			{
				d2 = p_150186_3_ + 0 - d0;
			}

			if ((l == 2) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube()))
			{
				d3 = p_150186_4_ + 1 + d0;
			}

			if ((l == 3) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube()))
			{
				d3 = p_150186_4_ + 0 - d0;
			}

			if ((l == 4) && (!p_150186_1_.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube()))
			{
				d1 = p_150186_2_ + 1 + d0;
			}

			if ((l == 5) && (!p_150186_1_.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube()))
			{
				d1 = p_150186_2_ + 0 - d0;
			}

			if ((d1 < p_150186_2_) || (d1 > p_150186_2_ + 1) || (d2 < 0.0D) || (d2 > p_150186_3_ + 1) || (d3 < p_150186_4_) || (d3 > p_150186_4_ + 1))
			{
				p_150186_1_.spawnParticle("portal", d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}


