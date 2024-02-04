package net.minecraft.titans.client.gui;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.titans.ClientProxy;
import net.minecraft.titans.api.ITitanBossBar;
import net.minecraft.titans.manager.EventObject;
import net.minecraft.util.ResourceLocation;
public class GuiEntry
{
	public int[] rgb = {255, 0, 0, 255, 135, 0};
	private ITitanBossBar entry;
	private double lastDamage;
	private double lastTotalDamage;
	private double prevMainValue;
	private double prevStaminaValue;
	public int ticksExisted;
	public int deathTicks;
	private int healTime;
	private int hurtTime;
	private int shakeLength;
	private double mainIndicatorSmooth;
	private double secondaryIndicatorSmooth;
	private double staminaIndicatorSmooth;
	private double mainIndicator;
	private double staminaIndicator;
	
	public GuiEntry(ITitanBossBar entry)
	{
		this.entry = entry;
		rgb = entry.getBarColor();
		shakeLength = 40;
		prevMainValue = entry.getBarHealth();
		prevStaminaValue = entry.getBarStamina();
	}
	
	public void onUpdate()
	{
		
		if (hurtTime > 0)
		hurtTime --;
		if (healTime > 0)
		healTime --;
		if (hurtTime <= 0)
		{
			lastDamage = 0.0D;
			lastTotalDamage = 0.0D;
		}

		if (getCurrent(0) <= 0.0D)
		deathTicks ++;
		else if (getCurrent(0) > 0.0D && deathTicks > 0)
		deathTicks = 0;
		if (getCurrent(0) < prevMainValue || getCurrent(2) < prevStaminaValue)
		{
			hurtTime = 120;
			lastDamage = Math.max(prevMainValue - getCurrent(0), 0.0D);
			lastTotalDamage += lastDamage;
			prevMainValue = getCurrent(0);
			prevStaminaValue = getCurrent(2);
		}

		else if (getCurrent(0) != prevMainValue || getCurrent(2) != prevStaminaValue)
		{
			healTime = 120;
			prevMainValue = getCurrent(0);
			prevStaminaValue = getCurrent(2);
		}

		if (mainIndicator > getMax(0))
			mainIndicator = getCurrent(0);
		else if (hurtTime <= 0 && mainIndicator > getCurrent(0))
			mainIndicator -= getMax(0) * 0.0015D;
		else if (mainIndicator < getCurrent(0))
			mainIndicator = getCurrent(0);
		if (staminaIndicator > getMax(2))
			staminaIndicator = getCurrent(2);
		else if (hurtTime <= 0 && staminaIndicator > getCurrent(2))
			staminaIndicator -= getMax(2) * 0.005D;
		else if (staminaIndicator < getCurrent(2))
			staminaIndicator = getCurrent(2);
		if (mainIndicatorSmooth > getCurrent(0) && mainIndicatorSmooth < getMax(0))
			mainIndicatorSmooth -= getMax(0) * 0.0095D;
		else if (mainIndicatorSmooth < getCurrent(0))
			mainIndicatorSmooth += getMax(0) * 0.0095D;
		else
			mainIndicatorSmooth = getCurrent(0);
		if (secondaryIndicatorSmooth > getCurrent(1) && secondaryIndicatorSmooth < getMax(1))
			secondaryIndicatorSmooth -= getMax(1) * 0.0095D;
		else if (secondaryIndicatorSmooth < getCurrent(1))
			secondaryIndicatorSmooth += getMax(1) * 0.0095D;
		else
			secondaryIndicatorSmooth = getCurrent(1);
		if (staminaIndicatorSmooth > getCurrent(2) && staminaIndicatorSmooth < getMax(2))
			staminaIndicatorSmooth -= getMax(2) * 0.0095D;
		else if (staminaIndicatorSmooth < getCurrent(2))
			staminaIndicatorSmooth += getMax(2) * 0.0095D;
		else
			staminaIndicatorSmooth = getCurrent(2);
		
		if (getCurrent(0) <= 0.0D && deathTicks > 350 && getIndicator(0) <= 0.0D || entry == null)
			ClientProxy.manager.inGameGui.queueRemoval(entry.getUniqueBarID());
		ticksExisted++;
	}

	public double getLastDamage()
	{
		return lastDamage;
	}

	public double getLastTotalDamage()
	{
		return lastTotalDamage;
	}

	public double getIndicatorPerc(int type)
	{
		switch(type)
		{
			case 1: return staminaIndicator / getMax(type);
			default: return mainIndicator / getMax(type);
		}
	}

	public double getIndicator(int type)
	{
		switch(type)
		{
			case 1: return staminaIndicator;
			default: return mainIndicator;
		}
	}

	public double getCurrentPerc(int type)
	{
		return getCurrent(type) / getMax(type);
	}

	public double getCurrent(int type)
	{
		if (entry != null)
			switch(type)
			{
				case 2:
					return entry.getBarStamina();
				default: 
					return entry.getBarHealth();
			}

		return Double.MAX_VALUE;
	}

	public double getMax(int type)
	{
		if (entry != null)
		switch(type)
		{
			case 2:
				return entry.getBarMaxStamina();
			default:
				return entry.getBarMaxHealth();
		}

		return Double.MAX_VALUE;
	}

	public double getCurrentPercSmooth(int type)
	{
		return getCurrentSmooth(type) / getMax(type);
	}

	public double getCurrentSmooth(int type)
	{
		if (entry != null)
		switch(type)
		{
			case 1: return secondaryIndicatorSmooth;
			case 2: return staminaIndicatorSmooth;
			default: return mainIndicatorSmooth;
		}

		return Double.MAX_VALUE;
	}

	public boolean is(Class<?>... classes)
	{
		for (Class<?> clazz : classes)
		if (clazz.isAssignableFrom(entry.getClass()))
		return true;
		return false;
	}

	public boolean isUUID(UUID uuid)
	{
		return uuid.equals(uuid);
	}

	public boolean healed()
	{
		return entry == null || entry instanceof EventObject ? false : 120 - shakeLength < healTime;
	}

	public boolean damaged()
	{
		return entry == null || entry instanceof EventObject ? false : 120 - shakeLength < hurtTime;
	}

	public boolean canShowDamage()
	{
		return entry.canShowDamage();
	}
	
	public boolean canRender(EntityPlayer entity)
	{
		if (entry != null)
			return entry.canRenderBar();
		return false;
	}

	public boolean canRenderStamina()
	{
		return entry.hasStamina();
	}

	public GuiEntry setColor(int... colors)
	{
		for (int i = 0; i < rgb.length && i < colors.length; i++)
		rgb[i] = colors[i];
		return this;
	}

	public ResourceLocation getTexture()
	{
		return entry.getBarTexture();
	}

	public String getName()
	{
		return entry.getBarName();
	}
	
	public ITitanBossBar getEntry()
	{
		return entry;
	}

	public int getHurtTime()
	{
		return hurtTime;
	}
	
	public void reset()
	{
		entry = null;
	}
}


