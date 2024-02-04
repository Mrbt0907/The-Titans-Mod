package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityEnderColossus;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.IAnimatedEntity;
import thehippomaster.AnimationAPI.client.Animator;
public class ModelEnderColossus
extends ModelBase
{
	public boolean isAttacking;
	public ModelRenderer BodyBottom;
	public ModelRenderer LeftThigh;
	public ModelRenderer RightThigh;
	public ModelRenderer BodyMiddle;
	public ModelRenderer BodyTop;
	public ModelRenderer LeftShoulder;
	public ModelRenderer RightShoulder;
	public ModelRenderer Mouth;
	public ModelRenderer LeftForeArm;
	public ModelRenderer RightForeArm;
	public ModelRenderer Head;
	public ModelRenderer Horn1;
	public ModelRenderer Horn2;
	public ModelRenderer Horn3;
	public ModelRenderer Horn4;
	public ModelRenderer LeftFemur;
	public ModelRenderer RightFemur;
	private Animator animator;
	public ModelEnderColossus()
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.RightShoulder = new ModelRenderer(this, 32, 0);
		this.RightShoulder.setRotationPoint(-5.0F, -2.0F, 0.0F);
		this.RightShoulder.addBox(-1.0F, -2.0F, -1.0F, 2, 15, 2, 0.0F);
		this.LeftShoulder = new ModelRenderer(this, 32, 0);
		this.LeftShoulder.mirror = true;
		this.LeftShoulder.setRotationPoint(5.0F, -2.0F, 0.0F);
		this.LeftShoulder.addBox(-1.0F, -2.0F, -1.0F, 2, 15, 2, 0.0F);
		this.Horn1 = new ModelRenderer(this, 24, 38);
		this.Horn1.setRotationPoint(4.0F, -4.0F, 0.0F);
		this.Horn1.addBox(0.0F, -1.0F, -1.0F, 4, 2, 2, 0.0F);
		this.Mouth = new ModelRenderer(this, 0, 16);
		this.Mouth.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.Mouth.addBox(-4.0F, -7.5F, -4.0F, 8, 8, 8, -0.25F);
		this.BodyMiddle = new ModelRenderer(this, 0, 40);
		this.BodyMiddle.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.BodyMiddle.addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, 0.0F);
		this.LeftForeArm = new ModelRenderer(this, 32, 17);
		this.LeftForeArm.mirror = true;
		this.LeftForeArm.setRotationPoint(0.0F, 13.0F, 0.0F);
		this.LeftForeArm.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
		this.RightForeArm = new ModelRenderer(this, 32, 17);
		this.RightForeArm.setRotationPoint(0.0F, 13.0F, 0.0F);
		this.RightForeArm.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
		this.BodyTop = new ModelRenderer(this, 0, 32);
		this.BodyTop.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.BodyTop.addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, 0.0F);
		this.LeftThigh = new ModelRenderer(this, 32, 0);
		this.LeftThigh.mirror = true;
		this.LeftThigh.setRotationPoint(2.0F, -6.0F, 0.0F);
		this.LeftThigh.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
		this.BodyBottom = new ModelRenderer(this, 0, 48);
		this.BodyBottom.setRotationPoint(0.0F, -6.0F, -0.0F);
		this.BodyBottom.addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, 0.0F);
		this.RightThigh = new ModelRenderer(this, 32, 0);
		this.RightThigh.setRotationPoint(-2.0F, -6.0F, 0.0F);
		this.RightThigh.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
		this.LeftFemur = new ModelRenderer(this, 32, 17);
		this.LeftFemur.mirror = true;
		this.LeftFemur.setRotationPoint(0.0F, 15.0F, 0.0F);
		this.LeftFemur.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25F);
		this.Horn2 = new ModelRenderer(this, 24, 38);
		this.Horn2.setRotationPoint(-4.0F, -4.0F, 0.0F);
		this.Horn2.addBox(-4.0F, -1.0F, -1.0F, 4, 2, 2, 0.0F);
		this.Horn3 = new ModelRenderer(this, 36, 36);
		this.Horn3.setRotationPoint(3.0F, -1.0F, 0.0F);
		this.Horn3.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
		this.RightFemur = new ModelRenderer(this, 32, 17);
		this.RightFemur.setRotationPoint(0.0F, 15.0F, 0.0F);
		this.RightFemur.addBox(-1.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F);
		this.Horn4 = new ModelRenderer(this, 36, 36);
		this.Horn4.setRotationPoint(-3.0F, -1.0F, 0.0F);
		this.Horn4.addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2, 0.0F);
		this.BodyTop.addChild(this.RightShoulder);
		this.BodyTop.addChild(this.LeftShoulder);
		this.Head.addChild(this.Horn1);
		this.BodyTop.addChild(this.Mouth);
		this.BodyBottom.addChild(this.BodyMiddle);
		this.LeftShoulder.addChild(this.LeftForeArm);
		this.RightShoulder.addChild(this.RightForeArm);
		this.BodyMiddle.addChild(this.BodyTop);
		this.LeftThigh.addChild(this.LeftFemur);
		this.Mouth.addChild(this.Head);
		this.Head.addChild(this.Horn2);
		this.Horn1.addChild(this.Horn3);
		this.RightThigh.addChild(this.RightFemur);
		this.Horn2.addChild(this.Horn4);
		this.animator = new Animator(this);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		animate((IAnimatedEntity)entity, f, f1, f2, f3, f4, f5);
		this.LeftThigh.render(f5);
		this.BodyBottom.render(f5);
		this.RightThigh.render(f5);
	}

	public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.animator.update(entity);
		setAngles();
		EntityEnderColossus entitytitan = (EntityEnderColossus)entity;
		if (this.isAttacking)
		this.Head.setRotationPoint(0.0F, -7.0F, 0.0F);
		else
		this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		float f6 = MathHelper.cos(f2 * 0.05F);
		this.RightThigh.rotateAngleX = -0.09F;
		this.LeftThigh.rotateAngleX = -0.09F;
		this.RightFemur.rotateAngleX = 0.18F;
		this.LeftFemur.rotateAngleX = 0.18F;
		if (entitytitan.deathTicks <= 0)
		{
			if (!this.isRiding)
			{
				this.LeftThigh.rotateAngleX = -0.09F + MathHelper.cos(f * 0.33F + 2.6415927F) * 0.75F * f1;
				this.RightThigh.rotateAngleX = -0.09F + MathHelper.cos(f * 0.33F - 0.5F) * 0.75F * f1;
				this.LeftFemur.rotateAngleX = 0.18F + MathHelper.cos(f * 0.33F) * 0.75F * f1;
				this.RightFemur.rotateAngleX = 0.18F + MathHelper.cos(f * 0.33F + 3.1415927F) * 0.75F * f1;
				if (this.RightFemur.rotateAngleX < 0.0F)
				{
					this.RightFemur.rotateAngleX = 0.0F;
				}

				if (this.LeftFemur.rotateAngleX < 0.0F)
				{
					this.LeftFemur.rotateAngleX = 0.0F;
				}
			}

			float faceYaw = f3 * 3.1415927F / 180.0F;
			float facePitch = f4 * 3.1415927F / 180.0F;
			if (entitytitan.getAnimID() == 0)
			{
				this.BodyMiddle.rotateAngleX = ((0.0F + -0.01F * f6) * 3.1415927F);
				this.BodyTop.rotateAngleX = ((0.0F + -0.01F * f6) * 3.1415927F);
				this.Head.rotateAngleX = ((-0.01F + -0.01F * f6) * 3.1415927F);
				this.Mouth.rotateAngleX = ((0.01F + 0.01F * f6) * 3.1415927F);
				this.BodyBottom.rotateAngleZ = (MathHelper.cos(f * 0.33F) * 0.125F * f1);
				this.BodyMiddle.rotateAngleZ = (MathHelper.cos(f * 0.33F) * 0.125F * f1);
				this.BodyTop.rotateAngleZ = (MathHelper.cos(f * 0.33F) * 0.125F * f1);
				this.Mouth.rotateAngleZ = (MathHelper.cos(f * 0.33F + 3.1415927F) * 0.375F * f1);
				this.RightForeArm.rotateAngleX = MathHelper.cos(f * 0.33F + 3.1415927F) * 0.75F * f1 - 0.3F;
				this.LeftForeArm.rotateAngleX = MathHelper.cos(f * 0.33F) * 0.75F * f1 - 0.3F;
			}

			this.RightShoulder.rotateAngleX = 0.09F + MathHelper.cos(f * 0.33F + 3.1415927F) * 0.75F * f1;
			this.LeftShoulder.rotateAngleX = 0.09F + MathHelper.cos(f * 0.33F) * 0.75F * f1;
			this.RightShoulder.rotateAngleY = 0.08F;
			this.LeftShoulder.rotateAngleY = -0.08F;
			this.RightShoulder.rotateAngleZ = 0.10471976F - (0.005F + 0.005F * f6) * 3.1415927F;
			this.LeftShoulder.rotateAngleZ = -0.10471976F + (-0.005F + -0.005F * f6) * 3.1415927F;
			if (entitytitan.getAnimID() == 0 && entitytitan.getEyeLaserTime() < 0)
			{
				this.Mouth.rotateAngleX += facePitch * 0.3F;
				this.Mouth.rotateAngleY += faceYaw * 0.3F;
				this.BodyTop.rotateAngleX += facePitch * 0.3F;
				this.BodyTop.rotateAngleY += faceYaw * 0.3F;
				this.BodyMiddle.rotateAngleX += facePitch * 0.3F;
				this.BodyMiddle.rotateAngleY += faceYaw * 0.3F;
				this.RightShoulder.rotateAngleX -= facePitch * 0.6F;
				this.LeftShoulder.rotateAngleX -= facePitch * 0.6F;
			}

			else
			{
				this.Mouth.rotateAngleX += facePitch * 0.9F;
				this.Mouth.rotateAngleY += faceYaw * 0.9F;
			}

			if (this.RightForeArm.rotateAngleX > -0.3F)
			this.RightForeArm.rotateAngleX = -0.3F;
			if (this.LeftForeArm.rotateAngleX > -0.3F)
			this.LeftForeArm.rotateAngleX = -0.3F;
			if (entitytitan.isFlying && !this.isRiding)
			{
				this.BodyTop.rotateAngleZ = 0F;
				this.BodyMiddle.rotateAngleZ = 0F;
				this.BodyBottom.rotateAngleZ = 0F;
				this.Mouth.rotateAngleZ = 0F;
				this.BodyBottom.rotateAngleZ = 0F;
				this.BodyMiddle.rotateAngleZ = 0F;
				this.BodyTop.rotateAngleZ = 0F;
				this.Head.rotateAngleZ = 0F;
				this.RightShoulder.rotateAngleX = 0.09F;
				this.LeftShoulder.rotateAngleX = 0.09F;
				this.RightForeArm.rotateAngleX = 0.09F;
				this.LeftForeArm.rotateAngleX = 0.09F;
				this.Mouth.rotateAngleX -= (float)(entitytitan.limbSwingAmount);
				this.BodyBottom.rotateAngleX += (float)(entitytitan.limbSwingAmount);
				this.RightThigh.rotateAngleX = ((MathHelper.cos(f2 * 0.1F - 0.5F)) * 0.25F) - (float)(entitytitan.motionY / 5) + entitytitan.limbSwingAmount;
				this.LeftThigh.rotateAngleX = ((MathHelper.cos(f2 * 0.1F - 3.6415927F)) * 0.25F) - (float)(entitytitan.motionY / 5) + entitytitan.limbSwingAmount;
				this.RightFemur.rotateAngleX = 0.5F - ((MathHelper.cos(f2 * 0.1F)) * 0.5F);
				this.LeftFemur.rotateAngleX = 0.5F - ((MathHelper.cos(f2 * 0.1F - 3.1415927F)) * 0.5F);
			}

			if (entitytitan.getAnimID() == 8 && (entitytitan.getAnimTick() > 20 && entitytitan.getAnimTick() < 60))
			{
				this.BodyTop.rotateAngleY = ((MathHelper.cos(f2)) * 0.5F);
				this.Mouth.rotateAngleY = ((MathHelper.cos(f2)) * 0.25F);
				this.BodyTop.rotateAngleX = ((MathHelper.cos(f2 * 0.25F - 2F)) * 0.25F);
				this.BodyMiddle.rotateAngleX = ((MathHelper.cos(f2 * 0.25F - 1F)) * 0.25F);
				this.BodyBottom.rotateAngleX = ((MathHelper.cos(f2 * 0.25F)) * 0.25F);
			}

			if (entitytitan.getAnimID() == 8 && entitytitan.getAnimTick() > 100 && entitytitan.getAnimTick() < 340)
			{
				this.Mouth.rotateAngleY = MathHelper.cos(f2 * 0.05F) * 0.2F;
			}

			if (entitytitan.getAnimID() == 1)
			{
				switch (entitytitan.getATAAID())
				{
					case 0:
					this.animateAntiTitanAttack1(entitytitan);
					break;
					case 1:
					this.animateAntiTitanAttack2();
					break;
					case 2:
					this.animateAntiTitanAttack3();
					break;
					case 3:
					this.animateAntiTitanAttack4();
				}
			}

			animateStomp();
			animateSwat();
			animateSlam();
			animateMeteor();
			animateChainLightning();
			animateLightning();
			animateLightningBall();
			animateDragonBall();
			animateScream();
			animateStunned();
			if (this.Head.rotationPointY < -7F)
			this.Head.rotationPointY = -7.0F;
			if (entitytitan.getAnimID() == 3 && (entitytitan.getAnimTick() > 30 && entitytitan.getAnimTick() < 50))
			{
				this.RightForeArm.rotateAngleX += ((0.1F * MathHelper.cos(f2)) * 3.1415927F);
				this.LeftForeArm.rotateAngleX -= ((0.1F * MathHelper.cos(f2)) * 3.1415927F);
			}
		}

		else
		{
			animateDeath();
		}
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setAngles()
	{
		this.RightShoulder.setRotationPoint(-5.0F, -2.0F, 0.0F);
		this.LeftShoulder.setRotationPoint(5.0F, -2.0F, 0.0F);
		this.Mouth.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.BodyBottom.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.LeftThigh.setRotationPoint(2.0F, -6.0F, 0.0F);
		this.RightThigh.setRotationPoint(-2.0F, -6.0F, 0.0F);
		this.Horn1.setRotationPoint(4.25F, -4.5F, 0.0F);
		this.Horn2.setRotationPoint(-4.25F, -4.5F, 0.0F);
	}

	private void animateAntiTitanAttack1(EntityEnderColossus entitytitan)
	{
		entitytitan.playSound("thetitans:titanEnderColossusChomp", 100F, 1F);
		this.animator.setAnim(1);
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.Head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1.0F, -1.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 1.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, -12.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -1.0F, -1.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(10);
	}

	private void animateAntiTitanAttack2()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.BodyTop, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1.6F, 0.0F, 2.0F);
		this.animator.rotate(this.RightForeArm, -0.9F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.0F, 0.0F, -0.75F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateAntiTitanAttack3()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -4F, 0.0F, -0.25F);
		this.animator.rotate(this.LeftShoulder, -4F, 0.0F, 0.25F);
		this.animator.rotate(this.RightForeArm, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1F, 0.0F, -0.25F);
		this.animator.rotate(this.LeftShoulder, 1F, 0.0F, 0.25F);
		this.animator.rotate(this.RightForeArm, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateAntiTitanAttack4()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(10);
		this.animator.rotate(this.RightThigh, 2F, 0.2F, 1.5F);
		this.animator.rotate(this.RightFemur, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.0F, -1.0F, -0.2F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, -0.2F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, -0.2F);
		this.animator.rotate(this.BodyBottom, -1.0F, 1.0F, 0.6F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, 0.75F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftShoulder, -1.5F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, -13.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, -11.0F);
		this.animator.rotate(this.RightThigh, -3F, 0.2F, 1.5F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.0F, 0.0F, -0.25F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.5F, -0.25F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.5F, -0.25F);
		this.animator.rotate(this.BodyBottom, -1.0F, -1.0F, 0.75F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, 0.75F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftShoulder, -1.5F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateMeteor()
	{
		this.animator.setAnim(2);
		this.animator.startPhase(30);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.BodyTop, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftShoulder, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateLightningBall()
	{
		this.animator.setAnim(4);
		this.animator.startPhase(30);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.BodyTop, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftShoulder, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateChainLightning()
	{
		this.animator.setAnim(3);
		this.animator.startPhase(30);
		this.animator.rotate(this.Mouth, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, 0.75F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, -0.25F);
		this.animator.rotate(this.LeftShoulder, -1.5F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftForeArm, -1.0F, 0.0F, 0.25F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(30);
		this.animator.startPhase(10);
		this.animator.rotate(this.Mouth, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.0F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.0F, 0.0F, -0.5F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateDragonBall()
	{
		this.animator.setAnim(11);
		this.animator.startPhase(30);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.BodyTop, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftShoulder, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForeArm, -1.0F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.25F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateLightning()
	{
		this.animator.setAnim(13);
		this.animator.startPhase(20);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Mouth, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateScream()
	{
		this.animator.setAnim(5);
		this.animator.startPhase(25);
		this.animator.move(this.BodyBottom, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.Head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1.0F, -1.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 1.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.Head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.5F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.5F);
		this.animator.rotate(this.BodyBottom, 0.5F, 0.0F, 1.5F);
		this.animator.rotate(this.RightShoulder, 0.0F, 0.0F, 1.0F);
		this.animator.rotate(this.LeftShoulder, 0.0F, 0.0F, -1.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(100);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.Head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.BodyBottom, 0.5F, 0.0F, -1.5F);
		this.animator.rotate(this.RightShoulder, 0.0F, 0.0F, 1.0F);
		this.animator.rotate(this.LeftShoulder, 0.0F, 0.0F, -1.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateSlam()
	{
		this.animator.setAnim(6);
		this.animator.startPhase(15);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.move(this.BodyBottom, 0.0F, -1F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, -1F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.RightThigh, -2F, 0.2F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.1F, 0.2F, 0.0F);
		this.animator.rotate(this.RightFemur, 2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -4F, 0.0F, -0.25F);
		this.animator.rotate(this.LeftShoulder, -4F, 0.0F, 0.25F);
		this.animator.rotate(this.RightForeArm, -1F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 16F, 1.0F);
		this.animator.move(this.RightThigh, 0.0F, 16F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 16F, 0.0F);
		this.animator.rotate(this.RightThigh, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -2.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -3F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -3F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -0.75F, 0.0F, -0.25F);
		this.animator.rotate(this.LeftForeArm, -0.75F, 0.0F, 0.25F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateStomp()
	{
		this.animator.setAnim(7);
		this.animator.startPhase(25);
		this.animator.move(this.BodyBottom, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.BodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -2.0F, 0.2F, 0.75F);
		this.animator.rotate(this.LeftThigh, 0.0F, 0.2F, 0.0F);
		this.animator.rotate(this.RightFemur, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.BodyTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.0F, 0.0F, 0.25F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.RightForeArm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BodyBottom, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.0F, 0.0F, -0.4F);
		this.animator.rotate(this.RightShoulder, 0.5F, 0.0F, 0.25F);
		this.animator.rotate(this.LeftShoulder, 0.5F, 0.0F, -0.25F);
		this.animator.rotate(this.RightForeArm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(5);
		this.animator.startPhase(20);
		this.animator.move(this.BodyBottom, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.Head, 0.0F, -2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -2.0F, 0.0F, -0.75F);
		this.animator.rotate(this.RightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.Mouth, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.BodyTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.0F, 0.0F, -0.25F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, 0.8F, 0.0F, -0.75F);
		this.animator.rotate(this.RightForeArm, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -0.8F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BodyBottom, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, -1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.0F, 0.0F, 0.4F);
		this.animator.rotate(this.RightShoulder, 0.75F, 0.0F, 0.25F);
		this.animator.rotate(this.LeftShoulder, 0.75F, 0.0F, -0.25F);
		this.animator.rotate(this.RightForeArm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}

	private void animateStunned()
	{
		this.animator.setAnim(8);
		this.animator.startPhase(10);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -1.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.move(this.BodyBottom, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -2.0F, 0.0F, 0.5F);
		this.animator.rotate(this.RightForeArm, -1.5F, 0.0F, -1.0F);
		this.animator.rotate(this.LeftShoulder, -2.0F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForeArm, -1.5F, 0.0F, 1.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(40);
		this.animator.startPhase(40);
		this.animator.move(this.BodyBottom, 0.0F, 19.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 19.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 19.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.9F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 2.2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -2.0F, 0.5F, -0.25F);
		this.animator.rotate(this.RightForeArm, -0.75F, -0.5F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -2.0F, -0.5F, 0.25F);
		this.animator.rotate(this.LeftForeArm, -0.75F, 0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(240);
		this.animator.startPhase(20);
		this.animator.move(this.BodyBottom, 0.0F, 14.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 14.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 14.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 1.8F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, -0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateSwat()
	{
		this.animator.setAnim(9);
		this.animator.startPhase(15);
		this.animator.move(this.BodyBottom, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 4.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.125F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.125F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.125F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.375F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1F, 1F, 1.5F);
		this.animator.rotate(this.LeftShoulder, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForeArm, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.BodyBottom, 0.0F, 16.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 16.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 16.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -1.0F, 0.5F, 0.0F);
		this.animator.rotate(this.LeftThigh, -1.0F, -0.5F, 0.0F);
		this.animator.rotate(this.RightFemur, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -2.0F, 0F, -0.5F);
		this.animator.rotate(this.LeftShoulder, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -0.25F, 0.0F, -1.0F);
		this.animator.rotate(this.LeftForeArm, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateDeath()
	{
		this.animator.setAnim(10);
		this.animator.startPhase(40);
		this.animator.move(this.BodyBottom, 0.0F, 4.0F, -6.0F);
		this.animator.move(this.RightThigh, 0.0F, 4.0F, -6.0F);
		this.animator.move(this.LeftThigh, 0.0F, 4.0F, -6.0F);
		this.animator.move(this.Head, 0.0F, -2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForeArm, -0.5F, 0.0F, -0.3F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.BodyBottom, 0.0F, 4.0F, -16.0F);
		this.animator.move(this.RightThigh, 0.0F, 4.0F, -16.0F);
		this.animator.move(this.LeftThigh, 0.0F, 4.0F, -16.0F);
		this.animator.move(this.Head, 0.0F, -5.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForeArm, -0.5F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftForeArm, -0.5F, 0.0F, -0.3F);
		this.animator.endPhase();
		this.animator.startPhase(80);
		this.animator.move(this.BodyBottom, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.RightThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.LeftThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftShoulder, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForeArm, 0.0F, 0.0F, -0.25F);
		this.animator.rotate(this.LeftForeArm, 0.0F, 0.0F, 0.25F);
		this.animator.endPhase();
		this.animator.startPhase(80);
		this.animator.move(this.BodyBottom, 0.0F, 26.0F, 16.0F);
		this.animator.move(this.RightThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.LeftThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -2.0F, 0.25F, 0.0F);
		this.animator.rotate(this.LeftThigh, -2.0F, -0.25F, 0.0F);
		this.animator.rotate(this.RightFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, -1.55F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.0F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.0F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForeArm, 0.0F, 0.0F, -0.25F);
		this.animator.rotate(this.LeftForeArm, 0.0F, 0.0F, 0.25F);
		this.animator.endPhase();
		this.animator.startPhase(200);
		this.animator.move(this.BodyBottom, 0.0F, 26.0F, 16.0F);
		this.animator.move(this.RightThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.LeftThigh, 0.0F, 28.0F, 16.0F);
		this.animator.move(this.Head, 0.0F, -7.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -1.55F, 0.25F, 0.0F);
		this.animator.rotate(this.LeftThigh, -1.55F, -0.25F, 0.0F);
		this.animator.rotate(this.RightFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftFemur, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Mouth, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.BodyBottom, -1.55F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.0F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.0F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForeArm, 0.0F, 0.0F, -0.3F);
		this.animator.rotate(this.LeftForeArm, 0.0F, 0.0F, 0.3F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(1520);
		this.animator.resetPhase(0);
	}
}


