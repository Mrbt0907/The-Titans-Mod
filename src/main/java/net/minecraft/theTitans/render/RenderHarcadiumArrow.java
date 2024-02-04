package net.minecraft.theTitans.render;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.titan.EntityHarcadiumArrow;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderHarcadiumArrow extends RenderArrow
{
	private static final ResourceLocation arrowTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "harcadium_arrow_entity.png"));
	private static final ResourceLocation arrow2Textures = new ResourceLocation(TheTitans.getTextures("textures/entities", "void_arrow_entity.png"));
	protected ResourceLocation getEntityTexture(EntityHarcadiumArrow p_180550_1_)
	{
		switch (p_180550_1_.getArrowType())
		{
			case 1:
			return arrow2Textures;
			default:
			return arrowTextures;
		}
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(EntityArrow p_110775_1_)
	{
		return getEntityTexture((EntityHarcadiumArrow)p_110775_1_);
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntityArrow)p_110775_1_);
	}
}


