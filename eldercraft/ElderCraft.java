package sambucus.eldercraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sambucus.eldercraft.proxy.CommonProxy;

import org.apache.logging.log4j.Logger;


@Mod(modid = ElderCraft.MODID, name = ElderCraft.MODNAME, version = ElderCraft.VERSION, useMetadata = true)
public class ElderCraft {
	
	public static final String MODID = "ec0";
	public static final String MODNAME = "ElderCraft";
	public static final String VERSION = "0.0.1";
	
	@SidedProxy(clientSide = "sambucus.eldercraft.proxy.ClientProxy", serverSide = "sambucus.eldercraft.proxy.ServerProxy")
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
}
