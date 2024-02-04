package net.minecraft.titans.client.entity.renders;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.client.entity.models.ModelCreeperTitanNuclear;
import net.minecraft.titans.entity.titan.EntityCreeperTitan;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCreeperTitan extends RenderTitan<EntityCreeperTitan>
{
	private static final ResourceLocation textures = new ResourceLocation(TheTitans.MODID, "textures/entities/titans/creeper_titan/creeper_titan_nuclear.png");
	
	public RenderCreeperTitan(RenderManager p_i47192_1_)
	{
		super(p_i47192_1_, new ModelCreeperTitanNuclear(), 1.0F);
	}

	protected ResourceLocation getEntityTexture(EntityCreeperTitan titan)
	{
		return textures;
	}

	@Override
	public boolean shouldSpecialRender(EntityCreeperTitan titan)
	{
		return true;
	}
}


