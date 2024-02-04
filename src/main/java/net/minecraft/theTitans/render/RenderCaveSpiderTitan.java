package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityCaveSpiderTitan;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelSpiderTitan;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderCaveSpiderTitan
extends RenderLiving
{
	private static final ResourceLocation spiderEyesTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/spider_titan_eyes.png"));
	private static final ResourceLocation caveSpiderTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/cave_spider_titan/cave_spider_titan.png"));
	private static final ResourceLocation caveSpider2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/cave_spider_titan/jesus_spider_titan.png"));
	private static final ResourceLocation caveSpider3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/cave_spider_titan/ore_spider_titan.png"));
	private static final ResourceLocation caveSpider4Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/cave_spider_titan/venomous_spider_titan.png"));
	private static final ResourceLocation caveSpider4EyesTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/cave_spider_titan/venomous_spider_titan_eyes.png"));
	private static final ResourceLocation caveSpider5Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/cave_spider_titan/ice_spider_titan.png"));
	private static final ResourceLocation caveSpider5EyesTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/cave_spider_titan/ice_spider_titan_eyes.png"));
	private static final ResourceLocation spider2EyesTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/void_spider_titan_eyes.png"));
	private static final ResourceLocation spider2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/spider_titan/void_spider_titan.png"));
	private ModelBase overlayingModel = new ModelSpiderTitan(0.1F);public RenderCaveSpiderTitan()
	{
		super(new ModelSpiderTitan(), 0.7F);
		setRenderPassModel(new ModelSpiderTitan());
	}

	protected float getDeathMaxRotation(EntityCaveSpiderTitan p_77037_1_)
	{
		return 180.0F;
	}

	protected ResourceLocation getEntityTexture(EntityCaveSpiderTitan entity)
	{
		switch (entity.getTitanVariant())
		{
			case 1:
			return caveSpider2Textures;
			case 2:
			return caveSpider3Textures;
			case 3:
			return caveSpider4Textures;
			case 4:
			return caveSpider5Textures;
			case 5:
			return spider2Textures;
			default:
			return caveSpiderTextures;
		}
	}

	protected int shouldRenderPass(EntityCaveSpiderTitan p_77032_1_, int p_77032_2_, float p_77032_3_)
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
				bindTexture(TheTitans.genericTitanWhiteTexture64x64);
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				float f2 = MathHelper.cos(f1 * 0.02F) * 5.0F;
				float f3 = f1 * 0.01F;
				GL11.glTranslatef(MathHelper.cos(f1 * 0.2F), f3, f2);
				setRenderPassModel(this.overlayingModel);
				GL11.glMatrixMode(5888);
				GL11.glEnable(3042);
				GL11.glColor4f(0.6F + (MathHelper.cos(f1 * 0.1F) * 0.3F), 0.0F, 0.0F, 1.0F);
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

		if (p_77032_1_.getTitanVariant() != 1)
		{
			if (p_77032_2_ != 0)
			{
				return -1;
			}

			switch (p_77032_1_.getTitanVariant())
			{
				case 3:
				bindTexture(caveSpider4EyesTextures);
				break;
				case 4:
				bindTexture(caveSpider5EyesTextures);
				break;
				case 5:
				bindTexture(spider2EyesTextures);
				break;
				default:
				bindTexture(spiderEyesTextures);
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

			char c0 = 61680;
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

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityCaveSpiderTitan)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected float getDeathMaxRotation(EntityLivingBase p_77037_1_)
	{
		return getDeathMaxRotation((EntityCaveSpiderTitan)p_77037_1_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityCaveSpiderTitan)entity);
	}

	protected void func_180592_a(EntityCaveSpiderTitan p_180592_1_, float p_180592_2_)
	{
		float f1 = p_180592_1_.getTitanSizeMultiplier();
		GL11.glScalef(f1, f1, f1);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		func_180592_a((EntityCaveSpiderTitan)p_77041_1_, p_77041_2_);
	}

	public void func_180579_a(EntityCaveSpiderTitan p_180579_1_, double p_180579_2_, double p_180579_4_, double p_180579_6_, float p_180579_8_, float p_180579_9_)
	{
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityCaveSpiderTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityCaveSpiderTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityCaveSpiderTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


