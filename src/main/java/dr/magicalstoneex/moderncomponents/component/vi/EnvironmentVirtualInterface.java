package dr.magicalstoneex.moderncomponents.component.vi;

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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnvironmentVirtualInterface extends AbstractManagedEnvironment {

    private final ItemStack base;
    private final EnvironmentHost host;
    private String shutdownMessage;
    private Set<EVirtualComponent> vcomponents = new HashSet<>();

    public EnvironmentVirtualInterface(ItemStack itemStack, EnvironmentHost environmentHost){
        base = itemStack;
        host = environmentHost;
        setNode(Network.newNode(this, Visibility.Network).withComponent("virtualinterface", Visibility.Neighbors).create());
    }

    @Override
    public void load(NBTTagCompound nbt) {
        super.load(nbt);
        shutdownMessage = nbt.getString("shutdownMessage");
    }

    @Override
    public void save(NBTTagCompound nbt) {
        super.save(nbt);
        nbt.setString("shutdownMessage", shutdownMessage);
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message);
        if(message.name().equals("computer.stopped")){
            for (EVirtualComponent component : vcomponents) {
                component.node().remove();
            }
            vcomponents.clear();
        } else {
            Object[] data = message.data();
            if(message.name().equals("computer.signal")
                    && (data.length > 4)
                    && "modem_message".equals(data[0])
                    && data[4] instanceof byte[]
                    && Arrays.equals(shutdownMessage.getBytes(), (byte[]) data[4])
                    && host instanceof MachineHost){
                ((MachineHost) host).machine().stop();
                node().sendToReachable("computer.stopped");
            }
        }
    }

    @Callback(doc = "function(component:table, type:string):boolean;")
    public Object[] registerComponent(Context context, Arguments args){
        if(host instanceof MachineHost){
            Map<String, Object> component = args.checkTable(0);
            vcomponents.add(new EVirtualComponent(((MachineHost) host).machine(), component, args.checkString(1)));
            return new Object[]{true};
        }
        return new Object[]{false};
    }

    @Callback(doc = "function(message:boolean/integer/double/string/bytearray):string; The message can be any \"modem_message\".")
    public Object[] setShutdownMessage(Context context, Arguments args){
        String old = shutdownMessage;
        shutdownMessage = args.checkString(0);
        return new Object[]{old};
    }
    @Callback(doc = "function():string;")
    public Object[] getShutdownMessage(Context context, Arguments args){
        return new Object[]{shutdownMessage};
    }

    @Callback(doc = "function(value:any);")
    public Object[] testValue(Context context, Arguments args){
        Object any = args.checkAny(0);
        return new Object[0];
    }
}

