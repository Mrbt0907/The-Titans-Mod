package net.minecraft.theTitans.world;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
public class WorldGeneratorBase extends WorldGenerator
{
	public int separation = 250;
	public boolean generate(World worldObj, Random rand, int posX, int posY, int posZ)
	{
		return false;
	}
}


