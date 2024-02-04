package net.minecraft.theTitans.world;
import cpw.mods.fml.common.IWorldGenerator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.titan.*;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.mrbt0907.utils.Maths;
import net.mrbt0907.utils.Maths.Vec3;
public class WorldGenMobs
implements IWorldGenerator
{
	private final Map<EntityLivingBase, Vec3> lastSpawn = new HashMap<EntityLivingBase, Vec3>();
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		world.theProfiler.startSection("genTitanMobs");
		int dimensionID = world.provider.dimensionId;
		switch (dimensionID)
		{
			case -1: spawnTitan(random, world, chunkX, chunkZ, 33.0F, new EntityMagmaCubeTitan(world), new EntityGhastTitan(world), new EntitySkeletonTitan(world).setSkeletonType(1), new EntityBlazeTitan(world), new EntityPigZombieTitan(world)); break;
			case TheTitans.NOWHERE_DIMENSION_ID: break;
			case TheTitans.VOID_DIMENSION_ID: break;
			default: spawnTitan(random, world, chunkX, chunkZ, 33.0F, new EntitySilverfishTitan(world), new EntityCaveSpiderTitan(world), new EntitySpiderTitan(world), new EntityZombieTitan(world), new EntitySkeletonTitan(world), new EntityCreeperTitan(world), new EntitySlimeTitan(world));
		}

		world.theProfiler.endSection();
	}

	public void removeEntity(EntityLivingBase entity)
	{
		if(entity != null && !lastSpawn.isEmpty() && lastSpawn.containsKey(entity))
		{
			lastSpawn.remove(entity);
			TheTitans.debug("Removed " + entity.getCommandSenderName() + " from the list");
		}
	}

	private void spawnTitan(Random rand, World world, int chunkX, int chunkZ, float chance, EntityLivingBase... entities)
	{
		if (entities.length == 0)
		return;
		Maths.updateRandom(rand);
		EntityLivingBase entity = null;
		Vec3 vectors = new Vec3(chunkX * 16, 0, chunkZ * 16);
		int attempts = 0;
		for (Vec3 last : lastSpawn.values())
		if (vectors.distance(last) < TitanConfig.spawnSeparation)
		return;
		if (vectors.distance(world.getSpawnPoint().posX, world.getSpawnPoint().posY, world.getSpawnPoint().posZ) > TitanConfig.spawnProtection)
		while (entity == null && attempts < 5)
		{
			entity = (EntityLivingBase) entities[Maths.random(entities.length - 1)];
			if (Maths.chance(chance * 0.01F) && entity != null && entity instanceof EntityLivingBase)
			{
				vectors.vecY = world.getTopSolidOrLiquidBlock((int)vectors.vecX, (int)vectors.vecZ);
				if (entity instanceof EntityTitan)
				{
					((EntityTitan) entity).destroyBlocksInAABBGriefingBypass(entity.boundingBox);
					((EntityTitan) entity).onSpawnWithEgg((IEntityLivingData)null);
				}

				entity.setLocationAndAngles(vectors.vecX + 0.5F + Maths.random(16), vectors.vecY, vectors.vecZ + 0.5F + Maths.random(16), Maths.random(360.0F), 0.0F);
				TheTitans.debug(entity.getCommandSenderName() +  "[" + (lastSpawn.size() - 1) + "] [" + vectors.distance(world.getSpawnPoint().posX, world.getSpawnPoint().posY, world.getSpawnPoint().posZ) +  "] spawned at X:" + entity.posX + ", Y:" + entity.posY + ", Z:" + entity.posZ);
				world.spawnEntityInWorld(entity);
				lastSpawn.put(entity, vectors);					
				break;
			}

			else
			{
				entity = null;
				attempts++;
			}
		}

		Maths.updateRandom();
	}
}


