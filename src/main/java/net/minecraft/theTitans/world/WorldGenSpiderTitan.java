package net.minecraft.theTitans.world;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.titan.EntitySpiderTitan;
import net.minecraft.init.Blocks;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
public class WorldGenSpiderTitan
extends WorldGenerator
{
	public WorldGenSpiderTitan(Block p_i45464_1_)
	{
	}

	public boolean generate(World worldIn, Random rng, int x, int y, int z)
	{
		if (!worldIn.getBlock(x, y - 1, z).isOpaqueCube())
		{
			--y;
		}

		else if (worldIn.getBlock(x, y, z).isOpaqueCube())
		{
			++y;
		}

		else if (!worldIn.isRemote && worldIn.isAirBlock(x, y, z) && worldIn.getBlock(x, y - 1, z).isOpaqueCube() && rng.nextInt(30) == 0)
		{
			EntitySpiderTitan entityomegafish = new EntitySpiderTitan(worldIn);
			entityomegafish.destroyBlocksInAABBGriefingBypass(entityomegafish.boundingBox);
			entityomegafish.onSpawnWithEgg((IEntityLivingData)null);
			entityomegafish.setLocationAndAngles(x + 0.5F, y, z + 0.5F, rng.nextFloat() * 360.0F, 0.0F);
			worldIn.spawnEntityInWorld(entityomegafish);
			LogManager.getLogger(TheTitans.class).info("Found a succesfully spawned Spider Titan at " + x + ", " + y + ", " + z + ", spawning.");
			this.generateSpidersWeb(worldIn, x, y, z);
			return true;
		}

		return false;
	}

	private void generateSpidersWeb(World worldIn, int x, int y, int z)
	{
		for (int l1 = x - 11; l1 <= x + 11; ++l1)
		{
			for (int j1 = y - 11; j1 <= y; ++j1)
			{
				for (int i2 = z - 11; i2 <= z + 11; ++i2)
				{
					if (worldIn.getBlock(l1, j1, i2).getBlockHardness(worldIn, l1, j1, i2) != -1F)
					{
						worldIn.setBlock(l1, j1, i2, Blocks.web, 0, 2);
					}
				}
			}
		}
	}
}


