package net.minecraft.theTitans.render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.orespawnaddon.EntityBurningMobzilla;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import danger.orespawn.ModelGodzilla;
public class RenderBurningMobzilla
extends RenderLiving
{
	protected ModelGodzilla model;
	private float scale = 1.0F;
	private static final ResourceLocation burningMobzillaMeltdownTexture = new ResourceLocation(TheTitans.getTextures("textures/entities", "Godzillaburningtexturemelt.png"));
	private static final ResourceLocation burningMobzillaTexture = new ResourceLocation(TheTitans.getTextures("textures/entities", "Godzillaburningtexture.png"));
	public RenderBurningMobzilla()
	{
		super(new ModelGodzilla(0.2F), 4F);
		this.model = ((ModelGodzilla)this.mainModel);
		this.scale = 2.05F;
	}

	public void renderGodzilla(EntityBurningMobzilla par1EntityGodzilla, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(par1EntityGodzilla, par2, par4, par6, par8, par9);
	}

	public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderGodzilla((EntityBurningMobzilla)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderGodzilla((EntityBurningMobzilla)par1Entity, par2, par4, par6, par8, par9);
	}

	protected void preRenderScale(EntityBurningMobzilla par1Entity, float par2)
	{
		if ((par1Entity != null) && (par1Entity.getPlayNicely() != 0))
		{
			GL11.glScalef(this.scale / 4.0F, this.scale / 4.0F, this.scale / 4.0F);
			return;
		}

		GL11.glScalef(this.scale, this.scale, this.scale);
		float f1 = (par1Entity.preventMeltDown * par2) / 100;
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
	}

	protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
	{
		preRenderScale((EntityBurningMobzilla)par1EntityLiving, par2);
	}

	protected ResourceLocation getEntityTexture(EntityBurningMobzilla entity)
	{
		return entity.getHealth() > 1000F ? burningMobzillaTexture : burningMobzillaMeltdownTexture;
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityBurningMobzilla)entity);
	}
}


