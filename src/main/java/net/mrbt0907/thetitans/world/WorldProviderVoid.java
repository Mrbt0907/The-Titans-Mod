package net.mrbt0907.thetitans.world;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.entity.god.EntityWitherzilla;
import net.mrbt0907.thetitans.registries.BiomeRegistry;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
public class WorldProviderVoid extends WorldProvider
{
	@Override
	protected void init()
    {
		hasSkyLight = true;
		biomeProvider = new BiomeProviderSingle(BiomeRegistry.BIOME_VOID);
    }
	
	@Override
	public DimensionType getDimensionType()
	{
		return TheTitans.DIMENSION_VOID;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkProviderVoid(world, world.getSeed());
	}
	
	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}
	
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
		return 0.5F;
    }
	
	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
    {
		return world.getGroundAboveSeaLevel(new BlockPos(x, 0 ,z)).isTopSolid();
    }
	
	@Override
	public boolean canBlockFreeze(BlockPos pos, boolean byWater)
    {
		return false;
    }
	
	@Override
	public boolean canSnowAt(BlockPos pos, boolean checkLight)
    {
		return false;
    }
	
	@Override
	public int getAverageGroundLevel()
    {
		return 50;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight()
	{
		return 128.0F;
	}
	
	@Nullable
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
	{
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(float p_76562_1, float p_76562_2)
	{
		List<Entity> entities = new ArrayList<Entity>(world.loadedEntityList);
		EntityWitherzilla witherzilla = null;
		
		for (Entity entity : entities)
		{
			if (entity instanceof EntityWitherzilla)
			witherzilla = (EntityWitherzilla) entity;
		}

		return witherzilla != null ? new Vec3d(1D, 1D, 1D) : new Vec3d(0.09D, 0.09D, 0.09D);
	}
}


