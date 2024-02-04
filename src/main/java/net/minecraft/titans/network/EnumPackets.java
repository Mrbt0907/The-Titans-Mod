package net.minecraft.titans.network;

import java.util.ArrayList;
import java.util.List;

public enum EnumPackets
{
	SYNC(0), EVENT_CREATE(1), EVENT_UPDATE(2), EVENT_REMOVE(3);
	
	private int type;
	protected static final List<EnumPackets> packets = new ArrayList<EnumPackets>(); 
	
	EnumPackets(int type)
	{
		this.type = type;
	}
	
	static
	{
		for (EnumPackets packet: EnumPackets.values())
			packets.add(packet);
	}
	
	public int getIndex()
	{
		return type;
	}
	
	public static EnumPackets addType(EnumPackets packet)
	{
		if (packet != null)
		{
			for (EnumPackets type : packets)
				if (packet.getIndex() == type.getIndex())
					return packet;
			packets.add(packet);
		}
		return packet;
	}
	
	public static EnumPackets getType(int index)
	{
		for (EnumPackets packet : packets)
			if (packet.getIndex() == index)
				return packet;
		return null;
	}
}
