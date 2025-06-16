package kamil_dalidowicz.service;

import kamil_dalidowicz.model.*;
import kamil_dalidowicz.strategy.LightControlStrategy;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final Intersection intersection;
    private final LightControlStrategy strategy;
    private long stepCount = 0;

    public SimulationEngine(LightControlStrategy strategy) {
        this.strategy = strategy;
        this.intersection = new Intersection();
    }

    public TrafficResult run(List<Command> commands) {
        List<StepStatus> statuses = new ArrayList<>();
        for (Command cmd : commands) {
            switch (cmd.getType()) {
                case addVehicle -> {
                    AddVehicleCommand av = (AddVehicleCommand) cmd;
                    intersection.addVehicle(av.getVehicleId(), av.getStartRoad(), av.getEndRoad());
                }
                case setPriority -> {
                    SetPriorityCommand sp = (SetPriorityCommand) cmd;
                    intersection.setPriority(sp.getRoad(), sp.isPriority());
                }
                case step -> {
                    stepCount++;
                    List<String> left = intersection.step(strategy);
                    strategy.apply(intersection, stepCount);
                    StepStatus st = new StepStatus(left);
                    statuses.add(st);
                }
            }
        }
        return new TrafficResult(statuses);
    }
}
