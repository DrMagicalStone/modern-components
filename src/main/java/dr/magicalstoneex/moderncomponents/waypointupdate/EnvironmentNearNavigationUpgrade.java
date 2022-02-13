package dr.magicalstoneex.moderncomponents.waypointupdate;

import li.cil.oc.api.Network;
import li.cil.oc.api.internal.Rotatable;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import scala.collection.Map;
import scala.collection.mutable.HashMap;

import java.util.Set;

public class EnvironmentNearNavigationUpgrade extends AbstractManagedEnvironment {

    EnvironmentHost host;

    EnvironmentNearNavigationUpgrade(ItemStack itemStack, EnvironmentHost environmentHost) {
        setNode(Network.newNode(this, Visibility.Neighbors).withComponent("near_navigation", Visibility.Neighbors).create());
        this.host = environmentHost;
    }

    @Callback(doc = "function(range:number):table -- Find waypoints in the specified range.")
    public Object[] findWaypoints(Context context, Arguments args) {
        return new Object[] {EnvironmentNearNavigationUpgrade.findWaypoints(host)};
    }

    @Callback(doc = "function():number -- Get the current orientation of the robot.")
    public Object[] getFacing(Context context, Arguments args) {
        if(host instanceof Rotatable) {
            return new Object[] {((Rotatable) host).facing().getIndex()};
        }
        return new Object[] {null, "This device doesn't have a facing."};
    }

    protected static Map<String, Object>[] findWaypoints(EnvironmentHost host) {
        Vec3d hostPos = new Vec3d(host.xPosition(), host.yPosition(), host.zPosition());
        Set<Node> portableWaypoints = EnvironmentPortableWaypointUpgrade.PortableWayPoints.getWaypoints(host.world(),
                hostPos);
        Map<String, Object> [] waypoints = (Map<String, Object> []) new Map[portableWaypoints.size()];
        {
            int i = 0;
            for(Node aWaypointNode : portableWaypoints) {
                HashMap<String, Object> aWaypoint = new HashMap<>();
                EnvironmentPortableWaypointUpgrade theEnvironment = (EnvironmentPortableWaypointUpgrade) aWaypointNode.host();

                Vec3d position = theEnvironment.getPosition().subtract(hostPos);
                aWaypoint.put("position", new double[] {position.x, position.y, position.z});
                aWaypoint.put("redstone", (int) theEnvironment.getRedstoneLevel());
                aWaypoint.put("label", theEnvironment.getLabel());
                aWaypoint.put("portable", true);
                waypoints[i] = new scala.collection.immutable.HashMap<String, Object>().$plus$plus(aWaypoint);
                i++;
            }
        }
        return waypoints;
    }

}
