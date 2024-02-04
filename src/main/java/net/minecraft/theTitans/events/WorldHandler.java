package net.minecraft.theTitans.events;
import java.util.HashMap;
import java.util.Map;
import cpw.mods.fml.client.GuiIngameModOptions;
import net.minecraft.theTitans.core.TheCore;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.entity.titanminion.EntityBlazeMinion;
import net.minecraft.entity.titanminion.EntityPigZombieMinion;
import net.minecraft.entity.titanminion.EntitySkeletonMinion;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.api.GuiEntry;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.theTitans.gui.GuiNewModList;
import net.minecraft.theTitans.items.ItemTitanArmor;
import net.minecraft.theTitans.network.NetworkHandler;
import net.minecraft.theTitans.network.packets.PacketWorldData;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.world.WorldEvent.*;
public class WorldHandler
{
	private Map<EntityLivingBase, Integer> ticks = new HashMap<EntityLivingBase, Integer>();
	@SubscribeEvent
	public void onLivingUpdate(LivingEvent event)
	{
		event.entity.worldObj.theProfiler.startSection("onTitanLivingUpdate");
		if (event.entityLiving.getMaxHealth() < 0F)
		event.entity.isDead = true;
		if(!(event.entityLiving instanceof EntityPlayer))
		{
			EntityLivingBase entity = event.entityLiving;
			for (int i = 1; i < 5; i++)
			if (entity.getEquipmentInSlot(i) != null && entity.getEquipmentInSlot(i).getItem() instanceof ItemTitanArmor)
			((ItemTitanArmor)entity.getEquipmentInSlot(i).getItem()).onUpdate(entity.getEquipmentInSlot(i), entity.worldObj, entity, i, false);	
		}

		event.entity.worldObj.theProfiler.endSection();
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onLivingHurt(LivingAttackEvent event)
	{
		event.entity.worldObj.theProfiler.startSection("onTitanLivingHurtUpdate");
		if (event.entityLiving != null)
		{
			float amount = 0.0F;
			EntityLivingBase entity = (EntityLivingBase)event.entityLiving;
			if (!ticks.containsKey(entity))
			ticks.put(entity, entity.ticksExisted);
			else if (entity instanceof EntityPlayer && ticks.get(entity) > entity.ticksExisted - 4)
			ticks.replace(entity, entity.ticksExisted);
			else if (!entity.isEntityAlive())
			ticks.remove(entity);
			if (TheTitans.isDifficulty(TheTitans.getDifficulty(entity.worldObj), 7, TheCore.DIFFICULTIES.length - 1))
			amount = event.ammount * 9.0F;
			else if (TheTitans.isDifficulty(TheTitans.getDifficulty(entity.worldObj), 6, TheCore.DIFFICULTIES.length - 1))
			amount = event.ammount * 3.0F;
			else if (TheTitans.isDifficulty(TheTitans.getDifficulty(entity.worldObj), 5, TheCore.DIFFICULTIES.length - 1))
			amount = event.ammount * 1.5F;
			else if (TheTitans.isDifficulty(TheTitans.getDifficulty(entity.worldObj), 4, TheCore.DIFFICULTIES.length - 1))
			amount = event.ammount;
			if (ticks.containsKey(entity) && ticks.get(entity) < entity.ticksExisted - 4)
			{
				ticks.replace(entity, entity.ticksExisted); 
				int index = 0;
				for (int i = 1; i < 5; i++)
				if (entity.getEquipmentInSlot(i) != null && entity.getEquipmentInSlot(i).getItem() instanceof ItemTitanArmor)
				if (event.ammount > 0.0F)
				{
					((ItemTitanArmor)entity.getEquipmentInSlot(i).getItem()).attackEntity(entity.getEquipmentInSlot(i), entity, event.ammount + amount);
					index++;
					break;
				}

				if (TheTitans.isDifficulty(entity.worldObj, EnumDifficulty.HARD) && amount > 0.0F && entity instanceof EntityPlayer && (event.source.canHarmInCreative() || !((EntityPlayer) entity).capabilities.isCreativeMode) && index == 0)
				EventData.damageEntity(entity, event.source, amount);
			}
		}

		event.entity.worldObj.theProfiler.endSection();
	}

	@SubscribeEvent()
	public void onLivingDeath(LivingDeathEvent event)
	{
		World worldObj = event.entityLiving.worldObj;
		worldObj.theProfiler.startSection("onTitanLivingDeathUpdate");
		for (EventObject eve : EventObject.instances)
			if (eve.onDeath(event.entity))
				break;
		if (event.entityLiving instanceof EntityPlayer)
			for (int i = 0; i < 4; i++)
			{
				EntityPlayer player = (EntityPlayer)event.entityLiving;
				if (player.getCurrentArmor(i) != null && player.getCurrentArmor(i).getItem() instanceof ItemTitanArmor)
				if (((ItemTitanArmor)player.getCurrentArmor(i).getItem()).getHealth(player.getCurrentArmor(i)) > 0.0F)
				{
					player.isDead = false;
					player.deathTime = 0;
					event.setCanceled(true);
					break;
				}
			}
		
		if (!worldObj.isRemote)
		{
			if (!EventData.getBool(worldObj, "PostDragon") && event.entityLiving instanceof EntityDragon)
			{
				EventData.saveInstance.setBoolean(worldObj, "PostDragon", true);
				NetworkHandler.sendClientPacket(new PacketWorldData("PostDragon", true));
				TheTitans.debug("The world is now post ender dragon");
				EventData.sendMessage(worldObj, TheTitans.translateMult("event", "pretitan.postdragon", 5));
			}
	
			else if (!EventData.getBool(worldObj, "PostWither") && event.entityLiving instanceof EntityWither)
			{
				EventData.saveInstance.setBoolean(worldObj, "PostWither", true);
				NetworkHandler.sendClientPacket(new PacketWorldData("PostWither", true));
				TheTitans.debug("The world is now post wither");
				EventData.sendMessage(worldObj, TheTitans.translateMult("event", "pretitan.postwither", 5));
			}
	
			if (!EventData.isTitanMode(worldObj) && EventData.getBool(worldObj, "PostDragon") && EventData.getBool(worldObj, "PostWither"))
			{
				EventData.saveInstance.setBoolean(worldObj, "PreTitanComplete", true);
				NetworkHandler.sendClientPacket(new PacketWorldData("PreTitanComplete", true));
				TheTitans.debug("The world can now spawn and awaken titans");
				EventData.sendMessage(worldObj, TheTitans.translateMult("event", "pretitan.complete", 5));
			}
		}
		event.entity.worldObj.theProfiler.endSection();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onGuiOpen(GuiOpenEvent event)
	{
		if (event.gui != null)
		{
			if (TitanConfig.isCoreModding && event.gui instanceof GuiIngameModOptions)
			event.gui = new GuiNewModList(new GuiIngameMenu());
			else if (event.gui instanceof GuiGameOver)
			for (int i = 0; i < 4; i++)
			if (Minecraft.getMinecraft().thePlayer.getCurrentArmor(i) != null && Minecraft.getMinecraft().thePlayer.getCurrentArmor(i).getItem() instanceof ItemTitanArmor && ((ItemTitanArmor)Minecraft.getMinecraft().thePlayer.getCurrentArmor(i).getItem()).getHealth(Minecraft.getMinecraft().thePlayer.getCurrentArmor(i)) > 0.0F)
			{
				event.setCanceled(true);
				break;
			}
		}
	}

	@SubscribeEvent
	public void onWorldLoad(Load event)
	{
		if (EventData.saveInstance == null)
		EventData.saveInstance = TitanSavedData.getStorageData(event.world);
	}

	@SubscribeEvent
	public void onWorldUnload(Unload event)
	{
		if (event.world.isRemote)
		GuiEntry.instances.clear();
		if (event.world instanceof WorldServer && EventData.deleteWorld != null && event.world.equals(EventData.deleteWorld))
		EventData.deleteWorld((WorldServer)event.world);
		//EventData.saveInstance = null;
	}

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void onMapGen(InitMapGenEvent event)
	{
		if (event.type == EventType.NETHER_BRIDGE)
		{
			event.newGen = new MapGenNetherBridge();
			((MapGenNetherBridge)event.newGen).getSpawnList().add(new BiomeGenBase.SpawnListEntry(EntityBlazeMinion.class, 10, 2, 3));
			((MapGenNetherBridge)event.newGen).getSpawnList().add(new BiomeGenBase.SpawnListEntry(EntitySkeletonMinion.class, 10, 4, 4));
			((MapGenNetherBridge)event.newGen).getSpawnList().add(new BiomeGenBase.SpawnListEntry(EntityPigZombieMinion.class, 5, 4, 4));
		}
	}

	@SubscribeEvent
	public void onSpawnCheck(CheckSpawn event)
	{
		//if (!EventData.getBool(event.world, "PostWither") && IMinion.class.isAssignableFrom(event.entity.getClass()))
		//event.setResult(Result.DENY);
	}

	@SubscribeEvent
	public void createEvent(LivingSpawnEvent event)
	{
		if (Loader.isModLoaded("OreSpawn"))
		{
			if (event.entityLiving instanceof danger.orespawn.Godzilla)
			{
				event.entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(danger.orespawn.OreSpawnMain.Godzilla_stats.health * 10);
				event.entityLiving.heal(event.entityLiving.getMaxHealth());
			}

			if (event.entityLiving instanceof danger.orespawn.TheKing)
			{
				event.entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(danger.orespawn.OreSpawnMain.TheKing_stats.health * 20);
				event.entityLiving.heal(event.entityLiving.getMaxHealth());
			}

			if (event.entityLiving instanceof danger.orespawn.TheQueen)
			{
				event.entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(danger.orespawn.OreSpawnMain.TheQueen_stats.health * 20);
				event.entityLiving.heal(event.entityLiving.getMaxHealth());
			}
		}

		if (event.entityLiving instanceof EntityVillager)
		((EntityVillager)event.entityLiving).tasks.addTask(0, new EntityAIAvoidEntity((EntityVillager)event.entityLiving, EntityZombieTitan.class, 96.0F, 1.2D, 1.2D));
	}
}


