package net.minecraft.theTitans.render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.orespawnaddon.EntityOverlordScorpion;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import danger.orespawn.ModelEmperorScorpion;
public class RenderOverlordScorpion
extends RenderLiving
{
	protected ModelEmperorScorpion model;
	private static final ResourceLocation texture = new ResourceLocation(TheTitans.getTextures("textures/entities", "overlordscorpion.png"));
	public RenderOverlordScorpion()
	{
		super(new ModelEmperorScorpion(0.22F), 1.75F);
		this.model = ((ModelEmperorScorpion)this.mainModel);
	}

	protected void preRenderScale(EntityOverlordScorpion par1Entity, float par2)
	{
		GL11.glScalef(3F, 3F, 3F);
	}

	protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
	{
		preRenderScale((EntityOverlordScorpion)par1EntityLiving, par2);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return texture;
	}
}


