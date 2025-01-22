package net.mrbt0907.thetitans.client.entity.renders;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.client.entity.models.ModelWitherzilla;
import net.mrbt0907.thetitans.entity.god.EntityWitherzilla;

@SideOnly(Side.CLIENT)
public class RenderWitherzilla extends RenderTitan<EntityWitherzilla>
{
	public RenderWitherzilla(RenderManager p_i47192_1_)
	{
		super(p_i47192_1_, new ModelWitherzilla(), 1.0F);
	}

	protected ResourceLocation getEntityTexture(EntityWitherzilla titan)
	{
		return titan.isInOmegaForm() ? new ResourceLocation(TheTitans.MODID, "textures/entities/titans/god_titans/witherzilla_omega.png") : new ResourceLocation(TheTitans.MODID, "textures/entities/titans/god_titans/witherzilla.png");
	}

	@Override
	public boolean shouldSpecialRender(EntityWitherzilla titan)
	{
		return true;
	}
}


