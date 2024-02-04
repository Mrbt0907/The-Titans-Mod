package net.minecraft.entity.titan;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.ai.EntityAIDefendVillage;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityGargoyle extends EntityGolem
{
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private int attackTimer;
	private int field_175479_bo;
	private EntityLivingBase field_175478_bn;
	private int homeCheckTimer;
	Village villageObj;
	public EntityGargoyle(World p_i1694_1_)
	{
		super(p_i1694_1_);
		switch (this.getGargoyleType())
		{
			case 0:
			default:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
			this.setHealth(50F);
			break;
			case 1:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
			this.setHealth(30F);
			break;
			case 2:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
			this.setHealth(200F);
			break;
			case 3:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
			this.setHealth(80F);
			break;
			case 4:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
			this.setHealth(100F);
			break;
			case 5:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
			this.setHealth(120F);
			break;
			case 6:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			this.setHealth(60F);
			break;
		}

		this.setCurrentItemOrArmor(0, new ItemStack(Blocks.stone));
		this.setSize(0.75F, 2.5F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityGargoyle.AIPerch());
		this.tasks.addTask(2, new EntityGargoyle.AIBeamAttack());
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
		this.tasks.addTask(6, new EntityAIWander(this, 0.75D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAIDefendVillage(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, IMob.mobSelector));
	}

	protected void updateAITick()
	{
		if (--this.homeCheckTimer <= 0)
		{
			this.homeCheckTimer = 70 + this.rand.nextInt(50);
			this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);
			if (this.villageObj == null)
			{
				this.detachHome();
			}

			else
			{
				ChunkCoordinates chunkcoordinates = this.villageObj.getCenter();
				this.setHomeArea(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ, (int)((float)this.villageObj.getVillageRadius() * 0.6F));
			}
		}

		super.updateAITick();
	}

	public void onDeath(DamageSource p_70645_1_)
	{
		if (!this.isPlayerCreated() && this.attackingPlayer != null && this.villageObj != null)
		{
			this.villageObj.setReputationForPlayer(this.attackingPlayer.getCommandSenderName(), -5);
		}

		super.onDeath(p_70645_1_);
	}

	public Village getVillage()
	{
		return this.villageObj;
	}

	public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_)
	{
		return (this.worldObj.getBlock(p_70783_1_, p_70783_2_ - 1, p_70783_3_) == Blocks.planks || this.worldObj.getBlock(p_70783_1_, p_70783_2_ - 1, p_70783_3_) == Blocks.cobblestone) ? 10.0F : this.worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_) - 0.5F;
	}

	public float func_175477_p(float p_175477_1_)
	{
		return ((float)this.field_175479_bo + p_175477_1_) / 80F;
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(13, new Byte((byte)0));
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
		this.dataWatcher.addObject(17, Integer.valueOf(0));
		this.getDataWatcher().addObject(21, Byte.valueOf((byte)0));
	}

	public int getGargoyleType()
	{
		return this.dataWatcher.getWatchableObjectByte(13);
	}

	public void setGargoyleType(int p_82201_1_)
	{
		this.dataWatcher.updateObject(13, Byte.valueOf((byte)p_82201_1_));
		switch (p_82201_1_)
		{
			case 0:
			default:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
			this.setHealth(50F);
			break;
			case 1:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
			this.setHealth(30F);
			break;
			case 2:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
			this.setHealth(200F);
			break;
			case 3:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
			this.setHealth(80F);
			break;
			case 4:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
			this.setHealth(100F);
			break;
			case 5:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
			this.setHealth(120F);
			break;
			case 6:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			this.setHealth(60F);
			break;
		}
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		setPlayerCreated(tagCompund.getBoolean("PlayerCreated"));
		if (tagCompund.hasKey("GargoyleType", 99))
		{
			setGargoyleType(tagCompund.getByte("GargoyleType"));
		}
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("PlayerCreated", isPlayerCreated());
		tagCompound.setByte("GargoyleType", (byte)getGargoyleType());
	}

	public void setAggressive(boolean p_82197_1_)
	{
		this.getDataWatcher().updateObject(21, Byte.valueOf((byte)(p_82197_1_ ? 1 : 0)));
	}

	public boolean getAggressive()
	{
		return this.getDataWatcher().getWatchableObjectByte(21) == 1;
	}

	protected float applyPotionDamageCalculations(DamageSource p_70672_1_, float p_70672_2_)
	{
		p_70672_2_ = super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);
		if (p_70672_1_.getEntity() == this)
		{
			p_70672_2_ = 0.0F;
		}

		if (p_70672_1_.isExplosion())
		{
			p_70672_2_ = (float)((double)p_70672_2_ * 2D);
		}

		if (p_70672_1_.isFireDamage())
		{
			p_70672_2_ = 0F;
		}

		return p_70672_2_;
	}

	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return (p_70686_1_ == EntityGargoyle.class) || (p_70686_1_ == EntityGargoyleTitan.class) ? false : (isPlayerCreated() && EntityPlayer.class.isAssignableFrom(p_70686_1_) ? false : super.canAttackClass(p_70686_1_));
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

	/**
	* Returns true if the newer Entity AI code should be run
	*/
	public boolean isAIEnabled()
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public int getAttackTimer()
	{
		return this.attackTimer;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	/**
	* Decrements the entity's air supply when underwater
	*/
	protected int decreaseAirSupply(int p_70682_1_)
	{
		return p_70682_1_;
	}

	protected void collideWithEntity(Entity p_82167_1_)
	{
		if (p_82167_1_ instanceof IMob && this.getRNG().nextInt(20) == 0)
		{
			this.setAttackTarget((EntityLivingBase)p_82167_1_);
		}

		if (p_82167_1_ instanceof EntityGargoyle && this.getAttackTarget() == null && ((EntityGargoyle)p_82167_1_).getAttackTarget() == null)
		{
			if (this.onGround && ((EntityGargoyle)p_82167_1_).onGround && this.getDistanceSq(((EntityGargoyle)p_82167_1_).waypointX, ((EntityGargoyle)p_82167_1_).waypointY, ((EntityGargoyle)p_82167_1_).waypointZ) < 4D)
			{
				++this.waypointY;
				this.noClip = false;
			}
		}

		super.collideWithEntity(p_82167_1_);
	}

	/**
	* Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	* use this to react to sunlight and start to burn.
	*/
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if (this.ticksExisted == 1)
		{
			switch (this.getGargoyleType())
			{
				case 0:
				default:
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
				this.setHealth(50F);
				case 1:
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
				this.setHealth(30F);
				case 2:
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
				this.setHealth(200F);
				case 3:
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
				this.setHealth(80F);
				case 4:
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
				this.setHealth(100F);
				case 5:
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
				this.setHealth(120F);
				case 6:
				this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
				this.setHealth(60F);
			}
		}

		if (this.getGargoyleType() == 6)
		{
			this.worldObj.spawnParticle("flame", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("largesmoke", this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
		}

		if (this.attackTimer > 0)
		{
			--this.attackTimer;
		}

		if (this.getVillage() != null && !this.worldObj.isRemote && this.getAttackTarget() == null && this.getDistanceSq(this.getVillage().getCenter().posX, this.getVillage().getCenter().posY, this.getVillage().getCenter().posZ) > 32D * 32D)
		{
			double d0 = this.getVillage().getCenter().posX - this.posX;
			double d1 = this.getVillage().getCenter().posZ - this.posZ;
			double d3 = d0 * d0 + d1 * d1;
			if (this.posY <= this.getVillage().getCenter().posY + 1D)
			this.motionY += 0.6D - this.motionY;
			double d5 = MathHelper.sqrt_double(d3);
			this.motionX += d0 / d5 * 0.6D - this.motionX;
			this.motionZ += d1 / d5 * 0.6D - this.motionZ;
		}

		if (this.getVillage() != null && !this.worldObj.isRemote && this.getAttackTarget() == null)
		{
			List<?> list = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox((double)(this.getVillage().getCenter().posX - this.getVillage().getVillageRadius()), (double)(this.getVillage().getCenter().posY - 4), (double)(this.getVillage().getCenter().posZ - this.getVillage().getVillageRadius()), (double)(this.getVillage().getCenter().posX + this.getVillage().getVillageRadius()), (double)(this.getVillage().getCenter().posY + 4), (double)(this.getVillage().getCenter().posZ + this.getVillage().getVillageRadius())));
			if (list != null && !list.isEmpty() && this.rand.nextInt(2) == 0)
			{
				for (int i = 0; i < list.size(); ++i)
				{
					Entity entity = (Entity)list.get(i);
					if (this.isEntityAlive() && entity.isEntityAlive() && this.canAttackClass(entity.getClass()) && entity instanceof EntityLivingBase && entity instanceof IMob)
					{
						this.setAttackTarget((EntityLivingBase)entity);
					}
				}
			}
		}

		if (!this.worldObj.isRemote && this.getAttackTarget() == null)
		{
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(16D, 32D, 16D));
			if (list != null && !list.isEmpty() && this.rand.nextInt(20) == 0)
			{
				for (int i = 0; i < list.size(); ++i)
				{
					Entity entity = (Entity)list.get(i);
					if (this.isEntityAlive() && entity.isEntityAlive() && this.canAttackClass(entity.getClass()) && entity instanceof EntityLivingBase && entity instanceof IMob)
					{
						this.setAttackTarget((EntityLivingBase)entity);
					}
				}
			}
		}

		if ((this.getAttackTarget() != null && !this.getAttackTarget().isEntityAlive()))
		{
			this.setAttackTarget(null);
		}

		if (this.getAttackTarget() == null && this.getNatureBlock(this.worldObj.getBlock((int)waypointX, (int)waypointY, (int)waypointZ)) && this.getNatureBlock(this.worldObj.getBlock((int)waypointX, (int)waypointY, (int)waypointZ)) && this.worldObj.canBlockSeeTheSky((int)waypointX, (int)waypointY + 1, (int)waypointZ))
		{
			double d0 = this.waypointX - this.posX;
			double d1 = this.waypointY + 1 - this.posY;
			double d2 = this.waypointZ - this.posZ;
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;
			if (d3 == 4D)
			this.renderYawOffset = this.rotationYaw = this.rotationYawHead += 180F;
			if (d3 > 3D)
			{
				double d5 = MathHelper.sqrt_double(d3);
				this.motionX += d0 / d5 * 0.6D - this.motionX;
				this.motionY += d1 / d5 * 0.6D - this.motionY;
				this.motionZ += d2 / d5 * 0.6D - this.motionZ;
				this.getLookHelper().setLookPosition(waypointX, waypointY, waypointZ, 180F, 0F);
				this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
				this.noClip = true;
			}

			else
			{
				this.setLocationAndAngles(waypointX + 0.5D, waypointY + 1D, waypointZ + 0.5D, this.rotationYawHead, 40F);
				this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
				this.noClip = false;
				this.extinguish();
				if (this.ticksExisted % (this.worldObj.getBlock((int)posX, (int)posY - 1, (int)posZ) == this.getFavoriteBlockToPerch() ? 20 : 40) == 0)
				this.heal(this.worldObj.getBlock((int)posX, (int)posY - 1, (int)posZ) == this.getFavoriteBlockToPerch() ? 2F : 1F);
			}
		}

		Entity entity = this.getAttackTarget();
		double move = entity != null ? 0.2D + (this.rand.nextDouble() * 0.6D) : 0.6D;
		if (entity != null)
		this.setAggressive(true);
		else
		this.setAggressive(false);
		if (!this.onGround && this.motionY < 0.0D)
		{
			this.motionY *= move;
		}

		if (!this.worldObj.isRemote && entity != null)
		{
			double d0 = entity.posX - this.posX;
			double d1 = entity.posZ - this.posZ;
			double d3 = d0 * d0 + d1 * d1;
			if ((d3 > (this.getGargoyleType() == 3 ? 256D : 4D)) && (this.canEntityBeSeen(entity) || this.isEntityInsideOpaqueBlock() || this.posY <= 0D))
			{
				if (this.posY <= entity.posY + 1D)
				this.motionY += move - this.motionY;
				double d5 = MathHelper.sqrt_double(d3);
				this.motionX += d0 / d5 * move - this.motionX;
				this.motionZ += d1 / d5 * move - this.motionZ;
				this.faceEntity(entity, 180F, 40F);
				this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
			}
		}

		if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0)
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

		if (this.getNatureBlock(this.worldObj.getBlock((int)waypointX, (int)waypointY + 1, (int)waypointZ)))
		++this.waypointY;
		if (!this.worldObj.isRemote && this.ticksExisted % 10 == 0 && !getNatureBlock(this.worldObj.getBlock((int)waypointX, (int)waypointY, (int)waypointZ)) && (this.worldObj.getBlock((int)posX, (int)posY, (int)posZ) != this.getFavoriteBlockToPerch() || this.worldObj.getBlock((int)waypointX, (int)waypointY, (int)waypointZ) != this.getFavoriteBlockToPerch() || this.worldObj.getBlock((int)waypointX, (int)waypointY + 1, (int)waypointZ).getMaterial() != Material.air || this.worldObj.getBlock((int)waypointX, (int)waypointY + 2, (int)waypointZ).getMaterial() != Material.air || !this.worldObj.canBlockSeeTheSky((int)waypointX, (int)waypointY + 1, (int)waypointZ)))
		{
			int i = MathHelper.floor_double(this.posY);
			int i1 = MathHelper.floor_double(this.posX);
			int j1 = MathHelper.floor_double(this.posZ);
			for (int l1 = -6; l1 <= 6; l1++)
			{
				for (int i2 = -6; i2 <= 6; i2++)
				{
					for (int j = -6; j <= 6; j++)
					{
						int j2 = i1 + l1;
						int k = i + j;
						int l = j1 + i2;
						Block blockmain = this.worldObj.getBlock(j2, k, l);
						Block block1 = this.worldObj.getBlock(j2, k + 1, l);
						Block block2 = this.worldObj.getBlock(j2, k + 2, l);
						if (!this.worldObj.isRemote && getNatureBlock(blockmain) && ((blockmain == this.getFavoriteBlockToPerch() && this.rand.nextInt(20) == 0) || this.rand.nextInt(300) == 0) && this.worldObj.canBlockSeeTheSky(j2, k + 1, l) && block1.getMaterial() == Material.air && block2.getMaterial() == Material.air)
						{
							this.waypointX = j2;
							this.waypointY = k;
							this.waypointZ = l;
						}
					}
				}
			}
		}
	}

	public boolean getNatureBlock(Block block)
	{
		return (block.isOpaqueCube() || block == getFavoriteBlockToPerch()) && block != Blocks.stone && block != Blocks.bedrock && block != Blocks.log && block != Blocks.log2 && block != Blocks.sand && block != Blocks.dirt && block != Blocks.gravel && block != Blocks.grass && block != Blocks.mycelium && block != Blocks.netherrack && block != Blocks.soul_sand;
	}

	public boolean getUNNatureBlock(Block block)
	{
		return (!block.isOpaqueCube() || block != getFavoriteBlockToPerch()) && (block == Blocks.stone || block == Blocks.bedrock || block == Blocks.log || block == Blocks.log2 || block == Blocks.sand || block == Blocks.dirt || block == Blocks.gravel || block == Blocks.grass || block == Blocks.mycelium || block == Blocks.netherrack || block == Blocks.soul_sand);
	}

	public Block getFavoriteBlockToPerch()
	{
		switch (this.getGargoyleType())
		{
			case 0:
			default:
			return TitanBlocks.stoneperch;
			case 1:
			return TitanBlocks.sandstoneperch;
			case 2:
			return TitanBlocks.obsidianperch;
			case 3:
			return TitanBlocks.goldperch;
			case 4:
			return TitanBlocks.ironperch;
			case 5:
			return TitanBlocks.endstoneperch;
			case 6:
			return TitanBlocks.netherbrickperch;
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		else if (source == DamageSource.inWall || source == DamageSource.drown || source == DamageSource.onFire || source == DamageSource.inFire)
		{
			return false;
		}

		else if ((source.getEntity() != null) && (source.getEntity() instanceof EntityGargoyle || source.getEntity() instanceof EntityGargoyleTitan))
		{
			return false;
		}

		else
		{
			return super.attackEntityFrom(source, amount);
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		this.attackTimer = 10;
		this.worldObj.setEntityState(this, (byte)4);
		boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), this.getGargoyleType() == 6 ? 14F : this.getGargoyleType() == 5 ? 26F : this.getGargoyleType() == 4 ? 14F : this.getGargoyleType() == 3 ? 20F : this.getGargoyleType() == 2 ? 18F : this.getGargoyleType() == 1 ? 4F : 8F);
		this.swingItem();
		if (flag)
		{
			switch (this.getGargoyleType())
			{
				case 0:
				default:
				p_70652_1_.motionY += 0.3D;
				break;
				case 1:
				p_70652_1_.motionY += 0.15D;
				break;
				case 2:
				p_70652_1_.motionY += 0.5D;
				break;
				case 3:
				p_70652_1_.motionY += 0.6D;
				this.playSound("mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F);
				break;
				case 4:
				p_70652_1_.motionY += 0.4D;
				break;
				case 5:
				p_70652_1_.motionY += 0.5D;
				break;
				case 6:
				{
					p_70652_1_.motionY += 0.3D;
					p_70652_1_.setFire(10);
				}
			}
		}

		this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
		return flag && this.attackTime <= 0;
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte p_70103_1_)
	{
		if (p_70103_1_ == 4)
		{
			this.attackTimer = 10;
			this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
		}

		else
		{
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	protected String getLivingSound()
	{
		return this.getNatureBlock(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY - 0.5D), MathHelper.floor_double(this.posZ))) ? null : "thetitans:gargoyleLiving";
	}

	protected String getHurtSound()
	{
		return "thetitans:gargoyleGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:gargoyleDeath";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
	}

	/*private void func_175463_b(int p_175463_1_)
	{
		this.dataWatcher.updateObject(17, Integer.valueOf(p_175463_1_));

	}

	*/
	public boolean func_175474_cn()
	{
		return this.dataWatcher.getWatchableObjectInt(17) != 0;
	}

	public EntityLivingBase getTargetedEntity()
	{
		if (!this.func_175474_cn())
		{
			return null;
		}

		else if (this.worldObj.isRemote)
		{
			if (this.field_175478_bn != null)
			{
				return this.field_175478_bn;
			}

			else
			{
				Entity entity = this.worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(17));
				if (entity instanceof EntityLivingBase)
				{
					this.field_175478_bn = (EntityLivingBase)entity;
					return this.field_175478_bn;
				}

				else
				{
					return null;
				}
			}
		}

		else
		{
			return this.getAttackTarget();
		}
	}

	/**
	* Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
	* par2 - Level of Looting used to kill this mob.
	*/
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int j = this.rand.nextInt(5);
		int k;
		switch (this.getGargoyleType())
		{
			case 0:
			default:
			for (k = 0; k < j; ++k)
			{
				this.func_145778_a(Item.getItemFromBlock(Blocks.stone), 1, 0.0F);
			}

			break;
			case 1:
			for (k = 0; k < j; ++k)
			{
				this.func_145778_a(Item.getItemFromBlock(Blocks.sandstone), 1, 0.0F);
			}

			break;
			case 2:
			for (k = 0; k < j; ++k)
			{
				this.func_145778_a(Item.getItemFromBlock(Blocks.obsidian), 1, 0.0F);
			}

			break;
			case 3:
			for (k = 0; k < j; ++k)
			{
				this.func_145778_a(Item.getItemFromBlock(Blocks.gold_block), 1, 0.0F);
			}

			break;
			case 4:
			for (k = 0; k < j; ++k)
			{
				this.func_145778_a(Item.getItemFromBlock(Blocks.iron_block), 1, 0.0F);
			}

			break;
			case 5:
			for (k = 0; k < j; ++k)
			{
				this.func_145778_a(Item.getItemFromBlock(Blocks.end_stone), 1, 0.0F);
			}

			break;
			case 6:
			for (k = 0; k < j; ++k)
			{
				this.func_145778_a(Item.getItemFromBlock(Blocks.nether_brick), 1, 0.0F);
			}
		}
	}

	class AIBeamAttack extends EntityAIBase
	{
		private EntityGargoyle field_179456_a = EntityGargoyle.this;
		private int field_179455_b;
		public AIBeamAttack()
		{
			this.setMutexBits(3);
		}

		/**
		* Returns whether the EntityAIBase should begin execution.
		*/
		public boolean shouldExecute()
		{
			EntityLivingBase entitylivingbase = this.field_179456_a.getAttackTarget();
			return entitylivingbase != null && entitylivingbase.isEntityAlive() && this.field_179456_a.getGargoyleType() == 3;
		}

		/**
		* Returns whether an in-progress EntityAIBase should continue executing
		*/
		public boolean continueExecuting()
		{
			return super.continueExecuting() && this.field_179456_a.getAttackTarget() != null && this.field_179456_a.getGargoyleType() == 3;
		}

		/**
		* Execute a one shot task or start executing a continuous task
		*/
		public void startExecuting()
		{
			this.field_179455_b = -10;
			this.field_179456_a.getNavigator().clearPathEntity();
			this.field_179456_a.getLookHelper().setLookPositionWithEntity(this.field_179456_a.getAttackTarget(), 90.0F, 90.0F);
			this.field_179456_a.isAirBorne = true;
		}

		/**
		* Resets the task
		*/
		public void resetTask()
		{
			this.field_179456_a.setAttackTarget((EntityLivingBase)null);
		}

		/**
		* Updates the task
		*/
		public void updateTask()
		{
			EntityLivingBase entitylivingbase = this.field_179456_a.getAttackTarget();
			this.field_179456_a.getNavigator().clearPathEntity();
			this.field_179456_a.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);
			if (!this.field_179456_a.canEntityBeSeen(entitylivingbase))
			{
				this.field_179456_a.setAttackTarget((EntityLivingBase)null);
			}

			else
			{
				++this.field_179455_b;
				field_179456_a.renderYawOffset = field_179456_a.rotationYaw = field_179456_a.rotationYawHead;
				if (this.field_179455_b > 0)
				{
					entitylivingbase.attackEntityFrom(DamageSource.magic, 1F);
					entitylivingbase.setFire(1 + field_179455_b);
				}

				if (this.field_179455_b == 0)
				{
					this.field_179456_a.worldObj.setEntityState(this.field_179456_a, (byte)21);
				}

				else if (this.field_179455_b >= 60)
				{
					float f = 12.0F;
					if (this.field_179456_a.worldObj.difficultySetting == EnumDifficulty.HARD)
					{
						f += 4.0F;
					}

					field_179456_a.attackEntityAsMob(entitylivingbase);
					entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.field_179456_a, this.field_179456_a), f);
					this.field_179456_a.setAttackTarget((EntityLivingBase)null);
				}

				else if (this.field_179455_b >= 60 && this.field_179455_b % 20 == 0)
				{
					;
				}

				super.updateTask();
			}
		}
	}

	class AIPerch extends EntityAIBase
	{
		public AIPerch()
		{
			setMutexBits(7);
		}

		public boolean shouldExecute()
		{
			Block blockmain = EntityGargoyle.this.worldObj.getBlock((int)EntityGargoyle.this.waypointX, (int)EntityGargoyle.this.waypointY, (int)EntityGargoyle.this.waypointZ);
			return EntityGargoyle.this.getNatureBlock(blockmain) && EntityGargoyle.this.getAttackTarget() == null;
		}
	}
}


