package net.minecraft.theTitans.render;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityGargoyleTitanFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderGargoyleTitanFireball
extends Render
{
	public RenderGargoyleTitanFireball()
	{
		this.shadowSize = 0.0F;
	}

	protected ResourceLocation getTextures(EntityGargoyleTitanFireball p_180554_1_)
	{
		return null;
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getTextures((EntityGargoyleTitanFireball)entity);
	}

	public void doRender(EntityGargoyleTitanFireball p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_ + 1.5F, (float)p_76986_6_);
		GL11.glScalef(3F, 3F, 3F);
		GL11.glRotatef((p_76986_1_.ticksExisted + p_76986_9_) * 10F, 1.0F, 1.0F, 1.0F);
		int i = 15728880;
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j / 1.0F, (float)k / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.bindTexture(TextureMap.locationBlocksTexture);
		switch (p_76986_1_.getModelVarient())
		{
			case 0:
			this.renderBlock(p_76986_1_);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.5F, -0.25F, -0.5F);
			GL11.glRotatef(30F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.5F, 0.0F, 0.25F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.25F, 0.5F, 0.0F);
			GL11.glRotatef(-40F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.0F, -0.25F, 0.5F);
			GL11.glRotatef(-50F, 1.0F, 1.0F, 1.0F);
			break;
			case 1:
			this.renderBlock(p_76986_1_);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.5F, 0.25F, -0.5F);
			GL11.glRotatef(60F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.5F, 1.0F, 0.25F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.25F, -0.5F, 0.0F);
			GL11.glRotatef(-40F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.25F, -0.25F, -0.5F);
			GL11.glRotatef(-50F, 1.0F, 1.0F, 1.0F);
			break;
			case 2:
			this.renderBlock(p_76986_1_);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.5F, 0.5F, -0.25F);
			GL11.glRotatef(15F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.5F, -1.0F, -1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.5F, 0.5F, 0.5F);
			GL11.glRotatef(30F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.0F, -0.25F, -1.5F);
			GL11.glRotatef(60F, 1.0F, 1.0F, 1.0F);
			break;
			case 3:
			this.renderBlock(p_76986_1_);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.5F, 0.25F, 0.5F);
			GL11.glRotatef(-30F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.5F, 0.0F, -0.25F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.25F, 0.5F, 0.0F);
			GL11.glRotatef(40F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.0F, 0.25F, -0.5F);
			GL11.glRotatef(50F, 1.0F, 1.0F, 1.0F);
			break;
			case 4:
			this.renderBlock(p_76986_1_);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(1.5F, -0.25F, -0.5F);
			GL11.glRotatef(30F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-1.5F, 0.5F, 0.25F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.25F, 0.5F, 0.0F);
			GL11.glRotatef(80F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.0F, -0.25F, 0.5F);
			GL11.glRotatef(30F, 1.0F, 1.0F, 1.0F);
			break;
			case 5:
			this.renderBlock(p_76986_1_);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.5F, -0.75F, 0.5F);
			GL11.glRotatef(30F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.5F, 0.0F, -0.25F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.25F, -0.5F, 0.0F);
			GL11.glRotatef(-40F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.25F, -0.25F, 0.5F);
			GL11.glRotatef(-50F, 1.0F, 1.0F, 1.0F);
			break;
			case 6:
			this.renderBlock(p_76986_1_);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.5F, -0.25F, -0.5F);
			GL11.glRotatef(-60F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.5F, 0.0F, 0.25F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(-0.25F, 0.5F, 0.0F);
			GL11.glRotatef(90F, 1.0F, 1.0F, 1.0F);
			this.renderBlock(p_76986_1_);
			GL11.glTranslatef(0.0F, -0.25F, 0.5F);
			GL11.glRotatef(-30F, 1.0F, 1.0F, 1.0F);
		}

		GL11.glPopMatrix();
	}

	private void renderBlock(EntityGargoyleTitanFireball p_76986_1_)
	{
		switch (p_76986_1_.getBlockType())
		{
			case 0:
			this.field_147909_c.renderBlockAsItem(Blocks.stone, 0, 1F);
			break;
			case 1:
			this.field_147909_c.renderBlockAsItem(Blocks.coal_ore, 0, 1F);
			break;
			case 2:
			this.field_147909_c.renderBlockAsItem(Blocks.iron_ore, 0, 1F);
			break;
			case 3:
			this.field_147909_c.renderBlockAsItem(Blocks.redstone_ore, 0, 1F);
			break;
			case 4:
			this.field_147909_c.renderBlockAsItem(Blocks.gold_ore, 0, 1F);
			break;
			case 5:
			this.field_147909_c.renderBlockAsItem(Blocks.diamond_ore, 0, 1F);
			break;
			case 6:
			this.field_147909_c.renderBlockAsItem(Blocks.emerald_ore, 0, 1F);
		}
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityGargoyleTitanFireball)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}


