package net.minecraft.theTitans.blocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.other.EntityVoidGolem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
public class BlockVoidBlock extends BlockNormalCompressed
{
	public static final Block.SoundType soundTypeVoid = new Block.SoundType("stone", 10.0F, 0.5F);
	public BlockVoidBlock(String name)
	{
		super(MapColor.blackColor, name);
		setHarvestLevel("pickaxe", 1000);
		setHardness(2400.0F);
		setResistance(18000000.0F);
		setStepSound(soundTypeVoid);
	}

	/**
	* Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	* cleared to be reused)
	*/
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
	{
		float f = 0.01F;
		return AxisAlignedBB.getBoundingBox((double)((float)p_149668_2_ + f), (double)p_149668_3_, (double)((float)p_149668_4_ + f), (double)((float)(p_149668_2_ + 1) - f), (double)((float)(p_149668_3_ + 1) - f), (double)((float)(p_149668_4_ + 1) - f));
	}

	/**
	* Returns the bounding box of the wired rectangular prism to render.
	*/
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
	{
		float f = 0.01F;
		return AxisAlignedBB.getBoundingBox((double)((float)p_149633_2_ + f), (double)p_149633_3_, (double)((float)p_149633_4_ + f), (double)((float)(p_149633_2_ + 1) - f), (double)(p_149633_3_ + 1), (double)((float)(p_149633_4_ + 1) - f));
	}

	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
	{
		if (!(p_149670_5_ instanceof EntityVoidGolem))
		{
			p_149670_5_.attackEntityFrom(DamageSource.outOfWorld, 36.0F);
			if (p_149670_5_ != null && !p_149670_5_.worldObj.isRemote)
			p_149670_5_.worldObj.newExplosion(null, p_149670_5_.posX, p_149670_5_.posY, p_149670_5_.posZ, 3.0F, false, p_149670_5_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
			p_149670_5_.motionX *= 0.2D;
			p_149670_5_.motionZ *= 0.2D;
			if (p_149670_5_ instanceof EntityLivingBase)
			p_149670_5_.motionY += 1F;
			if (((p_149670_5_ instanceof EntityLivingBase)) && (((EntityLivingBase)p_149670_5_).getRNG().nextInt(10) == 0))
			((EntityLivingBase)p_149670_5_).setFire(40);
			if (((p_149670_5_ instanceof EntityLivingBase)) && (((EntityLivingBase)p_149670_5_).getRNG().nextInt(60) == 0))
			((EntityLivingBase)p_149670_5_).addPotionEffect(new PotionEffect(Potion.wither.id, 160, 1));
		}

		if ((p_149670_5_ instanceof EntityPlayer))((EntityPlayer)p_149670_5_).addExhaustion(0.2F);
	}

	public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
	{
		p_149636_2_.addStat(net.minecraft.stats.StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		p_149636_2_.addExhaustion(100.0F);
		if ((canSilkHarvest(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_)) && (EnchantmentHelper.getSilkTouchModifier(p_149636_2_)))
		{
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			ItemStack itemstack = createStackedBlock(p_149636_6_);
			if (itemstack != null)
			{
				items.add(itemstack);
			}

			ForgeEventFactory.fireBlockHarvesting(items, p_149636_1_, this, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, 0, 1.0F, true, p_149636_2_);
			for (ItemStack is : items)
			{
				dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, is);
			}
		}

		else
		{
			this.harvesters.set(p_149636_2_);
			dropBlockAsItem(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, 0);
			this.harvesters.set(null);
			p_149636_2_.worldObj.newExplosion(null, p_149636_2_.posX, p_149636_2_.posY, p_149636_2_.posZ, 1.0F, false, p_149636_2_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
			p_149636_2_.worldObj.newExplosion(null, p_149636_2_.posX, p_149636_2_.posY, p_149636_2_.posZ, 1.0F, false, p_149636_2_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
			p_149636_2_.worldObj.newExplosion(null, p_149636_2_.posX, p_149636_2_.posY, p_149636_2_.posZ, 1.0F, false, p_149636_2_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
			p_149636_2_.worldObj.newExplosion(null, p_149636_2_.posX, p_149636_2_.posY, p_149636_2_.posZ, 1.0F, false, p_149636_2_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
			p_149636_2_.worldObj.newExplosion(null, p_149636_2_.posX, p_149636_2_.posY, p_149636_2_.posZ, 1.0F, false, p_149636_2_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
			p_149636_2_.worldObj.newExplosion(null, p_149636_2_.posX, p_149636_2_.posY, p_149636_2_.posZ, 1.0F, false, p_149636_2_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
			p_149636_2_.worldObj.newExplosion(null, p_149636_2_.posX, p_149636_2_.posY, p_149636_2_.posZ, 1.0F, false, p_149636_2_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
			p_149636_2_.worldObj.newExplosion(null, p_149636_2_.posX, p_149636_2_.posY, p_149636_2_.posZ, 1.0F, false, p_149636_2_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
			p_149636_2_.worldObj.newExplosion(null, p_149636_2_.posX, p_149636_2_.posY, p_149636_2_.posZ, 1.0F, false, p_149636_2_.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops"));
		}
	}

	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity)
	{
		return false;
	}

	public int tickRate(World p_149738_1_)
	{
		return 30;
	}

	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
	{
		randomDisplayTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
	{
		func_150186_m(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_);
		p_149734_1_.playSound(p_149734_2_ + 0.5F, p_149734_3_ + 0.5F, p_149734_4_ + 0.5F, "thetitans:harcadiumBlockHum", 2.0F, 0.75F, false);
	}

	private void func_150186_m(World p_150186_1_, int p_150186_2_, int p_150186_3_, int p_150186_4_)
	{
		Random random = p_150186_1_.rand;
		double d0 = 0.0625D;
		for (int l = 0; l < 6; l++)
		{
			double d1 = p_150186_2_ + random.nextFloat();
			double d2 = p_150186_3_ + random.nextFloat();
			double d3 = p_150186_4_ + random.nextFloat();
			if ((l == 0) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube()))
			{
				d2 = p_150186_3_ + 1 + d0;
			}

			if ((l == 1) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube()))
			{
				d2 = p_150186_3_ + 0 - d0;
			}

			if ((l == 2) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube()))
			{
				d3 = p_150186_4_ + 1 + d0;
			}

			if ((l == 3) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube()))
			{
				d3 = p_150186_4_ + 0 - d0;
			}

			if ((l == 4) && (!p_150186_1_.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube()))
			{
				d1 = p_150186_2_ + 1 + d0;
			}

			if ((l == 5) && (!p_150186_1_.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube()))
			{
				d1 = p_150186_2_ + 0 - d0;
			}

			if ((d1 < p_150186_2_) || (d1 > p_150186_2_ + 1) || (d2 < 0.0D) || (d2 > p_150186_3_ + 1) || (d3 < p_150186_4_) || (d3 > p_150186_4_ + 1))
			{
				p_150186_1_.spawnParticle("smoke", d1, d2, d3, 0.0D, 0.0D, 0.0D);
				p_150186_1_.spawnParticle("largesmoke", d1, d2, d3, 0.0D, 0.0D, 0.0D);
				p_150186_1_.spawnParticle("depthsuspend", d1, d2, d3, 0.0D, 0.0D, 0.0D);
				p_150186_1_.spawnParticle("depthsuspend", d1, d2, d3, 0.0D, 0.0D, 0.0D);
				p_150186_1_.spawnParticle("depthsuspend", d1, d2, d3, 0.0D, 0.0D, 0.0D);
				p_150186_1_.spawnParticle("depthsuspend", d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}


