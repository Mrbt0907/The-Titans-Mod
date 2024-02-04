package net.minecraft.titans.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.titans.ClientProxy;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.api.ITitanBossBar;
import net.minecraft.titans.utils.Maths;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTitanHud extends Gui
{
	private final Minecraft mc;
	private final Map<UUID, GuiEntry> entries = new HashMap<UUID, GuiEntry>();
	private final List<UUID> removeEntries = new ArrayList<UUID>();
	
	public GuiTitanHud(Minecraft mc)
	{
		this.mc = mc;
	}
	
	public void queueRemovalAll()
	{
		entries.forEach((uuid, entry) -> entry.reset());
		removeEntries.addAll(entries.keySet());
	}
	
	public void queueRemoval(UUID entry)
	{
		if (entries.containsKey(entry))
		{
			entries.get(entry).reset();
			removeEntries.add(entry);
		}
	}
	
	public void tickPre(float partialTicks)
	{	
		for(UUID uuid : removeEntries)
		{
			TheTitans.debug("Removing Entry " + uuid + "...");
			entries.remove(uuid);
		}
		if (removeEntries.size() > 0)
			removeEntries.clear();
		
		
		if (mc.world != null)
		{
			mc.world.loadedEntityList.forEach(entity ->
			{
				if (entity instanceof ITitanBossBar && !entries.containsKey(entity.getUniqueID()))
					entries.put(entity.getUniqueID(), new GuiEntry((ITitanBossBar) entity));
			});
			
			ClientProxy.manager.getEvents().forEach(event -> 
			{
				if (!entries.containsKey(event.getUniqueID()))
					entries.put(event.getUniqueID(), new GuiEntry(event));
			});
		}
		else if (!entries.isEmpty())
			queueRemovalAll();
	}
	
	public void tickPost(float partialTicks)
	{
		FontRenderer fr = mc.fontRenderer;
		ScaledResolution res = new ScaledResolution(mc);
		
		
		if (mc.inGameHasFocus)
		{
			int i = 0;
			for(GuiEntry entry : entries.values())
			{
				entry.onUpdate();
				if(entry.canRender(mc.player))
					renderNewBar(fr, res, i, entry);
				i++;
			}
		}
	}

	private void renderNewBar(FontRenderer fr, ScaledResolution res, int offset, GuiEntry entry)
	{
		GL11.glPushMatrix();
		GL11.glEnable(2977);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		ITitanBossBar ent = entry.getEntry();
		String output;
		int shakeX = (int)(!entry.canShowDamage() || !entry.damaged() ? 0 : Maths.random(-5, 5) * MathHelper.clamp((float)(entry.getLastDamage() / (entry.getMax(0) / 100.0F)), 0.1F, 1.0F));
		int shakeY = (int)(!entry.canShowDamage() || !entry.damaged() ? 0 : Maths.random(-5, 5) * MathHelper.clamp((float)(entry.getLastDamage() / (entry.getMax(0) / 100.0F)), 0.1F, 1.0F));
		int width = (res.getScaledWidth() / 2) - 104 + shakeX;
		int height = (offset * 55) + shakeY + 5;
		mc.getTextureManager().bindTexture(entry.getTexture());
		drawTexturedModalRect(width, height, 0, 0, 256, 51);
		if (entry.getIndicator(0) > entry.getCurrent(0))
		{
			color();
			drawBar(width, height, ent.getHealthBarStart(), 102, ent.getHealthBarLength(), 51, (float) entry.getIndicatorPerc(0));
		}

		if (entry.getCurrent(0) > 0.0D)
		{
			if (ent.canColorHealth()) color(entry.rgb[0], entry.rgb[1], entry.rgb[2]);
			drawBar(width, height, ent.getHealthBarStart(), 51, ent.getHealthBarLength(), 51, (float) entry.getCurrentPerc(0));
			color();
		}
		
		if (entry.canRenderStamina() && entry.getIndicator(1) > entry.getCurrent(2))
		{
			color();
			drawMirroredBar(width, height, ent.getStaminaBarStart(), 204, ent.getStaminaBarLength(), 51, (float)entry.getIndicatorPerc(2));
		}

		if (entry.canRenderStamina() && entry.getCurrent(2) > 0.0F)
		{
			if (ent.canColorStamina()) color(entry.rgb[3], entry.rgb[4], entry.rgb[5]);
			drawMirroredBar(width, height, ent.getStaminaBarStart(), 153, ent.getStaminaBarLength(), 51, (float)entry.getCurrentPerc(2));
			color();
		}

		output = entry.getName();
		fr.drawStringWithShadow(output, (width + ent.getHealthBarLength() * 0.5F) - (fr.getStringWidth(output) * 0.5F), height + ent.getNameBarStart(), 0xFFC0C0C0);
		output = parseDouble((float)entry.getCurrent(0));
		fr.drawStringWithShadow(output, (width + ent.getHealthBarLength() * 0.5F) - (fr.getStringWidth(output) * 0.5F), height + ent.getHealthNameStart(), 0xFFC0C0C0);
		if (ent.canShowDamage() && entry.getLastTotalDamage() != 0.0D)
		{
			output = parseDouble((float)entry.getLastTotalDamage());
			fr.drawStringWithShadow(entry.getLastTotalDamage() >= entry.getMax(0) ? entry.getLastTotalDamage() >= entry.getMax(0) + -(entry.getMax(0) * 0.5D) ? "Overkill" : "Killed" : output, width + 200 - (fr.getStringWidth(output) / 2) + (entry.getHurtTime() / 4), height + 4, 0xFFC02000);
		}

		color();
		GL11.glDisable(3042);
		GL11.glPopMatrix();
	}

	private String parseDouble(double value)
	{
		String input;
		if (value >= 1000000000000000000000000000000000000.0D)
		input = "Infinity (" + (int)Math.floor((value / Double.MAX_VALUE) * 100) + "%)";
		else if (value >= 1000000000000000000000000000000000.0D)
		input = Math.floor(value * 0.00000000000000000000000000000001D) * 0.1D + " Decillion";
		else if (value >= 1000000000000000000000000000000.0D)
		input = Math.floor(value * 0.00000000000000000000000000001D) / 10 + " Nonillion";
		else if (value >= 1000000000000000000000000000.0D)
		input = Math.floor(value * 0.00000000000000000000000001D) / 10 + " Octillion";
		else if (value >= 1000000000000000000000000.0D)
		input = Math.floor(value * 0.00000000000000000000001D) / 10 + " Septillion";
		else if (value >= 1000000000000000000000.0D)
		input = Math.floor(value * 0.00000000000000000001D) / 10D + " Sextillion";
		else if (value >= 1000000000000000000.0D)
		input = Math.floor(value * 0.00000000000000001D) / 10 + " Quintillion";
		else if (value >= 1000000000000000.0D)
		input = Math.floor(value * 0.00000000000001D) / 10 + " Quadrillion";
		else if (value >= 1000000000000.0D)
		input = Math.floor(value * 0.00000000001D) / 10 + " Trillion";
		else if (value >= 1000000000.0D)
		input = Math.floor(value * 0.00000001D) / 10 + " Billion";
		else if (value >= 1000000.0D)
		input =  Math.floor(value * 0.00001D) / 10 + " Million";
		else
		input = (int)Math.floor(value) + "";
		return input;
	}

	private void drawBar(int x, int y, int u, int v, int width, int height, float amount)
	{
		amount = MathHelper.clamp(amount, 0.0F, 1.0F);
		drawTexturedModalRect(x, y, u, v, (int)(width * amount), height);
	}

	private void drawMirroredBar(int x, int y, int u, int v, int width, int height, float amount)
	{
		int w = width / 2;
		int uu = (int) (u + (w - (w * amount)));
		amount = MathHelper.clamp(amount, 0.0F, 1.0F);
		drawTexturedModalRect(x + uu, y, uu, v, (int)(w * amount), height);
		drawTexturedModalRect(x + u + w, y, u + w, v, (int)(w * amount), height);
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
		GL11.glColor4f(MathHelper.clamp(rgba[0] / 255.0F, 0.0F, 1.0F), MathHelper.clamp(rgba[1] / 255.0F, 0.0F, 1.0F), MathHelper.clamp(rgba[2] / 255.0F, 0.0F, 1.0F), MathHelper.clamp(rgba[3] / 255.0F, 0.0F, 1.0F));
	}
}
