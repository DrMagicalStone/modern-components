package dr.magicalstoneex.moderncomponents.item;

import dr.magicalstoneex.moderncomponents.Items;
import dr.magicalstoneex.moderncomponents.ModernComponents;
import net.minecraft.item.Item;


public class ItemVirtualInterface extends Item {



    public ItemVirtualInterface() {
        setRegistryName(ModernComponents.MODID, "virtual_interface");
        setUnlocalizedName("virtualInterface");
        setCreativeTab(Items.getCreativeTab());
    }
}
