package net.mrbt0907.thetitans.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.mrbt0907.thetitans.api.event.EventProjectileHitbox;
import net.mrbt0907.util.util.WorldUtil;
import net.mrbt0907.util.util.math.Maths;

public class EntityMultiPart extends MultiPartEntityPart
{
	public static final Predicate<Entity> IS_PROJECTILE = new Predicate<Entity>()
	{
		public boolean apply(Entity input)
		{
			return input instanceof IProjectile || input instanceof EntityFireball || input instanceof EntityEvokerFangs || input instanceof EntityFishHook || input instanceof EntityShulkerBullet;
		}
	};
	public final boolean canCrush;
	public boolean canDamage;
	public int xOffset;
	public int yOffset;
	public int zOffset;
	
	public EntityMultiPart(IEntityMultiPart parent, String partName, int xOffset, int yOffset, int zOffset, float width, float height, boolean canDamage, boolean canCrush)
	{
		super(parent, partName, width, height);
		this.canCrush = canCrush;
		this.canDamage = canDamage;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = -zOffset;
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}
	
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return null;
    }
	
	public void onUpdate()
	{
		WorldUtil.getEntities(this, getEntityBoundingBox(), IS_PROJECTILE).forEach(projectile -> 
		{
			EventProjectileHitbox event = new EventProjectileHitbox(this, projectile);
			MinecraftForge.EVENT_BUS.post(event);
			
			switch(event.getResult())
			{
				case ALLOW:
					break;
				case DENY:
					projectile.setDead();
					break;
				default:
					attackEntityFrom(DamageSource.GENERIC, (float) Maths.speedSq(projectile.motionX, projectile.motionY, projectile.motionZ) * 2.0F);
					projectile.setDead();
			}
		});
	}
	
	@Override
	public boolean isEntityInvulnerable(DamageSource source)
    {
        return !canDamage;
    }
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return isEntityInvulnerable(source) ? false : parent.attackEntityFromPart(this, source, amount);
	}
}