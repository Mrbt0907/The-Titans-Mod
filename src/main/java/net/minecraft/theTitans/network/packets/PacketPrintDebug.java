package net.minecraft.theTitans.network.packets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.theTitans.TheTitans;
public class PacketPrintDebug implements IMessage, IMessageHandler<PacketPrintDebug, IMessage>
{
	public PacketPrintDebug() 
	{

	}


	@Override
	public void fromBytes(ByteBuf buffer) 
	{

	}


	@Override
	public void toBytes(ByteBuf buffer) 
	{

	}


	@Override
	public PacketPrintDebug onMessage(PacketPrintDebug message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		return onClientMessage(message, ctx);
		else
		return onServerMessage(message, ctx);
	}

	@SideOnly(Side.CLIENT)
	protected PacketPrintDebug onClientMessage(PacketPrintDebug message, MessageContext ctx)
	{
		TheTitans.logger.printErrors();
		return null;
	}

	protected PacketPrintDebug onServerMessage(PacketPrintDebug message, MessageContext ctx)
	{
		return null;
	}
}


