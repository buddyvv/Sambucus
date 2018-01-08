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
	private final Map<ItemStack, Integer> grindingV1List = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack, Integer> grindingV2List = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack, Integer> grindingV3List = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack, Integer> grindCostList = Maps.<ItemStack, Integer>newHashMap();
	private final Map<ItemStack, Boolean> canCheckList = Maps.<ItemStack, Boolean>newHashMap();
	
    public Map<ItemStack,ItemStack> getGrindingR1List(){
        return this.grindingR1List;
    }
    public Map<ItemStack,ItemStack> getGrindingR2List(){
        return this.grindingR2List;
    }
    public Map<ItemStack,ItemStack> getGrindingR3List(){
        return this.grindingR3List;
    }
    public Map<ItemStack, Integer> grindingV1List(){
    	return this.grindingV1List;
    }
    public Map<ItemStack, Integer> grindingV2List(){
    	return this.grindingV2List;
    }
    public Map<ItemStack, Integer> grindingV3List(){
    	return this.grindingV3List;
    }
    public Map<ItemStack, Integer> grindCostList(){
    	return this.grindCostList;
    }
    public Map<ItemStack, Boolean> canCheckList(){
    	return this.canCheckList;
    }
	public static GrinderManager instance() {
		return GRINDING_BASE;
	}
	private GrinderManager() {
		//the place the list is to be written
		this.addGinderRecipe(new ItemStack(Items.BEEF), 400, false,
				new ItemStack(ItemInitialization.PATTYRAWBEEF), 350,
				new ItemStack(Items.DYE , 15), 175,
				new ItemStack(Items.LEATHER), 100);
		
	}
	public void addGinderRecipe(
			ItemStack input, int fuel, boolean hasCan,
			ItemStack output1, int output1Volume,
			ItemStack output2, int output2Volume,
			ItemStack output3, int output3Volume) {
		if (getGrindingResult(input) != ItemStack.EMPTY){
			net.minecraftforge.fml.common.FMLLog.log.info("Ignored grinding recipe with conflicting input: {} = {}", input, output1);
			return;
		}
		this.grindingR1List.put(input, output1);
		this.grindingR2List.put(input, output2);
		this.grindingR3List.put(input, output3);
		this.grindingV1List.put(input, output1Volume);
		this.grindingV2List.put(input, output2Volume);
		this.grindingV3List.put(input, output3Volume);
		this.grindCostList.put(input, fuel);
		this.canCheckList.put(input, hasCan);
	}
	public ItemStack getGrindingResult(ItemStack stack){
		//TODO in order to send the full result over a more complete function will need to be made 
		//that will pass ALL the results and volumes rather than just the one
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