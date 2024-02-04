package net.minecraft.theTitans.render.minions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.titanminion.EntityZombieMinion;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.util.ResourceLocation;
@SideOnly(Side.CLIENT)
public class RenderZombieMinion extends RenderBiped
{
	private static final ResourceLocation zombieTemplarTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_templar.png"));
	private static final ResourceLocation zombieVillagerTemplarTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_villager_templar.png"));
	private static final ResourceLocation zombieBishopTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_bishop.png"));
	private static final ResourceLocation zombieVillagerBishopTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_villager_bishop.png"));
	private static final ResourceLocation zombieZealotTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_zealot.png"));
	private static final ResourceLocation zombieVillagerZealotTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_villager_zealot.png"));
	private static final ResourceLocation zombiePriestTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_priest.png"));
	private static final ResourceLocation zombieVillagerPriestTextures = new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/zombies/zombie_villager_priest.png"));
	private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
	private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
	private ModelBiped field_82434_o;
	private ModelZombieVillager zombieVillagerModel;
	protected ModelBiped field_82437_k;
	protected ModelBiped field_82435_l;
	protected ModelBiped field_82436_m;
	protected ModelBiped field_82433_n;
	private int field_82431_q = 1;
	public RenderZombieMinion()
	{
		super(new ModelZombie(), 0.5F, 1.0F);
		this.field_82434_o = this.modelBipedMain;
		this.zombieVillagerModel = new ModelZombieVillager();
		setRenderPassModel(this.mainModel);
	}

	protected void func_82421_b()
	{
		this.field_82423_g = new ModelZombie(1.0F, true);
		this.field_82425_h = new ModelZombie(0.5F, true);
		this.field_82437_k = this.field_82423_g;
		this.field_82435_l = this.field_82425_h;
		this.field_82436_m = new ModelZombieVillager(1.0F, 0.0F, true);
		this.field_82433_n = new ModelZombieVillager(0.5F, 0.0F, true);
	}

	/**
	* Queries whether should render the specified pass or not.
	*/
	protected int shouldRenderPass(EntityZombieMinion p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		this.func_82427_a(p_77032_1_);
		return super.shouldRenderPass((EntityLiving)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	/**
	* Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	* handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	* (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	* double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	*/
	public void doRender(EntityZombieMinion p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.func_82427_a(p_76986_1_);
		super.doRender((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(EntityZombieMinion p_110775_1_)
	{
		switch (p_110775_1_.getMinionTypeInt())
		{
			case 1:
			return (p_110775_1_.isVillager() ? zombieVillagerPriestTextures : zombiePriestTextures);
			case 2:
			return (p_110775_1_.isVillager() ? zombieVillagerZealotTextures : zombieZealotTextures);
			case 3:
			return (p_110775_1_.isVillager() ? zombieVillagerBishopTextures : zombieBishopTextures);
			case 4:
			return (p_110775_1_.isVillager() ? zombieVillagerTemplarTextures : zombieTemplarTextures);
			default:
			return (p_110775_1_.isVillager() ? zombieVillagerTextures : zombieTextures);
		}
	}

	protected void renderEquippedItems(EntityZombieMinion p_77029_1_, float p_77029_2_)
	{
		this.func_82427_a(p_77029_1_);
		super.renderEquippedItems((EntityLiving)p_77029_1_, p_77029_2_);
	}

	private void func_82427_a(EntityZombieMinion p_82427_1_)
	{
		if (p_82427_1_.isVillager())
		{
			if (this.field_82431_q != this.zombieVillagerModel.func_82897_a())
			{
				this.zombieVillagerModel = new ModelZombieVillager();
				this.field_82431_q = this.zombieVillagerModel.func_82897_a();
				this.field_82436_m = new ModelZombieVillager(1.0F, 0.0F, true);
				this.field_82433_n = new ModelZombieVillager(0.5F, 0.0F, true);
			}

			this.mainModel = this.zombieVillagerModel;
			this.field_82423_g = this.field_82436_m;
			this.field_82425_h = this.field_82433_n;
		}

		else
		{
			this.mainModel = this.field_82434_o;
			this.field_82423_g = this.field_82437_k;
			this.field_82425_h = this.field_82435_l;
		}

		this.modelBipedMain = (ModelBiped)this.mainModel;
	}

	protected void rotateCorpse(EntityZombieMinion p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		if (p_77043_1_.isConverting())
		{
			p_77043_3_ += (float)(Math.cos((double)p_77043_1_.ticksExisted * 3.25D) * Math.PI * 0.25D);
		}

		super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_)
	{
		this.renderEquippedItems((EntityZombieMinion)p_77029_1_, p_77029_2_);
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_)
	{
		return this.getEntityTexture((EntityZombieMinion)p_110775_1_);
	}

	/**
	* Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	* handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	* (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	* double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	*/
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityZombieMinion)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	* Queries whether should render the specified pass or not.
	*/
	protected int shouldRenderPass(EntityLiving p_77032_1_, int p_77032_2_, float p_77032_3_)
	{
		return this.shouldRenderPass((EntityZombieMinion)p_77032_1_, p_77032_2_, p_77032_3_);
	}

	protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_)
	{
		this.renderEquippedItems((EntityZombieMinion)p_77029_1_, p_77029_2_);
	}

	protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		this.rotateCorpse((EntityZombieMinion)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
	}

	/**
	* Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	* handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	* (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	* double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	*/
	public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityZombieMinion)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	/**
	* Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	*/
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntityZombieMinion)p_110775_1_);
	}

	/**
	* Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	* handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	* (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	* double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	*/
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityZombieMinion)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}


