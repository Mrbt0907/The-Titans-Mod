package net.mrbt0907.thetitans.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.mrbt0907.thetitans.api.event.EventProjectileHitbox;

public class InternalEventHandler
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onProjectileHitbox(EventProjectileHitbox event)
	{
		RayTraceResult result = new RayTraceResult(event.entityPart);
		if (event.projectile instanceof EntityArrow)
		{
			((EntityArrow)event.projectile).onHit(result);
			event.setResult(Result.ALLOW);
			event.setCanceled(true);
		}
		/*else if (event.projectile instanceof EntityFireball)
		{
			((EntityFireball)event.projectile).onImpact(result);
			event.setResult(Result.ALLOW);
			event.setCanceled(true);
		}
		else if (event.projectile instanceof EntityThrowable)
		{
			((EntityThrowable)event.projectile).onImpact(result);
			event.setResult(Result.ALLOW);
			event.setCanceled(true);
		}*/
		else if (event.projectile instanceof EntityEvokerFangs)
		{
			if (event.entityPart.parent instanceof EntityLivingBase)
				((EntityEvokerFangs)event.projectile).damage((EntityLivingBase) event.entityPart.parent);
			event.setResult(Result.ALLOW);
			event.setCanceled(true);
		}
		else if (event.projectile instanceof EntityFishHook)
		{
			event.setResult(Result.DENY);
			event.setCanceled(true);
		}
		else if (event.projectile instanceof EntityLlamaSpit)
		{
			((EntityLlamaSpit)event.projectile).onHit(result);
			event.setResult(Result.ALLOW);
			event.setCanceled(true);
		}
		else if (event.projectile instanceof EntityShulkerBullet)
		{
			((EntityShulkerBullet)event.projectile).bulletHit(result);
			event.setResult(Result.ALLOW);
			event.setCanceled(true);
		}
	}
}
