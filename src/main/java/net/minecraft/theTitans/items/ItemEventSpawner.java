package net.minecraft.theTitans.items;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class ItemEventSpawner extends ItemBase
{
	private IIcon[] field_150920_d;
	public static final String[] item = new String[]
	{
		"bone_with_flesh","goltinum","burning_sigal","strange_egg","dark_eye","forbidden_crystal","aetherial_beacon"
	};
	
	public ItemEventSpawner()
	{
		super("event");
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int p_77617_1_)
	{
		int j = MathHelper.clamp_int(p_77617_1_, 0, 6);
		return this.field_150920_d[j];
	}

	@SuppressWarnings(
	{
		 "rawtypes", "unchecked" 
	}
	)
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
	{
		for (int i = 0; i < 7; ++i)
		{
			p_150895_3_.add(new ItemStack(p_150895_1_, 1, i));
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack p_77636_1_)
	{
		return true;
	}

	public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		int i = MathHelper.clamp_int(p_77667_1_.getItemDamage(), 0, 6);
		return super.getUnlocalizedName() + "." + item[i];
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_)
	{
		this.field_150920_d = new IIcon[item.length];
		for (int i = 0; i < item.length; ++i)
		{
			this.field_150920_d[i] = p_94581_1_.registerIcon(getIconString() + "_" + item[i]);
		}
	}

	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		return p_77659_1_;
	}

	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int p_77615_4_)
	{
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.epic;
	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.block;
	}

	public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 72000;
	}
}


