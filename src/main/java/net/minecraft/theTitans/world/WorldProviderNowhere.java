package net.minecraft.theTitans.world;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
public class WorldProviderNowhere
extends WorldProvider
{
	public void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChuckNowhere(TheTitans.nowhere, 0.0F);
		this.worldChunkMgr.getBiomeGenAt(0, 0).setDisableRain().setTemperatureRainfall(0.0F, 0.0F);
		this.dimensionId = TheTitans.NOWHERE_DIMENSION_ID;
		this.hasNoSky = true;
	}

	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderNowhere(this.worldObj, this.worldObj.getSeed());
	}

	public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
	{
		return 0.0F;
	}

	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
	{
		return null;
	}

	public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
	{
		return false;
	}

	public boolean canSnowAt(int x, int y, int z, boolean checkLight)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float p_76562_1, float p_76562_2)
	{
		return Vec3.createVectorHelper(0.196078431372549D, 0.0196078431372549D, 0.196078431372549D);
	}

	@SideOnly(Side.CLIENT)
	public boolean isSkyColored()
	{
		return false;
	}

	public boolean canRespawnHere()
	{
		return false;
	}

	public boolean isSurfaceWorld()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public float getCloudHeight()
	{
		return 8.0F;
	}

	public boolean canCoordinateBeSpawn(int p_76566_1_, int p_76566_2_)
	{
		return this.worldObj.getTopBlock(p_76566_1_, p_76566_2_).getMaterial().blocksMovement();
	}

	public ChunkCoordinates getEntrancePortalLocation()
	{
		return new ChunkCoordinates(100, 50, 0);
	}

	public int getAverageGroundLevel()
	{
		return 50;
	}

	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int p_76568_1_, int p_76568_2_)
	{
		return true;
	}

	public String getDimensionName()
	{
		return "The Nowhere";
	}

	public String getInternalNameSuffix()
	{
		return "_nowhere";
	}

	public String getWelcomeMessage()
	{
		return "Entering your DEATH!";
	}

	public String getDepartMessage()
	{
		return "Leaving the Nowhere.";
	}
}


