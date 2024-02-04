package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
/**
* ModelOptimaAxe - Undefined
* Created using Tabula 4.1.1
*/
public class ModelOptimaAxe extends ModelBase 
{

	public ModelRenderer Grip;
	public ModelRenderer Handle;
	public ModelRenderer Blade1;
	public ModelRenderer Blade2;
	public ModelOptimaAxe()
	{
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.Grip = new ModelRenderer(this, 0, 0);
		this.Grip.setRotationPoint(0.0F, 24.0F, 0.0F);
		this.Grip.addBox(-8.0F, -48.0F, -8.0F, 16, 48, 16, 0.0F);
		this.Handle = new ModelRenderer(this, 96, 0);
		this.Handle.setRotationPoint(0.0F, -48.0F, 0.0F);
		this.Handle.addBox(-4.0F, -120.0F, -4.0F, 8, 120, 8, 0.0F);
		this.Blade2 = new ModelRenderer(this, 0, 16);
		this.Blade2.mirror = true;
		this.Blade2.setRotationPoint(0.0F, -116.0F, 0.0F);
		this.Blade2.addBox(0.0F, -32.0F, 4.0F, 0, 64, 48, 0.0F);
		this.setRotateAngle(Blade2, 0.0F, 3.141592653589793F, 0.0F);
		this.Blade1 = new ModelRenderer(this, 0, 16);
		this.Blade1.setRotationPoint(0.0F, -116.0F, 0.0F);
		this.Blade1.addBox(0.0F, -32.0F, 4.0F, 0, 64, 48, 0.0F);
		this.Grip.addChild(this.Handle);
		this.Handle.addChild(this.Blade2);
		this.Handle.addChild(this.Blade1);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		this.Grip.render(f5);
	}

	public void render(float f5) 
	{
		this.Grip.render(f5);
	}

	/**
	* This is a helper function from Tabula to set the rotation of model parts
	*/
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}


