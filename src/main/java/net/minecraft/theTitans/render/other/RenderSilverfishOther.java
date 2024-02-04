package net.minecraft.theTitans.render.other;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.other.EntitySilverfishOther;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderSilverfishOther extends RenderLiving
{
	public static final ResourceLocation[] SILVERFISH_TEXTURE_LIST = new ResourceLocation[]
	{
		new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/poisonfish.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/ender_silverfish.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/endermite.png")),new ResourceLocation(TheTitans.getTextures("textures/entities", "mobs/dustworm.png"))
	};
	public RenderSilverfishOther()
	{
		super(new ModelSilverfish(), 0.3F);
	}

	protected float getDeathMaxRotation(EntitySilverfishOther p_77037_1_)
	{
		return 180.0F;
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(EntitySilverfishOther mob)
	{
		return SILVERFISH_TEXTURE_LIST[mob.getMobType()];
	}

	protected float getDeathMaxRotation(EntityLivingBase p_77037_1_)
	{
		return this.getDeathMaxRotation((EntitySilverfishOther)p_77037_1_);
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntitySilverfishOther)p_110775_1_);
	}
}


