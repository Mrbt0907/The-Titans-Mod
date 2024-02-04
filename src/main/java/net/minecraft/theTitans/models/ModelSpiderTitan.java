package net.minecraft.theTitans.models;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.EntitySpiderTitan;
import net.minecraft.util.MathHelper;
import thehippomaster.AnimationAPI.client.Animator;
public class ModelSpiderTitan
extends ModelBase
{
	public ModelRenderer Thorax;
	public ModelRenderer Head;
	public ModelRenderer Abdoman;
	public ModelRenderer Frontleftupperarm;
	public ModelRenderer Frontrightupperarm;
	public ModelRenderer Frontleftupperleg;
	public ModelRenderer Frontrightupperleg;
	public ModelRenderer Backrightupperleg1;
	public ModelRenderer Backrightupperleg2;
	public ModelRenderer Backleftupperleg1;
	public ModelRenderer Backleftupperleg2;
	public ModelRenderer Frontleftlowerarm;
	public ModelRenderer Frontrightlowerarm;
	public ModelRenderer Frontleftlowerleg;
	public ModelRenderer Frontrightlowerleg;
	public ModelRenderer Backrightlowerleg1;
	public ModelRenderer Backrightlowerleg2;
	public ModelRenderer Backleftlowerleg1;
	public ModelRenderer Backleftlowerleg2;
	private Animator animator;
	public ModelSpiderTitan()
	{
		this(0.0F);
	}

	public ModelSpiderTitan(float p_i1147_1_)
	{
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, 0.0F, -3.0F);
		this.Head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, p_i1147_1_);
		this.Backleftlowerleg1 = new ModelRenderer(this, 32, 10);
		this.Backleftlowerleg1.mirror = true;
		this.Backleftlowerleg1.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Backleftlowerleg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Abdoman = new ModelRenderer(this, 0, 28);
		this.Abdoman.setRotationPoint(0.0F, 0.0F, 3.0F);
		this.Abdoman.addBox(-5.0F, -4.0F, 0.0F, 10, 8, 12, p_i1147_1_);
		this.Backrightupperleg1 = new ModelRenderer(this, 32, 0);
		this.Backrightupperleg1.setRotationPoint(-3.0F, 0.0F, 1.0F);
		this.Backrightupperleg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Backleftupperleg2 = new ModelRenderer(this, 32, 0);
		this.Backleftupperleg2.mirror = true;
		this.Backleftupperleg2.setRotationPoint(3.0F, 0.0F, 2.0F);
		this.Backleftupperleg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Thorax = new ModelRenderer(this, 0, 16);
		this.Thorax.setRotationPoint(0.0F, 15.0F, 0.0F);
		this.Thorax.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, p_i1147_1_);
		this.Frontleftlowerarm = new ModelRenderer(this, 32, 10);
		this.Frontleftlowerarm.mirror = true;
		this.Frontleftlowerarm.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Frontleftlowerarm.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Backrightupperleg2 = new ModelRenderer(this, 32, 0);
		this.Backrightupperleg2.setRotationPoint(-3.0F, 0.0F, 2.0F);
		this.Backrightupperleg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Frontleftupperleg = new ModelRenderer(this, 32, 0);
		this.Frontleftupperleg.mirror = true;
		this.Frontleftupperleg.setRotationPoint(3.0F, 0.0F, -1.0F);
		this.Frontleftupperleg.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Backrightlowerleg2 = new ModelRenderer(this, 32, 10);
		this.Backrightlowerleg2.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Backrightlowerleg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Frontrightupperleg = new ModelRenderer(this, 32, 0);
		this.Frontrightupperleg.setRotationPoint(-3.0F, 0.0F, -1.0F);
		this.Frontrightupperleg.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Frontrightlowerarm = new ModelRenderer(this, 32, 10);
		this.Frontrightlowerarm.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Frontrightlowerarm.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Frontleftlowerleg = new ModelRenderer(this, 32, 10);
		this.Frontleftlowerleg.mirror = true;
		this.Frontleftlowerleg.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Frontleftlowerleg.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Backleftlowerleg2 = new ModelRenderer(this, 32, 10);
		this.Backleftlowerleg2.mirror = true;
		this.Backleftlowerleg2.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Backleftlowerleg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Frontleftupperarm = new ModelRenderer(this, 32, 0);
		this.Frontleftupperarm.mirror = true;
		this.Frontleftupperarm.setRotationPoint(3.0F, 0.0F, -2.0F);
		this.Frontleftupperarm.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Frontrightupperarm = new ModelRenderer(this, 32, 0);
		this.Frontrightupperarm.setRotationPoint(-3.0F, 0.0F, -2.0F);
		this.Frontrightupperarm.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Backleftupperleg1 = new ModelRenderer(this, 32, 0);
		this.Backleftupperleg1.mirror = true;
		this.Backleftupperleg1.setRotationPoint(3.0F, 0.0F, 1.0F);
		this.Backleftupperleg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Frontrightlowerleg = new ModelRenderer(this, 32, 10);
		this.Frontrightlowerleg.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Frontrightlowerleg.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Backrightlowerleg1 = new ModelRenderer(this, 32, 10);
		this.Backrightlowerleg1.setRotationPoint(0.0F, 8.0F, 0.0F);
		this.Backrightlowerleg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, p_i1147_1_);
		this.Thorax.addChild(this.Head);
		this.Backleftupperleg1.addChild(this.Backleftlowerleg1);
		this.Thorax.addChild(this.Abdoman);
		this.Thorax.addChild(this.Backrightupperleg1);
		this.Thorax.addChild(this.Backleftupperleg2);
		this.Frontleftupperarm.addChild(this.Frontleftlowerarm);
		this.Thorax.addChild(this.Backrightupperleg2);
		this.Thorax.addChild(this.Frontleftupperleg);
		this.Backrightupperleg2.addChild(this.Backrightlowerleg2);
		this.Thorax.addChild(this.Frontrightupperleg);
		this.Frontrightupperarm.addChild(this.Frontrightlowerarm);
		this.Frontleftupperleg.addChild(this.Frontleftlowerleg);
		this.Backleftupperleg2.addChild(this.Backleftlowerleg2);
		this.Thorax.addChild(this.Frontleftupperarm);
		this.Thorax.addChild(this.Frontrightupperarm);
		this.Thorax.addChild(this.Backleftupperleg1);
		this.Frontrightupperleg.addChild(this.Frontrightlowerleg);
		this.Backrightupperleg1.addChild(this.Backrightlowerleg1);
		this.animator = new Animator(this);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		EntitySpiderTitan spiderTitan = (EntitySpiderTitan)entity;
		animate(spiderTitan, f, f1, f2, f3, f4, f5);
		this.Thorax.render(f5);
	}

	public void setAngles()
	{
		this.Thorax.rotationPointY = 15.0F;
		this.Thorax.rotationPointX = 0.0F;
		this.Thorax.rotationPointZ = 0.0F;
		this.Head.rotateAngleX = 0.0F;
		this.Thorax.rotateAngleX = 0.0F;
		this.Abdoman.rotateAngleX = 0.0F;
		this.Frontrightupperarm.rotateAngleX = -1.2217305F;
		this.Frontrightupperarm.rotateAngleY = 0.87266463F;
		this.Frontleftupperarm.rotateAngleX = -1.2217305F;
		this.Frontleftupperarm.rotateAngleY = -0.87266463F;
		this.Frontrightlowerarm.rotateAngleX = 0.475F;
		this.Frontleftlowerarm.rotateAngleX = 0.475F;
		this.Frontleftupperleg.rotateAngleX = -1.2217305F;
		this.Frontleftupperleg.rotateAngleY = -1.3089969F;
		this.Frontrightupperleg.rotateAngleX = -1.2217305F;
		this.Frontrightupperleg.rotateAngleY = 1.3089969F;
		this.Frontleftlowerleg.rotateAngleX = 0.475F;
		this.Frontrightlowerleg.rotateAngleX = 0.475F;
		this.Backleftupperleg1.rotateAngleX = -1.2217305F;
		this.Backleftupperleg1.rotateAngleY = -1.8325957F;
		this.Backrightupperleg1.rotateAngleX = -1.2217305F;
		this.Backrightupperleg1.rotateAngleY = 1.8325957F;
		this.Backleftlowerleg1.rotateAngleX = 0.475F;
		this.Backrightlowerleg1.rotateAngleX = 0.475F;
		this.Backleftupperleg2.rotateAngleX = -1.2217305F;
		this.Backleftupperleg2.rotateAngleY = -2.1816616F;
		this.Backrightupperleg2.rotateAngleX = -1.2217305F;
		this.Backrightupperleg2.rotateAngleY = 2.1816616F;
		this.Backleftlowerleg2.rotateAngleX = 0.475F;
		this.Backrightlowerleg2.rotateAngleX = 0.475F;
	}

	public void animate(EntitySpiderTitan entitytitan, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.animator.update(entitytitan);
		setAngles();
		if (entitytitan.deathTicks <= 0)
		{
			if (this.isRiding)
			{
				this.Frontrightupperarm.rotateAngleY = 0.87266463F;
				this.Frontleftupperarm.rotateAngleY = -0.87266463F;
				this.Frontrightupperleg.rotateAngleY = 1.3089969F;
				this.Frontleftupperleg.rotateAngleY = -1.3089969F;
				this.Backrightupperleg1.rotateAngleY = 1.8325957F;
				this.Backleftupperleg1.rotateAngleY = -1.8325957F;
				this.Backrightupperleg2.rotateAngleY = 2.1816616F;
				this.Backleftupperleg2.rotateAngleY = -2.1816616F;
				this.Frontrightupperarm.rotateAngleX = -0.8F;
				this.Frontleftupperarm.rotateAngleX = -0.8F;
				this.Frontrightupperleg.rotateAngleX = -0.8F;
				this.Frontleftupperleg.rotateAngleX = -0.8F;
				this.Backrightupperleg1.rotateAngleX = -0.8F;
				this.Backleftupperleg1.rotateAngleX = -0.8F;
				this.Backrightupperleg2.rotateAngleX = -0.8F;
				this.Backleftupperleg2.rotateAngleX = -0.8F;
			}

			else
			{
				if (entitytitan.getAnimID() == 0)
				{
					this.Frontrightupperarm.rotateAngleY = (0.87266463F + MathHelper.cos(f * 0.5F + 2F) * 0.3F * f1);
					this.Frontleftupperarm.rotateAngleY = (-0.87266463F + MathHelper.cos(f * 0.5F + 4F) * 0.3F * f1);
					this.Frontrightupperleg.rotateAngleY = (1.3089969F + MathHelper.cos(f * 0.5F + 6F) * 0.3F * f1);
					this.Frontleftupperleg.rotateAngleY = (-1.3089969F + MathHelper.cos(f * 0.5F) * 0.3F * f1);
					this.Backrightupperleg1.rotateAngleY = (1.8325957F + MathHelper.cos(f * 0.5F + 6F) * 0.3F * f1);
					this.Backleftupperleg1.rotateAngleY = (-1.8325957F + MathHelper.cos(f * 0.5F + 4F) * 0.3F * f1);
					this.Backrightupperleg2.rotateAngleY = (2.1816616F + MathHelper.cos(f * 0.5F + 2F) * 0.3F * f1);
					this.Backleftupperleg2.rotateAngleY = (-2.1816616F + MathHelper.cos(f * 0.5F) * 0.3F * f1);
					this.Frontrightupperarm.rotateAngleX = (-1.2217305F + MathHelper.cos(f * 0.5F + 2F) * 0.15F * f1);
					this.Frontleftupperarm.rotateAngleX = (-1.2217305F + MathHelper.cos(f * 0.5F + 4F) * 0.15F * f1);
					this.Frontrightupperleg.rotateAngleX = (-1.2217305F + MathHelper.cos(f * 0.5F + 6F) * 0.15F * f1);
					this.Frontleftupperleg.rotateAngleX = (-1.2217305F + MathHelper.cos(f * 0.5F) * 0.15F * f1);
					this.Backrightupperleg1.rotateAngleX = (-1.2217305F + MathHelper.cos(f * 0.5F + 6F) * 0.15F * f1);
					this.Backleftupperleg1.rotateAngleX = (-1.2217305F + MathHelper.cos(f * 0.5F + 4F) * 0.15F * f1);
					this.Backrightupperleg2.rotateAngleX = (-1.2217305F + MathHelper.cos(f * 0.5F + 2F) * 0.15F * f1);
					this.Backleftupperleg2.rotateAngleX = (-1.2217305F + MathHelper.cos(f * 0.5F) * 0.15F * f1);
				}
			}

			if (entitytitan.getAnimID() == 0)
			{
				this.Head.rotateAngleX = ((0.01F + 0.01F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Abdoman.rotateAngleX = -((0.025F * MathHelper.cos(f2 * 0.1F + 1.5F)) * 3.1415927F);
				this.Thorax.rotationPointY = 15.0F - ((0.05F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Frontrightupperarm.rotateAngleX = -1.2217305F + ((0.005F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Frontleftupperarm.rotateAngleX = -1.2217305F + ((0.005F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Frontrightupperleg.rotateAngleX = -1.2217305F + ((0.005F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Frontleftupperleg.rotateAngleX = -1.2217305F + ((0.005F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Backrightupperleg1.rotateAngleX = -1.2217305F + ((0.005F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Backleftupperleg1.rotateAngleX = -1.2217305F + ((0.005F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Backrightupperleg2.rotateAngleX = -1.2217305F + ((0.005F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
				this.Backleftupperleg2.rotateAngleX = -1.2217305F + ((0.005F * MathHelper.cos(f2 * 0.1F)) * 3.1415927F);
			}

			if (entitytitan.isFlying && !this.isRiding)
			{
				this.Abdoman.rotateAngleX -= (MathHelper.cos(f * 0.35F - 1.5F) * 0.1F * f1) - 0.25F + (entitytitan.limbSwingAmount);
				this.Frontrightupperarm.rotateAngleY = 0.5F;
				this.Frontleftupperarm.rotateAngleY = -0.5F;
				this.Frontrightupperleg.rotateAngleY = 1.4F + (entitytitan.limbSwingAmount);
				this.Frontleftupperleg.rotateAngleY = -1.4F - (entitytitan.limbSwingAmount);
				this.Backrightupperleg1.rotateAngleY = 1.8F + (entitytitan.limbSwingAmount);
				this.Backleftupperleg1.rotateAngleY = -1.8F - (entitytitan.limbSwingAmount);
				this.Backrightupperleg2.rotateAngleY = 2.0F + (entitytitan.limbSwingAmount);
				this.Backleftupperleg2.rotateAngleY = -2.0F - (entitytitan.limbSwingAmount);
				this.Frontrightupperleg.rotateAngleX = (-1.2217305F + (MathHelper.cos(f2 * 0.2F)) * 0.25F);
				this.Frontleftupperleg.rotateAngleX = (-1.2217305F + (MathHelper.cos(f2 * 0.2F - 0.5F)) * 0.25F);
				this.Backrightupperleg1.rotateAngleX = (-1.2217305F + (MathHelper.cos(f2 * 0.2F - 1F)) * 0.25F);
				this.Backleftupperleg1.rotateAngleX = (-1.2217305F + (MathHelper.cos(f2 * 0.2F - 1.5F)) * 0.25F);
				this.Backrightupperleg2.rotateAngleX = (-1.2217305F + (MathHelper.cos(f2 * 0.2F - 2F)) * 0.25F);
				this.Backleftupperleg2.rotateAngleX = (-1.2217305F + (MathHelper.cos(f2 * 0.2F - 2.5F)) * 0.25F);
				this.Frontrightupperarm.rotateAngleX = -1F - (entitytitan.limbSwingAmount);
				this.Frontleftupperarm.rotateAngleX = -1F - (entitytitan.limbSwingAmount);
			}

			float faceYaw = f3 * 3.1415927F / 180.0F;
			float facePitch = f4 * 3.1415927F / 180.0F;
			this.Head.rotateAngleX += facePitch * 0.9F;
			this.Head.rotateAngleY += faceYaw * 0.9F;
			if (entitytitan.getAnimID() == 6 && (entitytitan.getAnimTick() >= 30 && entitytitan.getAnimTick() < 50))
			{
				this.Abdoman.rotateAngleX = ((0.1F * MathHelper.cos(f2 * 1F)) * 3.1415927F);
			}

			if (entitytitan.getAnimID() == 6 && (entitytitan.getAnimTick() >= 50 && entitytitan.getAnimTick() < 100))
			{
				this.Abdoman.rotateAngleX = ((0.1F * MathHelper.cos(f2 * 1.5F)) * 3.1415927F);
			}

			if (entitytitan.getAnimID() == 13 && (entitytitan.getAnimTick() >= 90 && entitytitan.getAnimTick() < 140))
			{
				this.Thorax.rotationPointY += -3F + MathHelper.cos(entitytitan.getAnimTick() * 0.11F + 90F) * 4F;
				this.Head.rotateAngleX += 0.5F - MathHelper.cos(entitytitan.getAnimTick() * 0.11F + 60F) * 0.5F;
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
				this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
				this.animator.rotate(this.Abdoman, -0.5F, 0.0F, 0.0F);
				this.animator.move(this.Thorax, 0.0F, 14.0F, 0.0F);
				this.animator.rotate(this.Frontleftupperarm, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Frontrightupperarm, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Frontrightupperleg, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Frontleftupperleg, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Backleftupperleg1, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Backleftupperleg2, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Backrightupperleg1, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Backrightupperleg2, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Frontleftlowerarm, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Frontrightlowerarm, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Frontrightlowerleg, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Frontleftlowerleg, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Backleftlowerleg1, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Backleftlowerleg2, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Backrightlowerleg1, 1F, 0.0F, 0.0F);
				this.animator.rotate(this.Backrightlowerleg2, 1F, 0.0F, 0.0F);
			}

			animateBirth();
			animateBite();
			animateSpit();
			animateStomp();
			animateSwat();
			animateShootLightning();
			animateShootWeb();
			animateStunned();
			animateCrush();
		}

		else
		{
			animateDeath();
		}
	}

	private void animateBite()
	{
		this.animator.setAnim(4);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, -4.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -1.5F, 0.6F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -1.5F, -0.6F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, -0.3F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.3F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.move(this.Thorax, 0.0F, 0.0F, -16.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, 1F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(30);
	}

	private void animateSpit()
	{
		this.animator.setAnim(2);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.3F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.05F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -1.5F, -0.6F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -1.5F, 0.6F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, -0.3F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -1.5F, -0.6F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -1.5F, 0.6F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, -0.3F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -1.5F, -0.6F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -1.5F, 0.6F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, -0.3F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.resetPhase(20);
	}

	private void animateStomp()
	{
		this.animator.setAnim(3);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.3F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.05F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, -0.5F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, -1.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -2F, 0.6F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -2F, -0.6F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, -0.3F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.3F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.2F, 0.2F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.2F, -0.2F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.2F, -0.2F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.4F, -0.3F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.2F, 0.2F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.4F, 0.3F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.2F, 0.75F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.2F, -0.75F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.2F, -0.75F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.2F, 0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateSwat()
	{
		this.animator.setAnim(5);
		this.animator.startPhase(20);
		this.animator.rotate(this.Frontrightupperarm, -1.5F, 0.0F, -1.0F);
		this.animator.rotate(this.Frontrightlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.1F, -0.5F, 0.1F);
		this.animator.rotate(this.Abdoman, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, 0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Frontrightupperarm, 2.0F, -2.0F, -1.0F);
		this.animator.rotate(this.Frontrightlowerarm, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Head, 0.1F, 0.5F, -0.1F);
		this.animator.rotate(this.Abdoman, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.0F, -0.1F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, -0.1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, -0.1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, -0.1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, -0.1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, -0.1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, -0.1F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateAntiTitanAttack1()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(20);
		this.animator.move(this.Thorax, 0.0F, 0.0F, 12F);
		this.animator.rotate(this.Frontleftupperarm, -2F, 0.9F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -2F, -0.6F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, -0.3F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.3F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -0.75F, 0.5F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, -1F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Thorax, 0.0F, 0.0F, -12F);
		this.animator.rotate(this.Frontleftupperarm, -0.5F, 0.75F, 1.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.5F, -1.75F, 1.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.25F, -0.5F, -0.5F);
		this.animator.rotate(this.Frontrightlowerarm, 0.25F, 0.5F, 0.5F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, 1F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateAntiTitanAttack2()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(20);
		this.animator.move(this.Thorax, 0.0F, 0.0F, 12F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, -0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, -0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, -1F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Thorax, 0.0F, 0.0F, -12F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 1.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 1.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.5F, 0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateAntiTitanAttack3()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 0.0F, 12F);
		this.animator.rotate(this.Frontleftupperarm, -1.5F, 0.6F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -1.5F, -0.6F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, -0.3F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.3F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, -1F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Thorax, 0.0F, 0.0F, -12F);
		this.animator.rotate(this.Frontleftupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.5F, -0.5F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.5F, 0.5F, 0.0F);
		this.animator.rotate(this.Head, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, 1F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateAntiTitanAttack4()
	{
		this.animator.setAnim(1);
		this.animator.startPhase(20);
		this.animator.move(this.Thorax, 0.0F, 0.0F, 12F);
		this.animator.rotate(this.Frontleftupperarm, -2F, 0.6F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -2F, -0.9F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 1.75F, -0.3F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 1.75F, 0.3F, 0.0F);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -0.75F, -0.5F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, -1F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.move(this.Thorax, 0.0F, 0.0F, -12F);
		this.animator.rotate(this.Frontleftupperarm, -0.5F, -0.75F, -1.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.5F, 1.75F, -1.0F);
		this.animator.rotate(this.Frontleftlowerarm, -0.5F, 0.5F, -0.5F);
		this.animator.rotate(this.Frontrightlowerarm, -0.5F, -0.5F, 0.5F);
		this.animator.rotate(this.Head, -1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 1.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, 0.0F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, 1F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateShootWeb()
	{
		this.animator.setAnim(6);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.3F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 1.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.05F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -2.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, -4.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -1.5F, -0.6F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -1.5F, 0.6F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, -0.3F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.3F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(60);
		this.animator.resetPhase(40);
	}

	private void animateShootLightning()
	{
		this.animator.setAnim(7);
		this.animator.startPhase(50);
		this.animator.rotate(this.Head, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -1.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, -4.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.rotate(this.Head, -0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -0.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, -4.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -2.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, -4.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(50);
	}

	private void animateStunned()
	{
		this.animator.setAnim(8);
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, 0.1F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -0.1F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.05F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.05F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.5F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 6F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(60);
		this.animator.rotate(this.Head, 0.25F, 0.5F, 0.5F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 8F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(300);
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 8F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, -0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, -0.3F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 6F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(80);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, -0.5F, 0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.5F, -0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.5F, 0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.5F, -0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.5F, 0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.5F, -0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.5F, 0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(5);
		this.animator.rotate(this.Head, 0.5F, -0.75F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(40);
	}

	private void animateCrush()
	{
		this.animator.setAnim(9);
		this.animator.startPhase(15);
		this.animator.rotate(this.Head, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.5F, -1.0F, -0.75F);
		this.animator.rotate(this.Frontrightupperarm, 0.5F, 1.0F, 0.75F);
		this.animator.endPhase();
		this.animator.startPhase(15);
		this.animator.rotate(this.Head, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.0F, 0.7F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.0F, -0.7F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.6F, -0.8F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.6F, 0.8F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(20);
	}

	private void animateDeath()
	{
		this.animator.setAnim(10);
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 0.5F, 0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -0.5F, 0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, 0.5F, -0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -0.5F, -0.5F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -0.5F, 0.0F, 0.4F);
		this.animator.rotate(this.Abdoman, 0.25F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.05F, -0.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.05F, 0.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.05F, 0.25F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.05F, -0.25F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.05F, -0.25F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.05F, -0.25F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.05F, 0.25F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.05F, 0.25F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, 0.25F, 0.0F, -0.4F);
		this.animator.rotate(this.Abdoman, -0.25F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 4F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, 0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -0.3F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 3.5F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.5F, -0.25F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.5F, 0.25F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(40);
		this.animator.rotate(this.Head, -0.75F, 0.0F, -0.4F);
		this.animator.rotate(this.Abdoman, -0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Thorax, 0.1F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, -1F, -6.0F);
		this.animator.rotate(this.Frontleftupperarm, 0.0F, -1F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 0.0F, 1F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 0.0F, 0.75F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 0.0F, -0.75F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 0.0F, -0.25F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 0.0F, -0.5F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 0.0F, 0.25F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 0.0F, 0.5F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.4F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.4F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(60);
		this.animator.rotate(this.Head, -0.75F, 0.0F, -0.75F);
		this.animator.rotate(this.Thorax, 0.75F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 6.0F, -12.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.5F, -1.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.5F, 1.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.5F, 1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.5F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.75F, -0.75F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.75F, -0.75F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.75F, 0.5F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.75F, 0.5F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, -0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(70);
		this.animator.rotate(this.Head, 0.0F, 0.0F, -0.75F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 8.0F, -14.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.3F, -1.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.3F, 1.25F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.3F, 1F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.3F, -1F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.3F, -0.75F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.3F, -0.75F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.3F, 0.5F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.3F, 0.5F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(1700);
		this.animator.resetPhase(0);
	}

	private void animateBirth()
	{
		this.animator.setAnim(13);
		this.animator.startPhase(0);
		this.animator.rotate(this.Head, 0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -0.5F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 14.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(5);
		this.animator.startPhase(15);
		this.animator.rotate(this.Head, 0.2F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -0.4F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 15.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(100);
		this.animator.rotate(this.Head, 0.3F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, -0.5F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 8.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, -0.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, -0.5F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, 0.25F);
		this.animator.move(this.Thorax, 0.0F, 8.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, 0.5F);
		this.animator.move(this.Thorax, 0.0F, 8.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 1F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, -0.25F);
		this.animator.move(this.Thorax, 0.0F, 8.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -1.5F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.startPhase(10);
		this.animator.rotate(this.Head, -0.25F, 0.0F, 0.0F);
		this.animator.rotate(this.Abdoman, 0.0F, 0.0F, -0.5F);
		this.animator.move(this.Thorax, 0.0F, 8.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.6F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.6F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.setStationaryPhase(20);
		this.animator.startPhase(20);
		this.animator.rotate(this.Head, 0.0F, 0.0F, 0.0F);
		this.animator.move(this.Thorax, 0.0F, 6F, 0.0F);
		this.animator.rotate(this.Frontleftupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperarm, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftupperleg, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg1, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightupperleg2, -0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerarm, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontrightlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Frontleftlowerleg, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backleftlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg1, 0.75F, 0.0F, 0.0F);
		this.animator.rotate(this.Backrightlowerleg2, 0.75F, 0.0F, 0.0F);
		this.animator.endPhase();
		this.animator.resetPhase(60);
	}
}


