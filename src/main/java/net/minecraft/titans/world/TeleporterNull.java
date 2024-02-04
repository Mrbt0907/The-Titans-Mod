package net.minecraft.titans.world;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
public class TeleporterNull extends Teleporter
{
	public TeleporterNull(WorldServer p_i1963_1_)
	{
		super(p_i1963_1_);
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw)
	{
		int i = MathHelper.floor(entity.posX);
		int j = MathHelper.floor(entity.posY) - 1;
		int k = MathHelper.floor(entity.posZ);
		entity.setLocationAndAngles((double)i, (double)j, (double)k, rotationYaw, 0.0F);
		entity.motionX = entity.motionY = entity.motionZ = 0.0D;
	}

	/**
	* Place an entity in a nearby portal which already exists.
	*/
	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw)
	{
		int i = MathHelper.floor(entity.posX);
		int j = MathHelper.floor(entity.posY) - 1;
		int k = MathHelper.floor(entity.posZ);
		entity.setLocationAndAngles((double)i, (double)j, (double)k, rotationYaw, 0.0F);
		entity.motionX = entity.motionY = entity.motionZ = 0.0D;
		return true;
	}

	@Override
	public boolean makePortal(Entity p_85188_1_)
	{
		return true;
	}

	/**
	* called periodically to remove out-of-date portal locations from the cache list. Argument par1 is a
	* WorldServer.getTotalWorldTime() value.
	*/
	@Override
	public void removeStalePortalLocations(long p_85189_1_)
	{
	}
}


