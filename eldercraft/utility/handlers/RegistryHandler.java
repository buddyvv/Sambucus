package sambucus.eldercraft.utility.handlers;

import sambucus.eldercraft.initialization.ItemInitialization;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sambucus.eldercraft.utility.IHaveModel;

@EventBusSubscriber
public class RegistryHandler {

		@SubscribeEvent
		public static void onItemRegister(RegistryEvent.Register<Item> event)
		{
			event.getRegistry().registerAll(ItemInitialization.ITEMS.toArray(new Item[0]));
		}
	
		@SubscribeEvent
		public static void onModelRegister(ModelRegistryEvent event)
		{
			for(Item item : ItemInitialization.ITEMS)
			{
				if(item instanceof IHaveModel)
				{
					((IHaveModel)item).registerModels();
				}
			}
		}
	
}
