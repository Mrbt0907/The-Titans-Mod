package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityIronGolemTitan;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelIronGolemTitan;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderUltimaIronGolemTitan
extends RenderLiving
{
	private static final ResourceLocation ironGolemTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/golem_titans/iron_golem_titan.png"));
	private ModelIronGolemTitan golemModel;public RenderUltimaIronGolemTitan()
	{
		super(new ModelIronGolemTitan(), 1.0F);
		this.golemModel = ((ModelIronGolemTitan)this.mainModel);
		setRenderPassModel(this.golemModel);
	}

	protected ResourceLocation getEntityTexture(EntityIronGolemTitan entity)
	{
		return ironGolemTextures;
	}

	protected void func_180588_a(EntityIronGolemTitan p_180588_1_, float p_180588_2_, float p_180588_3_, float p_180588_4_)
	{
		super.rotateCorpse(p_180588_1_, p_180588_2_, p_180588_3_, p_180588_4_);
	}

	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		func_180588_a((EntityIronGolemTitan)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityIronGolemTitan)entity);
	}

	protected void func_180592_a(EntityIronGolemTitan p_180592_1_, float p_180592_2_)
	{
		float f1 = 32F;
		int i = p_180592_1_.getInvulTime();
		if (i > 0)
		{
			f1 -= (i - p_180592_2_) / 440.0F * 7.75F;
		}

		GL11.glScalef(f1, f1, f1);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		func_180592_a((EntityIronGolemTitan)p_77041_1_, p_77041_2_);
	}

	public void func_180579_a(EntityIronGolemTitan p_180579_1_, double p_180579_2_, double p_180579_4_, double p_180579_6_, float p_180579_8_, float p_180579_9_)
	{
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityIronGolemTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityIronGolemTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityIronGolemTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


