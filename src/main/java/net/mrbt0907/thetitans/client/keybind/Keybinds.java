package net.mrbt0907.thetitans.client.keybind;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybinds
{
	public static final String CATEGORY = "key.categories.titans";
	public static final KeyBinding KEY_ABILITY = new KeyBinding("key.mrbt0907.ability", new KeyConflictOverlap(false, false), -99, CATEGORY);
	public static final KeyBinding KEY_ALT_ABILITY = new KeyBinding("key.mrbt0907.alt.ability", new KeyConflictOverlap(false, true), Keyboard.KEY_G, CATEGORY);
	public static final KeyBinding KEY_SPECIAL_ABILITY = new KeyBinding("key.mrbt0907.special.ability", new KeyConflictOverlap(true, false), -99, CATEGORY);
	public static final IKeyConflictContext UNIVERSAL = new KeyConflictUniversal();
	
	public static void preInit()
	{
		ClientRegistry.registerKeyBinding(KEY_ABILITY);
		ClientRegistry.registerKeyBinding(KEY_ALT_ABILITY);
		ClientRegistry.registerKeyBinding(KEY_SPECIAL_ABILITY);
		MinecraftForge.EVENT_BUS.register(InputHandler.class);
	}
	
	public static void postInit()
	{
		for (KeyBinding keybind : Minecraft.getMinecraft().gameSettings.keyBindings)
			if (keybind.getKeyConflictContext() == KeyConflictContext.UNIVERSAL)
				keybind.setKeyConflictContext(UNIVERSAL);
	}
	
	public static void handleKeybind()
	{
		
	}
	
	public static class KeyConflictUniversal implements IKeyConflictContext
	{
		@Override
		public boolean isActive()
		{
			return true;
		}

		@Override
		public boolean conflicts(IKeyConflictContext other)
		{
			return !(other instanceof KeyConflictOverlap) || ((KeyConflictOverlap)other).noOverlapping;
		}
	}
	
	public static class KeyConflictOverlap implements IKeyConflictContext
	{
		public final boolean overlap;
		public final boolean noOverlapping;
		
		public KeyConflictOverlap(boolean overlap, boolean noOverlapping)
		{
			this.overlap = overlap;
			this.noOverlapping = noOverlapping;
		}
		
		@Override
		public boolean isActive()
		{
			return !KeyConflictContext.GUI.isActive();
		}

		@Override
		public boolean conflicts(IKeyConflictContext other)
		{
			if (!(other instanceof KeyConflictOverlap)) return other == KeyConflictContext.IN_GAME;
			KeyConflictOverlap context = (KeyConflictOverlap) other;
			
			return noOverlapping ? true : overlap && context.overlap;
		}
	}
}