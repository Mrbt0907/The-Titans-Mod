package net.minecraft.theTitans.blocks;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanRenders;
import net.minecraft.theTitans.api.IReloadable;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
public class BlockNormalOre extends BlockOre implements IReloadable
{
	protected IIcon ore;
	protected String materialName;
	protected String oreName;
	public Block baseBlock;
	private Item item;
	private int amount = 1;
	private int experience[] = new int[2];
	public boolean renderItem;
	private Random rand = new Random();
	public BlockNormalOre(String name, int harvestLevel, float hardness, float resistance)
	{
		this(null, name, harvestLevel, hardness, resistance);
	}

	public BlockNormalOre(Block base, String name, int harvestLevel, float hardness, float resistance)
	{
		super();
		String textureName = "stone";
		String unlocalizedName = name + "_ore";
		if (base != null && !base.equals(Blocks.stone))
		{
			baseBlock = base;
			textureName = (String)TheTitans.reflect.get(Block.class, baseBlock, "textureName", "field_149768_d");
			unlocalizedName += "_" + baseBlock.getUnlocalizedName().substring(5);
			setHarvestLevel("pickaxe", Math.max(harvestLevel, baseBlock.getHarvestLevel(0)));
			setHardness(Math.max(hardness, (float)TheTitans.reflect.get(Block.class, baseBlock, "blockHardness", "field_149782_v")));
			setResistance(Math.max(resistance, (float)TheTitans.reflect.get(Block.class, baseBlock, "blockResistance", "field_149781_w")));
		}

		else
		{
			baseBlock = Blocks.stone;
			setHarvestLevel("pickaxe", Math.max(harvestLevel, baseBlock.getHarvestLevel(0)));
			setHardness(Math.max(hardness, (float)TheTitans.reflect.get(Block.class, baseBlock, "blockHardness", "field_149782_v")));
			setResistance(Math.max(resistance, (float)TheTitans.reflect.get(Block.class, baseBlock, "blockResistance", "field_149781_w")));
		}

		materialName = name;
		setBlockTextureName(textureName, TheTitans.getTextures(name));
		setCreativeTab(TheTitans.titansTab);
		setBlockName(unlocalizedName);
		setStepSound(soundTypePiston);
	}

	@Override
	public Block setBlockTextureName(String base)
	{
		setBlockTextureName(base, null);
		return this;
	}

	public Block setBlockTextureName(String base, String ore)
	{
		textureName = base;
		oreName = ore;
		return this;
	}

	public IIcon getTexture(int index)
	{
		switch (index)
		{
			case 1: return ore;
			default: return blockIcon;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return renderItem ? 1 : 0;
	}

	@Override
	public boolean renderAsNormalBlock() 
	{

		return false;
	}

	@Override
	public int getRenderType()
	{
		return TitanRenders.RENDERBLOCKOREID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		blockIcon = p_149651_1_.registerIcon(textureName);
		ore = p_149651_1_.registerIcon(oreName);
	}

	public int quantityDropped(Random p_149745_1_)
	{
		return amount;
	}

	public BlockNormalOre setAmountDrop(int amount)
	{
		this.amount = amount;
		return this;
	}

	public int getExpDrop(IBlockAccess p_149690_1_, int p_149690_5_, int p_149690_7_)
	{
		return experience[0] + experience[1] == 0 ? 0 : MathHelper.getRandomIntegerInRange(rand, experience[0], experience[1]);
	}

	public BlockNormalOre setExpDrop(int experience)
	{
		setExpDrop(0, experience);
		return this;
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

	public BlockNormalOre setItemDropped(Item item)
	{
		this.item = item;  
		return this;
	}

	public void refreshTextures()
	{
		TheTitans.debug(TheTitans.getTextures(getUnlocalizedName().substring(5)));
		setBlockTextureName(TheTitans.getTextures(getUnlocalizedName().substring(5)), TheTitans.getTextures(materialName));
	}
}


