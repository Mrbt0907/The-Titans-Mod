package net.minecraft.theTitans.items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityChaff;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
public class ItemChaff extends ItemBase
{
	public ItemChaff()
	{
		super("chaff");
		this.maxStackSize = 24;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		EntityChaff entity1 = new EntityChaff(worldIn);
		EntityChaff entity2 = new EntityChaff(worldIn);
		if (!worldIn.isRemote)
		{
			entity1.setLocationAndAngles(playerIn.posX + 6.0D, playerIn.posY + 3.0D, playerIn.posZ + 6.0D, 0.0F, 0.0F);
			entity2.setLocationAndAngles(playerIn.posX - 6.0D, playerIn.posY + 3.0D, playerIn.posZ - 6.0D, 0.0F, 0.0F);
			worldIn.spawnEntityInWorld(entity1);
			worldIn.spawnEntityInWorld(entity2);
		}

		if (!playerIn.capabilities.isCreativeMode)
		stack.stackSize -= 1;
		return true;
	}
}


