package net.minecraft.entity.titan;
import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
public class EntityWebShot extends EntityFireball
{
	private int field_145795_e = -1;
	private int field_145793_f = -1;
	private int field_145794_g = -1;
	private Block field_145796_h;
	private boolean inGround;
	private int ticksAlive;
	public EntityWebShot(World worldIn)
	{
		super(worldIn);
		this.setSize(3F, 3F);
	}

	public EntityWebShot(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_)
	{
		super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
		this.setSize(3F, 3F);
	}

	public EntityWebShot(World worldIn, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_)
	{
		super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
		this.setSize(3F, 3F);
	}

	protected void onImpact(MovingObjectPosition movingObject)
	{
		if (!this.worldObj.isRemote)
		{
			if (movingObject.entityHit != null)
			{
				if (this.ticksExisted >= 5 && this.shootingEntity != null && this.shootingEntity instanceof EntitySpiderTitan && ((EntitySpiderTitan)this.shootingEntity).canAttackClass(movingObject.entityHit.getClass()))
				{
					((EntitySpiderTitan)this.shootingEntity).attackChoosenEntity(movingObject.entityHit, (float)((EntitySpiderTitan)this.shootingEntity).getAttackValue(2.0F), ((EntitySpiderTitan)this.shootingEntity).getKnockbackAmount());
					int i1 = MathHelper.floor_double(this.posY + 1);
					int i11 = MathHelper.floor_double(this.posX);
					int j1 = MathHelper.floor_double(this.posZ);
					for (int l1 = -2 - this.rand.nextInt(4); l1 <= 2 + this.rand.nextInt(4); l1++)
					{
						for (int i2 = -2 - this.rand.nextInt(4); i2 <= 2 + this.rand.nextInt(4); i2++)
						{
							for (int j = -2 - rand.nextInt(4); j <= 2 + rand.nextInt(4); j++)
							{
								int j2 = i11 + l1;
								int k = i1 + j;
								int l = j1 + i2;
								Block block1 = this.worldObj.getBlock(j2, k, l);
								if (!block1.isOpaqueCube())
								{
									this.worldObj.setBlock(j2, k, l, Blocks.web);
								}
							}
						}
					}

					this.setDead();
				}
			}

			else
			{
				int x = movingObject.blockX;
				int y = movingObject.blockY;
				int z = movingObject.blockZ;
				if (this.ticksExisted >= 5 && this.worldObj.getBlock(x, y, z) != Blocks.web)
				{
					switch (movingObject.sideHit)
					{
						case 0:
						--y;
						break;
						case 1:
						++y;
						break;
						case 2:
						--z;
						break;
						case 3:
						++z;
						break;
						case 4:
						--x;
						break;
						case 5:
						++x;
					}

					if (this.worldObj.isAirBlock(x, y, z))
					{
						int i1 = x;
						int i11 = y;
						int j1 = z;
						for (int l1 = -2 - this.rand.nextInt(4); l1 <= 2 + this.rand.nextInt(4); l1++)
						{
							for (int i2 = -2 - this.rand.nextInt(4); i2 <= 2 + this.rand.nextInt(4); i2++)
							{
								for (int j = -2 - rand.nextInt(4); j <= 2 + rand.nextInt(4); j++)
								{
									int j2 = i11 + l1;
									int k = i1 + j;
									int l = j1 + i2;
									Block block1 = this.worldObj.getBlock(j2, k, l);
									if (!block1.isOpaqueCube())
									{
										this.worldObj.setBlock(j2, k, l, Blocks.web);
									}
								}
							}
						}

						this.setDead();
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void onUpdate()
	{
		this.noClip = true;
		if ((this.shootingEntity != null && this.shootingEntity.isDead || !this.worldObj.blockExists((int)this.posX, (int)this.posY, (int)this.posZ)))
		{
			this.setDead();
		}

		else
		{
			this.onEntityUpdate();
			if (this.inGround)
			{
				if (this.worldObj.getBlock(this.field_145795_e, this.field_145793_f, this.field_145794_g) == this.field_145796_h)
				{
					++this.ticksAlive;
					if (this.ticksAlive == 600)
					{
						this.setDead();
					}

					return;
				}

				this.inGround = false;
				this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
				this.ticksAlive = 0;
			}

			else
			{
			}

			Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY + 1.5D, this.posZ);
			Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + 1.5D + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
			vec3 = Vec3.createVectorHelper(this.posX, this.posY + 1.5D, this.posZ);
			vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + 1.5D + this.motionY, this.posZ + this.motionZ);
			if (movingobjectposition != null)
			{
				vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(2.0D, 2.0D, 2.0D));
			if (list != null && !list.isEmpty())
			{
				for (int i = 0; i < list.size(); ++i)
				{
					Entity entity1 = (Entity)list.get(i);
					if (entity1.canBeCollidedWith() && this.ticksExisted >= 5)
					{
						float f = entity1.width;
						AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f, (double)f, (double)f);
						MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
						if (movingobjectposition1 != null)
						{
							entity = entity1;
						}
					}
				}
			}

			if (entity != null)
			{
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null && this.ticksExisted >= 5)
			{
				this.onImpact(movingobjectposition);
			}

			ArrayList<?> list1 = Lists.newArrayList(this.worldObj.loadedEntityList);
			if (list1 != null && !list1.isEmpty() && this.shootingEntity != null && this.shootingEntity instanceof EntityTitan && this.ticksExisted >= 5)
			{
				for (int i = 0; i < list1.size(); ++i)
				{
					Entity entity1 = (Entity)list1.get(i);
					if (this.isEntityAlive() && entity1 instanceof EntityLivingBase && !(entity1 instanceof EntityTitanPart) && !(entity1 instanceof EntityTitan) && ((EntityTitan)this.shootingEntity).canAttackClass(entity1.getClass()) && this.getDistanceSqToEntity(entity1) <= (this.width * this.width) + (entity1.width * entity1.width) + 4D)
					{
						this.onImpact(new MovingObjectPosition(entity1));
					}
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) + 90.0F;
			for (this.rotationPitch = (float)(Math.atan2((double)f1, this.motionY) * 180.0D / Math.PI) - 90.0F; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			{
				;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			{
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			{
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			{
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f2 = 0.99F;
			if (this.isInWater())
			{
				for (int j = 0; j < 4; ++j)
				{
					float f3 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f3, this.posY - this.motionY * (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ);
				}
			}

			this.motionX += this.accelerationX;
			this.motionY += this.accelerationY;
			this.motionZ += this.accelerationZ;
			this.motionX *= (double)f2;
			this.motionY *= (double)f2;
			this.motionZ *= (double)f2;
			this.worldObj.spawnParticle("explode", this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.0D, 0.0D);
			this.setPosition(this.posX, this.posY, this.posZ);
		}
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean isBurning()
	{
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return false;
	}

	public boolean isInWater()
	{
		return false;
	}
}


