package net.minecraft.theTitans.world;
import cpw.mods.fml.common.eventhandler.Event.Result;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.MINESHAFT;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_BRIDGE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.VILLAGE;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.titan.EntityEnderColossus;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.init.Blocks;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.structure.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
public class ChunkProviderNowhere implements IChunkProvider
{
	private Random voidRNG;
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	public NoiseGeneratorOctaves noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	public NoiseGeneratorOctaves noiseGen6;
	private World voidWorld;
	private double[] densities;
	private BiomeGenBase[] biomesForGeneration;
	private final float[] parabolicField;
	double[] noiseData1;
	double[] noiseData2;
	double[] noiseData3;
	double[] noiseData4;
	double[] noiseData5;
	double[] field_147427_d;
	double[] field_147428_e;
	double[] field_147425_f;
	double[] field_147426_g;
	int[][] field_73219_j = new int[32][32];
	private WorldType field_147435_p;
	private MapGenBase caveGenerator = new MapGenCavesNowhere();
	private MapGenVillage villageGenerator = new MapGenVillage();
	private MapGenBase ravineGenerator = new MapGenRavineNowhere();
	public MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
	private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
	{
		genNetherBridge = (MapGenNetherBridge) TerrainGen.getModdedMapGen(genNetherBridge, NETHER_BRIDGE);
		caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, CAVE);
		villageGenerator = (MapGenVillage) TerrainGen.getModdedMapGen(villageGenerator, VILLAGE);
		mineshaftGenerator = (MapGenMineshaft) TerrainGen.getModdedMapGen(mineshaftGenerator, MINESHAFT);
		ravineGenerator = TerrainGen.getModdedMapGen(ravineGenerator, RAVINE);
	}

	public ChunkProviderNowhere(World p_i2007_1_, long p_i2007_2_)
	{
		this.voidWorld = p_i2007_1_;
		this.voidRNG = new Random(p_i2007_2_);
		this.densities = new double[825];
		this.noiseGen1 = new NoiseGeneratorOctaves(this.voidRNG, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(this.voidRNG, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(this.voidRNG, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(this.voidRNG, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(this.voidRNG, 10);
		this.noiseGen6 = new NoiseGeneratorOctaves(this.voidRNG, 16);
		this.densities = new double[825];
		NoiseGenerator[] noiseGens = 
		{
			 this.noiseGen1, this.noiseGen2, this.noiseGen3, this.noiseGen4, this.noiseGen5, this.noiseGen6 
		};
		noiseGens = TerrainGen.getModdedNoiseGenerators(p_i2007_1_, this.voidRNG, noiseGens);
		this.noiseGen1 = ((NoiseGeneratorOctaves)noiseGens[0]);
		this.noiseGen2 = ((NoiseGeneratorOctaves)noiseGens[1]);
		this.noiseGen3 = ((NoiseGeneratorOctaves)noiseGens[2]);
		this.noiseGen4 = ((NoiseGeneratorOctaves)noiseGens[3]);
		this.noiseGen5 = ((NoiseGeneratorOctaves)noiseGens[4]);
		this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
		this.parabolicField = new float[25];
		for (int j = -2; j <= 2; ++j)
		{
			for (int k = -2; k <= 2; ++k)
			{
				float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 0.2F);
				this.parabolicField[j + 2 + (k + 2) * 5] = f;
			}
		}
	}

	private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_)
	{
		this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0D, 200.0D, 0.5D);
		this.field_147427_d = this.noiseGen3.generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		this.field_147428_e = this.noiseGen1.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		this.field_147425_f = this.noiseGen2.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		int l = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < 5; ++j1)
		{
			for (int k1 = 0; k1 < 5; ++k1)
			{
				float f = 0.0F;
				float f1 = 0.0F;
				float f2 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];
				for (int l1 = -b0; l1 <= b0; ++l1)
				{
					for (int i2 = -b0; i2 <= b0; ++i2)
					{
						BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float f3 = biomegenbase1.rootHeight;
						float f4 = biomegenbase1.heightVariation;
						if (this.field_147435_p == WorldType.AMPLIFIED && f3 > 0.0F)
						{
							f3 = 1.0F + f3 * 2.0F;
							f4 = 1.0F + f4 * 4.0F;
						}

						float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);
						if (biomegenbase1.rootHeight > biomegenbase.rootHeight)
						{
							f5 /= 2.0F;
						}

						f += f4 * f5;
						f1 += f3 * f5;
						f2 += f5;
					}
				}

				f /= f2;
				f1 /= f2;
				f = f * 0.9F + 0.1F;
				f1 = (f1 * 4.0F - 1.0F) / 8.0F;
				double d12 = this.field_147426_g[i1] / 8000.0D;
				if (d12 < 0.0D)
				{
					d12 = -d12 * 0.3D;
				}

				d12 = d12 * 3.0D - 2.0D;
				if (d12 < 0.0D)
				{
					d12 /= 2.0D;
					if (d12 < -1.0D)
					{
						d12 = -1.0D;
					}

					d12 /= 1.4D;
					d12 /= 2.0D;
				}

				else
				{
					if (d12 > 1.0D)
					{
						d12 = 1.0D;
					}

					d12 /= 8.0D;
				}

				++i1;
				double d13 = (double)f1;
				double d14 = (double)f;
				d13 += d12 * 0.2D;
				d13 = d13 * 8.5D / 8.0D;
				double d5 = 8.5D + d13 * 4.0D;
				for (int j2 = 0; j2 < 33; ++j2)
				{
					double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;
					if (d6 < 0.0D)
					{
						d6 *= 4.0D;
					}

					double d7 = this.field_147428_e[l] / 512.0D;
					double d8 = this.field_147425_f[l] / 512.0D;
					double d9 = (this.field_147427_d[l] / 10.0D + 1.0D) / 2.0D;
					double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;
					if (j2 > 29)
					{
						double d11 = (double)((float)(j2 - 29) / 3.0F);
						d10 = d10 * (1.0D - d11) + -10.0D * d11;
					}

					this.densities[l] = d10;
					++l;
				}
			}
		}
	}

	public void func_147424_a(int p_147424_1_, int p_147424_2_, Block[] p_147424_3_, BiomeGenBase[] biomesForGeneration2)
	{
		this.func_147423_a(p_147424_1_ * 4, 0, p_147424_2_ * 4);
		for (int k = 0; k < 4; ++k)
		{
			int l = k * 5;
			int i1 = (k + 1) * 5;
			for (int j1 = 0; j1 < 4; ++j1)
			{
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;
				for (int k2 = 0; k2 < 32; ++k2)
				{
					double d0 = 0.125D;
					double d1 = this.densities[k1 + k2];
					double d2 = this.densities[l1 + k2];
					double d3 = this.densities[i2 + k2];
					double d4 = this.densities[j2 + k2];
					double d5 = (this.densities[k1 + k2 + 1] - d1) * d0;
					double d6 = (this.densities[l1 + k2 + 1] - d2) * d0;
					double d7 = (this.densities[i2 + k2 + 1] - d3) * d0;
					double d8 = (this.densities[j2 + k2 + 1] - d4) * d0;
					for (int l2 = 0; l2 < 8; ++l2)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;
						for (int i3 = 0; i3 < 4; ++i3)
						{
							int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
							short short1 = 256;
							j3 -= short1;
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;
							for (int k3 = 0; k3 < 4; ++k3)
							{
								if ((d15 += d16) > 0.0D)
								{
									p_147424_3_[j3 += short1] = Blocks.obsidian;
								}

								else
								{
									p_147424_3_[j3 += short1] = null;
								}
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
		if (event.getResult() == Result.DENY) return;
		for (int k = 0; k < 16; ++k)
		{
			for (int l = 0; l < 16; ++l)
			{
				byte b0 = 1;
				int i1 = -1;
				Block block = Blocks.obsidian;
				Block block1 = Blocks.obsidian;
				for (int j1 = 127; j1 >= 0; --j1)
				{
					int k1 = (l * 16 + k) * 128 + j1;
					Block block2 = p_147421_3_[k1];
					if (block2 != null && block2.getMaterial() != Material.air)
					{
						if (block2 == Blocks.stone)
						{
							if (i1 == -1)
							{
								if (b0 <= 0)
								{
									block = null;
									block1 = Blocks.obsidian;
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
								--i1;
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
		this.voidRNG.setSeed((long)p_73154_1_ * 341873128712L + (long)p_73154_2_ * 132897987541L);
		Block[] ablock = new Block[65536];
		byte[] abyte = new byte[65536];
		this.biomesForGeneration = this.voidWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
		this.func_147424_a(p_73154_1_, p_73154_2_, ablock, this.biomesForGeneration);
		this.caveGenerator.func_151539_a(this, this.voidWorld, p_73154_1_, p_73154_2_, ablock);
		this.ravineGenerator.func_151539_a(this, this.voidWorld, p_73154_1_, p_73154_2_, ablock);
		this.genNetherBridge.func_151539_a(this, this.voidWorld, p_73154_1_, p_73154_2_, ablock);
		this.villageGenerator.func_151539_a(this, this.voidWorld, p_73154_1_, p_73154_2_, ablock);
		this.mineshaftGenerator.func_151539_a(this, this.voidWorld, p_73154_1_, p_73154_2_, ablock);
		replaceBiomeBlocks(p_73154_1_, p_73154_2_, ablock, this.biomesForGeneration, abyte);
		Chunk chunk = new Chunk(this.voidWorld, ablock, abyte, p_73154_1_, p_73154_2_);
		byte[] abyte1 = chunk.getBiomeArray();
		for (int k = 0; k < abyte1.length; k++)
		{
			abyte1[k] = ((byte)this.biomesForGeneration[k].biomeID);
		}

		chunk.generateSkylightMap();
		return chunk;
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
		this.voidRNG.setSeed(this.voidWorld.getSeed());
		long i1 = this.voidRNG.nextLong() / 2L * 2L + 1L;
		long j1 = this.voidRNG.nextLong() / 2L * 2L + 1L;
		this.voidRNG.setSeed((long)p_73153_2_ * i1 + (long)p_73153_3_ * j1 ^ this.voidWorld.getSeed());
		this.villageGenerator.generateStructuresInChunk(this.voidWorld, this.voidRNG, p_73153_2_, p_73153_3_);
		this.genNetherBridge.generateStructuresInChunk(this.voidWorld, this.voidRNG, p_73153_2_, p_73153_3_);
		this.mineshaftGenerator.generateStructuresInChunk(this.voidWorld, this.voidRNG, p_73153_2_, p_73153_3_);
		biomegenbase.decorate(this.voidWorld, this.voidRNG, k, l);
		int k1;
		int l1;
		int i2;
		for (k1 = 0; k1 < 100; ++k1)
		{
			l1 = k + this.voidRNG.nextInt(16) + 8;
			i2 = this.voidRNG.nextInt(256);
			int j2 = l + this.voidRNG.nextInt(16) + 8;
			(new WorldGenNowhereDungeon()).generate(this.voidWorld, this.voidRNG, l1, i2, j2);
		}

		for (k1 = 0; k1 < 800; ++k1)
		{
			l1 = k + this.voidRNG.nextInt(16) + 8;
			i2 = this.voidRNG.nextInt(256);
			int j2 = l + this.voidRNG.nextInt(16) + 8;
			(new WorldGenDungeons()).generate(this.voidWorld, this.voidRNG, l1, i2, j2);
		}

		if (this.voidRNG.nextInt(2000) == 0)
		{
			l1 = k + this.voidRNG.nextInt(16) + 8;
			int j2 = l + this.voidRNG.nextInt(16) + 8;
			i2 = this.voidWorld.getTopSolidOrLiquidBlock(l1, j2);
			EntityZombieTitan witherzilla = new EntityZombieTitan(this.voidWorld);
			witherzilla.onSpawnWithEgg(null);
			witherzilla.setLocationAndAngles(l1, i2, j2, 0.0F, 0.0F);
			LogManager.getLogger(TheTitans.class).info("Found a succesfully spawned Void Zombie Titan at " + l1 + ", " + i2 + ", " + j2 + ", spawning.");
			this.voidWorld.spawnEntityInWorld(witherzilla);
		}

		if (this.voidRNG.nextInt(50000) == 0)
		{
			l1 = k + this.voidRNG.nextInt(16) + 8;
			int j2 = l + this.voidRNG.nextInt(16) + 8;
			i2 = this.voidWorld.getTopSolidOrLiquidBlock(l1, j2);
			EntityEnderColossus witherzilla = new EntityEnderColossus(this.voidWorld);
			witherzilla.setLocationAndAngles(l1, i2, j2, 0.0F, 0.0F);
			LogManager.getLogger(TheTitans.class).info("Found a succesfully spawned Ender Colossus at " + l1 + ", " + i2 + ", " + j2 + ", spawning.");
			this.voidWorld.spawnEntityInWorld(witherzilla);
		}

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
		if (p_73155_1_ == EnumCreatureType.monster)
		{
			if (this.genNetherBridge.hasStructureAt(p_73155_2_, p_73155_3_, p_73155_4_))
			{
				return this.genNetherBridge.getSpawnList();
			}

			if (this.genNetherBridge.func_142038_b(p_73155_2_, p_73155_3_, p_73155_4_) && this.voidWorld.getBlock(p_73155_2_, p_73155_3_ - 1, p_73155_4_) == Blocks.nether_brick)
			{
				return this.genNetherBridge.getSpawnList();
			}
		}

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
		this.villageGenerator.func_151539_a(this, this.voidWorld, p_82695_1_, p_82695_2_, (Block[])null);
		this.genNetherBridge.func_151539_a(this, this.voidWorld, p_82695_1_, p_82695_2_, (Block[])null);
		this.mineshaftGenerator.func_151539_a(this, this.voidWorld, p_82695_1_, p_82695_2_, (Block[])null);
	}
}


