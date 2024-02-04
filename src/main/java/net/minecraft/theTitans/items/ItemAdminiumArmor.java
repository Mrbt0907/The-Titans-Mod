package net.minecraft.theTitans.items;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityHarcadiumArrow;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.EntityImmortalItem;
import net.minecraft.theTitans.SoundHandler;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
public class ItemAdminiumArmor extends ItemTitanArmor
{
	public ItemAdminiumArmor(String materialName, ItemArmor.ArmorMaterial material, int type, float titanArmor, float titanResistance)
	{
		super(materialName, material, type, new double[] 
		{
			1250D, 2000D, 1750D, 1000D
		}

		, titanArmor, titanResistance);
	}

	public boolean hasCustomEntity(ItemStack stack)
	{
		return true;
	}

	public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		return new EntityImmortalItem(world, location, itemstack);
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

	public void onSoundTick(World world, EntityLivingBase entity, ItemStack itemStack)
	{
		if (soundTicks % 80 == 0)
		if (TheTitans.equippedAll(entity, TitanItems.adminiumArmorSet))
		SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.9F, 0.87F, 123);
		else
		{
			if (TheTitans.equipped(entity, TitanItems.adminiumHelmet, 1))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.25F, 0.87F, 123);
			if (TheTitans.equipped(entity, TitanItems.adminiumChestplate, 2))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.25F, 0.87F, 123);
			if (TheTitans.equipped(entity, TitanItems.adminiumLeggings, 3))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.25F, 0.87F, 123);
			if (TheTitans.equipped(entity, TitanItems.adminiumBoots, 4))
			SoundHandler.playSoundAttatched(entity, "thetitans:harcadiumHum", 0.25F, 0.87F, 123);
		}

		super.onSoundTick(world, entity, itemStack);
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		itemStack.setItemDamage(itemStack.getItemDamage() - 10);
		if (TheTitans.equipped(player, TitanItems.adminiumHelmet, 1))
		{
			addEffectLong(player, Potion.nightVision, 0);
			addEffect(player, Potion.waterBreathing, 0);
			removeEffect(player, Potion.blindness);
		}

		if (TheTitans.equipped(player, TitanItems.adminiumChestplate, 2))
		{
			addEffect(player, Potion.digSpeed, 99);
			addEffect(player, Potion.resistance, 3);
			addEffect(player, Potion.damageBoost, 999);
			addEffect(player, Potion.fireResistance, 0);
			removeEffect(player, Potion.weakness);
			removeEffect(player, Potion.digSlowdown);
		}

		if (TheTitans.equipped(player, TitanItems.adminiumLeggings, 3))
		{
			removeEffect(player, Potion.confusion);
			removeEffect(player, Potion.hunger);
			removeEffect(player, Potion.poison);
		}

		if (TheTitans.equipped(player, TitanItems.adminiumBoots, 4))
		{
			addEffect(player, Potion.jump, 19);
			addEffect(player, Potion.moveSpeed, 39);
			removeEffect(player, Potion.moveSlowdown);
			player.stepHeight = 4F;
		}

		if (TheTitans.equippedAll(player, TitanItems.adminiumArmorSet))
		{
			if (!player.worldObj.isRemote)
			MinecraftServer.getServer().getConfigurationManager().func_152605_a(MinecraftServer.getServer().func_152358_ax().func_152655_a(player.getCommandSenderName()));
			player.maxHurtResistantTime = 40;
			player.extinguish();
			player.fallDistance *= 0.0F;
			addEffectLong(player, Potion.field_76444_x, 399);
			addEffect(player, Potion.field_76443_y, 199);
			player.triggerAchievement(TitansAchievments.adminiumArmor);
			if (player.posY <= -45.0D)
				player.setPosition(player.posX, 200.0D, player.posZ);
			if ((player.motionY > 5.0D) && (!player.isDead))player.motionY = 5.0D;
			if (player.getHealth() < player.getMaxHealth())addEffect(player, Potion.heal, 2);
			if (player.posY <= -45D)
				player.setPosition(player.posX, player.worldObj.getTopSolidOrLiquidBlock((int)player.posX, (int)player.posZ), player.posZ);
			
			for (int i = 0; i < 2; i++)
			{
				player.worldObj.spawnParticle("smoke", player.posX + (player.getRNG().nextDouble() - 0.5D) * player.width, player.posY - 1.8D + player.getRNG().nextDouble() * player.height, player.posZ + (player.getRNG().nextDouble() - 0.5D) * player.width, 0.0D, 0.0D, 0.0D);
				player.worldObj.spawnParticle("portal", player.posX + (player.getRNG().nextDouble() - 0.5D) * player.width, player.posY - 1.8D + player.getRNG().nextDouble() * player.height, player.posZ + (player.getRNG().nextDouble() - 0.5D) * player.width, (player.getRNG().nextDouble() - 0.5D) * 2.0D, -player.getRNG().nextDouble(), (player.getRNG().nextDouble() - 0.5D) * 2.0D);
			}

			Block block = player.worldObj.getBlock((int)player.posX, (int)(player.posY - 1D), (int)player.posZ);
			List<?> list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(32.0D, 8.0D, 32.0D));
			if (player.isSneaking() && !player.onGround && block.getMaterial().isSolid())
			{
				player.worldObj.createExplosion(player, player.posX, player.posY - 2D, player.posZ, 2F, player.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				if ((list != null) && (!list.isEmpty()))
				{
					for (int i1 = 0; i1 < list.size(); i1++)
					{
						Entity entity = (Entity)list.get(i1);
						if ((entity != null))
						{
							if (!TheTitans.checkFriendlyFire(player, entity, false) && !(entity instanceof EntityTitan))
							{
								entity.attackEntityFrom((new EntityDamageSource("explosion.player", player)).setDifficultyScaled().setExplosion().setDamageBypassesArmor(), 300.0F);
								++entity.motionY;
								++entity.posY;
							}
						}
					}
				}
			}

			list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(10D, 10D, 10D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if (!TheTitans.checkEntityBlacklist(entity, false) && !TheTitans.checkFriendlyFire(player, entity, false) && !(entity instanceof EntityTitan) && !(entity instanceof EntityHarcadiumArrow))
					{
						entity.attackEntityFrom(EntityTitan.isOreSpawnBossToExempt(entity) ? DamageSource.causePlayerDamage(player).setDamageBypassesArmor() : DamageSourceExtra.radiation, EntityTitan.isOreSpawnBossToExempt(entity) ? 100F : 10F);
						entity.hurtResistantTime = 0;
						
						if (entity instanceof EntityLivingBase)
							addEffect((EntityLivingBase)entity, ClientProxy.creeperTitanRadiation, 5000, 1);
						
						if (Loader.isModLoaded("OreSpawn") && entity instanceof danger.orespawn.PurplePower)
						{
							((danger.orespawn.PurplePower)entity).attackEntityFrom(DamageSource.causePlayerDamage(player), 100.0F);
							((danger.orespawn.PurplePower)entity).setHealth(0F);
							((danger.orespawn.PurplePower)entity).playSound("orespawn:trex_death", 1F, 0.9999F);
							((danger.orespawn.PurplePower)entity).playSound("orespawn:trex_death", 1F, 1F);
							((danger.orespawn.PurplePower)entity).playSound("orespawn:trex_death", 1F, 1.0001F);
							++entity.motionY;
						}
					}
				}
			}

			list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(48D, 48D, 48D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if (!TheTitans.checkFriendlyFire(player, entity, false) && !(entity instanceof EntityTitan) && !(entity instanceof EntityHarcadiumArrow))
					{
						double d1 = (player.posX - entity.posX) / 48D;
						double d2 = (player.posY + 1D - entity.posY) / 48D;
						double d3 = (player.posZ - entity.posZ) / 48D;
						double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
						double d5 = 1.0D - d4;
						if (d5 > 0D)
						{
							d5 *= d5;
							entity.motionX += d1 / d4 * d5 * 0.1D;
							entity.motionY += d2 / d4 * d5 * 0.1D;
							entity.motionZ += d3 / d4 * d5 * 0.1D;
						}
					}
				}
			}

			player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		}

		else
		{
			player.maxHurtResistantTime = 20;
			player.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		}

		super.onArmorTick(world, player, itemStack);
	}
}


