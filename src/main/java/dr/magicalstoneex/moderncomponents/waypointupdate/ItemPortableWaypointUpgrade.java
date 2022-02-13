package dr.magicalstoneex.moderncomponents.waypointupdate;

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

import static dr.magicalstoneex.moderncomponents.Items.DRIVER_WAYPOINT_UPGRADE;
import static dr.magicalstoneex.moderncomponents.Items.WAYPOINT_UPDATE;

@Mod.EventBusSubscriber
public class ItemPortableWaypointUpgrade extends Item {



    public ItemPortableWaypointUpgrade() {
        setRegistryName(ModernComponents.MODID, "portable_waypoint_upgrade");
        setUnlocalizedName("waypointUpgrade");
        setCreativeTab(Items.getCreativeTab());
    }


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(WAYPOINT_UPDATE);
        Driver.add(DRIVER_WAYPOINT_UPGRADE = new DriverWaypointUpgrade());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerClient(RegistryEvent.Register<Item> event) {
        ModelLoader.setCustomModelResourceLocation(WAYPOINT_UPDATE, 0,
                new ModelResourceLocation(
                        WAYPOINT_UPDATE.getRegistryName(), "inventory"));
    }
}
