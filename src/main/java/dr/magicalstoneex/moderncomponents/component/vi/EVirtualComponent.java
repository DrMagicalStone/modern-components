package dr.magicalstoneex.moderncomponents.component.vi;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.Machine;
import li.cil.oc.api.machine.Value;
import li.cil.oc.api.network.ManagedPeripheral;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EVirtualComponent extends AbstractManagedEnvironment implements ManagedPeripheral {

    private final Map component;
    private final Set<String> methods = new HashSet<>();
    private final boolean haveInnerMethods;
    private final Machine machine;
    public EVirtualComponent(Machine machine, Map<String, Object> component, String type) {

        if(!(component instanceof Value)){
            throw new IllegalArgumentException("Required a lua table, got a normal Map");
        }

        this.component = component;
        this.machine = machine;
        boolean fm = false;
        boolean fi = false;

        for (String method : component.keySet()) {
            if("methods".equals(method)){
                fm = true;
                continue;
            }
            if("invoke".equals(method)){
                fi = true;
                continue;
            }
            methods.add(method);
        }
        haveInnerMethods = fm && fi;
        setNode(Network.newNode(this, Visibility.Neighbors).withComponent(type, Visibility.Neighbors).create());
        {
            Node mnode = machine.node();
            mnode.network().connect(mnode, node());
        }
    }

    @Override
    public String[] methods() {
        if(haveInnerMethods){
            String[] imethods = null;
            try {
                imethods = (String[]) machine.invoke((Value) component, "method", new Object[0]);
            } catch (Exception e) {
            }
            int size = methods.size();
            String[] amethods = methods.toArray(new String[methods.size() + imethods.length]);
            for(int i = 0; i < imethods.length; i++){
                amethods[i + size] = imethods[i];
            }
            return amethods;
        } else {
            return methods.toArray(new String[methods.size()]);
        }
    }

    @Override
    public Object[] invoke(String method, Context context, Arguments args) throws Exception {
        if(methods.contains(method)){
            return machine.invoke((Value) component, method, args.toArray());
        } else{
            if(haveInnerMethods){
                Object[] arg = new Object[args.count() + 1];
                arg[0] = method;
                Object[] arr = args.toArray();
                for(int i = 0; i < arr.length; i++){
                    arg[i+1] = arr[i];
                }
                return machine.invoke((Value) component,"invoke", arg);
            }
        }
        return new Object[0];
    }
}
