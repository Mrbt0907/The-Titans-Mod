package net.minecraft.theTitans.network.packets;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.theTitans.events.EventData;
public class PacketWorldData implements IMessage, IMessageHandler<PacketWorldData, IMessage>
{
	protected String name;
	protected boolean truth;
	
	public PacketWorldData() {}


	public PacketWorldData(String name, boolean truth)
	{
		this.name = name;
		this.truth = truth;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		name = ByteBufUtils.readUTF8String(buffer);
		truth = buffer.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, name);
		buffer.writeBoolean(truth);
	}

	@Override
	public PacketWorldData onMessage(PacketWorldData message, MessageContext ctx)
	{
		if (ctx.side.isClient())
			return onClientMessage(message, ctx);
		else
			return onServerMessage(message, ctx);
	}

	@SideOnly(Side.CLIENT)
	protected PacketWorldData onClientMessage(PacketWorldData message, MessageContext ctx)
	{
		if (Minecraft.getMinecraft().theWorld != null)
			EventData.setBool(Minecraft.getMinecraft().theWorld, message.name, message.truth);
		
		return null;
	}

	protected PacketWorldData onServerMessage(PacketWorldData message, MessageContext ctx)
	{
		return null;
	}
}


