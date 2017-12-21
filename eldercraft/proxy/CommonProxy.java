package sambucus.eldercraft.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sambucus.eldercraft.utility.handlers.ModSmeltingHandler;

@Mod.EventBusSubscriber
public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e){}
	public void init(FMLInitializationEvent e){
		ModSmeltingHandler.registerSmelting();
	}
	public void postInit(FMLPostInitializationEvent e){} 
	public void registerItemRenderer(Item item, int meta, String id){}
}
