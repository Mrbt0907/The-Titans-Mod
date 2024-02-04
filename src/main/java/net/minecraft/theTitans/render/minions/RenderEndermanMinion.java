package net.minecraft.theTitans.render.minions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titanminion.EntityEndermanMinion;
import net.minecraft.entity.titanminion.EnumMinionType;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderEndermanMinion
extends RenderLiving
{
	private static final ResourceLocation endermanEyesTexture = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
	private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
	private static final ResourceLocation endermanPriestTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/endermen/enderman_priest.png"));
	private static final ResourceLocation endermanZealotTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/endermen/enderman_zealot.png"));
	private static final ResourceLocation endermanBishopTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/endermen/enderman_bishop.png"));
	private static final ResourceLocation endermanTemplarTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/endermen/enderman_templar.png"));
	private ModelEnderman endermanModel;
	private Random rnd = new Random();
	public RenderEndermanMinion()
	{
		super(new ModelEnderman(), 0.5F);
		this.endermanModel = ((ModelEnderman)this.mainModel);
		setRenderPassModel(this.endermanModel);
	}

	public void doRender(EntityEndermanMinion entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.endermanModel.isCarrying = (entity.func_146080_bZ().getMaterial() != Material.air);
		this.endermanModel.isAttacking = entity.isScreaming();
		double d3 = 0.01D;
		if (entity.isScreaming())
		{
			x += this.rnd.nextGaussian() * d3;
			z += this.rnd.nextGaussian() * d3;
		}

		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected ResourceLocation func_180573_a(EntityEndermanMinion p_180573_1_)
	{
		switch (p_180573_1_.getMinionTypeInt())
		{
			case 1:
			return endermanPriestTextures;
			case 2:
			return endermanZealotTextures;
			case 3:
			return endermanBishopTextures;
			case 4:
			return endermanTemplarTextures;
			default:
			return endermanTextures;
		}
	}

	protected void renderEquippedItems(EntityEndermanMinion p_77029_1_, float p_77029_2_)
	{
		super.renderEquippedItems(p_77029_1_, p_77029_2_);
		if (p_77029_1_.func_146080_bZ().getMaterial() != Material.air)
		{
			GL11.glEnable(32826);
			GL11.glPushMatrix();
			float f1 = 0.5F;
			GL11.glTranslatef(0.0F, 0.6875F, -0.75F);
			f1 *= 1.0F;
			GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(-f1, -f1, f1);
			int i = p_77029_1_.getBrightnessForRender(p_77029_2_);
			int j = i % 65536;
			int k = i / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			bindTexture(TextureMap.locationBlocksTexture);
			this.field_147909_c.renderBlockAsItem(p_77029_1_.func_146080_bZ(), p_77029_1_.getCarryingData(), 1.0F);
			GL11.glPopMatrix();
			GL11.glDisable(32826);
		}
	}

	protected int shouldRenderPass(EntityEndermanMinion p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		if (p_77032_2_ != 0)
		{
			return -1;
		}

		bindTexture(endermanEyesTexture);
		if (p_77032_1_.getMinionType() == EnumMinionType.TEMPLAR)
		bindTexture(endermanEyesTexture);
		float f1 = 1.0F;
		GL11.glEnable(3042);
		GL11.glDisable(3008);
		GL11.glBlendFunc(1, 1);
		GL11.glDisable(2896);
		if (p_77032_1_.isInvisible())
		{
			GL11.glDepthMask(false);
		}

		else
		{
			GL11.glDepthMask(true);
		}

		char c0 = 61680;
		int j = c0 % 65536;
		int k = c0 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
		GL11.glEnable(2896);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
		return 1;
	}

	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		doRender((EntityEndermanMinion)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityEndermanMinion)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_)
	{
		renderEquippedItems((EntityEndermanMinion)p_77029_1_, p_77029_2_);
	}

	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityEndermanMinion)entity, x, y, z, p_76986_8_, partialTicks);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_180573_a((EntityEndermanMinion)entity);
	}

	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		doRender((EntityEndermanMinion)entity, x, y, z, p_76986_8_, partialTicks);
	}
}


