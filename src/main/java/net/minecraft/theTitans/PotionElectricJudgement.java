package net.minecraft.theTitans;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
public class PotionElectricJudgement extends Potion
{
	protected PotionElectricJudgement(int p_i1573_1_, boolean p_i1573_2_, int p_i1573_3_)
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
		int k = 60 >> p_76397_2_;
		return k > 0 ? p_76397_1_ % k == 0 : true;
	}

	public void performEffect(EntityLivingBase p_76394_1_, int p_76394_2_)
	{
		if (p_76394_1_.isEntityAlive())
		{
			if (!p_76394_1_.worldObj.isRemote && p_76394_1_.isEntityAlive())
			p_76394_1_.worldObj.addWeatherEffect(new EntityLightningBolt(p_76394_1_.worldObj, p_76394_1_.posX - 0.5D, p_76394_1_.posY, p_76394_1_.posZ - 0.5D));
			p_76394_1_.attackEntityFrom(DamageSourceExtra.lightningBolt, 5.0F * p_76394_2_);
			p_76394_1_.setFire(20);
			if ((p_76394_1_.getMaxHealth() > 1000000000F || p_76394_1_.height < 0.5F) && !(p_76394_1_ instanceof EntityTitanSpirit) && !(p_76394_1_ instanceof EntityTitan) && !(p_76394_1_ instanceof EntityPlayer))
			{
				p_76394_1_.playSound("random.explode", 2F, 1F + p_76394_1_.getRNG().nextFloat());
				p_76394_1_.setHealth(p_76394_1_.getHealth() / 2F);
				if (p_76394_1_.getHealth() <= 1F)
				{
					p_76394_1_.worldObj.createExplosion(null, p_76394_1_.posX, p_76394_1_.posY, p_76394_1_.posZ, 7F, false);
					p_76394_1_.setDead();
					List<?> list11 = p_76394_1_.worldObj.getEntitiesWithinAABBExcludingEntity(p_76394_1_, p_76394_1_.boundingBox.expand(7, 7, 7));
					if ((list11 != null) && (!list11.isEmpty()))
					{
						for (int i1 = 0; i1 < list11.size(); i1++)
						{
							Entity entity = (Entity)list11.get(i1);
							if (entity instanceof EntityLivingBase)
							((EntityLivingBase)entity).addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, Integer.MAX_VALUE, 9));
						}
					}
				}
			}
		}

		super.performEffect(p_76394_1_, p_76394_2_);
	}
}


