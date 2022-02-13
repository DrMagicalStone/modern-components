package dr.magicalstoneex.moderncomponents;

import dr.magicalstoneex.moderncomponents.waypointupdate.EnvironmentPortableWaypointUpgrade;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ConfigLoader {

    public static void loadCommon(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        loadCommon(config);
    }

    @SideOnly(Side.CLIENT)
    public static void loadClient(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        loadCommon(config);
        loadClient(config);
    }

    private static void loadCommon(Configuration config) {
        EnvironmentPortableWaypointUpgrade.detectRange = config.getInt("portable_waypoint.detect_range",
                Configuration.CATEGORY_GENERAL, 2, 0, 15,
                "The max distance between a portable waypoint and a near navigation upgrade, " +
                        "in Chebyshev distance. The unit is chunk.");
        EnvironmentPortableWaypointUpgrade.positionUpdateInterval = config.getInt(
                "portable_waypoint.position_upgrade_interval", Configuration.CATEGORY_GENERAL,
                20, 1, Integer.MAX_VALUE,
                "The interval between two times a portable waypoint upgrade its position which unit is tick.");
    }

    @SideOnly(Side.CLIENT)
    private static void loadClient(Configuration config) {

    }
}
