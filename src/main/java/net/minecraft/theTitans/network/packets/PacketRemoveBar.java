package net.minecraft.theTitans.network.packets;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.TheTitans;
public class PacketRemoveBar implements IMessage, IMessageHandler<PacketRemoveBar, IMessage>
{
	private String uuid;
	public PacketRemoveBar() 
	{

	}


	public PacketRemoveBar(String uuid)
	{
		this.uuid = uuid;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		uuid = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, uuid);
	}

	@Override
	public PacketRemoveBar onMessage(PacketRemoveBar message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		return onClientMessage(message, ctx);
		else
		return onServerMessage(message, ctx);
	}

	@SideOnly(Side.CLIENT)
	protected PacketRemoveBar onClientMessage(PacketRemoveBar message, MessageContext ctx)
	{
		TheTitans.debug("Removing bar: " + message.uuid);
		ClientProxy.ingameGui.queueRemove(message.uuid);
		return null;
	}

	protected PacketRemoveBar onServerMessage(PacketRemoveBar message, MessageContext ctx)
	{
		return null;
	}
}


