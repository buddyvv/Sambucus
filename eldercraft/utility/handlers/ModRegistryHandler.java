package sambucus.eldercraft.utility.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sambucus.eldercraft.initialization.BlockInitialization;
import sambucus.eldercraft.initialization.ItemInitialization;
import sambucus.eldercraft.utility.IHaveModel;

@EventBusSubscriber
public class ModRegistryHandler {
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(ItemInitialization.ITEMS.toArray(new Item[0]));
	}	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event){
		event.getRegistry().registerAll(BlockInitialization.BLOCKS.toArray(new Block[0]));
	}
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event){
		for(Item item : ItemInitialization.ITEMS){
			if(item instanceof IHaveModel){
				((IHaveModel)item).registerModels();
			}
		}
		for(Block block : BlockInitialization.BLOCKS){
			if(block instanceof IHaveModel){
				((IHaveModel)block).registerModels();
			}
		}
	}
		
}
