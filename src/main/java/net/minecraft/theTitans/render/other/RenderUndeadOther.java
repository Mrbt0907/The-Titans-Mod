package net.minecraft.theTitans.render.other;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.titan.other.EntityUndeadOther;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderUndeadOther extends RenderBiped
{
	public static final ResourceLocation[] UNDEAD_TEXTURE_LIST = new ResourceLocation[]
	{
		new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/golden_guard.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie1.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie2.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie3.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie4.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie5.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie6.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie7.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie8.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie9.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/zombie10.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/ghoul_zombie.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/wight_zombie.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/ender_zombie.png"))
	};
	public RenderUndeadOther()
	{
		super(new ModelZombie(), 0.5F);
		setRenderPassModel(this.mainModel);
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(EntityUndeadOther mob)
	{
		return UNDEAD_TEXTURE_LIST[mob.getMobType()];
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntityUndeadOther)p_110775_1_);
	}
}


