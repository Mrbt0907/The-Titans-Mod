package net.minecraft.theTitans.items;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.titan.EntityTitan;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.theTitans.DamageSourceExtra;
import net.minecraft.theTitans.TheTitans;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
public class ItemHarcadiumHoe
extends ItemSword
{
	private float field_150934_a;
	private final Item.ToolMaterial field_150933_b;
	public ItemHarcadiumHoe(String unlocalizedName, Item.ToolMaterial material)
	{
		super(material);
		setTextureName(TheTitans.getTextures(unlocalizedName + "_hoe"));
		setUnlocalizedName(unlocalizedName + "_hoe");
		setCreativeTab(TheTitans.titansTab);
		this.field_150934_a = material.getDamageVsEntity();
		this.field_150933_b = material;
	}

	public float func_150931_i()
	{
		return this.field_150933_b.getDamageVsEntity() - 4.0F;
	}

	@SuppressWarnings("unchecked")
	public Multimap<String, AttributeModifier> getItemAttributeModifiers()
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.field_150934_a - 4.0D, 0));
		return multimap;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		if (target != null)
		{
			if ((target.height >= 6.0F) || ((target instanceof EntityTitan)) || (!target.onGround))
			{
				target.attackEntityFrom(DamageSourceExtra.causeAntiTitanDamage(attacker), 300.0F);
				target.playSound("thetitans:titanpunch", 10.0F, 1.0F);
			}
		}

		return true;
	}

	public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.none;
	}

	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
		{
			return false;
		}

		UseHoeEvent event = new UseHoeEvent(p_77648_2_, p_77648_1_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_);
		if (MinecraftForge.EVENT_BUS.post(event))
		{
			return false;
		}

		if (event.getResult() == Result.ALLOW)
		{
			p_77648_1_.damageItem(1, p_77648_2_);
			return true;
		}

		Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
		if ((p_77648_7_ != 0) && (p_77648_3_.getBlock(p_77648_4_, p_77648_5_ + 1, p_77648_6_).isAir(p_77648_3_, p_77648_4_, p_77648_5_ + 1, p_77648_6_)) && ((block == Blocks.grass) || (block == Blocks.dirt) || (block == Blocks.sponge) || (block == Blocks.mycelium) || (block == Blocks.gravel)))
		{
			Block block1 = Blocks.farmland;
			p_77648_3_.playSoundEffect(p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, block1.stepSound.getStepResourcePath(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
			p_77648_3_.playSoundEffect(p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);
			if (p_77648_3_.isRemote)
			{
				return true;
			}

			p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, block1);
			p_77648_1_.damageItem(1, p_77648_2_);
			return true;
		}

		return false;
	}

	public boolean func_150897_b(Block p_150897_1_)
	{
		return p_150897_1_ == Blocks.farmland;
	}
}


