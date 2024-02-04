package net.minecraft.theTitans.world;
import cpw.mods.fml.common.eventhandler.Event.Result;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
public class ChunkProviderVoid implements IChunkProvider
{
	private Random voidRNG;
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	public NoiseGeneratorOctaves noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	private World voidWorld;
	private double[] densities;
	private BiomeGenBase[] biomesForGeneration;
	double[] noiseData1;
	double[] noiseData2;
	double[] noiseData3;
	double[] noiseData4;
	double[] noiseData5;
	int[][] field_73203_h = new int[32][32];
	public ChunkProviderVoid(World p_i2007_1_, long p_i2007_2_)
	{
		this.voidWorld = p_i2007_1_;
		this.voidRNG = new Random(p_i2007_2_);
		this.noiseGen1 = new NoiseGeneratorOctaves(this.voidRNG, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.voidRNG, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.voidRNG, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.voidRNG, 10);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.voidRNG, 16);
		NoiseGenerator[] noiseGens = 
		{
			 this.noiseGen1, this.noiseGen2, this.noiseGen3, this.noiseGen4, this.noiseGen5 
		};
		noiseGens = TerrainGen.getModdedNoiseGenerators(p_i2007_1_, this.voidRNG, noiseGens);
		this.noiseGen1 = ((NoiseGeneratorOctaves)noiseGens[0]);
		this.noiseGen2 = ((NoiseGeneratorOctaves)noiseGens[1]);
		this.noiseGen3 = ((NoiseGeneratorOctaves)noiseGens[2]);
		this.noiseGen4 = ((NoiseGeneratorOctaves)noiseGens[3]);
		this.noiseGen5 = ((NoiseGeneratorOctaves)noiseGens[4]);
	}

	public void func_147420_a(int p_147420_1_, int p_147420_2_, Block[] p_147420_3_, BiomeGenBase[] p_147420_4_)
	{
		byte b0 = 2;
		int k = b0 + 1;
		byte b1 = 33;
		int l = b0 + 1;
		this.densities = initializeNoiseField(this.densities, p_147420_1_ * b0, 0, p_147420_2_ * b0, k, b1, l);
		for (int i1 = 0; i1 < b0; i1++)
		{
			for (int j1 = 0; j1 < b0; j1++)
			{
				for (int k1 = 0; k1 < 32; k1++)
				{
					double d0 = 0.25D;
					double d1 = this.densities[(((i1 + 0) * l + j1 + 0) * b1 + k1 + 0)];
					double d2 = this.densities[(((i1 + 0) * l + j1 + 1) * b1 + k1 + 0)];
					double d3 = this.densities[(((i1 + 1) * l + j1 + 0) * b1 + k1 + 0)];
					double d4 = this.densities[(((i1 + 1) * l + j1 + 1) * b1 + k1 + 0)];
					double d5 = (this.densities[(((i1 + 0) * l + j1 + 0) * b1 + k1 + 1)] - d1) * d0;
					double d6 = (this.densities[(((i1 + 0) * l + j1 + 1) * b1 + k1 + 1)] - d2) * d0;
					double d7 = (this.densities[(((i1 + 1) * l + j1 + 0) * b1 + k1 + 1)] - d3) * d0;
					double d8 = (this.densities[(((i1 + 1) * l + j1 + 1) * b1 + k1 + 1)] - d4) * d0;
					for (int l1 = 0; l1 < 4; l1++)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for (int i2 = 0; i2 < 8; i2++)
						{
							int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | k1 * 4 + l1;
							short short1 = 128;
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;
							for (int k2 = 0; k2 < 8; k2++)
							{
								Block block = null;
								if (d15 > 0.0D)
								{
									block = Blocks.bedrock;
								}

								p_147420_3_[j2] = block;
								j2 += short1;
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

	@Deprecated
	public void func_147421_b(int p_147421_1_, int p_147421_2_, Block[] p_147421_3_, BiomeGenBase[] p_147421_4_)
	{
		replaceBiomeBlocks(p_147421_1_, p_147421_2_, p_147421_3_, p_147421_4_, new byte[p_147421_3_.length]);
	}

	public void replaceBiomeBlocks(int p_147421_1_, int p_147421_2_, Block[] p_147421_3_, BiomeGenBase[] p_147421_4_, byte[] meta)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, p_147421_1_, p_147421_2_, p_147421_3_, meta, p_147421_4_, this.voidWorld);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) 
		{
			 return;
		}

		for (int k = 0; k < 16; k++)
		{
			for (int l = 0; l < 16; l++)
			{
				byte b0 = 1;
				int i1 = -1;
				Block block = Blocks.bedrock;
				Block block1 = Blocks.bedrock;
				for (int j1 = 127; j1 >= 0; j1--)
				{
					int k1 = (l * 16 + k) * 128 + j1;
					Block block2 = p_147421_3_[k1];
					if ((block2 != null) && (block2.getMaterial() != net.minecraft.block.material.Material.air))
					{
						if (block2 == Blocks.stone)
						{
							if (i1 == -1)
							{
								if (b0 <= 0)
								{
									block = null;
									block1 = Blocks.bedrock;
								}

								i1 = b0;
								if (j1 >= 0)
								{
									p_147421_3_[k1] = block;
								}

								else
								{
									p_147421_3_[k1] = block1;
								}
							}

							else if (i1 > 0)
							{
								i1--;
								p_147421_3_[k1] = block1;
							}
						}
					}

					else 
					{

						i1 = -1;
					}
				}
			}
		}
	}

	public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
	{
		return provideChunk(p_73158_1_, p_73158_2_);
	}

	public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
	{
		this.voidRNG.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
		Block[] ablock = new Block[32768];
		byte[] meta = new byte[ablock.length];
		this.biomesForGeneration = this.voidWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
		func_147420_a(p_73154_1_, p_73154_2_, ablock, this.biomesForGeneration);
		replaceBiomeBlocks(p_73154_1_, p_73154_2_, ablock, this.biomesForGeneration, meta);
		Chunk chunk = new Chunk(this.voidWorld, ablock, meta, p_73154_1_, p_73154_2_);
		byte[] abyte = chunk.getBiomeArray();
		for (int k = 0; k < abyte.length; k++)
		{
			abyte[k] = ((byte)this.biomesForGeneration[k].biomeID);
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	private double[] initializeNoiseField(double[] p_73187_1_, int p_73187_2_, int p_73187_3_, int p_73187_4_, int p_73187_5_, int p_73187_6_, int p_73187_7_)
	{
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, p_73187_1_, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY) 
		{
			 return event.noisefield;
		}

		if (p_73187_1_ == null)
		{
			p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];
		}

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
				float f2 = 100.0F - MathHelper.sqrt_float(f * f + f1 * f1) * 8.0F;
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

	public boolean chunkExists(int p_73149_1_, int p_73149_2_)
	{
		return true;
	}

	public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
	{
		net.minecraft.block.BlockFalling.fallInstantly = true;
		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(p_73153_1_, this.voidWorld, this.voidWorld.rand, p_73153_2_, p_73153_3_, false));
		int k = p_73153_2_ * 16;
		int l = p_73153_3_ * 16;
		BiomeGenBase biomegenbase = this.voidWorld.getBiomeGenForCoords(k + 16, l + 16);
		biomegenbase.decorate(this.voidWorld, this.voidWorld.rand, k, l);
		MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.PopulateChunkEvent.Post(p_73153_1_, this.voidWorld, this.voidWorld.rand, p_73153_2_, p_73153_3_, false));
		net.minecraft.block.BlockFalling.fallInstantly = false;
	}

	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
	{
		return true;
	}

	public void saveExtraData() 
	{
		 
	}


	public boolean unloadQueuedChunks()
	{
		return false;
	}

	public boolean canSave()
	{
		return true;
	}

	public String makeString()
	{
		return "RandomLevelSource";
	}

	@SuppressWarnings("rawtypes")
	public java.util.List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
	{
		BiomeGenBase biomegenbase = this.voidWorld.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
		return biomegenbase.getSpawnableList(p_73155_1_);
	}

	public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
	{
		return null;
	}

	public int getLoadedChunkCount()
	{
		return 0;
	}

	public void recreateStructures(int p_82695_1_, int p_82695_2_) 
	{
		 
	}
}


