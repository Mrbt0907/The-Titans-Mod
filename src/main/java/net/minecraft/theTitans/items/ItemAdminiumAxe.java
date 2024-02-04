package net.minecraft.theTitans.items;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockOre;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.EntityImmortalItem;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
public class ItemAdminiumAxe
extends ItemNormalAxe
{
	public ItemAdminiumAxe(String unlocalizedName, Item.ToolMaterial material)
	{
		super(unlocalizedName, material);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		if (target != null)
		{
			if ((target.height >= 6.0F) || ((target instanceof EntityTitan)) || (!target.onGround))
			{
				target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), 4000000000.0F);
				target.playSound("thetitans:titanpunch", 100.0F, 0.5F);
			}
		}

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
		MovingObjectPosition raycast = raytraceFromEntity(entityLiving.worldObj, entityLiving, true, 10.0D);
		if (!entityLiving.worldObj.isRemote && raycast != null && entityLiving.getDistance(raycast.blockX, raycast.blockY, raycast.blockZ) <= 6D)
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

		return false;
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
}


