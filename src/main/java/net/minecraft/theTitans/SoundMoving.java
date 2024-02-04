package net.minecraft.theTitans;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
public class SoundMoving extends MovingSound
{
	private Entity entity;
	protected int soundLength;
	public int ticksExisted;
	public SoundMoving(Entity entity, String sound, float volume, float pitch, int soundLength) 
	{
		super(new ResourceLocation(sound));
		this.entity = entity;
		this.volume = volume;
		this.soundLength = soundLength;
		this.repeat = true;
		field_147663_c = pitch;
		xPosF = (float) entity.posX;
		yPosF= (float) entity.posY;
		zPosF = (float) entity.posZ;
	}

	public void update() 
	{
		if (soundLength < ticksExisted)
		donePlaying = true;
		xPosF = (float) entity.posX;
		yPosF= (float) entity.posY;
		zPosF = (float) entity.posZ;
		ticksExisted ++;
	}
}


