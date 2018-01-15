package sambucus.eldercraft.objects.tiles;

import java.util.Arrays;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
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
import sambucus.eldercraft.initialization.ItemInitialization;
import sambucus.eldercraft.utility.managers.GrinderManager;

public class TileGrinder extends TileEntity implements IInventory, ITickable {
	/*/TODO part of the fuel storage
	private final int FUEL_MAX = 1000;//may be a config thing later
	private int intFuelHave = 0;//FUEL IN THE TANK
	*/
	private ItemStack[] itemStacks;
	private boolean cachedBurn = false;
	public static final int OUTPUT_SLOTS_COUNT = 4;
	public static final int FUEL_SLOTS_COUNT = 1;
	public static final int TOTAL_SLOTS_COUNT = 7;
	public static final int FUEL_SLOT = 0;
	public static final int GRIND_SLOT = 1;
	public static final int CAN_SLOT = 2;
	public static final int FIRST_OUTPUT_SLOT = 3;
	private int burnTimeRemaining = 0;//The number of burn ticks remaining on the current piece of fuel
	private int burnTimeInitialValue;//The initial fuel value of the currently burning fuel (in ticks of burn duration)
	private short grindTime;
	private static final short GRIND_TIME_FOR_COMPLETION = 200; //TODO time is not the same for all items need fix
	private static final byte GRIND_FIELD_ID = 0;
	private static final byte BURN_TIME_REMAINING_FIELD_ID = 1;
	private static final byte BURN_TIME_INITIAL_FIELD_ID = 2;
	private static final byte POWER_IN_TANK_ID = 3;
	private static final byte NUMBER_OF_FIELDS = 4;
	
