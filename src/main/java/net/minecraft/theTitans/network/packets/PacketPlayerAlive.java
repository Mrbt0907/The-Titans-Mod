package net.minecraft.theTitans.network.packets;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
public class PacketPlayerAlive implements IMessage, IMessageHandler<PacketPlayerAlive, IMessage>
{
	protected String username;
	public PacketPlayerAlive() 
	{

	}


	public PacketPlayerAlive(String username)
	{
		this.username = username;
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		username = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, username);
	}

	@Override
	public PacketPlayerAlive onMessage(PacketPlayerAlive message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		return onClientMessage(message, ctx);
		else
		return onServerMessage(message, ctx);
	}

	@SideOnly(Side.CLIENT)
	protected PacketPlayerAlive onClientMessage(PacketPlayerAlive message, MessageContext ctx)
	{
		if (message.username.equals(Minecraft.getMinecraft().thePlayer.getCommandSenderName()))
		{
			Minecraft.getMinecraft().thePlayer.isDead = false;
			Minecraft.getMinecraft().thePlayer.deathTime = -1;
			return null;
		}

		for (Object object : Minecraft.getMinecraft().theWorld.playerEntities)
		if (object != null && object instanceof EntityPlayer)
		if (((EntityPlayer)object).getCommandSenderName().equals(message.username))
		{
			((EntityPlayer)object).isDead = false;
			((EntityPlayer)object).deathTime = -1;
			break;
		}

		return null;
	}

	protected PacketPlayerAlive onServerMessage(PacketPlayerAlive message, MessageContext ctx)
	{
		return null;
	}
}


