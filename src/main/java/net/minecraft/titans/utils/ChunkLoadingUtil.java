package net.minecraft.titans.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.titans.TheTitans;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;


public class ChunkLoadingUtil implements LoadingCallback
{
	public static ChunkLoadingUtil instance;
	public static Map<Entity, Ticket> ticketList = new HashMap<Entity, Ticket>();
	public static Map<Entity, ArrayList<ChunkPos>> chunkList = new HashMap<Entity, ArrayList<ChunkPos>>();
	
	public static void preInit()
	{
		instance = new ChunkLoadingUtil();
		MinecraftForge.EVENT_BUS.register(instance);
		ForgeChunkManager.setForcedChunkLoadingCallback(TheTitans.instance, instance);
	}

	public static void updateLoaded(Entity mob)
	{
		Ticket ticket;
		ArrayList<ChunkPos> dragonChunks = new ArrayList<>();
		for (int xx = ((int) mob.posX / 16) - 2; xx <= ((int) mob.posX / 16) + 2; xx++)
		for (int zz = ((int) mob.posZ / 16) - 2; zz <= ((int) mob.posZ / 16) + 2; zz++)
			
		dragonChunks.add(new ChunkPos(xx, zz));
		
		if (chunkList.containsKey(mob) && dragonChunks.hashCode() == chunkList.get(mob).hashCode())
			return;
		
		if (ticketList.containsKey(mob))
		{
			ticket = ticketList.get(mob);
			ForgeChunkManager.releaseTicket(ticket);
		}
		ticket = ForgeChunkManager.requestTicket(TheTitans.instance, mob.world, ForgeChunkManager.Type.ENTITY);
		
		if (ticket != null)
		{
			ticket.bindEntity(mob);
			ticket.setChunkListDepth(25);
			ticketList.put(mob, ticket);
		}

		for (int i = 0; i < dragonChunks.size(); i++)
			ForgeChunkManager.forceChunk(ticket, dragonChunks.get(i));
		
		chunkList.put(mob, dragonChunks);
	}

	public static void stopLoading(Entity guardian)
	{
		if (!ticketList.containsKey(guardian))
		return;
		Ticket ticket = ticketList.get(guardian);
		
		ForgeChunkManager.releaseTicket(ticket);
		
		ticketList.remove(guardian);
	}

	public void ticketsLoaded(List<Ticket> tickets, World world)
	{
		if (!tickets.isEmpty())
			for (Ticket ticket : tickets)
				ForgeChunkManager.releaseTicket(ticket);
	}
}
