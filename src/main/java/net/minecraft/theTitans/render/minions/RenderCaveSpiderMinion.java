package net.minecraft.theTitans.render.minions;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titanminion.EntityCaveSpiderMinion;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderCaveSpiderMinion
extends RenderLiving
{
	private static final ResourceLocation spiderEyesTextures = new ResourceLocation("textures/entity/spider_eyes.png");
	private static final ResourceLocation templarEyes = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/spiders/spider_templar_overlay.png"));
	private static final ResourceLocation caveSpiderTemplarTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/spiders/cave_spider_templar.png"));
	private static final ResourceLocation caveSpiderBishopTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/spiders/cave_spider_bishop.png"));
	private static final ResourceLocation caveSpiderZealotTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/spiders/cave_spider_zealot.png"));
	private static final ResourceLocation caveSpiderPriestTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/spiders/cave_spider_priest.png"));
	private static final ResourceLocation caveSpiderTextures = new ResourceLocation("textures/entity/spider/cave_spider.png");
	public RenderCaveSpiderMinion()
	{
		super(new ModelSpider(), 0.7F);
		this.setRenderPassModel(new ModelSpider());
	}

	protected void preRenderCallback(EntityCaveSpiderMinion p_77041_1_, float p_77041_2_)
	{
		GL11.glScalef(0.7F, 0.7F, 0.7F);
	}

	protected float getDeathMaxRotation(EntityCaveSpiderMinion p_77037_1_)
	{
		return 180.0F;
	}

	protected float getDeathMaxRotation(EntityLivingBase p_77037_1_)
	{
		return this.getDeathMaxRotation((EntityCaveSpiderMinion)p_77037_1_);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		this.preRenderCallback((EntityCaveSpiderMinion)p_77041_1_, p_77041_2_);
	}

	protected ResourceLocation getEntityTexture(EntityCaveSpiderMinion p_180586_1_)
	{
		switch (p_180586_1_.getMinionTypeInt())
		{
			case 1:
			return caveSpiderPriestTextures;
			case 2:
			return caveSpiderZealotTextures;
			case 3:
			return caveSpiderBishopTextures;
			case 4:
			return caveSpiderTemplarTextures;
			default:
			return caveSpiderTextures;
		}
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityCaveSpiderMinion)entity);
	}

	protected int shouldRenderPass(EntityCaveSpiderMinion p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		if (p_77032_2_ != 0)
		{
			return -1;
		}

		bindTexture(p_77032_1_.getMinionTypeInt() == 4 ? templarEyes : spiderEyesTextures);
		GL11.glEnable(3042);
		GL11.glDisable(3008);
		GL11.glBlendFunc(1, 1);
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
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		return 1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityCaveSpiderMinion)p_77032_1_, p_77032_2_, p_77032_3_);
	}
}


