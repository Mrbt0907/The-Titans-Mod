package net.minecraft.theTitans;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
public class PotionRadiation extends Potion
{
	protected PotionRadiation(int p_i1573_1_, boolean p_i1573_2_, int p_i1573_3_)
	{
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
	}

	public Potion setIconIndex(int x, int y)
	{
		super.setIconIndex(x, y);
		return(Potion) this;
	}

	public boolean isBadEffect()
	{
		return true;
	}

	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.getTexture(ClientProxy.titanpotions);
		Minecraft.getMinecraft().renderEngine.bindTexture(ClientProxy.titanpotions);
		return super.getStatusIconIndex();
	}

	public boolean isReady(int p_76397_1_, int p_76397_2_)
	{
		int k = 20 >> p_76397_2_;
		return k > 0 ? p_76397_1_ % k == 0 : true;
	}

	public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_)
	{
		if (p_76394_1_.isEntityAlive())
		{
			p_76394_1_.attackEntityFrom(DamageSourceExtra.radiation, 2.0F * p_76394_2_);
			p_76394_1_.worldObj.playSound(p_76394_1_.posX, p_76394_1_.posY, p_76394_1_.posZ, "game.player.hurt", 2F, 2F, false);
		}

		super.performEffect(p_76394_1_, p_76394_2_);
	}
}


