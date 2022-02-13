package dr.magicalstoneex.moderncomponents;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public CommonProxy() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void preInit(FMLPreInitializationEvent event) {
        loadConfig(event);
    }

    protected void loadConfig(FMLPreInitializationEvent event) {
        ConfigLoader.loadCommon(event);
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
