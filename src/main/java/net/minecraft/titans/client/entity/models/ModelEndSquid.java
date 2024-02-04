package net.minecraft.titans.client.entity.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.titans.entity.animal.EntityEndSquid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEndSquid extends ModelBase 
{
    /** The squid's body */
    ModelRenderer body;
    /** The squid's tentacles */
    ModelRenderer[] tentacles = new ModelRenderer[8];
    ModelRenderer[] barnacles = new ModelRenderer[10];

	public ModelEndSquid() 
	{
		textureWidth = 128;
		textureHeight = 128;

        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
        this.body.rotationPointY += 8.0F;
        this.barnacles[0] = new ModelRenderer(this, 48, 2);
        this.barnacles[1] = new ModelRenderer(this, 48, 2);
        this.barnacles[2] = new ModelRenderer(this, 48, 2);
        this.barnacles[3] = new ModelRenderer(this, 48, 2);
        this.barnacles[4] = new ModelRenderer(this, 20, 48);
        this.barnacles[5] = new ModelRenderer(this, 20, 48);
        this.barnacles[6] = new ModelRenderer(this, 20, 48);
        this.barnacles[7] = new ModelRenderer(this, 20, 48);
        this.barnacles[8] = new ModelRenderer(this, 36, 0);
        this.barnacles[9] = new ModelRenderer(this, 36, 0);

        barnacles[0].cubeList.add(new ModelBox(barnacles[0], 48, 2, -8.0F, -5.0F, 0.0F, 2, 4, 4, 0.0F, false));
        barnacles[1].cubeList.add(new ModelBox(barnacles[1], 48, 2, -8.0F, 2.0F, -5.0F, 2, 4, 4, 0.0F, false));
        barnacles[2].cubeList.add(new ModelBox(barnacles[2], 48, 2, 6.0F, -7.0F, -1.0F, 2, 4, 4, 0.0F, false));
        barnacles[3].cubeList.add(new ModelBox(barnacles[3], 48, 2, 6.0F, 0.0F, -4.0F, 2, 4, 4, 0.0F, false));
        barnacles[4].cubeList.add(new ModelBox(barnacles[4], 20, 48, -3.0F, 0.0F, 6.0F, 4, 4, 2, 0.0F, false));
        barnacles[5].cubeList.add(new ModelBox(barnacles[5], 20, 48, 1.0F, -6.0F, 6.0F, 4, 4, 2, 0.0F, false));
        barnacles[6].cubeList.add(new ModelBox(barnacles[6], 20, 48, 1.0F, -6.0F, -8.0F, 4, 4, 2, 0.0F, false));
        barnacles[7].cubeList.add(new ModelBox(barnacles[7], 20, 48, -4.0F, 2.0F, -8.0F, 4, 4, 2, 0.0F, false));
        barnacles[8].cubeList.add(new ModelBox(barnacles[8], 36, 0, -4.0F, -10.0F, -5.0F, 4, 2, 4, 0.0F, false));
        barnacles[9].cubeList.add(new ModelBox(barnacles[9], 36, 0, 0.0F, -10.0F, 1.0F, 4, 2, 4, 0.0F, false));

        for (int j = 0; j < this.barnacles.length; ++j)
        {
            this.barnacles[j].rotationPointY += 8.0F;
        }
        
        this.tentacles[0] = new ModelRenderer(this, 0, 48);
        this.tentacles[1] = new ModelRenderer(this, 46, 46);
        this.tentacles[2] = new ModelRenderer(this, 40, 28);
        this.tentacles[3] = new ModelRenderer(this, 32, 28);
        this.tentacles[4] = new ModelRenderer(this, 24, 28);
        this.tentacles[5] = new ModelRenderer(this, 16, 28);
        this.tentacles[6] = new ModelRenderer(this, 8, 28);
        this.tentacles[7] = new ModelRenderer(this, 0, 28);
        
        for (int j = 0; j < this.tentacles.length; ++j)
        {
            double d0 = (double)j * Math.PI * 2.0D / (double)this.tentacles.length;
            float f = (float)Math.cos(d0) * 5.0F;
            float f1 = (float)Math.sin(d0) * 5.0F;
            this.tentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
            this.tentacles[j].rotationPointX = f;
            this.tentacles[j].rotationPointZ = f1;
            this.tentacles[j].rotationPointY = 15.0F;
            d0 = (double)j * Math.PI * -2.0D / (double)this.tentacles.length + (Math.PI / 2D);
            this.tentacles[j].rotateAngleY = (float)d0;
        }
	}

	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) 
	{
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		body.render(scale);

        for (ModelRenderer modelrenderer : this.tentacles)
        {
            modelrenderer.render(scale);
        }
        
        EntityEndSquid squid = (EntityEndSquid)entityIn;
        
        if (squid.getBarnacles() > 0)
        	for (int j = 0; j < squid.getBarnacles(); ++j)
        	{
        		barnacles[j].render(scale);
        	}
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        for (ModelRenderer modelrenderer : this.tentacles)
        {
            modelrenderer.rotateAngleX = ageInTicks;
        }
    }
}