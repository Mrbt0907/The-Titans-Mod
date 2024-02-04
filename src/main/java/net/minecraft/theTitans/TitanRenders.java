package net.minecraft.theTitans;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.theTitans.render.blocks.RenderBlockOre;
public class TitanRenders
{
	public static final int RENDERBLOCKOREID = RenderingRegistry.getNextAvailableRenderId();
	public static void init()
	{
		RenderingRegistry.registerBlockHandler(RENDERBLOCKOREID, new RenderBlockOre());
	}
}


