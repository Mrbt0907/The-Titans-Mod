package net.minecraft.theTitans.world;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
public class WorldGenSkeletonTitan
extends WorldGenerator
{
	public WorldGenSkeletonTitan(Block p_i45464_1_)
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

		else if (!worldIn.isRemote && worldIn.isAirBlock(x, y, z) && worldIn.getBlock(x, y - 1, z).isOpaqueCube() && rng.nextInt(100) == 0)
		{
			EntitySkeletonTitan entityomegafish = new EntitySkeletonTitan(worldIn);
			entityomegafish.setSkeletonType(0);
			entityomegafish.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
			entityomegafish.destroyBlocksInAABBGriefingBypass(entityomegafish.boundingBox);
			entityomegafish.onSpawnWithEgg((IEntityLivingData)null);
			entityomegafish.setLocationAndAngles(x + 0.5F, y, z + 0.5F, rng.nextFloat() * 360.0F, 0.0F);
			EntityTitan otherTitan = (EntityTitan)worldIn.findNearestEntityWithinAABB(EntityTitan.class, entityomegafish.boundingBox.expand(200, 200D, 200D), entityomegafish);
			if (otherTitan != null)
			return false;
			LogManager.getLogger(TheTitans.class).info("Found a succesfully spawned Skeleton Titan at " + x + ", " + y + ", " + z + ", spawning.");
			worldIn.spawnEntityInWorld(entityomegafish);
			return true;
		}

		return false;
	}
}


