package net.minecraft.entity.titanminion;
import net.minecraft.client.resources.I18n;
public enum EnumMinionType
{
	SPECIAL(-1, ""),
	LOYALIST(0, ""),
	PRIEST(1, ""),
	ZEALOT(2, ""),
	BISHOP(3, ""),
	TEMPLAR(4, ""),
	LORD(5, ""),
	DEMITITAN(7, "");
	private final int id;
	private final String localizationKey;
	private EnumMinionType(int id, String localizationKey)
	{
		this.id = id;
		this.localizationKey = localizationKey;
	}

	public int getID()
	{
		return id;
	}

	public String getKey()
	{
		return localizationKey;
	}

	public String getName()
	{
		return I18n.format(localizationKey);
	}

	public String getName(int localizationKeySuffix)
	{
		if (localizationKeySuffix != 0)
		return I18n.format(localizationKey + "." + localizationKeySuffix);
		return I18n.format(localizationKey);
	}
}


