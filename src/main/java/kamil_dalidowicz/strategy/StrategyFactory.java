package kamil_dalidowicz.strategy;

public class StrategyFactory {
    public static LightControlStrategy create(int strategyNumber) {
        return switch (strategyNumber) {
            case 2 -> new RandomStrategy();
            case 3 -> new MaliciousStrategy();
            default -> new NoStrategy();
        };
    }
}
