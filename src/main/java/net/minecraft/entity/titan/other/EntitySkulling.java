package net.minecraft.entity.titan.other;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class EntitySkulling extends EntityMob
{
	private int hideTimer;
	public EntitySkulling(World p_i1743_1_)
	{
		super(p_i1743_1_);
		this.setSize(0.6F, 0.6F);
	}

	@SideOnly(Side.CLIENT)
	public int getHideTimer()
	{
		return this.hideTimer;
	}

	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte p_70103_1_)
	{
		if (p_70103_1_ == 4)
		{
			this.hideTimer = 100;
			this.playSound("random.pop", 1.0F, 1.0F);
		}

		else
		{
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte)0));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.800000011920929D);
	}

	/**
	* Finds the closest player within 16 blocks to attack, or null if this Entity isn't interested in attacking
	* (Animals, Spiders at day, peaceful PigZombies).
	*/
	protected Entity findPlayerToAttack()
	{
		return this.worldObj.getClosestVulnerablePlayerToEntity(this, 24D);
	}

	/**
	* Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	* setBesideClimableBlock.
	*/
	public boolean isSheilded()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	/**
	* Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	* false.
	*/
	public void setSheilded(boolean p_70839_1_)
	{
		byte b0 = this.dataWatcher.getWatchableObjectByte(16);
		if (p_70839_1_)
		{
			b0 = (byte)(b0 | 1);
		}

		else
		{
			b0 &= -2;
		}

		this.dataWatcher.updateObject(16, Byte.valueOf(b0));
	}

	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}

		else if (super.attackEntityFrom(p_70097_1_, p_70097_2_))
		{
			Entity entity = p_70097_1_.getEntity();
			if (entity != null)
			{
				if ((rand.nextInt(4) == 0 || p_70097_2_ > 10F) && !this.isSheilded())
				{
					this.worldObj.setEntityState(this, (byte)4);
					this.hideTimer = 100;
					this.playSound("random.pop", 1.0F, 1.0F);
				}

				if (this.isSheilded())
				p_70097_2_ *= 0.2F;
			}

			return true;
		}

		else
		{
			return false;
		}
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if (this.isSheilded())
		{
			this.motionX *= 0.25D;
			this.motionZ *= 0.25D;
		}

		if (this.hideTimer > 0)
		{
			--this.hideTimer;
		}

		this.setSheilded(this.hideTimer > 0);
	}

	/**
	* Returns the sound this mob makes while it's alive.
	*/
	protected String getLivingSound()
	{
		return "mob.spider.say";
	}

	/**
	* Returns the sound this mob makes when it is hurt.
	*/
	protected String getHurtSound()
	{
		return "mob.spider.say";
	}

	/**
	* Returns the sound this mob makes on death.
	*/
	protected String getDeathSound()
	{
		return "mob.spider.death";
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		this.playSound("mob.spider.step", 0.15F, 1.25F);
	}

	/**
	* Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
	*/
	protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
	{
		if (p_70785_2_ > 2.0F && p_70785_2_ < 6.0F && this.rand.nextInt(10) == 0 && !this.isSheilded())
		{
			if (this.onGround)
			{
				double d0 = p_70785_1_.posX - this.posX;
				double d1 = p_70785_1_.posZ - this.posZ;
				float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
				this.motionX = d0 / (double)f2 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
				this.motionZ = d1 / (double)f2 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
				this.motionY = 0.4000000059604645D;
			}
		}

		else
		{
			super.attackEntity(p_70785_1_, p_70785_2_);
		}
	}

	protected Item getDropItem()
	{
		return Item.getItemFromBlock(Blocks.sand);
	}

	/**
	* Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
	* par2 - Level of Looting used to kill this mob.
	*/
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		super.dropFewItems(p_70628_1_, p_70628_2_);
		if (p_70628_1_ && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
		{
			this.dropItem(Items.spider_eye, 1);
		}
	}

	/**
	* Sets the Entity inside a web block.
	*/
	public void setInWeb() 
	{
		 
	}


	/**
	* Get this Entity's EnumCreatureAttribute
	*/
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}

	public boolean isPotionApplicable(PotionEffect p_70687_1_)
	{
		return p_70687_1_.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(p_70687_1_);
	}
}


