package kamil_dalidowicz.strategy;

import kamil_dalidowicz.model.Intersection;

public class MaliciousStrategy implements LightControlStrategy {
    @Override
    public void apply(Intersection intersection, long stepCount) {
        intersection.switchToNextGroup();
    }
}