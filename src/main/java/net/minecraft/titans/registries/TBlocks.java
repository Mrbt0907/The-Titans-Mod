package net.minecraft.titans.registries;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.blocks.BaseBlock;
import net.minecraft.titans.blocks.BaseOre;
import net.minecraft.titans.blocks.BaseResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class TBlocks 
{
	private static RegistryEvent.Register<Block> registry;
	
	public static final Block ADMINIUM = new BaseBlock(90000.0F, 54000000.0F, 12, 21);
	public static final Block CORMINIUM = new BaseBlock(-1.0F, Float.MAX_VALUE * 0.4F, Integer.MAX_VALUE, 0);
	public static final Block MALGRUM_CROP = new BaseBlock();
	public static final Block PLEASANT_BLADE_CROP = new BaseBlock();
	private static final float[][] BASE_BLOCK_RESISTS = {{3.0F, 10.0F}, {3.0F, 15.0F}, {0.4F, 0.0F}, {50.0F, 2000.0F}, {10000.0F, 6000000.0F}, {90000.0F, 54000000.0F}};
	private static final Block[] BASE_BLOCKS = {Blocks.STONE, Blocks.END_STONE, Blocks.NETHERRACK, Blocks.OBSIDIAN, Blocks.BEDROCK, ADMINIUM};
	public static Block[] demontium_ore = new Block[0];
	public static Block[] harcadium_ore = new Block[0];
	public static Block[] hellsite_ore = new Block[0];
	public static Block[] void_ore = new Block[0];
	public static Block[] adamantium_ore = new Block[0];
	public static Block[] titanus_ore = new Block[0];
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> event)
    {
		TheTitans.debug("Registering blocks...");
        registry = event;
        Blocks.BEDROCK.setHarvestLevel("pickaxe", 21);
        Blocks.BEDROCK.setHardness(10000.0F);
        Blocks.BEDROCK.setCreativeTab(TheTitans.TAB_BLOCKS);
        add("adminium", ADMINIUM);
        add("corminium", CORMINIUM);
    	demontium_ore = addOre("demontium", 200.0F, 1490.0F, 17);
    	harcadium_ore = addOre("harcadium", 400.0F, 5990.0F, 18);
    	hellsite_ore = addOre("hellsite", 400.0F, 5990.0F, 19);
    	titanus_ore = addOre("titanus", 1000.0F, 23990.0F, 20);
    	void_ore = addOre("void", 1000.0F, 23990.0F, 21);
    	adamantium_ore = addOre("adamantium", 9000.0F, 5000000.0F, Integer.MAX_VALUE - 24);
        
        registry = null;
        TheTitans.debug("Finished registering blocks");
    }
	
	private static Block[] addOre(String id, float hardness, float blast_resistance, int harvest_level, Block... blocksToRemove)
	{
		return addOre(id, hardness, blast_resistance, harvest_level, false, blocksToRemove);
	}
	
	private static Block[] addOre(String id, float hardness, float blast_resistance, int harvest_level, boolean removeBlock, Block... blocksToRemove)
	{
		int size = BASE_BLOCKS.length;
		
		if (size == 0)
		{
			TheTitans.error("Failed to register ore block as base_blocks returned null. Skippng...");
			return null;
		}
		else if (registry == null)
		{
			TheTitans.error("Failed to register ore block as the registry was not initialized yet. Skippng...");
			return null;
		}
		
		List<Block> final_blocks = new ArrayList<Block>();
		
		Block a;
		Block b;
		Block[] c = new Block[size];
		float[] d;
		boolean attempt;
		
		if (removeBlock)
			a = null;
		else
		{
			a = new BaseResource();
			add(id + "_block", a);
		}
		final_blocks.add(a);
		
		
		for (int i = 0; i < size; i++)
		{
			b = BASE_BLOCKS[i];
			d = BASE_BLOCK_RESISTS[i];
			attempt = true;
			
			for (Block rem : blocksToRemove)
				if (rem.equals(b))
				{
					attempt = false;
					break;
				}
				
			if (attempt)
			{
				a = new BaseOre(d[0] + hardness, d[1] + blast_resistance, harvest_level, 0);
				add(id + "_ore_" + b.getRegistryName().getResourcePath(), a);
				final_blocks.add(a);
			}
		}
		return final_blocks.toArray(c);
	}
	
	@SuppressWarnings("unused")
	private static void addTileEntity(String registry_name, Class<? extends TileEntity> tile)
    {
        if (tile != null)
            GameRegistry.registerTileEntity(tile, new ResourceLocation(TheTitans.MODID, registry_name));
    }
    
    private static void add(String registry_name, Block block)
    {
        add(registry_name, null, block, true);
    }
    
    @SuppressWarnings("unused")
	private static void add(String registry_name, Block block, boolean creative_tab)
    {
        add(registry_name, null, block, creative_tab);
    }
    
    @SuppressWarnings("unused")
	private static void add(String registry_name, String ore_dict_name, Block block)
    {
        add(registry_name, ore_dict_name, block, true);
    }
    
    private static void add(String registry_name, String ore_dict_name, Block block, boolean creative_tab)
    {
        if (registry != null)
        {
            block.setRegistryName(registry_name);
            block.setUnlocalizedName(registry_name);
            
            if (ore_dict_name != null)
                OreDictionary.registerOre(ore_dict_name, block);
            
            if (creative_tab)
            	block.setCreativeTab(TheTitans.TAB_BLOCKS);
            
            registry.getRegistry().register(block);            
            
            TItems.add(block);
            TheTitans.debug("Registered block " + block.getRegistryName().getResourceDomain() +  ":" + block.getRegistryName().getResourcePath());
            return;
        }
        
        TheTitans.error("Registry event returned null");
    }
}
