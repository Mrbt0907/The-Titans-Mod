package net.minecraft.entity.titan;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.titanminion.EntityGhastGuard;
import net.minecraft.entity.titanminion.EntityGiantZombieBetter;
import net.minecraft.entity.titanminion.EntityPigZombieMinion;
import net.minecraft.entity.titanminion.EntityZombieMinion;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityProtoBall
extends EntityThrowable
{
	public EntityProtoBall(World worldIn)
	{
		super(worldIn);
		setSize(3.0F, 3.0F);
		this.motionY += 0.25F;
	}

	public EntityProtoBall(World worldIn, EntityLivingBase p_i1780_2_)
	{
		super(worldIn, p_i1780_2_);
		setSize(3.0F, 3.0F);
		this.motionY += 0.25F;
	}

	public EntityProtoBall(World worldIn, double p_i1781_2_, double p_i1781_4_, double p_i1781_6_)
	{
		super(worldIn, p_i1781_2_, p_i1781_4_, p_i1781_6_);
		setSize(3.0F, 3.0F);
		this.motionY += 0.25F;
	}

	public void onUpdate()
	{
		super.onUpdate();
		for (int i = 0; i < 15; i++)
		{
			float f = (this.rand.nextFloat() - 0.5F) * this.width;
			float f1 = (this.rand.nextFloat() - 0.5F) * this.height;
			float f2 = (this.rand.nextFloat() - 0.5F) * this.width;
			this.worldObj.spawnParticle("largeexplode", this.posX + f, this.posY + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("explode", this.posX + f, this.posY + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("fire", this.posX + f, this.posY + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("smoke", this.posX + f, this.posY + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("lava", this.posX + f, this.posY + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
		}
	}

	protected void onImpact(MovingObjectPosition p_70184_1_)
	{
		if (!this.worldObj.isRemote)
		{
			if (this.getThrower() != null && this.getThrower() instanceof EntityTitan && p_70184_1_.entityHit != null && p_70184_1_.entityHit instanceof EntityLivingBase)
			((EntityTitan)this.getThrower()).attackChoosenEntity(p_70184_1_.entityHit, 75, 2);
			if (this.getThrower() != null && this.getThrower() instanceof EntityPigZombieTitan)
			{
				if (this.rand.nextInt(5) == 0)
				{
					EntityGhastGuard entitychicken = new EntityGhastGuard(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					++entitychicken.motionY;
					boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
					this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 6D, entitychicken.posZ, 12F, false, flag);
					entitychicken.master = (EntityPigZombieTitan) this.getThrower();
				}

				else
				{
					switch (this.rand.nextInt(4))
					{
						case 0:for (int l1 = 0; l1 <= 5; l1++)
						{
							EntityPigZombieMinion entitychicken = new EntityPigZombieMinion(this.worldObj);
							entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
							this.worldObj.spawnEntityInWorld(entitychicken);
							entitychicken.onSpawnWithEgg((IEntityLivingData)null);
							entitychicken.setMinionType(3);
							boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
							this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 2D, entitychicken.posZ, 6F, false, flag);
							entitychicken.master = (EntityPigZombieTitan) this.getThrower();
						}

						break;
						case 1:for (int l1 = 0; l1 <= 10; l1++)
						{
							EntityPigZombieMinion entitychicken = new EntityPigZombieMinion(this.worldObj);
							entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
							this.worldObj.spawnEntityInWorld(entitychicken);
							entitychicken.onSpawnWithEgg((IEntityLivingData)null);
							entitychicken.setMinionType(2);
							boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
							this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 2D, entitychicken.posZ, 4F, false, flag);
							entitychicken.master = (EntityPigZombieTitan) this.getThrower();
						}

						break;
						case 2:for (int l1 = 0; l1 <= 20; l1++)
						{
							EntityPigZombieMinion entitychicken = new EntityPigZombieMinion(this.worldObj);
							entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
							this.worldObj.spawnEntityInWorld(entitychicken);
							entitychicken.onSpawnWithEgg((IEntityLivingData)null);
							entitychicken.setMinionType(1);
							boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
							this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 2D, entitychicken.posZ, 3F, false, flag);
							entitychicken.master = (EntityPigZombieTitan) this.getThrower();
						}

						break;
						case 3:for (int l1 = 0; l1 <= 40; l1++)
						{
							EntityPigZombieMinion entitychicken = new EntityPigZombieMinion(this.worldObj);
							entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
							this.worldObj.spawnEntityInWorld(entitychicken);
							entitychicken.onSpawnWithEgg((IEntityLivingData)null);
							entitychicken.setMinionType(0);
							boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
							this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 2D, entitychicken.posZ, 2F, false, flag);
							entitychicken.master = (EntityPigZombieTitan) this.getThrower();
						}
					}
				}
			}

			if (this.getThrower() != null && this.getThrower() instanceof EntityZombieTitan)
			{
				if (this.rand.nextInt(5) == 0)
				{
					EntityGiantZombieBetter entitychicken = new EntityGiantZombieBetter(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
					this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 6D, entitychicken.posZ, 12F, false, flag);
					entitychicken.master = (EntityZombieTitan) this.getThrower();
				}

				else
				{
					switch (this.rand.nextInt(4))
					{
						case 0:for (int l1 = 0; l1 <= 5; l1++)
						{
							EntityZombieMinion entitychicken = new EntityZombieMinion(this.worldObj);
							entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
							this.worldObj.spawnEntityInWorld(entitychicken);
							entitychicken.onSpawnWithEgg((IEntityLivingData)null);
							entitychicken.setMinionType(3);
							boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
							this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 2D, entitychicken.posZ, 6F, false, flag);
							entitychicken.master = (EntityZombieTitan) this.getThrower();
						}

						break;
						case 1:for (int l1 = 0; l1 <= 10; l1++)
						{
							EntityZombieMinion entitychicken = new EntityZombieMinion(this.worldObj);
							entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
							this.worldObj.spawnEntityInWorld(entitychicken);
							entitychicken.onSpawnWithEgg((IEntityLivingData)null);
							entitychicken.setMinionType(2);
							boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
							this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 2D, entitychicken.posZ, 4F, false, flag);
							entitychicken.master = (EntityZombieTitan) this.getThrower();
						}

						break;
						case 2:for (int l1 = 0; l1 <= 20; l1++)
						{
							EntityZombieMinion entitychicken = new EntityZombieMinion(this.worldObj);
							entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
							this.worldObj.spawnEntityInWorld(entitychicken);
							entitychicken.onSpawnWithEgg((IEntityLivingData)null);
							entitychicken.setMinionType(1);
							boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
							this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 2D, entitychicken.posZ, 3F, false, flag);
							entitychicken.master = (EntityZombieTitan) this.getThrower();
						}

						break;
						case 3:for (int l1 = 0; l1 <= 40; l1++)
						{
							EntityZombieMinion entitychicken = new EntityZombieMinion(this.worldObj);
							entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, -this.rotationYaw, -this.rotationPitch);
							this.worldObj.spawnEntityInWorld(entitychicken);
							entitychicken.onSpawnWithEgg((IEntityLivingData)null);
							entitychicken.setMinionType(0);
							boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
							this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + 2D, entitychicken.posZ, 2F, false, flag);
							entitychicken.master = (EntityZombieTitan) this.getThrower();
						}
					}
				}
			}
		}

		setDead();
	}
}


