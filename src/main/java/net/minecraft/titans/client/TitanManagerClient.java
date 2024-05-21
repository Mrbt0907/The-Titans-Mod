package net.minecraft.titans.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.manager.EventObject;
import net.minecraft.titans.manager.TitanManager;
import net.minecraft.titans.network.EnumPackets;
import net.minecraft.titans.network.NetworkHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class TitanManagerClient extends TitanManager
{
	private final Minecraft mc;
	
	public TitanManagerClient()
	{
		mc = Minecraft.getMinecraft();
		TheTitans.debug("Initialized Titan Manager for Client");
	}
	
	public void tick()
	{
		super.tick();
		
		List<EventObject> events = new ArrayList<EventObject>(this.events.values());
		EventObject event;
		
		for (int i = 0; i < events.size(); i++)
		{
			event = events.get(i);
			
			if (event.isDead)
			{
				removeEvent(event.getUniqueID());
				continue;
			}
			
			event.tickClient();
		}
	}
	
	public void tickGui(Phase phase, float delta)
	{
	}
	
	@Override
	public void onNetworkMessage(int index, NBTTagCompound nbt)
	{
		switch (index)
		{
			case 0:
				TheTitans.debug("Client manager recieved nbt data from the server");
				readNBT(nbt);
				break;
			case 1:
				EventObject event = new EventObject(DimensionManager.getWorld(nbt.getInteger("dimension")));
				event.readNBT(nbt);
				events.put(event.getUniqueID(), event);
				TheTitans.debug("Client manager created event " + event.getName() + " at " + event.pos.posX + ", " + event.pos.posY + ", " + event.pos.posZ + "  (type:" + event.getType() + ")");
				break;
			case 2:
				UUID id = nbt.getUniqueId("uuid");
				events.get(id).readNBT(nbt);
				break;
			case 3:
				UUID uuid = nbt.getUniqueId("uuid");
				removeEvent(uuid);
				TheTitans.debug("Client manager removed event object " + uuid);
				break;
			default:
				TheTitans.error("Client manager recieved an invalid network message (type:" + index + ")");
		}
	}
	
	public void sync()
	{
		TheTitans.debug("Client manager sent a sync request");
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setUniqueId("uuid", mc.player.getUniqueID());
		NetworkHandler.sendServerPacket(EnumPackets.SYNC, nbt);
	}
	
	public void reset()
	{
		super.reset();
	}

	
}
