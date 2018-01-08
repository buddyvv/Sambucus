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

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, useMetadata = true)
public class ElderCraft {
	 public static final CreativeTabs eldercrafttab = new CreativeTab("eldercrafttab");
	 public static Logger logger;
	 
	@SidedProxy(clientSide = Reference.Client, serverSide = Reference.Common)
    public static CommonProxy proxy;

    @Mod.Instance
    public static ElderCraft instance;

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
}
