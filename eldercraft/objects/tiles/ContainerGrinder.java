package sambucus.eldercraft.objects.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGrinder extends Container{
	private TileGrinder tileGrinder;
	private int [] cachedFields;
	
	private final int HOTBAR_SLOT_COUNT = 9;
	private final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
	private final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	//a bunch of ones
	public final int FUEL_SLOTS_COUNT = 1;
	public final int INPUT_SLOTS_COUNT = 1;
	public final int INPUT_CAN_COUNT = 1;
	public final int OUTPUT_SLOTS_COUNT = 4;
	public final int FURNACE_SLOTS_COUNT = FUEL_SLOTS_COUNT + INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT + INPUT_CAN_COUNT;
	//hate all the things
	private final int VANILLA_FIRST_SLOT_INDEX = 0;
	private final int FIRST_FUEL_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private final int INPUT_SLOT_INDEX = FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT;
	private final int FIRST_CAN_SLOT_INDEX = INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT;
	private final int FIRST_OUTPUT_SLOT_INDEX = FIRST_CAN_SLOT_INDEX + INPUT_CAN_COUNT;
	
	private final int FIRST_FUEL_SLOT_NUMBER = 0;
	private final int FIRST_INPUT_SLOT_NUMBER = FIRST_FUEL_SLOT_NUMBER + FUEL_SLOTS_COUNT;
	private final int FIRST_CAN_SLOT_NUMBER = FIRST_INPUT_SLOT_NUMBER + INPUT_SLOTS_COUNT;
	private final int FIRST_OUTPUT_SLOT_NUMBER = FIRST_CAN_SLOT_NUMBER + INPUT_CAN_COUNT;

	public ContainerGrinder(InventoryPlayer invPlayer, TileGrinder tileGrinder) {
		this.tileGrinder = tileGrinder;
		
		final int SLOT_X_SPACING = 18;
		final int SLOT_Y_SPACING = 18;
		final int HOTBAR_XPOS = 8;
		final int HOTBAR_YPOS = 142;
		
		for (int x = 0; x < HOTBAR_SLOT_COUNT; x++) {
			int slotNumber = x;
			addSlotToContainer(new Slot(invPlayer, slotNumber, HOTBAR_XPOS + SLOT_X_SPACING * x, HOTBAR_YPOS));
		}
		
		final int PLAYER_INVENTORY_XPOS = 8;
		final int PLAYER_INVENTORY_YPOS = 84;
		for (int y = 0; y < 3; y++) {//PLAYER_INVENTORY_ROW_COUNT
			for (int x = 0; x < 9; x++) {//PLAYER_INVENTORY_COLUMN_COUNT
				//int slotNumber = HOTBAR_SLOT_COUNT + y * PLAYER_INVENTORY_COLUMN_COUNT + x;
				int slotNumber = 9 + y * 9 + x;
				int xpos = PLAYER_INVENTORY_XPOS + x * SLOT_X_SPACING;
				int ypos = PLAYER_INVENTORY_YPOS + y * SLOT_Y_SPACING;
				addSlotToContainer(new Slot(invPlayer, slotNumber,  xpos, ypos));
			}
		}
		final int FUEL_SLOTS_XPOS = 50;
		final int FUEL_SLOTS_YPOS = 37;
		for (int x = 0; x < FUEL_SLOTS_COUNT; x++) {
			int slotNumber = x + FIRST_FUEL_SLOT_NUMBER;
			addSlotToContainer(new SlotFuel(tileGrinder, slotNumber, FUEL_SLOTS_XPOS + SLOT_X_SPACING * x, FUEL_SLOTS_YPOS));
		}
		final int INPUT_SLOTS_XPOS = 15;
		final int INPUT_SLOTS_YPOS = 17;
		for (int y = 0; y < INPUT_SLOTS_COUNT; y++) {
			int slotNumber = y + FIRST_INPUT_SLOT_NUMBER;
			addSlotToContainer(new SlotGrindableInput(tileGrinder, slotNumber, INPUT_SLOTS_XPOS, INPUT_SLOTS_YPOS+ SLOT_Y_SPACING * y));
		}
		
		final int CAN_SLOT_XPOS = 91;
		final int CAN_SLOT_YPOS = 37;
		for (int y = 0; y < INPUT_CAN_COUNT; y++) {
			int slotNumber = y + FIRST_CAN_SLOT_NUMBER;
			addSlotToContainer(new SlotCanInput(tileGrinder, slotNumber, CAN_SLOT_XPOS, CAN_SLOT_YPOS + SLOT_Y_SPACING * y));
		}
		
		final int OUTPUT_SLOTS_XPOS = 120;
		final int OUTPUT_SLOTS_YPOS = 19;
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 2; x++) {
				int xpos = OUTPUT_SLOTS_XPOS + x * SLOT_X_SPACING;
				int ypos = OUTPUT_SLOTS_YPOS + y * SLOT_Y_SPACING;
				int slotNumber = FIRST_OUTPUT_SLOT_NUMBER + y * 2 + x;
				addSlotToContainer(new SlotOutput(tileGrinder, slotNumber, xpos, ypos));
			}
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex){
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		
		if (sourceSlot == null || !sourceSlot.getHasStack()) {
			return ItemStack.EMPTY;
		}
		
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();
		// Check if the slot clicked is one of the vanilla container slots
		if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			// If the stack is grindable try to merge merge the stack into the input slots
			if (!TileGrinder.getGrindingResultForItem(sourceStack).isEmpty()){  //isEmptyItem
				if (!mergeItemStack(sourceStack, INPUT_SLOT_INDEX, INPUT_SLOT_INDEX + INPUT_SLOTS_COUNT, false)){
					return ItemStack.EMPTY;
				}
			}else if (TileGrinder.getItemBurnTime(sourceStack) > 0) {
				if (!mergeItemStack(sourceStack, FIRST_FUEL_SLOT_INDEX, FIRST_FUEL_SLOT_INDEX + FUEL_SLOTS_COUNT, true)) {
					// Setting the boolean to true places the stack in the bottom slot first
					return ItemStack.EMPTY;
				}
			}	else {
				return ItemStack.EMPTY;
			}
		} else if (sourceSlotIndex >= FIRST_FUEL_SLOT_INDEX && sourceSlotIndex < FIRST_FUEL_SLOT_INDEX + FURNACE_SLOTS_COUNT) {
			// This is a furnace slot so merge the stack into the players inventory: try the hotbar first and then the main inventory
			//   because the main inventory slots are immediately after the hotbar slots, we can just merge with a single call
			if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			System.err.print("Invalid slotIndex:" + sourceSlotIndex);
			return ItemStack.EMPTY;
		}

		// If stack size == 0 (the entire stack was moved) set slot contents to null
		if (sourceStack.getCount() == 0) {
			sourceSlot.putStack(ItemStack.EMPTY);
		} else {
			sourceSlot.onSlotChanged();
		}

		sourceSlot.onTake(player, sourceStack);  // onPickupFromSlot()
		return copyOfSourceStack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return tileGrinder.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		boolean allFieldsHaveChanged = false;
		boolean fieldHasChanged [] = new boolean[tileGrinder.getFieldCount()];
		if (cachedFields == null) {
			cachedFields = new int[tileGrinder.getFieldCount()];
			allFieldsHaveChanged = true;
		}
		for (int i = 0; i < cachedFields.length; ++i) {
			if (allFieldsHaveChanged || cachedFields[i] != tileGrinder.getField(i)) {
				cachedFields[i] = tileGrinder.getField(i);
				fieldHasChanged[i] = true;
			}
		}

		// go through the list of listeners (players using this container) and update them if necessary
		for (IContainerListener listener : this.listeners) {
			for (int fieldID = 0; fieldID < tileGrinder.getFieldCount(); ++fieldID) {
				if (fieldHasChanged[fieldID]) {
					// Note that although sendWindowProperty takes 2 ints on a server these are truncated to shorts
					listener.sendWindowProperty(this, fieldID, cachedFields[fieldID]);
				}
			}
		}
	}
	
	public class SlotFuel extends Slot {
		public SlotFuel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		// if this function returns false, the player won't be able to insert the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileGrinder.isItemValidForFuelSlot(stack);
		}
	}
	public class SlotGrindableInput extends Slot {
		public SlotGrindableInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		// if this function returns false, the player won't be able to insert the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileGrinder.isItemValidForInputSlot(stack);
		}
	}
	public class SlotCanInput extends Slot {
		public SlotCanInput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		// if this function returns false, the player won't be able to insert the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileGrinder.isItemValidForCanSlot(stack);
		}
	}
	public class SlotOutput extends Slot {
		public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		// if this function returns false, the player won't be able to insert the given item into this slot
		@Override
		public boolean isItemValid(ItemStack stack) {
			return TileGrinder.isItemValidForOutputSlot(stack);
		}
	}
}