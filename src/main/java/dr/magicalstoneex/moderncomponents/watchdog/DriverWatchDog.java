package dr.magicalstoneex.moderncomponents.watchdog;

import dr.magicalstoneex.moderncomponents.Items;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DriverWatchDog extends DriverItem {

    public DriverWatchDog() {
        super(new ItemStack(Items.ITEM_WATCH_DOG));
    }

    @Override
    public ManagedEnvironment createEnvironment(ItemStack itemStack, EnvironmentHost environmentHost) {
        World hostWorld = environmentHost.world();
        if(hostWorld != null && !hostWorld.isRemote) {
            return new EnvironmentWatchDog(itemStack, environmentHost);
        } else {
            return null;
        }
    }

    @Override
    public String slot(ItemStack itemStack) {
        return Slot.Card;
    }

    @Override
    public int tier(ItemStack itemStack) {
        return 1;
    }


}
