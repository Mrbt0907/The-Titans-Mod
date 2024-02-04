package net.minecraft.theTitans.items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.DamageSource;
public class ItemNormalSword
extends ItemSword
{
	protected float damage;
	protected float multiplier;
	public ItemNormalSword(String unlocalizedName, Item.ToolMaterial material)
	{
		super(material);
		multiplier = 0.0F;
		damage = material.getDamageVsEntity() + 4.0F;
		setTextureName(TheTitans.getTextures(unlocalizedName + "_sword"));
		setUnlocalizedName(unlocalizedName + "_sword");
		setCreativeTab(TheTitans.titansTab);
	}

	public boolean hitEntity(ItemStack item, EntityLivingBase victim, EntityLivingBase attacker)
	{
		if (isSpecialty(victim))
		victim.attackEntityFrom(DamageSource.causeMobDamage(attacker), damage * multiplier);
		return true;
	}

	public ItemNormalSword setDamageMultiplier(float multiplier)
	{
		this.multiplier = multiplier;
		return this;
	}

	public float getDamageMultiplier()
	{
		return multiplier;
	}

	public boolean isSpecialty(Entity entity)
	{
		return false;
	}
}