	public TileGrinder() {
		itemStacks = new ItemStack[TOTAL_SLOTS_COUNT];
		clear();
	}
	//active area
	@Override
	public void update() {
		// TODO the thing that is updated each tick	
		//add fuel to "tank"
		if (canGrind()) {//change this to if there is room in the tank
			boolean fuelBurning = burnFuel();
			// If fuel is available, keep cooking the item, otherwise start "uncooking" it at double speed
			if (fuelBurning) {//TODO replace this with fuel tank info
				grindTime++;//will need to change this to adding to tank
			}else{
				grindTime -= 2;
			}
			if (grindTime < 0) {
				grindTime = 0;
			}
			if (grindTime >= GRIND_TIME_FOR_COMPLETION) {
				grindItem();
				grindTime = 0;
			}
		}else{
			grindTime = 0;
		}
		
		// when burning  changes, we need to force the block to re-render, otherwise the change in
		//   state will not be visible.  Likewise, we need to force a lighting recalculation.
		// The block update (for renderer) is only required on client side, but the lighting is required on both, since
		//    the client needs it for rendering and the server needs it for crop growth etc
		boolean burning = false;
		if (cachedBurn != burning) {
			cachedBurn = burning;
			if (world.isRemote) {
				IBlockState iblockstate = this.world.getBlockState(pos);
				final int FLAGS = 3;  // I'm not sure what these flags do, exactly.
				world.notifyBlockUpdate(pos, iblockstate, iblockstate, FLAGS);
			}
			world.checkLightFor(EnumSkyBlock.BLOCK, pos);
		}
	}
	private boolean burnFuel() {
		boolean burning = false;
		boolean inventoryChanged = false;
		if (burnTimeRemaining > 0) {
			--burnTimeRemaining;
			burning = true;
		}
		if (burnTimeRemaining == 0) {
			if (!itemStacks[FUEL_SLOT].isEmpty()){
				burnTimeRemaining = burnTimeInitialValue = getItemBurnTime(itemStacks[FUEL_SLOT]);
				itemStacks[FUEL_SLOT].shrink(1);
				burning = true;
				inventoryChanged = true;
				if (itemStacks[FUEL_SLOT].getCount() == 0) {
					itemStacks[FUEL_SLOT] = itemStacks[FUEL_SLOT].getItem().getContainerItem(itemStacks[FUEL_SLOT]);
				}
			}
		}
		if (inventoryChanged) {
			markDirty();
		}
		return burning;
	}
	private boolean canGrind() {
		return grindItem(false);
	}
	private void grindItem() {
		grindItem(true);
	}
	private boolean grindItem(boolean performGrind) {
		//this is called every tick
		//TODO have all the outputs show up.
		Integer suitableInputSlot = null;
		Integer firstSuitableOutputSlot = null;
		Integer volume1 = null;
		Integer volume2 = null;
		Integer volume3 = null;
		ItemStack result1 = ItemStack.EMPTY;  //EMPTY_ITEM
		ItemStack result2 = ItemStack.EMPTY;
		ItemStack result3 = ItemStack.EMPTY;
		if (!itemStacks[GRIND_SLOT].isEmpty()) {
			result1 = getGrindingResultR1(itemStacks[GRIND_SLOT]);
			result2 = getGrindingResultR2(itemStacks[GRIND_SLOT]);
			result3 = getGrindingResultR3(itemStacks[GRIND_SLOT]);
			//TODO may need this later --- System.err.println("there is an item in the grind slot " + itemStacks[GRIND_SLOT].getDisplayName() + " and it will make " + result.getDisplayName());
			if (!result1.isEmpty()) {
				for (int outputSlot = FIRST_OUTPUT_SLOT; outputSlot < FIRST_OUTPUT_SLOT + OUTPUT_SLOTS_COUNT; outputSlot++) {
					ItemStack outputStack = itemStacks[outputSlot];
					if (outputStack.isEmpty()) {
						suitableInputSlot = GRIND_SLOT;
						firstSuitableOutputSlot = outputSlot;
						break;
					}
					if (outputStack.getItem() == result1.getItem() &&
							(!outputStack.getHasSubtypes() ||
									outputStack.getMetadata() == outputStack.getMetadata())
							&& ItemStack.areItemStackTagsEqual(outputStack, result1)) {
						
						int combinedSize = itemStacks[outputSlot].getCount() + result1.getCount();
						
						
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
		if (!performGrind) {
			return true;
		}
		
		// alter input and output
		itemStacks[suitableInputSlot].shrink(1);
		if (itemStacks[suitableInputSlot].getCount() <= 0) {
			itemStacks[suitableInputSlot] = ItemStack.EMPTY;
		}
		// check if a can is used and shrink that also
		if (itemStacks[firstSuitableOutputSlot].isEmpty()) {
			itemStacks[firstSuitableOutputSlot] = result1.copy();
		}else{
			int newStackSize = itemStacks[firstSuitableOutputSlot].getCount() + result1.getCount();
			itemStacks[firstSuitableOutputSlot].setCount(newStackSize);
		}
		markDirty();
		return true;
	}
	//output block
	public static ItemStack getGrindingResultR1(ItemStack stack) {
		return GrinderManager.instance().getGrindingR1(stack);
	}
	public static int getGrindingResultV1(ItemStack stack) {
		return GrinderManager.instance().getGrindingV1(stack);
	}
	public static ItemStack getGrindingResultR2(ItemStack stack) {
		return GrinderManager.instance().getGrindingR2(stack);
	}
	public static int getGrindingResultV2(ItemStack stack) {
		return GrinderManager.instance().getGrindingV2(stack);
	}
	public static ItemStack getGrindingResultR3(ItemStack stack) {
		return GrinderManager.instance().getGrindingR3(stack);
	}
	public static int getGrindingResultV3(ItemStack stack) {
		return GrinderManager.instance().getGrindingV3(stack);
	}
	//NBT area
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
		parentNBTTagCompound.setTag("burnTimeRemaining", new NBTTagInt(burnTimeRemaining));
		parentNBTTagCompound.setTag("burnTimeInitial", new NBTTagInt(burnTimeInitialValue));
		
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
		burnTimeRemaining = parentNBTTagCompound.getInteger("burnTimeRemaining");
		burnTimeInitialValue = parentNBTTagCompound.getInteger("burnTimeInitial");
		cachedBurn = false;
	}
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket(){//was a crash from here
		NBTTagCompound updateTagDescribingTileEntityState = getUpdateTag();
		final int METADATA = 0;
		return new SPacketUpdateTileEntity(this.pos, METADATA, updateTagDescribingTileEntityState);
	}
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound updateTagDescribingTileEntityState = pkt.getNbtCompound();
		handleUpdateTag(updateTagDescribingTileEntityState);
	}
	
	public double fractionOfFuelRemaining(){
		if (burnTimeInitialValue <= 0 ) {
			return 0;
		}
		double fraction = burnTimeRemaining / (double)burnTimeInitialValue;
		return MathHelper.clamp(fraction, 0.0, 1.0);
	}
	public int secondsOfFuelRemaining(int fuelSlot){
		if (burnTimeRemaining <= 0 ) {
			return 0;
		}
		return burnTimeRemaining / 20; // 20 ticks per second
	}
	public double fractionOfGrindTimeComplete(){
		//TODO part of the variable time rework
		double fraction = grindTime / (double)GRIND_TIME_FOR_COMPLETION;
		return MathHelper.clamp(fraction, 0.0, 1.0);
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
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		}
		if (player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ) {
			return true;
		}
		return false;
	}
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}
	
	@Override
	public int getField(int id) {
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
			burnTimeRemaining = value;
		} else if (id == BURN_TIME_INITIAL_FIELD_ID) {
			burnTimeInitialValue = value;
		} else {
			System.err.println("Invalid field ID in TileInventorySmelting.setField:" + id);//still useful for now
		}
	}
	@Override
	public int getFieldCount() {
		return NUMBER_OF_FIELDS;
	}
	
	public static boolean isItemValidForFuelSlot(ItemStack stack) {
		if (getItemBurnTime(stack) >= 1) {
			return true;
		}
		return false;
	}
	public static boolean isItemValidForOutputSlot(ItemStack stack) {
		return false;//no placing items in the wrong end
	}
	public static boolean isItemValidForInputSlot(ItemStack stack) {
		return true;
	}
	public static boolean isItemValidForCanSlot(ItemStack stack) {
		if(stack.areItemsEqualIgnoreDurability(stack, new ItemStack(ItemInitialization.IRONCAN)) ) {//later we will check for other types of cans
			return true;
		}
		return false;
	}
	public static short getItemBurnTime(ItemStack stack){//rather than use it as time use a ++fuel  and  --fuel when doing a task
		int burntime = TileEntityFurnace.getItemBurnTime(stack);  // just use the vanilla values
		return (short)MathHelper.clamp(burntime, 0, Short.MAX_VALUE);
	}
	@Override
	public void clear() {
		Arrays.fill(itemStacks, ItemStack.EMPTY);
	}
	@Override
	public void openInventory(EntityPlayer player) {}//unused
	@Override
	public void closeInventory(EntityPlayer player) {}//unused
}