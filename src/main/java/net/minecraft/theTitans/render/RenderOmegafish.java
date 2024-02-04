package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelOmegafish;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderOmegafish
extends RenderLiving
{
	private static final ResourceLocation silverfishTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/omegafish.png"));
	private static final ResourceLocation silverfish2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/omegaworm.png"));
	private static final ResourceLocation silverfish3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/frostfish.png"));
	private static final ResourceLocation silverfish4Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/seafish.png"));
	private static final ResourceLocation silverfish5Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/poisonfish.png"));
	private static final ResourceLocation silverfish6Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/magmafish.png"));
	private static final ResourceLocation silverfish6OverlayTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/magmafish_eyes.png"));
	private static final ResourceLocation silverfish7Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/voidfish.png"));
	private static final ResourceLocation silverfish7OverlayTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/voidfish_eyes.png"));
	public RenderOmegafish()
	{
		super(new ModelOmegafish(), 0.3F);
	}

	protected float func_180584_a(EntitySilverfishTitan p_180584_1_)
	{
		return 180.0F;
	}

	protected ResourceLocation getEntityTexture(EntitySilverfishTitan entity)
	{
		switch (entity.getTitanVariant())
		{
			case 1:
			return silverfish2Textures;
			case 2:
			return silverfish3Textures;
			case 3:
			return silverfish4Textures;
			case 4:
			return silverfish5Textures;
			case 5:
			return silverfish6Textures;
			case 6:
			return silverfish7Textures;
			default:
			return silverfishTextures;
		}
	}

	protected int shouldRenderPass(EntitySilverfishTitan p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		if (p_77032_1_.isArmored() && p_77032_1_.isEntityAlive())
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
				bindTexture(TheTitans.genericTitanWhiteTexture32x64);
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				float f2 = MathHelper.cos(f1 * 0.05F) * 3.0F;
				float f3 = f1 * 0.01F;
				GL11.glTranslatef(f2, f3, 0.0F);
				setRenderPassModel(this.mainModel);
				GL11.glMatrixMode(5888);
				GL11.glEnable(3042);
				GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
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

		if (p_77032_1_.getTitanVariant() == 5 || p_77032_1_.getTitanVariant() == 6)
		{
			if (p_77032_2_ != 0)
			{
				return -1;
			}

			switch (p_77032_1_.getTitanVariant())
			{
				case 6:
				bindTexture(silverfish7OverlayTextures);
				break;
				default:
				bindTexture(silverfish6OverlayTextures);
			}

			GL11.glEnable(3042);
			GL11.glDisable(3008);
			GL11.glBlendFunc(1, 1);
			if (p_77032_1_.isInvisible())
			{
				GL11.glDepthMask(false);
			}

			else
			{
				GL11.glDepthMask(true);
			}

			int c0 = 15728880;
			int j = c0 % 65536;
			int k = c0 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			setRenderPassModel(this.mainModel);
			if (p_77032_1_.getAnimID() == 10 && p_77032_1_.deathTicks > 200)
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
			return 1;
		}

		return -1;
	}

	protected int inheritRenderPass(EntitySilverfishTitan p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return -1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntitySilverfishTitan)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected int inheritRenderPass(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return inheritRenderPass((EntitySilverfishTitan)p_77035_1_, p_77035_2_, p_77035_3_);
	}

	protected float getDeathMaxRotation(EntityLivingBase p_77037_1_)
	{
		return func_180584_a((EntitySilverfishTitan)p_77037_1_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntitySilverfishTitan)entity);
	}

	protected void func_180592_a(EntitySilverfishTitan p_180592_1_, float p_180592_2_)
	{
		float f1 = p_180592_1_.getTitanSizeMultiplier();
		if (p_180592_1_.isBurrowing)
		GL11.glTranslatef(0.0F, 8F, 0.0F);
		GL11.glScalef(f1, f1, f1);
		GL11.glTranslatef(0.0F, 0.01F, 0.0F);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		func_180592_a((EntitySilverfishTitan)p_77041_1_, p_77041_2_);
	}

	public void func_180579_a(EntitySilverfishTitan p_180579_1_, double p_180579_2_, double p_180579_4_, double p_180579_6_, float p_180579_8_, float p_180579_9_)
	{
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntitySilverfishTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntitySilverfishTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntitySilverfishTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


