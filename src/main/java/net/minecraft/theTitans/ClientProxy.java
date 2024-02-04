package net.minecraft.theTitans;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.orespawnaddon.*;
import net.minecraft.entity.titan.*;
import net.minecraft.entity.titan.other.*;
import net.minecraft.entity.titanminion.*;
import net.minecraft.theTitans.models.*;
import net.minecraft.theTitans.render.*;
import net.minecraft.theTitans.render.items.*;
import net.minecraft.theTitans.render.minions.*;
import net.minecraft.theTitans.render.other.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
public class ClientProxy extends CommonProxy
{
	public static final ResourceLocation titanpotions = new ResourceLocation(TheTitans.MODID, "/textures/entities/effects/titanpotions.png".substring(1));
	public static TitanBossBarGui ingameGui;
	public void preInit(FMLPreInitializationEvent e)
	{
		TitanRenders.init();
		super.preInit(e);
	}

	//give @p potion 1 16398 {CustomPotionEffects:[{Id:28,Amplifier:1, Duration:1200}]}


	//give @p potion 1 16387 {CustomPotionEffects:[{Id:29,Amplifier:1,Duration:1200}]}


	//give @p potion 1 16395 {CustomPotionEffects:[{Id:30,Amplifier:1,Duration:1200}]}


	//give @p potion 1 16398 {CustomPotionEffects:[{Id:31,Amplifier:1,Duration:1200}]}


