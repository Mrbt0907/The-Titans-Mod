package net.mrbt0907.thetitans.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrbt0907.thetitans.entity.titan.EntityTitan;
import net.mrbt0907.thetitans.registries.ItemRegistry;
import net.mrbt0907.util.util.math.Maths;

public class EntityGammaLightning extends EntityLightningBolt
{
	public static final DamageSource GAMMA_BOLT = new DamageSource("lightningBolt").setFireDamage().setDamageBypassesArmor().setDamageIsAbsolute();
	private static final DataParameter<Float> COLOR_RED = EntityDataManager.<Float>createKey(EntityGammaLightning.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> COLOR_GREEN = EntityDataManager.<Float>createKey(EntityGammaLightning.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> COLOR_BLUE = EntityDataManager.<Float>createKey(EntityGammaLightning.class, DataSerializers.FLOAT);
	private final float colorRed;
	private final float colorGreen;
	private final float colorBlue;
	
	public EntityGammaLightning(World world)
	{
		this(world, 0, 0, 0, false, 0F, 0F, 0F);
	}
	
	public EntityGammaLightning(World world, double x, double y, double z, boolean effectOnlyIn, float r, float g, float b)
	{
		super(world, x, y, z, effectOnlyIn);
		colorRed = r;
		colorGreen = g;
		colorBlue = b;
	}
	
	protected void entityInit()
	{
		getDataManager().register(COLOR_RED, Float.valueOf(colorRed));
		getDataManager().register(COLOR_GREEN, Float.valueOf(colorGreen));
		getDataManager().register(COLOR_BLUE, Float.valueOf(colorBlue));
	}
	
	@Override
	public void onUpdate()
	{
		if (lightningState == 2)
		{
			world.playSound((EntityPlayer)null, posX, posY, posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 10000.0F, 0.5F + rand.nextFloat() * 0.3F);
			world.playSound((EntityPlayer)null, posX, posY, posZ, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.WEATHER, 2.0F, 0.5F + rand.nextFloat() * 0.2F);
		}

		--lightningState;
		if (lightningState < 0)
		{
			if (boltLivingTime == 0)
			{
				if (!world.isRemote)
					onImpactA();
				setDead();
			}
			else if (lightningState < -rand.nextInt(10))
			{
				--boltLivingTime;
				lightningState = 1;

				if (!effectOnly && !world.isRemote)
				{
					boltVertex = rand.nextLong();
					BlockPos blockpos = new BlockPos(this);

					if (world.getGameRules().getBoolean("doFireTick") && world.getBlockState(blockpos).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(world, blockpos))
						world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
				}
			}
		}

		if (lightningState >= 0)
		{
			if (world.isRemote)
				world.setLastLightningBolt(2);
			else if (!effectOnly)
			{
				List<Entity> list = new ArrayList<Entity>(world.loadedEntityList);
				onImpactB(list);
			}
		}
	}
	
	protected void onImpactA()
	{
		if (!world.getGameRules().getBoolean("doFireTick"))
			return;
		BlockPos position = getPosition();
		BlockPos pos;
		IBlockState state;
		
		if (world.getBlockState(position).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(world, position))
			world.setBlockState(position, Blocks.FIRE.getDefaultState());
		
		for (int i = 0, x, z; i < 16; ++i)
		{
			x = (int) (posX + Maths.random(-6.0D, 6.0D));
			z = (int) (posZ + Maths.random(-6.0D, 6.0D));
			pos = new BlockPos(x, world.getHeight(x, z), z);
			if (!world.isBlockLoaded(pos))
				continue;
			state = world.getBlockState(pos);
			if (state.getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(world, pos))
				world.setBlockState(pos, Blocks.FIRE.getDefaultState());
		}
	}
	
	protected void onImpactB(List<Entity> list)
	{
		boolean immune;
		for (Entity target : list)
			if (!target.isImmuneToFire() && target.getDistanceSq(this) < 100.0D)
			{
				immune = false;
				if (target instanceof EntityPlayer)
				{
					EntityPlayer player = ((EntityPlayer)target);
					if (player.inventory.hasItemStack(new ItemStack(ItemRegistry.ultimaBlade)) || player.inventory.hasItemStack(new ItemStack(ItemRegistry.optimaAxe)))
						immune = true;
				}
				
				immune = target instanceof EntityGammaLightning || target instanceof EntityTitan;
				
				if (!immune)
				{
					target.onStruckByLightning(this);
					target.setFire(1000);
					target.attackEntityFrom(GAMMA_BOLT, 200F);
					target.hurtResistantTime = 0;
				}
			}
	}
	
	public float[] getColor()
	{
		EntityDataManager manager = getDataManager();
		return new float[] {manager.get(COLOR_RED).floatValue(), manager.get(COLOR_GREEN).floatValue(), manager.get(COLOR_BLUE).floatValue()};
	}
	
}
