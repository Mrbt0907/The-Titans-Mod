package net.minecraft.titans.registries;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
	
	public static final Block NETHRUM = new BaseBlock(Material.GROUND, 2F, 1F, 0, 2);
	public static final Block ADMINIUM = new BaseBlock(90000.0F, 54000000.0F, 12, 21);
	public static final Block CORMINIUM = new BaseBlock(-1.0F, Float.MAX_VALUE * 0.4F, Integer.MAX_VALUE, 0);
	public static final Block MAGIC_PUMPKIN = new BaseBlock();
	public static final Block MALGRUM_CROP = new BaseBlock();
	public static final Block PLEASANT_BLADE_CROP = new BaseBlock();
	private static final float[][] BASE_BLOCK_RESISTS = {{3.0F, 10.0F}, {3.0F, 15.0F}, {0.4F, 0.0F}, {50.0F, 2000.0F}, {10000.0F, 6000000.0F}, {2.0F, 1.0F}, {90000.0F, 54000000.0F}};
	private static final Block[] BASE_BLOCKS = {Blocks.STONE, Blocks.END_STONE, Blocks.NETHERRACK, Blocks.OBSIDIAN, Blocks.BEDROCK, NETHRUM, ADMINIUM};
	public static final Block BRASS_BLOCK = new BaseResource();
	public static final Block BRONZE_BLOCK = new BaseResource();
	public static final Block STEEL_BLOCK = new BaseResource();
	public static final Block CUT_BRASS_BLOCK = new BaseResource();
	public static final Block CUT_BRONZE_BLOCK = new BaseResource();
	public static final Block CUT_STEEL_BLOCK = new BaseResource();
	public static Block[] jadeite_ore = new Block[0];
	public static Block[] molten_fuel_ore = new Block[0];
	public static Block[] pig_iron_ore = new Block[0];
	public static Block[] ruby_ore = new Block[0];
	public static Block[] sapphire_ore = new Block[0];
	public static Block[] copper_ore = new Block[0];
	public static Block[] tin_ore = new Block[0];
	public static Block[] chromium_ore = new Block[0];
	public static Block[] nickel_ore = new Block[0];
	public static Block[] lead_ore = new Block[0];
	public static Block[] silver_ore = new Block[0];
	public static Block[] platinum_ore = new Block[0];
	public static Block[] titanium_ore = new Block[0];
	public static Block[] coal_ore = new Block[0];
	public static Block[] iron_ore = new Block[0];
	public static Block[] lapis_ore = new Block[0];
	public static Block[] redstone_ore = new Block[0];
	public static Block[] gold_ore = new Block[0];
	public static Block[] diamond_ore = new Block[0];
	public static Block[] emerald_ore = new Block[0];
	public static Block[] demontium_ore = new Block[0];
	public static Block[] harcadium_ore = new Block[0];
	public static Block[] hellsite_ore = new Block[0];
	public static Block[] void_ore = new Block[0];
	public static Block[] adamantium_ore = new Block[0];
	public static Block[] zinc_ore = new Block[0];
	public static Block[] thulium_ore = new Block[0];
	public static Block[] onyx_ore = new Block[0];
	public static Block[] mithril_ore = new Block[0];
	public static Block[] titanus_ore = new Block[0];
	
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Block> event)
    {
		TheTitans.debug("Registering blocks...");
        registry = event;
        Blocks.BEDROCK.setHarvestLevel("pickaxe", 21);
        Blocks.BEDROCK.setHardness(10000.0F);
        Blocks.BEDROCK.setCreativeTab(TheTitans.TAB_BLOCKS);
        add("nethrum", NETHRUM);
        add("adminium", ADMINIUM);
        add("corminium", CORMINIUM);
        add("brass_block", BRASS_BLOCK);
        add("cut_brass_block", CUT_BRASS_BLOCK);
        add("bronze_block", BRONZE_BLOCK);
        add("cut_bronze_block", CUT_BRONZE_BLOCK);
        add("steel_block", STEEL_BLOCK);
        add("cut_steel_block", CUT_STEEL_BLOCK);
        jadeite_ore = addOre("jadeite", 1.0F, 3490.0F, 4);
    	molten_fuel_ore = addOre("molten_fuel", 0.0F, 0.0F, 0);
    	pig_iron_ore = addOre("pig_iron", 0.0F, 0.0F, 1);
    	ruby_ore = addOre("ruby", 0.0F, 0.0F, 2);
    	sapphire_ore = addOre("sapphire", 0.0F, 0.0F, 2);
    	copper_ore = addOre("copper", 0.0F, -3.0F, 0);
    	tin_ore = addOre("tin", 0.0F, -1.0F, 0);
    	chromium_ore = addOre("chromium", 0.0F, 0.0F, 1);
    	nickel_ore = addOre("nickel", 0.0F, 0.0F, 1);
    	lead_ore = addOre("lead", 0.0F, 0.0F, 1);
    	silver_ore = addOre("silver", 0.0F, 0.0F, 1);
    	platinum_ore = addOre("platinum", 10.0F, 4.0F, 3);
    	titanium_ore = addOre("titanium", 20.0F, 20.0F, 4);
    	coal_ore = addOre("coal", 0.0F, 0.0F, 0, true, Blocks.STONE);
    	iron_ore = addOre("iron", 0.0F, 0.0F, 0, true, Blocks.STONE);
    	lapis_ore = addOre("lapis", 0.0F, 0.0F, 1, true, Blocks.STONE);
    	redstone_ore = addOre("redstone", 0.0F, 0.0F, 2, true, Blocks.STONE);
    	gold_ore = addOre("gold", 0.0F, 0.0F, 2, true, Blocks.STONE);
    	diamond_ore = addOre("diamond", 0.0F, 0.0F, 2, true, Blocks.STONE);
    	emerald_ore = addOre("emerald", 0.0F, 0.0F, 2, true, Blocks.STONE);
    	demontium_ore = addOre("demontium", 200.0F, 1490.0F, 17);
    	harcadium_ore = addOre("harcadium", 400.0F, 5990.0F, 18);
    	hellsite_ore = addOre("hellsite", 400.0F, 5990.0F, 19);
    	titanus_ore = addOre("titanus", 1000.0F, 23990.0F, 20);
    	void_ore = addOre("void", 1000.0F, 23990.0F, 21);
    	adamantium_ore = addOre("adamantium", 9000.0F, 5000000.0F, Integer.MAX_VALUE - 24);
    	zinc_ore = addOre("zinc", 0.0F, 0.0F, 2);
    	thulium_ore = addOre("thulium", 0.0F, 0.0F, 2);
    	onyx_ore = addOre("onyx", 0.0F, 0.0F, 2);
    	mithril_ore = addOre("mithril", 20.0F, 90.0F, 5);
        
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
	
	private static void addTileEntity(String registry_name, Class<? extends TileEntity> tile)
    {
        if (tile != null)
            GameRegistry.registerTileEntity(tile, new ResourceLocation(TheTitans.MODID, registry_name));
    }
    
    private static void add(String registry_name, Block block)
    {
        add(registry_name, null, block, true);
    }
    
    private static void add(String registry_name, Block block, boolean creative_tab)
    {
        add(registry_name, null, block, creative_tab);
    }
    
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
