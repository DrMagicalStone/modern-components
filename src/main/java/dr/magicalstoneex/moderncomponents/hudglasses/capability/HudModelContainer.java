package dr.magicalstoneex.moderncomponents.hudglasses.capability;

import dr.magicalstoneex.moderncomponents.hudglasses.capability.models.Model;

import java.util.ArrayList;
import java.util.List;

public class HudModelContainer implements IHudModelContainer {

    private final int[] backgroundColor = new int[4];
    private final List<Model> models = new ArrayList<>();

    @Override
    public void setBackgroundColor(int r, int g, int b, int a) {
        backgroundColor[0] = r;
        backgroundColor[1] = g;
        backgroundColor[2] = b;
        backgroundColor[3] = a;
    }

    @Override
    public int[] getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public List<Model> getModels() {
        return models;
    }


}
