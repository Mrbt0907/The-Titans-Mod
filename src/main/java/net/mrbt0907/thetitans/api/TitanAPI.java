package net.mrbt0907.thetitans.api;

import net.minecraft.entity.Entity;
import net.mrbt0907.thetitans.entity.titan.EntityTitan;

public class TitanAPI
{
	public static boolean isEntityGiant(Entity entity)
	{
		return entity instanceof EntityTitan || entity != null && entity.height > 6.0F;
	}
}