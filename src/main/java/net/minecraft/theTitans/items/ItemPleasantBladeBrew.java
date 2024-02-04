package net.minecraft.theTitans.items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
public class ItemPleasantBladeBrew extends ItemBase
{
	public ItemPleasantBladeBrew()
	{
		super("pleasant_blade_brew");
	}

	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
	{
		if (!p_77654_3_.capabilities.isCreativeMode)
		{
			--p_77654_1_.stackSize;
		}

		if (!p_77654_2_.isRemote)
		{
			p_77654_3_.removePotionEffect(Potion.poison.id);
			p_77654_3_.removePotionEffect(Potion.hunger.id);
			p_77654_3_.removePotionEffect(Potion.confusion.id);
			p_77654_3_.removePotionEffect(Potion.wither.id);
		}

		return p_77654_1_.stackSize <= 0 ? new ItemStack(Items.glass_bottle) : p_77654_1_;
	}

	/**
	* How long it takes to use or consume an item
	*/
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 32;
	}

	/**
	* returns the action that specifies what animation to play when the items is being used
	*/
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.drink;
	}

	/**
	* Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	*/
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		return p_77659_1_;
	}
}


