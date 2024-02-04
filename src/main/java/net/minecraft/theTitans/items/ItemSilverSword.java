package net.minecraft.theTitans.items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
public class ItemSilverSword extends ItemNormalSword 
{
	public ItemSilverSword(String unlocalizedName, ToolMaterial material)
	{
		super(unlocalizedName, material);
	}

	public boolean isSpecialty(Entity entity)
	{
		return entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isEntityUndead();
	}
}


