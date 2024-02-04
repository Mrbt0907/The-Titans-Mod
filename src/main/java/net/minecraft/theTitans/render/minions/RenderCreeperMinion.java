package net.minecraft.theTitans.render.minions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titanminion.EntityCreeperMinion;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
@SideOnly(Side.CLIENT)
public class RenderCreeperMinion
extends RenderLiving
{
	private static final ResourceLocation armoredCreeperTextures = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
	private static final ResourceLocation creeperTextures = new ResourceLocation("textures/entity/creeper/creeper.png");
	private static final ResourceLocation creeperPriestTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/creepers/creeper_priest.png"));
	private static final ResourceLocation creeperZealotTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/creepers/creeper_zealot.png"));
	private static final ResourceLocation creeperBishopTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/creepers/creeper_bishop.png"));
	private static final ResourceLocation creeperTemplarTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/creepers/creeper_templar.png"));
	private ModelBase creeperModel = new ModelCreeper(2.0F);
	public RenderCreeperMinion()
	{
		super(new ModelCreeper(), 0.5F);
		setRenderPassModel(new ModelCreeper());
	}

	protected void preRenderCallback(EntityCreeperMinion p_77041_1_, float p_77041_2_)
	{
		float f1 = p_77041_1_.getCreeperFlashIntensity(p_77041_2_);
		float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;
		if (f1 < 0.0F)
		{
			f1 = 0.0F;
		}

		if (f1 > 1.0F)
		{
			f1 = 1.0F;
		}

		f1 *= f1;
		f1 *= f1;
		float f3 = (1.0F + f1 * 0.4F) * f2;
		float f4 = (1.0F + f1 * 0.1F) / f2;
		GL11.glScalef(f3, f4, f3);
		GL11.glTranslatef(0.0F, 0.125F, 0.0F);
	}

	protected int getColorMultiplier(EntityCreeperMinion p_77030_1_, float p_77030_2_, float p_77030_3_)
	{
		float f2 = p_77030_1_.getCreeperFlashIntensity(p_77030_3_);
		if ((int)(f2 * 10.0F) % 2 == 0)
		{
			return 0;
		}

		int i = (int)(f2 * 0.2F * 255.0F);
		if (i < 0)
		{
			i = 0;
		}

		if (i > 255)
		{
			i = 255;
		}

		short short1 = 255;
		short short2 = 255;
		short short3 = 255;
		return i << 24 | short1 << 16 | short2 << 8 | short3;
	}

	protected ResourceLocation getEntityTexture(EntityCreeperMinion entity)
	{
		switch (entity.getMinionTypeInt())
		{
			case 1:
			return creeperPriestTextures;
			case 2:
			return creeperZealotTextures;
			case 3:
			return creeperBishopTextures;
			case 4:
			return creeperTemplarTextures;
			default:
			return creeperTextures;
		}
	}

	protected int shouldRenderPass(EntityCreeperMinion p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		if (p_77032_1_.getPowered())
		{
			if (p_77032_1_.isInvisible())
			{
				GL11.glDepthMask(false);
			}

			else
			{
				GL11.glDepthMask(true);
			}

			if (p_77032_2_ == 1)
			{
				float f1 = p_77032_1_.ticksExisted + p_77032_3_;
				bindTexture(armoredCreeperTextures);
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				float f2 = f1 * 0.01F;
				float f3 = f1 * 0.01F;
				GL11.glTranslatef(f2, f3, 0.0F);
				setRenderPassModel(this.creeperModel);
				GL11.glMatrixMode(5888);
				GL11.glEnable(3042);
				float f4 = 0.5F;
				GL11.glColor4f(f4, f4, f4, 1.0F);
				GL11.glDisable(2896);
				GL11.glBlendFunc(1, 1);
				return 1;
			}

			if (p_77032_2_ == 2)
			{
				GL11.glMatrixMode(5890);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(5888);
				GL11.glEnable(2896);
				GL11.glDisable(3042);
			}
		}

		return -1;
	}

	protected int inheritRenderPass(EntityCreeperMinion p_77035_1_, int p_77035_2_, float p_77035_3_)
	{
		return -1;
	}

	protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return shouldRenderPass((EntityCreeperMinion)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
	{
		preRenderCallback((EntityCreeperMinion)p_77041_1_, p_77041_2_);
	}

	protected int getColorMultiplier(EntityLivingBase p_77030_1_, float p_77030_2_, float p_77030_3_)
	{
		return getColorMultiplier((EntityCreeperMinion)p_77030_1_, p_77030_2_, p_77030_3_);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityCreeperMinion)entity);
	}
}


