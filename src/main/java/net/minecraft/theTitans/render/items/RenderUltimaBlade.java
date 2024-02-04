package net.minecraft.theTitans.render.items;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelUltimaBlade;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
public class RenderUltimaBlade implements net.minecraftforge.client.IItemRenderer
{
	private static final ResourceLocation ultimaBladeTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "ultima_blade.png"));
	ModelUltimaBlade swordmodel;
	public RenderUltimaBlade()
	{
		this.swordmodel = new ModelUltimaBlade();
	}

	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
	{
		switch (type)
		{
			case EQUIPPED:GL11.glPushMatrix();
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(ultimaBladeTextures);
			GL11.glRotatef(80.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-80.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -1.0F, -0.3F);
			this.swordmodel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
			case EQUIPPED_FIRST_PERSON:GL11.glPushMatrix();
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(ultimaBladeTextures);
			GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-60.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-240.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.75F, -1.5F, -0.25F);
			this.swordmodel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
			case ENTITY:GL11.glPushMatrix();
			GL11.glScalef(4.0F, 4.0F, 4.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(ultimaBladeTextures);
			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.5F, 0.0F);
			this.swordmodel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
			case INVENTORY:GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(ultimaBladeTextures);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-30.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0F, 0.0F);
			this.swordmodel.render(0.0625F);
			GL11.glPopMatrix();
			break;
			default:
			break;
		}
	}

	public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
	{
		return true;
	}

	public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
	{
		switch (type)
		{
			case ENTITY:case INVENTORY:return true;
			default:return false;
		}
	}
}


