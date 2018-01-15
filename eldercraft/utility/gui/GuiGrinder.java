package sambucus.eldercraft.utility.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sambucus.eldercraft.objects.tiles.ContainerGrinder;
import sambucus.eldercraft.objects.tiles.TileGrinder;

@SideOnly(Side.CLIENT)
public class GuiGrinder extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation("ec0", "textures/gui/container/grinder.png");
	private TileGrinder tileEntity;
	
	public GuiGrinder(InventoryPlayer invPlayer, TileGrinder tileGrinder) {
		super(new ContainerGrinder(invPlayer, tileGrinder));
		xSize = 176;
		ySize = 166;

		this.tileEntity = tileGrinder;
	}

	final int COOK_BAR_XPOS = 41;
	final int COOK_BAR_YPOS = 15;
	final int COOK_BAR_ICON_U = 176;  
	final int COOK_BAR_ICON_V = 14;
	final int COOK_BAR_WIDTH = 69;
	final int COOK_BAR_HEIGHT = 18;

	final int FLAME_XPOS = 15;
	final int FLAME_YPOS = 57;
	final int FLAME_ICON_U = 176;  
	final int FLAME_ICON_V = 0;    	
	final int FLAME_WIDTH = 14;
	final int FLAME_HEIGHT = 14;
	
	final int FUEL_BAR_XPOS = 14;
	final int FUEL_BAR_YPOS = 38;
	final int FUEL_BAR_ICON_U = 176;  
	final int FUEL_BAR_ICON_V = 33;
	final int FUEL_BAR_WIDTH = 18;
	final int FUEL_BAR_HEIGHT = 16;
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		double cookProgress = tileEntity.fractionOfGrindTimeComplete();
		drawTexturedModalRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V,
				(int)(cookProgress * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);
		
		double burnRemaining = tileEntity.fractionOfFuelRemaining();
		int yOffset = (int)((1.0 - burnRemaining) * FLAME_HEIGHT);
		drawTexturedModalRect(guiLeft + FLAME_XPOS, guiTop + FLAME_YPOS + yOffset, FLAME_ICON_U, FLAME_ICON_V + yOffset, FLAME_WIDTH, FLAME_HEIGHT - yOffset);
		
		double tankRemaining = tileEntity.fractionOfTankRemaining();
		int tankYOffset = (int)((1.0 - tankRemaining) * FUEL_BAR_HEIGHT);
		drawTexturedModalRect(guiLeft + FUEL_BAR_XPOS, guiTop + FUEL_BAR_YPOS + tankYOffset, FUEL_BAR_ICON_U, FUEL_BAR_ICON_V + tankYOffset, FUEL_BAR_WIDTH, FUEL_BAR_HEIGHT - tankYOffset);
			
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
		
		List<String> hoveringText = new ArrayList<String>();

		if (isInRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX, mouseY)){
			hoveringText.add("Progress:");
			int cookPercentage =(int)(tileEntity.fractionOfGrindTimeComplete() * 100);
			hoveringText.add(cookPercentage + "%");
		}

		for (int i = 0; i < tileEntity.FUEL_SLOTS_COUNT; ++i) {
			if (isInRect(guiLeft + FLAME_XPOS, guiTop + FLAME_YPOS, FLAME_WIDTH, FLAME_HEIGHT, mouseX, mouseY)) {
				hoveringText.add("Fuel Time:");
				hoveringText.add(tileEntity.secondsOfFuelRemaining(i) + "s");
			}
		}
		if (!hoveringText.isEmpty()){
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRenderer);
		}
	}
	
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY){
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
}
