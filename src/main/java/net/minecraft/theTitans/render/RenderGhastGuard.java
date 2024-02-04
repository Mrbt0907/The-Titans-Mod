package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titanminion.EntityGhastGuard;
import net.minecraft.theTitans.models.ModelGhastGuard;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderGhastGuard
extends RenderLiving
{
	private static final ResourceLocation ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
	private static final ResourceLocation ghastShootingTextures = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
	public RenderGhastGuard()
	{
		super(new ModelGhastGuard(), 3.0F);
	}

	protected ResourceLocation func_180576_a(EntityGhastGuard p_180576_1_)
	{
		return p_180576_1_.func_110182_bF() ? ghastShootingTextures : ghastTextures;
	}

	protected void preRenderCallback(EntityGhastGuard p_77041_1_, float p_77041_2_)
	{
		float f1 = (p_77041_1_.prevAttackCounter + (p_77041_1_.attackCounter - p_77041_1_.prevAttackCounter) * p_77041_2_) / 20.0F;
		if (f1 < 0.0F)
		{
			f1 = 0.0F;
		}

		f1 = 1.0F / (f1 * f1 * f1 * f1 * f1 * 2.0F + 1.0F);
		float f2 = (8.0F + f1) / 2.0F;
		float f3 = (8.0F + 1.0F / f1) / 2.0F;
		GL11.glScalef(f3, f2, f3);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef(0.0F, 0.5F, 0.0F);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		preRenderCallback((EntityGhastGuard)p_77041_1_, p_77041_2_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_180576_a((EntityGhastGuard)entity);
	}
}


