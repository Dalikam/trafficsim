package kamil_dalidowicz.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SetPriorityCommand extends Command {
    private final Road road;
    private final boolean priority;

    @JsonCreator
    public SetPriorityCommand(
            @JsonProperty("road") Road road,
            @JsonProperty("priority") boolean priority) {
        this.road = road;
        this.priority = priority;
    }

    public Road getRoad() {
        return road;
    }

    public boolean isPriority() {
        return priority;
    }

    @Override
    public CommandType getType() {
        return CommandType.setPriority;
    }
}
