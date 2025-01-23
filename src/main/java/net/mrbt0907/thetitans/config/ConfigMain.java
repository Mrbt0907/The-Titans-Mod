package net.mrbt0907.thetitans.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.mrbt0907.thetitans.TheTitans;

@Config(modid = TheTitans.MODID)
public class ConfigMain
{
	@Name("Enable Debug Mode")
	@Comment("Enabling this option will set the mod into debug mode, allowing it to print useful information into the console for debugging purposes.")
	public static boolean debug_mode = false;

	@Name("Mob Settings")
	@Comment("Change various settings of each mob in the titans mod.")
	public static Mobs tab_mobs = new Mobs();
	
	@Name("Titan Settings")
	@Comment("Change various settings of each titan in the titans mod.")
	public static Titans tab_titans = new Titans();
	
	@Name("Other Settings")
	@Comment("A list of settings that do not fit in any other category.")
	public static Other tab_other = new Other();
	
	public static class Mobs
	{
		
	}
	
	public static class Titans
	{
		@Name("Witherzilla")
		@Comment("e")
		public TitanOptions.Witherzilla tab_witherzilla = new TitanOptions.Witherzilla();
	}
	
	public static class Other
	{
		@Name("The Forbidden Option")
		@Comment("Enabling this feature will grant all the titans complete control over the configs. Do not enable!")
		public boolean enable_nightmare_mode = false;
	}
}
