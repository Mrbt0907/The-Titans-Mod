package net.minecraft.theTitans.items;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class ItemTitanSword extends ItemNormalSword
{
	protected int useTicks;
	protected int serverTicks;
	@SideOnly(Side.CLIENT)
	protected int clientTicks;
	private int uses;
	protected ToolMaterial material;
	public ItemTitanSword(String unlocalizedName, Item.ToolMaterial material)
	{
		super(unlocalizedName, material);
		this.material = material;
		uses = 0;
	}

	@SuppressWarnings(
	{
		 "unchecked", "rawtypes" 
	}
	)
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_)
	{
		super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
		if (p_77624_1_.getItem() instanceof ItemTitanSword)
		{
			p_77624_3_.add(EnumChatFormatting.BLUE + "+" + String.format("%.0f Titan Damage", ((ItemTitanSword)p_77624_1_.getItem()).getTitanDamage()));
		}
	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.bow;
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (worldIn.isRemote)
		clientTicks++;
		else
		serverTicks++;
		if (entityIn != null)
		{
			if (uses > 0)
			{
				net.mrbt0907.utils.Maths.Vec3 vector = new net.mrbt0907.utils.Maths.Vec3(entityIn.motionX, entityIn.motionY, entityIn.motionZ);
				if (worldIn.isRemote && clientTicks % 2 == 0)
				for (int i = 0; i < 3; i++)
				worldIn.spawnParticle("largesmoke", entityIn.posX + Maths.random(-0.05D, 0.05D), entityIn.posY + Maths.random(-0.05D, 0.05D), entityIn.posZ + Maths.random(-0.05D, 0.05D), -(vector.vecX * 0.25), -(vector.vecY * 0.25), -(vector.vecZ * 0.25));
				if (entityIn.isInWater() || entityIn.onGround)
				uses = 0;
				if (entityIn instanceof EntityLivingBase && (serverTicks % 3 == 0 || uses == 0))
				killRight((EntityLivingBase)entityIn, stack);
			}
		}

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		if (player == null || player.isDead)
		return;
		useTicks ++;
	}

	private void kill(EntityLivingBase entityLiving, ItemStack stack , double multiplier)
	{
		if (!entityLiving.worldObj.isRemote)
		{
			Vec3 vec3 = entityLiving.getLook(1.0F);
			double dx = vec3.xCoord * getRange() * 0.95;
			double dy = entityLiving.getEyeHeight() + vec3.yCoord * getRange() * 0.95;
			double dz = vec3.zCoord * getRange() * 0.95;
			List<?> list1 = entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(entityLiving, entityLiving.boundingBox.expand(getRange() * multiplier, getRange() * 0.5D * multiplier, getRange() * multiplier).offset(dx, dy, dz));
			if ((list1 != null) && (!list1.isEmpty()))
			{
				stack.damageItem(1, entityLiving);
				for (int i11 = 0; i11 < list1.size(); i11++)
				{
					Entity entity1 = (Entity)list1.get(i11);
					if (((entity1 instanceof EntityTNTPrimed)) && (!entity1.isEntityInvulnerable()))
					{
						entity1.worldObj.newExplosion(entityLiving, entity1.posX, entity1.posY, entity1.posZ, 4.0F, false, false);
						entity1.setDead();
					}

					else if (((entity1 instanceof EntityFireball)) && (!entity1.isEntityInvulnerable()))
					{
						entity1.worldObj.newExplosion(entityLiving, entity1.posX, entity1.posY, entity1.posZ, 0.0F, false, false);
						entity1.setDead();
					}

					else if (entity1 instanceof EntityLivingBase && entity1.isEntityAlive() && entity1 != entityLiving && (entityLiving instanceof EntityPlayer && !TheTitans.checkFriendlyFire((EntityPlayer)entityLiving, entity1, true)))
					hitEntity(stack, (EntityLivingBase) entity1, entityLiving);
				}
			}
		}
	}

	private void killRight(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (!entityLiving.worldObj.isRemote)
		{
			net.mrbt0907.utils.Maths.Vec3 vector = new net.mrbt0907.utils.Maths.Vec3(entityLiving.motionX, entityLiving.motionY, entityLiving.motionZ);
			if (vector.speed() / this.getDashSpeed() > 0.35D)
			kill(entityLiving, stack, 0.35D);
			serverTicks = 0;
		}

		else
		clientTicks = 0;
	}

	private void killLeft(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (!entityLiving.worldObj.isRemote)
		{
			kill(entityLiving, stack, 1.0D);
			serverTicks = 0;
		}

		else
		clientTicks = 0;
		useTicks = 0;
	}

	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_)
	{
		if (uses < getMaxUses())
		{
			Vec3 vector = p_77615_3_.getLook(1.0F);
			float usePerc = (1.0F - (0.75F * MathHelper.clamp_float((float)(p_77615_4_ - (getMaxItemUseDuration(p_77615_1_) - getUseTime())), 0.0F, 1.0F)));
			uses ++;
			if (p_77615_3_.isSneaking())
			{
				p_77615_3_.motionX = -getDashSpeed() * vector.xCoord * usePerc * 0.75D;
				p_77615_3_.motionY = -getDashSpeed() * vector.yCoord * usePerc * 0.75D;
				p_77615_3_.motionZ = -getDashSpeed() * vector.zCoord * usePerc * 0.75D;
			}

			else
			{
				p_77615_3_.motionX = getDashSpeed() * vector.xCoord * usePerc;
				p_77615_3_.motionY = getDashSpeed() * vector.yCoord * usePerc;
				p_77615_3_.motionZ = getDashSpeed() * vector.zCoord * usePerc;
			}

			if (p_77615_2_.isRemote)
			p_77615_3_.playSound("thetitans:titanSwing", 10.0F, 0.75F + (0.25F * Math.min(usePerc, 1.0F)));
		}
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target != null)
		{
			net.mrbt0907.utils.Maths.Vec3 vector = new net.mrbt0907.utils.Maths.Vec3(attacker.motionX, attacker.motionY, attacker.motionZ);
			float perc = MathHelper.clamp_float((float)(vector.speed()) / (float)getDashSpeed(), 1.0F, 2.0F);
			if (target.hurtResistantTime > getHurtTime() * 2)
			target.hurtResistantTime = getHurtTime() * 2;
			if (target instanceof EntityTitan || target.height >= 6.0F || !target.onGround)
			target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), (getTitanDamage() * (0.5F + (0.5F * Math.min((float)useTicks / (float)getUseTime(), 1.0F)))) * perc);
			else
			target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), getDamage() * perc);
		}

		return true;
	}

	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (entityLiving.worldObj.isRemote)
		entityLiving.playSound("thetitans:titanSwing", 10.0F, 0.75F + (0.25F * Math.min((float)clientTicks / (float)getUseTime(), 1.0F)));
		killLeft(entityLiving, stack);
		return false;
	}

	protected double getRange()
	{
		return 7.0D;
	}

	protected double getDashSpeed()
	{
		return 4.0D;
	}

	protected int getUseTime()
	{
		return 20;
	}

	protected int getHurtTime()
	{
		return 20;
	}

	public float getDamage()
	{
		return material.getDamageVsEntity();
	}

	protected int getMaxUses()
	{
		return 2;
	}

	public float getTitanDamage()
	{
		return ((getDamage() > 500.0F) ? getDamage() * Math.min((float)serverTicks / (float)getUseTime(), 1.0F) * 0.1F : 0.0F);
	}
}


