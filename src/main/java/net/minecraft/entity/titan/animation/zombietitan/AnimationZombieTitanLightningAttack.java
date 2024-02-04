package net.minecraft.entity.titan.animation.zombietitan;
import net.minecraft.entity.titan.EntityGammaLightning;
import net.minecraft.entity.titan.EntityZombieTitan;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationZombieTitanLightningAttack
extends AIAnimation
{
	private EntityZombieTitan entity;
	public AnimationZombieTitanLightningAttack(EntityZombieTitan test)
	{
		super(test);
		this.entity = test;
	}

	public int getAnimID()
	{
		return 5;
	}

	public boolean isAutomatic()
	{
		return true;
	}

	public int getDuration()
	{
		return 110;
	}

	public boolean continueExecuting()
	{
		return this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAttackTarget() != null)
		this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 0.0F);
		if (entity.isClient() && entity.getAnimTick() == 34)
		entity.playSound("thetitans:lightningCharge", 100F, 1F);
		if (entity.getAnimTick() <= 46 && entity.getAnimTick() >= 26)
		{
			float ex = 1.1875F * entity.width;
			float fl = this.entity.renderYawOffset * 3.1415927F / 180.0F;
			float fl1 = MathHelper.sin(fl);
			float fl2 = MathHelper.cos(fl);
			EntityGammaLightning lightning1 = new EntityGammaLightning(this.entity.worldObj, this.entity.posX - fl2 * ex, this.entity.posY + (0.8125D * entity.height), this.entity.posZ - fl1 * ex, 0.0F, 0.56F, 0F);
			lightning1.setRed(0F);
			lightning1.setGreen(0.56F);
			lightning1.setBlue(0F);
			lightning1.setPosition(this.entity.posX - fl2 * ex, this.entity.posY + (0.8125D * entity.height), this.entity.posZ - fl1 * ex);
			EntityGammaLightning lightning2 = new EntityGammaLightning(this.entity.worldObj, this.entity.posX + fl2 * ex, this.entity.posY + (0.8125D * entity.height), this.entity.posZ + fl1 * ex, 0.0F, 0.56F, 0F);
			lightning2.setRed(0F);
			lightning2.setGreen(0.56F);
			lightning2.setBlue(0F);
			lightning2.setPosition(this.entity.posX + fl2 * ex, this.entity.posY + (0.8125D * entity.height), this.entity.posZ + fl1 * ex);
			this.entity.worldObj.addWeatherEffect(lightning1);
			this.entity.worldObj.addWeatherEffect(lightning2);
			if (entity.getAttackTarget() == null)
			this.entity.heal(50F);
		}

		if (entity.getAnimTick() == 64)
		{
			this.entity.playSound("thetitans:lightningThrow", 100F, 1F);
			double d8 = 1.25F * entity.width;
			Vec3 vec3 = entity.getLook(1.0F);
			double dx = vec3.xCoord * d8;
			double dz = vec3.zCoord * d8;
			this.entity.worldObj.newExplosion(entity, this.entity.posX + dx, this.entity.posY + (0.8125D * entity.height), this.entity.posZ + dz, 1.0F, false, false);
			for (int i = 0; i <= 4; ++i)
			{
				EntityGammaLightning lightning = new EntityGammaLightning(this.entity.worldObj, this.entity.posX + dx, this.entity.posY + (entity.isChild() ? 18D : 36D), this.entity.posZ + dz, 0.0F, 0.56F, 0F);
				lightning.setRed(0F);
				lightning.setGreen(0.56F);
				lightning.setBlue(0F);
				lightning.setPosition(this.entity.posX + dx, this.entity.posY + (0.5625F * entity.height), this.entity.posZ + dz);
				this.entity.worldObj.addWeatherEffect(lightning);
			}

			if (entity.getAttackTarget() != null)
			{
				EntityGammaLightning lightning = new EntityGammaLightning(this.entity.worldObj, entity.getAttackTarget().posX, entity.getAttackTarget().posY, entity.getAttackTarget().posZ, 0.0F, 0.56F, 0F);
				lightning.setRed(0F);
				lightning.setGreen(0.56F);
				lightning.setBlue(0F);
				lightning.setPosition(entity.getAttackTarget().posX, entity.getAttackTarget().posY, entity.getAttackTarget().posZ);
				this.entity.worldObj.addWeatherEffect(lightning);
				this.entity.worldObj.newExplosion(entity, entity.getAttackTarget().posX, entity.getAttackTarget().posY, entity.getAttackTarget().posZ, 2.0F, false, false);
				float da = (float)entity.getAttackValue(1.0F);
				int i = entity.getKnockbackAmount();
				entity.attackChoosenEntity(entity.getAttackTarget(), da * 2.0F, i);
				entity.getAttackTarget().motionY += 1.0F + this.entity.getRNG().nextFloat();
				entity.getAttackTarget().attackEntityFrom(DamageSourceExtra.lightningBolt, da);
			}
		}
	}
}


