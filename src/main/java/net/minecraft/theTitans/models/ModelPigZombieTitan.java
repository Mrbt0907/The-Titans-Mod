package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityPigZombieTitan;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import thehippomaster.AnimationAPI.IAnimatedEntity;
import thehippomaster.AnimationAPI.client.Animator;
public class ModelPigZombieTitan
extends ModelBase
{
	public ModelRenderer Torso;
	public ModelRenderer Hat;
	public ModelRenderer LeftThigh;
	public ModelRenderer RightThigh;
	public ModelRenderer MiddleBody;
	public ModelRenderer TopBody;
	public ModelRenderer Head;
	public ModelRenderer Head2;
	public ModelRenderer LeftShoulder;
	public ModelRenderer RightShoulder;
	public ModelRenderer LeftForearm;
	public ModelRenderer RightForearm;
	public ModelRenderer HeldItem;
	public ModelRenderer LeftCalf;
	public ModelRenderer RightCalf;
	private Animator animator;
	public ModelPigZombieTitan()
	{
		this(0.0F);
	}

	public ModelPigZombieTitan(float p_i1147_1_)
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.Hat = new ModelRenderer(this, 32, 0);
		this.Hat.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Hat.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i1147_1_ + 0.5F);
		this.LeftShoulder = new ModelRenderer(this, 40, 16);
		this.LeftShoulder.mirror = true;
		this.LeftShoulder.setRotationPoint(6.0F, -2.0F, 0.0F);
		this.LeftShoulder.addBox(-2.0F, -2.0F, -2.0F, 4, 6, 4, p_i1147_1_);
		this.RightShoulder = new ModelRenderer(this, 40, 16);
		this.RightShoulder.setRotationPoint(-6.0F, -2.0F, 0.0F);
		this.RightShoulder.addBox(-2.0F, -2.0F, -2.0F, 4, 6, 4, p_i1147_1_);
		this.RightForearm = new ModelRenderer(this, 40, 26);
		this.RightForearm.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.RightForearm.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i1147_1_);
		this.TopBody = new ModelRenderer(this, 16, 16);
		this.TopBody.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.TopBody.addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, p_i1147_1_);
		this.LeftForearm = new ModelRenderer(this, 40, 26);
		this.LeftForearm.mirror = true;
		this.LeftForearm.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.LeftForearm.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i1147_1_);
		this.MiddleBody = new ModelRenderer(this, 16, 24);
		this.MiddleBody.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.MiddleBody.addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, p_i1147_1_);
		this.RightThigh = new ModelRenderer(this, 0, 16);
		this.RightThigh.setRotationPoint(-2.0F, 12.0F, 0.0F);
		this.RightThigh.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i1147_1_);
		this.RightCalf = new ModelRenderer(this, 0, 26);
		this.RightCalf.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.RightCalf.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i1147_1_);
		this.HeldItem = new ModelRenderer(this, 32, 32);
		this.HeldItem.setRotationPoint(0.0F, 4.0F, 0.0F);
		this.HeldItem.addBox(0.0F, -12.0F, -12.0F, 0, 16, 16, 0.0F);
		this.Torso = new ModelRenderer(this, 16, 32);
		this.Torso.setRotationPoint(0.0F, 12.0F, 0.0F);
		this.Torso.addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, p_i1147_1_);
		this.LeftThigh = new ModelRenderer(this, 0, 16);
		this.LeftThigh.mirror = true;
		this.LeftThigh.setRotationPoint(2.0F, 12.0F, 0.0F);
		this.LeftThigh.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i1147_1_);
		this.LeftCalf = new ModelRenderer(this, 0, 26);
		this.LeftCalf.mirror = true;
		this.LeftCalf.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.LeftCalf.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i1147_1_);
		this.RightThigh.addChild(this.RightCalf);
		this.TopBody.addChild(this.LeftShoulder);
		this.TopBody.addChild(this.RightShoulder);
		this.LeftThigh.addChild(this.LeftCalf);
		this.RightShoulder.addChild(this.RightForearm);
		this.MiddleBody.addChild(this.TopBody);
		this.LeftShoulder.addChild(this.LeftForearm);
		this.Torso.addChild(this.MiddleBody);
		this.RightForearm.addChild(this.HeldItem);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i1147_1_);
		this.TopBody.addChild(this.Head);
		this.Head.addChild(this.Hat);
		this.Head2 = new ModelRenderer(this, 0, 0);
		this.Head2.addBox(-4.0F, -10.0F, -4.0F, 8, 8, 8, p_i1147_1_ + 2.0F);
		this.Head.addChild(this.Head2);
		this.animator = new Animator(this);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		EntityPigZombieTitan entitytitan = (EntityPigZombieTitan)entity;
		animate((IAnimatedEntity)entity, f, f1, f2, f3, f4, f5);
		if (entitytitan.isChild())
		Head2.isHidden = false;
		else
		Head2.isHidden = true;
		if (this.isChild)
		{
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
			this.RightThigh.render(f5);
			this.Torso.render(f5);
			this.LeftThigh.render(f5);
			GL11.glPopMatrix();
		}

		else
		{
			this.RightThigh.render(f5);
			this.Torso.render(f5);
			this.LeftThigh.render(f5);
		}
	}

	public void setAngles()
	{
		this.LeftShoulder.rotateAngleX = -1.5707964F;
		this.RightShoulder.rotateAngleX = -1.5707964F;
		this.HeldItem.rotateAngleX = 0.7853982F;
		this.RightThigh.rotationPointZ = 0.0F;
		this.LeftThigh.rotationPointZ = 0.0F;
		this.Torso.rotationPointZ = 0.0F;
		this.Torso.rotationPointX = 0.0F;
		this.Torso.rotationPointY = 12.0F;
		this.HeldItem.rotationPointZ = 0.0F;
		this.HeldItem.rotationPointX = 0.0F;
		this.HeldItem.rotationPointY = 4.0F;
		this.RightThigh.rotationPointY = 12.0F;
		this.LeftThigh.rotationPointY = 12.0F;
	}

	public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		EntityPigZombieTitan entitytitan = (EntityPigZombieTitan)entity;
		this.animator.update(entity);
		setAngles();
		float fo = 0.6662F / 3F;
		if (entitytitan.deathTicks <= 0)
		{
			float f6 = MathHelper.cos(f2 * 0.1F);
			if (entitytitan.getAnimID() == 0)
			{
				this.Torso.rotateAngleX = ((0.001F + 0.005F * f6) * 3.1415927F);
				f6 = MathHelper.cos(f2 * 0.1F - 0.5F);
				this.MiddleBody.rotateAngleX = ((0.001F + 0.005F * f6) * 3.1415927F);
				f6 = MathHelper.cos(f2 * 0.1F - 1F);
				this.TopBody.rotateAngleX = ((0.001F + 0.005F * f6) * 3.1415927F);
				f6 = MathHelper.cos(f2 * 0.1F - 1.5F);
				this.Head.rotateAngleX = ((0.001F + 0.0075F * f6) * 3.1415927F);
				this.RightShoulder.rotateAngleY = ((-0.001F + 0.01F * f6) * 3.1415927F);
				this.LeftShoulder.rotateAngleY = ((0.001F + -0.01F * f6) * 3.1415927F);
			}

			if (this.isRiding)
			{
				this.RightThigh.rotateAngleX = -1.5707964F;
				this.LeftThigh.rotateAngleX = -1.5707964F;
				this.RightThigh.rotateAngleY = 0.31415927F;
				this.LeftThigh.rotateAngleY = -0.31415927F;
				this.RightCalf.rotateAngleX = 1.0471976F;
				this.LeftCalf.rotateAngleX = 1.0471976F;
			}

			else
			{
				if (entitytitan.getAnimID() == 0)
				{
					this.Torso.rotateAngleZ = (MathHelper.cos(f * fo) * 0.1F * f1);
					this.MiddleBody.rotateAngleZ = (MathHelper.cos(f * fo - 1F) * 0.1F * f1);
					this.TopBody.rotateAngleZ = (MathHelper.cos(f * fo - 2F) * 0.1F * f1);
					this.RightThigh.rotateAngleX = (MathHelper.cos(f * fo - 0.5F) * 1.25F * f1);
					this.LeftThigh.rotateAngleX = (MathHelper.cos(f * fo + 2.6415927F) * 1.25F * f1);
					this.RightCalf.rotateAngleX = (MathHelper.cos(f * fo + 3.1415927F) * 1.25F * f1);
					this.LeftCalf.rotateAngleX = (MathHelper.cos(f * fo) * 1.25F * f1);
					this.Torso.rotationPointY = 12.0F - (MathHelper.cos(f * (fo * 2) + 1F) * 2F * f1);
					this.RightThigh.rotationPointY = 12.0F - (MathHelper.cos(f * (fo * 2) + 1F) * 2F * f1);
					this.LeftThigh.rotationPointY = 12.0F - (MathHelper.cos(f * (fo * 2) + 1F) * 2F * f1);
					if (this.RightCalf.rotateAngleX < 0.0F)
					this.RightCalf.rotateAngleX = 0.0F;
					if (this.LeftCalf.rotateAngleX < 0.0F)
					this.LeftCalf.rotateAngleX = 0.0F;
				}
			}

			float faceYaw = f3 * 3.1415927F / 180.0F;
			float facePitch = f4 * 3.1415927F / 180.0F;
			if (entitytitan.isFlying && !this.isRiding)
			{
				this.Torso.rotateAngleZ = 0F;
				this.MiddleBody.rotateAngleZ = 0F;
				this.TopBody.rotateAngleZ = 0F;
				this.Head.rotateAngleZ = 0F;
				this.RightShoulder.rotateAngleX -= (float)(entitytitan.limbSwingAmount);
				this.LeftShoulder.rotateAngleX -= (float)(entitytitan.limbSwingAmount);
				this.Head.rotateAngleX -= (float)(entitytitan.limbSwingAmount);
				this.Torso.rotateAngleX += (float)(entitytitan.limbSwingAmount);
				this.RightThigh.rotateAngleZ = 0.25F;
				this.LeftThigh.rotateAngleZ = -0.25F;
				this.RightThigh.rotateAngleY = -0.25F;
				this.LeftThigh.rotateAngleY = 0.25F;
				this.RightThigh.rotateAngleX = ((MathHelper.cos(f2 * 0.2F)) * 0.25F) - (float)(entitytitan.motionY / 5) + entitytitan.limbSwingAmount;
				this.LeftThigh.rotateAngleX = ((MathHelper.cos(f2 * 0.2F - 3.1415927F)) * 0.25F) - (float)(entitytitan.motionY / 5) + entitytitan.limbSwingAmount;
				this.RightCalf.rotateAngleX = 0.5F - ((MathHelper.cos(f2 * 0.2F + 0.5F)) * 0.5F);
				this.LeftCalf.rotateAngleX = 0.5F - ((MathHelper.cos(f2 * 0.2F - 2.6415927F)) * 0.5F);
			}

			if (entitytitan.getAnimID() == 0)
			{
				this.Head.rotateAngleX += facePitch * 0.3F;
				this.Head.rotateAngleY += faceYaw * 0.3F;
				this.TopBody.rotateAngleX += facePitch * 0.3F;
				this.TopBody.rotateAngleY += faceYaw * 0.3F;
				this.MiddleBody.rotateAngleX += facePitch * 0.3F;
				this.MiddleBody.rotateAngleY += faceYaw * 0.3F;
			}

			else
			{
				this.Head.rotateAngleX += facePitch * 0.9F;
				this.Head.rotateAngleY += faceYaw * 0.9F;
			}

			if (entitytitan.getAnimID() == 11 && (entitytitan.getAnimTick() > 30 && entitytitan.getAnimTick() < 160))
			{
				this.Head.rotateAngleZ = ((MathHelper.cos(f2 * 0.25F - 3F)) * 0.5F);
				this.TopBody.rotateAngleZ = ((MathHelper.cos(f2 * 0.25F - 2F)) * 0.25F);
				this.MiddleBody.rotateAngleZ = ((MathHelper.cos(f2 * 0.25F - 1F)) * 0.25F);
				this.Torso.rotateAngleZ = ((MathHelper.cos(f2 * 0.25F)) * 0.25F);
			}

			if (entitytitan.getAnimID() == 12 && (entitytitan.getAnimTick() > 30 && entitytitan.getAnimTick() < 50))
			{
				this.RightForearm.rotateAngleX = ((0.1F * MathHelper.cos(f2 * 2F)) * 3.1415927F);
				this.LeftForearm.rotateAngleX = -((0.1F * MathHelper.cos(f2 * 2F)) * 3.1415927F);
			}

			if (entitytitan.getAnimID() == 6 && entitytitan.getAnimTick() > 20 && entitytitan.getAnimTick() < 114)
			{
				this.Torso.rotateAngleX = ((MathHelper.cos((float)entitytitan.getAnimTick() * 0.1F)) * 0.3F);
				this.MiddleBody.rotateAngleX = ((MathHelper.cos((float)entitytitan.getAnimTick() * 0.1F - 0.5F)) * 0.4F);
				this.TopBody.rotateAngleX = ((MathHelper.cos((float)entitytitan.getAnimTick() * 0.1F - 1F)) * 0.3F);
				this.Torso.rotateAngleZ = ((MathHelper.cos((float)entitytitan.getAnimTick() * 0.1F)) * 0.25F);
			}

			if (entitytitan.getAnimID() == 1)
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
				this.animator.move(this.Torso, 0.0F, 11F, 0.0F);
				this.animator.move(this.RightThigh, 0.0F, 10F, 0.0F);
				this.animator.move(this.LeftThigh, 0.0F, 10F, 0.0F);
				this.animator.rotate(this.RightThigh, -1.4F, 1.25F, 0.0F);
				this.animator.rotate(this.LeftThigh, -1.4F, -1.25F, 0.0F);
				this.animator.rotate(this.RightCalf, 1.0F, 0.0F, 0.0F);
				this.animator.rotate(this.LeftCalf, 1.0F, 0.0F, 0.0F);
				this.animator.rotate(this.Head, 0.6F, 0.0F, 0.0F);
				this.animator.rotate(this.TopBody, 0.3F, 0.0F, 0.0F);
				this.animator.rotate(this.MiddleBody, 0.3F, 0.0F, 0.0F);
				this.animator.rotate(this.Torso, 0.3F, 0.0F, 0.0F);
				this.animator.rotate(this.RightShoulder, -0.75F, 0.75F, -0.75F);
				this.animator.rotate(this.LeftShoulder, 0.0F, -0.75F, 0.75F);
				this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
				this.animator.rotate(this.LeftForearm, 0.75F, 0.0F, 0.0F);
				this.animator.rotate(this.HeldItem, 3.0F, 0.25F, -0.5F);
			}

			animateBirth();
			animateRoar();
			animateRangedAttackThrow();
			animateSlam();
			animateSwat();
			animateSwordDig();
			animateLightning();
			animateStomp();
			animateDownwardSlash();
			animateStun();
			animateSidewaySlash();
		}

		else
		{
			animateDeath();
		}
	}

	/*private void animatePunch()
	{
		this.animator.setAnim(3);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.rotate(this.TopBody, 0.0F, 0.0F, -0.5F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 3.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.Torso, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 4.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.6F, 0.5F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.6F, -0.5F, 0.0F);
		this.animator.rotate(this.RightCalf, 1.2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 1.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.0F, 0.0F, -0.5F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.Torso, 0.9F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.9F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 3.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(30);

	}

	*/
	private void animateAntiTitanAttack1()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 2.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 5.0F);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, -1.5F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Torso, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.RightShoulder, 3.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -1.5F, 0F, 0F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, -1.0F);
		this.animator.rotate(this.LeftForearm, -1.5F, 0F, 0F);
		this.animator.rotate(this.HeldItem, -12.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.0F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 1.5F, 0.0F);
		this.animator.rotate(this.TopBody, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, -1.2F);
		this.animator.rotate(this.RightForearm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 3.0F, 0.0F, -1.0F);
		this.animator.rotate(this.LeftForearm, -1.0F, 0F, 0F);
		this.animator.rotate(this.HeldItem, -11.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(10);
	}

	private void animateAntiTitanAttack2()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(8);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.5F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(12);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.0F);
		this.animator.rotate(this.RightThigh, 1.8F, -10F, 0.5F);
		this.animator.rotate(this.RightCalf, 0.0F, 0F, 0.5F);
		this.animator.rotate(this.LeftThigh, -0.5F, -10F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 1.75F, -10F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.RightThigh, 0.0F, -12.6F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.0F, -12.6F, 0.0F);
		this.animator.rotate(this.Torso, 0.0F, -12.6F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(0);
	}

	private void animateAntiTitanAttack3()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 2.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 5.0F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 1.5F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Torso, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 2F, -1F, -1F);
		this.animator.rotate(this.LeftForearm, -2F, 0F, 0F);
		this.animator.rotate(this.HeldItem, 3.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.0F);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, -1.5F, 0.0F);
		this.animator.rotate(this.TopBody, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.6F, 0.0F, -1F);
		this.animator.rotate(this.LeftShoulder, -0.5F, 0.0F, 1.0F);
		this.animator.rotate(this.RightForearm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 3.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(10);
	}

	private void animateAntiTitanAttack4()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 5.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 5.0F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1F, 0.0F, 0.5F);
		this.animator.rotate(this.RightForearm, -1F, 0.0F, -0.5F);
		this.animator.rotate(this.HeldItem, -1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, -12.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, -12.0F);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.5F, 0.0F, 0.5F);
		this.animator.rotate(this.RightForearm, -0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(10);
	}

	private void animateSwat()
	{
		this.animator.setAnim(2);
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightCalf, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 1.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -1.0F, 0.0F, 1.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -1.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 4.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 4.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 4.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -1.0F, 0.5F, 0.0F);
		this.animator.rotate(this.LeftThigh, -1.0F, -0.5F, 0.0F);
		this.animator.rotate(this.RightCalf, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -1.0F, 0.0F, -1.0F);
		this.animator.rotate(this.LeftForearm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.75F, 1.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 4.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateSwordDig()
	{
		this.animator.setAnim(4);
		this.animator.startPhase(25);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.Torso, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -2.0F, 0.2F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.0F, -0.2F, 0.0F);
		this.animator.rotate(this.RightCalf, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.0F, 0.2F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.0F, -0.2F, 0.0F);
		this.animator.rotate(this.RightForearm, 0.0F, -0.4F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.4F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 3.0F, 1.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Head, -1.2F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.0F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, -1.0F, 0.0F, -0.75F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForearm, -0.25F, 0.0F, 0.5F);
		this.animator.rotate(this.HeldItem, 3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(40);
		this.animator.startPhase(30);
		this.animator.move(this.Torso, 0.0F, 3.0F, 1.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.0F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, -1.0F, 0.0F, -0.75F);
		this.animator.rotate(this.RightForearm, 0.0F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.5F);
		this.animator.rotate(this.HeldItem, 2F, 0.0F, 2.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateSlam()
	{
		this.animator.setAnim(3);
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 0.6F, 0.0F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.move(this.Torso, 0.0F, -1F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -1.6F, 0.2F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.0F, 0.2F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.9F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -2F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -2F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, -0.3F, 0.0F, -0.75F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.move(this.Torso, 0.0F, 3.0F, 1.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForearm, -0.5F, 0.0F, 0.5F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateLightning()
	{
		this.animator.setAnim(5);
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 0.0F, 0.0F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -2.0F, 0.2F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.0F, -0.2F, 0.0F);
		this.animator.rotate(this.RightCalf, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.35F, 0.4F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.35F, -0.4F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -2.0F, 0.2F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.0F, -0.2F, 0.0F);
		this.animator.rotate(this.RightCalf, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.35F, 0.4F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.35F, -0.4F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(5);
		this.animator.startPhase(5);
		this.animator.move(this.Torso, 0.0F, 3.0F, 1.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.9F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, -0.9F, 0.0F, -0.75F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftForearm, -0.25F, 0.0F, 0.5F);
		this.animator.rotate(this.HeldItem, 2F, 0.0F, 2.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateStomp()
	{
		this.animator.setAnim(6);
		this.animator.startPhase(25);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.5F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.Torso, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -2.0F, 0.2F, 0.75F);
		this.animator.rotate(this.LeftThigh, 0.0F, 0.2F, 0.0F);
		this.animator.rotate(this.RightCalf, 1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftCalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.5F);
		this.animator.rotate(this.TopBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.0F, 0.0F, 0.25F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, -0.75F, 0.0F, 0.75F);
		this.animator.rotate(this.RightForearm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.5F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.5F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.0F, 0.0F, -0.4F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.RightForearm, -0.3F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftForearm, -0.3F, 0.0F, -0.75F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(5);
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, -2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -2.0F, 0.0F, -0.75F);
		this.animator.rotate(this.RightCalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.TopBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.0F, 0.0F, -0.25F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, -0.75F, 0.0F, 0.75F);
		this.animator.rotate(this.RightForearm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.5F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.0F, 0.0F, 0.4F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, -0.75F, 0.0F, 0.75F);
		this.animator.rotate(this.RightForearm, -0.3F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftForearm, -0.3F, 0.0F, 0.75F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}

	private void animateDownwardSlash()
	{
		this.animator.setAnim(7);
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, -0.2F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.1F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.1F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1.0F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, 0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 2.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 5.5F);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, -1.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Torso, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 0.0F, -1.5F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.75F);
		this.animator.rotate(this.HeldItem, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(50);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 1.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.55F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.5F, -1.5F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 2.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 5.5F);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 2.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateStun()
	{
		this.animator.setAnim(8);
		this.animator.startPhase(0);
		this.animator.move(this.Torso, 0.0F, 1.0F, -3.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -2.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -3.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, -0.5F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.5F, 1.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.55F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.55F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.55F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.55F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.55F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.55F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(40);
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(160);
		this.animator.startPhase(40);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1.5F, -0.75F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 1.5F, 0.75F, 0.0F);
		this.animator.rotate(this.HeldItem, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 0.0F, 0.0F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.move(this.Torso, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.25F, 0.25F);
		this.animator.rotate(this.LeftThigh, -0.25F, -0.25F, -0.25F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.25F, 1.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.25F, -1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(60);
		this.animator.resetPhase(20);
	}

	private void animateSidewaySlash()
	{
		this.animator.setAnim(9);
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.1F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.1F, 0.0F);
		this.animator.rotate(this.RightShoulder, 1.0F, 0.0F, 0.75F);
		this.animator.rotate(this.LeftShoulder, 0.8F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 2.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 5.5F);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.25F, 0.5F, -0.5F);
		this.animator.rotate(this.MiddleBody, -0.25F, 0.5F, -0.5F);
		this.animator.rotate(this.Torso, -0.25F, 0.5F, -0.5F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, -1.0F);
		this.animator.rotate(this.LeftShoulder, 0.0F, -1.5F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.75F);
		this.animator.rotate(this.HeldItem, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, -0.25F, -0.25F);
		this.animator.rotate(this.MiddleBody, 0.5F, -0.25F, -0.25F);
		this.animator.rotate(this.Torso, 1.0F, -0.25F, -0.5F);
		this.animator.rotate(this.RightShoulder, -1.0F, 0.0F, -1.0F);
		this.animator.rotate(this.LeftShoulder, 1.0F, 0.0F, -0.5F);
		this.animator.rotate(this.RightForearm, 0.5F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 2.0F, 0.0F, -0.5F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateDeath()
	{
		this.animator.setAnim(10);
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 2.0F, 4.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 4.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 4.0F);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.5F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, -0.5F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 2.0F, 8.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 8.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 8.0F);
		this.animator.rotate(this.RightThigh, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.5F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, -0.5F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 2.0F, 9.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 9.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 9.0F);
		this.animator.rotate(this.RightThigh, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.35F, 0.4F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.35F, -0.4F, 0.0F);
		this.animator.rotate(this.RightForearm, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 2.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(80);
		this.animator.move(this.Torso, 0.0F, 10.0F, 1.0F);
		this.animator.move(this.RightThigh, 0.0F, 10.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 10.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 1.4F, -0.5F, 0.0F);
		this.animator.rotate(this.LeftThigh, 1.4F, 0.5F, 0.0F);
		this.animator.rotate(this.RightCalf, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.9F, 0.0F);
		this.animator.rotate(this.TopBody, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 1.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.5F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftShoulder, -1.5F, 0.0F, 0.5F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.25F, 0.0F, 0.0F);
		this.animator.move(this.HeldItem, 0F, 0F, 30.0F);
		this.animator.endPhase();
		this.animator.startPhase(100);
		this.animator.move(this.Torso, 0.0F, 10.0F, 1.0F);
		this.animator.move(this.RightThigh, 0.0F, 10.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 10.0F, 0.0F);
		this.animator.rotate(this.RightThigh, 1.6F, -0.5F, 0.0F);
		this.animator.rotate(this.LeftThigh, 1.6F, 0.5F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.9F, 0.0F);
		this.animator.rotate(this.TopBody, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 1.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.3F, 0.0F, -0.5F);
		this.animator.rotate(this.LeftShoulder, -1.3F, 0.0F, 0.5F);
		this.animator.rotate(this.RightForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.HeldItem, 0F, 0F, 30.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(1700);
		this.animator.resetPhase(0);
	}

	private void animateRoar()
	{
		this.animator.setAnim(11);
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.75F);
		this.animator.rotate(this.HeldItem, 0.0F, 0.0F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.move(this.Torso, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.25F, 0.25F);
		this.animator.rotate(this.LeftThigh, -0.25F, -0.25F, -0.25F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.25F, 1.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.25F, -1.0F, 0.0F);
		this.animator.rotate(this.RightForearm, 0.0F, -1.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.move(this.Torso, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.25F, 0.25F);
		this.animator.rotate(this.LeftThigh, -0.25F, -0.25F, -0.25F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.25F, 1.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.25F, -1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(140);
		this.animator.resetPhase(40);
	}

	private void animateRangedAttackThrow()
	{
		this.animator.setAnim(12);
		this.animator.startPhase(25);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 4.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.Torso, 0.0F, 2.0F, 5.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 5.5F);
		this.animator.rotate(this.RightThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.65F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, -1.5F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.75F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.75F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.5F, 0.0F, 0.8F);
		this.animator.rotate(this.LeftShoulder, -0.5F, 0.0F, -0.8F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, -0.8F);
		this.animator.rotate(this.LeftForearm, -0.25F, 0.0F, 0.8F);
		this.animator.rotate(this.HeldItem, 4.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 1.0F, -5.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, -5.5F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, -5.5F);
		this.animator.rotate(this.RightThigh, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 4.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}

	private void animateBirth()
	{
		this.animator.setAnim(13);
		this.animator.startPhase(0);
		this.animator.move(this.Torso, 0.0F, 11F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 10F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 10F, 0.0F);
		this.animator.rotate(this.RightThigh, -1.4F, 1.25F, 0.0F);
		this.animator.rotate(this.LeftThigh, -1.4F, -1.25F, 0.0F);
		this.animator.rotate(this.RightCalf, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.75F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 0.0F, -0.75F, 0.75F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 3.0F, 0.25F, -0.5F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(60);
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 11F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 10F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 10F, 0.0F);
		this.animator.rotate(this.RightThigh, -1.4F, 1.25F, 0.0F);
		this.animator.rotate(this.LeftThigh, -1.4F, -1.25F, 0.0F);
		this.animator.rotate(this.RightCalf, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.75F, 0.75F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 0.0F, -0.75F, 0.75F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 3.0F, 0.25F, -0.5F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 7F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 7F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 7F, 0.0F);
		this.animator.rotate(this.RightThigh, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -1.0F, -1.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 2.3F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 1.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -1.25F, 0.75F, -0.75F);
		this.animator.rotate(this.LeftShoulder, -0.5F, -0.75F, 0.75F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 1.75F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 3.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 5F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 5F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 5F, 0.0F);
		this.animator.rotate(this.RightThigh, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 1.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, -0.5F, 0.75F, -0.75F);
		this.animator.rotate(this.LeftShoulder, 0.0F, -0.75F, 0.75F);
		this.animator.rotate(this.RightForearm, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 3.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 3.0F, 6.0F);
		this.animator.move(this.RightThigh, 0.0F, 3.0F, 6.0F);
		this.animator.move(this.LeftThigh, 0.0F, 3.0F, 6.0F);
		this.animator.rotate(this.RightThigh, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.0F, 0.0F, 0.5F);
		this.animator.rotate(this.RightShoulder, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.RightForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 2.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Torso, 0.0F, 0.0F, -4.0F);
		this.animator.move(this.RightThigh, 0.0F, 0.0F, -4.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0.0F, -4.0F);
		this.animator.rotate(this.RightThigh, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.0F, 0.0F, -0.5F);
		this.animator.rotate(this.RightShoulder, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 1.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.Torso, 0.0F, 0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 0F, 0.0F);
		this.animator.rotate(this.RightThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Torso, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.RightForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.HeldItem, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Torso, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 1.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftThigh, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.8F, 0.5F, 0.5F);
		this.animator.rotate(this.LeftShoulder, 0.8F, -0.5F, -0.5F);
		this.animator.rotate(this.RightForearm, -1.6F, 0.0F, -0.75F);
		this.animator.rotate(this.LeftForearm, -1.6F, 0.0F, 0.75F);
		this.animator.rotate(this.HeldItem, 0.0F, 0.0F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.move(this.Torso, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.25F, 0.25F);
		this.animator.rotate(this.LeftThigh, -0.25F, -0.25F, -0.25F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.25F, 1.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.25F, -1.0F, 0.0F);
		this.animator.rotate(this.RightForearm, 0.0F, -1.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, 0.0F, 1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.move(this.Torso, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.RightThigh, 0.0F, 2.0F, 0.0F);
		this.animator.move(this.LeftThigh, 0.0F, 2.0F, 0.0F);
		this.animator.rotate(this.RightThigh, -0.25F, 0.25F, 0.25F);
		this.animator.rotate(this.LeftThigh, -0.25F, -0.25F, -0.25F);
		this.animator.rotate(this.RightCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftCalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.TopBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.MiddleBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.RightShoulder, 0.5F, 1.0F, 0.0F);
		this.animator.rotate(this.LeftShoulder, 0.5F, -1.0F, 0.0F);
		this.animator.rotate(this.RightForearm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.LeftForearm, -0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(80);
		this.animator.resetPhase(40);
	}
}


