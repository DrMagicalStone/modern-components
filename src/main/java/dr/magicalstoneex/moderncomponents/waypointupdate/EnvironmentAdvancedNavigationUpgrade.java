package dr.magicalstoneex.moderncomponents.waypointupdate;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Node;
import li.cil.oc.server.component.UpgradeNavigation;
import net.minecraft.util.math.Vec3d;
import scala.collection.Map;
import scala.collection.mutable.HashMap;

import java.util.Arrays;
import java.util.Set;


public class EnvironmentAdvancedNavigationUpgrade extends UpgradeNavigation {
    public EnvironmentAdvancedNavigationUpgrade(EnvironmentHost host) {
        super(host);
    }

    @Override
    @Callback(doc = "function(range:number):table -- Find waypoints in the specified range.")
    public Object[] findWaypoints(Context context, Arguments args) {
        Object[] result = super.findWaypoints(context, args);
        Map<String, Object> [] unportable = (Map<String, Object>[]) result[0];
        Map<String, Object> [] portable = EnvironmentNearNavigationUpgrade.findWaypoints(host());
        Map<String, Object> [] combination = Arrays.copyOf(unportable, unportable.length + portable.length);
        System.arraycopy(portable, 0, combination, unportable.length, portable.length);
        result[0] = combination;
        return result;
    }
}
