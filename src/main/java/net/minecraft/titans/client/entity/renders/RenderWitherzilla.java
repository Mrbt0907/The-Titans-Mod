package net.minecraft.titans.client.entity.renders;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.client.entity.models.ModelWitherzilla;
import net.minecraft.titans.entity.god.EntityWitherzilla;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherzilla extends RenderTitan<EntityWitherzilla>
{
	private static final ResourceLocation theTruthAboutTheUniverseLaysIn1sAnd0s = new ResourceLocation(TheTitans.MODID, "textures/entities/binary.png");
	private static final ResourceLocation witherzillaSheild = new ResourceLocation(TheTitans.MODID, "textures/entities/wither_aura.png");
	private static final ResourceLocation witherzillaOmegaTextures = new ResourceLocation(TheTitans.MODID, "textures/entities/titans/god_titans/witherzilla_omega.png");
	private static final ResourceLocation witherzillaTextures = new ResourceLocation(TheTitans.MODID, "textures/entities/titans/god_titans/witherzilla.png");
	
	public RenderWitherzilla(RenderManager p_i47192_1_)
	{
		super(p_i47192_1_, new ModelWitherzilla(), 1.0F);
	}

	protected ResourceLocation getEntityTexture(EntityWitherzilla titan)
	{
		return titan.isInOmegaForm() ? witherzillaOmegaTextures : witherzillaTextures;
	}

	@Override
	public boolean shouldSpecialRender(EntityWitherzilla titan)
	{
		return true;
	}
}


