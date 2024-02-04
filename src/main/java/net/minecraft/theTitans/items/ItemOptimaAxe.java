package net.minecraft.theTitans.items;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockOre;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.titan.EntityGammaLightning;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.entity.titan.EntityTitanPart;
import net.minecraft.entity.titan.EntityTitanSpirit;
import net.minecraft.entity.titan.EntityUrLightning;
import net.minecraft.entity.titan.EntityWitherzilla;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.theTitans.ClientProxy;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.EntityImmortalItem;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.theTitans.TitanItems;
import net.minecraft.theTitans.TitansAchievments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.util.EnumHelper;
public class ItemOptimaAxe
extends ItemTitanSword
{
	public static ToolMaterial enumToolMaterialOptima = EnumHelper.addToolMaterial("Optima", Integer.MAX_VALUE, 2, 9999999F, Float.MAX_VALUE / 2.0F, 0);
	private int soundType;
	public ItemOptimaAxe()
	{
		super("", enumToolMaterialOptima);
		soundType = 0;
		setTextureName(TheTitans.getTextures("optima_axe"));
		setUnlocalizedName("optima_axe");
	}

	public boolean isItemTool(ItemStack p_77616_1_)
	{
		return true;
	}

	public boolean func_150897_b(Block p_150897_1_)
	{
		return true;
	}

	public boolean isDamageable()
	{
		return false;
	}

	public boolean hasCustomEntity(ItemStack stack)
	{
		return true;
	}

	public Entity createEntity(World world, Entity location, ItemStack itemstack)
	{
		return new EntityImmortalItem(world, location, itemstack);
	}

	@SuppressWarnings(
	{
		 "rawtypes", "unchecked" 
	}
	)
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_)
	{
		super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
		if (p_77624_1_.getItemDamage() > 0)

		{
			p_77624_3_.add("\u00A73\u00A7lThe ultimate weapon.");
			p_77624_3_.add("\u00A73\u00A7lCuts through all defences, even hacks.");
			p_77624_3_.add("\u00A73\u00A7lNormally owned by Frotheru");
		}

		else
		{
			p_77624_3_.add("\u00A7l\u00A7k\u00A73\u00A7lThe ultimate weapon.");
			p_77624_3_.add("\u00A7l\u00A7k\u00A73\u00A7lCuts through all defences, even hacks.");
			p_77624_3_.add("\u00A7l\u00A7k\u00A73\u00A7lNormally owned by Frotheru");
		}
	}

	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
	{
		return true;
	}

	private void giveAdvice(EntityPlayer player)
	{
		List<?> list1 = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, player.boundingBox.expand(16.0D, 16.0D, 16.0D));
		if ((list1 != null) && (!list1.isEmpty()))
		{
			for (int i1 = 0; i1 < list1.size(); i1++)
			{
				Entity entity = (Entity)list1.get(i1);
				if (((entity instanceof IMob || entity instanceof EntityPlayer)))
				{
					if (player.canEntityBeSeen(entity))
					{
						player.addChatMessage(new ChatComponentText("Optima Axe: \u00A7l\u00A7oThe " + ((EntityLivingBase)entity).getCommandSenderName() +" is " + player.getDistanceToEntity(entity) + "\u00A7l\u00A7o blocks away from you."));
					}

					else
					{
						player.addChatMessage(new ChatComponentText("Optima Axe: \u00A7l\u00A7oAn unseen mob is located out of your \u00A7l\u00A7osight " + player.getDistanceToEntity(entity) + " blocks away from you."));
						player.addChatMessage(new ChatComponentText("Optima Axe: \u00A7l\u00A7oThe mob's health: " + ((EntityLivingBase)entity).getHealth() + "/" + ((EntityLivingBase)entity).getMaxHealth()));
						player.addChatMessage(new ChatComponentText("Optima Axe: \u00A7l\u00A7oThe mob's name: " + ((EntityLivingBase)entity).getCommandSenderName()));
						player.playSound("mob.wither.hurt", 10.0F, (player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.2F + 0.5F);
					}
				}
			}
		}
	}

	private void offerAdvice(EntityPlayer player)
	{
		int y = MathHelper.floor_double(player.posY);
		int x = MathHelper.floor_double(player.posX);
		int z = MathHelper.floor_double(player.posZ);
		for (int l1 = -8; l1 <= 8; l1++)
		{
			for (int i2 = -8; i2 <= 8; i2++)
			{
				for (int j = -8; j <= 8; j++)
				{
					int j2 = x + l1;
					int k = y + j;
					int l = z + i2;
					Block block = player.worldObj.getBlock(j2, k, l);
					if ((block instanceof BlockOre))
					{
						player.addChatMessage(new ChatComponentText("Optima Axe: \u00A7l\u00A7oI sense a " + block.getLocalizedName() + "\u00A7l\u00A7o within atleast " + player.getDistance(j2, k, l) + " blocks of you."));
						player.playSound("mob.wither.hurt", 10.0F, 0.6F);
					}
				}
			}
		}
	}

	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		if (p_77659_3_.worldObj.isRemote)
		{
			if (p_77659_3_.isSneaking())
			giveAdvice(p_77659_3_);
			else
			offerAdvice(p_77659_3_);
		}

		if (p_77659_3_.inventory.hasItem(TitanItems.ultimaBlade))
		{
			if (!p_77659_2_.isRemote)
			{
				WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
				WorldInfo worldinfo = worldserver.getWorldInfo();
				worldinfo.setRainTime(9999999);
				worldinfo.setThunderTime(1000000);
				worldinfo.setRaining(true);
				worldinfo.setThundering(true);
			}

			List<?> list = p_77659_3_.worldObj.getEntitiesWithinAABBExcludingEntity(p_77659_3_, p_77659_3_.boundingBox.expand(200.0D, 100.0D, 200.0D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if ((entity != null) && entity.isEntityAlive() && entity instanceof EntityLivingBase && !(entity instanceof EntityTitan) && entity != p_77659_3_)
					{
						entity.copyLocationAndAnglesFrom(p_77659_3_);
						entity.attackEntityFrom(DamageSourceExtra.lightningBolt, 49F);
						p_77659_3_.worldObj.addWeatherEffect(new EntityGammaLightning(p_77659_3_.worldObj, entity.posX, entity.posY + entity.height, entity.posZ, p_77659_3_.getRNG().nextFloat(), p_77659_3_.getRNG().nextFloat(), p_77659_3_.getRNG().nextFloat()));
						entity.attackEntityFrom(DamageSource.causePlayerDamage(p_77659_3_).setDamageBypassesArmor().setDamageIsAbsolute(), 49F);
						if (entity instanceof EntityLivingBase && !(entity instanceof EntityTitan) && (entity.height >= 6.0F || entity.isEntityInvulnerable()))
						{
							((EntityLivingBase)entity).setDead();
							((EntityLivingBase)entity).getDataWatcher().updateObject(6, Float.valueOf(MathHelper.clamp_float(0.0F, 0.0F, ((EntityLivingBase)entity).getMaxHealth())));
							((EntityLivingBase)entity).attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
						}
					}
				}
			}
		}

		return super.onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		attacker.worldObj.getGameRules().setOrCreateGameRule("keepInventory", "true");
		float extradamage = EnchantmentHelper.func_152377_a(attacker.getHeldItem(), target.getCreatureAttribute());
		int knockbackAmount = EnchantmentHelper.getKnockbackModifier(attacker, target);
		int bonusdamage = 1;
		if (target != null)
		{
			target.setFire(Integer.MAX_VALUE);
			if (target.worldObj.isRemote)
			target.addPotionEffect(new PotionEffect(ClientProxy.electricJudgment.id, Integer.MAX_VALUE, 19));
			target.hurtResistantTime = 0;
			if (((target instanceof EntityTitan)) && (((EntityTitan)target).canBeHurtByPlayer()))
			{
				if (target instanceof EntityWitherzilla)
				bonusdamage = 40;
				if (attacker instanceof EntityPlayer)
				((EntityTitan)target).attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)attacker), func_150931_i());
				((EntityTitan)target).hurt((10000.0F * bonusdamage) - extradamage);
				((EntityTitan)target).setAttackTarget(attacker);
				((EntityTitan)target).addTitanVelocity(-MathHelper.sin(attacker.rotationYaw * 3.1415927F / 180.0F) * 2F + knockbackAmount, 0.5D + knockbackAmount, MathHelper.cos(attacker.rotationYaw * 3.1415927F / 180.0F) * 2F + knockbackAmount);
			}

			else if (!(target instanceof EntityTitan))
			{
				if ((target.height == 50F && target.width == 15F) || EntityTitan.isOreSpawnBossToExempt(target))
				{
					try
					{
						ReflectionHelper.findField(target.getClass(), new String[] 
						{
							 "hurt_timer" 
						}
						).setInt(target, 0);
					}

					catch (Exception e)
					{
						target.hurtResistantTime = 0;
					}

					double originalHealth = target.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue();
					target.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(0D);
					target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker).setDamageBypassesArmor().setDamageIsAbsolute(), 40.0F);
					if (target.worldObj.isRemote)
					target.addPotionEffect(new PotionEffect(ClientProxy.death.id, Integer.MAX_VALUE, 19));
					++target.deathTime;
					target.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(originalHealth);
				}

				target.getDataWatcher().updateObject(6, Float.valueOf(MathHelper.clamp_float(target.getHealth() - this.func_150931_i() - extradamage, 0.0F, target.getMaxHealth())));
				target.motionX = -MathHelper.sin(attacker.rotationYaw * 3.1415927F / 180.0F) * 6F + knockbackAmount;
				target.motionY = 6D + knockbackAmount;
				target.motionZ = MathHelper.cos(attacker.rotationYaw * 3.1415927F / 180.0F) * 6F + knockbackAmount;
				if (!target.isEntityAlive())
				{
					target.onDeath(DamageSource.magic);
					target.worldObj.setEntityState(target, (byte)3);
				}
			}
		}

		return super.hitEntity(stack, target, attacker);
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return TheTitans.godly;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
	{
		int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
		if (sharpness <= 0)
		{
			stack.addEnchantment(Enchantment.sharpness, 100);
		}

		int smite = EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, stack);
		if (smite <= 0)
		{
			stack.addEnchantment(Enchantment.smite, 100);
		}

		int bug = EnchantmentHelper.getEnchantmentLevel(Enchantment.baneOfArthropods.effectId, stack);
		if (bug <= 0)
		{
			stack.addEnchantment(Enchantment.baneOfArthropods, 100);
		}

		int fire = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
		if (fire <= 0)
		{
			stack.addEnchantment(Enchantment.fireAspect, 100);
		}

		int titanslaying = EnchantmentHelper.getEnchantmentLevel(Enchantment.looting.effectId, stack);
		if (titanslaying <= 0)
		{
			stack.addEnchantment(Enchantment.looting, 100);
		}
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if (!stack.hasTagCompound())
		stack.setTagCompound(new NBTTagCompound());
		else
		stack.getTagCompound().setBoolean("Unbreakable", true);
		entityIn.extinguish();
		if ((entityIn instanceof EntityPlayer) && !worldIn.isRemote)
		{
			((EntityPlayer)entityIn).triggerAchievement(TitansAchievments.optimaAxe);
			if (((EntityPlayer)entityIn).inventory.hasItem(TitanItems.ultimaBlade))
			{
				if (((EntityPlayer)entityIn).getRNG().nextInt(5) == 0)
				{
					for (int l = 0; l < 5; l++)
					{
						int i = MathHelper.floor_double(((EntityPlayer)entityIn).posX + (((EntityPlayer)entityIn).getRNG().nextInt() * 200 - 100));
						int j = MathHelper.floor_double(((EntityPlayer)entityIn).posY + (((EntityPlayer)entityIn).getRNG().nextInt() * 100 - 50));
						int k = MathHelper.floor_double(((EntityPlayer)entityIn).posZ + (((EntityPlayer)entityIn).getRNG().nextInt() * 200 - 100));
						EntityUrLightning entitylightning = new EntityUrLightning(((EntityPlayer)entityIn).worldObj, i, j, k);
						if ((World.doesBlockHaveSolidTopSurface(((EntityPlayer)entityIn).worldObj, i, j - 1, k)) && (((EntityPlayer)entityIn).worldObj.checkNoEntityCollision(entitylightning.boundingBox)) && (((EntityPlayer)entityIn).worldObj.getCollidingBoundingBoxes(entitylightning, entitylightning.boundingBox).isEmpty()) && (!((EntityPlayer)entityIn).worldObj.isAnyLiquid(entitylightning.boundingBox)))
						{
							((EntityPlayer)entityIn).worldObj.addWeatherEffect(entitylightning);
						}
					}
				}

				List<?> list = ((EntityPlayer)entityIn).worldObj.getEntitiesWithinAABBExcludingEntity(((EntityPlayer)entityIn), ((EntityPlayer)entityIn).boundingBox.expand(200.0D, 100.0D, 200.0D));
				if ((list != null) && (!list.isEmpty()))
				{
					for (int i1 = 0; i1 < list.size(); i1++)
					{
						Entity entity = (Entity)list.get(i1);
						if ((entity != null) && entity.isEntityAlive() && entity instanceof EntityLivingBase && ((EntityPlayer)entityIn).getRNG().nextInt(100) == 0 && entity != entityIn)
						{
							entity.addVelocity(-MathHelper.sin(entityIn.rotationYaw * 3.1415927F / 180.0F) * 1.25F, 1D, MathHelper.cos(entityIn.rotationYaw * 3.1415927F / 180.0F) * 1.25F);
							entity.attackEntityFrom(DamageSourceExtra.lightningBolt, 49F);
							((EntityPlayer)entityIn).worldObj.addWeatherEffect(new EntityGammaLightning(((EntityPlayer)entityIn).worldObj, entity.posX, entity.posY + entity.height, entity.posZ, ((EntityPlayer)entityIn).getRNG().nextFloat(), ((EntityPlayer)entityIn).getRNG().nextFloat(), ((EntityPlayer)entityIn).getRNG().nextFloat()));
							entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entityIn).setDamageBypassesArmor().setDamageIsAbsolute(), 49F);
						}
					}
				}
			}
		}

		if (entityIn.posY < -45D)
		entityIn.motionY += 10F;
		stack.setStackDisplayName("\u00A7lThe Optima Axe");
		for (int i = 0; i < 3; i++)
		{
			entityIn.worldObj.spawnParticle("portal", ((EntityLivingBase)entityIn).posX + (((EntityLivingBase)entityIn).getRNG().nextDouble() - 0.5D) * ((EntityLivingBase)entityIn).width, ((EntityLivingBase)entityIn).posY + ((EntityLivingBase)entityIn).getRNG().nextDouble() * ((EntityLivingBase)entityIn).height, ((EntityLivingBase)entityIn).posZ + (((EntityLivingBase)entityIn).getRNG().nextDouble() - 0.5D) * ((EntityLivingBase)entityIn).width, (((EntityLivingBase)entityIn).getRNG().nextDouble() - 0.5D) * 2.0D, 1.0D, (((EntityLivingBase)entityIn).getRNG().nextDouble() - 0.5D) * 2.0D);
			entityIn.worldObj.spawnParticle("largesmoke", ((EntityLivingBase)entityIn).posX + (((EntityLivingBase)entityIn).getRNG().nextDouble() - 0.5D) * ((EntityLivingBase)entityIn).width, ((EntityLivingBase)entityIn).posY + ((EntityLivingBase)entityIn).getRNG().nextDouble() * ((EntityLivingBase)entityIn).height, ((EntityLivingBase)entityIn).posZ + (((EntityLivingBase)entityIn).getRNG().nextDouble() - 0.5D) * ((EntityLivingBase)entityIn).width, (((EntityLivingBase)entityIn).getRNG().nextDouble() - 0.5D) * 2.0D, 1.0D, (((EntityLivingBase)entityIn).getRNG().nextDouble() - 0.5D) * 2.0D);
		}

		if (((entityIn instanceof EntityPlayer)) && (entityIn != null))
		{
			if (((EntityPlayer)entityIn).isBlocking())
			{
				List<?> list1 = entityIn.worldObj.getEntitiesWithinAABBExcludingEntity(entityIn, entityIn.boundingBox.expand(16D, 16D, 16D));
				if ((list1 != null) && (!list1.isEmpty()))
				{
					for (int i11 = 0; i11 < list1.size(); i11++)
					{
						Entity entity1 = (Entity)list1.get(i11);
						if (entity1 != null && entityIn.getDistanceSqToEntity(entity1) < 100D)
						{
							if (entity1 instanceof EntityArrow || entity1 instanceof EntityFireball || entity1 instanceof EntityThrowable || entity1 instanceof EntityTNTPrimed)
							{
								entity1.worldObj.newExplosion(entityIn, entity1.posX, entity1.posY, entity1.posZ, entity1.width, false, false);
								entity1.setDead();
							}
						}
					}
				}
			}

			((EntityPlayer)entityIn).heal(((EntityPlayer)entityIn).getMaxHealth());
			if (worldIn.isRemote && ((((EntityPlayer)entityIn).getActivePotionEffect(Potion.field_76444_x) == null) || (((EntityPlayer)entityIn).getActivePotionEffect(Potion.field_76444_x).getDuration() <= 1)))
			{
				((EntityPlayer)entityIn).addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 800, 299, false));
			}
		}

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (!entityLiving.worldObj.isRemote && entityLiving instanceof EntityPlayer && ((EntityPlayer)entityLiving).isSneaking())
		{
			List<?> list = entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(entityLiving, entityLiving.boundingBox.expand(64D, 64D, 64D));
			if ((list != null) && (!list.isEmpty()))
			{
				for (int i1 = 0; i1 < list.size(); i1++)
				{
					Entity entity = (Entity)list.get(i1);
					if ((entity != null) && !(entity instanceof EntityTitan))
					{
						entity.playSound("thetitans:titanpunch", 2F, 0.5F + (entityLiving.getRNG().nextFloat() * 0.25F));
						entity.playSound("random.fizz", 2F, 2F);
						entity.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
						if (entity instanceof EntityLivingBase)
						this.hitEntity(stack, (EntityLivingBase)entity, entityLiving);
						else if (!(entity instanceof EntityTitanSpirit) && !(entity instanceof EntityTitanPart))
						entity.setDead();
					}
				}
			}
		}

		if (!entityLiving.worldObj.isRemote && entityLiving instanceof EntityPlayer)
		{
			((EntityPlayer)entityLiving).setItemInUse(stack, this.getMaxItemUseDuration(stack));
			Vec3 vec3 = ((EntityPlayer)entityLiving).getLook(1.0F);
			double dx = vec3.xCoord * 16;
			double dy = entityLiving.getEyeHeight() + vec3.yCoord * 16;
			double dz = vec3.zCoord * 16;
			for (int k1 = -4; k1 <= 4; ++k1)
			{
				for (int l1 = -2; l1 <= 2; ++l1)
				{
					for (int i2 = -4; i2 <= 4; ++i2)
					{
						int y = MathHelper.floor_double(entityLiving.posY + dy + l1);
						int x = MathHelper.floor_double(entityLiving.posX + dx + k1);
						int z = MathHelper.floor_double(entityLiving.posZ + dz + i2);
						if (!entityLiving.worldObj.isRemote && !entityLiving.worldObj.isAirBlock(x, y, z))
						{
							Block block = entityLiving.worldObj.getBlock(x, y, z);
							if ((block instanceof IGrowable && !(block instanceof BlockGrass)) || block.getMaterial() == Material.circuits || block instanceof BlockOre)
							{
								entityLiving.worldObj.func_147480_a(x, y, z, true);
							}

							else
							{
								int l = entityLiving.worldObj.getBlockMetadata(x, y, z);
								entityLiving.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (l << 12));
								EntityItem entityitem = new EntityItem(entityLiving.worldObj, x, y, z, new ItemStack (Item.getItemFromBlock(block), 1, l));
								entityLiving.worldObj.spawnEntityInWorld(entityitem);
								entityLiving.worldObj.setBlockToAir(x, y, z);
								entityitem.addVelocity(-MathHelper.sin(entityLiving.rotationYaw * 3.1415927F / 180.0F) * 1.0F, 0.75D, MathHelper.cos(entityLiving.rotationYaw * 3.1415927F / 180.0F) * 1.0F);
							}
						}
					}
				}
			}
		}

		if (!entityLiving.worldObj.isRemote && entityLiving instanceof EntityPlayer)
		{
			((EntityPlayer)entityLiving).setItemInUse(stack, this.getMaxItemUseDuration(stack));
			for (int i1 = 0; i1 < 12; i1++)
			{
				Vec3 vec3 = ((EntityPlayer)entityLiving).getLook(1.0F);
				double dx = vec3.xCoord * i1;
				double dy = entityLiving.getEyeHeight() + vec3.yCoord * i1;
				double dz = vec3.zCoord * i1;
				int y = MathHelper.floor_double(entityLiving.posY + dy);
				int x = MathHelper.floor_double(entityLiving.posX + dx);
				int z = MathHelper.floor_double(entityLiving.posZ + dz);
				if (!entityLiving.worldObj.isAirBlock(x, y, z))
				entityLiving.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1012, x, y, z, 0);
				if (!entityLiving.worldObj.isRemote && !entityLiving.worldObj.isAirBlock(x, y, z))
				{
					Block block = entityLiving.worldObj.getBlock(x, y, z);
					if ((block instanceof IGrowable && !(block instanceof BlockGrass)) || block.getMaterial() == Material.circuits || block instanceof BlockOre)
					{
						entityLiving.worldObj.func_147480_a(x, y, z, true);
					}

					else
					{
						int l = entityLiving.worldObj.getBlockMetadata(x, y, z);
						entityLiving.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (l << 12));
						EntityItem entityitem = new EntityItem(entityLiving.worldObj, x, y, z, new ItemStack (Item.getItemFromBlock(block), 1, l));
						entityLiving.worldObj.spawnEntityInWorld(entityitem);
						entityLiving.worldObj.setBlockToAir(x, y, z);
						entityitem.addVelocity(-MathHelper.sin(entityLiving.rotationYaw * 3.1415927F / 180.0F) * 1.0F, 0.75D, MathHelper.cos(entityLiving.rotationYaw * 3.1415927F / 180.0F) * 1.0F);
					}
				}
			}
		}

		entityLiving.playSound("thetitans:titanSwing", 10.0F, 1.0F);
		Vec3 vec3 = entityLiving.getLook(1.0F);
		double dx = vec3.xCoord * 12;
		double dy = entityLiving.getEyeHeight() + vec3.yCoord * 12;
		double dz = vec3.zCoord * 12;
		List<?> list = entityLiving.worldObj.getEntitiesWithinAABBExcludingEntity(entityLiving, entityLiving.boundingBox.expand(12D, 8D, 12D).offset(dx, dy, dz));
		if ((list != null) && (!list.isEmpty()))
		{
			soundType = 0;
			entityLiving.playSound("thetitans:titanpunch", 2F, 0.5F + (entityLiving.getRNG().nextFloat() * 0.25F));
			entityLiving.playSound("random.fizz", 2F, 2F);
			for (int i1 = 0; i1 < list.size(); i1++)
			{
				Entity entity = (Entity)list.get(i1);
				if ((entity != null) && !(entity instanceof EntityTitan))
				{
					entity.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
					if (entity instanceof EntityLivingBase)
					{
						this.hitEntity(stack, (EntityLivingBase)entity, entityLiving);
						if (soundType < 4)
						if (entity instanceof EntityTitan)
						{
							soundType ++;
							entity.playSound("thetitans:titanpunch", 10.0F, 1.0F);
						}

						else
						{
							soundType ++;
							entity.playSound("thetitans:slashFlesh", 10.0F, 1.0F);
						}
					}
				}
			}
		}

		return false;
	}

	protected double getRange()
	{
		return 24.0D;
	}

	protected double getDashSpeed()
	{
		return 10.0D;
	}

	public int getUseTime()
	{
		return 5;
	}

	public int getHurtTime()
	{
		return 0;
	}

	protected int getMaxUses()
	{
		return 10;
	}
}


