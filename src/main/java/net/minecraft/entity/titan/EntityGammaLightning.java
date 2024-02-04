package net.minecraft.entity.titan;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanPart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class EntityGammaLightning extends EntityLightningBolt
{
	/** Declares which state the lightning bolt is in. Whether it's in the air, hit the ground, etc. */
	private int lightningState;
	/** A random long that is used to change the vertex of the lightning rendered in RenderLightningBolt */
	public long boltVertex;
	/** Determines the time before the EntityLightningBolt is destroyed. It is a random integer decremented over time. */
	private int boltLivingTime;
	public EntityGammaLightning(World p_i1703_1_, float red, float green, float blue)
	{
		super(p_i1703_1_, red, green, blue);
		this.setSize(3F, 3F);
		this.setRed(red);
		this.setGreen(green);
		this.setBlue(blue);
		this.lightningState = 2;
		this.boltVertex = this.rand.nextLong();
		this.boltLivingTime = this.rand.nextInt(3) + 1;
	}

	public EntityGammaLightning(World p_i1703_1_, double p_i1703_2_, double p_i1703_4_, double p_i1703_6_, float red, float green, float blue)
	{
		this(p_i1703_1_, red, green, blue);
		this.setPosition(p_i1703_2_, p_i1703_4_, p_i1703_6_);
	}

	public void setPosition(double p_70107_1_, double p_70107_3_, double p_70107_5_)
	{
		super.setPosition(p_70107_1_, p_70107_3_, p_70107_5_);
		if (worldObj.isRemote)
		{
			this.serverPosX = (int) p_70107_1_;
			this.serverPosY = (int) p_70107_3_;
			this.serverPosZ = (int) p_70107_5_;
		}
	}

	/**
	* Called to update the entity's position/logic.
	*/
	public void onUpdate()
	{
		if (worldObj.isRemote)
		{
			this.serverPosX = (int) posX;
			this.serverPosY = (int) posY;
			this.serverPosZ = (int) posZ;
		}

		if (this.lightningState == 2)
		{
			float volume = 0.5F + this.rand.nextFloat();
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume + 0.1F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume + 0.2F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume + 0.3F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 10F, 0.7F);
		}

		--this.lightningState;
		if (this.lightningState < 0)
		{
			if (this.boltLivingTime == 0)
			{
				if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("doFireTick") && this.worldObj.doChunksNearChunkExist(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 10))
				{
					int i = MathHelper.floor_double(this.posX);
					int j = MathHelper.floor_double(this.posY);
					int k = MathHelper.floor_double(this.posZ);
					if (this.worldObj.getBlock(i, j, k).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(this.worldObj, i, j, k))
					{
						this.worldObj.setBlock(i, j, k, Blocks.fire);
					}

					for (i = 0; i < 16; ++i)
					{
						j = MathHelper.floor_double(this.posX) + this.rand.nextInt(6) - 3;
						k = MathHelper.floor_double(this.posY) + this.rand.nextInt(6) - 3;
						int l = MathHelper.floor_double(this.posZ) + this.rand.nextInt(6) - 3;
						if (this.worldObj.getBlock(j, k, l).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(this.worldObj, j, k, l))
						{
							this.worldObj.setBlock(j, k, l, Blocks.fire);
						}
					}
				}

				this.setDead();
			}

			else if (this.lightningState < -this.rand.nextInt(10))
			{
				--this.boltLivingTime;
				this.lightningState = 1;
				this.boltVertex = this.rand.nextLong();
				if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("doFireTick") && this.worldObj.doChunksNearChunkExist(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 10))
				{
					int i = MathHelper.floor_double(this.posX);
					int j = MathHelper.floor_double(this.posY);
					int k = MathHelper.floor_double(this.posZ);
					if (this.worldObj.getBlock(i, j, k).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(this.worldObj, i, j, k))
					{
						this.worldObj.setBlock(i, j, k, Blocks.fire);
					}
				}
			}
		}

		if (this.lightningState >= 0)
		{
			this.worldObj.lastLightningBolt = 2;
			double d0 = 10D;
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + d0, this.posZ + d0));
			for (int l = 0; l < list.size(); ++l)
			{
				Entity entity = (Entity)list.get(l);
				if (entity != null && !(entity instanceof EntityTitanPart) && !(entity instanceof EntityTitan) && !entity.isImmuneToFire())
				{
					if (!(entity instanceof EntityPlayer) || (entity instanceof EntityPlayer && !((EntityPlayer)entity).inventory.hasItem(TitanItems.optimaAxe) && !((EntityPlayer)entity).inventory.hasItem(TitanItems.ultimaBlade)))
					{
						entity.onStruckByLightning(null);
						entity.setFire(1000);
						entity.attackEntityFrom(DamageSourceExtra.lightningBolt, 200F);
						entity.hurtResistantTime = 0;
					}

					if (entity instanceof EntityPlayer && (((EntityPlayer)entity).inventory.hasItem(TitanItems.optimaAxe) || ((EntityPlayer)entity).inventory.hasItem(TitanItems.ultimaBlade)) && ((EntityPlayer)entity).isBlocking())
					{
						((EntityPlayer)entity).motionX = 0D;
						((EntityPlayer)entity).motionY = 0D;
						((EntityPlayer)entity).motionZ = 0D;
						((EntityPlayer)entity).posX = ((EntityPlayer)entity).lastTickPosX;
						((EntityPlayer)entity).posY = ((EntityPlayer)entity).lastTickPosY;
						((EntityPlayer)entity).posZ = ((EntityPlayer)entity).lastTickPosZ;
					}
				}

				if (entity != null && entity.getClass() == EntityLightningBolt.class)
				{
					entity.setDead();
				}
			}
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.onEntityUpdate();
	}

	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double p_70112_1_)
	{
		return true;
	}

	protected void entityInit()
	{
		this.dataWatcher.addObject(15, Float.valueOf(0F));
		this.dataWatcher.addObject(16, Float.valueOf(0.56F));
		this.dataWatcher.addObject(17, Float.valueOf(0F));
	}

	public final float getRed()
	{
		return this.dataWatcher.getWatchableObjectFloat(15);
	}

	public void setRed(float p_70606_1_)
	{
		this.dataWatcher.updateObject(15, Float.valueOf(MathHelper.clamp_float(p_70606_1_, 0F, 1F)));
	}

	public final float getGreen()
	{
		return this.dataWatcher.getWatchableObjectFloat(16);
	}

	public void setGreen(float p_70606_1_)
	{
		this.dataWatcher.updateObject(16, Float.valueOf(MathHelper.clamp_float(p_70606_1_, 0F, 1F)));
	}

	public final float getBlue()
	{
		return this.dataWatcher.getWatchableObjectFloat(17);
	}

	public void setBlue(float p_70606_1_)
	{
		this.dataWatcher.updateObject(17, Float.valueOf(MathHelper.clamp_float(p_70606_1_, 0F, 1F)));
	}

	/**
	* (abstract) Protected helper method to read subclass entity data from NBT.
	*/
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		this.setRed(p_70037_1_.getFloat("R"));
		this.setGreen(p_70037_1_.getFloat("G"));
		this.setBlue(p_70037_1_.getFloat("B"));
	}

	/**
	* (abstract) Protected helper method to write subclass entity data to NBT.
	*/
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setFloat("R", this.getRed());
		p_70014_1_.setFloat("G", this.getGreen());
		p_70014_1_.setFloat("B", this.getBlue());
	}
}


