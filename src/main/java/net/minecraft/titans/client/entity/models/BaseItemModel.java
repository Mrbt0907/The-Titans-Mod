package net.minecraft.titans.client.entity.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class BaseItemModel extends ModelBase{

	public void render() {}
	
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) 
	{
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
