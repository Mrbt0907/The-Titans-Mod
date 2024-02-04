package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelPigZombieTitan;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderZombiePigmanTitan
extends RenderLiving
{
	private static final ResourceLocation zombiePigmanTitanTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/pigzombie_titan/zombie_pigman_titan.png"));
	private static final ResourceLocation zombiePigman2TitanTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/pigzombie_titan/king_pigman_titan.png"));
	private static final ResourceLocation zombiePigman3TitanTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/pigzombie_titan/wither_pigman_titan.png"));
	private static final ResourceLocation zombiePigman4TitanTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/pigzombie_titan/count_pigvampire_titan.png"));
	private static final ResourceLocation zombiePigman5TitanTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/zombie_titan/pigzombie_titan/void_pigman_titan.png"));
	private ModelBase overlayingModel = new ModelPigZombieTitan(0.1F);public RenderZombiePigmanTitan()
	{
		super(new ModelPigZombieTitan(), 0.5F);
	}

	protected ResourceLocation func_177119_a(EntityPigZombieTitan entity)
	{
		switch (entity.getTitanVariant())
		{
			case 1:
			return zombiePigman2TitanTextures;
			case 2:
			return zombiePigman3TitanTextures;
			case 3:
			return zombiePigman4TitanTextures;
			case 4:
			return zombiePigman5TitanTextures;
			default:
			return zombiePigmanTitanTextures;
		}
	}

	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return func_177119_a((EntityPigZombieTitan)entity);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_177119_a((EntityPigZombieTitan)entity);
	}

	protected void func_180592_a(EntityPigZombieTitan p_180592_1_, float p_180592_2_)
	{
		float f1 = p_180592_1_.getTitanSizeMultiplier();
		GL11.glScalef(f1, f1, f1);
		GL11.glTranslatef(0.0F, 0.01F, 0.0F);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		func_180592_a((EntityPigZombieTitan)p_77041_1_, p_77041_2_);
	}

	protected int shouldRenderPass(EntityPigZombieTitan p_77032_1_, int p_77032_2_, float p_77032_3_)
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
				((ModelPigZombieTitan)this.overlayingModel).HeldItem.showModel = false;
				GL11.glMatrixMode(5888);
				GL11.glEnable(3042);
				GL11.glColor4f(0.7F + (MathHelper.cos(f1 * 0.05F) * 0.2F), 0.2F + (MathHelper.cos(f1 * 0.05F) * 0.2F), 0F, 1.0F);
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

	protected int inheritRenderPass(EntityPigZombieTitan p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return -1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityPigZombieTitan)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected int inheritRenderPass(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return inheritRenderPass((EntityPigZombieTitan)p_77035_1_, p_77035_2_, p_77035_3_);
	}

	public void func_180579_a(EntityPigZombieTitan p_180579_1_, double p_180579_2_, double p_180579_4_, double p_180579_6_, float p_180579_8_, float p_180579_9_)
	{
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityPigZombieTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityPigZombieTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntityPigZombieTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


