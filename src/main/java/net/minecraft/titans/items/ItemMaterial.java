package net.minecraft.titans.items;
import net.endermanofdoom.mac.util.ReflectionUtil;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.util.EnumHelper;
public class ItemMaterial
{
	private ToolMaterial toolMaterial;
	private ArmorMaterial armorMaterial;
	private String name;
	/** The level of material this tool can harvest*/
	private int harvestLevel;
	/** The number of uses this material allows.*/
	private int durability;
	/** The strength of this tool material against blocks which it is effective against.*/
	private float efficiency;
	/** Damage versus entities.*/
	private float damage;
	private int armor[] = new int[4];
	/** Defines the natural enchantability factor of the material.*/
	private int enchantability;
	
	/**Creates a blank item material for use with tools, weapons, or armor*/
	public ItemMaterial(String name)
	{
		this.name = name;
	}

	/**Creates a new item material for tools and weapons*/
	public ItemMaterial(String name, int harvestLevel, int durability, float efficiency, float damage, int enchantability)
	{
		this.name = name;
		this.harvestLevel = harvestLevel;
		this.durability = durability;
		this.efficiency = efficiency;
		this.damage = damage;
		this.enchantability = enchantability;
		toolMaterial = EnumHelper.addToolMaterial(name, harvestLevel, durability, efficiency, damage, enchantability);
	}
	
	/**Creates a new item material for armor sets*/
	public ItemMaterial(String name, int durability, int helmetArmor, int chestplateArmor, int leggingsArmor, int bootsArmor, float toughness, int enchantability, SoundEvent onEquipSound)
	{
		this.name = name;
		this.durability = durability;
		armor[0] = helmetArmor;
		armor[1] = chestplateArmor;
		armor[2] = leggingsArmor;
		armor[3] = bootsArmor;
		armorMaterial = EnumHelper.addArmorMaterial(name, name, durability, armor, enchantability, onEquipSound, toughness);
	}

	/**Apply new values to a specific key.
	*@param key - [ItemSword: 0=durability, 1=damage]  [ItemTool: 0=durability, 1=damage, 2=harvestLevel, 3=efficiency]  [ItemArmor: any=durability]*/
	public static void apply(int key, float value, Item... items)
	{
		for (int i = 0; i < items.length; i++)
		if (items[i] instanceof ItemSword)
		{
			ItemSword sword = ((ItemSword)items[i]); 
			switch (key)
			{
				case 1: sword.attackDamage = value; break;
				default: sword.setMaxDamage((int)value);
			}
		}

		else if (items[i] instanceof ItemTool)
		{
			ItemTool tool = ((ItemTool)items[i]); 
			switch (key)
			{
				case 1: ReflectionUtil.set(ItemTool.class, tool, "attackDamage", "field_77865_bY", value); break;
				case 2: tool.setHarvestLevel((String)ReflectionUtil.get(ItemTool.class, tool, "toolClass", "toolClass"), (int)value); break;
				case 3: ReflectionUtil.set(ItemTool.class, tool, "efficiency", "field_77864_a", value); break;
				default: tool.setMaxDamage((int)value);
			}
		}

		else if (items[i] instanceof ItemArmor)
		{
			ItemArmor armorPiece = ((ItemArmor)items[i]); 
			armorPiece.setMaxDamage((int)value);
		}
	}

	/**Apply new armor values to the armor.*/
	public static void apply(int[] value, Item... items)
	{
		for (int i = 0; i < items.length; i++)
		if (items[i] instanceof ItemArmor)
		{
			ItemArmor armorPiece = ((ItemArmor)items[i]); 
			ReflectionUtil.set(ItemArmor.class, armorPiece, "damageReduceAmount", "field_77879_b", value[armorPiece.armorType.getIndex()]);
		}
	}

