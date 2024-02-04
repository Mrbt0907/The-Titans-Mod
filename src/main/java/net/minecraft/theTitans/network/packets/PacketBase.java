package net.minecraft.theTitans.network.packets;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
public class PacketBase implements IMessage, IMessageHandler<PacketBase, IMessage>
{
	protected int test;
	public PacketBase() 
	{

	}


	public PacketBase(int value)
	{
		test = value;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		test = buffer.readInt();
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(test);
	}

	@Override
	public PacketBase onMessage(PacketBase message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		return onClientMessage(message, ctx);
		else
		return onServerMessage(message, ctx);
	}

	@SideOnly(Side.CLIENT)
	protected PacketBase onClientMessage(PacketBase message, MessageContext ctx)
	{
		return null;
	}

	protected PacketBase onServerMessage(PacketBase message, MessageContext ctx)
	{
		return null;
	}
}


