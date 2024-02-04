package net.minecraft.entity.orespawnaddon;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danger.orespawn.BetterFireball;
import danger.orespawn.GenericTargetSorter;
import danger.orespawn.Godzilla;
import danger.orespawn.GodzillaHead;
import danger.orespawn.Kraken;
import danger.orespawn.MyEntityAIWanderALot;
import danger.orespawn.MyUtils;
import danger.orespawn.OreSpawnMain;
import danger.orespawn.PitchBlack;
import danger.orespawn.PurplePower;
import danger.orespawn.RenderInfo;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitanFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class EntityBurningMobzilla
extends Godzilla
{
	private GenericTargetSorter TargetSorter = null;
	private float moveSpeed = 0.8F;
	private int jumped = 0;
	private int jump_timer = 0;
	private int ticker = 0;
	private int stream_count = 24;
	private int head_found = 0;
	private int large_unknown_detected = 0;
	private MyEntityAIWanderALot wander = null;
	public int preventMeltDown = 0;
	private RenderInfo renderdata = new RenderInfo();
	public EntityBurningMobzilla(World par1World)
	{
		super(par1World);
		if (OreSpawnMain.PlayNicely == 0)
		{
			setSize(9.9F, 25.0F);

		}

		 else 
		{

			setSize(2.475F, 6.25F);
		}

		this.experienceValue = 50000;
		this.wander = new MyEntityAIWanderALot(this, 15, 1.0D);
		this.tasks.addTask(2, this.wander);
		this.TargetSorter = new GenericTargetSorter(this);
	}

	public boolean isBurning()
	{
		return this.preventMeltDown >= 300 ? true : false;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mygetMaxHealth());
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.moveSpeed);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(OreSpawnMain.Godzilla_stats.attack * 10);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
	}

	protected void entityInit()
	{
		super.entityInit();
		if (this.renderdata == null)
		{
			this.renderdata = new RenderInfo();
		}

		this.renderdata.rf1 = 0.0F;
		this.renderdata.rf2 = 0.0F;
		this.renderdata.rf3 = 0.0F;
		this.renderdata.rf4 = 0.0F;
		this.renderdata.ri1 = 0;
		this.renderdata.ri2 = 0;
		this.renderdata.ri3 = 0;
		this.renderdata.ri4 = 0;
	}

	public RenderInfo getRenderInfo()
	{
		return this.renderdata;
	}

	public void setRenderInfo(RenderInfo r)
	{
		this.renderdata.rf1 = r.rf1;
		this.renderdata.rf2 = r.rf2;
		this.renderdata.rf3 = r.rf3;
		this.renderdata.rf4 = r.rf4;
		this.renderdata.ri1 = r.ri1;
		this.renderdata.ri2 = r.ri2;
		this.renderdata.ri3 = r.ri3;
		this.renderdata.ri4 = r.ri4;
	}

	public int mygetMaxHealth()
	{
		return OreSpawnMain.Godzilla_stats.health * 2;
	}

	public int getTotalArmorValue()
	{
		if (this.large_unknown_detected != 0)
		{
			return 25;
		}

		return 24;
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_)
	{
		return this.getHealth() <= 1000F ? 15728880 : super.getBrightnessForRender(p_70070_1_);
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if (getHealth() <= 1000.0F)
		++this.preventMeltDown;
		else
		--this.preventMeltDown;
		if (this.preventMeltDown < 0)
		this.preventMeltDown = 0;
		this.removePotionEffect(Potion.wither.id);
		this.removePotionEffect(Potion.poison.id);
		if (!this.worldObj.isRemote && preventMeltDown >= 400)
		{
			playSound("thetitans:titanland", 10000.0F, 1.0F);
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1000.0D, 300.0D, 1000.0D));
			for (int i = 0; i < list.size(); i++)
			{
				Entity entity1 = (Entity)list.get(i);
				if ((entity1 instanceof EntityLiving) && !(entity1 instanceof Godzilla) && !(entity1 instanceof GodzillaHead))
				{
					((EntityLiving)entity1).addPotionEffect(new PotionEffect(Potion.wither.id, 10000, 10000));
					((EntityLiving)entity1).addPotionEffect(new PotionEffect(Potion.poison.id, 10000, 10000));
					((EntityLiving)entity1).addPotionEffect(new PotionEffect(ClientProxy.creeperTitanRadiation.id, 10000));
					((EntityLiving)entity1).attackEntityFrom(DamageSource.causeMobDamage(this), 20000.0F);
					if (((EntityLiving)entity1).getHealth() <= ((EntityLiving)entity1).getMaxHealth() * 1.0F / 4.0F) 
					{
						 
					}


					((EntityLiving)entity1).setHealth(0.0F);
					this.doLightningAttack((EntityLiving)entity1);
				}

				if ((entity1 instanceof EntityPlayer))
				{
					this.doLightningAttack((EntityPlayer)entity1);
					if (!((EntityPlayer)entity1).capabilities.isCreativeMode)
					{
						((EntityPlayer)entity1).addPotionEffect(new PotionEffect(Potion.wither.id, 10000, 10000));
						((EntityPlayer)entity1).addPotionEffect(new PotionEffect(Potion.poison.id, 10000, 10000));
						((EntityPlayer)entity1).addPotionEffect(new PotionEffect(ClientProxy.creeperTitanRadiation.id, 10000));
						((EntityPlayer)entity1).addPotionEffect(new PotionEffect(Potion.hunger.id, 50, 100));
						((EntityPlayer)entity1).setHealth(0.0F);
					}
				}
			}

			int i = MathHelper.floor_double(this.boundingBox.minX - 50.0D);
			int j = MathHelper.floor_double(this.boundingBox.minY - 50.0D);
			int k = MathHelper.floor_double(this.boundingBox.minZ - 50.0D);
			int l = MathHelper.floor_double(this.boundingBox.maxX + 50.0D);
			int i1 = MathHelper.floor_double(this.boundingBox.maxY + 50.0D);
			int j1 = MathHelper.floor_double(this.boundingBox.maxZ + 50.0D);
			for (int k1 = i; k1 <= l; k1++)
			{
				for (int l1 = j; l1 <= i1; l1++)
				{
					for (int i2 = k; i2 <= j1; i2++)
					{
						Block block = this.worldObj.getBlock(k1, l1, i2);
						if (!block.isAir(this.worldObj, k1, l1, i2))
						{
							if (!block.equals(Blocks.bedrock))
							{
								this.worldObj.setBlockToAir(k1, l1, i2);
							}
						}
					}
				}
			}

			int i3 = MathHelper.floor_double(this.boundingBox.minX - 200.0D);
			int j3 = MathHelper.floor_double(this.boundingBox.minY - 50.0D);
			int k3 = MathHelper.floor_double(this.boundingBox.minZ - 200.0D);
			int l3 = MathHelper.floor_double(this.boundingBox.maxX + 200.0D);
			int i13 = MathHelper.floor_double(this.boundingBox.maxY + 200.0D);
			int j13 = MathHelper.floor_double(this.boundingBox.maxZ + 200.0D);
			for (int k1 = i3; k1 <= l3; k1++)
			{
				for (int l1 = j3; l1 <= i13; l1++)
				{
					for (int i2 = k3; i2 <= j13; i2++)
					{
						Block block = this.worldObj.getBlock(k1, l1, i2);
						if (!block.isAir(this.worldObj, k1, l1, i2))
						{
							if ((block.equals(Blocks.leaves) | block.equals(Blocks.leaves2) | block.equals(Blocks.tallgrass) | block.equals(Blocks.cocoa) | block.equals(Blocks.red_mushroom) | block.equals(Blocks.red_mushroom_block) | block.equals(Blocks.melon_block) | block.equals(Blocks.melon_stem) | block.equals(Blocks.farmland) | block.equals(Blocks.cactus) | block.equals(Blocks.reeds) | block.equals(Blocks.sapling) | block.equals(Blocks.vine) | block.equals(Blocks.waterlily) | block.equals(Blocks.wheat) | block.equals(Blocks.brown_mushroom) | block.equals(Blocks.brown_mushroom_block) | block.equals(Blocks.carrots) | block.equals(Blocks.lit_pumpkin) | block.equals(Blocks.double_plant)))
							{
								this.worldObj.setBlockToAir(k1, l1, i2);

							}

							 else if (block.equals(Blocks.grass))
							{
								this.worldObj.setBlock(k1, l1, i2, Blocks.dirt);

							}

							 else if ((block.equals(Blocks.red_flower) | block.equals(Blocks.yellow_flower)))
							{
								this.worldObj.setBlock(k1, l1, i2, Blocks.deadbush);
							}
						}
					}
				}
			}

			playSound(this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());
			setDead();
		}

		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(30.0D, 30.0D, 30.0D));
		for (int i1 = 0; i1 < list.size(); i1++)
		{
			Entity entity1 = (Entity)list.get(i1);
			if ((entity1 instanceof EntityLiving) && !(entity1 instanceof Godzilla) && !(entity1 instanceof GodzillaHead))
			{
				((EntityLiving)entity1).addPotionEffect(new PotionEffect(Potion.poison.id, 10, 2));
				if (this.rand.nextInt(300) == 0)
				this.doLightningAttack((EntityLiving)entity1);
			}

			if ((entity1 instanceof EntityPlayer))
			{
				((EntityPlayer)entity1).addPotionEffect(new PotionEffect(Potion.poison.id, 10, 2));
				if (this.rand.nextInt(300) == 0)
				this.doLightningAttack((EntityPlayer)entity1);
			}

			if (entity1 != null && entity1.isEntityAlive() && entity1 instanceof PurplePower)
			((PurplePower) entity1).setHealth(0);
		}

		List<?> list2 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(10.0D, 10.0D, 10.0D));
		for (int i1 = 0; i1 < list2.size(); i1++)
		{
			Entity entity1 = (Entity)list2.get(i1);
			if ((entity1 instanceof EntityLiving) && !(entity1 instanceof Godzilla) && !(entity1 instanceof GodzillaHead))
			{
				entity1.setFire(5);
				if (this.rand.nextInt(100) == 0)
				this.doLightningAttack((EntityLiving)entity1);
			}

			if ((entity1 instanceof EntityPlayer))
			{
				entity1.setFire(5);
				if (this.rand.nextInt(100) == 0)
				this.doLightningAttack((EntityPlayer)entity1);
			}
		}

		int i = MathHelper.floor_double(this.boundingBox.minX - 10.0D);
		int j = MathHelper.floor_double(this.boundingBox.minY - 10.0D);
		int k = MathHelper.floor_double(this.boundingBox.minZ - 10.0D);
		int l = MathHelper.floor_double(this.boundingBox.maxX + 10.0D);
		int i1 = MathHelper.floor_double(this.boundingBox.maxY + 10.0D);
		int j1 = MathHelper.floor_double(this.boundingBox.maxZ + 10.0D);
		for (int k1 = i; k1 <= l; k1++)
		{
			for (int l1 = j; l1 <= i1; l1++)
			{
				for (int i2 = k; i2 <= j1; i2++)
				{
					Block block = this.worldObj.getBlock(k1, l1, i2);
					if (!block.isAir(this.worldObj, k1, l1, i2))
					{
						if (block.equals(Blocks.ice) || block.equals(Blocks.packed_ice) || block.equals(Blocks.water) || block.equals(Blocks.flowing_water) || block.equals(Blocks.snow) || block.equals(Blocks.snow_layer))
						{
							this.worldObj.setBlockToAir(k1, l1, i2);
							worldObj.playSoundEffect(k1, l1, i2, "random.fizz", 0.5F, 2.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.8F);
							for (int y = 0; y < 8; ++y)
							{
								worldObj.spawnParticle("largesmoke", k1 + (rand.nextGaussian() * 0.5D), l1 + (rand.nextGaussian() * 0.5D), i2 + (rand.nextGaussian() * 0.5D), 0.0D, 0.0D, 0.0D);
							}
						}
					}
				}
			}
		}
	}

	protected String getLivingSound()
	{
		if (this.worldObj.rand.nextBoolean())
		{
			return "orespawn:godzilla_living";
		}

		return null;
	}

	protected String getHurtSound()
	{
		return "orespawn:alo_hurt";
	}

	protected String getDeathSound()
	{
		this.playSound("fire.fire", getSoundVolume(), 0.5F);
		this.playSound("liquid.lava", getSoundVolume(), 0.5F);
		this.playSound("random.fizz", getSoundVolume(), 0.5F);
		return "orespawn:godzilla_death";
	}

	protected float getSoundVolume()
	{
		return 5F;
	}

	protected float getSoundPitch()
	{
		return 0.95F;
	}

	protected Item getDropItem()
	{
		return null;
	}

	protected void jump()
	{
		super.jump();
		this.motionY += 0.1D;
	}

	protected void jumpAtEntity(EntityLivingBase e)
	{
		this.motionY += 1.5D;
		this.posY += 1.7499999523162842D;
		double d1 = e.posX - this.posX;
		double d2 = e.posZ - this.posZ;
		float d = (float)Math.atan2(d2, d1);
		float f2 = (float)(d * 180.0D / 3.141592653589793D) - 90.0F;
		this.rotationYaw = f2;
		d1 = Math.sqrt(d1 * d1 + d2 * d2);
		this.motionX += d1 * 0.075D * Math.cos(d);
		this.motionZ += d1 * 0.075D * Math.sin(d);
		this.isAirBorne = true;
		getNavigator().setPath(null, 0.0D);
	}

	private double getHorizontalDistanceSqToEntity(Entity e)
	{
		double d1 = e.posZ - this.posZ;
		double d2 = e.posX - this.posX;
		return d1 * d1 + d2 * d2;
	}

	public double MygetDistanceSqToEntity(Entity par1Entity)
	{
		double d0 = this.posX - par1Entity.posX;
		double d1 = par1Entity.posY - this.posY;
		double d2 = this.posZ - par1Entity.posZ;
		if ((d1 > 0.0D) && (d1 < 20.0D))
		{
			d1 = 0.0D;
		}

		if (d1 > 20.0D)
		{
			d1 -= 10.0D;
		}

		return d0 * d0 + d1 * d1 + d2 * d2;
	}

	protected void updateAITasks()
	{
		EntityLivingBase e = null;
		int xzrange = 9;
		if (this.isDead)
		{
			return;
		}

		if (this.worldObj.isRemote)
		{
			return;
		}

		this.dataWatcher.updateObject(21, Integer.valueOf(OreSpawnMain.PlayNicely));
		this.worldObj.theProfiler.startSection("sensing");
		this.getEntitySenses().clearSensingCache();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("targetSelector");
		this.targetTasks.onUpdateTasks();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("goalSelector");
		this.tasks.onUpdateTasks();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("navigation");
		this.getNavigator().onUpdateNavigation();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("mob tick");
		this.updateAITick();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("controls");
		this.worldObj.theProfiler.startSection("move");
		this.getMoveHelper().onUpdateMoveHelper();
		this.worldObj.theProfiler.endStartSection("look");
		this.getLookHelper().onUpdateLook();
		this.worldObj.theProfiler.endStartSection("jump");
		this.getJumpHelper().doJump();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.endSection();
		Field hurt_timer = ReflectionHelper.findField(Godzilla.class, new String[] 
		{
			 "hurt_timer" 
		}
		);
		try
		{
			hurt_timer.setInt(this, hurt_timer.getInt(this) - 1);
		}

		catch (Exception ex)
		{
			this.setDead();
		}

		this.ticker += 1;
		if (this.ticker > 30000)
		{
			this.ticker = 0;
		}

		if (this.ticker % 70 == 0)
		{
			this.stream_count = 24;
		}

		if (this.jump_timer > 0)
		{
			this.jump_timer -= 1;
		}

		OreSpawnMain.godzilla_has_spawned = 1;
		if (this.worldObj.rand.nextInt(200) == 0)
		{
			setAttackTarget(null);
		}

		if (OreSpawnMain.PlayNicely == 0)
		{
			if (this.motionY < -0.95D)
			{
				this.jumped = 1;
			}

			if (this.motionY < -1.5D)
			{
				this.jumped = 2;
			}

			if ((this.jumped != 0) && (this.motionY > -0.1D))
			{
				double df = 1.0D;
				if (this.jumped == 2)
				{
					df = 1.5D;
				}

				doJumpDamage(this.posX, this.posY, this.posZ, 10.0D, OreSpawnMain.Godzilla_stats.attack * 2 * df, 0);
				doJumpDamage(this.posX, this.posY, this.posZ, 15.0D, OreSpawnMain.Godzilla_stats.attack * df, 0);
				doJumpDamage(this.posX, this.posY, this.posZ, 25.0D, OreSpawnMain.Godzilla_stats.attack / 2 * df, 0);
				this.jumped = 0;
			}
		}

		xzrange = 12;
		if (getAttacking() != 0)
		{
			xzrange = 16;
		}

		int k = -3 + this.ticker % 30;
		if (OreSpawnMain.PlayNicely == 0)
		{
			for (int i = -xzrange; i <= xzrange; i++)
			{
				for (int j = -xzrange; j <= xzrange; j++)
				{
					Block bid = this.worldObj.getBlock((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j);
					if (isCrushable(bid))
					{
						this.worldObj.setBlock((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j, Blocks.air);
						if (this.worldObj.rand.nextInt(15) == 1)
						{
							dropItemRand(Item.getItemFromBlock(bid), 1);
						}
					}

					else
					{
						if ((bid == Blocks.grass) &&(this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
						{
							this.worldObj.setBlock((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j, Blocks.dirt);
						}

						if ((bid == Blocks.farmland) &&(this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
						{
							this.worldObj.setBlock((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j, Blocks.dirt);
						}
					}
				}
			}
		}

		double dx = this.posX + 16.0D * Math.sin(Math.toRadians(this.rotationYawHead));
		double dz = this.posZ - 16.0D * Math.cos(Math.toRadians(this.rotationYawHead));
		k = -3 + this.ticker % 12;
		if (OreSpawnMain.PlayNicely == 0)
		{
			for (int i = -xzrange; i <= xzrange; i++)
			{
				for (int j = -xzrange; j <= xzrange; j++)
				{
					Block bid = this.worldObj.getBlock((int)dx + i, (int)this.posY + k, (int)dz + j);
					if (isCrushable(bid))
					{
						this.worldObj.setBlock((int)dx + i, (int)this.posY + k, (int)dz + j, Blocks.air);
						if (this.worldObj.rand.nextInt(15) == 1)
						{
							dropItemRandAt(Item.getItemFromBlock(bid), 1, dx, dz);
						}
					}

					else
					{
						if (bid.equals(Blocks.ice) || bid.equals(Blocks.packed_ice) || bid.equals(Blocks.water) || bid.equals(Blocks.flowing_water) || bid.equals(Blocks.snow) || bid.equals(Blocks.snow_layer))
						{
							this.worldObj.setBlockToAir((int)dx + i, (int)this.posY + k, (int)dz + j);
						}

						if ((bid == Blocks.grass) &&(this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
						{
							this.worldObj.setBlock((int)dx + i, (int)this.posY + k, (int)dz + j, Blocks.dirt);
						}

						if ((bid == Blocks.farmland) &&(this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
						{
							this.worldObj.setBlock((int)dx + i, (int)this.posY + k, (int)dz + j, Blocks.dirt);
						}
					}
				}
			}
		}

		if ((OreSpawnMain.PlayNicely == 0) &&(k == 0))
		{
			doJumpDamage(dx, this.posY, dz, 15.0D, OreSpawnMain.Godzilla_stats.attack, 1);
			doJumpDamage(dx, this.posY, dz, 20.0D, OreSpawnMain.Godzilla_stats.attack / 2, 1);
		}

		if (this.worldObj.rand.nextInt(5 - this.large_unknown_detected) == 1)
		{
			e = getAttackTarget();
			if (OreSpawnMain.PlayNicely != 0)
			{
				e = null;
			}

			if (e != null)
			{
				if (!e.isEntityAlive())
				{
					setAttackTarget(null);
					e = null;
				}

				else if (((e instanceof Godzilla)) || ((e instanceof GodzillaHead)))
				{
					setAttackTarget(null);
					e = null;
				}
			}

			@SuppressWarnings("unused")
			EntityLiving newent;
			if (e == null)
			{
				e = findSomethingToAttack();
				if (this.head_found == 0) 
				{

					newent = (EntityLiving)spawnCreature(this.worldObj, "MobzillaHead", this.posX, this.posY + 20.0D, this.posZ);
				}
			}

			if (e != null)
			{
				this.wander.setBusy(1);
				faceEntity(e, 10.0F, 10.0F);
				if ((this.worldObj.rand.nextInt(10) == 1) && (MygetDistanceSqToEntity(e) > 300.0D))
				{
					doLightningAttack(e);
				}

				else if ((this.worldObj.rand.nextInt(20 - this.large_unknown_detected * 5) == 1 || e.posY > e.posY + 10D) && (this.jump_timer == 0))
				{
					jumpAtEntity(e);
					this.jump_timer = 30;
				}

				else if (MygetDistanceSqToEntity(e) < 320.0F + e.width / 2.0F * (e.width / 2.0F))
				{
					setAttacking(1);
					this.getMoveHelper().setMoveTo(e.posX, e.posY, e.posZ, 3.0D);
					if (this.worldObj.rand.nextBoolean())
					{
						attackEntityAsMob(e);
					}
				}

				else
				{
					this.getMoveHelper().setMoveTo(e.posX, e.posY, e.posZ, 1.5D);
					if (getHorizontalDistanceSqToEntity(e) > 625.0D)
					{
						if (this.stream_count > 0)
						{
							setAttacking(1);
							double rr = Math.atan2(e.posZ - this.posZ, e.posX - this.posX);
							double rhdir = Math.toRadians((this.rotationYawHead + 90.0F) % 360.0F);
							double pi = 3.1415926545D;
							double rdd = Math.abs(rr - rhdir) % (pi * 2.0D);
							if (rdd > pi)
							{
								rdd -= pi * 2.0D;
							}

							rdd = Math.abs(rdd);
							if (rdd < 0.5D)
							{
								firecanon(e);
							}
						}

						else
						{
							setAttacking(rand.nextBoolean() ? 1 : 0);
						}
					}

					else 
					{

						setAttacking(rand.nextBoolean() ? 1 : 0);
					}
				}
			}

			else
			{
				setAttacking(rand.nextBoolean() ? 1 : 0);
				this.wander.setBusy(0);
				this.stream_count = 24;
			}
		}

		if ((this.worldObj.rand.nextInt(30) == 1) &&(getHealth() < mygetMaxHealth()))
		{
			heal(10F);
		}
	}

	public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6)
	{
		Entity var8 = null;
		var8 = EntityList.createEntityByName(par1, par0World);
		if (var8 != null)
		{
			var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
			par0World.spawnEntityInWorld(var8);
		}

		return var8;
	}

	private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2)
	{
		if (par1EntityLiving == null)
		{
			return false;
		}

		if (par1EntityLiving == this)
		{
			return false;
		}

		if (!par1EntityLiving.isEntityAlive())
		{
			return false;
		}

		if (MyUtils.isIgnoreable(par1EntityLiving))
		{
			return false;
		}

		if (!getEntitySenses().canSee(par1EntityLiving))
		{
			return false;
		}

		if ((par1EntityLiving instanceof Godzilla))
		{
			return false;
		}

		if ((par1EntityLiving instanceof GodzillaHead))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EntityCreeper))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EntityZombie))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EntitySpider))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EntitySkeleton))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EntityPlayer))
		{
			EntityPlayer p = (EntityPlayer)par1EntityLiving;
			if (p.capabilities.isCreativeMode == true)
			{
				return false;
			}
		}

		return true;
	}

	private boolean isVillagerTarget(EntityLivingBase par1EntityLiving, boolean par2)
	{
		if (par1EntityLiving == null)
		{
			return false;
		}

		if (par1EntityLiving == this)
		{
			return false;
		}

		if (!par1EntityLiving.isEntityAlive())
		{
			return false;
		}

		if (!getEntitySenses().canSee(par1EntityLiving))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EntityVillager))
		{
			return true;
		}

		if ((MyUtils.isRoyalty(par1EntityLiving)))
		{
			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	private EntityLivingBase doJumpDamage(double X, double Y, double Z, double dist, double damage, int knock)
	{
		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(X - dist, Y - 10.0D, Z - dist, X + dist, Y + 10.0D, Z + dist);
		List<?> var5 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, bb);
		Collections.sort(var5, this.TargetSorter);
		Iterator<?> var2 = var5.iterator();
		Entity var3 = null;
		EntityLivingBase var4 = null;
		while (var2.hasNext())
		{
			var3 = (Entity)var2.next();
			var4 = (EntityLivingBase)var3;
			if ((var4 != null) &&(var4 != this) &&(var4.isEntityAlive()) &&(!(var4 instanceof Godzilla)) &&(!(var4 instanceof GodzillaHead)))
			{
				DamageSource var21 = null;
				var21 = DamageSource.setExplosionSource(null);
				var21.setExplosion();
				var4.attackEntityFrom(var21, (float)damage / 2.0F);
				var4.attackEntityFrom(DamageSource.fall, (float)damage / 2.0F);
				this.worldObj.playSoundAtEntity(var4, "random.explode", 0.85F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
				if (knock != 0)
				{
					double ks = 3.5D;
					double inair = 0.75D;
					float f3 = (float)Math.atan2(var4.posZ - this.posZ, var4.posX - this.posX);
					var4.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
					var4.setFire(100);
				}
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private EntityLivingBase findSomethingToAttack()
	{
		if (OreSpawnMain.PlayNicely != 0)
		{
			this.head_found = 1;
			return null;
		}

		List<?> var5 = null;
		Iterator<?> var2 = null;
		Entity var3 = null;
		EntityLivingBase var4 = null;
		EntityLivingBase ret = null;
		int vf = 0;
		var5 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(100D, 50D, 100D));
		if (var5 == null)
		{
			return null;
		}

		Collections.sort(var5, this.TargetSorter);
		var2 = var5.iterator();
		this.head_found = 0;
		while (var2.hasNext())
		{
			var3 = (Entity)var2.next();
			var4 = (EntityLivingBase)var3;
			if ((var4 instanceof GodzillaHead))
			{
				this.head_found = 1;
				var4.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mygetMaxHealth());
			}

			if ((vf == 0) && (isVillagerTarget(var4, false)))
			{
				ret = var4;
				vf = 1;
			}

			if ((ret == null) && (vf == 0) && (isSuitableTarget(var4, false)))
			{
				ret = var4;
			}
		}

		return ret;
	}

	public boolean getCanSpawnHere()
	{
		if (!isValidLightLevel())
		{
			return false;
		}

		if (OreSpawnMain.godzilla_has_spawned != 0)
		{
			return false;
		}

		if (this.worldObj.rand.nextInt(40) != 1)
		{
			return false;
		}

		for (int k = -5; k <= 5; k++)
		{
			for (int j = -5; j <= 5; j++)
			{
				for (int i = 1; i < 23; i++)
				{
					Block bid = this.worldObj.getBlock((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k);
					if (bid != Blocks.air)
					{
						return false;
					}
				}
			}
		}

		Godzilla target = null;
		target = (Godzilla)this.worldObj.findNearestEntityWithinAABB(Godzilla.class, this.boundingBox.expand(64.0D, 16.0D, 64.0D), this);
		if (target != null)
		{
			return false;
		}

		if (!this.worldObj.isRemote)
		{
			OreSpawnMain.godzilla_has_spawned = 1;
		}

		return true;
	}

	private ItemStack dropItemRand(Item index, int par1)
	{
		EntityItem var3 = null;
		ItemStack is = new ItemStack(index, par1, 0);
		var3 = new EntityItem(this.worldObj, this.posX + OreSpawnMain.OreSpawnRand.nextInt(10) - OreSpawnMain.OreSpawnRand.nextInt(10), this.posY + 4.0D + this.worldObj.rand.nextInt(10), this.posZ + OreSpawnMain.OreSpawnRand.nextInt(10) - OreSpawnMain.OreSpawnRand.nextInt(10), is);
		if (var3 != null)
		{
			this.worldObj.spawnEntityInWorld(var3);
		}

		return is;
	}

	private ItemStack dropItemRandAt(Item index, int par1, double dx, double dz)
	{
		EntityItem var3 = null;
		ItemStack is = new ItemStack(index, par1, 0);
		var3 = new EntityItem(this.worldObj, dx + OreSpawnMain.OreSpawnRand.nextInt(10) - OreSpawnMain.OreSpawnRand.nextInt(10), this.posY + 4.0D + this.worldObj.rand.nextInt(6), dz + OreSpawnMain.OreSpawnRand.nextInt(10) - OreSpawnMain.OreSpawnRand.nextInt(10), is);
		if (var3 != null)
		{
			this.worldObj.spawnEntityInWorld(var3);
		}

		return is;
	}

	private boolean isCrushable(Block bid)
	{
		if (bid == null)
		{
			return false;
		}

		if (!this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
		{
			return false;
		}

		if (bid == Blocks.grass)
		{
			return false;
		}

		if (bid == Blocks.dirt)
		{
			return false;
		}

		if (bid == Blocks.stone)
		{
			return false;
		}

		if (bid == Blocks.farmland)
		{
			return false;
		}

		if (bid == Blocks.water)
		{
			return false;
		}

		if (bid == Blocks.flowing_water)
		{
			return false;
		}

		if (bid == Blocks.lava)
		{
			return false;
		}

		if (bid == Blocks.flowing_lava)
		{
			return false;
		}

		if (bid == Blocks.bedrock)
		{
			return false;
		}

		if (bid == Blocks.obsidian)
		{
			return false;
		}

		if (bid == Blocks.sand)
		{
			return false;
		}

		if (bid == Blocks.gravel)
		{
			return false;
		}

		if (bid == Blocks.iron_block)
		{
			return false;
		}

		if (bid == Blocks.diamond_block)
		{
			return false;
		}

		if (bid == Blocks.emerald_block)
		{
			return false;
		}

		if (bid == Blocks.gold_block)
		{
			return false;
		}

		if (bid == Blocks.netherrack)
		{
			return false;
		}

		if (bid == Blocks.end_stone)
		{
			return false;
		}

		if (bid == OreSpawnMain.MyBlockAmethystBlock)
		{
			return false;
		}

		if (bid == OreSpawnMain.MyBlockRubyBlock)
		{
			return false;
		}

		if (bid == OreSpawnMain.MyBlockUraniumBlock)
		{
			return false;
		}

		if (bid == OreSpawnMain.MyBlockTitaniumBlock)
		{
			return false;
		}

		if (bid == OreSpawnMain.CrystalStone)
		{
			return false;
		}

		if (bid == OreSpawnMain.CrystalGrass)
		{
			return false;
		}

		return true;
	}

	private void firecanon(EntityLivingBase e)
	{
		double yoff = 19.475D;
		double xzoff = 22.55D;
		BetterFireball bf = null;
		double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
		double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
		if (this.stream_count > 0)
		{
			EntityTitanFireball gf = new EntityTitanFireball(this.worldObj, this, e.posX - cx, e.posY + e.height / 2.0F - (this.posY + yoff), e.posZ - cz);
			gf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
			gf.setPosition(cx, this.posY + yoff, cz);
			this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
			this.worldObj.spawnEntityInWorld(gf);
			for (int i = 0; i < 9; i++)
			{
				float r1 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
				float r2 = 3.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
				float r3 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
				bf = new BetterFireball(this.worldObj, this, e.posX - cx + r1, e.posY + e.height / 2.0F - (this.posY + yoff) + r2, e.posZ - cz + r3);
				bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
				bf.setPosition(cx, this.posY + yoff, cz);
				if (this.worldObj.rand.nextInt(2) == 1)
				bf.setReallyBig();
				else
				bf.setBig();
				this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
				this.worldObj.spawnEntityInWorld(bf);
			}

			this.stream_count -= 1;
		}
	}

	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if ((par1Entity != null) && ((par1Entity instanceof EntityLivingBase)))
		{
			float s = par1Entity.height * par1Entity.width;
			if ((s > 30.0F) &&(!MyUtils.isRoyalty(par1Entity)) && (!(par1Entity instanceof Godzilla)) && (!(par1Entity instanceof GodzillaHead)) && (!(par1Entity instanceof PitchBlack)) && (!(par1Entity instanceof Kraken)))
			{
				EntityLivingBase e = (EntityLivingBase)par1Entity;
				e.setHealth(e.getHealth() / 2.0F);
				if (e.height == 50F && e.width == 15F)
				{
					e.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(e.getMaxHealth() <= this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() ? 0D : e.getMaxHealth() - this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
					e.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 40F);
					e.addPotionEffect(new PotionEffect(ClientProxy.death.id, Integer.MAX_VALUE, 19));
				}

				e.attackEntityFrom(DamageSource.causeMobDamage(this), OreSpawnMain.Godzilla_stats.attack * 10.0F);
				this.large_unknown_detected = 1;
			}
		}

		if ((par1Entity != null) && ((par1Entity instanceof EntityDragon)))
		{
			EntityDragon dr = (EntityDragon)par1Entity;
			DamageSource var21 = null;
			var21 = DamageSource.setExplosionSource(null);
			var21.setExplosion();
			if (this.worldObj.rand.nextInt(6) == 1)
			{
				dr.attackEntityFromPart(dr.dragonPartHead, var21, OreSpawnMain.Godzilla_stats.attack);

			}

			 else 
			{

				dr.attackEntityFromPart(dr.dragonPartBody, var21, OreSpawnMain.Godzilla_stats.attack);
			}
		}

		if (super.attackEntityAsMob(par1Entity))
		{
			if ((par1Entity != null) && ((par1Entity instanceof EntityLivingBase)))
			{
				double ks = 3.2D;
				double inair = 0.3D;
				float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
				if ((par1Entity.isDead) || ((par1Entity instanceof EntityPlayer)))
				{
					inair *= 2.0D;
				}

				par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
			}

			return true;
		}

		return false;
	}

	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (getHealth() <= 1000.0F)
		this.preventMeltDown += 20;
		boolean ret = false;
		float dm = par2;
		if (dm > 500.0F)
		{
			dm = 500.0F;
		}

		Entity e = par1DamageSource.getEntity();
		if (!par1DamageSource.getDamageType().equals("cactus"))
		{
			ret = super.attackEntityFrom(par1DamageSource, dm);
			e = par1DamageSource.getEntity();
			if ((e != null) && ((e instanceof EntityLivingBase)))
			{
				if ((!(e instanceof GodzillaHead)) && (!(e instanceof Godzilla)))
				{
					setAttackTarget((EntityLivingBase)e);
					setTarget(e);
					getNavigator().tryMoveToEntityLiving((EntityLivingBase)e, 1.2D);
				}
			}
		}

		return ret;
	}

	public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) 
	{
		 
	}


	private void doLightningAttack(EntityLivingBase e)
	{
		if (e == null)
		{
			return;
		}

		float var2 = 600.0F;
		e.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
		e.attackEntityFrom(DamageSourceExtra.lightningBolt, var2 / 5);
		e.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this), var2 / 10);
		e.setFire(5);
		for (int var3 = 0; var3 < 20; var3++)
		{
			this.worldObj.spawnParticle("smoke", e.posX + this.rand.nextFloat() - this.rand.nextFloat(), e.posY + this.rand.nextFloat() - this.rand.nextFloat(), e.posZ + this.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("largesmoke", e.posX + this.rand.nextFloat() - this.rand.nextFloat(), e.posY + this.rand.nextFloat() - this.rand.nextFloat(), e.posZ + this.rand.nextFloat() - this.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("fireworksSpark", e.posX, e.posY, e.posZ, this.worldObj.rand.nextGaussian(), this.worldObj.rand.nextGaussian(), this.worldObj.rand.nextGaussian());
		}

		this.worldObj.playSoundAtEntity(e, "random.explode", 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
		if (!this.worldObj.isRemote)
		{
			this.worldObj.newExplosion(this, e.posX, e.posY, e.posZ, 6F, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
		}

		this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, e.posX, e.posY + 1.0D, e.posZ));
		this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX, this.posY + 15.0D, this.posZ));
	}

	protected void dropFewItems(boolean par1, int par2)
	{
		this.playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
		ItemStack is = null;
		dropItemRand(Items.item_frame, 1);
		int var5 = 100 + this.worldObj.rand.nextInt(100);
		for (int var4 = 0; var4 < var5; var4++)
		{
			dropItemRand(OreSpawnMain.MyGodzillaScale, 1);
		}

		var5 = 300 + this.worldObj.rand.nextInt(300);
		for (int var4 = 0; var4 < var5; var4++)
		{
			dropItemRand(Items.beef, 1);
		}

		var5 = 100 + this.worldObj.rand.nextInt(100);
		for (int var4 = 0; var4 < var5; var4++)
		{
			dropItemRand(Items.bone, 1);
		}

		var5 = 100 + this.worldObj.rand.nextInt(100);
		for (int var4 = 0; var4 < var5; var4++)
		{
			dropItemRand(Items.blaze_powder, 1);
		}

		int i = 50 + this.worldObj.rand.nextInt(50);
		for (int var4 = 0; var4 < i; var4++)
		{
			int var3 = this.worldObj.rand.nextInt(80);
			switch (var3)
			{
				case 0:is = dropItemRand(OreSpawnMain.MyUltimateSword, 1);
				break;
				case 1:is = dropItemRand(Items.diamond, 1);
				break;
				case 2:is = dropItemRand(Item.getItemFromBlock(Blocks.diamond_block), 1);
				break;
				case 3:is = dropItemRand(Items.diamond_sword, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 4:is = dropItemRand(Items.diamond_shovel, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 5:is = dropItemRand(Items.diamond_pickaxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 6:is = dropItemRand(Items.diamond_axe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 7:is = dropItemRand(Items.diamond_hoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 8:is = dropItemRand(Items.diamond_helmet, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(2));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 9:is = dropItemRand(Items.diamond_chestplate, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 10:is = dropItemRand(Items.diamond_leggings, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 11:is = dropItemRand(Items.diamond_boots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 12:is = dropItemRand(OreSpawnMain.MyUltimateBow, 1);
				break;
				case 13:is = dropItemRand(OreSpawnMain.MyUltimateAxe, 1);
				break;
				case 14:is = dropItemRand(Items.iron_ingot, 1);
				break;
				case 15:is = dropItemRand(OreSpawnMain.MyUltimatePickaxe, 1);
				break;
				case 16:is = dropItemRand(Items.iron_sword, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 17:is = dropItemRand(Items.iron_shovel, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 18:is = dropItemRand(Items.iron_pickaxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 19:is = dropItemRand(Items.iron_axe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 20:is = dropItemRand(Items.iron_hoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 21:is = dropItemRand(Items.iron_helmet, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 22:is = dropItemRand(Items.iron_chestplate, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 23:is = dropItemRand(Items.iron_leggings, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 24:is = dropItemRand(Items.iron_boots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 25:is = dropItemRand(OreSpawnMain.MyUltimateShovel, 1);
				break;
				case 26:dropItemRand(Item.getItemFromBlock(Blocks.iron_block), 1);
				break;
				case 27:is = dropItemRand(Items.gold_nugget, 1);
				break;
				case 28:is = dropItemRand(Items.gold_ingot, 1);
				break;
				case 29:is = dropItemRand(Items.golden_carrot, 1);
				break;
				case 30:is = dropItemRand(Items.golden_sword, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 31:is = dropItemRand(Items.golden_shovel, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 32:is = dropItemRand(Items.golden_pickaxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 33:is = dropItemRand(Items.golden_axe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 34:is = dropItemRand(Items.golden_hoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 35:is = dropItemRand(Items.golden_helmet, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 36:is = dropItemRand(Items.golden_chestplate, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 37:is = dropItemRand(Items.golden_leggings, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 38:is = dropItemRand(Items.golden_boots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 39:dropItemRand(Items.golden_apple, 1);
				break;
				case 40:dropItemRand(Item.getItemFromBlock(Blocks.gold_block), 1);
				break;
				case 41:EntityItem var33 = null;
				is = new ItemStack(Items.golden_apple, 1, 1);
				var33 = new EntityItem(this.worldObj, this.posX + OreSpawnMain.OreSpawnRand.nextInt(3) - OreSpawnMain.OreSpawnRand.nextInt(3), this.posY + 1.0D, this.posZ + OreSpawnMain.OreSpawnRand.nextInt(3) - OreSpawnMain.OreSpawnRand.nextInt(3), is);
				if (var33 != null)
				{
					this.worldObj.spawnEntityInWorld(var33);
				}

				break;
				case 42:is = dropItemRand(OreSpawnMain.MyExperienceSword, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 43:is = dropItemRand(OreSpawnMain.ExperienceHelmet, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 44:is = dropItemRand(OreSpawnMain.ExperienceBody, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 45:is = dropItemRand(OreSpawnMain.ExperienceLegs, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 46:is = dropItemRand(OreSpawnMain.ExperienceBoots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 47:is = dropItemRand(OreSpawnMain.MyAmethystSword, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 48:is = dropItemRand(OreSpawnMain.MyAmethystShovel, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 49:is = dropItemRand(OreSpawnMain.MyAmethystPickaxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 50:is = dropItemRand(OreSpawnMain.MyAmethystAxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 51:is = dropItemRand(OreSpawnMain.MyAmethystHoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 52:is = dropItemRand(Item.getItemFromBlock(OreSpawnMain.MyBlockAmethystBlock), 1);
				break;
				case 53:is = dropItemRand(OreSpawnMain.AmethystHelmet, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 54:is = dropItemRand(OreSpawnMain.AmethystBody, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 55:is = dropItemRand(OreSpawnMain.AmethystLegs, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 56:is = dropItemRand(OreSpawnMain.AmethystBoots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 57:is = dropItemRand(OreSpawnMain.RubyHelmet, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 58:is = dropItemRand(OreSpawnMain.RubyBody, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 59:is = dropItemRand(OreSpawnMain.RubyLegs, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 60:is = dropItemRand(OreSpawnMain.RubyBoots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 61:is = dropItemRand(OreSpawnMain.MyRubySword, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.baneOfArthropods, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.knockback, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.looting, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireAspect, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 62:is = dropItemRand(OreSpawnMain.MyRubyShovel, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 63:is = dropItemRand(OreSpawnMain.MyRubyPickaxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 64:is = dropItemRand(OreSpawnMain.MyRubyAxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 65:is = dropItemRand(OreSpawnMain.MyRubyHoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 66:is = dropItemRand(Item.getItemFromBlock(OreSpawnMain.MyBlockRubyBlock), 1);
				break;
				case 67:is = dropItemRand(OreSpawnMain.UltimateHelmet, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.respiration, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.aquaAffinity, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 68:is = dropItemRand(OreSpawnMain.UltimateBody, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 69:is = dropItemRand(OreSpawnMain.UltimateLegs, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.protection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.blastProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.projectileProtection, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 70:is = dropItemRand(OreSpawnMain.UltimateBoots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 71:is = dropItemRand(OreSpawnMain.MyUltimateShovel, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 73:is = dropItemRand(OreSpawnMain.MyUltimatePickaxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fortune, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 74:is = dropItemRand(OreSpawnMain.MyUltimateAxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 75:is = dropItemRand(OreSpawnMain.MyUltimateHoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
			}
		}
	}
}


