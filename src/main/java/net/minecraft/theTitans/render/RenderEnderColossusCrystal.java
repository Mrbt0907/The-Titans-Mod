package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityEnderColossusCrystal;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelEnderColossusCrystal;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderEnderColossusCrystal
extends Render
{
	private static final ResourceLocation enderColossusCrystalBeamTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "endercrystal_beam.png"));
	private static final ResourceLocation enderCrystalTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "crystal.png"));
	private ModelBase field_76995_b;
	public RenderEnderColossusCrystal()
	{
		this.shadowSize = 1.0F;
		this.field_76995_b = new ModelEnderColossusCrystal(0.0F, true);
	}

	public void doRender(EntityEnderColossusCrystal entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		float f2 = entity.innerRotation + partialTicks;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		bindTexture(enderCrystalTextures);
		float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
		f3 += f3 * f3;
		this.field_76995_b.render(entity, 0.0F, f2 * 3.0F, f3 * 0.2F, 0.0F, 0.0F, 0.0625F);
		if (entity.hurtTime > 0 || !entity.isEntityAlive())
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
			this.field_76995_b.render(entity, 0.0F, f2 * 3.0F, f3 * 0.2F, 0.0F, 0.0F, 0.0625F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		GL11.glPopMatrix();
		if (entity.owner != null && !entity.owner.isStunned && entity.isEntityAlive())
		{
			float f1 = (entity.ticksExisted + partialTicks);
			float f4 = (float)(entity.owner.body.posX - entity.posX - (entity.prevPosX - entity.posX) * (1.0F - partialTicks));
			float f5 = (float)(entity.owner.body.posY - entity.posY + (entity.owner.body.height * 0.5F) + (double)f3 - (entity.prevPosY - entity.posY) * (1.0F - partialTicks));
			float f6 = (float)(entity.owner.body.posZ - entity.posZ - (entity.prevPosZ - entity.posZ) * (1.0F - partialTicks));
			float f7 = MathHelper.sqrt_float(f4 * f4 + f6 * f6);
			float f8 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6);
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y + 1F, (float)z);
			GL11.glRotatef((float)-Math.atan2(f6, f4) * 180.0F / 3.1415927F - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef((float)-Math.atan2(f7, f5) * 180.0F / 3.1415927F - 90.0F, 1.0F, 0.0F, 0.0F);
			Tessellator tessellator = Tessellator.instance;
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_CULL_FACE);
			bindTexture(enderColossusCrystalBeamTextures);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			float f9 = f1 * 0.005F;
			float f10 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6) / 32.0F - f9;
			tessellator.startDrawing(5);
			byte b0 = 64;
			int c0 = 15728880;
			int j = c0 % 65536;
			int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
			GL11.glEnable(2896);
			GL11.glColor4f(1F, 1F, 1F, 1F);
			for (int i = 0; i <= b0; ++i)
			{
				float f11 = MathHelper.sin((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F;
				float f12 = MathHelper.cos((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F;
				float f13 = (float)(i % b0) * 1.0F / (float)b0;
				tessellator.setColorOpaque_I(0);
				tessellator.addVertexWithUV((double)(f11 * 0.2F), (double)(f12 * 0.2F), 0.0D, (double)f13, (double)f10);
				tessellator.setColorOpaque_I(16777215);
				tessellator.addVertexWithUV((double)f11, (double)f12, (double)f8, (double)f13, (double)f9);
			}

			tessellator.draw();
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glShadeModel(GL11.GL_FLAT);
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
	}

	protected ResourceLocation getEntityTexture(EntityEnderColossusCrystal p_110775_1_)
	{
		return enderCrystalTextures;
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return getEntityTexture((EntityEnderColossusCrystal)p_110775_1_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityEnderColossusCrystal)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityEnderColossusCrystal)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		doRender((EntityEnderColossusCrystal)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}


