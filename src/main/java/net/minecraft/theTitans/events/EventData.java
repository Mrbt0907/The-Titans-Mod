package net.minecraft.theTitans.events;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
public class EventData
{
	public static TitanSavedData saveInstance;
	public static WorldServer deleteWorld;
	public static boolean isPreTitanMode(World worldObj)
	{
		return !getBool(worldObj, "PreTitanComplete") && !getBool(worldObj, "TitanComplete") && !getBool(worldObj, "PostTitanComplete");
	}

	public static boolean isTitanMode(World worldObj)
	{
		return !getBool(worldObj, "TitanComplete") && !getBool(worldObj, "PostTitanComplete") && getBool(worldObj, "PreTitanComplete");
	}

	public static boolean isPostTitanMode(World worldObj)
	{
		return !getBool(worldObj, "PostTitanComplete") && getBool(worldObj, "TitanComplete") && getBool(worldObj, "PreTitanComplete");
	}

	public static boolean isEndGame(World worldObj)
	{
		return getBool(worldObj, "PostTitanComplete") && getBool(worldObj, "TitanComplete") && getBool(worldObj, "PreTitanComplete");
	}

	public static boolean getBool(World worldObj, String key)
	{
		if (saveInstance == null)
		{
			saveInstance = TitanSavedData.getStorageData(worldObj);
			TheTitans.error("saveInstance returned null. saveInstance should of been initialized first", new NullPointerException());
			return false;
		}

		return saveInstance.getBoolean(worldObj, key);
	}

	public static void setBool(World worldObj, String key, boolean flag)
	{
		if (saveInstance == null)
		{
			saveInstance = TitanSavedData.getStorageData(worldObj);
			TheTitans.error("saveInstance returned null. saveInstance should of been initialized first", new NullPointerException());
			return;
		}

		saveInstance.setBoolean(worldObj, key, flag);
	}

	public static void sendMessage(World worldObj, String message)
	{
		if (!worldObj.isRemote && !worldObj.playerEntities.isEmpty())
		sendMessage(worldObj, message, worldObj.playerEntities.toArray());
	}

	public static void sendMessage(World worldObj, String message, Object... players)
	{
		if (!worldObj.isRemote)
		for (int i = 0; i < players.length; i++)
		if (players[i] != null && players[i] instanceof EntityPlayer)
		((EntityPlayer)players[i]).addChatMessage(new ChatComponentTranslation(message));
	}

	public static void queueDeleteWorld(String message)
	{
		MinecraftServer server = MinecraftServer.getServer();
		TheTitans.reflect.set(MinecraftServer.class, server, true, "worldIsBeingDeleted", "field_71290_O");
		server.initiateShutdown();
		deleteWorld = server.worldServers[0];
		TheTitans.info("Stopping server");
		if (server.func_147137_ag() != null)
		server.func_147137_ag().terminateEndpoints();
		if (server.getConfigurationManager() != null)
		{
			TheTitans.info("Remove players");
			for (int i = 0; i < server.getConfigurationManager().playerEntityList.size(); ++i)
			{
				((EntityPlayerMP)server.getConfigurationManager().playerEntityList.get(i)).playerNetServerHandler.kickPlayerFromServer(I18n.format(message));
			}
		}

		if (server.worldServers != null)
		{
			for (int i = 0; i < server.worldServers.length; ++i)
			{
				WorldServer worldserver = server.worldServers[i];
				MinecraftForge.EVENT_BUS.post(new WorldEvent.Unload(worldserver));
				worldserver.flush();
			}
		}

		if (server.getPlayerUsageSnooper().isSnooperRunning())
		server.getPlayerUsageSnooper().stopSnooper();
	}

	public static void deleteWorld(WorldServer world)
	{
		deleteWorld = null;
		if (world == null)
		return;
		TheTitans.debug("Attempting to delete " + world.getWorldInfo().getWorldName());
		MinecraftServer server = (MinecraftServer)TheTitans.reflect.get(WorldServer.class, world, "mcServer", "field_73061_a");
		server.getActiveAnvilConverter().flushCache();
		if (!server.getActiveAnvilConverter().deleteWorldDirectory(world.getSaveHandler().getWorldDirectoryName()))
		{
			TheTitans.warn("Deletion Failed. Retrying...");
			deleteWorld = world;
		}
	}

	protected static void damageEntity(EntityLivingBase entity, DamageSource source, float damage)
	{
		if (!entity.isEntityInvulnerable())
		{
			if (damage <= 0) return;
			damage = applyArmorCalculations(entity, source, damage);
			damage = applyPotionDamageCalculations(entity, source, damage);
			float f1 = damage;
			damage = Math.max(damage - entity.getAbsorptionAmount(), 0.0F);
			entity.setAbsorptionAmount(entity.getAbsorptionAmount() - (f1 - damage));
			if (damage != 0.0F)
			{
				float f2 = entity.getHealth();
				entity.setHealth(f2 - damage);
				entity.func_110142_aN().func_94547_a(source, f2, damage);
				entity.setAbsorptionAmount(entity.getAbsorptionAmount() - damage);
				if (!entity.isEntityAlive())
				entity.onDeath(source);
			}
		}
	}

	protected static float applyArmorCalculations(EntityLivingBase entity, DamageSource p_70655_1_, float p_70655_2_)
	{
		if (!p_70655_1_.isUnblockable())
		{
			int i = 25 - entity.getTotalArmorValue();
			float f1 = p_70655_2_ * (float)i;
			p_70655_2_ = f1 / 25.0F;
		}

		return p_70655_2_;
	}

	protected static float applyPotionDamageCalculations(EntityLivingBase entity, DamageSource p_70672_1_, float p_70672_2_)
	{
		if (p_70672_1_.isDamageAbsolute())
		return p_70672_2_;
		else
		{
			int i;
			int j;
			float f1;
			if (entity.isPotionActive(Potion.resistance) && p_70672_1_ != DamageSource.outOfWorld)
			{
				i = (entity.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
				j = 25 - i;
				f1 = p_70672_2_ * (float)j;
				p_70672_2_ = f1 / 25.0F;
			}

			if (p_70672_2_ <= 0.0F)
			return 0.0F;
			else
			{
				i = EnchantmentHelper.getEnchantmentModifierDamage(entity.getLastActiveItems(), p_70672_1_);
				if (i > 20)
				{
					i = 20;
				}

				if (i > 0 && i <= 20)
				{
					j = 25 - i;
					f1 = p_70672_2_ * (float)j;
					p_70672_2_ = f1 / 25.0F;
				}

				return p_70672_2_;
			}
		}
	}
}


