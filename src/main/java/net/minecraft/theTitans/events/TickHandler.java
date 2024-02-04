package net.minecraft.theTitans.events;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.theTitans.SoundHandler;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.theTitans.items.ItemTitanArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
public class TickHandler
{
	private static Minecraft mc;
	private int clientTicks;
	private int serverTicks;
	public TickHandler(Minecraft minecraft)
	{
		mc = minecraft;
	}

	@SubscribeEvent
	public void onServerTick(ServerTickEvent event)
	{
		for (int i = 0; i < EventObject.instances.size(); i++)
		EventObject.instances.get(i).onUpdate(event.side);
		if (EventData.deleteWorld != null)
		EventData.queueDeleteWorld("thetitans.retry.b");
		serverTicks++;
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(OnConfigChangedEvent event)

	{
			
		if(event.modID.equalsIgnoreCase(TheTitans.MODID))
		{
			TheTitans.config.save();
			TitanConfig.load();
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientTick(ClientTickEvent event)
	{
		for (int i = 0; i < EventObject.instances.size(); i++)
		EventObject.instances.get(i).onUpdate(event.side);
		if (mc.theWorld != null && !mc.isGamePaused())
		for (Object object : mc.theWorld.loadedEntityList)
		if (object instanceof EntityLivingBase)
		{
			EntityLivingBase entity = (EntityLivingBase) object;
			for (int i = 1; i < 5; i++)
			if (entity.getEquipmentInSlot(i) != null && entity.getEquipmentInSlot(i).getItem() instanceof ItemTitanArmor)
			{
				((ItemTitanArmor)entity.getEquipmentInSlot(i).getItem()).onSoundTick(mc.theWorld, entity, entity.getEquipmentInSlot(i));
				break;
			}
		}

		if (!mc.isGamePaused())
		for (int i = 0; i < SoundHandler.sounds.size(); i++)
		{
			SoundHandler.sounds.get(i).update();
			if (SoundHandler.sounds.get(i).isDonePlaying())
			{
				SoundHandler.sounds.remove(i);
				i--;
			}
		}

		if (!mc.isIntegratedServerRunning() && EventData.deleteWorld != null)
		MinecraftForge.EVENT_BUS.post(new WorldEvent.Unload(EventData.deleteWorld));
		clientTicks++;
	}
}


