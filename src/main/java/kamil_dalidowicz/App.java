package kamil_dalidowicz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import kamil_dalidowicz.model.*;
import kamil_dalidowicz.strategy.LightControlStrategy;
import kamil_dalidowicz.strategy.StrategyFactory;
import kamil_dalidowicz.service.SimulationEngine;

import java.io.File;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java -jar traffic-sim.jar <input.json> <output.json> [strategyNumber]");
            System.exit(1);
        }
        String in = args[0];
        String out = args[1];
        int strat = args.length >= 3 ? Integer.parseInt(args[2]) : 1;

        LightControlStrategy strategy = StrategyFactory.create(strat);
        SimulationEngine engine = new SimulationEngine(strategy);

        ObjectMapper mapper = new ObjectMapper();
        List<Command> commands = mapper.readValue(
                mapper.readTree(new File(in)).get("commands").toString(),
                new TypeReference<List<Command>>() {
                });

        TrafficResult result = engine.run(commands);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(out), result);

        System.out.println("Done with strategy #" + strat);
    }
}
