package net.mrbt0907.thetitans.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public abstract class CapabilitySpecial
{
	public double maxGauge = 100.0D;
	public double gauge;
	
	public void onAttack()
	{
		
	}
	
	public static class Storage implements Capability.IStorage<CapabilitySpecial>
	{
		@Override
		public NBTBase writeNBT(Capability<CapabilitySpecial> capability, CapabilitySpecial instance, EnumFacing side)
		{
			return new NBTTagCompound();
		}

		@Override
		public void readNBT(Capability<CapabilitySpecial> capability, CapabilitySpecial instance, EnumFacing side, NBTBase nbtData) {}
	}
	
	public static class Provider implements ICapabilitySerializable<NBTTagCompound>
	{
		@CapabilityInject(CapabilitySpecial.class)
		public static final Capability<CapabilitySpecial> INSTANCE = null;
		public static final EnumFacing FACE = EnumFacing.DOWN;
		private CapabilitySpecial defaultInstance = INSTANCE.getDefaultInstance();
		
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing)
		{
			return capability == INSTANCE && facing == FACE;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing)
		{
			return capability == INSTANCE && facing == FACE? INSTANCE.cast(defaultInstance) : null;
		}

		@Override
		public NBTTagCompound serializeNBT()
		{
			return (NBTTagCompound) INSTANCE.writeNBT(defaultInstance, FACE);
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt)
		{
			INSTANCE.readNBT(defaultInstance, FACE, nbt);
		}
	}
}