package dr.magicalstoneex.moderncomponents.watchdog;

import dr.magicalstoneex.moderncomponents.ModernComponents;
import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.MachineHost;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

public class EnvironmentWatchDog extends AbstractManagedEnvironment {

    private final EnvironmentHost host;
    private String shutdownMessage;
    private double feedDogInterval;
    private boolean enableFeedDog;

    public EnvironmentWatchDog(ItemStack itemStack, EnvironmentHost environmentHost) {
        host = environmentHost;
        setNode(Network.newNode(this, Visibility.Network).withComponent("watchdog", Visibility.Neighbors).create());
    }

    @Override
    public void load(NBTTagCompound nbt) {
        super.load(nbt);
        shutdownMessage = nbt.getString("shutdownMessage");
        feedDogInterval = nbt.getDouble("feedDogFreq");
        enableFeedDog = nbt.getBoolean("enableFeedDog");
        feedDogIntervalTicks = (int) feedDogInterval * 20;
    }

    @Override
    public void save(NBTTagCompound nbt) {
        super.save(nbt);
        nbt.setString("shutdownMessage", shutdownMessage);
        nbt.setDouble("feedDogFreq", feedDogInterval);
        nbt.setBoolean("enableFeedDog", enableFeedDog);
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message);
        if (message.name().equals("computer.stopped")) {
            enableFeedDog = false;
            ticksAfterLastFeed = 0;
        } else {
            Object[] data = message.data();
            if (message.name().equals("computer.signal")
                    && (data.length > 4)
                    && "modem_message".equals(data[0])
                    && data[4] instanceof byte[]
                    && Arrays.equals(shutdownMessage.getBytes(), (byte[]) data[4])
                    && host instanceof MachineHost) {
                ((MachineHost) host).machine().stop();
            }
        }
    }

    @Callback(value = "shutdownMessage", doc = "message:string; To set the shutdown message. " +
            "Everytime when the modem of this computer received this message, the computer will be shut down forcibly.",
            setter = true, getter = true)
    public Object[] setAndGetShutdownMessage(Context context, Arguments args) throws Exception {
        String old = shutdownMessage;
        if(args.isString(0)) {
            shutdownMessage = args.checkString(0);
            host.markChanged();
        }
        return new Object[]{old};
    }

    @Callback(value = "enableFeedDog", doc = "function(enableFeedDog:boolean):boolean; " +
            "If enableFeedDog is true, the program must feed dog every time.", setter = true, getter = true)
    public  Object[] setEnableFeedDog(Context context, Arguments args) throws Exception {
        boolean last = enableFeedDog;
        if(args.isBoolean(0)) {
            enableFeedDog = args.checkBoolean(0);
            ticksAfterLastFeed = 0;
            host.markChanged();
        }
        return new Object[]{last};
    }

    @Callback(value = "feedDogInterval", doc = "function(interval:number):double; Set the max interval between two time feeding dog." +
            "Don't make the interval too small.", setter = true, getter = true)
    public Object[] setAndGetFeedDogInterval(Context context, Arguments args) throws Exception {
        double previous = feedDogInterval;
        if(args.isDouble(0)) {
            feedDogInterval = args.checkDouble(0);
            feedDogIntervalTicks = (int) feedDogInterval * 20;
            host.markChanged();
        }
        return new Object[] {previous};
    }

    private int feedDogIntervalTicks = 0;
    private int ticksAfterLastFeed = 0;

    @Callback(doc = "function(); Feed dog")
    public Object[] feedDog(Context context, Arguments args) throws Exception {
        ticksAfterLastFeed = 0;
        host.markChanged();
        return new Object[] {};
    }

    @Override
    public void update() {
        super.update();
        if(enableFeedDog) {
            if(ticksAfterLastFeed == feedDogIntervalTicks) {
                ModernComponents.getLogger().info("stopped.");
                ((MachineHost) host).machine().stop();
            }
            ticksAfterLastFeed++;
            host.markChanged();
        }
    }

    @Override
    public boolean canUpdate() {
        return true;
    }
}

