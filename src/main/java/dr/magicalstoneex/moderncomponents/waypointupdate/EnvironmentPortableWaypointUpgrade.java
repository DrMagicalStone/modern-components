package dr.magicalstoneex.moderncomponents.waypointupdate;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

import static net.minecraft.util.math.MathHelper.floor;

public class EnvironmentPortableWaypointUpgrade extends AbstractManagedEnvironment {

    public static int detectRange;

    public static int positionUpdateInterval;

    private final EnvironmentHost host;

    private String label = "";
    private boolean enabled;
    private byte redstoneLevel;

    EnvironmentPortableWaypointUpgrade(ItemStack itemStack, EnvironmentHost host) {
        this.host = host;
        setNode(Network.newNode(this, Visibility.Neighbors)
                .withComponent("portable_waypoint", Visibility.Neighbors).create());
    }

    @Override
    public void load(NBTTagCompound nbt) {
        super.load(nbt);
        label = nbt.getString("label");
        enabled = nbt.getBoolean("enabled");
        redstoneLevel = nbt.getByte("redstoneLevel");
        if(enabled) {
            mountToWorld();
        }
    }

    @Override
    public void save(NBTTagCompound nbt) {
        super.save(nbt);
        nbt.setString("label", label);
        nbt.setBoolean("enabled", enabled);
        nbt.setByte("redstoneLevel", redstoneLevel);
    }

    @Override
    public void onDisconnect(Node node) {
        if(node.equals(node()) && enabled) {
            demountFromWorld();
        }
        super.onDisconnect(node);
    }

    @Override
    protected void setNode(Node value) {
        super.setNode(value);
    }

    @Callback(value = "label", doc = "string: The label of the portable way point.", getter = true, setter = true)
    public Object[] setOrGetLabel(Context context, Arguments args) throws Exception {
        if(args.isString(0)) {
            label = args.checkString(0);
            return new Object[] {};
        } else {
            return new Object[] {label};
        }
    }

    @Callback(value = "enabled", doc = "boolean: Enable or disable portable way point.", getter = true, setter = true)
    public Object[] enableOrDisable(Context context, Arguments args) throws Exception {
        if(args.isBoolean(0)) {
            if(enabled) {
                if (!args.checkBoolean(0)) {
                    demountFromWorld();
                    enabled = false;
                    return new Object[] {false};
                } else {
                    enabled = true;
                    return new Object[] {true};
                }
            } else {
                if (args.checkBoolean(0)) {
                    mountToWorld();
                    enabled = true;
                    return new Object[] {true};
                } else {
                    enabled = false;
                    return new Object[] {false};
                }
            }
        } else {
            return new Object[] {enabled};
        }
    }

    @Callback(value = "redstoneLevel", doc = "number: Integer between 0 and 15, the set redstone level of this waypoint.", getter = true, setter = true)
    public Object[] setOrGetRedstoneLevel(Context context, Arguments args) throws Exception {
        if(args.isInteger(0)) {
            int inputLevel = args.checkInteger(0);
            if((inputLevel & 0xfffffff0) == 0) {
                redstoneLevel = (byte) inputLevel;
                return new Object[] {};
            } else {
                return new Object[] {"The redstone level can only be an integer between 0 and 15."};
            }
        } else {
            return new Object[] {redstoneLevel};
        }
    }

