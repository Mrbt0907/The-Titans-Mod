package net.minecraft.theTitans.core;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.configs.TitanConfig;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.common.util.EnumHelper;
public class TheCore
{
	public static EnumDifficulty VERYHARD;
	public static EnumDifficulty EXPERT;
	public static EnumDifficulty NIGHTMARE;
	public static EnumDifficulty IMPOSSIBLE;
	public static final EnumDifficulty DIFFICULTIES[] = {EnumDifficulty.PEACEFUL, EnumDifficulty.EASY, EnumDifficulty.NORMAL, EnumDifficulty.HARD, VERYHARD, EXPERT, NIGHTMARE, IMPOSSIBLE};
	
	public static void preInit(FMLPreInitializationEvent event)
	{
		if (TitanConfig.isCoreModding)
		{
			if (TitanConfig.useCoreDifficulty)
			{
				VERYHARD = EnumHelper.addEnum(EnumDifficulty.class, "VERYHARD", new Class[] 
				{
					int.class, String.class
				}

				, new Object[] 
				{
					4, "options.difficulty.veryhard"
				}
				);
				EXPERT = EnumHelper.addEnum(EnumDifficulty.class, "EXPERT", new Class[] 
				{
					int.class, String.class
				}

				, new Object[] 
				{
					5, "options.difficulty.expert"
				}
				);
				NIGHTMARE = EnumHelper.addEnum(EnumDifficulty.class, "NIGHTMARE", new Class[] 
				{
					int.class, String.class
				}

				, new Object[] 
				{
					6, "options.difficulty.nightmare"
				}
				);
				IMPOSSIBLE = EnumHelper.addEnum(EnumDifficulty.class, "IMPOSSIBLE", new Class[] 
				{
					int.class, String.class
				}

				, new Object[] 
				{
					7, "options.difficulty.impossible"
				}
				);
				TheTitans.reflect.set(EnumDifficulty.class, EnumDifficulty.PEACEFUL, DIFFICULTIES, "difficultyEnums", "field_151530_e");
			}
		}
	}

	public static void init(FMLInitializationEvent event)
	{
		if (TitanConfig.isCoreModding)
		{
		}
	}

	public static void postInit(FMLPostInitializationEvent event)
	{
		if (TitanConfig.isCoreModding)
		{
		}
	}
}


