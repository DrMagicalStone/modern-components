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

import static dr.magicalstoneex.moderncomponents.Items.NEAR_NAVIGATION_UPGRADE;

@Mod.EventBusSubscriber
public class ItemNearNavigationUpgrade extends Item {

    public ItemNearNavigationUpgrade() {
        setRegistryName(ModernComponents.MODID, "near_navigation_upgrade");
        setUnlocalizedName("nearNavigationUpgrade");
        setCreativeTab(Items.getCreativeTab());
    }


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(NEAR_NAVIGATION_UPGRADE);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerClient(RegistryEvent.Register<Item> event) {
        ModelLoader.setCustomModelResourceLocation(NEAR_NAVIGATION_UPGRADE, 0,
                new ModelResourceLocation(
                        NEAR_NAVIGATION_UPGRADE.getRegistryName(), "inventory"));
    }
}
