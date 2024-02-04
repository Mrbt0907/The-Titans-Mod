package net.minecraft.theTitans.items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
public class ItemSpawnEggTitans
extends Item
{
	public String monstername;
	public ItemSpawnEggTitans(String unlocalizedName, String mobname)
	{
		this.monstername = mobname;
		this.maxStackSize = 64;
		setTextureName(TheTitans.getTextures(unlocalizedName));
		setUnlocalizedName(unlocalizedName);
		setCreativeTab(CreativeTabs.tabMisc);
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if (par3World.isRemote)
		{
			return true;
		}

		Entity ent = spawn_something(this.monstername, par3World, par4 + 0.5D, par5 + 1.01D, par6 + 0.5D);
		if (ent != null)
		{
			if (((ent instanceof EntityLiving)) && (par1ItemStack.hasDisplayName()))
			{
				((EntityLiving)ent).setCustomNameTag(par1ItemStack.getDisplayName());
			}
		}

		if (!par2EntityPlayer.capabilities.isCreativeMode)
		{
			par1ItemStack.stackSize -= 1;
		}

		return true;
	}

	public static Entity spawn_something(String id, World world, double d0, double d1, double d2)
	{
		Entity ent = spawnCreature(world, "thetitans." + id, d0, d1, d2);
		return ent;
	}

	public static Entity spawnCreature(World par0World, String name, double par2, double par4, double par6)
	{
		Entity var8 = EntityList.createEntityByName(name, par0World);
		if (var8 != null)
		{
			var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);
			par0World.spawnEntityInWorld(var8);
			((EntityLiving)var8).onSpawnWithEgg(null);
			((EntityLiving)var8).playLivingSound();
		}

		return var8;
	}
}


