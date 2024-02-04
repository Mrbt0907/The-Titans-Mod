package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntityCreeperTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.IAnimatedEntity;
import thehippomaster.AnimationAPI.client.Animator;
public class ModelCreeperTitan
extends ModelBase
{
	public ModelRenderer BottomBody;
	public ModelRenderer Rightfrontupperleg;
	public ModelRenderer Rightbackupperleg;
	public ModelRenderer Leftfrontupperleg;
	public ModelRenderer Leftbackupperleg;
	public ModelRenderer BottomMiddle;
	public ModelRenderer BottomTop;
	public ModelRenderer Head;
	public ModelRenderer Rightfrontcalf;
	public ModelRenderer Rightfrontfoot;
	public ModelRenderer Rightbackcalf;
	public ModelRenderer Rightbackfoot;
	public ModelRenderer Leftfrontcalf;
	public ModelRenderer Leftfrontfoot;
	public ModelRenderer Leftbackcalf;
	public ModelRenderer Leftbackfoot;
	private Animator animator;
	public ModelCreeperTitan()
	{
		this(0.0F);
	}

	public ModelCreeperTitan(float p_i1147_1_)
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.Leftbackfoot = new ModelRenderer(this, 0, 24);
		this.Leftbackfoot.setRotationPoint(0.0F, 3.5F, 0.0F);
		this.Leftbackfoot.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Leftfrontupperleg = new ModelRenderer(this, 0, 16);
		this.Leftfrontupperleg.setRotationPoint(3.0F, 16.0F, -1.0F);
		this.Leftfrontupperleg.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Rightbackcalf = new ModelRenderer(this, 0, 16);
		this.Rightbackcalf.setRotationPoint(0.0F, 3.5F, 0.0F);
		this.Rightbackcalf.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Rightbackupperleg = new ModelRenderer(this, 0, 16);
		this.Rightbackupperleg.setRotationPoint(-3.0F, 16.0F, 1.0F);
		this.Rightbackupperleg.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Leftfrontcalf = new ModelRenderer(this, 0, 16);
		this.Leftfrontcalf.setRotationPoint(0.0F, 3.5F, 0.0F);
		this.Leftfrontcalf.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.BottomTop = new ModelRenderer(this, 16, 16);
		this.BottomTop.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.BottomTop.addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, p_i1147_1_);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, -4.0F, 0.0F);
		this.Head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i1147_1_);
		this.BottomBody = new ModelRenderer(this, 16, 32);
		this.BottomBody.setRotationPoint(0.0F, 16.0F, 0.0F);
		this.BottomBody.addBox(-4.0F, -2.0F, -2.0F, 8, 4, 4, p_i1147_1_);
		this.Leftbackcalf = new ModelRenderer(this, 0, 16);
		this.Leftbackcalf.setRotationPoint(0.0F, 3.5F, 0.0F);
		this.Leftbackcalf.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Leftbackupperleg = new ModelRenderer(this, 0, 16);
		this.Leftbackupperleg.setRotationPoint(3.0F, 16.0F, 1.0F);
		this.Leftbackupperleg.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Rightbackfoot = new ModelRenderer(this, 0, 24);
		this.Rightbackfoot.setRotationPoint(0.0F, 3.5F, 0.0F);
		this.Rightbackfoot.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Rightfrontupperleg = new ModelRenderer(this, 0, 16);
		this.Rightfrontupperleg.setRotationPoint(-3.0F, 16.0F, -1.0F);
		this.Rightfrontupperleg.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.BottomMiddle = new ModelRenderer(this, 16, 24);
		this.BottomMiddle.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.BottomMiddle.addBox(-4.0F, -4.0F, -2.0F, 8, 4, 4, p_i1147_1_);
		this.Leftfrontfoot = new ModelRenderer(this, 0, 24);
		this.Leftfrontfoot.setRotationPoint(0.0F, 3.5F, 0.0F);
		this.Leftfrontfoot.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Rightfrontcalf = new ModelRenderer(this, 0, 16);
		this.Rightfrontcalf.setRotationPoint(0.0F, 3.5F, 0.0F);
		this.Rightfrontcalf.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Rightfrontfoot = new ModelRenderer(this, 0, 24);
		this.Rightfrontfoot.setRotationPoint(0.0F, 3.5F, 0.0F);
		this.Rightfrontfoot.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, p_i1147_1_);
		this.Leftbackcalf.addChild(this.Leftbackfoot);
		this.Rightbackupperleg.addChild(this.Rightbackcalf);
		this.Leftfrontupperleg.addChild(this.Leftfrontcalf);
		this.BottomMiddle.addChild(this.BottomTop);
		this.BottomTop.addChild(this.Head);
		this.Leftbackupperleg.addChild(this.Leftbackcalf);
		this.Rightbackcalf.addChild(this.Rightbackfoot);
		this.BottomBody.addChild(this.BottomMiddle);
		this.Leftfrontcalf.addChild(this.Leftfrontfoot);
		this.Rightfrontupperleg.addChild(this.Rightfrontcalf);
		this.Rightfrontcalf.addChild(this.Rightfrontfoot);
		this.animator = new Animator(this);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		animate((IAnimatedEntity)entity, f, f1, f2, f3, f4, f5);
		this.Leftfrontupperleg.render(f5);
		this.Rightbackupperleg.render(f5);
		this.BottomBody.render(f5);
		this.Leftbackupperleg.render(f5);
		this.Rightfrontupperleg.render(f5);
	}

	public void setAngles()
	{
		float f = 16.1F;
		this.Rightbackupperleg.setRotationPoint(-3.0F, f, 1.0F);
		this.Leftfrontupperleg.setRotationPoint(3.0F, f, -1.0F);
		this.BottomBody.setRotationPoint(0.0F, f, 0.0F);
		this.Leftbackupperleg.setRotationPoint(3.0F, f, 1.0F);
		this.Rightfrontupperleg.setRotationPoint(-3.0F, f, -1.0F);
		this.Rightbackcalf.rotateAngleX = 0.7853982F;
		this.Leftfrontcalf.rotateAngleX = 0.7853982F;
		this.Leftbackcalf.rotateAngleX = 0.7853982F;
		this.Rightfrontcalf.rotateAngleX = 0.7853982F;
		this.Rightbackfoot.rotateAngleX = 0.5235988F;
		this.Leftfrontfoot.rotateAngleX = 0.5235988F;
		this.Rightfrontfoot.rotateAngleX = 0.5235988F;
		this.Leftbackfoot.rotateAngleX = 0.5235988F;
		this.Leftfrontupperleg.rotateAngleX = -1.3089969F;
		this.Leftfrontupperleg.rotateAngleY = -0.525F;
		this.Rightbackupperleg.rotateAngleY = 2.625F;
		this.Rightbackupperleg.rotateAngleX = -1.3089969F;
		this.Leftbackupperleg.rotateAngleY = -2.625F;
		this.Leftbackupperleg.rotateAngleX = -1.3089969F;
		this.Rightfrontupperleg.rotateAngleX = -1.3089969F;
		this.Rightfrontupperleg.rotateAngleY = 0.525F;
	}

	public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.animator.update(entity);
		setAngles();
		EntityCreeperTitan entitytitan = (EntityCreeperTitan)entity;
		float f6 = MathHelper.cos(f2 * 0.1F);
		float size = entitytitan.getTitanSizeMultiplier() / 4;
		float fo = 0.55F / (size < 1 ? 1 : size);
		if (entitytitan.deathTicks <= 0)
		{
			float faceYaw = f3 * 3.1415927F / 180.0F;
			float facePitch = f4 * 3.1415927F / 180.0F;
			this.BottomBody.rotateAngleZ = (MathHelper.cos(f * fo) * 0.3F * f1);
			this.BottomMiddle.rotateAngleZ = (MathHelper.cos(f * fo - 1F) * 0.3F * f1);
			this.BottomTop.rotateAngleZ = (MathHelper.cos(f * fo - 2F) * 0.3F * f1);
			this.Head.rotateAngleZ = (MathHelper.cos(f * fo - 3F) * 0.3F * f1);
			if (entitytitan.getAnimID() == 8 && entitytitan.getAnimTick() > 130 && entitytitan.getAnimTick() < 430)
			{
				this.BottomMiddle.rotateAngleY = MathHelper.cos(f2 * 0.05F + 2F) * 0.2F;
				this.BottomTop.rotateAngleY = MathHelper.cos(f2 * 0.05F + 1F) * 0.2F;
				this.Head.rotateAngleY = MathHelper.cos(f2 * 0.05F) * 0.2F;
				this.BottomMiddle.rotateAngleX = ((-0.025F + -0.0125F * MathHelper.cos(f2 * 0.1F - 2F)) * 3.1415927F);
				this.BottomTop.rotateAngleX = ((0.0125F + 0.0125F * MathHelper.cos(f2 * 0.1F - 1F)) * 3.1415927F);
				this.Head.rotateAngleX = ((0.0125F + 0.0125F * f6) * 3.1415927F);
			}

			if (entitytitan.getAnimID() == 13 && entitytitan.getAnimTick() < 80)
			{
				this.BottomMiddle.rotateAngleX = ((-0.005F + -0.005F * MathHelper.cos(f2 * 0.1F - 2F)) * 3.1415927F);
				this.BottomTop.rotateAngleX = ((0.005F + 0.005F * MathHelper.cos(f2 * 0.1F - 1F)) * 3.1415927F);
				this.Head.rotateAngleX = ((0.005F + 0.005F * f6) * 3.1415927F);
			}

			if (this.isRiding)
			{
				this.Rightbackupperleg.setRotationPoint(-3.0F, 21F, 1.0F);
				this.Leftfrontupperleg.setRotationPoint(3.0F, 21F, -1.0F);
				this.BottomBody.setRotationPoint(0.0F, 21F, 0.0F);
				this.Leftbackupperleg.setRotationPoint(3.0F, 21F, 1.0F);
				this.Rightfrontupperleg.setRotationPoint(-3.0F, 21F, -1.0F);
				this.Leftfrontupperleg.rotateAngleX = -1.6F;
				this.Leftbackupperleg.rotateAngleX = -1.6F;
				this.Rightfrontupperleg.rotateAngleX = -1.6F;
				this.Rightbackupperleg.rotateAngleX = -1.6F;
				this.Leftfrontupperleg.rotateAngleY = -0.725F;
				this.Leftbackupperleg.rotateAngleY = -2.25F;
				this.Rightfrontupperleg.rotateAngleY = 0.725F;
				this.Rightbackupperleg.rotateAngleY = 2.25F;
				this.Leftbackcalf.rotateAngleX = 1.0F;
				this.Leftfrontcalf.rotateAngleX = 1.0F;
				this.Rightbackcalf.rotateAngleX = 1.0F;
				this.Rightfrontcalf.rotateAngleX = 1.0F;
			}

			else
			{
				if (entitytitan.getAnimID() == 0)
				{
					this.BottomMiddle.rotateAngleX = ((-0.025F + -0.0125F * MathHelper.cos(f2 * 0.1F - 2F)) * 3.1415927F);
					this.BottomTop.rotateAngleX = ((0.0125F + 0.0125F * MathHelper.cos(f2 * 0.1F - 1F)) * 3.1415927F);
					this.Head.rotateAngleX = ((0.0125F + 0.0125F * f6) * 3.1415927F);
					this.Leftfrontupperleg.rotateAngleX = -1.3089969F + (MathHelper.cos(f * fo + 0.25F) * 0.75F * f1);
					this.Leftbackupperleg.rotateAngleX = -1.3089969F + (MathHelper.cos(f * fo + 3.3915927F) * 0.75F * f1);
					this.Rightfrontupperleg.rotateAngleX = -1.3089969F + (MathHelper.cos(f * fo + 3.3915927F) * 0.75F * f1);
					this.Rightbackupperleg.rotateAngleX = -1.3089969F + (MathHelper.cos(f * fo + 0.25F) * 0.75F * f1);
					this.Leftfrontupperleg.rotateAngleY = -0.525F + (MathHelper.cos(f * fo + 2.6415927F) * 0.5F * f1);
					this.Leftbackupperleg.rotateAngleY = -2.625F + (MathHelper.cos(f * fo - 0.5F) * 0.5F * f1);
					this.Rightfrontupperleg.rotateAngleY = 0.525F - (MathHelper.cos(f * fo - 0.75F) * 0.5F * f1);
					this.Rightbackupperleg.rotateAngleY = 2.625F - (MathHelper.cos(f * fo + 2.6415927F) * 0.5F * f1);
					this.Leftbackcalf.rotateAngleX = 0.7853982F + (MathHelper.cos(f * fo) * 0.5F * f1);
					this.Leftfrontcalf.rotateAngleX = 0.7853982F + (MathHelper.cos(f * fo + 3.1415927F) * 0.5F * f1);
					this.Rightbackcalf.rotateAngleX = 0.7853982F + (MathHelper.cos(f * fo + 3.1415927F) * 0.5F * f1);
					this.Rightfrontcalf.rotateAngleX = 0.7853982F + (MathHelper.cos(f * fo) * 0.5F * f1);
					if (this.Leftfrontupperleg.rotateAngleX > -1.3089969F)
					this.Leftfrontupperleg.rotateAngleX = -1.3089969F;
					if (this.Rightfrontupperleg.rotateAngleX > -1.3089969F)
					this.Rightfrontupperleg.rotateAngleX = -1.3089969F;
					if (this.Rightbackupperleg.rotateAngleX > -1.3089969F)
					this.Rightbackupperleg.rotateAngleX = -1.3089969F;
					if (this.Leftbackupperleg.rotateAngleX > -1.3089969F)
					this.Leftbackupperleg.rotateAngleX = -1.3089969F;
					if (this.Rightbackcalf.rotateAngleX < 0.7853982F)
					this.Rightbackcalf.rotateAngleX = 0.7853982F;
					if (this.Rightfrontcalf.rotateAngleX < 0.7853982F)
					this.Rightfrontcalf.rotateAngleX = 0.7853982F;
					if (this.Leftbackcalf.rotateAngleX < 0.7853982F)
					this.Leftbackcalf.rotateAngleX = 0.7853982F;
					if (this.Leftfrontcalf.rotateAngleX < 0.7853982F)
					this.Leftfrontcalf.rotateAngleX = 0.7853982F;
					if (this.Rightbackcalf.rotateAngleX < 0.7853982F)
					this.Rightbackcalf.rotateAngleX = 0.7853982F;
					if (this.Rightfrontcalf.rotateAngleX < 0.7853982F)
					this.Rightfrontcalf.rotateAngleX = 0.7853982F;
				}
			}

			this.Head.rotateAngleX += facePitch * 0.3F;
			this.Head.rotateAngleY += faceYaw * 0.3F;
			this.BottomTop.rotateAngleX += facePitch * 0.3F;
			this.BottomTop.rotateAngleY += faceYaw * 0.3F;
			this.BottomMiddle.rotateAngleX += facePitch * 0.3F;
			this.BottomMiddle.rotateAngleY += faceYaw * 0.3F;
			if (entitytitan.isFlying && !this.isRiding)
			{
				this.BottomBody.rotateAngleZ = 0F;
				this.BottomMiddle.rotateAngleZ = 0F;
				this.BottomTop.rotateAngleZ = 0F;
				this.Head.rotateAngleZ = 0F;
				this.Head.rotateAngleX -= (float)(entitytitan.limbSwingAmount);
				this.BottomBody.rotateAngleX += (float)(entitytitan.limbSwingAmount);
				this.Leftfrontupperleg.rotateAngleY = -0.25F;
				this.Rightbackupperleg.rotateAngleY = 2.75F;
				this.Leftbackupperleg.rotateAngleY = -2.75F;
				this.Rightfrontupperleg.rotateAngleY = 0.25F;
				this.Rightbackupperleg.rotateAngleX = -2F - ((MathHelper.cos(f2 * 0.2F)) * 0.25F) - (float)(entitytitan.motionY / 5) - entitytitan.limbSwingAmount;
				this.Leftbackupperleg.rotateAngleX = -2F - ((MathHelper.cos(f2 * 0.2F - 3.1415927F)) * 0.25F) - (float)(entitytitan.motionY / 5) - entitytitan.limbSwingAmount;
				this.Rightfrontupperleg.rotateAngleX = -2F - ((MathHelper.cos(f2 * 0.2F)) * 0.25F) - (float)(entitytitan.motionY / 5) + entitytitan.limbSwingAmount;
				this.Leftfrontupperleg.rotateAngleX = -2F - ((MathHelper.cos(f2 * 0.2F - 3.1415927F)) * 0.25F) - (float)(entitytitan.motionY / 5) + entitytitan.limbSwingAmount;
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
				this.animator.move(this.BottomBody, 0.0F, 7.0F, 0.0F);
				this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 0.0F);
				this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 0.0F);
				this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
				this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
				this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
				this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
			}

			animateStunned();
			animateThunderClap();
			animateSpit();
			animateKick();
			animateStomp();
			animateAttackStomp();
			animateHeadSlam();
			animateBirth();
		}

		else
		{
			animateDeath();
		}
	}

	private void animateAntiTitanAttack1()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, -3.0F, 4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -4.0F, 4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -4.0F, 4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -2.0F, 4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -2.0F, 4.0F);
		this.animator.rotate(this.Head, -1.0F, -1.2F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.75F, 0.6F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.75F, 0.6F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.75F, -0.75F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -2.25F, -0.75F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -2.25F, 0.75F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, -8.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, -8.0F);
		this.animator.rotate(this.Head, 0.5F, 0.75F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.75F, 0.75F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.75F, 0.5F, 0.0F);
		this.animator.rotate(this.BottomBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateAntiTitanAttack2()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, -3.0F, 4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -4.0F, 4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -4.0F, 4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -2.0F, 4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -2.0F, 4.0F);
		this.animator.rotate(this.Head, 0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 1.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.5F, -0.75F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -1.5F, 0.75F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, -8.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, -8.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, -8.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, -8.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, -8.0F);
		this.animator.rotate(this.Head, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, -1.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateAntiTitanAttack3()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, 4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, 4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, 4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, 4.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -4F, -0.5F, -1.3F);
		this.animator.rotate(this.Leftfrontupperleg, -4F, 0.5F, 1.3F);
		this.animator.rotate(this.Rightbackupperleg, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, -8.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, -8.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, -8.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, -8.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, -8.0F);
		this.animator.rotate(this.Head, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, -0.5F, -1.3F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.5F, 1.3F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateAntiTitanAttack4()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, -3.0F, 4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -4.0F, 4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -4.0F, 4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -2.0F, 4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -2.0F, 4.0F);
		this.animator.rotate(this.Head, -1.0F, 1.2F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.75F, -0.6F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.75F, -0.6F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.75F, 0.75F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -2.25F, -0.75F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -2.25F, 0.75F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 1.0F, -8.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, -8.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, -8.0F);
		this.animator.rotate(this.Head, 0.5F, -0.75F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.75F, -0.75F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.75F, -0.5F, 0.0F);
		this.animator.rotate(this.BottomBody, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateAttackStomp()
	{
		this.animator.setAnim(2);
		this.animator.startPhase(25);
		this.animator.rotate(this.Head, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.5F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.25F, 0.5F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.25F, 0.5F);
		this.animator.rotate(this.Rightfrontupperleg, -1.5F, 0.75F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -1.5F, -0.75F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.25F, -0.5F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.25F, -0.5F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, 0.75F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(5);
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -2.0F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, -0.5F);
		this.animator.rotate(this.BottomMiddle, -0.5F, -0.25F, -0.5F);
		this.animator.rotate(this.BottomBody, -0.5F, -0.25F, -0.5F);
		this.animator.rotate(this.Leftfrontupperleg, -1.5F, -0.75F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -1.5F, 0.75F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.5F);
		this.animator.rotate(this.BottomMiddle, 0.5F, -0.25F, 0.5F);
		this.animator.rotate(this.BottomBody, 0.5F, -0.25F, 0.5F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.0F, 0.75F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}

	private void animateStomp()
	{
		this.animator.setAnim(3);
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 0.5F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -1.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.move(this.BottomBody, 0.0F, 1.0F, -3.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 1.0F, -2.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 1.0F, -2.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, -2.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, -2.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateHeadSlam()
	{
		this.animator.setAnim(4);
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -1.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateKick()
	{
		this.animator.setAnim(5);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, -1.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.25F, 0.5F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.25F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.5F, 1.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.75F, -1.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.75F, -1.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -1.0F, 1.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.25F, -1.5F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.25F, -0.75F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.25F, -0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateSpit()
	{
		this.animator.setAnim(6);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.25F, 0.0F, 0.0F);
		this.animator.move(this.BottomBody, 0.0F, -0.5F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateThunderClap()
	{
		this.animator.setAnim(7);
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 0.5F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -1.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.35F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.35F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.25F, -0.5F, -1.3F);
		this.animator.rotate(this.Leftfrontupperleg, -1.25F, 0.5F, 1.3F);
		this.animator.rotate(this.Rightbackupperleg, 0.35F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.35F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(25);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -2.5F, -0.5F, -1.3F);
		this.animator.rotate(this.Leftfrontupperleg, -2.5F, 0.5F, 1.3F);
		this.animator.rotate(this.Rightbackupperleg, 0.35F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.35F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.rotate(this.Head, 2.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.75F, -0.5F, -1.3F);
		this.animator.rotate(this.Leftfrontupperleg, -0.75F, 0.5F, 1.3F);
		this.animator.rotate(this.Rightbackupperleg, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -1.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.move(this.BottomBody, 0.0F, 1.0F, -3.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 1.0F, -2.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 1.0F, -2.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, -2.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, -2.0F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(50);
	}

	private void animateStunned()
	{
		this.animator.setAnim(8);
		this.animator.startPhase(0);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 1.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, 1.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, 1.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, 1.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, 1.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.2F, 0.25F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.2F, -0.25F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.BottomBody, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -3.0F, 1.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -3.0F, 1.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -1.0F, 1.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.25F, 0.35F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.25F, -0.35F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(60);
		this.animator.move(this.BottomBody, 0.0F, -1.0F, 1.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -2.0F, 1.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, 1.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, 1.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, 0.25F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, -0.25F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(30);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(300);
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, 1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, 1.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	/*private void animateStorm()
	{
		this.animator.setAnim(9);
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.25F, 0.0F, 0.0F);
		this.animator.move(this.BottomBody, 0.0F, -0.5F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.1F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);

	}

	*/
	private void animateDeath()
	{
		this.animator.setAnim(10);
		this.animator.startPhase(60);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, -4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, -4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, -4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, -4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, -4.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, 0.25F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, -0.25F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.2F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(60);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 1.0F, 0.0F, 2.0F);
		this.animator.rotate(this.Leftbackupperleg, 1.0F, 0.0F, -2.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(60);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.rotate(this.Head, 0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.25F, 0.25F, 0.0F);
		this.animator.rotate(this.BottomBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(60);
		this.animator.startPhase(40);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 4.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(1720);
		this.animator.resetPhase(0);
	}

	private void animateBirth()
	{
		this.animator.setAnim(13);
		this.animator.startPhase(0);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(60);
		this.animator.startPhase(60);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(80);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 6.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 6.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 6.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -1.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.move(this.BottomBody, 0.0F, 6.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 6.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 6.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 7.0F, 0.0F);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(80);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, -0.25F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, -0.3F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, 0.1F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, 0.2F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.BottomBody, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Rightbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Leftbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.BottomTop, 0.25F, 0.0F, 0.3F);
		this.animator.rotate(this.BottomMiddle, 0.25F, 0.0F, -0.1F);
		this.animator.rotate(this.BottomBody, 0.25F, 0.0F, -0.2F);
		this.animator.rotate(this.Rightfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackupperleg, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightfrontfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Leftbackfoot, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackcalf, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Rightbackfoot, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}
}


