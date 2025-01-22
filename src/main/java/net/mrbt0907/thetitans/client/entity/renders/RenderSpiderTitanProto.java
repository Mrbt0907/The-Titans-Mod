package net.mrbt0907.thetitans.client.entity.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mrbt0907.thetitans.entity.boss.EntitySpiderTitanProto;

@SideOnly(Side.CLIENT)
public class RenderSpiderTitanProto extends RenderPreTitan<EntitySpiderTitanProto>
{
    public RenderSpiderTitanProto(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSpider(), 1.0F);
        this.addLayer(new LayerSpiderEyes(this));
    }

    protected float getDeathMaxRotation(EntitySpiderTitanProto entityLivingBaseIn)
    {
        return 180.0F;
    }

    protected void applyRotations(EntitySpiderTitanProto entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);

        if (!entityLiving.isEntityAlive())
        {
            float f = ((float)entityLiving.deathTime + partialTicks - 1.0F) / 40F;
            f = MathHelper.sqrt(f);

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            GlStateManager.rotate(f * this.getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
        }
        else
        {
            String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());

            if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s)))
            {
                GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            }
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySpiderTitanProto entity)
    {
        return new ResourceLocation("textures/entity/spider/spider.png");
    }
    
    public class LayerSpiderEyes implements LayerRenderer<EntitySpiderTitanProto>
    {
        private final RenderSpiderTitanProto spiderRenderer;

        public LayerSpiderEyes(RenderSpiderTitanProto spiderRendererIn)
        {
            this.spiderRenderer = spiderRendererIn;
        }

        public void doRenderLayer(EntitySpiderTitanProto entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
        {
            this.spiderRenderer.bindTexture(new ResourceLocation("textures/entity/spider_eyes.png"));
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

            if (entitylivingbaseIn.isInvisible())
            {
                GlStateManager.depthMask(false);
            }
            else
            {
                GlStateManager.depthMask(true);
            }

            int i = 61680;
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.spiderRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            i = entitylivingbaseIn.getBrightnessForRender();
            j = i % 65536;
            k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
            this.spiderRenderer.setLightmap(entitylivingbaseIn);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
        }

        public boolean shouldCombineTextures()
        {
            return false;
        }
    }

	public boolean shouldSpecialRender(EntitySpiderTitanProto titan) 
	{
		return false;
	}
}