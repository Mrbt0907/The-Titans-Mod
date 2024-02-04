package net.minecraft.theTitans.models;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
@SideOnly(Side.CLIENT)
public class ModelSlimeTitan
extends ModelBase
{
	ModelRenderer slimeBodies;
	ModelRenderer slimeRightEye;
	ModelRenderer slimeLeftEye;
	ModelRenderer slimeMouth;
	public ModelSlimeTitan(int p_i1157_1_)
	{
		this.slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
		this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
		if (p_i1157_1_ > 0)
		{
			this.slimeBodies = new ModelRenderer(this, 0, p_i1157_1_);
			this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
			this.slimeRightEye = new ModelRenderer(this, 32, 0);
			this.slimeRightEye.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2);
			this.slimeRightEye.setRotationPoint(-2.25F, 19.0F, -2.5F);
			this.slimeLeftEye = new ModelRenderer(this, 32, 4);
			this.slimeLeftEye.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2);
			this.slimeLeftEye.setRotationPoint(2.25F, 19.0F, -2.5F);
			this.slimeMouth = new ModelRenderer(this, 32, 8);
			this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
		}
	}

	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	{
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		this.slimeBodies.render(p_78088_7_);
		if (this.slimeRightEye != null)
		{
			this.slimeRightEye.render(p_78088_7_);
			this.slimeLeftEye.render(p_78088_7_);
			this.slimeMouth.render(p_78088_7_);
		}
	}

	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		if (this.slimeRightEye != null)
		{
			this.slimeRightEye.rotateAngleY = (p_78087_4_ / 57.295776F);
			this.slimeRightEye.rotateAngleX = (p_78087_5_ / 57.295776F);
		}

		if (this.slimeLeftEye != null)
		{
			this.slimeLeftEye.rotateAngleY = (p_78087_4_ / 57.295776F);
			this.slimeLeftEye.rotateAngleX = (p_78087_5_ / 57.295776F);
		}
	}
}


