package net.minecraft.theTitans.items;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityWitherTurretMortar;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.world.World;
public class ItemGoodTurretMortar
extends ItemSword
{
	public ItemGoodTurretMortar(String unlocalizedName, Item.ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(TheTitans.titansTab);
		setTextureName(TheTitans.getTextures("mortar_good"));
	}

	public boolean isItemTool(ItemStack p_77616_1_)
	{
		return true;
	}

	public boolean isDamageable()
	{
		return false;
	}

	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
	{
		return true;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		EntityWitherTurretMortar entity = new EntityWitherTurretMortar(worldIn);
		worldIn.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
		double d0 = 1.0D;
		int a = EnchantmentHelper.getEnchantmentLevel(TheTitans.turretEnchant1.effectId, stack);
		int b = EnchantmentHelper.getEnchantmentLevel(TheTitans.turretEnchant2.effectId, stack);
		int c = EnchantmentHelper.getEnchantmentLevel(TheTitans.turretEnchant3.effectId, stack);
		int d = EnchantmentHelper.getEnchantmentLevel(TheTitans.turretEnchant4.effectId, stack);
		int e = EnchantmentHelper.getEnchantmentLevel(TheTitans.turretEnchant5.effectId, stack);
		int f = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
		entity.setPlayerCreated(true);
		entity.onSpawnWithEgg((IEntityLivingData)null);
		entity.worldObj.playBroadcastSound(1013, p_77648_4_, p_77648_5_, p_77648_6_, 0);
		Random rand = new Random();
		if (a > 0)
		{
			entity.durabilityLevel = a;
		}

		if (b > 0)
		{
			entity.ferocityLevel = b;
		}

		if (c > 0)
		{
			entity.maniacLevel = c;
		}

		if (d > 0)
		{
			entity.unstabilityLevel = d;
		}

		if (e > 0)
		{
			entity.shurakinLevel = e;
		}

		if (f > 0)
		{
			entity.unbreakingLevel = f;
		}

		entity.setLocationAndAngles(p_77648_4_ + 0.5D, p_77648_5_ + d0, p_77648_6_ + 0.5D, 0.0F, 0.0F);
		for (int i = 0; i < 500; i++)
		{
			entity.worldObj.spawnParticle("largesmoke", entity.posX + (rand.nextDouble() - 0.5D) * (entity.width * 3.0D), entity.posY + rand.nextDouble() * (entity.height + 1.0D), entity.posZ + (rand.nextDouble() - 0.5D) * (entity.width * 3.0D), 0.0D, 0.0D, 0.0D);
		}

		if (!worldIn.isRemote)
		{
			worldIn.spawnEntityInWorld(entity);
		}

		if (entity != null)
		{
			if (!playerIn.capabilities.isCreativeMode)
			{
				stack.stackSize -= 1;
			}
		}

		return true;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.rare;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if ((target instanceof EntityWitherTurretMortar))
		{
			if (((EntityWitherTurretMortar)target).isPlayerCreated())
			{
				ItemStack itemstack = ((EntityPlayer)attacker).getCurrentEquippedItem();
				if ((attacker != null) && (itemstack != null) && (itemstack.getItem() == TitanItems.goodTurret3) && (!((EntityWitherTurretMortar)target).worldObj.isRemote))
				{
					((EntityWitherTurretMortar)target).setDead();
					ItemStack goodTurret = new ItemStack(TitanItems.goodTurret3);
					if (((EntityWitherTurretMortar)target).durabilityLevel > 0)
					{
						goodTurret.addEnchantment(TheTitans.turretEnchant1, ((EntityWitherTurretMortar)target).durabilityLevel);
					}

					if (((EntityWitherTurretMortar)target).ferocityLevel > 0)
					{
						goodTurret.addEnchantment(TheTitans.turretEnchant2, ((EntityWitherTurretMortar)target).ferocityLevel);
					}

					if (((EntityWitherTurretMortar)target).maniacLevel > 0)
					{
						goodTurret.addEnchantment(TheTitans.turretEnchant3, ((EntityWitherTurretMortar)target).maniacLevel);
					}

					if (((EntityWitherTurretMortar)target).unstabilityLevel > 0)
					{
						goodTurret.addEnchantment(TheTitans.turretEnchant4, ((EntityWitherTurretMortar)target).unstabilityLevel);
					}

					if (((EntityWitherTurretMortar)target).shurakinLevel > 0)
					{
						goodTurret.addEnchantment(TheTitans.turretEnchant5, ((EntityWitherTurretMortar)target).shurakinLevel);
					}

					if (((EntityWitherTurretMortar)target).unbreakingLevel > 0)
					{
						goodTurret.addEnchantment(Enchantment.unbreaking, ((EntityWitherTurretMortar)target).unbreakingLevel);
					}

					if (!((EntityPlayer)attacker).capabilities.isCreativeMode)
					{
						((EntityWitherTurretMortar)target).entityDropItem(goodTurret, 1.0F);
					}
				}
			}

			return false;
		}

		return true;
	}
}


