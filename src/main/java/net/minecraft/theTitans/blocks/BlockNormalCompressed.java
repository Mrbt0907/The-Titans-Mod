package net.minecraft.theTitans.blocks;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.api.IBurnable;
import net.minecraft.theTitans.api.IReloadable;
import net.minecraft.world.IBlockAccess;
public class BlockNormalCompressed extends BlockCompressed implements IReloadable, IBurnable
{
	private int burnTime;
	public BlockNormalCompressed(MapColor color, String name)
	{
		super(color);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundTypePiston);
		setCreativeTab(TheTitans.titansTab);
		setBlockName(name);
		setBlockTextureName(TheTitans.getTextures(name));
	}

	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
	{
		return true;
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


