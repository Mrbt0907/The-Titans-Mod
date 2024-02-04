package net.minecraft.theTitans.world;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import net.minecraft.entity.titan.EntitySpiderTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
public class WorldGenSpiderJockeyTitan
extends WorldGenerator
{
	public WorldGenSpiderJockeyTitan(Block p_i45464_1_)
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

		else if (!worldIn.isRemote && worldIn.isAirBlock(x, y, z) && worldIn.getBlock(x, y - 1, z).isOpaqueCube() && rng.nextInt(1000) == 0)
		{
			EntitySkeletonTitan entityomegafish = new EntitySkeletonTitan(worldIn);
			entityomegafish.setSkeletonType(0);
			entityomegafish.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
			entityomegafish.destroyBlocksInAABBGriefingBypass(entityomegafish.boundingBox);
			entityomegafish.onSpawnWithEgg((IEntityLivingData)null);
			entityomegafish.setLocationAndAngles(x + 0.5F, y, z + 0.5F, rng.nextFloat() * 360.0F, 0.0F);
			EntitySpiderTitan entityomegafish1 = new EntitySpiderTitan(worldIn);
			entityomegafish1.destroyBlocksInAABBGriefingBypass(entityomegafish1.boundingBox);
			entityomegafish1.copyLocationAndAnglesFrom(entityomegafish);
			entityomegafish1.onSpawnWithEgg((IEntityLivingData)null);
			EntityTitan otherTitan = (EntityTitan)worldIn.findNearestEntityWithinAABB(EntityTitan.class, entityomegafish.boundingBox.expand(200, 200D, 200D), entityomegafish);
			if (otherTitan != null)
			return false;
			worldIn.spawnEntityInWorld(entityomegafish);
			worldIn.spawnEntityInWorld(entityomegafish1);
			LogManager.getLogger(TheTitans.class).info("Found a succesfully spawned Spider Jockey Titan at " + x + ", " + y + ", " + z + ", spawning.");
			entityomegafish.mountEntity(entityomegafish1);
			return true;
		}

		return false;
	}
}


