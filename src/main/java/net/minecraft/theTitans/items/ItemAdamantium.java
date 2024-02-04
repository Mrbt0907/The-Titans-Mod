package net.minecraft.theTitans.items;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.EntityImmortalItem;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.world.World;
public class ItemAdamantium extends ItemBase
{
	public ItemAdamantium(String unlocalizedName)
	{
		super(unlocalizedName, TitansAchievments.adamantium);
		mustBeCrafted = false;
	}

	public boolean hasCustomEntity(ItemStack stack)
	{
		return true;
	}

	public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		return new EntityImmortalItem(world, location, itemstack);
	}
}


