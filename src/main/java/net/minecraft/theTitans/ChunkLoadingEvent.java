package net.minecraft.theTitans;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
public class ChunkLoadingEvent implements LoadingCallback
{

	public static ChunkLoadingEvent instance;
	public static Map<Entity, Ticket> ticketList = new HashMap<Entity, Ticket>();
	public static Map<Entity, ArrayList<ChunkCoordIntPair>> chunkList = new HashMap<Entity, ArrayList<ChunkCoordIntPair>>();
	public static boolean hasReportedIssue = false;
	public static void init()
	{
		instance = new ChunkLoadingEvent();
		MinecraftForge.EVENT_BUS.register(instance);
		ForgeChunkManager.setForcedChunkLoadingCallback(TheTitans.modInstance, instance);
	}

	public static void updateLoaded(Entity mob)
	{
		Ticket ticket;
		ArrayList<ChunkCoordIntPair> dragonChunks = new ArrayList<ChunkCoordIntPair>();
		for (int xx = ((int) mob.posX / 16) - 2; xx <= ((int) mob.posX / 16) + 2; xx++)
		for (int zz = ((int) mob.posZ / 16) - 2; zz <= ((int) mob.posZ / 16) + 2; zz++)
		dragonChunks.add(new ChunkCoordIntPair(xx, zz));
		if (chunkList.containsKey(mob) && dragonChunks.hashCode() == chunkList.get(mob).hashCode())
		return;
		if (ticketList.containsKey(mob))
		{
			ticket = ticketList.get(mob);
			ForgeChunkManager.releaseTicket(ticket);
		}

		ticket = ForgeChunkManager.requestTicket(TheTitans.modInstance, mob.worldObj, ForgeChunkManager.Type.ENTITY);
		if (ticket != null)
		{
			ticket.bindEntity(mob);
			ticket.setChunkListDepth(25);
			ticketList.put(mob, ticket);
		}

		for (ChunkCoordIntPair pos : dragonChunks)
		ForgeChunkManager.forceChunk(ticket, pos);
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


