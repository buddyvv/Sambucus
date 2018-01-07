package sambucus.eldercraft.objects.tiles;

import java.util.Arrays;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sambucus.eldercraft.utility.managers.GrinderManager;

public class TileGrinder extends TileEntity implements IInventory, ITickable {
	//this will need to do some of the work to make the grinder function
	
	//FUEL THIS CAN HOLD
	private final int intFuelMax = 1000;
	private final int intFuelMin = 0;
	
	private int intFuelHave = 0;//FUEL IN THE TANK
	
	private short grindTime;//The number of ticks the current item has been cooking
	
	private ItemStack[] itemStacks;
	
	private int cachedNumberOfBurningSlots = -1;

	public static final int OUTPUT_SLOTS_COUNT = 4;
	
	public static final int FUEL_SLOTS_COUNT = 1;
	public static final int TOTAL_SLOTS_COUNT = 7;
	
	public static final int GRIND_SLOT = 0;
	public static final int FUEL_SLOT = 1;
	public static final int CAN_SLOT = 2;
	public static final int FIRST_OUTPUT_SLOT = 3;
	
	/**TODO change this to work for one slot
	 * 
	 *  The number of burn ticks remaining on the current piece of fuel */
	private int [] burnTimeRemaining = new int[FUEL_SLOTS_COUNT];
	
	/** The initial fuel value of the currently burning fuel (in ticks of burn duration) */
	private int [] burnTimeInitialValue = new int[FUEL_SLOTS_COUNT];//TODO rework for 1 slot
	
	private static final short GRIND_TIME_FOR_COMPLETION = 200; //TODO time is not the same for all items need fix
	
