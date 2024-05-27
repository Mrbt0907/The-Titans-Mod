package net.minecraft.titans.entity.god;

import java.util.ArrayList;
import java.util.List;

import net.endermanofdoom.mac.enums.EnumGender;
import net.endermanofdoom.mac.music.IMusicInteractable;
import net.endermanofdoom.mac.util.TranslateUtil;
import net.endermanofdoom.mac.util.math.Maths;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
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
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.api.EnumMobTier;
import net.minecraft.titans.entity.EntityMultiPart;
import net.minecraft.titans.entity.EntityUrLightning;
import net.minecraft.titans.entity.titan.EntityTitan;
import net.minecraft.titans.registries.TItems;
import net.minecraft.titans.registries.TSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class EntityWitherzilla extends EntityTitan implements IRangedAttackMob
{	
	private static final DataParameter<Integer> ATTACK_TARGET = EntityDataManager.<Integer>createKey(EntityWitherzilla.class, DataSerializers.VARINT);
	private float[] headPitches = new float[2];
	private float[] headRotations = new float[2];
	private int quoteIndex;
	
	public EntityWitherzilla(World worldIn)
	{
		super(worldIn);
		experienceValue = 5000000;
		if (worldIn != null && worldIn.isRemote)
			net.endermanofdoom.mac.internal.music.MusicManager.addMusicInteractable((IMusicInteractable) this);
	}

	@Override
	protected List<EntityMultiPart> onHitboxCreate(List<EntityMultiPart> hitboxes)
	{
		return super.onHitboxCreate(hitboxes);
	}
	
	 protected void entityInit()
	 {
		 super.entityInit();
		 dataManager.register(ATTACK_TARGET, Integer.valueOf(0));
	 }
	
	protected void initEntityAI()
	{
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityLivingBase>(this, EntityLivingBase.class, false, false));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1000000.0D);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(100000.0D);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(getSizeMultiplier() * 1.5D);
		setMaxHealth(1000000000000000000D);
		setHealthD(getMaxHealthD());

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
	
	@Override
	public float getSizeMultiplier()
	{
		return isInOmegaForm() ? 512.0F : 256.0F;
	}
	
	@Override
	public float getRenderSizeMultiplier()
	{
		return isInOmegaForm() ? 512.0F : 256.0F;
	}
	
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		Entity entity = getTarget();
		
		if (world.getWorldTime() != 8000)
			world.setWorldTime(8000);
		
        if (this.getHealthD() != this.getMobHealth() && this.isEntityAlive())
        {
    		setMaxHealth(getMobHealth());
    		setHealthD(getMaxHealthD());
        }
        
        if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() != this.getMobAttack())
        	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getMobAttack());

        if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() != this.getMobSpeed())
        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getMobSpeed());
		
		if (ticksExisted % 3 + rand.nextInt(3) == 0)
			setCustomNameTag("\u00A7k" + TranslateUtil.translate("entity.witherzilla.name.true"));
		else
			setCustomNameTag("\u00A7l" + TranslateUtil.translate("entity.witherzilla.name"));
		
		if (isServerWorld())
		{
			WorldServer worldserver = (WorldServer) world;
			WorldInfo worldinfo = worldserver.getWorldInfo();
			motionY *= 0.9D;
			noClip = true;
			setNoGravity(true);
			
			
			if (entity != null)
			{
				if (Maths.distance(posX, posY, posZ, entity.posX, posY, entity.posZ) > 256)
				{
					double speed = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
					double direction = Math.toDegrees(Math.atan2(entity.posX - posX, entity.posZ - posZ));
					motionX += Math.cos(direction) * speed * 0.1D;
					motionZ -= Math.sin(direction) * speed * 0.1D;
				}
				else
				{
					motionX = 0.0D;
					motionZ = 0.0D;
				}
			}
			else
			{
				motionX = 0.0D;
				motionZ = 0.0D;
			}
			
			if (ticksExisted % 10 == 0)
			{
				List<Entity> targets = new ArrayList<Entity>(world.loadedEntityList);
				targets.forEach(target ->
				{
					if (Maths.random(100) == 0)
						if (!(target instanceof EntityPlayer) && target instanceof EntityLivingBase && target.isEntityAlive())
							doLightningAttackTo((EntityLivingBase) target);
				});
			}
			
			if (ticksExisted % 20 == 0 && getAttackTarget() != null && !(getAttackTarget() instanceof EntityPlayer))
				attackEntityWithRangedAttack(getAttackTarget(), 0.0F);
			
			if (rand.nextInt(100) == 0)
			{
				for (int l = 0; l < 20; l++)
				{
					int i = MathHelper.floor(posX);
					int j = MathHelper.floor(posY);
					int k = MathHelper.floor(posZ);
					
					Maths.updateRandom(rand);
					int i1 = (int) (i + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 0.4F);
					int j1 = (int) (j + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 0.4F);
					int k1 = (int) (k + Maths.random(-1.0F, 1.0F) * getSizeMultiplier() * 0.4F);
					Maths.updateRandom();
					
					EntityLightningBolt entitylightning = new EntityLightningBolt(world, i1, j1, k1, false);
					
					if (world.getTopSolidOrLiquidBlock(new BlockPos(i1, j1 - 1, k1)).getY() > 0 && world.checkNoEntityCollision(entitylightning.getEntityBoundingBox()) && world.getCollisionBoxes(entitylightning, entitylightning.getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(entitylightning.getEntityBoundingBox()))
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
			if (entity != null)
				getLookHelper().setLookPosition(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, 10.0F, 10.0F);
			for (int j = 0; j < 2; ++j)
		       {
		           if (entity != null)
		           {   
		        	   double d11 = this.getHeadPosX(j + 1);
		                double d12 = this.getHeadPosY(j + 1);
		                double d13 = this.getHeadPosZ(j + 1);
		                double d6 = entity.posX - d11;
		                double d7 = entity.posY + (double)entity.getEyeHeight() - d12;
		                double d8 = entity.posZ - d13;
		                double d9 = (double)MathHelper.sqrt(d6 * d6 + d8 * d8);
		                float f = (float)(MathHelper.atan2(d8, d6) * (180D / Math.PI)) - 90.0F;
		                float f1 = (float)(-(MathHelper.atan2(d7, d9) * (180D / Math.PI)));
		                headRotations[j] = this.rotlerp(headRotations[j], f1, 10.0F);
		                headPitches[j] = this.rotlerp(headPitches[j], f, 10.0F);
		                
		            }
		            else
		            {
		            	headRotations[j] = this.rotlerp(headRotations[j], 0.0F, 10.0F);
		                headPitches[j] = this.rotlerp(this.headPitches[j], this.renderYawOffset, 10.0F);
		            }
		        }
	}
	
	public void onQuote(EntityPlayer player, int index, int subIndex)
	{
		switch (index)
		{
			case 0:
				if (world.provider.getDimension() != TheTitans.DIMENSION_VOID_ID && quoteIndex < 2)
					quoteIndex = 2;
				
				switch(quoteIndex)
				{
					case 1: 
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.spawn.b", 10, player.getName());
						break;
					case 2: 
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.spawn.c", 5, player.getName());
						quoteIndex = 3;
						break;
					case 3: 
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.spawn.d", 10, player.getName());
						break;
					default:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.spawn.a", 5, player.getName());
						quoteIndex = 1;
				}
				
				ticksTalked = Maths.random(200, 1200);
				break;
			case 1:
				if (ticksTalked > 0L) return;
				TranslateUtil.sendChatMult(player, "dialog.witherzilla.spawn.fail", 5, player.getName());
				ticksTalked = Maths.random(100, 200);
				break;
			case 2:
				if (ticksTalked > 0L) return;
				switch(subIndex)
				{
				
					case 1:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.hit.player.weak", 10, player.getName(), previousAttackingEntity == null ? "player" : previousAttackingEntity.getName());
						break;
					case 2:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.hit.player", 10, player.getName(), previousAttackingEntity == null ? "player" : previousAttackingEntity.getName());
						break;
					case 3:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.hit.player.strong", 10, player.getName(), previousAttackingEntity == null ? "player" : previousAttackingEntity.getName());
						break;
					default:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.hit.bruh", 10, player.getName(), previousAttackingEntity == null ? "player" : previousAttackingEntity.getName());
				}
				ticksTalked = Maths.random(200, 500);
				break;
			case 3:
				if (ticksTalked > 0L) return;
				switch(subIndex)
				{
					case 1:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.hit.weak", 10, player.getName(), previousAttackingEntity == null ? "mob" : previousAttackingEntity.getName());
						break;
					case 2:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.hit", 10, player.getName(), previousAttackingEntity == null ? "mob" : previousAttackingEntity.getName());
						break;
					case 3:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.hit.strong", 10, player.getName(), previousAttackingEntity == null ? "mob" : previousAttackingEntity.getName());
						break;
					default:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.hit.bruh", 10, player.getName(), previousAttackingEntity == null ? "mob" : previousAttackingEntity.getName());
				}
				ticksTalked = Maths.random(200, 500);
				break;
			case 4:
				if (ticksTalked > 0L) return;
				switch(subIndex)
				{
					case 1:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.living.b", 20, player.getName());
						break;
					case 2:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.living.c", 20, player.getName());
						break;
					case 3:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.living.d", 20, player.getName());
						break;
					case 4:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.living.e", 20, player.getName());
						break;
					default:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.living.a", 20, player.getName());
				}
				ticksTalked = Maths.random(400, 1400);
				break;
			case 10:
				switch(subIndex)
				{
					case 1:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.death.b", 20, player.getName());
						break;
					case 2:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.death.c", 20, player.getName());
						break;
					default:
						TranslateUtil.sendChatMult(player, "dialog.witherzilla.death.a", 20, player.getName());
				}
				break;
		}
	}
	
	protected void updateAITasks()
	{
		super.updateAITasks();
		Entity target = getTarget();
		Entity attackTarget = getAttackTarget();
		
		if (attackTarget != null)
		{
			if (attackTarget.isEntityAlive())
			{
				if (target == null)
					setTarget(attackTarget);
			}
			else
			{
				setAttackTarget(null);
				setTarget(null);
			}
		}	
		else if (target != null)
			setTarget(null);
			
	}

	public void onKillCommand()
	{
		TranslateUtil.sendChatAllMult("dialog.witherzilla.kill.fail.command", 4);
	}
	
	public void setDead()
	{
		if (isServerWorld())
		{
			if (deathTicks > 0)
				if (world.provider.getDimension() == TheTitans.DIMENSION_VOID_ID)
				{
					World world = this.world.getMinecraftServer().getWorld(0);
					EntityWitherzilla entity = new EntityWitherzilla(world);
					entity.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
					world.spawnEntity(entity);
				}
		}
		super.setDead();
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

	protected Entity getTarget()
	{
		int id = dataManager.get(ATTACK_TARGET).intValue();
		
		
		return id > 0 ? world.getEntityByID(id) : null;
	}
	
	protected void setTarget(Entity entity)
	{
		if (entity == null)
			dataManager.set(ATTACK_TARGET, Integer.valueOf(0));
		else
			dataManager.set(ATTACK_TARGET, Integer.valueOf(entity.getEntityId()));
	}
	
	private float rotlerp(float source, float target, float delta)
    {
        float f = MathHelper.wrapDegrees(target - source);

        if (f > delta)
        {
            f = delta;
        }

        if (f < -delta)
        {
            f = -delta;
        }

        return source + f;
    }
	
	public void doLightningAttackTo(Entity entity)
	{
		if (entity != null)
		{
			if (entity instanceof EntityPlayer)
			{
				if (rand.nextInt(1000) == 0)
				{
					attackChoosenEntity(entity, ((EntityPlayer)entity).getHealth() * 0.5F, 5, false);
					entity.setLocationAndAngles(entity.posX, posY + getEyeHeight(), entity.posZ, rotationYawHead + 180, 0F);
				}

				if (((EntityPlayer)entity).inventory.hasItemStack(new ItemStack(TItems.optimaAxe)))
				{
					((EntityPlayer)entity).motionX = 0D;
					((EntityPlayer)entity).motionY = 0D;
					((EntityPlayer)entity).motionZ = 0D;
					return;
				}
			}
			else if (entity.height >= 6.0F && !(entity instanceof EntityTitan))
			{
				((EntityLivingBase)entity).setHealth(0.0F);
				attackChoosenEntity(entity, Float.MAX_VALUE, 0, true);
				attackChoosenEntity(entity, Float.MAX_VALUE, 0, false);
			}
			
			if (!(entity instanceof EntityTitan))
				entity.motionY += 0.5D;
			
			world.spawnEntity(new EntityUrLightning(world, entity.posX, entity.posY, entity.posZ, false));
			shakeNearbyPlayerCameras(10D);
		}
	}
	
	private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
	{
		if (((p_82216_2_ instanceof EntityTitan)) || (p_82216_2_.height >= 6.0F))
		{
			double d0 = getDistance(p_82216_2_);
			if (d0 < width)
			{
				attackEntityAsMob(p_82216_2_);
				if (rand.nextInt(20) == 0)
					doLightningAttackTo(p_82216_2_);
			}
		}

		else
		{
			launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (rand.nextFloat() < 0.001F));
			if (rand.nextInt(200) == 0)
			p_82216_2_.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 100F);
			if (rand.nextInt(200) == 0)
			doLightningAttackTo(p_82216_2_);
			p_82216_2_.hurtResistantTime = 0;
		}

		launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (rand.nextFloat() < 0.001F));
	}

	private void launchWitherSkullToCoords(int headIndex, double targetPosX, double targetPosY, double targetPosZ, boolean isInvunerable)
	{
		double d3 = getHeadPosX(headIndex);
		double d4 = getHeadPosY(headIndex);
		double d5 = getHeadPosZ(headIndex);
		double d6 = targetPosX - d3;
		double d7 = targetPosY - d4;
		double d8 = targetPosZ - d5;
		EntityWitherSkull entitywitherskull = new EntityWitherSkull(world, this, d6, d7, d8);
		if (isInvunerable)
			entitywitherskull.setInvulnerable(true);
		entitywitherskull.posY = d4;
		entitywitherskull.posX = d3;
		entitywitherskull.posZ = d5;
		world.playEvent((EntityPlayer)null, 1024, new BlockPos(this), 0);
	}
	
	private void createBeaconPortal(int p_70975_1_, int p_70975_2_)
	{
		byte b0 = 64;
		byte b1 = 4;
		for (int k = b0 - 1; k <= b0 + 32; k++)
		for (int l = p_70975_1_ - b1; l <= p_70975_1_ + b1; l++)
		for (int i1 = p_70975_2_ - b1; i1 <= p_70975_2_ + b1; i1++)
		{
			double d0 = l - p_70975_1_;
			double d1 = i1 - p_70975_2_;
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

		world.setBlockState(new BlockPos(p_70975_1_, b0 + 0, p_70975_2_), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_, b0 + 1, p_70975_2_), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_, b0 + 2, p_70975_2_), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_ - 1, b0 + 2, p_70975_2_), Blocks.TORCH.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_ + 1, b0 + 2, p_70975_2_), Blocks.TORCH.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_, b0 + 2, p_70975_2_ - 1), Blocks.TORCH.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_, b0 + 2, p_70975_2_ + 1), Blocks.TORCH.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_, b0 + 3, p_70975_2_), Blocks.BEDROCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_, b0 + 4, p_70975_2_), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_ + 1, b0 + 4, p_70975_2_ + 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_ + 1, b0 + 4, p_70975_2_), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_ + 1, b0 + 4, p_70975_2_ - 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_ - 1, b0 + 4, p_70975_2_ + 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_ - 1, b0 + 4, p_70975_2_), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_ - 1, b0 + 4, p_70975_2_ - 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_, b0 + 4, p_70975_2_ + 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_, b0 + 4, p_70975_2_ - 1), Blocks.DIAMOND_BLOCK.getDefaultState());
		world.setBlockState(new BlockPos(p_70975_1_, b0 + 5, p_70975_2_), Blocks.BEACON.getDefaultState());
	}

	private double getHeadPosX(int p_82214_1_)
	{
		return posX;
	}

	private double getHeadPosY(int p_82208_1_)
	{
		return posY + 4D;
	}

	private double getHeadPosZ(int p_82213_1_)
	{
		return posZ;
	}
	
	@SideOnly(Side.CLIENT)
	public float getHeadPitch(int p_82207_1_)
	{
		return headPitches[p_82207_1_];
	}
	
	@SideOnly(Side.CLIENT)
	public float getHeadRotation(int p_82210_1_)
	{
		return headRotations[p_82210_1_];
	}
	
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		if (((target instanceof EntityTitan)) || (target.height >= 6.0F))
		{
			double d0 = getDistance(target);
			if (d0 < target.width +this.width && this.ticksExisted % 20 == 0)
				attackEntityAsMob(target);
		}

		else
		launchWitherSkullToEntity(0, target);
	}

	protected float getSoundVolume()
	{
		return 1000.0F;
	}

	@Override
    protected SoundEvent getAmbientSound()
    {
        return TSounds.get("witherzilla.living");
    }
	

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return TSounds.get("witherzilla.grunt");
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return TSounds.get("witherzilla.death");
	}

	protected void onNewDeathUpdate()
	{
		super.onNewDeathUpdate();
		
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
					ItemStack item = new ItemStack(TItems.ultimaBlade, 1, 1);
					player.entityDropItem(item, 0.0F);
					playLivingSound();
					
					onQuote(player, 10, 0);
				}
			}
		}

		if ((deathTicks >= 400))
			setDead();
	}

	public boolean isInOmegaForm()
	{
		return true;
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {}

	@Override
	public EnumMobTier getTier()
	{
		return EnumMobTier.ALGOD;
	}

	@Override
	public boolean canColorHealth() {return false;}
	
	@Override
	public boolean canColorStamina() {return false;}
	
	@Override
	public int getNameBarStart() {return 26;}
	
	@Override
	public int getHealthNameStart() {return 17;}
	
	@Override
	public int getStaminaBarLength() {return 256;}
	
	@Override
	public int getStaminaBarStart() {return 0;}
	
	@Override
	public int getHealthBarLength() {return 256;}
	
	@Override
	public ResourceLocation getBarTexture()
	{
		return new ResourceLocation(TheTitans.MODID, "textures/gui/titanbars/witherzilla_new.png");
	}

	public EnumGender getGender() 
	{
		return EnumGender.MALE;
	}

	public void setVariant(int type) {
		
	}

	public int getVariant() {
		return 0;
	}

	public double getMobHealth() {
		double hp = 300D * getSizeMultiplier() * this.getTier().getMultiplier();
		
		return hp;
	}

	public double getMobAttack() {
		double hp = 24D * getSizeMultiplier() * this.getTier().getMultiplier();
		
		return hp;
	}

	public double getMobSpeed() {
		return 1;
	}

	public boolean isMusicDead() {
		return false;
	}

	public int getMusicPriority() {
		return 10000;
	}

	public SoundEvent getMusic() {
		return TSounds.get("witherzilla.theme");
	}
}
