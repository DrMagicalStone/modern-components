package dr.magicalstoneex.moderncomponents;

import dr.magicalstoneex.moderncomponents.simpleitem.ItemARGlasses;
import dr.magicalstoneex.moderncomponents.watchdog.ItemWatchDog;
import dr.magicalstoneex.moderncomponents.waypointupdate.DriverWaypointUpgrade;
import dr.magicalstoneex.moderncomponents.waypointupdate.ItemAdvancedNavigationUpgrade;
import dr.magicalstoneex.moderncomponents.waypointupdate.ItemNearNavigationUpgrade;
import dr.magicalstoneex.moderncomponents.waypointupdate.ItemPortableWaypointUpgrade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Items {


    public static CreativeTabs getCreativeTab() {
        if (creativeTab == null) {
            creativeTab = new CreativeTabs(ModernComponents.MODID) {
                @Override
                public ItemStack getTabIconItem() {
                    return new ItemStack(Items.ITEM_WATCH_DOG);
                }
            };
        }
        return creativeTab;
    }

    private static CreativeTabs creativeTab;


    public static final ItemWatchDog ITEM_WATCH_DOG = new ItemWatchDog();
    public static final ItemARGlasses ITEM_AR_GLASSES = new ItemARGlasses();
    public static final ItemPortableWaypointUpgrade WAYPOINT_UPDATE = new ItemPortableWaypointUpgrade();
    public static final ItemNearNavigationUpgrade NEAR_NAVIGATION_UPGRADE = new ItemNearNavigationUpgrade();
    public static final ItemAdvancedNavigationUpgrade ADVANCED_NAVIGATION_UPGRADE_UPDATE = new ItemAdvancedNavigationUpgrade();



    public static DriverWaypointUpgrade DRIVER_WAYPOINT_UPGRADE;

}
