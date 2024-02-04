package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelRenderer;
public class ModelWitherMortar extends net.minecraft.client.model.ModelBase
{
	public ModelRenderer support;
	public ModelRenderer Head;
	public ModelRenderer backTripodLeg;
	public ModelRenderer rightFrontTripodLeg;
	public ModelRenderer leftFrontTripodLeg;
	public ModelRenderer backTripodLegTip;
	public ModelRenderer handle;
	public ModelWitherMortar()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.handle = new ModelRenderer(this, 24, 22);
		this.handle.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.handle.addBox(-5.5F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.Head.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
		this.backTripodLeg = new ModelRenderer(this, 0, 22);
		this.backTripodLeg.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.backTripodLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
		setRotateAngle(this.backTripodLeg, 1.3089969F, 0.0F, 0.0F);
		this.support = new ModelRenderer(this, 0, 22);
		this.support.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.support.addBox(-1.5F, 0.0F, -1.5F, 3, 10, 3, 0.0F);
		this.backTripodLegTip = new ModelRenderer(this, 12, 22);
		this.backTripodLegTip.setRotationPoint(0.0F, 18.0F, 9.0F);
		this.backTripodLegTip.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
		this.rightFrontTripodLeg = new ModelRenderer(this, 24, 22);
		this.rightFrontTripodLeg.mirror = true;
		this.rightFrontTripodLeg.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.rightFrontTripodLeg.addBox(0.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
		setRotateAngle(this.rightFrontTripodLeg, -0.61086524F, 2.6179938F, -0.87266463F);
		this.leftFrontTripodLeg = new ModelRenderer(this, 24, 22);
		this.leftFrontTripodLeg.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.leftFrontTripodLeg.addBox(-11.0F, -1.0F, -1.0F, 11, 2, 2, 0.0F);
		setRotateAngle(this.leftFrontTripodLeg, -0.61086524F, -2.6179938F, 0.87266463F);
	}

	public void render(net.minecraft.entity.Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.handle.render(f5);
		this.Head.render(f5);
		this.backTripodLeg.render(f5);
		this.support.render(f5);
		this.backTripodLegTip.render(f5);
		this.rightFrontTripodLeg.render(f5);
		this.leftFrontTripodLeg.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, net.minecraft.entity.Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Head.rotateAngleY = (f3 / 57.295776F);
		this.Head.rotateAngleX = (f4 / 57.295776F);
	}
}


