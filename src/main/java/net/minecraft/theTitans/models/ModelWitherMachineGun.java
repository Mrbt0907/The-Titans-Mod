package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
public class ModelWitherMachineGun
extends ModelBase
{
	public ModelRenderer pole;
	public ModelRenderer head2;
	public ModelRenderer support;
	public ModelRenderer base;
	public ModelRenderer head1;
	public ModelWitherMachineGun()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.head1 = new ModelRenderer(this, 32, 0);
		this.head1.setRotationPoint(-10.0F, 14.0F, 0.0F);
		this.head1.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
		this.base = new ModelRenderer(this, 0, 0);
		this.base.setRotationPoint(0.0F, 26.0F, 0.0F);
		this.base.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
		this.head2 = new ModelRenderer(this, 32, 0);
		this.head2.setRotationPoint(10.0F, 14.0F, 0.0F);
		this.head2.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
		this.support = new ModelRenderer(this, 0, 16);
		this.support.setRotationPoint(0.0F, 13.0F, 0.0F);
		this.support.addBox(-10.0F, 0.0F, -1.5F, 20, 3, 3, 0.0F);
		this.pole = new ModelRenderer(this, 12, 22);
		this.pole.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.pole.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.head1.render(f5);
		this.base.render(f5);
		this.head2.render(f5);
		this.support.render(f5);
		this.pole.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.head1.rotateAngleX = (f4 / 57.295776F);
		this.head2.rotateAngleX = (f4 / 57.295776F);
		this.head1.rotateAngleY = (f3 / 57.295776F);
		this.head2.rotateAngleY = (f3 / 57.295776F);
		this.support.rotateAngleX = (f4 / 57.295776F);
		this.pole.rotateAngleY = (f3 / 57.295776F / 2.0F);
	}
}


