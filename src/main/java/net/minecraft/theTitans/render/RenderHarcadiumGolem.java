package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.other.EntityHarcadiumGolem;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderHarcadiumGolem
extends RenderIronGolem
{
	private static final ResourceLocation harcadiumGolemTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/harcadium_golem.png"));
	public RenderHarcadiumGolem()
	{
		super();
	}

	protected ResourceLocation getEntityTexture(EntityHarcadiumGolem entity)
	{
		return harcadiumGolemTextures;
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getEntityTexture((EntityHarcadiumGolem)entity);
	}
}


