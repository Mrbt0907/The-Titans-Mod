package net.minecraft.theTitans.world;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.titanminion.EntityDragonMinion;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
public class BiomeGenNowhere
extends BiomeGenBase
{
	@SuppressWarnings("unchecked")
	public BiomeGenNowhere(int p_i1990_1_)
	{
		super(p_i1990_1_);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityEnderman.class, 100, 12, 12));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityDragonMinion.class, 10, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityZombie.class, 1, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 1, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 1, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySpider.class, 1, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityCreeper.class, 1, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityCaveSpider.class, 1, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityGhast.class, 1, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 1, 1, 1));
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 1, 1, 1));
		this.topBlock = Blocks.stone;
		this.fillerBlock = Blocks.stone;
		this.theBiomeDecorator = new BiomeNowhereDecorator();
	}

	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float p_76731_1_)
	{
		return 0;
	}
}


