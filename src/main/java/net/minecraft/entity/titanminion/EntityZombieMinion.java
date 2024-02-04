package net.minecraft.entity.titanminion;
import java.util.Calendar;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.entity.titan.ITitan;
import net.minecraft.entity.titan.ai.EntityAIBreakDoorMinion;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class EntityZombieMinion extends EntityZombie implements IRangedAttackMob, ITemplar
{
	public EntityLiving master;
	public int randomSoundDelay;
	public EntityLiving entityToHeal;
	private int attackPattern;
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 10, 64.0F);
	private float heightOffset = 0.5F;
	private int heightOffsetUpdateTime;
	public EntityZombieMinion(World worldIn)
	{
		super(worldIn);
		for (int i = 0; i < equipmentDropChances.length; ++i)
		{
			equipmentDropChances[i] = 0.2F;
		}

		setSize(0.5F, 1.9F);
		getNavigator().setBreakDoors(true);
		getNavigator().setEnterDoors(true);
		tasks.addTask(0, new EntityAIAvoidEntity(this, EntityWitherSkull.class, 2.0F, 1.2D, 1.75D));
		tasks.addTask(0, new EntityAIAvoidEntity(this, EntityTitanSpirit.class, 48.0F, 1.5D, 1.5D));
		tasks.addTask(0, new EntityAIBreakDoorMinion(this));
		tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.2D));
		tasks.addTask(1, new EntityAIRestrictSun(this));
		tasks.addTask(1, new EntityAIFleeSun(this, 1.2D));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, true));
		tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.ZombieTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0D);
	}

	public void setDead()
	{
		super.setDead();
		if ((master != null) && ((master instanceof EntityTitan)))
		{
			((EntityTitan)master).retractMinionNumFromType(getMinionType());
		}
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(19, Integer.valueOf(0));
	}

	public void setCombatTask()
	{
		tasks.removeTask(aiArrowAttack);
		if (attackPattern == 0 && getMinionTypeInt() == 4)
		{
			tasks.addTask(0, aiArrowAttack);
		}
	}

	protected String getLivingSound()
	{
		if (getMinionTypeInt() == 4)
		playSound("mob.zombie.say", getSoundVolume(), getSoundPitch() - 0.5F);
		return getMinionTypeInt() >= 4 ? "thetitans:titanZombieLiving" : "mob.zombie.say";
	}

	protected String getHurtSound()
	{
		if (getMinionTypeInt() == 4)
		playSound("mob.zombie.hurt", getSoundVolume(), getSoundPitch() - 0.5F);
		return getMinionTypeInt() >= 4 ? "thetitans:titanZombieGrunt" : "mob.zombie.hurt";
	}

	protected String getDeathSound()
	{
		if (getMinionTypeInt() == 4)
		playSound("mob.zombie.death", getSoundVolume(), getSoundPitch() - 0.5F);
		return getMinionTypeInt() >= 4 ? "thetitans:titanZombieDeath" : "mob.zombie.death";
	}

	protected float getSoundPitch()
	{
		return getMinionTypeInt() >= 4 ? super.getSoundPitch() + (getMinionTypeInt() == 6 ? 0F : 0.2F) : super.getSoundPitch();
	}

	public int getTotalArmorValue()
	{
		int i = 2;
		ItemStack[] aitemstack = getLastActiveItems();
		int j = aitemstack.length;
		for (int k = 0; k < j; ++k)
		{
			ItemStack itemstack = aitemstack[k];
			if (itemstack != null && itemstack.getItem() instanceof ItemArmor)
			{
				int l = ((ItemArmor)itemstack.getItem()).damageReduceAmount;
				i += l;
			}
		}

		switch (getMinionTypeInt())
		{
			case 1:
			i += 2;
			break;
			case 2:
			i += 15;
			break;
			case 3:
			i += 18;
			break;
			case 4:
			i += 22;
		}

		if (i > 24)
		{
			i = 24;
		}

		return i;
	}

	protected float applyPotionDamageCalculations(DamageSource p_70672_1_, float p_70672_2_)
	{
		if (getMinionTypeInt() == 4)
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

	public String getCommandSenderName()
	{
		switch (getMinionTypeInt())
		{
			case 1:
			return StatCollector.translateToLocal("entity.ZombiePriest.name");
			case 2:
			return StatCollector.translateToLocal("entity.ZombieZealot.name");
			case 3:
			return StatCollector.translateToLocal("entity.ZombieBishop.name");
			case 4:
			return StatCollector.translateToLocal("entity.ZombieTemplar.name");
			default:
			return StatCollector.translateToLocal("entity.ZombieLoyalist.name");
		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("MinionType", getMinionTypeInt());
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		setMinionType(tagCompund.getInteger("MinionType"));
	}

	public int getMinionTypeInt()
	{
		return dataWatcher.getWatchableObjectInt(19);
	}

	public EnumMinionType getMinionType()
	{
		switch (getMinionTypeInt())
		{
			case 1:
			return EnumMinionType.PRIEST;
			case 2:
			return EnumMinionType.ZEALOT;
			case 3:
			return EnumMinionType.BISHOP;
			case 4:
			return EnumMinionType.TEMPLAR;
			case 5:
			return EnumMinionType.LORD;
			case 6:
			return EnumMinionType.DEMITITAN;
			default:
			return EnumMinionType.LOYALIST;
		}
	}

	public void setMinionType(int miniontype)
	{
		dataWatcher.updateObject(19, Integer.valueOf(miniontype));
		switch (miniontype)
		{
			case 1:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
				tasks.addTask(0, new EntityAIFindEntityNearestInjuredAlly(this));
				experienceValue = 10;
				break;
			}

			case 2:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(180D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.375D);
				experienceValue = 50;
				break;
			}

			case 3:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(540D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(13.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
				isImmuneToFire = true;
				experienceValue = 250;
				break;
			}

			case 4:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1620D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(21.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
				getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				isImmuneToFire = true;
				experienceValue = 1250;
				break;
			}

			case 5:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4860D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(34.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.625D);
				getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				isImmuneToFire = true;
				experienceValue = 6250;
				break;
			}

			case 6:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14580D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(55.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
				getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				isImmuneToFire = true;
				experienceValue = 31250;
				break;
			}

			default:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
				experienceValue = 7;
			}
		}

		setHealth(getMaxHealth());
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_)
	{
		return getMinionTypeInt() == 4 ? 15728880 : super.getBrightnessForRender(p_70070_1_);
	}

	/**
	* Gets how bright this entity is.
	*/
	public float getBrightness(float p_70013_1_)
	{
		return getMinionTypeInt() == 4 ? 1.0F : super.getBrightness(p_70013_1_);
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (master != null)
		return;
		super.dropFewItems(p_70628_1_, p_70628_2_);
		int j = rand.nextInt(3);
		if (p_70628_2_ > 0)
		{
			j += rand.nextInt(p_70628_2_ + 1);
		}

		for (int k = 0; k < j; k++)
		{
			dropItem(Items.feather, 1);
		}

		j = rand.nextInt(2);
		for (int k = 0; k < j; k++)
		{
			dropItem(Items.bone, 1);
		}

		if (getMinionTypeInt() >= 1)
		{
			j = rand.nextInt(2);
			if (p_70628_2_ > 0)
			{
				j += rand.nextInt(p_70628_2_ + 1);
			}

			for (int k = 0; k < j; k++)
			{
				dropItem(Items.experience_bottle, 1);
			}

			if (getMinionTypeInt() >= 2)
			{
				j = rand.nextInt(2);
				if (p_70628_2_ > 0)
				{
					j += rand.nextInt(p_70628_2_ + 1);
				}

				for (int k = 0; k < j; k++)
				{
					dropItem(Items.golden_apple, 1);
				}

				if (getMinionTypeInt() >= 3)
				{
					j = rand.nextInt(2);
					if (p_70628_2_ > 0)
					{
						j += rand.nextInt(p_70628_2_ + 1);
					}

					for (int k = 0; k < j; k++)
					{
						switch (rand.nextInt(5))
						{
							case 0:dropItem(Items.emerald, 1);
							break;
							case 1:dropItem(Items.diamond, 1);
							break;
							case 2:dropItem(Items.gold_ingot, 1);
							break;
							case 3:dropItem(Items.gold_ingot, 1);
							break;
							case 4:dropItem(Items.gold_ingot, 1);
						}
					}

					if (getMinionTypeInt() >= 4)
					{
						if (rand.nextInt(5) == 0)
						{
							entityDropItem(new ItemStack(TitanItems.pleasantBladeSeed), 0.0F);
						}

						if (rand.nextInt(100) == 0)
						{
							entityDropItem(new ItemStack(TitanItems.malgrumSeeds), 0.0F);
						}

						j = 2 + rand.nextInt(5);
						if (p_70628_2_ > 0)
						{
							j += rand.nextInt(p_70628_2_ + 1);
						}

						for (int k = 0; k < j; k++)
						{
							switch (rand.nextInt(3))
							{
								case 0:dropItem(Items.emerald, 1);
								break;
								case 1:dropItem(Items.diamond, 1);
								break;
								case 2:dropItem(Items.gold_ingot, 1);
							}
						}
					}
				}
			}
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		if (super.attackEntityAsMob(p_70652_1_))
		{
			if (p_70652_1_ instanceof EntityLivingBase && getMinionTypeInt() >= 3)
			{
				byte b0 = 10;
				if (worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					b0 = 20;
				}

				else if (worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					b0 = 30;
				}

				if (b0 > 0)
				{
					if (worldObj.isRemote)
					((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, b0 * 20, 0));
				}
			}

			return true;
		}

		else
		{
			return false;
		}
	}

	protected void dropRareDrop(int p_70600_1_)
	{
		super.dropRareDrop(p_70600_1_);
		entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0F);
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ != EntityZombieMinion.class) && (p_70686_1_ != EntityGiantZombieBetter.class) && (p_70686_1_ != EntityZombieTitan.class);
	}

	protected void fall(float p_70069_1_)
	{
		if (getMinionTypeInt() != 4)
		super.fall(p_70069_1_);
		moveForward = 0F;
		moveStrafing = 0F;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable() || isFriendly(source.getEntity()) || (getMinionTypeInt() >= 4 && source == DamageSourceExtra.radiation))
		return false;
		if (source.getEntity() != null)
		{
			int difficulty = TheTitans.getDifficulty(worldObj);
			if (getMinionType() == EnumMinionType.ZEALOT && moveStrafing == 0F)
			{
				boolean shouldDodge;
				switch (difficulty)
				{
					case 0: shouldDodge = false; break;
					case 1: shouldDodge = false; break;
					case 2: shouldDodge = Maths.chance(10); break;
					case 3: shouldDodge = Maths.chance(33); break;
					case 4: shouldDodge = Maths.chance(50); break;
					case 5: shouldDodge = Maths.chance(75); break;
					default: shouldDodge = true;
				}

				if (shouldDodge)
				{
					renderYawOffset = rotationYaw = rotationYawHead;
					playSound("thetitans:titanSwing", 1F, 2F);
					switch (rand.nextInt(3))
					{
						case 0:
						{
							moveForward = -2F;
							moveFlying(0F, -2F, 0.99F);
							moveStrafing = 0.01F;
							break;
						}

						case 1:
						{
							moveStrafing = 1F;
							moveFlying(1F, 0F, 0.25F);
							break;
						}

						case 2:
						{
							moveStrafing = -1F;
							moveFlying(-1F, 0F, 0.25F);
						}
					}

					jump();
					return false;
				}
			}

			Entity entity = source.getEntity();
			if ((entity instanceof EntityLivingBase))
			{
				List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(32.0D, 32.0D, 32.0D));
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity1 = (Entity)list.get(i);
					if (entity1 instanceof EntityZombieMinion)
					{
						EntityZombieMinion entitypigzombie = (EntityZombieMinion)entity1;
						if (entitypigzombie.getAttackTarget() == null)
						entitypigzombie.setAttackTarget((EntityLivingBase)entity);
						entitypigzombie.setRevengeTarget((EntityLivingBase)entity);
						entitypigzombie.randomSoundDelay = rand.nextInt(40);
					}

					setAttackTarget((EntityLivingBase)entity);
					setRevengeTarget((EntityLivingBase)entity);
					randomSoundDelay = rand.nextInt(40);
				}
			}
		}

		return super.attackEntityFrom(source, amount);
	}

	protected void addRandomArmor()
	{
		if (rand.nextFloat() < (worldObj.difficultySetting == EnumDifficulty.NORMAL ? 0.25F : worldObj.difficultySetting == EnumDifficulty.HARD ? 0.95F : 0.05F))
		{
			int i = rand.nextInt(2);
			float f = worldObj.difficultySetting == EnumDifficulty.HARD ? 0.2F : 0.1F;
			if (rand.nextFloat() < 0.25F)
			{
				i++;
			}

			if (rand.nextFloat() < 0.25F)
			{
				i++;
			}

			if (rand.nextFloat() < 0.25F)
			{
				i++;
			}

			for (int j = 3; j >= 0; j--)
			{
				ItemStack itemstack = func_130225_q(j);
				if ((j < 3) && (rand.nextFloat() < f))
				{
					break;
				}

				if (itemstack == null)
				{
					Item item = getArmorItemForSlot(j + 1, i);
					if (item != null)
					{
						setCurrentItemOrArmor(j + 1, new ItemStack(item));
					}
				}
			}
		}

		if (rand.nextFloat() < (worldObj.difficultySetting == EnumDifficulty.HARD ? 0.75F : 0.1F))
		{
			int i = rand.nextInt(3);
			if (i == 0)
			{
				setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
			}

			else
			{
				setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
			}
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
	{
		Object p_110161_1_1 = p_110161_1_;
		float f = worldObj.func_147462_b(posX, posY, posZ);
		setCanPickUpLoot(true);
		if (p_110161_1_1 == null)
		{
			p_110161_1_1 = new GroupData(worldObj.rand.nextFloat() < (worldObj.difficultySetting == EnumDifficulty.NORMAL ? 0.05F : worldObj.difficultySetting == EnumDifficulty.HARD ? 0.25F : 0.005F), worldObj.rand.nextFloat() < 0.1F, null);
		}

		if ((p_110161_1_1 instanceof GroupData))
		{
			GroupData groupdata = (GroupData)p_110161_1_1;
			if (groupdata.field_142046_b)
			{
				setVillager(true);
			}

			if (groupdata.field_142048_a)
			{
				setChild(true);
			}
		}

		func_146070_a(true);
		addRandomArmor();
		enchantEquipment();
		Calendar calendar = worldObj.getCurrentDate();
		if (!isChild() && !isVillager() && (calendar.get(2) + 1 == 10 && calendar.get(5) >= 1 && calendar.get(5) <= 31) && (rand.nextFloat() < 0.5F))
		{
			setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
			equipmentDropChances[4] = 0.0F;
		}

		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", rand.nextDouble() * 0.1D, 0));
		double d0 = rand.nextDouble() * 1.5D * f;
		if (d0 > 1.0D)
		{
			getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
		}

		if (rand.nextFloat() < f * (getMinionTypeInt() * 0.1F + 0.1F) || getMinionType() == EnumMinionType.TEMPLAR)
		{
			getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 0.25D + 0.5D, 0));
			getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 3.0D + 4.0D, 2));
		}

		if (!worldObj.isRemote)
		{
			setMinionType(0);
			EntityPlayer player = worldObj.getClosestPlayerToEntity(this, -1D);
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

		getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", rand.nextGaussian(), 1));
		return p_110161_1_;
	}

	public void onLivingUpdate()
	{
		switch (getMinionTypeInt())
		{
			case 1:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
				experienceValue = 10;
				break;
			}

			case 2:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(180D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.375D);
				experienceValue = 50;
				break;
			}

			case 3:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(540D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(13.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
				isImmuneToFire = true;
				experienceValue = 250;
				break;
			}

			case 4:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1620D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(21.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
				getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				isImmuneToFire = true;
				experienceValue = 1250;
				break;
			}

			case 5:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4860D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(34.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.625D);
				getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				isImmuneToFire = true;
				experienceValue = 6250;
				break;
			}

			case 6:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(14580D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(55.0D);
				getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
				getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
				isImmuneToFire = true;
				experienceValue = 31250;
				break;
			}

			default:
			{
				getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
				experienceValue = 7;
			}
		}

		if (isEntityAlive() || getMinionTypeInt() != 4)
		super.onLivingUpdate();
		if (getMinionTypeInt() == 3)
		{
			if (rand.nextInt(120) == 0 && master == null && (getHealth() > 0.0F) && (!worldObj.isRemote) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityZombieMinion entitychicken = new EntityZombieMinion(worldObj);
				entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(0);
				entitychicken.setChild(isChild());
				entitychicken.setVillager(isVillager());
				worldObj.spawnEntityInWorld(entitychicken);
			}

			if (rand.nextInt(240) == 0 && master == null && (getHealth() > 0.0F) && (!worldObj.isRemote) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				EntityZombieMinion entitychicken = new EntityZombieMinion(worldObj);
				entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				entitychicken.setMinionType(1);
				entitychicken.setChild(isChild());
				entitychicken.setVillager(isVillager());
				worldObj.spawnEntityInWorld(entitychicken);
			}
		}

		if (getMinionTypeInt() == 4)
		{
			if (ticksExisted % 40 == 0)
			heal(1F);
			if (worldObj.rand.nextInt(150) == 1)
			heal(2.0F);
			if (worldObj.rand.nextInt(100) == 1 && getHealth() < getMaxHealth() * 0.75)
			heal(2.0F);
			if (worldObj.rand.nextInt(35) == 1 && getHealth() < getMaxHealth() * 0.5)
			heal(5.0F);
			if (worldObj.rand.nextInt(30) == 1 && getHealth() < getMaxHealth() * 0.25)
			heal(5.0F);
			if (worldObj.rand.nextInt(30) == 1 && getHealth() < getMaxHealth() * 0.05)
			heal(200.0F);
			if ((!onGround) && (motionY < 0.0D))
			{
				motionY *= 0.6D;
			}

			if (master == null && (getHealth() > 0.0F) && (!worldObj.isRemote) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL))
			{
				if (rand.nextInt(60) == 0)
				{
					EntityZombieMinion entitychicken = new EntityZombieMinion(worldObj);
					entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(0);
					entitychicken.setChild(isChild());
					entitychicken.setVillager(isVillager());
					worldObj.spawnEntityInWorld(entitychicken);
				}

				if (rand.nextInt(120) == 0)
				{
					EntityZombieMinion entitychicken = new EntityZombieMinion(worldObj);
					entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(1);
					entitychicken.setChild(isChild());
					entitychicken.setVillager(isVillager());
					worldObj.spawnEntityInWorld(entitychicken);
				}

				if (rand.nextInt(240) == 0)
				{
					EntityZombieMinion entitychicken = new EntityZombieMinion(worldObj);
					entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setMinionType(2);
					entitychicken.setChild(isChild());
					entitychicken.setVillager(isVillager());
					worldObj.spawnEntityInWorld(entitychicken);
				}
			}

			if ((worldObj.isRemote) && (!onGround))
			{
				for (int i = 0; i < 3; i++)
				{
					worldObj.spawnParticle("explode", posX + (rand.nextDouble() - 0.5D) * width, posY, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
				}
			}

			if ((rand.nextInt(60) == 0) && (getAttackTarget() != null))
			{
				setCombatTask();
				if (!onGround)
				{
					attackPattern = 0;
				}

				else
				{
					attackPattern = 1;
				}
			}

			--heightOffsetUpdateTime;
			if (heightOffsetUpdateTime <= 0)
			{
				jump();
				heightOffsetUpdateTime = 100;
				heightOffset = 0.5F + (float)rand.nextGaussian() * 3.0F;
				attackPattern = 0;
			}

			EntityLivingBase entitylivingbase = getAttackTarget();
			if ((attackPattern == 0) && (entitylivingbase != null) && !worldObj.isRemote)
			{
				if ((entitylivingbase.posY + entitylivingbase.getEyeHeight() > posY + getEyeHeight() + heightOffset))
				{
					motionY += (0.4D - motionY);
					isAirBorne = true;
				}

				getLookHelper().setLookPositionWithEntity(entitylivingbase, 180F, 40F);
				double d0 = entitylivingbase.posX - posX;
				double d1 = entitylivingbase.posZ - posZ;
				double d3 = d0 * d0 + d1 * d1;
				if (d3 > (entitylivingbase.width * entitylivingbase.width) + (width * width) + 16D)
				{
					double d5 = MathHelper.sqrt_double(d3);
					motionX += (d0 / d5 * 0.6D - motionX);
					motionZ += (d1 / d5 * 0.6D - motionZ);
				}
			}

			if (isEntityAlive() && (!worldObj.isRemote) && (rand.nextInt(1000) == 0) && (getAttackTarget() != null) && (getHealth() < getMaxHealth() / 2.0F) && (master == null))
			{
				for (int i = 0; i < 16; i++)
				{
					worldObj.spawnParticle("largesmoke", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
					worldObj.spawnParticle("flame", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
				}

				playSound("thetitans:titanland", 10000.0F, 1.0F);
				TransformEntity(this);
			}

			if (onGround)
			{
				isAirBorne = false;
			}

			List<?> list11 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(8D, 8D, 8D));
			if (!worldObj.isRemote && list11 != null && !list11.isEmpty() && (ticksExisted + getEntityId()) % (getHealth() < getMaxHealth() / 2 ? 40 : 160) == 0)
			{
				worldObj.createExplosion(this, posX, posY, posZ, 8F, false);
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (entity != null && entity instanceof EntityLivingBase && canAttackClass(entity.getClass()))
					{
						entity.motionY += rand.nextDouble();
						if (worldObj.isRemote)
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, 10, 1));
						worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1017, (int)entity.posX, (int)entity.posY, (int)entity.posZ, 0);
					}
				}
			}
		}

		if (getMinionTypeInt() == 2)
		{
			if (getAttackTarget() != null)
			{
				double d0 = getDistanceSqToEntity(getAttackTarget());
				if (d0 < 0.8D)
				{
					attackEntityAsMob(getAttackTarget());
				}

				if ((rand.nextInt(40) == 0) && (onGround) && (d0 < 256.0D) && (getAttackTarget().posY > posY + 3.0D))
				{
					addPotionEffect(new PotionEffect(Potion.jump.id, 60, 7));
					faceEntity(getAttackTarget(), 180.0F, 180.0F);
					double d01 = getAttackTarget().posX - posX;
					double d1 = getAttackTarget().posZ - posZ;
					float f2 = MathHelper.sqrt_double(d01 * d01 + d1 * d1);
					jump();
					motionX = (d01 / f2 * 0.75D * 0.75D + motionX * 0.75D);
					motionZ = (d1 / f2 * 0.75D * 0.75D + motionZ * 0.75D);
				}
			}
		}

		if (getMinionTypeInt() == 1)
		{
			if ((ticksExisted % 40 == 0) && (entityToHeal != null))
			{
				if (entityToHeal.getHealth() < entityToHeal.getMaxHealth())
				{
					swingItem();
					faceEntity(entityToHeal, 180.0F, getVerticalFaceSpeed());
					entityToHeal.heal(4.0F);
					playSound("mob.wither.shoot", 1.0F, 3.0F);
				}

				else
				{
					entityToHeal = null;
				}
			}
		}

		if ((getAttackTarget() != null) && (!getAttackTarget().isEntityAlive()))
		{
			setAttackTarget(null);
		}

		if (master != null)
		{
			if (master.getAttackTarget() != null && master instanceof EntityZombieTitan && (((EntityZombieTitan)master).getAnimID() == 11 && ((EntityZombieTitan)master).getAnimTick() > 80))
			{
				getMoveHelper().setMoveTo(master.getAttackTarget().posX, master.getAttackTarget().posY, master.getAttackTarget().posZ, 10D);
			}

			if ((master.getAttackTarget() != null))
			{
				if (master.getAttackTarget().height < 6F)
				setAttackTarget(master.getAttackTarget());
				else
				getLookHelper().setLookPositionWithEntity(master.getAttackTarget(), 10F, 40F);
			}

			if (ticksExisted % 10 == 0 && (master.getAttackTarget() != null) && master.getAttackTarget() instanceof EntityVillager)
			{
				if (getDistanceSqToEntity(master.getAttackTarget()) > 256D)
				getMoveHelper().setMoveTo(master.getAttackTarget().posX, master.getAttackTarget().posY, master.getAttackTarget().posZ, 1.0D);
				else
				getNavigator().tryMoveToEntityLiving(master.getAttackTarget(), 1.0D);
			}

			else if (getDistanceSqToEntity(master) > 2304.0D)
			{
				getMoveHelper().setMoveTo(master.posX, master.posY, master.posZ, 2.0D);
			}
		}

		else
		{
			List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(100.0D, 100.0D, 100.0D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if ((entity != null) && ((entity instanceof EntityZombieTitan)))
					{
						master = ((EntityZombieTitan)entity);
					}
				}
			}
		}
	}

	protected void updateAITasks()
	{
		if ((randomSoundDelay > 0) && (--randomSoundDelay == 0))
		playSound(getHurtSound(), getSoundVolume(), getSoundPitch() + 0.25F);
		if (isCollidedHorizontally && master != null)
		motionY = 0.2D;
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
		if (getMinionTypeInt() == 4)
		{
			--ticksExisted;
			++deathTicks;
			if (master != null)
			{
				double mx = posX - master.posX;
				double my = (posY + getEyeHeight()) - (master.posY + master.getEyeHeight());
				double mz = posZ - master.posZ;
				short short1 = (short) (getDistanceToEntity(master) * 2);
				for (int f = 0; f < short1; f++)
				{
					double d9 = f / (short1 - 1.0D);
					double d6 = posX + mx * -d9;
					double d7 = (posY + getEyeHeight()) + my * -d9;
					double d8 = posZ + mz * -d9;
					worldObj.spawnParticle("fireworksSpark", d6, d7, d8, master.motionX, master.motionY + 0.2D, master.motionZ);
				}
			}

			if (!worldObj.isRemote)
			{
				if (deathTicks > 150 && deathTicks % 5 == 0)
				{
					dropFewItems(true, 0);
				}

				if (deathTicks == 1)
				{
					worldObj.playBroadcastSound(1018, (int)posX, (int)posY, (int)posZ, 0);
				}
			}

			if (deathTicks >= 180 && deathTicks <= 200)
			{
				float f = (rand.nextFloat() - 0.5F) * width;
				float f1 = (rand.nextFloat() - 0.5F) * height;
				float f2 = (rand.nextFloat() - 0.5F) * width;
				worldObj.spawnParticle("hugeexplosion", posX + (double)f, posY + getEyeHeight() + (double)f1, posZ + (double)f2, 0.0D, 0.0D, 0.0D);
			}

			moveEntity(0.0D, 0.10000000149011612D, 0.0D);
			float f = (rand.nextFloat() - 0.5F) * width;
			float f1 = (rand.nextFloat() - 0.5F) * height;
			float f2 = (rand.nextFloat() - 0.5F) * width;
			worldObj.spawnParticle("largeexplode", posX + (double)f, posY + getEyeHeight() + (double)f1, posZ + (double)f2, 0.0D, 0.0D, 0.0D);
			worldObj.spawnParticle("lava", posX + (double)f, posY + getEyeHeight() + (double)f1, posZ + (double)f2, rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian());
			if (deathTicks == 200 && !worldObj.isRemote)
			{
				if (master != null)
				{
					master.heal(master.getMaxHealth() / 100F);
					for (int i = 0; i < 100; ++i)
					{
						double d2 = rand.nextGaussian() * 0.02D;
						double d0 = rand.nextGaussian() * 0.02D;
						double d1 = rand.nextGaussian() * 0.02D;
						worldObj.spawnParticle("largeexplode", master.posX + (double)(rand.nextFloat() * master.width * 2.0F) - (double)master.width, master.posY + (double)(rand.nextFloat() * master.height), master.posZ + (double)(rand.nextFloat() * master.width * 2.0F) - (double)master.width, d2, d0, d1);
					}
				}

				int i = experienceValue;
				while (i > 0)
				{
					int j = EntityXPOrb.getXPSplit(i);
					i -= j;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, j));
				}

				setDead();
			}
		}

		else
		{
			super.onDeathUpdate();
		}
	}

	public void onKillEntity(EntityLivingBase p_70074_1_)
	{
		if (p_70074_1_ instanceof EntityVillager)
		{
			EntityZombieMinion entityzombie = new EntityZombieMinion(worldObj);
			entityzombie.copyLocationAndAnglesFrom(p_70074_1_);
			worldObj.removeEntity(p_70074_1_);
			entityzombie.onSpawnWithEgg((IEntityLivingData)null);
			entityzombie.setMinionType(getMinionTypeInt());
			entityzombie.setVillager(true);
			if (p_70074_1_.isChild())
			{
				entityzombie.setChild(true);
			}

			worldObj.spawnEntityInWorld(entityzombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);
		}
	}

	public void TransformEntity(Entity entity)
	{
		entity.worldObj.newExplosion(entity, entity.posX, entity.posY, entity.posZ, 18.0F, true, entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		EntityZombieTitan entitytitan = new EntityZombieTitan(entity.worldObj);
		entitytitan.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0F);
		entitytitan.onSpawnWithEgg((IEntityLivingData)null);
		entity.setDead();
		entitytitan.func_82206_m();
		entity.worldObj.spawnEntityInWorld(entitytitan);
		if (isChild())
		{
			entitytitan.setChild(true);
		}

		if (isVillager())
		{
			entitytitan.setVillager(true);
		}
	}

	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
	{
		swingItem();
		if (getDistanceSqToEntity(p_82196_1_) < (p_82196_1_.width * p_82196_1_.width) + 36D)
		attackEntityAsMob(p_82196_1_);
		else
		switch (rand.nextInt(4))
		{
			case 0:EntityArrow entityarrow = new EntityArrow(worldObj, this, p_82196_1_, 1.6F, 1.0F);
			entityarrow.setIsCritical(true);
			entityarrow.setDamage(p_82196_2_ * 2.0F + rand.nextGaussian() * 0.25D + worldObj.difficultySetting.getDifficultyId() * 0.11F);
			playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
			worldObj.spawnEntityInWorld(entityarrow);
			break;
			case 1:EntityPotion entitypotion = new EntityPotion(worldObj, this, 32732);
			if (p_82196_1_.isEntityUndead())
			{
				entitypotion.setPotionDamage(32725);
			}

			double d0 = p_82196_1_.posY + 0.5D;
			entitypotion.rotationPitch -= -20.0F;
			double d1 = p_82196_1_.posX + p_82196_1_.motionX - posX;
			double d2 = d0 - posY;
			double d3 = p_82196_1_.posZ + p_82196_1_.motionZ - posZ;
			float f1 = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
			entitypotion.setThrowableHeading(d1, d2 + f1 * 0.2F, d3, 1.6F, f1 / 20.0F);
			worldObj.spawnEntityInWorld(entitypotion);
			break;
			case 2:double d011 = getDistanceSqToEntity(p_82196_1_);
			double d111 = p_82196_1_.posX - posX;
			double d211 = p_82196_1_.boundingBox.minY + p_82196_1_.height / 2.0F - (posY + p_82196_1_.height / 2.0F);
			double d311 = p_82196_1_.posZ - posZ;
			float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d011)) * 0.1F;
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)posX, (int)posY, (int)posZ, 0);
			EntitySmallFireball entitysmallfireball = new EntitySmallFireball(worldObj, this, d111 + getRNG().nextGaussian() * f, d211, d311 + getRNG().nextGaussian() * f);
			entitysmallfireball.posY = (posY + 1.6D);
			worldObj.spawnEntityInWorld(entitysmallfireball);
			break;
			case 3:playSound("mob.zombie.hurt", 1.0F, 0.5F);
			playSound("mob.zombie.hurt", 1.0F, 0.5F);
			playSound("mob.zombie.hurt", 1.0F, 0.5F);
			playSound("mob.zombie.hurt", 1.0F, 0.5F);
			worldObj.playSoundEffect(p_82196_1_.posX, p_82196_1_.posY, p_82196_1_.posZ, "random.explode", 4.0F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
			for (int i = 0; i < 256; i++)
			{
				EntityItem entityitem = p_82196_1_.dropItem(Items.rotten_flesh, 1);
				entityitem.delayBeforeCanPickup = 6000;
				entityitem.lifespan = (40 + rand.nextInt(20));
			}

			p_82196_1_.addPotionEffect(new PotionEffect(Potion.hunger.id, 100, 2));
			p_82196_1_.attackEntityFrom(DamageSource.starve, 5.0F);
			p_82196_1_.hurtResistantTime = 0;
		}
	}

	class GroupData
	implements IEntityLivingData
	{
		public boolean field_142048_a;
		public boolean field_142046_b;
		private GroupData(boolean p_i2348_2_, boolean p_i2348_3_)
		{
			field_142048_a = false;
			field_142046_b = false;
			field_142048_a = p_i2348_2_;
			field_142046_b = p_i2348_3_;
		}

		GroupData(boolean p_i2349_2_, boolean p_i2349_3_, Object p_i2349_4_)
		{
			this(p_i2349_2_, p_i2349_3_);
		}
	}

	public class EntityAIFindEntityNearestInjuredAlly extends EntityAIBase
	{
		private EntityZombieMinion field_179434_b;
		private EntityLivingBase field_179433_e;
		public EntityAIFindEntityNearestInjuredAlly(EntityZombieMinion entityCaveSpiderPriest)
		{
			field_179434_b = entityCaveSpiderPriest;
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

			if (field_179433_e != null)
			{
				return false;
			}

			double d0 = func_179431_f();
			List<?> list = field_179434_b.worldObj.getEntitiesWithinAABB(EntityZombieMinion.class, field_179434_b.boundingBox.expand(d0, d0, d0));
			if (list.isEmpty())
			{
				return false;
			}

			for (int i = 0; i < list.size(); i++)
			{
				EntityZombieMinion entity = (EntityZombieMinion)list.get(i);
				if (entity.getHealth() < entity.getMaxHealth() && entity.isEntityAlive())
				field_179433_e = entity;
			}

			return true;
		}

		public boolean continueExecuting()
		{
			EntityLivingBase entitylivingbase = field_179434_b.entityToHeal;
			if (entitylivingbase == null)
			{
				return false;
			}

			if (!entitylivingbase.isEntityAlive())
			{
				field_179433_e = null;
				return false;
			}

			if (entitylivingbase.getHealth() >= entitylivingbase.getMaxHealth())
			{
				field_179433_e = null;
				return false;
			}

			double d0 = func_179431_f();
			return field_179434_b.getDistanceSqToEntity(entitylivingbase) <= d0 * d0;
		}

		public void startExecuting()
		{
			field_179434_b.entityToHeal = ((EntityLiving)field_179433_e);
			super.startExecuting();
		}

		public void resetTask()
		{
			field_179434_b.entityToHeal = null;
			field_179433_e = null;
			super.resetTask();
		}

		public void updateTask()
		{
			if (field_179434_b.entityToHeal != null && field_179434_b.getDistanceToEntity(field_179434_b.entityToHeal) > 16D)
			{
				field_179434_b.getNavigator().tryMoveToEntityLiving(field_179434_b.entityToHeal, 1D);
				field_179434_b.getLookHelper().setLookPositionWithEntity(field_179434_b.entityToHeal, 10F, field_179434_b.getVerticalFaceSpeed());
			}
		}

		protected double func_179431_f()
		{
			return 32.0D;
		}
	}

	@Override
	public boolean isFriendly(Entity entity)
	{
		if (entity == null)
		return false;
		return entity instanceof EntityZombieMinion || entity instanceof EntityZombieTitan || entity instanceof EntityGiantZombieBetter;
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

		return 2F;
	}

	@Override
	public float getSummonPitch() 
	{

		return 1F;
	}

	@Override
	public double getSummonYOffset() 
	{

		return 0D;
	}
}


