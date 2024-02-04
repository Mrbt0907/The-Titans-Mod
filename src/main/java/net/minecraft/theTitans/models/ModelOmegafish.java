package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySilverfishTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.IAnimatedEntity;
import thehippomaster.AnimationAPI.client.Animator;
public class ModelOmegafish
extends ModelBase
{
	public ModelRenderer BodyCenter;
	public ModelRenderer Tail1;
	public ModelRenderer Fuzz1;
	public ModelRenderer FrontBody;
	public ModelRenderer Tail2;
	public ModelRenderer Tail3;
	public ModelRenderer Fuzz3;
	public ModelRenderer TailTip;
	public ModelRenderer Head;
	public ModelRenderer Fuzz2;
	private Animator animator;
	public ModelOmegafish()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.TailTip = new ModelRenderer(this, 13, 4);
		this.TailTip.setRotationPoint(0.0F, 0.0F, 2.0F);
		this.TailTip.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 2, 0.0F);
		this.Fuzz1 = new ModelRenderer(this, 20, 0);
		this.Fuzz1.setRotationPoint(0.0F, 2.0F, 0.0F);
		this.Fuzz1.addBox(-5.0F, -8.0F, -1.5F, 10, 8, 3, 0.0F);
		this.Tail3 = new ModelRenderer(this, 11, 0);
		this.Tail3.setRotationPoint(0.0F, 0.5F, 3.0F);
		this.Tail3.addBox(-1.0F, -0.5F, 0.0F, 2, 1, 2, 0.0F);
		this.Fuzz3 = new ModelRenderer(this, 20, 11);
		this.Fuzz3.setRotationPoint(0.0F, 1.0F, 1.5F);
		this.Fuzz3.addBox(-3.0F, -4.0F, -1.5F, 6, 4, 3, 0.0F);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, 0.5F, -2.0F);
		this.Head.addBox(-1.5F, -1.0F, -2.0F, 3, 2, 2, 0.0F);
		this.BodyCenter = new ModelRenderer(this, 0, 9);
		this.BodyCenter.setRotationPoint(0.0F, 22.0F, 1.0F);
		this.BodyCenter.addBox(-3.0F, -2.0F, -1.5F, 6, 4, 3, 0.0F);
		this.FrontBody = new ModelRenderer(this, 0, 4);
		this.FrontBody.setRotationPoint(0.0F, 0.5F, -1.5F);
		this.FrontBody.addBox(-2.0F, -1.5F, -2.0F, 4, 3, 2, 0.0F);
		this.Tail2 = new ModelRenderer(this, 0, 22);
		this.Tail2.setRotationPoint(0.0F, 0.5F, 3.0F);
		this.Tail2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3, 0.0F);
		this.Tail1 = new ModelRenderer(this, 0, 16);
		this.Tail1.setRotationPoint(0.0F, 0.5F, 1.5F);
		this.Tail1.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 3, 0.0F);
		this.Fuzz2 = new ModelRenderer(this, 20, 18);
		this.Fuzz2.setRotationPoint(0.0F, 1.5F, -0.5F);
		this.Fuzz2.addBox(-3.0F, -5.0F, -1.5F, 6, 5, 2, 0.0F);
		this.Tail3.addChild(this.TailTip);
		this.BodyCenter.addChild(this.Fuzz1);
		this.Tail2.addChild(this.Tail3);
		this.Tail2.addChild(this.Fuzz3);
		this.FrontBody.addChild(this.Head);
		this.BodyCenter.addChild(this.FrontBody);
		this.Tail1.addChild(this.Tail2);
		this.BodyCenter.addChild(this.Tail1);
		this.FrontBody.addChild(this.Fuzz2);
		this.animator = new Animator(this);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		animate((IAnimatedEntity)entity, f, f1, f2, f3, f4, f5);
		this.BodyCenter.render(f5);
	}

	public void setAngles()
	{
		this.BodyCenter.rotationPointY = 22.0F;
		this.BodyCenter.rotationPointX = 0.0F;
		this.BodyCenter.rotationPointZ = 1.0F;
		this.FrontBody.rotationPointY = 0.5F;
		this.FrontBody.rotationPointX = 0.0F;
		this.FrontBody.rotationPointZ = -1.5F;
	}

	public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.animator.update(entity);
		setAngles();
		EntitySilverfishTitan entitytitan = (EntitySilverfishTitan)entity;
		if (entitytitan.deathTicks <= 0)
		{
			if (entitytitan.getAnimID() == 0)
			{
				this.FrontBody.rotateAngleX = ((-0.01F + 0.01F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Head.rotateAngleX = ((0.01F + -0.01F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Head.rotateAngleY = -(MathHelper.cos(f * 0.25F) * 0.25F * f1);
				this.FrontBody.rotateAngleY = (MathHelper.cos(f * 0.25F - 0.5F) * 0.25F * f1);
				this.BodyCenter.rotateAngleY = (MathHelper.cos(f * 0.25F - 1F) * 0.25F * f1);
				this.Tail1.rotateAngleY = -(f3 * 3.1415927F / 180.0F / 4) + (0.01F * MathHelper.cos(f2 * 0.1F - 1F) * 3.1415927F) + (MathHelper.cos(f * 0.5F - 1.5F) * 0.25F * f1);
				this.Tail2.rotateAngleY = -(f3 * 3.1415927F / 180.0F / 4) + (0.01F * MathHelper.cos(f2 * 0.1F - 1.5F) * 3.1415927F) + (MathHelper.cos(f * 0.5F - 2F) * 0.25F * f1);
				this.Tail3.rotateAngleY = -(f3 * 3.1415927F / 180.0F / 4) + (0.01F * MathHelper.cos(f2 * 0.1F - 2F) * 3.1415927F) + (MathHelper.cos(f * 0.5F - 2.5F) * 0.25F * f1);
				this.TailTip.rotateAngleY = -(f3 * 3.1415927F / 180.0F / 4) + (0.01F * MathHelper.cos(f2 * 0.1F - 2.5F) * 3.1415927F) + (MathHelper.cos(f * 0.5F - 3F) * 0.25F * f1);
			}

			if (entitytitan.isFlying && !this.isRiding && entitytitan.getAnimID() != 2)
			{
				this.Head.rotateAngleY = -(MathHelper.cos(f * 0.35F) * 0.5F * f1);
				this.FrontBody.rotateAngleY = (MathHelper.cos(f * 0.35F - 0.5F) * 0.25F * f1);
				this.BodyCenter.rotateAngleY = (MathHelper.cos(f * 0.35F - 1F) * 0.25F * f1);
				this.Tail1.rotateAngleY = (MathHelper.cos(f * 0.35F - 1.5F) * 0.5F * f1);
				this.Tail2.rotateAngleY = (MathHelper.cos(f * 0.35F - 2F) * 0.5F * f1);
				this.Tail3.rotateAngleY = (MathHelper.cos(f * 0.35F - 2.5F) * 0.5F * f1);
				this.TailTip.rotateAngleY = (MathHelper.cos(f * 0.35F - 3F) * 0.5F * f1);
				this.Tail1.rotateAngleX += (MathHelper.cos(f * 0.35F - 1.5F) * 0.1F * f1) - 0.25F + (entitytitan.limbSwingAmount / 4);
				this.Tail2.rotateAngleX += (MathHelper.cos(f * 0.35F - 2F) * 0.1F * f1) - 0.25F + (entitytitan.limbSwingAmount / 4);
				this.Tail3.rotateAngleX += (MathHelper.cos(f * 0.35F - 2.5F) * 0.1F * f1) - 0.25F + (entitytitan.limbSwingAmount / 4);
				this.TailTip.rotateAngleX += (MathHelper.cos(f * 0.35F - 3F) * 0.1F * f1) - 0.25F + (entitytitan.limbSwingAmount / 4);
			}

			float faceYaw = f3 * 3.1415927F / 180.0F;
			float facePitch = f4 * 3.1415927F / 180.0F;
			this.Head.rotateAngleX += facePitch * 0.45F;
			this.Head.rotateAngleY += faceYaw * 0.45F;
			this.FrontBody.rotateAngleX += facePitch * 0.45F;
			this.FrontBody.rotateAngleY += faceYaw * 0.45F;
			if (entitytitan.getAnimID() == 11)
			{
				switch (entitytitan.getATAAID())
				{
					case 0:
					this.animateAntiTitanAttack1();
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

			if (entitytitan.getWaiting())
			{
				this.animator.rotate(this.Head, 1.5F, 0.0F, 0.0F);
				this.animator.rotate(this.FrontBody, 1.25F, 0.0F, 0.0F);
				this.animator.rotate(this.Tail1, -1.25F, 0.0F, 0.0F);
				this.animator.rotate(this.Tail2, -1.25F, 0.0F, 0.0F);
				this.animator.rotate(this.Tail3, -1.25F, 0.0F, 0.0F);
				this.animator.rotate(this.TailTip, -1.25F, 0.0F, 0.0F);
				this.animator.rotate(this.BodyCenter, -2.0F, 0.0F, -1.5F);
				this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
			}

			animateBodySlam();
			animateIncapacitated();
			animateLightningShot();
			animateTailSwipeL();
			animateTailSwipeR();
			animateHeadButt();
			animateTailSmash();
			animateUnburrow();
			animateBurrow();
			animateBirth();
		}

		else
		{
			animateDeath();
		}
	}

	private void animateAntiTitanAttack1()
	{
		this.animator.setAnim(11);
		this.animator.startPhase(20);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, 6F);
		this.animator.rotate(this.Tail1, -0.05F, -0.6F, 0.0F);
		this.animator.rotate(this.Tail2, -0.05F, -0.6F, 0.0F);
		this.animator.rotate(this.Tail3, -0.05F, -0.6F, 0.0F);
		this.animator.rotate(this.TailTip, -0.05F, -0.6F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.15F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, -12F);
		this.animator.rotate(this.Tail1, 1.0F, 0.6F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.6F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, 0.6F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.6F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 4.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, -0.15F, -0.2F);
		this.animator.rotate(this.FrontBody, 0.0F, -0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.BodyCenter, 0.0F, 6.3F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateAntiTitanAttack2()
	{
		this.animator.setAnim(11);
		this.animator.startPhase(20);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, 6F);
		this.animator.rotate(this.Tail1, -0.05F, 0.6F, 0.0F);
		this.animator.rotate(this.Tail2, -0.05F, 0.6F, 0.0F);
		this.animator.rotate(this.Tail3, -0.05F, 0.6F, 0.0F);
		this.animator.rotate(this.TailTip, -0.05F, 0.6F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.75F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, -0.15F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, -0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, -12F);
		this.animator.rotate(this.Tail1, 1.0F, -0.6F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, -0.6F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, -0.6F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, -0.6F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, -4.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.15F, 0.2F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.BodyCenter, 0.0F, -6.3F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateAntiTitanAttack3()
	{
		this.animator.setAnim(11);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.Tail1, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 2.0F, 0.0F, -1.0F);
		this.animator.move(this.BodyCenter, 0.0F, -8.0F, -16F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateAntiTitanAttack4()
	{
		this.animator.setAnim(11);
		this.animator.startPhase(20);
		this.animator.rotate(this.Tail1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.8F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BodyCenter, 0.0F, -8.0F, -16F);
		this.animator.rotate(this.Tail1, -0.5F, 0.2F, 0.0F);
		this.animator.rotate(this.Tail2, -0.5F, 0.2F, 0.0F);
		this.animator.rotate(this.Tail3, -0.5F, 0.2F, 0.0F);
		this.animator.rotate(this.TailTip, -0.5F, 0.2F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.5F, -4.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.15F, 0.2F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.BodyCenter, 6.8F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateBurrow()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(0);
		this.animator.move(this.BodyCenter, 0.0F, -8.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -2.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, -8.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -0.4F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, -28.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, -6.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, -2.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.4F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 8.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 16.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.4F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.6F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 24.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateUnburrow()
	{
		this.animator.setAnim(2);
		this.animator.startPhase(0);
		this.animator.rotate(this.Head, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 8.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(10);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -0.4F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, -24.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateTailSmash()
	{
		this.animator.setAnim(3);
		this.animator.startPhase(30);
		this.animator.rotate(this.Tail1, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail3, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.TailTip, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.Head, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.25F, 0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Tail1, 0.9F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.7F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail3, 0.6F, -0.5F, 0.0F);
		this.animator.rotate(this.TailTip, 0.6F, -0.5F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, -0.8F, 0.0F);
		this.animator.rotate(this.Head, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -0.4F, 0.8F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.8F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateHeadButt()
	{
		this.animator.setAnim(4);
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -2.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, 2.0F);
		this.animator.rotate(this.Tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, -4.0F);
		this.animator.rotate(this.Tail1, 0.0F, 0.75F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.6F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateTailSwipeR()
	{
		this.animator.setAnim(5);
		this.animator.startPhase(20);
		this.animator.rotate(this.Tail1, -0.05F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, -0.05F, -0.4F, 0.0F);
		this.animator.rotate(this.Tail3, -0.05F, -0.3F, 0.0F);
		this.animator.rotate(this.TailTip, -0.05F, -0.3F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.15F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Tail1, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.4F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.3F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 3.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, -0.15F, -0.2F);
		this.animator.rotate(this.FrontBody, 0.0F, -0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateTailSwipeL()
	{
		this.animator.setAnim(6);
		this.animator.startPhase(20);
		this.animator.rotate(this.Tail1, -0.05F, 0.5F, 0.0F);
		this.animator.rotate(this.Tail2, -0.05F, 0.4F, 0.0F);
		this.animator.rotate(this.Tail3, -0.05F, 0.3F, 0.0F);
		this.animator.rotate(this.TailTip, -0.05F, 0.3F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, -0.15F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, -0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Tail1, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, -0.3F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, -3.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.15F, -0.2F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.15F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateLightningShot()
	{
		this.animator.setAnim(7);
		this.animator.startPhase(20);
		this.animator.rotate(this.Tail1, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Tail1, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Tail1, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(25);
	}

	private void animateIncapacitated()
	{
		this.animator.setAnim(8);
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -0.7F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.7F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.0F, -1.25F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.Head, 0.5F, 0.5F, -0.3F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.0F, -1.25F);
		this.animator.rotate(this.Tail1, 0.1F, -0.1F, 0.1F);
		this.animator.rotate(this.Tail2, 0.0F, -0.2F, -0.2F);
		this.animator.rotate(this.Tail3, 0.0F, 0.1F, 0.2F);
		this.animator.rotate(this.TailTip, 0.0F, 0.2F, 0.3F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(300);
		this.animator.startPhase(30);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.Head, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.Head, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateBodySlam()
	{
		this.animator.setAnim(9);
		this.animator.startPhase(30);
		this.animator.rotate(this.Tail1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Tail1, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Tail2, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, -0.25F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Tail1, 0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Tail2, 0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, 0.25F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Tail1, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Tail2, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, -0.25F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Tail1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.Tail1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.8F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(60);
		this.animator.rotate(this.Head, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.Tail1, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -0.8F, 0.0F, -1.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(140);
		this.animator.rotate(this.Head, 1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.FrontBody, 1.5F, 0.0F, -0.75F);
		this.animator.rotate(this.Tail1, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -2.0F, 0.0F, -1.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(40);
		this.animator.startPhase(30);
		this.animator.rotate(this.Head, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, -0.75F);
		this.animator.rotate(this.Tail1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.0F, 0.0F, -1.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.Head, 1.0F, 0.0F, -0.3F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, -0.75F);
		this.animator.rotate(this.Tail1, -0.75F, 1.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.0F, 0.0F, -1.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateDeath()
	{
		this.animator.setAnim(10);
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -0.7F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, -12.0F, 0.0F);
		this.animator.rotate(this.Tail1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Tail1, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Tail1, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.4F);
		this.animator.rotate(this.FrontBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Tail1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.4F);
		this.animator.rotate(this.FrontBody, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Tail1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.4F);
		this.animator.rotate(this.FrontBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Tail1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -0.6F, 0.0F, 0.5F);
		this.animator.rotate(this.FrontBody, -0.5F, 0.0F, 0.3F);
		this.animator.rotate(this.BodyCenter, -3.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Tail1, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(1760);
		this.animator.resetPhase(0);
	}

	private void animateBirth()
	{
		this.animator.setAnim(13);
		this.animator.startPhase(0);
		this.animator.rotate(this.Head, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail1, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -2.0F, 0.0F, -1.5F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(60);
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, 1.25F, 0.0F, -0.5F);
		this.animator.rotate(this.FrontBody, 1.25F, 0.0F, -0.5F);
		this.animator.rotate(this.Tail1, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, -2.0F, 0.0F, -1.5F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.3F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.75F);
		this.animator.rotate(this.Tail1, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail2, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.0F, 0.0F, -1.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.3F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.75F);
		this.animator.rotate(this.Tail1, -0.75F, 1.0F, 0.0F);
		this.animator.rotate(this.Tail2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Tail3, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.TailTip, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 1.0F, 0.0F, -1.0F);
		this.animator.move(this.BodyCenter, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.FrontBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.BodyCenter, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}
}


