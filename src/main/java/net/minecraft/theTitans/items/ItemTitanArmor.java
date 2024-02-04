package net.minecraft.theTitans.items;
import java.util.List;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.network.NetworkHandler;
import net.minecraft.theTitans.network.packets.PacketPlayerAlive;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.mrbt0907.utils.Maths;
public class ItemTitanArmor
extends ItemArmor
{
	private AttributeModifier modifierHelmet;
	private AttributeModifier modifierChestplate;
	private AttributeModifier modifierLeggings;
	private AttributeModifier modifierBoots;
	private String materialName;
	private String types[] = 
	{
		"_helmet", "_chestplate", "_leggings", "_boots"
	};
	
	private float titanArmor;
	private float titanResistance;
	private int delay;
	protected int soundTicks;
	public ItemTitanArmor(String materialName, ItemArmor.ArmorMaterial material, int type, double[] maxHealth, float titanArmor, float titanResistance)
	{
		super(material, 0, type);
		this.materialName = materialName;
		this.titanArmor = titanArmor;
		this.titanResistance = titanResistance;
		materialName = materialName + types[type];
		setUnlocalizedName(materialName);
		setTextureName(TheTitans.getTextures(materialName));
		setCreativeTab(TheTitans.titansTab);
		if (maxHealth.length > 3)
		{
			modifierHelmet = new AttributeModifier("Helmet modifier", maxHealth[0], 0);
			modifierChestplate = new AttributeModifier("Chestplate modifier", maxHealth[1], 0);
			modifierLeggings = new AttributeModifier("Leggings modifier", maxHealth[2], 0);
			modifierBoots = new AttributeModifier("Boots modifier", maxHealth[3], 0);
		}
	}

	protected float getArmor(EntityLivingBase entity)
	{
		float armor = 0.0F;
		for (int i = 1; i < 5; i++)
		if (entity != null && entity.getEquipmentInSlot(i) != null && entity.getEquipmentInSlot(i).getItem() instanceof ItemTitanArmor)
		armor += ((ItemTitanArmor)entity.getEquipmentInSlot(i).getItem()).titanArmor;
		return armor;
	}

	public void attackEntity(ItemStack stack, EntityLivingBase entity, float damage)
	{
		if (!(entity == null) && !entity.worldObj.isRemote && damage > 0.0F)
		{
			float armor = getArmor(entity);
			float totalHealth = MathHelper.clamp_float((entity.getMaxHealth() * 10.0F) + (float) Maths.growth(175000.0F, 0.325F, armor), 0.0F, Float.MAX_VALUE);
			damage = Math.min(damage, cap());
			if (damage > 0.0F)
				damage *= 1.0F - MathHelper.clamp_float(1.0F * titanResistance, 0.0F, 1.0F);
			
			if (armor >= 0.0F);
				damage = Math.min(1.0F , damage / totalHealth);
			setHealthAll(entity, getHealth(stack) - (entity.getMaxHealth() * damage));
			entity.setHealth(getHealth(stack));
			if (getHealth(stack) > 0.0F)
			{
				entity.deathTime = -1;
				if (delay == 0)
				{
					delay = 80;
					NetworkHandler.sendClientPacket(new PacketPlayerAlive(entity.getCommandSenderName()));
				}	
			}
		}
	}

	protected ItemStack onNBT(ItemStack stack, EntityLivingBase entity)
	{
		NBTTagCompound nbt;
		if (stack != null)
		{
			if (stack.hasTagCompound())
			nbt = stack.getTagCompound();
			else
			nbt = new NBTTagCompound();
			if (!nbt.hasKey("health"))
			nbt.setFloat("health", entity.getMaxHealth());
			stack.setTagCompound(nbt);
		}

		return stack;
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
		if (p_77624_1_.getItem() instanceof ItemTitanArmor)
		{
			p_77624_3_.add(EnumChatFormatting.BLUE + "+" + (int)((ItemTitanArmor)p_77624_1_.getItem()).titanArmor + " Titan Armor");
			if (titanResistance > 0.0F)
			p_77624_3_.add(EnumChatFormatting.BLUE + "+" + (int)(((ItemTitanArmor)p_77624_1_.getItem()).titanResistance * 100.0F) + "% Titan Resistance");
		}
	}

	public Multimap<String, AttributeModifier> getItemAttributeModifiers()
	{
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		switch (armorType)
		{
			case 0:
			multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), modifierHelmet); break;
			case 1:
			multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), modifierChestplate); break;
			case 2:
			multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), modifierLeggings); break;
			case 3:
			multimap.put(SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName(), modifierBoots); break;
			default:;
		}

		return multimap;
	}

	public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
	{
		return false;
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return TheTitans.getTextures("textures/models", "armor/" + materialName + "_layer_" + (armorType == 2 ? "2" : "1") + ".png");
	}

	/**Runs per armor set to play sounds of your choosing*/
	public void onSoundTick(World world, EntityLivingBase entity, ItemStack itemStack)
	{
		soundTicks ++;
	}

	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (delay > 0)
		delay--;
		if (player != null && !world.isRemote)
		{
			if (itemStack != null)
			itemStack = onNBT(itemStack, player);
			if (delay == 0 && player.deathTime > 0)
			{
				delay = 80;
				NetworkHandler.sendClientPacket(new PacketPlayerAlive(player.getCommandSenderName()));
			}

			if (getHealth(itemStack) > player.getMaxHealth())
			setHealth(itemStack, player.getMaxHealth());
			if (getHealth(itemStack) > 0.0F && player.ticksExisted % 5 == 0 && player.getHealth() < player.getMaxHealth())
			player.setHealth(Math.min(player.getHealth() + (player.getMaxHealth() * 0.005F), player.getMaxHealth()));
			if (player.getHealth() < getHealth(itemStack))
			player.setHealth(getHealth(itemStack));
			else if (player.getHealth() > getHealth(itemStack))
			setHealth(itemStack, player.getHealth());
			if (getHealth(itemStack) <= 0.0F)
			{
				if (player.deathTime <= 0)
				player.deathTime++;
				player.setAbsorptionAmount(0);
			}
		}
	}

	protected void removeEffect(EntityLivingBase entity, Potion potion)
	{
		if (entity != null && (entity.getActivePotionEffect(potion) != null))
		{
			entity.removePotionEffect(potion.id);
			entity.playSound("random.fizz", 0.5F, 2.0F);
		}
	}

	protected void addEffect(EntityLivingBase entity, Potion potion, int time, int amplifier)
	{
		if (entity != null && (entity.getActivePotionEffect(potion) == null || entity.getActivePotionEffect(potion).getDuration() <= 1))
			entity.addPotionEffect(new PotionEffect(potion.id, time, amplifier, false));
	}
	
	protected void addEffect(EntityLivingBase entity, Potion potion, int amplifier)
	{
		if (entity != null && (entity.getActivePotionEffect(potion) == null || entity.getActivePotionEffect(potion).getDuration() <= 1))
			entity.addPotionEffect(new PotionEffect(potion.id, 1, amplifier, false));
	}

	protected void addEffectLong(EntityLivingBase entity, Potion potion, int amplifier)
	{
		if (entity != null && (entity.getActivePotionEffect(potion) == null || entity.getActivePotionEffect(potion).getDuration() <= 220))
			entity.addPotionEffect(new PotionEffect(potion.id, 800, amplifier, false));
	}

	public float getTitanArmor()
	{
		return titanArmor;
	}

	public float getTitanResistance()
	{
		return titanResistance;
	}

	public float getHealth(ItemStack stack)
	{
		return stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("health") ? stack.getTagCompound().getFloat("health") : 20.0F;
	}

	protected void setHealthAll(EntityLivingBase entity, float health)
	{
		if (entity != null)
		{
			for (int i = 1; i < 5 ; i++)
			if(entity.getEquipmentInSlot(i) != null && entity.getEquipmentInSlot(i).getItem() instanceof ItemTitanArmor)
			((ItemTitanArmor)entity.getEquipmentInSlot(i).getItem()).setHealth(entity.getEquipmentInSlot(i), health);
		}
	}

	protected void setHealth(ItemStack stack, float health)
	{
		if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("health"))
		stack.getTagCompound().setFloat("health", health);
	}

	protected float cap()
	{
		return Float.MAX_VALUE;
	}
}


