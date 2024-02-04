package net.minecraft.theTitans.render.blocks;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.theTitans.TitanRenders;
import net.minecraft.theTitans.blocks.BlockNormalOre;
import net.minecraft.world.IBlockAccess;
public class RenderBlockOre implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		render(block, renderer);
		renderer.setOverrideBlockTexture(((BlockNormalOre)block).getTexture(1));
		render(block, renderer);
		renderer.clearOverrideBlockTexture();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (renderer != null && world != null && block != null && modelId == getRenderId())
		{
			Minecraft.getMinecraft().theWorld.theProfiler.startSection("onRenderBlockOre");
			((BlockNormalOre)block).renderItem = true;
			renderer.renderStandardBlock(block, x, y, z);
			renderer.setOverrideBlockTexture(((BlockNormalOre)block).getTexture(1));
			renderer.renderStandardBlock(block, x, y, z);
			renderer.clearOverrideBlockTexture();
			((BlockNormalOre)block).renderItem = false;
			Minecraft.getMinecraft().theWorld.theProfiler.endSection();
			return true;
		}

		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{

		return true;
	}

	@Override
	public int getRenderId() 
	{

		return TitanRenders.RENDERBLOCKOREID;
	}

	protected void setRenderBoundsFromBlock(Block block, RenderBlocks renderer)
	{
		double mult = 1.0D;
		if (renderer.hasOverrideBlockTexture())
		mult = 1.001D;
		if (!renderer.lockBlockBounds)
		{
			renderer.renderMinX = block.getBlockBoundsMinX() * mult;
			renderer.renderMaxX = block.getBlockBoundsMaxX() * mult;
			renderer.renderMinY = block.getBlockBoundsMinY() * mult;
			renderer.renderMaxY = block.getBlockBoundsMaxY() * mult;
			renderer.renderMinZ = block.getBlockBoundsMinZ() * mult;
			renderer.renderMaxZ = block.getBlockBoundsMaxZ() * mult;
			renderer.partialRenderBounds = renderer.minecraftRB.gameSettings.ambientOcclusion >= 2 && (renderer.renderMinX > 0.0D || renderer.renderMaxX < 1.0D || renderer.renderMinY > 0.0D || renderer.renderMaxY < 1.0D || renderer.renderMinZ > 0.0D || renderer.renderMaxZ < 1.0D);
		}
	}

	protected void render(Block block, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.setColorOpaque_F(1, 1, 1);
		block.setBlockBoundsForItemRender();
		setRenderBoundsFromBlock(block, renderer);
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, 0));
		tessellator.draw();
		if (renderer.useInventoryTint)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, 0));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, 0));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}


