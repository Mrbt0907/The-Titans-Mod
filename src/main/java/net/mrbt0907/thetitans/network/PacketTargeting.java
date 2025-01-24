package net.mrbt0907.thetitans.network;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.entity.god.EntityWitherzilla;

public class PacketTargeting
{
	public static void sendTargets(World world, EntityWitherzilla witherzilla, List<EntityLivingBase> targets)
	{
		NBTTagCompound nbt = new NBTTagCompound(), nbtTargets = new NBTTagCompound();
		nbt.setInteger("id", witherzilla.getEntityId());
		for (EntityLivingBase target : targets)
			nbtTargets.setInteger("entity_" + target.getEntityId(), target.getEntityId());
		nbt.setTag("targets", nbtTargets);
		TheTitans.NETWORK.sendToClients(0, nbt, world);
	}
}
