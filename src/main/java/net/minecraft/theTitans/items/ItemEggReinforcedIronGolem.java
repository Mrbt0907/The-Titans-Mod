package net.minecraft.theTitans.items;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityIronGolemTitan;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
public class ItemEggReinforcedIronGolem
extends ItemBase
{
	public ItemEggReinforcedIronGolem()
	{
		super("spawn_egg_iron_golem_better");
		setTextureName(TheTitans.getTextures("eggultimairongolemtitan"));
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		for (int i = 0; i <= (playerIn.isSneaking() ? 0 : 29); i++)
		{
			new Random();
			EntityIronGolem entity = new EntityIronGolem(worldIn);
			//EntityGiantZombieBetter entity = new EntityGiantZombieBetter(worldIn);
			//EntityDragonMinion entity = new EntityDragonMinion(worldIn);
			//EntityWitherMinion entity = new EntityWitherMinion(worldIn);
			//EntityHarcadiumGolem entity = new EntityHarcadiumGolem(worldIn);
			//EntityVoidGolem entity = new EntityVoidGolem(worldIn);
			Block block = worldIn.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
			double d0 = 1.0D;
			if ((p_77648_7_ == 1) && (block.getRenderType() == 11))
			{
				d0 = 1.5D;
			}

			entity.motionX = entity.getRNG().nextDouble() * 4D - 2D;
			entity.motionZ = entity.getRNG().nextDouble() * 4D - 2D;
			entity.onSpawnWithEgg((IEntityLivingData)null);
			entity.setLocationAndAngles(p_77648_4_ + 0.5D, p_77648_5_ + d0, p_77648_6_ + 0.5D, 0.0F, 0.0F);
			entity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2000D);
			entity.setHealth(2000F);
			entity.setCustomNameTag("Reinforced Iron Golem");
			EntityIronGolemTitan.addTitanTargetingTaskToEntity(entity);
			entity.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
			entity.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
			if (!worldIn.isRemote)
			{
				worldIn.spawnEntityInWorld(entity);
			}
		}

		if (!playerIn.capabilities.isCreativeMode)
		{
			stack.stackSize -= 1;
		}

		return true;
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.rare;
	}
}


