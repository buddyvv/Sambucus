package sambucus.eldercraft.utility.handlers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sambucus.eldercraft.initialization.ItemInitialization;

public class ModSmeltingHandler {

	public static void registerSmelting() {
		
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYBEEFRAW), 	//input
				new ItemStack(ItemInitialization.PATTYBEEFCOOKED), 6f);				//output & experience points
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYCHICKENRAW), 	
				new ItemStack(ItemInitialization.PATTYCHICKENCOOKED), 8f);
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYFISHRAW), 
				new ItemStack(ItemInitialization.PATTYFISHCOOKED), 12f);
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYLEANRAW),
				new ItemStack(ItemInitialization.PATTYLEANCOOKED), 10f);
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYMUTTONRAW),
				new ItemStack(ItemInitialization.PATTYMUTTONCOOKED), 15f);
		GameRegistry.addSmelting(new ItemStack(ItemInitialization.PATTYPORKRAW),
				new ItemStack(ItemInitialization.PATTYPORKCOOKED), 5f);
	}

}