	public void apply(Item... items)
	{
		for (int i = 0; i < items.length; i++)
		if (toolMaterial != null && items[i] instanceof ItemSword)
		{
			ItemSword sword = ((ItemSword)items[i]); 
			sword.setMaxDamage(durability);
			ReflectionUtil.set(ItemSword.class, sword, "attackDamage", "field_150934_a", damage);
		}

		else if (toolMaterial != null && items[i] instanceof ItemTool)
		{
			ItemTool tool = ((ItemTool)items[i]); 
			tool.setMaxDamage(durability);
			tool.setHarvestLevel((String)ReflectionUtil.get(ItemTool.class, tool, "toolClass", "toolClass"), harvestLevel);
			ReflectionUtil.set(ItemTool.class, tool, "efficiency", "field_77864_a", efficiency);
			ReflectionUtil.set(ItemTool.class, tool, "attackDamage", "field_77865_bY", damage);
		}

		else if (armorMaterial != null && items[i] instanceof ItemArmor)
		{
			ItemArmor armorPiece = ((ItemArmor)items[i]); 
			armorPiece.setMaxDamage(durability);
			ReflectionUtil.set(ItemArmor.class, armorPiece, "damageReduceAmount", "field_77879_b", armor[armorPiece.armorType.getIndex()]);
		}
	}

	public ToolMaterial getToolMaterial()
	{
		return toolMaterial;
	}

	public void setToolMaterial(ToolMaterial toolMaterial)
	{
		this.toolMaterial = toolMaterial;
		if (harvestLevel == 0)
		harvestLevel = toolMaterial.getHarvestLevel();
		else if (efficiency == 0.0F)
			efficiency = toolMaterial.getEfficiency();
		else if (damage == 0.0F)
			damage = toolMaterial.getAttackDamage();
		else if (durability == 0)
			durability = toolMaterial.getMaxUses();
		else if (enchantability == 0)
			enchantability = toolMaterial.getEnchantability();
	}

	public void setToolMaterial(String name, int harvestLevel, int durability, float efficiency, float damage, int enchantability)
	{
		this.name = name;
		this.harvestLevel = harvestLevel;
		this.durability = durability;
		this.efficiency = efficiency;
		this.damage = damage;
		this.enchantability = enchantability;
		toolMaterial = EnumHelper.addToolMaterial(name, harvestLevel, durability, efficiency, damage, enchantability);
	}

	public ArmorMaterial getArmorMaterial()
	{
		return armorMaterial;
	}

	public void setArmorMaterial(ArmorMaterial armorMaterial)
	{
		this.armorMaterial = armorMaterial;
		EntityEquipmentSlot slot = null;
		for (int i = 0; i < 3; i++)
		{
			switch(i)
			{
				case 0:
					slot = EntityEquipmentSlot.HEAD;
					break;
				case 1:
					slot = EntityEquipmentSlot.CHEST;
					break;
				case 2:
					slot = EntityEquipmentSlot.LEGS;
					break;
				case 3:
					slot = EntityEquipmentSlot.FEET;
					break;
			}
			if (armor[i] == 0)
				armor[i] = armorMaterial.getDamageReductionAmount(slot);
			if (durability == 0)
				durability = armorMaterial.getDurability(slot);
		}
	}

	public void setArmorMaterial(String name, int durability, int helmetArmor, int chestplateArmor, int leggingsArmor, int bootsArmor, float toughness, int enchantability, SoundEvent onEquipSound)
	{
		this.name = name;
		this.durability = durability;
		armor[0] = helmetArmor;
		armor[1] = chestplateArmor;
		armor[2] = leggingsArmor;
		armor[3] = bootsArmor;
		armorMaterial = EnumHelper.addArmorMaterial(name, name, durability, armor, enchantability, onEquipSound, toughness);
	}

	public int getDurability()
	{
		return durability;
	}

	public void setDurability(int durability)
	{
		this.durability = durability;
	}

	public float getEfficiency()
	{
		return efficiency;
	}

	public void setEfficiency(int efficiency)
	{
		this.efficiency = efficiency;
	}

	public int getHarvestLevel()
	{
		return harvestLevel;
	}

	public void setHarvestLevel(int level)
	{
		harvestLevel = level;
	}

	public float getDamage()
	{
		return damage;
	}

	public void setDamage(int damage)
	{
		this.damage = damage;
	}

	public int getArmor(int slot)
	{
		return armor[MathHelper.clamp(slot, 0, 3)];
	}

	public void setArmor(int... armorValues)
	{
		for (int i = 0; i < 4 && i < armorValues.length; i++)
		{
			armor[i] = armorValues[i];
		}
	}

	public String getName()
	{
		return name;
	}

	public int getEnchantability()
	{
		return enchantability;
	}
}


