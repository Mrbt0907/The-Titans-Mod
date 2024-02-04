package net.minecraft.entity.titan.other;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.titan.EntityIronGolemTitan;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class EntityVoidGolem extends EntityIronGolem
{
	public EntityVoidGolem(World p_i1694_1_)
	{
		super(p_i1694_1_);
		EntityIronGolemTitan.addTitanTargetingTaskToEntity(this);
		this.isImmuneToFire = true;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100000.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
	}

	public int getTotalArmorValue()
	{
		return 0;
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
		if (p_82167_1_ instanceof IMob)
		{
			this.setAttackTarget((EntityLivingBase)p_82167_1_);
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
		if (this.motionY > 1D)
		this.motionY = 1D;
		if (this.motionX > 1D)
		this.motionX = 1D;
		if (this.motionZ > 1D)
		this.motionZ = 1D;
		if (this.motionX < -1D)
		this.motionX = -1D;
		if (this.motionZ < -1D)
		this.motionZ = -1D;
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

		if (this.getAttackTarget() != null)
		{
			double d0 = this.getDistanceToEntity(this.getAttackTarget());
			double d1 = (double)(this.width + this.getAttackTarget().width + 3D);
			if (d0 <= d1)
			this.attackEntityAsMob(this.getAttackTarget());
		}
	}

	public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		this.worldObj.setEntityState(this, (byte)4);
		float damage = 500 + this.rand.nextInt(2000);
		if (p_70652_1_ instanceof EntityTitan)
		damage *= 5F;
		boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
		if (flag)
		{
			p_70652_1_.motionY += 0.8D;
		}

		this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
		return flag;
	}

	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
	{
		this.playSound("mob.irongolem.walk", 1.0F, 0.9F);
	}

	/**
	* Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
	* par2 - Level of Looting used to kill this mob.
	*/
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int j = this.rand.nextInt(3);
		int k;
		for (k = 0; k < j; ++k)
		{
			this.func_145778_a(Item.getItemFromBlock(Blocks.red_flower), 1, 0.0F);
		}

		k = 6 + this.rand.nextInt(6);
		for (int l = 0; l < k; ++l)
		{
			this.dropItem(TitanItems.voidItem, 1);
		}
	}
}


