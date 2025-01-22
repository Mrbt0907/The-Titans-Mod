package net.mrbt0907.thetitans.client.entity.renders;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mrbt0907.thetitans.client.entity.models.ModelSkeletonTitan;
import net.mrbt0907.thetitans.entity.boss.EntitySkeletonTitanProto;

@SideOnly(Side.CLIENT)
public class RenderSkeletonTitanProto extends RenderPreTitan<EntitySkeletonTitanProto>
{
    public RenderSkeletonTitanProto(RenderManager p_i47204_1_)
    {
        super(p_i47204_1_, new ModelSkeletonTitan(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
    }

    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }

    protected void applyRotations(EntitySkeletonTitanProto entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);

        if (!entityLiving.isEntityAlive())
        {
            float f = ((float)entityLiving.deathTime + partialTicks - 1.0F) / 40F;
            f = MathHelper.sqrt(f);

            if (f > 1.0F)
                f = 1.0F;
            float f1 = ((float)entityLiving.deathTicks + partialTicks - 1.0F) / 40F;
            f1 = MathHelper.sqrt(f1);

            if (f1 > 1.0F)
                f1 = 1.0F;

            GlStateManager.translate(0, 0, f1 * entityLiving.getSizeMultiplier());
            GlStateManager.rotate(f * this.getDeathMaxRotation(entityLiving), -1.0F, 0.0F, 0.0F);
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

        if ((double)entityLiving.limbSwingAmount >= 0.01D)
        {
            float f = 13.0F;
            float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % f - 6.5F) - 3.25F) / 3.25F;
            GlStateManager.rotate((0.15F * entityLiving.getSizeMultiplier()) * f2, 0.0F, 0.0F, 1.0F);
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySkeletonTitanProto entity)
    {
        return new ResourceLocation("textures/entity/skeleton/skeleton.png");
    }

	public boolean shouldSpecialRender(EntitySkeletonTitanProto titan) 
	{
		return false;
	}
}