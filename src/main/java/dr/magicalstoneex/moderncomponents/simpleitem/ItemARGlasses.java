package dr.magicalstoneex.moderncomponents.simpleitem;

import dr.magicalstoneex.moderncomponents.Items;
import dr.magicalstoneex.moderncomponents.ModernComponents;
import dr.magicalstoneex.moderncomponents.hudglasses.ItemHudGlasses;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static dr.magicalstoneex.moderncomponents.Items.ITEM_AR_GLASSES;

@Mod.EventBusSubscriber
public class ItemARGlasses extends ItemHudGlasses {

    public static final ArmorMaterial material = EnumHelper.addArmorMaterial(ModernComponents.MODID + ":AR", ModernComponents.MODID + ":ar_glasses", 0, new int[]{0, 0, 0, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f);

    public ItemARGlasses() {
        super(material);
        setCreativeTab(Items.getCreativeTab());
        setRegistryName(ModernComponents.MODID, "ar_glasses");
        setUnlocalizedName("arGlasses");
    }


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ITEM_AR_GLASSES);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerClient(RegistryEvent.Register<Item> event) {
        ModelLoader.setCustomModelResourceLocation(ITEM_AR_GLASSES, 0,
                new ModelResourceLocation(
                        ITEM_AR_GLASSES.getRegistryName(), "inventory"));
    }
}
