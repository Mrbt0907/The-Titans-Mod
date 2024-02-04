package net.minecraft.theTitans.render.minions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titanminion.EntitySkeletonMinion;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelSkeletonLoyalist;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderSkeletonMinion
extends RenderBiped
{
	private static final ResourceLocation skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
	private static final ResourceLocation skeletonPriestTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/skeletons/skeleton_priest.png"));
	private static final ResourceLocation witherSkeletonPriestTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/skeletons/wither_skeleton_priest.png"));
	private static final ResourceLocation skeletonZealotTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/skeletons/skeleton_zealot.png"));
	private static final ResourceLocation witherSkeletonZealotTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/skeletons/wither_skeleton_zealot.png"));
	private static final ResourceLocation skeletonBishopTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/skeletons/skeleton_bishop.png"));
	private static final ResourceLocation witherSkeletonBishopTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/skeletons/wither_skeleton_bishop.png"));
	private static final ResourceLocation skeletonTemplarTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/skeletons/skeleton_templar.png"));
	private static final ResourceLocation witherSkeletonTemplarTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/skeletons/wither_skeleton_templar.png"));
	public RenderSkeletonMinion()
	{
		super(new ModelSkeletonLoyalist(), 0.5F);
	}

	protected void preRenderCallback(EntitySkeletonMinion p_77041_1_, float p_77041_2_)
	{
		if (p_77041_1_.getSkeletonType() == 1)
		{
			GL11.glScalef(1.2F, 1.2F, 1.2F);
		}
	}

	public void func_82422_c()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}

	protected ResourceLocation func_180577_a(EntitySkeletonMinion p_180577_1_)
	{
		switch (p_180577_1_.getMinionTypeInt())
		{
			case 1:
			return p_180577_1_.getSkeletonType() == 1 ? witherSkeletonPriestTextures : skeletonPriestTextures;
			case 2:
			return p_180577_1_.getSkeletonType() == 1 ? witherSkeletonZealotTextures : skeletonZealotTextures;
			case 3:
			return p_180577_1_.getSkeletonType() == 1 ? witherSkeletonBishopTextures : skeletonBishopTextures;
			case 4:
			return p_180577_1_.getSkeletonType() == 1 ? witherSkeletonTemplarTextures : skeletonTemplarTextures;
			default:
			return p_180577_1_.getSkeletonType() == 1 ? witherSkeletonTextures : skeletonTextures;
		}
	}

	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return func_180577_a((EntitySkeletonMinion)entity);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		preRenderCallback((EntitySkeletonMinion)p_77041_1_, p_77041_2_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_180577_a((EntitySkeletonMinion)entity);
	}
}


