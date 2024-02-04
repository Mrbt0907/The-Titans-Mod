package net.minecraft.titans.api;

import java.util.UUID;

import net.minecraft.util.ResourceLocation;

public interface ITitanBossBar
{
	
	public boolean canRenderBar();
	
	public boolean hasStamina();
	
	public default boolean canColorHealth() {return true;}
	
	public default boolean canColorStamina() {return true;}
	
	public boolean canShowDamage();
	
	public default int getHealthBarStart() {return 0;}
	
	public default int getHealthBarLength() {return 208;}
	
	public default int getStaminaBarStart() {return 24;}
	
	public default int getStaminaBarLength() {return 159;}
	
	public default int getNameBarStart() {return -2;}
	
	public default int getHealthNameStart() {return 8;}
	
	public double getBarHealth();
	
	public double getBarMaxHealth();
	
	public double getBarStamina();
	
	public double getBarMaxStamina();
	
	public ResourceLocation getBarTexture();
	
	public default int[] getBarColor() {return new int[] {255, 0, 0, 255, 135, 0};}
	public UUID getUniqueBarID();
	public String getBarName();
}
