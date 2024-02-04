package net.minecraft.theTitans;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.*;
import net.minecraft.potion.Potion;
import net.minecraft.profiler.Profiler;
import net.minecraft.theTitans.api.GuiEntry;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.theTitans.events.EventObject;
import net.minecraft.theTitans.items.ItemAdminiumArmor;
import net.minecraft.theTitans.items.ItemDemontiumArmor;
import net.minecraft.theTitans.items.ItemHarcadiumArmor;
import net.minecraft.theTitans.items.ItemTitanArmor;
import net.minecraft.theTitans.items.ItemVoidArmor;
import net.minecraft.theTitans.items.ItemWithireniumArmor;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.mrbt0907.utils.Maths;
import java.util.List;
import org.lwjgl.opengl.GL11;
public class TitanBossBarGui extends Gui
{
	private Minecraft mc;
	private Profiler profiler;
	public String removeUUID;
	public TitanBossBarGui(Minecraft mc)
	{
		this.mc = mc;
		if (mc != null)
		profiler = mc.mcProfiler;
		else
		profiler = null;
	}

	public void queueRemove(String uuid)
	{
		removeUUID = uuid;
	}

	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Pre event)
	{
		if (event.type != ElementType.BOSSHEALTH)
		return;
		if (mc.playerController.enableEverythingIsScrewedUpMode())
		return;
		boolean isCanceled = event.isCanceled();
		profiler.startSection("titanOnRenderOverlay_Health");
		if (isCanceled)
		event.setCanceled(false);
		FontRenderer fr = mc.fontRenderer;
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		String outstring = null; 
		if (GuiEntry.instances.isEmpty() || !GuiEntry.instances.get(0).getEntry().equals(mc.thePlayer))
		{
			GuiEntry.instances.clear();
			GuiEntry.create(mc.thePlayer, mc.thePlayer.getUniqueID(), false);
		}

		profiler.endStartSection("titanOnRenderOverlay_Boss");
		int offset = 0;
		int offsetB = 0;
		for (int i = 0; i < GuiEntry.instances.size(); i++)
		{
			if (GuiEntry.instances.get(i).isUUID(removeUUID))
			{
				removeUUID = null;
				GuiEntry.instances.remove(i);
			}

			else
			{
				GuiEntry.instances.get(i).onUpdate();
				if (i == 0)
					if (TitanConfig.useTitanBar)
					{
						boolean canRender = false;
						for (int ii = 0; ii < 4; ii++)
							if (TitanConfig.useTitanBarPerm)
							{
								canRender = true;
								break;
							}
							else if (mc.thePlayer.getCurrentArmor(i) != null && mc.thePlayer.getCurrentArmor(i).getItem() instanceof ItemTitanArmor)
								if (!mc.thePlayer.capabilities.isCreativeMode)
								{
									canRender = true;
									break;
								}
	
	
						if (GuiIngameForge.renderArmor == canRender)
							GuiIngameForge.renderArmor = !canRender;
						if (GuiIngameForge.renderHealth == canRender)
							GuiIngameForge.renderHealth = !canRender;
						
						if (canRender)
							renderMainPlayerBar(fr, GuiEntry.instances.get(i));
					}
					else
					{
						if (GuiIngameForge.renderArmor == false)
							GuiIngameForge.renderArmor = true;
						if (GuiIngameForge.renderHealth == false)
							GuiIngameForge.renderHealth = true;
					}
				else if (GuiEntry.instances.get(i).canRender(mc.thePlayer))
					if (GuiEntry.instances.get(i).getEntry() instanceof EntityLivingBase && TheTitans.checkFriendlyFire((EntityPlayer)mc.thePlayer, (EntityLivingBase)GuiEntry.instances.get(i).getEntry(), true))
					{
						if (offsetB < (int)(res.getScaledHeight() / 25))
						{
							renderPlayerBar(fr, offsetB, GuiEntry.instances.get(i));
							offsetB ++;
						}
					}
					else if (TitanConfig.textures == 1)
					{
						if (offset < (int)(res.getScaledHeight() / 45))
						{
							renderNewBar(fr, offset, GuiEntry.instances.get(i));
							offset ++;
						}
					}
			}
		}

		List<?> entities = mc.theWorld.loadedEntityList;
		List<EventObject> events =  EventObject.instances;
		if ((entities != null && !entities.isEmpty()) || (events != null && !events.isEmpty()))
		{
			if (TitanConfig.textures == 1)
			{
				EntityTitan titan;
				for (Object entity : entities)
				if (entity != null)
				{
					if (entity.equals(mc.thePlayer))
					continue;
					if (entity instanceof EntityTitan)
					{
						titan = (EntityTitan)entity;
						if(!titan.isDead && !GuiEntry.contains(titan.getUniqueID()))
						GuiEntry.create(titan, titan.getUniqueID(), true);
					}

					else if (!((Entity)entity).isDead && entity instanceof EntityLivingBase && TheTitans.checkFriendlyFire((EntityPlayer)mc.thePlayer, (EntityLivingBase)entity, true) && !GuiEntry.contains(((Entity)entity).getUniqueID()))
						GuiEntry.create(entity, ((Entity)entity).getUniqueID(), false);
					else if (Loader.isModLoaded("OreSpawn") && (entity instanceof danger.orespawn.Godzilla || entity instanceof danger.orespawn.TheKing || entity instanceof danger.orespawn.TheQueen) && !GuiEntry.contains(((Entity)entity).getUniqueID()))
						GuiEntry.create(entity, ((Entity)entity).getUniqueID(), false);
				}

				for (EventObject eve : events)
				if (eve != null && !GuiEntry.contains(eve.getBarUUID()))
				GuiEntry.create(eve, eve.getBarUUID(), null, false);
			}

			else
			{
				int u = 0;
				int v = 0;
				int y = -12;
				int barHeight = 24;
				int i = 0;
				offset = 0;
				Entity entity = null;
				for (int j = 0; j < entities.size(); ++j)
				{
					entity = (Entity)entities.get(j);
					if (entity != null && entity instanceof EntityTitan)
					{
						EntityTitan titan = (EntityTitan)entity;
						outstring = titan.getCommandSenderName();
						if  (outstring != null && !(titan.isDead || titan.deathTicks > 185) && !titan.getWaiting() && !titan.isInvisible())
						{
							if (titan instanceof EntitySnowGolemTitan)
							{
								barHeight = 27;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/snow_golem_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 185, barHeight, y, u, v, offset, y + 20, 12369084);
							}

							else if (titan instanceof EntitySlimeTitan)
							{
								barHeight = titan instanceof EntityMagmaCubeTitan ? 24 : 22;
								y += i + barHeight;
								if (titan instanceof EntityMagmaCubeTitan)
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/magma_cube_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 193, barHeight, y, u, v, offset, y + 14, 16579584);
								else
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/slime_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 191, barHeight, y, u, v, offset, y + 12, 5349438);
							}

							else if (titan instanceof EntitySilverfishTitan)
							{
								barHeight = 38;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/omegafish.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 183, barHeight, y, u, v, offset, y + 27, 7237230);
							}

							else if (titan instanceof EntitySpiderTitan)
							{
								barHeight = 29;
								y += i + barHeight;
								renderBar(titan instanceof EntityCaveSpiderTitan ? new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/cave_spider_titan.png")) : new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/spider_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 183, barHeight, y, u, v, offset, y + 19, 11013646);
							}

							else if (titan instanceof EntitySkeletonTitan)
							{
								barHeight = 28;
								y += i + barHeight;
								renderBar(new ResourceLocation(((EntitySkeletonTitan)titan).getSkeletonType() == 1 ? TheTitans.getTextures("textures/gui", "titanbars/wither_skeleton_titan.png") : TheTitans.getTextures("textures/gui", "titanbars/skeleton_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), ((EntitySkeletonTitan)titan).getSkeletonType() == 1 ? 195 : 185, barHeight, y, u, v, offset, y + 18, 12698049);
							}

							else if (titan instanceof EntityZombieTitan)
							{
								barHeight = 26;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/zombie_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 185, barHeight, y, u, v, offset, y + 16, 7969893);
							}

							else if (titan instanceof EntityCreeperTitan)
							{
								barHeight = 28;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/creeper_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 185, barHeight, y, u, ((EntityCreeperTitan)titan).getPowered() ? 0 + barHeight + barHeight : 0, offset, y + 18, 894731);
							}

							else if (titan instanceof EntityPigZombieTitan)
							{
								barHeight = 31;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/zombie_pigman_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 185, barHeight, y, u, v, -1, y + 22, 15373203);
							}

							else if (titan instanceof EntityBlazeTitan)
							{
								barHeight = 35;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/blaze_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 185, barHeight, y, u, v, offset, y + 25, 16167425);
							}

							else if (titan instanceof EntityGhastTitan)
							{
								barHeight = 37;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/ghast_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 195, barHeight, y, u, v, offset, y + 25, 12369084);
							}

							else if (titan instanceof EntityGargoyleTitan)
							{
								barHeight = 35;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/gargoyle_king.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 213, barHeight, y, u, v, offset, y + 25, 7237230);
							}

							else if (titan instanceof EntityIronGolemTitan)
							{
								barHeight = 30;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/ultima_iron_golem_titan.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 196, barHeight, y, u, v, offset, y + 20, 7237230);
							}

							else if (titan instanceof EntityEnderColossus)
							{
								barHeight = 32;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/ender_colossus.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 194, barHeight, y, u, v, offset, y + 19, 13369594);
							}

							else if (titan instanceof EntityWitherzilla)
							{
								barHeight = 36;
								y += i + barHeight;
								renderBar(new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/witherzilla.png")), titan, outstring, fr, !titan.isDead, titan.getTitanHealth() / titan.getTitanMaxHealth(), 256, barHeight, y, u, v, offset, y + 26, 15728880 - (int)(MathHelper.cos(titan.ticksExisted * 0.05F) * 15728880));
							}

							i++;
						}
					}
				}
			}
		}

		mc.renderEngine.bindTexture(Gui.icons);
		if (event.isCanceled() != isCanceled)
		event.setCanceled(isCanceled);
		profiler.endSection();
	}

	private void renderMainPlayerBar(FontRenderer fr, GuiEntry entry)
	{
		GL11.glPushMatrix();
		GL11.glEnable(2977);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		String output;
		int shakeX = (int)(!entry.damaged() ? 0 : Maths.random(-5, 5) * MathHelper.clamp_float((float)(entry.getLastDamage() / (entry.getMax(0) / 5.0F)), 0.1F, 1.0F));
		int shakeY = (int)(!entry.damaged() ? 0 : Maths.random(-5, 5) * MathHelper.clamp_float((float)(entry.getLastDamage() / (entry.getMax(0) / 5.0F)), 0.1F, 1.0F));
		int width = (int)((res.getScaledWidth() / 2) - 95) + shakeX;
		int height = (int)(res.getScaledHeight() - 45) + shakeY;
		mc.getTextureManager().bindTexture(entry.getTexture());
		if (entry.damaged() || entry.getCurrent(0) <= 0.0F)
		drawTexturedModalRect(width, height, 0, TitanConfig.barTextures == 1 ? 63 : 18, 90, 9);
		else if (entry.healed())
		drawTexturedModalRect(width, height, 0, TitanConfig.barTextures == 1 ? 72 : 27, 90, 9);
		else
		drawTexturedModalRect(width, height, 0, TitanConfig.barTextures == 1 ? 54 : 9, 90, 9);
		if (entry.getIndicator(0) > entry.getCurrent(0))
		{
			color();
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 45 : 0, 90, 9, (float)entry.getIndicatorPerc(0));
		}

		if (entry.getCurrent(0) > 0.0D)
		{
			if (((EntityPlayer)entry.getEntry()).isPotionActive(Potion.wither))
				color(55.0F, 10.0F, 10.0F);
			else if (((EntityPlayer)entry.getEntry()).isPotionActive(Potion.poison))
				color(55.0F, 100.0F, 0.0F);
			else
				color(255.0F, 0.0F, 0.0F);
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 45 : 0, 90, 9, (float)entry.getCurrentPercSmooth(0));
			color(255.0F, 255.0F, 255.0F, 175.0F);
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 81 : 36, 90, 9, (float)entry.getCurrentPercSmooth(0));
		}

		if (entry.getCurrent(1) > 0.0D)
		{
			if (((EntityPlayer)entry.getEntry()).isPotionActive(Potion.wither))
				color(55.0F, 55.0F, 10.0F);
			else if (((EntityPlayer)entry.getEntry()).isPotionActive(Potion.poison))
				color(55.0F, 155.0F, 0.0F);
			else
				color(255.0F, 200.0F, 0.0F);
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 45 : 0, 90, 9, (float)entry.getCurrentPercSmooth(1));
			color(255.0F, 255.0F, 255.0F, 175.0F);
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 81 : 36, 90, 9, (float)entry.getCurrentPercSmooth(1));
		}

		color();
		float armor = 0;
		float armorResist = 0.0F;
		int armorType = -1;
		int armors[] = new int[5];
		for (int i = 0; i < 4; i++)
		{
			if (((EntityPlayer)entry.getEntry()).getCurrentArmor(i) != null && ((EntityPlayer)entry.getEntry()).getCurrentArmor(i).getItem() instanceof ItemTitanArmor)
			{
				ItemTitanArmor item = ((ItemTitanArmor)((EntityPlayer)entry.getEntry()).getCurrentArmor(i).getItem());
				armor += (int)(item.getTitanArmor());
				if (armorResist < item.getTitanResistance())
				armorResist = item.getTitanResistance();
				if (item instanceof ItemAdminiumArmor)
				{
					if (armorType < 5)
					armorType = 5;
					armors[0] ++;
				}

				else if (item instanceof ItemVoidArmor)
				{
					if (armorType < 4)
					armorType = 4;
					armors[1] ++;
				}

				else if (item instanceof ItemWithireniumArmor)
				{
					if (armorType < 3)
					armorType = 3;
					armors[2] ++;

				}

					
				else if (item instanceof ItemHarcadiumArmor)
				{
					if (armorType < 2)
					armorType = 2;
					armors[3] ++;

				}

					
				else if (item instanceof ItemDemontiumArmor)
				{
					if (armorType < 1)
					armorType = 1;
					armors[4] ++;
				}
			}
		}

		if (armorType > -1)
		{
			int offsetA = 0;
			int offsetB = 0;
			if (armors[0] > 0)
			offsetA = 5 - armors[0];
			else if (armors[1] > 0)
			offsetA = 5 - armors[1];
			else if (armors[2] > 0)
			offsetA = 5 - armors[2];
			else if (armors[3] > 0)
			offsetA = 5 - armors[3];
			else if (armors[4] > 0)
			offsetA = 5 - armors[4];
			if (armorResist >= 0.24F)
			offsetB = 1;
			else if (armorResist >= 0.20F)
			offsetB = 2;
			else if (armorResist >= 0.12F)
			offsetB = 3;
			else if (armorResist >= 0.06F)
			offsetB = 4;
			drawTexturedModalRect(width + 5 + shakeX, height + shakeY - 12, 90 + (9 * offsetA), (armorType * 9), 9, 9);
			drawTexturedModalRect(width + 50 + shakeX, height + shakeY - 12, 90 + (9 * offsetB), (armorType * 9), 9, 9);
			output = parseFloat(armor);
			fr.drawStringWithShadow(output, (width + 16 + shakeX) + (output.length()), height + shakeY - 11, 0xFFE0E0E0);
			output = armorResist * 100.0F + "%";
			fr.drawStringWithShadow(output, (width + 58 + shakeX) + (output.length()), height + shakeY - 11, 0xFFE0E0E0);
		}

		else
		{
			int offsetA = 0;
			armor = ((EntityPlayer)entry.getEntry()).getTotalArmorValue();
			int offsetB = 0;
			for ( ; armor > 0 && offsetA < 10; )
			{
				if (armor > 1)
				{
					offsetB = 1;
					armor -= 2;
				}

				else
				{
					offsetB = 3;
					armor --;
				}

				drawTexturedModalRect(width + (offsetA * 9) + shakeX, height + shakeY - 12, 90 + (9 * offsetB), 0, 9, 9);
				offsetA ++;
			}
		}

		output = parseFloat((float)entry.getCurrent(0) + (float)entry.getCurrent(1)) + (entry.getCurrent(0) < entry.getMax(0) ? " / " + parseFloat((float)entry.getMax(0)) : "");
		fr.drawStringWithShadow(output, (width + 45) - (fr.getStringWidth(output) / 2), height + 1, entry.getCurrent(0) > 0.0F ? 0xFFE0E0E0 : 0xFFE00000);
		color();
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	private void renderPlayerBar(FontRenderer fr, int offset, GuiEntry entry)
	{
		GL11.glPushMatrix();
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		GL11.glEnable(2977);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		String output;
		int shakeX = (int)(!entry.damaged() ? 0 : Maths.random(-5, 5) * MathHelper.clamp_float((float)(entry.getLastDamage() / (entry.getMax(0) / 5.0F)), 0.1F, 1.0F));
		int shakeY = (int)(!entry.damaged() ? 0 : Maths.random(-5, 5) * MathHelper.clamp_float((float)(entry.getLastDamage() / (entry.getMax(0) / 5.0F)), 0.1F, 1.0F));
		int width = 10 + shakeX;
		int height = 30 + (offset * 32) + shakeY;
		mc.getTextureManager().bindTexture(entry.getTexture());
		if (entry.damaged() || entry.getCurrent(0) <= 0.0F)
			drawTexturedModalRect(width, height, 0, TitanConfig.barTextures == 1 ? 63 : 18, 90, 9);
		else if (entry.healed())
			drawTexturedModalRect(width, height, 0, TitanConfig.barTextures == 1 ? 72 : 27, 90, 9);
		else
			drawTexturedModalRect(width, height, 0, TitanConfig.barTextures == 1 ? 54 : 9, 90, 9);
		if (entry.getIndicator(0) > entry.getCurrent(0))
		{
			color();
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 45 : 0, 90, 9, (float)entry.getIndicatorPerc(0));
		}

		if (entry.getCurrent(0) > 0.0D)
		{
			if (((EntityLivingBase)entry.getEntry()).isPotionActive(Potion.wither))
				color(55.0F, 10.0F, 10.0F);
			else if (((EntityLivingBase)entry.getEntry()).isPotionActive(Potion.poison))
				color(55.0F, 100.0F, 0.0F);
			else
				if (entry.getEntry() instanceof EntityTameable || entry.getEntry() instanceof EntityHorse)
					color(55.0F, 155.0F, 255.0F);
				else if (entry.getEntry() instanceof EntityPlayer)
					color(255.0F, 0.0F, 0.0F);
				else
					color(55.0F, 200.0F, 0.0F);
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 45 : 0, 90, 9, (float)entry.getCurrentPercSmooth(0));
			color(255.0F, 255.0F, 255.0F, 175.0F);
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 81 : 36, 90, 9, (float)entry.getCurrentPercSmooth(0));
		}

		if (entry.getCurrent(1) > 0.0D)
		{
			if (((EntityLivingBase)entry.getEntry()).isPotionActive(Potion.wither))
			color(55.0F, 55.0F, 10.0F);
			else if (((EntityLivingBase)entry.getEntry()).isPotionActive(Potion.poison))
			color(55.0F, 155.0F, 0.0F);
			else
			if (entry.getEntry() instanceof EntityTameable)
			color(55.0F, 255.0F, 255.0F);
			else if (entry.getEntry() instanceof EntityPlayer)
			color(255.0F, 200.0F, 0.0F);
			else
			color(150.0F, 200.0F, 0.0F);
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 45 : 0, 90, 9, (float)entry.getCurrentPercSmooth(1));
			color(255.0F, 255.0F, 255.0F, 175.0F);
			drawBar(width, height, 0, TitanConfig.barTextures == 1 ? 81 : 36, 90, 9, (float)entry.getCurrentPercSmooth(1));
		}

		color();
		int offsetA = 0;
		int armor = ((EntityLivingBase)entry.getEntry()).getTotalArmorValue();
		int offsetB = 0;
		for ( ; armor > 0 && offsetA < 10; )
		{
			if (armor > 1)
			{
				offsetB = 1;
				armor -= 2;
			}

			else
			{
				offsetB = 3;
				armor --;
			}

			drawTexturedModalRect(width + (offsetA * 9) + shakeX, height + shakeY + 10, 90 + (9 * offsetB), 0, 9, 9);
			offsetA ++;
		}

		output = parseFloat((float)entry.getCurrent(0) + (float)entry.getCurrent(1)) + (entry.getCurrent(0) < entry.getMax(0) ? " / " + parseFloat((float)entry.getMax(0)) : "");
		fr.drawStringWithShadow(output, (width + 45) - (fr.getStringWidth(output) / 2), height + 1, entry.getCurrent(0) > 0.0F ? 0xFFE0E0E0 : 0xFFE00000);
		if (entry.is(Entity.class))
		output = ((Entity)entry.getEntry()).getCommandSenderName();
		else
		output = "Unknown";
		fr.drawStringWithShadow(output, (width + 45) - (fr.getStringWidth(output) / 2), height - 9, 0xFFC0C0C0);
		color();
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	private void renderBar(ResourceLocation texture, EntityTitan titan, String outstring, FontRenderer fr, boolean flag, float gfHealth, int barWidth, int barHeight, int y, int u, int v, int offset, int namey, int color)
	{
		barWidth = barWidth - titan.deathTicks;
		u = u + (titan.deathTicks / 2);
		if (flag && barWidth > 0)
		{
			Timer timer = new Timer(20.0F);
			ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
			int width = res.getScaledWidth();
			int barWidthFilled = (int)(gfHealth * (barWidth + 1));
			int x = width / 2 - barWidth / 2;
			mc.getTextureManager().bindTexture(texture);
			GL11.glEnable(2977);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			drawTexturedModalRect(x, y, u, v, barWidth, barHeight);
			if (barWidthFilled > 0)
			drawTexturedModalRect(x, y, u, v + barHeight, barWidthFilled, barHeight);
			if (titan instanceof EntityCreeperTitan && ((EntityCreeperTitan)titan).getPowered())
			drawTexturedModalRect(x, y, u + (titan.ticksExisted * (!titan.isEntityAlive() ? 1 : 3)) + (int)timer.elapsedPartialTicks, v + (barHeight * 4), barWidth, barHeight);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			fr.drawStringWithShadow(outstring, (width / 2) - (fr.getStringWidth(outstring) / 2) - offset, namey, color);
			String health = "";
			health = parseFloat(titan.getTitanHealth());
			fr.drawStringWithShadow(health, (width / 2) - (fr.getStringWidth(health) / 2), namey - 10, 12369084);
			GL11.glDisable(3042);
		}
	}

	private void renderNewBar(FontRenderer fr, int offset, GuiEntry entry)
	{
		GL11.glPushMatrix();
		GL11.glEnable(2977);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		String output;
		int shakeX = (int)(!entry.damaged() ? 0 : Maths.random(-5, 5) * MathHelper.clamp_float((float)(entry.getLastDamage() / (entry.getMax(0) / 100.0F)), 0.1F, 1.0F));
		int shakeY = (int)(!entry.damaged() ? 0 : Maths.random(-5, 5) * MathHelper.clamp_float((float)(entry.getLastDamage() / (entry.getMax(0) / 100.0F)), 0.1F, 1.0F));
		int width = (res.getScaledWidth() / 2) - 104 + shakeX;
		int height = (offset * 35) + shakeY;
		float size = !entry.canRenderStamina() ? 0.0F : MathHelper.clamp_float((((150.0F * 4.0F) - entry.deathTicks) / (65.0F * 4.0F)) - 0.8F, 0.0F, 1.0F);
		mc.getTextureManager().bindTexture(entry.getTexture());
		drawTexturedModalRect(width, height, 0, 0, 208, 30);
		if (entry.canRenderStamina() && entry.deathTicks < 150)
		drawMirroredBar(width + 25, height + 20, 0, 62, 80, 3, size);
		if (entry.getIndicator(0) > entry.getCurrent(0))
		{
			color();
			drawBar(width + 2, height + 14, 0, 45, 204, 5, (float)entry.getIndicatorPerc(0));
			drawBar(width + 2, height + 14, 0, 40, 204, 5, (float)entry.getIndicatorPerc(0));
		}

		if (entry.getCurrent(0) > 0.0D)
		{
			color(entry.rgb[0], entry.rgb[1], entry.rgb[2]);
			drawBar(width + 2, height + 14, 0, 35, 204, 5, (float)entry.getCurrentPerc(0));
			color();
			drawBar(width + 2, height + 14, 0, 30, 204, 5, (float)entry.getCurrentPerc(0));
		}

		if (entry.canRenderStamina() && entry.getIndicator(1) > entry.getCurrent(2))
		{
			color();
			drawMirroredBar(width + 25, height + 20, 0, 53, 80, 3, (float)(entry.getIndicatorPerc(2) * size));
			drawMirroredBar(width + 25, height + 20, 0, 50, 80, 3, (float)(entry.getIndicatorPerc(2) * size));
		}

		if (entry.canRenderStamina() && entry.getCurrent(2) > 0.0F)
		{
			color(entry.rgb[3], entry.rgb[4], entry.rgb[5]);
			drawMirroredBar(width + 25, height + 20, 0, 53, 80, 3, (float)(entry.getCurrentPerc(2) * size));
			color();
			drawMirroredBar(width + 25, height + 20, 0, 50, 80, 3, (float)(entry.getCurrentPerc(2) * size));
		}

		if (entry.is(Entity.class))
		output = ((Entity)entry.getEntry()).getCommandSenderName();
		else if (entry.is(EventObject.class))
		output = ((EventObject)entry.getEntry()).getName();
		else
		output = "Unknown";
		fr.drawStringWithShadow(output, (width + 104) - (fr.getStringWidth(output) / 2), height + 3, 0xFFC0C0C0);
		output = parseFloat((float)entry.getCurrent(0));
		fr.drawStringWithShadow(output, (width + 104) - (fr.getStringWidth(output) / 2), height + 14, 0xFFC0C0C0);
		if (entry.getLastTotalDamage() != 0.0D)
		{
			output = parseFloat((float)entry.getLastTotalDamage());
			fr.drawStringWithShadow(entry.getLastTotalDamage() >= entry.getMax(0) ? entry.getLastTotalDamage() >= entry.getMax(0) + (entry.getMax(0) * 0.1) ? "Overkill" : "Killed" : output, width + 200 - (fr.getStringWidth(output) / 2) + (entry.getHurtTime() / 4), height + 4, 0xFFC02000);
		}

		color();
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	private String parseFloat(float value)
	{
		String input;
		if (value >= 1000000000000000000000000000000000000.0F)
		input = "Infinity (" + (int)Math.floor((value / Float.MAX_VALUE) * 100) + "%)";
		else if (value >= 1000000000000000000000000000000000.0F)
		input = Math.floor(value * 0.00000000000000000000000000000001F) * 0.1F + " Decillion";
		else if (value >= 1000000000000000000000000000000.0F)
		input = Math.floor(value * 0.00000000000000000000000000001F) / 10 + " Nonillion";
		else if (value >= 1000000000000000000000000000.0F)
		input = Math.floor(value * 0.00000000000000000000000001F) / 10 + " Octillion";
		else if (value >= 1000000000000000000000000.0F)
		input = Math.floor(value * 0.00000000000000000000001F) / 10 + " Septillion";
		else if (value >= 1000000000000000000000.0F)
		input = Math.floor(value * 0.00000000000000000001F) / 10F + " Sextillion";
		else if (value >= 1000000000000000000.0F)
		input = Math.floor(value * 0.00000000000000001F) / 10 + " Quintillion";
		else if (value >= 1000000000000000.0F)
		input = Math.floor(value * 0.00000000000001F) / 10 + " Quadrillion";
		else if (value >= 1000000000000.0F)
		input = Math.floor(value * 0.00000000001F) / 10 + " Trillion";
		else if (value >= 1000000000.0F)
		input = Math.floor(value * 0.00000001F) / 10 + " Billion";
		else if (value >= 1000000.0F)
		input =  Math.floor(value * 0.00001F) / 10 + " Million";
		else
		input = (int)Math.floor(value) + "";
		return input;
	}

	private void drawBar(int x, int y, int u, int v, int width, int height, float amount)
	{
		amount = MathHelper.clamp_float(amount, 0.0F, 1.0F);
		drawTexturedModalRect(x, y, u, v, (int)(width * amount), height);
	}

	private void drawMirroredBar(int x, int y, int u, int v, int width, int height, float amount)
	{
		amount = MathHelper.clamp_float(amount, 0.0F, 1.0F);
		drawTexturedModalRect(x + (int)(width - (width * amount)), y, u + (int)(width - (width * amount)), v, (int)(width * amount), height);
		drawTexturedModalRect(x + width, y, width, v, (int)(width * amount), height);
	}

	private void color(float... values)
	{
		int index = 0;
		float[] rgba = {255.0F, 255.0F, 255.0F, 255.0F};
		for (int i = 0; i < values.length; i++)
		if (index < 4)
		{
			rgba[index] = values[i];
			index ++;
		}

		else
		break;
		GL11.glColor4f(MathHelper.clamp_float(rgba[0] / 255.0F, 0.0F, 1.0F), MathHelper.clamp_float(rgba[1] / 255.0F, 0.0F, 1.0F), MathHelper.clamp_float(rgba[2] / 255.0F, 0.0F, 1.0F), MathHelper.clamp_float(rgba[3] / 255.0F, 0.0F, 1.0F));
	}
}


