package dr.magicalstoneex.moderncomponents.hudglasses.capability.models;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public interface IModelFactory extends IForgeRegistryEntry<IModelFactory> {

    @Nullable
    NBTBase write(Model model);

    @Nullable
    Model read(NBTBase nbt);
}
