package net.minecraft.titans.client.entity.renders;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.titans.entity.boss.EntityZombieTitanProto;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderZombieTitanProto extends RenderBiped<EntityZombieTitanProto>
{
    public RenderZombieTitanProto(RenderManager p_i47204_1_)
    {
        super(p_i47204_1_, new ModelZombie(), 0.5F);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityZombieTitanProto entitylivingbaseIn, float partialTickTime)
    {
        float f = 16F;
        GlStateManager.scale(f, f, f);
        this.shadowSize = 0.5F * f;
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    protected void applyRotations(EntityZombieTitanProto entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);

        if ((double)entityLiving.limbSwingAmount >= 0.01D)
        {
            float f = 13.0F;
            float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % f - 6.5F) - 3.25F) / 3.25F;
            GlStateManager.rotate(6.5F * f2, 0.0F, 0.0F, 1.0F);
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityZombieTitanProto entity)
    {
        return new ResourceLocation("textures/entity/zombie/zombie.png");
    }
}