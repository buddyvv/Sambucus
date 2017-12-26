package sambucus.eldercraft.objects.tiles;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sambucus.eldercraft.utility.managers.GrinderManager;

public class TileGrinder extends TileEntity implements IInventory, ITickable {
	//this will need to do some of the work to make the grinder function
	//will also need a fucking GUI
	//will need to use the grinder manager to make this a bit nicer
	
	private final int intFuelMax = 1000;
	private final int intFuelMin = 0;
	private int intFuelHave = 0;
	private ItemStack[] itemStacks;
	public static final int slotsIn = 1;
	public static final int slotsFuel = 1;
	public static final int slotsCan = 1;
	public static final int slotsOut = 4;
	public static final int slotsTotal = slotsIn + slotsFuel + slotsCan + slotsOut;
	
	public static void initialize() {
		GameRegistry.registerTileEntity(TileGrinder.class ,"EC0:Grinder");//might need to move
	}
	
	public void setFuelHave(int fuel) {
		intFuelHave = fuel;
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
		//TODO is not saving state , still.
		parentNBTTagCompound.setInteger("havefuel", intFuelHave);
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save the tiles location
		//will need to add inventory to here i think
		return parentNBTTagCompound;
	}
	@Override
	public void readFromNBT(NBTTagCompound parentNBTTagCompound){
		int fuelcheck = 0;
		super.readFromNBT(parentNBTTagCompound); // The super call is required to load the tiles location
		// important rule: never trust the data you read from NBT, make sure it can't cause a crash
		if (parentNBTTagCompound.hasKey("havefuel",3)) {
			fuelcheck = parentNBTTagCompound.getInteger("havefuel");
			if(fuelcheck < 1) {
				fuelcheck = 0;
			}
		}
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
			if (!itemstack.isEmpty()) {  // isEmpty()
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
	public ItemStack decrStackSize(int index, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override //don't fully understand this
	public ItemStack removeStackFromSlot(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (!itemStack.isEmpty()) setInventorySlotContents(slotIndex, ItemStack.EMPTY);  //isEmpty(), EMPTY_ITEM
		return itemStack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this) return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO this should prevent useless items from going in maybe...
		return false;
	}
	
	private static final byte a = 0;
	

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	//private static final byte GRIND_FIELD_ID = 0;
	//private static final byte FIRST_BURN_TIME_REMAINING_FIELD_ID = 1;
	//private static final byte FIRST_BURN_TIME_INITIAL_FIELD_ID = FIRST_BURN_TIME_REMAINING_FIELD_ID + (byte)FUEL_SLOTS_COUNT;
	//private static final byte NUMBER_OF_FIELDS = FIRST_BURN_TIME_INITIAL_FIELD_ID + (byte)FUEL_SLOTS_COUNT;
	
	@Override
	public void setField(int id, int value) {
		// TODO Too much to deal with at 6am
		/*
		if (id == GRIND_FIELD_ID) {
			return cookTime;
		}
		if (id >= FIRST_BURN_TIME_REMAINING_FIELD_ID && id < FIRST_BURN_TIME_REMAINING_FIELD_ID + FUEL_SLOTS_COUNT) {
			return burnTimeRemaining[id - FIRST_BURN_TIME_REMAINING_FIELD_ID];
		}
		if (id >= FIRST_BURN_TIME_INITIAL_FIELD_ID && id < FIRST_BURN_TIME_INITIAL_FIELD_ID + FUEL_SLOTS_COUNT) {
			return burnTimeInitialValue[id - FIRST_BURN_TIME_INITIAL_FIELD_ID];
		}
		System.err.println("Invalid field ID in TileInventorySmelting.getField:" + id);
		*/
		return;
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}
	
	public static ItemStack getSmeltingResultForItem(ItemStack stack) {
		return GrinderManager.instance().getGrindingResult(stack);
	}
	//public static ItemStack getSmeltingResultForItem(ItemStack stack) { return FurnaceRecipes.instance().getSmeltingResult(stack); }
	@Override
	public void update() {
		// TODO the thing that is updated each tick	
		//add fuel to "tank"
		
		//grind items and remove fuel from tank
		
		//when done do the thing and give the outputs
		
	}
	
	@Override
	public void clear() {
		Arrays.fill(itemStacks, ItemStack.EMPTY);
	}
	@Override
	public void openInventory(EntityPlayer player) {}
	@Override
	public void closeInventory(EntityPlayer player) {}

	public static boolean isItemValidForFuelSlot(ItemStack stack) {
		// TODO check if it can burn? could use getItemBurnTime(ItemStack) and see if it is not 0.
		return true;
	}

	public static boolean isItemValidForOutputSlot(ItemStack stack) {
		//no placing items in the wrong end
		return false;
	}

	public static boolean isItemValidForInputSlot(ItemStack stack) {
		// TODO check if we can grind it
		return true;
	}

	public static boolean isItemValidForCanSlot(ItemStack stack) {
		// TODO if it is a can
		return true;
	}
	// returns the number of ticks the given item will burn. Returns 0 if the given item is not a valid fuel
	public static short getItemBurnTime(ItemStack stack){//we will probably rather than use it as time use a ++to the fuel we have and a -- when doing a task
		int burntime = TileEntityFurnace.getItemBurnTime(stack);  // just use the vanilla values
		return (short)MathHelper.clamp(burntime, 0, Short.MAX_VALUE);
	}
}
