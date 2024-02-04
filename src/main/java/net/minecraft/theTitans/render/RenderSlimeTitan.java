package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntitySlimeTitan;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderSlimeTitan
extends RenderLiving
{
	private static final ResourceLocation slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");
	private ModelBase scaleAmount;
	public RenderSlimeTitan(ModelBase p_i1267_1_, ModelBase p_i1267_2_, float p_i1267_3_)
	{
		super(p_i1267_1_, p_i1267_3_);
		this.scaleAmount = p_i1267_2_;
	}

	protected int shouldRenderPass(EntitySlimeTitan p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		if (p_77032_1_.isInvisible())
		{
			return 0;
		}

		else if (p_77032_2_ == 0)
		{
			this.setRenderPassModel(this.scaleAmount);
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			return 1;
		}

		else
		{
			if (p_77032_2_ == 1)
			{
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			return -1;
		}
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntitySlimeTitan)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	public void doRender(EntitySlimeTitan p_177124_1_, double p_177124_2_, double p_177124_4_, double p_177124_6_, float p_177124_8_, float p_177124_9_)
	{
		this.shadowSize = (0.25F * p_177124_1_.getSlimeSize());
		super.doRender(p_177124_1_, p_177124_2_, p_177124_4_, p_177124_6_, p_177124_8_, p_177124_9_);
	}

	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		GL11.glRotatef(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
		if (p_77043_1_.deathTime > 0)
		{
			float f3 = ((float)p_77043_1_.deathTime + p_77043_4_ - 1.0F) / 20.0F * 1.6F;
			f3 = MathHelper.sqrt_float(f3);
			if (f3 > 1.0F)
			{
				f3 = 1.0F;
			}

			GL11.glScalef(1.0F + (f3 * 2.0F), 1.0F - (f3 * 0.99F), 1.0F + (f3 * 2.0F));
		}
	}

	protected void preRenderCallback(EntitySlimeTitan p_77041_1_, float p_77041_2_)
	{
		float f1 = p_77041_1_.getSlimeSize();
		float f2 = (p_77041_1_.prevSquishFactor + (p_77041_1_.squishFactor - p_77041_1_.prevSquishFactor) * p_77041_2_) / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		GL11.glScalef(f3 * f1, 1.0F / f3 * f1, f3 * f1);
		float fl = 16.0F;
		int i = p_77041_1_.getInvulTime();
		if (i > 0)
		{
			fl -= (i - p_77041_2_) / 10F;
		}

		GL11.glScalef(fl, fl, fl);
		float f4 = 0.025F + ((p_77041_1_.getTitanHealth() - p_77041_1_.getTitanMaxHealth()) / p_77041_1_.getTitanMaxHealth() * 0.5F);
		float f5 = 1F + (MathHelper.cos((p_77041_1_.ticksExisted + p_77041_1_.getEntityId() + p_77041_2_) * f4) * f4);
		float f6 = 1F + (MathHelper.sin((p_77041_1_.ticksExisted + p_77041_1_.getEntityId() + p_77041_2_) * f4 + 0.785F) * f4);
		if (p_77041_1_.deathTime <= 0)
		GL11.glScalef(f5, f6, f5);
		GL11.glTranslatef(0F, 0.0075F, 0F);
	}

	protected ResourceLocation getEntityTexture(EntitySlimeTitan entity)
	{
		return slimeTextures;
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntitySlimeTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		preRenderCallback((EntitySlimeTitan)p_77041_1_, p_77041_2_);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntitySlimeTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntitySlimeTitan)entity);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntitySlimeTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


