package net.minecraft.titans.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.manager.EventObject.EventMobEntryPre;

public enum EnumEvents
{
	ZOMBIE_HOARD("zombie_hoard", 0, 50.0F, 100, new EventMobEntryPre(EntityZombie.class, 0.25F), new EventMobEntryPre(EntityHusk.class, 0.25F)),
	CREEPER_HOARD("creeper_hoard", 1, 50.0F, 100, new EventMobEntryPre(EntityCreeper.class, 1.0F));
	
	private static final Map<String, EnumEvents> events = new HashMap<String, EnumEvents>();
	private String unlocalizedName;
	private int type;
	private double distance;
	private int neededKills;
	private List<EventMobEntryPre> spawnList = new ArrayList<EventMobEntryPre>();
	
	EnumEvents(String unlocalizedName, int type, double distance, int killsNeeded, EventMobEntryPre... entries)
	{
		this.unlocalizedName = unlocalizedName;
		this.distance = distance;
		neededKills = killsNeeded;
		
		for(EventMobEntryPre entry : entries)
			spawnList.add(entry);
	}
	
	public EventObject addMobs(EventObject event)
	{
		for(EventMobEntryPre entry : spawnList)
			event.addMob(entry.entity, entry.chance);
		
		return event;
	}
	
	public int getType()
	{
		return type;
	}
	
	public double getEventRange()
	{
		return distance;
	}
	
	public int getKillsNeeded()
	{
		return neededKills;
	}
	
	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}
	
	static
	{
		for (EnumEvents event : EnumEvents.values())
			events.put(event.unlocalizedName, event);
	}
	
	public static EnumEvents getEvent(String unlocalizedName)
	{
		return events.get(unlocalizedName);
	}
	
	public static void addEvents(EnumEvents... types)
	{
		boolean truth;
		for(EnumEvents type : types)
		{
			truth = true;
			
			for (EnumEvents event : events.values())
			{
				if(type == null)
				{
					TheTitans.error("Failed to add type as it was a null EnumEvent. Skipping...");
					truth = false;
					break;
				}
				if (event.getType() == type.getType())
				{
					TheTitans.error("Failed to add a duplicate EnumEvent as " + type.toString() + " has the same index as " + event.toString() + ". Skipping...");
					truth = false;
					break;
				}
			}
			
			if (truth)
				events.put(type.unlocalizedName, type);
		}
	}
	
	public static boolean contains(String unlocalizedName)
	{
		return events.containsKey(unlocalizedName);
	}
	
	public static Set<String> getNames()
	{
		return events.keySet();
	}
	
	public static Map<String, EnumEvents> getEvents()
	{
		return events;
	}
}
