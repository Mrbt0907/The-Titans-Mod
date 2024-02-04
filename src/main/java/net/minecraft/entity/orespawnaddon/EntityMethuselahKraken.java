package net.minecraft.entity.orespawnaddon;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import cpw.mods.fml.relauncher.ReflectionHelper;
import danger.orespawn.*;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityGammaLightning;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TitanBlocks;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
public class EntityMethuselahKraken extends Kraken
{
	private GenericTargetSorter TargetSorter = null;
	private ChunkCoordinates currentFlightTarget = null;
	private EntityLivingBase caught = null;
	private int newtarget = 0;
	private int release = 0;
	private int weather_set = 10;
	private int long_enough = 3600;
	private int call_reinforcements = 0;
	private boolean hit_by_player = false;
	private int straight_down = 1;
	private int stream_count_l = 0;
	private int stream_count_i = 0;
	public EntityMethuselahKraken(World par1World)
	{
		super(par1World);
		if (OreSpawnMain.PlayNicely == 0)
		{
			setSize(8.0F, 30.0F);

		}

		 else 
		{

			setSize(2.6666668F, 10.0F);
		}

		getNavigator().setAvoidsWater(false);
		this.experienceValue = 25000;
		this.fireResistance = 120;
		this.isImmuneToFire = true;
		this.TargetSorter = new GenericTargetSorter(this);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mygetMaxHealth() * 50D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3700000047683716D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(OreSpawnMain.Kraken_stats.attack * 20D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
	}

	public int getTotalArmorValue()
	{
		return 24;
	}

	protected boolean isAIEnabled()
	{
		return true;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		this.noClip = true;
		if (OreSpawnMain.PlayNicely == 0)
		{
			setSize(8.0F, 30.0F);

		}

		 else 
		{

			setSize(2.6666668F, 10.0F);
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

	public void onUpdate()
	{
		super.onUpdate();
		if (this.isDead)
		{
			return;
		}

		if (this.currentFlightTarget == null)
		{
			this.currentFlightTarget = new ChunkCoordinates((int)this.posX, (int)(this.posY - 10.0D), (int)this.posZ);

		}

		 else if (this.posY < this.currentFlightTarget.posY)
		{
			this.motionY *= 0.9D;

		}

		 else 
		{

			this.motionY *= 0.5D;
		}

		if ((this.weather_set > 0) && (OreSpawnMain.PlayNicely == 0))
		{
			this.weather_set -= 1;
			if ((this.weather_set == 0) && (!this.worldObj.isRemote))
			{
				WorldInfo worldinfo = this.worldObj.getWorldInfo();
				if (!this.worldObj.isRaining())
				{
					worldinfo.setRainTime(300);
					worldinfo.setThunderTime(300);
					worldinfo.setRaining(true);
					worldinfo.setThundering(true);
				}

				else
				{
					worldinfo.setRainTime(300);
					worldinfo.setThunderTime(300);
				}

				this.weather_set = 100;
			}
		}
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("LongEnough", this.long_enough);
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		this.long_enough = par1NBTTagCompound.getInteger("LongEnough");
	}

	protected String getLivingSound()
	{
		return "orespawn:kraken_living";
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
		return 5F;
	}

	protected float getSoundPitch()
	{
		return 0.95F;
	}

	private ItemStack dropItemRand(Item index, int par1)
	{
		EntityItem var3 = null;
		ItemStack is = new ItemStack(index, par1, 0);
		var3 = new EntityItem(this.worldObj, this.posX + OreSpawnMain.OreSpawnRand.nextInt(12) - OreSpawnMain.OreSpawnRand.nextInt(12), this.posY + 2.0D, this.posZ + OreSpawnMain.OreSpawnRand.nextInt(12) - OreSpawnMain.OreSpawnRand.nextInt(12), is);
		if (var3 != null)
		{
			this.worldObj.spawnEntityInWorld(var3);
		}

		return is;
	}

	protected void dropFewItems(boolean par1, int par2)
	{
		ItemStack is = null;
		dropItemRand(Item.getItemFromBlock(TitanBlocks.void_block), 1);
		int var5 = 400 + this.worldObj.rand.nextInt(400);
		for (int var4 = 0; var4 < var5; var4++)
		{
			dropItemRand(Items.dye, 1);
		}

		this.worldObj.rand.nextInt(200);
		for (int var4 = 0; var4 < var5; var4++)
		{
			dropItemRand(Items.gold_ingot, 1);
		}

		this.worldObj.rand.nextInt(100);
		for (int var4 = 0; var4 < var5; var4++)
		{
			dropItemRand(Items.golden_carrot, 1);
		}

		this.worldObj.rand.nextInt(100);
		for (int var4 = 0; var4 < var5; var4++)
		{
			dropItemRand(Items.golden_apple, 1);
		}

		int i = 25 + this.worldObj.rand.nextInt(25);
		for (int var4 = 0; var4 < i; var4++)
		{
			int var3 = this.worldObj.rand.nextInt(53);
			switch (var3)
			{
				case 0:is = dropItemRand(TitanItems.voidAxe, 1);
				break;
				case 1:is = dropItemRand(TitanItems.harcadiumNugget, 1);
				break;
				case 2:is = dropItemRand(Item.getItemFromBlock(TitanBlocks.harcadium_block), 1);
				break;
				case 3:is = dropItemRand(TitanItems.harcadiumSword, 1);
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
				case 4:is = dropItemRand(TitanItems.harcadiumSpade, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 5:is = dropItemRand(TitanItems.harcadiumPickaxe, 1);
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
				case 6:is = dropItemRand(TitanItems.harcadiumAxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 7:is = dropItemRand(TitanItems.harcadiumHoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
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
				break;
				case 13:is = dropItemRand(TitanItems.diamondApple, 1);
				break;
				case 14:is = dropItemRand(Items.diamond, 1);
				break;
				case 15:is = dropItemRand(TitanItems.voidPickaxe, 1);
				break;
				case 16:is = dropItemRand(TitanItems.steelSword, 1);
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
				case 17:is = dropItemRand(TitanItems.steelSpade, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 18:is = dropItemRand(TitanItems.steelPickaxe, 1);
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
				case 19:is = dropItemRand(TitanItems.steelAxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 20:is = dropItemRand(TitanItems.steelHoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 21:is = dropItemRand(TitanItems.steelHelmet, 1);
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
				case 22:is = dropItemRand(TitanItems.steelChestplate, 1);
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
				case 23:is = dropItemRand(TitanItems.steelLeggings, 1);
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
				case 24:is = dropItemRand(TitanItems.steelBoots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 25:is = dropItemRand(TitanItems.voidSpade, 1);
				break;
				case 26:dropItemRand(Item.getItemFromBlock(TitanBlocks.harcadium_ore_end_stone), 1);
				break;
				case 27:is = dropItemRand(TitanItems.pleasantBladeSeed, 1);
				break;
				case 28:is = dropItemRand(TitanItems.malgrumSeeds, 1);
				break;
				case 29:is = dropItemRand(TitanItems.diamondCookie, 1);
				break;
				case 30:is = dropItemRand(TitanItems.bronzeSword, 1);
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
				case 31:is = dropItemRand(TitanItems.bronzeSpade, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 32:is = dropItemRand(TitanItems.bronzePickaxe, 1);
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
				case 33:is = dropItemRand(TitanItems.bronzeAxe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 34:is = dropItemRand(TitanItems.bronzeHoe, 1);
				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.efficiency, 1 + this.worldObj.rand.nextInt(5));
				}

				break;
				case 35:is = dropItemRand(TitanItems.bronzeHelmet, 1);
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
				case 36:is = dropItemRand(TitanItems.bronzeChestplate, 1);
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
				case 37:is = dropItemRand(TitanItems.bronzeLeggings, 1);
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
				case 38:is = dropItemRand(TitanItems.bronzeBoots, 1);
				if (this.worldObj.rand.nextInt(6) == 1)
				{
					is.addEnchantment(Enchantment.featherFalling, 5 + this.worldObj.rand.nextInt(5));
				}

				if (this.worldObj.rand.nextInt(2) == 1)
				{
					is.addEnchantment(Enchantment.unbreaking, 2 + this.worldObj.rand.nextInt(4));
				}

				break;
				case 39:dropItemRand(TitanItems.diamondPumpkinPie, 1);
				break;
				case 40:dropItemRand(Item.getItemFromBlock(TitanBlocks.harcadium_block), 1);
				break;
				case 41:EntityItem var33 = null;
				is = new ItemStack(TitanItems.diamondApple, 1, 1);
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
				case 52:is = dropItemRand(Item.getItemFromBlock(Blocks.diamond_block), 1);
			}
		}
	}

	protected boolean canDespawn()
	{
		return false;
	}

	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if ((par1Entity != null) && ((par1Entity instanceof EntityLivingBase)))
		{
			float s = par1Entity.height * par1Entity.width;
			if ((s > 30.0F) &&(!MyUtils.isRoyalty(par1Entity)) && (!(par1Entity instanceof Godzilla)) && (!(par1Entity instanceof GodzillaHead)) && (!(par1Entity instanceof PitchBlack)) && (!(par1Entity instanceof Kraken)))
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
		}

		if ((par1Entity != null) && ((par1Entity instanceof EntityDragon)))
		{
			EntityDragon dr = (EntityDragon)par1Entity;
			DamageSource var21 = null;
			var21 = DamageSource.setExplosionSource(null);
			var21.setExplosion();
			if (this.worldObj.rand.nextInt(6) == 1)
			{
				dr.attackEntityFromPart(dr.dragonPartHead, var21, (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());

			}

			 else 
			{

				dr.attackEntityFromPart(dr.dragonPartBody, var21, (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
			}
		}

		boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
		if (var4)
		{
			double ks = 3.3D;
			double inair = 0.25D;
			float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
			inair += this.worldObj.rand.nextFloat() * 0.25F;
			if ((par1Entity.isDead) || ((par1Entity instanceof EntityPlayer)))
			{
				inair *= 1.5D;
			}

			par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
		}

		return var4;
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
			if ((var4 != null) &&(var4 != this) &&(var4.isEntityAlive()) &&(!MyUtils.isRoyalty(var4)) &&(!(var4 instanceof Ghost)) &&(!(var4 instanceof GhostSkelly)))
			{
				DamageSource var21 = null;
				var21 = DamageSource.setExplosionSource(null);
				var21.setExplosion();
				var4.attackEntityFrom(var21, (float)damage / 2.0F);
				var4.attackEntityFrom(DamageSource.fall, (float)damage / 2.0F);
				this.worldObj.playSoundAtEntity(var4, "random.explode", 1F, 0.5F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.25F);
				if (knock != 0)
				{
					double ks = 2.75D;
					double inair = 0.65D;
					float f3 = (float)Math.atan2(var4.posZ - this.posZ, var4.posX - this.posX);
					var4.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
				}
			}
		}

		return null;
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
		Field hurt_timer = ReflectionHelper.findField(Kraken.class, new String[] 
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

		int xdir = 1;
		int zdir = 1;
		int keep_trying = 50;
		if (this.isDead)
		{
			return;
		}

		if (this.ticksExisted % 50 == 0)
		{
			this.stream_count_l = 64;
		}

		if (this.ticksExisted % 80 == 0)
		{
			this.stream_count_i = 8;
		}

		if (this.long_enough > 0)
		{
			this.long_enough -= 1;
		}

		EntityLivingBase e = null;
		this.entityToAttack = null;
		if (this.worldObj.rand.nextInt(2) == 0)
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
			if (e != null && !this.worldObj.isRemote)
			{
				faceEntity(e, 10.0F, 10.0F);
				if (getDistanceSqToEntity(e) < 600.0F + (e.width / 2.0F) * (e.width / 2.0F))
				{
					setAttacking(1);
					if ((this.worldObj.rand.nextInt(2) == 0))
					{
						attackEntityAsMob(e);
						if (this.worldObj.rand.nextInt(2) == 1)
						{
							doJumpDamage(this.posX, this.posY, this.posZ, 15.0D, OreSpawnMain.Kraken_stats.attack, 0);
						}
					}
				}

				else
				{
					if ((this.worldObj.rand.nextInt(65) == 1))
					{
						float var2 = 100.0F;
						e.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
						e.setFire(5);
						this.worldObj.playSoundAtEntity(e, "random.explode", 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
						if (!this.worldObj.isRemote)
						{
							this.worldObj.createExplosion(this, e.posX, e.posY, e.posZ, 3.0F, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
						}

						this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, e.posX, e.posY + 1.0D, e.posZ));
						this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX, this.posY - 30.0D, this.posZ));
					}

					if (this.stream_count_i > 0 && caught == null)
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

					if (this.stream_count_l > 0 && caught == null)
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

				if (this.worldObj.rand.nextInt(55) == 1 || this.worldObj.rand.nextInt(20) == 1)
				{
					newent = (EntityCreature)spawnCreature(this.worldObj, "The Kraken", (this.posX + e.posX) / 2.0D + this.worldObj.rand.nextInt(5) - this.worldObj.rand.nextInt(5), (this.posY + e.posY) / 2.0D + 1.01D, (this.posZ + e.posZ) / 2.0D + this.worldObj.rand.nextInt(5) - this.worldObj.rand.nextInt(5));
					newent.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2000D);
					newent.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(80D);
					newent.heal(2000);
					newent.setAttackTarget(e);
				}
			}

			else
			{
				setAttacking(0);
			}
		}

		if ((this.worldObj.rand.nextInt(35) == 1))
		heal(5.0F);
		this.dataWatcher.updateObject(21, Integer.valueOf(OreSpawnMain.PlayNicely));
		if ((this.worldObj.rand.nextInt(100) == 1) && (OreSpawnMain.PlayNicely == 0))
		{
			this.worldObj.addWeatherEffect(new EntityGammaLightning(this.worldObj, this.posX, this.posY - 30D, this.posZ, 0F, 0.75F, 1.0F));
		}

		if (this.currentFlightTarget == null)
		{
			this.currentFlightTarget = new ChunkCoordinates((int)this.posX, (int)this.posY, (int)this.posZ);
		}

		if ((this.newtarget != 0) || (this.rand.nextInt(250) == 1) || (this.currentFlightTarget.getDistanceSquared((int)this.posX, (int)this.posY, (int)this.posZ) < 9.1F))
		{
			int ground_dist = 0;
			this.newtarget = 0;
			for (ground_dist = 0; ground_dist < 31; ground_dist++)
			{
				Block bid = this.worldObj.getBlock((int)this.posX, (int)this.posY - ground_dist, (int)this.posZ);
				if (bid != Blocks.air)
				{
					this.straight_down = 0;
					break;
				}
			}

			ground_dist = 30 - ground_dist;
			Block bid = Blocks.stone;
			while ((bid != Blocks.air) && (keep_trying != 0))
			{
				zdir = this.worldObj.rand.nextInt(6) + 12;
				xdir = this.worldObj.rand.nextInt(6) + 12;
				if (this.worldObj.rand.nextInt(2) == 0)
				{
					zdir = -zdir;
				}

				if (this.worldObj.rand.nextInt(2) == 0)
				{
					xdir = -xdir;
				}

				if (this.straight_down != 0)
				{
					zdir = xdir = 0;
				}

				this.currentFlightTarget.set((int)this.posX + xdir, (int)this.posY + ground_dist + this.rand.nextInt(4) - 3, (int)this.posZ + zdir);
				bid = this.worldObj.getBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ);
				if ((bid == Blocks.air) &&(!canSeeTarget(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ)))
				{
					bid = Blocks.stone;
				}

				keep_trying--;
			}

			if ((this.long_enough <= 0) || ((this.posY < 200.0D) && (getHealth() < mygetMaxHealth() / 4)))
			{
				this.currentFlightTarget.set(this.currentFlightTarget.posX, this.currentFlightTarget.posY + 10, this.currentFlightTarget.posZ);
				if ((this.hit_by_player == true) && (this.call_reinforcements == 0) && (getHealth() < mygetMaxHealth() / 8) && (this.posY > 130.0D))
				{
					this.call_reinforcements = 1;
					@SuppressWarnings("unused")
					EntityCreature newent;
					for (int i = 0; i < 50; i++)
					{
						newent = (EntityCreature)spawnCreature(this.worldObj, "The Kraken", this.posX + this.worldObj.rand.nextInt(100) - this.worldObj.rand.nextInt(100), 170.0D, this.posZ + this.worldObj.rand.nextInt(100) - this.worldObj.rand.nextInt(100));
					}
				}
			}
		}

		else if ((this.caught == null) && (this.worldObj.rand.nextInt(4) == 1) && (OreSpawnMain.PlayNicely == 0))
		{
			EntityPlayer target = null;
			target = (EntityPlayer)this.worldObj.findNearestEntityWithinAABB(EntityPlayer.class, this.boundingBox.expand(80D, 80D, 80D), this);
			if (target != null)
			{
				if (!target.capabilities.isCreativeMode)
				{
					if (getEntitySenses().canSee(target))
					{
						this.currentFlightTarget.set((int)target.posX, (int)target.posY + 30, (int)target.posZ);
						attackWithSomething(target);
					}
				}

				else 
				{

					target = null;
				}
			}

			if ((target == null) && (this.worldObj.rand.nextInt(2) == 0))
			{
				e = this.getAttackTarget();
				if (e != null)
				{
					this.currentFlightTarget.set((int)e.posX, (int)e.posY + 30, (int)e.posZ);
					attackWithSomething(e);
				}
			}
		}

		if (this.caught != null)
		{
			if (!this.caught.isDead)
			{
				this.currentFlightTarget.set((int)this.posX, 200, (int)this.posZ);
				if (this.posY > 190.0D)
				{
					this.release = 1;
				}

				this.caught.motionX = 0D;
				this.caught.motionZ = 0D;
				this.caught.motionY = 0D;
				this.caught.setPosition(this.posX, this.posY - 30D, this.posZ);
				this.caught.renderYawOffset = this.caught.rotationYaw = this.rotationYaw;
				attackEntityAsMob(this.caught);
				doJumpDamage(this.posX, this.posY, this.posZ, 15.0D, OreSpawnMain.Kraken_stats.attack, 0);
				if ((this.release != 0) || (this.worldObj.rand.nextInt(250) == 1) || (this.getAttackTarget() != null && this.caught != this.getAttackTarget()))
				{
					this.caught = null;
					this.newtarget = 1;
					this.release = 0;
				}
			}

			else
			{
				this.caught = null;
				this.newtarget = 1;
				this.release = 0;
			}
		}

		double var1 = this.currentFlightTarget.posX - this.posX;
		double var3 = this.currentFlightTarget.posY - this.posY;
		double var5 = this.currentFlightTarget.posZ - this.posZ;
		this.motionX += (Math.signum(var1) * 0.5D - this.motionX) * 0.5D;
		this.motionY += (Math.signum(var3) * 0.5D - this.motionY) * 0.5D;
		this.motionZ += (Math.signum(var5) * 0.5D - this.motionZ) * 0.5D;
		float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
		float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
		this.moveForward = 0.4F;
		if (Math.abs(this.motionX) + Math.abs(this.motionZ) < 0.15D)
		{
			var8 = 0.0F;
		}

		this.renderYawOffset = this.rotationYaw += var8 / 5.0F;
		double obstruction_factor = 0.0D;
		double dx = 0.0D;double dz = 0.0D;
		int dist = 10;
		for (int k = -40; k < 36; k += 2)
		{
			for (int i = 1; i < dist; i += 2)
			{
				dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
				dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
				Block bid = this.worldObj.getBlock((int)(this.posX + dx), (int)this.posY + k, (int)(this.posZ + dz));
				if (bid != Blocks.air)
				{
					obstruction_factor += 0.1D;
				}
			}
		}

		this.motionY += obstruction_factor * 0.08D;
		this.posY += obstruction_factor * 0.08D;
		if ((this.posY > 256.0D) && (!isNoDespawnRequired()))
		{
			setDead();
		}
	}

	public boolean isNoDespawnRequired()
	{
		return true;
	}

	private void attackWithSomething(EntityLivingBase par1)
	{
		if (this.caught != null)
		{
			return;
		}

		double dist = (this.posX - par1.posX) * (this.posX - par1.posX);
		dist += (this.posZ - par1.posZ) * (this.posZ - par1.posZ);
		dist += (this.posY - par1.posY - 30.0D) * (this.posY - par1.posY - 30.0D);
		if (dist < 60.0D)
		{
			this.caught = par1;
			this.release = 0;
			setAttacking(1);
		}
	}

	private void firecanonl(EntityLivingBase e)
	{
		double yoff = -12.0D;
		double xzoff = 2.0D;
		double var3 = 0.0D;
		double var5 = 0.0D;
		double var7 = 0.0D;
		float var9 = 0.0F;
		double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
		double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
		if (this.stream_count_l > 0)
		{
			this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
			for (int i = 0; i < 10; i++)
			{
				float r = 8F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
				EntityThunderboltShot lb = new EntityThunderboltShot(this.worldObj, cx, this.posY + yoff, cz);
				lb.setLocationAndAngles(cx + r, this.posY + yoff, cz + r, 0.0F, 0.0F);
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
		double yoff = -12.0D;
		double xzoff = 2.0D;
		double var3 = 0.0D;
		double var5 = 0.0D;
		double var7 = 0.0D;
		float var9 = 0.0F;
		double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
		double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
		if (this.stream_count_i > 0)
		{
			this.worldObj.playSoundAtEntity(this, "random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
			for (int i = 0; i < 3; i++)
			{
				float r = 8F * (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat());
				EntityIceBall lb = new EntityIceBall(this.worldObj, cx, this.posY + yoff, cz);
				lb.setLocationAndAngles(cx + r, this.posY + yoff, cz + r, 0.0F, 0.0F);
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

		if ((par1EntityLiving instanceof EntityPlayer))
		{
			EntityPlayer p = (EntityPlayer)par1EntityLiving;
			if (p.capabilities.isCreativeMode == true)
			{
				return false;
			}

			if (p.capabilities.isFlying == true)
			{
				return false;
			}

			return true;
		}

		if ((par1EntityLiving instanceof EntitySquid))
		{
			return false;
		}

		if ((par1EntityLiving instanceof AttackSquid))
		{
			return false;
		}

		if ((par1EntityLiving instanceof Kraken))
		{
			return false;
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

		List<?> var5 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(80.0D, 160.0D, 80.0D));
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


