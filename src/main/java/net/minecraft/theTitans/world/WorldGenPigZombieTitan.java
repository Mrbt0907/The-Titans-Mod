package net.minecraft.theTitans.world;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.init.Blocks;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
public class WorldGenPigZombieTitan
extends WorldGenerator
{
	public WorldGenPigZombieTitan(Block p_i45464_1_)
	{
	}

	public boolean generate(World worldIn, Random rng, int x, int y, int z)
	{
		if (!worldIn.getBlock(x, y - 1, z).isOpaqueCube())
		{
			--y;
		}

		else if (!worldIn.isRemote && worldIn.isAirBlock(x, y, z) && worldIn.getBlock(x, y - 1, z).isOpaqueCube() && rng.nextInt(100) == 0)
		{
			EntityPigZombieTitan entityomegafish = new EntityPigZombieTitan(worldIn);
			entityomegafish.destroyBlocksInAABBGriefingBypass(entityomegafish.boundingBox);
			entityomegafish.onSpawnWithEgg((IEntityLivingData)null);
			entityomegafish.setLocationAndAngles(x + 0.5F, y + 5, z + 0.5F, rng.nextFloat() * 360.0F, 0.0F);
			EntityTitan otherTitan = (EntityTitan)worldIn.findNearestEntityWithinAABB(EntityTitan.class, entityomegafish.boundingBox.expand(200, 200D, 200D), entityomegafish);
			if (otherTitan != null)
			return false;
			worldIn.spawnEntityInWorld(entityomegafish);
			LogManager.getLogger(TheTitans.class).info("Found a succesfully spawned Zombie Pigman Titan at " + x + ", " + y + ", " + z + ", spawning.");
			generateGold(worldIn, x, y + 4, z, 5, 5);
			generateGold(worldIn, x, y + 3, z, 6, 6);
			generateGold(worldIn, x, y + 2, z, 7, 7);
			generateGold(worldIn, x, y + 1, z, 8, 8);
			generateGold(worldIn, x, y, z, 9, 9);
			return true;
		}

		return false;
	}

	private void generateGold(World worldIn, int x, int y, int z, int diax, int diaz)
	{
		for (int l1 = x - diax; l1 <= x + diax; ++l1)
		{
			for (int i2 = z - diaz; i2 <= z + diaz; ++i2)
			{
				if (!worldIn.getBlock(l1, y, i2).isOpaqueCube())
				{
					worldIn.setBlock(l1, y, i2, Blocks.gold_block, 0, 1);
				}
			}
		}
	}
}


