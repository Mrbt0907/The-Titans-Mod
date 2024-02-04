package net.minecraft.titans.api;

public interface IMobTier
{
	public EnumMobTier getTier();
	
	public default float getMultiplier()
	{
		return getTier().getMultiplier();
	}
}
