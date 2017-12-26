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
	public static int getGuiID() {return 1;}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileGrinder) {
			TileGrinder tileGrinder = (TileGrinder) tileEntity;
			return new ContainerGrinder(player.inventory, tileGrinder);
		}
		return null;
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos xyz = new BlockPos(x, y, z);
		TileEntity tileEntity = world.getTileEntity(xyz);
		if (tileEntity instanceof TileGrinder) {
			TileGrinder tileGrinder = (TileGrinder) tileEntity;
			return new GuiGrinder(player.inventory, tileGrinder);
		}
		return null;
	}
}
