package net.minecraft.theTitans;
import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
public class SoundHandler
{
	public static List<SoundMoving> sounds = new ArrayList<SoundMoving>();
	@SideOnly(Side.CLIENT)
	public static void playSoundAttatched(Entity entity, String name, float volume, float pitch, int soundLength)

	{
			
		Minecraft mc = Minecraft.getMinecraft(); 
		SoundMoving sound = new SoundMoving(entity, name, volume, pitch, soundLength);
		mc.getSoundHandler().playSound(sound);
		sounds.add(sound);
	}

	public static void onEntityPlay(String name, World world, Entity entityName, float volume, float pitch)
	{
		world.playSoundAtEntity(entityName, "thetitans:" + name, volume, pitch);
	}
}


