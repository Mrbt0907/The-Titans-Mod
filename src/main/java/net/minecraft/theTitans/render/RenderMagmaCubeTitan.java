package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityMagmaCubeTitan;
import net.minecraft.theTitans.models.ModelMagmaCubeTitan;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderMagmaCubeTitan
extends RenderLiving
{
	private static final ResourceLocation magmaCubeTextures = new ResourceLocation("textures/entity/slime/magmacube.png");
	public RenderMagmaCubeTitan()
	{
		super(new ModelMagmaCubeTitan(), 0.25F);
	}

	protected ResourceLocation getEntityTexture(EntityMagmaCubeTitan entity)
	{
		return magmaCubeTextures;
	}

	protected void preRenderCallback(EntityMagmaCubeTitan p_77041_1_, float p_77041_2_)
	{
		int i = p_77041_1_.getSlimeSize();
		float f1 = (p_77041_1_.prevSquishFactor + (p_77041_1_.squishFactor - p_77041_1_.prevSquishFactor) * p_77041_2_) / (i * 0.5F + 1.0F);
		float f2 = 1.0F / (f1 + 1.0F);
		float f3 = i;
		GL11.glScalef(f2 * f3, 1.0F / f2 * f3, f2 * f3);
		float fl = 16.0F;
		int i1 = p_77041_1_.getInvulTime();
		if (i1 > 0)
		{
			fl -= (i1 - p_77041_2_) / 10F;
		}

		GL11.glScalef(fl, fl, fl);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		preRenderCallback((EntityMagmaCubeTitan)p_77041_1_, p_77041_2_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityMagmaCubeTitan)entity);
	}

	public void doRender(EntityMagmaCubeTitan p_177124_1_, double p_177124_2_, double p_177124_4_, double p_177124_6_, float p_177124_8_, float p_177124_9_)
	{
		this.shadowSize = (0.25F * p_177124_1_.getSlimeSize());
		super.doRender(p_177124_1_, p_177124_2_, p_177124_4_, p_177124_6_, p_177124_8_, p_177124_9_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityMagmaCubeTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityMagmaCubeTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityMagmaCubeTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


