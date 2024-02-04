package net.minecraft.titans.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.titans.CommonProxy;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.entity.god.EntityWitherzilla;
import net.minecraft.titans.manager.EnumEvents;
import net.minecraft.titans.manager.EventObject;
import net.minecraft.titans.manager.EventObject.EventMobEntryPre;
import net.minecraft.titans.manager.TitanManager;
import net.minecraft.titans.network.EnumPackets;
import net.minecraft.titans.network.NetworkHandler;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.titans.utils.FileUtil;
import net.minecraft.titans.utils.TranslateUtil;
import net.minecraft.titans.utils.Maths.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TitanManagerServer extends TitanManager
{
	private UUID attributeHealthUUID;
	private UUID attributeDamageUUID;
	private EntityWitherzilla witherzilla;
	private final List<World> worlds = new ArrayList<World>();
	
	public TitanManagerServer()
	{
		TheTitans.debug("Initialized Titan Manager for Server");
	}
	
	public void readNBT(NBTTagCompound nbt)
	{
		super.readNBT(nbt);
		attributeHealthUUID = nbt.getUniqueId("attributeHealthUUID");
		if (attributeHealthUUID == null) attributeHealthUUID = UUID.randomUUID();
		attributeDamageUUID = nbt.getUniqueId("attributeDamageUUID");
		if (attributeDamageUUID == null) attributeDamageUUID = UUID.randomUUID();
	}
	
	private NBTTagCompound writeNBT()
	{
		return writeNBT(0, false);
	}
	
	private NBTTagCompound writeNBT(int dimension)
	{
		return writeNBT(dimension, true);
	}
	
	private NBTTagCompound writeNBT(int dimension, boolean useDimension)
	{
		TheTitans.debug("Grabbing nbt data...");
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagCompound nbtEvents = new NBTTagCompound();
		NBTTagCompound nbtDefeatedEvents = new NBTTagCompound();
		nbt.setBoolean("defeatedDragon", defeatedDragon);
		nbt.setBoolean("defeatedWither", defeatedWither);
		nbt.setBoolean("defeatedSkully", defeatedSkully);
		nbt.setBoolean("defeatedWitherzilla", defeatedWitherzilla);
		TheTitans.debug("Written variables to nbt");
		events.forEach((uuid, event) -> 
		{
			if (useDimension && event.getDimension() == dimension || !useDimension)
			{
				nbtEvents.setTag("event_" + uuid, event.writeNBT(new NBTTagCompound()));
				TheTitans.debug("Writing event_" + uuid + " to nbt...");
			}
		});
		nbt.setTag("events", nbtEvents);
		
		defeatedEvents.forEach(type -> nbtDefeatedEvents.setInteger("event_" + type, type));
		nbt.setTag("defeatedEvents", nbtDefeatedEvents);
		TheTitans.debug("Written defeated events to nbt");
		TheTitans.debug("Successfuly grabbed nbt data!");
		return nbt;
	}
	
	public void tick()
	{
		super.tick();
		
		//Event Objects - Spawn
		worlds.forEach(world ->
			world.playerEntities.forEach(player ->
			{
				
			})
		);
		
		//Event Objects - Tick
		List<EventObject> events = new ArrayList<EventObject>(this.events.values());
		EventObject event;
		
		for (int i = 0; i < events.size(); i++)
		{
			event = events.get(i);
			
			if (event.isDead)
			{
				TranslateUtil.sendChatAll("event." + event.getUnlocalizedName() + ".finished", event.getAmountKilled(), event.getKillsNeeded(), event.getAmountSpawned());
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setUniqueId("uuid", event.getUniqueID());
				NetworkHandler.sendClientPacket(EnumPackets.EVENT_REMOVE, nbt);
				removeEvent(event.getUniqueID());
				continue;
			}
			
			event.tick();
		}
	}
	
	public void onPlayerJoined(EntityPlayer player)
	{
		if (witherzilla != null)
			witherzilla.onQuote(player, 0, 0);
	}
	
	public void onEntityJoined(Entity entity)
	{
		super.onEntityJoined(entity);
		
		if (entity instanceof EntityWitherzilla && TheTitans.getClassName(entity).equals("net.minecraft.titans.entity.god.EntityWitherzilla"))
		{
			if (witherzilla == null)
			{
				witherzilla = (EntityWitherzilla) entity;
				witherzilla.playSound(TSounds.get("witherzilla.spawn"), Float.MAX_VALUE, 1F);
				FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(player -> witherzilla.onQuote(player, 0, 0));
			}
			else
				FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(player -> witherzilla.onQuote(player, 1, 0));
		}
	}
	
	public void onEntityKilled(Entity entity)
	{
		super.onEntityKilled(entity);
		if (witherzilla != null && entity instanceof EntityWitherzilla)
			witherzilla = null;
	}
	
	@Override
	public void onNetworkMessage(int index, NBTTagCompound nbt)
	{
		switch (index)
		{
			case 0:
				UUID uuid = nbt.getUniqueId("uuid"); 
				if (worlds.size() > 0)
				{
					List<EntityPlayerMP> players = new ArrayList<EntityPlayerMP>(worlds.get(0).getMinecraftServer().getPlayerList().getPlayers());
					for (EntityPlayerMP player : players)
						if (player.getUniqueID().equals(uuid))
						{
							syncData(player);
							break;
						}
				}
				
				break;
			default:
				TheTitans.error("Server manager recieved an invalid network message (type:" + index + ")");
		}
	}
	
	public boolean hasWitherzilla()
	{
		return witherzilla != null;
	}
	
	public void reset()
	{
		saveFile();
		witherzilla = null;
		super.reset();
	}
	
	public EventObject createEvent(World world, Vec3 pos, EnumEvents event)
	{
		EventObject newEvent = new EventObject(world, pos, event);
		events.put(newEvent.getUniqueID(), newEvent);
		NetworkHandler.sendClientPacket(EnumPackets.EVENT_CREATE, newEvent.writeNBT(new NBTTagCompound()), world.provider.getDimension());
		return newEvent;
	}
	
	public EventObject createEvent(World world, String unlocalizedName, int type, Vec3 pos, double distance, int neededKills, EventMobEntryPre... entries)
	{
		EventObject newEvent = new EventObject(world, unlocalizedName, type, pos, distance, neededKills);
		events.put(newEvent.getUniqueID(), newEvent);
		for(EventMobEntryPre entry : entries)
			newEvent.addMob(entry.entity, entry.chance);
		NetworkHandler.sendClientPacket(EnumPackets.EVENT_CREATE, newEvent.writeNBT(new NBTTagCompound()), world.provider.getDimension());
		return newEvent;
	}
	
	public TitanManagerServer addWorld(World world)
	{
		if (world != null && !worlds.contains(world))
			worlds.add(world);
		
		return this;
	}
	
	public TitanManagerServer removeWorld(World world)
	{
		worlds.remove(world);
		return this;
	}
	
	public World getWorld(int dimension)
	{
		for(World world : worlds)
			if (dimension == world.provider.getDimension())
				return world;
		return null;
	}
	
	public void syncData()
	{
		TheTitans.debug("Server manager is syncing data to all players");
		NetworkHandler.sendClientPacket(EnumPackets.SYNC, writeNBT());
	}
	
	public void syncData(EntityPlayerMP player)
	{
		TheTitans.debug("Server manager is syncing data to player " + player.getName());
		NetworkHandler.sendClientPacket(EnumPackets.SYNC, writeNBT(player.dimension), player);
	}
	
	public void updatePlayerStats()
	{
		FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(player -> updatePlayerStats(player));
	}
	
	public void updatePlayerStats(EntityPlayer player)
	{
		int type = CommonProxy.manager.getStage();
		AttributeModifier modifierHealth = null;
		AttributeModifier modifierDamage = null;
		
		switch(type)
		{
			case 1:
				modifierHealth = new AttributeModifier(CommonProxy.manager.getHealthAttributeUUID(), "titans_extra_health", 1.0D, 1);
				modifierDamage = new AttributeModifier(CommonProxy.manager.getDamageAttributeUUID(), "titans_extra_damage", 1.0D, 0);
				break;
			case 2:
				modifierHealth = new AttributeModifier(CommonProxy.manager.getHealthAttributeUUID(), "titans_extra_health", 2.0D, 1);
				modifierDamage = new AttributeModifier(CommonProxy.manager.getDamageAttributeUUID(), "titans_extra_damage", 3.0D, 0);
				break;
			case 3:
				modifierHealth = new AttributeModifier(CommonProxy.manager.getHealthAttributeUUID(), "titans_extra_health", 3.0D, 1);
				modifierDamage = new AttributeModifier(CommonProxy.manager.getDamageAttributeUUID(), "titans_extra_damage", 5.0D, 0);
				break;
		}
		
		if (type > 0)
		{
			IAttributeInstance maxHealth = player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
			IAttributeInstance attackDamage = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			if (maxHealth.hasModifier(modifierHealth))
				maxHealth.removeModifier(modifierHealth);
			maxHealth.applyModifier(modifierHealth);
			
			if (attackDamage.hasModifier(modifierDamage))
				attackDamage.removeModifier(modifierDamage);
			attackDamage.applyModifier(modifierDamage);
		}
	}
	
	public int getStage()
	{
		return 0 + (defeatedWitherzilla ? 1 : 0) + (defeatedWither ? 1 : 0) + (defeatedDragon ? 1 : 0);
	}
	
	public UUID getHealthAttributeUUID()
	{
		return attributeHealthUUID;
	}
	
	public UUID getDamageAttributeUUID()
	{
		return attributeDamageUUID;
	}
	
	public void loadFile()
	{
		TheTitans.debug("Loading world data for the titan manager...");
		String dataFile = FileUtil.getWorldFolderPath() + FileUtil.getWorldFolderName() + "thetitans" + File.separator + "titan_manager.dat";
		NBTTagCompound nbt = new NBTTagCompound();
		
		try
		{
			if (new File(dataFile).exists())
				nbt = CompressedStreamTools.readCompressed(new FileInputStream(dataFile));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			TheTitans.error("A critical error has occured loading titan_manager.dat. Skipping...");
		}
		
		readNBT(nbt);
		
		TheTitans.debug("Finished loading data for the titan manager");
	}
	
	public void saveFile()
	{
		TheTitans.debug("Saving world data from the titan manager...");
		String dataFolder = FileUtil.getWorldFolderPath() + FileUtil.getWorldFolderName() + "thetitans" + File.separator;
		String dataFile = dataFolder + "titan_manager.dat";
		NBTTagCompound nbt = writeNBT();
		
		try
		{
			File folder = new File(dataFolder);
			if (!folder.exists()) folder.mkdirs();
			FileOutputStream stream = new FileOutputStream(dataFile);
			CompressedStreamTools.writeCompressed(nbt, stream);
			stream.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			TheTitans.error("A critical error has occured saving to titan_manager.dat. Data was not saved");
		}
		
		TheTitans.debug("Finished saving data for the titan manager");
	}
}
