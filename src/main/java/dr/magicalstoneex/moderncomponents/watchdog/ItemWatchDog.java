package dr.magicalstoneex.moderncomponents.watchdog;

import dr.magicalstoneex.moderncomponents.Items;
import dr.magicalstoneex.moderncomponents.ModernComponents;
import li.cil.oc.api.Driver;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static dr.magicalstoneex.moderncomponents.Items.ITEM_WATCH_DOG;


@Mod.EventBusSubscriber
public class ItemWatchDog extends Item {


    public ItemWatchDog() {
        setRegistryName(ModernComponents.MODID, "watch_dog");
        setUnlocalizedName("watchDog");
        setCreativeTab(Items.getCreativeTab());
    }


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ITEM_WATCH_DOG);
        Driver.add(new DriverWatchDog());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerClient(RegistryEvent.Register<Item> event) {
        ModelLoader.setCustomModelResourceLocation(ITEM_WATCH_DOG, 0,
                new ModelResourceLocation(
                        ITEM_WATCH_DOG.getRegistryName(), "inventory"));
    }
}
