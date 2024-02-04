package net.minecraft.theTitans.world;
import net.minecraft.entity.titan.EntityWitherzilla;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
public class BiomeVoidDecorator
extends BiomeDecorator
{
	protected World worldObj;
	protected WorldGenerator turretGen;
	protected WorldGenerator turretGen2;
	protected WorldGenerator turretGen3;
	public BiomeVoidDecorator()
	{
		this.turretGen = new WorldGenTurretTowers(Blocks.bedrock);
		this.turretGen2 = new WorldGenGroundTurretTowers(Blocks.bedrock);
		this.turretGen3 = new WorldGenMortarTurretTowers(Blocks.bedrock);
	}

	protected void genDecorations(BiomeGenBase p_150513_1_)
	{
		generateOres();
		if (this.randomGenerator.nextInt(2) == 0)
		{
			int i = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			int j = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			int k = this.currentWorld.getTopSolidOrLiquidBlock(i, j);
			this.turretGen.generate(this.currentWorld, this.randomGenerator, i, k, j);
		}

		if (this.randomGenerator.nextInt(2) == 0)
		{
			int i = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			int j = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			int k = this.currentWorld.getTopSolidOrLiquidBlock(i, j);
			this.turretGen2.generate(this.currentWorld, this.randomGenerator, i, k, j);
		}

		if (this.randomGenerator.nextInt(5) == 0)
		{
			int i = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			int j = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			int k = this.currentWorld.getTopSolidOrLiquidBlock(i, j);
			this.turretGen3.generate(this.currentWorld, this.randomGenerator, i, k, j);
		}

		if ((this.chunk_X == 0) && (this.chunk_Z == 0))
		{
			EntityWitherzilla witherzilla = new EntityWitherzilla(this.currentWorld);
			witherzilla.setLocationAndAngles(0.0D, 200.0D, 0.0D, this.randomGenerator.nextFloat() * 360.0F, 0.0F);
			this.currentWorld.spawnEntityInWorld(witherzilla);
		}
	}
}


