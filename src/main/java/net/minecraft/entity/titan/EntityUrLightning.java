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
public class EntityUrLightning extends EntityLightningBolt
{
	/** Declares which state the lightning bolt is in. Whether it's in the air, hit the ground, etc. */
	private int lightningState;
	/** A random long that is used to change the vertex of the lightning rendered in RenderLightningBolt */
	public long boltVertex;
	/** Determines the time before the EntityLightningBolt is destroyed. It is a random integer decremented over time. */
	private int boltLivingTime;
	public EntityUrLightning(World p_i1703_1_, double p_i1703_2_, double p_i1703_4_, double p_i1703_6_)
	{
		super(p_i1703_1_, p_i1703_2_, p_i1703_4_, p_i1703_6_);
		this.setSize(6F, 6F);
		this.lightningState = 2;
		this.boltVertex = this.rand.nextLong();
		this.boltLivingTime = this.rand.nextInt(3) + 1;
	}

	/**
	* Called to update the entity's position/logic.
	*/
	public void onUpdate()
	{
		if (this.lightningState == 2)
		{
			float volume = 0.5F + this.rand.nextFloat();
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume + 0.1F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume + 0.2F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume + 0.3F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume - 0.1F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume - 0.2F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, volume - 0.3F);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 10F, 0.5F);
		}

		--this.lightningState;
		if (this.lightningState < 0)
		{
			if (this.boltLivingTime == 0)
			{
				if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing") && this.worldObj.doChunksNearChunkExist(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 10))
				{
					int i = MathHelper.floor_double(this.posX);
					int j = MathHelper.floor_double(this.posY);
					int k = MathHelper.floor_double(this.posZ);
					for (i = 0; i < 100; ++i)
					{
						j = MathHelper.floor_double(this.posX) + this.rand.nextInt(10) - 5;
						k = MathHelper.floor_double(this.posY) + this.rand.nextInt(10) - 5;
						int l = MathHelper.floor_double(this.posZ) + this.rand.nextInt(10) - 5;
						if (this.worldObj.getBlock(j, k, l).getMaterial() != Material.air)
						{
							this.worldObj.setBlock(j, k, l, Blocks.air);
						}
					}
				}

				this.setDead();
			}
		}

		if (this.lightningState >= 0)
		{
			this.worldObj.lastLightningBolt = 2;
			double d0 = 20D;
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + d0, this.posZ + d0));
			for (int l = 0; l < list.size(); ++l)
			{
				Entity entity = (Entity)list.get(l);
				if (entity != null && !(entity instanceof EntityTitanPart) && !(entity instanceof EntityTitan) && !(entity instanceof EntityTitanSpirit))
				{
					if (!(entity instanceof EntityPlayer) || (entity instanceof EntityPlayer && !((EntityPlayer)entity).inventory.hasItem(TitanItems.optimaAxe) && !((EntityPlayer)entity).inventory.hasItem(TitanItems.ultimaBlade)))
					{
						entity.setFire(Integer.MAX_VALUE);
						entity.attackEntityFrom(DamageSourceExtra.lightningBolt, 2000F);
						entity.hurtResistantTime = 0;
						if (!(entity instanceof EntityPlayer))
						entity.isDead = true;
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
			}
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		super.onUpdate();
	}

	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double p_70112_1_)
	{
		return true;
	}

	protected void entityInit()
	{
	}

	/**
	* (abstract) Protected helper method to read subclass entity data from NBT.
	*/
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
	}

	/**
	* (abstract) Protected helper method to write subclass entity data to NBT.
	*/
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
	}
}


