package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityItemBomb;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
@SideOnly(Side.CLIENT)
public class RenderItemBomb extends Render
{
	/**
	* Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	* handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	* (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	* double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	*/
	public void doRender(EntityItemBomb p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		Item item = p_76986_1_.getEntityItem().getItem();
		IIcon iicon = p_76986_1_.getWildCard() ? Items.filled_map.getIconFromDamage(0) : item.getIconFromDamage(p_76986_1_.getEntityItem().getItemDamage());
		if (item != null && iicon != null)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			float f9 = 3F;
			GL11.glScalef(f9, f9, f9);
			this.bindEntityTexture(p_76986_1_);
			Tessellator tessellator = Tessellator.instance;
			GL11.glTranslatef(0, MathHelper.sin(((float)p_76986_1_.ticksExisted + p_76986_9_) / 20.0F) * 0.1F + 0.1F, 0);
			GL11.glRotatef((float) ((((float)p_76986_1_.ticksExisted + p_76986_4_) / 40.0F) * (180F / (float)Math.PI)), 0.0F, 1.0F, 0.0F);
			if (p_76986_1_.getWildCard())
			{
				GL11.glScalef(1.25F, 1.25F, 1.25F);
				GL11.glTranslatef(-0.5F, -0.05F, 0);
				ItemRenderer.renderItemIn2D(tessellator, iicon.getMaxU(), iicon.getMinV(), iicon.getMinU(), iicon.getMaxV(), iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
			}

			else
			{
				if (item instanceof ItemBlock)
				{
					GL11.glTranslatef(0, 0.5F, 0);
					this.field_147909_c.renderBlockAsItem(Block.getBlockFromItem(item), 0, 1F);
				}

				else
				{
					GL11.glScalef(1.25F, 1.25F, 1.25F);
					GL11.glTranslatef(-0.5F, -0.05F, 0);
					ItemRenderer.renderItemIn2D(tessellator, iicon.getMaxU(), iicon.getMinV(), iicon.getMinU(), iicon.getMaxV(), iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
				}
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	protected ResourceLocation getEntityTexture(EntityItemBomb p_110775_1_)
	{
		return p_110775_1_.getEntityItem().getItem() instanceof ItemBlock ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture;
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntityItemBomb)p_110775_1_);
	}

	/**
	* Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	* handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	* (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	* double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	*/
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityItemBomb)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/*private void func_77026_a(Tessellator p_77026_1_, IIcon p_77026_2_)
	{
		float f = p_77026_2_.getMinU();
		float f1 = p_77026_2_.getMaxU();
		float f2 = p_77026_2_.getMinV();
		float f3 = p_77026_2_.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		p_77026_1_.startDrawingQuads();
		p_77026_1_.setNormal(0.0F, 1.0F, 0.0F);
		p_77026_1_.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
		p_77026_1_.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
		p_77026_1_.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
		p_77026_1_.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
		p_77026_1_.draw();

	}

	*/
}


