package dr.magicalstoneex.moderncomponents.hudglasses.capability;

import dr.magicalstoneex.moderncomponents.ModernComponents;
import dr.magicalstoneex.moderncomponents.hudglasses.capability.models.IModelFactory;
import dr.magicalstoneex.moderncomponents.hudglasses.capability.models.Model;
import dr.magicalstoneex.moderncomponents.hudglasses.capability.models.StaticModel.Factory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber
public class Storage implements Capability.IStorage<IHudModelContainer> {

    private static IForgeRegistry<IModelFactory> modelRegistry;


    @Override
    public final NBTBase writeNBT(Capability<IHudModelContainer> capability, IHudModelContainer instance, EnumFacing side) {
        NBTTagCompound nbt = new NBTTagCompound();
        {
            {
                nbt.setIntArray("color", instance.getBackgroundColor());
            }
            {
                NBTTagCompound models = new NBTTagCompound();
                nbt.setTag("models", models);
                for(Model model : instance.getModels()){
                    {
                        ResourceLocation factoryName = model.getStorageName();
                        IModelFactory factory = modelRegistry.getValue(factoryName);
                        NBTTagList factoryTag = (NBTTagList) models.getTag(factoryName.toString());
                        if(!models.hasKey(factoryName.toString())){
                            models.setTag(factoryName.toString(), factoryTag);
                        }
                        if (factory != null) {
                            NBTBase modelData = factory.write(model);
                            if(modelData != null) {
                                factoryTag.appendTag(modelData);
                            }
                        }
                    }
                }
            }
        }
        return nbt;
    }

    @Override
    public final void readNBT(Capability<IHudModelContainer> capability, IHudModelContainer instance, EnumFacing side, NBTBase nbt) {
        if (!(nbt instanceof NBTTagCompound)) {
            return;
        }
        List<Model> cap = instance.getModels();
        NBTTagCompound nbtC = (NBTTagCompound) nbt;
        {
            int[] from = nbtC.getIntArray("color");
            if(from.length == 4){
                instance.setBackgroundColor(from[0], from[1], from[2], from[3]);
            } else {
                instance.setBackgroundColor(255, 127, 127, 32);
            }
        }
        {
            NBTTagCompound models = nbtC.getCompoundTag("models");
            Set<String> factories = models.getKeySet();
            for(String key : factories){
                NBTTagList m = (NBTTagList) models.getTag(key);
                IModelFactory factory = modelRegistry.getValue(new ResourceLocation(key));
                if(factory == null){
                    continue;
                }
                for (NBTBase data : m){
                    Model specific = factory.read(data);
                    if(specific != null) {
                        cap.add(specific);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerRegistry(RegistryEvent.NewRegistry event){
        RegistryBuilder<IModelFactory> builder = new RegistryBuilder<>();
        builder.setName(new ResourceLocation(ModernComponents.MODID, "modelRegistry"));
        builder.setType(IModelFactory.class);
        modelRegistry = builder.create();
    }

    @SubscribeEvent
    public static void registerModels(RegistryEvent.Register<IModelFactory> event){
        event.getRegistry().register(new Factory());

    }
}
