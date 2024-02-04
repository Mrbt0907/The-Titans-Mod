package net.minecraft.entity.titan.other;
import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titan.EntityFallingBlockTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titanminion.EntityBlazeMinionFireball;
import net.minecraft.entity.titanminion.IMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
public class EntityMagicUser extends EntityMob implements IRangedAttackMob
{
	private int spellCastingTickCount;
	private int spellCastId;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.2D, false);
	private AIPassiveSpell aiPassiveSpells = new AIPassiveSpell();
	private AIAttackSpell aiAttackSpells = new AIAttackSpell();
	private AICastingSpell aiCast = new AICastingSpell();
	public EntityMagicUser(World p_i1738_1_)
	{
		super(p_i1738_1_);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(5, new EntityAIWander(this, 0.75D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, IMinion.class, 0, true, true));
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityTitan.class, 0, true, true));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, 0, true, true));
		if (p_i1738_1_ != null && !p_i1738_1_.isRemote)
		{
			this.setCombatTask();
		}
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte)0));
		this.dataWatcher.addObject(19, Integer.valueOf(0));
	}

	public boolean isCastingSpell()
	{
		return this.worldObj.isRemote ? ((Byte)this.dataWatcher.getWatchableObjectByte(16)).byteValue() > 0 : this.spellCastingTickCount > 0;
	}

	public void setIsCastingSpell(int p_190753_1_)
	{
		this.dataWatcher.updateObject(16, Byte.valueOf((byte)p_190753_1_));
	}

	public int getMobType()
	{
		return this.dataWatcher.getWatchableObjectInt(19);
	}

	public void setMobType(int miniontype)
	{
		this.dataWatcher.updateObject(19, Integer.valueOf(miniontype));
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		switch (this.getMobType())
		{
			case 1:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
				break;
			}

			case 2:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
				break;
			}

			case 3:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
				this.isImmuneToFire = true;
				break;
			}

			case 4:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
				break;
			}

			case 5:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				break;
			}

			case 6:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
				break;
			}

			case 7:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3D);
				break;
			}

			case 8:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10D);
				this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64D);
				break;
			}

			case 9:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				break;
			}

			case 10:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				break;
			}

			case 11:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(800D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
				break;
			}

			case 12:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(400D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64D);
				this.isImmuneToFire = true;
				break;
			}

			default:
			{
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4D);
			}
		}

		this.setCombatTask();
		this.setHealth(this.getMaxHealth());
	}

	private int getSpellCastingTime()
	{
		return this.spellCastingTickCount;
	}

	protected void updateAITasks()
	{
		super.updateAITasks();
		if (this.spellCastingTickCount > 0)
		{
			--this.spellCastingTickCount;
		}
	}

	/**
	* Called to update the entity's position/logic.
	*/
	public void onUpdate()
	{
		super.onUpdate();
		//this.setMobType(5);
		if (this.worldObj.isRemote && this.isCastingSpell())
		{
			int i = ((Byte)this.dataWatcher.getWatchableObjectByte(16)).byteValue();
			double d0 = 0.7D;
			double d1 = 0.5D;
			double d2 = 0.2D;
			if (i == 2)
			{
				d0 = 0.4D;
				d1 = 0.3D;
				d2 = 0.35D;
			}

			else if (i == 1)
			{
				d0 = 0.7D;
				d1 = 0.7D;
				d2 = 0.8D;
			}

			float f = this.renderYawOffset * 0.017453292F + MathHelper.cos((float)this.ticksExisted * 0.6662F) * 0.25F;
			float f1 = MathHelper.cos(f);
			float f2 = MathHelper.sin(f);
			this.worldObj.spawnParticle("mobSpell", this.posX + (double)f1 * 0.6D, this.posY + 1.8D, this.posZ + (double)f2 * 0.6D, d0, d1, d2);
			this.worldObj.spawnParticle("mobSpell", this.posX - (double)f1 * 0.6D, this.posY + 1.8D, this.posZ - (double)f2 * 0.6D, d0, d1, d2);
		}
	}

	/**
	* (abstract) Protected helper method to read subclass entity data from NBT.
	*/
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.spellCastingTickCount = compound.getInteger("SpellTicks");
		this.setMobType(compound.getInteger("Type"));
		this.setCombatTask();
	}

	/**
	* (abstract) Protected helper method to write subclass entity data to NBT.
	*/
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("SpellTicks", this.spellCastingTickCount);
		compound.setInteger("Type", this.getMobType());
	}

	public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_)
	{
		super.setCurrentItemOrArmor(p_70062_1_, p_70062_2_);
		if (!this.worldObj.isRemote && p_70062_1_ == 0)
		{
			this.setCombatTask();
		}
	}

	protected void addRandomArmor()
	{
		int i = this.rand.nextInt(100);
		if (i == 0)
		{
			this.setCurrentItemOrArmor(0, new ItemStack(TitanItems.sacredSword));
		}

		if (i > 0 && i <= 10)
		{
			this.setCurrentItemOrArmor(0, new ItemStack(Items.diamond_sword));
		}

		if (i > 10 && i <= 30)
		{
			this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
		}

		if (i > 30 && i <= 50)
		{
			this.setCurrentItemOrArmor(0, new ItemStack(TitanItems.bronzeSword));
		}

		else
		{
			this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	{
		Object p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
		if (!this.worldObj.isRemote)
		{
			this.setMobType(rand.nextInt(12));
		}

		if (this.getMobType() == 0)
		{
			this.addRandomArmor();
			this.enchantEquipment();
		}

		if (this.getMobType() == 1)
		{
			this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
		}

		if (this.getEquipmentInSlot(4) == null)
		{
			Calendar calendar = this.worldObj.getCurrentDate();
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			{
				this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				this.equipmentDropChances[4] = 0.0F;
			}
		}

		return (IEntityLivingData)p_110161_1_1;
	}

	protected void enchantEquipment()
	{
		float f = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
		if (this.getHeldItem() != null)
		{
			EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(10F + f * (float)this.rand.nextInt(10)));
		}
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		double d0 = p_82196_1_.posX - this.posX;
		double d1 = p_82196_1_.boundingBox.minY + (double)(p_82196_1_.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
		double d2 = p_82196_1_.posZ - this.posZ;
		float f1 = MathHelper.sqrt_float(p_82196_2_) * 0.5F;
		this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
		EntityWitherSkull entitysmallfireball = new EntityWitherSkull(this.worldObj, this, d0 + this.rand.nextGaussian() * (double)f1, d1, d2 + this.rand.nextGaussian() * (double)f1);
		entitysmallfireball.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
		this.worldObj.spawnEntityInWorld(entitysmallfireball);
	}

	public void setCombatTask()
	{
		this.tasks.removeTask(this.aiAttackOnCollide);
		this.tasks.removeTask(this.aiArrowAttack);
		this.tasks.removeTask(this.aiAttackSpells);
		this.tasks.removeTask(this.aiPassiveSpells);
		this.tasks.removeTask(this.aiCast);
		this.getHeldItem();
		if (this.getMobType() == 1)
		{
			this.tasks.addTask(4, this.aiArrowAttack);
		}

		else if (this.getMobType() == 0)
		{
			this.tasks.addTask(4, this.aiAttackOnCollide);
		}

		else
		{
			this.tasks.addTask(2, this.aiCast);
			this.tasks.addTask(3, this.aiAttackSpells);
			this.tasks.addTask(4, this.aiPassiveSpells);
		}
	}

	public String getCommandSenderName()
	{
		switch (this.getMobType())
		{
			case 1:
			return StatCollector.translateToLocal("entity.MagicalArcher.name");
			case 2:
			return StatCollector.translateToLocal("entity.NoviceMage.name");
			case 3:
			return StatCollector.translateToLocal("entity.FireMage.name");
			case 4:
			return StatCollector.translateToLocal("entity.IceMage.name");
			case 5:
			return StatCollector.translateToLocal("entity.EarthMage.name");
			case 6:
			return StatCollector.translateToLocal("entity.ZombieFresh.name");
			case 7:
			return StatCollector.translateToLocal("entity.ZombieRotting.name");
			case 8:
			return StatCollector.translateToLocal("entity.ZombieBrat.name");
			case 9:
			return StatCollector.translateToLocal("entity.ZombieMutilated.name");
			case 10:
			return StatCollector.translateToLocal("entity.ZombieHeadless.name");
			case 11:
			return StatCollector.translateToLocal("entity.ZombieGhoul.name");
			case 12:
			return StatCollector.translateToLocal("entity.ZombieWight.name");
			default:
			return StatCollector.translateToLocal("entity.MagicalGrunt.name");
		}
	}

	class AIAttackSpell extends EntityMagicUser.AIUseSpell
	{
		private AIAttackSpell()
		{
		}

		public boolean shouldExecute()
		{
			if (!super.shouldExecute())
			{
				return false;
			}

			else if (EntityMagicUser.this.getAttackTarget() == null)
			{
				return false;
			}

			else if (EntityMagicUser.this.getMobType() == 9)
			{
				return false;
			}

			else
			{
				return EntityMagicUser.this.rand.nextBoolean();
			}
		}

		protected int getCastingTime()
		{
			return EntityMagicUser.this.getMobType() == 12 ? 20 : 40;
		}

		protected int getCastingInterval()
		{
			return EntityMagicUser.this.getMobType() == 12 ? 10 : 40;
		}

		protected void castSpell()
		{
			EntityLivingBase entitylivingbase = EntityMagicUser.this.getAttackTarget();
			if (!entitylivingbase.worldObj.isRemote && (entitylivingbase != null) && entitylivingbase.isEntityAlive())
			{
				switch (EntityMagicUser.this.getMobType() == 12 ? EntityMagicUser.this.rand.nextInt(12) : EntityMagicUser.this.getMobType())
				{
					case 3:
					{
						int i = MathHelper.floor_double(entitylivingbase.boundingBox.minX - (1 + rand.nextInt(2)));
						int j = MathHelper.floor_double(entitylivingbase.boundingBox.minY - (1 + rand.nextInt(2)));
						int k = MathHelper.floor_double(entitylivingbase.boundingBox.minZ - (1 + rand.nextInt(2)));
						int l = MathHelper.floor_double(entitylivingbase.boundingBox.maxX + (1 + rand.nextInt(2)));
						int i1 = MathHelper.floor_double(entitylivingbase.boundingBox.maxY + (1 + rand.nextInt(2)));
						int j1 = MathHelper.floor_double(entitylivingbase.boundingBox.maxZ + (1 + rand.nextInt(2)));
						for (int k1 = i; k1 <= l; k1++)
						{
							for (int l1 = j; l1 <= i1; l1++)
							{
								for (int i2 = k; i2 <= j1; i2++)
								{
									Block block = EntityMagicUser.this.worldObj.getBlock(k1, l1, i2);
									if (EntityMagicUser.this.worldObj.checkChunksExist(k1, l1, i2, k1, l1, i2) && !block.isAir(EntityMagicUser.this.worldObj, k1, l1, i2))
									{
										if (EntityMagicUser.this.rand.nextInt(2) == 0 && block == Blocks.air)
										{
											EntityMagicUser.this.worldObj.setBlock(k1, l1, i2, Blocks.fire, 0, 3);
										}
									}
								}
							}
						}
					}

					case 4:
					{
						EntityMagicUser.this.playSound("random.explode", 0.5F, 1.0F + (EntityMagicUser.this.rand.nextFloat() - EntityMagicUser.this.rand.nextFloat()) * 0.5F);
						for (int i = 0; i < 5 + EntityMagicUser.this.worldObj.rand.nextInt(5); i++)
						{
							int x = EntityMagicUser.this.worldObj.rand.nextInt(3);
							if (EntityMagicUser.this.worldObj.rand.nextInt(2) == 1)
							{
								x = -x;
							}

							int y = EntityMagicUser.this.worldObj.rand.nextInt(3);
							if (EntityMagicUser.this.worldObj.rand.nextInt(2) == 1)
							{
								y = -y;
							}

							int z = EntityMagicUser.this.worldObj.rand.nextInt(3);
							if (EntityMagicUser.this.worldObj.rand.nextInt(2) == 1)
							{
								z = -z;
							}

							x = (int)(x + entitylivingbase.posX);
							y = (int)(y + entitylivingbase.posY);
							z = (int)(z + entitylivingbase.posZ);
							if (!EntityMagicUser.this.worldObj.getBlock(x, y, z).isOpaqueCube() && EntityMagicUser.this.worldObj.getBlock(x, y, z).getBlockHardness(EntityMagicUser.this.worldObj, x, y, z) != -1)
							{
								EntityMagicUser.this.worldObj.setBlock(x, y, z, Blocks.ice);
								EntityFallingBlockTitan entityfallingblock = new EntityFallingBlockTitan(EntityMagicUser.this.worldObj, x + 0.5D, y + 0.5D, z + 0.5D, Blocks.ice, EntityMagicUser.this.worldObj.getBlockMetadata(x, y, z));
								entityfallingblock.setPosition(x + 0.5D, y + 0.5D, z + 0.5D);
								entityfallingblock.addVelocity(0D, 0.5D, 0D);
								EntityMagicUser.this.worldObj.spawnEntityInWorld(entityfallingblock);
								EntityMagicUser.this.worldObj.setBlockToAir(x, y, z);
							}
						}

						EntityMagicUser.this.worldObj.createExplosion(EntityMagicUser.this, entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, EntityMagicUser.this.getMobType() == 12 ? 3F : 1F, false);
						break;
					}

					case 5:
					{
						int i = MathHelper.floor_double(entitylivingbase.boundingBox.minX - (1 + rand.nextInt(2)));
						int j = MathHelper.floor_double(entitylivingbase.boundingBox.minY - 1);
						int k = MathHelper.floor_double(entitylivingbase.boundingBox.minZ - (1 + rand.nextInt(2)));
						int l = MathHelper.floor_double(entitylivingbase.boundingBox.maxX + (1 + rand.nextInt(2)));
						int i1 = MathHelper.floor_double(entitylivingbase.boundingBox.maxY + 1);
						int j1 = MathHelper.floor_double(entitylivingbase.boundingBox.maxZ + (1 + rand.nextInt(2)));
						for (int k1 = i; k1 <= l; k1++)
						{
							for (int l1 = j; l1 <= i1; l1++)
							{
								for (int i2 = k; i2 <= j1; i2++)
								{
									Block block = EntityMagicUser.this.worldObj.getBlock(k1, l1, i2);
									if (EntityMagicUser.this.worldObj.checkChunksExist(k1, l1, i2, k1, l1, i2) && !block.isAir(EntityMagicUser.this.worldObj, k1, l1, i2))
									{
										if (block.getBlockHardness(EntityMagicUser.this.worldObj, k1, l1, i2) != -1F)
										{
											if (block.getMaterial().isLiquid() || block == Blocks.fire || block == Blocks.web)
											{
												EntityMagicUser.this.worldObj.setBlockToAir(k1, l1, i2);
											}

											else
											{
												EntityFallingBlockTitan entityfallingblock = new EntityFallingBlockTitan(EntityMagicUser.this.worldObj, k1 + 0.5D, l1 + 0.5D, i2 + 0.5D, block, EntityMagicUser.this.worldObj.getBlockMetadata(k1, l1, i2));
												entityfallingblock.setPosition(k1 + 0.5D, l1 + 0.5D, i2 + 0.5D);
												entityfallingblock.addVelocity(0D, 1D, 0D);
												EntityMagicUser.this.worldObj.spawnEntityInWorld(entityfallingblock);
												EntityMagicUser.this.worldObj.setBlockToAir(k1, l1, i2);
											}
										}
									}
								}
							}
						}

						break;
					}

					case 6:
					{
						entitylivingbase.attackEntityFrom(DamageSourceExtra.lightningBolt.setMagicDamage(), 25F);
						EntityMagicUser.this.worldObj.addWeatherEffect(new EntityLightningBolt(EntityMagicUser.this.worldObj, entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ));
						break;
					}

					case 12:
					{
						EntityMagicUser.this.playSound("mob.skeleton.death", 1.0F, 0.5F);
						EntityMagicUser.this.worldObj.playSoundEffect(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, "random.explode", 4.0F, (1.0F + (entitylivingbase.worldObj.rand.nextFloat() - entitylivingbase.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
						for (int i1 = 0; i1 < 256; i1++)
						{
							EntityItem entityitem = entitylivingbase.dropItem(Items.bone, 1);
							entityitem.motionY = 1.0D;
							entityitem.delayBeforeCanPickup = 6000;
							entityitem.lifespan = (40 + EntityMagicUser.this.rand.nextInt(20));
						}

						entitylivingbase.addPotionEffect(new PotionEffect(Potion.wither.id, 400, 3));
						entitylivingbase.attackEntityFrom(DamageSource.wither, 20.0F);
						entitylivingbase.hurtResistantTime = 1;
						break;
					}

					default:
					{
						double d0 = entitylivingbase.posX - EntityMagicUser.this.posX;
						double d1 = entitylivingbase.boundingBox.minY + (double)(entitylivingbase.height / 2.0F) - (EntityMagicUser.this.posY + (double)(EntityMagicUser.this.height / 2.0F));
						double d2 = entitylivingbase.posZ - EntityMagicUser.this.posZ;
						float f1 = MathHelper.sqrt_float(1F) * 0.5F;
						EntityMagicUser.this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)EntityMagicUser.this.posX, (int)EntityMagicUser.this.posY, (int)EntityMagicUser.this.posZ, 0);
						EntityBlazeMinionFireball entitysmallfireball = new EntityBlazeMinionFireball(EntityMagicUser.this.worldObj, EntityMagicUser.this, d0 + EntityMagicUser.this.rand.nextGaussian() * (double)f1, d1, d2 + EntityMagicUser.this.rand.nextGaussian() * (double)f1);
						entitysmallfireball.posY = EntityMagicUser.this.posY + (double)(EntityMagicUser.this.height / 2.0F) + 0.5D;
						EntityMagicUser.this.worldObj.spawnEntityInWorld(entitysmallfireball);
					}
				}
			}
		}

		protected String getSpellPrepareSound()
		{
			return "thetitans:castAttack";
		}

		protected int getSpellId()
		{
			switch (EntityMagicUser.this.getMobType())
			{
				default:
				{
					return 2;
				}
			}
		}
	}

	class AIPassiveSpell extends EntityMagicUser.AIUseSpell
	{
		EntityLivingBase target;
		private AIPassiveSpell()
		{
		}

		public boolean shouldExecute()
		{
			if (!super.shouldExecute())
			{
				return false;
			}

			else if (EntityMagicUser.this.getAttackTarget() == null)
			{
				return false;
			}

			else if (EntityMagicUser.this.getMobType() != 9)
			{
				return false;
			}

			else
			{
				return true;
			}
		}

		protected int getCastingTime()
		{
			return EntityMagicUser.this.getMobType() == 12 ? 20 : 40;
		}

		protected int getCastingInterval()
		{
			return EntityMagicUser.this.getMobType() == 12 ? 10 : 40;
		}

		protected void castSpell()
		{
			EntityLivingBase entitylivingbase = EntityMagicUser.this.getAttackTarget();
			if (!entitylivingbase.worldObj.isRemote && (entitylivingbase != null) && entitylivingbase.isEntityAlive())
			{
				switch (EntityMagicUser.this.getMobType())
				{
					case 12:
					{
						break;
					}

					default:
					{
					}
				}
			}
		}

		protected String getSpellPrepareSound()
		{
			return "thetitans:castPassive";
		}

		protected int getSpellId()
		{
			switch (EntityMagicUser.this.getMobType())
			{
				default:
				{
					return 1;
				}
			}
		}
	}

	class AICastingSpell extends EntityAIBase
	{
		public AICastingSpell()
		{
			this.setMutexBits(3);
		}

		public boolean shouldExecute()
		{
			return EntityMagicUser.this.getSpellCastingTime() > 0;
		}

		public void startExecuting()
		{
			super.startExecuting();
			EntityMagicUser.this.setIsCastingSpell(EntityMagicUser.this.spellCastId);
			EntityMagicUser.this.getNavigator().clearPathEntity();
		}

		public void resetTask()
		{
			super.resetTask();
			EntityMagicUser.this.setIsCastingSpell(0);
		}

		public void updateTask()
		{
			if (EntityMagicUser.this.getAttackTarget() != null)
			{
				EntityMagicUser.this.getLookHelper().setLookPositionWithEntity(EntityMagicUser.this.getAttackTarget(), 10F, (float)EntityMagicUser.this.getVerticalFaceSpeed());
			}
		}
	}

	abstract class AIUseSpell extends EntityAIBase
	{
		protected int spellWarmup;
		protected int nextCastTime;
		private AIUseSpell()
		{
		}

		public boolean shouldExecute()
		{
			return EntityMagicUser.this.getAttackTarget() == null ? false : (EntityMagicUser.this.isCastingSpell() ? false : EntityMagicUser.this.ticksExisted >= this.nextCastTime);
		}

		public boolean continueExecuting()
		{
			return EntityMagicUser.this.getAttackTarget() != null && this.spellWarmup > 0;
		}

		public void startExecuting()
		{
			this.spellWarmup = this.getCastWarmupTime();
			EntityMagicUser.this.spellCastingTickCount = this.getCastingTime();
			this.nextCastTime = EntityMagicUser.this.ticksExisted + this.getCastingInterval();
			EntityMagicUser.this.playSound(this.getSpellPrepareSound(), 1.0F, 1.0F);
			EntityMagicUser.this.spellCastId = this.getSpellId();
		}

		public void updateTask()
		{
			--this.spellWarmup;
			if (this.spellWarmup == 0)
			{
				this.castSpell();
				switch (EntityMagicUser.this.getMobType())
				{
					case 3:
					{
						EntityMagicUser.this.worldObj.playAuxSFX(2001, (int)EntityMagicUser.this.posX, (int)EntityMagicUser.this.posY + 1, (int)EntityMagicUser.this.posZ, Block.getIdFromBlock(Blocks.fire));
						EntityMagicUser.this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1008, (int)EntityMagicUser.this.posX, (int)EntityMagicUser.this.posY, (int)EntityMagicUser.this.posZ, 0);
						break;
					}

					case 4:
					{
						EntityMagicUser.this.worldObj.playAuxSFX(2001, (int)EntityMagicUser.this.posX, (int)EntityMagicUser.this.posY + 1, (int)EntityMagicUser.this.posZ, Block.getIdFromBlock(Blocks.ice));
						break;
					}

					case 5:
					{
						EntityMagicUser.this.worldObj.playAuxSFX(2001, (int)EntityMagicUser.this.posX, (int)EntityMagicUser.this.posY + 1, (int)EntityMagicUser.this.posZ, Block.getIdFromBlock(Blocks.dirt));
						break;
					}

					case 6:
					{
						EntityMagicUser.this.worldObj.playSoundEffect(EntityMagicUser.this.posX, EntityMagicUser.this.posY, EntityMagicUser.this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + EntityMagicUser.this.rand.nextFloat() * 0.2F);
						EntityMagicUser.this.worldObj.playSoundEffect(EntityMagicUser.this.posX, EntityMagicUser.this.posY, EntityMagicUser.this.posZ, "random.explode", 2.0F, 0.5F + EntityMagicUser.this.rand.nextFloat() * 0.2F);
						break;
					}

					default:
					{
						EntityMagicUser.this.playSound("thetitans:cast", 1.0F, 1.0F);
					}
				}
			}
		}

		protected abstract void castSpell();
		protected int getCastWarmupTime()
		{
			return 20;
		}

		protected abstract int getCastingTime();
		protected abstract int getCastingInterval();
		protected abstract String getSpellPrepareSound();
		protected abstract int getSpellId();
	}
}


