package net.minecraft.titans.registries;

import java.util.HashMap;

import net.minecraft.titans.TheTitans;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class TSounds {
	private static HashMap<String, SoundEvent> soundEvents = new HashMap<String, SoundEvent>();

	public static void init() {
		register("funny.explosion");
		register("evilgolem.punch");
		register("bigzombie.living");
		register("bigzombie.grunt");
		register("bigzombie.death");
		register("ghoul.living");
		register("ghoul.grunt");
		register("ghoul.death");
		register("wight.living");
		register("wight.grunt");
		register("wight.death");
		register("warlock.idle");
		register("warlock.hurt");
		register("wraith.hurt");
		register("wraith.death");
		register("cast");
		register("cast.attack");
		register("cast.passive");
		register("gargoyle.living");
		register("gargoyle.grunt");
		register("gargoyle.death");
		register("titan.crush.wood");
		register("titan.hit.metal");
		register("titan.press");
		register("sword.drag");
		register("titan.swing");
		register("slash.flesh");
		register("dimensional.transport");
		register("harcadium.hum");
		register("harcadium.block.hum");
		register("large.fall");
		register("quick.apperence");
		register("titan.birth");
		register("titan.quake");
		register("titan.rumble");
		register("distant.large.fall");
		register("titan.fall");
		register("titan.slam");
		register("titan.strike");
		register("groundsmash");
		register("omg");
		register("chaff.deployment");
		register("titan.punch");
		register("titan.land");
		register("turret.shoot");
		register("turret.death");
		register("turret.shoot.2");
		register("turret.death.2");
		register("mortar.shoot");
		register("mortar.hit");
		register("turret.death.3");
		register("titan.summon.minion");
		register("predator.spider");
		register("lightning.throw");
		register("lightning.charge");
		register("titan.zombie.living");
		register("titan.zombie.grunt");
		register("titan.step");
		register("titan.zombie.roar");
		register("titan.zombie.death");
		register("titan.zombie.creation");
		register("titan.spider.living");
		register("titan.spider.grunt");
		register("titan.spider.death");
		register("titan.creeper.living");
		register("titan.creeper.grunt");
		register("titan.creeper.death");
		register("titan.creeper.stun");
		register("titan.creeper.warning");
		register("titan.creeper.explosion");
		register("titan.creeper.awaken");
		register("titan.creeper.beginmove");
		register("titan.ghast.fireball");
		register("titan.ghast.charge");
		register("titan.ghast.living");
		register("titan.ghast.grunt");
		register("titan.ghast.death");
		register("titan.endercolossus.scream");
		register("titan.endercolossus.screamlong");
		register("titan.endercolossus.chomp");
		register("titan.endercolossus.stare");
		register("titan.endercolossus.roar");
		register("titan.endercolossus.living");
		register("titan.endercolossus.grunt");
		register("titan.endercolossus.death");
		register("titan.pigzombie.living");
		register("titan.pigzombie.grunt");
		register("titan.pigzombie.death");
		register("titan.blaze.breathe");
		register("titan.blaze.grunt");
		register("titan.blaze.death");
		register("titan.silverfish.living");
		register("titan.silverfish.grunt");
		register("titan.silverfish.death");
		register("titan.skeleton.living");
		register("titan.skeleton.grunt");
		register("titan.skeleton.death");
		register("titan.witherskeleton.living");
		register("titan.witherskeleton.grunt");
		register("titan.witherskeleton.death");
		register("titan.skeleton.awaken");
		register("titan.skeleton.beginmove");
		register("titan.skeleton.getup");
		register("titan.skeleton.bonecrunch");
		register("witherzilla.theme");
		register("witherzilla.spawn");
		register("witherzilla.living");
		register("witherzilla.grunt");
		register("witherzilla.death");
		register("executor.dragon.living");
		register("executor.dragon.grunt");
		register("executor.dragon.death");
		register("executor.dragon.flap");
	}

	public static void register(String soundPath)
	{
		ResourceLocation resLoc = new ResourceLocation(TheTitans.MODID, soundPath);
		SoundEvent event = new SoundEvent(resLoc).setRegistryName(resLoc);
		ForgeRegistries.SOUND_EVENTS.register(event);
		soundEvents.put(soundPath, event);
	}

	public static SoundEvent get(String soundPath)
	{
		return soundEvents.get(soundPath);
	}
}
