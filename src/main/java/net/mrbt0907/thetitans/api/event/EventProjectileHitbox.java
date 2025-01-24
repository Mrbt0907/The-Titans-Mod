package net.mrbt0907.thetitans.api.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.mrbt0907.thetitans.entity.EntityMultiPart;

public class EventProjectileHitbox extends Event
{
	public final EntityMultiPart entityPart;
	public final Entity projectile;
	
	public EventProjectileHitbox(EntityMultiPart entityPart, Entity projectile)
	{
		this.entityPart = entityPart;
		this.projectile = projectile;
	}
	
	@Override
	public boolean hasResult()
    {
        return true;
    }
	
	@Override
	public boolean isCancelable()
    {
        return true;
    }
}