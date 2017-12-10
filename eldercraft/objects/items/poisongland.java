package sambucus.eldercraft.objects.items;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import sambucus.eldercraft.ElderCraft;
import sambucus.eldercraft.initialization.ItemInitialization;
import sambucus.eldercraft.utility.IHaveModel;

public class poisongland extends Item implements IHaveModel{

		public poisongland(String name)
		{
			ItemInitialization.ITEMS.add(this);
			setUnlocalizedName(name);
			setRegistryName(name);
			setCreativeTab(CreativeTabs.BREWING);
			
		}
	
		@Override
		public void registerModels()
		{
			ElderCraft.proxy.registerItemRenderer(this, 0, "inventory");
		}
	
}
