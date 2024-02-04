package net.minecraft.theTitans.api;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.events.EventObject;
import net.minecraft.util.ResourceLocation;
public class GuiEntry
{
	public static final List<GuiEntry> instances = new ArrayList<GuiEntry>();
	private ResourceLocation texture;
	public int[] rgb = {255, 0, 0, 255, 135, 0};
	private Object entry;
	private UUID uuid;
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
	private boolean renderStaminaBar;
	GuiEntry(Object entry, UUID uuid, String texture, boolean... flags)
	{
		this.entry = entry;
		this.uuid = uuid;
		if (texture == null)
			if (entry instanceof EventObject)
				this.texture = new ResourceLocation(TheTitans.getTextures("textures/gui", "eventbars/base.png"));
			else if (entry instanceof EntityLivingBase && (entry instanceof EntityPlayer ||(TheTitans.checkFriendlyFire(Minecraft.getMinecraft().thePlayer, (EntityLivingBase) entry, true))))
				this.texture = new ResourceLocation(TheTitans.getTextures("textures/gui", "normalbars/base.png"));
			else
				this.texture = new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/base.png"));
		else
			if (entry instanceof EventObject)
				this.texture = new ResourceLocation(TheTitans.getTextures("textures/gui", "eventbars/" + texture + ".png"));
			else if (entry instanceof EntityLivingBase && (entry instanceof EntityPlayer ||(TheTitans.checkFriendlyFire(Minecraft.getMinecraft().thePlayer, (EntityLivingBase) entry, true))))
				this.texture = new ResourceLocation(TheTitans.getTextures("textures/gui", "normalbars/" + texture + ".png"));
			else
				this.texture = new ResourceLocation(TheTitans.getTextures("textures/gui", "titanbars/" + texture + ".png"));
		shakeLength = 40;
		for (int i = 0; i < flags.length; i++)
		switch(i)
		{
			case 0: renderStaminaBar = flags[0];
		}
	}

	public static GuiEntry create(Object entry, UUID uuid, boolean... flags)
	{
		return create(entry, uuid, null, flags);
	}

	public static GuiEntry create(Object entry, UUID uuid, String texture, boolean... flags)
	{
		instances.add(new GuiEntry(entry, uuid, texture, flags));
		return instances.get(instances.size() - 1);
	}

	public static boolean contains(UUID uuid)
	{
		for (GuiEntry ent : instances)
		if (ent.isUUID(uuid.toString()))
		return true;
		return false;
	}

	public void onUpdate()
	{
		if (deathTicks > 0)
		deathTicks ++;
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
		if (ticksExisted % 20 == 0)
		if (entry == null || entry instanceof Entity && ((Entity)entry).isDead)
		ClientProxy.ingameGui.queueRemove(uuid.toString());
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
			case 1:
			{
				if (entry instanceof EntityLivingBase)
				return ((EntityLivingBase)entry).getAbsorptionAmount();
				else if (entry instanceof EventObject)
				return ((EventObject)entry).getAmountLeft();
			}

			case 2:
			{
				if (entry instanceof EntityTitan)
				return ((EntityTitan)entry).isStunned ? 0.0D : ((EntityTitan)entry).getStamina();
				break;
			}

			default: if (entry instanceof EntityLivingBase)
			return ((EntityLivingBase)entry).getHealth();
			else if (entry instanceof EventObject)
			return ((EventObject)entry).getAmountLeft();
		}

		return Double.MAX_VALUE;
	}

	public double getMax(int type)
	{
		if (entry != null)
		switch(type)
		{
			case 1:
			{
				if (entry instanceof EntityLivingBase)
				return ((EntityLivingBase)entry).getMaxHealth();
				else
				return 1020.0F;
			}

			case 2:
			{
				if (entry instanceof EntityTitan)
				return ((EntityTitan)entry).getMaxStamina();
				break;
			}

			default: if (entry instanceof EntityLivingBase)
			return ((EntityLivingBase)entry).getMaxHealth();
			else if (entry instanceof EventObject)
			return ((EventObject)entry).getAmountMax();
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

	public boolean isUUID(String uuid)
	{
		return this.uuid.toString().equals(uuid);
	}

	public boolean healed()
	{
		return entry == null || entry instanceof EventObject ? false : 120 - shakeLength < healTime;
	}

	public boolean damaged()
	{
		return entry == null || entry instanceof EventObject ? false : 120 - shakeLength < hurtTime;
	}

	public boolean canRender(Entity entity)
	{
		if (entry != null)
		if (entry instanceof EntityTitan)
		return !((EntityTitan)entry).isDead && !((EntityTitan)entry).getWaiting() && !((EntityTitan)entry).isInvisible();
		else if (entry instanceof EntityLivingBase)
		return !((EntityLivingBase)entry).isDead;
		else if (entry instanceof EventObject)
		return ((EventObject)entry).canSee(entity);
		return false;
	}

	public boolean canRenderStamina()
	{
		return renderStaminaBar;
	}

	public GuiEntry setColor(int... colors)
	{
		for (int i = 0; i < rgb.length && i < colors.length; i++)
		rgb[i] = colors[i];
		return this;
	}

	public ResourceLocation getTexture()
	{
		return texture;
	}

	public Object getEntry()
	{
		return entry;
	}

	public int getHurtTime()
	{
		return hurtTime;
	}
}


