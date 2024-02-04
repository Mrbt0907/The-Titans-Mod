package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
/**
* SkullBoss - Undefined
* Created using Tabula 4.1.1
*/
public class ModelSkullBoss extends ModelBase 
{

	public ModelRenderer body;
	public ModelRenderer skull;
	public ModelRenderer head;
	public ModelRenderer leftClaw1;
	public ModelRenderer rightClaw1;
	public ModelRenderer leftUpperLeg1;
	public ModelRenderer leftUpperLeg2;
	public ModelRenderer leftUpperLeg3;
	public ModelRenderer rightUpperLeg1;
	public ModelRenderer rightUpperLeg2;
	public ModelRenderer rightUpperLeg3;
	public ModelRenderer rightEye;
	public ModelRenderer leftEye;
	public ModelRenderer leftClaw2;
	public ModelRenderer upperLeftClaw;
	public ModelRenderer lowerLeftClaw;
	public ModelRenderer rightClaw2;
	public ModelRenderer upperRightClaw;
	public ModelRenderer lowerRightClaw;
	public ModelRenderer leftLowerLeg1;
	public ModelRenderer leftFoot1;
	public ModelRenderer leftLowerLeg2;
	public ModelRenderer leftFoot2;
	public ModelRenderer leftLowerLeg3;
	public ModelRenderer leftFoot3;
	public ModelRenderer rightLowerLeg1;
	public ModelRenderer rightFoot1;
	public ModelRenderer rightLowerLeg2;
	public ModelRenderer rightFoot2;
	public ModelRenderer rightLowerLeg3;
	public ModelRenderer rightFoot3;
	public ModelSkullBoss()
	{
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.rightUpperLeg2 = new ModelRenderer(this, 0, 39);
		this.rightUpperLeg2.setRotationPoint(-5.5F, 2.0F, -4.0F);
		this.rightUpperLeg2.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(rightUpperLeg2, 1.0471975511965976F, 1.5707963267948966F, 0.0F);
		this.lowerLeftClaw = new ModelRenderer(this, 24, 32);
		this.lowerLeftClaw.setRotationPoint(0.0F, -10.0F, -1.0F);
		this.lowerLeftClaw.addBox(-0.5F, -4.0F, -1.0F, 1, 4, 2, 0.0F);
		this.rightLowerLeg3 = new ModelRenderer(this, 12, 39);
		this.rightLowerLeg3.mirror = true;
		this.rightLowerLeg3.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.rightLowerLeg3.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(rightLowerLeg3, 1.0471975511965976F, 0.17453292519943295F, 0.0F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 15.0F, 0.0F);
		this.body.addBox(-6.0F, -5.0F, -10.0F, 12, 10, 16, 0.0F);
		this.leftFoot3 = new ModelRenderer(this, 24, 39);
		this.leftFoot3.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.leftFoot3.addBox(-1.0F, -8.0F, -1.0F, 2, 8, 2, 0.0F);
		this.setRotateAngle(leftFoot3, 1.0471975511965976F, -0.17453292519943295F, 0.0F);
		this.upperLeftClaw = new ModelRenderer(this, 24, 26);
		this.upperLeftClaw.setRotationPoint(0.0F, -10.0F, 1.0F);
		this.upperLeftClaw.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(upperLeftClaw, 0.17453292519943295F, 0.0F, 0.0F);
		this.head = new ModelRenderer(this, 40, 0);
		this.head.setRotationPoint(0.0F, -2.0F, -8.0F);
		this.head.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F);
		this.setRotateAngle(head, 0.5235987755982988F, 0.0F, 0.0F);
		this.rightUpperLeg1 = new ModelRenderer(this, 0, 39);
		this.rightUpperLeg1.mirror = true;
		this.rightUpperLeg1.setRotationPoint(-5.5F, 2.0F, -7.0F);
		this.rightUpperLeg1.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(rightUpperLeg1, 1.0471975511965976F, 1.0471975511965976F, 0.0F);
		this.rightEye = new ModelRenderer(this, 0, 0);
		this.rightEye.mirror = true;
		this.rightEye.setRotationPoint(-1.5F, -5.5F, 0.0F);
		this.rightEye.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(rightEye, -0.5235987755982988F, 0.0F, 0.0F);
		this.rightFoot2 = new ModelRenderer(this, 24, 39);
		this.rightFoot2.mirror = true;
		this.rightFoot2.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.rightFoot2.addBox(-1.0F, -8.0F, -1.0F, 2, 8, 2, 0.0F);
		this.setRotateAngle(rightFoot2, 1.0471975511965976F, 0.17453292519943295F, 0.0F);
		this.leftUpperLeg3 = new ModelRenderer(this, 0, 39);
		this.leftUpperLeg3.setRotationPoint(5.5F, 2.0F, -1.0F);
		this.leftUpperLeg3.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(leftUpperLeg3, 1.0471975511965976F, -2.0943951023931953F, 0.0F);
		this.leftLowerLeg1 = new ModelRenderer(this, 12, 39);
		this.leftLowerLeg1.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.leftLowerLeg1.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(leftLowerLeg1, 1.0471975511965976F, -0.17453292519943295F, 0.0F);
		this.leftUpperLeg2 = new ModelRenderer(this, 0, 39);
		this.leftUpperLeg2.setRotationPoint(5.5F, 2.0F, -4.0F);
		this.leftUpperLeg2.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(leftUpperLeg2, 1.0471975511965976F, -1.5707963267948966F, 0.0F);
		this.lowerRightClaw = new ModelRenderer(this, 24, 32);
		this.lowerRightClaw.mirror = true;
		this.lowerRightClaw.setRotationPoint(0.0F, -10.0F, -1.0F);
		this.lowerRightClaw.addBox(-0.5F, -4.0F, -1.0F, 1, 4, 2, 0.0F);
		this.leftClaw1 = new ModelRenderer(this, 0, 26);
		this.leftClaw1.setRotationPoint(5.0F, 1.0F, -10.0F);
		this.leftClaw1.addBox(-1.5F, -10.0F, -1.5F, 3, 10, 3, 0.0F);
		this.setRotateAngle(leftClaw1, 1.0471975511965976F, -0.7853981633974483F, 0.0F);
		this.leftFoot1 = new ModelRenderer(this, 24, 39);
		this.leftFoot1.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.leftFoot1.addBox(-1.0F, -8.0F, -1.0F, 2, 8, 2, 0.0F);
		this.setRotateAngle(leftFoot1, 1.0471975511965976F, -0.17453292519943295F, 0.0F);
		this.leftFoot2 = new ModelRenderer(this, 24, 39);
		this.leftFoot2.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.leftFoot2.addBox(-1.0F, -8.0F, -1.0F, 2, 8, 2, 0.0F);
		this.setRotateAngle(leftFoot2, 1.0471975511965976F, -0.17453292519943295F, 0.0F);
		this.upperRightClaw = new ModelRenderer(this, 24, 26);
		this.upperRightClaw.mirror = true;
		this.upperRightClaw.setRotationPoint(0.0F, -10.0F, 1.0F);
		this.upperRightClaw.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
		this.setRotateAngle(upperRightClaw, 0.17453292519943295F, 0.0F, 0.0F);
		this.leftLowerLeg2 = new ModelRenderer(this, 12, 39);
		this.leftLowerLeg2.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.leftLowerLeg2.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(leftLowerLeg2, 1.0471975511965976F, -0.17453292519943295F, 0.0F);
		this.rightFoot1 = new ModelRenderer(this, 24, 39);
		this.rightFoot1.mirror = true;
		this.rightFoot1.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.rightFoot1.addBox(-1.0F, -8.0F, -1.0F, 2, 8, 2, 0.0F);
		this.setRotateAngle(rightFoot1, 1.0471975511965976F, 0.17453292519943295F, 0.0F);
		this.rightLowerLeg2 = new ModelRenderer(this, 12, 39);
		this.rightLowerLeg2.mirror = true;
		this.rightLowerLeg2.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.rightLowerLeg2.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(rightLowerLeg2, 1.0471975511965976F, 0.17453292519943295F, 0.0F);
		this.leftUpperLeg1 = new ModelRenderer(this, 0, 39);
		this.leftUpperLeg1.setRotationPoint(5.5F, 2.0F, -7.0F);
		this.leftUpperLeg1.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(leftUpperLeg1, 1.0471975511965976F, -1.0471975511965976F, 0.0F);
		this.leftEye = new ModelRenderer(this, 0, 0);
		this.leftEye.setRotationPoint(1.5F, -5.5F, 0.0F);
		this.leftEye.addBox(-0.5F, -4.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(leftEye, -0.5235987755982988F, 0.0F, 0.0F);
		this.leftLowerLeg3 = new ModelRenderer(this, 12, 39);
		this.leftLowerLeg3.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.leftLowerLeg3.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(leftLowerLeg3, 1.0471975511965976F, -0.17453292519943295F, 0.0F);
		this.rightClaw2 = new ModelRenderer(this, 12, 26);
		this.rightClaw2.mirror = true;
		this.rightClaw2.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.rightClaw2.addBox(-1.5F, -10.0F, -1.5F, 3, 10, 3, 0.0F);
		this.setRotateAngle(rightClaw2, 1.0471975511965976F, -0.5235987755982988F, 0.5235987755982988F);
		this.leftClaw2 = new ModelRenderer(this, 12, 26);
		this.leftClaw2.setRotationPoint(0.0F, -10.0F, 0.0F);
		this.leftClaw2.addBox(-1.5F, -10.0F, -1.5F, 3, 10, 3, 0.0F);
		this.setRotateAngle(leftClaw2, 1.0471975511965976F, 0.5235987755982988F, -0.5235987755982988F);
		this.rightUpperLeg3 = new ModelRenderer(this, 0, 39);
		this.rightUpperLeg3.setRotationPoint(-5.5F, 2.0F, -1.0F);
		this.rightUpperLeg3.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(rightUpperLeg3, 1.0471975511965976F, 2.0943951023931953F, 0.0F);
		this.rightFoot3 = new ModelRenderer(this, 24, 39);
		this.rightFoot3.mirror = true;
		this.rightFoot3.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.rightFoot3.addBox(-1.0F, -8.0F, -1.0F, 2, 8, 2, 0.0F);
		this.setRotateAngle(rightFoot3, 1.0471975511965976F, 0.17453292519943295F, 0.0F);
		this.skull = new ModelRenderer(this, 60, 0);
		this.skull.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.skull.addBox(-8.0F, -16.0F, -8.0F, 16, 16, 16, 0.0F);
		this.setRotateAngle(skull, -0.8651597102135892F, 0.0F, 0.0F);
		this.rightLowerLeg1 = new ModelRenderer(this, 12, 39);
		this.rightLowerLeg1.mirror = true;
		this.rightLowerLeg1.setRotationPoint(0.0F, -8.0F, 0.0F);
		this.rightLowerLeg1.addBox(-1.5F, -8.0F, -1.5F, 3, 8, 3, 0.0F);
		this.setRotateAngle(rightLowerLeg1, 1.0471975511965976F, 0.17453292519943295F, 0.0F);
		this.rightClaw1 = new ModelRenderer(this, 0, 26);
		this.rightClaw1.mirror = true;
		this.rightClaw1.setRotationPoint(-5.0F, 1.0F, -10.0F);
		this.rightClaw1.addBox(-1.5F, -10.0F, -1.5F, 3, 10, 3, 0.0F);
		this.setRotateAngle(rightClaw1, 1.0471975511965976F, 0.7853981633974483F, 0.0F);
		this.body.addChild(this.rightUpperLeg2);
		this.leftClaw2.addChild(this.lowerLeftClaw);
		this.rightUpperLeg3.addChild(this.rightLowerLeg3);
		this.leftLowerLeg3.addChild(this.leftFoot3);
		this.leftClaw2.addChild(this.upperLeftClaw);
		this.body.addChild(this.head);
		this.body.addChild(this.rightUpperLeg1);
		this.head.addChild(this.rightEye);
		this.rightLowerLeg2.addChild(this.rightFoot2);
		this.body.addChild(this.leftUpperLeg3);
		this.leftUpperLeg1.addChild(this.leftLowerLeg1);
		this.body.addChild(this.leftUpperLeg2);
		this.rightClaw2.addChild(this.lowerRightClaw);
		this.body.addChild(this.leftClaw1);
		this.leftLowerLeg1.addChild(this.leftFoot1);
		this.leftLowerLeg2.addChild(this.leftFoot2);
		this.rightClaw2.addChild(this.upperRightClaw);
		this.leftUpperLeg2.addChild(this.leftLowerLeg2);
		this.rightLowerLeg1.addChild(this.rightFoot1);
		this.rightUpperLeg2.addChild(this.rightLowerLeg2);
		this.body.addChild(this.leftUpperLeg1);
		this.head.addChild(this.leftEye);
		this.leftUpperLeg3.addChild(this.leftLowerLeg3);
		this.rightClaw1.addChild(this.rightClaw2);
		this.leftClaw1.addChild(this.leftClaw2);
		this.body.addChild(this.rightUpperLeg3);
		this.rightLowerLeg3.addChild(this.rightFoot3);
		this.body.addChild(this.skull);
		this.rightUpperLeg1.addChild(this.rightLowerLeg1);
		this.body.addChild(this.rightClaw1);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		this.body.render(f5);
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


