package net.mrbt0907.utils;
import java.util.*;
import java.util.Map.Entry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
public class RegistryHelper
{
	private List<IRegistry> list = new ArrayList<IRegistry>();
	private DebugExtender debugger;
	public static interface IRegistry
	{
		/**Called when the mod is in the PreInit stage*/
		void preInit();
		/**Called when the mod is in the Init stage*/
		void init();
		/**Called when the mod is in the PostInit stage*/
		void postInit();
		/**Called whenever the mod wants to update any registered objects*/
		void update();
	}

	@SuppressWarnings("unchecked")
	public static class Registry <T, E>
	{
		private E defaultz;
		private final Map<Object[], E> registry = new HashMap<Object[], E>();
		public Registry()
		{
			defaultz = null;
		}

		public Registry(E defaultValue)
		{
			defaultz = defaultValue;
		}

		public int size()
		{
			return registry.size();
		}

		public Set<Entry<Integer, E>> idSet()
		{
			Map<Integer, E> entrySet = new HashMap<Integer, E>();
			for (Entry<Object[], E> entry : registry.entrySet())
			entrySet.put((int)entry.getKey()[0], entry.getValue());
			return entrySet.entrySet();
		}

		public Set<Entry<T, E>> keySet()
		{
			Map<T, E> entrySet = new HashMap<T, E>();
			for (Entry<Object[], E> entry : registry.entrySet())
			entrySet.put((T)entry.getKey()[1], entry.getValue());
			return entrySet.entrySet();
		}

		public Set<Entry<Object[], E>> entrySet()
		{
			return registry.entrySet();
		}

		public E get(int id)
		{
			for (Map.Entry<Object[], E> entry : registry.entrySet())
			if ((int)entry.getKey()[0] == id)
			return entry.getValue();
			return null;
		}

		public E get(T key)
		{
			for (Map.Entry<Object[], E> entry : registry.entrySet())
			if ((T)entry.getKey()[1] == key)
			return entry.getValue();
			return null;
		}

		public int getID(T key)
		{
			for (Map.Entry<Object[], E> entry : registry.entrySet())
			if (((T)entry.getKey()[0]).equals(key))
			return (int)entry.getKey()[0];
			return -1;
		}

		public T getKey(int id)
		{
			for (Map.Entry<Object[], E> entry : registry.entrySet())
			if ((int)entry.getKey()[0] == id)
			return (T)entry.getKey()[1];
			return null;
		}

		public boolean add(T... keys)
		{
			if (keys.length < 1)
			return false;
			for (T key : keys)
			if (key != null)
			registry.put(new Object[] 
			{
				registry.size(), key
			}

			, defaultz);
			else
			return false;
			return true;
		}

		public boolean set(int id, E value)
		{
			if (id < registry.size())
			for (Map.Entry<Object[], E> entry : registry.entrySet())
			if ((int)entry.getKey()[0] == id)
			{
				registry.replace(entry.getKey(), value);
				return true;
			}

			return false;
		}

		public boolean set(T key, E value)
		{
			for (Map.Entry<Object[], E> entry : registry.entrySet())
			if (((T)entry.getKey()[1]).equals(key))
			{
				registry.replace(entry.getKey(), value);
				return true;
			}

			return false;
		}

		public boolean contains(T key)
		{
			for (Map.Entry<Object[], E> entry : registry.entrySet())
			if (((T)entry.getKey()[1]).equals(key))
			return true;
			return false;
		}
	}

	@SafeVarargs
	public RegistryHelper(DebugExtender debugger, Class<? extends IRegistry>... registries)
	{
		this.debugger = debugger;
		for(Class<? extends IRegistry> registry : registries)
		if (registry != null)
		try
		{
			list.add(registry.newInstance());
		}

		catch (Exception e)
		{
			this.debugger.fatal("Failed", e);
		}
	}

	public void preInit()
	{
		for(IRegistry registry : list)
		if (registry != null)
		registry.preInit();
	}

	public void init()
	{
		for(IRegistry registry : list)
		if (registry != null)
		registry.init();
	}

	public void postInit()
	{
		for(IRegistry registry : list)
		if (registry != null)
		registry.postInit();
	}

	public void update(Class<? extends IRegistry> clazz)
	{
		if (clazz != null)
		for(IRegistry registry : list)
		{
			if (registry != null && clazz.equals(registry.getClass()))
			registry.update();
		}

		else
		for(IRegistry registry : list)
		if (registry != null)
		registry.update();
	}

	////////////////////////////////////////////////////////////////////////////////
	//                                                                           //
	///////////////////////////////////////////////////////////////////////////////
	public static void register(Object... objects)
	{
		for (Object object : objects)
		{
			if (object instanceof Item)
			GameRegistry.registerItem((Item)object, ((Item)object).getUnlocalizedName().substring(5));
			else if (object instanceof Block)
			GameRegistry.registerBlock((Block)object, ((Block)object).getUnlocalizedName().substring(5));
		}
	}

	public static void register(String oreDictionary, Object... objects)
	{
		for (Object object : objects)
		{
			if (object instanceof Item)
			{
				GameRegistry.registerItem((Item)object, ((Item)object).getUnlocalizedName().substring(5));
				OreDictionary.registerOre(oreDictionary, (Item)object);
			}

			else if (object instanceof Block)
			{
				GameRegistry.registerBlock((Block)object, ((Block)object).getUnlocalizedName().substring(5));
				OreDictionary.registerOre(oreDictionary, (Block)object);
			}
		}
	}
}


