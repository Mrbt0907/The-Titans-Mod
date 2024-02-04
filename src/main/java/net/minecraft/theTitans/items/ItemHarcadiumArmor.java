package net.minecraft.theTitans.items;
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
public class ItemHarcadiumArmor	extends ItemTitanArmor
{
	public ItemHarcadiumArmor(String materialName, ItemArmor.ArmorMaterial material, int type, float titanArmor, float titanResistance)
	{
		super(materialName, material, type, new double[] 
		{
			50D, 80D, 70D, 40D
		}

		, titanArmor, titanResistance);
	}

	public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
	{
		return TitanItems.harcadium == p_82789_2_.getItem();
	}

	public void onSoundTick(World world, EntityLivingBase entity, ItemStack itemStack)
	{
		if (soundTicks % 80 == 0)
		if (TheTitans.equippedAll(entity, TitanItems.harcadiumArmorSet))
		SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.4F, 1.25F, 119);
		else
		{
			if (TheTitans.equipped(entity, TitanItems.harcadiumHelmet, 1))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.1F, 1.25F, 119);
			if (TheTitans.equipped(entity, TitanItems.harcadiumChestplate, 2))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.1F, 1.25F, 119);
			if (TheTitans.equipped(entity, TitanItems.harcadiumLeggings, 3))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.1F, 1.25F, 119);
			if (TheTitans.equipped(entity, TitanItems.harcadiumBoots, 4))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.1F, 1.25F, 119);
		}

		super.onSoundTick(world, entity, itemStack);
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (getDamage(itemStack) > 0)
		setDamage(itemStack, getDamage(itemStack) - (getMaxDamage() / 25000));
		if (TheTitans.equipped(player, TitanItems.harcadiumHelmet, 1))
		{
			addEffectLong(player, Potion.nightVision, 0);
			addEffect(player, Potion.waterBreathing, 0);
			removeEffect(player, Potion.blindness);
		}

		else if (TheTitans.equipped(player, TitanItems.harcadiumChestplate, 2))
		{
			addEffect(player, Potion.digSpeed, 3);
			addEffect(player, Potion.resistance, 3);
			addEffect(player, Potion.damageBoost, 9);
			addEffect(player, Potion.fireResistance, 0);
			removeEffect(player, Potion.weakness);
			removeEffect(player, Potion.digSlowdown);
		}

		else if (TheTitans.equipped(player, TitanItems.harcadiumLeggings, 3))
		{
			removeEffect(player, Potion.confusion);
			removeEffect(player, Potion.hunger);
			removeEffect(player, Potion.poison);
		}

		else if (TheTitans.equipped(player, TitanItems.harcadiumBoots, 4))
		{
			addEffect(player, Potion.jump, 3);
			addEffect(player, Potion.moveSpeed, 3);
			removeEffect(player, Potion.moveSlowdown);
		}

		if (TheTitans.equippedAll(player, TitanItems.harcadiumArmorSet))
		{
			player.extinguish();
			addEffectLong(player, Potion.field_76444_x, 99);
			addEffect(player, Potion.field_76443_y, 49);
			player.triggerAchievement(TitansAchievments.harcadiumArmor);
		}

		super.onArmorTick(world, player, itemStack);
	}
}


