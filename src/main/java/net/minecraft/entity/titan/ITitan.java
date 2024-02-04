package net.minecraft.entity.titan;
import cpw.mods.fml.common.Loader;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titanminion.*;
public abstract interface ITitan
{
	public static final IEntitySelector SearchForAThingToKill = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			if (p_179983_1_ == null)
			return false;
			else if (p_179983_1_ instanceof EntityTitanSpirit)
			return false;
			else if (p_179983_1_ instanceof EntityTitan && !(p_179983_1_ instanceof EntitySnowGolemTitan) && !(p_179983_1_ instanceof EntityIronGolemTitan) && !(p_179983_1_ instanceof EntityGargoyleTitan) && !(p_179983_1_ instanceof EntityWitherzilla))
			return false;
			else if (p_179983_1_ instanceof IMinion)
			return false;
			else if (Loader.isModLoaded("MutantCreatures") && (p_179983_1_ instanceof thehippomaster.MutantCreatures.MutantCreeper || p_179983_1_ instanceof thehippomaster.MutantCreatures.MutantZombie || p_179983_1_ instanceof thehippomaster.MutantCreatures.MutantSkeleton || p_179983_1_ instanceof thehippomaster.MutantCreatures.MutantEnderman || p_179983_1_ instanceof thehippomaster.MutantCreatures.Zombie))
			return false;
			else if (p_179983_1_ instanceof EntityPlayer && ((EntityPlayer)p_179983_1_).capabilities.isCreativeMode)
			return false;
			else if ((p_179983_1_ instanceof EntityAnimal && !(p_179983_1_ instanceof EntityTameable)) || p_179983_1_ instanceof EntityZombie || p_179983_1_ instanceof EntitySkeleton || p_179983_1_ instanceof EntitySpider || p_179983_1_ instanceof EntityCreeper || p_179983_1_ instanceof EntityEnderman || p_179983_1_ instanceof EntityBlaze || p_179983_1_ instanceof EntityGhast || p_179983_1_ instanceof EntityWither || p_179983_1_ instanceof EntityDragon || p_179983_1_ instanceof EntitySilverfish || p_179983_1_ instanceof EntitySlime || p_179983_1_ instanceof EntityGiantZombieBetter)
			return false;
			else
			return p_179983_1_.isEntityAlive();
		}
	};
	public static final IEntitySelector BlazeTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntityBlazeMinion)) && (!(p_179983_1_ instanceof EntityBlazeTitan));
		}
	};
	public static final IEntitySelector CaveSpiderTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntityCaveSpiderMinion)) && (!(p_179983_1_ instanceof EntityCaveSpiderTitan));
		}
	};
	public static final IEntitySelector CreeperTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntityCreeperMinion)) && (!(p_179983_1_ instanceof EntityCreeperTitan)) || (Loader.isModLoaded("MutantCreatures") && !(p_179983_1_ instanceof thehippomaster.MutantCreatures.MutantCreeper));
		}
	};
	public static final IEntitySelector EnderColossusSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntityEndermanMinion)) && (!(p_179983_1_ instanceof EntityEnderColossus)) && (!(p_179983_1_ instanceof EntityDragon)) && (!(p_179983_1_ instanceof EntityDragonMinion)) && (!(p_179983_1_ instanceof EntityEnderColossusCrystal)) || (Loader.isModLoaded("MutantCreatures") && !(p_179983_1_ instanceof thehippomaster.MutantCreatures.MutantEnderman));
		}
	};
	public static final IEntitySelector GhastTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntityGhastMinion)) && (!(p_179983_1_ instanceof EntityGhastTitan));
		}
	};
	public static final IEntitySelector MagmaCubeTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntityMagmaCube)) && ((!(p_179983_1_ instanceof EntityMagmaCubeTitan)) || ((p_179983_1_ instanceof EntitySlimeTitan)));
		}
	};
	public static final IEntitySelector PigZombieTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntityPigZombieMinion)) && (!(p_179983_1_ instanceof EntityGhastGuard)) && (!(p_179983_1_ instanceof EntityPigZombieTitan));
		}
	};
	public static final IEntitySelector SilverfishTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntitySilverfishMinion)) && (!(p_179983_1_ instanceof EntitySilverfishTitan));
		}
	};
	public static final IEntitySelector SkeletonTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntitySkeletonMinion)) && (!(p_179983_1_ instanceof EntitySkeletonTitan)) && (!(p_179983_1_ instanceof EntityWitherMinion)) || (Loader.isModLoaded("MutantCreatures") && !(p_179983_1_ instanceof thehippomaster.MutantCreatures.MutantSkeleton));
		}
	};
	public static final IEntitySelector SlimeTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return ((!(p_179983_1_ instanceof EntitySlime)) || ((p_179983_1_ instanceof EntityMagmaCube))) && ((!(p_179983_1_ instanceof EntitySlimeTitan)) || ((p_179983_1_ instanceof EntityMagmaCubeTitan)));
		}
	};
	public static final IEntitySelector SpiderTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntitySpiderMinion)) && (!(p_179983_1_ instanceof EntitySpiderTitan) || (p_179983_1_ instanceof EntityCaveSpiderTitan));
		}
	};
	public static final IEntitySelector ZombieTitanSorter = new IEntitySelector()
	{
		public boolean isEntityApplicable(Entity p_179983_1_)
		{
			return (!(p_179983_1_ instanceof EntityZombieMinion)) && (!(p_179983_1_ instanceof EntityZombieTitan)) && (!(p_179983_1_ instanceof EntityGiantZombieBetter)) || (Loader.isModLoaded("MutantCreatures") && !(p_179983_1_ instanceof thehippomaster.MutantCreatures.MutantZombie));
		}
	};
	
	public boolean shouldCrush();
	public EnumTitanStatus getTitanStatus();
}


