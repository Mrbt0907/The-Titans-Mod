package net.minecraft.theTitans.items;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.*;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class ItemTitanSpawnEgg extends Item
{
	public static final String[] omegafishEggsubs = new String[] 
	{
		"", ".desert", ".frost", ".sea", ".poison", ".magma", ".void"
	};
	public static final String[] cavespidertitanEggsubs = new String[] 
	{
		"", ".jesus", ".ore", ".venom", ".ice", ".void"
	};
	public static final String[] spidertitanEggsubs = new String[] 
	{
		"", ".test", ".mycelium", ".lunar", ".void"
	};
	public static final String[] zombietitanEggsubs = new String[] 
	{
		"", ".jungle", ".blood", ".radioactive", ".void"
	};
	public static final String[] skeletontitanEggsubs = new String[] 
	{
		"", ".sand", ".guitar", ".sleep", ".void"
	};
	public static final String[] creepertitanEggsubs = new String[] 
	{
		"", ".hell", ".ghost", ".ender", ".void"
	};
	public static final String[] zombiepigmantitanEggsubs = new String[] 
	{
		"", ".king", ".wither", ".count", ".void"
	};
	public static final String[] blazetitanEggsubs = new String[] 
	{
		"", ".blizzard", ".acid", ".dirt", ".void"
	};
	public static final String[] witherskeletontitanEggsubs = new String[] 
	{
		"", ".solar", ".reaper"
	};
	public static final String[] ghasttitanEggsubs = new String[] 
	{
		"", ".lightning", ".obsidian"
	};
	public static final String[] endercolossusEggsubs = new String[] 
	{
		"", ".holy", ".icey"
	};
	
	public ItemTitanSpawnEgg(String unlocalizedName)
	{
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(TheTitans.titansTab);
		setTextureName(TheTitans.getTextures(unlocalizedName));
		this.setHasSubtypes(true);
	}

	/**
	* Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
	* different names based on their damage or NBT.
	*/
	public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		int numb = 1;
		if (this == TitanItems.eggEnderColossus)
		numb = 3;
		if (this == TitanItems.eggGhastTitan)
		numb = 3;
		if (this == TitanItems.eggWitherSkeletonTitan)
		numb = 3;
		if (this == TitanItems.eggBlazeTitan)
		numb = 5;
		if (this == TitanItems.eggZombiePigmanTitan)
		numb = 5;
		if (this == TitanItems.eggCreeperTitan || this == TitanItems.eggChargedCreeperTitan)
		numb = 5;
		if (this == TitanItems.eggSkeletonTitan)
		numb = 5;
		if (this == TitanItems.eggZombieTitan)
		numb = 5;
		if (this == TitanItems.eggSpiderTitan)
		numb = 5;
		if (this == TitanItems.eggCaveSpiderTitan)
		numb = 6;
		if (this == TitanItems.eggOmegafish)
		numb = 7;
		int i = MathHelper.clamp_int(p_77667_1_.getItemDamage(), 0, numb);
		String sub = "";
		if (this == TitanItems.eggEnderColossus)
		sub = endercolossusEggsubs[i];
		if (this == TitanItems.eggGhastTitan)
		sub = ghasttitanEggsubs[i];
		if (this == TitanItems.eggWitherSkeletonTitan)
		sub = witherskeletontitanEggsubs[i];
		if (this == TitanItems.eggBlazeTitan)
		sub = blazetitanEggsubs[i];
		if (this == TitanItems.eggZombiePigmanTitan)
		sub = zombiepigmantitanEggsubs[i];
		if (this == TitanItems.eggCreeperTitan || this == TitanItems.eggChargedCreeperTitan)
		sub = creepertitanEggsubs[i];
		if (this == TitanItems.eggSkeletonTitan)
		sub = skeletontitanEggsubs[i];
		if (this == TitanItems.eggZombieTitan)
		sub = zombietitanEggsubs[i];
		if (this == TitanItems.eggSpiderTitan)
		sub = spidertitanEggsubs[i];
		if (this == TitanItems.eggCaveSpiderTitan)
		sub = cavespidertitanEggsubs[i];
		if (this == TitanItems.eggOmegafish)
		sub = omegafishEggsubs[i];
		return super.getUnlocalizedName() + sub;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		EntityTitan titan = getTitanToSpawn(worldIn);
		Block block = worldIn.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
		p_77648_4_ += net.minecraft.util.Facing.offsetsXForSide[p_77648_7_];
		p_77648_5_ += net.minecraft.util.Facing.offsetsYForSide[p_77648_7_];
		p_77648_6_ += net.minecraft.util.Facing.offsetsZForSide[p_77648_7_];
		double d0 = 0.0D;
		if ((p_77648_7_ == 1) && (block.getRenderType() == 11))
		d0 = 0.5D;
		titan.worldObj = worldIn;
		titan.onSpawnWithEgg((IEntityLivingData)null);
		titan.setLocationAndAngles(p_77648_4_ + 0.5D, p_77648_5_ + d0, p_77648_6_ + 0.5D, 0.0F, 0.0F);
		if (!worldIn.isRemote)
		worldIn.spawnEntityInWorld(titan);
		if (titan != null)
		{
			if (this == TitanItems.eggWitherSkeletonTitan || this == TitanItems.eggWitherJockeyTitan)
			{
				((EntitySkeletonTitan)titan).setSkeletonType(1);
			}

			if (this == TitanItems.eggChargedCreeperTitan)
			{
				((EntityCreeperTitan)titan).setPowered(true);
			}

			if (this == TitanItems.eggSpiderJockeyTitan)
			{
				EntitySpiderTitan entity = new EntitySpiderTitan(worldIn);
				entity.onSpawnWithEgg((IEntityLivingData)null);
				entity.setLocationAndAngles(p_77648_4_ + 0.5D, p_77648_5_ + d0, p_77648_6_ + 0.5D, 0.0F, 0.0F);
				worldIn.spawnEntityInWorld(entity);
				titan.mountEntity(entity);
			}

			titan.playLivingSound();
			titan.setTitanVariant(this.getDamage(stack));
			titan.playSound("random.break", 10000.0F, 0.5F);
			if (!playerIn.capabilities.isCreativeMode)
			stack.stackSize -= 1;
		}

		return true;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		if (this == TitanItems.eggWitherzilla)
		return TheTitans.godly;
		else if (this == TitanItems.eggWitherSkeletonTitan || this == TitanItems.eggWitherJockeyTitan || this == TitanItems.eggUltimaIronGolemTitan || this == TitanItems.eggGargoyleKing || this == TitanItems.eggEnderColossus || this == TitanItems.eggGhastTitan)
		return EnumRarity.epic;
		else if (this == TitanItems.eggCreeperTitan || this == TitanItems.eggChargedCreeperTitan || this == TitanItems.eggZombieTitan || this == TitanItems.eggZombiePigmanTitan || this == TitanItems.eggSkeletonTitan || this == TitanItems.eggSpiderJockeyTitan || this == TitanItems.eggBlazeTitan)
		return EnumRarity.rare;
		else
		return EnumRarity.uncommon;
	}

	public EntityTitan getTitanToSpawn(World world)
	{
		if (this == TitanItems.eggWitherzilla)
		return new EntityWitherzilla(world);
		else if (this == TitanItems.eggUltimaIronGolemTitan)
		return new EntityIronGolemTitan(world);
		else if (this == TitanItems.eggEnderColossus)
		return new EntityEnderColossus(world);
		else if (this == TitanItems.eggGhastTitan)
		return new EntityGhastTitan(world);
		else if (this == TitanItems.eggGargoyleKing)
		return new EntityGargoyleTitan(world);
		else if (this == TitanItems.eggWitherSkeletonTitan || this == TitanItems.eggWitherJockeyTitan || this == TitanItems.eggSkeletonTitan || this == TitanItems.eggSpiderJockeyTitan)
		return new EntitySkeletonTitan(world);
		else if (this == TitanItems.eggCreeperTitan || this == TitanItems.eggChargedCreeperTitan)
		return new EntityCreeperTitan(world);
		else if (this == TitanItems.eggZombieTitan)
		return new EntityZombieTitan(world);
		else if (this == TitanItems.eggSpiderTitan)
		return new EntitySpiderTitan(world);
		else if (this == TitanItems.eggCaveSpiderTitan)
		return new EntityCaveSpiderTitan(world);
		else if (this == TitanItems.eggBlazeTitan)
		return new EntityBlazeTitan(world);
		else if (this == TitanItems.eggZombiePigmanTitan)
		return new EntityPigZombieTitan(world);
		else if (this == TitanItems.eggOmegafish)
		return new EntitySilverfishTitan(world);
		else if (this == TitanItems.eggMagmaCubeTitan)
		return new EntityMagmaCubeTitan(world);
		else if (this == TitanItems.eggSlimeTitan)
		return new EntitySlimeTitan(world);
		else if (this == TitanItems.eggSnowGolemTitan)
		return new EntitySnowGolemTitan(world);
		else
		return null;
	}

	/**
	* returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	*/
	@SuppressWarnings(
	{
		 "rawtypes", "unchecked" 
	}
	)
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
	{
		int numb = 1;
		if (this == TitanItems.eggEnderColossus)
		numb = 3;
		if (this == TitanItems.eggGhastTitan)
		numb = 3;
		if (this == TitanItems.eggWitherSkeletonTitan)
		numb = 3;
		if (this == TitanItems.eggSkeletonTitan)
		numb = 5;
		if (this == TitanItems.eggCreeperTitan || this == TitanItems.eggChargedCreeperTitan)
		numb = 5;
		if (this == TitanItems.eggZombieTitan)
		numb = 5;
		if (this == TitanItems.eggSpiderTitan)
		numb = 5;
		if (this == TitanItems.eggCaveSpiderTitan)
		numb = 6;
		if (this == TitanItems.eggBlazeTitan)
		numb = 5;
		if (this == TitanItems.eggZombiePigmanTitan)
		numb = 5;
		if (this == TitanItems.eggOmegafish)
		numb = 7;
		for (int i = 0; i < numb; ++i)
		{
			p_150895_3_.add(new ItemStack(p_150895_1_, 1, i));
		}
	}
}


