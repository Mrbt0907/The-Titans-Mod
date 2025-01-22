package net.mrbt0907.thetitans.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.mrbt0907.thetitans.registries.SoundRegistry;
import net.mrbt0907.thetitans.util.DamageSourceTitan;

public class BaseTitanSword extends BaseSword
{
	public BaseTitanSword(ItemMaterial material)
	{
		super(material.getToolMaterial(), 0.0D);
	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.BOW;
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	private void kill(EntityLivingBase entityLiving, ItemStack stack , double multiplier)
	{
		if (entityLiving.isServerWorld())
		{
			Vec3d vec3 = entityLiving.getLook(1.0F);
			double dx = vec3.x * getRange();
			double dy = entityLiving.getEyeHeight() + vec3.y * getRange();
			double dz = vec3.z * getRange();
			List<Entity> entities = entityLiving.world.getEntitiesWithinAABBExcludingEntity(entityLiving, entityLiving.getEntityBoundingBox().expand(getRange() * multiplier, getRange() * 0.5D * multiplier, getRange() * multiplier).offset(dx, dy, dz));
			DamageSource source = DamageSourceTitan.causeTitanDamage(entityLiving);
				
			for (Entity entity : entities)
			{
				if (!entity.isEntityInvulnerable(source))
				{
					if (entity instanceof EntityTNTPrimed)
					{
						entity.world.newExplosion(entityLiving, entity.posX, entity.posY, entity.posZ, 4.0F, false, false);
						entity.setDead();
						stack.damageItem(1, entityLiving);
					}
					else if (entity instanceof EntityFireball)
					{
						entity.world.newExplosion(entityLiving, entity.posX, entity.posY, entity.posZ, 0.0F, false, false);
						entity.setDead();
						stack.damageItem(1, entityLiving);
					}
					else if (entity instanceof EntityLivingBase && entity.isEntityAlive() && (entityLiving instanceof EntityPlayer || true))
						hitEntity(stack, (EntityLivingBase) entity, entityLiving);
					else if (entity instanceof MultiPartEntityPart)
						entity.attackEntityFrom(DamageSourceTitan.causeTitanDamage(entityLiving), getAttackDamage());
				}
			}
		}
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target != null)
		{
			stack.damageItem(1, target);
			target.attackEntityFrom(DamageSourceTitan.causeTitanDamage(attacker), getAttackDamage());
		}

		return true;
	}

	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (entityLiving.world.isRemote)
		entityLiving.playSound(SoundRegistry.get("titan.swing"), 10.0F, 0.75F);
		kill(entityLiving, stack, 1.0F);
		return false;
	}

	protected double getRange()
	{
		return 7.0D;
	}
}
