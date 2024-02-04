package net.minecraft.theTitans.items;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
public class ItemForTheChallengeGames1 extends Item
{
	public ItemForTheChallengeGames1()
	{
		setUnlocalizedName("witherskeletonspawn");
		setCreativeTab(TheTitans.titansTab);
		setTextureName(TheTitans.getTextures("witherskeletonspawn"));
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		playerIn.playSound("thetitans:OMG", 10.0F, 0.25F + playerIn.getRNG().nextFloat() * 2F);
		for (int i = 0; i <= 9; i++)
		{
			new Random();
			EntitySkeleton entity = new EntitySkeleton(worldIn);
			Block block = worldIn.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
			double d0 = 1.0D;
			if ((p_77648_7_ == 1) && (block.getRenderType() == 11))
			{
				d0 = 1.5D;
			}

			entity.motionX = entity.getRNG().nextDouble() * 2D - 1D;
			entity.motionZ = entity.getRNG().nextDouble() * 2D - 1D;
			entity.onSpawnWithEgg((IEntityLivingData)null);
			entity.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
			entity.setLocationAndAngles(p_77648_4_ + 0.5D, p_77648_5_ + d0, p_77648_6_ + 0.5D, 0.0F, 0.0F);
			entity.setSkeletonType(1);
			entity.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
			entity.tasks.addTask(4, new EntityAIAttackOnCollide(entity, 1.2D, true));
			entity.targetTasks.addTask(2, new EntityAINearestAttackableTarget(entity, EntityIronGolem.class, 0, true));
			if (!worldIn.isRemote)
			{
				//Entity var8 = null;
				//var8 = EntityList.createEntityByName("Criminal", worldIn);
				//if (var8 != null)
				//
				{

					//var8.setLocationAndAngles(p_77648_4_ + 0.5D, p_77648_5_ + d0, p_77648_6_ + 0.5D, worldIn.rand.nextFloat() * 360.0F, 0.0F);
					//worldIn.spawnEntityInWorld(var8);
					//((EntityLiving)var8).playLivingSound();
					//
				}


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


