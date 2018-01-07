package sambucus.eldercraft.proxy;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sambucus.eldercraft.ElderCraft;
import sambucus.eldercraft.GuiHandlerRegistry;
import sambucus.eldercraft.objects.tiles.TileGrinder;
import sambucus.eldercraft.utility.Reference;
import sambucus.eldercraft.utility.handlers.GUIHandler;
import sambucus.eldercraft.utility.handlers.ModRegistryHandler;

@Mod.EventBusSubscriber
public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e){
		NetworkRegistry.INSTANCE.registerGuiHandler(ElderCraft.instance, GuiHandlerRegistry.getInstance());
		GuiHandlerRegistry.getInstance().registerGuiHandler(new GUIHandler(), GUIHandler.getGuiID());
		GameRegistry.registerTileEntity(TileGrinder.class, "TileGrinder");//yes this needs to be HERE
	}
	public void init(FMLInitializationEvent e){
		ModRegistryHandler.registerSmelting();
	}
	public void postInit(FMLPostInitializationEvent e){} 
	public void registerItemRenderer(Item item, int meta, String id){}
}