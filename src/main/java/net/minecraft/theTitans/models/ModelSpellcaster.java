package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.other.EntityMagicUser;
import net.minecraft.util.MathHelper;
public class ModelSpellcaster extends ModelBiped
{
	public boolean castingSpell;
	public ModelSpellcaster()
	{
		super(0.0F);
	}

	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		EntityMagicUser entity = (EntityMagicUser)p_78087_7_;
		castingSpell = entity.isCastingSpell();
		if (entity.getMobType() == 1)
		{
			aimedBow = true;
		}

		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		if (entity.getMobType() == 0)
		{
			float f6 = MathHelper.sin(this.onGround * (float)Math.PI);
			float f7 = MathHelper.sin((1.0F - (1.0F - this.onGround) * (1.0F - this.onGround)) * (float)Math.PI);
			this.bipedRightArm.rotateAngleZ = 0.0F;
			this.bipedLeftArm.rotateAngleZ = 0.0F;
			this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
			this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
			this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F);
			this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F);
			this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
			this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
			this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
			this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
		}

		if (castingSpell)
		{
			MathHelper.cos(p_78087_3_ * 0.1F);
			this.bipedRightArm.rotateAngleX = MathHelper.cos(p_78087_3_ * 0.6662F) * 0.25F;
			this.bipedLeftArm.rotateAngleX = MathHelper.cos(p_78087_3_ * 0.6662F) * 0.25F;
			this.bipedRightArm.rotateAngleZ = 2.3561945F;
			this.bipedLeftArm.rotateAngleZ = -2.3561945F;
			this.bipedRightArm.rotateAngleY = 0.0F;
			this.bipedLeftArm.rotateAngleY = 0.0F;
		}
	}
}


