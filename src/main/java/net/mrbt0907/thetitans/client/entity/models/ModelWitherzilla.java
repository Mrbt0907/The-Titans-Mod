package net.mrbt0907.thetitans.client.entity.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mrbt0907.thetitans.entity.god.EntityWitherzilla;

@SideOnly(Side.CLIENT)
public class ModelWitherzilla
extends ModelBase
{
	private ModelRenderer[] spine;
	public ModelRenderer[] heads;
	public ModelWitherzilla()
	{
		textureWidth = 64;
		textureHeight = 64;
		spine = new ModelRenderer[3];
		spine[0] = new ModelRenderer(this, 0, 16);
		spine[0].addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3);
		spine[1] = new ModelRenderer(this).setTextureSize(textureWidth, textureHeight);
		spine[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
		spine[1].setTextureOffset(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
		spine[1].setTextureOffset(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2);
		spine[1].setTextureOffset(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2);
		spine[1].setTextureOffset(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2);
		spine[2] = new ModelRenderer(this, 12, 22);
		spine[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 3);
		heads = new ModelRenderer[3];
		heads[0] = new ModelRenderer(this, 0, 0);
		heads[0].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
		heads[1] = new ModelRenderer(this, 32, 0);
		heads[1].addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
		heads[2] = new ModelRenderer(this, 32, 0);
		heads[2].addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
	}

	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		ModelRenderer[] amodelrenderer = heads;
		int i = amodelrenderer.length;
		for (int j = 0; j < i; j++)
		{
			ModelRenderer modelrenderer = amodelrenderer[j];
			modelrenderer.render(scale);
		}

		amodelrenderer = spine;
		i = amodelrenderer.length;
		for (int j = 0; j < i; j++)
		{
			ModelRenderer modelrenderer = amodelrenderer[j];
			modelrenderer.render(scale);
		}
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
	{
		EntityWitherzilla entitytitan = (EntityWitherzilla)entity;
		if (entitytitan.isEntityAlive())
		{
			heads[0].setRotationPoint(0.0F, 3.0F, 0.0F);
			heads[1].setRotationPoint(-10.0F, 7.0F, 0.0F);
			heads[2].setRotationPoint(10.0F, 7.0F, 0.0F);
			spine[0].setRotationPoint(0.0F, 3.0F, 0.0F);
			spine[1].setRotationPoint(-2.0F, 9.9F, -0.5F);
			float f6 = MathHelper.cos(ageInTicks * 0.05F);
			spine[1].rotateAngleX = ((0.065F + 0.025F * f6) * 3.1415927F);
			spine[2].setRotationPoint(-2.0F, 9.9F + MathHelper.cos(spine[1].rotateAngleX) * 10.0F, -0.5F + MathHelper.sin(spine[1].rotateAngleX) * 10.0F);
			f6 = MathHelper.cos(ageInTicks * 0.05F - 1F);
			spine[2].rotateAngleX = ((0.265F + 0.1F * f6) * 3.1415927F);
			heads[0].rotateAngleY = (netHeadYaw / 57.295776F);
			heads[0].rotateAngleX = (headPitch / 15.295776F);
		}

		else
		{
			heads[0].setRotationPoint((float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F));
			heads[1].setRotationPoint((float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F));
			heads[2].setRotationPoint((float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F));
			spine[0].setRotationPoint((float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F));
			spine[1].setRotationPoint((float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F));
			spine[2].setRotationPoint((float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F), (float)(entitytitan.getRNG().nextGaussian() * 2F));
		}
	}

	public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
	{
		EntityWitherzilla entitywither = (EntityWitherzilla)entity;
		for (int i = 1; i < 3; i++)
		{
			heads[i].rotateAngleY = ((entitywither.getHeadPitch(i - 1) - entity.renderYawOffset) * 0.017453292F);
			heads[i].rotateAngleX = (entitywither.getHeadRotation(i - 1) * 0.017453292F);
		}
	}
}


