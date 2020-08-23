package dr.magicalstoneex.moderncomponents;

import dr.magicalstoneex.moderncomponents.item.ItemVirtualInterface;
import net.minecraft.creativetab.CreativeTabs;

public class Items {


    public static CreativeTabs getCreativeTab() {
        if(creativeTab == null){
            for (CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
                if(tab.getTabLabel().equals("OpenComputers")){
                    creativeTab = tab;
                }
            }
            if(creativeTab == null){
                creativeTab = CreativeTabs.REDSTONE;

            }
        }
        return creativeTab;
    }

    private static CreativeTabs creativeTab;



    public static ItemVirtualInterface itemVirtualInterface;
}
