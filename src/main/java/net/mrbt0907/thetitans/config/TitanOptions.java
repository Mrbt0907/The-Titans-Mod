package net.mrbt0907.thetitans.config;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;

public class TitanOptions
{
	public static class Witherzilla
	{
		@Name("Max Health")
		@Comment("The maximum amount of health this titan will have.")
		@RangeDouble(min=0.01D)
		public double health = 10000000.0D;
		
		@Name("Max Armor")
		@Comment("The maximum amount of passive armor this titan will have.")
		@RangeDouble(min=0.0D)
		public double armor = 100000.0D;

		@Name("Base Attack Damage")
		@Comment("The amount of base damage this titan will deal. This value will vary depending on the move used.")
		@RangeDouble(min=0.0D)
		public double attack_damage = 1000000.0D;

		@Name("Armor Points")
		@Comment("The maximum amount of passive armor this titan will have.")
		@RangeDouble(min=0.0D)
		public double movement_speed = 0.5D;
		
		@Name("Experience Dropped")
		@Comment("The amount of experience this titan will drop when defeated.")
		@RangeInt(min=0)
		public int experience = 5000000;
		
		@Name("Base Form Scale")
		@Comment("The amount that the titan's size will be scaled to based on it's vanilla counterpart.")
		@RangeDouble(min=0.01D, max=Float.MAX_VALUE)
		public float scale = 512.0F;
		
		@Name("True Form Scale")
		@Comment("The amount that the titan's size will be scaled to based on it's vanilla counterpart when in it's true state.")
		@RangeDouble(min=0.01D, max=Float.MAX_VALUE)
		public float true_scale = 1024.0F;
	}
}