package net.mrbt0907.thetitans.world;
import java.util.List;
import java.util.Random;

import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class ChunkProviderVoid implements IChunkGenerator
{
	private World world;
	private Random random;
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	private NoiseGeneratorOctaves noiseGen4;
	private NoiseGeneratorOctaves noiseGen5;
	private Biome[] biomesForGeneration;
	private double[] densities;
	private double[] noiseData1;
	private double[] noiseData2;
	private double[] noiseData3;
	private double[] noiseData4;
	private double[] noiseData5;
	private final IBlockState AIR = Blocks.AIR.getDefaultState();
	private final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
	
	public ChunkProviderVoid(World world, long seed)
	{
		this.world = world;
		random = new Random(seed);
		this.noiseGen1 = new NoiseGeneratorOctaves(this.random, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.random, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.random, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.random, 10);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.random, 16);
		net.minecraftforge.event.terraingen.InitNoiseGensEvent.Context ctx = new net.minecraftforge.event.terraingen.InitNoiseGensEvent.Context(noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5);
        ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(world, random, ctx);
		this.noiseGen1 = ctx.getLPerlin1();
		this.noiseGen2 = ctx.getLPerlin2();
		this.noiseGen3 = ctx.getPerlin();
		this.noiseGen4 = ctx.getDepth();
		this.noiseGen5 = ctx.getScale();
	}
	
	public void setBlocksInChunk(int x, int z, ChunkPrimer primer)
    {
        densities = getHeights(densities, x * 2, 0, z * 2, 3, 33, 3);

        for (int i1 = 0; i1 < 2; ++i1)
        {
            for (int j1 = 0; j1 < 2; ++j1)
            {
                for (int k1 = 0; k1 < 32; ++k1)
                {
                    double d1 = densities[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 0];
                    double d2 = densities[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 0];
                    double d3 = densities[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 0];
                    double d4 = densities[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 0];
                    double d5 = (densities[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 1] - d1) * 0.25D;
                    double d6 = (densities[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 1] - d2) * 0.25D;
                    double d7 = (densities[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 1] - d3) * 0.25D;
                    double d8 = (densities[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 1] - d4) * 0.25D;

                    for (int l1 = 0; l1 < 4; ++l1)
                    {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.125D;
                        double d13 = (d4 - d2) * 0.125D;

                        for (int i2 = 0; i2 < 8; ++i2)
                        {
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.125D;

                            for (int j2 = 0; j2 < 8; ++j2)
                            {
                                IBlockState iblockstate = AIR;

                                if (d15 > 0.0D)
                                {
                                    iblockstate = BEDROCK;
                                }

                                int k2 = i2 + i1 * 8;
                                int l2 = l1 + k1 * 4;
                                int i3 = j2 + j1 * 8;
                                primer.setBlockState(k2, l2, i3, iblockstate);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }
	
	private double[] getHeights(double[] p_73187_1_, int p_73187_2_, int p_73187_3_, int p_73187_4_, int p_73187_5_, int p_73187_6_, int p_73187_7_)
	{
		ChunkGeneratorEvent.InitNoiseField event = new ChunkGeneratorEvent.InitNoiseField(this, p_73187_1_, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) return event.getNoisefield();

		if (p_73187_1_ == null)
			p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];

		double d0 = 684.412D;
		double d1 = 684.412D;
		this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 1.121D, 1.121D, 0.5D);
		this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 200.0D, 200.0D, 0.5D);
		d0 *= 2.0D;
		this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
		this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
		this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, d0, d1, d0);
		int k1 = 0;
		int l1 = 0;
		for (int i2 = 0; i2 < p_73187_5_; i2++)
		{
			for (int j2 = 0; j2 < p_73187_7_; j2++)
			{
				double d2 = (this.noiseData4[l1] + 256.0D) / 512.0D;
				if (d2 > 1.0D)
				{
					d2 = 1.0D;
				}

				double d3 = this.noiseData5[l1] / 8000.0D;
				if (d3 < 0.0D)
				{
					d3 = -d3 * 0.3D;
				}

				d3 = d3 * 3.0D - 2.0D;
				float f = (i2 + p_73187_2_ - 0) / 1.0F;
				float f1 = (j2 + p_73187_4_ - 0) / 1.0F;
				float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;
				if (f2 > 80.0F)
				{
					f2 = 80.0F;
				}

				if (f2 < -100.0F)
				{
					f2 = -100.0F;
				}

				if (d3 > 1.0D)
				{
					d3 = 1.0D;
				}

				d3 /= 8.0D;
				d3 = 0.0D;
				if (d2 < 0.0D)
				{
					d2 = 0.0D;
				}

				d2 += 0.5D;
				d3 = d3 * p_73187_6_ / 16.0D;
				l1++;
				double d4 = p_73187_6_ / 2.0D;
				for (int k2 = 0; k2 < p_73187_6_; k2++)
				{
					double d5 = 0.0D;
					double d6 = (k2 - d4) * 8.0D / d2;
					if (d6 < 0.0D)
					{
						d6 *= -1.0D;
					}

					double d7 = this.noiseData2[k1] / 512.0D;
					double d8 = this.noiseData3[k1] / 512.0D;
					double d9 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;
					if (d9 < 0.0D)
					{
						d5 = d7;
					}

					else if (d9 > 1.0D)
					{
						d5 = d8;
					}

					else
					{
						d5 = d7 + (d8 - d7) * d9;
					}

					d5 -= 8.0D;
					d5 += f2;
					byte b0 = 2;
					if (k2 > p_73187_6_ / 2 - b0)
					{
						double d10 = (k2 - (p_73187_6_ / 2 - b0)) / 64.0F;
						if (d10 < 0.0D)
						{
							d10 = 0.0D;
						}

						if (d10 > 1.0D)
						{
							d10 = 1.0D;
						}

						d5 = d5 * (1.0D - d10) + -3000.0D * d10;
					}

					b0 = 8;
					if (k2 < b0)
					{
						double d10 = (b0 - k2) / (b0 - 1.0F);
						d5 = d5 * (1.0D - d10) + -30.0D * d10;
					}

					p_73187_1_[k1] = d5;
					k1++;
				}
			}
		}

		return p_73187_1_;
	}
	
	public void buildSurfaces(int x, int z, ChunkPrimer chunk)
	{
		if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, chunk, world)) return;

		for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 16; ++j)
            {
                int l = -1;
                IBlockState iblockstate = BEDROCK;
                IBlockState iblockstate1 = BEDROCK;

                for (int i1 = 127; i1 >= 0; --i1)
                {
                    IBlockState iblockstate2 = chunk.getBlockState(i, i1, j);

                    if (iblockstate2.getMaterial() == Material.AIR)
                    {
                        l = -1;
                    }
                    else if (iblockstate2.getBlock() == Blocks.STONE)
                    {
                        if (l == -1)
                        {
                            l = 1;

                            if (i1 >= 0)
                            {
                            	chunk.setBlockState(i, i1, j, iblockstate);
                            }
                            else
                            {
                            	chunk.setBlockState(i, i1, j, iblockstate1);
                            }
                        }
                        else if (l > 0)
                        {
                            --l;
                            chunk.setBlockState(i, i1, j, iblockstate1);
                        }
                    }
                }
            }
        }
	}
	
	public Chunk generateChunk(int x, int z)
    {
        random.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.setBlocksInChunk(x, z, chunkprimer);
        this.buildSurfaces(x, z, chunkprimer);


        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
            abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);

        chunk.generateSkylightMap();
        return chunk;
    }

	@Override
	public void populate(int x, int z)
	{
		BlockFalling.fallInstantly = true;
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(this, world, random, x, z, false));
		BlockPos pos = new BlockPos(x + 16, 0, z + 16);
		Biome biomegenbase = world.getBiomeForCoordsBody(pos);
		biomegenbase.decorate(world, random, pos);
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(this, world, random, x, z, false));
		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z)
	{
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	{
		return world.getBiome(pos).getSpawnableList(creatureType);
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
	{
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z)
	{
		
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
	{
		return false;
	}
}


