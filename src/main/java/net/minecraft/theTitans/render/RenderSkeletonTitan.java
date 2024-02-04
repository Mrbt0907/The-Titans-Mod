package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntitySkeletonTitan;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelSkeletonTitan;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderSkeletonTitan
extends RenderLiving
{
	private static final ResourceLocation skeletonTitanTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/skeleton_titan.png"));
	private static final ResourceLocation skeletonTitanPull1Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/skeleton_titan_pulling_0.png"));
	private static final ResourceLocation skeletonTitanPull2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/skeleton_titan_pulling_1.png"));
	private static final ResourceLocation skeletonTitanPull3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/skeleton_titan_pulling_2.png"));
	private static final ResourceLocation skeletonTitan2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/sand_skeleton_titan.png"));
	private static final ResourceLocation skeletonTitan2Pull1Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/sand_skeleton_titan_pulling_0.png"));
	private static final ResourceLocation skeletonTitan2Pull2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/sand_skeleton_titan_pulling_1.png"));
	private static final ResourceLocation skeletonTitan2Pull3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/sand_skeleton_titan_pulling_2.png"));
	private static final ResourceLocation skeletonTitan3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/guitar_skeleton_titan.png"));
	private static final ResourceLocation skeletonTitan4Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/sleepy_skeleton_titan.png"));
	private static final ResourceLocation skeletonTitan4Pull1Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/sleepy_skeleton_titan_pulling_0.png"));
	private static final ResourceLocation skeletonTitan4Pull2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/sleepy_skeleton_titan_pulling_1.png"));
	private static final ResourceLocation skeletonTitan4Pull3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/sleepy_skeleton_titan_pulling_2.png"));
	private static final ResourceLocation skeletonTitan5Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/void_skeleton_titan.png"));
	private static final ResourceLocation skeletonTitan5Pull1Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/void_skeleton_titan_pulling_0.png"));
	private static final ResourceLocation skeletonTitan5Pull2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/void_skeleton_titan_pulling_1.png"));
	private static final ResourceLocation skeletonTitan5Pull3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/void_skeleton_titan_pulling_2.png"));
	private static final ResourceLocation witherSkeletonTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/wither_skeleton_titan/wither_skeleton_titan.png"));
	private static final ResourceLocation witherSkeleton2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/wither_skeleton_titan/solar_skeleton_titan.png"));
	private static final ResourceLocation witherSkeleton2OverlayTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/wither_skeleton_titan/solar_skeleton_titan_eyes.png"));
	private static final ResourceLocation witherSkeleton3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/skeleton_titan/wither_skeleton_titan/reaper_skeleton_titan.png"));
	private ModelSkeletonTitan skeletonModel;
	private ModelBase overlayingModel = new ModelSkeletonTitan(0.1F);public RenderSkeletonTitan()
	{
		super(new ModelSkeletonTitan(), 0.5F);
		this.skeletonModel = ((ModelSkeletonTitan)this.mainModel);
	}

	public void doRender(EntitySkeletonTitan entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		if (entity.getSkeletonType() == 1)
		{
			this.skeletonModel.isWither = true;
		}

		else
		{
			this.skeletonModel.isWither = false;
		}

		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected void preRenderCallback(EntitySkeletonTitan p_77041_1_, float p_77041_2_)
	{
		float f1 = p_77041_1_.getTitanSizeMultiplier();
		GL11.glScalef(f1, f1, f1);
		GL11.glTranslatef(0F, 0.0075F, 0F);
	}

	protected ResourceLocation func_180577_a(EntitySkeletonTitan entity)
	{
		if (entity.getSkeletonType() == 1)
		{
			switch (entity.getTitanVariant())
			{
				case 1:
				return witherSkeleton2Textures;
				case 2:
				return witherSkeleton3Textures;
				default:
				return witherSkeletonTextures;
			}
		}

		else
		{
			switch (entity.getTitanVariant())
			{
				case 1:
				return (entity.attackTimer >= 30 ? skeletonTitan2Pull3Textures : (entity.attackTimer < 30 && entity.attackTimer >= 20 ? skeletonTitan2Pull2Textures : (entity.attackTimer < 20 && entity.attackTimer >= 10 ? skeletonTitan2Pull1Textures : skeletonTitan2Textures)));
				case 2:
				return skeletonTitan3Textures;
				case 3:
				return (entity.attackTimer >= 30 ? skeletonTitan4Pull3Textures : (entity.attackTimer < 30 && entity.attackTimer >= 20 ? skeletonTitan4Pull2Textures : (entity.attackTimer < 20 && entity.attackTimer >= 10 ? skeletonTitan4Pull1Textures : skeletonTitan4Textures)));
				case 4:
				return (entity.attackTimer >= 30 ? skeletonTitan5Pull3Textures : (entity.attackTimer < 30 && entity.attackTimer >= 20 ? skeletonTitan5Pull2Textures : (entity.attackTimer < 20 && entity.attackTimer >= 10 ? skeletonTitan5Pull1Textures : skeletonTitan5Textures)));
				default:
				return (entity.attackTimer >= 30 ? skeletonTitanPull3Textures : (entity.attackTimer < 30 && entity.attackTimer >= 20 ? skeletonTitanPull2Textures : (entity.attackTimer < 20 && entity.attackTimer >= 10 ? skeletonTitanPull1Textures : skeletonTitanTextures)));
			}
		}
	}

	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return func_180577_a((EntitySkeletonTitan)entity);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		preRenderCallback((EntitySkeletonTitan)p_77041_1_, p_77041_2_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_180577_a((EntitySkeletonTitan)entity);
	}

	protected int shouldRenderPass(EntitySkeletonTitan p_77032_1_, int p_77032_2_, float p_77032_3_)
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
				((ModelSkeletonTitan)this.overlayingModel).HeldItem.showModel = false;
				((ModelSkeletonTitan)this.overlayingModel).HeldItem2.showModel = false;
				GL11.glMatrixMode(5888);
				GL11.glEnable(3042);
				if (p_77032_1_.getSkeletonType() == 1)
				{
					switch (p_77032_1_.getTitanVariant())
					{
						case 1:
						GL11.glColor4f(0.9F + (MathHelper.cos(f1 * 0.05F) * 0.2F), 0.3F, 0.2F, 1.0F);
						break;
						case 2:
						GL11.glColor4f(0.2F, 0.2F, 0.3F, 1.0F);
						break;
						default:
						GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
					}
				}

				else
				{
					switch (p_77032_1_.getTitanVariant())
					{
						case 1:
						GL11.glColor4f(0.76F + (MathHelper.cos(f1 * 0.05F) * 0.1F), 0.69F, 0.5F, 1.0F);
						break;
						case 2:
						GL11.glColor4f(0.7F + (MathHelper.cos(f1 * 0.5F) * 0.3F), 0.0F,0.7F + (MathHelper.cos(f1 * 0.5F) * 0.3F), 1.0F);
						break;
						case 3:
						GL11.glColor4f(0.1F, 0.5F, 0.6F + (MathHelper.cos(f1 * 0.05F) * 0.1F), 1.0F);
						break;
						case 4:
						GL11.glColor4f(0.4F, 0.0F,0.4F, 1.0F);
						break;
						default:
						GL11.glColor4f(0.5F, 0.6F + (MathHelper.cos(f1 * 0.05F) * 0.1F), 0.6F + (MathHelper.cos(f1 * 0.05F) * 0.1F), 1.0F);
					}
				}

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

		if (p_77032_1_.getTitanVariant() == 1 && p_77032_1_.getSkeletonType() == 1)
		{
			if (p_77032_2_ != 0)
			{
				return -1;
			}

			bindTexture(witherSkeleton2OverlayTextures);
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

	protected int inheritRenderPass(EntitySkeletonTitan p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return -1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntitySkeletonTitan)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected int inheritRenderPass(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return inheritRenderPass((EntitySkeletonTitan)p_77035_1_, p_77035_2_, p_77035_3_);
	}

	public void func_180579_a(EntitySkeletonTitan p_180579_1_, double p_180579_2_, double p_180579_4_, double p_180579_6_, float p_180579_8_, float p_180579_9_)
	{
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntitySkeletonTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntitySkeletonTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntitySkeletonTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


