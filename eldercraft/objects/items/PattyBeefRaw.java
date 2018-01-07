package sambucus.eldercraft.objects.items;

import net.minecraft.item.Item;
import sambucus.eldercraft.ElderCraft;
import sambucus.eldercraft.initialization.ItemInitialization;
import sambucus.eldercraft.utility.IHaveModel;

public class PattyBeefRaw extends Item implements IHaveModel{
	public PattyBeefRaw(String name){
		ItemInitialization.ITEMS.add(this);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ElderCraft.eldercrafttab);	
	}

	@Override
	public void registerModels(){
		ElderCraft.proxy.registerItemRenderer(this, 0, "inventory");
	}
}