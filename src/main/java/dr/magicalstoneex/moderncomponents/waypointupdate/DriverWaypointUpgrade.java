package dr.magicalstoneex.moderncomponents.waypointupdate;

import dr.magicalstoneex.moderncomponents.Items;
import li.cil.oc.api.driver.item.HostAware;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverItem;
import li.cil.oc.api.internal.Rotatable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DriverWaypointUpgrade extends DriverItem implements HostAware {
    public DriverWaypointUpgrade() {
        super(new ItemStack(Items.WAYPOINT_UPDATE), new ItemStack(Items.NEAR_NAVIGATION_UPGRADE), new ItemStack(Items.ADVANCED_NAVIGATION_UPGRADE_UPDATE));
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
//        if(!host.world().isRemote) {
//            ModernComponents.getLogger().info(revolve(RegistryManager.ACTIVE, "registries"));
//            try (PrintWriter toFile = new PrintWriter(new FileOutputStream(new File("recipes.txt")))) {
//                toFile.println(RegistryManager.ACTIVE.getRegistry(new ResourceLocation("minecraft", "recipes")).getEntries());
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
        World hostWorld = host.world();
        if (hostWorld == null || !hostWorld.isRemote) {
            Item item = stack.getItem();
            if(Items.WAYPOINT_UPDATE == item) {
                return new EnvironmentPortableWaypointUpgrade(stack, host);
            } else {
                if (Items.NEAR_NAVIGATION_UPGRADE == item) {
                    return new EnvironmentNearNavigationUpgrade(stack, host);
                } else {
                    return new EnvironmentAdvancedNavigationUpgrade(host);
                }
            }
        } else {
            return null;
        }
    }

    @Override
    public String slot(ItemStack stack) {
        return Slot.Upgrade;
    }

    @Override
    public int tier(ItemStack stack) {
        if(Items.WAYPOINT_UPDATE == stack.getItem()) {
            return 1;
        } else {
            if (Items.NEAR_NAVIGATION_UPGRADE == stack.getItem()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    @Override
    public boolean worksWith(ItemStack stack, Class<? extends EnvironmentHost> host) {
        Item item = stack.getItem();
        if(Items.WAYPOINT_UPDATE == item) {
            return true;
        } else {
            if (Items.NEAR_NAVIGATION_UPGRADE == item) {
                return true;
            } else {
                return Rotatable.class.isAssignableFrom(host);
            }
        }
    }
}
