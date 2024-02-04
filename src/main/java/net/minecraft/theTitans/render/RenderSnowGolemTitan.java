package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntitySnowGolemTitan;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
@SideOnly(Side.CLIENT)
public class RenderSnowGolemTitan
extends RenderLiving
{
	private static final ResourceLocation snowManTextures = new ResourceLocation("textures/entity/snowman.png");
	private ModelSnowMan snowmanModel;
	public RenderSnowGolemTitan()
	{
		super(new ModelSnowMan(), 0.5F);
		this.snowmanModel = ((ModelSnowMan)this.mainModel);
		setRenderPassModel(this.snowmanModel);
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

			GL11.glScalef(1.0F + (f3 * 1.05F), 1.0F - (f3 * 0.5F), 1.0F + (f3 * 1.05F));
		}
	}

	protected void renderEquippedItems(EntitySnowGolemTitan p_77029_1_, float p_77029_2_)
	{
		super.renderEquippedItems(p_77029_1_, p_77029_2_);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		Calendar calendar = Calendar.getInstance();
		ItemStack itemstack;if ((calendar.get(2) + 1 == 10) && (calendar.get(5) >= 29) && (calendar.get(5) <= 31))itemstack = new ItemStack(p_77029_1_.getHealth() <= p_77029_1_.getMaxHealth() / 2 ? Blocks.end_portal_frame : Blocks.ender_chest, 1);
		else
		itemstack = new ItemStack((p_77029_1_.getHealth() <= p_77029_1_.getMaxHealth() / 2 || ((p_77029_1_.getInvulTime() > 0) && ((p_77029_1_.getInvulTime() > 60) || (p_77029_1_.getInvulTime() / 5 % 2 != 1))) ? Blocks.pumpkin : Blocks.lit_pumpkin), 1);
		if ((itemstack.getItem() instanceof ItemBlock))
		{
			GL11.glPushMatrix();
			if (p_77029_1_.getHealth() > p_77029_1_.getMaxHealth() / 2)
			{
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)(15728880 % 65536) / 1.0F, (float)(15728880 / 65536) / 1.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				setRenderPassModel(this.mainModel);
			}

			this.snowmanModel.head.postRender(0.0625F);
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
			boolean is3D = (customRenderer != null) && (customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D));
			if ((is3D) || (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType())))
			{
				float f1 = 0.625F;
				GL11.glTranslatef(0.0F, -0.3437F, 0.0F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f1, -f1, f1);
			}

			if (p_77029_1_.getHealth() > p_77029_1_.getMaxHealth() / 4)
			this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, 0);
			GL11.glPopMatrix();
		}
	}

	protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_)
	{
		renderEquippedItems((EntitySnowGolemTitan)p_77029_1_, p_77029_2_);
	}

	protected ResourceLocation func_180587_a(EntitySnowGolemTitan p_180587_1_)
	{
		return snowManTextures;
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_180587_a((EntitySnowGolemTitan)entity);
	}

	protected void func_180592_a(EntitySnowGolemTitan p_180592_1_, float p_180592_2_)
	{
		float f1 = 16.0F;
		int i = p_180592_1_.getInvulTime();
		if (i > 0)
		{
			f1 -= (i - p_180592_2_) / 10F;
		}

		GL11.glScalef(f1, f1, f1);
		GL11.glTranslatef(0.0F, 0.0275F, 0.0F);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		func_180592_a((EntitySnowGolemTitan)p_77041_1_, p_77041_2_);
	}

	public void func_180579_a(EntitySnowGolemTitan p_180579_1_, double p_180579_2_, double p_180579_4_, double p_180579_6_, float p_180579_8_, float p_180579_9_)
	{
		super.doRender(p_180579_1_, p_180579_2_, p_180579_4_, p_180579_6_, p_180579_8_, p_180579_9_);
	}

	public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntitySnowGolemTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntitySnowGolemTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		func_180579_a((EntitySnowGolemTitan)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


