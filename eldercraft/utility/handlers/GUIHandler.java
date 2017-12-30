package sambucus.eldercraft.utility.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import sambucus.eldercraft.objects.tiles.ContainerGrinder;
import sambucus.eldercraft.objects.tiles.TileGrinder;
import sambucus.eldercraft.utility.gui.GuiGrinder;

public class GUIHandler implements IGuiHandler {
	public static int getGuiID() {
		System.err.println("get the GUI ID");//TODO this is called twice quickly
		return 1;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileGrinder) {
			System.err.println("server call to get the GUI");//TODO
			TileGrinder tileGrinder = (TileGrinder) tileEntity;
			return new ContainerGrinder(player.inventory, tileGrinder);
		}
		System.err.println("server fail to get the GUI");//TODO
		return null;
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID != getGuiID()) {
			System.err.println("Invalid ID: expected " + getGuiID() + ", received " + ID);
		}
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileGrinder) {
			System.err.println("client call to get the GUI");//TODO
			TileGrinder tileGrinder = (TileGrinder) tileEntity;
			return new GuiGrinder(player.inventory, tileGrinder);
		}
		System.err.println("client failed to get the GUI");//TODO
		return null;
	}
}
