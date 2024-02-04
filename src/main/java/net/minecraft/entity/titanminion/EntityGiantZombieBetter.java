package net.minecraft.entity.titanminion;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.entity.titan.ITitan;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.theTitans.TargetingSorter;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.events.EventData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class EntityGiantZombieBetter extends EntityGiantZombie implements IMinion
{
	public EntityLiving master;
	private TargetingSorter TargetSorter = null;
	public EntityGiantZombieBetter(World worldIn)
	{
		super(worldIn);
		setSize(3.0F, 12.0F);
		this.stepHeight = 3F;
		func_110163_bv();
		this.TargetSorter = new TargetingSorter(this);
		this.isImmuneToFire = true;
		this.experienceValue = 1000;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.ZombieTitanSorter));
		else
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	public int getTotalArmorValue()
	{
		return 20;
	}

	public void setDead()
	{
		super.setDead();
		if ((this.master != null) && ((this.master instanceof EntityTitan)))
		{
			((EntityTitan)this.master).retractMinionNumFromType(getMinionType());
		}
	}

	public EnumMinionType getMinionType()
	{
		return EnumMinionType.SPECIAL;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return EventData.isTitanMode(worldObj) && super.getCanSpawnHere();
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityZombieMinion.class) && (p_70686_1_ != EntityGiantZombieBetter.class) && (p_70686_1_ != EntityZombieTitan.class) || (Loader.isModLoaded("MutantCreatures") && p_70686_1_ == thehippomaster.MutantCreatures.MutantZombie.class);
	}

	protected void despawnEntity()
	{
		this.entityAge = 0;
	}

	public float getEyeHeight()
	{
		return 10.440001F;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20000.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
	}

	private EntityLivingBase doJumpDamage(double X, double Y, double Z, double dist, double damage, int knock)
	{
		List<?> var5 = this.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(1, 1, 1), ITitan.ZombieTitanSorter);
		Collections.sort(var5, this.TargetSorter);
		Iterator<?> var2 = var5.iterator();
		Entity var3 = null;
		EntityLivingBase var4 = null;
		while (var2.hasNext())
		{
			var3 = (Entity)var2.next();
			var4 = (EntityLivingBase)var3;
			if ((var4 != null) &&(var4 != this) &&(var4.isEntityAlive()) &&(!(var4 instanceof EntityZombieTitan)) &&(!(var4 instanceof EntityGiantZombieBetter)) &&(!(var4 instanceof EntityZombieMinion)))
			{
				DamageSource var21 = null;
				var21 = DamageSource.setExplosionSource(null);
				var21.setExplosion();
				if (rand.nextInt(2) == 0)
				var21.setDamageBypassesArmor();
				var4.attackEntityFrom(var21, (float)damage);
				var4.attackEntityFrom(DamageSource.fall, (float)damage / 4.0F);
				this.worldObj.playSoundAtEntity(var4, "random.explode", 0.85F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
				if (knock != 0)
				{
					double ks = 0.25D + rand.nextDouble() + rand.nextDouble();
					double inair = 0.25D;
					float f3 = (float)Math.atan2(var4.posZ - this.posZ, var4.posX - this.posX);
					var4.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
					if (rand.nextInt(5) == 0)
					var4.hurtResistantTime = 0;
				}
			}
		}

		return null;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if (this.ticksExisted % 20 == 0 && (!this.worldObj.isDaytime() || this.rand.nextInt(5) == 0))
		heal(this.worldObj.isDaytime() ? (1F + (this.rand.nextFloat() * 4F)) : (5F + (this.rand.nextFloat() * 15F)));
		if (this.worldObj.isRemote)
		setSize(3.0F, 12.0F);
		else
		setSize(1.5F, 6.0F);
		this.ignoreFrustumCheck = true;
		if (this.motionX != 0D && this.motionZ != 0D && (this.rand.nextInt(5) == 0))
		{
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY - 0.20000000298023224D - this.yOffset);
			int k = MathHelper.floor_double(this.posZ);
			Block block = this.worldObj.getBlock(i, j, k);
			if (block.getMaterial() != Material.air)
			{
				this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + this.worldObj.getBlockMetadata(i, j, k), this.posX + (this.rand.nextFloat() - 0.5D) * this.width, this.boundingBox.minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D);
			}
		}
	}

	public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_)
	{
		return 0.5F - this.worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_);
	}

	protected String getLivingSound()
	{
		return "mob.zombie.say";
	}

	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}

	protected String getDeathSound()
	{
		return "mob.zombie.death";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		playSound("mob.irongolem.walk", 2.0F, 0.9F);
		double dx = this.posX + 4.0D * -Math.sin(Math.toRadians(this.renderYawOffset));
		double dz = this.posZ - 4.0D * -Math.cos(Math.toRadians(this.renderYawOffset));
		doJumpDamage(dx, this.posY, dz, 6.0D, 10 + rand.nextInt(90), 1);
	}

	protected float getSoundVolume()
	{
		return 7.0F;
	}

	protected Item getDropItem()
	{
		return Items.rotten_flesh;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		int j = this.rand.nextInt(13) + this.rand.nextInt(1 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.feather, 1);
		}

		j = this.rand.nextInt(13) + this.rand.nextInt(2 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.rotten_flesh, 1);
		}

		if ((p_70628_1_) && ((this.rand.nextInt(5) == 0) || (this.rand.nextInt(1 + p_70628_2_) > 0)))
		{
			dropItem(Items.iron_ingot, 1);
		}

		if ((p_70628_1_) && ((this.rand.nextInt(5) == 0) || (this.rand.nextInt(1 + p_70628_2_) > 0)))
		{
			dropItem(Items.carrot, 1);
		}

		if ((p_70628_1_) && ((this.rand.nextInt(5) == 0) || (this.rand.nextInt(1 + p_70628_2_) > 0)))
		{
			dropItem(Items.potato, 1);
		}
	}

	protected void addRandomArmor()
	{
		switch (this.rand.nextInt(3))
		{
			case 0:dropItem(Items.iron_shovel, 1);
			break;
			case 1:dropItem(Items.iron_sword, 1);
			break;
			case 2:dropItem(Items.iron_helmet, 1);
		}
	}

	protected float getSoundPitch()
	{
		return isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.0F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 0.5F;
	}

	public void onKillEntity(EntityLivingBase entityLivingIn)
	{
		super.onKillEntity(entityLivingIn);
		if (entityLivingIn.getMaxHealth() <= 100D)
		entityLivingIn.motionY += 15D;
		heal(this.worldObj.isDaytime() ? (5F + (this.rand.nextFloat() * 15F)) : (15F + (this.rand.nextFloat() * 30F)));
		if ((entityLivingIn instanceof EntityVillager))
		{
			EntityZombieMinion entityzombie = new EntityZombieMinion(this.worldObj);
			entityzombie.copyLocationAndAnglesFrom(entityLivingIn);
			this.worldObj.removeEntity(entityLivingIn);
			entityzombie.onSpawnWithEgg((IEntityLivingData)null);
			entityzombie.setVillager(true);
			if (entityLivingIn.isChild())
			{
				entityzombie.setChild(true);
			}

			this.worldObj.spawnEntityInWorld(entityzombie);
			this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
		}
	}

	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if (super.attackEntityAsMob(par1Entity))
		{
			if ((par1Entity != null) && ((par1Entity instanceof EntityLivingBase)))
			{
				if (par1Entity.onGround)
				{
					double ks = 1.25D;
					double inair = 1.25D;
					float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
					par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
				}

				else
				{
					double ks = 3.0D;
					double inair = 0.25D;
					float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
					par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
				}
			}

			return true;
		}

		return false;
	}

	public void addVelocity(double p_70024_1_, double p_70024_3_, double p_70024_5_)
	{
		this.motionX += p_70024_1_ * 0.1D;
		this.motionY += p_70024_3_ * 0.1D;
		this.motionZ += p_70024_5_ * 0.1D;
	}

	protected void updateAITasks()
	{
		if ((this.getAttackTarget() != null) && this.onGround)
		getMoveHelper().setMoveTo(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ, 1D);
		if (this.isCollidedHorizontally && this.master != null)
		this.motionY = 0.2D;
		if (this.motionY < -0.95D)
		{
			double df = 1.0D;
			if (this.motionY < -1.5D)
			df = 1.5D;
			doJumpDamage(this.posX, this.posY, this.posZ, 12.0D, 100 * df, 0);
			doJumpDamage(this.posX, this.posY, this.posZ, 14.0D, 50 * df, 0);
			doJumpDamage(this.posX, this.posY, this.posZ, 16.0D, 25 * df, 0);
		}

		if (!this.worldObj.isRemote && this.getAttackTarget() != null && this.ticksExisted % 30 == 0 && this.worldObj.rand.nextInt(3) == 0)
		{
			this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
			this.motionY += 1.75D;
			this.posY += 1.5499999523162842D;
			double d1 = this.getAttackTarget().posX - this.posX;
			double d2 = this.getAttackTarget().posZ - this.posZ;
			float d = (float)Math.atan2(d2, d1);
			this.faceEntity(this.getAttackTarget(), 10F, this.getVerticalFaceSpeed());
			d1 = Math.sqrt(d1 * d1 + d2 * d2);
			if (getDistanceSqToEntity(this.getAttackTarget()) > (10F + this.getAttackTarget().width / 2.0F) * (10F + this.getAttackTarget().width / 2.0F) + 45D)
			{
				this.motionX += d1 * 0.05D * Math.cos(d);
				this.motionZ += d1 * 0.05D * Math.sin(d);
			}
		}

		if (this.getAttackTarget() != null)
		this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(), 10F, 40F);
		if (this.getAttackTarget() != null && this.ticksExisted % 20 == 0 && getDistanceSqToEntity(this.getAttackTarget()) <= (14F + this.getAttackTarget().width / 2.0F) * (14F + this.getAttackTarget().width / 2.0F))
		this.attackEntityAsMob(getAttackTarget());
		if (this.master != null)
		{
			if ((this.master.getAttackTarget() != null))
			{
				setAttackTarget(this.master.getAttackTarget());
			}

			else if (getDistanceSqToEntity(this.master) > 5000.0D)
			{
				getMoveHelper().setMoveTo(this.master.posX, this.master.posY, this.master.posZ, 2.0D);
			}
		}

		else
		{
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(100.0D, 100.0D, 100.0D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if ((entity != null) && ((entity instanceof EntityZombieTitan)))
					{
						this.master = ((EntityZombieTitan)entity);
					}
				}
			}
		}

		super.updateAITasks();
	}

	protected void fall(float par1) 
	{
		 
	}


	protected void updateFallState(double par1, boolean par3) 
	{
		 
	}


	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		if (((source.getEntity() instanceof EntityZombieMinion)) || ((source.getEntity() instanceof EntityZombieTitan)) || ((source.getEntity() instanceof EntityGiantZombieBetter)))
		{
			return false;
		}

		Entity entity = source.getEntity();
		if ((entity instanceof EntityLivingBase))
		{
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(128.0D, 64.0D, 128.0D));
			for (int i = 0; i < list.size(); i++)
			{
				Entity entity1 = (Entity)list.get(i);
				if (entity1 instanceof EntityGiantZombieBetter)
				{
					EntityGiantZombieBetter entitypigzombie = (EntityGiantZombieBetter)entity1;
					if (entitypigzombie.getAttackTarget() == null)
					entitypigzombie.setAttackTarget((EntityLivingBase)entity);
					entitypigzombie.setRevengeTarget((EntityLivingBase)entity);
				}

				setAttackTarget((EntityLivingBase)entity);
				setRevengeTarget((EntityLivingBase)entity);
			}
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	public EntityLiving getMaster() 
	{
		return master;
	}


	@Override
	public void setMaster(EntityLiving entity) 
	{
		master = entity;
	}


	@Override
	public float getSummonVolume() 
	{

		return 10F;
	}

	@Override
	public float getSummonPitch() 
	{

		return 0.5F;
	}

	@Override
	public void setMinionType(int type) 
	{

	}
}


