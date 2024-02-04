package net.minecraft.entity.orespawnaddon;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import danger.orespawn.*;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
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
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
public class EntityOverlordScorpion
extends EmperorScorpion
{
	private GenericTargetSorter TargetSorter = null;
	private int stream_count = 0;
	private int stream_count_l = 0;
	private int stream_count_i = 0;
	public EntityOverlordScorpion(World par1World)
	{
		super(par1World);
		setSize(7.0F, 3.0F);
		getNavigator().setAvoidsWater(true);
		this.experienceValue = 14000;
		this.fireResistance = 100;
		this.isImmuneToFire = true;
		this.TargetSorter = new GenericTargetSorter(this);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mygetMaxHealth() * 50D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(OreSpawnMain.EmperorScorpion_stats.attack * 10D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
	}

	protected boolean canDespawn()
	{
		return false;
	}

	public int getTotalArmorValue()
	{
		return 24;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		setSize(7.0F, 3.0F);
		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(20D, 20D, 20D));
		if ((list != null) && (!list.isEmpty()))
		{
			for (int i = 0; i < list.size(); i++)
			{
				Entity entity = (Entity)list.get(i);
				if (entity != null && entity.isEntityAlive() && entity instanceof PurplePower)
				{
					double mx = this.posX - entity.posX;
					double my = (this.posY + 15D) - entity.posY;
					double mz = this.posZ - entity.posZ;
					float f2 = MathHelper.sqrt_double(mx * mx + my * my + mz * mz);
					entity.motionX = (mx / f2 + entity.motionX);
					entity.motionY = (my / f2 + entity.motionY);
					entity.motionZ = (mz / f2 + entity.motionZ);
					entity.attackEntityFrom(DamageSource.generic, 10);
					entity.hurtResistantTime = 0;
					this.setHealth(this.getHealth() + 10);
					entity.playSound("orespawn:trex_death", 0.25F, 1.5F);
					if (this.getDistanceSqToEntity(entity) <= 100D)
					{
						entity.setDead();
						this.setHealth(this.getHealth() + ((EntityLivingBase)entity).getHealth());
					}
				}
			}
		}

		List<?> list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(20D, 20D, 20D));
		if ((list1 != null) && (!list1.isEmpty()))
		{
			for (int i = 0; i < list1.size(); i++)
			{
				Entity entity = (Entity)list1.get(i);
				if (entity != null && entity.isEntityAlive() && MyUtils.isRoyalty(entity) && !(entity instanceof PurplePower) && !(entity instanceof KingHead) && !(entity instanceof QueenHead))
				{
					double mx = this.posX - entity.posX;
					double my = (this.posY + 5D) - (entity.posY + entity.getEyeHeight());
					double mz = this.posZ - entity.posZ;
					short short1 = (short)(int)getDistanceToEntity(entity);
					for (int f = 0; f < short1; f++)
					{
						double d9 = f / (short1 - 1.0D);
						double d6 = this.posX + mx * -d9;
						double d7 = this.posY + my * -d9;
						double d8 = this.posZ + mz * -d9;
						this.worldObj.spawnParticle("fireworksSpark", d6, d7, d8, entity.motionX, entity.motionY, entity.motionZ);
					}
				}
			}
		}
	}

	protected String getHurtSound()
	{
		return "orespawn:king_hit";
	}

	protected String getDeathSound()
	{
		return "orespawn:trex_death";
	}

	protected float getSoundVolume()
	{
		return 3F;
	}

	protected float getSoundPitch()
	{
		return 0.999F;
	}

	protected Item getDropItem()
	{
		return Items.beef;
	}

	private ItemStack dropItemRand(Item index, int par1)
	{
		EntityItem var3 = null;
		ItemStack is = new ItemStack(index, par1, 0);
		var3 = new EntityItem(this.worldObj, this.posX + rand.nextInt(5) - rand.nextInt(5), this.posY + 1.0D, this.posZ + rand.nextInt(5) - rand.nextInt(5), is);
		if (var3 != null)
		{
			this.worldObj.spawnEntityInWorld(var3);
		}

		return is;
	}

	protected void dropFewItems(boolean par1, int par2)
	{
		dropItemRand(Item.getItemFromBlock(TitanBlocks.void_block), 1);
		int i = 64 + this.worldObj.rand.nextInt(32);
		for (int var4 = 0; var4 < i; var4++)
		{
			dropItemRand(Item.getItemFromBlock(Blocks.obsidian), 1);
		}

		i = 24 + this.worldObj.rand.nextInt(24);
		for (int var4 = 0; var4 < i; var4++)
		{
			dropItemRand(Items.beef, 1);
		}

		i = 8 + this.worldObj.rand.nextInt(8);
		for (int var4 = 0; var4 < i; var4++)
		{
			dropItemRand(Items.diamond, 1);
		}

		i = 5 + this.worldObj.rand.nextInt(5);
		for (int var4 = 0; var4 < i; var4++)
		{
			int var3 = this.worldObj.rand.nextInt(20);
			ItemStack is;
			switch (var3)
			{
				case 0:is = dropItemRand(TitanItems.voidSword, 1);
				break;
				case 1:is = dropItemRand(TitanItems.harcadium, 1);
				break;
				case 2:is = dropItemRand(Item.getItemFromBlock(TitanBlocks.harcadium_block), 1);
				break;
				case 3:is = dropItemRand(TitanItems.harcadiumSword, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.baneOfArthropods, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.knockback, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.looting, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 10 + this.worldObj.rand.nextInt(10));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fireAspect, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.sharpness, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 4:is = dropItemRand(TitanItems.harcadiumSpade, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 12 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 12 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 5:is = dropItemRand(TitanItems.harcadiumPickaxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 12 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 12 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.fortune, 12 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 6:is = dropItemRand(TitanItems.harcadiumAxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 12 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 12 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 7:is = dropItemRand(TitanItems.harcadiumHoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 12 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 12 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 8:is = dropItemRand(TitanItems.harcadiumHelmet, 1);
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
				case 9:is = dropItemRand(TitanItems.harcadiumChestplate, 1);
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
				case 10:is = dropItemRand(TitanItems.harcadiumLeggings, 1);
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
				case 11:is = dropItemRand(TitanItems.harcadiumBoots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 12:is = dropItemRand(TitanItems.harcadiumBow, 1);
			}
		}
	}

	private void firecanon(EntityLivingBase e)
	{
		double yoff = 3.0D;
		double xzoff = 9.0D;
		EntityTitanFireball bf = null;
		double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
		double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
		if (this.stream_count > 0)
		{
			bf = new EntityTitanFireball(this.worldObj, this, e.posX - cx, e.posY + 0.5D, e.posZ - cz);
			bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
			bf.setPosition(cx, this.posY + yoff, cz);
			this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
			this.worldObj.spawnEntityInWorld(bf);
			for (int i = 0; i < 2; i++)
			{
				float r1 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
				float r2 = 3.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
				float r3 = 5.0F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
				bf = new EntityTitanFireball(this.worldObj, this, e.posX - cx + r1, e.posY + e.height / 2.0F - (this.posY + yoff) + r2, e.posZ - cz + r3);
				bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0F);
				bf.setPosition(cx, this.posY + yoff, cz);
				this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
				this.worldObj.spawnEntityInWorld(bf);
			}

			this.stream_count -= 1;
		}
	}

	private void firecanonl(EntityLivingBase e)
	{
		double yoff = 3.0D;
		double xzoff = 9.0D;
		double var3 = 0.0D;
		double var5 = 0.0D;
		double var7 = 0.0D;
		float var9 = 0.0F;
		double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
		double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
		if (this.stream_count_l > 0)
		{
			this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
			for (int i = 0; i < 2; i++)
			{
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				EntityThunderboltShot lb = new EntityThunderboltShot(this.worldObj, cx, this.posY + yoff, cz);
				lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0F, 0.0F);
				var3 = e.posX - lb.posX;
				var5 = e.posY + 0.25D - lb.posY;
				var7 = e.posZ - lb.posZ;
				var9 = MathHelper.sqrt_double(var3 * var3 + var7 * var7) * 0.2F;
				lb.setThrowableHeading(var3, var5 + var9, var7, 1.25F, 9.0F);
				lb.motionX *= 3.0D;
				lb.motionY *= 3.0D;
				lb.motionZ *= 3.0D;
				this.worldObj.spawnEntityInWorld(lb);
			}

			this.stream_count_l -= 1;
		}
	}

	private void firecanoni(EntityLivingBase e)
	{
		double yoff = 3.0D;
		double xzoff = 9.0D;
		double var3 = 0.0D;
		double var5 = 0.0D;
		double var7 = 0.0D;
		float var9 = 0.0F;
		double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
		double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
		if (this.stream_count_i > 0)
		{
			this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
			for (int i = 0; i < 2; i++)
			{
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				this.worldObj.rand.nextFloat();
				EntityIceBall lb = new EntityIceBall(this.worldObj, cx, this.posY + yoff, cz);
				lb.setLocationAndAngles(cx, this.posY + yoff, cz, 0.0F, 0.0F);
				var3 = e.posX - lb.posX;
				var5 = e.posY + 0.25D - lb.posY;
				var7 = e.posZ - lb.posZ;
				var9 = MathHelper.sqrt_double(var3 * var3 + var7 * var7) * 0.2F;
				lb.setThrowableHeading(var3, var5 + var9, var7, 1.25F, 9.0F);
				lb.motionX *= 3.0D;
				lb.motionY *= 3.0D;
				lb.motionZ *= 3.0D;
				this.worldObj.spawnEntityInWorld(lb);
			}

			this.stream_count_i -= 1;
		}
	}

	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		return false;
	}

	public boolean attackEntityAsMob(Entity par1Entity)
	{
		double ks = 3.2D;
		double inair = 0.3D;
		int var2 = 6;
		boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
		if (var4)
		{
			if ((par1Entity != null) && ((par1Entity instanceof EntityLivingBase)))
			{
				float s = par1Entity.height * par1Entity.width;
				if (MyUtils.isRoyalty(par1Entity))
				{
					try
					{
						ReflectionHelper.findField(par1Entity.getClass(), new String[] 
						{
							 "hurt_timer" 
						}
						).setInt(this, 0);
						par1Entity.hurtResistantTime = 0;
						((EntityLivingBase)par1Entity).attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 10.0F);
					}

					catch (Exception e)
					{
					}
				}

				if ((s > 30.0F) && (!MyUtils.isRoyalty(par1Entity)) && (!(par1Entity instanceof Godzilla)) && (!(par1Entity instanceof GodzillaHead)) && (!(par1Entity instanceof PitchBlack)) && (!(par1Entity instanceof Kraken)))
				{
					EntityLivingBase e = (EntityLivingBase)par1Entity;
					if (e.height == 50F && e.width == 15F)
					{
						e.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(e.getMaxHealth() <= this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() ? 0D : e.getMaxHealth() - this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
						e.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 40F);
						e.addPotionEffect(new PotionEffect(ClientProxy.death.id, Integer.MAX_VALUE, 19));
					}

					e.setHealth(e.getHealth() / 2.0F);
					e.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 10.0F);
				}

				this.getLookHelper().setLookPositionWithEntity(par1Entity, 180F, 40F);
				if (par1Entity.getClass() == (Class<?>)EntityList.stringToClassMapping.get("Emperor Scorpion"))
				{
					((EntityLiving)par1Entity).setAttackTarget(null);
					this.setAttackTarget(null);
				}

				if (this.worldObj.difficultySetting == EnumDifficulty.EASY)
				{
					var2 = 12;
					if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
					{
						var2 = 16;

					}

					 else if (this.worldObj.difficultySetting == EnumDifficulty.HARD)
					{
						var2 = 20;
					}
				}

				if (this.worldObj.rand.nextInt(2) == 0)
				{
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, var2 * 20, 0));
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, var2 * 20, 0));
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.blindness.id, var2 * 20, 0));
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.wither.id, var2 * 20, 0));
				}

				float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
				if ((par1Entity.isDead) || ((par1Entity instanceof EntityPlayer)))
				{
					inair *= 2.0D;
				}

				par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
			}
		}

		return var4;
	}

	protected void updateAITasks()
	{
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
		if (!this.worldObj.isRemote && this.getAttackTarget() != null && this.ticksExisted % 30 == 0 && this.worldObj.rand.nextInt(6) == 0)
		{
			this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
			this.motionY += 1.25D;
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

		Field hurt_timer = ReflectionHelper.findField(EmperorScorpion.class, new String[] 
		{
			 "hurt_timer" 
		}
		);
		try
		{
			hurt_timer.setInt(this, hurt_timer.getInt(this) - 1);
		}

		catch (Exception e)
		{
			this.setDead();
		}

		EntityLivingBase e = null;
		this.entityToAttack = null;
		if (this.isDead)
		{
			return;
		}

		this.stepHeight = 2F;
		if (this.ticksExisted % 160 == 0)
		{
			this.stream_count = 10;
		}

		if (this.ticksExisted % 90 == 0)
		{
			this.stream_count_l = 5;
		}

		if (this.ticksExisted % 70 == 0)
		{
			this.stream_count_i = 8;
		}

		if (this.worldObj.rand.nextInt(4) == 0)
		{
			e = getAttackTarget();
			if ((e != null) && (!e.isEntityAlive()))
			{
				setAttackTarget(null);
				e = null;
			}

			if (this.worldObj.rand.nextInt(100) == 0)
			{
				setAttackTarget(null);
			}

			if (e == null)
			{
				e = findSomethingToAttack();
			}

			EntityCreature newent;
			if (e != null)
			{
				faceEntity(e, 10.0F, 10.0F);
				if (getDistanceSqToEntity(e) < (14.0F + e.width / 2.0F) * (14.0F + e.width / 2.0F))
				{
					setAttacking(1);
					if ((this.worldObj.rand.nextInt(2) == 0))
					{
						attackEntityAsMob(e);
						if (!this.worldObj.isRemote)
						{
							if (this.worldObj.rand.nextInt(2) == 0)
							{
								this.worldObj.playSoundAtEntity(e, "orespawn:scorpion_attack", 1.4F, 1.0F);

							}

							 else 
							{

								this.worldObj.playSoundAtEntity(e, "orespawn:scorpion_living", 1.0F, 1.0F);
							}
						}
					}
				}

				else
				{
					this.getMoveHelper().setMoveTo(e.posX, e.posY, e.posZ, 2.4D);
					if (getDistanceSqToEntity(e) > 512D && !this.worldObj.isRemote)
					{
						if (this.stream_count > 0)
						{
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

						if (this.stream_count_i > 0)
						{
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
								firecanoni(e);
							}
						}

						if (this.stream_count_l > 0)
						{
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
								firecanonl(e);
							}
						}
					}
				}

				if (this.worldObj.rand.nextInt(20) == 1)
				{
					newent = (EntityCreature)spawnCreature(this.worldObj, "Emperor Scorpion", (this.posX + e.posX) / 2.0D + this.worldObj.rand.nextInt(5) - this.worldObj.rand.nextInt(5), (this.posY + e.posY) / 2.0D + 1.01D, (this.posZ + e.posZ) / 2.0D + this.worldObj.rand.nextInt(5) - this.worldObj.rand.nextInt(5));
					newent.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(700D);
					newent.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(70D);
					newent.heal(700);
					newent.setAttackTarget(e);
				}
			}

			else
			{
				setAttacking(0);
			}
		}

		if ((this.worldObj.rand.nextInt(50) == 1))
		{
			heal(2.0F);
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
			((EntityLiving)var8).playLivingSound();
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

		if (!getEntitySenses().canSee(par1EntityLiving))
		{
			return false;
		}

		if (MyUtils.isIgnoreable(par1EntityLiving))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EntityEnderman))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EnderKnight))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EnderReaper))
		{
			return false;
		}

		if ((par1EntityLiving instanceof Scorpion))
		{
			return false;
		}

		if ((par1EntityLiving instanceof EmperorScorpion))
		{
			return false;
		}

		if ((par1EntityLiving instanceof PurplePower))
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

	@SuppressWarnings("unchecked")
	private EntityLivingBase findSomethingToAttack()
	{
		if (OreSpawnMain.PlayNicely != 0)
		{
			return null;
		}

		List<?> var5 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(80D, 80D, 80D));
		Collections.sort(var5, this.TargetSorter);
		Iterator<?> var2 = var5.iterator();
		Entity var3 = null;
		EntityLivingBase var4 = null;
		while (var2.hasNext())
		{
			var3 = (Entity)var2.next();
			var4 = (EntityLivingBase)var3;
			if (isSuitableTarget(var4, false))
			{
				this.setAttackTarget(var4);
				return var4;
			}
		}

		return null;
	}

	public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) 
	{
		 
	}


	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (!par1DamageSource.getDamageType().equals("cactus") && !par1DamageSource.getDamageType().equals("drown") && super.attackEntityFrom(par1DamageSource, par2))
		{
			Entity e = par1DamageSource.getEntity();
			if ((e != null) && ((e instanceof EntityLivingBase)))
			{
				setAttackTarget((EntityLivingBase)e);
				setTarget(e);
			}

			return true;
		}

		else
		{
			return false;
		}
	}
}


