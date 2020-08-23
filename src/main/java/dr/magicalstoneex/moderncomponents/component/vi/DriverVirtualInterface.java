package dr.magicalstoneex.moderncomponents.component.vi;

import dr.magicalstoneex.moderncomponents.Items;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverItem;
import net.minecraft.item.ItemStack;

public class DriverVirtualInterface extends DriverItem {

    public DriverVirtualInterface(){
        super(new ItemStack(Items.itemVirtualInterface));
    }
    @Override
    public ManagedEnvironment createEnvironment(ItemStack itemStack, EnvironmentHost environmentHost) {
        return new EnvironmentVirtualInterface(itemStack, environmentHost);
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
