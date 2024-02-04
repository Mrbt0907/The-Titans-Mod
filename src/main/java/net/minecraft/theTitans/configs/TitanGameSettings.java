package net.minecraft.theTitans.configs;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.world.EnumDifficulty;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
@SideOnly(Side.CLIENT)
public class TitanGameSettings extends GameSettings
{
	private static final Logger logger = LogManager.getLogger();
	private static final Gson gson = new Gson();
	private static final ParameterizedType typeListString = new ParameterizedType()
	{
		public Type[] getActualTypeArguments()
		{
			return new Type[] 
			{
				String.class
			};
		}

		public Type getRawType()
		{
			return List.class;
		}

		public Type getOwnerType()
		{
			return null;
		}
	};
	
	/** GUI scale values */
	private static final String[] GUISCALES = new String[] 
	{
		"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"
	};
	
	private static final String[] PARTICLES = new String[] 
	{
		"options.particles.all", "options.particles.decreased", "options.particles.minimal"
	};
	
	private static final String[] AMBIENT_OCCLUSIONS = new String[] 
	{
		"options.ao.off", "options.ao.min", "options.ao.max"
	};
	
	private static final String[] field_152391_aS = new String[] 
	{
		"options.stream.compression.low", "options.stream.compression.medium", "options.stream.compression.high"
	};
	
	private static final String[] field_152392_aT = new String[] 
	{
		"options.stream.chat.enabled.streaming", "options.stream.chat.enabled.always", "options.stream.chat.enabled.never"
	};
	
	private static final String[] field_152393_aU = new String[] 
	{
		"options.stream.chat.userFilter.all", "options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods"
	};
	
	private static final String[] field_152394_aV = new String[] 
	{
		"options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk"
	};
	
