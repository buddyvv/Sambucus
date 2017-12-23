package sambucus.eldercraft.objects.tiles;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileGrinder extends TileEntity implements IInventory {
	//this will need to do some of the work to make the grinder function
	//will also need to keep the items but that is for after it is alive
	//will need to use the grinder manager to make this a bit nicer
	
	private final int intFuelMax = 1000;
	private final int intFuelMin = 0;
	private int intFuelHave = 0;
	private ItemStack[] itemStacks;
	private final int NUMBER_OF_SLOTS = 6;//fuel in,item in 3 item out+over flow
	
	public static void initialize() {
		GameRegistry.registerTileEntity(TileGrinder.class ,"EC0:Grinder");
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
		//TODO is not saving state
		parentNBTTagCompound.setInteger("havefuel", intFuelHave);
		super.writeToNBT(parentNBTTagCompound); // The super call is required to save the tiles location

		
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {}
	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}
	@Override
	public void clear() {
		Arrays.fill(itemStacks, ItemStack.EMPTY);
	}
}
