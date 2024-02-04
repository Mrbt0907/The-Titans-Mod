package net.minecraft.theTitans.render.other;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.other.EntityEvilGolem;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelEvilGolem;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderEvilGolem extends RenderLiving
{
	public static final ResourceLocation[] EVIL_GOLEM_TEXTURE_LIST = new ResourceLocation[]
	{
		new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/big_zombie.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/wither_golem.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/gold_golem.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/big_enderman.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/spider_golem.png"))
	};
	private final ModelEvilGolem ironGolemModel;
	public RenderEvilGolem()
	{
		super(new ModelEvilGolem(0.0F, -7.0F), 0.75F);
		this.ironGolemModel = (ModelEvilGolem)this.mainModel;
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(EntityEvilGolem mob)
	{
		return EVIL_GOLEM_TEXTURE_LIST[mob.getMobType()];
	}

	protected void rotateCorpse(EntityEvilGolem p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
		ironGolemModel.hasSmallerHead = p_77043_1_.getMobType() == 0 || p_77043_1_.getMobType() == 3 || p_77043_1_.getMobType() == 4;
		if ((double)p_77043_1_.limbSwingAmount >= 0.01D)
		{
			float f3 = 13.0F;
			float f4 = p_77043_1_.limbSwing - p_77043_1_.limbSwingAmount * (1.0F - p_77043_4_) + 6.0F;
			float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
			GL11.glRotatef(6.5F * f5, 0.0F, 0.0F, 1.0F);
		}

		GL11.glScalef(1.2F, 1.2F, 1.2F);
	}

	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		this.rotateCorpse((EntityEvilGolem)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntityEvilGolem)p_110775_1_);
	}

	public void doRender(EntityEvilGolem p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		super.doRender((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityEvilGolem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityEvilGolem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityEvilGolem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}


