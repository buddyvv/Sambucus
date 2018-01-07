package sambucus.eldercraft.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {

		@Override
		public void preInit(FMLPreInitializationEvent e) 
		{
	        super.preInit(e);
	        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("ec0:block_grinder", "inventory");
	        final int DEFAULT_ITEM_SUBTYPE = 0;
	    }
		@Override
		public void registerItemRenderer(Item item, int meta, String id)
		{
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
		}
		
		@SubscribeEvent
	    public static void registerModels(ModelRegistryEvent event) 
		{
			
	    }

 
    
}