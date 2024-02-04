package net.minecraft.theTitans.world;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
public class SpawnEggOreGeneration
implements IWorldGenerator
{
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		int dimensionId = world.provider.dimensionId;
		if (Loader.isModLoaded("OreSpawn") && dimensionId != -1 && dimensionId != 1 && dimensionId != TheTitans.VOID_DIMENSION_ID && dimensionId != TheTitans.NOWHERE_DIMENSION_ID && TitanBlocks.MyOverlordScorpionPartSpawnBlock != null)
		{
			generateOre(random, world, chunkX * 16, chunkZ * 16, TitanBlocks.MyOverlordScorpionPartSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, TitanBlocks.MyOverlordScorpionSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, TitanBlocks.MyMethuselahKrakenPartSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, TitanBlocks.MyMethuselahKrakenSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, TitanBlocks.MyBurningMobzillaPartSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, TitanBlocks.MyBurningMobzillaSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyBrutalflySpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyNastysaurusSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyPointysaurusSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCricketSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyFrogSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySpiderDriverSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCrabSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySpiderSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyBatSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCowSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyPigSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySquidSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyChickenSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCreeperSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySkeletonSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyZombieSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySlimeSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyGhastSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyZombiePigmanSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyEndermanSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCaveSpiderSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySilverfishSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyMagmaCubeSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyWitchSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySheepSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyWolfSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyMooshroomSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyOcelotSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyBlazeSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyWitherSkeletonSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyEnderDragonSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySnowGolemSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyIronGolemSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyWitherBossSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyGirlfriendSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyRedCowSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyGoldCowSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyEnchantedCowSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyMOTHRASpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyAloSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCryoSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCamaSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyVeloSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyHydroSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyBasilSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyDragonflySpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyEmperorScorpionSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyScorpionSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCaveFisherSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySpyroSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyBaryonyxSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyGammaMetroidSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCockateilSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyKyuubiSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyAlienSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyAttackSquidSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyWaterDragonSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyKrakenSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyLizardSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCephadromeSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyDragonSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyBeeSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyHorseSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyTrooperBugSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySpitBugSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyStinkBugSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyOstrichSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyGazelleSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyChipmunkSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCreepingHorrorSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyTerribleTerrorSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCliffRacerSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyTriffidSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyPitchBlackSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyLurkingTerrorSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyGodzillaPartSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyGodzillaSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySmallWormSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyMediumWormSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyLargeWormSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCassowarySpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCloudSharkSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyGoldFishSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyLeafMonsterSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyTshirtSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyEnderKnightSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyEnderReaperSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyBeaverSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyTRexSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyHerculesSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyMantisSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyStinkySpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyBoyfriendSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyTheKingPartSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyTheKingSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyEasterBunnySpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCaterKillerSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyMolenoidSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySeaMonsterSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MySeaViperSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyLeonSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyHammerheadSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyRubberDuckySpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyVillagerSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyCriminalSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyTheQueenPartSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
			generateOre(random, world, chunkX * 16, chunkZ * 16, danger.orespawn.OreSpawnMain.MyTheQueenSpawnBlock, Blocks.stone, danger.orespawn.OreSpawnMain.SpawnOres_stats.clumpsize, 1, danger.orespawn.OreSpawnMain.SpawnOres_stats.maxdepth);
		}
	}

	private void generateOre(Random rand, World world, int chunkX, int chunkZ, Block state, Block generateIn, int veinSize, int veinsPerChunk, int maxY)
	{
		for (int vein = 0; vein < veinsPerChunk; vein++)
		{
			int randPosX = chunkX + rand.nextInt(16);
			int randPosY = rand.nextInt(maxY);
			int randPosZ = chunkZ + rand.nextInt(16);
			WorldGenMinable genMinable = new WorldGenMinable(state, veinSize, generateIn);
			genMinable.generate(world, rand, randPosX, randPosY, randPosZ);
		}
	}
}


