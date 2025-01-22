package net.mrbt0907.thetitans.api;

public enum EnumMobTier
{
	JOKE(1.0F), NORMAL(1.0F), LOYALIST(1.25F), PRIEST(2.0F), ZEALOT(3.0F), BISHOP(5.0F), TEMPLAR(10.0F), LORD(25.0F),
	LESSER_TITAN(50.0F), GREATER_TITAN(250.0F), GRAND_TITAN(1500.0F),
	DELTA(3000.0F), OMNIGOD(10000.0F), ALGOD(9999999.0F);
	
	private float multiplier;
	
	EnumMobTier(float multiplier)
	{	
		this.multiplier = multiplier;
	}
	
	public float getMultiplier()
	{
		return multiplier;
	}
}
