package net.minecraft.theTitans.render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.orespawnaddon.EntityMethuselahKraken;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import danger.orespawn.*;
public class RenderMethuselahKraken
extends RenderLiving
{
	protected ModelKraken model;
	private float scale = 2.0F;
	private static final ResourceLocation texture = new ResourceLocation(TheTitans.getTextures("textures/entities", "methuselahkraken.png"));
	public RenderMethuselahKraken()
	{
		super(new ModelKraken(1F), 2F);
		this.model = ((ModelKraken)this.mainModel);
	}

	public void renderKraken(EntityMethuselahKraken par1EntityKraken, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(par1EntityKraken, par2, par4, par6, par8, par9);
	}

	public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderKraken((EntityMethuselahKraken)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderKraken((EntityMethuselahKraken)par1Entity, par2, par4, par6, par8, par9);
	}

	protected void preRenderScale(EntityMethuselahKraken par1Entity, float par2)
	{
		if ((par1Entity != null) && (par1Entity.getPlayNicely() != 0))
		{
			GL11.glScalef(this.scale / 3.0F, this.scale / 3.0F, this.scale / 3.0F);
			return;
		}

		GL11.glScalef(this.scale, this.scale, this.scale);
	}

	protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
	{
		preRenderScale((EntityMethuselahKraken)par1EntityLiving, par2);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}


