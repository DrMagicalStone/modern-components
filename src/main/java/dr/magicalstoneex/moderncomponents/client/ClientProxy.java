package dr.magicalstoneex.moderncomponents.client;

import dr.magicalstoneex.moderncomponents.CommonProxy;
import dr.magicalstoneex.moderncomponents.Items;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void registItems(RegistryEvent.Register<Item> event) {
        super.registItems(event);
        ModelLoader.setCustomModelResourceLocation(Items.itemVirtualInterface, 0,
                new ModelResourceLocation(
                        Items.itemVirtualInterface.getRegistryName(), "inventory"));
    }
}
