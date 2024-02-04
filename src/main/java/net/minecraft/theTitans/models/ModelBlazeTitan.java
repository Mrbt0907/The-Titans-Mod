package net.minecraft.theTitans.models;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityBlazeTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.client.Animator;
@SideOnly(Side.CLIENT)
public class ModelBlazeTitan
extends ModelBase
{
	private ModelRenderer[] blazeSticks = new ModelRenderer[12];
	private ModelRenderer blazeHead;
	@SuppressWarnings("unused")
	private Animator animator;
	public ModelBlazeTitan()
	{
		this(0.0F);
	}

	public ModelBlazeTitan(float p_i1147_1_)
	{
		for (int i = 0; i < this.blazeSticks.length; i++)
		{
			this.blazeSticks[i] = new ModelRenderer(this, 0, 16);
			this.blazeSticks[i].addBox(-1.0F, -4.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		}

		this.blazeHead = new ModelRenderer(this, 0, 0);
		this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, p_i1147_1_);
		this.animator = new Animator(this);
	}

	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	{
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		this.blazeHead.render(p_78088_7_);
		for (int i = 0; i < this.blazeSticks.length; i++)
		{
			this.blazeSticks[i].render(p_78088_7_);
		}
	}

	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		EntityBlazeTitan entitytitan = (EntityBlazeTitan)p_78087_7_;
		float f6 = p_78087_3_ * 3.1415927F * -0.008F;
		int i;
		for (i = 0; i < 4; i++)
		{
			this.blazeSticks[i].rotationPointY = (4.0F + MathHelper.cos((i * 2 + p_78087_3_) * 0.03F));
			this.blazeSticks[i].rotationPointX = (MathHelper.cos(f6) * 10.0F);
			this.blazeSticks[i].rotationPointZ = (MathHelper.sin(f6) * 10.0F);
			if (entitytitan.func_70845_n())
			{
				this.blazeSticks[i].rotateAngleX = MathHelper.cos(p_78087_3_ * 0.1F) * (float)Math.PI;
			}

			else
			{
				this.blazeSticks[i].rotateAngleX = 0F;
			}

			f6 += 1.5F;
		}

		f6 = 0.7853982F + p_78087_3_ * 3.1415927F * 0.005F;
		for (i = 4; i < 8; i++)
		{
			this.blazeSticks[i].rotationPointY = (10.0F + MathHelper.cos((i * 3 + p_78087_3_) * 0.05F));
			this.blazeSticks[i].rotationPointX = (MathHelper.cos(f6) * 7.0F);
			this.blazeSticks[i].rotationPointZ = (MathHelper.sin(f6) * 7.0F);
			f6 += 1.5F;
		}

		f6 = 0.47123894F + p_78087_3_ * 3.1415927F * -0.003F;
		for (i = 8; i < 12; i++)
		{
			this.blazeSticks[i].rotationPointY = (17.0F + MathHelper.cos((i * 1.5F + p_78087_3_) * 0.02F));
			this.blazeSticks[i].rotationPointX = (MathHelper.cos(f6) * 4.0F);
			this.blazeSticks[i].rotationPointZ = (MathHelper.sin(f6) * 4.0F);
			f6 += 1.5F;
		}

		this.blazeHead.rotateAngleY = (p_78087_4_ / 57.295776F);
		this.blazeHead.rotateAngleX = (p_78087_5_ / 57.295776F);
		this.blazeHead.rotationPointY = 0F;
	}
}


