package net.minecraft.theTitans.items;
import java.util.List;
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
import net.minecraft.entity.titan.EntityTitanPart;
import net.minecraft.entity.titan.EntityWitherzilla;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.EntityImmortalItem;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
public class ItemAdamantiumSword
extends ItemSword
{
	public static ToolMaterial enumToolMaterialAdamantium = EnumHelper.addToolMaterial("Adamantium", Integer.MAX_VALUE, 2, 9999999F, 9999999999999999999999999999999999996.0F, 0);
	public ItemAdamantiumSword()
	{
		super(enumToolMaterialAdamantium);
		setTextureName(TheTitans.getTextures("adamantium_sword"));
		setUnlocalizedName("adamantium_sword");
		setCreativeTab(TheTitans.titansTab);
	}

	public boolean isItemTool(ItemStack p_77616_1_)
	{
		return true;
	}

	public boolean func_150897_b(Block p_150897_1_)
	{
		return true;
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

	@SuppressWarnings(
	{
		 "rawtypes", "unchecked" 
	}
	)
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_)
	{
		super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
		p_77624_3_.add("\u00A73\u00A7lThe weapon of the CHOSEN ONE.");
		p_77624_3_.add("\u00A73\u00A7lCan destroy anything. (expect deities)");
		p_77624_3_.add("\u00A73\u00A7lOnly the CHOSEN ONE (you) is allowed to wield it");
	}

	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
	{
		return true;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if (target != null && !target.worldObj.isRemote)
		{
			++target.motionY;
			target.hurtResistantTime = 0;
			target.setHealth(0F);
			target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), Float.MAX_VALUE);
			target.playSound("thetitans:titanpunch", 10.0F, 1.0F);
			if ((target instanceof EntityTitan) && ((EntityTitan)target).canBeHurtByPlayer() && !(target instanceof EntityWitherzilla) && ((EntityTitan)target).getInvulTime() < 1)
			{
				target.playSound("thetitans:titanpunch", 10.0F, 1.0F);
				((EntityTitan)target).hurt(5000.0F);
			}
		}

		return true;
	}

	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_)
	{
		int j = this.getMaxItemUseDuration(p_77615_1_) - p_77615_4_;
		ArrowLooseEvent event = new ArrowLooseEvent(p_77615_3_, p_77615_1_, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return;
		}

		j = event.charge;
		float f = (float)j / 60.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if ((double)f < 0.1D)
		{
			return;
		}

		if (f > 1.0F)
		{
			f = 1.0F;
		}

		p_77615_3_.playSound("thetitans:titanSwing", 1.0F, 2.0F);
		p_77615_3_.swingItem();
		Vec3 vec3 = ((EntityPlayer)p_77615_3_).getLook(1.0F);
		double dx = vec3.xCoord * 4D;
		double dy = p_77615_3_.getEyeHeight() + vec3.yCoord * 4D;
		double dz = vec3.zCoord * 4D;
		List<?> list1 = p_77615_3_.worldObj.getEntitiesWithinAABBExcludingEntity(p_77615_3_, p_77615_3_.boundingBox.expand(4.0D, 4.0D, 4.0D).offset(dx, dy, dz));
		if ((list1 != null) && (!list1.isEmpty()))
		{
			for (int i11 = 0; i11 < list1.size(); i11++)
			{
				Entity entity1 = (Entity)list1.get(i11);
				if (entity1 != p_77615_3_ && (entity1 instanceof EntityLivingBase || entity1 instanceof EntityTitanPart || entity1 instanceof EntityTitan))
				{
					++entity1.motionY;
					entity1.hurtResistantTime = 0;
					entity1.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(p_77615_3_), 10000000000.0F * f);
					if (entity1 instanceof EntityLivingBase)
					((EntityLivingBase)entity1).setHealth(((EntityLivingBase)entity1).getHealth() - (5000.0F * f));
					entity1.playSound("thetitans:titanpunch", 10.0F, 1.0F);
					if ((entity1 instanceof EntityTitan) && ((EntityTitan)entity1).canBeHurtByPlayer() && !(entity1 instanceof EntityWitherzilla) && ((EntityTitan)entity1).getInvulTime() < 1)
					{
						entity1.playSound("thetitans:titanpunch", 10.0F, 1.0F);
						((EntityTitan)entity1).hurt(5000.0F * f);
					}
				}
			}
		}
	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.bow;
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
				EntityItem entityitem = new EntityItem(entityLiving.worldObj, raycast.blockX, raycast.blockY, raycast.blockZ, new ItemStack (Item.getItemFromBlock(block), 1, l));
				entityLiving.worldObj.spawnEntityInWorld(entityitem);
				entityLiving.worldObj.setBlockToAir(raycast.blockX, raycast.blockY, raycast.blockZ);
			}
		}

		return false;
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


