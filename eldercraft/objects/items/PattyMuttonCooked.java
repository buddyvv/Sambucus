package sambucus.eldercraft.objects.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import sambucus.eldercraft.ElderCraft;
import sambucus.eldercraft.initialization.ItemInitialization;
import sambucus.eldercraft.utility.IHaveModel;

public class PattyMuttonCooked extends ItemFood implements IHaveModel{
	public PattyMuttonCooked(String name, int amount, boolean isWolfFood, PotionEffect... potionEffects){
		super(amount, isWolfFood);
		ItemInitialization.ITEMS.add(this);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ElderCraft.eldercrafttab);
		this.effects = potionEffects;;	
	}
	private PotionEffect[] effects;
	
	@Override
	public void registerModels(){
		ElderCraft.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		for (PotionEffect effect : this.effects) {
			player.addPotionEffect(new PotionEffect(effect));
			}
		}
}