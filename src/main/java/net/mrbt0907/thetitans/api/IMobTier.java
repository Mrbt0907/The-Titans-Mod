package net.mrbt0907.thetitans.api;

public interface IMobTier
{
	public EnumMobTier getTier();
	
	public default float getMultiplier()
	{
		return getTier().getMultiplier();
	}
}
