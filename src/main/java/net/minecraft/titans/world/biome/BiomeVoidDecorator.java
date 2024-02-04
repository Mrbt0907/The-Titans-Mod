package net.minecraft.titans.world.biome;
import java.util.Random;

import net.minecraft.titans.entity.god.EntityWitherzilla;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class BiomeVoidDecorator extends BiomeDecorator
{
	protected World worldObj;
	public BiomeVoidDecorator() {}

	protected void genDecorations(Biome biomeIn, World world, Random random)
	{
		generateOres(world, random);

		if (chunkPos.getX() == 0 && chunkPos.getZ() == 0)
		{
			EntityWitherzilla witherzilla = new EntityWitherzilla(world);
			witherzilla.setLocationAndAngles(0.0D, 200.0D, 0.0D, random.nextFloat() * 360.0F, 0.0F);
			world.spawnEntity(witherzilla);
		}
	}
}


