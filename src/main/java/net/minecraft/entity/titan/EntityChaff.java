package net.minecraft.entity.titan;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class EntityChaff
extends Entity
{
	public int lifespan;
	public EntityChaff(World worldIn)
	{
		super(worldIn);
		setSize(1.0F, 1.0F);
		this.preventEntitySpawning = true;
	}

	protected void entityInit()
	{
		this.dataWatcher.addObject(8, Integer.valueOf(this.lifespan));
	}

	protected void readEntityFromNBT(NBTTagCompound tagCompund) 
	{
		 
	}


	protected void writeEntityToNBT(NBTTagCompound tagCompound) 
	{
		 
	}


	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.dataWatcher.updateObject(8, Integer.valueOf(this.lifespan));
		MathHelper.floor_double(this.posX);
		MathHelper.floor_double(this.posY);
		MathHelper.floor_double(this.posZ);
		float f = (this.rand.nextFloat() - 0.5F) * 16.0F;
		float f1 = (this.rand.nextFloat() - 0.5F) * 16.0F;
		float f2 = (this.rand.nextFloat() - 0.5F) * 16.0F;
		this.worldObj.spawnParticle("smoke", this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
		this.worldObj.spawnParticle("explode", this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
		this.lifespan += 1;
		if (this.lifespan == 1)
		{
			playSound("thetitans:chaffDeployment", 5.0F, 1.0F);
		}

		if (this.lifespan == 300)
		{
			setDead();
		}

		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(24.0D, 24.0D, 24.0D));
		if ((list != null) && (!list.isEmpty()))
		{
			for (int i1 = 0; i1 < list.size(); i1++)
			{
				Entity entity = (Entity)list.get(i1);
				if (((entity instanceof EntityHomingWitherSkull)) && (entity != null))
				{
					((EntityHomingWitherSkull)entity).assginedEntity = null;
				}
			}
		}
	}

	protected void collideWithNearbyEntities() 
	{
		 
	}
}


