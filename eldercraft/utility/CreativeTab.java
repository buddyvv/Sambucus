package sambucus.eldercraft.utility;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import sambucus.eldercraft.initialization.ItemInitialization;

public class CreativeTab extends CreativeTabs {

	public CreativeTab(String label) 
	{
		super("elderCraft");
		this.setBackgroundImageName("items.png");
	}
	
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(ItemInitialization.POISONGLAND);
	}
	
	
}

