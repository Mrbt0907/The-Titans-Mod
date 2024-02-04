package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.theTitans.core.TheCore;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.titan.ai.EntityAIHurtByTargetTitan;
import net.minecraft.entity.titan.ai.EntityAINearestTargetTitan;
import net.minecraft.entity.titanminion.EntityBlazeMinion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityBlazeTitan extends EntityTitan implements ITitanHitbox
{
	private float heightOffset = 32.0F;
	private int heightOffsetUpdateTime;
	public boolean shouldBurn;
	public EntityTitanPart head;
	public EntityTitanPart[] rods = new EntityTitanPart[12];
	public EntityBlazeTitan(World worldIn)
	{
		super(worldIn);
		head = new EntityTitanPart(worldIn, this, "head", 8.0F, 8.0F);
		rods[0] = new EntityTitanPart(worldIn, this, "rod1", 2.0F, 8.0F);
		rods[1] = new EntityTitanPart(worldIn, this, "rod2", 2.0F, 8.0F);
		rods[2] = new EntityTitanPart(worldIn, this, "rod3", 2.0F, 8.0F);
		rods[3] = new EntityTitanPart(worldIn, this, "rod4", 2.0F, 8.0F);
		rods[4] = new EntityTitanPart(worldIn, this, "rod5", 2.0F, 8.0F);
		rods[5] = new EntityTitanPart(worldIn, this, "rod6", 2.0F, 8.0F);
		rods[6] = new EntityTitanPart(worldIn, this, "rod7", 2.0F, 8.0F);
		rods[7] = new EntityTitanPart(worldIn, this, "rod8", 2.0F, 8.0F);
		rods[8] = new EntityTitanPart(worldIn, this, "rod9", 2.0F, 8.0F);
		rods[9] = new EntityTitanPart(worldIn, this, "rod10", 2.0F, 8.0F);
		rods[10] = new EntityTitanPart(worldIn, this, "rod11", 2.0F, 8.0F);
		rods[11] = new EntityTitanPart(worldIn, this, "rod12", 2.0F, 8.0F);
		
		partArray.add(head);
		partArray.add(rods[0]);
		partArray.add(rods[1]);
		partArray.add(rods[2]);
		partArray.add(rods[3]);
		partArray.add(rods[4]);
		partArray.add(rods[5]);
		partArray.add(rods[6]);
		partArray.add(rods[7]);
		partArray.add(rods[8]);
		partArray.add(rods[9]);
		partArray.add(rods[10]);
		partArray.add(rods[11]);
		
		shouldParticlesBeUpward = true;
		setSize(8.0F, 8.0F);
		experienceValue = 50000;
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityIronGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntitySnowGolemTitan.class, 0, false));
		targetTasks.addTask(0, new EntityAIHurtByTargetTitan(this, true, new Class[] 
		{
			EntityBlazeMinion.class, EntityBlazeTitan.class
		}
		));
		worldObj.spawnEntityInWorld(head);
		worldObj.spawnEntityInWorld(rods[0]);
		worldObj.spawnEntityInWorld(rods[1]);
		worldObj.spawnEntityInWorld(rods[2]);
		worldObj.spawnEntityInWorld(rods[3]);
		worldObj.spawnEntityInWorld(rods[4]);
		worldObj.spawnEntityInWorld(rods[5]);
		worldObj.spawnEntityInWorld(rods[6]);
		worldObj.spawnEntityInWorld(rods[7]);
		worldObj.spawnEntityInWorld(rods[8]);
		worldObj.spawnEntityInWorld(rods[9]);
		worldObj.spawnEntityInWorld(rods[10]);
		worldObj.spawnEntityInWorld(rods[11]);
	}

	protected void onHitboxUpdate()
	{
		if (ticksExisted > 5)
		{
			head.height = head.width = height;
			for (int f = 0; f < 12; f++)
			{
				rods[f].height = height;
				rods[f].width = width * 0.25F;
			}

			float f = renderYawOffset * 3.1415927F / 180.0F;
			float f6 = (ticksExisted) * 3.1415927F * 0.008F + 0.15F;
			int i;
			
			for (i = 0; i < 4; i++)
			{
				rods[i].setLocationAndAngles(posX - (MathHelper.cos(f6 + f) * (0.625F * getTitanSizeMultiplier())), posY - ((0.25F * getTitanSizeMultiplier())), posZ - (MathHelper.sin(f6 + f) * (0.625F * getTitanSizeMultiplier())), 0F, 0F);
				f6 += 1.5F;
			}

			f6 = 0.7853982F + (ticksExisted) * 3.1415927F * -0.005F - 1.4F;
			for (i = 4; i < 8; i++)
			{
				rods[i].setLocationAndAngles(posX - (MathHelper.cos(f6 + f) * (0.4375F * getTitanSizeMultiplier())), posY - ((0.625F * getTitanSizeMultiplier())), posZ - (MathHelper.sin(f6 + f) * (0.4375F * getTitanSizeMultiplier())), 0F, 0F);
				f6 += 1.5F;
			}

			f6 = 0.47123894F + (ticksExisted) * 3.1415927F * 0.003F - 0.8F;
			for (i = 8; i < 12; i++)
			{
				rods[i].setLocationAndAngles( posX - (MathHelper.cos(f6 + f) * (0.25F * getTitanSizeMultiplier())), posY - ((1.0625F * getTitanSizeMultiplier())), posZ - (MathHelper.sin(f6 + f) * (0.25F * getTitanSizeMultiplier())), 0F, 0F);
				f6 += 1.5F;
			}

			head.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
			for (int u = 0; u < getParticleCount(); u++)
				for (int w = 0; w < rods.length; w++)
					worldObj.spawnParticle(getParticles(), rods[w].posX + (rand.nextDouble() - 0.5D) * rods[w].width, rods[w].posY + rand.nextDouble() * rods[w].height, rods[w].posZ + (rand.nextDouble() - 0.5D) * rods[w].width, 0D, 0D, 0D);
		}
		
		super.onHitboxUpdate();
	}
	
	protected void applyEntityAI()
	{
		tasks.addTask(4, new AIFireballAttack());
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		if (TheTitans.TitansFFAMode)
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.BlazeTitanSorter));
		else
		targetTasks.addTask(0, new EntityAINearestTargetTitan(this, EntityLivingBase.class, 0, false, false, ITitan.SearchForAThingToKill));
	}

	@Override
	public double getHealthValue()
	{
		return super.getHealthValue() * 20.0F;
	}

	public float getEyeHeight()
	{
		return 0.5F * height;
	}

	public boolean isArmored()
	{
		return getHealth() <= getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue() / 4.0F || TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) || TheTitans.isDifficulty(worldObj, TheCore.IMPOSSIBLE);
	}

	protected void fall(float p_70069_1_) 
	{
		 
	}


	@SuppressWarnings("rawtypes")
	public boolean canAttackClass(Class p_70686_1_)
	{
		return p_70686_1_ != head.getClass() && p_70686_1_ != rods[0].getClass() && p_70686_1_ != rods[1].getClass() && p_70686_1_ != rods[2].getClass() && p_70686_1_ != rods[3].getClass() && p_70686_1_ != rods[4].getClass() && p_70686_1_ != rods[5].getClass() && p_70686_1_ != rods[6].getClass() && p_70686_1_ != rods[7].getClass() && p_70686_1_ != rods[8].getClass() && p_70686_1_ != rods[9].getClass() && p_70686_1_ != rods[10].getClass() && p_70686_1_ != rods[11].getClass() && (p_70686_1_ != EntityTitanFireball.class) && (p_70686_1_ != EntityBlazeMinion.class) && (p_70686_1_ != EntityBlazeTitan.class);
	}

	public boolean getCanSpawnHere()
	{
		return (rand.nextInt(250) == 0) && (worldObj.difficultySetting != EnumDifficulty.PEACEFUL) && (super.getCanSpawnHere());
	}

	public int getMinionSpawnRate()
	{
		return TheTitans.BlazeTitanMinionSpawnrate;
	}

	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte)0));
	}

	public int getParticleCount()
	{
		return 40;
	}

	public String getParticles()
	{
		if (rand.nextInt(isWet() ? 2 : 6) == 0)
		{
			return "explode";
		}

		return "largesmoke";
	}

	public EnumTitanStatus getTitanStatus()
	{
		return EnumTitanStatus.AVERAGE;
	}

	protected String getLivingSound()
	{
		return "thetitans:titanBlazeBreathe";
	}

	protected String getHurtSound()
	{
		return "thetitans:titanBlazeGrunt";
	}

	protected String getDeathSound()
	{
		return "thetitans:titanBlazeDeath";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) 
	{
		 
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

	public double getSpeed()
	{
		return 0.5D + getExtraPower() * 0.001D;
	}

	public String getCommandSenderName()
	{
		switch (getTitanVariant())
		{
			case 0:
			switch (rand.nextInt(2))
			{
				case 0:return "\u00A7c" + StatCollector.translateToLocal("entity.BlazeTitan.name") + "\u00A7f";
				case 1:return "\u00A76" + StatCollector.translateToLocal("entity.BlazeTitan.name") + "\u00A7f";
			}

			case 1:
			return "\u00A7b" + StatCollector.translateToLocal("entity.BlazeTitanIce.name") + "\u00A7f";
			case 2:
			return "\u00A7a" + StatCollector.translateToLocal("entity.BlazeTitanAcid.name") + "\u00A7f";
			case 3:
			return "\u00A74" + StatCollector.translateToLocal("entity.BlazeTitanDirt.name") + "\u00A7f";
			case 4:
			return "\u00A70" + "\u00A7l" + StatCollector.translateToLocal("entity.BlazeTitanVoid.name") + "\u00A7f";
		}

		return null;
	}

	public void onLivingUpdate()
	{
		switch (getTitanVariant())
		{
			case 0:
			{
				enactEffectAura(3, boundingBox.expand(super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier()).offset(0, -8, 0));
				break;
			}

			case 3:
			{
				enactEffectAura(5, boundingBox.expand(super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier()).offset(0, -8, 0));
				break;
			}

			case 4:
			{
				enactEffectAura(4, boundingBox.expand(super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier(), super.getTitanSizeMultiplier()));
				break;
			}
		}

		setSize(0.5F * getTitanSizeMultiplier(), 0.5F * getTitanSizeMultiplier());
		isAirBorne = true;
		onGround = false;
		if ((worldObj.isRemote) && (deathTicks < getThreashHold()))
		{
			for (int i = 0; i < getParticleCount(); i++)
			worldObj.spawnParticle(getParticles(), posX + (rand.nextDouble() - 0.5D) * width, posY - 24.0D + rand.nextDouble() * 32.0D, posZ + (rand.nextDouble() - 0.5D) * width, 0D, 0D, 0D);
			if ((TheTitans.isDifficulty(worldObj, TheCore.NIGHTMARE) == true) && (rand.nextInt(20) == 0))
			{
				for (int i = 0; i < 2; i++)
				{
					worldObj.spawnParticle("flame", posX + (rand.nextDouble() - 0.5D) * width, posY - 16.0D + rand.nextDouble() * 16.0D, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
				}
			}
		}

		for (int i = 0; i < 8; i++)
		{
			double d0 = posX + (rand.nextFloat() * 8.0F - 4.0F);
			double d1 = posY + rand.nextFloat() * (getTitanSizeMultiplier() * 1.75F - height);
			double d2 = posZ + (rand.nextFloat() * 8.0F - 4.0F);
			if ((!worldObj.isRemote) && (worldObj.getBlock((int)d0, (int)d1 + (int)getEyeHeight(), (int)d2).isOpaqueCube() || worldObj.getBlock((int)d0, (int)d1 + (int)getEyeHeight(), (int)d2).getMaterial().isLiquid()))
			{
				setPosition(posX, posY + 0.1D, posZ);
			}
		}

		for (int in = 0; in < 1000; in++)
		{
			double d0 = posX + (rand.nextFloat() * 8.0F - 4.0F);
			double d1 = posY - rand.nextFloat() * (getTitanSizeMultiplier() * 1.75F - height);
			double d2 = posZ + (rand.nextFloat() * 8.0F - 4.0F);
			if ((!worldObj.isRemote) && (worldObj.getBlock((int)d0, (int)d1, (int)d2).isOpaqueCube() || worldObj.getBlock((int)d0, (int)d1, (int)d2).getMaterial().isLiquid()))
			{
				motionY = 0.25D;
			}
		}

		

		if (worldObj.isRemote)
		{
			if (rand.nextInt(48) == 0)
			{
				worldObj.playSound(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "fire.fire", 10.0F + rand.nextFloat(), rand.nextFloat() * 0.3F + 0.2F, false);
			}
		}

		if (getAttackTarget() != null && !worldObj.isRemote)
		{
			faceEntity(getAttackTarget(), 5.0F, 180.0F);
			getMoveHelper().setMoveTo(getAttackTarget().posX, getAttackTarget().posY, getAttackTarget().posZ, 20.0D);
			if (posY < getAttackTarget().posY + 20D)
			{
				if (motionY < 0.0D)
				{
					motionY = 0.0D;
				}

				motionY += (0.8D - motionY) * 0.8D;
			}
		}

		else if ((!onGround) && (motionY < 0.0D))
		{
			motionY *= 0.25D;
		}

		super.onLivingUpdate();
	}

	protected void updateAITasks()
	{
		if (getInvulTime() < 0)
		{
			heightOffsetUpdateTime -= 1;
			if (heightOffsetUpdateTime <= 0)
			{
				heightOffsetUpdateTime = 300;
				heightOffset = (40.0F + (float)rand.nextGaussian() * 16.0F);
			}

			EntityLivingBase entitylivingbase = getAttackTarget();
			if ((entitylivingbase != null) && (entitylivingbase.posY + entitylivingbase.getEyeHeight() > posY + getEyeHeight() + heightOffset))
			{
				motionY += (0.9D - motionY) * 0.9D;
				isAirBorne = true;
			}
		}

		super.updateAITasks();
	}

	public void fall(float distance, float damageMultiplier) 
	{
		 
	}


	protected Item getDropItem()
	{
		return Items.blaze_rod;
	}

	public int getVerticalFaceSpeed()
	{
		return 180;
	}

	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		if (deathTicks > 0)
		{
			for (int x = 0; x < 32; x++)
			{
				EntityXPBomb entitylargefireball = new EntityXPBomb(worldObj, posX, posY + 4D, posZ);
				entitylargefireball.setPosition(posX, posY + 4D, posZ);
				++entitylargefireball.motionY;
				entitylargefireball.setXPCount(18000);
				worldObj.spawnEntityInWorld(entitylargefireball);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.blaze_rod));
				itembomb.setItemCount(24 + rand.nextInt(24 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 8; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.blaze_powder));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 6; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.coal));
				itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.emerald));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 2; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.diamond));
				itembomb.setItemCount(16 + rand.nextInt(16 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 3; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Items.gold_ingot));
				itembomb.setItemCount(32 + rand.nextInt(32 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(TitanItems.harcadium));
				itembomb.setItemCount(8 + rand.nextInt(8 + p_70628_2_) + p_70628_2_);
				worldObj.spawnEntityInWorld(itembomb);
			}

			if (rand.nextInt(5) == 0)
			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.bedrock));
				itembomb.setItemCount(1);
				worldObj.spawnEntityInWorld(itembomb);
			}

			if (rand.nextInt(5) == 0)
			for (int x = 0; x < 1; x++)
			{
				EntityItemBomb itembomb = new EntityItemBomb(worldObj, posX, posY + 6D, posZ);
				itembomb.setEntityItemStack(new ItemStack(Blocks.bedrock));
				itembomb.setItemCount(1);
				worldObj.spawnEntityInWorld(itembomb);
			}
		}
	}

	public boolean func_70845_n()
	{
		return getTitanVariant() != 0 ? false : (dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
	}

	public void func_70844_e(boolean p_70844_1_)
	{
		byte b0 = dataWatcher.getWatchableObjectByte(16);
		if (p_70844_1_)
		{
			b0 = (byte)(b0 | 0x1);
		}

		else
		{
			b0 = (byte)(b0 & 0xFFFFFFFE);
		}

		dataWatcher.updateObject(16, Byte.valueOf(b0));
	}

	public boolean isBurning()
	{
		return func_70845_n() && hurtResistantTime < 20;
	}

	protected boolean isValidLightLevel()
	{
		return true;
	}

	public Entity[] getParts()
	{
		return (Entity[]) partArray.toArray();
	}

	public boolean handleLavaMovement()
	{
		return false;
	}

	protected void collideWithEntity(Entity p_82167_1_)
	{
		if ((!(p_82167_1_ instanceof EntitySmallFireball)) || (!(p_82167_1_ instanceof EntityLargeFireball)))
		{
			p_82167_1_.applyEntityCollision(this);
		}
	}

	public StatBase getAchievement()
	{
		return TitansAchievments.blazetitan;
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (isEntityInvulnerable())
		{
			return false;
		}

		if (((source.getEntity() instanceof EntityBlazeMinion)) || ((source.getEntity() instanceof EntityBlazeTitan)))
		{
			return false;
		}

		if (source.isFireDamage())
		{
			heal(amount);
			return false;
		}

		return super.attackEntityFrom(source, amount);
	}

	protected void inactDeathAction()
	{
		if (!worldObj.isRemote)
		{
			playSound("mob.blaze.death", getSoundVolume(), getSoundPitch());
			if ((worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
			{
				dropFewItems(true, 0);
				dropEquipment(true, 0);
				dropRareDrop(1);
			}

			EntityTitanSpirit entitytitan = new EntityTitanSpirit(worldObj);
			entitytitan.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			worldObj.spawnEntityInWorld(entitytitan);
			entitytitan.setVesselHunting(false);
			entitytitan.setSpiritType(9);
		}
	}

	class AIFireballAttack
	extends EntityAIBase
	{
		private EntityBlazeTitan field_179469_a = EntityBlazeTitan.this;
		private int field_179467_b;
		private int field_179468_c;
		public AIFireballAttack()
		{
			setMutexBits(4);
		}

		public boolean shouldExecute()
		{
			EntityLivingBase entitylivingbase = field_179469_a.getAttackTarget();
			return (entitylivingbase != null) && (entitylivingbase.isEntityAlive());
		}

		public void startExecuting()
		{
			field_179467_b = 0;
			if (field_179469_a.getTitanVariant() == 0)
			field_179469_a.func_70844_e(true);
		}

		public void resetTask()
		{
			field_179469_a.func_70844_e(false);
		}

		public void updateTask()
		{
			field_179468_c -= 1;
			EntityLivingBase entitylivingbase = field_179469_a.getAttackTarget();
			double d0 = field_179469_a.getDistanceToEntity(entitylivingbase);
			if (d0 <= field_179469_a.getMeleeRange())
			{
				if (field_179468_c <= 0)
				field_179468_c = 30;
				if (field_179468_c == 18)
				{
					float f = (float)getAttackValue(4.0F);
					int i = field_179469_a.getKnockbackAmount();
					field_179469_a.attackChoosenEntity(entitylivingbase, f, i);
				}
			}

			else if (d0 > field_179469_a.getMeleeRange())
			{
				double d1 = entitylivingbase.posX - field_179469_a.posX;
				double d2 = entitylivingbase.posY - (field_179469_a.posY + field_179469_a.height * 0.5F);
				double d3 = entitylivingbase.posZ - field_179469_a.posZ;
				if (field_179468_c <= 0)
				{
					field_179467_b += 1;
					if (field_179467_b == 1)
					{
						field_179468_c = (field_179469_a.isArmored() ? 10 : (20 + field_179469_a.getRNG().nextInt(40)));
					}

					else if (field_179467_b <= 4)
					{
						field_179468_c = 6;
					}

					else
					{
						field_179468_c = (field_179469_a.isArmored() ? 10 : (20 + field_179469_a.getRNG().nextInt(40)));
						field_179467_b = 0;
					}

					if (field_179467_b > 1)
					{
						float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d0)) * 0.75F;
						field_179469_a.playSound("thetitans:titanGhastFireball", 100F, 1.1F);
						for (int i = 0; i < 5; i++)
						{
							EntityTitanFireball entitylargefireball = new EntityTitanFireball(field_179469_a.worldObj, field_179469_a, d1 + field_179469_a.getRNG().nextGaussian() * f, d2, d3 + field_179469_a.getRNG().nextGaussian() * f, 2);
							double d8 = 10D;
							Vec3 vec3 = field_179469_a.getLook(1.0F);
							entitylargefireball.posX = (field_179469_a.posX + vec3.xCoord * d8);
							entitylargefireball.posY = (field_179469_a.posY + 4.0D + vec3.yCoord * d8);
							entitylargefireball.posZ = (field_179469_a.posZ + vec3.zCoord * d8);
							field_179469_a.worldObj.spawnEntityInWorld(entitylargefireball);
							entitylargefireball.setFireballID(2);
							if (field_179469_a.getRNG().nextInt(50) == 0)
							{
								entitylivingbase.attackEntityFrom(DamageSourceExtra.lightningBolt, 49.0F);
								float f2 = (float)getAttackValue(4.0F);
								int i1 = getKnockbackAmount();
								attackChoosenEntity(entitylivingbase, f2, i1);
								field_179469_a.worldObj.addWeatherEffect(new EntityGammaLightning(field_179469_a.worldObj, field_179469_a.posX, field_179469_a.posY + field_179469_a.getEyeHeight(), field_179469_a.posZ, 1.0F, 0.8F, 0F));
								field_179469_a.worldObj.addWeatherEffect(new EntityGammaLightning(field_179469_a.worldObj, entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0F, 0.8F, 0F));
							}
						}
					}
				}
			}

			else
			{
				field_179469_a.getNavigator().clearPathEntity();
			}

			super.updateTask();
		}
	}

	public boolean attackEntityFromPart(EntityTitanPart p_70965_1_, DamageSource source, float amount)
	{
		func_82195_e(source, amount);
		return true;
	}

	protected boolean func_82195_e(DamageSource p_82195_1_, float p_82195_2_)
	{
		return attackEntityFrom(p_82195_1_, p_82195_2_);
	}

	public World func_82194_d()
	{
		return worldObj;
	}

	protected EntityLiving getMinion()
	{
		return new EntityBlazeMinion(worldObj);
	}

	protected double cap()
	{
		return super.cap();
	}
}


