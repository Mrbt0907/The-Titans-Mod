package net.minecraft.theTitans.items;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.theTitans.SoundHandler;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
public class ItemVoidArmor
extends ItemTitanArmor
{
	public ItemVoidArmor(String materialName, ItemArmor.ArmorMaterial material, int type, float titanArmor, float titanResistance)
	{
		super(materialName, material, type, new double[] 
		{
			250D, 400D, 350D, 200D
		}

		, titanArmor, titanResistance);
	}

	public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
	{
		return TitanItems.voidItem == p_82789_2_.getItem();
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.rare;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	public void onSoundTick(World world, EntityLivingBase entity, ItemStack itemStack)
	{
		if (soundTicks % 80 == 0)
		if (TheTitans.equippedAll(entity, TitanItems.voidArmorSet))
		SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.9F, 0.9F, 120);
		else
		{
			if (TheTitans.equipped(entity, TitanItems.voidHelmet, 1))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.20F, 0.9F, 120);
			if (TheTitans.equipped(entity, TitanItems.voidChestplate, 2))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.20F, 0.9F, 120);
			if (TheTitans.equipped(entity, TitanItems.voidLeggings, 3))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.20F, 0.9F, 120);
			if (TheTitans.equipped(entity, TitanItems.voidBoots, 4))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.20F, 0.9F, 120);
		}

		super.onSoundTick(world, entity, itemStack);
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (TheTitans.equipped(player, TitanItems.voidHelmet, 1))
		{
			addEffectLong(player, Potion.nightVision, 0);
			addEffect(player, Potion.waterBreathing, 0);
			removeEffect(player, Potion.blindness);
		}

		if (TheTitans.equipped(player, TitanItems.voidChestplate, 2))
		{
			addEffect(player, Potion.digSpeed, 99);
			addEffect(player, Potion.resistance, 3);
			addEffect(player, Potion.damageBoost, 49);
			addEffect(player, Potion.fireResistance, 0);
			removeEffect(player, Potion.weakness);
			removeEffect(player, Potion.digSlowdown);
			if (player.isBurning())
				player.extinguish();
		}

		if (TheTitans.equipped(player, TitanItems.voidLeggings, 3))
		{
			removeEffect(player, Potion.confusion);
			removeEffect(player, Potion.hunger);
			removeEffect(player, Potion.poison);
		}

		if (TheTitans.equipped(player, TitanItems.voidBoots, 4))
		{
			addEffect(player, Potion.jump, 5);
			addEffect(player, Potion.moveSpeed, 19);
			removeEffect(player, Potion.moveSlowdown);
			player.stepHeight = 2F;
		}

		if (TheTitans.equippedAll(player, TitanItems.voidArmorSet))
		{
			player.fallDistance *= 0.0F;
			addEffectLong(player, Potion.field_76444_x, 199);
			addEffect(player, Potion.field_76443_y, 99);
			player.triggerAchievement(TitansAchievments.voidArmor);
			
			for (int i = 0; i < 4; i++)
				player.worldObj.spawnParticle("depthsuspend", player.posX + (player.getRNG().nextDouble() - 0.5D) * player.width * 2.0D, player.posY - 1.75D + player.getRNG().nextDouble() * player.height, player.posZ + (player.getRNG().nextDouble() - 0.5D) * player.width * 2.0D, 0.0D, 0.05D, 0.0D);

			List<?> list11 = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(4.0D, 4.0D, 4.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (entity instanceof EntityLivingBase && !TheTitans.checkFriendlyFire(player, entity, false))
					{
						entity.attackEntityFrom(DamageSource.outOfWorld, 4F);
						if (entity.worldObj.isRemote)
						{
							addEffect((EntityLivingBase)entity, Potion.blindness, 5000, 1);
							addEffect((EntityLivingBase)entity, Potion.confusion, 5000, 1);
							addEffect((EntityLivingBase)entity, Potion.moveSlowdown, 5000, 9);
							addEffect((EntityLivingBase)entity, Potion.wither, 5000, 3);
						}
					}
				}
			}
		}

		super.onArmorTick(world, player, itemStack);
	}
}


