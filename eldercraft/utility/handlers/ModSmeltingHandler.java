package sambucus.eldercraft.utility.handlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sambucus.eldercraft.initialization.ItemInitialization;

public class ModSmeltingHandler {
	public static void registerSmelting() {
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYRAWBEEF), 	//input
				new ItemStack(ItemInitialization.PATTYCOOKEDBEEF), 6f);				//output & experience points
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYRAWCHICKEN), 	
				new ItemStack(ItemInitialization.PATTYCOOKEDCHICKEN), 8f);
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYRAWFISH), 
				new ItemStack(ItemInitialization.PATTYCOOKEDFISH), 12f);
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYRAWLEAN),
				new ItemStack(ItemInitialization.PATTYCOOKEDLEAN), 10f);
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYRAWMUTTON),
				new ItemStack(ItemInitialization.PATTYCOOKEDMUTTON), 15f);
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYRAWPORK),
				new ItemStack(ItemInitialization.PATTYCOOKEDPORK), 5f);
	}
}