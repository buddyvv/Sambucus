package sambucus.eldercraft.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sambucus.eldercraft.ElderCraft;
import sambucus.eldercraft.GuiHandlerRegistry;
import sambucus.eldercraft.objects.tiles.TileGrinder;
import sambucus.eldercraft.utility.handlers.GUIHandler;
import sambucus.eldercraft.utility.handlers.ModSmeltingHandler;

@Mod.EventBusSubscriber
public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(ElderCraft.instance, GuiHandlerRegistry.getInstance());
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GUIHandler(), GUIHandler.getGuiID());
		GameRegistry.registerTileEntity(TileGrinder.class, "TileGrinder");	
	}
	public void init(FMLInitializationEvent e){
		ModSmeltingHandler.registerSmelting();
	}
	public void postInit(FMLPostInitializationEvent e){} 
	public void registerItemRenderer(Item item, int meta, String id){}
}
