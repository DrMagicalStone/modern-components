package dr.magicalstoneex.moderncomponents.client;

import dr.magicalstoneex.moderncomponents.CommonProxy;
import dr.magicalstoneex.moderncomponents.ConfigLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    protected void loadConfig(FMLPreInitializationEvent event) {
        ConfigLoader.loadClient(event);
    }
}
