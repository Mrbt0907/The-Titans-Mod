package net.minecraft.theTitans.items;
import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.titan.EntityHarcadiumArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
public class ItemNormalBow extends ItemBow
{
	@SideOnly(Side.CLIENT)
	protected IIcon[] iconArray;
	protected float arrowSpeed;
	protected Item arrowItem;
	protected Class<? extends EntityArrow> arrow;
	private List<String> information = new ArrayList<String>();
	protected StatBase achievement;
	public ItemNormalBow(String materialName, ToolMaterial material, Item arrowItem, Class<? extends EntityArrow> arrow, float arrowSpeed, String... information)
	{
		setUnlocalizedName(materialName + "_bow");
		setCreativeTab(TheTitans.titansTab);
		this.arrowSpeed = arrowSpeed;
		this.arrow = arrow;
		this.arrowItem = arrowItem;
		setMaxDamage(material.getMaxUses());
	}

	public ItemNormalBow(String materialName, ToolMaterial material, float arrowSpeed, String... information)
	{
		this(materialName, material, null, null, arrowSpeed, information);
	}

	public ItemNormalBow(String materialName, ToolMaterial material, String... information)
	{
		this(materialName, material, null, null, 1.0F, information);
	}

	public ItemNormalBow(String materialName, ToolMaterial material, float arrowSpeed)
	{
		this(materialName, material, null, null, arrowSpeed);
	}

	public ItemNormalBow(String materialName, ToolMaterial material)
	{
		this(materialName, material, null, null, 1.0F);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_)
	{
		itemIcon = p_94581_1_.registerIcon(TheTitans.getTextures(getUnlocalizedName().substring(5) + "_standby"));
		iconArray = new IIcon[bowPullIconNameArray.length];
		for (int i = 0; i < bowPullIconNameArray.length; i++)
		iconArray[i] = p_94581_1_.registerIcon(TheTitans.getTextures(getUnlocalizedName().substring(5) + "_" + bowPullIconNameArray[i]));
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		if (usingItem == null)
		return this.itemIcon;
		int ticksInUse = stack.getMaxItemUseDuration() - useRemaining;
		if (ticksInUse > 17)
		return iconArray[2]; 
		if (ticksInUse > 13)
		return iconArray[1]; 
		if (ticksInUse > 0)
		return iconArray[0];
		return this.itemIcon;
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

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if ((entityIn instanceof EntityPlayer))
		((EntityPlayer)entityIn).triggerAchievement(achievement);
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public void onPlayerStoppedUsing(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_)
	{
		onFire(p_77615_1_, p_77615_2_, p_77615_3_, p_77615_4_);
	}

	public void onFire(ItemStack stack, World worldIn, EntityPlayer playerIn, int useTime)
	{
		int j = stack.getMaxItemUseDuration() - useTime;
		ArrowLooseEvent event = new ArrowLooseEvent(playerIn, stack, j);
		if (MinecraftForge.EVENT_BUS.post(event))
		return;
		j = event.charge;
		boolean flag = (playerIn.capabilities.isCreativeMode) || (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0);
		if ((flag) || (getArrowItem() != null && playerIn.inventory.hasItem(getArrowItem())))
		{
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;
			if (f < 0.1D)
			return;
			if (f > 1.0F)
			f = 1.0F;
			EntityArrow entityarrow = getArrow(worldIn, playerIn, f * 2.0F);
			if (entityarrow == null)
			return;
			if (f == 1.0F)
			entityarrow.setIsCritical(true);
			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
			if (k > 0)
			entityarrow.setDamage(entityarrow.getDamage() + k * 100D);
			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
			if (l > 0)
			entityarrow.setKnockbackStrength(l * 3);
			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
			entityarrow.setFire(500);
			stack.damageItem(1, playerIn);
			worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			if (flag)
			entityarrow.canBePickedUp = 2;
			else
			playerIn.inventory.consumeInventoryItem(getArrowItem());
			playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
			if (!worldIn.isRemote)
			{
				worldIn.spawnEntityInWorld(entityarrow);
				Vec3 vec3 = playerIn.getLook(1.0F);
				entityarrow.posX = (playerIn.posX + vec3.xCoord);
				entityarrow.posY = (playerIn.posY + vec3.yCoord + 1.5D);
				entityarrow.posZ = (playerIn.posZ + vec3.zCoord);
			}
		}
	}

	public ItemNormalBow setAchievement(StatBase achievement)
	{
		this.achievement = achievement;
		return this;
	}

	public StatBase getAchievement()
	{
		return achievement;
	}

	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		ArrowNockEvent event = new ArrowNockEvent(playerIn, itemStackIn);
		if (MinecraftForge.EVENT_BUS.post(event)) return event.result;
		if ((playerIn.capabilities.isCreativeMode) || (playerIn.inventory.hasItem(getArrowItem())))
		playerIn.setItemInUse(itemStackIn, itemStackIn.getMaxItemUseDuration());
		return itemStackIn;
	}

	public ItemNormalBow addInfo(String... information)
	{
		if (information.length > 0)
		for (int i = 0; i < information.length; i++)
		if (information[i] != null)
		this.information.add(information[i]);
		return this;
	}

	protected Item getArrowItem()
	{
		return arrowItem == null ? Items.arrow : arrowItem;
	}

	protected EntityArrow getArrow(World world, EntityPlayer player, float speedMultiplier)
	{
		if (arrow == null)
		return new EntityArrow(world, player, arrowSpeed * speedMultiplier);
		else
		{
			EntityArrow newArrow;
			try
			{
				newArrow = arrow.getConstructor(World.class, EntityLivingBase.class, float.class).newInstance(world, player, 1.0F);
			}

			catch (Exception e)
			{
				newArrow = new EntityArrow(world, player, arrowSpeed * speedMultiplier);
				TheTitans.error("Attempted to use an entity that does not extend EntityArrow(World, EntityLivingBase, float)", e);
			}

			if (newArrow instanceof EntityHarcadiumArrow)
			if (getArrowItem() instanceof ItemVoidArrow)
			((EntityHarcadiumArrow)newArrow).setArrowType(1);
			newArrow.setThrowableHeading(newArrow.motionX, newArrow.motionY, newArrow.motionZ, arrowSpeed * speedMultiplier * 1.5F, 1.0F);
			return newArrow;
		}
	}
}


