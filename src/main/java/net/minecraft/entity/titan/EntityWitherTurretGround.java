package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
public class EntityWitherTurretGround
extends EntityGolem
implements IRangedAttackMob
{
	public int durabilityLevel;
	public int ferocityLevel;
	public int maniacLevel;
	public int unstabilityLevel;
	public int shurakinLevel;
	public int unbreakingLevel;
	public int shootingTimer;
	public int shots = 14;
	public EntityWitherTurretGround(World worldIn)
	{
		super(worldIn);
		this.isImmuneToFire = true;
		this.preventEntitySpawning = true;
		setSize(0.75F, 1.375F);
	}

	public void onKillCommand()
	{
		onDeathUpdate();
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_)
	{
		return 15728880;
	}

	public float getBrightness(float p_70013_1_)
	{
		return 1.0F;
	}

	protected void despawnEntity() 
	{
		 
	}


	public boolean isAIEnabled()
	{
		return true;
	}

	protected boolean canTargetEntity(EntityLivingBase entity)
	{
		if (isPlayerCreated())
		{
			return (!(entity instanceof EntityTitanSpirit)) && (!(entity instanceof EntityPlayer)) && (!(entity instanceof EntityGolem)) && (!(entity instanceof EntityAgeable)) && (!(entity instanceof EntityIronGolemTitan)) && (!(entity instanceof EntitySnowGolemTitan)) && (!(entity instanceof EntityAnimal));
		}

		return (!(entity instanceof EntityTitanSpirit)) && ((entity instanceof EntityLivingBase)) && (entity.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD);
	}

	public int getTotalArmorValue()
	{
		int i = 0 + this.unbreakingLevel * 6;
		if (i > 20)
		{
			i = 20;
		}

		return i;
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4000.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (!isPlayerCreated()) || ((p_70686_1_ != EntityPlayer.class) && (p_70686_1_ != EntityAgeable.class) && (p_70686_1_ != EntityGolem.class));
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if ((this.shots <= 0) && (this.shootingTimer <= 10))
		{
			this.shots = 14;
		}

		this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
		this.onGround = true;
		this.isAirBorne = false;
		this.ignoreFrustumCheck = true;
		this.preventEntitySpawning = true;
		this.isImmuneToFire = true;
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.ticksExisted % 20 == 0)
		{
			heal(1.0F + this.durabilityLevel);
		}

		if (!this.worldObj.isRemote)
		{
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY - 1.0D);
			int k = MathHelper.floor_double(this.posZ);
			if (this.worldObj.getBlock(i, j, k) != Blocks.bedrock)
			{
				this.worldObj.setBlock(i, j, k, Blocks.bedrock);
			}
		}

		double d8 = 15.0D;
		Vec3 vec3 = this.getLook(1.0F);
		double dx = vec3.xCoord * d8;
		double dz = vec3.zCoord * d8;
		List<?> list11 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(16.0D, 16.0D, 16.0D).offset(dx, -8D, dz));
		if ((list11 != null) && (!list11.isEmpty()) && (getAttackTarget() == null))
		{
			for (int i1 = 0; i1 < list11.size(); i1++)
			{
				Entity entity = (Entity)list11.get(i1);
				if (((entity instanceof EntityLivingBase)) && (entity != null) && (entity.isEntityAlive()) && (getAttackTarget() == null) && (canTargetEntity((EntityLivingBase)entity)) && (canEntityBeSeen(entity)) && (entity.posY <= this.posY + 6.0D))
				{
					setAttackTarget((EntityLivingBase)entity);
				}

				else
				{
					list11.remove(entity);
				}
			}
		}

		if (getAttackTarget() != null)
		{
			if (!this.worldObj.isRemote)
			{
				attackEntityWithRangedAttack(getAttackTarget(), 1.0F);
				attackEntityWithRangedAttack(getAttackTarget(), 1.0F);
			}

			getLookHelper().setLookPositionWithEntity(getAttackTarget(), 20.0F, 180.0F);
			if ((!getAttackTarget().isEntityAlive()) || (getAttackTarget().isDead) || (getAttackTarget().posY > this.posY + 8.0D) || (getAttackTarget().getDistanceSqToEntity(this) > 1024.0D) || (!canEntityBeSeen(getAttackTarget())))
			{
				setAttackTarget(null);
			}

			if (!canTargetEntity(getAttackTarget()))
			{
				setAttackTarget(null);
			}
		}
	}

	public void onUpdate()
	{
		super.onUpdate();
		for (int j = 0; j < 3; j++)
		{
			double d10 = func_82214_u(j);
			double d2 = func_82208_v(j);
			double d4 = func_82213_w(j);
			this.worldObj.spawnParticle("smoke", d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		MathHelper.floor_double(this.posX);
		MathHelper.floor_double(this.posY);
		MathHelper.floor_double(this.posZ);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		if (this.shootingTimer >= 0)
		{
			this.shootingTimer -= 1;
		}

		if (this.shootingTimer <= 0)
		{
			this.shootingTimer = 0;
		}
	}

	public void fall(float distance, float damageMultiplier) 
	{
		 
	}


	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setBoolean("PlayerCreated", isPlayerCreated());
		tagCompound.setInteger("Ench1Level", this.durabilityLevel);
		tagCompound.setInteger("Ench2Level", this.ferocityLevel);
		tagCompound.setInteger("Ench3Level", this.maniacLevel);
		tagCompound.setInteger("Ench4Level", this.unstabilityLevel);
		tagCompound.setInteger("Ench5Level", this.shurakinLevel);
		tagCompound.setInteger("Ench6Level", this.unbreakingLevel);
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		if (tagCompund.hasKey("Ench1Level", 99))
		{
			this.durabilityLevel = tagCompund.getInteger("Ench1Level");
		}

		if (tagCompund.hasKey("Ench2Level", 99))
		{
			this.ferocityLevel = tagCompund.getInteger("Ench2Level");
		}

		if (tagCompund.hasKey("Ench3Level", 99))
		{
			this.maniacLevel = tagCompund.getInteger("Ench3Level");
		}

		if (tagCompund.hasKey("Ench4Level", 99))
		{
			this.unstabilityLevel = tagCompund.getInteger("Ench4Level");
		}

		if (tagCompund.hasKey("Ench5Level", 99))
		{
			this.shurakinLevel = tagCompund.getInteger("Ench5Level");
		}

		if (tagCompund.hasKey("Ench6Level", 99))
		{
			this.unbreakingLevel = tagCompund.getInteger("Ench6Level");
		}

		setPlayerCreated(tagCompund.getBoolean("PlayerCreated"));
	}

	public boolean isPlayerCreated()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
	}

	public void setPlayerCreated(boolean p_70849_1_)
	{
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		if (p_70849_1_)
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
		}

		else
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
		}
	}

	private double func_82214_u(int p_82214_1_)
	{
		if (p_82214_1_ <= 0)
		{
			return this.posX;
		}

		float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * 3.1415927F;
		float f1 = MathHelper.cos(f);
		return this.posX + f1 * 1.2D;
	}

	private double func_82208_v(int p_82208_1_)
	{
		return p_82208_1_ <= 0 ? this.posY + 0.5D : this.posY + 1.15D;
	}

	private double func_82213_w(int p_82213_1_)
	{
		if (p_82213_1_ <= 0)
		{
			return this.posZ;
		}

		float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * 3.1415927F;
		float f1 = MathHelper.sin(f);
		return this.posZ + f1 * 1.2D;
	}

	private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
	{
		if ((p_82216_2_.getDistanceSqToEntity(this) > 900.0D) || (p_82216_2_.posY >= this.posY + 5.0D))
		{
			launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX + (this.rand.nextDouble() * 2.0D - 1.0D), p_82216_2_.posY + 0.5D, p_82216_2_.posZ + (this.rand.nextDouble() * 2.0D - 1.0D), false);

		}

		 else 
		{

			launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + 0.5D, p_82216_2_.posZ, false);
		}
	}

	private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
	{
		if (this.shootingTimer <= 0)
		{
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 0.15F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 1.8F);
			playSound("thetitans:turretShoot2", 3.0F, 1.0F);
			double d3 = func_82214_u(1);
			double d4 = func_82208_v(1);
			double d5 = func_82213_w(1);
			double d6 = p_82209_2_ - d3;
			double d7 = p_82209_4_ - d4;
			double d8 = p_82209_6_ - d5;
			EntityBulletWitherSkull entitywitherskull = new EntityBulletWitherSkull(this.worldObj, this, d6, d7, d8);
			if (p_82209_8_)
			{
				entitywitherskull.setInvulnerable(true);
			}

			entitywitherskull.extraDamage = (this.ferocityLevel * 2);
			entitywitherskull.explosivePower = ((int)(this.unstabilityLevel * 0.75F));
			entitywitherskull.speedFactor = (this.shurakinLevel * 0.1F);
			entitywitherskull.posY = d4;
			entitywitherskull.posX = d3;
			entitywitherskull.posZ = d5;
			this.worldObj.spawnEntityInWorld(entitywitherskull);
			playSound("thetitans:turretShoot2", 6.0F, 1.0F);
			double d31 = func_82214_u(2);
			double d41 = func_82208_v(2);
			double d51 = func_82213_w(2);
			double d61 = p_82209_2_ - d31;
			double d71 = p_82209_4_ - d41;
			double d81 = p_82209_6_ - d51;
			EntityBulletWitherSkull entitywitherskull1 = new EntityBulletWitherSkull(this.worldObj, this, d61, d71, d81);
			if (p_82209_8_)
			{
				entitywitherskull1.setInvulnerable(true);
			}

			entitywitherskull1.extraDamage = (this.ferocityLevel * 2);
			entitywitherskull1.explosivePower = ((int)(this.unstabilityLevel * 0.75F));
			entitywitherskull1.speedFactor = (this.shurakinLevel * 0.1F);
			entitywitherskull1.posY = d41;
			entitywitherskull1.posX = d31;
			entitywitherskull1.posZ = d51;
			this.worldObj.spawnEntityInWorld(entitywitherskull1);
			this.shots -= 1;
			this.shots -= 1;
			if (this.shots > 0)
			{
				this.shootingTimer = 2;

			}

			 else 
			{

				this.shootingTimer = (14 - this.maniacLevel * 4);
				for (int j = 0; j < 3; j++)
				{
					double d10 = func_82214_u(j);
					double d2 = func_82208_v(j);
					double d24 = func_82213_w(j);
					this.worldObj.spawnParticle("largesmoke", d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d24 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
				}
			}

			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY - 1.0D);
			int k = MathHelper.floor_double(this.posZ);
			this.worldObj.setBlock(i, j, k, Blocks.glowstone);
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		if (((source.getEntity() instanceof EntityWitherTurretMortar)) || ((source.getEntity() instanceof EntityWitherTurretGround)) || ((source.getEntity() instanceof EntityWitherTurret)))
		{
			return false;
		}

		if ((source == DamageSource.anvil) || (source == DamageSource.generic) || (source == DamageSource.inFire) || (source == DamageSource.lava) || (source == DamageSourceExtra.lightningBolt) || (source == DamageSource.onFire) || (source == DamageSource.magic) || (source == DamageSource.wither) || (source == DamageSource.fallingBlock) || (source == DamageSource.drown) || (source == DamageSource.cactus))
		{
			return false;
		}

		Entity entity = source.getEntity();
		if (((entity instanceof EntityLivingBase)) && (!isEntityInvulnerable()))
		{
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(100.0D, 100.0D, 100.0D));
			for (int i = 0; i < list.size(); i++)
			{
				Entity entity1 = (Entity)list.get(i);
				if ((entity1 instanceof EntityWitherTurretGround))
				{
					EntityWitherTurretGround entitypigzombie = (EntityWitherTurretGround)entity1;
					entitypigzombie.setAttackTarget((EntityLivingBase)entity);
					entitypigzombie.setRevengeTarget((EntityLivingBase)entity);
				}

				setAttackTarget((EntityLivingBase)entity);
				setRevengeTarget((EntityLivingBase)entity);
				this.shootingTimer -= 4;
			}
		}

		return super.attackEntityFrom(source, amount);
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		launchWitherSkullToEntity(1, p_82196_1_);
		getLookHelper().setLookPositionWithEntity(p_82196_1_, 180.0F, 180.0F);
	}

	protected String getLivingSound()
	{
		return "mob.wither.idle";
	}

	protected String getHurtSound()
	{
		return "mob.wither.hurt";
	}

	protected String getDeathSound()
	{
		return this.isPlayerCreated() ? "mob.wither.hurt" : "mob.wither.death";
	}

	protected void onDeathUpdate()
	{
		setDead();
		if ((!this.worldObj.isRemote) && (this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot") == true))
		{
			if (this.isPlayerCreated())
			{
				ItemStack goodTurret = new ItemStack(TitanItems.goodTurret2);
				if (this.durabilityLevel > 0)
				goodTurret.addEnchantment(TheTitans.turretEnchant1, this.durabilityLevel);
				if (this.ferocityLevel > 0)
				goodTurret.addEnchantment(TheTitans.turretEnchant2, this.ferocityLevel);
				if (this.maniacLevel > 0)
				goodTurret.addEnchantment(TheTitans.turretEnchant3, this.maniacLevel);
				if (this.unstabilityLevel > 0)
				goodTurret.addEnchantment(TheTitans.turretEnchant4, this.unstabilityLevel);
				if (this.shurakinLevel > 0)
				goodTurret.addEnchantment(TheTitans.turretEnchant5, this.shurakinLevel);
				if (this.unbreakingLevel > 0)
				goodTurret.addEnchantment(Enchantment.unbreaking, this.unbreakingLevel);
				this.entityDropItem(goodTurret, 3.0F);
			}

			else
			{
				entityDropItem(new ItemStack(Items.skull, 1, 1), 6.0F);
				entityDropItem(new ItemStack(Items.skull, 1, 1), 3.0F);
			}

			int i = 10;
			while (i > 0)
			{
				int j = EntityXPOrb.getXPSplit(i);
				i -= j;
				this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 4.0D, this.posZ, j));
			}

			i = 20;
			while (i > 0)
			{
				int j = EntityXPOrb.getXPSplit(i);
				i -= j;
				this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 4.0D, this.posZ, j));
			}
		}

		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posY - 1.0D);
		int k = MathHelper.floor_double(this.posZ);
		this.worldObj.setBlock(i, j, k, Blocks.bedrock);
		if (!this.worldObj.isRemote)
		{
			playSound("thetitans:turretDeath2", 6.0F, 1.0F);
			playSound("thetitans:turretDeath2", 6.0F, 1.0F);
			playSound("thetitans:turretDeath2", 6.0F, 1.0F);
			playSound("thetitans:turretDeath2", 6.0F, 1.0F);
			playSound("thetitans:turretDeath2", 6.0F, 1.0F);
			playSound("thetitans:turretDeath2", 6.0F, 1.0F);
			playSound("thetitans:turretDeath2", 6.0F, 1.0F);
			playSound("thetitans:turretDeath2", 6.0F, 1.0F);
			this.worldObj.createExplosion((Entity)null, this.posX, this.posY - 1.0D, this.posZ, 2.0F, true);
		}
	}

	public float getEyeHeight()
	{
		return 1.1F;
	}

	public AxisAlignedBB getCollisionBox(Entity entityIn)
	{
		return entityIn.boundingBox;
	}

	public AxisAlignedBB getBoundingBox()
	{
		return this.boundingBox;
	}

	public boolean canBeCollidedWith()
	{
		return true;
	}

	public boolean canBePushed()
	{
		return false;
	}

	public boolean isEnchanted()
	{
		if ((this.durabilityLevel > 0) || (this.ferocityLevel > 0) || (this.maniacLevel > 0) || (this.unstabilityLevel > 0) || (this.shurakinLevel > 0) || (this.unbreakingLevel > 0))
		{
			return true;
		}

		return false;
	}

	public boolean shouldShowEnchants()
	{
		return isEnchanted();
	}

	protected void collideWithNearbyEntities() 
	{
		 
	}


	public void knockBack(Entity p_70653_1_, float p_70653_2_, double p_70653_3_, double p_70653_5_) 
	{
		 
	}


	public void addVelocity(double x, double y, double z) 
	{
		 
	}


	public void moveEntity(double x, double y, double z) 
	{
		 
	}


	protected int decreaseAirSupply(int p_70682_1_)
	{
		return p_70682_1_;
	}
}


