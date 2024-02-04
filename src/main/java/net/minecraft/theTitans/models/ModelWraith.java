package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
public class ModelWraith extends ModelZombie
{
	public ModelRenderer bipedLowerBody;
	public ModelWraith()
	{
		this(0.0F);
	}

	public ModelWraith(float p_i1148_1_)
	{
		this(p_i1148_1_, 0.0F, 64, 32);
	}

	public ModelWraith(float p_i1149_1_, float p_i1149_2_, int p_i1149_3_, int p_i1149_4_)
	{
		super(p_i1149_1_, 0.0F, 64, 64);
		this.bipedLowerBody = new ModelRenderer(this, 16, 32);
		this.bipedLowerBody.addBox(-4F, 0F, -2F, 8, 12, 4);
		this.bipedLowerBody.setRotationPoint(0F, 10F, 0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	{
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		this.bipedHead.render(p_78088_7_);
		this.bipedBody.render(p_78088_7_);
		this.bipedLowerBody.render(p_78088_7_);
		this.bipedRightArm.render(p_78088_7_);
		this.bipedLeftArm.render(p_78088_7_);
		this.bipedHeadwear.render(p_78088_7_);
	}

	/**
	* Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	* and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	* "far" arms and legs can swing at most.
	*/
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		float f6 = MathHelper.cos(p_78087_3_ * 0.1F);
		this.bipedBody.rotateAngleX = (0.0165F + 0.05F * f6) * (float)Math.PI;
		this.bipedLowerBody.setRotationPoint(0.0F, 0.0F + MathHelper.cos(this.bipedBody.rotateAngleX) * 11.0F, 0.0F + MathHelper.sin(this.bipedBody.rotateAngleX) * 11.0F);
		this.bipedLowerBody.rotateAngleX = (0.065F + 0.1F * f6) * (float)Math.PI;
		this.bipedLowerBody.rotateAngleY = 0.0F;
		this.bipedLowerBody.rotateAngleZ = 0.0F;
	}
}


