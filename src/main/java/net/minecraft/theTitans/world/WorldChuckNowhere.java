package net.minecraft.theTitans.world;
import java.util.List;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
public class WorldChuckNowhere extends WorldChunkManagerHell
{
	public WorldChuckNowhere(BiomeGenBase p_i45374_1_, float p_i45374_2_)
	{
		super(p_i45374_1_, p_i45374_2_);
	}

	@SuppressWarnings("rawtypes")
	public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List p_76940_4_)
	{
		return true;
	}
}


