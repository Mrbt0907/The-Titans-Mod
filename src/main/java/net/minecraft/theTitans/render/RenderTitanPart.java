package net.minecraft.theTitans.render;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanPart;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
@SideOnly(Side.CLIENT)
public class RenderTitanPart
extends Render
{
	public RenderTitanPart()
	{
		this.shadowSize = 0.1F;
	}

	protected ResourceLocation getTextures(EntityTitanPart p_180554_1_)
	{
		return null;
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getTextures((EntityTitanPart)entity);
	}

	private void renderEntityOnFire(Entity p_76977_1_, double p_76977_2_, double p_76977_4_, double p_76977_6_, float p_76977_8_)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		IIcon iicon = Blocks.fire.getFireIcon(0);
		IIcon iicon1 = Blocks.fire.getFireIcon(1);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)p_76977_2_, (float)p_76977_4_, (float)p_76977_6_);
		float f1 = p_76977_1_.width * 1.4F;
		GL11.glScalef(f1, f1, f1);
		Tessellator tessellator = Tessellator.instance;
		float f2 = 0.5F;
		float f3 = 0.0F;
		float f4 = p_76977_1_.height / f1;
		float f5 = (float)(p_76977_1_.posY - p_76977_1_.boundingBox.minY);
		GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.0F, -0.3F + (float)((int)f4) * 0.02F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f6 = 0.0F;
		int i = 0;
		tessellator.startDrawingQuads();
		while (f4 > 0.0F)
		{
			IIcon iicon2 = i % 2 == 0 ? iicon : iicon1;
			this.bindTexture(TextureMap.locationBlocksTexture);
			float f7 = iicon2.getMinU();
			float f8 = iicon2.getMinV();
			float f9 = iicon2.getMaxU();
			float f10 = iicon2.getMaxV();
			if (i / 2 % 2 == 0)
			{
				float f11 = f9;
				f9 = f7;
				f7 = f11;
			}

			tessellator.addVertexWithUV((double)(f2 - f3), (double)(0.0F - f5), (double)f6, (double)f9, (double)f10);
			tessellator.addVertexWithUV((double)(-f2 - f3), (double)(0.0F - f5), (double)f6, (double)f7, (double)f10);
			tessellator.addVertexWithUV((double)(-f2 - f3), (double)(1.4F - f5), (double)f6, (double)f7, (double)f8);
			tessellator.addVertexWithUV((double)(f2 - f3), (double)(1.4F - f5), (double)f6, (double)f9, (double)f8);
			f4 -= 0.45F;
			f5 -= 0.45F;
			f2 *= 0.9F;
			f6 += 0.03F;
			++i;
		}

		tessellator.draw();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private void func_147907_a(Block p_147907_1_, double p_147907_2_, double p_147907_4_, double p_147907_6_, int p_147907_8_, int p_147907_9_, int p_147907_10_, float p_147907_11_, float p_147907_12_, double p_147907_13_, double p_147907_15_, double p_147907_17_)
	{
		Tessellator tessellator = Tessellator.instance;
		if (p_147907_1_.renderAsNormalBlock())
		{
			double d6 = ((double)p_147907_11_ - (p_147907_4_ - ((double)p_147907_9_ + p_147907_15_)) / 2.0D) * 0.5D * (double)this.renderManager.worldObj.getLightBrightness(p_147907_8_, p_147907_9_, p_147907_10_);
			if (d6 >= 0.0D)
			{
				if (d6 > 1.0D)
				d6 = 1.0D;
				tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float)d6);
				double d7 = (double)p_147907_8_ + p_147907_1_.getBlockBoundsMinX() + p_147907_13_;
				double d8 = (double)p_147907_8_ + p_147907_1_.getBlockBoundsMaxX() + p_147907_13_;
				double d9 = (double)p_147907_9_ + p_147907_1_.getBlockBoundsMinY() + p_147907_15_ + 0.015625D;
				double d10 = (double)p_147907_10_ + p_147907_1_.getBlockBoundsMinZ() + p_147907_17_;
				double d11 = (double)p_147907_10_ + p_147907_1_.getBlockBoundsMaxZ() + p_147907_17_;
				float f2 = (float)((p_147907_2_ - d7) / 2.0D / (double)p_147907_12_ + 0.5D);
				float f3 = (float)((p_147907_2_ - d8) / 2.0D / (double)p_147907_12_ + 0.5D);
				float f4 = (float)((p_147907_6_ - d10) / 2.0D / (double)p_147907_12_ + 0.5D);
				float f5 = (float)((p_147907_6_ - d11) / 2.0D / (double)p_147907_12_ + 0.5D);
				tessellator.addVertexWithUV(d7, d9, d10, (double)f2, (double)f4);
				tessellator.addVertexWithUV(d7, d9, d11, (double)f2, (double)f5);
				tessellator.addVertexWithUV(d8, d9, d11, (double)f3, (double)f5);
				tessellator.addVertexWithUV(d8, d9, d10, (double)f3, (double)f4);
			}
		}
	}

	private void renderShadow(Entity p_76975_1_, double p_76975_2_, double p_76975_4_, double p_76975_6_, float p_76975_8_, float p_76975_9_)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.renderManager.renderEngine.bindTexture(new ResourceLocation("textures/misc/shadow.png"));
		World world = this.renderManager.worldObj;
		GL11.glDepthMask(false);
		float f2 = this.shadowSize;
		if (p_76975_1_ instanceof EntityLiving)
		{
			EntityLiving entityliving = (EntityLiving)p_76975_1_;
			f2 *= entityliving.getRenderSizeModifier();
			if (entityliving.isChild())
			f2 *= 0.5F;
		}

		double d8 = p_76975_1_.lastTickPosX + (p_76975_1_.posX - p_76975_1_.lastTickPosX) * (double)p_76975_9_;
		double d3 = p_76975_1_.lastTickPosY + (p_76975_1_.posY - p_76975_1_.lastTickPosY) * (double)p_76975_9_ + (double)p_76975_1_.getShadowSize();
		double d4 = p_76975_1_.lastTickPosZ + (p_76975_1_.posZ - p_76975_1_.lastTickPosZ) * (double)p_76975_9_;
		int i = MathHelper.floor_double(d8 - (double)f2);
		int j = MathHelper.floor_double(d8 + (double)f2);
		int k = MathHelper.floor_double(d3 - (double)f2);
		int l = MathHelper.floor_double(d3);
		int i1 = MathHelper.floor_double(d4 - (double)f2);
		int j1 = MathHelper.floor_double(d4 + (double)f2);
		double d5 = p_76975_2_ - d8;
		double d6 = p_76975_4_ - d3;
		double d7 = p_76975_6_ - d4;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		for (int k1 = i; k1 <= j; ++k1)
		for (int l1 = k; l1 <= l; ++l1)
		for (int i2 = i1; i2 <= j1; ++i2)
		{
			Block block = world.getBlock(k1, l1 - 1, i2);
			if (block.getMaterial() != Material.air)
			func_147907_a(block, p_76975_2_, p_76975_4_ + (double)p_76975_1_.getShadowSize(), p_76975_6_, k1, l1, i2, p_76975_8_, f2, d5, d6 + (double)p_76975_1_.getShadowSize(), d7);
		}

		tessellator.draw();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
	}

	public void doRenderShadowAndFire(Entity p_76979_1_, double p_76979_2_, double p_76979_4_, double p_76979_6_, float p_76979_8_, float p_76979_9_)
	{
		if (this.shadowSize > 0.0F && !p_76979_1_.isInvisible())
		{
			double d3 = this.renderManager.getDistanceToCamera(p_76979_1_.posX, p_76979_1_.posY, p_76979_1_.posZ);
			float f2 = (float)((1.0D - d3 / 40000D));
			if (f2 > 0.0F)
			this.renderShadow(p_76979_1_, p_76979_2_, p_76979_4_, p_76979_6_, f2, p_76979_9_);
		}

		if (p_76979_1_.canRenderOnFire() && !p_76979_1_.isInvisible())
		this.renderEntityOnFire(p_76979_1_, p_76979_2_, p_76979_4_, p_76979_6_, p_76979_9_);
	}

	public void doRender(EntityTitanPart entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.shadowSize = entity.getParent() != null && entity.getParent() instanceof EntityTitan && ((EntityTitan)entity.getParent()).getWaiting() ? 0F : 1F + (entity.width / 2F);
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityTitanPart)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}


