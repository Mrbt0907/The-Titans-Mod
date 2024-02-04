package net.minecraft.theTitans;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
public class PotionDeath extends Potion
{
	private static final ResourceLocation advancedwither = new ResourceLocation(TheTitans.MODID, "/textures/entities/effects/withered.png".substring(1));
	protected PotionDeath(int p_i1573_1_, boolean p_i1573_2_, int p_i1573_3_)
	{
		super(p_i1573_1_, p_i1573_2_, p_i1573_3_);
	}

	public Potion setIconIndex(int x, int y)
	{
		super.setIconIndex(x, y);
		return this;
	}

	public boolean isBadEffect()
	{
		return true;
	}

	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.getTexture(advancedwither);
		Minecraft.getMinecraft().renderEngine.bindTexture(advancedwither);
		return super.getStatusIconIndex();
	}

	public boolean isReady(int p_76397_1_, int p_76397_2_)
	{
		int k = 40 >> p_76397_2_;
		return k > 0 ? p_76397_1_ % k == 0 : true;
	}

	public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_)
	{
		p_76394_1_.attackEntityFrom(DamageSourceExtra.outOfWorld, 4F * p_76394_2_);
		p_76394_1_.setFire(20);
		if (p_76394_1_.deathTime > 0)
		{
			++p_76394_1_.deathTime;
			if (p_76394_1_.deathTime > 20)
			{
				p_76394_1_.setDead();
			}
		}

		if (p_76394_1_.getMaxHealth() > 1000000000F && !(p_76394_1_ instanceof EntityTitanSpirit) && !(p_76394_1_ instanceof EntityTitan) && !(p_76394_1_ instanceof EntityPlayer))
		{
			p_76394_1_.playSound("random.explode", 2F, 1F + p_76394_1_.getRNG().nextFloat());
			p_76394_1_.setHealth(p_76394_1_.getHealth() / 2F);
			if (p_76394_1_.getHealth() <= 1F)
			{
				p_76394_1_.worldObj.createExplosion(null, p_76394_1_.posX, p_76394_1_.posY, p_76394_1_.posZ, 7F, false);
				p_76394_1_.setDead();
			}
		}

		super.performEffect(p_76394_1_, p_76394_2_);
	}
}


