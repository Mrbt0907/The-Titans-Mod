package net.mrbt0907.thetitans.entity;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrbt0907.thetitans.entity.titan.EntityTitan;
import net.mrbt0907.thetitans.registries.ItemRegistry;
import net.mrbt0907.util.util.math.Maths;

public class EntityUrLightning extends EntityGammaLightning {

	public EntityUrLightning(World world)
	{
		super(world);
	}

	public EntityUrLightning(World world, double x, double y, double z, boolean effectOnly)
	{
		super(world, x, y, z, effectOnly, 0F, 0F, 0F);
		
	}
	
	@Override
	protected void onImpactA()
	{
		if (!world.getGameRules().getBoolean("mobGriefing"))
			return;
		BlockPos position = getPosition();
		BlockPos pos;
		IBlockState state;
		
		if (world.getBlockState(position).getMaterial() == Material.AIR && Blocks.FIRE.canPlaceBlockAt(world, position))
			world.setBlockState(position, Blocks.FIRE.getDefaultState());
		
		for (int i = 0, x, z; i < 100; ++i)
		{
			x = (int) (posX + Maths.random(-10.0D, 10.0D));
			z = (int) (posZ + Maths.random(-10.0D, 10.0D));
			pos = new BlockPos(x, world.getHeight(x, z) - 1, z);
			if (!world.isBlockLoaded(pos))
				continue;
			state = world.getBlockState(pos);
			if (state.getMaterial() != Material.AIR)
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}
	
	@Override
	protected void onImpactB(List<Entity> list)
	{
		boolean immune;
		for (Entity target : list)
			if (target.getDistanceSq(this) < 50.0D)
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
					target.setFire(Integer.MAX_VALUE - 1);
					target.hurtResistantTime = 0;
					
					//Obliterate
					if (!(target instanceof EntityPlayer))
					{
						target.attackEntityFrom(GAMMA_BOLT, Float.MAX_VALUE - 1);
						target.hurtResistantTime = 0;
						target.setDead();
						target.isDead = true;
						if (target instanceof EntityLivingBase)
						{
							EntityLivingBase targetLiving = (EntityLivingBase) target;
							targetLiving.dead = true;
							targetLiving.deathTime += 1;
							targetLiving.setHealth(0F);
							targetLiving.onDeath(GAMMA_BOLT);
						}
						try
						{
							world.removeEntityDangerously(target);
						}
						catch(Exception e)
						{
							world.removeEntity(target);
						}
						
					}
					else
						target.attackEntityFrom(GAMMA_BOLT, 2000F);
				}
			}
	}
	
	@Override
	public float[] getColor()
	{
		return new float[] {Maths.random(1.0F), Maths.random(1.0F), Maths.random(1.0F)};
	}
}
