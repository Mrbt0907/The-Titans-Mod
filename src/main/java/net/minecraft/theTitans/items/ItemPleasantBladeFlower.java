package net.minecraft.theTitans.items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
public class ItemPleasantBladeFlower extends ItemFood
{
	public ItemPleasantBladeFlower(int p_i45341_1_, float p_i45341_2_, boolean p_i45341_3_)
	{
		super(p_i45341_1_, p_i45341_2_, p_i45341_3_);
		this.setUnlocalizedName("pleasant_blade_flower");
		this.setTextureName(TheTitans.getTextures("pleasant_blade_flower"));
		this.setCreativeTab(TheTitans.titansTab);
	}

	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return EnumRarity.rare;
	}

	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
	{
		if (!p_77654_2_.isRemote)
		{
			p_77654_3_.removePotionEffect(Potion.blindness.id);
			p_77654_3_.removePotionEffect(Potion.confusion.id);
			p_77654_3_.removePotionEffect(Potion.moveSlowdown.id);
			p_77654_3_.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1000, 1));
			p_77654_3_.heal(2F);
		}

		return super.onEaten(p_77654_1_, p_77654_2_, p_77654_3_);
	}

	/**
	* How long it takes to use or consume an item
	*/
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 16;
	}

	/**
	* returns the action that specifies what animation to play when the items is being used
	*/
	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.eat;
	}
}


