package net.minecraft.theTitans.world;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
public class WorldGenVein extends WorldGenerator
{
	private Block ore;
	/** The number of blocks to generate. */
	private int amount;
	private final List<Block> bases = new ArrayList<Block>();
	private int mineableBlockMeta;
	public WorldGenVein(Block ore, int amount)
	{
		this(ore, amount, Blocks.stone);
	}

	public WorldGenVein(Block ore, int amount, Block... bases)
	{
		this.ore = ore;
		this.amount = amount;
		for (Block block : bases)
		this.bases.add(block);
	}

	public WorldGenVein(Block block, int meta, int amount, Block... bases)
	{
		this(block, amount, bases);
		mineableBlockMeta = meta;
	}

	public Block getOre()
	{
		return ore;
	}

	public boolean generate(World worldObj, Random rand, int posX, int posY, int posZ)
	{
		float f = rand.nextFloat() * (float)Math.PI;
		double d0 = (double)((float)(posX + 8) + MathHelper.sin(f) * (float)amount / 8.0F);
		double d1 = (double)((float)(posX + 8) - MathHelper.sin(f) * (float)amount / 8.0F);
		double d2 = (double)((float)(posZ + 8) + MathHelper.cos(f) * (float)amount / 8.0F);
		double d3 = (double)((float)(posZ + 8) - MathHelper.cos(f) * (float)amount / 8.0F);
		double d4 = (double)(posY + rand.nextInt(3) - 2);
		double d5 = (double)(posY + rand.nextInt(3) - 2);
		for (int l = 0; l <= amount; ++l)
		{
			double d6 = d0 + (d1 - d0) * (double)l / (double)amount;
			double d7 = d4 + (d5 - d4) * (double)l / (double)amount;
			double d8 = d2 + (d3 - d2) * (double)l / (double)amount;
			double d9 = rand.nextDouble() * (double)amount / 16.0D;
			double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)amount) + 1.0F) * d9 + 1.0D;
			double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)amount) + 1.0F) * d9 + 1.0D;
			int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
			int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
			int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
			int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
			int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);
			for (int k2 = i1; k2 <= l1; ++k2)
			{
				double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2.0D);
				if (d12 * d12 < 1.0D)
				for (int l2 = j1; l2 <= i2; ++l2)
				{
					double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2.0D);
					if (d12 * d12 + d13 * d13 < 1.0D)
					for (int i3 = k1; i3 <= j2; ++i3)
					{
						double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2.0D);
						for (Block base : bases)
						if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && worldObj.getBlock(k2, l2, i3).isReplaceableOreGen(worldObj, k2, l2, i3, base))
						{
							worldObj.setBlock(k2, l2, i3, ore, mineableBlockMeta, 2);
							break;
						}
					}
				}
			}
		}

		return true;
	}
}


