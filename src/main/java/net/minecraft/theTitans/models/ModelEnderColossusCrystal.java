package net.minecraft.theTitans.models;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class ModelEnderColossusCrystal
extends ModelBase
{
	private ModelRenderer cube;
	private ModelRenderer glass = new ModelRenderer(this, "glass");
	public ModelEnderColossusCrystal(float p_i1170_1_, boolean p_i1170_2_)
	{
		this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
		this.cube = new ModelRenderer(this, "cube");
		this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
	}

	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	{
		GL11.glPushMatrix();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glTranslatef(0.0F, -0.5F, 0.0F);
		GL11.glRotatef(p_78088_3_, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.8F + p_78088_4_, 0.0F);
		GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
		this.glass.render(p_78088_7_);
		float f6 = 0.875F;
		GL11.glScalef(f6, f6, f6);
		GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
		GL11.glRotatef(p_78088_3_, 0.0F, 1.0F, 0.0F);
		this.glass.render(p_78088_7_);
		GL11.glScalef(f6, f6, f6);
		GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
		GL11.glRotatef(p_78088_3_, 0.0F, 1.0F, 0.0F);
		this.cube.render(p_78088_7_);
		GL11.glPopMatrix();
	}
}


