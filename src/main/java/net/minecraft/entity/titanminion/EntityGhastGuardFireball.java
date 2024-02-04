package net.minecraft.entity.titanminion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityGhastGuardFireball
extends EntityLargeFireball
{
	public EntityGhastGuardFireball(World worldIn)
	{
		super(worldIn);
	}

	@SideOnly(Side.CLIENT)
	public EntityGhastGuardFireball(World worldIn, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_)
	{
		super(worldIn, p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_);
	}

	public EntityGhastGuardFireball(World worldIn, EntityLivingBase p_i1769_2_, double p_i1769_3_, double p_i1769_5_, double p_i1769_7_)
	{
		super(worldIn, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
		if (!this.worldObj.isRemote)
		{
			if (movingObject.entityHit != null)
			{
				if (this.shootingEntity instanceof EntityGhastMinion)
				((EntityGhastMinion)this.shootingEntity).attackEntityAsMob(movingObject.entityHit);
				else
				movingObject.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), 17.0F);
			}

			boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			this.worldObj.newExplosion((Entity)null, this.posX, this.posY, this.posZ, this.field_92057_e, flag, flag);
			setDead();
		}
	}
}


