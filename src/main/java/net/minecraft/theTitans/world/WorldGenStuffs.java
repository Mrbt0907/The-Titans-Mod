package net.minecraft.theTitans.world;
import cpw.mods.fml.common.IWorldGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.theTitans.blocks.BlockNormalOre;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.mrbt0907.utils.Maths;
import net.mrbt0907.utils.Maths.Vec3;
public class WorldGenStuffs implements IWorldGenerator
{
	private final Map<WorldGeneratorBase, Vec3> lastStructure = new HashMap<WorldGeneratorBase, Vec3>();
	private static class GenOre
	{
		private List<Block> ores = new ArrayList<Block>();
		private List<Integer> dimensionIDs = new ArrayList<Integer>();
		private Map<Integer, Integer> maxYs = new HashMap<Integer, Integer>();
		private boolean isWhitelist;
		public int veinSize;
		public int veinsPerChunk;
		public GenOre(Block ore, int veinSize, int veinsPerChunk, int maxY, boolean isWhitelist)
		{
			if (ore != null && TitanBlocks.exists(ore))
			ores.add(ore);
			maxYs.put(0, maxY);
			this.veinSize = veinSize;
			this.veinsPerChunk = veinsPerChunk;
			this.isWhitelist = isWhitelist;
		}

		public GenOre(Block[] ores, int veinSize, int veinsPerChunk, int maxY, boolean isWhitelist)
		{
			for (int i = 0; i < ores.length; i++)
			if (ores[i] != null && TitanBlocks.exists(ores[i]))
			this.ores.add(ores[i]);
			maxYs.put(0, maxY);
			this.veinSize = veinSize;
			this.veinsPerChunk = veinsPerChunk;
			this.isWhitelist = isWhitelist;
		}

		public Block getOre(Block base)
		{
			for(int i = 0; i < ores.size(); i++)
			if (ores.get(i) != null && ores.get(i) instanceof BlockNormalOre && ((BlockNormalOre)ores.get(i)).baseBlock.equals(base))
			return ores.get(i);
			return !ores.isEmpty() ? ores.get(0) : null;
		}

		public GenOre addYOffset(int dimensionID, int yOffset)
		{
			if (!maxYs.containsKey(dimensionID))
			maxYs.put(dimensionID, yOffset);
			return this;
		}

		public GenOre addDimension(int... dimensionIDs)
		{
			for (int i = 0; i < dimensionIDs.length; i++)
			if (!this.dimensionIDs.contains(dimensionIDs[i]))
			this.dimensionIDs.add(dimensionIDs[i]);
			return this;
		}

		public boolean isBlacklist()
		{
			return !isWhitelist;
		}

		public boolean isWhitelist()
		{
			return isWhitelist;
		}

		public int getMaxY(int dimensionID)
		{
			if (maxYs.containsKey(dimensionID))
			return maxYs.get(dimensionID);
			return maxYs.get(0);
		}

		public boolean isAllowed(int dimensionID)
		{
			if (ores.size() > 0)
			if (isWhitelist)
			return !dimensionIDs.isEmpty() && dimensionIDs.contains(dimensionID);
			else
			return dimensionIDs.isEmpty() || !dimensionIDs.contains(dimensionID);
			else
			return false;
		}
	}

