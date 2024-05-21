package net.minecraft.titans.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.endermanofdoom.mac.util.TranslateUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.titans.ClientProxy;
import net.minecraft.titans.CommonProxy;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.entity.EntityMultiPart;
import net.minecraft.titans.entity.god.EntityWitherzilla;
import net.minecraft.titans.entity.titan.EntityTitan;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GameEventHandler 
{
	private List<UUID> renderedTitans = new ArrayList<UUID>();
	
	@SubscribeEvent
	public void onMobSpawn(EntityJoinWorldEvent event)
	{
		if (!event.getWorld().isRemote)
		{
			TheTitans.checkServerManager();
			
			if (event.getEntity() instanceof EntityWitherzilla && CommonProxy.manager.hasWitherzilla())
				event.setCanceled(true);
			
			CommonProxy.manager.onEntityJoined(event.getEntity());
		}
	}
	
	@SubscribeEvent
	public void onExplosion(Detonate event)
	{
		Iterator<Entity> itr = event.getAffectedEntities().iterator();
        while (itr.hasNext())
            if (itr.next() instanceof EntityMultiPart) itr.remove();
	}
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		CommonProxy.manager.onPlayerJoined(event.player);
	}
	
	@SubscribeEvent
	public void onPlayerSpawn(PlayerRespawnEvent event)
	{
		CommonProxy.manager.updatePlayerStats(event.player);
		event.player.setHealth((float) event.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
	}
	
	@SubscribeEvent
	public void onEntityDamaged(LivingHurtEvent event)
	{
		float damage = event.getAmount();
		if (damage > 100.0F)
		{
			float toughness = (float) (1.0D + event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue() * 0.25D);
			damage = MathHelper.clamp(damage / toughness - event.getEntityLiving().getTotalArmorValue(), 1.0F, Float.MAX_VALUE);
			event.setAmount(damage);
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onFogRender(RenderFogEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.world != null && (mc.world.provider.getDimension() == 0 || mc.world.provider.getDimension() == TheTitans.DIMENSION_VOID_ID))
			event.getRenderer().farPlaneDistance = 10000.0F;
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onEntityRendered(RenderLivingEvent.Pre<EntityLivingBase> event)
	{
		EntityLivingBase entity = event.getEntity();
		UUID uuid = entity.getUniqueID();
		if (entity instanceof EntityTitan && !renderedTitans.contains(uuid))
			renderedTitans.add(uuid);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onWorldLastRender(RenderWorldLastEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		boolean shouldRender = net.minecraftforge.client.MinecraftForgeClient.getRenderPass() == -1;
		if (shouldRender)
		{
			GlStateManager.pushMatrix();
			mc.entityRenderer.enableLightmap();
			RenderHelper.enableStandardItemLighting();
			mc.world.loadedEntityList.forEach(entity ->
			{
				if (entity instanceof EntityTitan && !renderedTitans.contains(entity.getUniqueID()))
				{
		            mc.getRenderManager().renderEntityStatic(entity, event.getPartialTicks(), true);
				}
			});

            RenderHelper.disableStandardItemLighting();
            mc.entityRenderer.disableLightmap();
			GlStateManager.popMatrix();
			
			renderedTitans.clear();
		}
	}
	
	@SubscribeEvent
	public void onMobDeath(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		Entity killer = event.getSource().getTrueSource();
		
		if (entity.isServerWorld())
		{
			CommonProxy.manager.onEntityKilled(entity);
			
			if (killer != null)
			{
				switch(TheTitans.getClassName(entity))
				{
					case "net.minecraft.entity.boss.EntityDragon":
						if (!CommonProxy.manager.defeatedDragon)
						{
							CommonProxy.manager.defeatedDragon = true;
							FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(player -> TranslateUtil.sendChat(player, "dialog.progression.kill.dragon", killer.getName()));
							CommonProxy.manager.syncData();
							CommonProxy.manager.updatePlayerStats();
							CommonProxy.manager.saveFile();
						}
						break;
					case "net.minecraft.entity.boss.EntityWither":
						if (!CommonProxy.manager.defeatedWither)
						{
							CommonProxy.manager.defeatedWither = true;
							FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(player -> TranslateUtil.sendChat(player, "dialog.progression.kill.wither", killer.getName()));
							CommonProxy.manager.syncData();
							CommonProxy.manager.updatePlayerStats();
							CommonProxy.manager.saveFile();
						}
						break;
					case "net.minecraft.titans.entity.god.EntityWitherzilla":
						if (!CommonProxy.manager.defeatedWitherzilla)
						{
							CommonProxy.manager.defeatedWitherzilla = true;
							CommonProxy.manager.syncData();
							CommonProxy.manager.updatePlayerStats();
							CommonProxy.manager.saveFile();
						}
						break;
				}
				
				if (entity.isChild() && killer.getName().equals("Mrbt0907"))
					FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(player -> TranslateUtil.sendChat(player, "dialog.mrbt0907.finally", killer.getName(), entity.getName()));
			}
		}
	}
	
	@SubscribeEvent
	public void playerLoggedIn(PlayerLoggedInEvent event)
	{
		if (event.player instanceof EntityPlayerMP)
			CommonProxy.manager.syncData((EntityPlayerMP) event.player);
	}
	
	@SubscribeEvent
	public void tickClient(ClientTickEvent event)
	{
		if (event.phase == Phase.START)
			ClientProxy.manager.tick();
	}
	
	@SubscribeEvent
	public void tickRenderScreen(RenderTickEvent event)
	{
			ClientProxy.manager.tickGui(event.phase, event.renderTickTime);
	}
	
	@SubscribeEvent
	public void worldSave(WorldEvent.Save event)
	{
		if (!event.getWorld().isRemote)
		{
			TheTitans.checkServerManager();
			CommonProxy.manager.saveFile();
		}
	}
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load event)
	{
		if (!event.getWorld().isRemote)
		{
			TheTitans.checkServerManager();
			CommonProxy.manager.addWorld(event.getWorld());
		}
	}
	
	@SubscribeEvent
	public void worldUnload(WorldEvent.Unload event)
	{
		if (!event.getWorld().isRemote)
		{
			TheTitans.checkServerManager();
			CommonProxy.manager.removeWorld(event.getWorld());
		}
		else
		{
			TheTitans.info("Resetting Manager");
			ClientProxy.manager.reset();
		}
	}
	
	@SubscribeEvent
	public void tickServer(ServerTickEvent event)
	{
		if (event.phase == Phase.START)
			CommonProxy.manager.tick();
	}
}
