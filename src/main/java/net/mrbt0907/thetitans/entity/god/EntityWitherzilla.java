package net.mrbt0907.thetitans.entity.god;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mrbt0907.thetitans.TheTitans;
import net.mrbt0907.thetitans.api.TitanAPI;
import net.mrbt0907.thetitans.config.ConfigMain;
import net.mrbt0907.thetitans.entity.EntityMultiPart;
import net.mrbt0907.thetitans.entity.EntityUrLightning;
import net.mrbt0907.thetitans.entity.titan.EntityTitan;
import net.mrbt0907.thetitans.network.PacketTargeting;
import net.mrbt0907.thetitans.registries.ItemRegistry;
import net.mrbt0907.thetitans.registries.SoundRegistry;
import net.mrbt0907.util.util.TranslateUtil;
import net.mrbt0907.util.util.WorldUtil;
import net.mrbt0907.util.util.math.Maths;

public final class EntityWitherzilla extends EntityTitan implements IRangedAttackMob
{	
	private static final DataParameter<Integer> ATTACK_TARGET = EntityDataManager.<Integer>createKey(EntityWitherzilla.class, DataSerializers.VARINT);
	private final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
	private float[] headPitches = new float[2];
	private float[] headRotations = new float[2];
	private int quoteIndex;
	
	public EntityWitherzilla(World worldIn)
	{
		super(worldIn);
		experienceValue = ConfigMain.tab_titans.tab_witherzilla.experience;
	}
	
