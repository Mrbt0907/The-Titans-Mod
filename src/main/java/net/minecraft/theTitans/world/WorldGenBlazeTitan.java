package net.minecraft.theTitans.world;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityBlazeTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
@SuppressWarnings("deprecation")
public class WorldGenBlazeTitan
extends WorldGenerator
{
	public WorldGenBlazeTitan(Block p_i45464_1_)
	{
	}

	public boolean generate(World worldIn, Random rng, int x, int y, int z)
	{
		if (!worldIn.getBlock(x, y - 1, z).isOpaqueCube())
		{
			--y;
		}

		else if (!worldIn.isRemote && worldIn.isAirBlock(x, y, z) && worldIn.getBlock(x, y - 1, z).isOpaqueCube() && rng.nextInt(300) == 0)
		{
			EntityBlazeTitan entityomegafish = new EntityBlazeTitan(worldIn);
			entityomegafish.destroyBlocksInAABBGriefingBypass(entityomegafish.boundingBox);
			entityomegafish.onSpawnWithEgg((IEntityLivingData)null);
			entityomegafish.setLocationAndAngles(x + 0.5F, y, z + 0.5F, rng.nextFloat() * 360.0F, 0.0F);
			EntityTitan otherTitan = (EntityTitan)worldIn.findNearestEntityWithinAABB(EntityTitan.class, entityomegafish.boundingBox.expand(200, 200D, 200D), entityomegafish);
			if (otherTitan != null)
			return false;
			EntityPlayer registeredplayer = (EntityPlayer)worldIn.playerEntities;
			if (!worldIn.playerEntities.isEmpty() && registeredplayer != null && entityomegafish.getDistanceSq(registeredplayer.getBedLocation().posX, registeredplayer.getBedLocation().posY, registeredplayer.getBedLocation().posZ) <= 10000D)
			return false;
			worldIn.spawnEntityInWorld(entityomegafish);
			LogManager.getLogger(TheTitans.class).info("Found a succesfully spawned Blaze Titan at " + x + ", " + y + ", " + z + ", spawning.");
			return true;
		}

		return false;
	}
}


