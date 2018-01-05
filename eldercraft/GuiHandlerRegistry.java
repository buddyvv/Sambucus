package sambucus.eldercraft;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandlerRegistry implements IGuiHandler {
	private HashMap<Integer, IGuiHandler> registeredHandlers = new HashMap<Integer, IGuiHandler>();
	private static GuiHandlerRegistry guiHandlerRegistry = new GuiHandlerRegistry();
	
	public void registerGuiHandler(IGuiHandler handler, int guiID){
		registeredHandlers.put(guiID, handler);
	}

	public static GuiHandlerRegistry getInstance() {
		return guiHandlerRegistry;
	}

	// Gets the server side element for the given gui id- this should return a container
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.err.println("server call to register the GUI");//TODO
		IGuiHandler handler = registeredHandlers.get(ID);
		if (handler != null) {
			return handler.getServerGuiElement(ID, player, world, x, y, z);
		} else {
			return null;
		}
	}

	// Gets the client side element for the given gui id- this should return a gui
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		System.err.println("client call to register the GUI");//TODO
		IGuiHandler handler = registeredHandlers.get(ID);
		if (handler != null) {
			return handler.getClientGuiElement(ID, player, world, x, y, z);
		} else {
			return null;
		}
	}
}