package net.minecraft.theTitans.items;
import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.api.IBurnable;
import net.minecraft.theTitans.api.IReloadable;
import net.minecraft.world.World;
public class ItemBase extends Item implements IReloadable, IBurnable
{
	private List<String> information = new ArrayList<String>();
	protected StatBase achievement;
	public boolean mustBeCrafted;
	private int burnTime;
	public ItemBase(String unlocalizedName, StatBase achievement, String... information)
	{
		setTextureName(TheTitans.getTextures(unlocalizedName));
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(TheTitans.titansTab);
		addInfo(information);
		setAchievement(null);
		mustBeCrafted = true;
		burnTime = 0;
	}

	public ItemBase(String unlocalizedName, StatBase achievement)
	{
		this(unlocalizedName, achievement, (String)null);
	}

	public ItemBase(String unlocalizedName)
	{
		this(unlocalizedName, null, (String)null);
	}

	@SuppressWarnings(
	{
		 "unchecked", "rawtypes" 
	}
	)
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_)
	{
		super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
		p_77624_3_.addAll(information);
	}

	public ItemBase addInfo(String... information)
	{
		if (information.length > 0)
		for (int i = 0; i < information.length; i++)
		if (information[i] != null)
		this.information.add(information[i]);
		return this;
	}

	public ItemBase setAchievement(StatBase achievement)
	{
		this.achievement = achievement;
		return this;
	}

	public StatBase getAchievement()
	{
		return achievement;
	}

	public void onCreated(ItemStack stack, World world, EntityPlayer player)
	{
		if (getAchievement() != null && mustBeCrafted)
		player.triggerAchievement(getAchievement());
		super.onCreated(stack, world, player);
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if (getAchievement() != null && entity instanceof EntityPlayer && !mustBeCrafted)
		((EntityPlayer)entity).triggerAchievement(getAchievement());
		super.onUpdate(stack, world, entity, itemSlot, isSelected);
	}

	public void refreshTextures()
	{
		setTextureName(TheTitans.getTextures(getUnlocalizedName().substring(5)));
	}

	@Override
	public void setBurnTime(int burnTime)
	{
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTime()
	{
		return burnTime;
	}
}


