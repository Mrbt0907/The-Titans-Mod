package net.minecraft.theTitans.render.minions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.titanminion.EntityPigZombieMinion;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderPigZombieMinion
extends RenderBiped
{
	private static final ResourceLocation zombiePigmanTextures = new ResourceLocation("textures/entity/zombie_pigman.png");
	private static final ResourceLocation zombiePigmanPriestTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_pigman_priest.png"));
	private static final ResourceLocation zombiePigmanZealotTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_pigman_zealot.png"));
	private static final ResourceLocation zombiePigmanBishopTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_pigman_bishop.png"));
	private static final ResourceLocation zombiePigmanTemplarTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_pigman_templar.png"));
	public RenderPigZombieMinion()
	{
		super(new ModelZombie(), 0.5F, 1.0F);
		setRenderPassModel(new ModelZombie());
	}

	protected ResourceLocation func_177119_a(EntityPigZombieMinion p_177119_1_)
	{
		switch (p_177119_1_.getMinionTypeInt())
		{
			case 1:
			return zombiePigmanPriestTextures;
			case 2:
			return zombiePigmanZealotTextures;
			case 3:
			return zombiePigmanBishopTextures;
			case 4:
			return zombiePigmanTemplarTextures;
			default:
			return zombiePigmanTextures;
		}
	}

	protected ResourceLocation getEntityTexture(EntityLiving entity)
	{
		return func_177119_a((EntityPigZombieMinion)entity);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return func_177119_a((EntityPigZombieMinion)entity);
	}
}