	public TileGrinder() {
		itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];
		clear();
	}
	
	public double fractionOfFuelRemaining(int fuelSlot){
		//TODO change to 1 slot
		if (burnTimeInitialValue[fuelSlot] <= 0 ) return 0;
		double fraction = burnTimeRemaining[fuelSlot] / (double)burnTimeInitialValue[fuelSlot];
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}
	
	public int secondsOfFuelRemaining(int fuelSlot){
		//TODO change to 1 slot later
		if (burnTimeRemaining[fuelSlot] <= 0 ) {
			return 0;
		}
		return burnTimeRemaining[fuelSlot] / 20; // 20 ticks per second
	}
	
	public double fractionOfGrindTimeComplete(){
		//TODO part of the variable time rework
		double fraction = grindTime / (double)GRIND_TIME_FOR_COMPLETION;
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}
	
	private boolean canGrind() {
		return grindItem(false);//i have no idea why this needs to be here
	}
	private void grindItem() {
		grindItem(true);//or this
	}
	private boolean grindItem(boolean performSmelt) {
		//this is called every tick
		Integer suitableInputSlot = null;
		Integer firstSuitableOutputSlot = null;
		ItemStack result = ItemStack.EMPTY;  //EMPTY_ITEM
		if (!itemStacks[GRIND_SLOT].isEmpty()) {
			result = getGrindingResultForItem(itemStacks[GRIND_SLOT]);
			
			if (!result.isEmpty()) {
				// find the first suitable output slot- either empty, or with identical item that has enough space
				
				for (int outputSlot = FIRST_OUTPUT_SLOT; outputSlot < FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT; outputSlot++) {
					ItemStack outputStack = itemStacks[outputSlot];
					
					if (outputStack.isEmpty()) {
						suitableInputSlot = GRIND_SLOT;
						firstSuitableOutputSlot = outputSlot;
						
						break;
					}
					
					//a complex if statement ,can the the item go into the output slot
					if (outputStack.getItem() == result.getItem() &&
							(!outputStack.getHasSubtypes() ||
									outputStack.getMetadata() == outputStack.getMetadata())
							&& ItemStack.areItemStackTagsEqual(outputStack, result)) {
						
						int combinedSize = itemStacks[outputSlot].getCount() + result.getCount();
						
						
						if (combinedSize <= getInventoryStackLimit() && combinedSize <= itemStacks[outputSlot].getMaxStackSize()) {
							suitableInputSlot = GRIND_SLOT;
							firstSuitableOutputSlot = outputSlot;
							break;
						}
					}
				}	
			}
		}

		if (suitableInputSlot == null) {
			return false;
		}
		
		if (!performSmelt) {
			return true;
		}
		
		// alter input and output
		itemStacks[suitableInputSlot].shrink(1);  // decreaseStackSize()
		if (itemStacks[suitableInputSlot].getCount() <= 0) {
			itemStacks[suitableInputSlot] = ItemStack.EMPTY;  //getStackSize(), EmptyItem
		}
		//TODO check if a can is used and shrink that also
		if (itemStacks[firstSuitableOutputSlot].isEmpty()) {  // isEmpty()
			itemStacks[firstSuitableOutputSlot] = result.copy(); // Use deep .copy() to avoid altering the recipe
		}else{
			int newStackSize = itemStacks[firstSuitableOutputSlot].getCount() + result.getCount();
			itemStacks[firstSuitableOutputSlot].setCount(newStackSize) ;  //setStackSize(), getStackSize()
		}
		
		markDirty();
		return true;
	}
	/*
	public static void initialize() {
		GameRegistry.registerTileEntity(TileGrinder.class ,"ec0:Grinder");//might need to move
	}
	*/
	public void setFuelHave(int fuel) {
		intFuelHave = fuel;//TODO make this less stupid
	}
	
	@Override
	public NBTTagCompound getUpdateTag(){
	    NBTTagCompound nbtTagCompound = new NBTTagCompound();
	    writeToNBT(nbtTagCompound);
	    
	    return nbtTagCompound;
	}
	@Override
	public void handleUpdateTag(NBTTagCompound tag){
	    this.readFromNBT(tag);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound parentNBTTagCompound){
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save the tiles location
		
		NBTTagList dataForAllSlots = new NBTTagList();
		for (int i = 0; i < this.itemStacks.length; ++i) {
			if (!this.itemStacks[i].isEmpty()) {  //isEmpty()
				NBTTagCompound dataForThisSlot = new NBTTagCompound();
				dataForThisSlot.setByte("Slot", (byte) i);
				this.itemStacks[i].writeToNBT(dataForThisSlot);
				dataForAllSlots.appendTag(dataForThisSlot);
			}
		}
		
		// the array of hashmaps is then inserted into the parent hashmap for the container
		parentNBTTagCompound.setTag("Items", dataForAllSlots);

		// Save everything else
		parentNBTTagCompound.setShort("GrindTime", grindTime);
		parentNBTTagCompound.setTag("burnTimeRemaining", new NBTTagIntArray(burnTimeRemaining));
		parentNBTTagCompound.setTag("burnTimeInitial", new NBTTagIntArray(burnTimeInitialValue));
		
		return parentNBTTagCompound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound){
		// TODO whatever the fuck needs to be added to make this work for us ... i can't give a shit much longer
		
		super.readFromNBT(parentNBTTagCompound); // The super call is required to save and load the tiles location
		final byte NBT_TYPE_COMPOUND = 10;       // See NBTBase.createNewByType() for a listing
		NBTTagList dataForAllSlots = parentNBTTagCompound.getTagList("Items", NBT_TYPE_COMPOUND);

		Arrays.fill(itemStacks, ItemStack.EMPTY);           // set all slots to empty EMPTY_ITEM
		for (int i = 0; i < dataForAllSlots.tagCount(); ++i) {
			NBTTagCompound dataForOneSlot = dataForAllSlots.getCompoundTagAt(i);
			byte slotNumber = dataForOneSlot.getByte("Slot");
			if (slotNumber >= 0 && slotNumber < this.itemStacks.length) {
				this.itemStacks[slotNumber] = new ItemStack(dataForOneSlot);
			}
		}

		// Load everything else.  Trim the arrays (or pad with 0) to make sure they have the correct number of elements
		grindTime = parentNBTTagCompound.getShort("GrindTime");
		burnTimeRemaining = Arrays.copyOf(parentNBTTagCompound.getIntArray("burnTimeRemaining"), FUEL_SLOTS_COUNT);
		burnTimeInitialValue = Arrays.copyOf(parentNBTTagCompound.getIntArray("burnTimeInitial"), FUEL_SLOTS_COUNT);
		cachedNumberOfBurningSlots = -1;
	}
	
	@Override
	@Nullable//TODO
	public SPacketUpdateTileEntity getUpdatePacket(){//TODO
		//TODO crash issue in area!!
		//NBTTagCompound updateTagDescribingTileEntityState = getUpdateTag();TODO
		//TODO work on this area!
		final int METADATA = 0;//TODO
		//TODO this area has major issues
		//TODO return new SPacketUpdateTileEntity(this.pos, METADATA, updateTagDescribingTileEntityState);
		return null;//TODO THIS ^ WILL CALSE A CRASH more info is needed on how the fuck all this is ment to work null for now
		//TODO something here needs work
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound updateTagDescribingTileEntityState = pkt.getNbtCompound();
		handleUpdateTag(updateTagDescribingTileEntityState);
	}

	@Override
	public String getName() {
		return "container.ec0_inventory_grinder.name";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return itemStacks.length;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : itemStacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return itemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int count) {
		ItemStack itemStackInSlot = getStackInSlot(slotIndex);
		
		if (itemStackInSlot.isEmpty()) {
			return ItemStack.EMPTY;
		}

		ItemStack itemStackRemoved;
		if (itemStackInSlot.getCount() <= count) { //getStackSize
			itemStackRemoved = itemStackInSlot;
			
			setInventorySlotContents(slotIndex, ItemStack.EMPTY);
		} else {
			itemStackRemoved = itemStackInSlot.splitStack(count);
			
			if (itemStackInSlot.getCount() == 0) {
				setInventorySlotContents(slotIndex, ItemStack.EMPTY);
			}
		}
		markDirty();
		return itemStackRemoved;
	}

	@Override
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (!itemStack.isEmpty()) setInventorySlotContents(slotIndex, ItemStack.EMPTY);
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		itemStacks[index] = stack;
		if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		}
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		
		if (player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ) {
			return true;
		}
		return false;
		//return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}
	
	private static final byte GRIND_FIELD_ID = 0;
	private static final byte BURN_TIME_REMAINING_FIELD_ID = 1;
	private static final byte BURN_TIME_INITIAL_FIELD_ID = 2;
	private static final byte POWER_IN_TANK_ID = 3;
	private static final byte NUMBER_OF_FIELDS = 4;
	
	@Override
	public int getField(int id) {
		// TODO make this work for the grinder
		if (id == GRIND_FIELD_ID) {
			return grindTime;
		}
		if (id == BURN_TIME_REMAINING_FIELD_ID ) {
			//return burnTimeRemaining;
		}
		if (id == BURN_TIME_INITIAL_FIELD_ID) {
			//return burnTimeInitialValue;
		}
		//TODO this needs to be fixed
		//System.err.println("the ID in getField is wrong " + id + " is not accounted for");
		return 0;
	}
	
	@Override
	public void setField(int id, int value) {
		// TODO this don't look right...copy pasta code
		
		if (id == GRIND_FIELD_ID) {
			grindTime = (short)value;
		} else if (id == BURN_TIME_REMAINING_FIELD_ID ) {
			burnTimeRemaining[id - BURN_TIME_REMAINING_FIELD_ID] = value;
		} else if (id == BURN_TIME_INITIAL_FIELD_ID) {
			burnTimeInitialValue[id - BURN_TIME_INITIAL_FIELD_ID] = value;
		} else {
			System.err.println("Invalid field ID in TileInventorySmelting.setField:" + id);
		}
	}
	
	@Override
	public int getFieldCount() {
		//number of fields
		return NUMBER_OF_FIELDS;
	}
	
	public static ItemStack getGrindingResultForItem(ItemStack stack) {
		return GrinderManager.instance().getGrindingResult(stack);
	}
	
	@Override
	public void update() {
		// TODO the thing that is updated each tick	
		//add fuel to "tank"
		
		//grind items and remove fuel from tank
		
		// If there is nothing to smelt or there is no room in the output, reset cookTime and return
		if (canGrind()) {
			System.err.println("it is trying to grind a thing");
			int numberOfFuelBurning = burnFuel();
			
			// If fuel is available, keep cooking the item, otherwise start "uncooking" it at double speed
			if (numberOfFuelBurning > 0) {//TODO replace this with fuel tank info
				grindTime += numberOfFuelBurning;
			}else{
				grindTime -= 2;
			}
			
			if (grindTime < 0) grindTime = 0;
			
			// If cookTime has reached maxCookTime smelt the item and reset cookTime
			if (grindTime >= GRIND_TIME_FOR_COMPLETION) {
				grindItem();
				grindTime = 0;
			}
		}	else {
			grindTime = 0;
		}
		
		// when the number of burning slots changes, we need to force the block to re-render, otherwise the change in
		//   state will not be visible.  Likewise, we need to force a lighting recalculation.
		// The block update (for renderer) is only required on client side, but the lighting is required on both, since
		//    the client needs it for rendering and the server needs it for crop growth etc
		int numberBurning = numberOfBurningFuelSlots();
		if (cachedNumberOfBurningSlots != numberBurning) {
			cachedNumberOfBurningSlots = numberBurning;
			if (world.isRemote) {
				IBlockState iblockstate = this.world.getBlockState(pos);
				final int FLAGS = 3;  // I'm not sure what these flags do, exactly.
				world.notifyBlockUpdate(pos, iblockstate, iblockstate, FLAGS);
			}
			world.checkLightFor(EnumSkyBlock.BLOCK, pos);
		}
	}

	/**TODO ONE fuel slot
	 * 	for each fuel slot: decreases the burn time, checks if burnTimeRemaining = 0 and tries to consume a new piece of fuel if one is available
	 * @return the number of fuel slots which are burning
	 */
	private int burnFuel() {
		//TODO rework this to only need one slot
		int burningCount = 0;
		boolean inventoryChanged = false;
		// Iterate over all the fuel slots
		for (int i = 0; i < FUEL_SLOTS_COUNT; i++) {
			int fuelSlotNumber = i + FUEL_SLOT;
			if (burnTimeRemaining[i] > 0) {
				--burnTimeRemaining[i];
				++burningCount;
			}
			if (burnTimeRemaining[i] == 0) {
				if (!itemStacks[fuelSlotNumber].isEmpty() && getItemBurnTime(itemStacks[fuelSlotNumber]) > 0) {  // isEmpty()
					// If the stack in this slot is not null and is fuel, set burnTimeRemaining & burnTimeInitialValue to the
					// item's burn time and decrease the stack size
					burnTimeRemaining[i] = burnTimeInitialValue[i] = getItemBurnTime(itemStacks[fuelSlotNumber]);
					itemStacks[fuelSlotNumber].shrink(1);  // decreaseStackSize()
					++burningCount;
					inventoryChanged = true;
					// If the stack size now equals 0 set the slot contents to the items container item. This is for fuel
					// items such as lava buckets so that the bucket is not consumed. If the item dose not have
					// a container item getContainerItem returns null which sets the slot contents to null
					if (itemStacks[fuelSlotNumber].getCount() == 0) {//TODO for fuel slots
						itemStacks[fuelSlotNumber] = itemStacks[fuelSlotNumber].getItem().getContainerItem(itemStacks[fuelSlotNumber]);
					}
				}
			}
		}
		if (inventoryChanged) markDirty();
		return burningCount;
	}

	/**
	 * Get the number of slots which have fuel burning in them.
	 * @return number of slots with burning fuel, 0 - FUEL_SLOTS_COUNT
	 */
	public int numberOfBurningFuelSlots(){
		//TODO rework this as we only have one fuel slot
		int burningCount = 0;
		for (int burnTime : burnTimeRemaining) {
			if (burnTime > 0) {
				++burningCount;
			}
		}
		return burningCount;
	}
	
	@Override
	public void clear() {
		Arrays.fill(itemStacks, ItemStack.EMPTY);
	}
	
	@Override
	public void openInventory(EntityPlayer player) {}//unused
	@Override
	public void closeInventory(EntityPlayer player) {}//unused

	public static boolean isItemValidForFuelSlot(ItemStack stack) {
		if (getItemBurnTime(stack) >= 1) {
			return true;
		}
		return false;
	}

	public static boolean isItemValidForOutputSlot(ItemStack stack) {
		//no placing items in the wrong end
		return false;
	}

	public static boolean isItemValidForInputSlot(ItemStack stack) {
		//TODO  check if we can grind it
		return true;
	}

	public static boolean isItemValidForCanSlot(ItemStack stack) {
		//TODO check if it is a can
		return true;
	}
	// returns the number of ticks the given item will burn. Returns 0 if the given item is not a valid fuel
	public static short getItemBurnTime(ItemStack stack){//rather than use it as time use a ++fuel  and  --fuel when doing a task
		int burntime = TileEntityFurnace.getItemBurnTime(stack);  // just use the vanilla values
		return (short)MathHelper.clamp(burntime, 0, Short.MAX_VALUE);
	}
}