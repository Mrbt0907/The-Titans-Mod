package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class EntityHarcadiumArrow
extends EntityArrow
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private Block inTile;
	private int inData;
	private boolean inGround;
	public int canBePickedUp;
	public int arrowShake;
	public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir;
	private double damage = 50D;
	private int knockbackStrength;
	private int piercing = 0;
	private Entity pierced;
	public EntityHarcadiumArrow(World worldIn)
	{
		super(worldIn);
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
	}

	public EntityHarcadiumArrow(World worldIn, double x, double y, double z)
	{
		super(worldIn);
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
		setPosition(x, y, z);
	}

	public EntityHarcadiumArrow(World worldIn, EntityLivingBase shooter, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_)
	{
		super(worldIn);
		renderDistanceWeight = 10.0D;
		shootingEntity = shooter;
		if ((shooter instanceof EntityPlayer))
		{
			canBePickedUp = 1;
		}

		posY = (shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D);
		double d0 = p_i1755_3_.posX - shooter.posX;
		double d1 = p_i1755_3_.boundingBox.minY + p_i1755_3_.height / 3.0F - posY;
		double d2 = p_i1755_3_.posZ - shooter.posZ;
		double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
		if (d3 >= 1.0E-7D)
		{
			float f2 = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F;
			float f3 = (float)-(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
			double d4 = d0 / d3;
			double d5 = d2 / d3;
			setLocationAndAngles(shooter.posX + d4, posY, shooter.posZ + d5, f2, f3);
			float f4 = (float)(d3 * 0.20000000298023224D);
			setThrowableHeading(d0, d1 + f4, d2, p_i1755_4_, p_i1755_5_);
		}
	}

	public EntityHarcadiumArrow(World worldIn, EntityLivingBase shooter, float p_i1756_3_)
	{
		super(worldIn);
		renderDistanceWeight = 10.0D;
		shootingEntity = shooter;
		if ((shooter instanceof EntityPlayer))
		{
			canBePickedUp = 1;
		}

		setSize(0.5F, 0.5F);
		setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * 0.16F;
		setPosition(posX, posY, posZ);
		motionX = (-MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F));
		motionZ = (MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F));
		motionY = (-MathHelper.sin(rotationPitch / 180.0F * 3.1415927F));
		setThrowableHeading(motionX, motionY, motionZ, p_i1756_3_ * 1.5F, 1.0F);
	}

	protected void entityInit()
	{
		dataWatcher.addObject(16, Byte.valueOf((byte)0));
		dataWatcher.addObject(19, Integer.valueOf(0));
	}

	public int getArrowType()
	{
		return dataWatcher.getWatchableObjectInt(19);
	}

	public void setArrowType(int type)
	{
		dataWatcher.updateObject(19, Integer.valueOf(type));
	}

	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
	{
		float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= f2;
		y /= f2;
		z /= f2;
		x += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * inaccuracy;
		y += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * inaccuracy;
		z += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * inaccuracy;
		x *= velocity;
		y *= velocity;
		z *= velocity;
		motionX = x;
		motionY = y;
		motionZ = z;
		float f3 = MathHelper.sqrt_double(x * x + z * z);
		prevRotationYaw = (rotationYaw = (float)(Math.atan2(x, z) * 180.0D / 3.141592653589793D));
		prevRotationPitch = (rotationPitch = (float)(Math.atan2(y, f3) * 180.0D / 3.141592653589793D));
		ticksInGround = 0;
	}

	@SideOnly(Side.CLIENT)
	public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_)
	{
		setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
		setRotation(p_180426_7_, p_180426_8_);
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z)
	{
		motionX = x;
		motionY = y;
		motionZ = z;
		if ((prevRotationPitch == 0.0F) && (prevRotationYaw == 0.0F))
		{
			float f = MathHelper.sqrt_double(x * x + z * z);
			prevRotationYaw = (rotationYaw = (float)(Math.atan2(x, z) * 180.0D / 3.141592653589793D));
			prevRotationPitch = (rotationPitch = (float)(Math.atan2(y, f) * 180.0D / 3.141592653589793D));
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			ticksInGround = 0;
		}
	}

	public void onUpdate()
	{
		onEntityUpdate();
		net.mrbt0907.utils.Maths.Vec3 vector = new net.mrbt0907.utils.Maths.Vec3(motionX, motionY, motionZ);
		if (worldObj.isRemote && getArrowType() == 1)
		if (!inGround && vector.speed() > 0.1D)
		for (int i = 0; i < 2; i++)
		worldObj.spawnParticle("largesmoke", posX + motionX * i / 4.0D, posY + motionY * i / 4.0D, posZ + motionZ * i / 4.0D, -(vector.vecX * 0.25), -(vector.vecY * 0.25), -(vector.vecZ * 0.25));
		else if (ticksExisted % 2 == 0)
		for (int i = 0; i < 2; i++)
		worldObj.spawnParticle("smoke", posX + Maths.random(-0.05, 0.05), posY + Maths.random(-0.05, 0.05), posZ + Maths.random(-0.05, 0.05), 0.0D, 0.0D, 0.0D);
		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float)(Math.atan2(motionY, (double)f) * 180.0D / Math.PI);
		}

		Block block = worldObj.getBlock(xTile, yTile, zTile);
		if (block.getMaterial() != Material.air)
		{
			block.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
			if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(posX, posY, posZ)))
			inGround = true;
		}

		if (arrowShake > 0)
		--arrowShake;
		if (inGround)
		{
			int j = worldObj.getBlockMetadata(xTile, yTile, zTile);
			if ((block == inTile) && (j == inData))
			{
				ticksInGround += 1;
				if (shootingEntity != null && (shootingEntity instanceof EntitySkeletonTitan || (shootingEntity instanceof EntityLivingBase && ((EntityLivingBase)shootingEntity).getHeldItem() != null && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, ((EntityLivingBase)shootingEntity).getHeldItem()) > 0)))
				{
					worldObj.newExplosion(this, posX, posY, posZ, 1F + (rand.nextFloat() * 7F), false, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					setDead();
				}

				if (ticksInGround >= 1200 || (ticksInGround >= 100 && !(shootingEntity instanceof EntityPlayer)))
				setDead();
			}

			else
			{
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksInGround = 0;
				ticksInAir = 0;
			}
		}

		else
		{
			ticksInAir += 1;
			Vec3 vec31 = Vec3.createVectorHelper(posX, posY, posZ);
			Vec3 vec3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition movingobjectposition = worldObj.func_147447_a(vec31, vec3, false, true, false);
			vec31 = Vec3.createVectorHelper(posX, posY, posZ);
			vec3 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
			if (movingobjectposition != null)
			vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			Entity entity = null;
			List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			for (int i = 0; i < list.size(); i++)
			{
				Entity entity1 = (Entity)list.get(i);
				if (entity1 != pierced && (entity1.canBeCollidedWith()) && ((entity1 != shootingEntity) || (ticksInAir >= 5)))
				{
					float f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);
					if (movingobjectposition1 != null)
					{
						double d1 = vec31.distanceTo(movingobjectposition1.hitVec);
						if ((d1 < d0) || (d0 == 0.0D))
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null)
			movingobjectposition = new MovingObjectPosition(entity);
			if ((movingobjectposition != null) && (movingobjectposition.entityHit != null) && ((movingobjectposition.entityHit instanceof EntityPlayer)))
			{
				EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
				if ((entityplayer.capabilities.disableDamage) || (((shootingEntity instanceof EntityPlayer)) && (!((EntityPlayer)shootingEntity).canAttackPlayer(entityplayer))))
				movingobjectposition = null;
			}

			if (movingobjectposition != null)
			if (movingobjectposition.entityHit != null)
			{
				float f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
				int k = MathHelper.ceiling_double_int(f2 * damage);
				if (getIsCritical())
				k *= 2;
				if (getArrowType() == 1)
				if (movingobjectposition.entityHit.hurtResistantTime > 5)
				movingobjectposition.entityHit.hurtResistantTime = 5;
				else
				if (movingobjectposition.entityHit.hurtResistantTime > 10)
				movingobjectposition.entityHit.hurtResistantTime = 10;
				DamageSource damagesource;
				if (shootingEntity == null)
				damagesource = DamageSourceExtra.causeHarcadiumArrowDamage(this, this);
				else
				damagesource = DamageSourceExtra.causeHarcadiumArrowDamage(this, shootingEntity);
				if (isBurning())
				movingobjectposition.entityHit.setFire(150);
				if (movingobjectposition.entityHit.attackEntityFrom(damagesource, k))
				{
					if (movingobjectposition.entityHit instanceof EntityLivingBase)
					{
						EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;
						if (!worldObj.isRemote)
						entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
						if (knockbackStrength > 0)
						{
							float f4 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
							if (f4 > 0.0F)
							movingobjectposition.entityHit.addVelocity(motionX * knockbackStrength * 1.5D / f4, 0.75D, motionZ * knockbackStrength * 1.5D / f4);
						}

						if ((shootingEntity instanceof EntityLivingBase))
						{
							EnchantmentHelper.func_151384_a(entitylivingbase, shootingEntity);
							EnchantmentHelper.func_151385_b((EntityLivingBase)shootingEntity, entitylivingbase);
						}

						if ((shootingEntity != null) && (movingobjectposition.entityHit != shootingEntity) && ((movingobjectposition.entityHit instanceof EntityPlayer)) && ((shootingEntity instanceof EntityPlayerMP)))
						((EntityPlayerMP)shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
					}

					playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					piercing ++;
					if (!(movingobjectposition.entityHit instanceof EntityEnderman) && ((getArrowType() == 1 && piercing > 9) || (piercing > 4)))
					{
						setDead();
						if (canBePickedUp == 1)
						switch (getArrowType())
						{
							case 1:
							movingobjectposition.entityHit.dropItem(TitanItems.voidArrow, 1);
							break;
							default:
							movingobjectposition.entityHit.dropItem(TitanItems.harcadiumArrow, 1);
						}
					}
				}
			}

			else
			{
				xTile = movingobjectposition.blockX;
				yTile = movingobjectposition.blockY;
				zTile = movingobjectposition.blockZ;
				inTile = worldObj.getBlock(xTile, yTile, zTile);
				inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
				if (inTile instanceof BlockGlass)
				{
					playSound("dig.glass", 1.0F, 1.0F / (rand.nextFloat() * 0.2F + 0.9F));
					worldObj.setBlock(xTile, yTile, zTile, Blocks.air);
				}

				else
				{
					motionX = ((float)(movingobjectposition.hitVec.xCoord - posX));
					motionY = ((float)(movingobjectposition.hitVec.yCoord - posY));
					motionZ = ((float)(movingobjectposition.hitVec.zCoord - posZ));
					float f2 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					posX -= motionX / f2 * 0.05000000074505806D;
					posY -= motionY / f2 * 0.05000000074505806D;
					posZ -= motionZ / f2 * 0.05000000074505806D;
					inGround = true;
					arrowShake = 7;
					playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					setIsCritical(false);
					if (inTile.getMaterial() != Material.air)
					inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
				}
			}

			if (getIsCritical())
			for (int i = 0; i < 4; i++)
			worldObj.spawnParticle("critMagic", posX + motionX * i / 4.0D, posY + motionY * i / 4.0D, posZ + motionZ * i / 4.0D, -motionX, -motionY + 0.2D, -motionZ);
			for (int i = 0; i < 4; i++)
			worldObj.spawnParticle("crit", posX + motionX * i / 4.0D, posY + motionY * i / 4.0D, posZ + motionZ * i / 4.0D, -motionX, -motionY + 0.2D, -motionZ);
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			float f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			for (rotationPitch = (float)(Math.atan2(motionY, (double)f2) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F);
			while (rotationPitch - prevRotationPitch >= 180.0F)
			prevRotationPitch += 360.0F;
			while (rotationYaw - prevRotationYaw < -180.0F)
			prevRotationYaw -= 360.0F;
			while (rotationYaw - prevRotationYaw >= 180.0F)
			prevRotationYaw += 360.0F;
			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float f3 = 0.99F;
			float f1 = 0.05F;
			if (isInWater())
			{
				for (int l = 0; l < 4; ++l)
				{
					float f4 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * (double)f4, posY - motionY * (double)f4, posZ - motionZ * (double)f4, motionX, motionY, motionZ);
				}

				f3 = 0.8F;
			}

			if (isWet())
			extinguish();
			motionX *= (double)f3;
			motionY *= (double)f3;
			motionZ *= (double)f3;
			motionY -= (double)f1;
			setPosition(posX, posY, posZ);
			func_145775_I();
		}
	}

	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setShort("xTile", (short)xTile);
		p_70014_1_.setShort("yTile", (short)yTile);
		p_70014_1_.setShort("zTile", (short)zTile);
		p_70014_1_.setShort("life", (short)ticksInGround);
		p_70014_1_.setByte("inTile", (byte)Block.getIdFromBlock(inTile));
		p_70014_1_.setByte("inData", (byte)inData);
		p_70014_1_.setByte("shake", (byte)arrowShake);
		p_70014_1_.setByte("inGround", (byte)(inGround ? 1 : 0));
		p_70014_1_.setByte("pickup", (byte)canBePickedUp);
		p_70014_1_.setDouble("damage", damage);
		p_70014_1_.setInteger("ArrowType", getArrowType());
	}

	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		setArrowType(p_70037_1_.getInteger("ArrowType"));
		xTile = p_70037_1_.getShort("xTile");
		yTile = p_70037_1_.getShort("yTile");
		zTile = p_70037_1_.getShort("zTile");
		ticksInGround = p_70037_1_.getShort("life");
		inTile = Block.getBlockById(p_70037_1_.getByte("inTile") & 0xFF);
		inData = (p_70037_1_.getByte("inData") & 0xFF);
		arrowShake = (p_70037_1_.getByte("shake") & 0xFF);
		inGround = (p_70037_1_.getByte("inGround") == 1);
		if (p_70037_1_.hasKey("damage", 99))
		{
			damage = p_70037_1_.getDouble("damage");
		}

		if (p_70037_1_.hasKey("pickup", 99))
		{
			canBePickedUp = p_70037_1_.getByte("pickup");

		}

		 else if (p_70037_1_.hasKey("player", 99))
		{
			canBePickedUp = (p_70037_1_.getBoolean("player") ? 1 : 0);
		}
	}

	public void onCollideWithPlayer(EntityPlayer entityIn)
	{
		if ((!worldObj.isRemote) && (inGround) && (arrowShake <= 0))
		{
			boolean flag = (canBePickedUp == 1) || ((canBePickedUp == 2) && (entityIn.capabilities.isCreativeMode));
			if ((canBePickedUp == 1) && (!entityIn.inventory.addItemStackToInventory(new ItemStack(getArrowType() == 1 ? TitanItems.voidArrow : TitanItems.harcadiumArrow, 1))))
			{
				flag = false;
			}

			if (flag)
			{
				playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				entityIn.onItemPickup(this, 1);
				setDead();
			}
		}
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public void setDamage(double p_70239_1_)
	{
		damage = p_70239_1_;
	}

	public double getDamage()
	{
		return damage;
	}

	public void setKnockbackStrength(int p_70240_1_)
	{
		knockbackStrength = p_70240_1_;
	}

	public boolean canAttackWithItem()
	{
		return false;
	}

	public void setIsCritical(boolean p_70243_1_)
	{
		byte b0 = dataWatcher.getWatchableObjectByte(16);
		if (p_70243_1_)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));

		}

		 else 
		{

			dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
		}
	}

	public boolean getIsCritical()
	{
		byte b0 = dataWatcher.getWatchableObjectByte(16);
		return (b0 & 0x1) != 0;
	}
}