    public byte getRedstoneLevel() {
        return redstoneLevel;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    protected int phaseOfUpdate = 0;
    protected World lastWorldFrom;
    protected Vec3d lastPosition;

    @Override
    public void update() {
        super.update();
        if(enabled) {
            phaseOfUpdate++;
            if(phaseOfUpdate == positionUpdateInterval) {
                World hostWorld = host.world();
                Vec3d position = new Vec3d(host.xPosition(), host.yPosition(), host.zPosition());
                PortableWayPoints.updateWaypointPosition(
                        node(), hostWorld, position,
                        lastWorldFrom, lastPosition);
                lastWorldFrom = hostWorld;
                lastPosition = position;
                phaseOfUpdate = 0;
            }
        }
    }

    protected void mountToWorld() {
        World hostWorld = host.world();
        Vec3d position = new Vec3d(host.xPosition(), host.yPosition(), host.zPosition());
        PortableWayPoints.mountWayPoint(
                node(), hostWorld, position);
        lastWorldFrom = hostWorld;
        lastPosition = position;
    }

    protected void demountFromWorld() {
        PortableWayPoints.demountWayPoint(node(), lastWorldFrom, lastPosition);
    }

    public Vec3d getPosition() {
        return new Vec3d(host.xPosition(), host.yPosition(), host.zPosition());
    }

    public String getLabel() {
        return label;
    }

    @Mod.EventBusSubscriber
    public static class PortableWayPoints {

        protected static Map<World, PortableWayPoints> portableWayPoints;

        protected final Multimap<ChunkPos, Node> waypointsInChunks;

        public PortableWayPoints() {
            waypointsInChunks = HashMultimap.create();

        }

        public static void mountWayPoint(Node waypoint, World worldFrom, Vec3d position) {
            portableWayPoints.get(worldFrom).mountWayPoint(waypoint, position);
        }

        public void mountWayPoint(Node waypoint, Vec3d position) {
            ChunkPos pos = new ChunkPos(floor(position.x) >> 4, floor(position.z) >> 4);
            waypointsInChunks.put(pos, waypoint);
        }

        public static void updateWaypointPosition(Node waypoint, World worldFrom, Vec3d position,
                                                  World lastWorldFrom, Vec3d lastPosition) {
            ChunkPos pos = new ChunkPos(floor(position.x)  >> 4, floor(position.z)  >> 4);
            Multimap<ChunkPos, Node> waypointsInChunks = portableWayPoints.get(worldFrom).waypointsInChunks;
            if(!waypointsInChunks.containsEntry(pos, waypoint)) {
                demountWayPoint(waypoint, lastWorldFrom, lastPosition);
                waypointsInChunks.put(pos, waypoint);
            }
        }

        public static void demountWayPoint(Node waypoint, World lastWorldFrom, Vec3d lastPosition) {
            portableWayPoints.get(lastWorldFrom).waypointsInChunks
                    .remove(new ChunkPos(floor(lastPosition.x)  >> 4, floor(lastPosition.z)  >> 4), waypoint);
        }

        public static Set<Node> getWaypoints(World world, Vec3d centre) {
            return portableWayPoints.get(world).getWaypoints(new ChunkPos(floor(centre.x)  >> 4, floor(centre.z)  >> 4));
        }

        public Set<Node> getWaypoints(ChunkPos centre) {
            Set<Node> result = new HashSet<>();
            for(int centreX = centre.x, x = centreX - detectRange, maxX = centreX + detectRange; x <= maxX; x++) {
                for(int centreZ = centre.z, z = centreZ - detectRange, maxZ = centreZ + detectRange; z <= maxZ; z++) {
                    ChunkPos chunk;
                    if (x == centreX && z == centreZ) {
                        chunk = centre;
                    } else {
                        chunk = new ChunkPos(x, z);
                    }
                    Collection<Node> thatChunk = waypointsInChunks.get(chunk);
                    if(thatChunk != null) {
                        result.addAll(thatChunk);
                    }
                }
            }
            return result;
        }

        @SubscribeEvent
        public static void newWorld(WorldEvent.CreateSpawnPosition event) {
            mountWorld(event);
        }

        @SubscribeEvent
        public static void loadWorld(WorldEvent.Load event) {
            mountWorld(event);
        }

        public static void mountWorld(WorldEvent event) {
            World worldFrom = event.getWorld();
            if(!worldFrom.isRemote) {
                if(portableWayPoints == null) {
                    portableWayPoints = new HashMap<>();
                }
                PortableWayPoints wayPointsInSingleWorld = new PortableWayPoints();
                portableWayPoints.put(worldFrom, wayPointsInSingleWorld);
            }
        }

        @SubscribeEvent
        public static void unloadWorld(WorldEvent.Unload event) {
            World worldFrom = event.getWorld();
            if(!worldFrom.isRemote) {
                portableWayPoints.remove(worldFrom);
                if(portableWayPoints.isEmpty()) {
                    portableWayPoints = null;
                }
            }
        }
    }

}


