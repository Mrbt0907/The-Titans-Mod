package net.minecraft.entity.titan.animation.endercolossus;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titan.EntityEnderColossus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import thehippomaster.AnimationAPI.AIAnimation;
public class AnimationEnderColossusScream
extends AIAnimation
{
	private EntityEnderColossus entity;
	public AnimationEnderColossusScream(EntityEnderColossus test)
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
		return 200;
	}

	public boolean continueExecuting()
	{
		return this.entity.getAnimTick() > this.getDuration() || this.entity.isStunned ? false : super.continueExecuting();
	}

	public void updateTask()
	{
		if (this.entity.getAnimTick() > 200)
		{
			this.entity.setAttackTarget(null);
		}

		if ((this.entity.getAnimTick() < 60) && (this.entity.getAttackTarget() != null))
		{
			this.entity.getLookHelper().setLookPositionWithEntity(this.entity.getAttackTarget(), 5.0F, 40.0F);
		}

		if (this.entity.getAnimTick() > 80)
		{
			WorldServer worldserver = net.minecraft.server.MinecraftServer.getServer().worldServers[0];
			WorldInfo worldinfo = worldserver.getWorldInfo();
			worldinfo.setRainTime(0);
			worldinfo.setThunderTime(0);
			worldinfo.setRaining(false);
			worldinfo.setThundering(false);
			this.entity.setScreaming(false);
			this.entity.collideWithEntities(this.entity.body, this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.body.boundingBox.expand(16.0D, 8.0D, 16.0D)));
			List<?> list11 = this.entity.worldObj.getEntitiesWithinAABBExcludingEntity(this.entity, this.entity.boundingBox.expand(entity.getTitanSizeMultiplier() * 16D, entity.getTitanSizeMultiplier() * 16D, entity.getTitanSizeMultiplier() * 16D));
			if ((list11 != null) && (!list11.isEmpty()))
			{
				for (int i1 = 0; i1 < list11.size(); i1++)
				{
					Entity entity1 = (Entity)list11.get(i1);
					if (this.entity.canAttackClass(entity1.getClass()))
					{
						this.entity.attackChoosenEntity(entity1, this.entity.getAnimTick(), 0);
						this.entity.attackChoosenEntity(entity1, this.entity.getAnimTick(), 0);
						this.entity.attackChoosenEntity(entity1, this.entity.getAnimTick(), 0);
						this.entity.attackChoosenEntity(entity1, 10000000, 0);
						entity1.motionX = 0D;
						entity1.motionZ = 0D;
						++entity1.rotationPitch;
						if (entity1 instanceof EntityLivingBase)
						{
							((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.confusion.id, 400, 1));
							((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 400, 99));
							((EntityLivingBase)entity1).addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 1));
						}
					}
				}
			}
		}

		if (entity.isClient() && this.entity.getAnimTick() == 80)
		this.entity.playSound("thetitans:titanEnderColossusScreamLong", Float.MAX_VALUE, 1.0F);
	}
}


