package dr.magicalstoneex.moderncomponents;

import dr.magicalstoneex.moderncomponents.component.vi.DriverVirtualInterface;
import dr.magicalstoneex.moderncomponents.item.ItemVirtualInterface;
import li.cil.oc.api.Driver;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxy {

    public CommonProxy(){
        MinecraftForge.EVENT_BUS.register(this);
        Items.itemVirtualInterface = new ItemVirtualInterface();
    }
    public void preInit(FMLPreInitializationEvent event){

    }
    public void init(FMLInitializationEvent event){

    }
    public void postInit(FMLPostInitializationEvent event){

    }


    @SubscribeEvent
    public void registItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(Items.itemVirtualInterface);
        Driver.add(new DriverVirtualInterface());
    }
}
