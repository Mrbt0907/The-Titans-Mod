package net.minecraft.theTitans.models;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titanminion.EntitySkeletonMinion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
@SideOnly(Side.CLIENT)
public class ModelSkeletonLoyalist
extends ModelBiped
{
	public boolean raiseArms;
	public ModelSkeletonLoyalist()
	{
		this(0.0F, false);
	}

	public ModelSkeletonLoyalist(float p_i46303_1_, boolean p_i46303_2_)
	{
		super(p_i46303_1_, 0.0F, 64, 32);
		if (!p_i46303_2_)
		{
			this.bipedRightArm = new ModelRenderer(this, 40, 16);
			this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i46303_1_);
			this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
			this.bipedLeftArm = new ModelRenderer(this, 40, 16);
			this.bipedLeftArm.mirror = true;
			this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i46303_1_);
			this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
			this.bipedRightLeg = new ModelRenderer(this, 0, 16);
			this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i46303_1_);
			this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
			this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
			this.bipedLeftLeg.mirror = true;
			this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i46303_1_);
			this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		}
	}

	public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
	{
		EntitySkeletonMinion entityskeleton = (EntitySkeletonMinion)p_78086_1_;
		if (entityskeleton.ticksExisted % 1 == 0)
		{
			ItemStack itemstack = entityskeleton.getHeldItem();
			this.aimedBow = ((itemstack != null) && (itemstack.getItem() == Items.bow));
			this.raiseArms = ((entityskeleton.getAttackTarget() != null) && (entityskeleton.getDistanceSqToEntity(entityskeleton.getAttackTarget()) < 36.0D) && (((itemstack != null) && (itemstack.getItem() != Items.bow)) || (itemstack == null)));
		}

		super.setLivingAnimations(entityskeleton, p_78086_2_, p_78086_3_, p_78086_4_);
	}

	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		if (this.raiseArms)
		{
			float f6 = MathHelper.sin(this.onGround * 3.1415927F);
			float f7 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * 3.1415927F);
			this.bipedRightArm.rotateAngleZ = 0.0F;
			this.bipedLeftArm.rotateAngleZ = 0.0F;
			this.bipedRightArm.rotateAngleY = (-(0.1F - f6 * 0.6F));
			this.bipedLeftArm.rotateAngleY = (0.1F - f6 * 0.6F);
			this.bipedRightArm.rotateAngleX = -1.5707964F;
			this.bipedLeftArm.rotateAngleX = -1.5707964F;
			this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
			this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
			this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
			this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
		}
	}
}


