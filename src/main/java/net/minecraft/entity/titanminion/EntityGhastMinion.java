package net.minecraft.entity.titanminion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.titan.EntityGhastTitan;
import net.minecraft.entity.titan.EntityHarcadiumArrow;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.ITitan;
import net.minecraft.entity.titan.ai.EntityAIHurtByTargetTitan;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityGhastMinion
extends EntityCreature
implements IMob, IRangedAttackMob, ITemplar
{
	public EntityLiving master;
	public int courseChangeCooldown;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private Entity targetedEntity;
	private int aggroCooldown;
	public int prevAttackCounter;
	public int attackCounter;
	protected int explosionStrength;
	public EntityLiving entityToHeal;
	private int attackPattern;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 5, 64.0F);
	public EntityGhastMinion(World worldIn)
	{
		super(worldIn);
		explosionStrength = 1;
		setSize(4.5F, 4.5F);
		this.isImmuneToFire = true;
		this.experienceValue = 20;
		this.tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTargetTitan(this, true, new Class[] 
		{
			EntityGhastMinion.class, EntityGhastTitan.class
		}
		));
		if (TheTitans.TitansFFAMode)
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.GhastTitanSorter));
		else
		this.targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	public void setDead()
	{
		super.setDead();
		if ((this.master != null) && ((this.master instanceof EntityTitan)))
		{
			((EntityTitan)this.master).retractMinionNumFromType(getMinionType());
		}
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityGhastMinion.class) && (p_70686_1_ != EntityGhastTitan.class);
	}

	public String getCommandSenderName()
	{
		switch (this.getMinionTypeInt())
		{
			case 1:
			return StatCollector.translateToLocal("entity.GhastPriest.name");
			case 2:
			return StatCollector.translateToLocal("entity.GhastZealot.name");
			case 3:
			return StatCollector.translateToLocal("entity.GhastBishop.name");
			case 4:
			return StatCollector.translateToLocal("entity.GhastTemplar.name");
			default:
			return StatCollector.translateToLocal("entity.GhastLoyalist.name");
		}
	}

	protected void fall(float p_70069_1_) 
	{
		 
	}


	protected void updateFallState(double p_70064_1_, boolean p_70064_3_) 
	{
		 
	}


	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		int i = 0;
		if (p_70652_1_ instanceof EntityLivingBase)
		{
			f += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase)p_70652_1_);
			i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase)p_70652_1_);
		}

		boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), f);
		if (flag)
		{
			if (p_70652_1_ instanceof EntityLivingBase && this.getMinionTypeInt() >= 3)
			{
				byte b0 = 10;
				if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					b0 = 20;
				}

				else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					b0 = 30;
				}

				if (b0 > 0)
				{
					if (worldObj.isRemote)
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, b0 * 20, 0));
				}
			}

			if (i > 0)
			{
				p_70652_1_.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}

			int j = EnchantmentHelper.getFireAspectModifier(this);
			if (j > 0)
			{
				p_70652_1_.setFire(j * 4);
			}

			if (p_70652_1_ instanceof EntityLivingBase)
			{
				EnchantmentHelper.func_151384_a((EntityLivingBase)p_70652_1_, this);
			}

			EnchantmentHelper.func_151385_b(this, p_70652_1_);
		}

		return flag;
	}

	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		if (isInWater())
		{
			moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.800000011920929D;
			this.motionY *= 0.800000011920929D;
			this.motionZ *= 0.800000011920929D;
		}

		else if (handleLavaMovement())
		{
			moveFlying(p_70612_1_, p_70612_2_, 0.02F);
			moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		}

		else
		{
			float f2 = 0.91F;
			if (this.onGround)
			{
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
			}

			float f3 = 0.16277136F / (f2 * f2 * f2);
			moveFlying(p_70612_1_, p_70612_2_, this.onGround ? 0.1F * f3 : 0.02F);
			f2 = 0.91F;
			if (this.onGround)
			{
				f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
			}

			moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= f2;
			this.motionY *= f2;
			this.motionZ *= f2;
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double d1 = this.posX - this.prevPosX;
		double d0 = this.posZ - this.prevPosZ;
		float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;
		if (f4 > 1.0F)
		{
			f4 = 1.0F;
		}

		this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

	public boolean isOnLadder()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean func_110182_bF()
	{
		return this.dataWatcher.getWatchableObjectByte(16) != 0;
	}

	public void func_175454_a(boolean p_175454_1_)
	{
		this.dataWatcher.updateObject(16, Byte.valueOf((byte)(p_175454_1_ ? 1 : 0)));
	}

	public int func_175453_cd()
	{
		return this.explosionStrength;
	}

	public void onUpdate()
	{
		super.onUpdate();
		if (getAttackTarget() != null)
		{
			getLookHelper().setLookPosition(getAttackTarget().posX, getAttackTarget().posY + getAttackTarget().getEyeHeight(), getAttackTarget().posZ, 180.0F, 180.0F);
		}
	}

	protected void updateAITasks()
	{
		if ((this.rand.nextInt(60) == 0) && (getAttackTarget() != null))
		{
			setCombatTask();
			if (!this.onGround)
			{
				this.attackPattern = 0;
			}

			else
			{
				this.attackPattern = 1;
			}
		}

		if (this.master != null)
		{
			if ((this.master.getAttackTarget() != null))
			{
				if (this.master.getAttackTarget().height < 6F)
				this.setAttackTarget(this.master.getAttackTarget());
				else
				this.getLookHelper().setLookPositionWithEntity(this.master.getAttackTarget(), 10F, 40F);
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
					if ((entity != null) && ((entity instanceof EntityGhastTitan)))
					{
						this.master = ((EntityGhastTitan)entity);
					}
				}
			}
		}

		if ((getAttackTarget() != null) && (!canEntityBeSeen(getAttackTarget())) && (this.rand.nextInt(150) == 0))
		{
			setAttackTarget(null);
		}

		if ((!this.worldObj.isRemote) && (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL))
		{
			setDead();
		}

		despawnEntity();
		this.prevAttackCounter = this.attackCounter;
		double d0 = this.waypointX - this.posX;
		double d1 = this.waypointY - this.posY;
		double d2 = this.waypointZ - this.posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		if ((d3 < 1.0D) || (d3 > 10000.0D))
		{
			if (this.master != null)
			{
				double extra = 0.0D;
				if ((getAttackTarget() != null) && (getAttackTarget().height > 4.0F))
				{
					extra = 64.0D;
				}

				double x = this.master.posX;
				double y = this.master.posY + 32.0D + extra;
				double z = this.master.posZ;
				d0 += this.rand.nextDouble() * 96D - 48D;
				d1 += this.rand.nextDouble() * 96D - 48D;
				d2 += this.rand.nextDouble() * 96D - 48D;
				this.waypointX = (x + d0);
				this.waypointY = (y + d1);
				this.waypointZ = (z + d2);
			}

			else if (getAttackTarget() != null)
			{
				this.waypointX = getAttackTarget().posX + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
				this.waypointY = getAttackTarget().posY + 32D + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
				this.waypointZ = getAttackTarget().posZ + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
			}

			else
			{
				EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 512D);
				if (player != null)
				{
					this.waypointX = player.posX + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
					this.waypointY = player.posY + 32D + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
					this.waypointZ = player.posZ + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
				}

				else
				{
					this.waypointX = this.posX + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
					this.waypointY = this.posY + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
					this.waypointZ = this.posZ + ((this.rand.nextDouble() * 2.0D - 1.0D) * 16D);
				}
			}
		}

		if (this.courseChangeCooldown-- <= 0)
		{
			this.courseChangeCooldown += this.rand.nextInt(5) + 2;
			d3 = MathHelper.sqrt_double(d3);
			if (isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3))
			{
				if ((this.master != null) && (getDistanceSqToEntity(this.master) > 4096D))
				{
					this.motionX += d0 / d3 * (this.getMinionType() == EnumMinionType.ZEALOT ? 0.8D : 0.2D);
					this.motionY += d1 / d3 * (this.getMinionType() == EnumMinionType.ZEALOT ? 0.8D : 0.2D);
					this.motionZ += d2 / d3 * (this.getMinionType() == EnumMinionType.ZEALOT ? 0.8D : 0.2D);
				}

				else
				{
					this.motionX += d0 / d3 * (this.getMinionType() == EnumMinionType.ZEALOT ? 0.4D : 0.1D);
					this.motionY += d1 / d3 * (this.getMinionType() == EnumMinionType.ZEALOT ? 0.4D : 0.1D);
					this.motionZ += d2 / d3 * (this.getMinionType() == EnumMinionType.ZEALOT ? 0.4D : 0.1D);
				}
			}

			else
			{
				this.waypointX = this.posX;
				this.waypointY = this.posY;
				this.waypointZ = this.posZ;
			}
		}

		if ((this.targetedEntity != null) && (this.targetedEntity.isDead))
		{
			this.targetedEntity = null;
		}

		if ((this.targetedEntity == null) || (this.aggroCooldown-- <= 0))
		{
			this.targetedEntity = getAttackTarget();
			if (this.targetedEntity != null)
			{
				this.aggroCooldown = 20;
			}
		}

		double d4 = 100.0D;
		if ((this.targetedEntity != null) && (this.targetedEntity.getDistanceSqToEntity(this) < d4 * d4))
		{
			double d8 = 2D;
			Vec3 vec3 = getLook(1.0F);
			double d5 = (this.targetedEntity.posX + targetedEntity.motionX) - (this.posX + vec3.xCoord * d8);
			double d6 = (this.targetedEntity.posY + 1D) - (this.posY + vec3.yCoord * d8 + 1D);
			double d7 = (this.targetedEntity.posZ + targetedEntity.motionZ) - (this.posZ + vec3.zCoord * d8);
			this.renderYawOffset = (this.rotationYaw = -(float)Math.atan2(d5, d7) * 180.0F / 3.1415927F);
			if (canEntityBeSeen(this.targetedEntity))
			{
				if (this.attackCounter == 10)
				{
					this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1007, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				}

				this.attackCounter += 1;
				if (this.attackCounter > 20 && this.getMinionType() == EnumMinionType.TEMPLAR)
				{
					this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
					EntityGhastMinionFireball entitylargefireball = new EntityGhastMinionFireball(this.worldObj, this, d5, d6, d7);
					entitylargefireball.field_92057_e = this.explosionStrength;
					entitylargefireball.posX = (this.posX + vec3.xCoord * d8);
					entitylargefireball.posY = (this.posY + vec3.yCoord * d8 + 1D);
					entitylargefireball.posZ = (this.posZ + vec3.zCoord * d8);
					this.worldObj.spawnEntityInWorld(entitylargefireball);
				}

				if (this.attackCounter >= (this.getMinionType() == EnumMinionType.TEMPLAR ? 40 : 20))
				{
					this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
					EntityGhastMinionFireball entitylargefireball = new EntityGhastMinionFireball(this.worldObj, this, d5, d6, d7);
					entitylargefireball.field_92057_e = this.explosionStrength;
					entitylargefireball.posX = (this.posX + vec3.xCoord * d8);
					entitylargefireball.posY = (this.posY + vec3.yCoord * d8 + 1D);
					entitylargefireball.posZ = (this.posZ + vec3.zCoord * d8);
					this.worldObj.spawnEntityInWorld(entitylargefireball);
					this.attackCounter = this.getMinionType() == EnumMinionType.ZEALOT ? 0 : -40;
				}
			}

			else if (this.attackCounter > 0)
			{
				this.attackCounter -= 1;
			}
		}

		else
		{
			this.renderYawOffset = (this.rotationYaw = -(float)Math.atan2(this.motionX, this.motionZ) * 180.0F / 3.1415927F);
			if (this.attackCounter > 0)
			{
				this.attackCounter -= 1;
			}
		}

		if (!this.worldObj.isRemote)
		{
			byte b1 = this.dataWatcher.getWatchableObjectByte(16);
			byte b0 = (byte)(this.attackCounter > 10 ? 1 : 0);
			if (b1 != b0)
			{
				this.dataWatcher.updateObject(16, Byte.valueOf(b0));
			}
		}

		super.updateAITasks();
	}

	public int deathTicks;
	public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_)
	{
		if (deathTicks > 0)
		{
			super.moveEntity(0.0D, 0.10000000149011612D, 0.0D);
		}

		else
		{
			super.moveEntity(p_70091_1_, p_70091_3_, p_70091_5_);
		}
	}

	protected void onDeathUpdate()
	{
		if (this.getMinionTypeInt() == 4)
		{
			--this.ticksExisted;
			++this.deathTicks;
			if (this.master != null)
			{
				double mx = this.posX - master.posX;
				double my = (this.posY + this.getEyeHeight()) - (master.posY + master.getEyeHeight());
				double mz = this.posZ - master.posZ;
				short short1 = (short) (getDistanceToEntity(master) * 2);
				for (int f = 0; f < short1; f++)
				{
					double d9 = f / (short1 - 1.0D);
					double d6 = this.posX + mx * -d9;
					double d7 = (this.posY + this.getEyeHeight()) + my * -d9;
					double d8 = this.posZ + mz * -d9;
					this.worldObj.spawnParticle("fireworksSpark", d6, d7, d8, master.motionX, master.motionY + 0.2D, master.motionZ);
				}
			}

			if (!this.worldObj.isRemote)
			{
				if (this.deathTicks > 150 && this.deathTicks % 5 == 0)
				{
					this.dropFewItems(true, 0);
				}

				if (this.deathTicks == 1)
				{
					this.worldObj.playBroadcastSound(1018, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				}
			}

			if (this.deathTicks >= 180 && this.deathTicks <= 200)
			{
				float f = (this.rand.nextFloat() - 0.5F) * this.width;
				float f1 = (this.rand.nextFloat() - 0.5F) * this.height;
				float f2 = (this.rand.nextFloat() - 0.5F) * this.width;
				this.worldObj.spawnParticle("hugeexplosion", this.posX + (double)f, this.posY + this.getEyeHeight() + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
			}

			this.moveEntity(0.0D, 0.10000000149011612D, 0.0D);
			float f = (this.rand.nextFloat() - 0.5F) * this.width;
			float f1 = (this.rand.nextFloat() - 0.5F) * this.height;
			float f2 = (this.rand.nextFloat() - 0.5F) * this.width;
			this.worldObj.spawnParticle("largeexplode", this.posX + (double)f, this.posY + this.getEyeHeight() + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("lava", this.posX + (double)f, this.posY + this.getEyeHeight() + (double)f1, this.posZ + (double)f2, rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian());
			if (this.deathTicks == 200 && !this.worldObj.isRemote)
			{
				if (this.master != null)
				{
					this.master.heal(this.master.getMaxHealth() / 100F);
					for (int i = 0; i < 100; ++i)
					{
						double d2 = this.rand.nextGaussian() * 0.02D;
						double d0 = this.rand.nextGaussian() * 0.02D;
						double d1 = this.rand.nextGaussian() * 0.02D;
						this.worldObj.spawnParticle("largeexplode", master.posX + (double)(this.rand.nextFloat() * master.width * 2.0F) - (double)master.width, master.posY + (double)(this.rand.nextFloat() * master.height), master.posZ + (double)(this.rand.nextFloat() * master.width * 2.0F) - (double)master.width, d2, d0, d1);
					}
				}

				int i = this.experienceValue;
				while (i > 0)
				{
					int j = EntityXPOrb.getXPSplit(i);
					i -= j;
					this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
				}

				this.setDead();
			}
		}

		else
		{
			super.onDeathUpdate();
		}
	}

	private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_)
	{
		double d4 = (this.waypointX - this.posX) / p_70790_7_;
		double d5 = (this.waypointY - this.posY) / p_70790_7_;
		double d6 = (this.waypointZ - this.posZ) / p_70790_7_;
		AxisAlignedBB axisalignedbb = this.boundingBox.copy();
		for (int i = 1; i < p_70790_7_; i++)
		{
			axisalignedbb.offset(d4, d5, d6);
			if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
		this.dataWatcher.addObject(19, Integer.valueOf(0));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
	}

	protected String getLivingSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanGhastLiving" : "mob.ghast.moan";
	}

	protected String getHurtSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanGhastGrunt" : "mob.ghast.scream";
	}

	protected String getDeathSound()
	{
		return this.getMinionTypeInt() == 4 ? "thetitans:titanGhastDeath" : "mob.ghast.death";
	}

	protected float getSoundPitch()
	{
		return this.getMinionTypeInt() == 4 ? super.getSoundPitch() + 0.3F : super.getSoundPitch();
	}

	protected Item getDropItem()
	{
		return Items.gunpowder;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		int j = this.rand.nextInt(2) + this.rand.nextInt(1 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.ghast_tear, 1);
		}

		j = this.rand.nextInt(3) + this.rand.nextInt(1 + p_70628_2_);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.gunpowder, 1);
		}

		if (this.getMinionTypeInt() >= 1)
		{
			j = 1 + this.rand.nextInt(4);
			if (p_70628_2_ > 0)
			{
				j += this.rand.nextInt(p_70628_2_ + 1);
			}

			for (int k = 0; k < j; k++)
			{
				dropItem(Items.experience_bottle, 1);
			}

			if (this.getMinionTypeInt() >= 2)
			{
				j = this.rand.nextInt(2);
				if (p_70628_2_ > 0)
				{
					j += this.rand.nextInt(p_70628_2_ + 1);
				}

				for (int k = 0; k < j; k++)
				{
					if (this.rand.nextInt(10) == 0)
					{
						entityDropItem(new ItemStack(Items.golden_apple, 1, 1), 0.0F);
					}

					else
					{
						dropItem(Items.golden_apple, 1);
					}
				}

				if (this.getMinionTypeInt() >= 3)
				{
					j = this.rand.nextInt(2);
					if (p_70628_2_ > 0)
					{
						j += this.rand.nextInt(p_70628_2_ + 1);
					}

					for (int k = 0; k < j; k++)
					{
						switch (this.rand.nextInt(5))
						{
							case 0:entityDropItem(new ItemStack(Blocks.emerald_block, 1, 0), 0.0F);
							break;
							case 1:entityDropItem(new ItemStack(Blocks.diamond_block, 1, 0), 0.0F);
							break;
							case 2:entityDropItem(new ItemStack(Blocks.gold_block, 1, 0), 0.0F);
							break;
							case 3:entityDropItem(new ItemStack(Blocks.gold_block, 1, 0), 0.0F);
							break;
							case 4:entityDropItem(new ItemStack(Blocks.gold_block, 1, 0), 0.0F);
						}
					}

					entityDropItem(new ItemStack(Blocks.obsidian), 0.0F);
					if (this.getMinionTypeInt() >= 4)
					{
						if (this.rand.nextInt(5) == 0)
						{
							entityDropItem(new ItemStack(TitanItems.pleasantBladeSeed), 0.0F);
						}

						if (this.rand.nextInt(100) == 0)
						{
							entityDropItem(new ItemStack(TitanItems.malgrumSeeds), 0.0F);
						}

						j = 2 + this.rand.nextInt(5);
						if (p_70628_2_ > 0)
						{
							j += this.rand.nextInt(p_70628_2_ + 1);
						}

						for (int k = 0; k < j; k++)
						{
							switch (this.rand.nextInt(3))
							{
								case 0:entityDropItem(new ItemStack(Blocks.emerald_block, 1, 0), 0.0F);
								break;
								case 1:entityDropItem(new ItemStack(Blocks.diamond_block, 1, 0), 0.0F);
								break;
								case 2:entityDropItem(new ItemStack(Blocks.gold_block, 1, 0), 0.0F);
							}
						}

						entityDropItem(new ItemStack(Blocks.obsidian), 0.0F);
					}
				}
			}
		}
	}

	protected float getSoundVolume()
	{
		return 10.0F;
	}

	public boolean getCanSpawnHere()
	{
		return (this.rand.nextInt(20) == 0) && (super.getCanSpawnHere()) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL);
	}

	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	protected float applyPotionDamageCalculations(DamageSource p_70672_1_, float p_70672_2_)
	{
		if (this.getMinionTypeInt() == 4)
		{
			p_70672_2_ = super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);
			if (p_70672_1_.getEntity() == this)
			{
				p_70672_2_ = 0.0F;
			}

			if (p_70672_1_.isMagicDamage())
			{
				p_70672_2_ = (float)(p_70672_2_ * 0.15D);
			}

			return p_70672_2_;
		}

		else
		{
			return super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);
		}
	}

	public int getTotalArmorValue()
	{
		switch (this.getMinionTypeInt())
		{
			case 1:
			return 2;
			case 2:
			return 15;
			case 3:
			return 18;
			case 4:
			return 22;
			default:
			return 0;
		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("ExplosionPower", this.explosionStrength);
		tagCompound.setInteger("MinionType", this.getMinionTypeInt());
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		if (tagCompund.hasKey("ExplosionPower", 99))
		{
			this.explosionStrength = tagCompund.getInteger("ExplosionPower");
		}

		this.setMinionType(tagCompund.getInteger("MinionType"));
	}

	public int getMinionTypeInt()
	{
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	public EnumMinionType getMinionType()
	{
		switch (this.getMinionTypeInt())
		{
			case 1:
			return EnumMinionType.PRIEST;
			case 2:
			return EnumMinionType.ZEALOT;
			case 3:
			return EnumMinionType.BISHOP;
			case 4:
			return EnumMinionType.TEMPLAR;
			default:
			return EnumMinionType.LOYALIST;
		}
	}

	public void setMinionType(int miniontype)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(miniontype));
		if (miniontype == 1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
			this.explosionStrength = 2;
			this.setHealth(90F);
			this.experienceValue = 30;
		}

		else if (miniontype == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.explosionStrength = 4;
			this.setHealth(700F);
			this.experienceValue = 200;
		}

		else if (miniontype == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1200.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
			this.explosionStrength = 6;
			this.setHealth(1600F);
			this.experienceValue = 500;
		}

		else if (miniontype == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(3000.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(100.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
			this.explosionStrength = 9;
			this.setHealth(3000F);
			this.experienceValue = 3000;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
			this.setHealth(60F);
			this.explosionStrength = 1;
			this.experienceValue = 15;
		}
	}

	public float getEyeHeight()
	{
		return 3.0F;
	}

	public void onLivingUpdate()
	{
		if (this.getMinionTypeInt() == 1)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
			this.explosionStrength = 2;
			this.experienceValue = 30;
		}

		else if (this.getMinionTypeInt() == 2)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
			this.explosionStrength = 4;
			this.experienceValue = 200;
		}

		else if (this.getMinionTypeInt() == 3)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1200.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
			this.explosionStrength = 6;
			this.experienceValue = 500;
		}

		else if (this.getMinionTypeInt() == 4)
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(3000.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(100.0D);
			this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
			this.explosionStrength = 9;
			this.experienceValue = 3000;
		}

		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
			this.explosionStrength = 1;
			this.experienceValue = 15;
		}

		if (this.isEntityAlive() || this.getMinionTypeInt() != 4)
		super.onLivingUpdate();
		if (this.getMinionTypeInt() == 3)
		{
			if (this.rand.nextInt(120) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityGhastMinion entitychicken = new EntityGhastMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(0);
				this.worldObj.spawnEntityInWorld(entitychicken);
			}

			if (this.rand.nextInt(240) == 0 && this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityGhastMinion entitychicken = new EntityGhastMinion(this.worldObj);
				entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(1);
				this.worldObj.spawnEntityInWorld(entitychicken);
			}
		}

		if (this.getMinionTypeInt() == 4)
		{
			if (this.ticksExisted % 40 == 0)
			heal(1F);
			if (this.worldObj.rand.nextInt(150) == 1)
			heal(2.0F);
			if (this.worldObj.rand.nextInt(100) == 1 && getHealth() < this.getMaxHealth() * 0.75)
			heal(2.0F);
			if (this.worldObj.rand.nextInt(35) == 1 && getHealth() < this.getMaxHealth() * 0.5)
			heal(5.0F);
			if (this.worldObj.rand.nextInt(30) == 1 && getHealth() < this.getMaxHealth() * 0.25)
			heal(5.0F);
			if (this.worldObj.rand.nextInt(30) == 1 && getHealth() < this.getMaxHealth() * 0.05)
			heal(200.0F);
			if ((!this.onGround) && (this.motionY < 0.0D))
			{
				this.motionY *= 0.6D;
			}

			if (this.master == null && (getHealth() > 0.0F) && (!this.worldObj.isRemote) && (this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				if (this.rand.nextInt(60) == 0)
				{
					EntityGhastMinion entitychicken = new EntityGhastMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(0);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(120) == 0)
				{
					EntityGhastMinion entitychicken = new EntityGhastMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(1);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				if (this.rand.nextInt(240) == 0)
				{
					EntityGhastMinion entitychicken = new EntityGhastMinion(this.worldObj);
					entitychicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(2);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}
			}

			if ((this.worldObj.isRemote) && (!this.onGround))
			{
				for (int i = 0; i < 3; i++)
				{
					this.worldObj.spawnParticle("explode", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
				}
			}

			if ((this.rand.nextInt(60) == 0) && (getAttackTarget() != null))
			{
				setCombatTask();
				if (!this.onGround)
				{
					this.attackPattern = 0;
				}

				else
				{
					this.attackPattern = 1;
				}
			}

			if (this.isEntityAlive() && (!this.worldObj.isRemote) && (this.rand.nextInt(1000) == 0) && (getAttackTarget() != null) && (getHealth() < getMaxHealth() / 2.0F) && (this.master == null))
			{
				for (int i = 0; i < 16; i++)
				{
					this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
					this.worldObj.spawnParticle("flame", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
				}

				playSound("thetitans:titanland", 10000.0F, 1.0F);
				TransformEntity(this);
			}

			if (this.onGround)
			{
				this.isAirBorne = false;
			}

			List<?> list11 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(8D, 8D, 8D));
			if (!this.worldObj.isRemote && list11 != null && !list11.isEmpty() && this.ticksExisted % (this.getHealth() < this.getMaxHealth() / 2 ? 40 : 160) == 0)
			{
				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 8F, false);
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (entity != null && entity instanceof EntityLivingBase && this.canAttackClass(entity.getClass()))
					{
						entity.motionY += rand.nextDouble();
						if (worldObj.isRemote)
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, 10, 1));
						this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1017, (int)entity.posX, (int)entity.posY, (int)entity.posZ, 0);
					}
				}
			}
		}

		if (this.getMinionTypeInt() == 1)
		{
			if ((this.ticksExisted % 40 == 0) && (this.entityToHeal != null))
			{
				if (this.entityToHeal.getHealth() < this.entityToHeal.getMaxHealth())
				{
					swingItem();
					faceEntity(this.entityToHeal, 180.0F, getVerticalFaceSpeed());
					this.entityToHeal.heal(4.0F);
					playSound("mob.wither.shoot", 1.0F, 3.0F);
				}

				else
				{
					this.entityToHeal = null;
				}
			}
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable() || source.getDamageType() == "thorns" || (this.getMinionTypeInt() >= 4 && source == DamageSourceExtra.radiation))
		{
			return false;
		}

		if (((source.getEntity() instanceof EntityGhastMinion)) || ((source.getEntity() instanceof EntityGhastTitan)))
		{
			return false;
		}

		if (("fireball".equals(source.getDamageType())) && ((source.getEntity() instanceof EntityPlayer)))
		{
			super.attackEntityFrom(source, 1000.0F);
			((EntityPlayer)source.getEntity()).triggerAchievement(AchievementList.ghast);
			return true;
		}

		Entity entity = source.getEntity();
		if ((source.getEntity() instanceof EntityLivingBase))
		{
			setAttackTarget((EntityLivingBase)entity);
			setRevengeTarget((EntityLivingBase)entity);
		}

		return super.attackEntityFrom(source, amount);
	}

	public void TransformEntity(Entity entity)
	{
		entity.worldObj.newExplosion(entity, entity.posX, entity.posY, entity.posZ, 8.0F, true, entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		EntityGhastTitan entitytitan = new EntityGhastTitan(entity.worldObj);
		entitytitan.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0F);
		entity.setDead();
		entitytitan.func_82206_m();
		entity.worldObj.spawnEntityInWorld(entitytitan);
		entitytitan.playSound("thetitans:titanGhastLiving", 10000.0F, 0.8F);
	}

	public void setCombatTask()
	{
		this.tasks.removeTask(this.aiArrowAttack);
		if (this.attackPattern == 0 && this.getMinionTypeInt() == 4)
		{
			this.tasks.addTask(0, this.aiArrowAttack);
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	{
		super.onSpawnWithEgg(p_110161_1_);
		if (!this.worldObj.isRemote)
		{
			this.setMinionType(0);
			EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, -1D);
			if (player != null)
			if (rand.nextInt(2) == 0 && player.getTotalArmorValue() >= 15)
			{
				setMinionType(1);
				if (rand.nextInt(4) == 0 && player.getTotalArmorValue() >= 20)
				{
					setMinionType(2);
					if (rand.nextInt(6) == 0 && player.getTotalArmorValue() >= 40)
					{
						setMinionType(3);
						if (rand.nextInt(10) == 0 && player.getTotalArmorValue() >= 50)
						{
							setMinionType(4);
						}
					}
				}
			}
		}

		return p_110161_1_;
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		this.attackCounter = 10;
		this.swingItem();
		this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1007, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
		if (getDistanceSqToEntity(p_82196_1_) < (p_82196_1_.width * p_82196_1_.width) + 36D)
		attackEntityAsMob(p_82196_1_);
		else
		switch (this.rand.nextInt(4))
		{
			case 0:for (int i = 0; i < 100; i++)
			{
				EntityHarcadiumArrow entityarrow = new EntityHarcadiumArrow(this.worldObj, this, p_82196_1_, 4F, 16F);
				entityarrow.setIsCritical(true);
				entityarrow.setDamage(p_82196_2_ * 2.0F + this.rand.nextGaussian() * 0.25D + this.worldObj.difficultySetting.getDifficultyId() * 0.11F);
				this.worldObj.spawnEntityInWorld(entityarrow);
				double d8 = 4D;
				Vec3 vec3 = getLook(1.0F);
				entityarrow.posX = (this.posX + vec3.xCoord * d8);
				entityarrow.posY = (this.posY + vec3.yCoord * d8 + 1D);
				entityarrow.posZ = (this.posZ + vec3.zCoord * d8);
			}

			break;
			case 1:for (int i = 0; i < 200; i++)
			{
				EntityPotion entitypotion = new EntityPotion(this.worldObj, this, rand.nextInt(5) == 0 ? 32660 : (rand.nextInt(4) == 0 ? 32696 : (rand.nextInt(3) == 0 ? 32698 : 32732)));
				if (p_82196_1_.isEntityUndead())
				{
					entitypotion.setPotionDamage(32725);
				}

				double d0 = p_82196_1_.posY + 0.5D;
				entitypotion.rotationPitch -= -20.0F;
				double d1 = (p_82196_1_.posX + p_82196_1_.motionX) - this.posX;
				double d2 = d0 - this.posY;
				double d3 = (p_82196_1_.posZ + p_82196_1_.motionZ) - this.posZ;
				float f1 = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
				entitypotion.setThrowableHeading(d1, d2 + f1 * 0.2F, d3, 2F, 25F);
				this.worldObj.spawnEntityInWorld(entitypotion);
				double d8 = 4D;
				Vec3 vec3 = getLook(1.0F);
				entitypotion.posX = (this.posX + vec3.xCoord * d8);
				entitypotion.posY = (this.posY + vec3.yCoord * d8 + 1D);
				entitypotion.posZ = (this.posZ + vec3.zCoord * d8);
			}

			break;
			case 2:this.worldObj.newExplosion(this, p_82196_1_.posX, p_82196_1_.posY + 1D, p_82196_1_.posZ, 2.0F, false, false);
			p_82196_1_.attackEntityFrom(DamageSourceExtra.lightningBolt, 100.0F);
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
			this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ));
			break;
			case 3:for (int i = 0; i < 50; i++)
			{
				double d8 = 4D;
				Vec3 vec3 = getLook(1.0F);
				this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
				getDistanceSqToEntity(p_82196_1_);
				double d11 = (p_82196_1_.posX + p_82196_1_.motionX) - (this.posX + vec3.xCoord * d8);
				double d21 = (p_82196_1_.posY + 1D) - (this.posY + vec3.yCoord * d8 + 1D);
				double d31 = (p_82196_1_.posZ + p_82196_1_.motionZ) - (this.posZ + vec3.zCoord * d8);
				EntityGhastMinionFireball entityfireball = new EntityGhastMinionFireball(this.worldObj, this, d11 + (getRNG().nextGaussian() * 9F), d21, d31 + (getRNG().nextGaussian() * 9F));
				entityfireball.field_92057_e = this.explosionStrength;
				this.worldObj.spawnEntityInWorld(entityfireball);
				entityfireball.posX = (this.posX + vec3.xCoord * d8);
				entityfireball.posY = (this.posY + vec3.yCoord * d8 + 1D);
				entityfireball.posZ = (this.posZ + vec3.zCoord * d8);
			}

			break;
		}
	}

	public class EntityAIFindEntityNearestInjuredAlly extends EntityAIBase
	{
		private EntityGhastMinion field_179434_b;
		private EntityLivingBase field_179433_e;
		public EntityAIFindEntityNearestInjuredAlly(EntityGhastMinion entityCaveSpiderPriest)
		{
			this.field_179434_b = entityCaveSpiderPriest;
		}

		public boolean shouldExecute()
		{
			if (!field_179434_b.isEntityAlive())
			{
				return false;
			}

			if (field_179434_b.getMinionType() != EnumMinionType.PRIEST)
			{
				return false;
			}

			if (this.field_179433_e != null)
			{
				return false;
			}

			double d0 = func_179431_f();
			List<?> list = this.field_179434_b.worldObj.getEntitiesWithinAABB(EntityGhastMinion.class, this.field_179434_b.boundingBox.expand(d0, d0, d0));
			if (list.isEmpty())
			{
				return false;
			}

			for (int i = 0; i < list.size(); i++)
			{
				EntityGhastMinion entity = (EntityGhastMinion)list.get(i);
				if (entity.getHealth() < entity.getMaxHealth() && entity.isEntityAlive())
				this.field_179433_e = entity;
			}

			return true;
		}

		public boolean continueExecuting()
		{
			EntityLivingBase entitylivingbase = this.field_179434_b.entityToHeal;
			if (entitylivingbase == null)
			{
				return false;
			}

			if (!entitylivingbase.isEntityAlive())
			{
				return false;
			}

			if (entitylivingbase.getHealth() >= entitylivingbase.getMaxHealth())
			{
				return false;
			}

			double d0 = func_179431_f();
			return this.field_179434_b.getDistanceSqToEntity(entitylivingbase) <= d0 * d0;
		}

		public void startExecuting()
		{
			this.field_179434_b.entityToHeal = ((EntityLiving)this.field_179433_e);
			super.startExecuting();
		}

		public void resetTask()
		{
			this.field_179434_b.entityToHeal = null;
			this.field_179433_e = null;
			super.resetTask();
		}

		protected double func_179431_f()
		{
			return 100.0D;
		}
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
	public String getSummonSound() 
	{

		return "mob.ghast.moan";
	}

	@Override
	public float getSummonVolume() 
	{

		return 10F;
	}

	@Override
	public float getSummonPitch() 
	{

		return 1F;
	}

	@Override
	public boolean startGrounded() 
	{
		return false;
	}
}


