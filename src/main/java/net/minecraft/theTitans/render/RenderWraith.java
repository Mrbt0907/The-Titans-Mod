package net.minecraft.theTitans.render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.other.EntityWraith;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelWraith;
import net.minecraft.util.ResourceLocation;
public class RenderWraith extends RenderLiving
{
	public static final ResourceLocation entityWraith = new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/wraith.png"));
	public RenderWraith()
	{
		super(new ModelWraith(), 0.5F);
	}

	protected ResourceLocation getEntityTexture(EntityWraith entity)
	{
		return entityWraith;
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityWraith)entity);
	}
}


