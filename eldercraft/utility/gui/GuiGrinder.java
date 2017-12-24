package sambucus.eldercraft.utility.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import sambucus.eldercraft.objects.tiles.ContainerGrinder;
import sambucus.eldercraft.objects.tiles.TileGrinder;

public class GuiGrinder extends GuiContainer {
	
	private TileGrinder TileEntity;
	
	
	public GuiGrinder(InventoryPlayer invPlayer, TileGrinder tileGrinder) {
		super(new ContainerGrinder(invPlayer, tileGrinder));
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}



}