	 protected void entityInit()
	 {
		 super.entityInit();
		 dataManager.register(ATTACK_TARGET, Integer.valueOf(0));
	 }

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ConfigMain.tab_titans.tab_witherzilla.movement_speed);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ConfigMain.tab_titans.tab_witherzilla.attack_damage);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ConfigMain.tab_titans.tab_witherzilla.armor);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(getSizeMultiplier() * 1.5D);
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund)
	{
		super.readEntityFromNBT(tagCompund);
		quoteIndex = tagCompund.getInteger("quoteIndex");
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setInteger("quoteIndex", quoteIndex);
		super.writeEntityToNBT(tagCompound);
	}
	
	//------- TICK METHODS -------\\
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (world.getWorldTime() != 8000)
			world.setWorldTime(8000);
		
		EntityLivingBase primaryTarget = getPrimaryTarget();
		
		if (isServerWorld())
		{
			WorldInfo worldinfo = world.getWorldInfo();
			
			noClip = true;
			motionY *= 0.9D;
			setNoGravity(true);
			
			if (motionY > 1.0D)
				motionY = 1.0D;
			
			if (primaryTarget != null)
			{
				if (posY > primaryTarget.posY + 8)
					motionY -= 0.2D;
				
				if (Maths.distance(posX, posY, posZ, primaryTarget.posX, posY, primaryTarget.posZ) > 256)
				{
					double speed = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
					double direction = Math.toDegrees(Maths.fastATan2(primaryTarget.posX - posX, primaryTarget.posZ - posZ));
					motionX += Maths.fastCos(direction) * speed * 0.1D;
					motionZ -= Maths.fastSin(direction) * speed * 0.1D;
				}
				else
				{
					motionX = 0.0D;
					motionZ = 0.0D;
				}

				if (ticksExisted % 20 == 0 && primaryTarget != null)
					attackEntityWithRangedAttack(primaryTarget, 0.0F);
			}
			else
			{
				motionX = 0.0D;
				motionZ = 0.0D;
			}
			
			if (ticksExisted % 10 == 0)
			{
				targets.forEach(target ->
				{
					if (Maths.chance(1) && !(target instanceof EntityPlayer) && target.isEntityAlive())
						attackUrLightning((EntityLivingBase) target);
				});
			}
			
			if (rand.nextInt(100) == 0)
			{
				for (int l = 0; l < 20; l++)
				{
					int i = MathHelper.floor(posX);
					int j = MathHelper.floor(posY);
					int k = MathHelper.floor(posZ);
					
					Maths.updateRandom(rand);
					int posX = (int) (i + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 0.4F);
					int posY = (int) (j + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 0.4F);
					int posZ = (int) (k + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 0.4F);
					Maths.updateRandom();
					
					EntityLightningBolt entitylightning = new EntityLightningBolt(world, posX, posY, posZ, false);
					
					if (world.getTopSolidOrLiquidBlock(new BlockPos(posX, posY - 1, posZ)).getY() > 0 && world.checkNoEntityCollision(entitylightning.getEntityBoundingBox()) && world.getCollisionBoxes(entitylightning, entitylightning.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(entitylightning.getEntityBoundingBox()))
						world.addWeatherEffect(entitylightning);
				}
			}
			
			if (!worldinfo.isRaining())
			{
				worldinfo.setRainTime(9999999);
				worldinfo.setThunderTime(1000000);
				worldinfo.setRaining(true);
				worldinfo.setThundering(true);
			}
			
			if (rand.nextInt(250) == 0 && ticksTalked == 0L)
				world.playerEntities.forEach(player -> onQuote(player, 4, world.provider.getDimension() == TheTitans.DIMENSION_VOID_ID ? stage : stage + 2));
			
		}
		
		if (primaryTarget != null)
			getLookHelper().setLookPosition(primaryTarget.posX, primaryTarget.posY + primaryTarget.getEyeHeight(), primaryTarget.posZ, 10.0F, 10.0F);
		
		for (int headIndex = 0; headIndex < 2; ++headIndex)
		{
			if (getAttackTarget() != null)
			{
				double headX = getHeadPosX(headIndex);
				double headY = getHeadPosY(headIndex);
				double headZ = getHeadPosZ(headIndex);
				double rotationX = primaryTarget.posX - headX;
				double rotationY = primaryTarget.posY + (double)primaryTarget.getEyeHeight() - headY;
				double rotationZ = primaryTarget.posZ - headZ;
				double distance = (double)MathHelper.sqrt(rotationX * rotationX + rotationZ * rotationZ);
				float pitch = (float)(Maths.fastATan2(rotationZ, rotationX) * (180D / Math.PI)) - 90.0F;
				float rotation = (float)(-(Maths.fastATan2(rotationY, distance) * (180D / Math.PI)));
				headRotations[headIndex] = rotlerp(headRotations[headIndex], rotation, 10.0F);
				headPitches[headIndex] = rotlerp(headPitches[headIndex], pitch, 10.0F);
			}
			else if (!targets.isEmpty())
			{
				EntityLivingBase target = getTarget(headIndex);
				double headX = getHeadPosX(headIndex);
				double headY = getHeadPosY(headIndex);
				double headZ = getHeadPosZ(headIndex);
				double rotationX = target.posX - headX;
				double rotationY = target.posY + (double)target.getEyeHeight() - headY;
				double rotationZ = target.posZ - headZ;
				double distance = (double)MathHelper.sqrt(rotationX * rotationX + rotationZ * rotationZ);
				float pitch = (float)(Maths.fastATan2(rotationZ, rotationX) * (180D / Math.PI)) - 90.0F;
				float rotation = (float)(-(Maths.fastATan2(rotationY, distance) * (180D / Math.PI)));
				headRotations[headIndex] = rotlerp(headRotations[headIndex], rotation, 10.0F);
				headPitches[headIndex] = rotlerp(headPitches[headIndex], pitch, 10.0F);
			}
			else
			{
				headRotations[headIndex] = rotlerp(headRotations[headIndex], 0.0F, 10.0F);
				headPitches[headIndex] = rotlerp(headPitches[headIndex], renderYawOffset, 10.0F);
			}
		}
	}
	
	protected void updateAITasks()
	{
		super.updateAITasks();
		
		if (ticksExisted % 200 == 0)
		{
			targets.clear();
			EntityLivingBase primaryTarget = getAttackTarget();
			List<Entity> targets = WorldUtil.getEntities(this, getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue(), IS_VALID_TARGET);
			
			for (Entity target : targets)
			{
				if (target == primaryTarget || isOnSameTeam(target)) continue;
				this.targets.add((EntityLivingBase) target);
			}
			
			PacketTargeting.sendTargets(world, this, this.targets);
		}
	}

	protected void onDeathUpdate()
	{
		if (isEntityAlive()) return;
		super.onDeathUpdate();
		
		if (isServerWorld())
		{
			if (deathTicks == 1)
			{
				BlockPos pos = new BlockPos(posX, posY, posZ);
				playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
				world.playBroadcastSound(1018, pos, 0);
				world.playBroadcastSound(1018, pos, 0);
				world.playBroadcastSound(1018, pos, 0);
				world.playBroadcastSound(1018, pos, 0);
				
				List<EntityPlayerMP> players = world.getMinecraftServer().getPlayerList().getPlayers();
				for (EntityPlayerMP player : players)
				{
					ItemStack item = new ItemStack(ItemRegistry.ultimaBlade, 1, 1);
					player.entityDropItem(item, 0.0F);
					playLivingSound();
					
					onQuote(player, 10, 0);
				}
			}
		}

		if ((deathTicks >= 400))
			setDead();
	}
	
	
	//------- EVENTS -------\\
	@Override
	protected List<EntityMultiPart> onHitboxCreate(List<EntityMultiPart> hitboxes)
	{
		hitboxes.add(new EntityMultiPart(this, "main", 0, (int) height, 0, getSizeMultiplier(), getSizeMultiplier(), true, false));
		return hitboxes;
	}
	
	public void onQuote(EntityPlayer player, int index, int subIndex)
	{
		
	}
	
	protected void dropLoot()
	{
		if (isServerWorld())
		{
			createBeaconPortal(MathHelper.floor(posX), MathHelper.floor(posZ));
			if ((world.getGameRules().getBoolean("doMobLoot")))
			{
				dropFewItems(true, 0);
				dropEquipment(true, 0);
			}
		}
	}
	
	//------- FUNCTIONAL METHODS -------\\
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		if (TitanAPI.isEntityGiant(target))
		{
			double d0 = getDistance(target);
			if (d0 < target.width +this.width && this.ticksExisted % 20 == 0)
				attackEntityAsMob(target);
		}
		else
			attackWitherSkull(0, target, true);
	}
	
	public void attackUrLightning(Entity entity)
	{
		if (entity != null)
		{	
			if (!TitanAPI.isEntityGiant(entity))
				entity.motionY += 0.5D;
			
			world.spawnEntity(new EntityUrLightning(world, entity.posX, entity.posY, entity.posZ, false));
		}
	}
	
	private void attackWitherSkull(int headIndex, EntityLivingBase target, boolean isInvunerable)
	{
		attackWitherSkull(headIndex, target.posX, target.posY + target.getEyeHeight() * 0.5D, target.posZ, isInvunerable);
	}

	private void attackWitherSkull(int headIndex, double targetPosX, double targetPosY, double targetPosZ, boolean isInvunerable)
	{
		double posX = getHeadPosX(headIndex);
		double posY = getHeadPosY(headIndex);
		double posZ = getHeadPosZ(headIndex);
		double velX = targetPosX - posX;
		double velY = targetPosY - posY;
		double velZ = targetPosZ - posZ;
		EntityWitherSkull entitywitherskull = new EntityWitherSkull(world, this, velX, velY, velZ);
		if (isInvunerable)
			entitywitherskull.setInvulnerable(true);
		entitywitherskull.posY = posY;
		entitywitherskull.posX = posX;
		entitywitherskull.posZ = posZ;
		world.playEvent((EntityPlayer)null, 1024, new BlockPos(this), 0);
	}
	
	private void createBeaconPortal(int posX, int posZ)
	{
		byte b0 = 64;
		byte b1 = 4;
		for (int k = b0 - 1; k <= b0 + 32; k++)
		for (int l = posX - b1; l <= posX + b1; l++)
		for (int i1 = posZ - b1; i1 <= posZ + b1; i1++)
		{
			double d0 = l - posX;
			double d1 = i1 - posZ;
			double d2 = d0 * d0 + d1 * d1;
			if (d2 <= (b1 - 0.5D) * (b1 - 0.5D))
			if (k < b0)
			if (d2 <= (b1 - 1 - 0.5D) * (b1 - 1 - 0.5D))
			world.setBlockState(new BlockPos(l, k, i1), Blocks.BEDROCK.getDefaultState());
			else if (k > b0)
				world.setBlockState(new BlockPos(l, k, i1), Blocks.AIR.getDefaultState());
			else if (d2 > (b1 - 1 - 0.5D) * (b1 - 1 - 0.5D))
				world.setBlockState(new BlockPos(l, k, i1), Blocks.BEDROCK.getDefaultState());
			else
				world.setBlockState(new BlockPos(l, k, i1), Blocks.END_PORTAL.getDefaultState());
		}

		world.setBlockState(new BlockPos(posX, b0 + 0, posZ), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(new BlockPos(posX, b0 + 1, posZ), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(new BlockPos(posX, b0 + 2, posZ), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(new BlockPos(posX - 1, b0 + 2, posZ), Blocks.TORCH.getDefaultState());
		world.setBlockState(new BlockPos(posX + 1, b0 + 2, posZ), Blocks.TORCH.getDefaultState());
		world.setBlockState(new BlockPos(posX, b0 + 2, posZ - 1), Blocks.TORCH.getDefaultState());
		world.setBlockState(new BlockPos(posX, b0 + 2, posZ + 1), Blocks.TORCH.getDefaultState());
		world.setBlockState(new BlockPos(posX, b0 + 3, posZ), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(new BlockPos(posX, b0 + 4, posZ), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(posX + 1, b0 + 4, posZ + 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(posX + 1, b0 + 4, posZ), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(posX + 1, b0 + 4, posZ - 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(posX - 1, b0 + 4, posZ + 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(posX - 1, b0 + 4, posZ), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(posX - 1, b0 + 4, posZ - 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(posX, b0 + 4, posZ + 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(posX, b0 + 4, posZ - 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(posX, b0 + 5, posZ), Blocks.BEACON.getDefaultState());
	}
	
	
	//------- GETTER/SETTER METHODS -------\\
	public boolean isInOmegaForm()
	{
		return world.provider instanceof WorldProviderSurface;
	}
	
	public EntityLivingBase getPrimaryTarget()
	{
		EntityLivingBase target = getAttackTarget();
		return target == null ? getTarget(0) : target;
	}
	
	public EntityLivingBase getTarget()
	{
		return getTarget(Maths.random(targets.size() - 1));
	}
	
	public EntityLivingBase getTarget(int index)
	{
		if (targets.isEmpty()) return null;
		return targets.get(Math.min(index, targets.size() - 1));
	}
	
	@SideOnly(Side.CLIENT)
	public void updateTargets(NBTTagCompound nbt)
	{
		Set<String> keys = nbt.getKeySet();
		Entity target;
		
		targets.clear();
		for (String key : keys)
		{
			target = world.getEntityByID(nbt.getInteger(key));
			
			if (target != null && target instanceof EntityLivingBase)
				targets.add((EntityLivingBase) target);
		}
	}
	
	private double getHeadPosX(int headIndex)
	{
		return posX;
	}

	private double getHeadPosY(int headIndex)
	{
		return posY + getSizeMultiplier();
	}

	private double getHeadPosZ(int headIndex)
	{
		return posZ;
	}
	
	@SideOnly(Side.CLIENT)
	public float getHeadPitch(int headIndex)
	{
		return headPitches[headIndex];
	}
	
	@SideOnly(Side.CLIENT)
	public float getHeadRotation(int headIndex)
	{
		return headRotations[headIndex];
	}
	
	private float rotlerp(float source, float target, float delta)
	{
		float f = MathHelper.wrapDegrees(target - source);

		if (f > delta)
			f = delta;

		if (f < -delta)
			f = -delta;

		return source + f;
	}
	
	
	//------- OVERRIDES -------\\
	@Override
	public String getName()
	{
		if (ticksExisted % 3 + rand.nextInt(3) == 0)
			return "\u00A7k" + TranslateUtil.translate("entity.witherzilla.name.true");
		else
			return "\u00A7l" + TranslateUtil.translate("entity.witherzilla.name");
	}
	
	@Override
	public double getTrueMaxHealth() {return ConfigMain.tab_titans.tab_witherzilla.health;}

	@Override
	public double getMaxStamina() {return Double.MAX_VALUE;}

	@Override
	protected void setStamina(double value) {}
	
	@Override
	public double getStamina() {return Double.MAX_VALUE;}

	@Override
	protected SoundEvent getAmbientSound() {return SoundRegistry.get("witherzilla.living");}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {return SoundRegistry.get("witherzilla.grunt");}

	@Override
	protected SoundEvent getDeathSound() {return SoundRegistry.get("witherzilla.death");}

	@Override
	public void setSwingingArms(boolean swingingArms) {}
	
	@Override
	public float getSizeMultiplier()
	{
		return isInOmegaForm() ? ConfigMain.tab_titans.tab_witherzilla.true_scale : ConfigMain.tab_titans.tab_witherzilla.scale;
	}
	
	@Override
	public float getRenderSizeMultiplier()
	{
		return isInOmegaForm() ? ConfigMain.tab_titans.tab_witherzilla.true_scale : ConfigMain.tab_titans.tab_witherzilla.scale;
	}
}
