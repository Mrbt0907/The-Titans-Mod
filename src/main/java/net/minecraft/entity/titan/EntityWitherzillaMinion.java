package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titanminion.EnumMinionType;
import net.minecraft.entity.titanminion.IMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
public class EntityWitherzillaMinion
extends EntityMob
implements IRangedAttackMob, IMinion
{
	private float[] field_82220_d = new float[2];
	private float[] field_82221_e = new float[2];
	private float[] field_82217_f = new float[2];
	private float[] field_82218_g = new float[2];
	private int[] field_82223_h = new int[2];
	private int[] field_82224_i = new int[2];
	private int blockBreakCounter;
	private static final IEntitySelector attackEntitySelector = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_180027_1_)
		{
			return (!(p_180027_1_ instanceof EntityWitherzilla)) && (!(p_180027_1_ instanceof EntityWitherzillaMinion)) && (!(p_180027_1_ instanceof EntityWitherTurret)) && (!(p_180027_1_ instanceof EntityWitherTurretGround)) && (!(p_180027_1_ instanceof EntityWitherTurretMortar));
		}
	};
	public EntityWitherzillaMinion(World worldIn)
	{
		super(worldIn);
		setHealth(getMaxHealth());
		setSize(0.9F, 3.5F);
		this.isImmuneToFire = true;
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 64.0F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.tasks.addTask(7, new EntityAIWander(this, 0.5D));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
		this.experienceValue = 20;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(0));
		this.dataWatcher.addObject(19, new Integer(0));
		this.dataWatcher.addObject(20, new Integer(0));
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		setInvulTime(tagCompund.getInteger("Invul"));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("Invul", getInvulTime());
	}

	public int getInvulTime()
	{
		return this.dataWatcher.getWatchableObjectInt(20);
	}

	public void setInvulTime(int p_82215_1_)
	{
		this.dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
	}

	public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_)
	{
		return this.worldObj.getBlock(p_70783_1_, p_70783_2_ - 1, p_70783_3_) == Blocks.obsidian ? 10.0F : super.getBlockPathWeight(p_70783_1_, p_70783_2_, p_70783_3_);
	}

	public int getMaxSpawnedInChunk()
	{
		return 12;
	}

	protected boolean isValidLightLevel()
	{
		return true;
	}

	public boolean getCanSpawnHere()
	{
		return true;
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
		return "mob.wither.death";
	}

	public void onLivingUpdate()
	{
		if ((getAttackTarget() instanceof EntityWitherzilla))
		{
			setAttackTarget(null);
		}

		if (this.getInvulTime() < 1)
		this.motionY *= 0.6000000238418579D;
		if (!this.worldObj.isRemote && this.getWatchedTargetId(0) > 0)
		{
			Entity entity = this.worldObj.getEntityByID(this.getWatchedTargetId(0));
			if (entity != null)
			{
				if (this.posY < entity.posY || !this.isArmored() && this.posY < entity.posY + 5.0D + entity.getEyeHeight())
				{
					if (this.motionY < 0.0D)
					{
						this.motionY = 0.0D;
					}

					this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
				}

				double d0 = entity.posX - this.posX;
				double d1 = entity.posZ - this.posZ;
				double d3 = d0 * d0 + d1 * d1;
				if (d3 > 9.0D)
				{
					double d5 = (double)MathHelper.sqrt_double(d3);
					this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
					this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
				}
			}
		}

		if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0D)
		{
			this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * (180F / (float)Math.PI) - 90.0F;
		}

		super.onLivingUpdate();
		for (int i = 0; i < 2; i++)
		{
			this.field_82218_g[i] = this.field_82221_e[i];
			this.field_82217_f[i] = this.field_82220_d[i];
		}

		for (int i = 0; i < 2; i++)
		{
			int j = getWatchedTargetId(i + 1);
			Entity entity1 = null;
			if (j > 0)
			{
				entity1 = this.worldObj.getEntityByID(j);
			}

			if (entity1 != null)
			{
				double d1 = func_82214_u(i + 1);
				double d3 = func_82208_v(i + 1);
				double d5 = func_82213_w(i + 1);
				double d6 = entity1.posX - d1;
				double d7 = entity1.posY + entity1.getEyeHeight() - d3;
				double d8 = entity1.posZ - d5;
				double d9 = MathHelper.sqrt_double(d6 * d6 + d8 * d8);
				float f = (float)(Math.atan2(d8, d6) * 180.0D / 3.141592653589793D) - 90.0F;
				float f1 = (float)-(Math.atan2(d7, d9) * 180.0D / 3.141592653589793D);
				this.field_82220_d[i] = func_82204_b(this.field_82220_d[i], f1, 40.0F);
				this.field_82221_e[i] = func_82204_b(this.field_82221_e[i], f, 10.0F);
			}

			else
			{
				this.field_82221_e[i] = func_82204_b(this.field_82221_e[i], this.renderYawOffset, 10.0F);
			}
		}

		boolean flag = isArmored();
		for (int j = 0; j < 3; j++)
		{
			double d10 = func_82214_u(j);
			double d2 = func_82208_v(j);
			double d4 = func_82213_w(j);
			this.worldObj.spawnParticle("smoke", d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
			if ((flag) && (this.worldObj.rand.nextInt(4) == 0))
			{
				this.worldObj.spawnParticle("mobSpell", d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
			}
		}

		if (getInvulTime() > 0)
		{
			for (int j = 0; j < 3; j++)
			{
				this.worldObj.spawnParticle("mobSpell", this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
			}
		}
	}

	protected boolean isAIEnabled()
	{
		return true;
	}

	protected void updateAITasks()
	{
		if (getInvulTime() > 0)
		{
			int i = getInvulTime() - 1;
			if (i <= 0)
			{
				this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				this.worldObj.playBroadcastSound(1013, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
			}

			setInvulTime(i);
			if (this.ticksExisted % 10 == 0)
			{
				heal(10.0F);
			}
		}

		else
		{
			super.updateAITasks();
			for (int i = 1; i < 3; i++)
			{
				if (this.ticksExisted >= this.field_82223_h[(i - 1)])
				{
					this.field_82223_h[(i - 1)] = (this.ticksExisted + 10 + this.rand.nextInt(10));
					if ((this.worldObj.difficultySetting == EnumDifficulty.NORMAL) || (this.worldObj.difficultySetting == EnumDifficulty.HARD))
					{
						int k2 = i - 1;
						int l2 = this.field_82224_i[(i - 1)];
						this.field_82224_i[k2] = (this.field_82224_i[(i - 1)] + 1);
						if (l2 > 15)
						{
							float f = 10.0F;
							float f1 = 5.0F;
							double d0 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - f, this.posX + f);
							double d1 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - f1, this.posY + f1);
							double d2 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - f, this.posZ + f);
							launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
							this.field_82224_i[(i - 1)] = 0;
						}
					}

					int i1 = getWatchedTargetId(i);
					if (i1 > 0)
					{
						Entity entity = this.worldObj.getEntityByID(i1);
						if ((entity != null) && (entity.isEntityAlive()) && (getDistanceSqToEntity(entity) <= 900.0D) && (canEntityBeSeen(entity)))
						{
							launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
							this.field_82223_h[(i - 1)] = (this.ticksExisted + 40 + this.rand.nextInt(20));
							this.field_82224_i[(i - 1)] = 0;
						}

						else
						{
							func_82211_c(i, 0);
						}
					}

					else
					{
						List<?> list = this.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0D, 8.0D, 20.0D), attackEntitySelector);
						for (int k1 = 0; (k1 < 10) && (!list.isEmpty()); k1++)
						{
							EntityLivingBase entitylivingbase = (EntityLivingBase)list.get(this.rand.nextInt(list.size()));
							if ((entitylivingbase != this) && (entitylivingbase.isEntityAlive()) && (canEntityBeSeen(entitylivingbase)))
							{
								if ((entitylivingbase instanceof EntityPlayer))
								{
									if (((EntityPlayer)entitylivingbase).capabilities.disableDamage)
									break;
									func_82211_c(i, entitylivingbase.getEntityId()); break;
								}

								func_82211_c(i, entitylivingbase.getEntityId());
								break;
							}

							list.remove(entitylivingbase);
						}
					}
				}
			}

			if (getAttackTarget() != null)
			{
				func_82211_c(0, getAttackTarget().getEntityId());
			}

			else
			{
				func_82211_c(0, 0);
			}

			if (this.blockBreakCounter > 0)
			{
				this.blockBreakCounter -= 1;
				if ((this.blockBreakCounter == 0) && (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
				{
					int i = MathHelper.floor_double(this.posY);
					int i1 = MathHelper.floor_double(this.posX);
					int j1 = MathHelper.floor_double(this.posZ);
					boolean flag = false;
					for (int l1 = -1; l1 <= 1; l1++)
					{
						for (int i2 = -1; i2 <= 1; i2++)
						{
							for (int j = 0; j <= 3; j++)
							{
								int j2 = i1 + l1;
								int k = i + j;
								int l = j1 + i2;
								Block block = this.worldObj.getBlock(j2, k, l);
								if ((!block.isAir(this.worldObj, j2, k, l)) && (canEntityDestroy(block, this.worldObj, j2, k, l, this)))
								{
									flag = (this.worldObj.func_147480_a(j2, k, l, true)) || (flag);
								}
							}
						}
					}

					if (flag)
					{
						this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1012, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
					}
				}
			}

			if (this.ticksExisted % 20 == 0)
			{
				heal(1.0F);
			}
		}
	}

	public boolean canEntityDestroy(Block block, IBlockAccess world, int x, int y, int z, Entity entity)
	{
		return (block != Blocks.bedrock) && (block != Blocks.end_portal) && (block != Blocks.end_portal_frame) && (block != Blocks.command_block);
	}

	public void func_82206_m()
	{
		setInvulTime(220);
		setHealth(getMaxHealth() / 3.0F);
	}

	public void setInWeb() 
	{
		 
	}


	public int getTotalArmorValue()
	{
		return 4;
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
		return p_82208_1_ <= 0 ? this.posY + 3.0D : this.posY + 2.25D;
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

	private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_)
	{
		float f3 = MathHelper.wrapAngleTo180_float(p_82204_2_ - p_82204_1_);
		if (f3 > p_82204_3_)
		{
			f3 = p_82204_3_;
		}

		if (f3 < -p_82204_3_)
		{
			f3 = -p_82204_3_;
		}

		return p_82204_1_ + f3;
	}

	private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
	{
		launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
	}

	private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
	{
		this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
		double d3 = func_82214_u(p_82209_1_);
		double d4 = func_82208_v(p_82209_1_);
		double d5 = func_82213_w(p_82209_1_);
		double d6 = p_82209_2_ - d3;
		double d7 = p_82209_4_ - d4;
		double d8 = p_82209_6_ - d5;
		EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.worldObj, this, d6, d7, d8);
		if (p_82209_8_)
		{
			entitywitherskull.setInvulnerable(true);
		}

		entitywitherskull.posY = d4;
		entitywitherskull.posX = d3;
		entitywitherskull.posZ = d5;
		this.worldObj.spawnEntityInWorld(entitywitherskull);
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		launchWitherSkullToEntity(0, p_82196_1_);
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if ((isEntityInvulnerable()) || (source.isExplosion()))
		{
			return false;
		}

		if ((source != DamageSource.drown) && (!(source.getEntity() instanceof EntityWitherzillaMinion)) && (!(source.getEntity() instanceof EntityWitherzilla)))
		{
			if ((getInvulTime() > 0) && (source != DamageSource.outOfWorld))
			{
				return false;
			}

			if (isArmored())
			{
				Entity entity = source.getSourceOfDamage();
				if ((entity instanceof EntityArrow))
				{
					return false;
				}
			}

			Entity entity = source.getEntity();
			if ((entity instanceof EntityLivingBase))
			{
				List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0D, 32.0D, 32.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if ((entity1 instanceof EntityWitherzillaMinion))
					{
						EntityWitherzillaMinion entitypigzombie = (EntityWitherzillaMinion)entity1;
						entitypigzombie.setAttackTarget((EntityLivingBase)entity);
						entitypigzombie.setRevengeTarget((EntityLivingBase)entity);
						entitypigzombie.func_82211_c(1, entity.getEntityId());
						entitypigzombie.func_82211_c(2, entity.getEntityId());
					}

					setAttackTarget((EntityLivingBase)entity);
					setRevengeTarget((EntityLivingBase)entity);
					func_82211_c(1, entity.getEntityId());
					func_82211_c(2, entity.getEntityId());
				}
			}

			if (((entity != null) && ((entity instanceof EntityWitherzilla))) || ((entity != null) && ((entity instanceof EntityWitherzillaMinion))))
			{
				return false;
			}

			if (this.blockBreakCounter <= 0)
			{
				this.blockBreakCounter = 20;
			}

			for (int i = 0; i < this.field_82224_i.length; i++)
			{
				this.field_82224_i[i] += 3;
			}

			return super.attackEntityFrom(source, amount);
		}

		return false;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (this.rand.nextInt(4) == 0)
		{
			dropItem(Items.nether_star, 1);
		}
	}

	protected void despawnEntity()
	{
		this.entityAge = 0;
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_)
	{
		return 15728880;
	}

	protected void fall(float p_70069_1_) 
	{
		 
	}


	public void addPotionEffect(PotionEffect p_70690_1_) 
	{
		 
	}


	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
	}

	@SideOnly(Side.CLIENT)
	public float func_82207_a(int p_82207_1_)
	{
		return this.field_82221_e[p_82207_1_];
	}

	@SideOnly(Side.CLIENT)
	public float func_82210_r(int p_82210_1_)
	{
		return this.field_82220_d[p_82210_1_];
	}

	public int getWatchedTargetId(int p_82203_1_)
	{
		return this.dataWatcher.getWatchableObjectInt(17 + p_82203_1_);
	}

	public void func_82211_c(int p_82211_1_, int p_82211_2_)
	{
		this.dataWatcher.updateObject(17 + p_82211_1_, Integer.valueOf(p_82211_2_));
	}

	public boolean isArmored()
	{
		return getHealth() <= getMaxHealth() / 2.0F;
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	public void mountEntity(Entity entityIn)
	{
		this.ridingEntity = null;
	}

	public EnumMinionType getMinionType()
	{
		return EnumMinionType.LOYALIST;
	}

	@Override
	public void setMinionType(int type)
	{
	}

	@Override
	public EntityLiving getMaster() 
	{
		return null;
	}


	@Override
	public void setMaster(EntityLiving entity) 
	{

	}


	@Override
	public boolean startGrounded() 
	{
		return false;
	}
}


