package net.minecraft.theTitans;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
public class TitansAchievments
{

	public static Achievement harcadium = new Achievement("achievement.harcadium", "harcadium", -4, 0, TitanItems.harcadium, (Achievement)null).initIndependentStat().registerStat();
	public static Achievement harcadiumArmor = new Achievement("achievement.harcadiumArmor", "harcadiumArmor", -3, 0, TitanItems.harcadiumChestplate, harcadium).registerStat();
	public static Achievement harcadiumSword = new Achievement("achievement.harcadiumSword", "harcadiumSword", -2, 0, TitanItems.harcadiumSword, harcadiumArmor).registerStat();
	public static Achievement harcadiumBow = new Achievement("achievement.harcadiumBow", "harcadiumBow", -1, 0, TitanItems.harcadiumBow, harcadiumSword).registerStat();
	public static Achievement harcadiumArrows = new Achievement("achievement.harcadiumArrows", "harcadiumArrows", 0, 0, TitanItems.harcadiumArrow, harcadiumBow).registerStat();
	public static Achievement locateTitan = new Achievement("achievement.locateTitan", "locateTitan", 1, 0, TitanItems.growthSerum, harcadiumArrows).registerStat();
	public static Achievement withirenium = new Achievement("achievement.withirenium", "withirenium", 0, -2, TitanItems.withirenium, locateTitan).registerStat();
	public static Achievement withireniumArmor = new Achievement("achievement.withireniumArmor", "withireniumArmor", -1, -3, TitanItems.withireniumChestplate, withirenium).registerStat();
	public static Achievement voidEssence = new Achievement("achievement.voidEssence", "voidEssence", -1, -3, TitanItems.voidItem, withireniumArmor).registerStat();
	public static Achievement voidArmor = new Achievement("achievement.voidArmor", "voidArmor", -2, -4, TitanItems.voidChestplate, voidEssence).registerStat();
	public static Achievement voidSword = new Achievement("achievement.voidSword", "voidSword", -3, -4, TitanItems.voidSword, voidArmor).registerStat();
	public static Achievement voidBow = new Achievement("achievement.voidBow", "voidBow", -4, -3, TitanItems.voidBow, voidSword).registerStat();
	public static Achievement voidArrows = new Achievement("achievement.voidArrows", "voidArrows", -5, -3, TitanItems.voidArrow, voidBow).registerStat();
	public static Achievement omegafish = new Achievement("achievement.omegafish", "omegafish", 2, 0, TitanItems.eggOmegafish, locateTitan).registerStat().setSpecial();
	public static Achievement cavespidertitan = new Achievement("achievement.cavespidertitan", "cavespidertitan", 2, 1, TitanItems.eggCaveSpiderTitan, locateTitan).registerStat().setSpecial();
	public static Achievement slimetitan = new Achievement("achievement.slimetitan", "slimetitan", 2, 2, TitanItems.eggSlimeTitan, locateTitan).registerStat().setSpecial();
	public static Achievement magmacubetitan = new Achievement("achievement.magmacubetitan", "magmacubetitan", 2, 3, TitanItems.eggMagmaCubeTitan, locateTitan).registerStat().setSpecial();
	public static Achievement spidertitan = new Achievement("achievement.spidertitan", "spidertitan", 2, 4, TitanItems.eggSpiderTitan, locateTitan).registerStat().setSpecial();
	public static Achievement spiderjockeytitan = new Achievement("achievement.spiderjockeytitan", "spiderjockeytitan", 2, 5, TitanItems.eggSpiderJockeyTitan, locateTitan).registerStat().setSpecial();
	public static Achievement zombietitan = new Achievement("achievement.zombietitan", "zombietitan", 2, -1, TitanItems.eggZombieTitan, locateTitan).registerStat().setSpecial();
	public static Achievement skeletontitan = new Achievement("achievement.skeletontitan", "skeletontitan", 2, -2, TitanItems.eggSkeletonTitan, locateTitan).registerStat().setSpecial();
	public static Achievement creepertitan = new Achievement("achievement.creepertitan", "creepertitan", 2, -3, TitanItems.eggCreeperTitan, locateTitan).registerStat().setSpecial();
	public static Achievement pigzombietitan = new Achievement("achievement.pigzombietitan", "pigzombietitan", 2, -4, TitanItems.eggZombiePigmanTitan, locateTitan).registerStat().setSpecial();
	public static Achievement blazetitan = new Achievement("achievement.blazetitan", "blazetitan", 2, -5, TitanItems.eggBlazeTitan, locateTitan).registerStat().setSpecial();
	public static Achievement witherskeletontitan = new Achievement("achievement.witherskeletontitan", "witherskeletontitan", 2, -6, TitanItems.eggWitherSkeletonTitan, locateTitan).registerStat().setSpecial();
	public static Achievement ghasttitan = new Achievement("achievement.ghasttitan", "ghasttitan", 2, -7, TitanItems.eggGhastTitan, locateTitan).registerStat().setSpecial();
	public static Achievement endercolossus = new Achievement("achievement.endercolossus", "endercolossus", 3, 0, TitanItems.eggEnderColossus, locateTitan).registerStat().setSpecial();
	public static Achievement adminiumArmor = new Achievement("achievement.adminiumArmor", "adminiumArmor", 4, 0, TitanItems.adminiumChestplate, endercolossus).registerStat().setSpecial();
	public static Achievement adamantium = new Achievement("achievement.adamantium", "adamantium", 6, 0, TitanItems.adamantium, adminiumArmor).registerStat().setSpecial();
	public static Achievement adamantiumSword = new Achievement("achievement.adamantiumSword", "adamantiumSword", 8, 0, TitanItems.adamantiumSword, adamantium).registerStat().setSpecial();
	public static Achievement voidTime = new Achievement("achievement.voidTime", "voidTime", 6, 2, TitanItems.teleporter, adminiumArmor).registerStat().setSpecial();
	public static Achievement witherzilla = new Achievement("achievement.witherzilla", "witherzilla", 8, 2, TitanItems.malgrum, voidTime).registerStat().setSpecial();
	public static Achievement witherzilla2 = new Achievement("achievement.witherzilla2", "witherzilla2", 10, 2, TitanItems.eggWitherzilla, witherzilla).registerStat().setSpecial();
	public static Achievement nowhereTime = new Achievement("achievement.nowhereTime", "nowhereTime", 6, -2, TitanItems.teleporter2, adminiumArmor).registerStat().setSpecial();
	public static Achievement executorDragon = new Achievement("achievement.executorDragon", "executorDragon", 8, -2, TitanItems.malgrum, nowhereTime).registerStat().setSpecial();
	public static Achievement executorDragon2 = new Achievement("achievement.executorDragon2", "executorDragon2", 10, -2, TitanItems.eggWitherzilla, executorDragon).registerStat().setSpecial();
	public static Achievement ultimaBlade = new Achievement("achievement.ultimaBlade", "ultimaBlade", 9, 1, TitanItems.ultimaBlade, (Achievement)null).initIndependentStat().registerStat().setSpecial();
	public static Achievement optimaAxe = new Achievement("achievement.optimaAxe", "optimaAxe", 9, -1, TitanItems.optimaAxe, (Achievement)null).initIndependentStat().registerStat().setSpecial();
	public static void addAchievments()
	{
		AchievementPage.registerAchievementPage
		(new AchievementPage("The Titans", new Achievement[]
		{
			harcadium,harcadiumArmor,harcadiumSword,harcadiumBow,harcadiumArrows,locateTitan,withirenium,withireniumArmor,voidEssence,voidArmor,voidSword,voidBow,voidArrows,omegafish,cavespidertitan,slimetitan,magmacubetitan,spidertitan,spiderjockeytitan,zombietitan,skeletontitan,creepertitan,pigzombietitan,blazetitan,witherskeletontitan,ghasttitan,endercolossus,adminiumArmor,adamantium,adamantiumSword,voidTime,witherzilla,witherzilla2,nowhereTime,executorDragon,executorDragon2,ultimaBlade,optimaAxe
		}
		));
	}
}


