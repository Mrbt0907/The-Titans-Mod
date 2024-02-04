package net.minecraft.entity.titan;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
public class EntityTitanPart extends Entity
{
	private ITitanHitbox entity;
	private String partName;
	private boolean shouldCrush;
	public EntityTitanPart(World world)
	{
		super(world);
		preventEntitySpawning = true;
		noClip = true;
	}

	public EntityTitanPart(World world, ITitanHitbox entity, String partName, float width, float height)
	{
		this(world);
		setSize(width, height);
		this.entity = entity;
		this.partName = partName;
		if (entity instanceof Entity)
		setPosition(((Entity)entity).posX, ((Entity)entity).posY, ((Entity)entity).posZ);
	}

	public EntityTitanPart(World world, ITitanHitbox entity, String partName, float width, float height, boolean shouldCrush)
	{
		this(world, entity, partName, width, height);
		this.shouldCrush = shouldCrush;
	}

	/**Returns the parent entity that this hitbox is apart of*/
	public ITitanHitbox getParent()
	{
		return entity;
	}

	/**Returns if the hitbox should crush other entities that collide with it*/
	public boolean shouldCrush()
	{
		return shouldCrush;
	}

	public String getCommandSenderName()
	{
		return partName;
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public float getRenderSizeModifier()
	{
		return width;
	}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return entity != null && entity instanceof EntityLivingBase && posY > ((EntityLivingBase)entity).posY ? (float)(((EntityLivingBase)entity).posY - posY) : 0.0F;
	}

	public boolean isBurning()
	{
		return entity != null && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isBurning();
	}

	protected void entityInit() 
	{

	}


	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) 
	{

	}


	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) 
	{

	}


	public boolean canBeCollidedWith()
	{
		return getBoundingBox() != null;
	}

	public boolean isEntityInvulnerable()
	{
		return isInvisible() || super.isEntityInvulnerable();
	}

	public boolean attackEntityFrom(DamageSource source, float damage)
	{
		if (isEntityInvulnerable() || entity == null)
		return false;
		return entity.attackEntityFromPart(this, source, damage);
	}

	public AxisAlignedBB getCollisionBox(Entity entityIn)
	{
		return getBoundingBox();
	}

	public AxisAlignedBB getBoundingBox()
	{
		return isInvisible() || entity == null ? null : boundingBox;
	}

	public void setLocationAndAngles(double p_70012_1_, double p_70012_3_, double p_70012_5_, float p_70012_7_, float p_70012_8_)
	{
		if (entity != null && entity instanceof EntityLivingBase)
		{
			p_70012_1_ += ((EntityLivingBase)entity).motionX;
			p_70012_5_ += ((EntityLivingBase)entity).motionZ;
		}

		super.setLocationAndAngles(p_70012_1_, p_70012_3_, p_70012_5_, p_70012_7_, p_70012_8_);
	}

	public void onUpdate()
	{
		if (entity != null && entity instanceof EntityLivingBase)
		{
			rotationYaw = ((EntityLivingBase)entity).renderYawOffset;
			if (entity instanceof EntityTitan)
			setInvisible(((EntityTitan)entity).getWaiting() || ((EntityTitan)entity).getAnimID() == 13);
			else
			setInvisible(((EntityLivingBase)entity).isInvisible());
		}

		if (entity == null || worldObj == null || (entity instanceof EntityLivingBase && (!((EntityLivingBase)entity).isEntityAlive()) || ((EntityLivingBase)entity).isDead))
		setDead();
		super.onUpdate();
	}
}


