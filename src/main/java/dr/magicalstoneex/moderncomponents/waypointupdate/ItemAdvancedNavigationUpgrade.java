package dr.magicalstoneex.moderncomponents.waypointupdate;

import com.google.gson.JsonObject;
import dr.magicalstoneex.moderncomponents.Items;
import dr.magicalstoneex.moderncomponents.ModernComponents;
import li.cil.oc.Settings;
import li.cil.oc.api.Driver;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

import static dr.magicalstoneex.moderncomponents.Items.ADVANCED_NAVIGATION_UPGRADE_UPDATE;
import static dr.magicalstoneex.moderncomponents.Items.DRIVER_WAYPOINT_UPGRADE;

@Mod.EventBusSubscriber
public class ItemAdvancedNavigationUpgrade extends Item {

    public ItemAdvancedNavigationUpgrade() {
        setRegistryName(ModernComponents.MODID, "advanced_navigation_upgrade");
        setUnlocalizedName("advancedNavigationUpgrade");
        setCreativeTab(Items.getCreativeTab());
    }


    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ADVANCED_NAVIGATION_UPGRADE_UPDATE);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerClient(RegistryEvent.Register<Item> event) {
        ModelLoader.setCustomModelResourceLocation(ADVANCED_NAVIGATION_UPGRADE_UPDATE, 0,
                new ModelResourceLocation(
                        ADVANCED_NAVIGATION_UPGRADE_UPDATE.getRegistryName(), "inventory"));
    }

    private static final ResourceLocation originalNavigationUpgradeName = new ResourceLocation(Settings.namespace() + "navigationupgrade");

    @SubscribeEvent
    public static void attachMapData(PlayerEvent.ItemCraftedEvent event) {
        ItemStack stack = event.crafting;
        if(stack.getItem() == ADVANCED_NAVIGATION_UPGRADE_UPDATE) {
            NBTTagCompound nbt = DRIVER_WAYPOINT_UPGRADE.dataTag(stack);
            IInventory ingredients = event.craftMatrix;
            for (int i = 0, length = ingredients.getSizeInventory(); i < length; i++) {
                ItemStack ingredientWithMap = ingredients.getStackInSlot(i);
                Item ingredientType = ingredientWithMap.getItem();
                if(ingredientType.isMap()) {
                    nbt.setTag(Settings.namespace() + "map", ingredientWithMap.writeToNBT(new NBTTagCompound()));
                    break;
                } else {
                    if((ingredientType == ADVANCED_NAVIGATION_UPGRADE_UPDATE || Objects.equals(ingredientType.getRegistryName(), originalNavigationUpgradeName))) {
                        if(ingredientWithMap.hasTagCompound()) {
                            NBTTagCompound tag = ingredientWithMap.getTagCompound();
                            assert tag != null;
                            if(tag.hasKey("oc:data", Constants.NBT.TAG_COMPOUND)) {
                                nbt.setTag(Settings.namespace() + "map", tag.getCompoundTag("oc:data").getCompoundTag(Settings.namespace() + "map").copy());
                                break;
                            }
                        }
                    }
                }
            }

        }
    }

}
