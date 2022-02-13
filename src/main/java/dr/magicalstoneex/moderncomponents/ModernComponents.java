package dr.magicalstoneex.moderncomponents;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModernComponents.MODID, name = ModernComponents.NAME, version = ModernComponents.VERSION, dependencies = "required-after:opencomputers")
public class ModernComponents {
    public static final String MODID = "moderncomponents";
    public static final String NAME = "ModernComponents";
    public static final String VERSION = "0.1.0";

    public static Logger getLogger() {
        return logger;
    }

    private static Logger logger;

    @SuppressWarnings("unused")
    @SidedProxy(clientSide = "dr.magicalstoneex.moderncomponents.client.ClientProxy", serverSide = "dr.magicalstoneex.moderncomponents.CommonProxy")
    private static CommonProxy proxy;

    public static ModernComponents getInstance() {
        return instance;
    }

    private static ModernComponents instance;

    public ModernComponents() {
        instance = this;
    }


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
