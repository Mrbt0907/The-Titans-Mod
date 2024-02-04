package net.minecraft.theTitans.world;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityWitherzilla;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import scala.util.Random;
public class WorldProviderVoid
extends WorldProvider
{
	public void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerHell(TheTitans.voidland, 0.0F);
		this.worldChunkMgr.getBiomeGenAt(0, 0).setDisableRain().setTemperatureRainfall(2.0F, 0.0F);
		this.dimensionId = TheTitans.VOID_DIMENSION_ID;
		this.hasNoSky = true;
	}

	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderVoid(this.worldObj, this.worldObj.getSeed());
	}

	public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
	{
		return 0.5F;
	}

	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
	{
		return null;
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float p_76562_1, float p_76562_2)
	{
		new Random();
		ArrayList<?> allPlayerList = Lists.newArrayList(this.worldObj.loadedEntityList);
		EntityWitherzilla witherzilla = null;
		if ((allPlayerList != null) && (!allPlayerList.isEmpty()))
		{
			for (int i1 = 0; i1 < allPlayerList.size(); i1++)
			{
				Entity entity = (Entity)allPlayerList.get(i1);
				if (entity instanceof EntityWitherzilla && entity.worldObj.provider == this)
				witherzilla = (EntityWitherzilla) entity;
			}
		}

		return witherzilla != null ? Vec3.createVectorHelper(1D, 1D, 1D) : Vec3.createVectorHelper(0.09D, 0.09D, 0.09D);
	}

	protected void generateLightBrightnessTable()
	{
		float f = 1F;
		for (int i = 0; i <= 15; ++i)
		{
			float f1 = 1.0F - (float)i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean isSkyColored()
	{
		return true;
	}

	public boolean canRespawnHere()
	{
		return false;
	}

	public boolean isSurfaceWorld()
	{
		return false;
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
	public float getCloudHeight()
	{
		return 128.0F;
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
		return false;
	}

	public String getDimensionName()
	{
		return "The Void";
	}

	public String getInternalNameSuffix()
	{
		return "_void";
	}

	public String getWelcomeMessage()
	{
		return "Entering your DOOM!";
	}

	public String getDepartMessage()
	{
		return "Leaving the Void.";
	}
}


