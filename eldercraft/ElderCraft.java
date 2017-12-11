package sambucus.eldercraft;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sambucus.eldercraft.proxy.CommonProxy;
import sambucus.eldercraft.utility.CreativeTab;
import sambucus.eldercraft.utility.Reference;


@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, useMetadata = true)		//References to sambucus.eldercraft.utility.reference to gain this information

public class ElderCraft {
	
	
	
	@SidedProxy(clientSide = Reference.Client, serverSide = Reference.Common)			//Renaming a link to sambucus.eldercraft.utility.reference for the client and common proxy
    public static CommonProxy proxy;

    @Mod.Instance
    public static ElderCraft instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
    
    public static final CreativeTabs eldercrafttab = new CreativeTab("eldercrafttab");
    
}
