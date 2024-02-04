package net.minecraft.theTitans.render.other;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.other.EntityMagicUser;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.models.ModelSpellcaster;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderSpellcaster extends RenderBiped
{
	private ModelSpellcaster endermanModel;
	public static final ResourceLocation[] SPELLCASTER_TEXTURE_LIST = new ResourceLocation[]
	{
		new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_novice.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_novice.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_novice.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_fire.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_ice.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_earth.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_lightning.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_void.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_sniper.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_healer.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_barrage.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_aether.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/magic_archmage.png"))
	};
	public RenderSpellcaster()
	{
		super(new ModelSpellcaster(), 0.5F);
		this.endermanModel = (ModelSpellcaster)super.mainModel;
		setRenderPassModel(this.mainModel);
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(EntityMagicUser mob)
	{
		return SPELLCASTER_TEXTURE_LIST[mob.getMobType()];
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntityMagicUser)p_110775_1_);
	}

	public void doRender(EntityMagicUser p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.endermanModel.castingSpell = p_76986_1_.isCastingSpell();
		super.doRender((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMagicUser)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMagicUser)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMagicUser)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}


