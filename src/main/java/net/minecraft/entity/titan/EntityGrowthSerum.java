package net.minecraft.entity.titan;
import cpw.mods.fml.common.Loader;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.titanminion.EntityGhastMinion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityGrowthSerum
extends EntityThrowable
{
	public EntityGrowthSerum(World worldIn)
	{
		super(worldIn);
		setSize(0.5F, 0.5F);
	}

	public EntityGrowthSerum(World worldIn, EntityLivingBase p_i1780_2_)
	{
		super(worldIn, p_i1780_2_);
		setSize(0.5F, 0.5F);
	}

	public EntityGrowthSerum(World worldIn, double p_i1781_2_, double p_i1781_4_, double p_i1781_6_)
	{
		super(worldIn, p_i1781_2_, p_i1781_4_, p_i1781_6_);
		setSize(0.5F, 0.5F);
	}

	protected void onImpact(MovingObjectPosition p_70184_1_)
	{
		if (!this.worldObj.isRemote)
		{
			if (p_70184_1_.entityHit != null)
			{
				if ((p_70184_1_.entityHit instanceof EntityTitan))
				{
					((EntityTitan)p_70184_1_.entityHit).playSound("random.fizz", 100F, 0.5F);
					((EntityTitan)p_70184_1_.entityHit).heal(50.0F);
					((EntityTitan)p_70184_1_.entityHit).setInvulTime(((EntityTitan)p_70184_1_.entityHit).getInvulTime() - 50);
				}

				else if ((p_70184_1_.entityHit instanceof EntityZombie))
				{
					if ((p_70184_1_.entityHit instanceof EntityPigZombie))
					{
						EntityPigZombieTitan entitychicken = new EntityPigZombieTitan(this.worldObj);
						entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
						p_70184_1_.entityHit.setDead();
						this.worldObj.spawnEntityInWorld(entitychicken);
						entitychicken.playSound("portal.travel", 10000.0F, 1F);
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
						entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1F);
						entitychicken.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
						entitychicken.setChild(((EntityPigZombie)p_70184_1_.entityHit).isChild());
					}

					else
					{
						EntityZombieTitan entitychicken = new EntityZombieTitan(this.worldObj);
						entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
						p_70184_1_.entityHit.setDead();
						this.worldObj.spawnEntityInWorld(entitychicken);
						entitychicken.playSound("portal.travel", 10000.0F, 1F);
						entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1F);
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
						entitychicken.setChild(((EntityZombie)p_70184_1_.entityHit).isChild());
						entitychicken.setVillager(((EntityZombie)p_70184_1_.entityHit).isVillager());
					}
				}

				else if ((p_70184_1_.entityHit instanceof EntitySkeleton))
				{
					EntitySkeletonTitan entitychicken = new EntitySkeletonTitan(this.worldObj);
					entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
					p_70184_1_.entityHit.setDead();
					entitychicken.setSkeletonType(((EntitySkeleton)p_70184_1_.entityHit).getSkeletonType());
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.playSound("portal.travel", 10000.0F, 1F);
					entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					if ((p_70184_1_.entityHit.ridingEntity != null) && ((p_70184_1_.entityHit.ridingEntity instanceof EntitySpider)))
					{
						EntitySpiderTitan entitychicken1 = new EntitySpiderTitan(this.worldObj);
						entitychicken1.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
						this.worldObj.spawnEntityInWorld(entitychicken1);
						entitychicken.mountEntity(entitychicken1);
						p_70184_1_.entityHit.ridingEntity.setDead();
						entitychicken1.onSpawnWithEgg((IEntityLivingData)null);
					}
				}

				else if (p_70184_1_.entityHit instanceof EntityCreeper)
				{
					EntityCreeperTitan entitychicken = new EntityCreeperTitan(this.worldObj);
					entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
					p_70184_1_.entityHit.setDead();
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.playSound("portal.travel", 10000.0F, 1F);
					entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setPowered(((EntityCreeper)p_70184_1_.entityHit).getPowered());
				}

				else if ((p_70184_1_.entityHit instanceof EntityBlaze))
				{
					EntityBlazeTitan entitychicken = new EntityBlazeTitan(this.worldObj);
					entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
					p_70184_1_.entityHit.setDead();
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.playSound("portal.travel", 10000.0F, 1F);
					entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				}

				else if ((p_70184_1_.entityHit instanceof EntitySpider))
				{
					if ((p_70184_1_.entityHit instanceof EntityCaveSpider))
					{
						EntityCaveSpiderTitan entitychicken = new EntityCaveSpiderTitan(this.worldObj);
						entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
						p_70184_1_.entityHit.setDead();
						this.worldObj.spawnEntityInWorld(entitychicken);
						entitychicken.playSound("portal.travel", 10000.0F, 1F);
						entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1.25F);
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					}

					else
					{
						EntitySpiderTitan entitychicken = new EntitySpiderTitan(this.worldObj);
						entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
						p_70184_1_.entityHit.setDead();
						this.worldObj.spawnEntityInWorld(entitychicken);
						entitychicken.playSound("portal.travel", 10000.0F, 1F);
						entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1.25F);
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
						if ((p_70184_1_.entityHit.riddenByEntity != null) && ((p_70184_1_.entityHit.riddenByEntity instanceof EntitySkeleton)))
						{
							EntitySkeletonTitan entitychicken1 = new EntitySkeletonTitan(this.worldObj);
							entitychicken1.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
							entitychicken1.setSkeletonType(((EntitySkeleton)p_70184_1_.entityHit.riddenByEntity).getSkeletonType());
							this.worldObj.spawnEntityInWorld(entitychicken1);
							entitychicken1.onSpawnWithEgg((IEntityLivingData)null);
							entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1.0F);
							entitychicken1.mountEntity(entitychicken);
							p_70184_1_.entityHit.riddenByEntity.setDead();
						}
					}
				}

				else if (p_70184_1_.entityHit instanceof EntityEnderman)
				{
					EntityEnderColossus entitychicken = new EntityEnderColossus(this.worldObj);
					entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
					p_70184_1_.entityHit.setDead();
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.playSound("portal.travel", 10000.0F, 1F);
					entitychicken.playSound("thetitans:titanBirth", 1000.0F, 0.875F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				}

				else if (p_70184_1_.entityHit instanceof EntityGhast || p_70184_1_.entityHit instanceof EntityGhastMinion)
				{
					EntityGhastTitan entitychicken = new EntityGhastTitan(this.worldObj);
					entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
					p_70184_1_.entityHit.setDead();
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.playSound("portal.travel", 10000.0F, 1F);
					entitychicken.playSound("thetitans:titanBirth", 1000.0F, 0.875F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				}

				else if ((p_70184_1_.entityHit instanceof EntitySnowman))
				{
					EntitySnowGolemTitan entitychicken = new EntitySnowGolemTitan(this.worldObj);
					entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
					p_70184_1_.entityHit.setDead();
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.playSound("random.break", 10000.0F, 0.5F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				}

				else if ((p_70184_1_.entityHit instanceof EntityGargoyle))
				{
					EntityGargoyleTitan entitychicken = new EntityGargoyleTitan(this.worldObj);
					entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
					p_70184_1_.entityHit.setDead();
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.playSound("random.break", 10000.0F, 0.5F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				}

				else if ((p_70184_1_.entityHit instanceof EntityIronGolem))
				{
					EntityIronGolemTitan entitychicken = new EntityIronGolemTitan(this.worldObj);
					entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
					p_70184_1_.entityHit.setDead();
					entitychicken.playSound("random.break", 10000.0F, 0.5F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					entitychicken.setPlayerCreated(((EntityIronGolem)p_70184_1_.entityHit).isPlayerCreated());
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					this.worldObj.spawnEntityInWorld(entitychicken);
				}

				else if ((p_70184_1_.entityHit instanceof EntitySilverfish))
				{
					EntitySilverfishTitan entitychicken = new EntitySilverfishTitan(this.worldObj);
					entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
					p_70184_1_.entityHit.setDead();
					this.worldObj.spawnEntityInWorld(entitychicken);
					entitychicken.playSound("portal.travel", 10000.0F, 1F);
					entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1.25F);
					entitychicken.onSpawnWithEgg((IEntityLivingData)null);
				}

				else if ((p_70184_1_.entityHit instanceof EntitySlime))
				{
					if ((p_70184_1_.entityHit instanceof EntityMagmaCube))
					{
						EntityMagmaCubeTitan entitychicken = new EntityMagmaCubeTitan(this.worldObj);
						entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
						p_70184_1_.entityHit.setDead();
						this.worldObj.spawnEntityInWorld(entitychicken);
						entitychicken.playSound("portal.travel", 10000.0F, 1F);
						entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1.5F);
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
						entitychicken.setSlimeSize(((EntityMagmaCube)p_70184_1_.entityHit).getSlimeSize());
					}

					else
					{
						EntitySlimeTitan entitychicken = new EntitySlimeTitan(this.worldObj);
						entitychicken.setLocationAndAngles(p_70184_1_.entityHit.posX, p_70184_1_.entityHit.posY, p_70184_1_.entityHit.posZ, p_70184_1_.entityHit.rotationYaw, 0.0F);
						p_70184_1_.entityHit.setDead();
						this.worldObj.spawnEntityInWorld(entitychicken);
						entitychicken.playSound("portal.travel", 10000.0F, 1F);
						entitychicken.playSound("thetitans:titanBirth", 1000.0F, 1.5F);
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
						entitychicken.setSlimeSize(((EntitySlime)p_70184_1_.entityHit).getSlimeSize());
					}
				}

				else if (Loader.isModLoaded("OreSpawn") && (p_70184_1_.entityHit instanceof danger.orespawn.EmperorScorpion) && !(p_70184_1_.entityHit instanceof net.minecraft.entity.orespawnaddon.EntityOverlordScorpion))
				{
					this.playSound("game.player.hurt", 2F, 2F);
					this.playSound("thetitans:slashFlesh", 2F, 1.5F);
					this.playSound("orespawn:emperorscorpion_death", 5F, 1.0F);
					if (!this.worldObj.isRemote)
					{
						net.minecraft.entity.orespawnaddon.EntityOverlordScorpion entitychicken = new net.minecraft.entity.orespawnaddon.EntityOverlordScorpion(this.worldObj);
						entitychicken.copyLocationAndAnglesFrom(p_70184_1_.entityHit);
						entitychicken.renderYawOffset = entitychicken.rotationYaw = entitychicken.rotationYawHead = ((EntityLivingBase)p_70184_1_.entityHit).rotationYawHead;
						p_70184_1_.entityHit.setDead();
						this.worldObj.spawnEntityInWorld(entitychicken);
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					}
				}

				else if (Loader.isModLoaded("OreSpawn") && (p_70184_1_.entityHit instanceof danger.orespawn.Kraken) && !(p_70184_1_.entityHit instanceof net.minecraft.entity.orespawnaddon.EntityMethuselahKraken))
				{
					this.playSound("game.player.hurt", 2F, 2F);
					this.playSound("thetitans:slashFlesh", 2F, 1.5F);
					this.playSound("orespawn:alo_death", 5F, 1.0F);
					if (!this.worldObj.isRemote)
					{
						net.minecraft.entity.orespawnaddon.EntityMethuselahKraken entitychicken = new net.minecraft.entity.orespawnaddon.EntityMethuselahKraken(this.worldObj);
						entitychicken.copyLocationAndAnglesFrom(p_70184_1_.entityHit);
						entitychicken.renderYawOffset = entitychicken.rotationYaw = entitychicken.rotationYawHead = ((EntityLivingBase)p_70184_1_.entityHit).rotationYawHead;
						p_70184_1_.entityHit.setDead();
						this.worldObj.spawnEntityInWorld(entitychicken);
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
					}
				}

				else if (Loader.isModLoaded("OreSpawn") && (p_70184_1_.entityHit instanceof danger.orespawn.Godzilla) && !(p_70184_1_.entityHit instanceof net.minecraft.entity.orespawnaddon.EntityBurningMobzilla))
				{
					this.playSound("game.player.hurt", 2F, 2F);
					this.playSound("thetitans:slashFlesh", 2F, 1.5F);
					this.playSound("orespawn:godzilla_death", 5F, 1.1F);
					if (!this.worldObj.isRemote)
					{
						net.minecraft.entity.orespawnaddon.EntityBurningMobzilla entitychicken = new net.minecraft.entity.orespawnaddon.EntityBurningMobzilla(this.worldObj);
						entitychicken.copyLocationAndAnglesFrom(p_70184_1_.entityHit);
						entitychicken.renderYawOffset = entitychicken.rotationYaw = entitychicken.rotationYawHead = ((EntityLivingBase)p_70184_1_.entityHit).rotationYawHead;
						p_70184_1_.entityHit.setDead();
						this.worldObj.spawnEntityInWorld(entitychicken);
						entitychicken.onSpawnWithEgg((IEntityLivingData)null);
						this.worldObj.newExplosion(entitychicken, entitychicken.posX, entitychicken.posY + (entitychicken.height / 2), entitychicken.posZ, 15, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
					}
				}

				else if ((p_70184_1_.entityHit instanceof EntityLivingBase))
				{
					this.playSound("game.player.hurt", 2F, 2F);
					((EntityLivingBase)p_70184_1_.entityHit).setFire(20);
					((EntityLivingBase)p_70184_1_.entityHit).attackEntityFrom(DamageSourceExtra.wip, 2000F);
					if (!this.worldObj.isRemote)
					this.dropItem(TitanItems.growthSerum, 1);
				}
			}
		}

		if (!this.worldObj.isRemote)
		{
			if (p_70184_1_.entityHit == null)
			this.dropItem(TitanItems.growthSerum, 1);
			this.playSound("game.player.hurt", 2F, 2F);
			setDead();
		}
	}
}


