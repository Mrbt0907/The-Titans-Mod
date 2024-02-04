package net.minecraft.theTitans.items;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
public class ItemTitanBow extends ItemNormalBow
{
	protected int useTicks;
	protected int serverTicks;
	@SideOnly(Side.CLIENT)
	protected int clientTicks;
	public ItemTitanBow(String materialName, ToolMaterial material, Item arrowItem, Class<? extends EntityArrow> arrow, float arrowSpeed, String... information)
	{
		super(materialName, material, arrowItem, arrow, arrowSpeed, information);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if (usingItem == null)
		return itemIcon;
		int ticksInUse = clientTicks;
		float ticksMult = 3.0F - (2.0F * Math.min(useTicks / (float)getMaxBowDuration(), 1.0F));
		if (ticksInUse > (int)(getMaxItemUseDuration() * 0.75 * ticksMult))
		return iconArray[2];
		else if (ticksInUse > (int)(getMaxItemUseDuration() * 0.35 * ticksMult))
		return iconArray[1];
		else if (ticksInUse > 0)
		return iconArray[0];
		else
		return itemIcon;
	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		if (player == null || player.isDead)
		return;
		useTicks ++;
		if (player.worldObj.isRemote)
		{
			clientTicks ++;
			if (clientTicks >= (int)(getMaxItemUseDuration() * (3.0F - (2.0F * Math.min( (getMaxItemUseDuration(stack) - count) / (float)getMaxBowDuration(), 1.0F)))))
			clientTicks = 0;
		}

		else

		{
						
			serverTicks ++;
			if (serverTicks >= (int)(getMaxItemUseDuration() * (3.0F - (2.0F * Math.min( (getMaxItemUseDuration(stack) - count) / (float)getMaxBowDuration(), 1.0F)))))
			{
				serverTicks = 0;
				onFire(stack, player.worldObj, player, 0);
			}
		}
	}

	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_)
	{
		if (p_77615_2_.isRemote)
		clientTicks = 0;
		else
		serverTicks = 0;
		useTicks = 0;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		if (target != null)
		{
			if (target.hurtResistantTime > 15)
			target.hurtResistantTime = 15;
			if (target.height >= 7.0F || target instanceof EntityTitan)
			{
				target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), getDamage() / 20.0F);
				target.playSound("thetitans:titanpunch", 10.0F, 1.0F);
			}

			else
			target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), getDamage() / 20000.0F);
		}

		return true;
	}

	public int getMaxBowDuration()
	{
		return 100;
	}

	public int getMaxItemUseDuration()
	{
		return 10;
	}

	protected float getDamage()
	{
		return 1.0F;
	}
}


