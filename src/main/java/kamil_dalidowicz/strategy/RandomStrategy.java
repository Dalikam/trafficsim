package kamil_dalidowicz.strategy;

import kamil_dalidowicz.model.Intersection;
import java.util.concurrent.ThreadLocalRandom;

public class RandomStrategy implements LightControlStrategy {
    @Override
    public void apply(Intersection intersection, long stepCount) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            intersection.switchToNextGroup();
        }
    }
}
