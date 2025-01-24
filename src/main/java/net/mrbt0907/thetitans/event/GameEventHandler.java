package net.mrbt0907.thetitans.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.entity.EntityMultiPart;
import net.mrbt0907.thetitans.entity.titan.EntityTitan;
import net.mrbt0907.util.util.TranslateUtil;

public class GameEventHandler 
{
	private static List<UUID> renderedTitans = new ArrayList<UUID>();
	
	@SubscribeEvent
	public static void onMobSpawn(EntityJoinWorldEvent event)
	{
		
	}
	
	@SubscribeEvent
	public static void onExplosion(Detonate event)
	{
		Iterator<Entity> itr = event.getAffectedEntities().iterator();
        while (itr.hasNext())
            if (itr.next() instanceof EntityMultiPart) itr.remove();
	}
	
	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent event)
	{
	}
	
	@SubscribeEvent
	public static void onPlayerSpawn(PlayerRespawnEvent event)
	{
	}
	
	@SubscribeEvent
	public static void onEntityDamaged(LivingHurtEvent event)
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
	public static void onFogRender(RenderFogEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.world != null && (mc.world.provider.getDimension() == 0 || mc.world.provider.getDimension() == TheTitans.DIMENSION_VOID_ID))
			event.getRenderer().farPlaneDistance = 10000.0F;
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onEntityRendered(RenderLivingEvent.Pre<EntityLivingBase> event)
	{
		EntityLivingBase entity = event.getEntity();
		UUID uuid = entity.getUniqueID();
		if (entity instanceof EntityTitan && !renderedTitans.contains(uuid))
			renderedTitans.add(uuid);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onWorldLastRender(RenderWorldLastEvent event)
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
	public static void onMobDeath(LivingDeathEvent event)
	{
		EntityLivingBase entity = event.getEntityLiving();
		Entity killer = event.getSource().getTrueSource();
		
		if (entity.isServerWorld())
		{
			
			if (killer != null)
			{
				if (entity.isChild() && killer.getName().equals("Mrbt0907"))
					FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().forEach(player -> TranslateUtil.sendChat(player, "dialog.mrbt0907.finally", killer.getName(), entity.getName()));
			}
		}
	}
	
	@SubscribeEvent
	public static void playerLoggedIn(PlayerLoggedInEvent event)
	{
	}
	
	@SubscribeEvent
	public static void tickClient(ClientTickEvent event)
	{
		if (event.phase == Phase.START);
	}
	
	@SubscribeEvent
	public static void tickRenderScreen(RenderTickEvent event)
	{
	}
	
	@SubscribeEvent
	public static void worldSave(WorldEvent.Save event)
	{
	}
	
	@SubscribeEvent
	public static void worldLoad(WorldEvent.Load event)
	{
	}
	
	@SubscribeEvent
	public static void worldUnload(WorldEvent.Unload event)
	{
	}
	
	@SubscribeEvent
	public static void tickServer(ServerTickEvent event)
	{
		if (event.phase == Phase.START);
	}
}
