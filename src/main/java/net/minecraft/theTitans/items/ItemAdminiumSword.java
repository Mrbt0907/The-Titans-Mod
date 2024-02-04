package net.minecraft.theTitans.items;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockOre;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityWitherzilla;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.EntityImmortalItem;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
public class ItemAdminiumSword extends ItemTitanSword
{
	public ItemAdminiumSword(String unlocalizedName, Item.ToolMaterial material)
	{
		super(unlocalizedName, material);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		if (target != null && !target.worldObj.isRemote)
		{
			++target.motionY;
			target.hurtResistantTime = 0;
			if (target.height >= 6.0F || target instanceof EntityTitan || !target.onGround)
			{
				try
				{

					ReflectionHelper.findField(EntityLivingBase.class, new String[] 
					{
						 "recentlyHit", "field_70718_bc" 
					}
					).setInt(target, 100);
				}

				catch (Exception e)
				{
					target.hurtResistantTime = 0;
				}

				try
				{

					ReflectionHelper.findField(EntityLivingBase.class, new String[] 
					{
						 "hurt_timer" 
					}
					).setInt(target, 0);
					target.hurtResistantTime = 0;
				}

				catch (Exception e)
				{
					target.hurtResistantTime = 0;
				}

				target.setHealth(target.getHealth() - 5000000000.0F);
				target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), 5000000000.0F);
				target.playSound("thetitans:titanpunch", 10.0F, 1.0F);
				if (target.height == 50F && target.width == 15F)
				{
					target.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(0D);
					target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), 40.0F);
					target.handleHealthUpdate((byte)3);
					target.addPotionEffect(new PotionEffect(ClientProxy.death.id, Integer.MAX_VALUE, 19));
					++target.deathTime;
				}

				if ((target instanceof EntityTitan) && ((EntityTitan)target).canBeHurtByPlayer() && !(target instanceof EntityWitherzilla) && ((EntityTitan)target).getInvulTime() < 1)
				{
					target.worldObj.newExplosion(null, target.posX, target.posY + (target.height * 0.5F), target.posZ, 7F, false, false);
					target.playSound("thetitans:titanpunch", 10.0F, 1.0F);
					((EntityTitan)target).hurt(1000.0F);
				}
			}
		}

		super.hitEntity(stack, target, attacker);
		return true;
	}

	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player)
	{
		return true;
	}

	public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean wut, double range)
	{
		float f = 1.0F;
		float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * f;
		if ((!world.isRemote) && ((player instanceof EntityPlayer)))
		{
			d1 += 1.62D;
		}

		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
		Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - 3.1415927F);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - 3.1415927F);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = range;
		Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		return world.rayTraceBlocks(vec3, vec31, wut);
	}

	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		MovingObjectPosition raycast = raytraceFromEntity(entityLiving.worldObj, entityLiving, true, 6D);
		if (!entityLiving.worldObj.isRemote && raycast != null)
		{
			Block block = entityLiving.worldObj.getBlock(raycast.blockX, raycast.blockY, raycast.blockZ);
			if ((block instanceof IGrowable && !(block instanceof BlockGrass)) || block.getMaterial() == Material.circuits || block instanceof BlockOre)
			{
				entityLiving.worldObj.func_147480_a(raycast.blockX, raycast.blockY, raycast.blockZ, true);
			}

			else
			{
				int l = entityLiving.worldObj.getBlockMetadata(raycast.blockX, raycast.blockY, raycast.blockZ);
				entityLiving.worldObj.playAuxSFX(2001, raycast.blockX, raycast.blockY, raycast.blockZ, Block.getIdFromBlock(block) + (l << 12));
				EntityItem entityitem = new EntityItem(entityLiving.worldObj, raycast.blockX, raycast.blockY, raycast.blockZ, new ItemStack (Item.getItemFromBlock(block), 1, l));
				entityLiving.worldObj.spawnEntityInWorld(entityitem);
				entityLiving.worldObj.setBlockToAir(raycast.blockX, raycast.blockY, raycast.blockZ);
			}
		}

		return super.onEntitySwing(entityLiving, stack);
	}

	public boolean isDamageable()
	{
		return false;
	}

	public boolean hasCustomEntity(ItemStack stack)
	{
		return true;
	}

	public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		return new EntityImmortalItem(world, location, itemstack);
	}

	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, 0);
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return TheTitans.godly;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	protected double getRange()
	{
		return 9.0D;
	}

	protected double getDashSpeed()
	{
		return 6.0D;
	}

	public int getUseTime()
	{
		return 5;
	}

	public int getHurtTime()
	{
		return 5;
	}

	protected int getMaxUses()
	{
		return 6;
	}
}


