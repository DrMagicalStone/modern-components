package dr.magicalstoneex.moderncomponents.hudglasses.capability.models;

import dr.magicalstoneex.moderncomponents.ModernComponents;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.Constants;

public class StaticModel implements Model {

    public static final ResourceLocation NAME = new ResourceLocation(ModernComponents.NAME, "static");

    private final Vec3d opposite;
    private final Vec3d vertex;

    public StaticModel(Vec3d vertex, Vec3d opposite) {
        this.vertex = vertex;
        this.opposite = opposite;
    }

    @Override
    public Vec3d getOppositeVertex() {
        return opposite;
    }

    @Override
    public Vec3d getOneVertex() {
        return vertex;
    }

    @Override
    public boolean available() {
        return true;
    }

    @Override
    public ResourceLocation getStorageName() {
        return NAME;
    }

    public static class Factory extends ModelFactory {

        public Factory(){
            setRegistryName(NAME);
        }
        @Override
        public NBTBase write(Model m) {
            StaticModel model = (StaticModel) m;
            NBTTagCompound nbt = new NBTTagCompound();
            NBTTagList vertex = new NBTTagList();
            vertex.appendTag(new NBTTagDouble(model.vertex.x));
            vertex.appendTag(new NBTTagDouble(model.vertex.y));
            vertex.appendTag(new NBTTagDouble(model.vertex.z));
            NBTTagList opposite = new NBTTagList();
            opposite.appendTag(new NBTTagDouble(model.opposite.x));
            opposite.appendTag(new NBTTagDouble(model.opposite.y));
            opposite.appendTag(new NBTTagDouble(model.opposite.z));
            nbt.setTag("vertex", vertex);
            nbt.setTag("opposite", opposite);
            return nbt;
        }

        @Override
        public Model read(NBTBase n) {
            NBTTagCompound nbt = (NBTTagCompound) n;
            NBTTagList v = nbt.getTagList("vertex", Constants.NBT.TAG_DOUBLE);
            Vec3d vertex = new Vec3d(v.getDoubleAt(0), v.getDoubleAt(1), v.getDoubleAt(2));
            NBTTagList o = nbt.getTagList("opposite", Constants.NBT.TAG_DOUBLE);
            Vec3d opposite = new Vec3d(o.getDoubleAt(0), o.getDoubleAt(1), o.getDoubleAt(2));
            return new StaticModel(vertex, opposite);
        }
    }

}
