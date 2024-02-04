package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityGargoyleTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.client.Animator;
/**
* ModelGargoyleTitan - Either Mojang or a mod author
* Created using Tabula 4.1.1
*/
public class ModelGargoyleTitan extends ModelBase 
{

	public ModelRenderer Torso;
	public ModelRenderer Body;
	public ModelRenderer LeftLeg;
	public ModelRenderer RightLeg;
	public ModelRenderer Head;
	public ModelRenderer RightArm;
	public ModelRenderer LeftArm;
	public ModelRenderer RightWing1;
	public ModelRenderer LeftWing1;
	public ModelRenderer RightHorn1;
	public ModelRenderer Nose;
	public ModelRenderer LeftHorn1;
	public ModelRenderer RightHorn2;
	public ModelRenderer RightHorn3;
	public ModelRenderer LeftHorn2;
	public ModelRenderer LeftHorn3;
	public ModelRenderer RightArmShoulder;
	public ModelRenderer RightForearm;
	public ModelRenderer LeftArmShoulder;
	public ModelRenderer LeftForearm;
	public ModelRenderer RightWing2;
	public ModelRenderer RightWingSkin1;
	public ModelRenderer RightWingSkin2;
	public ModelRenderer LeftWing2;
	public ModelRenderer LeftWingSkin1;
	public ModelRenderer LeftWingSkin2;
	public ModelRenderer LeftFoot;
	public ModelRenderer RightFoot;
	private Animator animator;
	public ModelGargoyleTitan()
	{
		this.textureWidth = 128;
		this.textureHeight = 128;
		this.LeftForearm = new ModelRenderer(this, 68, 16);
		this.LeftForearm.mirror = true;
		this.LeftForearm.setRotationPoint(2.0F, 8.0F, 0.0F);
		this.LeftForearm.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
		this.RightHorn3 = new ModelRenderer(this, 0, 0);
		this.RightHorn3.setRotationPoint(0.0F, -3.0F, -0.5F);
		this.RightHorn3.addBox(-1.0F, -4.0F, -1.5F, 2, 4, 2, 0.0F);
		this.setRotateAngle(RightHorn3, -0.8651597102135892F, 0.0F, -0.091106186954104F);
		this.LeftLeg = new ModelRenderer(this, 46, 0);
		this.LeftLeg.mirror = true;
		this.LeftLeg.setRotationPoint(5.0F, 0.0F, 0.0F);
		this.LeftLeg.addBox(-3.0F, 0.0F, -3.0F, 6, 7, 5, 0.0F);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, -10.0F, -2.0F);
		this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		this.LeftArmShoulder = new ModelRenderer(this, 68, 0);
		this.LeftArmShoulder.mirror = true;
		this.LeftArmShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LeftArmShoulder.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
		this.setRotateAngle(LeftArmShoulder, 0.0F, -1.0471975511965976F, -1.0471975511965976F);
		this.Torso = new ModelRenderer(this, 0, 36);
		this.Torso.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.Torso.addBox(-5.0F, -5.0F, -3.0F, 10, 5, 6, 0.5F);
		this.RightArm = new ModelRenderer(this, 68, 0);
		this.RightArm.setRotationPoint(-9.0F, -7.0F, 0.0F);
		this.RightArm.addBox(-5.0F, -2.0F, -3.0F, 6, 10, 6, 0.0F);
		this.Nose = new ModelRenderer(this, 0, 0);
		this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Nose.addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F);
		this.Body = new ModelRenderer(this, 0, 16);
		this.Body.setRotationPoint(0.0F, -5.0F, 0.0F);
		this.Body.addBox(-9.0F, -10.0F, -5.0F, 18, 10, 10, 0.0F);
		this.LeftWing2 = new ModelRenderer(this, 0, 47);
		this.LeftWing2.mirror = true;
		this.LeftWing2.setRotationPoint(-18.0F, 0.0F, 0.0F);
		this.LeftWing2.addBox(-18.0F, -1.5F, -1.5F, 18, 3, 3, 0.0F);
		this.setRotateAngle(LeftWing2, 0.0F, 0.0F, -0.17453292519943295F);
		this.LeftFoot = new ModelRenderer(this, 46, 12);
		this.LeftFoot.mirror = true;
		this.LeftFoot.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.LeftFoot.addBox(-3.0F, 0.0F, -3.0F, 6, 7, 5, 0.0F);
		this.RightHorn1 = new ModelRenderer(this, 0, 0);
		this.RightHorn1.setRotationPoint(-2.0F, -7.5F, -1.5F);
		this.RightHorn1.addBox(-1.0F, -4.0F, -1.5F, 2, 4, 2, 0.0F);
		this.setRotateAngle(RightHorn1, 0.22759093446006054F, 0.0F, -0.27314402793711257F);
		this.RightWingSkin1 = new ModelRenderer(this, 0, 53);
		this.RightWingSkin1.mirror = true;
		this.RightWingSkin1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.RightWingSkin1.addBox(-18.0F, 0.0F, 0.0F, 18, 12, 0, 0.0F);
		this.LeftWingSkin2 = new ModelRenderer(this, 0, 65);
		this.LeftWingSkin2.mirror = true;
		this.LeftWingSkin2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LeftWingSkin2.addBox(-18.0F, 0.0F, 0.0F, 18, 12, 0, 0.0F);
		this.RightForearm = new ModelRenderer(this, 68, 16);
		this.RightForearm.setRotationPoint(-2.0F, 8.0F, 0.0F);
		this.RightForearm.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
		this.LeftHorn1 = new ModelRenderer(this, 0, 0);
		this.LeftHorn1.mirror = true;
		this.LeftHorn1.setRotationPoint(2.0F, -7.5F, -1.5F);
		this.LeftHorn1.addBox(-1.0F, -4.0F, -1.5F, 2, 4, 2, 0.0F);
		this.setRotateAngle(LeftHorn1, 0.22759093446006054F, 0.0F, 0.27314402793711257F);
		this.RightWing2 = new ModelRenderer(this, 0, 47);
		this.RightWing2.setRotationPoint(-18.0F, 0.0F, 0.0F);
		this.RightWing2.addBox(-18.0F, -1.5F, -1.5F, 18, 3, 3, 0.0F);
		this.setRotateAngle(RightWing2, 0.0F, 0.0F, -0.17453292519943295F);
		this.LeftWingSkin1 = new ModelRenderer(this, 0, 53);
		this.LeftWingSkin1.mirror = true;
		this.LeftWingSkin1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.LeftWingSkin1.addBox(-18.0F, 0.0F, 0.0F, 18, 12, 0, 0.0F);
		this.RightLeg = new ModelRenderer(this, 46, 0);
		this.RightLeg.setRotationPoint(-5.0F, 0.0F, 0.0F);
		this.RightLeg.addBox(-3.0F, 0.0F, -3.0F, 6, 7, 5, 0.0F);
		this.LeftWing1 = new ModelRenderer(this, 0, 47);
		this.LeftWing1.mirror = true;
		this.LeftWing1.setRotationPoint(6.0F, -8.0F, 3.0F);
		this.LeftWing1.addBox(-18.0F, -1.5F, -1.5F, 18, 3, 3, 0.0F);
		this.setRotateAngle(LeftWing1, -0.3490658503988659F, 2.792526803190927F, -0.3490658503988659F);
		this.RightFoot = new ModelRenderer(this, 46, 12);
		this.RightFoot.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.RightFoot.addBox(-3.0F, 0.0F, -3.0F, 6, 7, 5, 0.0F);
		this.LeftArm = new ModelRenderer(this, 68, 0);
		this.LeftArm.mirror = true;
		this.LeftArm.setRotationPoint(9.0F, -7.0F, 0.0F);
		this.LeftArm.addBox(-1.0F, -2.0F, -3.0F, 6, 10, 6, 0.0F);
		this.RightWing1 = new ModelRenderer(this, 0, 47);
		this.RightWing1.setRotationPoint(-6.0F, -8.0F, 3.0F);
		this.RightWing1.addBox(-18.0F, -1.5F, -1.5F, 18, 3, 3, 0.0F);
		this.setRotateAngle(RightWing1, 0.3490658503988659F, 0.3490658503988659F, 0.3490658503988659F);
		this.RightHorn2 = new ModelRenderer(this, 0, 0);
		this.RightHorn2.setRotationPoint(0.0F, -3.0F, -0.5F);
		this.RightHorn2.addBox(-1.0F, -4.0F, -1.5F, 2, 4, 2, 0.0F);
		this.setRotateAngle(RightHorn2, -0.6829473363053812F, 0.0F, -0.091106186954104F);
		this.RightArmShoulder = new ModelRenderer(this, 68, 0);
		this.RightArmShoulder.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.RightArmShoulder.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
		this.setRotateAngle(RightArmShoulder, -0.0F, 1.0471975511965976F, 1.0471975511965976F);
		this.LeftHorn2 = new ModelRenderer(this, 0, 0);
		this.LeftHorn2.mirror = true;
		this.LeftHorn2.setRotationPoint(0.0F, -3.0F, -0.5F);
		this.LeftHorn2.addBox(-1.0F, -4.0F, -1.5F, 2, 4, 2, 0.0F);
		this.setRotateAngle(LeftHorn2, -0.6829473363053812F, 0.0F, 0.091106186954104F);
		this.LeftHorn3 = new ModelRenderer(this, 0, 0);
		this.LeftHorn3.mirror = true;
		this.LeftHorn3.setRotationPoint(0.0F, -3.0F, -0.5F);
		this.LeftHorn3.addBox(-1.0F, -4.0F, -1.5F, 2, 4, 2, 0.0F);
		this.setRotateAngle(LeftHorn3, -0.8651597102135892F, 0.0F, 0.091106186954104F);
		this.RightWingSkin2 = new ModelRenderer(this, 0, 65);
		this.RightWingSkin2.mirror = true;
		this.RightWingSkin2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.RightWingSkin2.addBox(-18.0F, 0.0F, 0.0F, 18, 12, 0, 0.0F);
		this.LeftArm.addChild(this.LeftForearm);
		this.RightHorn2.addChild(this.RightHorn3);
		this.Torso.addChild(this.LeftLeg);
		this.Body.addChild(this.Head);
		this.LeftArm.addChild(this.LeftArmShoulder);
		this.Body.addChild(this.RightArm);
		this.Head.addChild(this.Nose);
		this.Torso.addChild(this.Body);
		this.LeftWing1.addChild(this.LeftWing2);
		this.LeftLeg.addChild(this.LeftFoot);
		this.Head.addChild(this.RightHorn1);
		this.RightWing1.addChild(this.RightWingSkin1);
		this.LeftWing2.addChild(this.LeftWingSkin2);
		this.RightArm.addChild(this.RightForearm);
		this.Head.addChild(this.LeftHorn1);
		this.RightWing1.addChild(this.RightWing2);
		this.LeftWing1.addChild(this.LeftWingSkin1);
		this.Torso.addChild(this.RightLeg);
		this.Body.addChild(this.LeftWing1);
		this.RightLeg.addChild(this.RightFoot);
		this.Body.addChild(this.LeftArm);
		this.Body.addChild(this.RightWing1);
		this.RightHorn1.addChild(this.RightHorn2);
		this.RightArm.addChild(this.RightArmShoulder);
		this.LeftHorn1.addChild(this.LeftHorn2);
		this.LeftHorn2.addChild(this.LeftHorn3);
		this.RightWing2.addChild(this.RightWingSkin2);
		this.animator = new Animator(this);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.Torso.render(f5);
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

	public void setAngles()
	{
		this.Torso.setRotationPoint(0.0F, 10.0F, 0.0F);
		this.RightLeg.setRotationPoint(-5.0F, 0.0F, 0.0F);
		this.LeftLeg.setRotationPoint(5.0F, 0.0F, 0.0F);
		this.setRotateAngle(LeftArmShoulder, 0.0F, -1.0471975511965976F, -1.0471975511965976F);
		this.setRotateAngle(RightArmShoulder, -0.0F, 1.0471975511965976F, 1.0471975511965976F);
		this.setRotateAngle(RightWing1, 0.3490658503988659F, 0.3490658503988659F, 0.3490658503988659F);
		this.setRotateAngle(LeftWing1, -0.3490658503988659F, 2.792526803190927F, -0.3490658503988659F);
		this.setRotateAngle(RightWing2, 0.0F, 0.0F, -0.17453292519943295F);
		this.setRotateAngle(LeftWing2, 0.0F, 0.0F, -0.17453292519943295F);
		this.setRotateAngle(RightHorn1, 0.22759093446006054F, 0.0F, -0.27314402793711257F);
		this.setRotateAngle(RightHorn2, -0.6829473363053812F, 0.0F, -0.091106186954104F);
		this.setRotateAngle(RightHorn3, -0.8651597102135892F, 0.0F, -0.091106186954104F);
		this.setRotateAngle(LeftHorn1, 0.22759093446006054F, 0.0F, 0.27314402793711257F);
		this.setRotateAngle(LeftHorn2, -0.6829473363053812F, 0.0F, 0.091106186954104F);
		this.setRotateAngle(LeftHorn3, -0.8651597102135892F, 0.0F, 0.091106186954104F);
	}

	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		EntityGargoyleTitan entitytitan = (EntityGargoyleTitan)p_78087_7_;
		this.animator.update(entitytitan);
		this.setAngles();
		if (entitytitan.deathTicks <= 0)
		{
			this.Head.rotateAngleY = (p_78087_4_ / 57.295776F);
			this.Head.rotateAngleX = (p_78087_5_ / 57.295776F);
			if (entitytitan.getAnimID() == 0)
			{
				RightWing1.rotateAngleY += MathHelper.cos(p_78087_3_ * 0.05F) * 0.125F;
				LeftWing1.rotateAngleY -= MathHelper.cos(p_78087_3_ * 0.05F) * 0.125F;
				RightWing2.rotateAngleY += MathHelper.cos(p_78087_3_ * 0.05F - 0.2F) * 0.25F;
				LeftWing2.rotateAngleY -= MathHelper.cos(p_78087_3_ * 0.05F - 0.2F) * 0.25F;
				RightWing1.rotateAngleY += MathHelper.cos(p_78087_3_ * 0.3331F) * 0.2F * p_78087_2_;
				LeftWing1.rotateAngleY -= MathHelper.cos(p_78087_3_ * 0.3331F) * 0.2F * p_78087_2_;
				RightWing2.rotateAngleY += MathHelper.cos(p_78087_3_ * 0.3331F - 0.2F) * 0.2F * p_78087_2_;
				LeftWing2.rotateAngleY -= MathHelper.cos(p_78087_3_ * 0.3331F - 0.2F) * 0.2F * p_78087_2_;
				this.RightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.3331F - 0.5F) * 1.25F * p_78087_2_);
				this.LeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.3331F + 2.6415927F) * 1.25F * p_78087_2_);
				this.RightFoot.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.3331F + 3.1415927F) * 1.25F * p_78087_2_);
				this.LeftFoot.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.3331F) * 1.25F * p_78087_2_);
				this.RightArm.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.25F + 3.1415927F) * 1.0F * p_78087_2_);
				this.LeftArm.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.25F) * 1.0F * p_78087_2_);
				this.RightForearm.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.25F + 2.1415927F) * 1.0F * p_78087_2_);
				this.LeftForearm.rotateAngleX = (MathHelper.cos(p_78087_1_ * 0.25F - 1.0F) * 1.0F * p_78087_2_);
			}

			if (this.RightFoot.rotateAngleX < 0.0F)
			this.RightFoot.rotateAngleX = 0.0F;
			if (this.LeftFoot.rotateAngleX < 0.0F)
			this.LeftFoot.rotateAngleX = 0.0F;
			if (this.RightForearm.rotateAngleX > 0.0F)
			{
				this.RightForearm.rotateAngleX = 0.0F;
			}

			if (this.LeftForearm.rotateAngleX > 0.0F)
			{
				this.LeftForearm.rotateAngleX = 0.0F;
			}

			if (!entitytitan.onGround)
			{
				RightWing1.rotateAngleY += MathHelper.cos(p_78087_3_ * 0.2F);
				LeftWing1.rotateAngleY -= MathHelper.cos(p_78087_3_ * 0.2F);
				RightWing2.rotateAngleY += MathHelper.cos(p_78087_3_ * 0.2F - 2F);
				LeftWing2.rotateAngleY -= MathHelper.cos(p_78087_3_ * 0.2F - 2F);
				this.Torso.rotateAngleX += 1F;
				this.Head.rotateAngleX -= 1F;
				this.RightArm.rotateAngleX = -0.5F;
				this.LeftArm.rotateAngleX = -0.5F;
				this.RightForearm.rotateAngleX = -0.5F;
				this.LeftForearm.rotateAngleX = -0.5F;
				this.RightLeg.rotateAngleX = 0.5F;
				this.LeftLeg.rotateAngleX = 0.5F;
				this.RightFoot.rotateAngleX = 0.5F;
				this.LeftFoot.rotateAngleX = 0.5F;
			}

			this.animateAntiTitanAttack();
			this.animateWingBuffet();
			this.animateSlam();
			this.animateMeteor();
			this.animateWaterSpout();
			this.animateLavaSpit();
			this.animateStomp();
			this.animateSwat();
			this.animatePunch();
		}

		else
		{
		}
	}

	private void animateAntiTitanAttack()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(10);
		this.animator.rotate(this.RightWing1, 0F, 1F, 0.5F);
		this.animator.rotate(this.LeftWing1, 0F, -1F, -0.5F);
		this.animator.rotate(this.RightWing2, 0F, 1F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, -1F, 0F);
		this.animator.rotate(this.Head, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftArm, 2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.RightWing1, 0F, -1F, -1F);
		this.animator.rotate(this.LeftWing1, 0F, 1F, 1F);
		this.animator.rotate(this.RightWing2, 0F, -1F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 1F,0F);
		this.animator.rotate(this.Head, 0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, -2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftArm, -2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(10);
	}

	private void animateWingBuffet()
	{
		this.animator.setAnim(2);
		this.animator.startPhase(10);
		this.animator.rotate(this.RightWing1, 0F, 1F, 0F);
		this.animator.rotate(this.LeftWing1, 0F, -1F, 0F);
		this.animator.rotate(this.RightWing2, 0F, 1F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, -1F, 0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, -1F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, -1F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.RightWing1, -0.25F, -1F, 0.5F);
		this.animator.rotate(this.LeftWing1, 0.25F, 1F, -0.5F);
		this.animator.rotate(this.RightWing2, 0F, -1F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 1F, 0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, -1F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, -1F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.rotate(this.RightWing1, 0F, -0.75F, -0.5F);
		this.animator.rotate(this.LeftWing1, 0F, 0.75F, 0.5F);
		this.animator.rotate(this.RightWing2, 0F, -0.75F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 0.75F,0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.RightWing1, 0F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftWing1, 0F, -0.5F, -0.5F);
		this.animator.rotate(this.RightWing2, 0F, 0.5F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, -0.5F,0F);
		this.animator.rotate(this.Head, 0F, -0.5F, 0.0F);
		this.animator.rotate(this.Body, 0F, 0.5F, 0.0F);
		this.animator.rotate(this.RightArm, -2F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, 0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(60);
		this.animator.resetPhase(20);
	}

	private void animateSlam()
	{
		this.animator.setAnim(3);
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightWing1, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftWing1, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightLeg, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftLeg, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.move(this.Torso, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.RightWing1, 0F, -0.5F, 0.25F);
		this.animator.rotate(this.LeftWing1, 0F, 0.5F, -0.25F);
		this.animator.rotate(this.RightWing2, 0F, -0.5F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 0.5F,0F);
		this.animator.rotate(this.RightLeg, -1.6F, 0.2F, 0.0F);
		this.animator.rotate(this.LeftLeg, 0.0F, 0.2F, 0.0F);
		this.animator.rotate(this.RightFoot, 0.9F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, -2.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftArm, -2.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.move(this.Torso, 0.0F, 4.0F, -1.0F);
		this.animator.rotate(this.RightWing1, 0F, 0.5F, -0.5F);
		this.animator.rotate(this.LeftWing1, 0F, -0.5F, 0.5F);
		this.animator.rotate(this.RightWing2, 0F, 0.5F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, -0.5F,0F);
		this.animator.rotate(this.RightLeg, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftLeg, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForearm, -0.5F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateMeteor()
	{
		this.animator.setAnim(4);
		this.animator.startPhase(10);
		this.animator.rotate(this.RightWing1, 0F, -0.75F, -0.5F);
		this.animator.rotate(this.LeftWing1, 0F, 0.75F, 0.5F);
		this.animator.rotate(this.RightWing2, 0F, -0.75F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 0.75F,0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.RightWing1, 0F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftWing1, 0F, -0.5F, -0.5F);
		this.animator.rotate(this.RightWing2, 0F, 0.5F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, -0.5F,0F);
		this.animator.rotate(this.Head, 0F, -0.5F, 0.0F);
		this.animator.rotate(this.Body, 0F, 0.5F, 0.0F);
		this.animator.rotate(this.RightArm, -2F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, 0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(60);
		this.animator.resetPhase(20);
	}

	private void animateWaterSpout()
	{
		this.animator.setAnim(5);
		this.animator.startPhase(20);
		this.animator.rotate(this.RightWing1, 0F, -0.75F, -0.5F);
		this.animator.rotate(this.LeftWing1, 0F, 0.75F, 0.5F);
		this.animator.rotate(this.RightWing2, 0F, -0.75F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 0.75F,0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.RightWing1, 0F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftWing1, 0F, -0.5F, -0.5F);
		this.animator.rotate(this.RightWing2, 0F, 0.5F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, -0.5F,0F);
		this.animator.rotate(this.Nose, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.5F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.5F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateLavaSpit()
	{
		this.animator.setAnim(6);
		this.animator.startPhase(20);
		this.animator.rotate(this.RightWing1, 0F, -0.75F, -0.5F);
		this.animator.rotate(this.LeftWing1, 0F, 0.75F, 0.5F);
		this.animator.rotate(this.RightWing2, 0F, -0.75F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 0.75F,0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.RightWing1, 0F, -0.75F, -0.5F);
		this.animator.rotate(this.LeftWing1, 0F, 0.75F, 0.5F);
		this.animator.rotate(this.RightWing2, 0F, -0.75F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 0.75F,0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.RightWing1, 0F, -0.75F, -0.5F);
		this.animator.rotate(this.LeftWing1, 0F, 0.75F, 0.5F);
		this.animator.rotate(this.RightWing2, 0F, -0.75F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 0.75F,0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(10);
		this.animator.resetPhase(20);
	}

	private void animateStomp()
	{
		this.animator.setAnim(7);
		this.animator.startPhase(25);
		this.animator.rotate(this.RightLeg, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftLeg, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.rotate(this.RightLeg, -2.0F, 0.2F, 0.75F);
		this.animator.rotate(this.LeftLeg, 0.0F, 0.2F, 0.0F);
		this.animator.rotate(this.RightFoot, 1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftFoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.Body, -1.0F, 0.0F, 0.25F);
		this.animator.rotate(this.RightArm, -0.75F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftArm, -0.75F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForearm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.RightWing1, -0.25F, -1F, 0.5F);
		this.animator.rotate(this.LeftWing1, 0.25F, 1F, -0.5F);
		this.animator.rotate(this.RightWing2, 0F, -1F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 1F, 0F);
		this.animator.rotate(this.RightLeg, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.LeftLeg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.5F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, -0.4F);
		this.animator.rotate(this.RightArm, -0.75F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftArm, -0.75F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForearm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(5);
		this.animator.startPhase(20);
		this.animator.rotate(this.RightLeg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftLeg, -2.0F, 0.0F, -0.75F);
		this.animator.rotate(this.RightFoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.Body, -1.0F, 0.0F, -0.25F);
		this.animator.rotate(this.RightArm, 0.8F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.8F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForearm, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.8F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.RightWing1, -0.25F, -1F, 0.5F);
		this.animator.rotate(this.LeftWing1, 0.25F, 1F, -0.5F);
		this.animator.rotate(this.RightWing2, 0F, -1F, 0F);
		this.animator.rotate(this.LeftWing2, 0F, 1F, 0F);
		this.animator.rotate(this.RightLeg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftLeg, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.RightFoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.Body, 0.5F, 0.0F, 0.4F);
		this.animator.rotate(this.RightArm, 0.75F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftArm, 0.75F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForearm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}

	private void animateSwat()
	{
		this.animator.setAnim(8);
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightLeg, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.LeftLeg, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightFoot, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.5F, 1.0F, 0.0F);
		this.animator.rotate(this.LeftArm, -2F, 0.75F, 1.5F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 1.5F, -1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 6.0F, 0.0F);
		this.animator.rotate(this.RightLeg, -1.0F, 0.5F, 0.0F);
		this.animator.rotate(this.LeftLeg, -1.0F, -0.5F, 0.0F);
		this.animator.rotate(this.RightFoot, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftArm, -2F, -1.25F, -1.5F);
		this.animator.rotate(this.LeftForearm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 2.25F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animatePunch()
	{
		this.animator.setAnim(9);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 0.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Body, 0.0F, 0.0F, 0.5F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftArm, -2.5F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftForearm, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -1.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 4.0F, 0.0F);
		this.animator.rotate(this.RightLeg, -1F, 0.5F, 0.0F);
		this.animator.rotate(this.LeftLeg, -1F, -0.5F, 0.0F);
		this.animator.rotate(this.RightFoot, 2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFoot, 2F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.Body, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightArm, -2F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.5F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}
}


