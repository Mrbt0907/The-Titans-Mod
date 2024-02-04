package net.minecraft.theTitans.events;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
public class TeleporterNull extends Teleporter
{
	public TeleporterNull(WorldServer p_i1963_1_)
	{
		super(p_i1963_1_);
	}

	@Override
	public void placeInPortal(Entity p_77185_1_, double p_77185_2_, double p_77185_4_, double p_77185_6_, float p_77185_8_)
	{
		int i = MathHelper.floor_double(p_77185_1_.posX);
		int j = MathHelper.floor_double(p_77185_1_.posY) - 1;
		int k = MathHelper.floor_double(p_77185_1_.posZ);
		p_77185_1_.setLocationAndAngles((double)i, (double)j, (double)k, p_77185_1_.rotationYaw, 0.0F);
		p_77185_1_.motionX = p_77185_1_.motionY = p_77185_1_.motionZ = 0.0D;
	}

	/**
	* Place an entity in a nearby portal which already exists.
	*/
	@Override
	public boolean placeInExistingPortal(Entity p_77184_1_, double p_77184_2_, double p_77184_4_, double p_77184_6_, float p_77184_8_)
	{
		int i = MathHelper.floor_double(p_77184_1_.posX);
		int j = MathHelper.floor_double(p_77184_1_.posY) - 1;
		int k = MathHelper.floor_double(p_77184_1_.posZ);
		p_77184_1_.setLocationAndAngles((double)i, (double)j, (double)k, p_77184_1_.rotationYaw, 0.0F);
		p_77184_1_.motionX = p_77184_1_.motionY = p_77184_1_.motionZ = 0.0D;
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


