package net.mrbt0907.thetitans.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.entity.god.EntityWitherzilla;
import net.mrbt0907.util.interfaces.INetworkReciever;

public class NetworkReciever implements INetworkReciever
{
	@Override
	public String getID()
	{
		return TheTitans.MODID + "_main";
	}

	@Override
	public void onClientRecieved(int commandID, NBTTagCompound nbt)
	{
		World world = FMLClientHandler.instance().getClient().world;
		switch(commandID)
		{
			case 0:
				Entity entity = world.getEntityByID(nbt.getInteger("id"));
				if (entity instanceof EntityWitherzilla)
					((EntityWitherzilla)entity).updateTargets(nbt.getCompoundTag("targets"));
				break;
		}
	}

	@Override
	public void onServerRecieved(int commandID, NBTTagCompound nbt, EntityPlayerMP player)
	{
		
	}
}
