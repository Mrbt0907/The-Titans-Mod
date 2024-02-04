package net.minecraft.theTitans.items;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.theTitans.SoundHandler;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class ItemWithireniumArmor
extends ItemTitanArmor
{
	public ItemWithireniumArmor(String materialName, ItemArmor.ArmorMaterial material, int type, float titanArmor, float titanResistance)
	{
		super(materialName, material, type, new double[] 
		{
			100D, 160D, 140D, 80D
		}

		, titanArmor, titanResistance);
	}

	public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
	{
		return TitanItems.withirenium == p_82789_2_.getItem();
	}

	public void onSoundTick(World world, EntityLivingBase entity, ItemStack itemStack)
	{
		if (soundTicks % 80 == 0)
		if (TheTitans.equippedAll(entity, TitanItems.withireniumArmorSet))
		SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.75F, 1.0F, 119);
		else
		{
			if (TheTitans.equipped(entity, TitanItems.withireniumHelmet, 1))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.15F, 1.0F, 119);
			if (TheTitans.equipped(entity, TitanItems.withireniumChestplate, 2))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.15F, 1.0F, 119);
			if (TheTitans.equipped(entity, TitanItems.withireniumLeggings, 3))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.15F, 1.0F, 119);
			if (TheTitans.equipped(entity, TitanItems.withireniumBoots, 4))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.15F, 1.0F, 119);
		}

		super.onSoundTick(world, entity, itemStack);
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (TheTitans.equipped(player, TitanItems.withireniumHelmet, 1))
		{
			addEffectLong(player, Potion.nightVision, 0);
			addEffect(player, Potion.waterBreathing, 0);
			removeEffect(player, Potion.blindness);
		}

		if (TheTitans.equipped(player, TitanItems.withireniumChestplate, 2))
		{
			addEffect(player, Potion.digSpeed, 4);
			addEffect(player, Potion.resistance, 3);
			addEffect(player, Potion.damageBoost, 14);
			addEffect(player, Potion.fireResistance, 0);
			removeEffect(player, Potion.weakness);
			removeEffect(player, Potion.digSlowdown);
		}

		if (TheTitans.equipped(player, TitanItems.withireniumLeggings, 3))
		{
			removeEffect(player, Potion.confusion);
			removeEffect(player, Potion.hunger);
			removeEffect(player, Potion.poison);
		}

		if (TheTitans.equipped(player, TitanItems.withireniumBoots, 4))
		{
			addEffect(player, Potion.jump, 4);
			addEffect(player, Potion.moveSpeed, 4);
			removeEffect(player, Potion.moveSlowdown);
			player.stepHeight = 2F;
		}

		if (TheTitans.equippedAll(player, TitanItems.withireniumArmorSet))
		{
			addEffectLong(player, Potion.field_76444_x, 99);
			addEffect(player, Potion.field_76443_y, 49);
			removeEffect(player, Potion.wither);
			player.triggerAchievement(TitansAchievments.withireniumArmor);
			if (player.isBurning())
			player.extinguish();
			if (Maths.chance(25))
				player.worldObj.spawnParticle("flame", player.posX + (player.getRNG().nextDouble() - 0.5D) * player.width * 2.0D, player.posY - 1.75D + player.getRNG().nextDouble() * player.height, player.posZ + (player.getRNG().nextDouble() - 0.5D) * player.width * 2.0D, 0.0D, 0.05D, 0.0D);
			List<?> list11 = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(4.0D, 4.0D, 4.0D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity = (Entity)list11.get(i1);
					if (entity instanceof EntityLivingBase && !TheTitans.checkFriendlyFire(player, entity, false))
					{
						entity.setFire(30);
						addEffect((EntityLivingBase)entity, Potion.wither, 500, 1);
					}
				}
			}
		}

		super.onArmorTick(world, player, itemStack);
	}
}


