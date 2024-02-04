package net.minecraft.theTitans.blocks;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.api.IBurnable;
import net.minecraft.theTitans.api.IReloadable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
public class BlockNormal extends Block implements IReloadable, IBurnable
{
	private Item item;
	private int amount = 1;
	private int experience[] = new int[2];
	private int burnTime;
	private Random rand = new Random();
	public BlockNormal(String name, int harvestLevel, float hardness, float resistance)
	{
		super(Material.rock);
		setHarvestLevel("pickaxe", harvestLevel);
		setCreativeTab(TheTitans.titansTab);
		setBlockName(name);
		setBlockTextureName(TheTitans.getTextures(name));
		setStepSound(soundTypePiston);
		setHardness(hardness);
		setResistance(resistance);
	}

	public int quantityDropped(Random p_149745_1_)
	{
		return amount;
	}

	public void setAmountDrop(int amount)
	{
		this.amount = amount;
	}

	public int getExpDrop(IBlockAccess p_149690_1_, int p_149690_5_, int p_149690_7_)
	{
		return experience[0] + experience[1] == 0 ? 0 : MathHelper.getRandomIntegerInRange(rand, experience[0], experience[1]);
	}

	public void setExpDrop(int experience)
	{
		setExpDrop(0, experience);
	}

	public void setExpDrop(int expMin, int expMax)
	{
		experience[0] = expMin;
		experience[1] = expMax;
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return item == null ? super.getItemDropped(p_149650_1_, p_149650_2_, p_149650_3_) : item;
	}

	public void setItemDropped(Item item)
	{
		this.item = item;  
	}

	public void refreshTextures()
	{
		setBlockTextureName(TheTitans.getTextures(getUnlocalizedName().substring(5)));
	}

	@Override
	public void setBurnTime(int burnTime)
	{
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTime()
	{
		return burnTime;
	}
}


