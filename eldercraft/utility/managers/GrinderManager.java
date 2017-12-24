package sambucus.eldercraft.utility.managers;

import gnu.trove.map.hash.THashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import sambucus.eldercraft.initialization.ItemInitialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public class GrinderManager {
	private static final GrinderManager GRINDING_BASE = new GrinderManager();
	
	private final Map<ItemStack, ItemStack> grindingR1List = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, ItemStack> grindingR2List = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, ItemStack> grindingR3List = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack,Integer>grindingV1List = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack,Integer>grindingV2List = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack,Integer>grindingV3List = Maps.<ItemStack, Integer>newHashMap();
	
    public Map<ItemStack,ItemStack> getGrindingR1List(){
        return this.grindingR1List;
    }
    public Map<ItemStack,ItemStack> getGrindingR2List(){
        return this.grindingR2List;
    }
    public Map<ItemStack,ItemStack> getGrindingR3List(){
        return this.grindingR3List;
    }
	
	public static GrinderManager instance() {
		return GRINDING_BASE;
	}
	
	private GrinderManager() {
		this.addGinderRecipe(new ItemStack(Items.BEEF), 400, false,
				new ItemStack(ItemInitialization.PATTYBEEFRAW), 350,
				new ItemStack(Items.BONE), 175,
				new ItemStack(Items.LEATHER), 100);
	}
	
	

	//TODO the whole fuckin thing
// ( input, fuel use , out1, out 1 amount, out2, out 2 amount , out3 , out 3 amount,can check)
// fuel value are from furnace
	
//this needs a lot of work and some more thought just got the basic crap here
	//still needs the block class
	//still needs the tile class
	//still needs a gui
	//still need to understand this mess
	
	
	public void addGinderRecipe(
		ItemStack input, int fuel, boolean hasCan,
		ItemStack output1, int output1Volume,
		ItemStack Output2, int output2Volume,
		ItemStack Output3, int output3Volume) {
		
		//space for a list, I have no idea what the hell i'm doing...

			
	}
	
	public ItemStack getGrindingResult(ItemStack stack){
        for (Entry<ItemStack, ItemStack> entry : this.grindingR1List.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2){
        return stack2.getItem() == stack1.getItem() &&
        		(stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
	
}
