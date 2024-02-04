package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.other.EntityVoidGolem;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderVoidGolem
extends RenderIronGolem
{
	private static final ResourceLocation voidGolemTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/void_golem.png"));
	public RenderVoidGolem()
	{
		super();
	}

	protected ResourceLocation getEntityTexture(EntityVoidGolem entity)
	{
		return voidGolemTextures;
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityVoidGolem)entity);
	}
}


