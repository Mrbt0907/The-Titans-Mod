package net.minecraft.titans.client.entity.renders;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.titans.entity.boss.EntityHuskTitanProto;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHuskTitanProto extends RenderPreTitan<EntityHuskTitanProto>
{
    public RenderHuskTitanProto(RenderManager p_i47204_1_)
    {
        super(p_i47204_1_, new ModelZombie(), 0.5F);
        this.addLayer(new LayerElytra(this));
        this.addLayer(new LayerHeldItem(this));
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
    }

    protected void applyRotations(EntityHuskTitanProto entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
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
    protected ResourceLocation getEntityTexture(EntityHuskTitanProto entity)
    {
        return new ResourceLocation("textures/entity/zombie/husk.png");
    }

	public boolean shouldSpecialRender(EntityHuskTitanProto titan) 
	{
		return false;
	}
}