package net.mrbt0907.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
public class ReflectionHelper
{
	private DebugExtender debugger;
	public ReflectionHelper(DebugExtender debugger)
	{
		this.debugger = debugger;
	}

	public <T, E> void setAlt(Class <? extends T> classToAccess, T instance, E value, String... fieldNames)
	{
		Field field = cpw.mods.fml.relauncher.ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));
		try
		{
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(instance, value);
		}

		catch (Exception e)
		{
			debugger.error("Failed to set field", e);
		}
	}

	public <T, E> void set(Class <T> classToAccess, T instance, E value, String... fieldNames)
	{
		Field field = cpw.mods.fml.relauncher.ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));
		try
		{
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(instance, value);
		}

		catch (Exception e)
		{
			debugger.error("Failed to set field", e);
		}
	}

	public <T> Object get(Class <? super T > classToAccess, T instance, String... fieldNames)
	{
		Field field = cpw.mods.fml.relauncher.ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));
		try
		{
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			return field.get(instance);
		}

		catch (Exception e)
		{
			debugger.error("Failed to get field", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> void view(String... classes)
	{
		for (int i = 0; i < classes.length; i++)
		try
		{
			Class <? extends T> clazz = (Class<? extends T>) Class.forName(classes[i]);
			Field fields[] = clazz.getDeclaredFields();
			for (int ii = 0; ii < fields.length; ii++)	
			try
			{
				debugger.debug("Found variable: [ " + Modifier.toString(fields[ii].getModifiers()) + " " + fields[ii].getType().getSimpleName() + " " + fields[ii].getName() + "; ]");
			}

			catch (Exception e)
			{
				debugger.error("Cannot show variable", e);
			}
		}

		catch (Exception e)
		{
			debugger.error("Failed to find class file", e);
		}
	}
}


