package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityGhastTitan;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelGhastTitan;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderGhastTitan
extends RenderLiving
{
	private static final ResourceLocation ghastTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ghast_titan/ghast_titan.png"));
	private static final ResourceLocation ghastShootingTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ghast_titan/ghast_titan_shooting.png"));
	private static final ResourceLocation ghast2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ghast_titan/lightning_ghast_titan.png"));
	private static final ResourceLocation ghast2ShootingTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ghast_titan/lightning_ghast_titan_shooting.png"));
	private static final ResourceLocation ghast3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ghast_titan/obsidian_ghast_titan.png"));
	private static final ResourceLocation ghast3ShootingTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ghast_titan/obsidian_ghast_titan_shooting.png"));
	public RenderGhastTitan()
	{
		super(new ModelGhastTitan(), 4.0F);
	}

	protected ResourceLocation func_180576_a(EntityGhastTitan entity)
	{
		switch (entity.getTitanVariant())
		{
			case 1:
			return entity.func_110182_bF() ? ghast2ShootingTextures : ghast2Textures;
			case 2:
			return entity.func_110182_bF() ? ghast3ShootingTextures : ghast3Textures;
			default:
			return entity.func_110182_bF() ? ghastShootingTextures : ghastTextures;
		}
	}

	protected void preRenderCallback(EntityGhastTitan p_77041_1_, float p_77041_2_)
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
		float f11 = p_77041_1_.getTitanSizeMultiplier();
		GL11.glScalef(f11, f11, f11);
		if (p_77041_1_.hurtTime > 0)
		{
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		preRenderCallback((EntityGhastTitan)p_77041_1_, p_77041_2_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_180576_a((EntityGhastTitan)entity);
	}

	public void func_180579_a(EntityGhastTitan p_180579_1_, double p_180579_2_, double p_180579_4_, double p_180579_6_, float p_180579_8_, float p_180579_9_)
	{
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityGhastTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityGhastTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityGhastTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


