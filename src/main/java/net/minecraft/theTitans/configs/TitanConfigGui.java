package net.minecraft.theTitans.configs;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.theTitans.TheTitans;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
public class TitanConfigGui implements IModGuiFactory 
{
	private static String id = TheTitans.MODID + ".config.";
	protected static class TitanGui extends GuiConfig 
	{
		public TitanGui(GuiScreen parent)
		{
			super(parent, getConfigElements(), TheTitans.MODID, false, false, I18n.format(id + "title"));
		}

		@SuppressWarnings("rawtypes")
		private static List<IConfigElement> getConfigElements()
		{
			List<IConfigElement> config = new ArrayList<IConfigElement>();
			List<ConfigCategory> categories = new ArrayList<ConfigCategory>();
			categories.add(TheTitans.config.getCategory("general"));
			categories.add(TheTitans.config.getCategory("client"));
			categories.add(TheTitans.config.getCategory("core"));
			categories.add(TheTitans.config.getCategory("blocks"));
			categories.add(TheTitans.config.getCategory("items"));
			categories.add(TheTitans.config.getCategory("mobs"));
			categories.add(TheTitans.config.getCategory("misc"));
			for (int i = 0; i < categories.size(); i++)
			if (categories.get(i).getName().equals("general"))
			config.addAll(new ConfigElement<IConfigElement<?>>(categories.get(i)).getChildElements());
			else
			config.add(new ConfigElement<IConfigElement<?>>(categories.get(i)));
			return config;
		}
	}

	@Override
	public void initialize(Minecraft minecraftInstance) 
	{

	}


	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return TitanGui.class;
	}

	private static final Set<RuntimeOptionCategoryElement> fmlCategories = ImmutableSet.of(new RuntimeOptionCategoryElement("HELP", "FML"));
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return fmlCategories;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		return new RuntimeOptionGuiHandler()
		{
			@Override
			public void paint(int x, int y, int w, int h)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void close() 
			{

			}


			@Override
			public void addWidgets(List<Gui> widgets, int x, int y, int w, int h)
			{
				widgets.add(new GuiButton(100, x+10, y+10, "HELLO"));
			}

			@Override
			public void actionCallback(int actionId) 
			{

			}
		};
	}
}


