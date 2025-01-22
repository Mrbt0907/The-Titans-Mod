package net.mrbt0907.thetitans.world.biome;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class BiomeVoid extends Biome
{
	public BiomeVoid(Biome.BiomeProperties properties)
	{
		super(properties);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		//this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
		this.topBlock = Blocks.BEDROCK.getDefaultState();
		this.fillerBlock = Blocks.BEDROCK.getDefaultState();
		this.decorator = new BiomeVoidDecorator();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float p_76731_1_)
	{
		return 0;
	}
}


