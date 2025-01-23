package net.mrbt0907.thetitans.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.mrbt0907.thetitans.TheTitans;
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
		
	}

	@Override
	public void onServerRecieved(int commandID, NBTTagCompound nbt, EntityPlayerMP player)
	{
		
	}
}
