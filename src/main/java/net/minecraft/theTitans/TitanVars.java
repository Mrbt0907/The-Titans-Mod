package net.minecraft.theTitans;
public class TitanVars 
{

	public static String[] preInit =
	{
		"Loading The Titans...",
		"Waking Up The Titans...",
		"Picking Up The Titans...",
		"Slapping Awake The Titans...",
		"Unloading Herobrine...",
		"Stomping On The Titans...",
		"Deleting The Dogo...",
		"Updating Titan.exe...",
		"Deleting Titan32...",
		"Dropping The Titans..."
	};
	
	public static String[] postInit =
	{
		"Finished The Titans!",
		"The Titans Have Arrived!",
		"The Titans Have Awoken!",
		"They Have Awoken From Their Slumber!",
		"Tremble Before The Titans!",
		"Loaded The Titans!",
		"Thingy!",
		"The End Is Here!",
		"*Motionless Musamu*",
		"Finished The Titans!  Engender: \"Am I a joke to you?\"",
		"Witherzilla: \"Balance.  As All Things Should Be...\"",
		"Executor Dragon: \"I Am Eternal!\""
	};
	
	public static Object getEntityDefaults(int index, String entityClassName)
	{
		switch (entityClassName)
		{
			case "yeet":
			{
				switch (index)
				{
					case 1: return Double.MAX_VALUE;
					case 2: return 2.0D;
					case 3: return 0.0D;
					case 4: return 0.0D;
					default: return 20.0D;
				}
			}
		}

		return null;
	}
}


