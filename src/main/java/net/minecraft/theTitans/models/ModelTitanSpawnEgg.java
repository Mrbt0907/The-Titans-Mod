package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
public class ModelTitanSpawnEgg
extends ModelBase
{
	public ModelRenderer EggCore;
	public ModelRenderer EggBottom;
	public ModelRenderer EggSide1;
	public ModelRenderer EggSide2;
	public ModelRenderer EggSide3;
	public ModelRenderer EggSide4;
	public ModelRenderer EggTop;
	public ModelRenderer Fire;
	public ModelRenderer Rod1;
	public ModelRenderer Rod2;
	public ModelRenderer Rod3;
	public ModelRenderer Rod4;
	public ModelRenderer Item;
	public ModelRenderer Fuzz;
	public ModelRenderer Horn1;
	public ModelRenderer Horn11;
	public ModelRenderer Horn2;
	public ModelRenderer Horn22;
	public ModelRenderer EggTip;
	public ModelTitanSpawnEgg()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.EggBottom = new ModelRenderer(this, 16, 0);
		this.EggBottom.setRotationPoint(0.0F, 2.5F, 0.0F);
		this.EggBottom.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
		this.EggSide2 = new ModelRenderer(this, 10, 12);
		this.EggSide2.setRotationPoint(-2.5F, 0.0F, 0.0F);
		this.EggSide2.addBox(-1.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
		this.Horn2 = new ModelRenderer(this, 10, 4);
		this.Horn2.setRotationPoint(-6.0F, 0.0F, 0.0F);
		this.Horn2.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.EggSide3 = new ModelRenderer(this, 0, 0);
		this.EggSide3.setRotationPoint(0.0F, 0.0F, 2.5F);
		this.EggSide3.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 1, 0.0F);
		this.Rod4 = new ModelRenderer(this, 0, 56);
		this.Rod4.setRotationPoint(5.0F, 0.0F, -5.0F);
		this.Rod4.addBox(-0.5F, -3.5F, -0.5F, 1, 7, 1, 0.0F);
		this.EggTip = new ModelRenderer(this, 50, 0);
		this.EggTip.setRotationPoint(0.0F, -3.0F, 0.0F);
		this.EggTip.addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
		this.Rod3 = new ModelRenderer(this, 0, 56);
		this.Rod3.setRotationPoint(-5.0F, 0.0F, -5.0F);
		this.Rod3.addBox(-0.5F, -3.5F, -0.5F, 1, 7, 1, 0.0F);
		this.Horn22 = new ModelRenderer(this, 10, 6);
		this.Horn22.setRotationPoint(-5.5F, -0.5F, 0.0F);
		this.Horn22.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.EggSide1 = new ModelRenderer(this, 0, 12);
		this.EggSide1.setRotationPoint(2.5F, 0.0F, 0.0F);
		this.EggSide1.addBox(0.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
		this.Fire = new ModelRenderer(this, 19, 49);
		this.Fire.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Fire.addBox(-3.5F, -3.0F, -4.0F, 7, 7, 8, 0.0F);
		setRotateAngle(this.Fire, 0.0F, 0.7853982F, 0.0F);
		this.Horn1 = new ModelRenderer(this, 10, 4);
		this.Horn1.setRotationPoint(3.5F, 0.0F, 0.0F);
		this.Horn1.addBox(0.0F, -0.5F, -0.5F, 3, 1, 1, 0.0F);
		this.Item = new ModelRenderer(this, 0, 16);
		this.Item.setRotationPoint(-4.0F, 0.0F, 0.0F);
		this.Item.addBox(0.0F, -4.0F, -4.0F, 0, 4, 4, 0.0F);
		setRotateAngle(this.Item, 0.0F, 0.5235988F, 0.0F);
		this.EggSide4 = new ModelRenderer(this, 0, 6);
		this.EggSide4.setRotationPoint(0.0F, 0.0F, -2.5F);
		this.EggSide4.addBox(-1.5F, -1.5F, -1.0F, 3, 3, 1, 0.0F);
		this.Fuzz = new ModelRenderer(this, 44, 4);
		this.Fuzz.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Fuzz.addBox(-5.0F, -5.5F, 0.0F, 10, 7, 0, 0.0F);
		this.Rod1 = new ModelRenderer(this, 0, 56);
		this.Rod1.setRotationPoint(5.0F, 0.0F, 5.0F);
		this.Rod1.addBox(-0.5F, -3.5F, -0.5F, 1, 7, 1, 0.0F);
		this.Horn11 = new ModelRenderer(this, 10, 6);
		this.Horn11.setRotationPoint(6.0F, -0.5F, 0.0F);
		this.Horn11.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.EggTop = new ModelRenderer(this, 40, 0);
		this.EggTop.setRotationPoint(0.0F, -2.5F, 0.0F);
		this.EggTop.addBox(-1.5F, -1.0F, -1.5F, 3, 1, 3, 0.0F);
		this.EggCore = new ModelRenderer(this, 24, 0);
		this.EggCore.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.EggCore.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0.0F);
		this.Rod2 = new ModelRenderer(this, 0, 56);
		this.Rod2.setRotationPoint(-5.0F, 0.0F, 5.0F);
		this.Rod2.addBox(-0.5F, -3.5F, -0.5F, 1, 7, 1, 0.0F);
	}

	public void render(float f5, int eggtype)
	{
		this.EggCore.render(f5);
		this.EggBottom.render(f5);
		this.EggSide1.render(f5);
		this.EggSide2.render(f5);
		this.EggSide3.render(f5);
		this.EggSide4.render(f5);
		this.EggTop.render(f5);
		this.EggTip.render(f5);
		if (eggtype == 1)
		{
			this.Fire.rotateAngleY += f5;
		}

		if (eggtype == 3)
		{
			this.Fuzz.render(f5);
		}

		if ((eggtype == 1) || (eggtype == 2))
		{
			this.Fire.render(f5);
		}

		if (eggtype == 2)
		{
			this.Rod1.render(f5);
			this.Rod2.render(f5);
			this.Rod3.render(f5);
			this.Rod4.render(f5);
		}

		if (eggtype == 4)
		{
			this.Item.render(f5);
		}

		if (eggtype == 5)
		{
			this.Horn1.render(f5);
			this.Horn2.render(f5);
			this.Horn11.render(f5);
			this.Horn22.render(f5);
		}
	}

	public void render(int eggtype, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.EggCore.render(f5);
		this.EggBottom.render(f5);
		this.EggSide1.render(f5);
		this.EggSide2.render(f5);
		this.EggSide3.render(f5);
		this.EggSide4.render(f5);
		this.EggTop.render(f5);
		this.EggTip.render(f5);
		if (eggtype == 1)
		{
			this.Fire.rotateAngleY = -(f4 * 0.25F);
		}

		if (eggtype == 3)
		{
			this.Fuzz.render(f5);
		}

		if ((eggtype == 1) || (eggtype == 2))
		{
			this.Fire.render(f5);
		}

		if (eggtype == 2)
		{
			this.Rod1.render(f5);
			this.Rod2.render(f5);
			this.Rod3.render(f5);
			this.Rod4.render(f5);
		}

		if (eggtype == 4)
		{
			this.Item.render(f5);
		}

		if (eggtype == 5)
		{
			this.Horn1.render(f5);
			this.Horn2.render(f5);
			this.Horn11.render(f5);
			this.Horn22.render(f5);
		}
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}


