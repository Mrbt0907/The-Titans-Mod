package net.mrbt0907.thetitans.client.keybind;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.mrbt0907.thetitans.TheTitans;

public class InputHandler
{
	private static final Minecraft MC = Minecraft.getMinecraft();
	private static boolean alternateMode;
	private static long abilityTicks;
	private static long altAbilityTicks;
	private static long specialAbilityTicks;
	
	@SubscribeEvent
	public static void onKeyInput(InputEvent event)
	{
		onInput();
	}
	
	@SubscribeEvent
    public static void onMouseInput(MouseEvent event)
	{
		onInput();
	}
	
	private static void onInput()
	{
		alternateMode = Keybinds.KEY_ABILITY.getKeyCode() == Keybinds.KEY_SPECIAL_ABILITY.getKeyCode();
		if (Keybinds.KEY_ABILITY.isPressed())
			abilityTicks = 1;
		if (Keybinds.KEY_ALT_ABILITY.isPressed())
			altAbilityTicks = 1;
		if (Keybinds.KEY_SPECIAL_ABILITY.isPressed())
			specialAbilityTicks = 1;
	}
	
	public static void onInputTick()
	{
		if (alternateMode)
		{
			if (specialAbilityTicks > 0)
			{
				if (Keybinds.KEY_ABILITY.isKeyDown())
				{
					specialAbilityTicks++;
				}
				else
				{
					if (specialAbilityTicks < 20)
					{
						onAbilityPressed();
						specialAbilityTicks = 0;
					}
					else
						onSpecialAbilityPressed();
				}
			}
		}
		else
		{
			if (abilityTicks > 0 && !Keybinds.KEY_ABILITY.isKeyDown())
			{
				onAbilityPressed();
			}
			if (specialAbilityTicks > 0 && !Keybinds.KEY_SPECIAL_ABILITY.isKeyDown())
			{
				onSpecialAbilityPressed();
			}
		}
		
		if (altAbilityTicks > 0 && !Keybinds.KEY_ALT_ABILITY.isKeyDown())
		{
			onAltAbilityPressed();
		}
	}
	
	public static void onAbilityPressed()
	{
		abilityTicks = 0;
		TheTitans.info("Did Ability!");
	}
	
	public static void onAltAbilityPressed()
	{
		altAbilityTicks = 0;
		TheTitans.info("Did Alt Ability!");
	}
	
	public static void onSpecialAbilityPressed()
	{
		specialAbilityTicks = 0;
		TheTitans.info("Did Special Ability!");
	}
}