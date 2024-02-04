package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySkeletonTitanGiantArrow;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
@SideOnly(Side.CLIENT)
public class RenderGiantArrow extends Render
{
	public void doRender(EntitySkeletonTitanGiantArrow p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		if (!p_76986_1_.isInvisible())
		{
			GL11.glPushMatrix();
			GL11.glEnable(2977);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			this.bindEntityTexture(p_76986_1_);
			GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
			GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
			Tessellator tessellator = Tessellator.instance;
			byte b0 = 0;
			float f2 = 0.0F;
			float f3 = 0.5F;
			float f4 = (float)(0 + b0 * 10) / 32.0F;
			float f5 = (float)(5 + b0 * 10) / 32.0F;
			float f6 = 0.0F;
			float f7 = 0.15625F;
			float f8 = (float)(5 + b0 * 10) / 32.0F;
			float f9 = (float)(10 + b0 * 10) / 32.0F;
			float f10 = 1.8F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(f10, f10, f10);
			GL11.glTranslatef(-4.0F, 1.5F, -1.5F);
			GL11.glNormal3f(f10, 0.0F, 0.0F);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f8);
			tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f7, (double)f8);
			tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f7, (double)f9);
			tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f9);
			tessellator.draw();
			GL11.glNormal3f(-f10, 0.0F, 0.0F);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)f6, (double)f8);
			tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)f7, (double)f8);
			tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)f7, (double)f9);
			tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)f6, (double)f9);
			tessellator.draw();
			for (int i = 0; i < 4; ++i)
			{
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glNormal3f(0.0F, 0.0F, f10);
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double)f2, (double)f4);
				tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, (double)f3, (double)f4);
				tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, (double)f3, (double)f5);
				tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double)f2, (double)f5);
				tessellator.draw();
			}

			GL11.glDisable(3042);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}

	protected ResourceLocation getEntityTexture(EntitySkeletonTitanGiantArrow p_110775_1_)
	{
		switch (p_110775_1_.getArrowType())
		{
			case 1:
			return new ResourceLocation(TheTitans.getTextures("textures/entities", "harcadium_arrow_entity.png"));
			case 2:
			return new ResourceLocation(TheTitans.getTextures("textures/entities", "void_arrow_entity.png"));
			default:
			return new ResourceLocation("textures/entity/arrow.png");
		}
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntitySkeletonTitanGiantArrow)p_110775_1_);
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntitySkeletonTitanGiantArrow)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}


