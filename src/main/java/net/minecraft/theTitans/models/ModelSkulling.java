package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
/**
* ModelHumanoidHead - Either Mojang or a mod author
* Created using Tabula 4.1.1
*/
public class ModelSkulling extends ModelBase 
{

	public ModelRenderer body;
	public ModelRenderer leftclaw;
	public ModelRenderer rightclaw;
	public ModelRenderer rightleg1;
	public ModelRenderer rightleg2;
	public ModelRenderer rightleg3;
	public ModelRenderer leftleg1;
	public ModelRenderer leftleg2;
	public ModelRenderer leftleg3;
	public ModelRenderer skull;
	public ModelRenderer lefteye;
	public ModelRenderer righteye;
	public ModelSkulling()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.leftleg1 = new ModelRenderer(this, 0, 16);
		this.leftleg1.setRotationPoint(1.0F, 21.0F, -2.0F);
		this.leftleg1.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.setRotateAngle(leftleg1, 0.0F, 0.5918411493512771F, 0.36425021489121656F);
		this.righteye = new ModelRenderer(this, 0, 0);
		this.righteye.setRotationPoint(-1.0F, 4.5F, -0.5F);
		this.righteye.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(righteye, 2.2689280275926285F, 0.0F, 0.0F);
		this.leftleg2 = new ModelRenderer(this, 0, 16);
		this.leftleg2.setRotationPoint(1.0F, 21.0F, -1.0F);
		this.leftleg2.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.setRotateAngle(leftleg2, 0.0F, 0.136659280431156F, 0.36425021489121656F);
		this.rightleg3 = new ModelRenderer(this, 0, 16);
		this.rightleg3.setRotationPoint(-1.0F, 21.0F, 0.0F);
		this.rightleg3.addBox(0.0F, -1.0F, 0.0F, 6, 1, 1, 0.0F);
		this.setRotateAngle(rightleg3, 0.0F, -0.4553564018453205F, 2.777342438698576F);
		this.lefteye = new ModelRenderer(this, 0, 0);
		this.lefteye.setRotationPoint(1.0F, 4.5F, -0.5F);
		this.lefteye.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(lefteye, 2.2689280275926285F, 0.0F, 0.0F);
		this.rightleg2 = new ModelRenderer(this, 0, 16);
		this.rightleg2.setRotationPoint(-1.0F, 21.0F, -1.6F);
		this.rightleg2.addBox(0.0F, -1.0F, 0.0F, 6, 1, 1, 0.0F);
		this.setRotateAngle(rightleg2, 0.0F, 0.136659280431156F, 2.777342438698576F);
		this.leftleg3 = new ModelRenderer(this, 0, 16);
		this.leftleg3.setRotationPoint(1.0F, 21.0F, 0.0F);
		this.leftleg3.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.setRotateAngle(leftleg3, 0.0F, -0.36425021489121656F, 0.36425021489121656F);
		this.rightclaw = new ModelRenderer(this, 0, 16);
		this.rightclaw.setRotationPoint(-0.7F, 21.0F, -2.7F);
		this.rightclaw.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.setRotateAngle(rightclaw, 0.0F, 2.231054382824351F, 0.0F);
		this.skull = new ModelRenderer(this, 0, 0);
		this.skull.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.skull.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.setRotateAngle(skull, 1.2292353921796064F, 0.0F, 0.0F);
		this.body = new ModelRenderer(this, 32, 0);
		this.body.setRotationPoint(0.0F, 20.0F, 0.0F);
		this.body.addBox(-2.0F, -3.0F, -1.0F, 4, 8, 4, 0.0F);
		this.setRotateAngle(body, -1.8668041679331349F, 0.0F, 0.0F);
		this.leftclaw = new ModelRenderer(this, 0, 16);
		this.leftclaw.setRotationPoint(0.0F, 21.0F, -3.5F);
		this.leftclaw.addBox(0.0F, 0.0F, 0.0F, 6, 1, 1, 0.0F);
		this.setRotateAngle(leftclaw, 0.0F, 0.7285004297824331F, 0.0F);
		this.rightleg1 = new ModelRenderer(this, 0, 16);
		this.rightleg1.setRotationPoint(-1.0F, 21.0F, -2.5F);
		this.rightleg1.addBox(0.0F, -1.0F, 0.0F, 6, 1, 1, 0.0F);
		this.setRotateAngle(rightleg1, 0.0F, 0.5918411493512771F, 2.777342438698576F);
		this.body.addChild(this.righteye);
		this.body.addChild(this.lefteye);
		this.body.addChild(this.skull);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		this.leftleg1.render(f5);
		this.leftleg2.render(f5);
		this.rightleg3.render(f5);
		this.rightleg2.render(f5);
		this.leftleg3.render(f5);
		this.rightclaw.render(f5);
		this.body.render(f5);
		this.leftclaw.render(f5);
		this.rightleg1.render(f5);
	}

	/**
	* This is a helper function from Tabula to set the rotation of model parts
	*/
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}


