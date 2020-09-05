package dr.magicalstoneex.moderncomponents;

import dr.magicalstoneex.moderncomponents.item.ItemVirtualInterface;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Items {


    public static CreativeTabs getCreativeTab() {
        if(creativeTab == null){
            creativeTab = new CreativeTabs(ModernComponents.MODID) {
                @Override
                public ItemStack getTabIconItem() {
                    return new ItemStack(Items.itemVirtualInterface);
                }
            };
        }
        return creativeTab;
    }

    private static CreativeTabs creativeTab;



    public static ItemVirtualInterface itemVirtualInterface;
}
