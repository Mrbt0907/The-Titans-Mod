package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityGargoyle;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelBeam;
import net.minecraft.theTitans.models.ModelGargoyle;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderGargoyle
extends RenderLiving
{
	private static final ResourceLocation stoneGargoyleTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "gargoyle1.png"));
	private static final ResourceLocation sandstoneGargoyleTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "gargoyle2.png"));
	private static final ResourceLocation obsidianGargoyleTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "gargoyle3.png"));
	private static final ResourceLocation goldenGargoyleTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "gargoyle4.png"));
	private static final ResourceLocation ironGargoyleTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "gargoyle5.png"));
	private static final ResourceLocation endstoneGargoyleTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "gargoyle6.png"));
	private static final ResourceLocation nethraticGargoyleTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "gargoyle7.png"));
	private static final ResourceLocation gargoyleBeamTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "gargoyle_beam.png"));
	public RenderGargoyle()
	{
		super(new ModelGargoyle(), 0.8F);
	}

	protected ResourceLocation getEntityTexture(EntityGargoyle entity)
	{
		switch (entity.getGargoyleType())
		{
			case 0:
			default:
			return stoneGargoyleTextures;
			case 1:
			return sandstoneGargoyleTextures;
			case 2:
			return obsidianGargoyleTextures;
			case 3:
			return goldenGargoyleTextures;
			case 4:
			return ironGargoyleTextures;
			case 5:
			return endstoneGargoyleTextures;
			case 6:
			return nethraticGargoyleTextures;
		}
	}

	protected void func_180588_a(EntityGargoyle p_180588_1_, float p_180588_2_, float p_180588_3_, float p_180588_4_)
	{
		super.rotateCorpse(p_180588_1_, p_180588_2_, p_180588_3_, p_180588_4_);
		if (!p_180588_1_.onGround || p_180588_1_.getAggressive())
		{
			GL11.glRotatef(75F, -1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0F, 1.0F);
		}

		if (p_180588_1_.getGargoyleType() == 1)
		{
			GL11.glScalef(0.75F, 1F, 0.75F);
		}

		if (p_180588_1_.getGargoyleType() == 2)
		{
			GL11.glScalef(1.25F, 1F, 1.25F);
		}

		if (p_180588_1_.getGargoyleType() == 3)
		{
			GL11.glScalef(1.1F, 1F, 1.1F);
		}

		if (p_180588_1_.getGargoyleType() == 4)
		{
			GL11.glScalef(1.2F, 1F, 1.2F);
		}

		if (p_180588_1_.getGargoyleType() == 5)
		{
			GL11.glScalef(1.1F, 1.1F, 1.1F);
		}

		if (p_180588_1_.getGargoyleType() == 6)
		{
			GL11.glScalef(1.05F, 0.95F, 1.05F);
		}
	}

	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		func_180588_a((EntityGargoyle)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityGargoyle)entity);
	}

	protected int shouldRenderPass(EntityGargoyle p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		int i1 = MathHelper.floor_double(p_77032_1_.posX);
		int j = MathHelper.floor_double(p_77032_1_.boundingBox.minY - 0.5D);
		int k = MathHelper.floor_double(p_77032_1_.posZ);
		boolean flag = p_77032_1_.worldObj.getBlock(i1, j, k) == p_77032_1_.getFavoriteBlockToPerch();
		if (p_77032_1_.getGargoyleType() == 3)
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
				((ModelGargoyle)this.mainModel).Torso.postRender(0.0625F / 3);
				((ModelGargoyle)this.mainModel).Body.postRender(0.0625F / 3);
				((ModelGargoyle)this.mainModel).Head.postRender(0.0625F / 3);
				float f1 = p_77032_1_.ticksExisted + p_77032_3_;
				bindTexture(gargoyleBeamTextures);
				GL11.glTranslatef(0.0F, -0.5F, 0.0F);
				if (!p_77032_1_.onGround)
				GL11.glTranslatef(0.0F, 0.25F, 0.0F);
				if (flag)
				GL11.glTranslatef(0.0F, 0.3F, -0.65F);
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glScalef(0.75F, 10.0F, 0.4F);
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				float f3 = f1 * 0.01F;
				GL11.glTranslatef(0.0F, f3, 0.0F);
				setRenderPassModel(modelmatter);
				GL11.glMatrixMode(5888);
				GL11.glEnable(3042);
				float f4 = 0.5F;
				GL11.glColor4f(f4, f4, f4, f4);
				modelmatter.render(p_77032_1_, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
				GL11.glDisable(2896);
				GL11.glBlendFunc(1, 1);
				GL11.glEnable(3042);
				GL11.glDisable(3008);
				GL11.glBlendFunc(1, 1);
				GL11.glDisable(2896);
				char c0 = 61680;
				int j1 = c0 % 65536;
				int k1 = c0 / 65536;
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j1 / 1.0F, k1 / 1.0F);
				GL11.glEnable(2896);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 2F);
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

		return -1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityGargoyle)p_77032_1_, p_77032_2_, p_77032_3_);
	}
}


