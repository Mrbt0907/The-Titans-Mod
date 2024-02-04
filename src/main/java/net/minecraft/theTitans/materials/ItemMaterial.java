package net.minecraft.theTitans.materials;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.theTitans.TheTitans;
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
	public ItemMaterial(String name)
	{
		this.name = name;
	}

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

	public ItemMaterial(String name, int durability, int helmetArmor, int chestplateArmor, int leggingsArmor, int bootsArmor, int enchantability)
	{
		this.name = name;
		this.durability = durability;
		armor[0] = helmetArmor;
		armor[1] = chestplateArmor;
		armor[2] = leggingsArmor;
		armor[3] = bootsArmor;
		armorMaterial = EnumHelper.addArmorMaterial(name, durability, armor, enchantability);
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
				case 1: TheTitans.reflect.set(ItemSword.class, sword, value, "field_150934_a"); break;
				default: sword.setMaxDamage((int)value);
			}
		}

		else if (items[i] instanceof ItemTool)
		{
			ItemTool tool = ((ItemTool)items[i]); 
			switch (key)
			{
				case 1: TheTitans.reflect.set(ItemTool.class, tool, value, "damageVsEntity", "field_77865_bY"); break;
				case 2: tool.setHarvestLevel((String)TheTitans.reflect.get(ItemTool.class, tool, "toolClass"), (int)value); break;
				case 3: TheTitans.reflect.set(ItemTool.class, tool, value, "efficiencyOnProperMaterial", "field_77864_a"); break;
				default: tool.setMaxDamage((int)value);
			}
		}

		else if (items[i] instanceof ItemArmor)
		{
			ItemArmor armorPiece = ((ItemArmor)items[i]); 
			armorPiece.setMaxDamage((int)value);
			//TheTitans.reflect.set(ItemArmor.class, armorPiece, armor[armorPiece.armorType], "damageReduceAmount", "field_77879_b");
		}
	}

	/**Apply new armor values to the armor.*/
	public static void apply(int[] value, Item... items)
	{
		for (int i = 0; i < items.length; i++)
		if (items[i] instanceof ItemArmor)
		{
			ItemArmor armorPiece = ((ItemArmor)items[i]); 
			TheTitans.reflect.set(ItemArmor.class, armorPiece, value[armorPiece.armorType], "damageReduceAmount", "field_77879_b");
		}
	}

	public void apply(Item... items)
	{
		for (int i = 0; i < items.length; i++)
		if (toolMaterial != null && items[i] instanceof ItemSword)
		{
			ItemSword sword = ((ItemSword)items[i]); 
			sword.setMaxDamage(durability);
			TheTitans.reflect.set(ItemSword.class, sword, damage, "field_150934_a");
		}

		else if (toolMaterial != null && items[i] instanceof ItemTool)
		{
			ItemTool tool = ((ItemTool)items[i]); 
			tool.setMaxDamage(durability);
			tool.setHarvestLevel((String)TheTitans.reflect.get(ItemTool.class, tool, "toolClass"), harvestLevel);
			TheTitans.reflect.set(ItemTool.class, tool, efficiency, "efficiencyOnProperMaterial", "field_77864_a");
			TheTitans.reflect.set(ItemTool.class, tool, damage, "damageVsEntity", "field_77865_bY");
		}

		else if (armorMaterial != null && items[i] instanceof ItemArmor)
		{
			ItemArmor armorPiece = ((ItemArmor)items[i]); 
			armorPiece.setMaxDamage(durability);
			TheTitans.reflect.set(ItemArmor.class, armorPiece, armor[armorPiece.armorType], "damageReduceAmount", "field_77879_b");
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
		efficiency = toolMaterial.getEfficiencyOnProperMaterial();
		else if (damage == 0.0F)
		damage = toolMaterial.getDamageVsEntity();
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
		for (int i = 0; i < 3; i++)
		if (armor[i] == 0)
		armor[i] = armorMaterial.getDamageReductionAmount(i);
		if (durability == 0)
		durability = armorMaterial.getDurability(1);
	}

	public void setArmorMaterial(String name, int durability, int helmetArmor, int chestplateArmor, int leggingsArmor, int bootsArmor, int enchantability)
	{
		this.name = name;
		this.durability = durability;
		armor[0] = helmetArmor;
		armor[1] = chestplateArmor;
		armor[2] = leggingsArmor;
		armor[3] = bootsArmor;
		armorMaterial = EnumHelper.addArmorMaterial(name, durability, armor, enchantability);
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
		return armor[MathHelper.clamp_int(slot, 0, 3)];
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


