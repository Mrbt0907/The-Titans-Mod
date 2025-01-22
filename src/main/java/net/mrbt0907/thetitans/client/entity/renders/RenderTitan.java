package net.mrbt0907.thetitans.client.entity.renders;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.mrbt0907.thetitans.entity.titan.EntityTitan;

public abstract class RenderTitan<T extends EntityTitan> extends RenderLiving<T>
{
	public RenderTitan(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn)
	{
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}
	
	@Override
	public boolean shouldRender(T titan, ICamera camera, double camX, double camY, double camZ)
	{
		return true;
	}
	
	public abstract boolean shouldSpecialRender(T titan);
	
	@Override
	protected void preRenderCallback(T titan, float partialTickTime)
	{
		float f1 = titan.getRenderSizeMultiplier() * 16;
		GL11.glScalef(f1, f1, f1);
		shadowSize = 0.0F;
		GL11.glRotatef(45F, 1F, 0, 0);
		GL11.glTranslated(0, 0.75F, 0.325F);
	}
}
