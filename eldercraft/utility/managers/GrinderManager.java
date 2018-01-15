package sambucus.eldercraft.utility.managers;

import gnu.trove.map.hash.THashMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
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
	private GrinderManager(){
		//the place the list is to be written
		this.addGinderRecipe(Items.BEEF, 400, false,
				new ItemStack(ItemInitialization.PATTYRAWBEEF), 350,
				new ItemStack(Items.DYE , 1, 15), 175,
				new ItemStack(Items.LEATHER), 100);
		
		this.addGinderRecipe(Items.PORKCHOP, 200, false,
				new ItemStack(ItemInitialization.PATTYRAWPORK), 225,
				new ItemStack(Items.DYE , 1, 15), 60,
				new ItemStack(Items.LEATHER), 30);
		
		this.addGinderRecipe(Items.CHICKEN, 300, false,
				new ItemStack(ItemInitialization.PATTYRAWCHICKEN), 100,
				new ItemStack(Items.DYE , 1, 15), 80,
				new ItemStack(Items.WHEAT_SEEDS), 100);//change it to random seed type later
		
		this.addGinderRecipe(Items.RABBIT, 100, false,
				new ItemStack(ItemInitialization.PATTYRAWLEAN), 100,
				new ItemStack(Items.DYE , 1, 15), 10,
				new ItemStack(Items.RABBIT_FOOT), 1);
		
		this.addGinderRecipe(Items.MUTTON, 200, false,
				new ItemStack(ItemInitialization.PATTYRAWMUTTON), 175,
				new ItemStack(Items.DYE, 1, 15), 30,
				ItemStack.EMPTY, 0);
		
		this.addGinderRecipeFinal(new ItemStack(Items.FISH , 1, 0), 100, false,//normal
				new ItemStack(ItemInitialization.PATTYRAWFISH), 100,
				new ItemStack(Items.DYE ,1, 15), 50,
				new ItemStack(ItemInitialization.FISHSCALES), 10);
		
		this.addGinderRecipeFinal(new ItemStack(Items.FISH , 1, 1), 200, false,//salmon
				new ItemStack(ItemInitialization.PATTYRAWFISH), 125,
				new ItemStack(Items.DYE , 1, 15), 80,
				new ItemStack(ItemInitialization.FISHSCALES), 80);
		
		this.addGinderRecipeFinal(new ItemStack(Items.FISH , 1, 2), 100, false,//clown
				new ItemStack(ItemInitialization.PATTYRAWFISH), 75,
				new ItemStack(Items.DYE , 1, 15), 25,
				new ItemStack(ItemInitialization.OILYSKIN), 75);
		
		this.addGinderRecipeFinal(new ItemStack(Items.FISH , 1, 3), 100, false,//puff
				new ItemStack(ItemInitialization.PATTYRAWFISH), 80,
				new ItemStack(Items.DYE , 1, 15), 10,
				new ItemStack(ItemInitialization.POISONGLAND), 100);
		
		this.addGinderRecipe(ItemInitialization.CLUSTEROFBERRIES, 100, true,//test
				new ItemStack(ItemInitialization.MASHGROWING), 100,
				new ItemStack(ItemInitialization.JUICEBURNTCAN), 100,
				ItemStack.EMPTY, 0);
	}
	public void addGinderRecipe(Item input, int fuel, boolean hasCan,
			ItemStack output1, int output1Volume,
			ItemStack output2, int output2Volume,
			ItemStack output3, int output3Volume) {
		this.addGinderRecipeFinal(new ItemStack(input, 1, 32767), fuel, hasCan,
				output1, output1Volume,
				output2, output2Volume,
				output3, output3Volume);
	}
	
	public void addGinderRecipeFinal(
			ItemStack input, int fuel, boolean hasCan,
			ItemStack output1, int output1Volume,
			ItemStack output2, int output2Volume,
			ItemStack output3, int output3Volume){
		
		if (getGrindingR1(input) != ItemStack.EMPTY){
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
	public ItemStack getGrindingR1(ItemStack stack){
        for (Entry<ItemStack, ItemStack> entry : this.grindingR1List.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }
	public Integer getGrindingV1(ItemStack stack){
        for (Entry<ItemStack, Integer> entry : this.grindingV1List.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }
        return 0;
    }
	public ItemStack getGrindingR2(ItemStack stack){
        for (Entry<ItemStack, ItemStack> entry : this.grindingR2List.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }
	public Integer getGrindingV2(ItemStack stack){
        for (Entry<ItemStack, Integer> entry : this.grindingV2List.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }
        return 0;
    }
	public ItemStack getGrindingR3(ItemStack stack){
        for (Entry<ItemStack, ItemStack> entry : this.grindingR3List.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }
	public Integer getGrindingV3(ItemStack stack){
        for (Entry<ItemStack, Integer> entry : this.grindingV3List.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }
        return 0;
    }
	public Integer getGrindingCost(ItemStack stack){
        for (Entry<ItemStack, Integer> entry : this.grindCostList.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }
        return 0;
    }
	public Boolean getCanCheck(ItemStack stack){
        for (Entry<ItemStack, Boolean> entry : this.canCheckList.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }
        return false;
    }
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2){
        return stack2.getItem() == stack1.getItem() &&
        		(stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}