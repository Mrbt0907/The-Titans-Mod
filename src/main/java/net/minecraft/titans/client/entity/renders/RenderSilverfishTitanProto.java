package net.minecraft.titans.client.entity.renders;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.titans.client.entity.models.ModelSilverfishTitan;
import net.minecraft.titans.entity.boss.EntitySilverfishTitanProto;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSilverfishTitanProto extends RenderPreTitan<EntitySilverfishTitanProto>
{
    public RenderSilverfishTitanProto(RenderManager p_i47204_1_)
    {
        super(p_i47204_1_, new ModelSilverfishTitan(), 0.3F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySilverfishTitanProto entity)
    {
        return new ResourceLocation("textures/entity/silverfish.png");
    }

    protected void applyRotations(EntitySilverfishTitanProto entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
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

	public boolean shouldSpecialRender(EntitySilverfishTitanProto titan) 
	{
		return false;
	}

    protected float getDeathMaxRotation(EntitySilverfishTitanProto entityLivingBaseIn)
    {
        return 180.0F;
    }
}