package kamil_dalidowicz.strategy;

import kamil_dalidowicz.model.Intersection;

public interface LightControlStrategy {
    void apply(Intersection intersection, long stepCount);
}
