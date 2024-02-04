package net.minecraft.titans.utils;

import java.io.File;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class FileUtil
{
	private static String lastWorldSave;
	
	public static String getWorldFolderPath()
	{
    	if (FMLCommonHandler.instance().getMinecraftServerInstance() == null || FMLCommonHandler.instance().getMinecraftServerInstance().isSinglePlayer())
    		return FMLClientHandler.instance().getClient().mcDataDir.getPath() + File.separator + "saves" + File.separator;
    	else
    		return new File(".").getAbsolutePath() + File.separator;
    }
	
	public static String getWorldFolderName()
	{
		World world = DimensionManager.getWorld(0);
		
		if (world != null)
		{
			lastWorldSave = ((WorldServer)world).getChunkSaveLocation().getName();
			return lastWorldSave + File.separator;
		}
		
		return lastWorldSave + File.separator;
	}
}
