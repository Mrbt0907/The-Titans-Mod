package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityGargoyleTitan;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelBeam;
import net.minecraft.theTitans.models.ModelGargoyleTitan;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderGargoyleTitan
extends RenderLiving
{
	private static final ResourceLocation ironGolemTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/golem_titans/gargoyle_titan.png"));
	public RenderGargoyleTitan()
	{
		super(new ModelGargoyleTitan(), 1.0F);
	}

	protected ResourceLocation getEntityTexture(EntityGargoyleTitan entity)
	{
		return ironGolemTextures;
	}

	protected void func_180588_a(EntityGargoyleTitan p_180588_1_, float p_180588_2_, float p_180588_3_, float p_180588_4_)
	{
		super.rotateCorpse(p_180588_1_, p_180588_2_, p_180588_3_, p_180588_4_);
		if (p_180588_1_.limbSwingAmount >= 0.01D)
		{
			float f3 = 13.0F;
			float f4 = p_180588_1_.limbSwing - p_180588_1_.limbSwingAmount * (1.0F - p_180588_4_) + 6.0F;
			float f5 = (Math.abs((f4/2) % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
			GL11.glRotatef(3.5F * f5, 0.0F, 0.0F, 1.0F);
		}
	}

	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		func_180588_a((EntityGargoyleTitan)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityGargoyleTitan)entity);
	}

	protected int shouldRenderPass(EntityGargoyleTitan p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		if (p_77032_1_.getAnimID() == 5 && p_77032_1_.getAnimTick() > 24 && p_77032_1_.getAnimTick() < 36)
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
				ModelBase modelmatter = new ModelBeam();
				((ModelGargoyleTitan)this.mainModel).Torso.postRender(0.0625F / 3);
				((ModelGargoyleTitan)this.mainModel).Body.postRender(0.0625F / 3);
				((ModelGargoyleTitan)this.mainModel).Head.postRender(0.0625F / 3);
				float f1 = p_77032_1_.ticksExisted + p_77032_3_;
				bindTexture(new ResourceLocation("textures/blocks/water_still.png"));
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(0.5F, 10.0F, 0.5F);
				GL11.glTranslatef(0.0F, -0.035F, 0.35F);
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				float f3 = f1 * 0.01F;
				GL11.glTranslatef(0.0F, f3, 0.0F);
				setRenderPassModel(modelmatter);
				GL11.glMatrixMode(5888);
				GL11.glEnable(3042);
				float f4 = 0.9F;
				GL11.glColor4f(f4, f4, f4, f4);
				modelmatter.render(p_77032_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
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
				return -1;
			}
		}

		return -1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityGargoyleTitan)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected void func_180592_a(EntityGargoyleTitan p_180592_1_, float p_180592_2_)
	{
		float f1 = 32.0F;
		int i = p_180592_1_.getInvulTime();
		if (i > 0)
		{
			f1 -= (i - p_180592_2_) / 440.0F * 7.75F;
		}

		GL11.glScalef(f1, f1, f1);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		func_180592_a((EntityGargoyleTitan)p_77041_1_, p_77041_2_);
	}

	public void func_180579_a(EntityGargoyleTitan p_180579_1_, double p_180579_2_, double p_180579_4_, double p_180579_6_, float p_180579_8_, float p_180579_9_)
	{
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityGargoyleTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityGargoyleTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityGargoyleTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


