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
	//CAN BE ANYWHERE just update CommonProxy to reflect change this is what makes the furnace work MUST be in an event
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