	public void init(FMLInitializationEvent e)
	{
		super.init(e);
	}

	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}

	public static void generateGiantSmoke(Entity theEntity, double posX, double posY, double posZ)
	{
		double motionX = theEntity.worldObj.rand.nextGaussian() * 0.1D;
		double motionY = theEntity.worldObj.rand.nextGaussian() * 0.1D;
		double motionZ = theEntity.worldObj.rand.nextGaussian() * 0.1D;
		EntityFX greenSmoke = new EntityGiantSmokeFX(theEntity.worldObj, posX, posY, posZ, motionX, motionY, motionZ);
		Minecraft.getMinecraft().effectRenderer.addEffect(greenSmoke);
	}

	public void registerItemRenderers()
	{
		MinecraftForgeClient.registerItemRenderer(TitanItems.ultimaBlade, new RenderUltimaBlade());
		MinecraftForgeClient.registerItemRenderer(TitanItems.optimaAxe, new RenderOptimaAxe());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggOmegafish, new RenderOmegafishSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggCaveSpiderTitan, new RenderCaveSpiderTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggSpiderTitan, new RenderSpiderTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggSlimeTitan, new RenderSlimeTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggMagmaCubeTitan, new RenderMagmaCubeTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggSpiderJockeyTitan, new RenderSpiderJockeyTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggSkeletonTitan, new RenderSkeletonTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggZombieTitan, new RenderZombieTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggCreeperTitan, new RenderCreeperTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggChargedCreeperTitan, new RenderChargedCreeperTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggZombiePigmanTitan, new RenderZombiePigmanTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggBlazeTitan, new RenderBlazeTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggWitherJockeyTitan, new RenderWitherJockeyTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggWitherSkeletonTitan, new RenderWitherSkeletonTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggSnowGolemTitan, new RenderSnowGolemTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggUltimaIronGolemTitan, new RenderUltimaIronGolemTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggGargoyleKing, new RenderGargoyleKingSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggGhastTitan, new RenderGhastTitanSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggEnderColossus, new RenderEnderColossusSpawnEgg());
		MinecraftForgeClient.registerItemRenderer(TitanItems.eggWitherzilla, new RenderWitherzillaSpawnEgg());
	}

	public void registerRenderThings()
	{
		ingameGui = new TitanBossBarGui(Minecraft.getMinecraft());
		MinecraftForge.EVENT_BUS.register(ingameGui);
		RenderingRegistry.registerEntityRenderingHandler(EntitySilverfishMinion.class, new RenderSilverfishMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityCaveSpiderMinion.class, new RenderCaveSpiderMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntitySpiderMinion.class, new RenderSpiderMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieMinion.class, new RenderZombieMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonMinion.class, new RenderSkeletonMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityCreeperMinion.class, new RenderCreeperMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityPigZombieMinion.class, new RenderPigZombieMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlazeMinion.class, new RenderBlazeMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityGhastMinion.class, new RenderGhastMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityEndermanMinion.class, new RenderEndermanMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityGhastGuard.class, new RenderGhastGuard());
		RenderingRegistry.registerEntityRenderingHandler(EntityGiantZombieBetter.class, new RenderGiantZombie(new ModelZombie(), 0.5F, 6.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityWitherMinion.class, new RenderWitherMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonMinion.class, new RenderDragonMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityWitherzillaMinion.class, new RenderWitherzillaMinion());
		RenderingRegistry.registerEntityRenderingHandler(EntityWitherTurret.class, new RenderWitherTurret());
		RenderingRegistry.registerEntityRenderingHandler(EntityWitherTurretGround.class, new RenderWitherTurretGround());
		RenderingRegistry.registerEntityRenderingHandler(EntityWitherTurretMortar.class, new RenderWitherTurretMortar());
		RenderingRegistry.registerEntityRenderingHandler(EntitySilverfishOther.class, new RenderSilverfishOther());
		RenderingRegistry.registerEntityRenderingHandler(EntityUndeadOther.class, new RenderUndeadOther());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilGolem.class, new RenderEvilGolem());
		RenderingRegistry.registerEntityRenderingHandler(EntityMagicUser.class, new RenderSpellcaster());
		RenderingRegistry.registerEntityRenderingHandler(EntityWraith.class, new RenderWraith());
		RenderingRegistry.registerEntityRenderingHandler(EntityGargoyle.class, new RenderGargoyle());
		RenderingRegistry.registerEntityRenderingHandler(EntityHarcadiumGolem.class, new RenderHarcadiumGolem());
		RenderingRegistry.registerEntityRenderingHandler(EntityVoidGolem.class, new RenderVoidGolem());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderColossusCrystal.class, new RenderEnderColossusCrystal());
		RenderingRegistry.registerEntityRenderingHandler(EntityHarcadiumArrow.class, new RenderHarcadiumArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonTitanGiantArrow.class, new RenderGiantArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityChaff.class, new RenderChaff());
		RenderingRegistry.registerEntityRenderingHandler(EntityXPBomb.class, new RenderXPBomb());
		RenderingRegistry.registerEntityRenderingHandler(EntityItemBomb.class, new RenderItemBomb());
		RenderingRegistry.registerEntityRenderingHandler(EntityTitanPart.class, new RenderTitanPart());
		RenderingRegistry.registerEntityRenderingHandler(EntityGammaLightning.class, new RenderGammaLightning());
		RenderingRegistry.registerEntityRenderingHandler(EntityProtoBall.class, new RenderProtoBall());
		RenderingRegistry.registerEntityRenderingHandler(EntityLightningBall.class, new RenderLightningBall(8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTitanFireball.class, new RenderTitanFireball());
		RenderingRegistry.registerEntityRenderingHandler(EntityGargoyleTitanFireball.class, new RenderGargoyleTitanFireball());
		RenderingRegistry.registerEntityRenderingHandler(EntityLavaSpit.class, new RenderLavaSpit());
		RenderingRegistry.registerEntityRenderingHandler(EntityTitanSpirit.class, new RenderInvisibleEntity());
		RenderingRegistry.registerEntityRenderingHandler(EntityWebShot.class, new RenderWebShot(4F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGrowthSerum.class, new RenderSnowball(TitanItems.growthSerum));
		RenderingRegistry.registerEntityRenderingHandler(EntitySnowGolemTitan.class, new RenderSnowGolemTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntitySlimeTitan.class, new RenderSlimeTitan(new ModelSlimeTitan(16), new ModelSlimeTitan(0), 0.25F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMagmaCubeTitan.class, new RenderMagmaCubeTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntitySilverfishTitan.class, new RenderOmegafish());
		RenderingRegistry.registerEntityRenderingHandler(EntityCaveSpiderTitan.class, new RenderCaveSpiderTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntitySpiderTitan.class, new RenderSpiderTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieTitan.class, new RenderZombieTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonTitan.class, new RenderSkeletonTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntityCreeperTitan.class, new RenderCreeperTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntityPigZombieTitan.class, new RenderZombiePigmanTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlazeTitan.class, new RenderBlazeTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntityGargoyleTitan.class, new RenderGargoyleTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntityGhastTitan.class, new RenderGhastTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderColossus.class, new RenderEnderColossus());
		RenderingRegistry.registerEntityRenderingHandler(EntityIronGolemTitan.class, new RenderUltimaIronGolemTitan());
		RenderingRegistry.registerEntityRenderingHandler(EntityWitherzilla.class, new RenderWitherzilla());
		if (Loader.isModLoaded("OreSpawn"))
		{
			RenderingRegistry.registerEntityRenderingHandler(EntityOverlordScorpion.class, new RenderOverlordScorpion());
			RenderingRegistry.registerEntityRenderingHandler(EntityMethuselahKraken.class, new RenderMethuselahKraken());
			RenderingRegistry.registerEntityRenderingHandler(EntityBurningMobzilla.class, new RenderBurningMobzilla());
		}
	}
}