	private GenOre[] ores =
	{
		new GenOre(TitanBlocks.jadeite_ore, 9, 2, 20, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		//new GenOre(TitanBlocks.demontium_ore, 6 ,2 ,16, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.harcadium_ore, 5 ,1 ,8, false).addDimension(TheTitans.VOID_DIMENSION_ID, TheTitans.NOWHERE_DIMENSION_ID, 1),
		new GenOre(TitanBlocks.harcadium_ore_end_stone, 10 ,25 ,128, true).addDimension(1),
		new GenOre(TitanBlocks.harcadium_ore_obsidian, 5 ,20 ,256, true).addDimension(TheTitans.NOWHERE_DIMENSION_ID),
		new GenOre(TitanBlocks.withirenium_ore, 6 ,10 ,32, false).addDimension(TheTitans.VOID_DIMENSION_ID, 0, 1),
		new GenOre(TitanBlocks.void_ore, 3 ,1 ,4, false).addDimension(TheTitans.VOID_DIMENSION_ID, TheTitans.NOWHERE_DIMENSION_ID, 1),
		new GenOre(TitanBlocks.void_ore_end_stone, 7 ,2 ,128, true).addDimension(1),
		new GenOre(TitanBlocks.void_ore_obsidian, 8 ,10 ,256, true).addDimension(TheTitans.NOWHERE_DIMENSION_ID),
		new GenOre(TitanBlocks.molten_fuel_ore, 16, 10, 128, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.pig_iron_ore, 16, 10, 128, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.ruby_ore, 7, 2, 28, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.sapphire_ore, 7, 2, 28, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.copper_ore, 16, 20, 128, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.tin_ore, 16, 20, 128, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.chromium_ore, 9, 20, 48, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.magnesium_ore, 9, 20, 48, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.lead_ore, 9, 20, 48, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.silver_ore, 8, 8, 32, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.platinum_ore, 7, 2, 10, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.titanium_ore, 7, 2, 10, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(TitanBlocks.coal_ore, 16, 20, 128, true).addDimension(-1, 1, TheTitans.NOWHERE_DIMENSION_ID),
		new GenOre(TitanBlocks.iron_ore, 14, 20, 63, true).addDimension(-1, 1, TheTitans.NOWHERE_DIMENSION_ID).addYOffset(-1, 128),
		new GenOre(TitanBlocks.redstone_ore, 8, 8, 16, true).addDimension(-1, 1, TheTitans.NOWHERE_DIMENSION_ID).addYOffset(-1, 64),
		new GenOre(TitanBlocks.gold_ore, 16, 2, 32, true).addDimension(-1, 1, TheTitans.NOWHERE_DIMENSION_ID).addYOffset(-1, 128),
		new GenOre(TitanBlocks.lapis_ore, 8, 4, 30, true).addDimension(-1, 1, TheTitans.NOWHERE_DIMENSION_ID).addYOffset(-1, 64),
		new GenOre(TitanBlocks.emerald_ore, 2, 2, 32, true).addDimension(-1, 1, TheTitans.NOWHERE_DIMENSION_ID),
		new GenOre(TitanBlocks.diamond_ore, 7, 1, 16, true).addDimension(-1, 1, TheTitans.NOWHERE_DIMENSION_ID),
		new GenOre(Blocks.coal_ore, 32, 1, 128, true).addDimension(0, TheTitans.VOID_DIMENSION_ID).addYOffset(TheTitans.NOWHERE_DIMENSION_ID, 64),
		new GenOre(Blocks.iron_ore, 24, 1, 63, true).addDimension(0, TheTitans.VOID_DIMENSION_ID).addYOffset(TheTitans.NOWHERE_DIMENSION_ID, 64),
		new GenOre(Blocks.gold_ore, 24, 1, 32, true).addDimension(0, TheTitans.VOID_DIMENSION_ID).addYOffset(TheTitans.NOWHERE_DIMENSION_ID, 64),
		new GenOre(Blocks.diamond_ore, 18, 1, 16, true).addDimension(0, TheTitans.VOID_DIMENSION_ID).addYOffset(TheTitans.NOWHERE_DIMENSION_ID, 64),
		new GenOre(Blocks.emerald_ore, 18, 1, 16, true).addDimension(0, TheTitans.VOID_DIMENSION_ID).addYOffset(TheTitans.NOWHERE_DIMENSION_ID, 64),
		new GenOre(Blocks.redstone_ore, 18, 1, 32, true).addDimension(0, TheTitans.VOID_DIMENSION_ID).addYOffset(TheTitans.NOWHERE_DIMENSION_ID, 64),
		new GenOre(Blocks.lapis_ore, 16, 1, 16, true).addDimension(0, TheTitans.VOID_DIMENSION_ID).addYOffset(TheTitans.NOWHERE_DIMENSION_ID, 64),
		new GenOre(Blocks.coal_block, 16, 1, 128, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(Blocks.iron_block, 8, 1, 64, false).addDimension(TheTitans.VOID_DIMENSION_ID).addYOffset(-1, 128),
		new GenOre(Blocks.gold_block, 8, 1, 32, false).addDimension(TheTitans.VOID_DIMENSION_ID).addYOffset(-1, 128),
		new GenOre(Blocks.diamond_block, 5, 1, 16, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(Blocks.emerald_block, 5, 1, 16, false).addDimension(TheTitans.VOID_DIMENSION_ID),
		new GenOre(Blocks.redstone_block, 7, 1, 32, false).addDimension(TheTitans.VOID_DIMENSION_ID).addYOffset(-1, 64),
		new GenOre(Blocks.lapis_block, 6, 1, 16, false).addDimension(TheTitans.VOID_DIMENSION_ID).addYOffset(-1, 64),
		new GenOre(TitanBlocks.netherite, 20, 50, 256, true).addDimension(-1)
	};
	
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		world.theProfiler.startSection("genTitanStuffs");
		int dimensionId = world.provider.dimensionId;
		for (int i = 0; i < ores.length; i++)
		generateOre(random, world, chunkX, chunkZ, dimensionId, ores[i]);
		switch (dimensionId)
		{
			case -1: break;
			case 1: break;
			case TheTitans.NOWHERE_DIMENSION_ID: break;
			case TheTitans.VOID_DIMENSION_ID:
			{
				generateOre(random, world, chunkX, chunkZ, Blocks.obsidian, Blocks.bedrock, 32, 20, 128);
				break;
			}

			default:
			generateStructure(random, world, chunkX, chunkZ, 128, 10.0F, new WorldGenMobhenge());
		}

		world.theProfiler.endSection();
	}

	private void generateOre(Random random, World world, int chunkX, int chunkZ, int dimensionID, GenOre gen)
	{
		if (gen != null && gen.isAllowed(dimensionID))
		{
			switch(dimensionID)
			{
				case -1: generateOre(random, world, chunkX, chunkZ, gen.getOre(Blocks.netherrack), Blocks.netherrack, gen.veinSize, gen.veinsPerChunk, gen.getMaxY(dimensionID)); break;
				case 1: generateOre(random, world, chunkX, chunkZ, gen.getOre(Blocks.end_stone), Blocks.end_stone, gen.veinSize, gen.veinsPerChunk, gen.getMaxY(dimensionID)); break;
				case TheTitans.VOID_DIMENSION_ID: generateOre(random, world, chunkX, chunkZ, gen.getOre(Blocks.bedrock), Blocks.bedrock, gen.veinSize, gen.veinsPerChunk, gen.getMaxY(dimensionID)); break;
				case TheTitans.NOWHERE_DIMENSION_ID: generateOre(random, world, chunkX, chunkZ, gen.getOre(Blocks.obsidian), Blocks.obsidian, gen.veinSize, gen.veinsPerChunk, gen.getMaxY(dimensionID)); break;
				default: generateOre(random, world, chunkX, chunkZ, gen.getOre(Blocks.stone), Blocks.stone, gen.veinSize, gen.veinsPerChunk, gen.getMaxY(dimensionID)); 
			}
		}
	}

	private void generateOre(Random random, World world, int chunkX, int chunkZ, Block state, Block generateIn, int veinSize, int veinsPerChunk, int maxY)
	{
		if (state == null && generateIn == null)
		{
			TheTitans.warn("Failed to generate ore because state and generateIn was null");
			return;
		}

		else if (state == null)
		{
			TheTitans.warn("Failed to generate ore in block " + generateIn.getUnlocalizedName().substring(5) + " because state was null");
			return;
		}

		else if (generateIn == null)
		{
			TheTitans.warn("Failed to generate ore " + state.getUnlocalizedName().substring(5) + " because generateIn was null");
			return;
		}

		for (int vein = 0; vein < veinsPerChunk; vein++)
		{
			int randPosX = chunkX * 16 + random.nextInt(16);
			int randPosY = random.nextInt(maxY);
			int randPosZ = chunkZ * 16 + random.nextInt(16);
			WorldGenVein gen = new WorldGenVein(state, veinSize, generateIn);
			gen.generate(world, random, randPosX, randPosY, randPosZ);
		}
	}

	private void generateStructure(Random rand, World world, int chunkX, int chunkZ, int maxY, float chance, WorldGeneratorBase... gens)
	{
		if (gens.length == 0)
		return;
		Maths.updateRandom(rand);
		WorldGeneratorBase gen = null;
		Vec3 vectors = new Vec3(chunkX * 16, 0, chunkZ * 16);
		int attempts = 0;
		while (gen == null && attempts < 5)
		{
			gen = (WorldGeneratorBase) gens[Maths.random(gens.length - 1)];
			if (Maths.chance(chance * 0.01F) && gen != null && gen instanceof WorldGeneratorBase)
			{
				for (Vec3 last : lastStructure.values())
				if (vectors.distance(last) < gen.separation)
				return;
				vectors.vecY = Math.min(world.getTopSolidOrLiquidBlock((int)vectors.vecX, (int)vectors.vecZ), maxY);
				gen.generate(world, rand, (int)vectors.vecX, (int)vectors.vecY, (int)vectors.vecZ);
				lastStructure.put(gen, vectors);
				break;
			}

			else
			{
				gen = null;
				attempts++;
			}
		}

		Maths.updateRandom();
	}
}


