package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityEnderColossus;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelEnderColossus;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderEnderColossus
extends RenderLiving
{
	private static final ResourceLocation endermanEyeBeamTexture = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/ender_colossus_beam.png"));
	private static final ResourceLocation endermanEyesTexture = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/ender_colossus_eyes.png"));
	private static final ResourceLocation endermanTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/ender_colossus.png"));
	private static final ResourceLocation endermanEyesDeadTexture = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/ender_colossus_eyes_dead.png"));
	private static final ResourceLocation endermanDeadTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/ender_colossus_dead.png"));
	private static final ResourceLocation enderman2EyesTexture = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/holy_ender_colossus_eyes.png"));
	private static final ResourceLocation enderman2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/holy_ender_colossus.png"));
	private static final ResourceLocation enderman2EyesDeadTexture = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/holy_ender_colossus_eyes_dead.png"));
	private static final ResourceLocation enderman2DeadTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/holy_ender_colossus_dead.png"));
	private static final ResourceLocation enderman3EyesTexture = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/icey_ender_colossus_eyes.png"));
	private static final ResourceLocation enderman3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/icey_ender_colossus.png"));
	private static final ResourceLocation enderman3EyesDeadTexture = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/icey_ender_colossus_eyes_dead.png"));
	private static final ResourceLocation enderman3DeadTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/ender_colossus/icey_ender_colossus_dead.png"));
	private ModelEnderColossus endermanModel;
	public RenderEnderColossus()
	{
		super(new ModelEnderColossus(), 0.5F);
		this.endermanModel = ((ModelEnderColossus)this.mainModel);
		setRenderPassModel(this.endermanModel);
	}

	protected void func_180592_a(EntityEnderColossus p_180592_1_, float p_180592_2_)
	{
		float f1 = p_180592_1_.getTitanSizeMultiplier();
		GL11.glScalef(f1, f1, f1);
		GL11.glTranslatef(0.0F, 0.015F, 0.0F);
		if (p_180592_1_.ridingEntity != null)
		{
			GL11.glTranslatef(0.0F, 0.1F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		}
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		func_180592_a((EntityEnderColossus)p_77041_1_, p_77041_2_);
	}

	public void doRender(EntityEnderColossus entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.endermanModel.isAttacking = entity.isScreaming();
		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
		if (entity.getEyeLaserTime() >= 0 && entity.getHealth() > 0.0F)
		{
			Tessellator tessellator = Tessellator.instance;
			bindTexture(endermanEyeBeamTexture);
			GL11.glTexParameterf(3553, 10242, 10497.0F);
			GL11.glTexParameterf(3553, 10243, 10497.0F);
			GL11.glDisable(2896);
			GL11.glDisable(2884);
			GL11.glDisable(3042);
			GL11.glDepthMask(true);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 15728880 % 65536 / 1.0F, 15728880 / 65536 / 1.0F);
			OpenGlHelper.glBlendFunc(770, 1, 1, 0);
			float f4 = (float)entity.worldObj.getTotalWorldTime() + partialTicks;
			float f5 = f4 * 0.5F % 1.0F;
			float f6 = entity.getEyeHeight();
			GL11.glPushMatrix();
			float f = entity.rotationYawHead * 3.1415927F / 180.0F;
			MathHelper.sin(f);
			MathHelper.cos(f);
			GL11.glTranslatef((float)x, (float)y + f6 + ((0.02F + 0.02F * MathHelper.cos(partialTicks * 0.05F)) * 3.1415927F), (float)z);
			GL11.glScalef(32F, 32F, 32F);
			Vec3 vec3 = func_177110_b(entity, f6, partialTicks);
			Vec3 vec31 = func_177110_a(entity, f6, partialTicks);
			Vec3 vec32 = vec3.subtract(vec31);
			double d3 = vec32.lengthVector() + 0.1D;
			vec32 = vec32.normalize();
			float f7 = (float)Math.acos(vec32.yCoord);
			float f8 = (float)Math.atan2(vec32.zCoord, vec32.xCoord);
			GL11.glRotatef((1.5707964F + -f8) * 57.295776F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(f7 * 57.295776F, 1.0F, 0.0F, 0.0F);
			double d4 = f4 * 0.005D * -1.5D;
			tessellator.startDrawingQuads();
			tessellator.setColorRGBA(255, 0, 255, 255);
			double d15 = Math.cos(d4 * 0.0D + 3.141592653589793D) * 0.2D;
			double d16 = Math.sin(d4 * 0.0D + 3.141592653589793D) * 0.2D;
			double d17 = Math.cos(d4 * 0.0D + 0.0D) * 0.2D;
			double d18 = Math.sin(d4 * 0.0D + 0.0D) * 0.2D;
			double d25 = -1.0F + f5;
			double d26 = d3 * 2.5D + d25;
			tessellator.addVertexWithUV(d15, -d3, d16, 0.5D, d26);
			tessellator.addVertexWithUV(d15, 0.0D, d16, 0.5D, d25);
			tessellator.addVertexWithUV(d17, 0.0D, d18, 0.0D, d25);
			tessellator.addVertexWithUV(d17, -d3, d18, 0.0D, d26);
			tessellator.draw();
			GL11.glPopMatrix();
		}
	}

	private Vec3 func_177110_a(EntityLivingBase p_177110_1_, double p_177110_2_, float p_177110_4_)
	{
		double d1 = p_177110_1_.lastTickPosX + (p_177110_1_.posX - p_177110_1_.lastTickPosX) * p_177110_4_;
		double d2 = p_177110_2_ + p_177110_1_.lastTickPosY + (p_177110_1_.posY - p_177110_1_.lastTickPosY) * p_177110_4_;
		double d3 = p_177110_1_.lastTickPosZ + (p_177110_1_.posZ - p_177110_1_.lastTickPosZ) * p_177110_4_;
		return Vec3.createVectorHelper(d1, d2, d3);
	}

	private Vec3 func_177110_b(EntityLivingBase p_177110_1_, double p_177110_2_, float p_177110_4_)
	{
		Vec3 vec3 = p_177110_1_.getLookVec();
		double dx = vec3.xCoord * 300D;
		double dy = vec3.yCoord * 300D;
		double dz = vec3.zCoord * 300D;
		double d1 = p_177110_1_.lastTickPosX + (p_177110_1_.posX + dx - p_177110_1_.lastTickPosX) * p_177110_4_;
		double d2 = p_177110_2_ + p_177110_1_.lastTickPosY + (p_177110_1_.posY + dy - p_177110_1_.lastTickPosY) * p_177110_4_;
		double d3 = p_177110_1_.lastTickPosZ + (p_177110_1_.posZ + dz - p_177110_1_.lastTickPosZ) * p_177110_4_;
		return Vec3.createVectorHelper(d1, d2, d3);
	}

	protected int shouldRenderPass(EntityEnderColossus p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		if (p_77032_2_ != 0)
		{
			return -1;
		}

		if (p_77032_1_.getEyeLaserTime() >= 0 || (p_77032_1_.getAnimID() == 10 && p_77032_1_.deathTicks > 160))
		{
			switch (p_77032_1_.getTitanVariant())
			{
				case 1:
				bindTexture(enderman2EyesDeadTexture);
				break;
				case 2:
				bindTexture(enderman3EyesDeadTexture);
				break;
				default:
				bindTexture(endermanEyesDeadTexture);
			}
		}

		else
		{
			switch (p_77032_1_.getTitanVariant())
			{
				case 1:
				bindTexture(enderman2EyesTexture);
				break;
				case 2:
				bindTexture(enderman3EyesTexture);
				break;
				default:
				bindTexture(endermanEyesTexture);
			}
		}

		GL11.glEnable(3042);
		GL11.glDisable(3008);
		GL11.glBlendFunc(1, 1);
		GL11.glDisable(2896);
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
		GL11.glEnable(2896);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		return 1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityEnderColossus)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected ResourceLocation func_180573_a(EntityEnderColossus p_180573_1_)
	{
		if (p_180573_1_.getAnimID() == 10 && p_180573_1_.deathTicks > 200)
		{
			switch (p_180573_1_.getTitanVariant())
			{
				case 1:
				return enderman2DeadTextures;
				case 2:
				return enderman3DeadTextures;
				default:
				return endermanDeadTextures;
			}
		}

		else
		{
			switch (p_180573_1_.getTitanVariant())
			{
				case 1:
				return enderman2Textures;
				case 2:
				return enderman3Textures;
				default:
				return endermanTextures;
			}
		}
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityEnderColossus)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityEnderColossus)entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_180573_a((EntityEnderColossus)entity);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityEnderColossus)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


