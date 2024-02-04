package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelZombieTitan;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderZombieTitan
extends RenderLiving
{
	private static final ResourceLocation zombieTitanTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/zombie_titan.png"));
	private static final ResourceLocation zombieTitan2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/jungle_zombie_titan.png"));
	private static final ResourceLocation zombieTitan3Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/blood_zombie_titan.png"));
	private static final ResourceLocation zombieTitan4Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/radiation_zombie_titan.png"));
	private static final ResourceLocation zombieTitan5Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/void_zombie_titan.png"));
	private ModelBase overlayingModel = new ModelZombieTitan(0.1F);public RenderZombieTitan()
	{
		super(new ModelZombieTitan(), 0.5F);
	}

	protected int shouldRenderPass(EntityZombieTitan p_77032_1_, int p_77032_2_, float p_77032_3_)
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
				((ModelZombieTitan)this.overlayingModel).HeldItem.showModel = false;
				GL11.glMatrixMode(5888);
				GL11.glEnable(3042);
				GL11.glColor4f(0.0F, 0.6F + (MathHelper.cos(f1 * 0.05F) * 0.3F), 0F, 1.0F);
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

		return -1;
	}

	protected int inheritRenderPass(EntityZombieTitan p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return -1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityZombieTitan)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected int inheritRenderPass(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return inheritRenderPass((EntityZombieTitan)p_77035_1_, p_77035_2_, p_77035_3_);
	}

	protected void func_180592_a(EntityZombieTitan p_180592_1_, float p_180592_2_)
	{
		float f1 = p_180592_1_.getTitanSizeMultiplier();
		GL11.glScalef(f1, f1, f1);
		GL11.glTranslatef(0.0F, 0.01F, 0.0F);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		func_180592_a((EntityZombieTitan)p_77041_1_, p_77041_2_);
	}

	public void func_180579_a(EntityZombieTitan p_180579_1_, double p_180579_2_, double p_180579_4_, double p_180579_6_, float p_180579_8_, float p_180579_9_)
	{
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	protected ResourceLocation func_180578_a(EntityZombieTitan entity)
	{
		switch (entity.getTitanVariant())
		{
			case 1:
			return zombieTitan2Textures;
			case 2:
			return zombieTitan3Textures;
			case 3:
			return zombieTitan4Textures;
			case 4:
			return zombieTitan5Textures;
			default:
			return zombieTitanTextures;
		}
	}

	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return func_180578_a((EntityZombieTitan)entity);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityZombieTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityZombieTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_180578_a((EntityZombieTitan)entity);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityZombieTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityZombieTitan p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}


