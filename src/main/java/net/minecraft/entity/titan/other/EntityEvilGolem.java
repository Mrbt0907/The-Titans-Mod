package net.minecraft.entity.titan.other;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
public class EntityEvilGolem extends EntityMob
{
	private int attackTimer;
	private int teleportDelay;
	public EntityEvilGolem(World p_i1738_1_)
	{
		super(p_i1738_1_);
		this.setSize(1.45F, 3.2F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAIAttackOnCollideGolem(this, 1.0D, true));
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
		this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityGolem.class, 0, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
		this.experienceValue = 25;
	}

	@SideOnly(Side.CLIENT)
	public int getAttackTimer()
	{
		return this.attackTimer;
	}

	public float getEyeHeight()
	{
		return 2.9F;
	}

	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}

		else
		{
			if (this.getMobType() == 3 && p_70097_1_ instanceof EntityDamageSourceIndirect)
			{
				for (int i = 0; i < 64; ++i)
				{
					if (this.teleportRandomly())
					{
						return true;
					}
				}

				return super.attackEntityFrom(p_70097_1_, p_70097_2_);
			}

			else
			{
				return super.attackEntityFrom(p_70097_1_, p_70097_2_);
			}
		}
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte)0));
		this.dataWatcher.addObject(19, Integer.valueOf(0));
	}

	/**
	* Called to update the entity's position/logic.
	*/
	public void onUpdate()
	{
		super.onUpdate();
		if (!this.worldObj.isRemote && this.getMobType() == 4)
		{
			this.setBesideClimbableBlock(this.isCollidedHorizontally);
		}
	}

	protected void fall(float p_70069_1_) 
	{
		 
	}


	public boolean isPotionApplicable(PotionEffect p_70687_1_)
	{
		return this.getMobType() == 4 && p_70687_1_.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(p_70687_1_);
	}

	/**
	* Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	* setBesideClimableBlock.
	*/
	public boolean isBesideClimbableBlock()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	/**
	* Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	* false.
	*/
	public void setBesideClimbableBlock(boolean p_70839_1_)
	{
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		if (p_70839_1_)
		{
			b0 = (byte)(b0 | 1);
		}

		else
		{
			b0 &= -2;
		}

		this.dataWatcher.updateObject(16, Byte.valueOf(b0));
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		this.setMobType(tagCompund.getInteger("Type"));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("Type", this.getMobType());
	}

	public int getMobType()
	{
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	public void setMobType(int miniontype)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(miniontype));
		switch (miniontype)
		{
			case 1:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
				break;
			}

			case 2:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(600D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
				break;
			}

			case 3:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1500D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(60D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
				break;
			}

			case 4:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(240D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
				break;
			}

			default:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(25D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.325D);
			}
		}

		this.setHealth(this.getMaxHealth());
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected int decreaseAirSupply(int p_70682_1_)
	{
		return p_70682_1_;
	}

	public String getCommandSenderName()
	{
		switch (this.getMobType())
		{
			case 1:
			return StatCollector.translateToLocal("entity.GolemSkeletonWither.name");
			case 2:
			return StatCollector.translateToLocal("entity.GoldenGuardian.name");
			case 3:
			return StatCollector.translateToLocal("entity.GolemEnder.name");
			case 4:
			return StatCollector.translateToLocal("entity.GolemSpider.name");
			default:
			return StatCollector.translateToLocal("entity.GolemZombie.name");
		}
	}

	protected Item getDropItem()
	{
		switch (this.getMobType())
		{
			case 1:
			return Items.coal;
			case 2:
			return Items.gold_ingot;
			case 3:
			return Items.ender_eye;
			case 4:
			return Items.string;
			default:
			return Items.rotten_flesh;
		}
	}

	protected Item getSecondDropItem()
	{
		switch (this.getMobType())
		{
			case 1:
			return Items.bone;
			case 2:
			return Items.gold_ingot;
			case 3:
			return Items.ender_pearl;
			case 4:
			return Items.fermented_spider_eye;
			default:
			return rand.nextBoolean() ? Items.carrot : Items.potato;
		}
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		Item item = this.getDropItem();
		if (item != null)
		{
			int j = 2 + this.rand.nextInt(4);
			if (p_70628_2_ > 0)
			{
				j += this.rand.nextInt(p_70628_2_ + 1);
			}

			for (int k = 0; k < j; ++k)
			{
				this.dropItem(item, 1);
			}
		}

		item = this.getSecondDropItem();
		if (item != null)
		{
			int j = this.rand.nextInt(3);
			if (p_70628_2_ > 0)
			{
				j += this.rand.nextInt(p_70628_2_ + 1);
			}

			for (int k = 0; k < j; ++k)
			{
				this.dropItem(item, 1);
			}
		}
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote && getMobType() == 0)
		{
			float f = this.getBrightness(1.0F);
			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				boolean flag = true;
				ItemStack itemstack = this.getEquipmentInSlot(4);
				if (itemstack != null)
				{
					if (itemstack.isItemStackDamageable())
					{
						itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + this.rand.nextInt(2));
						if (itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage())
						{
							this.renderBrokenItemStack(itemstack);
							this.setCurrentItemOrArmor(4, (ItemStack)null);
						}
					}

					flag = false;
				}

				if (flag)
				{
					this.setFire(8);
				}
			}
		}

		if (getMobType() == 4)
		{
			if (this.getAttackTarget() != null && this.getDistanceToEntity(this.getAttackTarget()) > 2.0F && this.getDistanceToEntity(this.getAttackTarget()) < 6.0F && this.rand.nextInt(10) == 0)
			{
				if (this.onGround)
				{
					double d0 = this.getAttackTarget().posX - this.posX;
					double d1 = this.getAttackTarget().posZ - this.posZ;
					float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
					this.motionX = d0 / (double)f2 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
					this.motionZ = d1 / (double)f2 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
					this.motionY = 0.4000000059604645D;
				}
			}
		}

		if (getMobType() == 3)
		{
			if (!this.worldObj.isRemote && this.isEntityAlive())
			{
				if (this.getAttackTarget() != null)
				{
					if (this.getAttackTarget().getDistanceSqToEntity(this) < 4.0D)
					{
						this.teleportRandomly();
					}

					if (this.getAttackTarget().getDistanceSqToEntity(this) > 256.0D && this.teleportDelay++ >= 30 && this.teleportToEntity(this.getAttackTarget()))
					{
						this.teleportDelay = 0;
					}
				}

				else
				{
					this.teleportDelay = 0;
				}
			}
		}

		if (this.attackTimer > 0)
		{
			--this.attackTimer;
		}

		if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0D && this.rand.nextInt(5) == 0)
		{
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.posY - 0.20000000298023224D - (double)this.yOffset);
			int k = MathHelper.floor_double(this.posZ);
			Block block = this.worldObj.getBlock(i, j, k);
			if (block.getMaterial() != Material.air)
			{
				this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(block) + "_" + this.worldObj.getBlockMetadata(i, j, k), this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, this.boundingBox.minY + 0.1D, this.posZ + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, 4.0D * ((double)this.rand.nextFloat() - 0.5D), 0.5D, ((double)this.rand.nextFloat() - 0.5D) * 4.0D);
			}
		}

		if (this.getEntityAttribute(SharedMonsterAttributes.followRange).getBaseValue() < 32D)
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32D);
		switch (this.getMobType())
		{
			case 1:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
				this.isImmuneToFire = true;
				break;
			}

			case 2:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(600D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
				break;
			}

			case 3:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1500D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(60D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
				break;
			}

			case 4:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(240D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
				break;
			}

			default:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(25D);
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.325D);
			}
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		this.attackTimer = 10;
		this.worldObj.setEntityState(this, (byte)4);
		boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
		if (flag)
		{
			int i = this.worldObj.difficultySetting.getDifficultyId();
			if (getMobType() == 0 && this.isBurning() && this.rand.nextFloat() < (float)i * 0.3F)
			{
				p_70652_1_.setFire(4 * i);
			}

			p_70652_1_.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * 1.4F), 0.6D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * 1.4F));
			if (p_70652_1_ instanceof EntityLivingBase && getMobType() == 1)
			{
				((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
			}

			if (p_70652_1_ instanceof EntityLivingBase && getMobType() == 2)
			{
				byte b0 = 0;
				if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					b0 = 7;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					b0 = 15;
				}

				if (b0 > 0)
				{
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, b0 * 20, 0));
				}
			}
		}

		this.playSound("thetitans:evilGolemPunch", 1.0F, 1.0F);
		return flag;
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte p_70103_1_)
	{
		if (p_70103_1_ == 4)
		{
			this.attackTimer = 10;
			this.playSound("thetitans:evilGolemPunch", 1.0F, 1.0F);
		}

		else
		{
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	protected String getLivingSound()
	{
		switch (this.getMobType())
		{
			case 0:
			return "thetitans:bigZombieLiving";
			case 1:
			return "mob.skeleton.say";
			case 3:
			return "mob.endermen.idle";
			case 4:
			return "mob.spider.say";
			default:
			return "none";
		}
	}

	protected String getHurtSound()
	{
		switch (this.getMobType())
		{
			case 0:
			return "thetitans:bigZombieGrunt";
			case 1:
			return "mob.skeleton.hurt";
			case 3:
			return "mob.endermen.hit";
			case 4:
			return "mob.spider.say";
			default:
			return "mob.irongolem.hit";
		}
	}

	protected String getDeathSound()
	{
		switch (this.getMobType())
		{
			case 0:
			return "thetitans:bigZombieDeath";
			case 1:
			return "mob.skeleton.death";
			case 3:
			return "mob.endermen.death";
			case 4:
			return "mob.spider.death";
			default:
			return "mob.irongolem.death";
		}
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		switch (this.getMobType())
		{
			case 0:
			{
				this.playSound("mob.zombie.step", 1.0F, 1.0F);
				this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
				break;
			}

			case 1:
			{
				this.playSound("mob.skeleton.step", 1.0F, 1.0F);
				this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
				break;
			}

			case 4:
			{
				this.playSound("mob.spider.step", 1.0F, 1.0F);
				this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
				break;
			}

			default:
			{
				this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
			}
		}
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return this.getMobType() == 0 || this.getMobType() == 1 ? EnumCreatureAttribute.UNDEAD : (this.getMobType() == 4 ? EnumCreatureAttribute.ARTHROPOD : EnumCreatureAttribute.UNDEFINED);
	}

	public void setInWeb()
	{
		if (this.getMobType() != 4)
		{
			super.setInWeb();
		}
	}

	/**
	* returns true if this entity is by a ladder, false otherwise
	*/
	public boolean isOnLadder()
	{
		return this.getMobType() == 4 ? this.isBesideClimbableBlock() : super.isOnLadder();
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	{
		Object p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
		if (this.getMobType() == 4)
		{
			if (this.worldObj.rand.nextInt(100) == 0)
			{
				EntitySkeleton entityskeleton = new EntitySkeleton(this.worldObj);
				entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entityskeleton.onSpawnWithEgg((IEntityLivingData)null);
				this.worldObj.spawnEntityInWorld(entityskeleton);
				entityskeleton.mountEntity(this);
			}

			if (p_110161_1_1 == null)
			{
				p_110161_1_1 = new EntityEvilGolem.GroupData();
				if (this.worldObj.rand.nextFloat() < 0.1F * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ))
				{
					((EntityEvilGolem.GroupData)p_110161_1_1).func_111104_a(this.worldObj.rand);
				}
			}

			if (p_110161_1_1 instanceof EntityEvilGolem.GroupData)
			{
				int i = ((EntityEvilGolem.GroupData)p_110161_1_1).field_111105_a;
				if (i > 0 && Potion.potionTypes[i] != null)
				{
					this.addPotionEffect(new PotionEffect(i, Integer.MAX_VALUE));
				}
			}
		}

		return (IEntityLivingData)p_110161_1_1;
	}

	public static class GroupData implements IEntityLivingData
	{
		public int field_111105_a;
		public void func_111104_a(Random p_111104_1_)
		{
			int i = p_111104_1_.nextInt(5);
			if (i <= 1)
			{
				this.field_111105_a = Potion.moveSpeed.id;
			}

			else if (i <= 2)
			{
				this.field_111105_a = Potion.damageBoost.id;
			}

			else if (i <= 3)
			{
				this.field_111105_a = Potion.regeneration.id;
			}

			else if (i <= 4)
			{
				this.field_111105_a = Potion.invisibility.id;
			}
		}
	}

	/**
	* Teleport the enderman to a random nearby position
	*/
	protected boolean teleportRandomly()
	{
		double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
		double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
		return this.teleportTo(d0, d1, d2);
	}

	/**
	* Teleport the enderman to another entity
	*/
	protected boolean teleportToEntity(Entity p_70816_1_)
	{
		Vec3 vec3 = Vec3.createVectorHelper(this.posX - p_70816_1_.posX, this.boundingBox.minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
		vec3 = vec3.normalize();
		double d0 = 16.0D;
		double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
		double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
		double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
		return this.teleportTo(d1, d2, d3);
	}

	/**
	* Teleport the enderman
	*/
	protected boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_)
	{
		EnderTeleportEvent event = new EnderTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_, 0);
		if (MinecraftForge.EVENT_BUS.post(event))
		{
			return false;
		}

		double d3 = this.posX;
		double d4 = this.posY;
		double d5 = this.posZ;
		this.posX = event.targetX;
		this.posY = event.targetY;
		this.posZ = event.targetZ;
		boolean flag = false;
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posY);
		int k = MathHelper.floor_double(this.posZ);
		if (this.worldObj.blockExists(i, j, k))
		{
			boolean flag1 = false;
			while (!flag1 && j > 0)
			{
				Block block = this.worldObj.getBlock(i, j - 1, k);
				if (block.getMaterial().blocksMovement())
				{
					flag1 = true;
				}

				else
				{
					--this.posY;
					--j;
				}
			}

			if (flag1)
			{
				this.setPosition(this.posX, this.posY, this.posZ);
				if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox))
				{
					flag = true;
				}
			}
		}

		if (!flag)
		{
			this.setPosition(d3, d4, d5);
			return false;
		}

		else
		{
			short short1 = 128;
			for (int l = 0; l < short1; ++l)
			{
				double d6 = (double)l / ((double)short1 - 1.0D);
				float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
				double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * (double)this.height;
				double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
				this.worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
			}

			this.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
			this.playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
	}

	public class EntityAIAttackOnCollideGolem extends EntityAIBase
	{
		World worldObj;
		protected EntityCreature attacker;
		int attackTick;
		double speedTowardsTarget;
		boolean longMemory;
		PathEntity entityPathEntity;
		Class<?> classTarget;
		private int field_75445_i;
		private double field_151497_i;
		private double field_151495_j;
		private double field_151496_k;
		private int failedPathFindingPenalty = 0;
		private boolean canPenalize = false;
		@SuppressWarnings("rawtypes")
		public EntityAIAttackOnCollideGolem(EntityCreature p_i1635_1_, Class p_i1635_2_, double p_i1635_3_, boolean p_i1635_5_)
		{
			this(p_i1635_1_, p_i1635_3_, p_i1635_5_);
			this.classTarget = p_i1635_2_;
			canPenalize = classTarget == null || !net.minecraft.entity.player.EntityPlayer.class.isAssignableFrom(classTarget); //Only enable delaying when not targeting players.
		}

		public EntityAIAttackOnCollideGolem(EntityCreature p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_)
		{
			this.attacker = p_i1636_1_;
			this.worldObj = p_i1636_1_.worldObj;
			this.speedTowardsTarget = p_i1636_2_;
			this.longMemory = p_i1636_4_;
			this.setMutexBits(3);
		}

		/**
		* Returns whether the EntityAIBase should begin execution.
		*/
		public boolean shouldExecute()
		{
			EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
			if (entitylivingbase == null)
			{
				return false;
			}

			else if (!entitylivingbase.isEntityAlive())
			{
				return false;
			}

			else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass()))
			{
				return false;
			}

			else
			{
				if (canPenalize)
				{
					if (--this.field_75445_i <= 0)
					{
						this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
						this.field_151497_i = 4 + this.attacker.getRNG().nextInt(7);
						return this.entityPathEntity != null;
					}

					else
					{
						return true;
					}
				}

				this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
				return this.entityPathEntity != null;
			}
		}

		/**
		* Returns whether an in-progress EntityAIBase should continue executing
		*/
		public boolean continueExecuting()
		{
			EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
			return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.isWithinHomeDistance(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ))));
		}

		/**
		* Execute a one shot task or start executing a continuous task
		*/
		public void startExecuting()
		{
			this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
			this.field_75445_i = 0;
		}

		/**
		* Resets the task
		*/
		public void resetTask()
		{
			this.attacker.getNavigator().clearPathEntity();
		}

		/**
		* Updates the task
		*/
		public void updateTask()
		{
			EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
			this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
			double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
			double d1 = this.func_179512_a(entitylivingbase);
			--this.field_75445_i;
			if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.field_75445_i <= 0 && (this.field_151497_i == 0.0D && this.field_151495_j == 0.0D && this.field_151496_k == 0.0D || entitylivingbase.getDistanceSq(this.field_151497_i, this.field_151495_j, this.field_151496_k) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F))
			{
				this.field_151497_i = entitylivingbase.posX;
				this.field_151495_j = entitylivingbase.boundingBox.minY;
				this.field_151496_k = entitylivingbase.posZ;
				this.field_75445_i = 4 + this.attacker.getRNG().nextInt(7);
				if (this.canPenalize)
				{
					this.field_151497_i += failedPathFindingPenalty;
					if (this.attacker.getNavigator().getPath() != null)
					{
						net.minecraft.pathfinding.PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
						if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1)
						failedPathFindingPenalty = 0;
						else
						failedPathFindingPenalty += 10;
					}

					else
					{
						failedPathFindingPenalty += 10;
					}
				}

				if (d0 > 1024.0D)
				{
					this.field_75445_i += 10;
				}

				else if (d0 > 256.0D)
				{
					this.field_75445_i += 5;
				}

				if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget))
				{
					this.field_75445_i += 15;
				}
			}

			this.attackTick = Math.max(this.attackTick - 1, 0);
			if (d0 <= d1 && this.attackTick <= 0)
			{
				this.attackTick = 20;
				if (this.attacker.getHeldItem() != null)
				{
					this.attacker.swingItem();
				}

				this.attacker.attackEntityAsMob(entitylivingbase);
			}
		}

		protected double func_179512_a(EntityLivingBase p_179512_1_)
		{
			return (double)(attacker.width * attacker.width + p_179512_1_.width * p_179512_1_.width) + 4D;
		}
	}
}


