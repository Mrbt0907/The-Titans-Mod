package net.minecraft.theTitans.render.minions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titanminion.EntitySilverfishMinion;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderSilverfishMinion extends RenderLiving
{
	private static final ResourceLocation silverfishTemplarTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/silverfish/silverfish_templar.png"));
	private static final ResourceLocation silverfishBishopTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/silverfish/silverfish_bishop.png"));
	private static final ResourceLocation silverfishZealotTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/silverfish/silverfish_zealot.png"));
	private static final ResourceLocation silverfishPriestTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/silverfish/silverfish_priest.png"));
	private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
	@SuppressWarnings("unused")
	private static final ResourceLocation poisonfishTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "titans/omegafish/poisonfish.png"));
	public RenderSilverfishMinion()
	{
		super(new ModelSilverfish(), 0.3F);
	}

	protected float getDeathMaxRotation(EntitySilverfishMinion p_77037_1_)
	{
		return 180.0F;
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(EntitySilverfishMinion mob)
	{
		switch (mob.getMinionTypeInt())
		{
			case 1:
			return silverfishPriestTextures;
			case 2:
			return silverfishZealotTextures;
			case 3:
			return silverfishBishopTextures;
			case 4:
			return silverfishTemplarTextures;
			default:
			return silverfishTextures;
		}
	}

	protected float getDeathMaxRotation(EntityLivingBase p_77037_1_)
	{
		return this.getDeathMaxRotation((EntitySilverfishMinion)p_77037_1_);
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntitySilverfishMinion)p_110775_1_);
	}
}


