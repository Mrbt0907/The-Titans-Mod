package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
public class ModelUltimaBlade
extends ModelBase
{
	public ModelRenderer Handle;
	public ModelRenderer Guard;
	public ModelRenderer BladeLower;
	public ModelRenderer BladeTip;
	public ModelUltimaBlade()
	{
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.BladeTip = new ModelRenderer(this, 16, 48);
		this.BladeTip.setRotationPoint(0.0F, -72.0F, 0.0F);
		this.BladeTip.addBox(0.0F, -72.0F, -4.0F, 0, 72, 8, 0.0F);
		setRotateAngle(this.BladeTip, -0.017453292F, 0.0F, 0.0F);
		this.Guard = new ModelRenderer(this, 48, 0);
		this.Guard.setRotationPoint(0.0F, -48.0F, 0.0F);
		this.Guard.addBox(-10.0F, -2.0F, -10.0F, 20, 2, 20, 0.0F);
		this.Handle = new ModelRenderer(this, 0, 0);
		this.Handle.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.Handle.addBox(-4.0F, -48.0F, -4.0F, 8, 48, 8, 0.0F);
		this.BladeLower = new ModelRenderer(this, 0, 48);
		this.BladeLower.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BladeLower.addBox(0.0F, -72.0F, -4.0F, 0, 72, 8, 0.0F);
		this.BladeLower.addChild(this.BladeTip);
		this.Handle.addChild(this.Guard);
		this.Guard.addChild(this.BladeLower);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.Handle.render(f5);
	}

	public void render(float f5)
	{
		this.Handle.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}


