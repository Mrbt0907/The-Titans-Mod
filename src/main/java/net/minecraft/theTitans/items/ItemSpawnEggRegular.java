package net.minecraft.theTitans.items;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titanminion.EntitySkeletonMinion;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
public class ItemSpawnEggRegular extends ItemBase
{
	@SideOnly(Side.CLIENT)
	private IIcon primaryIcon;
	@SideOnly(Side.CLIENT)
	private IIcon secondaryIcon;
	public ItemSpawnEggRegular()
	{
		super("spawn_egg");
		setHasSubtypes(true);
	}

	public String getItemStackDisplayName(ItemStack stack)
	{
		ModContainer container = FMLCommonHandler.instance().findContainerFor(TheTitans.modInstance);
		EntityRegistry.EntityRegistration registration = EntityRegistry.instance().lookupModSpawn(container, stack.getItemDamage());	
		return "Spawn " + ((registration == null) ? "Unknown" : StatCollector.translateToLocal(new StringBuilder().append("entity.thetitans.").append(registration.getEntityName()).append(".name").toString()));
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int i)
	{
		EggColors colors = (EggColors)eggColorsMap.get(Integer.valueOf(stack.getItemDamage()));
		if (colors == null)
		{
			return 16777215;
		}

		return i == 0 ? colors.primaryColor : colors.secondaryColor;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		if (world.isRemote)
		{
			return true;
		}

		Block block = world.getBlock(x, y, z);
		x += net.minecraft.util.Facing.offsetsXForSide[side];
		y += net.minecraft.util.Facing.offsetsYForSide[side];
		z += net.minecraft.util.Facing.offsetsZForSide[side];
		double addY = 0.0D;
		if ((side == 1) && (block != Blocks.air) && (block.getRenderType() == 11))
		{
			addY = 0.5D;
		}

		EntityLiving living = spawnMobCreature(world, stack.getItemDamage(), x + 0.5D, y + addY, z + 0.5D);
		if (living != null)
		{
			if (stack.hasDisplayName())
			{
				living.setCustomNameTag(stack.getDisplayName());
			}

			if (player.isSneaking() && !player.onGround)
			{
				if (living instanceof EntitySkeletonMinion)
				((EntitySkeletonMinion)living).setSkeletonType(1);
				living.setHealth(living.getMaxHealth());
			}

			if (!player.capabilities.isCreativeMode)stack.stackSize -= 1;
			if (player.isSneaking())
			{
				for (int i = 0; i <= 9; i++)
				{
					EntityLiving living1 = spawnMobCreature(world, stack.getItemDamage(), x + 0.5D, y + addY, z + 0.5D);
					if (living1 != null)
					{
						if (stack.hasDisplayName())
						{
							living1.setCustomNameTag(stack.getDisplayName());
						}

						if (player.isSneaking() && !player.onGround)
						{
							if (living1 instanceof EntitySkeletonMinion)
							((EntitySkeletonMinion)living1).setSkeletonType(1);
							living1.setHealth(living1.getMaxHealth());
						}
					}
				}
			}
		}

		return true;
	}

	public static EntityLiving spawnMobCreature(World world, int id, double x, double y, double z)
	{
		if (world.isRemote)
		{
			return null;
		}

		ModContainer container = FMLCommonHandler.instance().findContainerFor(TheTitans.modInstance);
		EntityRegistry.EntityRegistration registration = EntityRegistry.instance().lookupModSpawn(container, id);
		Entity entity = createEntity(registration.getEntityClass(), world);
		if ((entity != null) && ((entity instanceof EntityLiving)))
		{
			EntityLiving living = (EntityLiving)entity;
			living.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
			living.rotationYawHead = living.rotationYaw;
			living.renderYawOffset = living.rotationYaw;
			living.onSpawnWithEgg(null);
			world.spawnEntityInWorld(living);
			living.playLivingSound();
			return living;
		}

		return null;
	}

	public static Entity createEntity(Class<? extends Entity> clazz, World world)
	{
		if (clazz == null)
		{
			return null;
		}

		Entity entity = null;
		try
		{
			entity = (Entity)clazz.getConstructor(new Class[] 
			{
				 World.class 
			}
			).newInstance(new Object[] 
			{
				 world 
			}
			);
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}

		return entity;
	}

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 > 0 ? this.secondaryIcon : this.primaryIcon;
	}

	@SuppressWarnings(
	{
		 "rawtypes", "unchecked" 
	}
	)
	public void getSubItems(Item par1, CreativeTabs creativeTabs, List list)
	{
		Iterator<Integer> iterator = eggColorsMap.keySet().iterator();
		while (iterator.hasNext())
		{
			list.add(new ItemStack(par1, 1, ((Integer)iterator.next()).intValue()));
		}
	}

	public void registerIcons(IIconRegister iconRegister)
	{
		super.registerIcons(iconRegister);
		this.primaryIcon = iconRegister.registerIcon(TheTitans.getTextures("spawn_egg_yin"));
		this.secondaryIcon = iconRegister.registerIcon(TheTitans.getTextures("spawn_egg_yang"));
	}

	@SuppressWarnings("unlikely-arg-type")
	public static void addEgg(Class<? extends Entity> clazz, int color1, int color2)
	{
		if (eggColorsMap.containsKey(clazz))
		{
			throw new IllegalArgumentException("Mapping for that entity exists already!");
		}

		EntityRegistry.EntityRegistration registration = EntityRegistry.instance().lookupModSpawn(clazz, false);
		eggColorsMap.put(Integer.valueOf(registration.getModEntityId()), new EggColors(color1, color2));
	}

	public static HashMap<Integer, EggColors> eggColorsMap = new HashMap<Integer, EggColors>();
	protected static class EggColors
	{
		private int primaryColor;
		private int secondaryColor;
		public EggColors(int color1, int color2)
		{
			this.primaryColor = color1;
			this.secondaryColor = color2;
		}
	}
}


