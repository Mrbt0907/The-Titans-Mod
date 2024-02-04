package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityWitherzilla;
import net.minecraft.init.Blocks;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelWitherzilla;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderWitherzilla
extends RenderLiving
{
	private static final ResourceLocation theTruthAboutTheUniverseLaysIn1sAnd0s = new ResourceLocation(TheTitans.getTextures("textures/entities", "binary.png"));
	private static final ResourceLocation witherzillaSheild = new ResourceLocation(TheTitans.getTextures("textures/entities", "wither_aura.png"));
	private static final ResourceLocation witherzillaOmegaTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/god_titans/witherzilla_omega.png"));
	private static final ResourceLocation witherzillaTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/god_titans/witherzilla.png"));
	private int field_82419_a;
	public RenderWitherzilla()
	{
		super(new ModelWitherzilla(), 1.0F);
		this.field_82419_a = ((ModelWitherzilla)this.mainModel).func_82903_a();
	}

	protected void renderEquippedItems(EntityWitherzilla p_77029_1_, float p_77029_2_)
	{
		super.renderEquippedItems(p_77029_1_, p_77029_2_);
		Random random11 = new Random(432L);
		GL11.glEnable(32826);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, -2.0F, 0.0F);
		GL11.glScalef(0.05F, 0.05F, 0.05F);
		int i = p_77029_1_.getBrightnessForRender(p_77029_2_);
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(TextureMap.locationBlocksTexture);
		this.field_147909_c.renderBlockAsItem(Blocks.glowstone, 0, 1.0F);
		GL11.glRotatef(p_77029_1_.ticksExisted, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		GL11.glDisable(32826);
		Tessellator tessellator = Tessellator.instance;
		if (p_77029_1_.affectTicks > 0 && p_77029_1_.getInvulTime() < 1)
		{
			RenderHelper.disableStandardItemLighting();
			float f111 = (p_77029_1_.affectTicks + p_77029_2_) / 1000.0F;
			float f211 = 0.0F;
			if (f111 > 0.8F)
			{
				f211 = (f111 - 0.8F) / 0.2F;
			}

			GL11.glDisable(3553);
			GL11.glShadeModel(7425);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 1);
			GL11.glDisable(3008);
			GL11.glEnable(2884);
			GL11.glDepthMask(false);
			GL11.glTranslatef(0.0F, -2.0F, 0.0F);
			GL11.glPushMatrix();
			for (int i1 = 0; i1 < (f111 + f111 * f111) / 2.0F * 100.0F; i1++)
			{
				GL11.glRotatef(random11.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F + f111 * 90.0F, 0.0F, 0.0F, 1.0F);
				tessellator.startDrawing(6);
				float f3 = random11.nextFloat() * 10.0F + 5.0F + f211 * 20.0F;
				float f4 = random11.nextFloat() * 2.0F + 1.0F + f211 * 2.0F;
				tessellator.setColorRGBA_I(14809319, (int)(255.0F * (1.0F - f211)));
				tessellator.addVertex(0.0D, 0.0D, 0.0D);
				tessellator.setColorRGBA_I(14809319, 0);
				tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
				tessellator.addVertex(0.866D * f4, f3, -0.5F * f4);
				tessellator.addVertex(0.0D, f3, 1.0F * f4);
				tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
				tessellator.draw();
			}

			GL11.glPopMatrix();
			GL11.glDepthMask(true);
			GL11.glDisable(2884);
			GL11.glDisable(3042);
			GL11.glShadeModel(7424);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(3553);
			GL11.glEnable(3008);
			RenderHelper.enableStandardItemLighting();
		}

		else if (p_77029_1_.deathTicks > 0)
		{
			RenderHelper.disableStandardItemLighting();
			float f111 = (p_77029_1_.deathTicks + p_77029_2_) / 300.0F;
			float f211 = 0.0F;
			if (f111 > 0.8F)
			{
				f211 = (f111 - 0.8F) / 0.2F;
			}

			GL11.glDisable(3553);
			GL11.glShadeModel(7425);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 1);
			GL11.glDisable(3008);
			GL11.glEnable(2884);
			GL11.glDepthMask(false);
			GL11.glPushMatrix();
			for (int i1 = 0; i1 < (f111 + f111 * f111) / 2.0F * 800.0F; i1++)
			{
				GL11.glRotatef(random11.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random11.nextFloat() * 360.0F + f111 * 90.0F, 0.0F, 0.0F, 1.0F);
				tessellator.startDrawing(6);
				float f3 = random11.nextFloat() * 2.0F + 1.0F + f211 * 20.0F;
				float f4 = random11.nextFloat() * 4.0F + 2.0F + f211 * 4.0F;
				tessellator.setColorRGBA_I(14809319, (int)(255.0F * (1.0F - f211)));
				tessellator.addVertex(0.0D, 0.0D, 0.0D);
				tessellator.setColorRGBA_I(14809319, 0);
				tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
				tessellator.addVertex(0.866D * f4, f3, -0.5F * f4);
				tessellator.addVertex(0.0D, f3, 1.0F * f4);
				tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
				tessellator.draw();
			}

			GL11.glPopMatrix();
			GL11.glDepthMask(true);
			GL11.glDisable(2884);
			GL11.glDisable(3042);
			GL11.glShadeModel(7424);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(3553);
			GL11.glEnable(3008);
			RenderHelper.enableStandardItemLighting();
		}
	}

	protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_)
	{
		renderEquippedItems((EntityWitherzilla)p_77029_1_, p_77029_2_);
	}

	protected int shouldRenderPass(EntityWitherzilla p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		if ((p_77032_1_.isArmored()) || (p_77032_1_.isInOmegaForm()))
		{
			if (p_77032_1_.isInvisible())
			{
				GL11.glDepthMask(false);
			}

			else
			{
				GL11.glDepthMask(true);
			}

			if (p_77032_2_ == 1)
			{
				float f1 = p_77032_1_.ticksExisted + p_77032_3_;
				int i = p_77032_1_.getInvulTime();
				bindTexture(((i > 0) && ((i > 300) || (i / 10 % 2 != 1))) ? TheTitans.genericTitanWhiteTexture64x64 : witherzillaSheild);
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				float f2 = f1 * 0.015F;
				float f3 = f1 * 0.01F;
				GL11.glTranslatef(f2, f3, 0.0F);
				setRenderPassModel(this.mainModel);
				GL11.glMatrixMode(5888);
				GL11.glEnable(3042);
				float f4 = 0.5F;
				GL11.glColor4f(f4, f4, f4, 1.0F);
				GL11.glDisable(2896);
				GL11.glBlendFunc(1, 1);
				return 1;
			}

			if (p_77032_2_ == 2)
			{
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(5888);
				GL11.glEnable(2896);
				GL11.glDisable(3042);
			}
		}

		return -1;
	}

	protected int inheritRenderPass(EntityWitherzilla p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return -1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityWitherzilla)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected int inheritRenderPass(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return inheritRenderPass((EntityWitherzilla)p_77035_1_, p_77035_2_, p_77035_3_);
	}

	protected void renderModel(EntityWitherzilla p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_)
	{
		this.bindEntityTexture(p_77036_1_);
		if (!p_77036_1_.isInvisible())
		{
			this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
		}

		else if (!p_77036_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))
		{
			if (p_77036_1_.deathTicks > 0)
			{
				GL11.glDepthFunc(514);
				GL11.glDisable(3553);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				bindTexture(TheTitans.genericTitanWhiteTexture64x64);
				this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
				GL11.glEnable(3553);
				GL11.glDisable(3042);
				GL11.glDepthFunc(515);
			}

			if (p_77036_1_.hurtTime > 0)
			{
				GL11.glDepthFunc(514);
				GL11.glDisable(3553);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
				this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
				GL11.glEnable(3553);
				GL11.glDisable(3042);
				GL11.glDepthFunc(515);
			}
		}

		else
		{
			this.mainModel.setRotationAngles(p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_, p_77036_1_);
		}
	}

	protected void renderModel(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_)
	{
		renderModel((EntityWitherzilla)p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
	}

	public void func_180591_a(EntityWitherzilla p_180591_1_, double p_180591_2_, double p_180591_4_, double p_180591_6_, float p_180591_8_, float p_180591_9_)
	{
		int i = ((ModelWitherzilla)this.mainModel).func_82903_a();
		if (i != this.field_82419_a)
		{
			this.field_82419_a = i;
			this.mainModel = new ModelWitherzilla();
		}

		super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
	}

	protected ResourceLocation getEntityTexture(EntityWitherzilla entity)
	{
		int i = entity.getInvulTime();
		return (entity.isArmored() ? theTruthAboutTheUniverseLaysIn1sAnd0s : ((i > 0) && ((i > 300) || (i / 10 % 2 != 1))) ? TheTitans.genericTitanWhiteTexture64x64 : ((entity.isEntityInvulnerable() || entity.isInOmegaForm()) ? witherzillaOmegaTextures : witherzillaTextures));
	}

	protected void func_180592_a(EntityWitherzilla p_180592_1_, float p_180592_2_)
	{
		float f1 = p_180592_1_.getTitanSizeMultiplier();
		GL11.glScalef(f1, f1, f1);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180591_a((EntityWitherzilla)entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		func_180592_a((EntityWitherzilla)p_77041_1_, p_77041_2_);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180591_a((EntityWitherzilla)entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityWitherzilla)entity);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180591_a((EntityWitherzilla)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


