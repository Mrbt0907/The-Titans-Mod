package net.minecraft.theTitans;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;
public class EntityGiantSmokeFX extends EntitySmokeFX
{
	public EntityGiantSmokeFX(World parWorld, double parX, double parY, double parZ, double parMotionX, double parMotionY, double parMotionZ)
	{
		super(parWorld, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);
		this.particleScale = 16F;
	}
}


