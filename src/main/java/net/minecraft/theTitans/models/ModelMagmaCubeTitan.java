package net.minecraft.theTitans.models;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityMagmaCubeTitan;
@SideOnly(Side.CLIENT)
public class ModelMagmaCubeTitan
extends ModelBase
{
	ModelRenderer[] segments = new ModelRenderer[8];
	ModelRenderer core;
	public ModelMagmaCubeTitan()
	{
		for (int i = 0; i < this.segments.length; i++)
		{
			byte b0 = 0;
			int j = i;
			if (i == 2)
			{
				b0 = 24;
				j = 10;
			}

			else if (i == 3)
			{
				b0 = 24;
				j = 19;
			}

			this.segments[i] = new ModelRenderer(this, b0, j);
			this.segments[i].addBox(-4.0F, 16 + i, -4.0F, 8, 1, 8);
		}

		this.core = new ModelRenderer(this, 0, 16);
		this.core.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
	}

	public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
	{
		EntityMagmaCubeTitan entitymagmacube = (EntityMagmaCubeTitan)p_78086_1_;
		float f3 = entitymagmacube.prevSquishFactor + (entitymagmacube.squishFactor - entitymagmacube.prevSquishFactor) * p_78086_4_;
		if (f3 < 0.0F)
		{
			f3 = 0.0F;
		}

		for (int i = 0; i < this.segments.length; i++)
		{
			this.segments[i].rotationPointY = (-(4 - i) * f3 * 1.7F);
		}
	}

	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	{
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		this.core.render(p_78088_7_);
		for (int i = 0; i < this.segments.length; i++)
		{
			this.segments[i].render(p_78088_7_);
		}
	}
}


