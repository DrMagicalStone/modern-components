package dr.magicalstoneex.moderncomponents.hudglasses.capability.models;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public interface Model {

    Vec3d getOneVertex();

    Vec3d getOppositeVertex();

    boolean available();

    ResourceLocation getStorageName();
}
