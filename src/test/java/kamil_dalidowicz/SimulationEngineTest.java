package kamil_dalidowicz;

import kamil_dalidowicz.model.*;
import kamil_dalidowicz.service.SimulationEngine;
import kamil_dalidowicz.strategy.LightControlStrategy;
import kamil_dalidowicz.strategy.StrategyFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class StrategySimulationTest {

    private List<Command> twoVehiclesThenSteps() {
        return List.of(
                new AddVehicleCommand("v1", Road.south, Road.north),
                new AddVehicleCommand("v2", Road.north, Road.south),
                new StepCommand(),
                new StepCommand());
    }

    @Test
    void defaultStrategyAllowsBothInFirstStep() {
        LightControlStrategy strategy = StrategyFactory.create(1);
        SimulationEngine engine = new SimulationEngine(strategy);

        TrafficResult res = engine.run(twoVehiclesThenSteps());
        List<List<String>> out = res.getStepStatuses().stream()
                .map(StepStatus::getLeftVehicles)
                .collect(Collectors.toList());

        assertEquals(2, out.size());
        assertEquals(List.of("v1", "v2"), out.get(0));
        assertTrue(out.get(1).isEmpty());
    }

    @Test
    void randomStrategyWithinBounds() {
        LightControlStrategy strategy = StrategyFactory.create(2);
        SimulationEngine engine = new SimulationEngine(strategy);

        TrafficResult res = engine.run(twoVehiclesThenSteps());
        List<List<String>> out = res.getStepStatuses().stream()
                .map(StepStatus::getLeftVehicles)
                .collect(Collectors.toList());

        assertTrue(
                out.get(0).isEmpty() || out.get(0).equals(List.of("v1", "v2")),
                "RandomStrategy step1 must be [] or [v1,v2]");
        assertTrue(
                out.get(1).isEmpty() || out.get(1).equals(List.of("v1", "v2")),
                "RandomStrategy step2 must be [] or [v1,v2]");
    }

    @Test
    void maliciousStrategyDoesntLetOnlyCarsOut() {
        LightControlStrategy strategy = StrategyFactory.create(3);
        SimulationEngine engine = new SimulationEngine(strategy);

        TrafficResult res = engine.run(twoVehiclesThenSteps());
        List<List<String>> out = res.getStepStatuses().stream()
                .map(StepStatus::getLeftVehicles)
                .collect(Collectors.toList());

        assertTrue(out.get(0).isEmpty(), "Malicious step1 should be empty");
        assertTrue(out.get(1).isEmpty(), "Malicious step2 should be empty");
    }
}
