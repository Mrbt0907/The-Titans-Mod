package net.minecraft.titans.entity;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.endermanofdoom.mac.util.math.Maths;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityMultiPart extends MultiPartEntityPart
{
	public boolean canDamage;
	public int xOffset;
	public int yOffset;
	public int zOffset;
	
	public EntityMultiPart(IEntityMultiPart parent, String partName, int xOffset, int yOffset, int zOffset, float width, float height, boolean canDamage)
	{
		super(parent, partName, width, height);
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
		world.getEntitiesInAABBexcluding(this, getEntityBoundingBox(), new Predicate<Entity>() { @Override public boolean apply(Entity input) {return input instanceof IProjectile;}}).forEach(entity -> 
		{
			if (entity instanceof EntityArrow)
				attackEntityFrom(DamageSource.causeArrowDamage(((EntityArrow)entity), ((EntityArrow)entity).shootingEntity != null ? ((EntityArrow)entity).shootingEntity : ((EntityArrow)entity)), (float) (Maths.speedSq(entity.motionX, entity.motionY, entity.motionZ) * ((EntityArrow)entity).getDamage()));
			else
				attackEntityFrom(DamageSource.GENERIC, (float) Maths.speedSq(entity.motionX, entity.motionY, entity.motionZ) * 2.0F);
			entity.setDead();
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