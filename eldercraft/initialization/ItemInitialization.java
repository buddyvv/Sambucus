package sambucus.eldercraft.initialization;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import sambucus.eldercraft.objects.items.poisongland;


public class ItemInitialization {

		public static final List<Item> ITEMS = new ArrayList<Item>();					//The array list that is creating our item list. Shouldn't need to to touch this.
		
		public static final Item POISONGLAND = new poisongland("item_poisongland");		//Initialization of the sugarcube block. Refers to sambucus.eldercraft.objects.blocks.sugarcube
	
		
}