	private Map<SoundCategory, Object> mapSoundLevels;
	private File optionsFile;
	public TitanGameSettings(Minecraft p_i1016_1_, File p_i1016_2_)
	{
		chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
		chatColours = true;
		chatLinks = true;
		chatLinksPrompt = true;
		chatOpacity = 1.0F;
		snooperEnabled = true;
		enableVsync = true;
		pauseOnLostFocus = true;
		showCape = true;
		heldItemTooltips = true;
		chatScale = 1.0F;
		chatWidth = 1.0F;
		chatHeightUnfocused = 0.44366196F;
		chatHeightFocused = 1.0F;
		showInventoryAchievementHint = true;
		mipmapLevels = 4;
		anisotropicFiltering = 1;
		mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
		field_152400_J = 0.5F;
		field_152401_K = 1.0F;
		field_152402_L = 1.0F;
		field_152403_M = 0.5412844F;
		field_152404_N = 0.31690142F;
		field_152405_O = 1;
		field_152406_P = true;
		field_152407_Q = "";
		field_152408_R = 0;
		field_152409_S = 0;
		field_152410_T = 0;
		keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
		keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
		keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
		keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
		keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
		keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
		keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
		keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
		keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
		keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
		keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
		keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
		keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
		keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
		keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
		keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
		keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
		keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
		field_152395_am = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
		field_152396_an = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
		field_152397_ao = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
		field_152398_ap = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
		field_152399_aq = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
		keyBindsHotbar = new KeyBinding[] 
		{
			new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")
		};
		
		keyBindings = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[] 
		{
			keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat, keyBindPlayerList, keyBindPickBlock, keyBindCommand, keyBindScreenshot, keyBindTogglePerspective, keyBindSmoothCamera, keyBindSprint, field_152396_an, field_152397_ao, field_152398_ap, field_152399_aq, field_152395_am
		}

		, keyBindsHotbar);
		difficulty = EnumDifficulty.NORMAL;
		lastServer = "";
		noclipRate = 1.0F;
		debugCamRate = 1.0F;
		fovSetting = 70.0F;
		language = "en_US";
		forceUnicodeFont = false;
		mc = p_i1016_1_;
		optionsFile = new File(p_i1016_2_, "options.txt");
		GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0F);
		renderDistanceChunks = p_i1016_1_.isJava64bit() ? 12 : 8;
		loadOptions();
	}

	public TitanGameSettings()
	{
		chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
		chatColours = true;
		chatLinks = true;
		chatLinksPrompt = true;
		chatOpacity = 1.0F;
		snooperEnabled = true;
		enableVsync = true;
		pauseOnLostFocus = true;
		showCape = true;
		heldItemTooltips = true;
		chatScale = 1.0F;
		chatWidth = 1.0F;
		chatHeightUnfocused = 0.44366196F;
		chatHeightFocused = 1.0F;
		showInventoryAchievementHint = true;
		mipmapLevels = 4;
		anisotropicFiltering = 1;
		mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
		field_152400_J = 0.5F;
		field_152401_K = 1.0F;
		field_152402_L = 1.0F;
		field_152403_M = 0.5412844F;
		field_152404_N = 0.31690142F;
		field_152405_O = 1;
		field_152406_P = true;
		field_152407_Q = "";
		field_152408_R = 0;
		field_152409_S = 0;
		field_152410_T = 0;
		keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
		keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
		keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
		keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
		keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
		keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
		keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
		keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
		keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
		keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
		keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
		keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
		keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
		keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
		keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
		keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
		keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
		keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
		field_152395_am = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
		field_152396_an = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
		field_152397_ao = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
		field_152398_ap = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
		field_152399_aq = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
		keyBindsHotbar = new KeyBinding[] 
		{
			new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")
		};
		
		keyBindings = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[] 
		{
			keyBindAttack, keyBindUseItem, keyBindForward, keyBindLeft, keyBindBack, keyBindRight, keyBindJump, keyBindSneak, keyBindDrop, keyBindInventory, keyBindChat, keyBindPlayerList, keyBindPickBlock, keyBindCommand, keyBindScreenshot, keyBindTogglePerspective, keyBindSmoothCamera, keyBindSprint, field_152396_an, field_152397_ao, field_152398_ap, field_152399_aq, field_152395_am
		}

		, keyBindsHotbar);
		difficulty = EnumDifficulty.NORMAL;
		lastServer = "";
		noclipRate = 1.0F;
		debugCamRate = 1.0F;
		fovSetting = 70.0F;
		language = "en_US";
		forceUnicodeFont = false;
	}

	/**
	* Represents a key or mouse button as a string. Args: key
	*/
	public static String getKeyDisplayString(int p_74298_0_)
	{
		return p_74298_0_ < 0 ? I18n.format("key.mouseButton", new Object[] 
		{
			Integer.valueOf(p_74298_0_ + 101)
		}
		): Keyboard.getKeyName(p_74298_0_);
	}

	/**
	* Returns whether the specified key binding is currently being pressed.
	*/
	public static boolean isKeyDown(KeyBinding p_100015_0_)
	{
		return p_100015_0_.getKeyCode() == 0 ? false : (p_100015_0_.getKeyCode() < 0 ? Mouse.isButtonDown(p_100015_0_.getKeyCode() + 100) : Keyboard.isKeyDown(p_100015_0_.getKeyCode()));
	}

	/**
	* Sets a key binding and then saves all settings.
	*/
	public void setOptionKeyBinding(KeyBinding p_151440_1_, int p_151440_2_)
	{
		p_151440_1_.setKeyCode(p_151440_2_);
		saveOptions();
	}

	/**
	* If the specified option is controlled by a slider (float value), this will set the float value.
	*/
	public void setOptionFloatValue(GameSettings.Options p_74304_1_, float p_74304_2_)
	{
		if (p_74304_1_ == GameSettings.Options.SENSITIVITY)
		{
			mouseSensitivity = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.FOV)
		{
			fovSetting = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.GAMMA)
		{
			gammaSetting = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.FRAMERATE_LIMIT)
		{
			limitFramerate = (int)p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_OPACITY)
		{
			chatOpacity = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED)
		{
			chatHeightFocused = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED)
		{
			chatHeightUnfocused = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_WIDTH)
		{
			chatWidth = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		if (p_74304_1_ == GameSettings.Options.CHAT_SCALE)
		{
			chatScale = p_74304_2_;
			mc.ingameGUI.getChatGUI().refreshChat();
		}

		int i;
		if (p_74304_1_ == GameSettings.Options.ANISOTROPIC_FILTERING)
		{
			i = anisotropicFiltering;
			anisotropicFiltering = (int)p_74304_2_;
			if ((float)i != p_74304_2_)
			{
				mc.getTextureMapBlocks().setAnisotropicFiltering(anisotropicFiltering);
				mc.scheduleResourcesRefresh();
			}
		}

		if (p_74304_1_ == GameSettings.Options.MIPMAP_LEVELS)
		{
			i = mipmapLevels;
			mipmapLevels = (int)p_74304_2_;
			if ((float)i != p_74304_2_)
			{
				mc.getTextureMapBlocks().setMipmapLevels(mipmapLevels);
				mc.scheduleResourcesRefresh();
			}
		}

		if (p_74304_1_ == GameSettings.Options.RENDER_DISTANCE)
		{
			renderDistanceChunks = (int)p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_BYTES_PER_PIXEL)
		{
			field_152400_J = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_VOLUME_MIC)
		{
			field_152401_K = p_74304_2_;
			mc.func_152346_Z().func_152915_s();
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_VOLUME_SYSTEM)
		{
			field_152402_L = p_74304_2_;
			mc.func_152346_Z().func_152915_s();
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_KBPS)
		{
			field_152403_M = p_74304_2_;
		}

		if (p_74304_1_ == GameSettings.Options.STREAM_FPS)
		{
			field_152404_N = p_74304_2_;
		}
	}

	/**
	* For non-float options. Toggles the option on/off, or cycles through the list i.e. render distances.
	*/
	public void setOptionValue(GameSettings.Options p_74306_1_, int p_74306_2_)
	{
		switch (p_74306_1_)
		{
			case INVERT_MOUSE: invertMouse = !invertMouse; break;
			case GUI_SCALE: guiScale = guiScale + p_74306_2_ & 3; break;
			case PARTICLES: particleSetting = (particleSetting + p_74306_2_) % 3; break;
			case VIEW_BOBBING: viewBobbing = !viewBobbing; break;
			case RENDER_CLOUDS: clouds = !clouds; break;
			case FORCE_UNICODE_FONT:
			{
				forceUnicodeFont = !forceUnicodeFont;
				mc.fontRenderer.setUnicodeFlag(mc.getLanguageManager().isCurrentLocaleUnicode() || forceUnicodeFont);
				break;
			}

			case ADVANCED_OPENGL: 
			{
				advancedOpengl = !advancedOpengl;
				mc.renderGlobal.loadRenderers();
				break;
			}

			case FBO_ENABLE: fboEnable = !fboEnable; break;
			case ANAGLYPH:
			{
				anaglyph = !anaglyph;
				mc.refreshResources();
				break;
			}

			case DIFFICULTY:
			{
				int dif = difficulty.getDifficultyId() + p_74306_2_;
				if (dif >= EnumDifficulty.values().length)
				dif = 0;
				difficulty = EnumDifficulty.values()[dif];
				break;
			}

			case GRAPHICS:
			{
				fancyGraphics = !fancyGraphics;
				mc.renderGlobal.loadRenderers();
				break;
			}

			case AMBIENT_OCCLUSION:
			{
				ambientOcclusion = (ambientOcclusion + p_74306_2_) % 3;
				mc.renderGlobal.loadRenderers();
				break;
			}

			case CHAT_VISIBILITY: chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((chatVisibility.getChatVisibility() + p_74306_2_) % 3); break;
			case STREAM_COMPRESSION: field_152405_O = (field_152405_O + p_74306_2_) % 3; break;
			case STREAM_SEND_METADATA: field_152406_P = !field_152406_P; break;
			case STREAM_CHAT_ENABLED: field_152408_R = (field_152408_R + p_74306_2_) % 3; break;
			case STREAM_CHAT_USER_FILTER: field_152409_S = (field_152409_S + p_74306_2_) % 3; break;
			case STREAM_MIC_TOGGLE_BEHAVIOR: field_152410_T = (field_152410_T + p_74306_2_) % 2; break;
			case CHAT_COLOR: chatColours = !chatColours; break;
			case CHAT_LINKS: chatLinks = !chatLinks; break;
			case CHAT_LINKS_PROMPT: chatLinksPrompt = !chatLinksPrompt; break;
			case SNOOPER_ENABLED: snooperEnabled = !snooperEnabled; break;
			case SHOW_CAPE: showCape = !showCape; break;
			case TOUCHSCREEN: touchscreen = !touchscreen; break;
			case USE_FULLSCREEN:
			{
				fullScreen = !fullScreen;
				if (mc.isFullScreen() != fullScreen)
				mc.toggleFullscreen();
				break;
			}

			case ENABLE_VSYNC:
			{
				enableVsync = !enableVsync;
				Display.setVSyncEnabled(enableVsync);
				break;
			}

			default: ;
		}

		saveOptions();
	}

	public float getOptionFloatValue(GameSettings.Options p_74296_1_)
	{
		return p_74296_1_ == GameSettings.Options.FOV ? fovSetting : (p_74296_1_ == GameSettings.Options.GAMMA ? gammaSetting : (p_74296_1_ == GameSettings.Options.SATURATION ? saturation : (p_74296_1_ == GameSettings.Options.SENSITIVITY ? mouseSensitivity : (p_74296_1_ == GameSettings.Options.CHAT_OPACITY ? chatOpacity : (p_74296_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED ? chatHeightFocused : (p_74296_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED ? chatHeightUnfocused : (p_74296_1_ == GameSettings.Options.CHAT_SCALE ? chatScale : (p_74296_1_ == GameSettings.Options.CHAT_WIDTH ? chatWidth : (p_74296_1_ == GameSettings.Options.FRAMERATE_LIMIT ? (float)limitFramerate : (p_74296_1_ == GameSettings.Options.ANISOTROPIC_FILTERING ? (float)anisotropicFiltering : (p_74296_1_ == GameSettings.Options.MIPMAP_LEVELS ? (float)mipmapLevels : (p_74296_1_ == GameSettings.Options.RENDER_DISTANCE ? (float)renderDistanceChunks : (p_74296_1_ == GameSettings.Options.STREAM_BYTES_PER_PIXEL ? field_152400_J : (p_74296_1_ == GameSettings.Options.STREAM_VOLUME_MIC ? field_152401_K : (p_74296_1_ == GameSettings.Options.STREAM_VOLUME_SYSTEM ? field_152402_L : (p_74296_1_ == GameSettings.Options.STREAM_KBPS ? field_152403_M : (p_74296_1_ == GameSettings.Options.STREAM_FPS ? field_152404_N : 0.0F)))))))))))))))));
	}

	public boolean getOptionOrdinalValue(GameSettings.Options p_74308_1_)
	{
		switch (TitanGameSettings.SwitchOptions.optionIds[p_74308_1_.ordinal()])
		{
			case 1:
			return invertMouse;
			case 2:
			return viewBobbing;
			case 3:
			return anaglyph;
			case 4:
			return advancedOpengl;
			case 5:
			return fboEnable;
			case 6:
			return clouds;
			case 7:
			return chatColours;
			case 8:
			return chatLinks;
			case 9:
			return chatLinksPrompt;
			case 10:
			return snooperEnabled;
			case 11:
			return fullScreen;
			case 12:
			return enableVsync;
			case 13:
			return showCape;
			case 14:
			return touchscreen;
			case 15:
			return field_152406_P;
			case 16:
			return forceUnicodeFont;
			default:
			return false;
		}
	}

	/**
	* Returns the translation of the given index in the given String array. If the index is smaller than 0 or greater
	* than/equal to the length of the String array, it is changed to 0.
	*/
	private static String getTranslation(String[] p_74299_0_, int p_74299_1_)
	{
		if (p_74299_1_ < 0 || p_74299_1_ >= p_74299_0_.length)
		{
			p_74299_1_ = 0;
		}

		return I18n.format(p_74299_0_[p_74299_1_], new Object[0]);
	}

	/**
	* Gets a key binding.
	*/
	public String getKeyBinding(GameSettings.Options p_74297_1_)
	{
		String s = I18n.format(p_74297_1_.getEnumString(), new Object[0]) + ": ";
		if (p_74297_1_.getEnumFloat())
		{
			float f1 = getOptionFloatValue(p_74297_1_);
			float f = p_74297_1_.normalizeValue(f1);
			return p_74297_1_ == GameSettings.Options.SENSITIVITY ? (f == 0.0F ? s + I18n.format("options.sensitivity.min", new Object[0]) : (f == 1.0F ? s + I18n.format("options.sensitivity.max", new Object[0]) : s + (int)(f * 200.0F) + "%")) : (p_74297_1_ == GameSettings.Options.FOV ? (f1 == 70.0F ? s + I18n.format("options.fov.min", new Object[0]) : (f1 == 110.0F ? s + I18n.format("options.fov.max", new Object[0]) : s + (int)f1)) : (p_74297_1_ == GameSettings.Options.FRAMERATE_LIMIT ? (f1 == p_74297_1_.getValueMax() ? s + I18n.format("options.framerateLimit.max", new Object[0]) : s + (int)f1 + " fps") : (p_74297_1_ == GameSettings.Options.GAMMA ? (f == 0.0F ? s + I18n.format("options.gamma.min", new Object[0]) : (f == 1.0F ? s + I18n.format("options.gamma.max", new Object[0]) : s + "+" + (int)(f * 100.0F) + "%")) : (p_74297_1_ == GameSettings.Options.SATURATION ? s + (int)(f * 400.0F) + "%" : (p_74297_1_ == GameSettings.Options.CHAT_OPACITY ? s + (int)(f * 90.0F + 10.0F) + "%" : (p_74297_1_ == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED ? s + GuiNewChat.func_146243_b(f) + "px" : (p_74297_1_ == GameSettings.Options.CHAT_HEIGHT_FOCUSED ? s + GuiNewChat.func_146243_b(f) + "px" : (p_74297_1_ == GameSettings.Options.CHAT_WIDTH ? s + GuiNewChat.func_146233_a(f) + "px" : (p_74297_1_ == GameSettings.Options.RENDER_DISTANCE ? s + (int)f1 + " chunks" : (p_74297_1_ == GameSettings.Options.ANISOTROPIC_FILTERING ? (f1 == 1.0F ? s + I18n.format("options.off", new Object[0]) : s + (int)f1) : (p_74297_1_ == GameSettings.Options.MIPMAP_LEVELS ? (f1 == 0.0F ? s + I18n.format("options.off", new Object[0]) : s + (int)f1) : (p_74297_1_ == GameSettings.Options.STREAM_FPS ? s + TwitchStream.func_152948_a(f) + " fps" : (p_74297_1_ == GameSettings.Options.STREAM_KBPS ? s + TwitchStream.func_152946_b(f) + " Kbps" : (p_74297_1_ == GameSettings.Options.STREAM_BYTES_PER_PIXEL ? s + String.format("%.3f bpp", new Object[] 
			{
				Float.valueOf(TwitchStream.func_152947_c(f))
			}
			): (f == 0.0F ? s + I18n.format("options.off", new Object[0]) : s + (int)(f * 100.0F) + "%")))))))))))))));
		}

		else if (p_74297_1_.getEnumBoolean())
		{
			boolean flag = getOptionOrdinalValue(p_74297_1_);
			return flag ? s + I18n.format("options.on", new Object[0]) : s + I18n.format("options.off", new Object[0]);
		}

		else if (p_74297_1_ == GameSettings.Options.DIFFICULTY)
		{
			return s + I18n.format(difficulty.getDifficultyResourceKey(), new Object[0]);
		}

		else if (p_74297_1_ == GameSettings.Options.GUI_SCALE)
		{
			return s + getTranslation(GUISCALES, guiScale);
		}

		else if (p_74297_1_ == GameSettings.Options.CHAT_VISIBILITY)
		{
			return s + I18n.format(chatVisibility.getResourceKey(), new Object[0]);
		}

		else if (p_74297_1_ == GameSettings.Options.PARTICLES)
		{
			return s + getTranslation(PARTICLES, particleSetting);
		}

		else if (p_74297_1_ == GameSettings.Options.AMBIENT_OCCLUSION)
		{
			return s + getTranslation(AMBIENT_OCCLUSIONS, ambientOcclusion);
		}

		else if (p_74297_1_ == GameSettings.Options.STREAM_COMPRESSION)
		{
			return s + getTranslation(field_152391_aS, field_152405_O);
		}

		else if (p_74297_1_ == GameSettings.Options.STREAM_CHAT_ENABLED)
		{
			return s + getTranslation(field_152392_aT, field_152408_R);
		}

		else if (p_74297_1_ == GameSettings.Options.STREAM_CHAT_USER_FILTER)
		{
			return s + getTranslation(field_152393_aU, field_152409_S);
		}

		else if (p_74297_1_ == GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR)
		{
			return s + getTranslation(field_152394_aV, field_152410_T);
		}

		else if (p_74297_1_ == GameSettings.Options.GRAPHICS)
		{
			if (fancyGraphics)
			{
				return s + I18n.format("options.graphics.fancy", new Object[0]);
			}

			else
			{
				return s + I18n.format("options.graphics.fast", new Object[0]);
			}
		}

		else
		return s;
	}

	/**
	* Loads the options from the options file. It appears that this has replaced the previous 'loadOptions'
	*/
	@SuppressWarnings("rawtypes")
	public void loadOptions()
	{
		try
		{
			if (!optionsFile.exists()) return;
			BufferedReader bufferedreader = new BufferedReader(new FileReader(optionsFile));
			String s = "";
			mapSoundLevels.clear();
			while ((s = bufferedreader.readLine()) != null)
			{
				try
				{
					String[] astring = s.split(":");
					switch(astring[0])
					{
						case "mouseSensitivity": mouseSensitivity = parseFloat(astring[1]); break;
						case "invertYMouse": invertMouse = astring[1].equals("true"); break;
						case "gamma": gammaSetting = parseFloat(astring[1]); break;
						case "saturation": saturation = parseFloat(astring[1]); break;
						case "fov": fovSetting = parseFloat(astring[1]) * 40.0F + 70.0F; break;
						case "renderDistance": renderDistanceChunks = Integer.parseInt(astring[1]); break;
						case "guiScale": guiScale = Integer.parseInt(astring[1]); break;
						case "particles": particleSetting = Integer.parseInt(astring[1]); break;
						case "bobView": viewBobbing = astring[1].equals("true"); break;
						case "anaglyph3d": anaglyph = astring[1].equals("true"); break;
						case "advancedOpengl": advancedOpengl = astring[1].equals("true"); break;
						case "maxFps": limitFramerate = Integer.parseInt(astring[1]); break;
						case "fboEnable": fboEnable = astring[1].equals("true"); break;
						case "difficulty": difficulty = EnumDifficulty.values()[Integer.parseInt(astring[1])]; break;
						case "fancyGraphics": fancyGraphics = astring[1].equals("true"); break;
						case "ao": 
						if (astring[1].equals("true"))
						ambientOcclusion = 2;
						else if (astring[1].equals("false"))
						ambientOcclusion = 0;
						else
						ambientOcclusion = Integer.parseInt(astring[1]);
						break;
						case "clouds": clouds = astring[1].equals("true"); break;
						case "resourcePacks": 
						{
							resourcePacks = (List<?>)gson.fromJson(s.substring(s.indexOf(58) + 1), typeListString);
							if (resourcePacks == null)
							resourcePacks = new ArrayList();
							break;
						}

						case "lastServer": 
						if (astring.length >= 2)
						lastServer = s.substring(s.indexOf(58) + 1);
						break;
						case "lang": 
						if (astring.length >= 2)
						language = astring[1];
						break;
						case "chatVisibility": chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(astring[1])); break;
						case "chatColors": chatColours = astring[1].equals("true"); break;
						case "chatLinks": chatLinks = astring[1].equals("true"); break;
						case "chatLinksPrompt": chatLinksPrompt = astring[1].equals("true"); break;
						case "chatOpacity": chatOpacity = parseFloat(astring[1]); break;
						case "snooperEnabled": snooperEnabled = astring[1].equals("true"); break;
						case "fullscreen": fullScreen = astring[1].equals("true"); break;
						case "enableVsync": enableVsync = astring[1].equals("true"); break;
						case "hideServerAddress": hideServerAddress = astring[1].equals("true"); break;
						case "advancedItemTooltips": advancedItemTooltips = astring[1].equals("true"); break;
						case "pauseOnLostFocus": pauseOnLostFocus = astring[1].equals("true"); break;
						case "showCape": showCape = astring[1].equals("true"); break;
						case "touchscreen": touchscreen = astring[1].equals("true"); break;
						case "overrideHeight": overrideHeight = Integer.parseInt(astring[1]); break;
						case "overrideWidth": overrideWidth = Integer.parseInt(astring[1]); break;
						case "heldItemTooltips": heldItemTooltips = astring[1].equals("true"); break;
						case "chatHeightFocused": chatHeightFocused = parseFloat(astring[1]); break;
						case "chatHeightUnfocused": chatHeightUnfocused = parseFloat(astring[1]); break;
						case "chatScale": chatScale = parseFloat(astring[1]); break;
						case "chatWidth": chatWidth = parseFloat(astring[1]); break;
						case "showInventoryAchievementHint": showInventoryAchievementHint = astring[1].equals("true"); break;
						case "mipmapLevels": mipmapLevels = Integer.parseInt(astring[1]); break;
						case "anisotropicFiltering": anisotropicFiltering = Integer.parseInt(astring[1]); break;
						case "streamBytesPerPixel": field_152400_J = parseFloat(astring[1]); break;
						case "streamMicVolume": field_152401_K = parseFloat(astring[1]); break;
						case "streamSystemVolume": field_152402_L = parseFloat(astring[1]); break;
						case "streamKbps": field_152403_M = parseFloat(astring[1]); break;
						case "streamFps": field_152404_N = parseFloat(astring[1]); break;
						case "streamCompression": field_152405_O = Integer.parseInt(astring[1]); break;
						case "streamSendMetadata": field_152406_P = astring[1].equals("true"); break;
						case "streamPreferredServer": 
						if (astring.length >= 2)
						field_152407_Q = s.substring(s.indexOf(58) + 1); break;
						case "streamChatEnabled": field_152408_R = Integer.parseInt(astring[1]); break;
						case "streamChatUserFilter": field_152409_S = Integer.parseInt(astring[1]); break;
						case "streamMicToggleBehavior": field_152410_T = Integer.parseInt(astring[1]); break;
						case "forceUnicodeFont": forceUnicodeFont = astring[1].equals("true"); break;
						default:
						{
							KeyBinding[] akeybinding = keyBindings;
							int i = akeybinding.length;
							int j;
							for (j = 0; j < i; ++j)
							{
								KeyBinding keybinding = akeybinding[j];
								if (astring[0].equals("key_" + keybinding.getKeyDescription()))
								keybinding.setKeyCode(Integer.parseInt(astring[1]));
							}

							SoundCategory[] asoundcategory = SoundCategory.values();
							i = asoundcategory.length;
							for (j = 0; j < i; ++j)
							{
								SoundCategory soundcategory = asoundcategory[j];
								if (astring[0].equals("soundCategory_" + soundcategory.getCategoryName()))
								mapSoundLevels.put(soundcategory, Float.valueOf(parseFloat(astring[1])));
							}
						}
					}
				}

				catch (Exception exception)
				{
					logger.warn("Skipping bad option: " + s);
				}
			}

			KeyBinding.resetKeyBindingArrayAndHash();
			bufferedreader.close();
		}

		catch (Exception exception1)
		{
			logger.error("Failed to load options", exception1);
		}
	}

	/**
	* Parses a string into a float.
	*/
	private float parseFloat(String p_74305_1_)
	{
		return p_74305_1_.equals("true") ? 1.0F : (p_74305_1_.equals("false") ? 0.0F : Float.parseFloat(p_74305_1_));
	}

	/**
	* Saves the options to the options file.
	*/
	public void saveOptions()
	{
		if (FMLClientHandler.instance().isLoading()) return;
		try
		{
			PrintWriter printwriter = new PrintWriter(new FileWriter(optionsFile));
			printwriter.println("invertYMouse:" + invertMouse);
			printwriter.println("mouseSensitivity:" + mouseSensitivity);
			printwriter.println("fov:" + (fovSetting - 70.0F) / 40.0F);
			printwriter.println("gamma:" + gammaSetting);
			printwriter.println("saturation:" + saturation);
			printwriter.println("renderDistance:" + renderDistanceChunks);
			printwriter.println("guiScale:" + guiScale);
			printwriter.println("particles:" + particleSetting);
			printwriter.println("bobView:" + viewBobbing);
			printwriter.println("anaglyph3d:" + anaglyph);
			printwriter.println("advancedOpengl:" + advancedOpengl);
			printwriter.println("maxFps:" + limitFramerate);
			printwriter.println("fboEnable:" + fboEnable);
			printwriter.println("difficulty:" + difficulty.getDifficultyId());
			printwriter.println("fancyGraphics:" + fancyGraphics);
			printwriter.println("ao:" + ambientOcclusion);
			printwriter.println("clouds:" + clouds);
			printwriter.println("resourcePacks:" + gson.toJson(resourcePacks));
			printwriter.println("lastServer:" + lastServer);
			printwriter.println("lang:" + language);
			printwriter.println("chatVisibility:" + chatVisibility.getChatVisibility());
			printwriter.println("chatColors:" + chatColours);
			printwriter.println("chatLinks:" + chatLinks);
			printwriter.println("chatLinksPrompt:" + chatLinksPrompt);
			printwriter.println("chatOpacity:" + chatOpacity);
			printwriter.println("snooperEnabled:" + snooperEnabled);
			printwriter.println("fullscreen:" + fullScreen);
			printwriter.println("enableVsync:" + enableVsync);
			printwriter.println("hideServerAddress:" + hideServerAddress);
			printwriter.println("advancedItemTooltips:" + advancedItemTooltips);
			printwriter.println("pauseOnLostFocus:" + pauseOnLostFocus);
			printwriter.println("showCape:" + showCape);
			printwriter.println("touchscreen:" + touchscreen);
			printwriter.println("overrideWidth:" + overrideWidth);
			printwriter.println("overrideHeight:" + overrideHeight);
			printwriter.println("heldItemTooltips:" + heldItemTooltips);
			printwriter.println("chatHeightFocused:" + chatHeightFocused);
			printwriter.println("chatHeightUnfocused:" + chatHeightUnfocused);
			printwriter.println("chatScale:" + chatScale);
			printwriter.println("chatWidth:" + chatWidth);
			printwriter.println("showInventoryAchievementHint:" + showInventoryAchievementHint);
			printwriter.println("mipmapLevels:" + mipmapLevels);
			printwriter.println("anisotropicFiltering:" + anisotropicFiltering);
			printwriter.println("streamBytesPerPixel:" + field_152400_J);
			printwriter.println("streamMicVolume:" + field_152401_K);
			printwriter.println("streamSystemVolume:" + field_152402_L);
			printwriter.println("streamKbps:" + field_152403_M);
			printwriter.println("streamFps:" + field_152404_N);
			printwriter.println("streamCompression:" + field_152405_O);
			printwriter.println("streamSendMetadata:" + field_152406_P);
			printwriter.println("streamPreferredServer:" + field_152407_Q);
			printwriter.println("streamChatEnabled:" + field_152408_R);
			printwriter.println("streamChatUserFilter:" + field_152409_S);
			printwriter.println("streamMicToggleBehavior:" + field_152410_T);
			printwriter.println("forceUnicodeFont:" + forceUnicodeFont);
			KeyBinding[] akeybinding = keyBindings;
			int i = akeybinding.length;
			int j;
			for (j = 0; j < i; ++j)
			{
				KeyBinding keybinding = akeybinding[j];
				printwriter.println("key_" + keybinding.getKeyDescription() + ":" + keybinding.getKeyCode());
			}

			SoundCategory[] asoundcategory = SoundCategory.values();
			i = asoundcategory.length;
			for (j = 0; j < i; ++j)
			{
				SoundCategory soundcategory = asoundcategory[j];
				printwriter.println("soundCategory_" + soundcategory.getCategoryName() + ":" + getSoundLevel(soundcategory));
			}

			printwriter.close();
		}

		catch (Exception exception)
		{
			logger.error("Failed to save options", exception);
		}

		sendSettingsToServer();
	}

	public float getSoundLevel(SoundCategory p_151438_1_)
	{
		return mapSoundLevels.containsKey(p_151438_1_) ? ((Float)mapSoundLevels.get(p_151438_1_)).floatValue() : 1.0F;
	}

	public void setSoundLevel(SoundCategory p_151439_1_, float p_151439_2_)
	{
		mc.getSoundHandler().setSoundLevel(p_151439_1_, p_151439_2_);
		mapSoundLevels.put(p_151439_1_, Float.valueOf(p_151439_2_));
	}

	/**
	* Send a client info packet with settings information to the server
	*/
	public void sendSettingsToServer()
	{
		if (mc.thePlayer != null)
		mc.thePlayer.sendQueue.addToSendQueue(new C15PacketClientSettings(language, renderDistanceChunks, chatVisibility, chatColours, difficulty, showCape));
	}

	/**
	* Should render clouds
	*/
	public boolean shouldRenderClouds()
	{
		return renderDistanceChunks >= 4 && clouds;
	}

	@SideOnly(Side.CLIENT)
	static final class SwitchOptions
	{
		static final int[] optionIds = new int[GameSettings.Options.values().length];
		static
		{
			try
			{
				optionIds[GameSettings.Options.INVERT_MOUSE.ordinal()] = 1;
			}

			catch (NoSuchFieldError var16)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.VIEW_BOBBING.ordinal()] = 2;
			}

			catch (NoSuchFieldError var15)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.ANAGLYPH.ordinal()] = 3;
			}

			catch (NoSuchFieldError var14)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.ADVANCED_OPENGL.ordinal()] = 4;
			}

			catch (NoSuchFieldError var13)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.FBO_ENABLE.ordinal()] = 5;
			}

			catch (NoSuchFieldError var12)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.RENDER_CLOUDS.ordinal()] = 6;
			}

			catch (NoSuchFieldError var11)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.CHAT_COLOR.ordinal()] = 7;
			}

			catch (NoSuchFieldError var10)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.CHAT_LINKS.ordinal()] = 8;
			}

			catch (NoSuchFieldError var9)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.CHAT_LINKS_PROMPT.ordinal()] = 9;
			}

			catch (NoSuchFieldError var8)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.SNOOPER_ENABLED.ordinal()] = 10;
			}

			catch (NoSuchFieldError var7)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.USE_FULLSCREEN.ordinal()] = 11;
			}

			catch (NoSuchFieldError var6)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.ENABLE_VSYNC.ordinal()] = 12;
			}

			catch (NoSuchFieldError var5)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.SHOW_CAPE.ordinal()] = 13;
			}

			catch (NoSuchFieldError var4)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.TOUCHSCREEN.ordinal()] = 14;
			}

			catch (NoSuchFieldError var3)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.STREAM_SEND_METADATA.ordinal()] = 15;
			}

			catch (NoSuchFieldError var2)
			{
				;
			}

			try
			{
				optionIds[GameSettings.Options.FORCE_UNICODE_FONT.ordinal()] = 16;
			}

			catch (NoSuchFieldError var1)
			{
				;
			}
		}
	}
}


