package net.minecraft.titans.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.titans.TheTitans;
import net.minecraftforge.common.DimensionManager;


public abstract class TitanManager
{
	protected long ticks = 0L;
	public boolean defeatedDragon;
	public boolean defeatedWither;
	public boolean defeatedSkully;
	public boolean defeatedWitherzilla;
	public final List<Integer> defeatedEvents = new ArrayList<Integer>();
	protected final Map<UUID, EventObject> events = new HashMap<UUID, EventObject>();
	
	public void readNBT(NBTTagCompound nbt)
	{
		TheTitans.debug("Reading nbt data...");
		EventObject event;
		NBTTagCompound entryNBT;
		NBTTagCompound nbtEvents = null;
		if (nbt.hasKey("events"))
			nbtEvents = (NBTTagCompound) nbt.getTag("events");
		NBTTagCompound nbtDefeatedEvents = null;
		if (nbt.hasKey("defeatedEvents"))
			nbtDefeatedEvents = (NBTTagCompound) nbt.getTag("defeatedEvents");
		
		defeatedDragon = nbt.getBoolean("defeatedDragon");
		defeatedWither = nbt.getBoolean("defeatedWither");
		defeatedSkully = nbt.getBoolean("defeatedSkully");
		defeatedWitherzilla = nbt.getBoolean("defeatedWitherzilla");
		TheTitans.debug("Read variables from nbt");
		
		if (nbtEvents != null)
			for (String key : nbtEvents.getKeySet())
			{
				entryNBT = (NBTTagCompound) nbtEvents.getTag(key);
				event = new EventObject(DimensionManager.getWorld(entryNBT.getInteger("dimension")));
				event.readNBT(entryNBT);
				events.put(event.getUniqueID(), event);

				TheTitans.debug("Reading event_" + event.getUniqueID() + " from nbt...");
			}
		
		if (nbtDefeatedEvents != null)
			for (String key : nbtDefeatedEvents.getKeySet())
				defeatedEvents.add(nbtDefeatedEvents.getInteger(key));

		TheTitans.debug("Read defeated events from nbt");		
		TheTitans.debug("Successfully read nbt data!");
	}
	
	public void tick()
	{
		ticks++;
	}
	
	public void onEntityJoined(Entity entity)
	{
		for (EventObject event : events.values())
			if(event.onEntityJoined(entity))
				break;
	}
	
	public void onEntityKilled(Entity entity)
	{
		for (EventObject event : events.values())
			if (event.onEntityKilled(entity))
				break;
	}
	
	public EventObject getEvent(UUID uuid)
	{
		return events.get(uuid);
	}
	
	public List<EventObject> getEvents()
	{
		return new ArrayList<EventObject>(events.values());
	}
	
	protected TitanManager removeEvent(UUID uuid)
	{
		EventObject event = events.get(uuid); 
		event.reset();
		if (!defeatedEvents.contains(event.getType())) 
			defeatedEvents.add(event.getType());
		events.remove(uuid);
		return this;
	}
	
	public abstract void onNetworkMessage(int index, NBTTagCompound nbt); 
	
	public void reset()
	{
		defeatedDragon = false;
		defeatedWither = false; 
		defeatedSkully = false; 
		defeatedWitherzilla = false; 
		defeatedEvents.clear();
		events.clear();
	}
}
