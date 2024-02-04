package net.minecraft.theTitans.render.items;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelTitanSpawnEgg;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
public class RenderWitherSkeletonTitanSpawnEgg implements IItemRenderer
{
	public static final ResourceLocation[] EGG_TEXTURE_LIST = new ResourceLocation[]
	{
		new ResourceLocation(TheTitans.getTextures("textures/entities", "eggs/witherskeletontitan/wither_skeleton_titan_egg.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "eggs/witherskeletontitan/solar_skeleton_titan_egg.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "eggs/witherskeletontitan/reaper_skeleton_titan_egg.png"))
	};
	
	ModelTitanSpawnEgg eggmodel;
	public RenderWitherSkeletonTitanSpawnEgg()
	{
		this.eggmodel = new ModelTitanSpawnEgg();
	}

	public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
	{
		switch (type)
		{
			case EQUIPPED:GL11.glPushMatrix();
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(EGG_TEXTURE_LIST[item.getItemDamage()]);
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -0.1F, 0.2F);
			this.eggmodel.render(4, (Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
			case EQUIPPED_FIRST_PERSON:GL11.glPushMatrix();
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(EGG_TEXTURE_LIST[item.getItemDamage()]);
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(125.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.45F, -0.2F, 0.5F);
			this.eggmodel.render(4, (Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
			case ENTITY:GL11.glPushMatrix();
			GL11.glScalef(3.0F, 3.0F, 3.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(EGG_TEXTURE_LIST[item.getItemDamage()]);
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.02F, -0.15F, 0.02F);
			this.eggmodel.render(4, (Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
			case INVENTORY:GL11.glPushMatrix();
			GL11.glScalef(2.0F, 2.0F, 2.0F);
			Minecraft.getMinecraft().renderEngine.bindTexture(EGG_TEXTURE_LIST[item.getItemDamage()]);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			this.eggmodel.render(0.0625F, 4);
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


