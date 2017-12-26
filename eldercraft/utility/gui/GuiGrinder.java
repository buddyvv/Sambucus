package sambucus.eldercraft.utility.gui;

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
	//no idea if that will work
	private static final ResourceLocation texture = new ResourceLocation("ElderCraft", "textures/gui/container/creative_inventory");
	private TileGrinder tileEntity;
	
	
	public GuiGrinder(InventoryPlayer invPlayer, TileGrinder tileGrinder) {
		super(new ContainerGrinder(invPlayer, tileGrinder));
		// Set the width and height of the gui
		xSize = 176;
		ySize = 207;

		this.tileEntity = tileGrinder;
	}


	// some [x,y] coordinates of graphical elements
	final int COOK_BAR_XPOS = 49;
	final int COOK_BAR_YPOS = 60;
	final int COOK_BAR_ICON_U = 0;   // texture position of white arrow icon
	final int COOK_BAR_ICON_V = 207;
	final int COOK_BAR_WIDTH = 80;
	final int COOK_BAR_HEIGHT = 17;

	final int FLAME_XPOS = 54;
	final int FLAME_YPOS = 80;
	final int FLAME_ICON_U = 176;   // texture position of flame icon
	final int FLAME_ICON_V = 0;    	//no doubt will need to change all of this
	final int FLAME_WIDTH = 14;
	final int FLAME_HEIGHT = 14;
	final int FLAME_X_SPACING = 18;
	
	//will for sure need to fix this up for our own use
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// Bind the image texture
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		// Draw the image
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		//this is the area that we will need to draw the fuel bar 
		
		
		// get grind progress as a double between 0 and 1
		
		
		
		//double cookProgress = tileEntity.fractionOfCookTimeComplete();//this seems to be a var to help draw the fire bit
		// draw the cook progress bar
		//drawTexturedModalRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V,
		//		(int)(cookProgress * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);
		
		// draw the fuel remaining bar for each fuel slot flame
		for (int i = 0; i < tileEntity.slotsFuel; ++i) {
			//double burnRemaining = tileEntity.fractionOfFuelRemaining(i);
			//int yOffset = (int)((1.0 - burnRemaining) * FLAME_HEIGHT);
			//drawTexturedModalRect(guiLeft + FLAME_XPOS + FLAME_X_SPACING * i, guiTop + FLAME_YPOS + yOffset,
			//		FLAME_ICON_U, FLAME_ICON_V + yOffset, FLAME_WIDTH, FLAME_HEIGHT - yOffset);
		}	
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		final int LABEL_XPOS = 5;
		final int LABEL_YPOS = 5;
   // fontRenderer.drawString(tileEntity.getDisplayName().getUnformattedText(), LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());

		//List<String> hoveringText = new ArrayList<String>();

		// If the mouse is over the progress bar add the progress bar hovering text
		//if (isInRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_WIDTH, COOK_BAR_HEIGHT, mouseX, mouseY)){
			//hoveringText.add("Progress:");
			//int cookPercentage =(int)(tileEntity.fractionOfCookTimeComplete() * 100);
			//hoveringText.add(cookPercentage + "%");
		//}

		// If the mouse is over one of the burn time indicator add the burn time indicator hovering text
		//for (int i = 0; i < tileEntity.FUEL_SLOTS_COUNT; ++i) {
			//if (isInRect(guiLeft + FLAME_XPOS + FLAME_X_SPACING * i, guiTop + FLAME_YPOS, FLAME_WIDTH, FLAME_HEIGHT, mouseX, mouseY)) {
				//hoveringText.add("Fuel Time:");
				//hoveringText.add(tileEntity.secondsOfFuelRemaining(i) + "s");
			//}
		//}
		// If hoveringText is not empty draw the hovering text
		//if (!hoveringText.isEmpty()){
			//drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRenderer);
		//}
	}
}
