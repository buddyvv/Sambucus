package sambucus.eldercraft.proxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sambucus.eldercraft.ElderCraft;
import sambucus.eldercraft.GuiHandlerRegistry;
import sambucus.eldercraft.initialization.ItemInitialization;
import sambucus.eldercraft.utility.handlers.GUIHandler;
import sambucus.eldercraft.utility.handlers.ModRegistryHandler;

@Mod.EventBusSubscriber
public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e){
		NetworkRegistry.INSTANCE.registerGuiHandler(ElderCraft.instance, GuiHandlerRegistry.getInstance());
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GUIHandler(), GUIHandler.getGuiID());
	}
	public void init(FMLInitializationEvent e){
		ModRegistryHandler.registerSmelting();
	}
	public void postInit(FMLPostInitializationEvent e){} 
	public void registerItemRenderer(Item item, int meta, String id){}
}