package dr.magicalstoneex.moderncomponents.hudglasses.capability;


import dr.magicalstoneex.moderncomponents.hudglasses.capability.models.Model;

import java.util.List;

public interface IHudModelContainer {

    void setBackgroundColor(int r, int g, int b, int a);

    int[] getBackgroundColor();

    List<Model> getModels();
}
