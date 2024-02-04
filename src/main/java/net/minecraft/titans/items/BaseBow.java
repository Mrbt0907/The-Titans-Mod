package net.minecraft.titans.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class BaseBow extends ItemBow 
{
	private int arrows;
	private boolean use_more_arrows;
	private float vel;
	
	public BaseBow (int durability, float velocity, int arrows, boolean use_more_arrows) 
	{
		super();
		this.arrows = arrows <= 0 ? 1 : arrows;
		this.use_more_arrows = use_more_arrows;
		vel = velocity + 1.0f;
		this.setMaxDamage(durability);
	}
	
	public BaseBow (int durability, float velocity, int arrows) 
	{
		super();
		this.arrows = arrows <= 0 ? 1 : arrows;
		this.use_more_arrows = false;
		vel = velocity + 1.0f;
		this.setMaxDamage(durability);
	}
	
	public BaseBow (int durability, float velocity) 
	{
		super();
		arrows = 1;
		this.use_more_arrows = false;
		vel = velocity;
		this.setMaxDamage(durability);
	}
	
	public BaseBow (int durability) 
	{
		super();
		arrows = 1;
		this.use_more_arrows = false;
		vel = 1.0f;
		this.setMaxDamage(durability);
	}
	
	public BaseBow () 
	{
		super();
		arrows = 1;
		this.use_more_arrows = false;
		vel = 1.0f;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = this.findAmmo(entityplayer);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag)
            {
                if (itemstack.isEmpty())
                {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);

                if ((double)f >= 0.1D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (itemstack.getItem() instanceof ItemArrow && ((ItemArrow) itemstack.getItem()).isInfinite(itemstack, stack, entityplayer));

                    for (int ii=1; ii <= arrows; ii++)
                    {
	                    if (!worldIn.isRemote)
	                    {
	                        ItemArrow itemarrow = (ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW);
	                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
	                        entityarrow = this.customizeArrow(entityarrow);
	                        entityarrow.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * (3.0F + vel), 1.0F * ((ii == 1) ? 1.0f : arrows));
	
	                        if (f == 1.0F)
	                        {
	                            entityarrow.setIsCritical(true);
	                        }
	
	                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
	
	                        if (j > 0)
	                        {
	                            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
	                        }
	
	                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
	
	                        if (k > 0)
	                        {
	                            entityarrow.setKnockbackStrength(k);
	                        }
	
	                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
	                        {
	                            entityarrow.setFire(100);
	                        }
	
	                        stack.damageItem(1, entityplayer);
	
	                        if (flag1 || entityplayer.capabilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW) || ii > 1)
	                        {
	                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
	                        }
	
	                        worldIn.spawnEntity(entityarrow);
	                    }
                    }

                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !entityplayer.capabilities.isCreativeMode)
                    {
                    	int ammos = 1;
                    	if (use_more_arrows) ammos = arrows;
                    	for (int ii=1; ii <= ammos; ii++)
                    	{
	                        itemstack.shrink(1);
	
	                        if (itemstack.isEmpty())
	                        {
	                            entityplayer.inventory.deleteStack(itemstack);
	                            break;
	                        }
                    	}
                    }

                    entityplayer.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }
}